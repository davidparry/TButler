package com.davidparry.twitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.davidparry.twitter.widgets.OptionMenu;

public class InfoActivity  extends Activity {
	private static final String tag = "InfoActivity";
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return new OptionMenu().setupOptionMenu(menu, getBaseContext());
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		 setContentView(R.layout.info);
	         
	        Button intro = (Button)findViewById(R.id.introYouTube);
	        intro.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent sti = new Intent();
					//sti.putExtra("com.davidparry.twitter.youtube","rtsp://v4.cache8.c.youtube.com/CiILENy73wIaGQneWNJuq4D2TxMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp");
					sti.putExtra("com.davidparry.twitter.youtube","rtsp://v4.cache6.c.youtube.com/CiILENy73wIaGQneWNJuq4D2TxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp");
					
					sti.setClass(getBaseContext(),YoutubeActivity.class);
					v.getContext().startActivity(sti);
				}
			});
	        Button intro1 = (Button)findViewById(R.id.introYouTube1);
	        intro1.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent sti = new Intent();
					sti.putExtra("com.davidparry.twitter.youtube","rtsp://v4.cache8.c.youtube.com/CiILENy73wIaGQneWNJuq4D2TxMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp");
					
					sti.setClass(getBaseContext(),YoutubeActivity.class);
					v.getContext().startActivity(sti);
				}
			});
	        Button tweetReg = (Button)findViewById(R.id.tweetYouTube);
	        tweetReg.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent sti = new Intent();
					sti.putExtra("com.davidparry.twitter.youtube","rtsp://v6.cache1.c.youtube.com/CiILENy73wIaGQlx5Q2w5g7jIxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp");
					sti.setClass(getBaseContext(),YoutubeActivity.class);
					v.getContext().startActivity(sti);
				}
			});
	        Button tweetReg1 = (Button)findViewById(R.id.tweetYouTube1);
	        tweetReg1.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					Intent sti = new Intent();
					sti.putExtra("com.davidparry.twitter.youtube","rtsp://v7.cache4.c.youtube.com/CiILENy73wIaGQlx5Q2w5g7jIxMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp");
					sti.setClass(getBaseContext(),YoutubeActivity.class);
					v.getContext().startActivity(sti);
				}
			});
	        
	}

}
