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
package com.davidparry.twitter.threads;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.davidparry.twitter.ButlerActivity;
import com.davidparry.twitter.ButlerException;
import com.davidparry.twitter.TweetList;
import com.davidparry.twitter.TwitterPersistence;
import com.davidparry.twitter.common.TwitterResult;

public class SDCardIOReadThread implements Runnable {
	private static final String tag = "SDCardIOThread";
	private TwitterPersistence persistor;
	private ButlerActivity activity;
	public SDCardIOReadThread(TwitterPersistence persistor,ButlerActivity activity){
		this.persistor = persistor;
		this.activity = activity;
	}
	public void run() {
		Message handlerMessage = new Message();
		try {
			handlerMessage.obj = persistor.readTweets();
		} catch (ButlerException e) {
			Log.e(tag, "Unable read Tweets from SDCard.", e);
			handlerMessage.arg1 = 5;
			handlerMessage.obj = e.toString();
		}
		handler.sendMessage(handlerMessage);
	}
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Object obj = msg.obj;
			if(msg.arg1 == 5){
				Toast.makeText(activity.getContext(),"There are no Tweets saved to your SDCard\n"+msg.obj, Toast.LENGTH_LONG);
			} else if(obj != null){
				Intent intent = new Intent();
				intent.setClass(activity.getContext(), TweetList.class);
				intent.putExtra("com.davidparry.twitter.tweets", (TwitterResult)obj);
				activity.runActivity(intent);
			}else {
				Toast.makeText(activity.getContext(),"There are no Tweets saved to your SDCard", Toast.LENGTH_LONG);
			}
			activity.closeDialog();
		}
	};
}
