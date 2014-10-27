package jdbcTemplates;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.sql.DataSource;

import mappers.EventMapper;
import objects.Event;

import org.springframework.jdbc.core.JdbcTemplate;

import dao.EventDAO;

public class EventJDBCTemplate implements EventDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	public void create(int eventId, String eventName, Date date,
			Time start, Time end, String location, String description,
			String attendees, int epId) {
		String SQL = "insert into theEvents (eventName, date, start, end, location, description, attendees, epId) values (?,?,?,?,?,?,?,?)";

		jdbcTemplateObject.update(SQL, eventId, eventName, date,
				start, end, location, description,
				attendees, epId);
		System.out.println("Created Event Name = " + eventName);
		return;
	}
	
	public Event getEvent(Integer id) {
		String SQL = "select * from theEvents where eventId = ?";
		Event event = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new EventMapper());
		return event;
	}
	
	public List<Event> listEvents() {
		String SQL = "select * from theEvents";
		List<Event> events = jdbcTemplateObject.query(SQL,
				new EventMapper());
		return events;
	}

	public void delete(Integer id) {
		String SQL = "delete from theEvents where eventId = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted Record with ID = " + id);
		return;
	}
	
	public Event getEvent(String eventName) {
		String SQL = "select * from theEvents where eName = ?";
		Event event = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { eventName }, new EventMapper());
		return event;
	}

	public void delete(String eventName) {
		String SQL = "delete from events where eName = ?";
		jdbcTemplateObject.update(SQL, eventName);
		System.out.println("Deleted Record with eName = " + eventName);
		return;
	}
	
	// public void update(Integer id, Integer age){
	// String SQL = "update Student set age = ? where id = ?";
	// jdbcTemplateObject.update(SQL, age, id);
	// System.out.println("Updated Record with ID = " + id );
	// return;
	// }
}
