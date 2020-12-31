#!/usr/bin/env bash

cd core/product-service;                ./gradlew clean build; cd -
cd core/review-service;                 ./gradlew clean build; cd -
cd product-composite-service;           ./gradlew clean build; cd -
cd support/discovery-server;            ./gradlew clean build; cd -
cd support/edge-server;                 ./gradlew clean build; cd -

