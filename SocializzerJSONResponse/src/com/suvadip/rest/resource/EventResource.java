package com.suvadip.rest.resource;


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
 
@Path("/event")
public class EventResource {
 
	 	private final static int PICTURE = 0;
	    private final static String NAME = "name";
	    private final static String DESCRIPTION = "description" ;
	    private final static int SUBTYPE = 0;
	    private final static float LATITUDE = 0;
	    private final static float LONGITUDE = 0;
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
    @Path("sample")
    @Produces(MediaType.APPLICATION_JSON)
    public Event getSampleEvent() {
         
        System.out.println("Returning sample event: " + event.getEventID() + " " + event.getName());
         
        return event;
    }
         
    // Use data from the client source to create a new Person object, returned in JSON format.  
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Event postEvent( MultivaluedMap<String, String> personParams) {
         
        String name = personParams.getFirst(NAME);
        String description = personParams.getFirst(DESCRIPTION);
         
        System.out.println("Storing posted " + name + " " + description);
         
        event.setName(name);
        event.setDescription(description);
         
        System.out.println("Event info: " + event.getName() + " " + event.getDescription());
        event.setName("SUpaerStar");
        return event;
                         
    }
}
