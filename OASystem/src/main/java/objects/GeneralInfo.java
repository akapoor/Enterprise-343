package objects;

import java.sql.Date;
import java.sql.Blob;

public class GeneralInfo {
	private int genInId;
	private int personId;
	private Date date;
	private Blob content;
	private String filename;
	
	// empty constructor
	public GeneralInfo() {
	}

	public int getGenInId() {
		return genInId;
	}

	public void setGenInId(int genInId) {
		this.genInId = genInId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
