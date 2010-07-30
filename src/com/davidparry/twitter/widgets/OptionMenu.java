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
