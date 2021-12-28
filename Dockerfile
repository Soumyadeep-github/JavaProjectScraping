# Get base image
FROM sgrio/java:jdk_15_ubuntu

# Update and install maven
RUN apt-get update 
RUN apt-get install -y maven

# Copy maven xml and app directory to container app
COPY pom.xml /usr/app/pom.xml
COPY src /usr/app/src

# Set work directory
WORKDIR /usr/app

# Run maven to create a jar with all dependencies packaged into it
RUN mvn clean compile assembly:single

# Execute the jar and specify the class with class path
CMD ["java", "-cp", "target/JavaProjectScraping-1.0-SNAPSHOT-jar-with-dependencies.jar","com.imdbscraper.top250.FirstJavaSpider"]


