package com.knb.handlers;

import com.knb.obj.sword.KatanaMovementThread;

public class MainActivityTouchEventHandler extends AbstractTouchEventHandler {
	
	public void onTouchEvent(){
		
	}

	@Override
	public void onTocuhEvent(float xTouch, float yTouch) {
		super.onTocuhEvent(xTouch, yTouch);
		KatanaMovementThread katanaMovementThread = new KatanaMovementThread();
		katanaMovementThread.setTouchCoord(xTouch, yTouch);
		katanaMovementThread.start();
		previousThread = katanaMovementThread;
		
	}

}
