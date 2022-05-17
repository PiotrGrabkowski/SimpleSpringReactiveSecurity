package pl.grabkowski.CakeOrderPlatformReactiveMongo.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.ConfigurationProperties.JWTConfigurationProperties;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.DTO.UserTO;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JWTProviderImpl implements JWTProvider{

    private final ReactiveAuthenticationManager reactiveAuthenticationManager;
    private final JWTConfigurationProperties jwtConfigurationProperties;
    private Collection<? extends GrantedAuthority> authorities;

    public JWTProviderImpl(ReactiveAuthenticationManager reactiveAuthenticationManager, JWTConfigurationProperties jwtConfigurationProperties) {
        this.reactiveAuthenticationManager = reactiveAuthenticationManager;
        this.jwtConfigurationProperties = jwtConfigurationProperties;
    }

    @Override
    public Mono<String> provideJWT (UserTO userTO){

        Long now = System.currentTimeMillis();
        Long expirationTime = now + 120000;


       Mono<Authentication> ma  = reactiveAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userTO.getUsername(), userTO.getPassword()));



       return ma.map(a -> {

            Set<SimpleGrantedAuthority> setOfRoles = a.getAuthorities().stream().map(b ->  new SimpleGrantedAuthority(b.getAuthority())).collect(Collectors.toSet());


                  return   Jwts.builder()
                   .setSubject(a.getName())
                   .setIssuedAt(new Date(now))
                   .setExpiration(new Date(expirationTime))
                          .claim("auth", setOfRoles)

                   .signWith(Keys.hmacShaKeyFor(jwtConfigurationProperties.getSecretKey().getBytes()))
                   .compact();

       });
       
     


    }


}
