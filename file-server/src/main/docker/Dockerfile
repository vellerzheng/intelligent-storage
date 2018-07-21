
FROM openjdk:8-jdk
RUN apt-get update && apt-get install -y --no-install-recommends openjfx && rm -rf /var/lib/apt/lists/*
RUN echo "Asia/Shanghai" > /etc/timezone
VOLUME /opt/data/fileShareDir
ADD file-server-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=docker"]
EXPOSE 8764
