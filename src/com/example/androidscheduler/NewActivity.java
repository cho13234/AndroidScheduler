package com.example.androidscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class NewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        
    }
    
    public void onButtonReturnClicked(View v){
    	Toast.makeText(getApplicationContext(), "pushed return", Toast.LENGTH_LONG).show();
    	
    	Intent intent = new Intent();
    	setResult(1000, intent);
    	finish();
    }
    
    public void onButtonPushClicked(View v){

    	EditText text;
    	text = (EditText)findViewById(R.id.editText1);
    	
    	final String TEXT = text.getText().toString();
    	Toast.makeText(getApplicationContext(), TEXT, Toast.LENGTH_LONG).show();
    	
    	Intent intent = new Intent();
    	intent.putExtra("TEXT", TEXT);

    	setResult(1000, intent);
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
