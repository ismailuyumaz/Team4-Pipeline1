pipeline {
    agent any

    stages {
        stage('Git Setup') {
            steps {
                script {
                    // Initialize Git repository
                    sh 'git init'
                }
            }
        }

        stage('Checkout develop') {
            steps {
                script {
                    // Skip default checkout and perform custom Git checkout
                    skipDefaultCheckout()
                    git branch: 'develop', url: 'https://github.com/joejonenjonas/Team4-Pipeline1.git'
                }
            }
        }

        stage('Create Dockerfile') {
            steps {
                script {
                    // Define Dockerfile content
                    def dockerfileContent = """
FROM node:14-alpine as base
WORKDIR /app
COPY package.json ./

# Download dependencies
RUN npm ci --only=production

# Copy source code
COPY . .

# Build the application
RUN npm run build

# Create the final image
FROM node:14-alpine
WORKDIR /app
COPY --from=base /app/dist ./dist

# Set environment variables if needed

# Expose the port that the application listens on
EXPOSE 4000

# Run the application
CMD ["npm", "start"]
"""

                    // Write Dockerfile to the correct location
                    writeFile(file: 'Dockerfile', text: dockerfileContent)
                }
            }
        }

        stage('Build and Run Node.js App in Docker') {
            steps {
                script {
                    dir('bussinbee/src/app') {
                        // Build Docker image and run container
                        sh 'docker build -t my-node-app .'
                        sh 'docker run -p 4000:4000 -d --name my-node-app-container my-node-app'
                    }
                }
            }
        }

        stage('Checkout test') {
            steps {
                script {
                    // Skip default checkout and perform custom Git checkout for the 'test' branch
                    skipDefaultCheckout()
                    git branch: 'test', url: 'https://github.com/joejonenjonas/Team4-Pipeline1.git'
                    sh 'git pull'
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    // Add your Maven build and test commands here
                    sh 'mvn verify -X'
                }
            }
        }

        stage('Cleanup Docker Container') {
            steps {
                script {
                    // Stop and remove the Docker container
                    sh 'docker stop my-node-app-container || true'
                    sh 'docker rm my-node-app-container || true'
                }
            }
        }
    }
}

