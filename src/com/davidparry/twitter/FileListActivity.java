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

import java.io.File;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.davidparry.twitter.common.Util;

public class FileListActivity extends ListActivity {
	
	Menu optionMenu;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}
		

		

	    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
     	Toast.makeText( l.getContext().getApplicationContext(), "Retrieving file "+ l.getItemAtPosition(position) , Toast.LENGTH_SHORT).show();
     	Intent sti = new Intent();
     	sti.putExtra("com.davidparry.twitter.html", Util.getFileContents(l.getItemAtPosition(position).toString()));
	 	sti.setClass(getBaseContext(),ItemViewActivity.class);
	 	startActivity(sti);
		
	}

		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        	
	        // Use an existing ListAdapter that will map an array
	        // of strings to TextViews
	        String[] files = getFiles();
	        if(files == null || files.length <=0){
	        	files = new String[1];
	        	files[0] = "There are no saved Tweets please return back and complete a search. You then must do a long click on a Tweet to save it.";  
	        }	
	        ListAdapter ad = new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1, files);
	        
	        setListAdapter(ad);
	        getListView().setTextFilterEnabled(true);
	    }

	    public String[] getFiles() {
	    	String[] list = {};
	    	File[] files = Util.getItemsFromTButlerFolder();
	    	if(files != null && files.length > 0){
	    		list = new String[files.length];
	    		for(int i=0; i<files.length; i++){
	    			list[i] = files[i].getAbsolutePath();
	    		}
	    	}
	    	return list;
	    }
		
		
}
