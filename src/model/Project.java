package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Project {
	private StringProperty Pname;
	private StringProperty Pnumber;
	private StringProperty Plocation;
	private StringProperty Dnum;
	
	public Project(String pname, String pnumber, String plocation, String dnum) {
		Pname = new SimpleStringProperty(pname);
		Pnumber = new SimpleStringProperty(pnumber);
		Plocation = new SimpleStringProperty(plocation);
		Dnum = new SimpleStringProperty(dnum);
	}

	public StringProperty getPname() {
		return Pname;
	}

	public void setPname(StringProperty pname) {
		Pname = pname;
	}

	public StringProperty getPnumber() {
		return Pnumber;
	}

	public void setPnumber(StringProperty pnumber) {
		Pnumber = pnumber;
	}

	public StringProperty getPLocation() {
		return Plocation;
	}

	public void setPLocation(StringProperty plocation) {
		Plocation = plocation;
	}

	public StringProperty getDnum() {
		return Dnum;
	}

	public void setDnum(StringProperty dnum) {
		Dnum = dnum;
	}
	
	
	
}
