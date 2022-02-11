FROM adoptopenjdk/openjdk15:alpine-jre
ARG JAR_FILE=target/applicationProject-1.0-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8484