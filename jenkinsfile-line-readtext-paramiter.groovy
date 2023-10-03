pipeline {
    agent {label 'slave-01'}
    
    parameters {
        string(name: 'lineToken', defaultValue: '0IEAf1pEG9sOE44lKtQjyljyVlckn9T59dFfDYxNVGj', description: 'LINE Notify Token')
        string(name: 'inputFilePath', defaultValue: '/home/jenkins/workspace/LINE-Notify/test.txt', description: 'Path to the output file')
    }
    
    stages {
        stage('Set Value') {
            steps {
                script {
                    def outputFilePath = "${inputFilePath}"
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
                        token="$lineToken"

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
