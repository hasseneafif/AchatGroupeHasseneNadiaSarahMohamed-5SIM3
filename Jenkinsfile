pipeline {
    environment { 
        registry = "nadiyabelhaj/achat" 
        registryCredential = 'dockerhub_id' 
        dockerImage = '' 
    }
    
    agent any
        stages{
        stage('GIT') {
            steps {
                git branch :'nadia',
                url:'https://github.com/hasseneafif/AchatGroupeHasseneNadiaSarahMohamed-5SIM3.git';
            }
        }
        
        stage('MVN CLEAN') {
            steps {
                sh 'mvn clean'
            }
        }
        
        stage('MVN COMPILE') {
            steps {
                sh 'mvn compile'
            }
        }
        
        stage('MVN JUNIT') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('MVN SONAR') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
            }
        }
        
        stage('MVN NEXUS') {
            steps {
                sh 'mvn deploy'
            }
        }
        
        stage('BUILD IMAGE') { 
            steps { 
                script { 
                    dockerImage = docker.build registry + ":$BUILD_NUMBER" 
                }
            } 
        }
        stage('DEPLOY IMAGE') { 
            steps { 
                script { 
                    docker.withRegistry( '', registryCredential ) { 
                        dockerImage.push() 
                    }
                } 
            }
        } 
        stage('CLEAN IMAGE') { 
            steps { 
                sh "docker rmi $registry:$BUILD_NUMBER" 
            }
        }
        
        stage('DOCKER COMPOSE & MYSQL CONTAINERS') {
                    steps{
                       dir('Spring/tpAchatProject'){
                            sh 'docker-compose up -d'
                            }
                       }
                    }
        
    }
        post {
                success {
                     mail to: "nadia.belhaj@esprit.tn",
                     subject: "success",
                     body: "success on job ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Build URL: ${env.BUILD_URL}"
                }
               failure {
                    mail to: "nadia.belhaj@esprit.tn",
                     subject: "Failure",
                      body: "Failure on job ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Build URL: ${env.BUILD_URL} "     
                }          
            }
}
