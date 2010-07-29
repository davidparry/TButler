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
