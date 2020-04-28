package travel.security;

import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import travel.security.jwt.TokenConsumer;
import travel.security.jwt.model.JwtPayload;

@Component
public class TokenAuthenticator {

    @Autowired(required = true)
    @Qualifier(value = "tokenConsumer")
    private TokenConsumer tokenConsumer;

    public Authentication getAuthentication(String token) throws InvalidJwtException {
        UserAuthentication userAuthentication = null;
        if (token != null) {
            JwtPayload jwtPayload = this.tokenConsumer.consume(token);
            UserDetails userDetails = new UserDetails();
            userDetails.setUser(jwtPayload);
            userAuthentication = new UserAuthentication(userDetails);
        }

        return userAuthentication;
    }
}
