package com.example.androidscheduler;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class mCustomAdapter extends BaseAdapter {
	
	private ArrayList<String> mList;
	
	public mCustomAdapter(){
		mList = new ArrayList<String>();
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
		CustomHolder holder = null;
		
		// (too long list..)fading item.convertView == null
		if(convertView == null){
			
			// view == null --> get custom layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.m_custom_item, parent, false);
			
			text	= (TextView) convertView.findViewById(R.id.text);
			
			// create holder , setTag
			holder 				= new CustomHolder();
			holder.mTextView	= text;
			convertView.setTag(holder);
		}
		else{
			holder = (CustomHolder) convertView.getTag();
			text = holder.mTextView;
		}
		
		text.setText(mList.get(position));
		text.setClickable(false);
		convertView.setClickable(false);
		
		return convertView;
	}
	
	private class CustomHolder{
		TextView mTextView;
	}
	
	public void add(String _msg){
		mList.add(_msg);
	}
	
	public void remove(int _pos){
		mList.remove(_pos);
	}

}
