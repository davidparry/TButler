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
package com.davidparry.twitter.listeners.buttons;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.text.ClipboardManager;
import android.util.Log;
import android.widget.Toast;

import com.davidparry.twitter.common.Tweet;

public class ActionButtonDialogOnClickListener implements OnClickListener {
	final CharSequence[] items;
	private Tweet tweet;
	private static final String TAG = "ActionButtonDialogOnClickListener";
	
	public ActionButtonDialogOnClickListener(final CharSequence[] items, Tweet tweet){
		this.items =items;
		this.tweet = tweet;
	}
	
	public void onClick(DialogInterface dialog, int which) {
		AlertDialog d = (AlertDialog) dialog;
        try{
            if("Save".equalsIgnoreCase(items[which].toString())){
	        	//String file = Util.writeItemToFile(this.tweet);
	        	//Toast.makeText( d.getContext().getApplicationContext(), "Item saved on sdcard location" + file , Toast.LENGTH_LONG).show();
	        } else if("Email".equalsIgnoreCase(items[which].toString())){
	        	Toast.makeText( d.getContext().getApplicationContext(), "Email application starting..." , Toast.LENGTH_SHORT).show();
	        	Log.d(TAG, "Sending email "+ this.tweet.getFromUser()); 
	        	Intent emailIntent = new Intent(Intent.ACTION_SEND);
	        	emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Check out this Tweet from "+this.tweet.getFromUser());
	        	emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	emailIntent.putExtra(Intent.EXTRA_TEXT, this.tweet.getText());
	        	emailIntent.setType("message/rfc822");
	        	d.getContext().getApplicationContext().startActivity(emailIntent);
	        } else if("Copy".equalsIgnoreCase(items[which].toString())){
	        	ClipboardManager clipboard = 
	        	      (ClipboardManager) d.getContext().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE); 
	        	clipboard.setText("Tweet:"+this.tweet.getText()+"\nAuthor:"+this.tweet.getFromUser());
	         	Toast.makeText(d.getContext(), "Tweet copied to clipboard.", Toast.LENGTH_SHORT).show();
	    	    
	        }  else if ("SMS".equalsIgnoreCase(items[which].toString())) {
	        	Toast.makeText(d.getContext(), "SMS clicked", Toast.LENGTH_SHORT).show();
	        	
	        }
        } catch(Exception er) {
        	Log.e(TAG, "error executing action "+items[which].toString() , er);
        }
        
	
	}

}
