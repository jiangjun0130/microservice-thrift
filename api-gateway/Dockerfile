FROM hub.jj.com:8080/micro-service/openjdk:8u181-jre
MAINTAINER Jason 1137829446@qq.com

COPY target/api-gateway-2.0.4.RELEASE.jar /api-gateway.jar

ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]