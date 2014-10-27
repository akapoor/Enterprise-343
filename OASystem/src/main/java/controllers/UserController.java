package controllers;

import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import jdbcTemplates.UserJDBCTemplate;
import objects.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

	public UserJDBCTemplate getJdbcCon() {
		if (this.jdbcCon == null) {
			this.jdbcCon = (UserJDBCTemplate) context
					.getBean("userJDBCTemplate");
		}
		return jdbcCon;
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
	
	
}
