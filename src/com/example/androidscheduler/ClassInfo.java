package com.example.androidscheduler;

import java.util.ArrayList;

public class ClassInfo {
	
	public String name;
	public ArrayList<String> day;		// day - number ex) mon - 2, mon - 3
	public ArrayList<Integer> number;
	public String detail;
	
	public ClassInfo(String name, String day,
			Integer number, String detail) {
		
		this.day = new ArrayList<String>();
		this.number = new ArrayList<Integer>();
		
		this.name = name;
		this.day.add(day);
		this.number.add(number);
		this.detail = detail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getDay() {
		return day;
	}

	public void setDay(ArrayList<String> day) {
		this.day = day;
	}

	public ArrayList<Integer> getNumber() {
		return number;
	}

	public void setNumber(ArrayList<Integer> number) {
		this.number = number;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
