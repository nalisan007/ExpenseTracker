FROM maven:3.9.2-eclipse-temurin-17 AS build


WORKDIR /app

RUN mkdir -p /root/.m2
RUN echo '<settings><mirrors><mirror><id>central</id><mirrorOf>*</mirrorOf><url>https://repo.maven.apache.org/maven2</url></mirror></mirrors></settings>' > /root/.m2/settings.xml

COPY pom.xml .


RUN mvn dependency:go-offline -B

COPY src ./src


RUN mvn clean package -DskipTests


FROM  eclipse-temurin:21-jdk

WORKDIR /app


COPY --from=build /app/target/ExpenseTracker-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]