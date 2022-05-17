package pl.grabkowski.CakeOrderPlatformReactiveMongo.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.DTO.UserTO;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.JWT.JWTProviderImpl;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.Model.User;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.Repositories.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final JWTProviderImpl jwtProviderImpl;
    private final UserRepository userRepository;

    public UserController(JWTProviderImpl jwtProviderImpl, UserRepository userRepository) {
        this.jwtProviderImpl = jwtProviderImpl;
        this.userRepository = userRepository;
    }

    @GetMapping("login")
    public Mono<String> login(@RequestBody UserTO userTO){
        return jwtProviderImpl.provideJWT(userTO);

    }
    @GetMapping("users")
    public Flux<User> getAllUsers(){
            return userRepository.findAll();

        }

}
