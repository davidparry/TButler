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
import java.net.URL;

import twitter4j.User;

/**
 * @author david
 *
 */
public class TwitterUser implements Serializable {
	private String name;
	private String location;
	private String status;
	private int friendCount;
	private String description;
	private String screenName;
	private String url;
	private URL profileUrl;
	public TwitterUser(User user){
		name = user.getName();
		location = user.getLocation();
		status = user.getStatus().getText();
		friendCount = user.getFriendsCount();
		description = user.getDescription();
		screenName = user.getScreenName();
		if(user.getURL() != null){
			url = user.getURL().toExternalForm();
		}
		profileUrl = user.getProfileImageURL();
	}
	
	public String getName() {
		return name;
	}
	public String getLocation() {
		return location;
	}
	public String getStatus() {
		return status;
	}
	public int getFriendCount() {
		return friendCount;
	}
	public String getDescription() {
		return description;
	}
	public String getScreenName() {
		return screenName;
	}
	public String getUrl() {
		return url;
	}
	
	public URL getProfileUrl() {
		return profileUrl;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((screenName == null) ? 0 : screenName.hashCode());
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
		TwitterUser other = (TwitterUser) obj;
		if (screenName == null) {
			if (other.screenName != null)
				return false;
		} else if (!screenName.equals(other.screenName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TwitterUser [description=" + description + ", friendCount="
				+ friendCount + ", location=" + location + ", name=" + name
			    + ", profileUrl="
				+ profileUrl + ", screenName=" + screenName + ", status="
				+ status + ", url=" + url + "]";
	}
	
	
	

	
	
}
