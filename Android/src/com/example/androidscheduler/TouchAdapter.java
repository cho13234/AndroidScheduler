package com.example.androidscheduler;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TouchAdapter extends BaseAdapter {
	
	private ArrayList<ClassInfo> mList;
	
	public TouchAdapter() {
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
		TextView text2		= null;
		TextView text3		= null;
		TextView text4		= null;
		TextView text5		= null;
		
		CustomHolder holder = null;
		
		// (too long list..)fading item.convertView == null
		if(convertView == null){
			
			// view == null --> get custom layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.custom_item, parent, false);
			
			text	= (TextView) convertView.findViewById(R.id.text);
			text1	= (TextView) convertView.findViewById(R.id.text1);
			text2	= (TextView) convertView.findViewById(R.id.text2);
			text3	= (TextView) convertView.findViewById(R.id.text3);
			text4	= (TextView) convertView.findViewById(R.id.text4);
			text5	= (TextView) convertView.findViewById(R.id.text5);
			
			// create holder , setTag
			holder 				= new CustomHolder();
			holder.mTextView	= text;
			holder.mTextView1	= text1;
			holder.mTextView2	= text2;
			holder.mTextView3	= text3;
			holder.mTextView4	= text4;
			holder.mTextView5	= text5;
			convertView.setTag(holder);
		}
		else{
			holder = (CustomHolder) convertView.getTag();
			text = holder.mTextView;
			text1 = holder.mTextView1;
			text2 = holder.mTextView2;
			text3 = holder.mTextView3;
			text4 = holder.mTextView4;
			text5 = holder.mTextView5;
		}
		
		text.setText(mList.get(position).name);
		text1.setText(mList.get(position).dept);
		text2.setText(mList.get(position).type);
		text3.setText("학년:"+mList.get(position).grade);
		text4.setText("학점:"+mList.get(position).credit);
		
		String day = mList.get(position).day;
		day = day.replace("_", " ");
		if(day.contains("+")){
			String array[] = day.split("\\+");
			// 개행문자 삽입으로 일별 한줄씩 사용
			for(int i = 1; i < array.length; i++){
				array[0] += "\n"+array[i];
			}
			text5.setText(array[0]);
		}
		else{
			text5.setText(mList.get(position).day);
		}

		return convertView;
	}
	
	private class CustomHolder{
		TextView mTextView;
		TextView mTextView1;
		TextView mTextView2;
		TextView mTextView3;
		TextView mTextView4;
		TextView mTextView5;
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
