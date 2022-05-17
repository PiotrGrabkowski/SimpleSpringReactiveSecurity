package pl.grabkowski.CakeOrderPlatformReactiveMongo.InitAndTest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.Model.User;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.Repositories.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class InitAndTestClass {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitAndTestClass(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init(){


      userRepository.deleteAll().then(Mono.just(new User()))
                .map((user) -> {
                    user.setUsername("Jarek");
                    user.setPassword(
                            passwordEncoder.encode("Jarek23")
                    );
                    user.setAccountEnabled(true);
                    user.setRole("ROLE_ADMIN");
                    return user;
                }).flatMap(user -> userRepository.save(user))
                .thenMany(userRepository.findAll())
                .doOnNext(found -> System.out.println(found))
               .subscribe();





    }

}
