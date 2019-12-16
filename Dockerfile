FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/currencyConventer-0.0.1-SNAPSHOT.jar currencyConventer-0.0.1-SNAPSHOT.jar
EXPOSE 80:80
ENTRYPOINT ["java","-jar","/currencyConventer-0.0.1-SNAPSHOT.jar"]