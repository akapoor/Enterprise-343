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
	private UserJDBCTemplate jdbcCon;
	private LoginController loginCon;

	public UserJDBCTemplate getJdbcCon() {
		if (this.jdbcCon == null) {
			this.jdbcCon = (UserJDBCTemplate) context
					.getBean("userJDBCTemplate");
		}
		return jdbcCon;
	}

	@RequestMapping("/user/assigned")
	public User[] getAssignedUsers(
			@RequestParam(value = "email", required = true) String email) {
		// check admin permissions
		ArrayList<User> arr = new ArrayList<User>();
		if (loginCon.isAdmin(email)) {
			return jdbcCon.getAssign(true).toArray(
					new User[jdbcCon.getAssign(true).size()]);
		} else
			return null;
	}

	@RequestMapping("/user/unassigned")
	public User[] getUnassignedUsers(
			@RequestParam(value = "email", required = true) String email) {
		// check admin permissions
		ArrayList<User> arr = new ArrayList<User>();
		if (loginCon.isAdmin(email)) {
			return jdbcCon.getAssign(false).toArray(
					new User[jdbcCon.getAssign(false).size()]);
		} else
			return null;
	}

	@RequestMapping("/user")
	public User[] getUser(
			@RequestParam(value = "email", required = true) String email)
			throws Exception {

		UserJDBCTemplate database = this.getJdbcCon();
		User[] users = new User[1];
		users[0] = database.getUser(email);
		return users;
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public boolean createUser(@RequestBody @Valid final User user) {
		return this.getJdbcCon().create(user.getPersonId(),
				user.getUserPassword(), user.getUserType(), user.getfName(),
				user.getlName(), user.getEmail(), user.getYearLevel(),
				user.getMajor());
	}

	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	public boolean deleteUser(
			@RequestParam(value = "email", required = true) String email) {
		return this.getJdbcCon().delete(email);
	}

	// TODO finish the caching of login controller to hold users and be able to
	// reference them with all requests
	// return array of boolean representing successs or not
	@RequestMapping(value = "/user/connect", method = RequestMethod.POST)
	public boolean[] connectStuds(
			@RequestParam(value = "email", required = true) String PalsEmail,
			@RequestBody @Valid final User[] users) {
		if (loginCon.isAdmin(PalsEmail)) {
			boolean[] ret = new boolean[users.length];
			int index = 0;
			// get cache of person logged in
			User tempuser = loginCon.getCurUser(PalsEmail);
			for (User u : users) {
				try {
					jdbcCon.createStudentConnection((u.getPersonId() + ""),
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
			@RequestBody @Valid final User[] users) {
		if (loginCon.isAdmin(PalsEmail)) {
			boolean[] ret = new boolean[users.length];
			int index = 0;
			// get cache of person logged in
			User tempuser = loginCon.getCurUser(PalsEmail);
			for (User u : users) {
				try {
					jdbcCon.deleteStudentConnection((u.getPersonId() + ""),
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
