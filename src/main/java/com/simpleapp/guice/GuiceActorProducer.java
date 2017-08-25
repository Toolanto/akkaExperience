package com.simpleapp.guice;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import com.google.common.collect.Lists;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GuiceActorProducer implements IndirectActorProducer {
    private final Injector injector;
    private Class<? extends Actor> actorClass;
    private List<Object> arguments;

    public GuiceActorProducer(Injector injector, Class<? extends Actor> actorClass, Object ... arguments) {
        this.injector = injector;
        this.actorClass = actorClass;
        this.arguments = Lists.newArrayList(arguments);
    }

    @Override
    public Actor produce() {
        LOGGER.debug("##########################################");
        LOGGER.debug("build an instance of {}", actorClass);
        LOGGER.debug("with parameters:");
        for(Object arg : arguments) {
            LOGGER.debug("{}", arg.getClass());
        }
        LOGGER.debug("##########################################");

    	return injector.getInstance(actorClass);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return actorClass;
    }
}