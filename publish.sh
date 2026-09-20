#!/usr/bin/zsh

# Environment variables
export $(xargs < .env)

gradleww :26.1:modrinth
gradleww :26.2:modrinth
gradleww :26.3:modrinth
