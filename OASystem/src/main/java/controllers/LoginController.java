package controllers;

import java.util.HashMap;

import javax.validation.Valid;

import jdbcTemplates.UserJDBCTemplate;
import objects.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import requestObjects.LoginObject;

@RestController
public class LoginController {

	@Autowired
	private ApplicationContext context;
	@Autowired
	private UserJDBCTemplate jdbcCon;
	private HashMap<String, User> userCacher = new HashMap<String, User>();

	public UserJDBCTemplate getJdbcCon() {
		if (this.jdbcCon == null) {
			this.jdbcCon = (UserJDBCTemplate) context
					.getBean("userJDBCTemplate");
		}
		return jdbcCon;
	}

	/**
	 * This method takes a login request, and checks to see if the user is in
	 * the system if the user is then this code returns true otherwise it will
	 * return false values are passed in request body to avoid having the
	 * password in the open. This uses a loginObject to pass in the data since
	 * two strings was confusing the json parser
	 * TODO: create a unique id and pass that back to view who will pass it to me upon all requests?
	 * 
	 * @param email
	 * @param password
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody @Valid final LoginObject info)
			throws Exception {

		UserJDBCTemplate database = this.getJdbcCon();
		User temp = null;
		try {
			temp = database.getUser(info.getEmail());
		} catch (Exception e) {
			return null;
		}
		// assume user exists at this point. Check passwords
		// if needed can take curUser and make it its own class so you dont
		// always have
		// to reference login to check for the curUser. But for now I am going
		// with this
		// Implementation
		if (info.getPassword().equals(temp.getUserPassword())) {
			this.userCacher.put(temp.getEmail(), temp);
			return temp.getUserType();
		} else {
			return null;
		}
	}

	public User getCurUser(String email) {
		return this.userCacher.get(email);
	}

	/**
	 * checks to make sure that the current user is an admin
	 * 
	 * @return
	 */
	public boolean isAdmin(String email) {
		if (this.userCacher.get(email).getUserType().toUpperCase()
				.equals("ADMIN")) {
			return true;
		} else
			return false;
	}

}
