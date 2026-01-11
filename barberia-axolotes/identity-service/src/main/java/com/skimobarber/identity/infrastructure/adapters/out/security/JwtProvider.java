package com.skimobarber.identity.infrastructure.adapters.out.security;

import com.skimobarber.identity.domain.model.Usuario;
import com.skimobarber.identity.domain.ports.out.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * Proveedor de JWT que genera tokens de acceso.
 * Este es el único lugar donde se crean tokens en todo el sistema.
 */
@Component
public class JwtProvider implements TokenProvider {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms:3600000}") long expirationMs) {
        // Usar la clave secreta para firmar tokens con HMAC-SHA256
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    @Override
    public String generateAccessToken(Usuario usuario) {
        return generateToken(usuario);
    }

    @Override
    public String generateRefreshToken(Usuario usuario) {
        // No se usa refresh token - solo access token
        return null;
    }

    @Override
    public long getExpirationSeconds() {
        return expirationMs / 1000;
    }

    /**
     * Genera un token JWT para el usuario dado.
     * 
     * @param usuario El usuario autenticado
     * @return Token JWT firmado
     */
    public String generateToken(Usuario usuario) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(usuario.getLogin())
                .claim("userId", usuario.getPersonaId())
                .claim("roles", List.of(usuario.getRol().name()))
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS384) // Explicit HS384 to match decoders
                .compact();
    }

    /**
     * Obtiene la clave secreta para validación.
     * Usado por la configuración del Resource Server.
     */
    public SecretKey getSecretKey() {
        return secretKey;
    }
}
