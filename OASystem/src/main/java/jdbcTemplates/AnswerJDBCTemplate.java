package jdbcTemplates;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mappers.AnswerMapper;
import objects.Answer;

import org.springframework.jdbc.core.JdbcTemplate;

import dao.AnswerDAO;

public class AnswerJDBCTemplate implements AnswerDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private AnswerMapper am = new AnswerMapper();

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	@Override
	public Answer getAnswer(int aId) {
		// TODO Auto-generated method stub
		//return null;
		List<Answer> ret = new ArrayList<Answer>();
		try {
			String selectSQL = "select * from answer where aId=?;";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, aId);
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(am.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret.get(0);
	}

	@Override
	public List<Answer> getAllAnswers(int qId) {
		// TODO Auto-generated method stub
		//return null;
		List<Answer> ret = new ArrayList<Answer>();
		try {
			String selectSQL = "select * from answer where qId=?;";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, qId);
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(am.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public void createAnswer(Answer answer) {
		// TODO Auto-generated method stub
		try {
			String selectSQL = "insert into answer (qId, personId, aSubject, aContent, postedDate) values (?, ?, ?, ?, ?);";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, answer.getqId());
			preparedStatement.setInt(2, answer.getPersonId());
			preparedStatement.setString(3, answer.getqSubject());
			preparedStatement.setString(4, answer.getqContent());
			preparedStatement.setDate(5, answer.getDate());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAnswer(int aId) {
		// TODO Auto-generated method stub
		try {
			String selectSQL = "delete from answer where aId = ?;";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, aId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
