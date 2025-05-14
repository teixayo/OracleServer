# Use Maven+JDK 21 to compile & package
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /build/target/Main-0.5.0-BETA.jar ./server.jar

ENV JVM_OPTS=c"-Xms1G -Xmx2G --enable-preview"
ENV PORT=25565

EXPOSE ${PORT}

CMD ["sh", "-c", "java $JVM_OPTS -jar server.jar"]
