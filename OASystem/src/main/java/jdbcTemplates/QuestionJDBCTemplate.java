package jdbcTemplates;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mappers.QuestionMapper;
import objects.Question;

import org.springframework.jdbc.core.JdbcTemplate;

import dao.QuestionDAO;

public class QuestionJDBCTemplate implements QuestionDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private QuestionMapper qm = new QuestionMapper();

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	@Override
	public List<Question> getQuestions(objects.User user) {
		// TODO Auto-generated method stub
		// return null;
		List<Question> ret = new ArrayList<Question>();
		try {
			String selectSQL = "select * from question where personId=?";
			PreparedStatement preparedStatement = this.dataSource
					.getConnection().prepareStatement(selectSQL);
			preparedStatement.setInt(1, user.getPersonId());
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(qm.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public List<Question> getAllQuestions() {
		// TODO Auto-generated method stub
		String selectSQL = "SELECT * FROM question;";
		List<Question> ret = jdbcTemplateObject.query(selectSQL,
				new QuestionMapper());
		return ret;
	}

	@Override
	public List<Question> searchQuestions(String name) {
		// TODO Auto-generated method stub
		// return null;
		List<Question> ret = new ArrayList<Question>();
		try {
			String selectSQL = "select * from question where 'qContent' LIKE ?";
			PreparedStatement preparedStatement = this.dataSource
					.getConnection().prepareStatement(selectSQL);
			String temp = "%" + name + "%";
			preparedStatement.setString(1, temp);
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(qm.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public void createQuestion(Question question) {
		// TODO Auto-generated method stub
		try {
			String selectSQL = "insert into question (personId, qSubject, qContent, postedDate) values (?, ?, ?, ?);";
			PreparedStatement preparedStatement = this.dataSource
					.getConnection().prepareStatement(selectSQL);
			preparedStatement.setInt(1, question.getPersonId());
			preparedStatement.setString(2, question.getqSubject());
			preparedStatement.setString(3, question.getqContent());
			preparedStatement.setDate(4, question.getDate());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteQuestion(int qId) {
		try {
			String selectSQL = "delete from question where qId = ?;";
			PreparedStatement preparedStatement = this.dataSource
					.getConnection().prepareStatement(selectSQL);
			preparedStatement.setInt(1, qId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
