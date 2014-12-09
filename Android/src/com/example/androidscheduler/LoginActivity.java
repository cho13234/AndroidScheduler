package com.example.androidscheduler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

		try {
			FileInputStream fis = openFileInput("student_info.txt");
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);

			String line;
			String array[];
			for (int i = 0; i < 4; i++) {
				if ((line = br.readLine()) != null) {
					array = line.split("=");
					if (!array[1].equals("")) {
						switch (i) {
						case 0:
							nameView.setText(array[1]);
							break;
						case 1:
							numberView.setText(array[1]);
							break;
						case 2:
							MainActivity.isSpecificName = array[1].equals("true");
							break;
						case 3:
							MainActivity.isSpecificNumber = array[1].equals("true");
						default:
							break;
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loginBtnClick(View v) throws IOException {
		String name = nameView.getText().toString();
		String number = numberView.getText().toString();

		StudentInfo student = new StudentInfo(name, number);
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("StudentInfo", student);

		// File file = getFileStreamPath("student_info.txt");
		FileOutputStream fos = openFileOutput("student_info.txt", MODE_PRIVATE);

		try {
			// Writer out = new OutputStreamWriter(fos,"UTF-8");
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			name = "name=" + name;
			number = "number=" + number;
			bw.write(name);
			bw.newLine();
			bw.write(number);
			bw.newLine();
			if (MainActivity.isSpecificName) {
				bw.write("specificName=true");
			} else {
				bw.write("specificName=false");
			}
			bw.newLine();
			if (MainActivity.isSpecificNumber) {
				bw.write("specificNumber=true");
			} else {
				bw.write("specificNumber=false");
			}
			bw.newLine();

			Log.d("inLoginAct", name);
			Log.d("inLoginAct", number);
			bw.close();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
