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
package com.davidparry.twitter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.davidparry.twitter.common.ActivityHelper;
import com.davidparry.twitter.common.Tweet;
import com.davidparry.twitter.common.TwitterResult;
import com.davidparry.twitter.listeners.ProfileImageLongClickListener;
import com.davidparry.twitter.listeners.buttons.ListTweetsOnClickListener;
import com.davidparry.twitter.listeners.buttons.NextPageOnClickListener;
import com.davidparry.twitter.threads.ImageIconLoaderThread;
import com.davidparry.twitter.threads.StatusLongClickListener;
import com.davidparry.twitter.widgets.OptionMenu;

public class TweetList extends ListActivity implements ButlerActivity,
		TwitterPersistence {
	private static final String tag = "TweetList";
	private TweetsAdapter adapter;
	protected Drawable defaultImage = null;
	private ProgressDialog pd;
	private ActivityHelper helper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper = new ActivityHelper(this);
		defaultImage = new BitmapDrawable(BitmapFactory.decodeResource(this
				.getResources(), android.R.drawable.presence_busy));
		setContentView(R.layout.tweet_list);
		Bundle extras = getIntent().getExtras();
		List<Tweet> tweets = null;
		TwitterResult result = null;
		if (extras != null) {
			result = (TwitterResult) extras
					.get("com.davidparry.twitter.tweets");
			tweets = result.getTweets();
		} else {
			tweets = new ArrayList<Tweet>();
		}
		if (tweets == null || tweets.size() <= 0) {
			Toast.makeText(this,
					"Sorry no results returned please try another search.",
					Toast.LENGTH_LONG).show();
		}
		this.adapter = new TweetsAdapter(this, R.layout.row, tweets);
		setListAdapter(this.adapter);

		ImageButton search = (ImageButton) findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(v.getContext(), ButlerTabActivity.class);
				startActivity(intent);
			}
		});
		ImageButton menu = (ImageButton) findViewById(R.id.menu);
		menu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				openOptionsMenu();
			}
		});

		ImageButton next = (ImageButton) findViewById(R.id.next);
		if (result != null && StringUtils.isNotEmpty(result.getNextPage())) {
			next.setOnClickListener(new NextPageOnClickListener(this, result
					.getNextPage()));
		} else {
			next.setVisibility(View.INVISIBLE);
		}
		ImageButton back = (ImageButton) findViewById(R.id.back);
		back.setOnClickListener(new ListTweetsOnClickListener(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return new OptionMenu().setupOptionMenu(menu, getBaseContext());
	}

	public class TweetsAdapter extends ArrayAdapter<Tweet> {
		private List<Tweet> tweets;
		private ButlerActivity activity;

		public TweetsAdapter(Context context, int textViewResourceId,
				List<Tweet> list) {
			super(context, textViewResourceId, list);
			activity = (ButlerActivity) context;
			this.tweets = list;
		}

		@Override
		public void add(Tweet object) {
			if (this.tweets == null) {
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
			if (this.tweets != null) {
				tweet = this.tweets.get(position);
			}
			if (tweet == null) {
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
			if (this.tweets != null) {
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
				iv.setOnLongClickListener(new ProfileImageLongClickListener(
						tweet.getFromUser(), activity));
				if(!helper.getUserIcons().containsKey(tweet.getFromUser())){
					iv.setImageDrawable(defaultImage);
					Thread t = new Thread(new ImageIconLoaderThread(tweet
							.getProfileImageUrl(), iv, defaultImage));
					t.start();
					helper.getUserIcons().put(tweet.getFromUser(), tweet.getProfileImageUrl());
				}
				
			}
			TextView tv = (TextView) v.findViewById(R.id.list_value);
			Typeface acmesa = Typeface.createFromAsset(getAssets(),
					"fonts/acmesa.TTF");

			tv.setTypeface(acmesa);
			if (tv != null) {
				tv.setText(tweet.getText());
				tv.setOnLongClickListener(new StatusLongClickListener(tweet));
			}

			return v;
		}
	}

	public void closeDialog() {
		if (pd != null) {
			pd.cancel();
		}
	}

	public Context getContext() {
		return this;
	}

	public ProgressDialog getDialog(String title, String msg) {
		if (pd == null) {
			pd = new ProgressDialog(this);
			pd.setIcon(R.drawable.icon);
			pd.setIndeterminate(true);
		}
		pd.setMessage(msg);
		pd.setTitle(title);
		return pd;
	}

	public String getTextFieldValue(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isChecked(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void runActivity(Intent intent) {
		startActivity(intent);

	}

	public void writeTweets(TwitterResult result) throws ButlerException {
		helper.writeTweets(result);
	}

	public TwitterResult readTweets() throws ButlerException {
		return helper.readTweets();
	}

	public void setFocusOnItem(int id) {
		// TODO Auto-generated method stub
		
	}

	public void clearItem(int id) {
		// TODO Auto-generated method stub
		
	}

}
