pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
            post {
                success {
                    echo 'Build Success'
                }
                failure {
                    echo 'Build Failed'
                }
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                   pkill -f clg-0.0.1-SNAPSHOT.jar || true

                   cd /var/lib/jenkins/workspace/web

                   setsid java -jar target/clg-0.0.1-SNAPSHOT.jar > spring.log 2>&1 < /dev/null &
 
                   sleep 10

                   ps -ef | grep clg || true

                   cat spring.log || true
                   '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline Success'
        }
        failure {
            echo 'Pipeline Failed'
        }
    }
}
