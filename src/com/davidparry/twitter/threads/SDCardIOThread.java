package com.davidparry.twitter.threads;

import android.util.Log;

import com.davidparry.twitter.ButlerException;
import com.davidparry.twitter.TwitterPersistence;
import com.davidparry.twitter.common.TwitterResult;

public class SDCardIOThread implements Runnable {
	private static final String tag = "SDCardIOThread";
	private TwitterPersistence persistor;
	private TwitterResult result;
	public SDCardIOThread(TwitterPersistence persistor,TwitterResult result){
		this.persistor = persistor;
		this.result = result;
	}
	public void run() {
		try {
			persistor.writeTweets(result);
		} catch (ButlerException e) {
			Log.e(tag, "Unable save Tweets to SDCard.", e);
		}

	}

}
