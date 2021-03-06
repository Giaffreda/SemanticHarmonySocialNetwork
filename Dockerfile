FROM alpine/git
WORKDIR /app
RUN git clone https://github.com/Giaffreda/SemanticHarmonySocialNetwork.git

FROM maven:3.5-jdk-8-alpine
WORKDIR /app
COPY --from=0 app/SemanticHarmonySocialNetwork /app
RUN mvn package

FROM openjdk:8-jre-alpine
WORKDIR /app
ENV ADRESS=127.0.0.1 
ENV ID=0
COPY --from=1 /app/target/LucaGiaffreda-1.0-jar-with-dependencies.jar /app

CMD /usr/bin/java -jar LucaGiaffreda-1.0-jar-with-dependencies.jar -a $ADRESS -id $ID
