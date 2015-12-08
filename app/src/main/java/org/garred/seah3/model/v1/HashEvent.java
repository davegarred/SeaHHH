package org.garred.seah3.model.v1;

import java.io.Serializable;
import java.net.URL;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalTime;

public class HashEvent implements Comparable<Object>,Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final String id; 
	private final DateTime date;
	private final LocalTime time;
	private final Type type;
	private final int eventNumber;
	private final String hare;
	private final String eventName;
	private final String description;
	private final String address;
	private final URL mapLink;
	private final boolean locked;
	
	public enum Type {
		SEATTLE("Seattle H3",1,"SH3",true),
		TACOMA("Tacoma H3",2,"TH3",true),
		HAPPY_HOUR("Hashy Hour",3,"Seattle H3 Hashy Hour",false),
		SOUTH_SOUND("South Sound",4,"SSH3",true),
		PUGET_SOUND("Puget Sound",5,"PSH3",true),
		NO_BALLS("No Balls H3",6,"NBH3",true),
		RAIN_CITY("Rain City H3",7,"RCH3",true),
		HSWTF("Holy Shit WTF H3",8,"HSWTF",true),
		BASH("Bike Hash",9,"SH2B",true),
		RENTON_HAPPY_HOUR("Renton Happy Hour",10,"Thursday Renton Happy Hour",false),
		FULL_MOON("Full Moon",11,"FMH3",true),
		OTHER("",0,"",false);
		
		public static Type findType(int code) {
			for(Type type : Type.class.getEnumConstants()) {
				if(type.code == code) return type;
			}
			return Type.OTHER;
		}
		private final String name;
		private final int code;
		private final String identifier;
		private final boolean hasHare;
		
		private Type(String name,int code,String identifier, boolean hasHare) {
			this.name = name;
			this.code = code;
			this.identifier = identifier;
			this.hasHare = hasHare;
		}
		public String getName() {
			return name;
		}
		public int getCode() {
			return code;
		}
		public String getIdentifier() {
			return identifier;
		}
		public boolean hasHare() {
			return hasHare;
		}

	}

		
	public HashEvent(Builder builder) {
		this.id = builder.id;
		this.date = builder.date;
		this.time = builder.time;
		this.type = builder.type;
		this.eventNumber = builder.eventNumber;
		this.hare = builder.hare;
		this.eventName = builder.eventName;
		this.description = builder.description;
		this.address = builder.address;
		this.mapLink = builder.mapLink;
		this.locked = builder.locked;
	}

	public String getFormattedLine() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getDay() + "\t");
		builder.append(this.getTime().toString("h:mm a") + " CST\t");
		builder.append(this.getType() + "\t");
		builder.append(this.getEventNumber() + "\t");
		builder.append(this.getHare() + "\t");
		builder.append(this.getEventName() + "\t");
		builder.append(this.getDescription() + "\t");
		builder.append(this.getAddress() + "\t");
		builder.append(this.getMapLink() + "\n");
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashEvent other = (HashEvent) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		if (hare == null) {
			if (other.hare != null)
				return false;
		} else if (!hare.equals(other.hare))
			return false;
		if (time != other.time)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result + ((hare == null) ? 0 : hare.hashCode());
		result = prime * result + time.getMillisOfDay();
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder(this.eventName);
		if(string.length() > 0) string.append("\n");
		return string.toString() + this.hare;
	}
	@Override
	public int compareTo(Object o) {
		if(o instanceof HashEvent) {
			HashEvent event = (HashEvent) o;
			if(event.getDay() == this.getDay()) {
				return this.getTime().getMillisOfDay() - event.getTime().getMillisOfDay();
			}
			return this.getDay() - event.getDay();
		}
		return -1;		
	}
	
	public DateTime getDate() {
		return date;
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

	public String getId() {
		return id;
	}

	public LocalTime getTime() {
		return time;
	}
	public boolean hasTime() {
		if(time.getMillisOfDay() > 0 && time.getHourOfDay() <= 24) return true;
		return false;
	}
	
	public Type getType() {
		return type;
	}

	public int getEventNumber() {
		return eventNumber;
	}
	public boolean hasEventNumber() {
		if(eventNumber > 0) return true;
		return false;
	}

	public String getHare() {
		return hare;
	}

	public String getEventName() {
		if(eventName.length() > 0) return eventName;
		return this.getTitle();
	}

	public String getDescription() {
		return description;
	}

	public String getAddress() {
		return address;
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
		builder.append(this.getType().getName());
		if(eventNumber > 0) builder.append(" #" + Integer.toString(eventNumber));
		return builder.toString();
	}

	public CharSequence getDateTime() {
		StringBuilder builder = new StringBuilder();
		builder.append(date.toString("MMMM d"));
		if(date.getHourOfDay() > 0) builder.append(", " + date.toString("h:mm a"));
		return builder;
	}

	public boolean isLocked() {
		return locked;
	}
	
	public boolean hasDetails() {
		return !description.isEmpty() || mapLink != null;
	}

	public static class Builder {
		private String id = null;
		private DateTime date = null;
		private LocalTime time = null;
		private Type type = Type.OTHER;
		private int eventNumber = 0;
		private String hare = "";
		private String eventName = "";
		private String description = "";
		private String address = "";
		private URL mapLink = null;
		private boolean locked = false;
		
		public HashEvent build() {
			return new HashEvent(this);
		}
		
		public String getId() {
			return id;
		}

		public void setId(String i) {
			this.id = i;
		}

		public DateTime getDate() {
			return date;
		}
		public void setDate(DateTime datetime) {
			this.time = datetime.toLocalTime();
			this.date = datetime;
		}
		public LocalTime getTime() {
			return time;
		}
		public void setTime(LocalTime time) {
			this.time = time;
		}
		public Type getType() {
			return type;
		}
		public void setType(Type type) {
			this.type = type;
		}
		public int getEventNumber() {
			return eventNumber;
		}
		public void setEventNumber(int eventNumber) {
			this.eventNumber = eventNumber;
		}
		public String getHare() {
			return hare;
		}
		public void setHare(String hare) {
			this.hare = hare;
		}
		public String getEventName() {
			return eventName;
		}
		public void setEventName(String eventName) {
			this.eventName = eventName;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public URL getMapLink() {
			return mapLink;
		}
		public void setMapLinkBuild(URL mapLink) {
			this.mapLink = mapLink;
		}

		public boolean isLocked() {
			return locked;
		}

		public void setLocked(boolean locked) {
			this.locked = locked;
		}

	}
}
