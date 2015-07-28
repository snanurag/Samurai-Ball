package com.knb.handlers;

public abstract class AbstractTouchEventHandler {

	protected Thread previousThread;
	
	public void onTocuhEvent(float xTouch, float yTouch){
	
		//TODO not interrupting for now. Have to deal with it later.
//		if(previousThread!=null){
//			previousThread.interrupt();
//		}
	}
}
