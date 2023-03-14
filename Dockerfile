FROM openjdk:8
MAINTAINER marcel.pokrandt@vgbe.energy
COPY target/latexws-1.0-SNAPSHOT.jar latexws.jar
ENTRYPOINT ["java","-jar","/latexws.jar"]