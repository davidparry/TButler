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

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import android.view.View;
import android.view.View.OnClickListener;

import com.davidparry.twitter.ButlerActivity;
import com.davidparry.twitter.twitter4j.ButlerTwitterAdapter;

public class NextPageOnClickListener implements OnClickListener {
	private ButlerActivity ba;
	private String nexturl;
	public NextPageOnClickListener(ButlerActivity ba,String nexturl){
		this.ba = ba;
		this.nexturl = nexturl;
	}
	

	public void onClick(View v) {
		 ButlerTwitterAdapter adapter = new ButlerTwitterAdapter(this.ba);
		 ba.getDialog("Next Page", "Working ...").show();
		    //try {
		    	//need to fix twitter4J to use nextId to go to next page
		    	AsyncTwitter twitter = new AsyncTwitterFactory(adapter).getInstance();
				twitter.next(nexturl);
				//query.set
			//} catch (QueryValidationException e) {
			//	Toast.makeText(this.ba.getContext(),e.getMessage() ,Toast.LENGTH_LONG).show();
			//}
		
	
	}

}
