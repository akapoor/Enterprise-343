package jdbcTemplates;

import java.util.List;

import javax.sql.DataSource;

import mappers.UserMapper;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserJDBCTemplate implements dao.User {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	public boolean create(int personId, String userPassword, String userType,
			String fName, String lName, String email, int yearLevel,
			String major) {
		String SQL = "insert into user (userPassword, userType, fName, lName, email, yearLevel, major) values (?,?,?,?,?,?,?)";

		jdbcTemplateObject.update(SQL, userPassword, userType, fName, lName,
				email, yearLevel, major);
		System.out.println("Created Record Name = " + email);
		return true;
	}

	public objects.User getUser(Integer id) {
		String SQL = "select * from user where personId = ?";
		objects.User user = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new UserMapper());
		return user;
	}

	public List<objects.User> listUsers() {
		String SQL = "select * from user";
		List<objects.User> users = jdbcTemplateObject.query(SQL,
				new UserMapper());
		return users;
	}

	public void delete(Integer id) {
		String SQL = "delete from user where personId = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted Record with ID = " + id);
		return;
	}

	public objects.User getUser(String email) {
		String SQL = "select * from user where email = ?";
		objects.User user = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { email }, new UserMapper());
		return user;
	}

	public boolean delete(String email) {
		String SQL = "delete from user where email = ?";
		jdbcTemplateObject.update(SQL, email);
		System.out.println("Deleted Record with email = " + email);
		return true;

	}
	// public void update(Integer id, Integer age){
	// String SQL = "update Student set age = ? where id = ?";
	// jdbcTemplateObject.update(SQL, age, id);
	// System.out.println("Updated Record with ID = " + id );
	// return;
	// }
}
