package com.fengxiangkai.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JWTVerify {
    private static String signKey="fengxiangkai";   //不能为空
    private static Long expire = 43200000L;
    public JWTVerify(String signKey)
    {
        this.signKey = signKey;
    }

    //生成令牌，将Json格式的map信息参与到令牌制作，使令牌更有意义
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }
    //校验令牌
    public static Claims pargeJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}

