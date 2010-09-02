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
package com.davidparry.twitter.common;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ServicePreferences {
	private SharedPreferences pref;
	private Editor edit;
	public ServicePreferences(SharedPreferences preferences){
		this.pref = preferences;
		edit = pref.edit();
	}
	
	public boolean isRunning(){
		return pref.getBoolean("service_run", false);
	}
	public void setRunning(boolean flag){
		edit.putBoolean("service_run", flag);
		edit.commit();
	}
	public String getAndTerms(){
		return pref.getString("service_andTerms", "");
	}
	public void setAndTerms(String terms){
		edit.putString("service_andTerms", terms);
		edit.commit();
	}
	public String getOrTerms(){
		return pref.getString("service_orTerms", "");
	}
	public void setOrTerms(String terms){
		edit.putString("service_orTerms", terms);
		edit.commit();
	}
	public String getExcludeTerms(){
		return pref.getString("service_excludeTerms", "");
	}
	public void setExcludeTerms(String terms){
		edit.putString("service_excludeTerms", terms);
		edit.commit();
	}
	public int getServiceTime(){
		return pref.getInt("service_time", 3);
	}
	public void setServiceTime(int time){
		edit.putInt("service_time", time);
		edit.commit();
	}
	public String getMaxId(){
		return pref.getString("service_maxId", "");
	}
	public void setMaxId(String maxid){
		edit.putString("service_maxId", maxid);
		edit.commit();
	}
}
