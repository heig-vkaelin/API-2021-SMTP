FROM openjdk:11-jre
LABEL authors="Alexandre Jaquier, Valentin Kaelin"

RUN apt-get update && \
  apt-get clean 

COPY MockMock-1.4.0.one-jar.jar /opt/app/MockMock.jar

# Pour le serveur SMTP
EXPOSE 25

# Pour l'interface web
EXPOSE 8282

CMD java -jar /opt/app/MockMock.jar
