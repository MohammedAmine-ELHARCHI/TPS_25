#!/bin/bash

echo "========================================"
echo "Démarrage du Service SOAP"
echo "========================================"
echo ""

cd "$(dirname "$0")"

if [ -f mvnw ]; then
    echo "Utilisation du Maven Wrapper..."
    ./mvnw clean spring-boot:run
else
    echo "Utilisation de Maven installé..."
    mvn clean spring-boot:run
fi
