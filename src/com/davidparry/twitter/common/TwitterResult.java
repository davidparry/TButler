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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import twitter4j.QueryResult;

public class TwitterResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1164543704829407410L;
	private List<Tweet> tweets = new ArrayList<Tweet>();
	private long maxId;
	private double completedIn;
	private int page;
	private String query;
	private String refreshUrl;
	private int resultsPerPage;
	private long sinceId;
	private String warning;
	
	public TwitterResult(){}
	public TwitterResult(QueryResult result){
		if(result != null){
			if(result.getTweets() != null && result.getTweets().size() >0){
				for(twitter4j.Tweet tweet: result.getTweets()){
					tweets.add(new Tweet(tweet));
				}
			}
			this.maxId = result.getMaxId();
			this.completedIn = result.getCompletedIn();
			this.page = result.getPage();
			this.query = result.getQuery();
			this.refreshUrl = result.getRefreshUrl();
			this.resultsPerPage =result.getResultsPerPage();
			this.sinceId = result.getSinceId();
			this.warning = result.getWarning();
		}
		
	}
	public List<Tweet> getTweets() {
		return tweets;
	}
	public long getMaxId() {
		return maxId;
	}
	public double getCompletedIn() {
		return completedIn;
	}
	public int getPage() {
		return page;
	}
	public String getQuery() {
		return query;
	}
	public String getRefreshUrl() {
		return refreshUrl;
	}
	public int getResultsPerPage() {
		return resultsPerPage;
	}
	public long getSinceId() {
		return sinceId;
	}
	public String getWarning() {
		return warning;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(completedIn);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (maxId ^ (maxId >>> 32));
		result = prime * result + page;
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		result = prime * result
				+ ((refreshUrl == null) ? 0 : refreshUrl.hashCode());
		result = prime * result + resultsPerPage;
		result = prime * result + (int) (sinceId ^ (sinceId >>> 32));
		result = prime * result + ((tweets == null) ? 0 : tweets.hashCode());
		result = prime * result + ((warning == null) ? 0 : warning.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwitterResult other = (TwitterResult) obj;
		if (Double.doubleToLongBits(completedIn) != Double
				.doubleToLongBits(other.completedIn))
			return false;
		if (maxId != other.maxId)
			return false;
		if (page != other.page)
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (refreshUrl == null) {
			if (other.refreshUrl != null)
				return false;
		} else if (!refreshUrl.equals(other.refreshUrl))
			return false;
		if (resultsPerPage != other.resultsPerPage)
			return false;
		if (sinceId != other.sinceId)
			return false;
		if (tweets == null) {
			if (other.tweets != null)
				return false;
		} else if (!tweets.equals(other.tweets))
			return false;
		if (warning == null) {
			if (other.warning != null)
				return false;
		} else if (!warning.equals(other.warning))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TwitterResult [completedIn=" + completedIn + ", maxId=" + maxId
				+ ", page=" + page + ", query=" + query + ", refreshUrl="
				+ refreshUrl + ", resultsPerPage=" + resultsPerPage
				+ ", sinceId=" + sinceId + ", tweets=" + tweets + ", warning="
				+ warning + "]";
	}
	
}
