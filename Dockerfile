FROM maven:3.6.3-jdk-11-slim AS build
COPY src /build/src
COPY pom.xml /build/
RUN mvn -f /build/pom.xml clean package -DskipTests

FROM openjdk:11
COPY --from=build /build/target/trading_app.war /usr/local/app/trading/lib/trading_app.war
ENTRYPOINT ["java","-jar","/usr/local/app/trading/lib/trading_app.war"]