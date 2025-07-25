#!/bin/bash

echo "🚀 Starting DevToolkit Backend deployment..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker and try again."
    exit 1
fi

# Build and start backend service
echo "📦 Building and starting backend service..."
docker-compose up --build -d

# Wait for service to be ready
echo "⏳ Waiting for backend service to be ready..."
sleep 15

# Check if service is running
if docker-compose ps | grep -q "Up"; then
    echo "✅ Backend service is running successfully!"
    echo ""
    echo "🔧 Backend API: http://localhost:8080/api"
    echo "🏥 Health Check: http://localhost:8080/actuator/health"
    echo ""
    echo "📋 To view logs: docker-compose logs -f"
    echo "🛑 To stop service: docker-compose down"
else
    echo "❌ Backend service failed to start. Check logs with: docker-compose logs"
    exit 1
fi 