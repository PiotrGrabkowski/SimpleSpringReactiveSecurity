package pl.grabkowski.CakeOrderPlatformReactiveMongo.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JWTVerrifier {

    boolean verrifyJWT(String jwt);
    Jws<Claims> obtainJwsClaims (String jwt);
}
