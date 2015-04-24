package com.suvadip.rest.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import com.suvadip.rest.pojo.*;

public class EventFinder {

	List<Event> lst;
	int size;
	Person person;
	private final int NUMBEROFEVENTSSENT = 5;

	EventFinder(Person person) {
		lst = new ArrayList<Event>();
		size = 0;
		this.person = person;
	}

	public List<Event> getMeList() {
		LoadList(); // assume we read from file and fill up the satisfaction
					// Index for each event
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
		lst1.add(new Event(1, 3, "Asiana Me Sexy", 1.11, 3.57, 2.14));
		lst1.add(new Event(1, 3, "Hoopla Me Sexy", 1.11, 3.57, 1.47));
		lst1.add(new Event(1, 3, "Maria Me Sexy", 1.11, 3.57, 1.56));
		lst1.add(new Event(1, 3, "SexMachine Me Sexy", 1.11, 3.57, 1.9));
		lst1.add(new Event(1, 3, "Lala Me Sexy", 1.11, 3.57, 3.56));
		lst1.add(new Event(1, 3, "Tipsy Me Sexy", 1.11, 3.57, 1.32));
		lst1.add(new Event(1, 3, "Shreyash Harish Me Black", 1.11, 3.57, 0));
		
		try {
			FileOutputStream fos = new FileOutputStream("database.ser");
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
		
		try {
			FileInputStream fis = new FileInputStream("database.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			List<Event> list = new ArrayList<Event>() ;
			lst = (List<Event>) ois.readObject();
			
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
		 
		
	}

}
