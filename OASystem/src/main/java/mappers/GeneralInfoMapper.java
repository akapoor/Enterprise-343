package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import objects.GeneralInfo;

import org.springframework.jdbc.core.RowMapper;

public class GeneralInfoMapper implements RowMapper<GeneralInfo> {
	public GeneralInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		GeneralInfo generalInfo = new GeneralInfo();
		generalInfo.setGenInId(rs.getInt("genInId"));
		generalInfo.setPersonId(rs.getInt("personId"));
		generalInfo.setDate(rs.getDate("createDate"));
		generalInfo.setContent(rs.getBlob("content"));
		generalInfo.setFilename(rs.getString("filename"));
		return generalInfo;
	}
}
