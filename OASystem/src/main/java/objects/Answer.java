package objects;

import java.sql.Date;

public class Answer {
	private int aId;
	private int qId;
	private int personId;
	private String aSubject;
	private String aContent;
	private Date postedDate;

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

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
		return aSubject;
	}

	public void setqSubject(String qSubject) {
		this.aSubject = qSubject;
	}

	public String getqContent() {
		return aContent;
	}

	public void setqContent(String qContent) {
		this.aContent = qContent;
	}

	public Date getDate() {
		return postedDate;
	}

	public void setDate(Date date) {
		this.postedDate = date;
	}
}
