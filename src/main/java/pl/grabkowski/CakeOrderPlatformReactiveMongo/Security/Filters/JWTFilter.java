package pl.grabkowski.CakeOrderPlatformReactiveMongo.Security.Filters;


import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Example;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.JWT.JWTVerrifier;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.Model.User;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.Repositories.UserRepository;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class JWTFilter implements WebFilter {

    private final JWTVerrifier jwtVerrifier;
    private  final UserRepository userRepository;

    public JWTFilter(JWTVerrifier jwtVerrifier, UserRepository userRepository) {
        this.jwtVerrifier = jwtVerrifier;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        String header = serverWebExchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer "))
        {
            String jwt = header.substring(7);
            if(jwtVerrifier.verrifyJWT(jwt)){


                Claims body = jwtVerrifier.obtainJwsClaims(jwt).getBody();
                String username = body.getSubject();
               /* List<Map<String, String>>  setOfRoles = (List<Map<String, String>>)  body.get("auth");
                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = setOfRoles.stream()
                        .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                        .collect(Collectors.toSet());
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
                */

               Mono<SecurityContextImpl> contextMono =  Mono.just(username).flatMap(u -> userRepository.findByUsername(u))
                        .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities()))
                        .map(au -> new SecurityContextImpl(au));

               return webFilterChain.filter(serverWebExchange).contextWrite(ReactiveSecurityContextHolder.withSecurityContext(contextMono));









  /*              Mono<UserDetails> userDetailsMono = userRepository.findByUsername(username);
               Mono<Authentication> ma = userDetailsMono.map(userDetails ->
                         new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null, userDetails.getAuthorities()));
``  `

               AtomicReference<Authentication> authentication = new AtomicReference<>();
               ma.subscribe(a -> authentication.set(a));*/
         //      Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);


            //   return webFilterChain.filter(serverWebExchange).subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication));  deprecated




           //     return  webFilterChain.filter(serverWebExchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
               


            }


        }

        return  webFilterChain.filter(serverWebExchange);
    }
}
