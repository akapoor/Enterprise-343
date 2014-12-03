package jdbcTemplates;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mappers.GeneralInfoMapper;
import objects.GeneralInfo;

import org.springframework.jdbc.core.JdbcTemplate;

import dao.GeneralInfoDAO;

public class GeneralInfoJDBCTemplate implements GeneralInfoDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private GeneralInfoMapper gim = new GeneralInfoMapper();

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	@Override
	public GeneralInfo getMostRecent() {
		String selectSQL = "SELECT * FROM generalinfo;";
		List<GeneralInfo> ret = jdbcTemplateObject.query(selectSQL, new GeneralInfoMapper());
		return ret.get(0);
	}

	@Override
	public GeneralInfo getFilename(String filename, Date createDate) {
		List<GeneralInfo> ret = new ArrayList<GeneralInfo>();
		try {
			String selectSQL = "select * from generalinfo where filename = ? AND createDate = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, filename);
			preparedStatement.setDate(2, createDate);
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(gim.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret.get(0);
	}

	@Override
	public void createGeneralInfo(GeneralInfo genInfo) {
		try {
			String selectSQL = "insert into generalinfo (personId, createDate, content, filename) values (?, ?, ?, ?);";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, genInfo.getGenInId());
			preparedStatement.setDate(2, genInfo.getDate());
			preparedStatement.setBlob(3, genInfo.getContent());
			preparedStatement.setString(4, genInfo.getFilename());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteGeneralInfo(String filename, Date createDate) {
		try {
			String selectSQL = "delete from generalinfo where filename = ? and createDate = ?;";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, filename);
			preparedStatement.setDate(2, createDate);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
