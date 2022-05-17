package pl.grabkowski.CakeOrderPlatformReactiveMongo.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.ConfigurationProperties.JWTConfigurationProperties;

@Service
public class JWTVerrifierImpl implements JWTVerrifier{


    private final JWTConfigurationProperties jwtConfigurationProperties;

    public JWTVerrifierImpl(JWTConfigurationProperties jwtConfigurationProperties) {
        this.jwtConfigurationProperties = jwtConfigurationProperties;
    }


    @Override
    public boolean verrifyJWT(String jwt) {
        try {
            obtainJwsClaims(jwt);

        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            return false;
        } catch (SignatureException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public Jws<Claims> obtainJwsClaims (String jwt){
       return  Jwts.parserBuilder().setSigningKey(jwtConfigurationProperties.getSecretKey().getBytes())
                .build().parseClaimsJws(jwt);

    }
}
