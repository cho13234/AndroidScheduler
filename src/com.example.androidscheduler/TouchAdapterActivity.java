package com.example.androidscheduler;

import java.io.*;
import java.util.*;

import org.apache.http.*;

import android.R.string;
import android.app.*;
import android.os.*;
import android.util.*;
import android.widget.*;

public class TouchAdapterActivity extends Activity{
	ArrayList arraylist = new ArrayList<string>();
	
	ArrayList arraylist1 = new ArrayList<string>();
	ArrayList arraylist2 = new ArrayList<string>();
	ArrayList arraylist3 = new ArrayList<string>();
	ArrayList arraylist4 = new ArrayList<string>();
	ArrayList arraylist5 = new ArrayList<string>();
	
	protected void onCreate(Bundle savedInstanceState) {
		
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		

		new Thread(new Runnable(){
			public void run(){

				loadTxt();
			}
		}).start();
		
		ArrayAdapter<String> Adapter;
		Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,arraylist);
		
		
		for(int e = 0;e<arraylist3.size();e++){
			if(arraylist3.get(e).toString().contains("필수")){
				System.out.println(arraylist.get(e));
				ListView list = (ListView)findViewById(R.id.listview);
				list.setAdapter(Adapter);
							
			}
		}
		
		
	}
	
	public void loadTxt(){
		int i=0;
		InputStream inputData = getResources().openRawResource(R.raw.class_info);

		
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputData,"UTF-8"));
			while(true){
				String string = bufferedReader.readLine();
				
				if(string !=null){
					arraylist.add(string);
					
				}else{
					break;
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		

		for(int j=0;j<arraylist.size();j++)	
		{
			StringTokenizer st1 = new StringTokenizer((String)arraylist.get(j),",");
			arraylist1.add(st1.nextToken());	//학과
			arraylist2.add(st1.nextToken());	//학년
			arraylist3.add(st1.nextToken());	//선택or필수
			arraylist4.add(st1.nextToken());	//과목
			arraylist5.add(st1.nextToken());	//요일
			
			System.out.println(arraylist1.get(j));
			System.out.println(arraylist2.get(j));
			System.out.println(arraylist3.get(j));
			System.out.println(arraylist4.get(j));
			System.out.println(arraylist5.get(j));
		}
		
		
		
		for(int k=0;k<arraylist5.size();k++){
			StringTokenizer st2 = new StringTokenizer((String)arraylist.get(k),"/");
		}
		
		for(int j=0;j<arraylist.size();j++)	
		{
			if(arraylist5.get(j).toString().contains("수5")){
				System.out.println(arraylist.get(j));
			}
		}
		
		
	}
	
	
}
