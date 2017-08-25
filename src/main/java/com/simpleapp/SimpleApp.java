package com.simpleapp;

import com.simpleapp.actors.ActorA;
import com.simpleapp.actors.ActorB;
import com.simpleapp.actors.ActorC;
import com.simpleapp.actors.MasterActor;
import com.simpleapp.guice.GuiceActorUtils;
import com.simpleapp.guice.GuiceExtension;
import com.simpleapp.guice.GuiceExtensionImpl;
import com.google.inject.Injector;
import com.typesafe.config.Config;

import akka.actor.ActorSystem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleApp {

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
        
//		new HTTPListener(
//			system, engineConf.getString("engine.http.listener.health.host"), engineConf.getInt("engine.http.listener.health.port")
//		);

//        system.actorOf(
//            GuiceActorUtils.makeProps(system, MachineStatusInfoActor.class)
//        );
        
        system.actorOf(
            GuiceActorUtils.makeProps(system, MasterActor.class), "Master"
        );
        system.actorOf(
            GuiceActorUtils.makeProps(system, ActorA.class), "ActorA"
        );
        system.actorOf(
            GuiceActorUtils.makeProps(system, ActorB.class), "ActorB"
        );
        system.actorOf(
            GuiceActorUtils.makeProps(system, ActorC.class), "ActorC"
        );

        LOGGER.info("-------------------------------------------------");
        LOGGER.info("        SIMPLE APP STARTED");
        LOGGER.info("-------------------------------------------------");

    }
}
