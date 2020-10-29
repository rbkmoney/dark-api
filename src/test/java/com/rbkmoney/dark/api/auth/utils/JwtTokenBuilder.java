package com.rbkmoney.dark.api.auth.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.time.Instant;
import java.util.UUID;

/**
 * @since 04.07.17
 **/
public class JwtTokenBuilder {

    public final static String DEFAULT_USERNAME = "Darth Vader";

    public final static String DEFAULT_EMAIL = "darkside-the-best@mail.com";

    private final String userId;

    private final String username;

    private final String email;

    private final PrivateKey privateKey;

    public JwtTokenBuilder(PrivateKey privateKey) {
        this(UUID.randomUUID().toString(), DEFAULT_USERNAME, DEFAULT_EMAIL, privateKey);
    }

    public JwtTokenBuilder(String userId, String username, String email, PrivateKey privateKey) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.privateKey = privateKey;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String generateJwtWithRoles(String issuer, String... roles) {
        long iat = Instant.now().getEpochSecond();
        long exp = iat + 60 * 10;
        return generateJwtWithRoles(iat, exp, issuer, roles);
    }

    public String generateJwtWithRoles(long iat, long exp, String issuer, String... roles) {
        String payload;
        try {
            payload = new JSONObject()
                    .put("jti", UUID.randomUUID().toString())
                    .put("exp", exp)
                    .put("nbf", "0")
                    .put("iat", iat)
                    .put("iss", issuer)
                    .put("aud", "private-api")
                    .put("sub", userId)
                    .put("typ", "Bearer")
                    .put("azp", "private-api")
                    .put("resource_access", new JSONObject()
                            .put("common-api", new JSONObject()
                                    .put("roles", new JSONArray(roles))))
                    .put("preferred_username", username)
                    .put("email", email).toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String jwt = Jwts.builder()
                .setPayload(payload)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
        return jwt;
    }

}
