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

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.davidparry.twitter.common.ActivityHelper;
import com.davidparry.twitter.common.ServicePreferences;
import com.davidparry.twitter.common.TwitterResult;
import com.davidparry.twitter.common.Util;
import com.davidparry.twitter.exception.QueryValidationException;
import com.davidparry.twitter.listeners.buttons.ListTweetsOnClickListener;
import com.davidparry.twitter.twitter4j.TwitterQuery;
import com.davidparry.twitter.widgets.OptionMenu;

public class ServiceActivity extends Activity implements ButlerActivity,TwitterPersistence{
	private static final String TAG = "ServiceActivity";
	private ProgressDialog pd;
	private ServicePreferences prefs;
	private ActivityHelper helper;


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	    OptionMenu omenu = new OptionMenu();
	    return omenu.setupOptionMenu(menu, this);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service);
		prefs = new ServicePreferences(PreferenceManager.getDefaultSharedPreferences(this));
		helper = new ActivityHelper(this);
		String ands = prefs.getAndTerms();
		String ors = prefs.getOrTerms();
		String exc = prefs.getExcludeTerms();

		EditText exctext = (EditText) findViewById(R.id.exc_search_terms);
		exctext.getEditableText().append(exc);
		EditText andtext = (EditText) findViewById(R.id.and_search_terms);
		andtext.getEditableText().append(ands);
		EditText ortext = (EditText) findViewById(R.id.or_search_terms);
		ortext.getEditableText().append(ors);
		Spinner serviceTime = (Spinner) findViewById(R.id.service_time);
		serviceTime.setSelection(prefs.getServiceTime());
		serviceTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long id) {
						prefs.setServiceTime(position);
					}
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		final CompoundButton serviceButton = (ToggleButton) findViewById(R.id.service_button);
		if (prefs.isRunning()) {
			serviceButton.setChecked(true);
		} else {
			serviceButton.setChecked(false);
		}
		ImageButton list_button = (ImageButton) findViewById(R.id.back_to_results);
		ListTweetsOnClickListener listListener = new ListTweetsOnClickListener(this);
		list_button.setOnClickListener(listListener);
		    
		
		ImageButton save = (ImageButton) findViewById(R.id.save_service_data);
		save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
				PendingIntent pi = PendingIntent.getBroadcast(getBaseContext(),
						PendingIntent.FLAG_UPDATE_CURRENT, i, 0);
				AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				CompoundButton serviceButton = (ToggleButton) findViewById(R.id.service_button);
				if (serviceButton.isChecked()) {
					try {
						setServiceToRun(v);
					} catch (QueryValidationException e) {
						Log.e(TAG, "Error creating query", e);
						prefs.setMaxId("");
						mgr.cancel(pi);
						prefs.setRunning(false);
						Toast.makeText(v.getContext(),
								"Validation error please fill in a word field",
								Toast.LENGTH_LONG).show();
						serviceButton.setChecked(false);
					}
				} else {
					EditText exctext = (EditText) findViewById(R.id.exc_search_terms);
					final EditText andtext = (EditText) findViewById(R.id.and_search_terms);
					EditText ortext = (EditText) findViewById(R.id.or_search_terms);
					String andString = Util.getTrimDataFromField(andtext);
					String orString = Util.getTrimDataFromField(ortext);
					if (andString.length() <= 0 && orString.length() <= 0) {
						new AlertDialog.Builder(v.getContext()).setIcon(
								R.drawable.icon).setTitle("Validation Error")
								.setMessage("Please type atleast one word.")
								.setPositiveButton("ok",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {
												andtext.requestFocus();
											}
										}).create().show();
					} else {
						prefs.setMaxId("");
						prefs.setExcludeTerms(Util.getTrimDataFromField(exctext));
						prefs.setAndTerms(andString);
						prefs.setOrTerms(orString);
						Toast.makeText(v.getContext(),
								"Service setting saved.", Toast.LENGTH_SHORT)
								.show();
					}
				}
			}
		});

		serviceButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
				PendingIntent pi = PendingIntent.getBroadcast(getBaseContext(),
						PendingIntent.FLAG_UPDATE_CURRENT, i, 0);
				AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				if (isChecked) {
					try {
						setServiceToRun(buttonView);
					} catch (QueryValidationException e) {
						Log.e(TAG, "Error creating query", e);
						prefs.setMaxId("");
						mgr.cancel(pi);
						prefs.setRunning(false);
						new AlertDialog.Builder(buttonView.getContext())
								.setIcon(R.drawable.icon).setTitle(
										"Validation Error").setMessage(
										"Please type atleast one word.")
								.setPositiveButton("ok",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {
												EditText andtext = (EditText) findViewById(R.id.and_search_terms);
												andtext.requestFocus();
											}
										}).create().show();
						serviceButton.setChecked(false);
					}
				} else {
					serviceButton.setChecked(false);
					prefs.setMaxId("");
					prefs.setRunning(false);
					mgr.cancel(pi);
				}
			}
		});
	}

	protected void setServiceToRun(View view)
			throws QueryValidationException {
		Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(getBaseContext(),
				PendingIntent.FLAG_UPDATE_CURRENT, i, 0);
		AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		prefs.setMaxId("");
		EditText exctext = (EditText) findViewById(R.id.exc_search_terms);
		EditText andtext = (EditText) findViewById(R.id.and_search_terms);
		EditText ortext = (EditText) findViewById(R.id.or_search_terms);
		prefs.setExcludeTerms(Util.getTrimDataFromField(exctext));
		prefs.setAndTerms(Util.getTrimDataFromField(andtext));
		prefs.setOrTerms(Util.getTrimDataFromField(ortext));
		// need to run query here
		TwitterQuery twitterQuery = new TwitterQuery(prefs.getAndTerms(),prefs.getOrTerms(),prefs.getExcludeTerms());
		twitterQuery.validate();
		long timevalue = getTimeValue();
		mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock
				.elapsedRealtime(), timevalue, pi);
		Toast.makeText(view.getContext(), "TButler service running...",
				Toast.LENGTH_SHORT).show();
		prefs.setRunning(true);
		Log.d(TAG, "registered with notifier for time:" + timevalue);
	}

	protected long getTimeValue() {
		int timeindex = prefs.getServiceTime();
		long timevalue = AlarmManager.INTERVAL_DAY;
		if (timeindex == 0) {
			timevalue = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
			Log.d(TAG, "registered time: INTERVAL_FIFTEEN_MINUTES");
		} else if (timeindex == 1) {
			timevalue = AlarmManager.INTERVAL_HALF_HOUR;
			Log.d(TAG, "registered time: INTERVAL_HALF_HOUR");
		} else if (timeindex == 2) {
			timevalue = AlarmManager.INTERVAL_HOUR;
			Log.d(TAG, "registered time: INTERVAL_HOUR");
		} else if (timeindex == 3) {
			timevalue = AlarmManager.INTERVAL_HALF_DAY;
			Log.d(TAG, "registered time: INTERVAL_HALF_DAY");
		} else if (timeindex == 4) {
			timevalue = AlarmManager.INTERVAL_DAY;
			Log.d(TAG, "registered time: INTERVAL_DAY");
		}
		return timevalue;
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

	public void runActivity(Intent intent) {
		startActivity(intent);
	}

	public TwitterResult readTweets() throws ButlerException {
		return helper.readTweets();
	}

	public void writeTweets(TwitterResult result) throws ButlerException {
		helper.writeTweets(result);
	}

}