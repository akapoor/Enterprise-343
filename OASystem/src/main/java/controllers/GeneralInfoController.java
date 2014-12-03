package controllers;

import java.sql.Date;

import javax.validation.Valid;

import jdbcTemplates.GeneralInfoJDBCTemplate;
import jdbcTemplates.UserJDBCTemplate;
import objects.GeneralInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralInfoController {

	@Autowired
	private ApplicationContext context;
	@Autowired
	private GeneralInfoJDBCTemplate jdbcCon;
	@Autowired
	private UserJDBCTemplate jdbcUser;
	@Autowired
	private LoginController loginCon;

	public GeneralInfoJDBCTemplate getJdbcCon() {
		if (this.jdbcCon == null) {
			context.getBeanDefinitionNames();
			this.jdbcCon = (GeneralInfoJDBCTemplate) context
					.getBean("generalInfoJDBCTemplate");

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

	// get a specific event. pass event name and date in url
	@RequestMapping("/info")
	public GeneralInfo[] getCurrent() {
		GeneralInfo[] ret = new GeneralInfo[1];
		ret[0] = this.jdbcCon.getMostRecent();
		return ret;
	}

	// get an event given an event name. pass name in url
	@RequestMapping("/info/specific")
	public GeneralInfo[] getSpecificInfo(
			@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "date", required = true) Date date) {
		GeneralInfo[] ret = new GeneralInfo[1];
		ret[0] = this.jdbcCon.getFilename(fileName, date);
		return ret;
	}

	// create a basic question.
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public boolean uploadInfo(@RequestBody @Valid final GeneralInfo gi) {
		boolean ret = true;
		try {
			getJdbcCon().createGeneralInfo(gi);
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	// delete event. pass event name and data through url
	@RequestMapping(value = "/info", method = RequestMethod.DELETE)
	public boolean deleteInfo(@RequestBody @Valid final GeneralInfo gi) {
		boolean ret = true;
		try {
			getJdbcCon().deleteGeneralInfo(gi.getFilename(), gi.getDate());
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
}
