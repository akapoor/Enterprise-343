package objects;

import java.sql.Date;

public class Question {
	private int qId;
	private int personId;
	private String qSubject;
	private String qContent;
	private Date postedDate;
	
	public int getqId() {
		return qId;
	}
	public void setqId(int qId) {
		this.qId = qId;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getqSubject() {
		return qSubject;
	}
	public void setqSubject(String qSubject) {
		this.qSubject = qSubject;
	}
	public String getqContent() {
		return qContent;
	}
	public void setqContent(String qContent) {
		this.qContent = qContent;
	}
	public Date getDate() {
		return postedDate;
	}
	public void setDate(Date date) {
		this.postedDate = date;
	}
}
