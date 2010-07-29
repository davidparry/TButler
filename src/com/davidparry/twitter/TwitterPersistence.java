package com.davidparry.twitter;

import com.davidparry.twitter.common.TwitterResult;

public interface TwitterPersistence {
	void writeTweets(TwitterResult result) throws ButlerException;
	TwitterResult readTweets() throws ButlerException;
	
}
