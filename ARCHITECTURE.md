# Architecture

Client -> API Service (:8082) -> Rate Limit Service (:8081) -> Redis
                                             |
                                             +-> PostgreSQL (rules)

Redis holds short-lived counters. PostgreSQL stores durable client quota policies. The API service delegates each request decision to the rate-limit service.
