FROM openjdk:17

COPY viewcount-0.0.1-SNAPSHOT.jar /viewcount-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "viewcount-0.0.1-SNAPSHOT.jar"]

# docker buildx build --platform linux/arm64 --push -t ojt90902/viewcount-test:latest .