package run;

import java.util.concurrent.atomic.AtomicLong;

import jdbcTemplates.UserJDBCTemplate;
import objects.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	@Autowired
	private ApplicationContext context;

	@RequestMapping("/Customer")
	public User getCustomer(
			@RequestParam(value = "id", required = false, defaultValue = "1") String id)
			throws Exception {

		UserJDBCTemplate usertemp = (UserJDBCTemplate) context
				.getBean("userJDBCTemplate");

		System.out.println("Database test");
		System.out.println("get User that already exists");
		User temp = usertemp.getUser("rnn7726@g.rit.edu");
		System.out.println("name =" + temp.getfName() + temp.getlName()
				+ " email =" + temp.getEmail());
		System.out.println("The Get test worked perfectly yayayayayay");

		return temp;
	}
}
