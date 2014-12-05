package com.example.androidscheduler;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.os.Parcel;
import android.os.Parcelable;

public class ClassInfo implements Parcelable {

	public String name;
	public String day;
	public String detail;

	public ClassInfo(String name, String day, String detail) {

		this.name = name;
		
		this.day = day;
		
		this.detail = detail;
	}

	public ClassInfo(Parcel src) {

		name = src.readString();
		day = src.readString();
		detail = src.readString();
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
		dest.writeString(detail);
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
