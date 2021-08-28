package com.example.service.foo;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

@RestController
public class FooController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FooController.class);
    RestTemplate restTemplate;
    CircuitBreakerRegistry circuitBreakerRegistry;
    TimeLimiterConfig timeLimiterConfig;
    TimeLimiter timeLimiter;
    FooService fooService;

    TimeLimiterConfig tlConfig
            = TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(1)).build();

    CircuitBreakerConfig cbConfig = new CircuitBreakerConfig.Builder()
            .failureRateThreshold(1)
            .minimumNumberOfCalls(1)
            .build();

    @Autowired
    public FooController(FooService fooService) {
        this.restTemplate = new RestTemplate();
        circuitBreakerRegistry = CircuitBreakerRegistry.of(cbConfig);
        timeLimiter = TimeLimiter.of(tlConfig);
        this.fooService = fooService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello foo";
    }

    @GetMapping("/bar/annotated")
    public ResponseEntity invokeBarAnnotated() {
        return ResponseEntity.ok(fooService.invokeRemoteBar());
    }

    @GetMapping("/bar")
    public ResponseEntity invokeBar() {

//        Supplier<CompletionStage<String>> origCompletionStageSupplier =
//                () -> CompletableFuture.supplyAsync(this::invokeRemoteBar);
//        Supplier<CompletionStage<String>> timeLimiterDecorated = timeLimiter.decorateCompletionStage(scheduler, origCompletionStageSupplier);
//        timeLimiterDecorated.get().whenComplete((result, ex) -> {
//            if (ex != null) {
//                System.out.println(ex.getMessage());
//            }
//            if (result != null) {
//                System.out.println(result);
//            }
//        });

        try {
//            Supplier<String> directSupplier = this::invokeRemoteBar;
//            Supplier<String> decorated = circuitBreaker.decorateSupplier(directSupplier);
//            return ResponseEntity.ok(decorated.get());

//            Supplier<CompletionStage<String>> decorated = circuitBreaker.decorateSupplier(origCompletionStageSupplier);
//            return ResponseEntity.ok(decorated.get().toCompletableFuture().get());

//            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//
//            Supplier<CompletionStage<String>> supplier = () -> CompletableFuture.supplyAsync(this::invokeRemoteBar);
//
//            Decorators.DecorateCompletionStage<String> decorated = Decorators.ofCompletionStage(supplier)
//                    .withCircuitBreaker(circuitBreaker)
//                    .withTimeLimiter(timeLimiter, scheduledExecutorService);
//
//            return ResponseEntity.ok(decorated.get().toCompletableFuture().get());

//            String s = timeout();

//            String s = simpleCB();
            
            String s = timeoutAndCB();

            return ResponseEntity.ok(s);
        } catch (Exception e) {
            LOGGER.error("Remote call issue: " + e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String timeoutAndCB() throws ExecutionException, InterruptedException {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("my");
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Supplier<CompletionStage<String>> supplier = () -> CompletableFuture.supplyAsync(this::invokeRemoteBar);

        Supplier<CompletionStage<String>> decorated = Decorators.ofCompletionStage(supplier)
                .withCircuitBreaker(circuitBreaker)
                .withTimeLimiter(timeLimiter, scheduledExecutorService)
                .decorate();

        return decorated.get().toCompletableFuture().get();
    }

    private String timeout() throws ExecutionException, InterruptedException {
            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            Supplier<CompletionStage<String>> supplier = () -> CompletableFuture.supplyAsync(this::invokeRemoteBar);

        Supplier<CompletionStage<String>> decorated = Decorators.ofCompletionStage(supplier)
                    .withTimeLimiter(timeLimiter, scheduledExecutorService)
                .decorate();

        return decorated.get().toCompletableFuture().get();
    }

    private String simpleCB() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("my");
        String s = Decorators.ofSupplier(this::invokeRemoteBar)
                .withCircuitBreaker(circuitBreaker)
                .get();
        return s;
    }

    public String invokeRemoteBar() {
//        return restTemplate.getForObject("http://google.com:8091/hello", String.class);
        return restTemplate.getForObject("http://localhost:8091/hello", String.class);
    }
}
