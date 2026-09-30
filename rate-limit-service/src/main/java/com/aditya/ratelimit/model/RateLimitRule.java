package com.aditya.ratelimit.model;
import jakarta.persistence.*;
@Entity @Table(name="rate_limit_rules", uniqueConstraints=@UniqueConstraint(columnNames="clientId"))
public class RateLimitRule { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @Column(nullable=false) public String clientId; public long limitPerMinute=60; public boolean enabled=true; public RateLimitRule(){} public RateLimitRule(String c,long l){clientId=c;limitPerMinute=l;} }
