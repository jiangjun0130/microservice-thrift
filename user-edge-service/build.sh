#!/usr/bin/env bash

mvn clean package -e -U -Dmaven.test.skip=true

docker build -t hub.jj.com:8080/micro-service/user-edge-service:latest .
docker push hub.jj.com:8080/micro-service/user-edge-service:latest

#docker rmi $(docker images -f "dangling=true" -q)