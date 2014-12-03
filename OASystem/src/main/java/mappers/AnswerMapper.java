package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import objects.Answer;

import org.springframework.jdbc.core.RowMapper;

public class AnswerMapper implements RowMapper<Answer> {
	public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Answer answer = new Answer();
		answer.setaId(rs.getInt("aId"));
		answer.setqId(rs.getInt("qId"));
		answer.setPersonId(rs.getInt("personId"));
		answer.setqSubject(rs.getString("aSubject"));
		answer.setqContent(rs.getString("aContent"));
		answer.setDate(rs.getDate("postedDate"));
		return answer;
	}
}
