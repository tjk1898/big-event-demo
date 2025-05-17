package edu.czjtu.big_event_demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {
    // token过期时间 7天
    static public int expiresTime = 1000 * 60 * 60 * 24 * 7;
    // 密钥
    static public String secret = "czjtu";
    private static RedisTemplate<String, String> redisTemplate;
    private static String redisKeyPrefix = "TOKEN:";

    public static String generateToken(Map<String, Object> claims) {
        String token = JWT.create()
                .withClaim("Claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresTime))
                .sign(Algorithm.HMAC256(secret));
        redisTemplate.opsForValue().set(redisKeyPrefix + token, "true");
        return token;
    }

    public static Map<String, Object> parseToken(String token) throws Exception {
        String redisToken = redisTemplate.opsForValue().get(redisKeyPrefix + token);
        if (redisToken == null) {
            throw new Exception("用户未登录。");
        }
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getClaim("Claims")
                .asMap();
    }

    public static void deleteToken(String token) {
        redisTemplate.delete(redisKeyPrefix + token);
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        JWTUtil.redisTemplate = redisTemplate;
    }

}
