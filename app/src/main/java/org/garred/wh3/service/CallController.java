package org.garred.wh3.service;

import java.util.ArrayList;
import java.util.List;

import org.garred.seah3.model.HashEvent;
import org.garred.seah3.model.v2.HashEventDto;
import org.garred.wh3.ContentHolder;
import org.garred.wh3.EventListActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.Settings.Secure;

public class CallController implements Callback {

	private final CommunicationsService commService = new CommunicationsService(this);
	private Activity activity;

	public CallController(Activity activity) {
		this.activity = activity;
		String androidId = Secure.getString(activity.getContentResolver(), Secure.ANDROID_ID); 
		int versionCode = 0;
		try {
			versionCode = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		commService.downloadEventList(androidId,versionCode);
	}

	@Override
	public boolean handleMessage(Message msg) {
		if(activity != null) {
			List<HashEvent> events = new ArrayList<HashEvent>();
			for(HashEventDto dto : (List<HashEventDto>) msg.getData().getSerializable("events")) {
				events.add(HashEvent.fromDto(dto));
			}

			ContentHolder.setItems(events);
			String message = msg.getData().getString("message");
			if(message != null && !message.isEmpty()) ContentHolder.setMessage(message);

			Intent listIntent = new Intent(activity, EventListActivity.class);
			listIntent.putExtra("message",message);
			activity.startActivity(listIntent);
		}
		return false;
	}

}
