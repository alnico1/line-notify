pipeline {
    agent {label 'slave-01'}

    stages {
        stage('Execute Script') {
            steps {
                script {
                        def output = sh(script: '''
                        output=$(cat /home/jenkins/workspace/LINE-Notify/test.txt) # แทน /path/to/test.txt ด้วยเส้นทางที่ถูกต้องไปยังไฟล์ txt
                        LINE_API="https://notify-api.line.me/api/notify"
                        token="0IEAf1pEG9sOE44lKtQjyljyVlckn9T59dFfDYxNVGj" # แทน Token ที่คุณคัดลอกมาที่นี่

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
