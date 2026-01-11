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
            withCredentials([string(credentialsId: 'discord-webhook-backend', variable: 'DISCORD_WEBHOOK')]) {
                sh """
                curl -H "Content-Type: application/json" \
                     -d '{"content": "✅ HRadar Backend CI 성공"}' \
                     $DISCORD_WEBHOOK
                """
            }
        }
        failure {
            withCredentials([string(credentialsId: 'discord-webhook-backend', variable: 'DISCORD_WEBHOOK')]) {
                sh """
                curl -H "Content-Type: application/json" \
                     -d '{"content": "❌ HRadar Backend CI 실패"}' \
                     $DISCORD_WEBHOOK
                """
            }
        }
    }
}
