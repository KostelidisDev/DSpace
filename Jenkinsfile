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
                            build('repository-api', './', './Dockerfile')
                        }
                        script {
                            push('repository-api', './', './Dockerfile')
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