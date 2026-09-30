package com.aditya.ratelimit.service;
import com.aditya.ratelimit.model.RateLimitRule; import com.aditya.ratelimit.repo.RuleRepository; import org.springframework.data.redis.core.StringRedisTemplate; import org.springframework.stereotype.Service; import java.time.Duration;
@Service public class RateLimitService { private final StringRedisTemplate redis; private final RuleRepository repo; public RateLimitService(StringRedisTemplate r,RuleRepository p){redis=r;repo=p;}
 public Decision check(String client){RateLimitRule rule=repo.findByClientId(client).orElseGet(()->new RateLimitRule(client,60)); if(!rule.enabled)return new Decision(true,0,rule.limitPerMinute); String key="rl:"+client+":"+(System.currentTimeMillis()/60000); Long n=redis.opsForValue().increment(key); if(n!=null&&n==1)redis.expire(key,Duration.ofSeconds(61)); long used=n==null?0:n; return new Decision(used<=rule.limitPerMinute,Math.max(0,rule.limitPerMinute-used),rule.limitPerMinute);}
 public record Decision(boolean allowed,long remaining,long limit){}
}
