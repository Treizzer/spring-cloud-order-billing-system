#!/bin/bash

echo "=============================="
echo "Iniciando sistema completo..."
echo "=============================="

# Configuración de memoria
export MAVEN_OPTS="-Xmx128m"

# Si algo falla el script se detiene
set -e

# ---- CONFIG SERVER ----
echo "Iniciando config-server..."
cd ..
cd config-server
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx128m" &
cd ..
sleep 10

# ---- DISCOVERY SERVER ----
echo "Iniciando discovery-server..."
cd discovery-server
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx128m" &
cd ..
sleep 15

# ---- ORDER SERVICE ----
echo "Iniciando order-service..."
cd order-service
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx128m" &
cd ..
sleep 10

# ---- BILLING SERVICE ----
echo "Iniciando billing-service..."
cd billing-service
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx128m" &
cd ..
sleep 10

# ---- API GATEWAY ----
echo "Iniciando api-gateway..."
cd api-gateway
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx128m" &
cd ..

echo "=============================="
echo "Todos los servicios iniciados"
echo "=============================="