#!/bin/bash

PORT=8080

echo "🔄 Suche nach Prozessen auf Port $PORT..."
PID=$(lsof -ti tcp:$PORT)

if [ -n "$PID" ]; then
  echo "⚠️  Port $PORT ist belegt durch PID $PID – wird beendet..."
  sudo kill -9 $PID
  sleep 1
  echo "✅ Prozess $PID beendet."
else
  echo "✅ Port $PORT ist frei."
fi

echo "🧹 Baue Projekt neu mit Maven (clean install)..."
./mvnw clean install

if [ $? -ne 0 ]; then
  echo "❌ Build fehlgeschlagen. Skript wird beendet."
  exit 1
fi

echo "🚀 Starte Spring Boot Anwendung auf Port $PORT..."
./mvnw spring-boot:run
