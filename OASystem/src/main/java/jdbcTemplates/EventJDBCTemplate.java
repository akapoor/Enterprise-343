package jdbcTemplates;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
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

public class EventJDBCTemplate implements dao.EventDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private EventMapper evm = new EventMapper();

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
//		String SQL = "insert into theEvents (eventName, date, start, end, location, description, attendees, epId) values (?,?,?,?,?,?,?,?)";
//
//		jdbcTemplateObject.update(SQL, eventId, eventName, date, start, end,
//				location, description, attendees, epId);
//		return;
		
		try {
			String selectSQL = "insert into theEvents (eventName, date, start, end, location, description, attendees, epId) values (?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, eventName);
			preparedStatement.setDate(2, date);
			preparedStatement.setTime(3, start);
			preparedStatement.setTime(4, end);
			preparedStatement.setString(5, location);
			preparedStatement.setString(6, description);
			preparedStatement.setString(7, attendees);
			preparedStatement.setInt(8, epId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 3 (non-Javadoc)
	 * 
	 * @see dao.EventDAO#getEvent(java.lang.String)
	 */
	public Event getEvent(String eventName) {
//		String SQL = "select * from theEvents where eName = ?";
//		Event event = jdbcTemplateObject.queryForObject(SQL,
//				new Object[] { eventName }, new EventMapper());
//		return event;
		
		List<Event> ret = new ArrayList<Event>();
		try {
			String selectSQL = "select * from theEvents where eName = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, eventName);
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(evm.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret.get(0);
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
//		String SQL = "delete from theEvents where eName = ?";
//		jdbcTemplateObject.update(SQL, eventName);
//		System.out.println("Deleted Record with eName = " + eventName);
//		return;
		try {
			String selectSQL = "delete from theEvents where eName = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, eventName);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 6 (non-Javadoc)
	 * 
	 * @see dao.EventDAO#deleteEvent(java.lang.String, java.sql.Date)
	 */
	public void deleteEvent(String eventName, Date date) {
//		String SQL = "delete from theEvents where eName = ? and eDate = ?";
//		jdbcTemplateObject.update(SQL, eventName, date);
//		return;
		try {
			String selectSQL = "delete from theEvents where eName = ? and eDate = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, eventName);
			preparedStatement.setDate(2, date);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Event getEvent(Integer id) {
//		String SQL = "select * from theEvents where eventId = ?";
//		Event event = jdbcTemplateObject.queryForObject(SQL,
//				new Object[] { id }, new EventMapper());
//		return event;
		List<Event> ret = new ArrayList<Event>();
		try {
			String selectSQL = "select * from theEvents where eventId = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(evm.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret.get(0);
	}

	public void delete(Integer id) {
//		String SQL = "delete from theEvents where eventId = ?";
//		jdbcTemplateObject.update(SQL, id);
//		return;
		try {
			String selectSQL = "delete from theEvents where eventId = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Event getEvent(String eName, Date eDate) {
//		String SQL = "select * from theEvents where eName = ? and eDate = ?";
//		Event event = jdbcTemplateObject.queryForObject(SQL,
//				new Object[] { /* not sure if i need anything here */},
//				new EventMapper());
//		return event;
		List<Event> ret = new ArrayList<Event>();
		try {
			String selectSQL = "select * from theEvents where eName = ? and eDate = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, eName);
			preparedStatement.setDate(1, eDate);
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(evm.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret.get(0);	
		
	}

	@Override
	public List<Event> getEvents(String eName) {
//		String SQL = "select * from theEvents where eName = ?";
//		List<Event> events = jdbcTemplateObject.query(SQL, new EventMapper());
//		return events;
		List<Event> ret = new ArrayList<Event>();
		try {
			String selectSQL = "select * from theEvents where eName = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, eName);
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(evm.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;	
	}

	public List<Event> getEmailEvents(String email) throws SQLException {
		// String selectSQL =
		// "SELECT USER_ID, USERNAME FROM DBUSER WHERE USER_ID = ?";
		// PreparedStatement preparedStatement =
		// dbConnection.prepareStatement(selectSQL);
		// preparedStatement.setInt(1, 1001);
		// ResultSet rs = preparedStatement.executeQuery(selectSQL );
		// resultSet = statement.executeQuery();
		// // simple JDBC code to run SQL query and populate resultSet - END
		// List<SamplePojo> pojoList =
		// resultSetMapper.mapRersultSetToObject(resultSet, SamplePojo.class);
		// find from email person id in user
		// find event id in eventpal
		// select events of those id
		List<Event> ret = new ArrayList<Event>();
		String selectSQL = "SELECT * FROM theEvents INNER JOIN eventPal ON theEvents.eventId = eventPal.eventId INNER JOIN user ON eventPal.personId = user.personId WHERE user.email=?";
		PreparedStatement preparedStatement = this.dataSource.getConnection()
				.prepareStatement(selectSQL);
		preparedStatement.setString(1, email);
		ResultSet rs = preparedStatement.executeQuery();
		int i = 0;
		while (rs.next()) {
			ret.add(evm.mapRow(rs, i++));
		}
		return ret;
	}

	@Override
	public List<Event> getEvents(Date eDate) {
//		String SQL = "select * from theEvents where eDate = ?";
//		List<Event> events = jdbcTemplateObject.query(SQL, new EventMapper());
//		return events;
		List<Event> ret = new ArrayList<Event>();
		try {
			String selectSQL = "select * from theEvents where eDate = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setDate(1, eDate);
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(evm.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public List<Event> getEvents(int year) {
//		String SQL = "select * from theEvents where YEAR(FROM_UNIXTIME(eDate)) = ?";
//		List<Event> events = jdbcTemplateObject.query(SQL, new EventMapper());
//		return events;
		List<Event> ret = new ArrayList<Event>();
		try {
			String selectSQL = "select * from theEvents where YEAR(FROM_UNIXTIME(eDate)) = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, year);
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(evm.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}

	@Override
	public void createEventConnection(String personId, String eventId) {
//		String SQL = "insert into eventPal (eventId, personId) values (?, ?)";
//		jdbcTemplateObject.update(SQL, eventId, personId);
		try {
			String selectSQL = "insert into eventPal (eventId, personId) values (?, ?)";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, personId);
			preparedStatement.setString(2, eventId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteEventConnection(String personId, String eventId) {
//		String SQL = "delete from eventPal where eventId=? AND personId=?";
//		jdbcTemplateObject.update(SQL, eventId, personId);
		try {
			String selectSQL = "delete from eventPal where personId=? AND eventId=?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, personId);
			preparedStatement.setString(2, eventId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
