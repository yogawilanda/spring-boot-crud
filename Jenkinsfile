pipeline {
    agent any

    tools {
        jdk 'JDK17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'chmod +x mvnw && ./mvnw -B clean verify'
                    } else {
                        bat 'mvnw.cmd -B clean verify'
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Build and tests passed.'
        }
        failure {
            echo 'Build or tests failed.'
        }
        always {
            archiveArtifacts artifacts: 'target/*.jar',
                             allowEmptyArchive: true
        }
    }
}
