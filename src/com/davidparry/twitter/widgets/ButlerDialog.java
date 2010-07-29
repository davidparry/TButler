package com.davidparry.twitter.widgets;

import android.app.Activity;
import android.app.ProgressDialog;

import com.davidparry.twitter.R;

public class ButlerDialog {
	private Activity activity;
	ProgressDialog pd = null;
	public ButlerDialog(Activity activity) {
		this.activity = activity;
	}
	
	public void start(){
		if(pd == null){
			pd = new ProgressDialog(this.activity);
			pd.setIcon(R.drawable.icon);
			pd.setMessage("Retrieving Tweets.");
			pd.setTitle("Working ...");
			pd.setIndeterminate(true);	
		}
		pd.show();
	}
	public void stop(){
		if(pd != null && pd.isShowing()){
			pd.hide();
		}
	}
		
}
