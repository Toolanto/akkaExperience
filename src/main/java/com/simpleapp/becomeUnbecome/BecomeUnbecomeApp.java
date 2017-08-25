package com.simpleapp.becomeUnbecome;

import akka.actor.ActorSystem;
import com.google.inject.Injector;
import com.simpleapp.becomeUnbecome.actors.ReceiverActor;
import com.simpleapp.becomeUnbecome.actors.SenderActor;
import com.simpleapp.guice.GuiceActorUtils;
import com.simpleapp.guice.GuiceExtension;
import com.simpleapp.guice.GuiceExtensionImpl;
import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BecomeUnbecomeApp {

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
            GuiceActorUtils.makeProps(system, SenderActor.class), "Sender"
        );
        system.actorOf(
            GuiceActorUtils.makeProps(system, ReceiverActor.class), "Receiver"
        );

        LOGGER.info("-------------------------------------------------");
        LOGGER.info("        SIMPLE APP STARTED");
        LOGGER.info("-------------------------------------------------");

    }
}
