package com.simpleapp.guice;

import com.google.inject.Injector;

import akka.actor.Props;
import akka.camel.javaapi.UntypedConsumerActor;

public abstract class GuiceUntypedConsumerActor extends UntypedConsumerActor {
	
    public Injector getInjector() {
        return GuiceExtension.provider.get(getContext().system()).getInjector();
    }

    public Props makeGuiceProps(Class<?> clazz, Object ... arguments) {
        return Props.create(GuiceActorProducer.class, getInjector(), clazz, arguments);
    }
    
}