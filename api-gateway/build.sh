#!/usr/bin/env bash

mvn clean package -e -U -Dmaven.test.skip=true

docker build -t api-gateway:latest .

docker rmi $(docker images -f "dangling=true" -q)