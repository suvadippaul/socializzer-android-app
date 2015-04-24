package com.suvadip.rest.resource;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.suvadip.rest.pojo.Event;
import com.suvadip.rest.pojo.Person;
 
@Path("/event")
public class EventResource {
 
	    private final static String NAME = "name";
	    private final static String TYPE = "type" ;
	    private final static String SUBTYPE = "subtype";
	    private final static String LATITUDE = "latitude";
	    private final static String LONGITUDE = "longitude";
	    private final static String COST_ME = "costMe";

	    private Event event = new Event();
     
    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;
 
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    Request request;
     
    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
        return " Mommy got ass like a donkay in a monkay ";
    }
 
    @GET
    @Path("/sample")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getSampleEvents() {
         
        System.out.println("Returning sample event: " + event.getEventID() + " " + event.getName());
        String name = "Sample";
        String type = "0";
        String subtype = "0";
	    String latitude = "0";
	    String longitude = "0";
	    String costMe = "0";
	    Person person = new Person(name, type, subtype, latitude, longitude, costMe);
        EventFinder newFind = new EventFinder(person);
        List<Event> lst = newFind.getMeList();
        return lst;
    }
         
    // Use data from the client source to create a new Person object, returned in JSON format.  
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> postEvent( MultivaluedMap<String, String> personParams) {
         
        String name = personParams.getFirst(NAME);
        String type = personParams.getFirst(SUBTYPE);
        String subtype = personParams.getFirst(SUBTYPE);
	    String latitude = personParams.getFirst(LATITUDE);
	    String longitude = personParams.getFirst(LONGITUDE); 
	    String costMe = personParams.getFirst(COST_ME); 

	    Person person = new Person(name, type, subtype, latitude, longitude, costMe);
        System.out.println("Storing posted: " + name);
        EventFinder newFind = new EventFinder(person);
        List<Event> lst = newFind.getMeList();
        return lst;
                         
    }
   
}
