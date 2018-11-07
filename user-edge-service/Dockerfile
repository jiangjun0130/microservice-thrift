FROM hub.jj.com:8080/micro-service/openjdk:8u181-jre
MAINTAINER Jason 1137829446@qq.com

COPY target/user-edge-service-2.0.4.RELEASE.jar /user-edge-service.jar

ENTRYPOINT ["java", "-jar", "/user-edge-service.jar"]