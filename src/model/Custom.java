package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Custom {
	private StringProperty one;
	private StringProperty two;
	private StringProperty three;
	private StringProperty four;
	private StringProperty five;
	
	public Custom(String one, String two, String three, String four, String five){
		this.one = new SimpleStringProperty(one);
		this.two = new SimpleStringProperty(two);
		this.three = new SimpleStringProperty(three);;
		this.four = new SimpleStringProperty(four);
		this.five = new SimpleStringProperty(five);
	}

	public StringProperty getOne() {
		return one;
	}

	public void setOne(StringProperty one) {
		this.one = one;
	}

	public StringProperty getTwo() {
		return two;
	}

	public void setTwo(StringProperty two) {
		this.two = two;
	}

	public StringProperty getThree() {
		return three;
	}

	public void setThree(StringProperty three) {
		this.three = three;
	}

	public StringProperty getFour() {
		return four;
	}

	public void setFour(StringProperty four) {
		this.four = four;
	}

	public StringProperty getFive() {
		return five;
	}

	public void setFive(StringProperty five) {
		this.five = five;
	}
	
	public void add(StringProperty n){
		String temp = n.toString();
		switch(temp){
		case "1":
			setOne(n);
			break;
		case "2":
			setTwo(n);
			break;
		case "3":
			setThree(n);
			break;
		case "4":
			setFour(n);
			break;
		case "5":
			setFive(n);
			break;
		}
			
	}
}
