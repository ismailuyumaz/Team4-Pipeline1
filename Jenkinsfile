pipeline {
    agent any

    stages {
        stage('Git Setup') {
            steps {
                sh 'git init'
            }
        }

        stage('Checkout develop') {
            steps {
                script {
                    skipDefaultCheckout()
                    git branch: 'develop', url: 'https://github.com/joejonenjonas/Team4-Pipeline1.git'
                }
            }
        }

        stage('Create Dockerfile') {
            steps {
                script {
                    def dockerfileContent = """
# Use the official Node.js image as the base image
FROM node:14-alpine as base
# Set the working directory inside the container
WORKDIR ../Team4-Pipeline1
RUN ls -l
# Copy package.json and package-lock.json to leverage Docker's caching
COPY package.json ./

# Download dependencies
RUN npm ci --omit=dev

# Create a stage for building
FROM base as build

# Copy the rest of the source files into the image
COPY . .

# Build the application
RUN npm run build

# Create the final stage
FROM base as final

# Set the environment to production
ENV NODE_ENV production

# Switch to the 'node' user
USER node

# Copy necessary files from the build stage
COPY --from=build /usr/src/app/next.config.js .

# Expose the port that the application listens on
EXPOSE 4000

# Run the application
CMD npm run dev







                        
                    """
                    writeFile(file: 'bussinbee/src/app/Dockerfile', text: dockerfileContent)
                }
            }
        }

        stage('Build and Run Node.js App in Docker') {
            steps {
                script {
                    // Assuming your Node.js app is in a directory named 'app'
                    dir('bussinbee/src/app') {
                        // Build Docker image
                        sh 'docker build -t my-node-app .'
                        sh 'docker run -p 4000:3000 -d --name karaoke my-node-app'
                    }
                }
            }
        }

        stage('Checkout test') {
            steps {
                script {
                    skipDefaultCheckout()
                    git branch: 'test', url: 'https://github.com/joejonenjonas/Team4-Pipeline1.git'
                    sh 'git pull'
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    sh 'mvn verify -X'
                }
            }
        }

        stage('Cleanup Docker Container') {
            steps {
                script {
                    sh 'docker stop karaoke || true'
                    sh 'docker rm karaoke || true'
                }
            }
        }
    }
}
