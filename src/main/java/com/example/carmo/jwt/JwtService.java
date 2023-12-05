package com.example.carmo.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    @Autowired
    private Key myKey;

    @Value("${jwt.expiration}")
    private Long tokenExpiration;

    public String generateToken(String subject){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + tokenExpiration);

        return Jwts.builder().setSubject(subject).setIssuedAt(nowDate)
        .setExpiration(expireDate).signWith(myKey, SignatureAlgorithm.HS256).compact();
    }

    public String getSubjectToken(String token){
        return Jwts.parserBuilder().setSigningKey(myKey)
        .build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token)throws Exception{
        try{
            Jwts.parserBuilder().setSigningKey(myKey)
            .build().parseClaimsJws(token);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
}
