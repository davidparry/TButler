package com.davidparry.twitter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public interface ButlerActivity {

	static final String TWITTER_SOURCE ="source:TButler ";
	ProgressDialog getDialog(String title,String msg);
	String getTextFieldValue(int id);
	void closeDialog();
	Context getContext();
	void runActivity(Intent intent);
	
}
