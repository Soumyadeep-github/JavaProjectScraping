FROM sgrio/java:jdk_15_ubuntu
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/app/pom.xml
COPY src /usr/app/src
WORKDIR /usr/app
RUN mvn clean compile assembly:single
CMD ["java", "-cp", "target/JavaProjectScraping-1.0-SNAPSHOT-jar-with-dependencies.jar","com.imdbscraper.top250.FirstJavaSpider"]
VOLUME /usr/app

