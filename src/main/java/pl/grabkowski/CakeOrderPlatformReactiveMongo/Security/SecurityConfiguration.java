package pl.grabkowski.CakeOrderPlatformReactiveMongo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.Security.Filters.JWTFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    private final JWTFilter jwtFilter;

    public SecurityConfiguration(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity){
        return serverHttpSecurity
                .addFilterAfter(jwtFilter, SecurityWebFiltersOrder.FIRST)

                .authorizeExchange()
                .pathMatchers("/login").permitAll()
                .pathMatchers("/users").permitAll()
                .pathMatchers("/test1").permitAll()
                .anyExchange().hasRole("ADMIN")
                .and()
                .csrf().disable()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) //making session stateless
               .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

}
