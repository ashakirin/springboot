FROM adoptopenjdk/openjdk13

ARG APP_FOLDER=/root
ARG APP_NAME=service-bar
ARG JAR_FILE

COPY target/${JAR_FILE} $APP_FOLDER/service.jar

ENTRYPOINT [ "java", "-jar", "/root/service.jar"]

