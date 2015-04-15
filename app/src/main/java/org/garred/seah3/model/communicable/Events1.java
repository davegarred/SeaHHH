package org.garred.seah3.model.communicable;

import java.util.List;

import org.garred.seah3.model.v1.HashEvent;

/**
 * Initial Events item which provides a list of events of type
 * <code>org.garred.dfwh3.model.next.HashEvent</code>
 * 
 * @author dave
 *
 */
public class Events1 implements Communicable {

	public static final String IDENTIFIER = "events_1";
	private static final long serialVersionUID = 1L;

	private final List<HashEvent> events;

	public Events1(List<HashEvent> events) {
		this.events = events;
	}
	
	public List<HashEvent> getEvents() {
		return events;
	}

	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

}
