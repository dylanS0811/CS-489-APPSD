# Lab10 - Docker Containers, Kubernetes, and Cloud Deployment

Course project: Advantis Dental Surgeries secure Web API.

GitHub repository URL:

```text
https://github.com/dylanS0811/CS-489-APPSD
```

Local deployed software URL after Docker Compose or Kubernetes port-forward:

```text
http://localhost:8080/adsweb/api/v1
```

## 1. Docker Desktop

Docker Desktop is required. Start Docker Desktop before running Docker commands.

Verify Docker:

```bash
docker version
```

## 2. Build the Container Image

From the repository root:

```bash
docker build -t cs489-appsd:lab10 .
```

## 3. Run with Docker Compose

The Compose setup runs the secure Spring Boot API and a MySQL 8.4 database.

```bash
docker compose up --build
```

The API is available at:

```text
http://localhost:8080/adsweb/api/v1
```

Verify the running API by logging in with the seeded administrator account:

```bash
curl -X POST http://localhost:8080/adsweb/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"ethan.reed","password":"welcome1"}'
```

Use the returned `accessToken` as a Bearer token:

```bash
curl http://localhost:8080/adsweb/api/v1/patients \
  -H "Authorization: Bearer <accessToken>"
```

Stop the containers:

```bash
docker compose down
```

## 4. Kubernetes Deployment

This repository includes Kubernetes manifests under `k8s/`:

```text
k8s/namespace.yaml
k8s/mysql-secret.yaml
k8s/mysql.yaml
k8s/app.yaml
k8s/kustomization.yaml
```

Option A: use Docker Desktop Kubernetes. Enable Kubernetes in Docker Desktop settings first, then apply the manifests:

```bash
kubectl apply -k k8s
```

Option B: use kind, which runs Kubernetes locally on Docker:

```bash
brew install kind
kind create cluster --name cs489-lab10
kind load docker-image cs489-appsd:lab10 --name cs489-lab10
kubectl apply -k k8s
```

Wait for the app and database pods:

```bash
kubectl -n cs489-lab10 get pods
```

For local access, port-forward the service. Use `8081` if Docker Compose is already using local port `8080`.

```bash
kubectl -n cs489-lab10 port-forward service/cs489-appsd 8080:8080
kubectl -n cs489-lab10 port-forward service/cs489-appsd 8081:8080
```

Then open:

```text
http://localhost:8080/adsweb/api/v1
http://localhost:8081/adsweb/api/v1
```

For cloud Kubernetes, push the image to Docker Hub, GitHub Container Registry, AWS ECR, Azure ACR, or Google Artifact Registry, then update `k8s/app.yaml`:

```yaml
image: your-registry/cs489-appsd:lab10
```

After applying the manifests in a cloud cluster, get the public URL:

```bash
kubectl -n cs489-lab10 get service cs489-appsd
```

The submission deployed software URL should be the service external IP or DNS name on port `8080`.

## 5. Local Verification Completed

The following checks were completed locally:

```text
mvn -q test -> passed
mvn -q -DskipTests package -> passed
docker build -t cs489-appsd:lab10 . -> passed
docker compose config -> passed
docker compose up -d -> MySQL healthy and API running
POST /adsweb/api/v1/auth/login through Docker Compose -> HTTP 200
GET /adsweb/api/v1/patients with Bearer token through Docker Compose -> HTTP 200
kind create cluster --name cs489-lab10 -> passed
kind load docker-image cs489-appsd:lab10 --name cs489-lab10 -> passed
kubectl apply -k k8s -> passed
kubectl -n cs489-lab10 rollout status deployment/cs489-appsd -> passed
POST /adsweb/api/v1/auth/login through Kubernetes port-forward -> HTTP 200
GET /adsweb/api/v1/patients with Bearer token through Kubernetes port-forward -> HTTP 200
```

## 6. Lab10 Deliverables Added

- `Dockerfile` for the Spring Boot course project image.
- `docker-compose.yml` for local Docker deployment with MySQL.
- `k8s/` Kubernetes manifests for app, MySQL, service, secret, namespace, and kustomization.
- This Lab10 runbook for build, run, Kubernetes deployment, and submission URLs.
