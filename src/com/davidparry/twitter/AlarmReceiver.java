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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	 private static final String TAG = "AlarmReceiver";
		
	public AlarmReceiver() {
		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "inside onReceive");
		//SharedPreferences activityPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		//SharedPreferences butler =context.getSharedPreferences("TWITTER_BUTLER", 0);
		/**
		TweetResult result = new TweetResult(Util.readTweets(TweetsActivity.getCache()));
		Log.d(TAG, "result toString "+result.getToStringValue());
		TwitterQuery query =new TwitterQuery(activityPrefs,butler);
	    query.setResult(result);
		Log.d(TAG, "starting "+query);
	    try{
		    if(activityPrefs.getBoolean("service_run", false)){
		    	query.validate();
		    	ServiceTweetingThread serviceThread = new ServiceTweetingThread(context,query,butler,activityPrefs);
		    	Thread thread = new Thread(serviceThread);
				thread.start();
		    }
		} catch(Exception er){
			Log.e(TAG,"Error running TButler query",er);
		}
		**/
	}

	
	
	
}
