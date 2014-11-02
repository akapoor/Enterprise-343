package dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.sql.DataSource;

import objects.Event;

public interface EventDAO {

	/** 1
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	/** 2
	 * This is the method to be used to create a record in the event table.
	 */
	public void create(int eventId, String eventName, Date date,
			Time start, Time end, String location, String description,
			String attendees, int epId);

	/** 3
	 * This is the method to be used to list down a record from the event
	 * table corresponding to a passed event name.
	 */
	public Event getEvent(String eventName);

	/** 4
	 * This is the method to be used to list down all the records from the
	 * event table.
	 */
	public List<Event> listEvents();

	/** 5
	 * This is the method to be used to delete a record from the event table
	 * corresponding to a event name
	 * 
	 * YOU SHOULD NOT USE THIS
	 */
	public void delete(String eventName);

	/** 6 this is used to delete a specific event, and it should always be
	 * unique because there shouldnt be the same name event on the same day
	 * @param eventName
	 * @param date
	 */
	public void deleteEvent(String eventName, Date date);
	
	
	public Event getEvent(String eName, Date eDate);
	
	//I think this just needs to check if the string has an @ symbol, then 
	//figure out if it's an email or name from that
	public List<Event> getEvents(String eName);
	
	public List<Event> getEvents(Date eDate);
	
	//public List<Event> getEvents(String email);
	
	public List<Event> getEvents(int year);
	
	public Event getEvent(Integer id);
	public void delete(Integer id);
	
	
}