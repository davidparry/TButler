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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.davidparry.twitter.widgets.OptionMenu;

public class InfoActivity  extends Activity {
	private static final String tag = "InfoActivity";
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return new OptionMenu().setupOptionMenu(menu, getBaseContext());
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		 setContentView(R.layout.info);
	         
	        Button intro = (Button)findViewById(R.id.introYouTube);
	        intro.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent sti = new Intent();
					//sti.putExtra("com.davidparry.twitter.youtube","rtsp://v4.cache8.c.youtube.com/CiILENy73wIaGQneWNJuq4D2TxMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp");
					sti.putExtra("com.davidparry.twitter.youtube","rtsp://v4.cache6.c.youtube.com/CiILENy73wIaGQneWNJuq4D2TxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp");
					
					sti.setClass(getBaseContext(),YoutubeActivity.class);
					v.getContext().startActivity(sti);
				}
			});
	        Button intro1 = (Button)findViewById(R.id.introYouTube1);
	        intro1.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent sti = new Intent();
					sti.putExtra("com.davidparry.twitter.youtube","rtsp://v4.cache8.c.youtube.com/CiILENy73wIaGQneWNJuq4D2TxMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp");
					
					sti.setClass(getBaseContext(),YoutubeActivity.class);
					v.getContext().startActivity(sti);
				}
			});
	        Button tweetReg = (Button)findViewById(R.id.tweetYouTube);
	        tweetReg.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent sti = new Intent();
					sti.putExtra("com.davidparry.twitter.youtube","rtsp://v6.cache1.c.youtube.com/CiILENy73wIaGQlx5Q2w5g7jIxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp");
					sti.setClass(getBaseContext(),YoutubeActivity.class);
					v.getContext().startActivity(sti);
				}
			});
	        Button tweetReg1 = (Button)findViewById(R.id.tweetYouTube1);
	        tweetReg1.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent sti = new Intent();
					sti.putExtra("com.davidparry.twitter.youtube","rtsp://v7.cache4.c.youtube.com/CiILENy73wIaGQlx5Q2w5g7jIxMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp");
					sti.setClass(getBaseContext(),YoutubeActivity.class);
					v.getContext().startActivity(sti);
				}
			});
	        
	}

}
