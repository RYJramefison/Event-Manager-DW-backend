FROM openjdk:21-jdk-slim

# Installer tzdata uniquement pour le fuseau horaire
RUN apt-get update && \
    apt-get install -y --no-install-recommends tzdata && \
    ln -fs /usr/share/zoneinfo/Europe/Paris /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Définir la timezone comme variable d’environnement
ENV TZ=Europe/Paris

# Create a non-root user and group
RUN groupadd -r springboot && useradd -r -g springboot userWithNoRoot

# Set the working directory
WORKDIR /src

# Copy your Spring Boot JAR
COPY build/libs/geo-jobs-tuita.jar geo-jobs-tuita.jar

# Change ownership of the application JAR to the non-root user
RUN chown userWithNoRoot:springboot geo-jobs-tuita.jar

# Switch to the non-root user
USER userWithNoRoot

# Expose the application port
EXPOSE 8080

# Define the entrypoint for your application
ENTRYPOINT ["java", "-jar", "geo-jobs-tuita.jar"]

