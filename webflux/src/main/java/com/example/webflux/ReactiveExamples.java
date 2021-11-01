package com.example.webflux;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class ReactiveExamples {

    public static void main(String[] args) throws InterruptedException {
        testSubscribeOnPublishOn();
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
                    System.out.println("Inside map the thread is " + Thread.currentThread().getName());
                    return i * 10;
                })
                .doOnNext(consumer)
                .publishOn(Schedulers.newElastic("Second_PublishOn()_thread"))
                .doOnNext(consumer)
                .subscribeOn(Schedulers.newElastic("subscribeOn_thread"))
                .subscribe();

    }
}
