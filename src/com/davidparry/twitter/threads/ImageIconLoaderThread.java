package com.davidparry.twitter.threads;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class ImageIconLoaderThread implements Runnable {
	private static final String tag = "ImageIconLoaderThread";
	private String url;
	private final ImageView iv;
	private Drawable defaultImage;
	public ImageIconLoaderThread(String url,ImageView iv,Drawable defaultImage){
		this.url = url;
		this.iv =iv;
		this.defaultImage = defaultImage;
	}
	public void run() {
		Message handlerMessage = new Message();
		handlerMessage.obj = imageOperations(this.url,this.defaultImage);
		handler.sendMessage(handlerMessage);
	}
	 public Drawable imageOperations(String url,Drawable defaultImage) {
		 InputStream is= null;
		 	try {
		 		is = (InputStream) fetch(url);
		 		return Drawable.createFromStream(is, "src");	
			} catch (MalformedURLException e) {			
				Log.e(tag, "Bad url "+ url,e);
			} catch (IOException e) {
				Log.e(tag, "IO problem url with "+url,e);
			} finally{
				try {
					if(is != null){
						is.close();
					}
				} catch (IOException e) {}
			}
			return defaultImage;
		}

		public Object fetch(String address) throws MalformedURLException,IOException {
			//Log.d(TAG, "Making request:"+address);
			URL url = new URL(address);
			Object content = url.getContent();
			return content;
		}	
		
		
		private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Object obj = msg.obj;
				if(obj != null){
					iv.setImageDrawable((Drawable) obj);
				}
				
			}
		};
}
