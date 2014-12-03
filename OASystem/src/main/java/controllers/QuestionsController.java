package controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import jdbcTemplates.AnswerJDBCTemplate;
import jdbcTemplates.QuestionJDBCTemplate;
import jdbcTemplates.UserJDBCTemplate;
import objects.Answer;
import objects.Question;
import objects.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import responseObjects.QuestionAnswer;

@RestController
public class QuestionsController {

	@Autowired
	private ApplicationContext context;
	@Autowired
	private QuestionJDBCTemplate jdbcCon;
	@Autowired
	private AnswerJDBCTemplate jdbcAns;
	@Autowired
	private UserJDBCTemplate jdbcUser;
	@Autowired
	private LoginController loginCon;

	public QuestionJDBCTemplate getJdbcCon() {
		if (this.jdbcCon == null) {
			context.getBeanDefinitionNames();
			this.jdbcCon = (QuestionJDBCTemplate) context
					.getBean("questionJDBCTemplate");

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
	@RequestMapping("/questions")
	public QuestionAnswer[] getQuestionsByEmail(
			@RequestParam(value = "email", required = true) String email) {
		// is user a pal or a student?
		ArrayList<QuestionAnswer> temp = new ArrayList<QuestionAnswer>();
		User curUser = jdbcUser.getUser(email);
		if (curUser.getUserType().equals("PAL")) {
			// get all students and all students unanswered questinos
			ArrayList<User> lst = new ArrayList<User>();
			lst = (ArrayList<User>) this.jdbcUser.getAssign(true);
			// for user in array list get all unanswered questions
			for (User u : lst) {
				ArrayList<Question> unfltrd = new ArrayList<Question>();
				unfltrd = (ArrayList<Question>) this.jdbcCon.getQuestions(u);
				for (Question q : unfltrd) {
					// if wanted to filter results this would be the place to do
					// it
					for (Answer a : this.jdbcAns.getAllAnswers(q.getqId())) {
						temp.add(new QuestionAnswer(q, a));
					}
				}
			}
		}// ifPAL
		else {
			for (Question q : (ArrayList<Question>) this.jdbcCon
					.getQuestions(curUser)) {
				for (Answer a : this.jdbcAns.getAllAnswers(q.getqId())) {
					temp.add(new QuestionAnswer(q, a));
				}
			}
		}
		return temp.toArray(new QuestionAnswer[temp.size()]);
	}

	// get an event given an event name. pass name in url
	@RequestMapping("/questions/all")
	public QuestionAnswer[] getAllQuestions() {
		ArrayList<QuestionAnswer> temp = new ArrayList<QuestionAnswer>();
		for (Question q : (ArrayList<Question>) this.jdbcCon.getAllQuestions()) {
			for (Answer a : this.jdbcAns.getAllAnswers(q.getqId())) {
				temp.add(new QuestionAnswer(q, a));
			}
		}
		return temp.toArray(new QuestionAnswer[temp.size()]);
	}

	// get an event given an event name. pass name in url
	@RequestMapping(value = "/questions/answer", method = RequestMethod.POST)
	public boolean answerQuestion(@RequestBody @Valid final Answer answer,
			@RequestParam(value = "email", required = true) String email) {
		// look up user and set pId on answer object
		boolean ret = true;
		try {
			User u = this.jdbcUser.getUser(email);
			answer.setPersonId(u.getPersonId());
			this.jdbcAns.createAnswer(answer);
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	// get event based on a specific date. pass date in url
	@RequestMapping("/questions/search")
	public QuestionAnswer[] searchQuestionName(
			@RequestParam(value = "name", required = true) String name) {
		ArrayList<QuestionAnswer> temp = new ArrayList<QuestionAnswer>();
		for (Question q : this.jdbcCon.searchQuestions(name)) {
			for (Answer a : this.jdbcAns.getAllAnswers(q.getqId())) {
				temp.add(new QuestionAnswer(q, a));
			}
		}
		return temp.toArray(new QuestionAnswer[temp.size()]);
	}

	// create a basic question.
	@RequestMapping(value = "/questions", method = RequestMethod.POST)
	public boolean createQuestion(@RequestBody @Valid final Question quest) {
		boolean ret = true;
		try {
			getJdbcCon().createQuestion(quest);
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	// delete event. pass event name and data through url
	@RequestMapping(value = "/questions", method = RequestMethod.DELETE)
	public boolean deleteQuestion(@RequestBody @Valid final Question quest) {
		boolean ret = true;
		try {
			getJdbcCon().deleteQuestion(quest.getqId());
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
}
