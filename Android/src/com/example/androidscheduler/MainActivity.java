package com.example.androidscheduler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ArrayList<ListView> mListArray = new ArrayList<ListView>();

	private ListView mListView;
	private ListView mListViewMon;
	private ListView mListViewTue;
	private ListView mListViewWed;
	private ListView mListViewThu;
	private ListView mListViewFri;
	private ListView mListViewSat;

	private ArrayList<CustomAdapter> mCustomArray = new ArrayList<CustomAdapter>();

	private mCustomAdapter mAdapter;
	private CustomAdapter mAdapterMon;
	private CustomAdapter mAdapterTue;
	private CustomAdapter mAdapterWed;
	private CustomAdapter mAdapterThu;
	private CustomAdapter mAdapterFri;
	private CustomAdapter mAdapterSat;

	public static String week = "월화수목금토일";
	private int x = 0, y = 0;

	public static ClassArray classArray;
	public ClassArray originalArray;
	public StudentInfo student;

	public static boolean isSpecificName = false;
	public static boolean isSpecificNumber = false;
	public static boolean isTypeSelected = false;

	// item listener
	private class CustomClickListner implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(MainActivity.this, TouchActivity.class);

			ClassInfo ci = (ClassInfo) parent.getAdapter().getItem(position);

			if (!ci.day.equals("")) {
				// classInfo 존재할 때
				intent.putExtra("mode", 1);
				intent.putExtra("ClassInfo", ci);
			} else {
				// classInfo 없으므로 위치정보만 넘김
				x = parent.getId() - mListViewMon.getId();
				y = position;

				intent.putExtra("mode", 2);
				intent.putExtra("isX", x);
				intent.putExtra("isY", y);
			}
			startActivityForResult(intent, 1000);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// data parsing
		try {
			// res/raw/ <- in text file
			InputStream in = getResources().openRawResource(R.raw.class_info);
			BufferedReader br = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			String line;
			String array[];
			classArray = new ClassArray();

			int i = 0;
			while ((line = br.readLine()) != null) {
				array = line.split(",");

				classArray.dept.add(array[0]);
				classArray.grade.add(array[1]);
				classArray.type.add(array[2]);
				classArray.name.add(array[3]);
				classArray.day.add(array[4]);
				classArray.credit.add(array[5]);
				Log.d("InMainClass", classArray.dept.get(i++));

			}
			br.close();

			originalArray = new ClassArray();
			originalArray.set(classArray);
			Log.d("original test",originalArray.name.get(10));

		} catch (Exception e) {
			e.printStackTrace();
		}

		// student data parsing
		studentDataRead();

		// listview - adapterview -- need adapter
		mAdapter = new mCustomAdapter();
		mAdapterMon = new CustomAdapter();
		mAdapterTue = new CustomAdapter();
		mAdapterWed = new CustomAdapter();
		mAdapterThu = new CustomAdapter();
		mAdapterFri = new CustomAdapter();
		mAdapterSat = new CustomAdapter();

		mCustomArray.add(mAdapterMon);
		mCustomArray.add(mAdapterTue);
		mCustomArray.add(mAdapterWed);
		mCustomArray.add(mAdapterThu);
		mCustomArray.add(mAdapterFri);
		mCustomArray.add(mAdapterSat);

		// get listview id
		mListView = (ListView) findViewById(R.id.listview);
		mListViewMon = (ListView) findViewById(R.id.listview_1);
		mListViewTue = (ListView) findViewById(R.id.listview_2);
		mListViewWed = (ListView) findViewById(R.id.listview_3);
		mListViewThu = (ListView) findViewById(R.id.listview_4);
		mListViewFri = (ListView) findViewById(R.id.listview_5);
		mListViewSat = (ListView) findViewById(R.id.listview_6);

		mListArray.add(mListViewMon);
		mListArray.add(mListViewTue);
		mListArray.add(mListViewWed);
		mListArray.add(mListViewThu);
		mListArray.add(mListViewFri);
		mListArray.add(mListViewSat);

		mListView.setAdapter(mAdapter);

		for (int i = 0; i < 6; i++) {
			// mListArray has mListViewMon~Sat, mCustomArray has mAdapterMon~Sat
			mListArray.get(i).setAdapter(mCustomArray.get(i));
		}

		for (int i = 1; i < 10; i++) {
			mAdapter.add((i + 8) + ":" + 30);
		}

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				ClassInfo ci = new ClassInfo("", "", "", "");
				mCustomArray.get(i).add(ci);
			}
		}

		mListViewMon.setOnItemClickListener(new CustomClickListner());
		mListViewTue.setOnItemClickListener(new CustomClickListner());
		mListViewWed.setOnItemClickListener(new CustomClickListner());
		mListViewThu.setOnItemClickListener(new CustomClickListner());
		mListViewFri.setOnItemClickListener(new CustomClickListner());
		mListViewSat.setOnItemClickListener(new CustomClickListner());
		
		// 학생 정보가 없다면 해당 구간에 진입한다.
		if (student == null) {
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivityForResult(intent, 1000);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.d("MainResult", "result code is " + resultCode);

		ClassInfo ci;
		Bundle bundle = data.getExtras();
		if (resultCode == 2001) {
			ci = (ClassInfo) bundle.get("ClassInfo");
			refreshData(ci.day, ci);

		} else if (resultCode == 2002) {
			String day = (String) bundle.get("ClassInfo");
			ci = new ClassInfo("", "", "", "");
			refreshData(day, ci);
		} else if (resultCode == 2003) {
			studentDataRead();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void refreshData(String dayInfo, ClassInfo filler) {
		String day = dayInfo;

		if (day.contains("+")) {

			// 일부 특수문자를 string 으로 변환하기 위해 \\를 앞에 붙인다.
			String mArray[] = day.split("\\+");

			for (int i = 0; i < mArray.length; i++) {

				// (length +1)/2 로 반올림 성립
				for (int j = 1; j < (mArray[i].length() + 1) / 2; j++) {

					// 원래 -'0'을 함으로써 ascii -> integer 변환을 하지만 숫자표기는 1~9이고 실제
					// position 은 0~8이므로 -'1'을 해준다.
					mCustomArray.get(week.indexOf(mArray[i].charAt(0))).edit(
							mArray[i].charAt(j * 2) - '1', filler);
				}

				// refresh data cell
				mCustomArray.get(week.indexOf(mArray[i].charAt(0)))
						.notifyDataSetChanged();
			}
		} else {

			for (int j = 1; j < (day.length() + 1) / 2; j++) {
				mCustomArray.get(week.indexOf(day.charAt(0))).edit(
						day.charAt(j * 2) - '1', filler);
			}

			mCustomArray.get(week.indexOf(day.charAt(0)))
					.notifyDataSetChanged();
		}
	}

	public void modData(int mode){
		for (int i = classArray.dept.size()-1; i >= 0; i--) {
			if (!classArray.dept.get(i).equals(student.name)) {
				classArray.remove(i);
			}
		}
		for (int i = classArray.grade.size()-1; i >= 0; i--) {
			if (!classArray.grade.get(i).equals(student.number)) {
				classArray.remove(i);
			}
		}
		if(mode == 1){
			for (int i = classArray.type.size()-1; i >= 0; i--) {
				if (classArray.type.get(i).equals("전필")) {
					ClassInfo ci = new ClassInfo(classArray.name.get(i),
							classArray.day.get(i), classArray.type.get(i),
							classArray.credit.get(i), classArray.dept.get(i),
							classArray.grade.get(i));
					refreshData(classArray.day.get(i), ci);
				}
			}
		}
		else{
			for (int i = classArray.type.size()-1; i >= 0; i--) {
				if (classArray.type.get(i).equals("전필")) {
					ClassInfo ci = new ClassInfo("","","","");
					refreshData(classArray.day.get(i), ci);
				}
			}
		}
		
	}
	
	public void studentDataRead(){
		// student data
				try {
					FileInputStream fis = openFileInput("student_info.txt");
					BufferedReader br = new BufferedReader(new InputStreamReader(fis,
							"UTF-8"));
					String line;
					String array[];
					student = new StudentInfo("", "");

					for (int i = 0; i < 4; i++) {
						if ((line = br.readLine()) != null) {
							array = line.split("=");
							if (!array[1].equals("")) {
								switch (i) {
								case 0:
									student.setName(array[1]);
									break;
								case 1:
									student.setNumber(array[1]);
									break;
								case 2:
									// true 라면 true 반환
									isSpecificName = array[1].equals("true");
									break;
								case 3:
									isSpecificNumber = array[1].equals("true");
									break;
								default:
									break;
								}
							}
						}
					}
					br.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		menu.add(0, 0, Menu.NONE, "ONE").setTitle("사용자 정보 설정");
		menu.add(0, 1, Menu.NONE, "TWO").setTitle("과목 자동선택 설정");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		switch (item.getItemId()) {
		case 0:
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivityForResult(intent, 1000);
			break;
		case 1:
			if(isTypeSelected){
				Toast.makeText(getApplicationContext(), "과목 자동선택 기능을 해제합니다.", Toast.LENGTH_LONG).show();
				isTypeSelected = false;
				modData(2);
				classArray.set(originalArray);
				Log.d("original test",originalArray.name.get(10));
				Log.d("original test",classArray.name.get(10));
			}
			else{
				Toast.makeText(getApplicationContext(), "전공필수 과목을 자동선택합니다.", Toast.LENGTH_LONG).show();
				isTypeSelected = true;
				modData(1);
				classArray.set(originalArray);
				Log.d("original test",originalArray.name.get(10));
				Log.d("original test",classArray.name.get(10));
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
