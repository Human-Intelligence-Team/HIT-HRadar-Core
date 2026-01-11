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
            withCredentials([...]) {
                bat """
                curl ...
                """
            }
        }

        failure {
            withCredentials([...]) {
                bat """
                curl ...
                """
            }
        }
    }
}
