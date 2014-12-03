package controllers;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import jdbcTemplates.EventJDBCTemplate;
import jdbcTemplates.UserJDBCTemplate;
import objects.Event;
import objects.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

	@Autowired
	private ApplicationContext context;
	@Autowired
	private EventJDBCTemplate jdbcCon;
	@Autowired
	private UserJDBCTemplate jdbcUser;
	@Autowired
	private LoginController loginCon;

	public EventJDBCTemplate getJdbcCon() {
		if (this.jdbcCon == null) {
			context.getBeanDefinitionNames();
			this.jdbcCon = (EventJDBCTemplate) context
					.getBean("eventJDBCTemplate");

		}
		return jdbcCon;
	}

	public UserJDBCTemplate getJdbcUser() {
		if (this.jdbcUser == null) {
			this.jdbcUser = (UserJDBCTemplate) context
					.getBean("userJDBCTemplate");
		}
		return jdbcUser;
	}

	//get a specific event. pass event name and date in url
	@RequestMapping("/events/specific")
	public Event[] getSpecificEvent(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "date", required = true) Date date) {
		Event[] ret = new Event[1];
		ret[0] = getJdbcCon().getEvent(name, date);
		return ret;
	}

	//get an event given an event name. pass name in url
	@RequestMapping("/events/name")
	public Event[] getEventsByName(
			@RequestParam(value = "name", required = true) String name) {
		List<Event> temp = getJdbcCon().getEvents(name);
		return temp.toArray(new Event[temp.size()]);
	}

	//get event based on a specific date. pass date in url
	@RequestMapping("/events/date")
	public Event[] getEventsByDate(
			@RequestParam(value = "date", required = true) Date date) {
		List<Event> temp = getJdbcCon().getEvents(date);
		return temp.toArray(new Event[temp.size()]);
	}

	//get all events for current logged in user. pass email in url
	// TODO finish up returning all events for logged in user
	@RequestMapping("/events/email")
	public Event[] getEventsForEmail(
			@RequestParam(value = "email", required = true) String email) {
		// if the email is null then I will use the currently
		// logged in users email to get the data
		Event[] ret = null;
		try {
			ret = this
					.getJdbcCon()
					.getEmailEvents(email)
					.toArray(
							new Event[this.getJdbcCon().getEmailEvents(email)
									.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	//get all events
	@RequestMapping("/events/all")
	public Event[] getAllEvents() {
		return getJdbcCon().listEvents().toArray(
				new Event[this.getJdbcCon().listEvents().size()]);
	}

	//get all events for a given year. pass year in url
	@SuppressWarnings("deprecation")
	@RequestMapping("/events/year")
	public Event[] getEventsInThisYear(
			@RequestParam(value = "year", required = true) Date date) {
		return getJdbcCon().getEvents(date.getYear()).toArray(
				new Event[this.getJdbcCon().getEvents(date.getYear()).size()]);
	}

	//
	// TODO finish implementation on model
	@RequestMapping(value = "/events/connect", method = RequestMethod.POST)
	public boolean[] createEventConnect(
			@RequestParam(value = "name", required = true) String eventName,
			@RequestParam(value = "date", required = true) Date date,
			@RequestParam(value = "loggedIn", required = true) String LoggedIn,
			@RequestBody @Valid final User[] users) {
		// check validity of event if good then check users then check to make
		// sure
		// the connection does not already exist
		// check admin priv
		boolean[] ret = new boolean[users.length];
		int i = 0;
		if (loginCon.isAdmin(LoggedIn)) {
			// check validity of event
			Event ev = this.getJdbcCon().getEvent(eventName, date);
			if (ev != null) {
				for (User u : users) {
					// check the validity of users
					User tempu = getJdbcUser().getUser(u.getEmail());
					if (tempu.equals(u)) {
						boolean t = true;
						try {
							getJdbcCon().createEventConnection(
									u.getPersonId() + "", ev.getEventId() + "");
						} catch (Exception e) {
							t = false;
						}
						ret[i] = t;
					} else {
						ret[i] = false;
					}
					i++;
				}// for
			} else {
				for (int k = 0; k < users.length; k++) {
					ret[k] = false;
				}// for
			}
		} else {
			for (int k = 0; k < users.length; k++) {
				ret[k] = false;
			}// for
		}
		return ret;
	}

	// TODO finish implementation on model
	@RequestMapping(value = "/events/connect", method = RequestMethod.DELETE)
	public boolean[] deleteEventConnect(
			@RequestParam(value = "name", required = true) String eventName,
			@RequestParam(value = "date", required = true) Date date,
			@RequestParam(value = "loggedIn", required = true) String LoggedIn,
			@RequestBody @Valid final User[] users) {
		// check validity of event if good then check users then check to make
		// sure
		// the connection does not already exist
		// check admin priv
		boolean[] ret = new boolean[users.length];
		int i = 0;
		if (loginCon.isAdmin(LoggedIn)) {
			// check validity of event
			Event ev = this.getJdbcCon().getEvent(eventName, date);
			if (ev != null) {
				for (User u : users) {
					// check the validity of users
					User tempu = getJdbcUser().getUser(u.getEmail());
					if (tempu.equals(u)) {
						boolean t = true;
						try {
							getJdbcCon().deleteEventConnection(
									u.getPersonId() + "", ev.getEventId() + "");
						} catch (Exception e) {
							t = false;
						}
						ret[i] = t;
					} else {
						ret[i] = false;
					}
					i++;
				}// for
			} else {
				for (int k = 0; k < users.length; k++) {
					ret[k] = false;
				}// for
			}
		} else {
			for (int k = 0; k < users.length; k++) {
				ret[k] = false;
			}// for
		}
		return ret;
	}
	
	
	//create a basic event. 
	@RequestMapping(value = "/events", method = RequestMethod.POST)
	public boolean createEvent(@RequestBody @Valid final Event event) {
		boolean ret = true;
		try {
			getJdbcCon().create(0, event.getEventName(), event.getDate(),
					event.getStart(), event.getEnd(), event.getLocation(),
					event.getDescription(), event.getAttendees(),
					event.getEpId());
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	//delete event. pass event name and data through url
	@RequestMapping(value = "/events", method = RequestMethod.DELETE)
	public boolean createEvent(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "date", required = true) Date date) {
		boolean ret = true;
		try {
			getJdbcCon().deleteEvent(name, date);
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
}
