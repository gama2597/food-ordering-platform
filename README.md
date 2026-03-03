# Food Ordering Platform (Microservices) — Starter Scaffold

This repository is a **senior-level** starter scaffold aligned with the course document:
- Microservices
- REST + Kafka events
- Database per service
- Docker containers
- Kubernetes + Ingress (Nginx)
- OAuth2/JWT via Keycloak
- CI/CD (GitHub Actions placeholder)
- IaC via Helm Charts (plus an umbrella chart)
- Observability (placeholders for Prometheus/Grafana/ELK)

## Repo layout
- `services/` — Spring Boot microservices (placeholders)
- `gateway/` — API Gateway (placeholder)
- `frontend/` — Angular 21 + PrimeNG (placeholder)
- `contracts/` — OpenAPI specs + event schemas (placeholders)
- `infra/docker-compose/` — local dev dependencies (Kafka, Keycloak, Postgres per service)
- `infra/helm/` — Helm charts per component + umbrella chart
- `.github/workflows/` — CI/CD pipelines (placeholder)

## Local dev (Windows + Docker Desktop)
1) Copy env:
```bash
copy infra\docker-compose\.env.example infra\docker-compose\.env
```

2) Start dependencies:
```bash
docker compose -f infra/docker-compose/docker-compose.yml --env-file infra/docker-compose/.env up -d
```

3) (Optional) Check UIs:
- Kafka UI: http://localhost:8088
- Keycloak: http://localhost:8080  (admin/admin by default in env example)

## Kubernetes (Docker Desktop Kubernetes)
> Docker Desktop's Kubernetes can use your local Docker images (same Docker engine),
> so for **local** clusters you can build images and set `imagePullPolicy: IfNotPresent`.

Create namespaces:
```bash
kubectl create namespace dev
kubectl create namespace staging
```

Install via Helm umbrella chart (dev):
```bash
helm upgrade --install food-ordering infra/helm/food-ordering-platform -n dev -f infra/helm/food-ordering-platform/values-dev.yaml
```

## Next steps
- Implement `user-service` first (OpenAPI -> backend -> generated Angular client -> UI test).
- Add outbox + idempotent consumers before scaling to the rest of services.
