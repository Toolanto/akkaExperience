package com.simpleapp.dto;

public class EventIn {
	private final long customerId;
	private final Object event;	
	
	public EventIn(long customerId, Object event) {
		this.customerId = customerId;
		this.event = event;
	}	
	
	public long getCustomerId() {
		return customerId;
	}
	
	public Object getEvent() {
		return event;
	}
	

	@Override
	public String toString() {
		return "event on customerId " + customerId + " and body " + event.toString();
	}	
}
