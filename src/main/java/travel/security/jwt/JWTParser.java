package travel.security.jwt;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import travel.security.jwt.util.String2KeyConverter;

import java.security.PublicKey;
import java.util.Map;

public final class JWTParser {
    private PublicKey publicKey;

    private String2KeyConverter converter;

    public JWTParser() {
        converter = new String2KeyConverter();
    }

    public JWTParser(String publicKey) {
        this();
        setPublicKey(publicKey);
    }

    public JWTParser(PublicKey publicKey) {
        this();
        this.publicKey = publicKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = converter.getPublicKey(publicKey);
    }

    public Map<String, Object> parseToken(String token, String... audience) throws InvalidJwtException {
        JwtConsumerBuilder builder = new JwtConsumerBuilder().setRequireExpirationTime().setVerificationKey(publicKey);

        if (audience.length > 0) {
            builder.setExpectedAudience(audience);
        }

        JwtConsumer consumer = builder.build();
        JwtClaims claims = consumer.processToClaims(token);

        return claims.getClaimsMap();
    }
}
