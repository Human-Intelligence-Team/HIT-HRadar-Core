pipeline {
    agent any

    tools {
        jdk 'openjdk21'
    }

    environment {
        DISCORD_WEBHOOK = credentials('discord-webhook-backend')
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
            sh """
            curl -H "Content-Type: application/json" \\
                 -X POST \\
                 -d '{
                   "content": "✅ **HRadar CI 성공**\\n브랜치: ${env.BRANCH_NAME}\\n빌드: #${env.BUILD_NUMBER}"
                 }' \\
                 ${DISCORD_WEBHOOK}
            """
        }

        failure {
            sh """
            curl -H "Content-Type: application/json" \\
                 -X POST \\
                 -d '{
                   "content": "❌ **HRadar CI 실패**\\n브랜치: ${env.BRANCH_NAME}\\n빌드: #${env.BUILD_NUMBER}"
                 }' \\
                 ${DISCORD_WEBHOOK}
            """
        }
    }
}
