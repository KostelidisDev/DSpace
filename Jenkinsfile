pipeline {
    agent {
        label 'docker-builder'
    }

    environment {
        DOCKER_PROJECT = 'repository'
    }

    stages {
        stage('Pipeline') {
            parallel {
                stage('API') {
                    steps {
                        script {
                            produce(
                                'repository-api', 
                                './', 
                                './Dockerfile'
                            )
                        }
                    }
                }
                stage('CLI') {
                    steps {
                        script {
                            produce(
                                'repository-cli', 
                                './', 
                                './Dockerfile.cli'
                            )
                        }
                    }
                }
                stage('PostgreSQL with pgcrypto and curl') {
                    steps {
                        script {
                            produce(
                                'repository-postgres-pgcrypto-curl', 
                                './dspace/src/main/docker/dspace-postgres-pgcrypto-curl/', 
                                './dspace/src/main/docker/dspace-postgres-pgcrypto-curl/Dockerfile'
                            )
                        }
                    }
                }
                stage('PostgreSQL with pgcrypto') {
                    steps {
                        script {
                            produce(
                                'repository-postgres-pgcrypto', 
                                './dspace/src/main/docker/dspace-postgres-pgcrypto/', 
                                './dspace/src/main/docker/dspace-postgres-pgcrypto/Dockerfile'
                            )
                        }
                    }
                }
                stage('Solr') {
                    steps {
                        script {
                            produce(
                                'repository-postgres-pgcrypto', 
                                './', 
                                './dspace/src/main/docker/dspace-solr/Dockerfile'
                            )
                        }
                    }
                }
            }
        }
    }

    post {
        failure {
            echo "❌ Build or push failed."
        }
        success {
            echo "✅ All images built and pushed successfully."
        }
    }
}

def produce(imageName, contextPath, dockerfileName) {
    build(imageName, contextPath, dockerfileName)
    push(imageName, contextPath, dockerfileName)
}

def build(imageName, contextPath, dockerfileName) {
    def imageTag = "${DOCKER_REGISTRY}/${DOCKER_PROJECT}/${imageName}:latest"
    sh """
            docker build -f ${dockerfileName} -t ${imageTag} ${contextPath}
        """
}

def push(imageName, contextPath, dockerfileName) {
    def imageTag = "${DOCKER_REGISTRY}/${DOCKER_PROJECT}/${imageName}:latest"

    withCredentials([
        usernamePassword(
            credentialsId: DOCKER_CREDENTIALS_ID, 
            usernameVariable: 'DOCKER_USER', 
            passwordVariable: 'DOCKER_PASS'
        )
    ]) {
        sh """
            echo \$DOCKER_PASS | docker login ${DOCKER_REGISTRY} -u \$DOCKER_USER --password-stdin
        """
        sh """
            docker push ${imageTag}
        """
        sh """
            docker rmi ${imageTag}
        """
    }
}