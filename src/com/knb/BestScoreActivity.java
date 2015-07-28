package com.knb;

import min3d.Shared;
import min3d.core.Scene;
import min3d.handlers.CustomHandler;
import min3d.handlers.HandlerDataObject;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.knb.constants.SBStore;
import com.knb.data.ContextData;
import com.knb.data.Scorer;
import com.knb.images.Background;

public class BestScoreActivity {

	private Scene scene;

	private boolean isLoaded;

	private Handler bestScoreHandler = Shared.getHandler(R.id.best_score);

	private Handler scoreEndHandler = Shared.getHandler(R.id.score_end);

	private HandlerDataObject handlerDataObj = new HandlerDataObject();

	private float startSize = 20f;

	private float endSize = 40f;

	private long loadedTime;

	private long TIME_TO_LIVE = 2000;

	int best = Scorer.readBest();

	public BestScoreActivity(Scene scene) {
		this.scene = scene;
	}

	public void initScene() {
		init();
	}

	public void updateScene() {

		if ((System.currentTimeMillis() - loadedTime) > TIME_TO_LIVE) {
			ContextData.setValue(SBStore.ACTIVITY, SBStore.FIRST_PAGE_ACTIVITY);
		}

		if (handlerDataObj.getSize() == 0) {
			handlerDataObj.setSize(startSize);
		} else if (handlerDataObj.getSize() < endSize) {
			handlerDataObj.setSize(handlerDataObj.getSize() + 1);
		}

		Message bestScoreMsg = bestScoreHandler.obtainMessage();
		bestScoreMsg.what = CustomHandler.CHANGE_SIZE;
		bestScoreMsg.obj = handlerDataObj;
		bestScoreHandler.sendMessage(bestScoreMsg);

		Message scoreEndMsg = scoreEndHandler.obtainMessage();
		scoreEndMsg.obj = handlerDataObj;
		scoreEndMsg.what = CustomHandler.CHANGE_SIZE;
		scoreEndHandler.sendMessage(scoreEndMsg);

	}

	public boolean isLoaded() {
		return isLoaded;
	}

	private void init() {

		Background.loadBackGroundImage(scene);

		best = Scorer.readBest();

		handlerDataObj = new HandlerDataObject();
		handlerDataObj.setSize(startSize);

		Message scoreEndMsgCHANGE = scoreEndHandler.obtainMessage();
		scoreEndMsgCHANGE.obj = handlerDataObj;
		scoreEndMsgCHANGE.what = CustomHandler.CHANGE_SIZE;
		scoreEndHandler.sendMessage(scoreEndMsgCHANGE);

		Message bestScoreMsgCHANGE = bestScoreHandler.obtainMessage();
		bestScoreMsgCHANGE.obj = handlerDataObj;
		bestScoreMsgCHANGE.what = CustomHandler.CHANGE_SIZE;
		bestScoreHandler.sendMessage(bestScoreMsgCHANGE);

		Message scoreEndMsgADD = scoreEndHandler.obtainMessage();
		scoreEndMsgADD.what = CustomHandler.ADD_VIEW;
		scoreEndHandler.sendMessage(scoreEndMsgADD);

		Message bestScoreMsgADD = bestScoreHandler.obtainMessage();
		bestScoreMsgADD.what = CustomHandler.ADD_VIEW;
		bestScoreHandler.sendMessage(bestScoreMsgADD);

		handlerDataObj = new HandlerDataObject();
		handlerDataObj.setString(String.valueOf(best));

		Message scoreEndMsgEDIT = scoreEndHandler.obtainMessage();
		scoreEndMsgEDIT.what = CustomHandler.EDIT_VIEW;
		scoreEndMsgEDIT.obj = handlerDataObj;
		scoreEndHandler.sendMessage(scoreEndMsgEDIT);

		loadedTime = System.currentTimeMillis();

		isLoaded = true;

		Log.d(SBStore.TAG, "Best Score activity is loaded");

	}

	public void clearActivity() {

		Message bestScoreMsg = bestScoreHandler.obtainMessage();
		bestScoreMsg.what = CustomHandler.REMOVE_VIEW;
		bestScoreHandler.sendMessage(bestScoreMsg);

		Message scoreEndMsg = scoreEndHandler.obtainMessage();
		scoreEndMsg.what = CustomHandler.REMOVE_VIEW;
		scoreEndHandler.sendMessage(scoreEndMsg);

		handlerDataObj = null;

		isLoaded = false;

		Log.d(SBStore.TAG, "Best Score activity is cleared");
	}
}