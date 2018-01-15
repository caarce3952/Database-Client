package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {
	private StringProperty fname;
	private StringProperty lname;
	private StringProperty Ssn;
	private StringProperty Super_ssn;
	private StringProperty Dno;
	
	public Employee(String fname, String lname, String Ssn, String Super_ssn, String Dno){
		this.fname = new SimpleStringProperty(fname);
		this.lname = new SimpleStringProperty(lname);
		this.Ssn = new SimpleStringProperty(Ssn);;
		this.Super_ssn = new SimpleStringProperty(Super_ssn);
		this.Dno = new SimpleStringProperty(Dno);
	}

	public StringProperty getfName() {
		return fname;
	}

	public void setfName(StringProperty fName) {
		this.fname = fName;
	}

	public StringProperty getlName() {
		return lname;
	}

	public void setlName(StringProperty lName) {
		this.lname = lName;
	}

	public StringProperty getSsn() {
		return Ssn;
	}

	public void setSsn(StringProperty ssn) {
		Ssn = ssn;
	}

	public StringProperty getSuper_ssn() {
		return Super_ssn;
	}

	public void setSuper_ssn(StringProperty super_ssn) {
		Super_ssn = super_ssn;
	}

	public StringProperty getDno() {
		return Dno;
	}

	public void setDno(StringProperty dno) {
		Dno = dno;
	}
}
