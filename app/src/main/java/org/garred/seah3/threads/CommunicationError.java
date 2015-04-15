package org.garred.seah3.threads;

public class CommunicationError {
	
	private final String errorString;
	
	public CommunicationError(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}

}
