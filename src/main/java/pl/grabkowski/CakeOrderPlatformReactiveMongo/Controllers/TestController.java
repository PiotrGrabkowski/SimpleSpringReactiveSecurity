package pl.grabkowski.CakeOrderPlatformReactiveMongo.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.grabkowski.CakeOrderPlatformReactiveMongo.Model.User;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class TestController {

    @GetMapping("test")
    public Mono<String> test(){

    Mono<Authentication> ma = ReactiveSecurityContextHolder.getContext().map(sc -> sc.getAuthentication());

        return ma.map(a -> "hello " + a.getName());
    }
    @GetMapping("test1")
    public Mono<String> test1(){



        return Mono.just("For all users available");
    }

}
