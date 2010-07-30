package com.davidparry.twitter.oauth;

import android.content.SharedPreferences;
import android.net.Uri;

import com.davidparry.twitter.ButlerException;

public interface OAUTH {

	public static final String USER_TOKEN = "user_token";
	public static final String USER_SECRET = "user_secret";
	public static final String REQUEST_TOKEN = "request_token";
	public static final String REQUEST_SECRET = "request_secret";
	public static final String TWITTER_REQUEST_TOKEN_URL = "http://twitter.com/oauth/request_token";
	public static final String TWITTER_ACCESS_TOKEN_URL = "http://twitter.com/oauth/access_token";
	public static final String TWITTER_AUTHORIZE_URL = "http://twitter.com/oauth/authorize";
	public static final String CONSUMER_KEY = "f1mo98QZDzlq6eO4djzUsA";
	public static final String CONSUMER_SECRET = "ZOnLMRBInWlAwR22iMxQodRcFbHuaqEKSBvoFF8oXI";

	String retrieveRequestToken() throws ButlerException;
	void setTokenWithSecret(SharedPreferences settings);
	void saveRequestInformation(SharedPreferences settings,
			String token, String secret);
	public void authorizeCheckBeforeSending(SharedPreferences settings)throws AuthorizeException;
		
	void saveAuthInformation(SharedPreferences settings,
			String token, String secret);
	void saveAuthorizationFromUri(Uri uri,SharedPreferences settings) throws ButlerException;
}