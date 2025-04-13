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
                            dockerBuilder(
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
                            dockerBuilder(
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
                            dockerBuilder(
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
                            dockerBuilder(
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
                            dockerBuilder(
                                'repository-postgres-solr', 
                                './', 
                                './dspace/src/main/docker/dspace-solr/Dockerfile',
                                [
                                    "solrconfigs=./dspace/solr"
                                ]
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

def dockerBuilder(imageName, contextPath, dockerfileName, extraBuildContext = []) {
    def timestamp = sh(script: "date +%Y.%m.%d%H", returnStdout: true).trim()

    def imageTagLatest = "${DOCKER_REGISTRY}/${DOCKER_PROJECT}/${imageName}:latest"
    def imageTagTimestamped = "${DOCKER_REGISTRY}/${DOCKER_PROJECT}/${imageName}:${timestamp}"

    def buildContextArgs = ""

    if (extraBuildContext) {
        extraBuildContext.each { context ->
            buildContextArgs += " --build-context ${context}"
        }
    }

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
            docker buildx create --use --platform=$ARCHS --name multi-platform-builder-${imageName}
        """
        sh """
            docker buildx inspect --bootstrap
        """
        sh """
            docker \
                buildx \
                build \
                --push \
                --platform=$ARCHS \
                ${buildContextArgs} \
                    -f ${dockerfileName} \
                    -t ${imageTagLatest} \
                    -t ${imageTagTimestamped} \
                ${contextPath}
        """
    }
}