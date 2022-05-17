package pl.grabkowski.CakeOrderPlatformReactiveMongo.JWT;

import pl.grabkowski.CakeOrderPlatformReactiveMongo.DTO.UserTO;
import reactor.core.publisher.Mono;

public interface JWTProvider {
    Mono<String> provideJWT (UserTO userTO);
}
