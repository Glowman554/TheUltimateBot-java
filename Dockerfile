FROM debian

RUN apt update
RUN apt install git openjdk-11-jdk maven gcc make automake -y

RUN git clone https://github.com/Glowman554/TheUltimateBot-java
RUN git clone https://github.com/Glowman554/cpuminer.git

WORKDIR /cpuminer

RUN bash autogen.sh
RUN bash configure
RUN make

RUN mv minerd ../TheUltimateBot-java/.

WORKDIR /TheUltimateBot-java

RUN mvn package
RUN mv target/TheUltimateBot-1.0-SNAPSHOT.jar .

COPY token.txt /TheUltimateBot-java/.
COPY users.json /TheUltimateBot-java/.
COPY miner_config.json /TheUltimateBot-java/.

ENTRYPOINT java -jar TheUltimateBot-1.0-SNAPSHOT.jar
