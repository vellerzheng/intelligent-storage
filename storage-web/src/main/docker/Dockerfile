

FROM java:latest
RUN echo "Asia/Shanghai" > /etc/timezone
VOLUME /opt/data/fileShareDir
ADD storage-web-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8765
