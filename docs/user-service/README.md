# User Service (user-service)

Microservicio responsable de **perfil del usuario autenticado** y **gestión de direcciones**.

- Stack: **Spring Boot 4 + Java 21**
- Persistencia: **PostgreSQL** (DB por servicio) + **Flyway**
- Seguridad: **Keycloak** (OAuth2/OIDC) + **JWT Resource Server**
- Arquitectura: **DDD + Hexagonal + Clean Architecture**
- Documentación API: **Swagger UI (protegido con login Keycloak)**

---

## 1) Requisitos

- Java 21
- Maven
- Docker Desktop (con Docker Compose)
- Keycloak + Postgres + Kafka levantados con `docker-compose` del repo

---

## 2) Levantar dependencias (infra)

Desde la raíz del repo:

```bash
docker compose -f infra/docker-compose/docker-compose.yml --env-file infra/docker-compose/.env up -d