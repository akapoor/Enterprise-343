package dao;

import java.util.List;
import javax.sql.DataSource;

public interface User {

	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to create a record in the Student table.
	 */
	public boolean create(int personId, String userPassword, String userType,
			String fName, String lName, String email, int yearLevel,
			String major);

	/**
	 * This is the method to be used to list down a record from the Student
	 * table corresponding to a passed student id.
	 */
	// INFO why are they using big Integer?
	public objects.User getUser(String email);

	/**
	 * This is the method to be used to list down all the records from the
	 * Student table.
	 */
	public List<objects.User> listUsers();

	/**
	 * This is the method to be used to delete a record from the Student table
	 * corresponding to a passed student id.
	 */
	public boolean delete(String email);

	/**
	 * This is the method to be used to update a record into the Student table.
	 */
	// TODO thing above this comment
}