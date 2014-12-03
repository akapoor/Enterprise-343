package dao;

import java.util.List;

import javax.sql.DataSource;

import objects.Answer;

public interface AnswerDAO {

	/** 1
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	public Answer getAnswer(int aId);
	
	public List<Answer> getAllAnswers(int qId);
	
	public void createAnswer(Answer answer);
	
	public void deleteAnswer(int aId);
	
}