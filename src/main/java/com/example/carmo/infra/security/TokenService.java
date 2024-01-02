package com.example.carmo.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.carmo.clients.ClientModel;
import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    // método para gerar tokens de usuário
    public String generateToken(ClientModel client) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            Claims claims = Jwts.claims().setSubject(client.getUsername());
            claims.put("roles", client.getRole());

            Gson gson = new Gson();
            String rolesJson = gson.toJson(claims.get("roles"));
            String token = JWT.create()
                    .withIssuer("carmo")
                    .withSubject(client.getUsername())
                    .withExpiresAt(generateExpironDate())
                    .withClaim("roles", rolesJson)
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("carmo")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }

    }

    public Instant generateExpironDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
