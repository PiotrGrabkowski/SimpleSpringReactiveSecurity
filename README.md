# SimpleSpringReactiveSecurity
Simple project showing features of Spring Reactive Security with Reactive MongoDb


The project uses Spring's reactive stack (Spring Webflux and Netty). 
For storing information about users the MongoDb is used (and of course reactive drivers).
Authorization of requests is achieved through Json Web Token (JWT) - the session is stateless.
