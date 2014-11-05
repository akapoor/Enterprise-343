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

	// TODO implement user/assinged, user/unassinged admin needed
	// TODO user/create post

	@RequestMapping("/user/assigned")
	public User[] getAssignedUsers() {
		// check admin permissions
		ArrayList<User> arr = new ArrayList<User>();
		if (loginCon.isAdmin()) {
			return null;
		} else
			return null;
	}

	@RequestMapping("/user/unassigned")
	public User[] getUnassignedUsers() {
		// check admin permissions
		ArrayList<User> arr = new ArrayList<User>();
		if (loginCon.isAdmin()) {
			return null;
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

	//needs to handle batch processing now
//	@RequestMapping(value = "/user/connect", method = RequestMethod.POST)
//	public boolean connectStuds(
//			@RequestParam(value = "email", required = true) String PalsEmail,
//			@RequestBody @Valid final User[] users) {
//		return this.jdbcCon.connectUsers(PalsEmail, Arrays.asList(users));
//	}

	@RequestMapping(value = "/user/connect", method = RequestMethod.DELETE)
	public boolean unConnectStuds(
			@RequestParam(value = "email", required = true) String PalsEmail,
			@RequestBody @Valid final User[] users) {
		return false;
	}

}
