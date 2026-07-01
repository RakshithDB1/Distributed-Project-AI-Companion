# Distributed AI Project Companion

## Overview

Distributed AI Project Companion is a cloud-native microservices platform that enables users to create software projects, manage files, collaborate with team members, generate code using AI, and deploy preview environments on Kubernetes.

The platform follows a distributed microservices architecture built with Spring Boot, Spring Cloud, PostgreSQL, Redis, MinIO, Kubernetes, and OpenAI-compatible LLMs.

---

# Architecture

## High-Level Architecture

```text
┌─────────────────────┐
│      React UI       │
│    (Vite + TS)      │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│     API Gateway     │
└──────────┬──────────┘
           │
 ┌─────────┼─────────┐
 │         │         │
 ▼         ▼         ▼

Account   Workspace  Intelligence
Service   Service    Service

 │            │          │
 ▼            ▼          ▼

Postgres   Postgres   Postgres
            MinIO
            Redis

                 │
                 ▼

           Kubernetes
        Preview Deployments
```

---

# Microservices

## 1. Account Service

Responsibilities:

* User registration and login
* JWT authentication
* Subscription management
* Stripe billing integration
* Usage limits management

Database:

* PostgreSQL

Key Entities:

* User
* Plan
* Subscription

---

## 2. Workspace Service

Responsibilities:

* Project management
* Team collaboration
* File tree management
* File storage
* Kubernetes preview deployments

Storage:

* PostgreSQL
* MinIO
* Redis

Key Entities:

* Project
* ProjectMember
* ProjectFile
* Preview

---

## 3. Intelligence Service

Responsibilities:

* AI chat
* Context gathering
* Code generation
* File-aware AI assistance

Database:

* PostgreSQL

Key Entities:

* ChatSession
* ChatMessage
* ChatEvent
* UsageLog

Integrations:

* OpenAI/OpenRouter
* Workspace Service
* Account Service

---

## 4. API Gateway

Responsibilities:

* Single entry point
* Routing
* CORS management
* Request forwarding

---

## 5. Discovery Service

Responsibilities:

* Service registration
* Service discovery

Technology:

* Netflix Eureka

---

## 6. Config Service

Responsibilities:

* Centralized configuration
* Environment management

---

## 7. Common Library

Shared functionality:

* JWT authentication filter
* Feign interceptors
* DTOs
* Security utilities
* Shared configurations

---

# Technology Stack

## Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Cloud
* Spring Data JPA
* OpenFeign
* Spring AI
* MapStruct

## Databases

* PostgreSQL
* Redis

## Object Storage

* MinIO

## Infrastructure

* Docker
* Kubernetes (GKE)
* NGINX Ingress

## Frontend

* React
* TypeScript
* Vite
* Tailwind CSS

## AI

* OpenAI
* OpenRouter (optional)
* Spring AI

---

# Repository Structure

```text
.
├── account-service
├── workspace-service
├── intelligence-service
├── api-gateway
├── config-service
├── discovery-service
├── common-lib
├── project-companion-ui
├── k8s
│   ├── infra
│   ├── stateful
│   ├── services
│   └── proxy
└── .github/workflows
```

---

# Prerequisites

Install the following:

* Java 21
* Maven 3.9+
* Node.js 20+
* Docker Desktop
* Kubernetes CLI (kubectl)
* MinIO
* PostgreSQL
* Redis
* Git

Optional:

* Google Cloud SDK
* GKE Cluster

---

# Environment Variables

Create a `.env` file.

```env
JWT_SECRET=your-secret

OPENAI_API_KEY=your-openai-key

POSTGRES_USERNAME=postgres
POSTGRES_PASSWORD=password

REDIS_HOST=localhost
REDIS_PORT=6379

MINIO_ACCESS_KEY=minioadmin
MINIO_SECRET_KEY=minioadmin

STRIPE_SECRET_KEY=sk_xxx
STRIPE_WEBHOOK_SECRET=whsec_xxx
```

---

# Local Development Setup

## 1. Build Shared Library

```bash
cd common-lib

chmod +x mvnw

./mvnw clean install -DskipTests
```

Windows:

```powershell
mvnw.cmd clean install -DskipTests
```

---

## 2. Start Infrastructure

### PostgreSQL

```bash
docker run -d \
  --name postgres \
  -e POSTGRES_PASSWORD=password \
  -p 5432:5432 \
  postgres:16
```

### Redis

```bash
docker run -d \
  --name redis \
  -p 6379:6379 \
  redis:7
```

### MinIO

```bash
docker run -d \
  --name minio \
  -p 9000:9000 \
  -p 9001:9001 \
  -e MINIO_ROOT_USER=minioadmin \
  -e MINIO_ROOT_PASSWORD=minioadmin \
  quay.io/minio/minio server /data --console-address ":9001"
```

---

## 3. Start Services

### Config Service

```bash
cd config-service
./mvnw spring-boot:run
```

### Discovery Service

```bash
cd discovery-service
./mvnw spring-boot:run
```

### Account Service

```bash
cd account-service
./mvnw spring-boot:run
```

### Workspace Service

```bash
cd workspace-service
./mvnw spring-boot:run
```

### Intelligence Service

```bash
cd intelligence-service
./mvnw spring-boot:run
```

### API Gateway

```bash
cd api-gateway
./mvnw spring-boot:run
```

---

## 4. Start Frontend

```bash
cd project-companion-ui

npm install

npm run dev
```

Frontend:

```text
http://localhost:5173
```

---

# Building JARs

```bash
./mvnw clean package -DskipTests
```

or

```bash
./mvnw clean install -DskipTests
```

---

# Docker Image Build

Example:

```bash
./mvnw compile jib:dockerBuild
```

Push to Docker Hub:

```bash
./mvnw compile jib:build
```

---

# Kubernetes Deployment

## Create Namespaces

```bash
kubectl create namespace lovable-core

kubectl create namespace lovable-previews
```

## Apply Secrets

```bash
kubectl create secret generic app-secrets \
  --from-env-file=.env \
  -n lovable-core
```

## Deploy Infrastructure

```bash
kubectl apply -f k8s/stateful/ -n lovable-core
```

## Deploy Services

```bash
kubectl apply -f k8s/services/ -n lovable-core
```

## Verify

```bash
kubectl get pods -n lovable-core

kubectl get svc -n lovable-core
```

---

# NGINX Ingress

Install:

```bash
kubectl apply -f \
https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.8.2/deploy/static/provider/cloud/deploy.yaml
```

Watch LoadBalancer:

```bash
kubectl get svc ingress-nginx-controller \
-n ingress-nginx -w
```

Apply ingress:

```bash
kubectl apply -f k8s/ingress.yaml \
-n lovable-core
```

---

# CI/CD Pipeline

GitHub Actions automatically:

1. Builds common-lib
2. Builds changed microservice
3. Creates Docker image via Jib
4. Pushes image to Docker Hub
5. Authenticates with GCP
6. Updates Kubernetes deployment
7. Performs rolling deployment

Required GitHub Secrets:

```text
DOCKERHUB_USERNAME
DOCKERHUB_TOKEN

GCP_PROJECT
GCP_CLUSTER
GCP_ZONE

GCP_SERVICE_ACCOUNT
GCP_WORKLOAD_IDENTITY_PROVIDER
```

---

# AI Integration

## OpenAI

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
```

## OpenRouter

```yaml
spring:
  ai:
    openai:
      api-key: ${AI_API_KEY}
      base-url: https://openrouter.ai/api
```

---

# Monitoring Commands

View Pods

```bash
kubectl get pods -A
```

View Logs

```bash
kubectl logs deployment/account-service \
-n lovable-core --tail=100
```

Port Forward Database

```bash
kubectl port-forward pod/postgres-0 \
5432:5432
```

Restart Deployment

```bash
kubectl rollout restart deployment/account-service \
-n lovable-core
```

---

# Security

* JWT Authentication
* Service-to-Service Authentication
* Feign JWT Propagation
* Role-based Authorization
* Stripe Webhook Validation
* Kubernetes Secrets
* Workload Identity Federation

---

# Future Enhancements

* Multi-model AI support
* Vector database integration
* RAG workflows
* Collaborative editing
* Multi-region deployments
* Observability with Prometheus and Grafana
* Distributed tracing with OpenTelemetry
