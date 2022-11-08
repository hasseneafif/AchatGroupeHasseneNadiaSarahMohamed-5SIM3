FROM openjdk:11-jre-slim
EXPOSE 8089
ADD target/achat-0.1.0.jar achat.jar
ENTRYPOINT ["java","-jar","/achat.jar"]