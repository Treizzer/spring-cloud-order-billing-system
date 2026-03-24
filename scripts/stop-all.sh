#!/bin/bash

echo "=============================="
echo "Deteniendo sistema completo..."
echo "=============================="

services=(
  "config-server"
  "discovery-server"
  "order-service"
  "billing-service"
  "api-gateway"
)

for service in "${services[@]}"
do
  echo "Deteniendo $service..."
  pkill -f "$service"
done

echo "Servicios detenidos."