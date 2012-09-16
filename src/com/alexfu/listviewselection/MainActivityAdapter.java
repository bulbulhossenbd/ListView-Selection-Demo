package com.alexfu.listviewselection;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainActivityAdapter extends BaseAdapter {
	private Context mContext;
	private int mLayoutId;
	private String[] mData;
	private SparseBooleanArray mCheckedIdStates;
	private ChoiceMode mChoiceMode = ChoiceMode.NONE;
	
	public enum ChoiceMode {
		NONE,SINGLE,MULTIPLE;
	}
	
	public MainActivityAdapter(Context context, int layoutId, String[] data) {
		mContext = context;
		mLayoutId = layoutId;
		mData = data;
	}
	
	public int getCount() {
		return mData.length;
	}

	public Object getItem(int position) {
		return mData[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mLayoutId, parent, false);
		}
		
		TextView title = (TextView) convertView.findViewById(R.id.title);
		title.setText(getItem(position).toString());
		
		if(mCheckedIdStates != null && mCheckedIdStates.get(position)) {
			convertView.setBackgroundResource(R.color.holo_red);
		}
		else {
			convertView.setBackgroundResource(android.R.color.transparent);
		}
		
		convertView.setPadding(10, 10, 10, 10);
		return convertView;
	}
	
	public void setChoiceMode(ChoiceMode mode) {		
		if(mCheckedIdStates == null) {
			mCheckedIdStates = new SparseBooleanArray();
		}
		
		if(mode == ChoiceMode.NONE) {
			mCheckedIdStates.clear();
			notifyDataSetChanged();
		}
		
		mChoiceMode = mode;
	}
	
	public void setItemChecked(int position, boolean value) {
		switch(mChoiceMode) {
		case SINGLE:
			mCheckedIdStates.clear();
			mCheckedIdStates.put(position, value);
			break;
		case MULTIPLE:
			mCheckedIdStates.put(position, value);
			break;
		case NONE:
			return;
		}
		notifyDataSetChanged();
	}
	
	public SparseBooleanArray getCheckedItemPositions() {
		return mCheckedIdStates;
	}
}