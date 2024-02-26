FROM gradle:8.6-jdk17 as build
WORKDIR /tmp
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
RUN gradle dependencies --refresh-dependencies
COPY src src
RUN gradle build

FROM gradle:8.6-jdk17
WORKDIR /app
COPY --from=build /tmp/build/libs/receptai-server-*-SNAPSHOT.jar /app/app.jar
RUN ls -la .
ENTRYPOINT ["java", "-jar", "app.jar"]
