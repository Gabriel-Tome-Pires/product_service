FROM ubuntu:latest
LABEL authors="gtpkt"

WORKDIR /app

# Copiar o jar aqui(Quando tiver)
COPY target/myapp.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]