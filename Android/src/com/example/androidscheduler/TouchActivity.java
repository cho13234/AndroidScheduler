package com.example.androidscheduler;

import android.app.*;
import android.os.*;
import android.widget.*;

public class TouchActivity extends Activity {

	private ListView touchListView;
	private TouchAdapter touchAdapter;
	private ClassArray classArr;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch);
		
		touchAdapter = new TouchAdapter();
		touchListView = (ListView) findViewById(R.id.listview);
		touchListView.setAdapter(touchAdapter);
		
		classArr = MainActivity.classArray;
		for(int i = 0; i < classArr.dept.size(); i++){
			ClassInfo ci = new ClassInfo(classArr.name.get(i),classArr.day.get(i),
					classArr.type.get(i),classArr.credit.get(i),classArr.dept.get(i),
					classArr.grade.get(i));
			touchAdapter.add(ci);
		}

	}
}
