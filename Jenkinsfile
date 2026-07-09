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
                    sudo pkill -f clg-0.0.1-SNAPSHOT.jar || true

            export BUILD_ID=dontKillMe

            cd /var/lib/jenkins/workspace/web

            nohup java -jar target/clg-0.0.1-SNAPSHOT.jar > spring.log 2>&1 < /dev/null &

            sleep 5

            ps -ef | grep clg || true
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
