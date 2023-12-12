package com.example.webflux;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class ReactiveExamples {

    public static void main(String[] args) throws InterruptedException {
//        testSubscribeOnPublishOn();
        testZipVoid();
//        concurrent();
        System.out.println("-----------------");

        Thread.sleep(20000);
    }

    public static void collectingElements() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .subscribe(elements::add);

        assertThat(elements).containsExactly(1, 2, 3, 4);
    }

    public static void mappingElements() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .subscribe(elements::add);

        assertThat(elements).containsExactly(2, 4, 6, 8);
    }

    public static void combiningElements() {
        List<String> elements = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .zipWith(Flux.range(0, Integer.MAX_VALUE),
                        (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
                .subscribe(elements::add);

        assertThat(elements).containsExactly(
                "First Flux: 2, Second Flux: 0",
                "First Flux: 4, Second Flux: 1",
                "First Flux: 6, Second Flux: 2",
                "First Flux: 8, Second Flux: 3");
    }

    public static void backPressure() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
                .log()
                .subscribe(new Subscriber<Integer>() {
                    private Subscription s;
                    int onNextAmount;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.s = s;
                        s.request(3);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        elements.add(integer);
                        onNextAmount++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (onNextAmount % 3 == 0) {
                            s.request(3);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void hotStreams() {
        ConnectableFlux<Object> connectableFlux = Flux.create(fluxSink -> {
            while (true) {
                fluxSink.next(System.currentTimeMillis());
            }
        }).sample(Duration.ofSeconds(2))
                .publish();

        connectableFlux.subscribe(System.out::println);

        connectableFlux.connect();
    }

    public static void concurrent() {
        // subscribeOn() for whole subscription
        // publishOn() for subsequent operations

        List<Integer> elements = new ArrayList<>();
        Flux.just(1, 2, 3, 4, 5)
                .publishOn(Schedulers.boundedElastic())
                .log()
                .map(i -> i * 2)
                .delayElements(Duration.ofMillis(300))
                .subscribeOn(Schedulers.elastic())
                .subscribe(i -> System.out.println(i + " - " + Thread.currentThread().getName()));

        Flux.just(6, 7, 8, 9, 10)
                .publishOn(Schedulers.boundedElastic())
                .log()
                .map(i -> i * 2)
                .delayElements(Duration.ofMillis(300))
                .subscribeOn(Schedulers.elastic())
                .subscribe(i -> System.out.println(i + " - " + Thread.currentThread().getName()));
    }

    public static void testSubscribeOnPublishOn() {
        Consumer<Integer> consumer = s -> System.out.println(s + " : " + Thread.currentThread().getName());

        Flux.range(1, 5)
                .doOnNext(consumer)
                .map(i -> {
                    System.out.println("Inside map the thread is " + Thread.currentThread().getName() + ";  " + i);
                    return i * 10;
                })
                .doOnNext(consumer)
                .publishOn(Schedulers.newElastic("Second_PublishOn()_thread"))
                .doOnNext(consumer)
                .subscribeOn(Schedulers.newElastic("subscribeOn_thread"))
                .subscribe();

    }

//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        Mono<String> monoA = componentA.getNext();
//        Mono<String> monoB = componentB.getNext();
//
//        BiFunction<String, String, Mono<Void>> fun = (String inputA, String inputB) -> {
//
//            exchange.getResponse()
//                    .getHeaders().add("test-header-a", inputA);
//            exchange.getResponse()
//                    .getHeaders().add("test-header-b", inputB);
//
//            return chain.filter(exchange);
//        };
//        return Mono.zip(monoA, monoB, fun);
//    }

    public static void testZip() {
        Mono<String> monoA = Mono.fromCallable(() -> {
            Thread.sleep(100);
            Thread.sleep(100);
            Thread.sleep(100);
            System.out.println("mono A");
            return "test1";
        }).subscribeOn(Schedulers.parallel());

        Mono<String> monoB = Mono.fromCallable(() -> {
            System.out.println("mono B");
            return "test2";
        }).subscribeOn(Schedulers.parallel());

        Mono<Tuple2<String, String>> zip = Mono.zip(monoA, monoB);
        zip.map(tuple2 -> {
            System.out.println(tuple2.getT1() + " - " + tuple2.getT2());
            return tuple2.getT1() + " - " + tuple2.getT2();
        }).block();
    }

    public static void testZipVoid() {
        Mono<Void> h1 = Mono.fromRunnable(() -> {
            System.out.println("Hello1.1");
            throw new RuntimeException("XXX");
        });

        Mono<Void> h2 = Mono.fromRunnable(() -> {
            System.out.println("Hello2.2");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Hello1.2");
        });

        Mono<Void> parallelH1 = h1.subscribeOn(Schedulers.parallel());
        Mono<Void> parallelH2 = h2.subscribeOn(Schedulers.parallel());

        Mono<Tuple2<Void, Void>> zip = Mono.zipDelayError(parallelH1, parallelH2)
                .onErrorMap((t) -> {
                    System.out.println("Error: " + t);
                    return new RuntimeException("Error: " + t.getMessage(), t);
                });
        zip.log().block();
    }
}
