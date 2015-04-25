package com.suvadip.rest.pojo;

import java.io.Serializable;

public class Person implements Serializable{

    private static final long SerialVersionUID=2L;
    private String name;
    private int type;
    private int subtype;
    private double costMe;
    private double latitude;
    private double longitude;
    private double rating;
    
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public double getCostMe() {
		return costMe;
	}
	public void setCostMe(double costMe) {
		this.costMe = costMe;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
    
	public Person(){
		name = "default";
		costMe = 500;
	    type = 0 ;
	    subtype = 0 ;
	    latitude = 0.0 ;
	    longitude = 0.0 ;
	    rating = 3;
	}
	//PLease multiple latitude and longitude by 10,000 before sending it 
    public Person(String name, String type, String subtype, String latitude, String longitude, String costMe, String rating){
    	this.name = name;
	    this.type = Integer.parseInt(type) ;
	    this.subtype = Integer.parseInt(subtype) ;
	    this.latitude  = Double.parseDouble(latitude);
	    this.longitude  = Double.parseDouble(longitude);
	    this.costMe  = Double.parseDouble(costMe);
	    this.rating = Double.parseDouble(rating);


    }
}
