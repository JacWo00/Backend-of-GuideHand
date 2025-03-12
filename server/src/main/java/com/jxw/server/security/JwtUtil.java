package com.jxw.server.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    /**
     * 过期时间(单位:秒)
     */
    private static final int ACCESS_EXPIRE = 3600000;
    /**
     * 加密算法
     */
    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    /**
     * 私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
     * 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     * 应该大于等于 256位(长度32及以上的字符串)，并且是随机的字符串
     */

    private final static String SECRET="eW91ci1yYW5kb20tc2VjcmV0LWtleS0zMi1jaGFyYWN0ZXJzLWxvbmc=";
    /**
     * 秘钥实例
     */
    public static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    /**
     * jwt签发者
     */
    private final static String JWT_ISS = "Tiam";
    /**
     * jwt主题
     */
    private final static String SUBJECT = "Peripherals";

    public static String genAccessToken(String userId) {
        // 令牌id
        String uuid = UUID.randomUUID().toString();
        Date exprireDate = Date.from(Instant.now().plusSeconds(ACCESS_EXPIRE));

        return Jwts.builder()
                // 设置头部信息header
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                // 设置自定义负载信息payload
                .claim("username", userId)
                // 令牌ID
                .id(uuid)
                // 过期日期
                .expiration(exprireDate)
                // 签发时间
                .issuedAt(new Date())
                // 主题
                .subject(SUBJECT)
                // 签发者
                .issuer(JWT_ISS)
                // 签名
                .signWith(KEY, ALGORITHM)
                .compact();
    }
    /**
     * 解析token
     * @param token token
     * @return Jws<Claims>
     */
    public static Jws<Claims> parseClaim(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
    }

    public static JwsHeader parseHeader(String token) {
        return parseClaim(token).getHeader();
    }

    public static Claims parsePayload(String token) {
        return parseClaim(token).getPayload();
    }

}

