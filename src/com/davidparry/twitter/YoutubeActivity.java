package com.davidparry.twitter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.davidparry.twitter.widgets.OptionMenu;


public class YoutubeActivity extends Activity {
	private static final String tag = "YoutubeActivity";
	private VideoView mVideoView;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return new OptionMenu().setupOptionMenu(menu, getBaseContext());
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.introyoutube);
		try{
			Bundle extras = getIntent().getExtras();
			String url = extras.getString("com.davidparry.twitter.youtube");
			
			
			 mVideoView = (VideoView) findViewById(R.id.intro_view);

		        if (url == "") {
		            // Tell the user to provide a media file URL/path.
		            Toast.makeText(
		                    this,
		                    "Please contact technical support @ d.dry.parry@gmail.com",
		                    Toast.LENGTH_LONG).show();

		        } else {

		            /*
		             * Alternatively,for streaming media you can use
		             * mVideoView.setVideoURI(Uri.parse(URLstring));
		             */
		           // mVideoView.setVideoPath(path);
		        	mVideoView.setVideoURI(Uri.parse(url));
		            mVideoView.setMediaController(new MediaController(this));
		            mVideoView.requestFocus();
		            mVideoView.start();
		        }
		     	        
		} catch(Exception er){
			Log.e(tag,"Error loading Introdcution ",er);
			this.finish();
		}
		
	}

	
}