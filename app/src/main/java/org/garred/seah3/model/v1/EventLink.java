package org.garred.seah3.model.v1;

import org.garred.seah3.model.v2.Kennel;
import org.joda.time.DateTime;

public class EventLink {
	
	private final DateTime date;
	private String image;
	private String title;
	private String hare;
	private String link;
	private Kennel eventType;
	
	public EventLink(int year, int month, int day) {
		date = new DateTime(year,month,day,0,0);
	}

	public EventLink(DateTime date) {
		this.date=date;
	}

	public DateTime getDate() {
		return date;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHare() {
		return hare;
	}
	public void setHare(String hare) {
		this.hare = hare;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	public Kennel getEventType() {
		return eventType;
	}

	public void setEventType(Kennel eventType) {
		this.eventType = eventType;
	}

}
