package com.simpleapp.actors;

import akka.camel.CamelMessage;
import com.google.inject.Inject;
import com.simpleapp.guice.GuiceUntypedConsumerActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;

public class MachineStatusInfoActor extends GuiceUntypedConsumerActor {
	private final static Logger LOG = LoggerFactory.getLogger(MachineStatusInfoActor.class);

	private final OperatingSystemMXBean osBean;
	private final Runtime runtime;
	
	@Inject
	public MachineStatusInfoActor() {
		this.osBean = ManagementFactory.getOperatingSystemMXBean();
		this.runtime = Runtime.getRuntime();
	}

	@Override
	public String getEndpointUri() {
		return "timer:machine-status?delay=10s&period=100s";		
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onReceive(Object message) throws Throwable {
		if (message instanceof CamelMessage) {
			printStatus();
		} else {
			unhandled(message);
		}
	}

	private void printStatus() {
		LOG.info("############## MACHINE STATUS ################");
		for (Method method : osBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);

			logPublicMethodGet(method);
		}		
		
		LOG.info("############## RUNTIME ################");
		LOG.info("  totalMemory = {}", runtime.totalMemory());
		LOG.info("  freeMemory = {}", runtime.freeMemory());
		
		LOG.info("##############################################");
	}

	private void logPublicMethodGet(Method method) {
		if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
            Object value;
            try {
                value = method.invoke(osBean);

                if (value instanceof Long) {
                    value = MessageFormat.format("{0}", value);
                }
            } catch (Exception e) {
                value = e;
            }

            LOG.info("  {} = {}", method.getName(), value);
        }
	}
}
