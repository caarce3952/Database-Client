package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Department {
	private StringProperty dname;
	private StringProperty dnumber;
	private StringProperty mgr_ssn;
	private StringProperty dLocation;
	
	public Department(String dname, String dnumber, String mgr_ssn, String dLocation) {
		
		this.dname = new SimpleStringProperty(dname);
		this.dnumber = new SimpleStringProperty(dnumber);
		this.mgr_ssn = new SimpleStringProperty(mgr_ssn);
		this.dLocation = new SimpleStringProperty(dLocation);
	}

	public StringProperty getDname() {
		return dname;
	}

	public void setDname(StringProperty dname) {
		this.dname = dname;
	}

	public StringProperty getDnumber() {
		return dnumber;
	}

	public void setDnumber(StringProperty dnumber) {
		this.dnumber = dnumber;
	}

	public StringProperty getMgr_ssn() {
		return mgr_ssn;
	}

	public void setMgr_ssn(StringProperty mgr_ssn) {
		this.mgr_ssn = mgr_ssn;
	}

	public StringProperty getDLocation() {
		return dLocation;
	}

	public void setDLocation(StringProperty dLocation) {
		this.dLocation = dLocation;
	}
	
	
}
