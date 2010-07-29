package com.davidparry.twitter;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.davidparry.twitter.common.Tweet;
import com.davidparry.twitter.common.TwitterResult;
import com.davidparry.twitter.listeners.ProfileImageLongClickListener;
import com.davidparry.twitter.threads.ImageIconLoaderThread;

public class TweetList extends ListActivity {
	private static final String tag ="TweetList";
	private TweetsAdapter adapter;
	protected Drawable defaultImage = null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		defaultImage = new BitmapDrawable(BitmapFactory
				.decodeResource(this.getResources(),
						android.R.drawable.presence_busy));
		setContentView(R.layout.tweet_list);
		Bundle extras = getIntent().getExtras();
		List<Tweet> tweets = null;
		if(extras != null){
			TwitterResult result = (TwitterResult)extras.get("com.davidparry.twitter.tweets");
			tweets = result.getTweets();
		} else {
			tweets = new ArrayList<Tweet>();
			Toast.makeText(this, "Sorry no results returned please try another search.", Toast.LENGTH_LONG).show();
		}
		this.adapter = new TweetsAdapter(this,R.layout.row , tweets);
		setListAdapter(this.adapter);
	}
	
	public class TweetsAdapter extends ArrayAdapter<Tweet> {
		private List<Tweet> tweets;
		public TweetsAdapter(Context context, int textViewResourceId,List<Tweet> list){
			super(context,textViewResourceId,list);
			this.tweets = list;
		}
		@Override
		public void add(Tweet object) {
			if(this.tweets == null){
				this.tweets = new ArrayList<Tweet>();
			}
			this.tweets.add(object);
		}
		@Override
		public void clear() {
			this.tweets.clear();
		}
		@Override
		public int getCount() {
			return this.tweets.size();
		}
		@Override
		public Tweet getItem(int position) {
			// never will be null
			Tweet tweet = null;
			if(this.tweets != null){
				tweet = this.tweets.get(position);
			} 
			if(tweet == null){
				tweet = new Tweet();
			}
			return tweet;
		}
		@Override
		public long getItemId(int position) {
			// 0 if none or the twitter assigned id
			return getItem(position).getId();
		}
		@Override
		public void remove(Tweet object) {
			if(this.tweets != null){
				this.tweets.remove(object);
			}
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				Log.d(tag, "view is null");
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			
			Tweet tweet = getItem(position);
				ImageView iv = (ImageView) v.findViewById(R.id.list_image);
				if (iv != null) {
					if(iv.getDrawable() ==null){
						Thread t = new Thread(new ImageIconLoaderThread(tweet.getProfileImageUrl(), iv,defaultImage));
						t.start();
						iv.setOnLongClickListener(new ProfileImageLongClickListener());
					}
					
					//ImageObject obj = getImage(getBaseContext(), tweet.getProfileImageUrl());
					//iv.setImageDrawable(obj.getDrawable());
					//iv.setOnLongClickListener(new ProfileImageLongClickListener(it.getUserId()));
				}
				TextView tv = (TextView) v.findViewById(R.id.list_value);
				Typeface acmesa = Typeface.createFromAsset(getAssets(), "fonts/acmesa.TTF");
				
				tv.setTypeface(acmesa);
				if (tv != null) {
					tv.setText(tweet.getText());
					//tv.setOnLongClickListener(new StatusLongClickListener(it));
				}
			
			return v;
		}
		
		
		
		
	}
	
}
