package com.davidparry.twitter.oauth;

import junit.framework.Assert;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.davidparry.twitter.ButlerException;

public class ButlerOauth implements OAUTH {
	
	private static final Uri CALLBACK_URI = Uri.parse("tbutler:///twitt");
	private static final Uri REDIRECT_URI = Uri.parse("http://app.davidparry.com/tbutler.jsp");
	 
	private OAuthConsumer mConsumer = null;
	private OAuthProvider mProvider = null;
	private static final String tag = "ButlerOauth";
	
	
	public ButlerOauth() {
		mConsumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
 
		mProvider = new CommonsHttpOAuthProvider (
				TWITTER_REQUEST_TOKEN_URL, 
				TWITTER_ACCESS_TOKEN_URL,
				TWITTER_AUTHORIZE_URL);
	}
	
	
	public void saveAuthorizationFromUri(Uri uri,SharedPreferences settings) throws ButlerException{
		setTokenWithSecret(settings);
		String otoken = uri.getQueryParameter(OAuth.OAUTH_TOKEN);
		String verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

		// We send out and save the request token, but the secret is not the same as the verifier
		// Apparently, the verifier is decoded to get the secret, which is then compared - crafty
		// This is a sanity check which should never fail - hence the assertion
		try {
			Assert.assertEquals(otoken, mConsumer.getToken());

			// This is the moment of truth - we could throw here
			mProvider.retrieveAccessToken(mConsumer, verifier);
			// Now we can retrieve the goodies
			String token = mConsumer.getToken();
			String secret = mConsumer.getTokenSecret();
			saveAuthInformation(settings, token, secret);
			// Clear the request stuff, now that we have the real thing
			saveRequestInformation(settings, null, null);
		} catch (Exception e) {
			throw new ButlerException(e);
		} 
	}
	
	public void setTokenWithSecret(SharedPreferences settings){
		String token = settings.getString(REQUEST_TOKEN, null);
		String secret = settings.getString(REQUEST_SECRET, null);
		if(!(token == null || secret == null)) {
			mConsumer.setTokenWithSecret(token, secret);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.davidparry.twitter.oauth.OAUTH#retrieveRequestToken()
	 */
	public String retrieveRequestToken() throws ButlerException{
		// need to pass in empty string to Twitter it can not handle redirect url
		try {
			return mProvider.retrieveRequestToken(mConsumer, "");
		} catch (Exception e) {
			throw new ButlerException(e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.davidparry.twitter.oauth.OAUTH#saveRequestInformation(android.content.SharedPreferences, java.lang.String, java.lang.String)
	 */
	public void saveRequestInformation(SharedPreferences settings, String token, String secret) {
		// null means to clear the old values
		SharedPreferences.Editor editor = settings.edit();
		if(token == null) {
			editor.remove(REQUEST_TOKEN);
			Log.d(tag, "Clearing Request Token");
		}
		else {
			editor.putString(REQUEST_TOKEN, token);
			Log.d(tag, "Saving Request Token: " + token);
		}
		if (secret == null) {
			editor.remove(REQUEST_SECRET);
			Log.d(tag, "Clearing Request Secret");
		}
		else {
			editor.putString(REQUEST_SECRET, secret);
			Log.d(tag, "Saving Request Secret: " + secret);
		}
		editor.commit();
 
	}
	/* (non-Javadoc)
	 * @see com.davidparry.twitter.oauth.OAUTH#saveAuthInformation(android.content.SharedPreferences, java.lang.String, java.lang.String)
	 */
	public void saveAuthInformation(SharedPreferences settings, String token, String secret) {
		// null means to clear the old values
	SharedPreferences.Editor editor = settings.edit();
		if(token == null) {
			editor.remove(USER_TOKEN);
			Log.d(tag, "Clearing OAuth Token");
		}
		else {
			editor.putString(USER_TOKEN, token);
			Log.d(tag, "Saving OAuth Token: " + token);
		}
		if (secret == null) {
			editor.remove(USER_SECRET);
			Log.d(tag, "Clearing OAuth Secret");
		}
		else {
			editor.putString(USER_SECRET, secret);
			Log.d(tag, "Saving OAuth Secret: " + secret);
		}
		editor.commit();
 
	}
	public void authorizeCheckBeforeSending(SharedPreferences settings)throws AuthorizeException{
		String token = settings.getString(USER_TOKEN, null); 
		String secret = settings.getString(USER_SECRET,null);
		
		if(!(token == null || secret == null)) {
			mConsumer.setTokenWithSecret(token, secret);
		} else {
			throw new AuthorizeException("You have not been authorized with Twitter please authoize first");
		}
	}
}
