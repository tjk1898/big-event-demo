package edu.czjtu.big_event_demo.util;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.Value;

public class JWTUtil {
    static public int expiresTime = 1000 * 60 * 60 * 24 * 7;

    static public String secret = "czjtu";

    public static String generateToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("Claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresTime))
                .sign(Algorithm.HMAC256(secret));
    }

    public static Map<String, Object> parseToken(String token) throws Exception {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getClaim("Claims")
                .asMap();
    }

}
