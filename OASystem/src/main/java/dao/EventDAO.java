package dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.sql.DataSource;

import objects.Event;

public interface EventDAO {

	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to create a record in the event table.
	 */
	public void create(int eventId, String eventName, Date date,
			Time start, Time end, String location, String description,
			String attendees, int epId);

	/**
	 * This is the method to be used to list down a record from the event
	 * table corresponding to a passed event name.
	 */
	public Event getEvent(String eventName);

	/**
	 * This is the method to be used to list down all the records from the
	 * event table.
	 */
	public List<Event> listEvents();

	/**
	 * This is the method to be used to delete a record from the event table
	 * corresponding to a event name
	 */
	public void delete(String eventName);

	/**
	 * This is the method to be used to update a record into the Student table.
	 */
	//TODO thing above this comment
}