pipeline {
    environment { 
        registry = "asma25/achat" 
        registryCredential = 'dockerhub_id' 
        dockerImage = '' 
    }
  
    agent any

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling...'
                git branch: "asma",
                url:'https://github.com/hasseneafif/AchatGroupeHasseneNadiaSarahMohamed-5SIM3.git'
            }
        }
        stage('MVN CLEAN'){
            steps{
                sh 'mvn clean'
            }
        }
         stage('MVN COMPILE'){
            steps{
                sh 'mvn compile'
            }
        }
         stage('MVN SONAR 5SIM3') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
            }
        }
         stage('MVN TEST') {
            steps {
                sh 'mvn test'
            }
        }
         stage('MVN DEPLOY') {
            steps {
                sh 'mvn deploy'
            }
        }
        stage('Building our image') { 
            steps { 
                dir('.'){
                script { 
                    dockerImage = docker.build registry + ":$BUILD_NUMBER" 
                }
                }
            } 
        }
        stage('Deploy our image') { 
            steps { 
                script { 
                    docker.withRegistry( '', registryCredential ) { 
                        dockerImage.push() 
                    }
                } 
            }
        } 
        stage('Cleaning up') { 
            steps { 
                sh "docker rmi $registry:$BUILD_NUMBER" 
            }
        }
        stage('Docker Compose'){
                    steps{
                       dir('Spring/tpAchatProject'){
                            sh 'docker-compose up -d'
                            }
                       }
                    }
    
    }
}
