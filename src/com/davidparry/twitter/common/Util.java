package com.davidparry.twitter.common;

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
	

	
}
