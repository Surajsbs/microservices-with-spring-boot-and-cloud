# Employee service
Employee service is the entry service for all of the requests. This service is registered in the service registry for dynamic discovery.
```
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.instance.instance-id=${spring.application.name}:${random.int}
eureka.instance.hostname=localhost
```
### Dependencies for service discovery and circuit breaker and load balanced:
```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-circuitbreaker-reactor-resilience4j</artifactId>
</dependency>
```
# Department service
Department service is the sub-service which pull the associated department for an employee. This service is registered in the service registry for dynamic discovery.
```
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=false
eureka.instance.instance-id=${spring.application.name}:${random.int}
eureka.instance.hostname=localhost
```

### Dependencies
```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```


# Service registry
Service registry is a spring cloud dynamic service registry for all the clients. 

### @EnableEurekaServer
It enables the service registry to register the services (clients). And it resolves clients ip and port dynamically.


### Dependencies
```
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```
