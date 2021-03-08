FROM debian

RUN apt update
RUN apt install git openjdk-11-jdk maven -y

RUN git clone https://github.com/Glowman554/TheUltimateBot-java

WORKDIR /TheUltimateBot-java

RUN mvn package
RUN mv target/TheUltimateBot-1.0-SNAPSHOT.jar .

COPY config.json /TheUltimateBot-java/.

ENTRYPOINT java -jar TheUltimateBot-1.0-SNAPSHOT.jar
