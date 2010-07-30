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
