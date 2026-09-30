package com.aditya.ratelimit.controller;
import com.aditya.ratelimit.model.RateLimitRule; import com.aditya.ratelimit.repo.RuleRepository; import com.aditya.ratelimit.service.RateLimitService; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/rate-limit") public class RateLimitController { private final RateLimitService svc; private final RuleRepository repo; public RateLimitController(RateLimitService s,RuleRepository r){svc=s;repo=r;}
 @PostMapping("/check") public ResponseEntity<RateLimitService.Decision> check(@RequestParam String clientId){var d=svc.check(clientId); return ResponseEntity.status(d.allowed()?200:429).header("X-RateLimit-Limit",String.valueOf(d.limit())).header("X-RateLimit-Remaining",String.valueOf(d.remaining())).body(d);}
 @PostMapping("/rules") public RateLimitRule rule(@RequestBody RateLimitRule r){return repo.findByClientId(r.clientId).map(x->{x.limitPerMinute=r.limitPerMinute;x.enabled=r.enabled;return repo.save(x);}).orElseGet(()->repo.save(r));}
 @GetMapping("/rules") public List<RateLimitRule> rules(){return repo.findAll();}
}
