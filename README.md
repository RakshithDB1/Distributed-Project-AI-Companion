# 🚀 Distributed AI Project Companion

> A cloud-native AI-powered software development platform built with Spring Boot Microservices, Kubernetes, OpenAI, React, PostgreSQL, Redis, MinIO, and Google Kubernetes Engine (GKE).

---

# 📋 Table of Contents

* Overview
* System Architecture
* Microservices Architecture
* Technology Stack
* Database Design
* API Documentation
* Local Development Setup
* Docker Deployment
* Kubernetes Deployment
* CI/CD Pipeline
* Security Architecture
* Monitoring & Troubleshooting
* Future Roadmap

---

# 🎯 Overview

Distributed AI Project Companion enables developers to:

* Create and manage software projects
* Collaborate with team members
* Store and manage project files
* Generate code using AI
* Chat with project-aware AI assistants
* Deploy preview environments automatically
* Scale applications through Kubernetes

---

# 🏗️ System Architecture

```mermaid
flowchart TB

    UI[React + Vite Frontend]

    GW[API Gateway]

    ACC[Account Service]
    WS[Workspace Service]
    INT[Intelligence Service]

    PG1[(Account DB)]
    PG2[(Workspace DB)]
    PG3[(Intelligence DB)]

    REDIS[(Redis)]
    MINIO[(MinIO)]

    OPENAI[OpenAI/OpenRouter]

    K8S[Kubernetes Cluster]

    UI --> GW

    GW --> ACC
    GW --> WS
    GW --> INT

    ACC --> PG1

    WS --> PG2
    WS --> REDIS
    WS --> MINIO
    WS --> K8S

    INT --> PG3
    INT --> OPENAI

    INT --> WS
    INT --> ACC
```

---

# 🔧 Microservices Architecture

```mermaid
flowchart LR

    AccountService --> AccountDB

    WorkspaceService --> WorkspaceDB
    WorkspaceService --> Redis
    WorkspaceService --> MinIO

    IntelligenceService --> IntelligenceDB
    IntelligenceService --> OpenAI

    IntelligenceService --> WorkspaceService
    IntelligenceService --> AccountService

    Gateway --> AccountService
    Gateway --> WorkspaceService
    Gateway --> IntelligenceService

    Discovery[Eureka Server]

    AccountService -.-> Discovery
    WorkspaceService -.-> Discovery
    IntelligenceService -.-> Discovery
    Gateway -.-> Discovery
```

---

# 📦 Service Responsibilities

| Service              | Responsibility                   |
| -------------------- | -------------------------------- |
| Account Service      | Authentication, Users, Billing   |
| Workspace Service    | Projects, Files, Team Members    |
| Intelligence Service | AI Chat, Code Generation         |
| API Gateway          | Routing, Security                |
| Config Service       | Centralized Configuration        |
| Discovery Service    | Service Discovery                |
| Common Lib           | Shared DTOs, JWT, Feign Security |

---

# 🛠️ Technology Stack

## Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Cloud
* Spring AI
* OpenFeign
* JPA / Hibernate
* MapStruct

## Frontend

* React
* TypeScript
* Vite
* TailwindCSS

## Storage

* PostgreSQL
* Redis
* MinIO

## Infrastructure

* Docker
* Kubernetes
* GKE
* NGINX Ingress

## CI/CD

* GitHub Actions
* Docker Hub
* Google Cloud Workload Identity Federation

---

# 🗄️ Database Design

## Account Service ER Diagram

```mermaid
erDiagram

    USER ||--o{ SUBSCRIPTION : owns
    PLAN ||--o{ SUBSCRIPTION : assigned

    USER {
        bigint id
        string email
        string password
        string role
        timestamp created_at
    }

    PLAN {
        bigint id
        string name
        int max_projects
        int ai_tokens
    }

    SUBSCRIPTION {
        bigint id
        string stripe_customer_id
        string status
        timestamp expiry_date
    }
```

---

## Workspace Service ER Diagram

```mermaid
erDiagram

    PROJECT ||--o{ PROJECT_MEMBER : contains
    PROJECT ||--o{ PROJECT_FILE : contains
    PROJECT ||--o{ PREVIEW : deploys

    PROJECT {
        bigint id
        string name
        string description
        bigint owner_id
    }

    PROJECT_MEMBER {
        bigint id
        bigint user_id
        string role
    }

    PROJECT_FILE {
        bigint id
        string path
        string file_name
        string storage_key
    }

    PREVIEW {
        bigint id
        string deployment_name
        string url
    }
```

---

## Intelligence Service ER Diagram

```mermaid
erDiagram

    CHAT_SESSION ||--o{ CHAT_MESSAGE : contains
    CHAT_SESSION ||--o{ CHAT_EVENT : tracks

    CHAT_SESSION {
        bigint id
        bigint project_id
        bigint user_id
    }

    CHAT_MESSAGE {
        bigint id
        string role
        text content
    }

    CHAT_EVENT {
        bigint id
        string event_type
        text details
    }

    USAGE_LOG {
        bigint id
        bigint user_id
        int tokens_used
    }
```

---

# 🔐 Security Architecture

```mermaid
sequenceDiagram

    User->>Gateway: Login Request

    Gateway->>Account Service: Authenticate

    Account Service-->>Gateway: JWT Token

    Gateway-->>User: JWT Token

    User->>Gateway: API Request

    Gateway->>Workspace Service: Forward JWT

    Workspace Service->>Common Lib: JwtAuthFilter

    Common Lib-->>Workspace Service: Security Context

    Workspace Service-->>User: Response
```

---

# 🤖 AI Request Flow

```mermaid
sequenceDiagram

    User->>Frontend: Ask AI Question

    Frontend->>Gateway: Chat Request

    Gateway->>Intelligence Service: Process Request

    Intelligence Service->>Workspace Service: Fetch Project Context

    Workspace Service-->>Intelligence Service: Files & Metadata

    Intelligence Service->>OpenAI: Prompt + Context

    OpenAI-->>Intelligence Service: Response

    Intelligence Service-->>Frontend: Generated Code
```

---

# 🌐 API Documentation

## Authentication APIs

### Register

```http
POST /auth/register
```

Request

```json
{
  "email": "user@example.com",
  "password": "password"
}
```

Response

```json
{
  "id": 1,
  "email": "user@example.com"
}
```

---

### Login

```http
POST /auth/login
```

Response

```json
{
  "token": "jwt-token"
}
```

---

## Project APIs

### Create Project

```http
POST /api/projects
```

### Get Project

```http
GET /api/projects/{projectId}
```

### Delete Project

```http
DELETE /api/projects/{projectId}
```

---

## File APIs

### Upload File

```http
POST /api/projects/{projectId}/files
```

### Get File Tree

```http
GET /api/projects/{projectId}/tree
```

### Download File

```http
GET /api/projects/{projectId}/files/{fileId}
```

---

## AI APIs

### Create Chat Session

```http
POST /api/chat/sessions
```

### Send Message

```http
POST /api/chat/{sessionId}/message
```

### Generate Code

```http
POST /api/ai/generate
```

---

# 🚀 Local Development

## Build Common Library

```bash
cd common-lib
./mvnw clean install -DskipTests
```

## Start Backend Services

```bash
./mvnw spring-boot:run
```

## Start Frontend

```bash
cd project-companion-ui

npm install

npm run dev
```

---

# 🐳 Docker Deployment

## Build Image

```bash
./mvnw compile jib:dockerBuild
```

## Push Image

```bash
./mvnw compile jib:build
```

---

# ☸️ Kubernetes Deployment

## Create Namespaces

```bash
kubectl create namespace lovable-core

kubectl create namespace lovable-previews
```

## Deploy Infrastructure

```bash
kubectl apply -f k8s/databases/ -n lovable-core
```

## Deploy Services

```bash
kubectl apply -f k8s/services/ -n lovable-core
```

## Deploy Ingress

```bash
kubectl apply -f k8s/ingress.yaml -n lovable-core
```

---

# 🔄 Deployment Flow Diagram

```mermaid
flowchart LR

    DEV[Developer Push]

    GH[GitHub Actions]

    BUILD[Maven Build]

    JIB[Jib Docker Build]

    DH[Docker Hub]

    GCP[GKE Authentication]

    K8S[Kubernetes Cluster]

    DEPLOY[Rolling Deployment]

    DEV --> GH
    GH --> BUILD
    BUILD --> JIB
    JIB --> DH
    DH --> GCP
    GCP --> K8S
    K8S --> DEPLOY
```

---

# 🔄 CI/CD Pipeline

```mermaid
flowchart TB

    Commit[Git Push]

    Workflow[GitHub Workflow]

    CommonLib[Build Common Lib]

    ServiceBuild[Build Service]

    DockerPush[Push Docker Image]

    GKEAuth[Authenticate GCP]

    UpdateDeploy[Update Deployment]

    Rollout[Rolling Update]

    Commit --> Workflow
    Workflow --> CommonLib
    CommonLib --> ServiceBuild
    ServiceBuild --> DockerPush
    DockerPush --> GKEAuth
    GKEAuth --> UpdateDeploy
    UpdateDeploy --> Rollout
```

---

# 📊 Monitoring Commands

```bash
kubectl get pods -A
```

```bash
kubectl get svc -A
```

```bash
kubectl logs deployment/account-service -n lovable-core
```

```bash
kubectl rollout restart deployment/account-service -n lovable-core
```

---

# 🛣️ Future Roadmap

* Vector Database Integration
* RAG-based Code Generation
* Multi-Agent AI Workflows
* Real-time Collaboration
* Prometheus Monitoring
* Grafana Dashboards
* OpenTelemetry Tracing
* Multi-Region Kubernetes Deployment
