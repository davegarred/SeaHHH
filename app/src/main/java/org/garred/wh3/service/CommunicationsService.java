package org.garred.wh3.service;

import java.io.Serializable;

import org.garred.seah3.threads.MasterGet;
import org.garred.seah3.threads.ThreadCallback;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Handler.Callback;
import android.os.Message;


public class CommunicationsService implements ThreadCallback {
	
	private final Callback controllerCallback;
	private MasterGet get = null;

	public CommunicationsService(Callback callController) {
		this.controllerCallback = callController;
	}

	public void downloadEventList(String androidId, int versionCode) {
		get = new MasterGet(this, "http://nullgeodesic.com/seahhh/v1?dev=" + androidId + "&vers=" + versionCode, "");
//		get = new MasterGet(this, "http://192.168.1.189:8080/seahhh/v1?dev=" + androidId + "&vers=" + versionCode, "");
		new Thread(get).start();
	}

	@Override
	public void update(int returnMessage) {
		Message message = new Message();
		Bundle bundle = new Bundle();
		bundle.putSerializable("events", (Serializable) get.getEvents());
		if(get.getMessage() != null && !get.getMessage().isEmpty()) {
			bundle.putString("message", get.getMessage());
		}
		message.setData(bundle);
		Looper.prepare();
		Handler handler = new Handler(controllerCallback);
		handler.dispatchMessage(message);
	}

}
