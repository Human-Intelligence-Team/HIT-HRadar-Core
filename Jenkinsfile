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
            curl -H "Content-Type: application/json" \
                 -d '{"content":"✅ **CI SUCCESS**\\nJob: ${JOB_NAME}\\nBuild: #${BUILD_NUMBER}"}' \
                 "$DISCORD_WEBHOOK"
            """
        }
        failure {
            sh """
            curl -H "Content-Type: application/json" \
                 -d '{"content":"❌ **CI FAILED**\\nJob: ${JOB_NAME}\\nBuild: #${BUILD_NUMBER}"}' \
                 "$DISCORD_WEBHOOK"
            """
        }
    }
}
