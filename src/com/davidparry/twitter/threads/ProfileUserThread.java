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

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.davidparry.twitter.ProfileActivity;
import com.davidparry.twitter.common.TwitterUser;


public class ProfileUserThread implements Runnable {
    private ProfileActivity activity;
    private String screenname;
    private static final String TAG = "ProfileUserThread";
	public ProfileUserThread(String screenname,ProfileActivity activity) {
		this.activity = activity;
		this.screenname = screenname;
	}

	public void run() {
		Message handlerMessage = new Message();
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			handlerMessage.obj = new TwitterUser(twitter.showUser(screenname));
			handler.sendMessage(handlerMessage);
		} catch (Exception e) {
			Log.d(TAG, "Problem getting profile info",e);
			handler.sendEmptyMessage(2);
		} finally{
			activity.closeDialog();
		}
		
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Object obj = msg.obj;
			if(obj != null){
				TwitterUser user = (TwitterUser) obj;
				activity.setItems(user);
			} else {
				Toast.makeText(activity, "Error retrieving Profile Twitter returned 404 for user screenName:"+screenname, Toast.LENGTH_LONG).show();
				activity.finish();
			}
		}
	};
}
