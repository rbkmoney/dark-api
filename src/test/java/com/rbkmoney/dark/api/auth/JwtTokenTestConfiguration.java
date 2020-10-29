package com.rbkmoney.dark.api.auth;

import com.rbkmoney.dark.api.auth.utils.JwtTokenBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

@Configuration
public class JwtTokenTestConfiguration {

    @Bean
    public JwtTokenBuilder JwtTokenBuilder(KeyPair keyPair) {
        return new JwtTokenBuilder(keyPair.getPrivate());
    }

    @Bean
    public KeyPair keyPair() throws GeneralSecurityException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties(KeyPair keyPair) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = fact.getKeySpec(keyPair.getPublic(), X509EncodedKeySpec.class);
        String publicKey = Base64.getEncoder().encodeToString(spec.getEncoded());
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Properties properties = new Properties();
        properties.load(new ClassPathResource("application.yml").getInputStream());
        properties.setProperty("keycloak.realm-public-key", publicKey);
        pspc.setProperties(properties);
        pspc.setLocalOverride(true);
        return pspc;
    }
}
