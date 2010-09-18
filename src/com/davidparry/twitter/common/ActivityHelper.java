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
package com.davidparry.twitter.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.Query;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.davidparry.twitter.ButlerException;
import com.davidparry.twitter.OAUTH;
import com.davidparry.twitter.R;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ActivityHelper {
	private static final String tag = "ActivityHelper";
	private Context activity;
	private static String INTENT_MESSAGE_KEY = "com.davidparry.twitter.message";
	//private static String INTENT_RESULT_KEY = "com.davidparry.twitter.result";
	private static final String VERSION = "13";
	public static final String TWEETS_FILE_PATH = "/sdcard/tbutler.ser";
	private Map<String,String> userIcons = (Map<String,String>) Collections.synchronizedMap(new HashMap<String,String>());
	
	public Map<String,String> getUserIcons() {
		return userIcons;
	}
	public ActivityHelper(Context activity){
		this.activity = activity;
	}
	/**
	 * shows the toast message from the message in the Intent
	 */
	public void showToastMessage(Bundle extras){
		if (extras != null) {
			if (extras.get(INTENT_MESSAGE_KEY) != null) {
				Toast.makeText(this.activity,extras.getString(INTENT_MESSAGE_KEY) , Toast.LENGTH_LONG);
			}
		}
	}
	
	public void initProperties(Query query){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.activity);
		query.setLang(prefs.getString("lang_key", "en"));
		query.setRpp(Integer.parseInt(prefs.getString("tweetrpp", "20")));
		
	}
	
	public static String writeItemToFile(Item item) {
		FileWriter f = null;
		String path = null;
		try {
			File file = new File(createTbutlerFolder() + item.getFileName()
					+ ".html");
			Log.d(tag, "file to be written:" + file);
			if (!file.exists()) {
				file.createNewFile();
			}
			f = new FileWriter(file, false);
			f.write(item.toFileString());
			f.flush();
			path = file.getAbsolutePath();
		} catch (Exception er) {
			Log.e(tag, "Error writing file ", er);
		} finally {
			try {
				f.close();
			} catch (Exception e) {
			}
		}
		return path;
	}
	
	
	public boolean whatsNew() {
		if(VERSION.equals(PreferenceManager.getDefaultSharedPreferences(this.activity).getString("whatsnew", ""))) {
			return false;
		} else {
			return true;
		}
	}
	
	public void showWhatsNewDialog() {
		Log.d(tag, "showWhatsNewDialog() version value "+PreferenceManager.getDefaultSharedPreferences(this.activity).getString("whatsnew", ""));
		if(whatsNew()){
			LayoutInflater factory = LayoutInflater.from(this.activity);
	        final View textEntryView = factory.inflate(com.davidparry.twitter.R.layout.whats_new, null);
	        new AlertDialog.Builder(this.activity).setIcon(R.drawable.icon).setTitle("What's New").setView(textEntryView).setPositiveButton("Done",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface d, int which) {
							Dialog dialog = (Dialog) d;
							SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(dialog.getContext());
							Editor edit = prefs.edit();
					        edit.putString("whatsnew", VERSION);
					        edit.commit();
						}
					}).setNegativeButton("Email Me",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							AlertDialog d = (AlertDialog) dialog;
							Toast.makeText( d.getContext().getApplicationContext(), "Email application starting..." , Toast.LENGTH_SHORT).show();
				        	Log.d(tag, "Sending email "); 
				        	Intent emailIntent = new Intent(Intent.ACTION_SEND);
				        	emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Enhancement/Bug Report for TButler");
				        	emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				        	emailIntent.putExtra(Intent.EXTRA_TEXT, "");
				        	String[] tos = new String[]{"d.dry.parry@gmail.com"};
				        	emailIntent.putExtra(Intent.EXTRA_EMAIL, tos);
				        	emailIntent.setType("message/rfc822");
				        	try {
								d.getContext().getApplicationContext().startActivity(emailIntent);
							} catch (Exception e) {
								Log.e(tag, "Error starting email application ",e);
							}
						}
					}).show();
	        }
   }
	
	public synchronized void writeTweets(TwitterResult result) throws ButlerException {
		if(checkState()){
			Log.d(tag,"inside writeTweets");
			try{
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.activity);
				int totalSize =  Integer.parseInt(prefs.getString("tweetcache", "100"));
					
				TwitterResult old = readTweets();
					if(hasTweets(old)){
						if(hasTweets(result)){
							List<Tweet> oldOnes = old.getTweets();
							for(Tweet t: result.getTweets()){
								oldOnes.add(0,t);
							}
							result.getTweets().clear();
							result.getTweets().addAll(oldOnes);
						} 
					}
					if(result.getTweets().size() > totalSize){
						//List<Tweet> copy = Collections.unmodifiableList(result.getTweets());
						List<Tweet> tmp = new ArrayList<Tweet>(result.getTweets());
						Collections.copy(tmp, result.getTweets());
						result.getTweets().clear();
						result.getTweets().addAll(tmp.subList(0, totalSize));
					}
					writeXStreamObject(result);
				} catch(Exception er){
					Log.e(tag, "error in adding tweets ", er);
				}
		}
	}
	public TwitterResult readTweets(){
		if(checkState()){
			return readXStreamObject();
		}
		return null;
	}
	public static String createTbutlerFolder() {
		String folder = "/sdcard/tbutler";
		if(checkState()){
			try {
				File file = new File(folder);
				if (!file.exists()) {
					file.mkdir();
				}
			} catch (Exception er) {
				Log.e(tag, "error creating folder", er);
			}
		}
		return folder + "/";
	}
	
	public static boolean checkState(){
		boolean mExternalStorageAvailable = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    mExternalStorageAvailable = true;
		}
		return mExternalStorageAvailable;
	}
	
	private synchronized static TwitterResult readXStreamObject(){
		  Log.d(tag, "Inside readXStream");
		  XStream xs = new XStream(new DomDriver());
		  TwitterResult result = new TwitterResult();
	        try {
	            FileInputStream fis = new FileInputStream(TWEETS_FILE_PATH);
	            Log.d(tag, "FileInputStream"+fis+"  xs"+xs);
	            xs.fromXML(fis, result);
	            Log.d(tag, "results "+result);
		    } catch (FileNotFoundException ex) {
	            Log.e(tag, "Error reading XStreamFile", ex);
	            removeFile();
		    }
	        Log.d(tag, "Result"+result);
	        return result;
	}
	private static synchronized void removeFile(){
		try{
			File file = new File(TWEETS_FILE_PATH);
	        if(file.exists()){
	        	file.delete();
	        }
		}catch(Exception er){
			Log.e(tag,"Error removing file",er);
		}
	}
	
	
	private static synchronized void writeXStreamObject(TwitterResult result) throws ButlerException{
		 XStream xs = new XStream();
	        try {
	            FileOutputStream fs = new FileOutputStream(TWEETS_FILE_PATH);
	            xs.toXML(result, fs);
	        } catch (FileNotFoundException e) {
	           Log.e(tag, "Error writing XStream File", e);
	           removeFile();
	           throw new ButlerException(e);
	        }
	}
	
	private static boolean hasTweets(TwitterResult result){
		if(result != null && result.getTweets() != null && result.getTweets().size()>0){
			return true;
		} else {
			return false;
		}
	}

	public boolean isAuthorized(){
		SharedPreferences prefs = activity.getSharedPreferences("TWITTER_BUTLER", Context.MODE_PRIVATE);
		if(prefs.getString(OAUTH.USER_TOKEN, null) == null || prefs.getString(OAUTH.USER_SECRET, null) == null) {
			return false;
		}
		return true;
	}
		
}
