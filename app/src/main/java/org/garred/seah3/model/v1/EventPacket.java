package org.garred.seah3.model.v1;

import java.io.Serializable;
import java.util.List;

import org.joda.time.DateTime;

public class EventPacket implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final DateTime requestDate;
	private List<HashEvent> events;
	
	public EventPacket() {
		requestDate = DateTime.now();
	}

	public DateTime getRequestDate() {
		return requestDate;
	}

	public List<HashEvent> getEvents() {
		return events;
	}

	public void setEvents(List<HashEvent> events) {
		this.events = events;
	}

}
