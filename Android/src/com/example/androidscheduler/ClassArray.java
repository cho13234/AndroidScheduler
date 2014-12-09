package com.example.androidscheduler;

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
		grade = new ArrayList<String>();
		type = new ArrayList<String>();
		name = new ArrayList<String>();
		day = new ArrayList<String>();
		credit = new ArrayList<String>();

	}

	public void remove(int i) {
		dept.remove(i);
		grade.remove(i);
		type.remove(i);
		name.remove(i);
		day.remove(i);
		credit.remove(i);
	}

	public void set(ClassArray ca) {

//		dept.clear();
//		grade.clear();
//		type.clear();
//		name.clear();
//		day.clear();
//		credit.clear();
		dept = null;
		grade = null;
		type = null;
		name = null;
		day = null;
		credit = null;
		
		dept = new ArrayList<String>();
		grade = new ArrayList<String>();
		type = new ArrayList<String>();
		name = new ArrayList<String>();
		day = new ArrayList<String>();
		credit = new ArrayList<String>();
		
		dept.addAll(ca.dept);
		grade.addAll(ca.grade);
		type.addAll(ca.type);
		name.addAll(ca.name);
		day.addAll(ca.day);
		credit.addAll(ca.credit);
	}
}
