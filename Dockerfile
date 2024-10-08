FROM openjdk:17

LABEL authors="kmg"

ARG JAR_FILE=build/libs/memoa-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENV TZ=Asia/Seoul

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar","-Duser.timezone=Asia/Seoul"]