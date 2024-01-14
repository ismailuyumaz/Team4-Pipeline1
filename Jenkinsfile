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
FROM node:14-alpine as base
WORKDIR ../Team4-Pipeline1
RUN ls -l
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

ENV NODE_ENV production
USER node

# Copy necessary files from the build stage
COPY --from=build /usr/src/app/next.config.js .

EXPOSE 4000
CMD npm run dev
"""
                    writeFile(file: 'Dockerfile', text: dockerfileContent)
                }
            }
        }

        stage('Build and Run Node.js App in Docker') {
            steps {
                script {
                    dir('bussinbee/src/app') {
                        sh 'docker build -t my-node-app .'
                        sh 'docker run -p 4000:4000 -d --name karaoke my-node-app'
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
