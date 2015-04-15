package org.garred.seah3.model.communicable;

import java.io.Serializable;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * This will provide a generic packet that can be standardized
 * for all versions of the DFWH3 app. Only the communicable interface
 * and a string identifier is exposed to protect order versions.
 * 
 * @author dave
 *
 */
public class CommunicablePacket implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final DateTime requestDate;
	private final Map<String,Communicable> items;
	
	public CommunicablePacket(Map<String,Communicable> items) {
		requestDate = DateTime.now();
		this.items = items;
	}

	public DateTime getRequestDate() {
		return requestDate;
	}

	public Communicable getItem(String identifier) {
		return items.get(identifier);
	}

}
