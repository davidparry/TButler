package com.davidparry.twitter.common;

import java.text.SimpleDateFormat;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class Item {
	Drawable image;
	String message;
	String imageSrc;
	String userId;
	long id;
	String createdAt;
	String source;
	private static final String TAG = "Item";

	
	public String toFileString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><body><h3>Tweet</h3>");
		buffer.append("<img src='");
		buffer.append(this.imageSrc);
		buffer.append("'>");
		buffer.append("<br><br>");
		buffer.append("<b>status:</b>");
		buffer.append(encode(this.message));
		buffer.append("<br>");
		buffer.append("<b>userId:</b>");
		buffer.append(userId);
		buffer.append("<br>");		
		buffer.append("<b>id:</b>");
		buffer.append(this.id);
		buffer.append("<br>");
		buffer.append("<b>create date:</b>");
		buffer.append(createdAt);
		buffer.append("</body></html>");
		return buffer.toString();
	}
	
	
	
	public static String encode(String value){
		String a ="<a href='";
		String es = "</a>";
		Log.d(TAG, value);
		try {
			if(value != null){
				int v = value.indexOf("http");
				Log.d(TAG, "Index "+v);
				if(v >0){
					int t = value.indexOf(" ",v);
					String url ="";
					if(t >0){
						url = value.substring(v,t);
					} else{
						url = value.substring(v);
					}
					Log.d(TAG, "Url "+url);
					String r = a+url+"'>"+url+es;
					Log.d(TAG, "R "+r);
					value = value.replaceFirst(url,r);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error finding url ",e);
		}
		return value;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getImageSrc() {
		return imageSrc;
	}
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
	public Item() {}
	
	public Item(String imageSrc, String message, String userId,long id, String createdAt,String fromUser,String source) {
		this.imageSrc = imageSrc;
		this.message = message;
		this.userId = userId;
		this.id = id;
		this.createdAt = createdAt;
		this.source = source;
	}
	public Item(Tweet tweet){
		this.imageSrc = tweet.getProfileImageUrl();
		this.message = tweet.getText();
		this.userId = tweet.getFromUser();
		this.id = tweet.getId();
		String date = "";
		if(tweet.getCreatedAt() != null){
			SimpleDateFormat format = new SimpleDateFormat();
			date = format.format(tweet.getCreatedAt());
		}
		
	}
	
	
	public String getFileName() {
		return this.userId+this.id;
	}
	public Drawable getImage() {
		return image;
	}
	public void setImage(Drawable image) {
		this.image = image;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
