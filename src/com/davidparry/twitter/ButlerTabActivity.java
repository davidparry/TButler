/*
Copyright (c) 2009-2010, David Parry
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the David Parry nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY David Parry ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL David Parry BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.davidparry.twitter;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.davidparry.twitter.analytics.ButlerAnalytics;
import com.davidparry.twitter.common.ActivityHelper;
import com.davidparry.twitter.common.TwitterResult;
import com.davidparry.twitter.listeners.buttons.AdvancedSearchOnClickListener;
import com.davidparry.twitter.listeners.buttons.BasicSearchOnClickListener;
import com.davidparry.twitter.listeners.buttons.ListTweetsOnClickListener;
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
	    ImageButton adv_search = (ImageButton) findViewById(R.id.adv_search_button);
	    adv_search.setOnClickListener(new AdvancedSearchOnClickListener(this));
	    ImageButton menu = (ImageButton) findViewById(R.id.menu);
	    menu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				openOptionsMenu();
			}
		});
	    ImageButton adv_more_button = (ImageButton) findViewById(R.id.adv_more_button);
	    adv_more_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				openOptionsMenu();
			}
		});
	    ImageButton adv_list_button = (ImageButton) findViewById(R.id.adv_list_button);
	    adv_list_button.setOnClickListener(new ListTweetsOnClickListener(this)); 
	    ImageButton list_button = (ImageButton) findViewById(R.id.list_button);
	    list_button.setOnClickListener(new ListTweetsOnClickListener(this));
	    
	    
	    
	    
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

	
	
	public boolean isChecked(int id) {
		RadioButton button = (RadioButton) findViewById(id);
		if(button != null){
			return button.isChecked();
		} else {
			return false;
		}
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