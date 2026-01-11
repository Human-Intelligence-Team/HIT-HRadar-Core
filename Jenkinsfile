pipeline {
    agent any

    tools {
        jdk 'openjdk21'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
    }

    post {
        success {
            withCredentials([
                string(credentialsId: 'discord-webhook-backend', variable: 'DISCORD_WEBHOOK')
            ]) {
                sh """
                curl -X POST -H "Content-Type: application/json" \
                     -d '{
                       "content": "✅ HRadar CI 성공\\n브랜치: ${env.BRANCH_NAME}\\n빌드 번호: #${env.BUILD_NUMBER}"
                     }' \
                     "$DISCORD_WEBHOOK"
                """
            }
        }

        failure {
            withCredentials([
                string(credentialsId: 'discord-webhook-backend', variable: 'DISCORD_WEBHOOK')
            ]) {
                sh """
                curl -X POST -H "Content-Type: application/json" \
                     -d '{
                       "content": "❌ HRadar CI 실패\\n브랜치: ${env.BRANCH_NAME}\\n빌드 번호: #${env.BUILD_NUMBER}"
                     }' \
                     "$DISCORD_WEBHOOK"
                """
            }
        }

        always {
            echo "CI finished (success or failure)"
        }
    }
}
