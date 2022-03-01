package edu.uwo.health.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.expiration}")
    public long expiration;

    @Value("${jwt.secret}")
    public String base64Security;

    /**
     * parse token
     * @param jsonWebToken
     * @return
     */
    public Claims parseToken(String jsonWebToken) {
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security)).parseClaimsJws(jsonWebToken).getBody();
        return claims;
    }

    /**
     * create token
     * @param audience
     * @param issuer
     * @return
     */
    public String createToken(String audience, String issuer) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // generate assign secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // add parameters that make up JWT
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setIssuer(issuer).setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);

        // add token issuing time
        builder.setIssuedAt(now);
        // Add token expiration time
        if (expiration >= 0) {
            long expMillis = nowMillis + expiration;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        // generate JWT
        return builder.compact();
    }

    /**
     * refresh token
     *
     * @param claims
     * @return
     */
    public String refreshToken(Claims claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setIssuer((String) claims.get("iss")).setAudience((String) claims.get("aud"))
                .signWith(signatureAlgorithm, signingKey);

        builder.setIssuedAt(now);

        if (expiration >= 0) {
            long expMillis = nowMillis + expiration;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        return builder.compact();
    }
}
