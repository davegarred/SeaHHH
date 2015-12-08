package org.garred.seah3.model.v2;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalTime;

import java.net.URL;

public class HashEventDto {

	public String id;
	public Integer[] date;
	public int eventNumber;
	public String hare;
	public String eventName;
	public String description;
	public String address;
	public URL mapLink;

	public String kennel;
}
