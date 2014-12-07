package com.example.androidscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class NewActivity extends Activity {

	private EditText text1;
	private EditText text2;
	private EditText text3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);

		text1 = (EditText) findViewById(R.id.editText1);
		text2 = (EditText) findViewById(R.id.editText2);
		text3 = (EditText) findViewById(R.id.editText3);

		Bundle bundle = getIntent().getExtras();
		ClassInfo ci = bundle.getParcelable("ClassInfo");

		text1.setText(ci.getName());
		text2.setText(ci.getDay());
		text3.setText(ci.getType());
		Log.d("NewResult", "NewActiviy CI Name is " + ci.getName());
	}

	public void onButtonReturnClicked(View v) {

		Intent intent = new Intent();
		setResult(2000, intent);
		finish();
	}

	public void onButtonPushClicked(View v) {

		String name 		= text1.getText().toString();
		String day 			= text2.getText().toString();
		String type 		= text3.getText().toString();
//		String credit;

		ClassInfo ci 		= new ClassInfo(name,day,type,"");
		Intent intent = new Intent();

		intent.putExtra("ClassInfo", ci);
		setResult(2001, intent);
		finish();
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
