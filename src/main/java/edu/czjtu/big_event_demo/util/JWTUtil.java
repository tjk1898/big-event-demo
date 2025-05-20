package edu.czjtu.big_event_demo.util;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.Resource;

@Component
public class JWTUtil {

    // token过期时间：7天
    public static final long EXPIRES_TIME = 1000 * 60 * 60 * 24 * 7;

    // 密钥
    public static final String SECRET = "czjtu";

    // Redis key前缀
    public static final String TOKEN_PREFIX = "TOKEN:";

    // 注入Redis
    private static StringRedisTemplate stringRedisTemplate;

    @Resource
    public void setStringRedisTemplate(StringRedisTemplate template) {
        JWTUtil.stringRedisTemplate = template;
    }

    /**
     * 生成Token并保存到Redis
     */
    public static String generateToken(Map<String, Object> claims) {
        long now = System.currentTimeMillis();
        String token = JWT.create()
                .withClaim("Claims", claims)
                .withExpiresAt(new Date(now + EXPIRES_TIME))
                .sign(Algorithm.HMAC256(SECRET));

        // 保存到Redis
        stringRedisTemplate.opsForValue().set(
                TOKEN_PREFIX + token,
                "1",
                EXPIRES_TIME,
                TimeUnit.MILLISECONDS
        );

        return token;
    }

    /**
     * 校验并解析Token
     */
    public static Map<String, Object> parseToken(String token) throws Exception {
        // 校验签名
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);

        // 校验Redis中是否存在
        Boolean hasKey = stringRedisTemplate.hasKey(TOKEN_PREFIX + token);
        if (hasKey == null || !hasKey) {
            throw new Exception("Token已失效，请重新登录");
        }

        // 解析内容
        return decodedJWT.getClaim("Claims").asMap();
    }

    /**
     * 删除Token (登出用)
     */
    public static void deleteToken(String token) {
        stringRedisTemplate.delete(TOKEN_PREFIX + token);
    }
}
