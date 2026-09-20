#!/usr/bin/zsh

# Environment variables
export $(xargs < .env)

./gradlew :26.1:modrinth
./gradlew :26.2:modrinth
./gradlew :26.3:modrinth
