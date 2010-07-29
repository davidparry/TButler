package com.davidparry.twitter;

import twitter4j.QueryResult;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;

import com.davidparry.twitter.analytics.ButlerAnalytics;
import com.davidparry.twitter.common.ActivityHelper;
import com.davidparry.twitter.common.TwitterResult;
import com.davidparry.twitter.listeners.buttons.BasicSearchOnClickListener;
import com.davidparry.twitter.widgets.OptionMenu;

public class ButlerTabActivity extends TabActivity implements ButlerActivity,TwitterPersistence {
	private static final String tag = "TabIndexActivity";
	private ButlerAnalytics analytics;
	private ActivityHelper helper;
	private ProgressDialog pd;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new ActivityHelper(this);
        helper.showToastMessage();
        analytics = new ButlerAnalytics();
        analytics.startTracking(this);
        TabHost tabHost = getTabHost();
		LayoutInflater.from(this).inflate(R.layout.tabindex, tabHost.getTabContentView(), true);
	    tabHost.addTab(tabHost.newTabSpec("Basic")
	                .setIndicator("basic search",new BitmapDrawable(BitmapFactory.decodeResource(this.getResources(), android.R.drawable.ic_menu_search)))
	                .setContent(R.id.basic_view));
	    tabHost.addTab(tabHost.newTabSpec("Advanced")
                .setIndicator("advanced",new BitmapDrawable(BitmapFactory.decodeResource(this.getResources(), android.R.drawable.ic_menu_search)))
                .setContent(R.id.basic_advanced));
	    ImageButton basic_search = (ImageButton) findViewById(R.id.basic_search_button);
	    basic_search.setOnClickListener(new BasicSearchOnClickListener(this));
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return new OptionMenu().setupOptionMenu(menu, getBaseContext());
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(tag, "onDestroy()");
		analytics.stop();
	}

	public ProgressDialog getDialog(String title,String msg) {
		if(pd == null){
			pd = new ProgressDialog(this);
			pd.setIcon(R.drawable.icon);
			pd.setIndeterminate(true);
		}
		pd.setMessage(msg);
		pd.setTitle(title);
		return pd;
	}

	public void closeDialog(){
		if(pd != null){
			pd.cancel();
		}
	}
	
	public String getTextFieldValue(int id) {
		String msg = "";
		final EditText text = (EditText) this.findViewById(id);
		if(text != null){
			msg =text.getText().toString();
		}
		return msg;
	}

	public void writeTweets(TwitterResult result) throws ButlerException{
		helper.writeTweets(result);
		
	}

	public TwitterResult readTweets() throws ButlerException {
		return helper.readTweets();
	}

	public Context getContext() {
		return this;
	}

	public void runActivity(Intent intent) {
		startActivity(intent);
		
	}
	
	
	
}