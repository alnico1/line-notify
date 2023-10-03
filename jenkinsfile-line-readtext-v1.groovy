pipeline {
    agent {label 'slave-01'}
    stages {
        stage('Some Other Stage') {
            steps {
                script {
                    // สร้างหรือคำนวณ OUTPUT_PATH ให้มีค่าเป็นเส้นทางไฟล์ output ที่คุณต้องการ
                    def outputFilePath = '/home/jenkins/workspace/LINE-Notify/test.txt'
        
                    // กำหนดค่าของตัวแปรสภาพแวดล้อม OUTPUT_PATH
                    env.OUTPUT_PATH = outputFilePath
                }
            }
        }
        stage('Send Line Notify') {
            steps {
                script {
                    def output = sh(script: '''
                        output=$(cat "$OUTPUT_PATH")
                        LINE_API="https://notify-api.line.me/api/notify"
                        token="0IEAf1pEG9sOE44lKtQjyljyVlckn9T59dFfDYxNVGj"

                        notify_message() {
                            message="$1"
                            token="$2"
                            queryData="message=$message"
                            curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -H "Authorization: Bearer $token" --data "$queryData" "$LINE_API"
                        }

                        notify_message "$output" "$token"
                    ''', returnStatus: true)
                }
            }
        }
    }
}
