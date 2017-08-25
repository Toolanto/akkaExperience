package com.simpleapp.guice;

import java.io.File;

import akka.actor.ActorSystem;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.simpleapp.actors.MasterProducerHandlerImpl;
import com.simpleapp.actors.ProducerHandler;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AkkaModule implements Module {

    @Override
    public void configure(Binder binder) {

    	final File smartSendConfig = new File(System.getProperty("engine.config.file"));
    	final Config handlerConf = ConfigFactory.parseFile(smartSendConfig).resolve();
    	final String root = smartSendConfig.getParent();

    	binder
    		.bindConstant()
    		.annotatedWith(Names.named("rootDir"))
    		.to(root);

    	binder
        	.bind(Config.class)
        	.toProvider(() -> handlerConf)
        	.in(Singleton.class);


        final ActorSystem system = ActorSystem.create("smart-suppression", ConfigFactory.load());
        system.registerExtension(GuiceExtension.provider);
        
        binder
        	.bind(ActorSystem.class)
        	.toInstance(system);        

        binder
			.bind(ProducerHandler.class)
			.to(MasterProducerHandlerImpl.class);


    }
}
