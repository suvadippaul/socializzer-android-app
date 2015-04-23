package com.suvadip.rest.pojo;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Event implements Serializable{

    private static final long SerialVersionUID=1L;
    private int eventID;
    private float tempSatisfactionIndex;
    private int picture;
    private String name;
    private String description;
    private int subtype;
    private float latitude;
    private float longitude;

    
    public int getEventID() {
		return eventID;
	}


	public void setEventID(int eventID) {
		this.eventID = eventID;
	}


	public float getTempSatisfactionIndex() {
		return tempSatisfactionIndex;
	}


	public void setTempSatisfactionIndex(float tempSatisfactionIndex) {
		this.tempSatisfactionIndex = tempSatisfactionIndex;
	}


	public int getPicture() {
		return picture;
	}


	public void setPicture(int picture) {
		this.picture = picture;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getSubtype() {
		return subtype;
	}


	public void setSubtype(int subtype) {
		this.subtype = subtype;
	}


	public float getLatitude() {
		return latitude;
	}


	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}


	public float getLongitude() {
		return longitude;
	}


	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}


	public static long getSerialversionuid() {
		return SerialVersionUID;
	}


	public Event(){
		eventID = 0;
	    picture = 0;
		tempSatisfactionIndex = 0;
	    name = "Default";
	    description = "Default";
	    subtype=0;
	    latitude=0;
	    longitude=0;
	}
	
	public Event( int eventID, int picture, String name, int latitude, int longitude){
		this.eventID = eventID;
		this.picture = picture;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String toString()
    {
        return (name + " Description: " + description);
    }

    
}