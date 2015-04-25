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
	private int type;
	private int subtype;
	private double latitude;
	private double longitude;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
		type = 0;

	}

	public void getSatisfied(Person person) {

		int[][] typeConnectivity = { { 2, 1, 0, 1, 0 }, { 1, 2, 1, 2, 0 },
				{ 0, 1, 2, 0, 1 }, { 1, 2, 0, 2, 1 }, { 0, 0, 1, 1, 2 } };

		int[][][] subtype1 = { { { 2, 1, 0 }, { 1, 2, 1 }, { 0, 1, 2 } },
				{ { 2, 0, 0 }, { 0, 2, 1 }, { 0, 1, 2 } },
				{ { 2, 1, 1 }, { 1, 2, 1 }, { 1, 1, 2 } },
				{ { 2, 1, 0 }, { 1, 2, 0 }, { 0, 0, 2 } },
				{ { 2, 1, 1 }, { 1, 2, 1 }, { 1, 1, 2 } } };
		double lat = latitude - person.getLatitude();

		double lon = longitude - person.getLongitude();

		double dist = lat * lat + lon * lon;
		dist = 1 / dist;
		if (dist > 25)
			dist = 25;
		double cost = person.getCostMe() - averageCostFor2;
		if (cost > 0)
			cost = cost / 2;
		else
			cost = -cost;
		tempSatisfactionIndex = dist + 9 / cost + rating * 7.7;
		tempSatisfactionIndex = tempSatisfactionIndex + 2*typeConnectivity[person.getType()][type];
		if ( person.getType() == type)
			tempSatisfactionIndex = tempSatisfactionIndex + subtype1[type][person.getSubtype()][subtype];


	}

	public Event(int eventID, int picture, int type, int subtype, String name,
			String Description, double latitude, double longitude,
			double averageCostFor2, double rating) {
		this.eventID = eventID;
		this.picture = picture;
		this.type = type;
		this.subtype = subtype;
		this.name = name;
		this.description = Description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.averageCostFor2 = averageCostFor2;
		this.rating = rating;
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