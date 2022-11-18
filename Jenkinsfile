pipeline {
    environment { 
        registry = "sarahby/achat" 
        registryCredential = 'dockerhub_id' 
        dockerImage = '' 
    }
  
    agent any

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling...'
                git branch: "sarah",
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
                sh 'mvn sonar:sonar -Dsonar.projectKey=achat -Dsonar.host.url=http://172.10.1.140:9000 -Dsonar.login=454d87808a05c9e0e80efb6d6c900938cf4e2db4'
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
    post {
                success {
                     mail to: "sarah.benyahia@esprit.tn",
                     subject: "success",
                     body: "success on job ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Build URL: ${env.BUILD_URL}"
                }
               failure {
                    mail to: "sarah.benyahia@esprit.tn",
                     subject: "Failure",
                     body: "Failure on job ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Build URL: ${env.BUILD_URL} "     
                }          
            }
}
