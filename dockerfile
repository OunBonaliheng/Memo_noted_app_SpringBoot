# BUILD STAGE
FROM maven:3.8.7-eclipse-temurin-19 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy current local directory to /app inside the container
COPY . .

# Clean the existing build and package the application to create JAR file
RUN mvn clean package

# RUN STAGE
FROM eclipse-temurin:21-jre-jammy

# Copy the executable JAR file from build stage to /app directory in container and rename it to app.jar
COPY --from=build /app/target/*.jar /app/app.jar

# Expose the port on which your Spring application will run (change as per your application)
EXPOSE 8080

# Set the command to run your Spring application when the container starts
CMD ["java", "-jar", "/app/app.jar"]

