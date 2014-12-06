package com.example.androidscheduler;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.os.Parcel;
import android.os.Parcelable;

public class ClassInfo implements Parcelable {

	public String name;
	public String day;
	public String type;
	public String credit;
	
	

	public ClassInfo(String name, String day, String detail, String credit) {

		this.name = name;
		this.day = day;
		this.type = detail;
		this.credit = credit;
	}

	public ClassInfo(Parcel src) {

		name = src.readString();
		day = src.readString();
		type = src.readString();
		credit = src.readString();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Object createFromParcel(Parcel source) {
			return new ClassInfo(source);
		}

		@Override
		public Object[] newArray(int size) {
			return new ClassInfo[size];
		}

	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(day);
		dest.writeString(type);
		dest.writeString(credit);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getCredit() {
		return credit;
	}
	
	public void setCredit(String credit) {
		this.credit = credit;
	}

}
