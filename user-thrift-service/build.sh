#!/usr/bin/env bash

mvn clean package -e -U -Dmaven.test.skip=true

docker build -t user-thrift-service:latest .

docker rmi $(docker images -f "dangling=true" -q)