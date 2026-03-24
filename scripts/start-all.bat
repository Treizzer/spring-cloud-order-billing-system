@echo off
echo ==============================
echo Iniciando sistema completo...
echo ==============================

@REM Configuración de memoria para Maven
set MAVEN_OPTS=-Xmx128m
@REM set MAVEN_OPTS=-Xmx128m -XX:+PrintFlagsFinal

REM ---- CONFIG SERVER ----
echo Iniciando config-server...
@REM start "config-server" cmd /k "cd ../config-server && set MAVEN_OPTS=-Xmx128m && mvn spring-boot:run"
start "config-server" cmd /k "cd ../config-server && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Xmx128m"
timeout /t 10

REM ---- DISCOVERY SERVER ----
echo Iniciando discovery-server...
start "discovery-server" cmd /k "cd ../discovery-server && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Xmx128m"
timeout /t 15

REM ---- ORDER SERVICE ----
echo Iniciando order-service...
start "order-service" cmd /k "cd ../order-service && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Xmx128m"
timeout /t 10

REM ---- BILLING SERVICE ----
echo Iniciando billing-service...
start "billing-service" cmd /k "cd ../billing-service && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Xmx128m"
timeout /t 10

REM ---- API GATEWAY ----
echo Iniciando api-gateway...
start "api-gateway" cmd /k "cd ../api-gateway && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Xmx128m"

echo ==============================
echo Todos los servicios iniciados
echo ==============================