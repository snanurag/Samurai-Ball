package com.knb.handlers;

import com.knb.constants.SBStore;
import com.knb.data.ContextData;

public class TouchEventHandler extends AbstractTouchEventHandler {

	AbstractTouchEventHandler mainActivityTouchEventHandler = new MainActivityTouchEventHandler();

	AbstractTouchEventHandler firstPageTouchEventHandler = new FrontPageTouchEventHandler();

	@Override
	public void onTocuhEvent(float xTouch, float yTouch) {

		if (ContextData.getValue(SBStore.ACTIVITY) != null
				&& (ContextData.getValue(SBStore.ACTIVITY).equals(
						SBStore.MAIN_ACTIVITY) || ContextData.getValue(SBStore.ACTIVITY).equals(
								SBStore.GAME_OVER_ACTIVITY))) {
			mainActivityTouchEventHandler.onTocuhEvent(xTouch, yTouch);
		} 
		else if (ContextData.getValue(SBStore.ACTIVITY) != null
				&& ContextData.getValue(SBStore.ACTIVITY).equals(
						SBStore.FIRST_PAGE_ACTIVITY)) {
			firstPageTouchEventHandler.onTocuhEvent(xTouch, yTouch);
		}

	}
}
