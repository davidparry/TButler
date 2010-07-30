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
package com.davidparry.twitter.widgets;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.davidparry.twitter.InfoActivity;

public class OptionMenu {

	
	public boolean setupOptionMenu(Menu menu, Context baseContext){
		menu.add(Menu.FIRST,Menu.FIRST,Menu.FIRST,"info");
		menu.add(Menu.FIRST,Menu.FIRST,2,"saved tweets");
		menu.add(Menu.FIRST,Menu.FIRST,3,"preferences");
		menu.add(Menu.FIRST,Menu.FIRST,4,"service");
		MenuItem mi = menu.getItem(0);
		Intent res = new Intent();
		res.setClass(baseContext,InfoActivity.class);
		mi.setIntent(res);
		MenuItem st = menu.getItem(1);
		Intent sti = new Intent();
		//sti.setClass(baseContext,FileListActivity.class);
		st.setIntent(sti);
		MenuItem st3 = menu.getItem(2);
		Intent sti3 = new Intent();
		//sti3.setClass(baseContext, TweetPreferenceActivity.class);
		st3.setIntent(sti3);
		MenuItem st4 = menu.getItem(3);
		Intent sti4 = new Intent();
		//sti4.setClass(baseContext, ServiceActivity.class);
		st4.setIntent(sti4);
		mi.setIcon(android.R.drawable.ic_menu_view);
		st3.setIcon(android.R.drawable.ic_menu_preferences);
		st.setIcon(android.R.drawable.ic_menu_save);
		st4.setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}
	
}
