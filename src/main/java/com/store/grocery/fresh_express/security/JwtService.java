package com.store.grocery.fresh_express.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private final String privateKeyPath;
    private final String publicKeyPath;
    private final String passPhrase;
    private final long accessTokenExpiry;
    private final long refreshTokenExpiry;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    private final Logger logger = LoggerFactory.getLogger(JwtService.class);

    public JwtService(@Value("${application.security.jwt.private_key}") String privateKeyPath,
                      @Value("${application.security.jwt.public_key}") String publicKeyPath,
                      @Value("${application.security.jwt.passphrase}") String passPhrase,
                      @Value("${application.security.jwt.access_token_exp}") long accessTokenExpiry,
                      @Value("${application.security.jwt.refresh_token_exp}") long refreshTokenExpiry) {
        this.privateKeyPath = privateKeyPath;
        this.publicKeyPath = publicKeyPath;
        this.passPhrase = passPhrase;
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    @PostConstruct
    private void init() {
        try {
            logger.info("Initializing JWT Service");
            this.privateKey = RSAKeyReader.readPrivateKey(privateKeyPath, passPhrase);
            this.publicKey = RSAKeyReader.readPublicKey(publicKeyPath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA keys", e);
        }
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, accessTokenExpiry);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, refreshTokenExpiry);
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(privateKey)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.
                parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
