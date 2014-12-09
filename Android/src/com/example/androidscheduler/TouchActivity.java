package com.example.androidscheduler;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

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
		for (int i = 0; i < classArr.dept.size(); i++) {
			ClassInfo ci = new ClassInfo(classArr.name.get(i),
					classArr.day.get(i), classArr.type.get(i),
					classArr.credit.get(i), classArr.dept.get(i),
					classArr.grade.get(i));
			touchAdapter.add(ci);
		}
		
		touchListView.setOnItemClickListener(new TouchClickListner());

	}

	// item listener
	private class TouchClickListner implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(TouchActivity.this, MainActivity.class);

			ClassInfo ci = (ClassInfo) parent.getAdapter().getItem(position);
//			ClassInfo ci = (ClassInfo) mCustomArray.get(x).getItem(y);
//			Log.d("MainClickListener", x + " " + y);

			intent.putExtra("ClassInfo", ci);
			Log.d("TouchListner",ci.name);
			setResult(2001, intent);
			finish();
//			startActivityForResult(intent, 1000);
		}
	}
}
