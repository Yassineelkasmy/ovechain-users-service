FROM openjdk:11 as build
VOLUME /tmp
ADD target/users*.jar /app.jar
CMD ["java","-jar","/app.jar","--spring.profiles.active=prod"]