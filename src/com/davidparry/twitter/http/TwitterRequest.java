package com.davidparry.twitter.http;

import com.davidparry.twitter.ButlerException;

public interface TwitterRequest {

	public String getUrl()  throws ButlerException;
	
}
