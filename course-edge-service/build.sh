#!/usr/bin/env bash

mvn clean package -e -U -Dmaven.test.skip=true

docker build -t course-edge-service:latest .

#docker rmi -f $(docker images -f "dangling=true" -q)