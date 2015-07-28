package com.knb.text;

import min3d.handlers.HandlerDataObject;
import android.os.Handler;
import android.os.Message;

public class TextSizeChangeEffect {
	
	private Handler handler;

	public void setHandler(Handler handler){
		this.handler = handler;
	}
	
	public void changeSizeTo(float size){
		
		if(handler!=null){
			Message msg = handler.obtainMessage();
			
			HandlerDataObject data;
			
			if(msg.obj!=null){
				data = (HandlerDataObject)msg.obj;
			}
			else{
				data = new HandlerDataObject();
			}
			
			data.setSize(size);
		}
	}
}
