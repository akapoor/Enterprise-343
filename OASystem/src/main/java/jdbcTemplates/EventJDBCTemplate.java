package jdbcTemplates;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;
import javax.validation.Valid;

import mappers.EventMapper;
import objects.Event;
import objects.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dao.EventDAO;

public class EventJDBCTemplate implements EventDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	/*
	 * 1 (non-Javadoc)
	 * 
	 * @see dao.EventDAO#setDataSource(javax.sql.DataSource)
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	/*
	 * 2 (non-Javadoc)
	 * 
	 * @see dao.EventDAO#create(int, java.lang.String, java.sql.Date,
	 * java.sql.Time, java.sql.Time, java.lang.String, java.lang.String,
	 * java.lang.String, int)
	 */
	public void create(int eventId, String eventName, Date date, Time start,
			Time end, String location, String description, String attendees,
			int epId) {
		String SQL = "insert into theEvents (eventName, date, start, end, location, description, attendees, epId) values (?,?,?,?,?,?,?,?)";

		jdbcTemplateObject.update(SQL, eventId, eventName, date, start, end,
				location, description, attendees, epId);
		System.out.println("Created Event Name = " + eventName);
		return;
	}

	/*
	 * 3 (non-Javadoc)
	 * 
	 * @see dao.EventDAO#getEvent(java.lang.String)
	 */
	public Event getEvent(String eventName) {
		String SQL = "select * from theEvents where eName = ?";
		Event event = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { eventName }, new EventMapper());
		return event;
	}
	
	/*
	 * 4 (non-Javadoc)
	 * 
	 * @see dao.EventDAO#listEvents()
	 */
	public List<Event> listEvents() {
		String SQL = "select * from theEvents";
		List<Event> events = jdbcTemplateObject.query(SQL, new EventMapper());
		return events;
	}

	/*
	 * 5 (non-Javadoc)
	 * 
	 * @see dao.EventDAO#delete(java.lang.String)
	 */
	public void delete(String eventName) {
		String SQL = "delete from theEvents where eName = ?";
		jdbcTemplateObject.update(SQL, eventName);
		System.out.println("Deleted Record with eName = " + eventName);
		return;
	}

	/*
	 * 6 (non-Javadoc)
	 * 
	 * @see dao.EventDAO#deleteEvent(java.lang.String, java.sql.Date)
	 */
	public void deleteEvent(String eventName, Date date) {
		String SQL = "delete from theEvents where eName = ? and eDate = ?";
		jdbcTemplateObject.update(SQL, eventName, date);
		return;
	}

	public Event getEvent(Integer id) {
		String SQL = "select * from theEvents where eventId = ?";
		Event event = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new EventMapper());
		return event;
	}

	public void delete(Integer id) {
		String SQL = "delete from theEvents where eventId = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted Record with ID = " + id);
		return;
	}

	@Override
	public Event getEvent(String eName, Date eDate) {
		String SQL = "select * from theEvents where eName = ? and eDate = ?";
		Event event = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { /* not sure if i need anything here */},
				new EventMapper());
		return event;
	}

	@Override
	public List<Event> getEvents(String eName) {
		String SQL = "select * from theEvents where eName = ?";
		List<Event> events = jdbcTemplateObject.query(SQL, new EventMapper());
		return events;
	}
	
	public List<Event> getEmailEvents(String email) {
		//find from email person id in user
		//find event id in eventpal
		//select events of those id
		String SQL = "SELECT * FROM theEvents INNER JOIN eventPal ON theEvents.eventId = eventPal.eventId INNER JOIN user ON eventPal.personId = user.personId WHERE user.email = ?";
		List<Event> events = jdbcTemplateObject.query(SQL, new EventMapper());
		return events;
	}

	@Override
	public List<Event> getEvents(Date eDate) {
		String SQL = "select * from theEvents where eDate = ?";
		List<Event> events = jdbcTemplateObject.query(SQL, new EventMapper());
		return events;
	}

	@Override
	public List<Event> getEvents(int year) {
		String SQL = "select * from theEvents where YEAR(FROM_UNIXTIME(eDate)) = ?";
		List<Event> events = jdbcTemplateObject.query(SQL, new EventMapper());
		return events;
	}

}
