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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.davidparry.twitter.listeners.buttons.ButtonPreference;
import com.davidparry.twitter.widgets.OptionMenu;
import com.davidparry.twitter.widgets.TwitterCheckBox;

public class TweetPreferenceActivity extends PreferenceActivity {
	private static final String TAG = "TweetPreferenceActivity";
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		OptionMenu menuOne = new OptionMenu();
		menuOne.setupOptionMenu(menu,this);
		return true;
	}  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "Inside Preferences");
		PreferenceScreen ps = createPreferenceHierarchy();
		setPreferenceScreen(ps);
    }

   

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
    }

	private PreferenceScreen createPreferenceHierarchy() {
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
       
        PreferenceCategory contentItem = new PreferenceCategory(this);
        contentItem.setTitle(R.string.content_terms_cat);
        root.addPreference(contentItem);
        
        // Edit text preference
        EditTextPreference resultPref = new EditTextPreference(this);
        resultPref.setDialogTitle(R.string.result_dialog_title);
        resultPref.setKey("tweetcache");
        resultPref.setTitle(R.string.result_title);
        resultPref.setSummary(R.string.result_summary);
        resultPref.setDefaultValue("100");
        resultPref.setDialogIcon(R.drawable.icon);
        resultPref.getEditText().setRawInputType(InputType.TYPE_CLASS_NUMBER);
        contentItem.addPreference(resultPref);
        
        // Edit text preference
        EditTextPreference rppPref = new EditTextPreference(this);
        rppPref.setDialogTitle(R.string.rpp_dialog_title);
        rppPref.setKey("tweetrpp");
        rppPref.setTitle(R.string.rpp_title);
        rppPref.setSummary(R.string.rpp_summary);
        rppPref.setDefaultValue("10");
        rppPref.setDialogIcon(R.drawable.icon);
        rppPref.getEditText().setRawInputType(InputType.TYPE_CLASS_NUMBER);
        contentItem.addPreference(rppPref);
        
        ListPreference langPref = new ListPreference(this);
        langPref.setEnabled(true);
        langPref.setSelectable(true);
        langPref.setPersistent(true);
        langPref.setEntries(R.array.langvalues);
        langPref.setEntryValues(R.array.langids);
        langPref.setDialogTitle(R.string.lang_dialog_title);
        langPref.setKey("lang_key");
        langPref.setTitle(R.string.lang_title);
        langPref.setSummary(R.string.lang_summary);
        langPref.setDefaultValue("en");
        langPref.setDialogIcon(R.drawable.icon);
        contentItem.addPreference(langPref);
        
       
       
        //TODO: stsill need to check if OATH has been established
        //if(PreferenceHelper.isAuthorized(prefs)) {
        	ButtonPreference resetUserToken = new ButtonPreference(this,R.layout.resetusertoken,new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(),"Authorization Tokens from Twitter have been reset.", Toast.LENGTH_LONG);
					View button = findViewById(R.id.resetUserTokens);
					button.setVisibility(View.INVISIBLE);
				}
			});
        	contentItem.addPreference(resetUserToken);
        //}
        
        
        TwitterCheckBox start = new TwitterCheckBox(this);
        contentItem.addPreference(start);
        PreferenceCategory action = new PreferenceCategory(this);
	    action.setTitle("");
	    root.addPreference(action);
		
	    
	    
		
        /*
         * You can add more preferences to screenPref that will be shown on the
         * next screen.
         */

                // Intent preference
        PreferenceScreen intentPref = getPreferenceManager().createPreferenceScreen(this);
        intentPref.setIntent(new Intent().setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse("http://www.android.com")));
        intentPref.setTitle("Title for Intent ");
        intentPref.setSummary("Intent Summary");
        //launchPrefCat.addPreference(intentPref);

        return root;
    }
	
	
	
	
}
