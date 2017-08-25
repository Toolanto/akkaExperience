package com.simpleapp;

import com.simpleapp.guice.AkkaModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class StartSystem {	
	public static Injector injector;
	
	public static void main(String[] args) throws Exception {
		injector = Guice.createInjector(new AkkaModule());

		injector.getInstance(SimpleApp.class).run();
    }
}

