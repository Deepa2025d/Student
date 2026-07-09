pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {
        stage('build stage') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
            post {
                success {
                    echo "build success"
                }
                failure {
                    echo "build failure"
                }
            }
        }
        stage("Run the spring application") {
            steps { 
                sh '''
                    echo "Stopping existing Spring Boot application if running..."
                    if pgrep -f *.jar > /dev/null; then
                        sudo pkill -f *.jar
                        echo "Application stopped."
                    else
                        echo "No existing application running."
                    fi

                    echo "Starting the Spring Boot application..."
                    sudo java -jar target/*.jar > /dev/null 2>&1 &
                '''
            }
        }
    }
    post {
        success {
            echo "pipeline success"
        }
        failure {
            echo "pipeline failure"
        }
    }
}
stage('version') {
            steps {
                sh 'java --version'
            }
        }
