package com.suvadip.rest.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;

import com.suvadip.rest.pojo.*;

public class EventFinder {

	List<Event> lst;
	int size;
	Person person;
	private final int NUMBEROFEVENTSSENT = 15;
	int[][] typeConnectivity = { { 2, 1, 0, 1, 0 }, { 1, 2, 1, 2, 0 },
			{ 0, 1, 2, 0, 1 }, { 1, 2, 0, 2, 1 }, { 0, 0, 1, 1, 2 } };

	int[][][] subtype = { { { 2, 1, 0 }, { 1, 2, 1 }, { 0, 1, 2 } },
			{ { 2, 0, 0 }, { 0, 2, 1 }, { 0, 1, 2 } },
			{ { 2, 1, 1 }, { 1, 2, 1 }, { 1, 1, 2 } },
			{ { 2, 1, 0 }, { 1, 2, 0 }, { 0, 0, 2 } },
			{ { 2, 1, 1 }, { 1, 2, 1 }, { 1, 1, 2 } } };
	
	

	EventFinder(Person person) {
		lst = new ArrayList<Event>();
		size = 0;
		this.person = person;
		System.out.println(person.getCostMe());
	}

	public List<Event> getMeList() {
		LoadList(); // assume we read from file and fill up the satisfaction
					// Index for each event
		if ( size < NUMBEROFEVENTSSENT)
			return lst;
		PriorityQueue<Event> pq = getTop();
		Stack<Event> newList = new Stack<Event>();
		for (int i = 0; i < NUMBEROFEVENTSSENT; i++) {
			newList.push(pq.poll());
		}
		List<Event> newFinalList = new ArrayList<Event>();
		for (int i = 0; i < NUMBEROFEVENTSSENT; i++) {
			newFinalList.add(newList.pop());
		}
		return newFinalList;
	}

	private PriorityQueue<Event> getTop() {
		PriorityQueue<Event> pq = new PriorityQueue<Event>();
		for (int i = 0; i < NUMBEROFEVENTSSENT; i++) {
			pq.add(lst.get(i));
		}

		for (int i = NUMBEROFEVENTSSENT; i < size; i++) {
			if (lst.get(i).getTempSatisfactionIndex() > pq.peek()
					.getTempSatisfactionIndex()) {
				pq.poll();
				pq.add(lst.get(i));
			}
		}

		return pq;
	}

	
	private void LoadList() {
		// assume we read from file and fill up the satisfaction Index for each
		// event
		// and we fill the size too but as of now me just load it with junk
		// values
		List<Event> lst1 = new ArrayList<Event>();
		/*
		String[] sampleNames = { "Chinese" , "Dragon" , "Restaurant" , "Oriental" , "Harbour" , "Chocolate" , "Fantasy" , "Ramu" , "Pearl" , "Faaso's" };
		String[] sampleDescription = { "So beautiful" , "Exuisite" , "Brilliant" , "Interestion" , "Meh" , "Nice" , "5-star" , "WOW!" , "Really Good" , "Me Likey" };
		Random rg = new Random(System.currentTimeMillis());
		
		for (int i = 0 ; i < 50000 ; i++){
			int name1 = rg.nextInt(10);
			int name2 = rg.nextInt(10);
			String name = sampleNames[name1] + " " + sampleNames[name2];
			String description = sampleDescription[name2] + ", " + sampleDescription[name1];
			int picture = rg.nextInt(15);
			int type = rg.nextInt(5);
			int subtype = rg.nextInt(3);
			double latitude = rg.nextGaussian();
			double longitude = rg.nextGaussian();
			double averageCostFor2 = rg.nextInt(1000) ;
			double rating = rg.nextInt(6);
			rating = rating + rg.nextGaussian();
			lst1.add(new Event(i, picture,type,subtype,name, description, latitude, longitude,averageCostFor2,rating));
		}
		try {
			FileOutputStream fos = new FileOutputStream("/home/suvadip/workspace_new/SocializzerJSONResponse/database.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//used the required stream to write the object list 
			
				oos.writeObject(lst1);
				
			
			oos.flush();
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		try {
			FileInputStream fis = new FileInputStream("/home/suvadip/workspace_new/SocializzerJSONResponse/database.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			List<Event> readObject = (List<Event>) ois.readObject();
			lst1 = readObject;
			
			ois.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FilterMe(lst1);
		for ( int i = 0 ; i < size ; i++){
			lst.get(i).getSatisfied(person);
		}
			
		
		 
		
	}

	private void FilterMe(List<Event> lst1) {
		
		Iterator<Event> itr = lst1.iterator();
		
		while ( itr.hasNext()){
			Event tempEvent = itr.next();
			if (Math.abs(person.getCostMe() - tempEvent.getAverageCostFor2()) > 200 )
				continue;
			else 
				if (Math.abs(person.getRating() - tempEvent.getRating()) >= 2)
				continue;
			else if (Math.abs(person.getLatitude() - tempEvent.getLatitude()) >= 0.5)
				continue;
			else if (Math.abs(person.getLongitude() - tempEvent.getLongitude()) >= 0.5)
				continue;
			else if (typeConnectivity[person.getType()][tempEvent.getType()] == 0)
				continue;
				
			 //Compare time and eliminate 
			size++;
			lst.add(tempEvent);
		}
		System.out.println(size);
		
	}

}
