package com.example.androidscheduler;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.*;

public class MainActivity extends Activity {

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
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			Intent intent = new Intent(MainActivity.this, NewActivity.class);

			monId = mListViewMon.getId();
			int currentId = parent.getId();
//			String currentDay = new String();

			for (int i = 0; i < 6; i++) {
				if ((currentId - monId) == i) {
//					currentDay = Day[i]; // return <- click item - day
					x = i;
					y = position;
				}
			}

			// Toast.makeText(getApplicationContext(), currentDay+" "+position,
			// Toast.LENGTH_LONG).show();
//			Log.d("clickListener", "" + x + " " + y);

			// x - 1~6, y - 1~9, xy/10 = x, xy%10 = y
//			int xy = ((x + 1) * 10) + (y + 1);
//			Log.d("clickListener", "" + xy);

			 ClassInfo ci = (ClassInfo)mCustomArray.get(x).getItem(y);
			
			 intent.putExtra("ClassInfo", ci);
			 startActivityForResult(intent, 1000);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

		mListView.setAdapter(mAdapter);

		// mListViewMon.setAdapter(mAdapterMon);
		// mListViewTue.setAdapter(mAdapterTue);
		// mListViewWed.setAdapter(mAdapterWed);
		// mListViewThu.setAdapter(mAdapterThu);
		// mListViewFri.setAdapter(mAdapterFri);
		// mListViewSat.setAdapter(mAdapterSat);

		mListViewMon.setAdapter(mCustomArray.get(0));
		mListViewTue.setAdapter(mCustomArray.get(1));
		mListViewWed.setAdapter(mCustomArray.get(2));
		mListViewThu.setAdapter(mCustomArray.get(3));
		mListViewFri.setAdapter(mCustomArray.get(4));
		mListViewSat.setAdapter(mCustomArray.get(5));

		for (int i = 1; i < 10; i++) {
			mAdapter.add((i + 8) + ":" + 30);
		}

		ClassInfo ci = new ClassInfo("", "", "");

		// for(int i = 0; i < 9; i++)
		// {
		// mAdapterMon.add(ci);
		// mAdapterTue.add(ci);
		// mAdapterWed.add(ci);
		// mAdapterThu.add(ci);
		// mAdapterFri.add(ci);
		// mAdapterSat.add(ci);
		// }
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 9; j++) {
				mCustomArray.get(i).add(ci);
			}
		}

		ClassInfo ci1 = new ClassInfo("Chemistry", "Mon-1", "example");
		// mAdapterMon.edit(0,ci1);
		mCustomArray.get(0).edit(3, ci1);
		mCustomArray.get(2).edit(0, ci1);

		mListViewMon.setOnItemClickListener(new CustomClickListner());
		mListViewTue.setOnItemClickListener(new CustomClickListner());
		mListViewWed.setOnItemClickListener(new CustomClickListner());
		mListViewThu.setOnItemClickListener(new CustomClickListner());
		mListViewFri.setOnItemClickListener(new CustomClickListner());
		mListViewSat.setOnItemClickListener(new CustomClickListner());

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.d("MainResult", "" + "result code is " + resultCode);

//		String TEXT = data.getStringExtra("TEXT");
		
//		Log.d("MainResult", "TEXT is " + TEXT);
		ClassInfo ci;
		Bundle bundle = data.getExtras();
		ci = (ClassInfo) bundle.get("ClassInfo");

		if (resultCode == 2001) {
//			ClassInfo ci = new ClassInfo(TEXT, Day[x]+"-"+(y+1), "ex");
			mCustomArray.get(x).edit(y, ci);
			mCustomArray.get(x).notifyDataSetChanged();

			Log.d("MainResult", "x is " + x + " y is " + y);
			Log.d("MainResult", "Class name is " + ci.name);
			Log.d("MainResult", "Class day is " + ci.day);
			Log.d("MainResult", "Class detail is " + ci.detail);
			
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

//파싱
class ParsingData {
	ParsingData(){
		
	}
}
