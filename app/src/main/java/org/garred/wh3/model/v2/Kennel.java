package org.garred.wh3.model.v2;


public enum Kennel {

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
	SEAMON("Seattle Monday Hash",12,"SeaMon",true),
	OTHER("",0,"",false);

	public static Kennel findType(int code) {
		for(final Kennel type : Kennel.class.getEnumConstants()) {
			if(type.code == code) return type;
		}
		return Kennel.OTHER;
	}
	private final String name;
	private final int code;
	private final String identifier;
	private final boolean hasHare;

	private Kennel(String name,int code,String identifier, boolean hasHare) {
		this.name = name;
		this.code = code;
		this.identifier = identifier;
		this.hasHare = hasHare;
	}
	public String getName() {
		return this.name;
	}
	public int getCode() {
		return this.code;
	}
	public String getIdentifier() {
		return this.identifier;
	}
	public boolean hasHare() {
		return this.hasHare;
	}

	public static Kennel fromCode(String code) {
		for(Kennel kennel : Kennel.values()) {
			if(kennel.name().equals(code)) {
				return kennel;
			}
		}
		return OTHER;
	}
}
