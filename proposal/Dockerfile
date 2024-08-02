FROM openjdk:17

COPY target/proposta-app-0.0.1-SNAPSHOT.jar app.jar

COPY wait-for-it.sh wait-for-it.sh

RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "rabbitmq:5672", "--", "java", "-Duser.language=pt", "-Duser.country=BR", "-jar", "app.jar"]