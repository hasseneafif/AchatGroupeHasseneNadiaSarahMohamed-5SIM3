FROM openjdk:11-jre-slim
EXPOSE 8089
ADD /var/lib/jenkins/workspace/devops/target/achat-1.0.jar /achat.jar
ENTRYPOINT ["java","-jar","/achat.jar"]
