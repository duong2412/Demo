package travel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import travel.security.jwt.TokenConsumer;
import travel.security.jwt.TokenProducer;
import travel.security.jwt.util.KeyReader;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Configuration
public class TokenManagerConfig {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.subject}")
    private String subject;

    @Value("${jwt.audience}")
    private String audience;

    @Value("#{'${jwt.audiences}'.split(',')}")
    private List<String> audiences;

    @Value("${jwt.expiration}")
    private float expirationInMin;

    @Value("${jwt.notBefore}")
    private float notBeforeInMin;

    @Value("${jwt.privateKey}")
    private String privateKeyPath;

    @Value("${jwt.publicKey}")
    private String publicKeyPath;


    @Bean(name = "tokenProducer")
    public TokenProducer tokenProducer() {
        return createTokenProducer(expirationInMin);

    }

    public TokenProducer createTokenProducer(float time) {
        try {
            PrivateKey privateKey = KeyReader.getPrivateKey(this.privateKeyPath);
            return new TokenProducer(this.issuer, this.subject, this.audiences.stream().toArray(size -> new String[size]),
                    time, this.notBeforeInMin, privateKey);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            return null;
        }
    }

    @Bean(name = "tokenConsumer")
    public TokenConsumer tokenConsumer() {
        try {
            PublicKey publicKey = KeyReader.getPublicKey(this.publicKeyPath);
            return new TokenConsumer(this.audience, publicKey);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            return null;
        }
    }
}
