package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import jdbcTemplates.UserJDBCTemplate;
import objects.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private ApplicationContext context;
	@Autowired
	private UserJDBCTemplate jdbcCon;
	@Autowired
	private LoginController loginCon;

	public UserJDBCTemplate getJdbcCon() {
		if (this.jdbcCon == null) {
			this.jdbcCon = (UserJDBCTemplate) context
					.getBean("userJDBCTemplate");
		}
		return jdbcCon;
	}

	//gets a list of students who have assigned pals
	@RequestMapping("/user/assigned")
	public User[] getAssignedUsers(
			@RequestParam(value = "loggedIn", required = true) String loggedIn) {
		// check admin permissions
		ArrayList<User> arr = new ArrayList<User>();
		if (loginCon.isAdmin(loggedIn)) {
			return getJdbcCon().getAssign(true).toArray(
					new User[getJdbcCon().getAssign(true).size()]);
		} else
			return null;
	}

	//gets a list of all pals
	@RequestMapping("/user/pal")
	public User[] getPals(
			@RequestParam(value = "loggedIn", required = true) String loggedIn) {
		// check admin permissions
		ArrayList<User> arr = new ArrayList<User>();
		if (loginCon.isAdmin(loggedIn)) {
			return getJdbcCon().getPals().toArray(
					new User[getJdbcCon().getAssign(true).size()]);
		} else
			return null;
	}//TODO: null pointer exception occuring

	//gets a list of students who are not assigned to a pal. give current logged in email through url
	@RequestMapping("/user/unassigned")
	public User[] getUnassignedUsers(
			@RequestParam(value = "loggedIn", required = true) String loggedIn) {
		// check admin permissions
		ArrayList<User> arr = new ArrayList<User>();
		if (loginCon.isAdmin(loggedIn)) {
			return getJdbcCon().getAssign(false).toArray(
					new User[getJdbcCon().getAssign(false).size()]);
		} else
			return null;
	}

	//get a user given his email from the url
	@RequestMapping("/user")
	public User[] getUser(
			@RequestParam(value = "email", required = true) String email)
			throws Exception {

		UserJDBCTemplate database = getJdbcCon();
		User[] users = new User[1];
		users[0] = database.getUser(email);
		return users;
	}

	//create new user
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	// TODO richard dont need no personID and it wont effect anything so pass 0
	public boolean createUser(@RequestBody @Valid final User user) {
		return getJdbcCon().create(user.getPersonId(), user.getUserPassword(),
				user.getUserType(), user.getfName(), user.getlName(),
				user.getEmail(), user.getYearLevel(), user.getMajor());
	}

	//delete existing user
	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	public boolean deleteUser(
			@RequestParam(value = "email", required = true) String email) {
		return getJdbcCon().delete(email);
	}

	// TODO finish the caching of login controller to hold users and be able to
	// reference them with all requests
	// return array of boolean representing success or not
	@RequestMapping(value = "/user/connect", method = RequestMethod.POST)
	public boolean[] connectStuds(
			@RequestParam(value = "email", required = true) String PalsEmail,
			@RequestParam(value = "loggedIn", required = true) String LoggedIn,
			@RequestBody @Valid final User[] users) {
		if (loginCon.isAdmin(LoggedIn)) {
			boolean[] ret = new boolean[users.length];
			int index = 0;
			// get cache of person logged in
			User tempuser = loginCon.getCurUser(PalsEmail);
			for (User u : users) {
				try {
					getJdbcCon().createStudentConnection(
							(u.getPersonId() + ""),
							(tempuser.getPersonId() + ""));
				} catch (Exception e) {
					ret[index] = false;
				}
				ret[index] = true;
				index++;
			}
			return ret;
		} else {
			return null;
		}

	}

	// TODO finish the cacheing of user object in the loginController
	@RequestMapping(value = "/user/connect", method = RequestMethod.DELETE)
	public boolean[] unConnectStuds(
			@RequestParam(value = "email", required = true) String PalsEmail,
			@RequestParam(value = "loggedIn", required = true) String LoggedIn,
			@RequestBody @Valid final User[] users) {
		if (loginCon.isAdmin(LoggedIn)) {
			boolean[] ret = new boolean[users.length];
			int index = 0;
			// get cache of person logged in
			User tempuser = loginCon.getCurUser(PalsEmail);
			for (User u : users) {
				try {
					getJdbcCon().deleteStudentConnection(
							(u.getPersonId() + ""),
							(tempuser.getPersonId() + ""));
				} catch (Exception e) {
					ret[index] = false;
				}
				ret[index] = true;
				index++;
			}
			return ret;
		} else {
			return null;
		}
	}

}
