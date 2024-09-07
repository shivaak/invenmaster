pipeline {
    agent { label 'docker-agent-jdk17' }

    environment {
        BUILD_ENV = 'dev'
        DOCKER_HUB_REPO = 'shivaak/invenmaster'
        DOCKER_HOST = 'tcp://172.18.0.2:2376'
        DOCKER_CERT_PATH = '/certs/client'
        DOCKER_TLS_VERIFY = '1'
        DOCKER_HUB_USERNAME = 'shivaak'
        //APP_VERSION_URL = 'http://157.173.221.117:6001/version'
    }

    stages {
        stage('Check Docker Daemon and Maven Installation') {
            steps {
                script {
                   // sh "sleep 300"

                     sh "docker version"
                     sh "mvn --version"
                }
            }
        }

        stage('Checkout Code From Git') {
            steps {
                git branch: 'main', url: 'https://github.com/shivaak/invenmaster.git'
            }
        }

        stage('Build and Test') {
            steps {
                // Build the code using Maven
                sh 'mvn clean install'
            }
        }

        stage('Versioning') {
            steps {
                script {
                    // Get the current version from the Maven POM file
                    def version = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()

                    // Use the Maven version and Jenkins build ID for the full version tag
                    def fullVersion = "${version}-${env.BUILD_ENV}-${env.BUILD_ID}"

                    // Use the Maven version with environment for the version tag
                    def buildVersion = "${version}-${env.BUILD_ENV}"
                    env.IMAGE_TAG = buildVersion
                    env.IMAGE_VERSION = fullVersion

                    echo "Building Docker image with tag: ${env.IMAGE_TAG}"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image with both versioned and latest tags
                    sh "docker build --build-arg BUILD_ID=${env.IMAGE_VERSION} -t ${env.DOCKER_HUB_REPO}:${env.IMAGE_TAG} ."
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                script {
                   withCredentials([string(credentialsId: 'dockerhub-secret-pwd', variable: 'dockerhub_cred')]) {
                        sh "echo $dockerhub_cred | docker login --username ${env.DOCKER_HUB_USERNAME} --password-stdin"
                        sh "docker push ${env.DOCKER_HUB_REPO}:${env.IMAGE_TAG}"
                    }
                }
            }
        }

        stage('Cleanup Docker Images From Local') {
            steps {
                // Remove all images from docker:dind container
                script {
                    sh '''
                        docker image prune -f
                        docker images --filter=reference="${DOCKER_HUB_REPO}:*dev*" --format "{{.ID}}" | tail -n +1 | xargs --no-run-if-empty docker rmi -f || true
                    '''
                }
            }
        }

        stage('Deploy to Dev Server') {
            steps {
                sshagent(['my-server-private-key']) {
                    sh '''
                    ssh -o StrictHostKeyChecking=no root@157.173.221.117 "
                    cd /root/invenmaster &&

                    # Stop the already running container if any
                    docker-compose --env-file .env.dev -f docker-compose.dev.yaml down &&

                    # Remove the specific image by repository and tag
                    docker rmi -f ${DOCKER_HUB_REPO}:${IMAGE_TAG} || true &&

                    # Pull the latest image
                    docker-compose --env-file .env.dev -f docker-compose.dev.yaml pull &&

                    # Start the new container
                    docker-compose --env-file .env.dev -f docker-compose.dev.yaml up -d
                    "
                    '''
                }
            }
        }

       /*
        stage('Verify Application Running Status and Deployed Version') {
            steps {
                script {
                    // Check the application version to verify deployment
                    def response = sh(script: "curl -s ${env.APP_VERSION_URL}", returnStdout: true).trim()

                    if (response.contains("Build Version : ${env.IMAGE_VERSION}")) {
                        echo "Application is running with the expected version: ${env.IMAGE_VERSION}"
                    } else {
                        error "Expected version ${env.IMAGE_VERSION} not found in response: ${response}"
                    }
                }
            }
        } */

    }

    post {
        always {
            script {
                // Clean up unused volumes after the build
                sh 'docker volume prune -f'
            }
        }
    }


}
