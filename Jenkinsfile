pipeline {
    agent any

    tools {
        jdk 'openJDK21'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat '.\\gradlew.bat clean build'
            }
        }
    }

    post {
        always {
            echo 'CI finished (success or failure)'
        }

        success {
            withCredentials([string(credentialsId: 'discord-webhook-backend', variable: 'DISCORD_WEBHOOK')]) {
                bat """
                curl -X POST ^
                  -H "Content-Type: application/json" ^
                  -d "{\\"content\\":\\"✅ Jenkins CI SUCCESS: %JOB_NAME% #%BUILD_NUMBER%\\"}" ^
                  %DISCORD_WEBHOOK%
                """
            }
        }

        failure {
            withCredentials([string(credentialsId: 'discord-webhook-backend', variable: 'DISCORD_WEBHOOK')]) {
                bat """
                curl -X POST ^
                  -H "Content-Type: application/json" ^
                  -d "{\\"content\\":\\"❌ Jenkins CI FAILED: %JOB_NAME% #%BUILD_NUMBER%\\n%BUILD_URL%\\"}" ^
                  %DISCORD_WEBHOOK%
                """
            }
        }
    }
}
