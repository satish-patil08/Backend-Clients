pipeline {
    agent any

    stages {
        stage('Generate Jar Files') {
            steps {
                sh 'mvn clean install' // Build the Spring Boot project and generate the JAR file
            }
        }

        stage('Delete the old containers and deploy new JAR') {
            steps {
                sh '''
                # Stop the existing service
                sudo systemctl stop backend-notificationbackend-clients || echo "Service already stopped or does not exist"

                # Clean up the deployment directory
                sudo rm -rf /var/lib/jenkins/backend/backend-clients/*

                # Copy the new JAR file to the deployment directory
                sudo cp -r target/*.jar /var/lib/jenkins/backend/backend-clients/

                # Change ownership of the files to the appropriate user
                sudo chown -R azureuser:azureuser /var/lib/jenkins/backend/backend-clients/

                # Rename the JAR file to app.jar for consistency
                sudo mv /var/lib/jenkins/backend/backend-clients/*.jar /var/lib/jenkins/backend/backend-clients/app.jar
                '''
            }
        }

        stage('Run the updated service') {
            steps {
                sh '''
                sudo systemctl start backend-clients

                # Check the status of the service to ensure it started successfully
                sudo systemctl status backend-clients --no-pager
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded! The application has been deployed successfully.'
        }
        failure {
            echo 'Pipeline failed! Check the logs for errors.'
        }
        always {
            echo 'Cleaning up workspace...'
            cleanWs() // Clean up the Jenkins workspace
        }
    }
}