package com.simpleapp.askPattern;

import com.simpleapp.askPattern.actors.ActorA;
import com.simpleapp.askPattern.actors.ActorB;
import com.simpleapp.askPattern.actors.ActorC;
import com.simpleapp.askPattern.actors.MasterActor;
import com.simpleapp.guice.GuiceActorUtils;
import com.simpleapp.guice.GuiceExtension;
import com.simpleapp.guice.GuiceExtensionImpl;
import com.google.inject.Injector;
import com.typesafe.config.Config;

import akka.actor.ActorSystem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AskApp {

	public void run() throws Exception {
		final Injector injector = StartSystem.injector;
        final ActorSystem system = injector.getInstance(ActorSystem.class);
        final Config engineConf = injector.getInstance(Config.class);
        
		//Add shutdownhook
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("START - shutdown akka actor system");
            system.terminate();
            System.out.println("END - shutdown akka actor system");
        }));

        //configure Guice
        final GuiceExtensionImpl guiceExtension = GuiceExtension.provider.get(system);
        guiceExtension.setInjector(injector);

        system.actorOf(
            GuiceActorUtils.makeProps(system, MasterActor.class), "Master"
        );
        system.actorOf(
            GuiceActorUtils.makeProps(system, ActorA.class), "ActorA"
        );
        system.actorOf(
            GuiceActorUtils.makeProps(system, ActorB.class), "ReceiverActor"
        );
        system.actorOf(
            GuiceActorUtils.makeProps(system, ActorC.class), "ActorC"
        );

        LOGGER.info("-------------------------------------------------");
        LOGGER.info("        SIMPLE APP STARTED");
        LOGGER.info("-------------------------------------------------");

    }
}
