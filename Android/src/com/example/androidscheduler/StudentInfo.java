package com.example.androidscheduler;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentInfo implements Parcelable {

	public String name;
	public String number;
	

	public StudentInfo(String name, String number) {

		this.name = name;
		this.number = number;
	}

	public StudentInfo(Parcel src) {

		name = src.readString();
		number = src.readString();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Object createFromParcel(Parcel source) {
			return new StudentInfo(source);
		}

		@Override
		public Object[] newArray(int size) {
			return new StudentInfo[size];
		}

	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(number);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}

}
