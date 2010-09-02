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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.util.Log;
import android.widget.EditText;

public class Util {
	private static final String TAG = "Util";
	private static final String VERSION = "13";
	
	
	public static String getTrimDataFromField(EditText field){
		String value = "";
		if(field != null && field.getText() != null){
			value = field.getText().toString().trim();
		}
		return value;
	}
	public static List<String> getTermsList(String sterms){
		List<String> terms = new ArrayList<String>();
		Log.d(TAG, "terms:" + sterms);
		if (sterms != null && !"".equals(sterms)) {
			sterms = sterms.replace('+', ' ');
			sterms = sterms.replace('-', ' ');
			StringTokenizer tokenizer = new StringTokenizer(sterms, " ");
			while (tokenizer.hasMoreTokens()) {
				terms.add(tokenizer.nextToken());
			}
		}
		return terms;
	}
	

	public static String getFileContents(String filePath) {
		BufferedReader br = null;
		try {
			Log.d(TAG, "readTweets before reader");
			StringBuffer buf = new StringBuffer();
			File file = new File(filePath);
			if (file.exists()) {
				FileReader reader = new FileReader(file);
				Log.d(TAG, "readTweets reader:" + reader);
				if (reader != null) {
					br = new BufferedReader(reader);
					String s;
					if (br != null) {
						while ((s = br.readLine()) != null) {
							buf.append(s);
						}
						if (buf.length() > 0) {
							return buf.toString();
						}
					}
				}
			}
		} catch (Exception er) {
			Log.e(TAG, "Error reading file", er);
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				Log.e(TAG, "Error closing file", e);
			}
		}
		return "<html><body>No File for path:" + filePath + "</body></html>";

	}
	
	public static File[] getItemsFromTButlerFolder() {
		String folder = "/sdcard/tbutler";
		File[] files = null;
		try {
			File file = new File(folder);
			if (file.exists() && file.isDirectory()) {
				files = file.listFiles();
			}
		} catch (Exception er) {
			Log.e(TAG, "error creating folder", er);
		}
		return files;
	}

	
}
