package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import objects.Question;

import org.springframework.jdbc.core.RowMapper;

public class QuestionMapper implements RowMapper<Question> {
	public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
		Question question = new Question();
		question.setqId(rs.getInt("qId"));
		question.setPersonId(rs.getInt("personId"));
		question.setqSubject(rs.getString("qSubject"));
		question.setqContent(rs.getString("qContent"));
		question.setDate(rs.getDate("postedDate"));
		return question;
	}
}
