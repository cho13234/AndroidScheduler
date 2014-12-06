package com.example.androidscheduler;

import java.io.BufferedReader;
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

	private String[] Day = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	private int x = 0, y = 0;
	public int monId = 0;

	// item listener
	public class CustomClickListner implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(MainActivity.this, NewActivity.class);

			monId = mListViewMon.getId();
			int currentId = parent.getId();

			for (int i = 0; i < 6; i++) {
				if ((currentId - monId) == i) {
					x = i;
					y = position;
					mListArray.get(x).setSelection(y);
				}
			}

			ClassInfo ci = (ClassInfo) mCustomArray.get(x).getItem(y);
			Log.d("MainClickListener", x + " " + y);

			intent.putExtra("ClassInfo", ci);
			startActivityForResult(intent, 1000);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			// res/raw/ <- in textfile
			InputStream in = getResources().openRawResource(R.raw.class_info);
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String line;
			String array[];
			ClassArray classArray = new ClassArray();
			
			while((line = br.readLine()) != null){
				array = line.split(",");
				
				classArray.dept.add(array[0]);
				classArray.grade.add(array[1]);
				classArray.type.add(array[2]);
				classArray.name.add(array[3]);
				classArray.day.add(array[4]);
				classArray.credit.add(array[5]);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

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

		ClassInfo ci = new ClassInfo("", "", "","");

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				mCustomArray.get(i).add(ci);
			}
		}

		ClassInfo ci1 = new ClassInfo("Chemistry", "Mon-1", "example", "example");

		mCustomArray.get(0).edit(3, ci1); // set initial value - test
		mCustomArray.get(2).edit(0, ci1); // set initial value - test2

		mListViewMon.setOnItemClickListener(new CustomClickListner());
		mListViewTue.setOnItemClickListener(new CustomClickListner());
		mListViewWed.setOnItemClickListener(new CustomClickListner());
		mListViewThu.setOnItemClickListener(new CustomClickListner());
		mListViewFri.setOnItemClickListener(new CustomClickListner());
		mListViewSat.setOnItemClickListener(new CustomClickListner());

		try {
			ClassArray fr = new ClassArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.d("MainResult", "" + "result code is " + resultCode);

		if (resultCode == 2001) {
			ClassInfo ci;
			Bundle bundle = data.getExtras();
			ci = (ClassInfo) bundle.get("ClassInfo");

			mCustomArray.get(x).edit(y, ci);
			mCustomArray.get(x).notifyDataSetChanged();

			Log.d("MainResult", "x is " + x + " y is " + y);
			Log.d("MainResult", "Class name is " + ci.name);
			Log.d("MainResult", "Class day is " + ci.day);
			Log.d("MainResult", "Class detail is " + ci.type);

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		return super.onOptionsItemSelected(item);
	}
}

// 파싱
class ParsingData {
	ParsingData() {

	}
}
