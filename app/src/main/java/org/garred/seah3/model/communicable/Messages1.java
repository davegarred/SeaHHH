package org.garred.seah3.model.communicable;


/**
 * An initial message item providing a simple String message.
 * 
 * @author dave
 *
 */
public class Messages1 implements Communicable {

	public static final String IDENTIFIER = "message_1";
	private static final long serialVersionUID = 1L;

	private final String message;
	
	public Messages1(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}
	
}
