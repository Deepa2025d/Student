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
                    echo "Stopping existing Spring Boot application..."

                    pkill -f "clg-0.0.1-SNAPSHOT.jar" || true

                    sleep 5

                    echo "Starting Spring Boot application..."

                    nohup sudo java -jar target/*.jar > spring.log 2>&1 &

                    sleep 10

                    echo "Application Started"
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
