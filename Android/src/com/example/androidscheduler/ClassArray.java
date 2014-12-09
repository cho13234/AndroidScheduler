package com.example.androidscheduler;

import java.io.IOException;
import java.util.ArrayList;

public class ClassArray {
	public ArrayList<String> dept;
	public ArrayList<String> grade;
	public ArrayList<String> type;
	public ArrayList<String> name;
	public ArrayList<String> day;
	public ArrayList<String> credit;
	
	public ClassArray() {
		
		dept = new ArrayList<String>();
		grade= new ArrayList<String>();
		type = new ArrayList<String>();
		name = new ArrayList<String>();
		day = new ArrayList<String>();
		credit = new ArrayList<String>();
		
	}
	
	public void remove(int i){
		dept.remove(i);
		grade.remove(i);
		type.remove(i);
		name.remove(i);
		day.remove(i);
		credit.remove(i);
	}
	
	public void set(ClassArray ca) throws CloneNotSupportedException{
		dept = (ArrayList<String>) ca.dept.clone();
		grade = (ArrayList<String>) ca.grade.clone();
		type = (ArrayList<String>) ca.type.clone();
		name = (ArrayList<String>) ca.name.clone();
		day = (ArrayList<String>) ca.day.clone();
		credit = (ArrayList<String>) ca.credit.clone();
	}
}
