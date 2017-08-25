package com.simpleapp.askPattern.actors;

import akka.actor.AbstractActor;
import com.simpleapp.dto.EventIn;

public interface ProducerHandler {
    public void produce(AbstractActor.ActorContext context, EventIn e);
}
