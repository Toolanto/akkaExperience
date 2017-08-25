package com.simpleapp.askPattern.actors;


//import static akka.pattern.PatternsCS.ask;
//import static akka.pattern.PatternsCS.pipe;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

import java.util.ArrayList;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import com.google.inject.Inject;
import com.simpleapp.dto.EventIn;
import lombok.extern.slf4j.Slf4j;
import scala.concurrent.Future;

@Slf4j
public class MasterProducerHandlerImpl implements ProducerHandler {

    private ActorSystem system;

    @Inject
    public MasterProducerHandlerImpl(ActorSystem system) {
        this.system = system;
    }

    @Override
    public void produce(AbstractActor.ActorContext context, EventIn e) {


        LOGGER.info("New message event");

        final ActorSelection actorA = context.actorSelection("../ActorA");
        final ActorSelection actorB = context.actorSelection("../ReceiverActor");
        final ActorSelection actorC = context.actorSelection("../ActorC");


        final ArrayList<Future<Object>> futures = new ArrayList<>();
        futures.add(ask(actorA, "request", 5000));
        LOGGER.info("x1");
        futures.add(ask(actorB, "request", 5000));
        LOGGER.info("x2");

        final Future<Iterable<Object>> sequence = Futures.sequence(futures, context.dispatcher());
        LOGGER.info("x3");

        final Future<String> map = sequence.map(
            new Mapper<Iterable<Object>, String>() {
                @Override
                public String apply(Iterable<Object> parameter) {
                    final StringBuilder sb = new StringBuilder();
                    parameter.forEach(i -> sb.append(i));

                    return sb.toString();
                }
            },
            context.dispatcher()
        );

        LOGGER.info("x4");
        pipe(map, context.dispatcher()).to(actorC);
    }

//    @Override
//    public void produce(AbstractActor.ActorContext context, EventIn e) {
//
//
//        LOGGER.info("New message event");
//
//
//        final ActorSelection actorA = context.actorSelection("../ActorA");
//
//        final ActorSelection actorB = context.actorSelection("../ReceiverActor");
//
//        final ActorSelection actorC = context.actorSelection("../ActorC");
//
//
//        final CompletableFuture<Object> future1 = ask(actorA, "request", 5000).toCompletableFuture();
//
//        final CompletableFuture<Object> future2 = ask(actorB, "request", 1000).toCompletableFuture();
//
//
//        CompletableFuture<String> transformed =
//            CompletableFuture.allOf(future1, future2)
//                .thenApply(v -> {
//                    String x = (String) future1.join();
//                    String s = (String) future2.join();
//                    return x + s;
//                });
//
//        pipe(transformed, context.dispatcher()).to(actorC);
//    }
}
