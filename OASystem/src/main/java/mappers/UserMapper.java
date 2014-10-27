package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<objects.User> {
	public objects.User mapRow(ResultSet rs, int rowNum) throws SQLException {
		objects.User user = new objects.User();
		user.setPersonId(rs.getInt("personId"));
		user.setUserPassword(rs.getString("userPassword"));
		user.setUserType(rs.getString("userType"));
		user.setfName(rs.getString("fName"));
		user.setlName(rs.getString("lName"));
		user.setEmail(rs.getString("email"));
		user.setMajor(rs.getString("major"));
		user.setYearLevel(rs.getInt("yearLevel"));
		return user;
	}
}
