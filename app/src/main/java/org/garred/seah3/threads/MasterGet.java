package org.garred.seah3.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.garred.seah3.model.communicable.CommunicablePacket;
import org.garred.seah3.model.communicable.Events1;
import org.garred.seah3.model.communicable.Messages1;
import org.garred.seah3.model.v1.HashEvent;
import org.joda.time.DateTime;

public class MasterGet implements Runnable {

	private static final long TIME_BETWEEN_UPATES = 8 * 60 * 60 * 1000; //8 hours
	private final URL masterUrl;
	private DateTime lastUpdate;
	private boolean doUpdateNow;
	private String message;
	private List<HashEvent> hashEvents;
	private List<CommunicationError> communicationErrors;
	private final ThreadCallback threadCallback;
	public static final int COMPLETED_DOWNLOAD = 0;
	public static final int FAILED_DOWNLOAD = 1;

	
	public MasterGet(ThreadCallback threadCallback, String page, String dataRequestSuffix) {
		this.threadCallback = threadCallback;
		URL initUrl = null;
		try {
			 initUrl =  new URL(page + dataRequestSuffix);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.masterUrl = initUrl;
		this.lastUpdate = new DateTime();
		this.doUpdateNow = true;
		this.communicationErrors = new ArrayList<CommunicationError>();
	}

	@Override
	public void run() {
		downloadEvents();
		doUpdateNow = false;
		lastUpdate = new DateTime();
	}
	
	private void downloadEvents() {
		if(masterUrl != null) {
			try {
				URLConnection connection = masterUrl.openConnection();
				ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
				Object o = in.readObject();
				CommunicablePacket packet = (CommunicablePacket) o;
				hashEvents = ((Events1)packet.getItem(Events1.IDENTIFIER)).getEvents();
				message = ((Messages1)packet.getItem(Messages1.IDENTIFIER)).getMessage();
				in.close();
				threadCallback.update(COMPLETED_DOWNLOAD);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public List<HashEvent> getEvents() {
		return hashEvents;
	}

	public void updateData() {
		this.doUpdateNow = true;
	}
	
	public void addError(CommunicationError communicationError) {
		communicationErrors.add(communicationError);
	}
	
	public boolean updateNow() {
		long timeToLastUpdate = new DateTime().getMillis() - lastUpdate.getMillis();
		return timeToLastUpdate < TIME_BETWEEN_UPATES || doUpdateNow;
	}

	public String getMessage() {
		return this.message;
	}

}
