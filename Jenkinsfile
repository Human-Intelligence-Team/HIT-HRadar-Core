pipeline {
  agent any

  environment {
    JWT_SECRET_KEY     = credentials('JWT_SECRET_KEY')
    GMAIL_USERNAME     = credentials('GMAIL_USERNAME')
    GMAIL_APP_PASSWORD = credentials('GMAIL_APP_PASSWORD')
  }

  stages {

    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build JAR') {
      steps {
        dir('hradar') {
          bat 'gradlew.bat clean build -x test'
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        dir('hradar') {
          // Docker 이미지 생성
          bat 'docker build -t hradar-backend:latest .'
        }
      }
    }

    stage('Deploy with Docker Compose') {
      steps {
        bat 'docker compose up -d'
      }
    }
  }

  post {
    success {
      echo 'Deployment SUCCESS'
    }
    failure {
      echo 'Deployment FAILED'
    }
  }
}
