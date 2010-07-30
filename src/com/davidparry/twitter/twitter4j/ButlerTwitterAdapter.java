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
