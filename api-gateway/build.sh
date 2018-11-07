#!/usr/bin/env bash

mvn clean package -e -U -Dmaven.test.skip=true
# build -t [name] [.]:当前目录下的Dockerfile
docker build -t hub.jj.com:8080/micro-service/api-gateway:latest .

docker push hub.jj.com:8080/micro-service/api-gateway:latest

#docker rmi $(docker images -f "dangling=true" -q)