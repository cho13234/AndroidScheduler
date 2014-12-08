package com.example.androidscheduler;

import java.util.ArrayList;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	
	private ArrayList<ClassInfo> mList;
	
	public CustomAdapter(){
		mList = new ArrayList<ClassInfo>();
	}

	// return - number of item
	@Override
	public int getCount() {
		return mList.size();
	}

	// return - item object, use - (casting)object
	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	// return - item position ID
	@Override
	public long getItemId(int position) {
		return position;
	}

	// inflate item ( draw )
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final Context context = parent.getContext();
		
		TextView text 		= null;
		TextView text1		= null;
		CustomHolder holder = null;
		
		// (too long list..)fading item.convertView == null
		if(convertView == null){
			
			// view == null --> get custom layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.custom_item, parent, false);
			
			text	= (TextView) convertView.findViewById(R.id.text);
			text1	= (TextView) convertView.findViewById(R.id.text1);
			
			// create holder , setTag
			holder 				= new CustomHolder();
			holder.mTextView	= text;
			holder.mTextView1	= text1;
			convertView.setTag(holder);
		}
		else{
			holder = (CustomHolder) convertView.getTag();
			text = holder.mTextView;
			text1 = holder.mTextView1;
		}
		
		text.setText(mList.get(position).name);
		text1.setText(mList.get(position).type);

		return convertView;
	}
	
	private class CustomHolder{
		TextView mTextView;
		TextView mTextView1;
	}
	
	public void add(ClassInfo _msg){
		mList.add(_msg);
	}
	
	public void remove(int _pos){
		mList.remove(_pos);
	}
	
	public void edit(int _pos, ClassInfo _msg){
		mList.set(_pos, _msg);
	}

}
