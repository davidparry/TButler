package com.davidparry.twitter.analytics;

import android.app.Activity;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class ButlerAnalytics {
	GoogleAnalyticsTracker tracker = GoogleAnalyticsTracker.getInstance();
	
	public void startTracking(Activity activity){
		 tracker.start("UA-15720435-2", activity);
		 tracker.trackPageView(activity.getTitle().toString());
	}
	public void stop(){
		tracker.dispatch();
		tracker.stop();
	}
	
}
