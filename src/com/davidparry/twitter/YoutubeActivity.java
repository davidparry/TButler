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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.davidparry.twitter.widgets.OptionMenu;


public class YoutubeActivity extends Activity {
	private static final String tag = "YoutubeActivity";
	private VideoView mVideoView;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return new OptionMenu().setupOptionMenu(menu, getBaseContext());
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.introyoutube);
		try{
			Bundle extras = getIntent().getExtras();
			String url = extras.getString("com.davidparry.twitter.youtube");
			
			
			 mVideoView = (VideoView) findViewById(R.id.intro_view);

		        if (url == "") {
		            // Tell the user to provide a media file URL/path.
		            Toast.makeText(
		                    this,
		                    "Please contact technical support @ d.dry.parry@gmail.com",
		                    Toast.LENGTH_LONG).show();

		        } else {

		            /*
		             * Alternatively,for streaming media you can use
		             * mVideoView.setVideoURI(Uri.parse(URLstring));
		             */
		           // mVideoView.setVideoPath(path);
		        	mVideoView.setVideoURI(Uri.parse(url));
		            mVideoView.setMediaController(new MediaController(this));
		            mVideoView.requestFocus();
		            mVideoView.start();
		        }
		     	        
		} catch(Exception er){
			Log.e(tag,"Error loading Introdcution ",er);
			this.finish();
		}
		
	}

	
}