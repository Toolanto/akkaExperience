package com.simpleapp.askPattern.actors;

import java.util.concurrent.atomic.AtomicInteger;

import com.simpleapp.guice.GuiceAbstractActor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActorB extends GuiceAbstractActor {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, e -> {
                getSender().tell("Sono actorB: " + counter.getAndIncrement(), getSelf());
//                Thread.sleep(2000);
            })
            .matchAny(o -> {
                LOGGER.warn("not handled message", o);
            })
            .build();
    }
}
