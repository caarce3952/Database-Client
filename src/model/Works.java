package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Works {
	private StringProperty Essn;
	private StringProperty Pno;
	private StringProperty Hours;
	
	public Works(String essn, String pno, String hours) {
		super();
		Essn = new SimpleStringProperty(essn);
		Pno = new SimpleStringProperty(pno);
		Hours = new SimpleStringProperty(hours);
	}
	
	public StringProperty getEssn() {
		return Essn;
	}
	public void setEssn(StringProperty essn) {
		Essn = essn;
	}
	public StringProperty getPno() {
		return Pno;
	}
	public void setPno(StringProperty pno) {
		Pno = pno;
	}
	public StringProperty getHours() {
		return Hours;
	}
	public void setHours(StringProperty hours) {
		Hours = hours;
	}
	
}
