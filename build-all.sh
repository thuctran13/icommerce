#!/usr/bin/env bash

cd core/product-service;                ./gradlew build; cd -
cd core/review-service;                 ./gradlew build; cd -
cd product-composite-service;           ./gradlew build; cd -
cd support/discovery-server;            ./gradlew build; cd -
cd support/edge-server;                 ./gradlew build; cd -

