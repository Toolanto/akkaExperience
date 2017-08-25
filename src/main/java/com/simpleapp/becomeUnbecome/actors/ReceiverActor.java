package com.simpleapp.becomeUnbecome.actors;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import akka.actor.AbstractActor;
import akka.actor.UnhandledMessage;
import com.simpleapp.guice.GuiceAbstractActor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiverActor extends GuiceAbstractActor {


    private static final int MAX = 10;
    private AbstractActor.Receive available;
    private AbstractActor.Receive busy;
    private final AtomicInteger counter;

    public ReceiverActor() {

        counter = new AtomicInteger();

        available = receiveBuilder()
            .match(String.class, (String s) -> {

                final int message = counter.getAndIncrement();
                getSender().tell("Received new message " + message, getSelf());
                LOGGER.info("Received message {}", message);

                if(counter.get() == MAX) {
                    getContext().become(busy);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getContext().become(available);
                        }
                    }, 10000L);
                }

            })
            .build();

        busy = receiveBuilder()
            .matchAny(o -> {

                LOGGER.info("Received message but i am busy");
                counter.set(0);
                getSender().tell(new UnhandledMessage("Max message arrive", getSelf(), getSelf()), getSelf());

            })
            .build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, e -> getContext().become(available))
            .matchAny(o -> {
                LOGGER.warn("not handled message", o);
            })
            .build();
    }
}
