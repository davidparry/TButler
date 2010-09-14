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

import twitter4j.Query;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.davidparry.twitter.common.ActivityHelper;
import com.davidparry.twitter.common.ServicePreferences;
import com.davidparry.twitter.common.TwitterResult;
import com.davidparry.twitter.exception.QueryValidationException;
import com.davidparry.twitter.twitter4j.TwitterQuery;

public class AlarmReceiver extends BroadcastReceiver {
	private static final String TAG = "AlarmReceiver";
	private NotificationManager mNM;

	public AlarmReceiver() {

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "inside onReceive");
		SharedPreferences activityPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		ServicePreferences prefs = new ServicePreferences(activityPrefs);
		TwitterQuery twitterQuery = new TwitterQuery(prefs.getAndTerms(), prefs
				.getOrTerms(), prefs.getExcludeTerms());
		try {
			twitterQuery.validate();
			ActivityHelper helper = new ActivityHelper(context);
			String q = twitterQuery.getQuery();
			Query query = new Query(q);
			helper.initProperties(query);
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.search(query);
			TwitterResult tweets = new TwitterResult(twitter.search(query));
			helper.writeTweets(tweets);
			showNotification(tweets, context);
		} catch (QueryValidationException e) {
			Log.e(TAG, "Error in validate", e);
		} catch (TwitterException er) {
			Log.e(TAG, "Error querying twitter ", er);
		} catch (ButlerException et) {
			Log.e(TAG, "Error writing tweets to disk ", et);
		}
	}

	private void showNotification(TwitterResult tweets, Context context) {
		mNM = (NotificationManager) context
				.getSystemService(Activity.NOTIFICATION_SERVICE);

		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(
				com.davidparry.twitter.R.drawable.icon, "New Tweets", System
						.currentTimeMillis());
		// long[] vibrate = {0,100,200,300,400,200};
		// notification.vibrate = vibrate;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		Log.d(TAG, "notification " + notification);

		Intent intent = new Intent(context, TweetList.class);
		intent.setClass(context, TweetList.class);
		intent.putExtra("com.davidparry.twitter.tweets", tweets);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				intent, 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(context, "New Tweets",
				"Click to launch TButler", contentIntent);

		// Send the notification.
		// We use a string id because it is a unique number. We use it later to
		// cancel.
		try {
			mNM.notify(com.davidparry.twitter.R.string.butler_started,
					notification);
		} catch (Exception e) {
			Log.e(TAG, "error with notifiy", e);
		}
		Log.d(TAG, "after notify ");

	}

}
