package com.knb.handlers;

import com.knb.obj.sword.KatanaMovementThread;

public class FrontPageTouchEventHandler extends AbstractTouchEventHandler {

	private static boolean isKatanaTouched;
	
	@Override
	public void onTocuhEvent(float xTouch, float yTouch) {
		super.onTocuhEvent(xTouch, yTouch);
		KatanaMovementThread katanaMovementThread = new KatanaMovementThread();
		katanaMovementThread.setTouchCoord(xTouch, yTouch);
		katanaMovementThread.start();
		previousThread = katanaMovementThread;
	}
	
	public static boolean isKatanaTouched(){
		return isKatanaTouched;
	}
	
	public static void setKatanaTouched(boolean isTouched){
		isKatanaTouched = isTouched;
	}
	
	
}
