pipeline {
    agent any

    stages {
       stage('Build') {
            parallel {
                stage('JDK11') {
                    steps {
                        sh 'echo build'
                        sh 'COMMIT=${GIT_COMMIT} docker-compose -p 11-jdk-${GIT_COMMIT} build'
                    }
                }
            }
        }

        stage('Test'){
            parallel {
                stage('JDK11') {
                    steps {
                        sh 'echo test'
                        sh 'COMMIT=${GIT_COMMIT} docker-compose -p 11-jdk-${GIT_COMMIT} up --abort-on-container-exit --exit-code-from riposte-tests'
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'COMMIT=${GIT_COMMIT} docker-compose -p 11-jdk-${GIT_COMMIT} down -v'
        }
    }

}