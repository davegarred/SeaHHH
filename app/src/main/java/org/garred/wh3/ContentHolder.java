package org.garred.wh3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.garred.seah3.model.HashEvent;

public class ContentHolder {

	public static final String EVENT_DETAIL_ID = "event_id";

	public static List<HashEvent> ITEMS = new ArrayList<HashEvent>();
	public static Map<String, HashEvent> ITEM_MAP = new HashMap<String, HashEvent>();
	private static String message = "";
	
	private static void addItem(HashEvent item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.getId().toString(), item);
	}

	public static void setItems(List<HashEvent> events) {
		ITEMS.clear();
		ITEM_MAP.clear();
		for(HashEvent event : events) {
			addItem(event);
		}
	}
	
	public static void setMessage(String msg) {
		message = msg;
	}

	public static String getMessage() {
		return message;
	}
}
