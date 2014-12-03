package dao;

import java.sql.Date;

import javax.sql.DataSource;

import objects.GeneralInfo;

public interface GeneralInfoDAO {

	public void setDataSource(DataSource ds);

	public GeneralInfo getMostRecent();
	
	//public GeneralInfo getFilename(String filename);
	
	public void createGeneralInfo(GeneralInfo genInfo);
	
	public void deleteGeneralInfo(String filename, Date createDate);

	public GeneralInfo getFilename(String filename, Date createDate);
	
	
}