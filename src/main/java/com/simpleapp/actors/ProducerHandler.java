package com.simpleapp.actors;

import akka.actor.AbstractActor;
import com.simpleapp.actors.dto.EventIn;

public interface ProducerHandler {
    public void produce(AbstractActor.ActorContext context, EventIn e);
}
