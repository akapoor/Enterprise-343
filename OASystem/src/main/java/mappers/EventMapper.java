package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import objects.Event;

import org.springframework.jdbc.core.RowMapper;

public class EventMapper implements RowMapper<Event> {
	public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
		Event event = new Event();
		event.setEventId(rs.getInt("eventId"));
		event.setAttendees(rs.getString("attendees"));
		event.setDate(rs.getDate("eDate"));
		event.setDescription(rs.getString("description"));
		event.setStart(rs.getTime("startTime"));
		event.setEnd(rs.getTime("endTime"));
		event.setEpId(rs.getInt("epId"));
		event.setEventName(rs.getString("eName"));
		event.setLocation(rs.getString("location"));
		
		return event;
	}
}
