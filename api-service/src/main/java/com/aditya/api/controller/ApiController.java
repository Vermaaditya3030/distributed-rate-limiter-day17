package com.aditya.api.controller;
import com.aditya.api.service.ApiService; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api") public class ApiController {private final ApiService svc; public ApiController(ApiService s){svc=s;} @GetMapping("/hello") public ResponseEntity<?> hello(@RequestHeader(value="X-Client-Id",defaultValue="anonymous") String id){if(!svc.allowed(id))return ResponseEntity.status(429).body("Rate limit exceeded"); return ResponseEntity.ok("Hello "+id+" — request accepted");}}
