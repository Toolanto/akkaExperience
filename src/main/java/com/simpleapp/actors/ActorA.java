package com.simpleapp.actors;


import java.util.concurrent.atomic.AtomicInteger;

import com.simpleapp.guice.GuiceAbstractActor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActorA extends GuiceAbstractActor {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, e -> {
                //Thread.sleep(3000);
                getSender().tell("Sono actorA: " + counter.getAndIncrement(), getSelf());
            })
            .matchAny(o -> {
                LOGGER.warn("not handled message", o);
            })
            .build();
    }
}
