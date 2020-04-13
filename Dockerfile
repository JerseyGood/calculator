FROM openjdk:8-jre-alpine
ADD target/RpnCalculator-1.0.0-SNAPSHOT.jar /opt/rpn-calculator/RpnCalculator.jar
CMD ["java","-jar","/opt/rpn-calculator/RpnCalculator.jar"]
