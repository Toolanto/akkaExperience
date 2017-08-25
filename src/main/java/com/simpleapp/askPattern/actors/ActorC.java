package com.simpleapp.askPattern.actors;

import com.simpleapp.guice.GuiceAbstractActor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActorC extends GuiceAbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, e -> {
                LOGGER.info("ActorC ha ricevuto " + e);
            })
            .matchAny(o -> {
                LOGGER.warn("not handled message", o);
            })
            .build();
    }
}
