package com.davidparry.twitter.widgets;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.davidparry.twitter.ButlerTabActivity;
import com.davidparry.twitter.R;

public class TwitterCheckBox extends CheckBoxPreference {
	private static final String TAG = "TwitterCheckBox";

	public TwitterCheckBox(Context context) {
		super(context);
		
	}

	public TwitterCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TwitterCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onClick() {
		super.onClick();
		Log.d(TAG, "OnClick");
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		Log.d(TAG, "onBindView");
		
	}

	@Override
	public View getView(View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.prefsearch, null);
			// only add one listener
			List<View> c =v.getTouchables();
			ImageButton b = (ImageButton) c.get(0);
			b.setOnClickListener(
			new OnClickListener(){
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(v.getContext(), ButlerTabActivity.class);
					v.getContext().startActivity(intent);
				}
			});
				
		}
		return v;
	}
	
	

}
