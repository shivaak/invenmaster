# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY target/invenmaster-0.0.1-SNAPSHOT.jar /app/invenmaster.jar

# Make port available to the world outside this container
EXPOSE ${SERVER_PORT}

# Run the jar file with the active profile
ENTRYPOINT ["java", "-jar", "invenmaster.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}", "--server.port=${SERVER_PORT}"]
