package jdbcTemplates;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import mappers.UserMapper;
import objects.User;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserJDBCTemplate implements dao.User {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

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
		String SQL = "insert into user (userPassword, userType, fName, lName, email, yearLevel, major) values (?,?,?,?,?,?,?)";
		jdbcTemplateObject.update(SQL, userPassword, userType, fName, lName,
				email, yearLevel, major);
		System.out.println("Created Record Name = " + email);
		return true;
	}

	/*
	 * 3 (non-Javadoc)
	 * 
	 * @see dao.User#getUser(java.lang.String)
	 */
	public objects.User getUser(String email) {
		String SQL = "select * from user where email = ?";
		objects.User user = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { email }, new UserMapper());
		return user;
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
		String SQL = "delete from user where email = ?";
		jdbcTemplateObject.update(SQL, email);
		System.out.println("Deleted Record with email = " + email);
		return true;

	}

	/*
	 * 6 (non-Javadoc)
	 * 
	 * @see dao.User#getUser(boolean)
	 */
	public List<objects.User> getAssign(boolean assigned) {
		String SQL;
		if (assigned){
			SQL = "SELECT * FROM user INNER JOIN student ON user.personId = student.personId";
		}
		else{
			SQL = "SELECT * FROM user LEFT JOIN student ON user.personId = student.personId WHERE student.personId is NULL UNION SELECT * FROM user RIGHT JOIN student ON user.personId = student.personId WHERE student.personId is NULL";
		}
		List<objects.User> users = jdbcTemplateObject.query(SQL,
				new UserMapper());
		return users;
	}

	/* 8 (non-Javadoc)
	 * @see dao.User#getUser(java.lang.Integer)
	 */
	public objects.User getUser(Integer id) {
		String SQL = "select * from user where personId = ?";
		objects.User user = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new UserMapper());
		return user;
	}

	/* 9 (non-Javadoc)
	 * @see dao.User#delete(java.lang.Integer)
	 */
	public void delete(Integer id) {
		String SQL = "delete from user where personId = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted Record with ID = " + id);
		return;
	}

	@Override
	public void createStudentConnection(String userId, String palNum) {
		// TODO Auto-generated method stub
		String SQL = "insert into student (personId, palNum) values (?, ?)";
		jdbcTemplateObject.update(SQL, userId, palNum);
	}

	@Override
	public void deleteStudentConnection(String userId, String palNum) {
		// TODO Auto-generated method stub
		String SQL = "delete from student where personId=? AND palNum=?";
		jdbcTemplateObject.update(SQL, userId, palNum);
	}
}
