package com.demo.service;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceTest {
    private static final String SECRET_KEY = "some_secret_key";
    private static final String ID = "some_id";
    private static final String ISSUER = "some_issuer";
    private static final String SUBJECT = "some_subject";
    private static final long TOKEN_EXPIRE = 3_600;

    @InjectMocks
    private JwtService target;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createJWT_decodeJWT() {
        String jwt = target.createJWT(SECRET_KEY, ID, ISSUER, SUBJECT, TOKEN_EXPIRE);
        assertThat(jwt).isNotBlank();

        Claims claims = target.decodeJWT(SECRET_KEY, jwt);
        assertThat(claims).isNotNull();
        assertThat(claims.getId()).isEqualTo(ID);
        assertThat(claims.getSubject()).isEqualTo(SUBJECT);
        assertThat(claims.getIssuer()).isEqualTo(ISSUER);
        assertThat(claims.getIssuedAt()).isBefore(claims.getExpiration());
    }
}
