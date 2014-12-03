package dao;

import java.util.List;

import javax.sql.DataSource;

import objects.Question;

public interface QuestionDAO {

	/** 1
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	public List<Question> getQuestions(objects.User user);
	
	public List<Question> getAllQuestions();
	
	public List<Question> searchQuestions(String name);
	
	public void createQuestion(Question question);
	
	public void deleteQuestion(int qId);
	
}