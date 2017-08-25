package com.simpleapp.actors;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.simpleapp.actors.dto.EventIn;
import com.simpleapp.guice.GuiceAbstractActor;
import com.google.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import scala.concurrent.duration.Duration;

@Slf4j
public class MasterActor extends GuiceAbstractActor {
	public static final String EVENT_PRODUCER = "eventProducer";
	
	private final ProducerHandler handler;
    private ActorSystem system;

    @Inject
	public MasterActor(
	    ProducerHandler handler,
        ActorSystem system
    ) {
		this.handler = handler;
        this.system = system;
    }


    @Override
    public void preStart() throws Exception {
        super.preStart();

        system.scheduler().schedule(
            Duration.create(10000, TimeUnit.MILLISECONDS),
            Duration.create(10000, TimeUnit.MILLISECONDS),
            getSelf(),
            new EventIn(123L, "ciao"),
            system.dispatcher(),
            null
        );
    }

    @Override
	public Receive createReceive() { 
		return receiveBuilder()
			.match(EventIn.class, e -> handler.produce(getContext(), e))
			.matchAny(o -> {
				LOGGER.warn("not handled message", o);
			})
			.build();			
	}
}
