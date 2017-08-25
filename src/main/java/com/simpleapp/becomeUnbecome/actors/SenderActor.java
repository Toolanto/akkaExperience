package com.simpleapp.becomeUnbecome.actors;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.google.inject.Inject;
import com.simpleapp.guice.GuiceAbstractActor;
import com.simpleapp.guice.GuiceActorUtils;
import lombok.extern.slf4j.Slf4j;
import scala.concurrent.duration.Duration;

@Slf4j
public class SenderActor extends GuiceAbstractActor {
    private ActorSystem system;

    @Inject
	public SenderActor(ActorSystem system) {
        this.system = system;
    }


    @Override
    public void preStart() throws Exception {
        super.preStart();


        final ActorRef receiver = system.actorOf(
            GuiceActorUtils.makeProps(system, ReceiverActor.class), "ReceiverActor"
        );

        system.scheduler().schedule(
            Duration.create(500, TimeUnit.MILLISECONDS),
            Duration.create(500, TimeUnit.MILLISECONDS),
            receiver,
            "New message",
            system.dispatcher(),
            getSelf()
        );
    }

    @Override
	public Receive createReceive() { 
		return receiveBuilder()
            .match(String.class, (String m) -> LOGGER.info("Message from received: {}", m))
			.matchAny(o -> {
				LOGGER.warn("not handled message {}", o);
			})
			.build();			
	}
}
