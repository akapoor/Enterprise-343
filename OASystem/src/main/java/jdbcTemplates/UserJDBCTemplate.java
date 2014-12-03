package jdbcTemplates;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mappers.EventMapper;
import mappers.UserMapper;
import objects.Event;
import objects.User;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserJDBCTemplate implements dao.User {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private UserMapper um = new UserMapper();
	/*
	 * 1 (non-Javadoc)
	 * 
	 * @see dao.User#setDataSource(javax.sql.DataSource)
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	/*
	 * 2 (non-Javadoc)
	 * 
	 * @see dao.User#create(int, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, int,
	 * java.lang.String)
	 */
	public boolean create(int personId, String userPassword, String userType,
			String fName, String lName, String email, int yearLevel,
			String major) {
//		String SQL = "insert into user (userPassword, userType, fName, lName, email, yearLevel, major) values (?,?,?,?,?,?,?)";
//		jdbcTemplateObject.update(SQL, userPassword, userType, fName, lName,
//				email, yearLevel, major);
//		System.out.println("Created Record Name = " + email);
//		return true;
			try {
				String selectSQL = "insert into user (userPassword, userType, fName, lName, email, yearLevel, major) values (?,?,?,?,?,?,?)";
				PreparedStatement preparedStatement = this.dataSource.getConnection()
						.prepareStatement(selectSQL);
				preparedStatement.setString(1, userPassword);
				preparedStatement.setString(2, userType);
				preparedStatement.setString(3, fName);
				preparedStatement.setString(4, lName);
				preparedStatement.setString(5, email);
				preparedStatement.setInt(6, yearLevel);
				preparedStatement.setString(7, major);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

	/*
	 * 3 (non-Javadoc)
	 * 
	 * @see dao.User#getUser(java.lang.String)
	 */
	public objects.User getUser(String email) {
//		String SQL = "select * from user where email = ?";
//		objects.User user = jdbcTemplateObject.queryForObject(SQL,
//				new Object[] { email }, new UserMapper());
//		return user;
		
		List<objects.User> ret = new ArrayList<objects.User>();
		try {
			String selectSQL = "select * from user where email = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(um.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret.get(0);
	}

	/*
	 * 4 (non-Javadoc)
	 * 
	 * @see dao.User#listUsers()
	 */
	public List<objects.User> listUsers() {
		String SQL = "select * from user";
		List<objects.User> users = jdbcTemplateObject.query(SQL,
				new UserMapper());
		return users;
	}

	/*
	 * 5 (non-Javadoc)
	 * 
	 * @see dao.User#delete(java.lang.String)
	 */
	public boolean delete(String email) {
//		String SQL = "delete from user where email = ?";
//		jdbcTemplateObject.update(SQL, email);
//		System.out.println("Deleted Record with email = " + email);
//		return true;
		try {
			String selectSQL = "delete from user where email = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * 6 (non-Javadoc)
	 * 
	 * @see dao.User#getUser(boolean)
	 */
	public List<objects.User> getAssign(boolean assigned) {
		String SQL;
		if (assigned) {
			SQL = "SELECT * FROM user INNER JOIN student ON user.personId = student.personId";
		} else {
			SQL = "SELECT * FROM user LEFT JOIN student ON user.personId = student.personId WHERE student.personId is NULL UNION SELECT * FROM user RIGHT JOIN student ON user.personId = student.personId WHERE student.personId is NULL";
		}
		List<objects.User> users = jdbcTemplateObject.query(SQL,
				new UserMapper());
		return users;
	}

	/*
	 * 8 (non-Javadoc)
	 * 
	 * @see dao.User#getUser(java.lang.Integer)
	 */
	public objects.User getUser(Integer id) {
//		String SQL = "select * from user where personId = ?";
//		objects.User user = jdbcTemplateObject.queryForObject(SQL,
//				new Object[] { id }, new UserMapper());
//		return user;
		
		List<objects.User> ret = new ArrayList<objects.User>();
		try {
			String selectSQL = "select * from user where personId = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				ret.add(um.mapRow(rs, i++));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret.get(0);
	}

	/*
	 * 9 (non-Javadoc)
	 * 
	 * @see dao.User#delete(java.lang.Integer)
	 */
	public void delete(Integer id) {
//		String SQL = "delete from user where personId = ?";
//		jdbcTemplateObject.update(SQL, id);
//		System.out.println("Deleted Record with ID = " + id);
//		return;
		try {
			String selectSQL = "delete from user where personId = ?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void createStudentConnection(String userId, String palNum) {
//		String SQL = "insert into student (personId, palNum) values (?, ?)";
//		jdbcTemplateObject.update(SQL, userId, palNum);
		try {
			String selectSQL = "insert into student (personId, palNum) values (?, ?)";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, palNum);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteStudentConnection(String userId, String palNum) {
//		String SQL = "delete from student where personId=? AND palNum=?";
//		jdbcTemplateObject.update(SQL, userId, palNum);
		try {
			String selectSQL = "delete from student where personId=? AND palNum=?";
			PreparedStatement preparedStatement = this.dataSource.getConnection()
					.prepareStatement(selectSQL);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, palNum);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<User> getPals() {
		String SQL = "SELECT * FROM user WHERE userType = 'pal'";
		List<objects.User> users = jdbcTemplateObject.query(SQL,
				new UserMapper());
		return users;
	}
}
