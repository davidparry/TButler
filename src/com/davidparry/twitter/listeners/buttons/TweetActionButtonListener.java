package com.davidparry.twitter.listeners.buttons;

import java.util.LinkedList;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.davidparry.twitter.ButlerActivity;
import com.davidparry.twitter.OAUTH;
import com.davidparry.twitter.R;
import com.davidparry.twitter.oath.TButlerOATH;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class TweetActionButtonListener implements OnClickListener,TButlerOATH {
	private static final String TAG = "TweetActionButtonListener";
	private ButlerActivity ba;
	DefaultHttpClient mClient;
	private OAuthConsumer mConsumer = null;
	private OAuthProvider mProvider = null; 
	GoogleAnalyticsTracker tracker =null;
    
	public TweetActionButtonListener(ButlerActivity ba){
		this.ba = ba;
		this.mClient = new DefaultHttpClient();
		this.mConsumer = new CommonsHttpOAuthConsumer(
				CONSUMER_KEY, 
				CONSUMER_SECRET);
 
		this.mProvider = new CommonsHttpOAuthProvider (
				OAUTH.TWITTER_REQUEST_TOKEN_URL, 
				OAUTH.TWITTER_ACCESS_TOKEN_URL,
				OAUTH.TWITTER_AUTHORIZE_URL);
	//	this.tracker = tracker;
		
	}
	public void onClick(View v) {
		/** still need to add tracker for google anaylics
		this.tracker.trackEvent(
		            "Clicks",  // Category
		            "Button",  // Action
		            "clicked", // Label
		            3);       // Value
		**/            
		ba.getDialog("Sending Tweet", "Data bits moving...");
		String tweet =  ba.getTextFieldValue(R.id.tweet_msg);
		if(tweet.length() <= 0){
			ba.closeDialog();
			new AlertDialog.Builder(v.getContext())
            .setIcon(R.drawable.icon)
            .setTitle("Validation Error").setMessage("Please type atleast one word.")
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	ba.setFocusOnItem(R.id.tweet_msg);
                }
            }).create().show();
		} else {
			JSONObject jso = null;
			try {
				authorizeCheckBeforeSending();
				// It turns out this was the missing thing to making standard Activity launch mode work
				mProvider.setOAuth10a(true);
				HttpPost post = new HttpPost("http://twitter.com/statuses/update.json");
				LinkedList<BasicNameValuePair> out = new LinkedList<BasicNameValuePair>();
				out.add(new BasicNameValuePair("status", ba.getTextFieldValue(R.id.tweet_msg)));
				post.setEntity(new UrlEncodedFormEntity(out, HTTP.UTF_8));
				post.setParams(getParams());
				// sign the request to authenticate
				mConsumer.sign(post);
				String response = mClient.execute(post, new BasicResponseHandler());
				jso = new JSONObject(response);
				String msg = "";
				if(jso != null){
					msg = jso.getString("text");
				}
				
				new AlertDialog.Builder(v.getContext())
	            .setIcon(R.drawable.icon)
	            .setTitle("Post was successful").setMessage("Following Status Posted:"+msg)
	            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                	ba.setFocusOnItem(R.id.tweet_msg);
	                }
	            }).create().show();
				ba.clearItem(R.id.tweet_msg);
			} catch (AuthorizeException au){	
				Toast.makeText(v.getContext(), "You have not authorized Tbutler with Twitter please do this." , Toast.LENGTH_LONG);
				Intent sti = new Intent();
				sti.setClass(v.getContext(),OAUTH.class);
				v.getContext().startActivity(sti);
			} catch (Exception e) {
				new AlertDialog.Builder(v.getContext())
	            .setIcon(R.drawable.icon)
	            .setTitle("Error posting to Twitter").setMessage("Twitter had an error:\n"+e)
	            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                	ba.setFocusOnItem(R.id.tweet_msg);
	                }
	            }).create().show();
			} finally {
				ba.closeDialog();
				//this.tracker.dispatch();
			}
		}
	}
	
	public HttpParams getParams() {
		// Tweak further as needed for your app
		HttpParams params = new BasicHttpParams();
		// set this to false, or else you'll get an Expectation Failed: error
		HttpProtocolParams.setUseExpectContinue(params, false);
		return params;
	}	
	
	public void authorizeCheckBeforeSending()throws AuthorizeException{
		SharedPreferences prefs = ba.getContext().getSharedPreferences("TWITTER_BUTLER", Context.MODE_PRIVATE);
		String token = prefs.getString(USER_TOKEN, null);
		String secret = prefs.getString(USER_SECRET, null);
		
		if(!(token == null || secret == null)) {
			mConsumer.setTokenWithSecret(token, secret);
		} else {
			throw new AuthorizeException("You have not authorized to Twitter please authoize first");
		}
	}
	
}
