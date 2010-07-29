/**
 * 
 */
package com.davidparry.twitter.listeners.buttons;

import org.apache.commons.lang.StringUtils;

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.Query;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.davidparry.twitter.ButlerActivity;
import com.davidparry.twitter.R;
import com.davidparry.twitter.twitter4j.ButlerTwitterAdapter;

/**
 * @author david
 *
 */
public class BasicSearchOnClickListener implements OnClickListener{
	private static final String tag = "BasicSearchOnClickListener";
	private ButlerActivity ba;
	/**
	 * 
	 */
	public BasicSearchOnClickListener(ButlerActivity ba) {
		this.ba = ba;
	}

	public void onClick(View v) {
		Log.d(tag, "CLicked");
		this.ba.getDialog("Performing basic search","Working please wait ...").show();
		String terms = this.ba.getTextFieldValue(R.id.basic_search_terms);
		if(StringUtils.isNotBlank(terms)){
			    ButlerTwitterAdapter adapter = new ButlerTwitterAdapter(ba);
			    Query query = new Query(terms);
			    AsyncTwitter twitter = new AsyncTwitterFactory(adapter).getInstance();
			    twitter.search(query);
		}
	}

}
