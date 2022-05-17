package pl.grabkowski.CakeOrderPlatformReactiveMongo.Services;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.Model.User;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.Repositories.UserRepository;
import reactor.core.publisher.Mono;
@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return userRepository.findByUsername(s);
    }
}
