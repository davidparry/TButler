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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidparry.twitter.common.TwitterUser;
import com.davidparry.twitter.threads.ProfileUserThread;
import com.davidparry.twitter.widgets.OptionMenu;


public class ProfileActivity extends Activity implements ButlerActivity {
	private static final String TAG = "ProfileActivity";
	private ProgressDialog pd;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		OptionMenu m = new OptionMenu();
		m.setupOptionMenu(menu, this);
		return true;
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		String screenname =(String)getIntent().getExtras().get("com.davidparry.twitter.profile");
		Log.d(TAG, "screenname from intent"+screenname);
		getDialog("Getting Profile", "Working ...").show();
		Thread thread = new Thread(new ProfileUserThread(screenname,this));
		thread.start();	
	}

	
	public void setItems(TwitterUser user){
		try{
			Typeface acmesa = Typeface.createFromAsset(getAssets(), "fonts/acmesa.TTF");
			Drawable drawable = loadImage(user.getProfileUrl());
			ImageView image = (ImageView) findViewById(R.id.profile_icon_id);
			Log.d(TAG, "Image:"+image);
			if(image != null){
				image.setImageDrawable(drawable);
				image.invalidate();
			}
			TextView profileName = (TextView)findViewById(R.id.profile_name);
			profileName.setText(user.getName());
			profileName.setTypeface(acmesa);
			TextView profileLocation = (TextView)findViewById(R.id.profile_location);
			profileLocation.setText(user.getLocation());
			profileLocation.setTypeface(acmesa);
			try{
				TextView profileTweet = (TextView)findViewById(R.id.profile_text);
				String after = URLDecoder.decode(user.getStatus(),"UTF-8"); 
				profileTweet.setText(after);
				profileTweet.setTypeface(acmesa);
			} catch(Exception er){
				Log.e(TAG,"Status empty", er);
			}
			TextView profileTotalFriends = (TextView)findViewById(R.id.profile_total_friends);
			profileTotalFriends.setText(user.getFriendCount()+"");
			profileTotalFriends.setTypeface(acmesa);
			TextView profileDescription = (TextView)findViewById(R.id.profile_desc);
			profileDescription.setText(user.getDescription());
			profileDescription.setTypeface(acmesa);
			TextView profileScreenName = (TextView)findViewById(R.id.profile_screen_name);
			profileScreenName.setText(user.getScreenName());
			profileScreenName.setTypeface(acmesa);
			TextView profileUrl = (TextView)findViewById(R.id.profile_url);
			profileUrl.setText(user.getUrl());
			profileUrl.setTypeface(acmesa);
		} catch(Exception er){
			Log.e(TAG,"Error loading profile ",er);
			this.finish();
		}
	}
	
	
	public Drawable loadImage(URL url){
		Drawable d = null;
		InputStream is = null;
		try {
			is = url.openStream();
			d = Drawable.createFromStream(is, "src");	
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				is.close();
			} catch (IOException e) {}
		}
		return d;
	}


	public void closeDialog() {
		if(pd != null){
			pd.cancel();
		}
	}


	public Context getContext() {
		return this;
	}


	public ProgressDialog getDialog(String title, String msg) {
		if(pd == null){
			pd = new ProgressDialog(this);
			pd.setIcon(R.drawable.icon);
			pd.setIndeterminate(true);
		}
		pd.setMessage(msg);
		pd.setTitle(title);
		return pd;
	}


	public String getTextFieldValue(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean isChecked(int id) {
		// TODO Auto-generated method stub
		return false;
	}


	public void runActivity(Intent intent) {
		// TODO Auto-generated method stub
		
	}


	public void setFocusOnItem(int id) {
		// TODO Auto-generated method stub
		
	}


	public void clearItem(int id) {
		// TODO Auto-generated method stub
		
	}

	
}