package dao;

import java.util.List;

import javax.sql.DataSource;

public interface User {

	/**
	 * 1 This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	/**
	 * 2 This is the method to be used to create a record in the Student table.
	 */
	public boolean create(int personId, String userPassword, String userType,
			String fName, String lName, String email, int yearLevel,
			String major);

	/**
	 * 3 This is the method to be used to list down a record from the Student
	 * table corresponding to a passed student id.
	 */
	// INFO why are they using big Integer?
	public objects.User getUser(String email);

	/**
	 * 4 This is the method to be used to list down all the records from the
	 * Student table.
	 */
	public List<objects.User> listUsers();

	/**
	 * 5 This is the method to be used to delete a record from the Student table
	 * corresponding to a passed student id.
	 */
	public boolean delete(String email);

	/**
	 * 6 If you are a PAL, then return all assigned students if assigned is true,
	 * if admin, return all unassigned users if unassigned, if not pal or admin
	 * the handling will be done by view
	 * 
	 * @param assigned true means assigned, false means unassigned
	 * @return
	 */
	public List<objects.User> getAssign(boolean assigned);	
	
	/** 8 get user from id
	 */
	public objects.User getUser(Integer id);
	/** 9 delete user by id
	 */
	public void delete(Integer id);
	
	public void createStudentConnection(String userId, String palNum);
	public void deleteStudentConnection(String userId, String palNum);
	
}