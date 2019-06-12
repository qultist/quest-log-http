FROM azul/zulu-openjdk:8

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/http-service.jar"]
EXPOSE 8080

ARG JAR_FILE
COPY target/${JAR_FILE} http-service.jar