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

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnLongClickListener;

import com.davidparry.twitter.R;
import com.davidparry.twitter.common.Tweet;
import com.davidparry.twitter.listeners.buttons.ActionButtonDialogOnClickListener;

public class StatusLongClickListener implements OnLongClickListener {

	private Tweet tweet;
	public StatusLongClickListener(Tweet tweet){
		this.tweet = tweet;
	}
	
	public boolean onLongClick(View v) {
		final CharSequence[] items = {"Copy","Email","Save"};
		AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
		builder.setTitle("Select an Action");
		builder.setIcon(R.drawable.icon);
		builder.setItems(items, new ActionButtonDialogOnClickListener(items,this.tweet));
		AlertDialog alert = builder.create();
		alert.show();
		return true;
	}
	
	
	
}
