# Day 17 — Distributed API Rate Limiter & Quota Platform

A portfolio-grade Spring Boot project demonstrating a distributed, Redis-backed fixed-window rate limiter. Rules are persisted in PostgreSQL and requests are checked by a dedicated rate-limit service. A separate API service consumes the limiter and returns HTTP 429 when a client exceeds its quota.

## Stack
Java 17 · Spring Boot 3.3.5 · Redis 7 · PostgreSQL 16 · Docker Compose · Actuator/Prometheus · Grafana · Maven

## Run
```bash
docker compose up --build
```

## Test
Create a 3-request/minute rule:
```bash
curl -X POST http://localhost:8081/api/rate-limit/rules -H "Content-Type: application/json" -d '{"clientId":"aditya","limitPerMinute":3,"enabled":true}'
```
Call the protected API repeatedly:
```bash
curl -H "X-Client-Id: aditya" http://localhost:8082/api/hello
```
The first three requests return 200; subsequent requests return 429 until the minute window changes.

Check rules: `GET http://localhost:8081/api/rate-limit/rules`
Health: `/actuator/health` on both services.
Prometheus: http://localhost:9090 · Grafana: http://localhost:3000

## GitHub
```bash
git init
git add .
git commit -m "Day 17 distributed rate limiter"
git branch -M main
git remote add origin https://github.com/Vermaaditya3030/distributed-rate-limiter-day17.git
git push -u origin main
```

## What this demonstrates
- Redis atomic counters with TTL
- Centralized quota policy
- HTTP 429 and rate-limit headers
- PostgreSQL-backed client rules
- Service-to-service communication
- Dockerized microservices
- Observability with Actuator/Prometheus

## Production roadmap
Use Redis Lua scripts/token-bucket or sliding-window algorithms, API Gateway integration, authentication-aware keys, tenant quotas, distributed metrics, retries/circuit breakers, Kubernetes HPA, and integration/load tests. The current implementation intentionally uses a simple fixed-window algorithm for learning.
