package pl.grabkowski.CakeOrderPlatformReactiveMongo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

@SpringBootTest
class CakeOrderPlatformReactiveMongoApplicationTests {


		@Test
		public void contextTest() {

			Mono<Object> contextMono = Mono.deferContextual(ctx -> Mono.just(ctx.get("key")))
					.contextWrite(ctx -> ctx.put("key" , "HelloWorld"))
					.doOnNext(System.out::println);

			StepVerifier.create(contextMono)
					.expectNext("HelloWorld")
					.verifyComplete();




		}




}
