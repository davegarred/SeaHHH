package org.garred.seah3.model.v2;

import com.fasterxml.jackson.annotation.JsonSetter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalTime;

import java.net.URL;

public class HashEventDto {

	public String id;
	// TODO use java.time.LocalDateTime
	private DateTime date;
	public int eventNumber;
	public String hare;
	public String eventName;
	public String description;
	public String address;
	public URL mapLink;
	private Kennel kennel;

	public LocalTime getTime() {
		return date.toLocalTime();
	}
	public boolean hasDetails() {
		return !description.isEmpty() || mapLink != null;
	}
	public String getMapLink() {
		if(mapLink == null) return "";
		return mapLink.toExternalForm();
	}

	public boolean beforeDate(DateTime date) {
		if(this.date.isBefore(date)) return true;
		return false;
	}

	public boolean afterDate(DateTime date) {
		if(this.date.isAfter(date)) return true;
		return false;
	}

	public String getTitle() {
		StringBuilder builder = new StringBuilder();
		builder.append(kennel.getName());
		if(eventNumber > 0) builder.append(" #" + Integer.toString(eventNumber));
		return builder.toString();
	}

	public CharSequence getDateTime() {
		StringBuilder builder = new StringBuilder();
		builder.append(date.toString("MMMM d"));
		if(date.getHourOfDay() > 0) builder.append(", " + date.toString("h:mm a"));
		return builder;
	}
	public String getEventName() {
		if(eventName.length() > 0) return eventName;
		return this.getTitle();
	}
	public boolean hasEventNumber() {
		if(eventNumber > 0) return true;
		return false;
	}
	public boolean hasTime() {
		LocalTime time = getTime();
		if(time.getMillisOfDay() > 0 && time.getHourOfDay() <= 24) return true;
		return false;
	}
	public int getDay() {
		return date.get(DateTimeFieldType.dayOfMonth());
	}
	public int getMonth() {
		return date.get(DateTimeFieldType.monthOfYear());
	}

	public String getDateString() {
		return date.toString("MMMM d, yyyy"); 	//String.format("%1$tB %1$te, %1$tY", date);
	}
	public String getDateTimeString() {
		return date.toString("yyyy-MM-dd HH:mm");
	}
	public String getDateStringShort() {
		return date.toString("E\nMMM d"); 		//String.format("%1$ta\n%1$tb %1$te", date);
	}
	public String getDayOfWeek() {
		return date.toString("E"); 				//String.format("%1$ta", date);
	}
	public String getDayAndDate() {
		return date.toString("EEEE, MMMM d"); 	//String.format("%1$tA, %1$tB %1$te", date);
	}


	public DateTime getDate() {
		return date;
	}

	@JsonSetter("kennel")
	public void setDate(Integer[] dateValues) {
		this.date = new DateTime(dateValues);
	}
	public Kennel getKennel() {
		return kennel;
	}

	@JsonSetter("kennel")
	public void setKennel(String kennelId) {
		for(Kennel kennel : Kennel.values()) {
			if(kennel.name().equals(kennelId)) {
				this.kennel = kennel;
				return;
			}
		}
		this.kennel = Kennel.OTHER;
	}
}
