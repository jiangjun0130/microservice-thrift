#!/usr/bin/env bash

mvn clean compile package -e -U -Dmaven.test.skip=true

docker build -t hub.jj.com:8080/micro-service/course-edge-service:latest .
docker push hub.jj.com:8080/micro-service/course-edge-service:latest

#docker rmi -f $(docker images -f "dangling=true" -q)