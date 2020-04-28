package travel.security.jwt;

import org.jose4j.jwt.consumer.InvalidJwtException;
import travel.security.jwt.model.JwtPayload;
import travel.security.jwt.util.JwtClaimKey;

import java.security.PublicKey;
import java.util.Map;

public class TokenConsumer {
    private String audience;
    private JWTParser parser;


    public TokenConsumer(String audience, PublicKey publicKey) {
        this.audience = audience;
        parser = new JWTParser(publicKey);
    }

    public JwtPayload consume(String token) throws InvalidJwtException {
        Map<String, Object> claims = parser.parseToken(token, audience);

        JwtPayload jwtPayload = new JwtPayload();

        jwtPayload.setId((Long) claims.get(JwtClaimKey.ID.getValue()));
        jwtPayload.setEmail((String) claims.get(JwtClaimKey.EMAIL.getValue()));
        jwtPayload.setUserName((String) claims.get(JwtClaimKey.USERNAME.getValue()));
        jwtPayload.setRole((String) claims.get(JwtClaimKey.ROLE.getValue()));

        return jwtPayload;
    }
}
