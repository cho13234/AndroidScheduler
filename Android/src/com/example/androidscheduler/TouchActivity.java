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
	private int mode;
	private ClassInfo ci;
	Integer x, y;
	String day;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch);

		Bundle bundle = getIntent().getExtras();
		// mode 1 --> ci 존재
		mode = (Integer) bundle.get("mode");

		touchAdapter = new TouchAdapter();
		touchListView = (ListView) findViewById(R.id.listview);
		touchListView.setAdapter(touchAdapter);

		Log.d("TouchMode",mode+"");
		if (mode == 1) {
			ci = (ClassInfo) bundle.get("ClassInfo");
			ClassInfo cinfo = new ClassInfo("", "취소", "", "", "", "");
			
			touchAdapter.add(cinfo);
		} 
		else {
			x = bundle.getInt("isX");
			y = bundle.getInt("isY");
			y = y+1;
			Character c = MainActivity.week.charAt(x);
			day = c.toString();
//			classArr = MainActivity.classArray;
			try {
				classArr = new ClassArray();
				classArr.set(MainActivity.classArray);
			} catch (Exception e) {
				e.printStackTrace();
			}

			int count = 0;
			String s;
			String array[];
			for (int i = classArr.day.size()-1; i >= 0; i--) {
				if(classArr.name.get(i).equals("공학작문및발표")){
					Log.d("itisError?",classArr.day.get(i));
				}
				else{
					Log.d("itisError?",classArr.name.get(i));
				}
				if (classArr.day.get(i).contains("+")) {
					array = classArr.day.get(i).split("\\+");

					for (int j = 0; j < array.length; j++) {
						if (array[j].contains(day)
								&& array[j].contains(y.toString())) {
							++count;
						}
					}
					if(count == 0){
						classArr.remove(i);
					}
					count = 0;
				} 
				else {
					s = classArr.day.get(i);

					if (!s.contains(day) || !s.contains(y.toString())) {
						classArr.remove(i);
					}

				}

			}
			for (int i = 0; i < classArr.dept.size(); i++) {
				ClassInfo info = new ClassInfo(classArr.name.get(i),
						classArr.day.get(i), classArr.type.get(i),
						classArr.credit.get(i), classArr.dept.get(i),
						classArr.grade.get(i));
				touchAdapter.add(info);
			}
		}

		touchListView.setOnItemClickListener(new TouchClickListner());
	}
	
	public void onButtonReturnClicked(View v) {

		Intent intent = new Intent();
		setResult(2000, intent);
		finish();
	}

	// item listener
	private class TouchClickListner implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(TouchActivity.this, MainActivity.class);

			ClassInfo info = (ClassInfo) parent.getAdapter().getItem(position);

			if(info.day.equals("취소")){
				intent.putExtra("ClassInfo", ci.getDay());
				setResult(2002, intent);
			}
			else{
				intent.putExtra("ClassInfo", info);
				setResult(2001, intent);
			}
			finish();
		}
	}
}
