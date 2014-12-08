package com.example.androidscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	EditText nameView;
	EditText numberView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		nameView = (EditText) findViewById(R.id.name);
		numberView = (EditText) findViewById(R.id.number);
	}
	
	public void loginBtnClick(View v){
		String name = nameView.getText().toString();
		String number = numberView.getText().toString();
		
		StudentInfo student = new StudentInfo(name,number);
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("StudentInfo", student);

		setResult(2002, intent);
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
