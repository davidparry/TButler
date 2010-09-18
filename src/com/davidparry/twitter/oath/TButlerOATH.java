package com.davidparry.twitter.oath;

import android.net.Uri;

public interface TButlerOATH {
	public static final String REQUEST_TOKEN_URL ="http://twitter.com/oauth/request_token";
	public static final String AUTHORIZATION_URL="http://twitter.com/oauth/authorize";
    public static final String ACCESS_TOKEN_URL="http://twitter.com/oauth/access_token";
    public static final String VERIFY_URL_STRING = "http://twitter.com/account/verify_credentials.json";
    public static final String CONSUMER_KEY = "r";
    public static final String CONSUMER_SECRET = "E";
    
    public static final String USER_TOKEN = "user_token";
	public static final String USER_SECRET = "user_secret";
	public static final String REQUEST_TOKEN = "request_token";
	public static final String REQUEST_SECRET = "request_secret";
	
	Uri CALLBACK_URI = Uri.parse("tbutler:///twitt");
	Uri REDIRECT_URI = Uri.parse("http://app.davidparry.com/tbutler.jsp");
	
	
 
	String TWITTER_REQUEST_TOKEN_URL = "http://twitter.com/oauth/request_token";
	String TWITTER_ACCESS_TOKEN_URL = "http://twitter.com/oauth/access_token";
	String TWITTER_AUTHORIZE_URL = "http://twitter.com/oauth/authorize";
 
}
