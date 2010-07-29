package com.davidparry.twitter.twitter4j;

import twitter4j.QueryResult;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterMethod;
import android.content.Intent;
import android.util.Log;

import com.davidparry.twitter.ButlerActivity;
import com.davidparry.twitter.TweetList;
import com.davidparry.twitter.TwitterPersistence;
import com.davidparry.twitter.common.TwitterResult;
import com.davidparry.twitter.threads.SDCardIOThread;

public class ButlerTwitterAdapter extends TwitterAdapter {
	private static final String tag = "ButlerTwitterAdapter";
	
	private ButlerActivity activity; 	
	public ButlerTwitterAdapter(ButlerActivity activity) {
		this.activity = activity;
	}
	@Override
	public void searched(QueryResult result) {
		super.searched(result);
		TwitterResult tweets = new TwitterResult(result);
		// return to write the tweets to the screen don't wait for the write to card
		Thread t = new Thread(new SDCardIOThread((TwitterPersistence)activity,tweets));
		t.start();
		activity.closeDialog();
		Intent intent = new Intent();
		intent.setClass(this.activity.getContext(), TweetList.class);
		intent.putExtra("com.davidparry.twitter.tweets", tweets);
		activity.runActivity(intent);
	}
	@Override
	public void onException(TwitterException ex, TwitterMethod method) {
		super.onException(ex, method);
		Log.e(tag, "Error communicating with Twitter", ex);
		activity.closeDialog();
	}
	
	

}
