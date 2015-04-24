package com.suvadip.rest.pojo;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Event implements Serializable, Comparable<Event> {

	private static final long SerialVersionUID = 1L;
	private int eventID;
	private double tempSatisfactionIndex;
	private double rating;
	private double averageCostFor2;
	private int picture;
	private String name;
	private String description;
	private int subtype;
	private double latitude;
	private double longitude;
	private Time startTime;
	private Time finishTIme;
	private Date date;

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getFinishTIme() {
		return finishTIme;
	}

	public void setFinishTIme(Time finishTIme) {
		this.finishTIme = finishTIme;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getAverageCostFor2() {
		return averageCostFor2;
	}

	public void setAverageCostFor2(double averageCostFor2) {
		this.averageCostFor2 = averageCostFor2;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public double getTempSatisfactionIndex() {
		return tempSatisfactionIndex;
	}

	public void setTempSatisfactionIndex(double tempSatisfactionIndex) {
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public static long getSerialversionuid() {
		return SerialVersionUID;
	}

	public Event() {
		rating = 0;
		averageCostFor2 = 0;
		eventID = 0;
		picture = 0;
		tempSatisfactionIndex = 0;
		name = "Default";
		description = "Default";
		subtype = 0;
		latitude = 0;
		longitude = 0;
		date = new Date();
		startTime = new Time(2, 2, 2);
		finishTIme = new Time(2, 2, 2);

	}

	public void getSatisfied( Person person){
		
		double lat = latitude-person.getLatitude() ;
		
		double lon = longitude-person.getLongitude() ;
		
		double dist = lat*lat + lon*lon;
		double cost = Math.abs(person.getCostMe() - averageCostFor2);
		tempSatisfactionIndex = 1/dist + 1/cost + rating*100;
		
	}
	
	public Event(int eventID, int picture, String name, double latitude,
			double longitude, double tempSatisfactionIndex) {
		this.eventID = eventID;
		this.picture = picture;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.tempSatisfactionIndex = tempSatisfactionIndex;
	}

	public String toString() {
		return (name + " Description: " + description);
	}

	@Override
	public int compareTo(Event o) {
		if (this.tempSatisfactionIndex > o.tempSatisfactionIndex)
			return 1;
		else if (this.tempSatisfactionIndex < o.tempSatisfactionIndex)
			return -1;
		else
			return 0;
	}

}