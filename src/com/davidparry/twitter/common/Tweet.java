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
import java.util.Date;

public class Tweet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6838825855212900664L;

	private Date createdAt;
	private String fromUser;
	private int fromUserId;
	private Location geoLocation;
	private long id;
	private String isoLanguageCode;
	private String profileImageUrl;
	private String source;
	private String text;
	private String toUser;
	private int toUserId;
	public Tweet(){}
	public Tweet(twitter4j.Tweet tweet){
		if(tweet != null){
			this.createdAt = tweet.getCreatedAt();
			this.fromUser = tweet.getFromUser();
			this.fromUserId = tweet.getFromUserId();
			this.geoLocation = new Location(tweet.getGeoLocation());
			this.id = tweet.getId();
			this.isoLanguageCode = tweet.getIsoLanguageCode();
			this.profileImageUrl = tweet.getProfileImageUrl();
			this.source = tweet.getSource();
			this.text = tweet.getText();
			this.toUser = tweet.getToUser();
			this.toUserId = tweet.getToUserId();
		}
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public String getFromUser() {
		return fromUser;
	}
	public int getFromUserId() {
		return fromUserId;
	}
	public Location getGeoLocation() {
		return geoLocation;
	}
	public long getId() {
		return id;
	}
	public String getIsoLanguageCode() {
		return isoLanguageCode;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public String getSource() {
		return source;
	}
	public String getText() {
		return text;
	}
	public String getToUser() {
		return toUser;
	}
	public int getToUserId() {
		return toUserId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Tweet other = (Tweet) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Tweet [createdAt=" + createdAt + ", fromUser=" + fromUser
				+ ", fromUserId=" + fromUserId + ", geoLocation=" + geoLocation
				+ ", id=" + id + ", isoLanguageCode=" + isoLanguageCode
				+ ", profileImageUrl=" + profileImageUrl + ", source=" + source
				+ ", text=" + text + ", toUser=" + toUser + ", toUserId="
				+ toUserId + "]";
	}
	
	
}
