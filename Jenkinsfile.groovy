pipeline {
    agent any

    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    docker.build(asi)
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    // Push Docker image to Docker Hub
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                        docker.image(asi).push()
                    }
                }
            }
        }

        stage('Deploy to AWS') {
            steps {
                script {
                    // Use Ansible to deploy to AWS
                    ansiblePlaybook(
                        playbook: 'deploy.yml',
                        inventory: 'inventory.ini',
                        credentialsId: 'ansible-ssh-credentials'
                    )
                }
            }
        }
    }
}
