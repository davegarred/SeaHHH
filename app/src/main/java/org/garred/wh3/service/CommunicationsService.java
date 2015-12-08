package org.garred.wh3.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.garred.seah3.model.communicable.CommunicablePacket;
import org.garred.seah3.model.communicable.Events1;
import org.garred.seah3.model.communicable.Messages1;
import org.garred.seah3.model.v2.CalendarDto;
import org.garred.seah3.threads.MasterGet;
import org.garred.seah3.threads.ThreadCallback;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Handler.Callback;
import android.os.Message;

import com.google.gson.Gson;

public class CommunicationsService implements ThreadCallback {

	public static final int COMPLETED_DOWNLOAD = 1000;
	public static final int FAILED_DOWNLOAD = 1001;

	private final Callback controllerCallback;
//	private final ObjectMapper objectMapper;

	public CalendarDto receivedDto;

	public CommunicationsService(Callback callController) {
		this.controllerCallback = callController;
//		objectMapper = new ObjectMapper();
	}

	public void downloadEventList(String androidId, int versionCode) {
//		get = new MasterGet(this, "http://nullgeodesic.com/seahhh/v1?dev=" + androidId + "&vers=" + versionCode, "");
//		get = new MasterGet(this, "http://192.168.1.190:8080/seahhh/v1?dev=" + androidId + "&vers=" + versionCode, "");
		Runnable runnable = getV2(this, androidId, versionCode);
		new Thread(runnable).start();
	}

	@Override
	public void update(int returnMessage) {
		Message message = new Message();
		Bundle bundle = new Bundle();
		bundle.putSerializable("events", (Serializable) receivedDto.events);
		if(receivedDto.message != null && !receivedDto.message.isEmpty()) {
			bundle.putString("message", receivedDto.message);
		}
		message.setData(bundle);
		Looper.prepare();
		Handler handler = new Handler(controllerCallback);
		handler.dispatchMessage(message);
	}
	
	public Runnable getV2(final CommunicationsService threadCallback, String androidId, int versionCode) {
		final String target = "http://nullgeodesic.com/seahhh/v2?dev=" + androidId + "&vers=" + versionCode;
		return new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(target);
					URLConnection connection = url.openConnection();
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder builder = new StringBuilder();
//					receivedDto = objectMapper.readValue(in, CalendarDto.class);
					String val;
					while((val = reader.readLine()) != null) {
						builder.append(val);
					}
					in.close();
					Gson gson = new Gson();
					receivedDto = gson.fromJson(builder.toString(), CalendarDto.class);
					threadCallback.update(COMPLETED_DOWNLOAD);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					threadCallback.update(FAILED_DOWNLOAD);
				}
			}
		};
	}

}
