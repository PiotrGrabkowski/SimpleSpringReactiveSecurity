package pl.grabkowski.CakeOrderPlatformReactiveMongo.ConfigurationProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties (prefix = "jwtproperties")
public class JWTConfigurationProperties {
    private String secretKey;

    public JWTConfigurationProperties(String secretKey) {
        this.secretKey = secretKey;
    }

    public JWTConfigurationProperties() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
