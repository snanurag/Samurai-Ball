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
import com.knb.obj.sword.KatanaCollection;

public class GameOverActivity {

	private Scene scene;

	private boolean isLoaded;

	private Handler yourScoreHandler = Shared.getHandler(R.id.your_score);

	private Handler scoreEndHandler = Shared.getHandler(R.id.score_end);

	private Handler newBestHandler = Shared.getHandler(R.id.new_best);

	private HandlerDataObject handlerDataObj = new HandlerDataObject();

	private float startSize = 20f;

	private float endSize = 40f;

	private long loadedTime;

	private long TIME_TO_LIVE = 5000;

	int best = Scorer.readBest();
	int score = Integer.parseInt(Scorer.getCurrentScore());

	public GameOverActivity(Scene scene) {
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

		if (score <= best) {
			Message yourScoreMsg = yourScoreHandler.obtainMessage();
			yourScoreMsg.what = CustomHandler.CHANGE_SIZE;
			yourScoreMsg.obj = handlerDataObj;
			yourScoreHandler.sendMessage(yourScoreMsg);
		} else {
			Message newBestMsg = newBestHandler.obtainMessage();
			newBestMsg.what = CustomHandler.CHANGE_SIZE;
			newBestMsg.obj = handlerDataObj;
			newBestHandler.sendMessage(newBestMsg);
		}

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
		score = Integer.parseInt(Scorer.getCurrentScore());

		handlerDataObj = new HandlerDataObject();
		handlerDataObj.setSize(startSize);
		
		Message scoreEndMsgCHANGE = scoreEndHandler.obtainMessage();
		scoreEndMsgCHANGE.obj = handlerDataObj;
		scoreEndMsgCHANGE.what = CustomHandler.CHANGE_SIZE;
		scoreEndHandler.sendMessage(scoreEndMsgCHANGE);

		Message yourScoreMsgCHANGE = yourScoreHandler.obtainMessage();
		yourScoreMsgCHANGE.obj = handlerDataObj;
		yourScoreMsgCHANGE.what = CustomHandler.CHANGE_SIZE;
		yourScoreHandler.sendMessage(yourScoreMsgCHANGE);

		Message newBestMsgCHANGE = newBestHandler.obtainMessage();
		newBestMsgCHANGE.obj = handlerDataObj;
		newBestMsgCHANGE.what = CustomHandler.CHANGE_SIZE;
		newBestHandler.sendMessage(newBestMsgCHANGE);
		
		if (score <= best) {
			Message yourScoreMsgADD = yourScoreHandler.obtainMessage();
			yourScoreMsgADD.what = CustomHandler.ADD_VIEW;
			yourScoreHandler.sendMessage(yourScoreMsgADD);
		} else {
			Message newBestMsgADD = newBestHandler.obtainMessage();
			newBestMsgADD.what = CustomHandler.ADD_VIEW;
			newBestHandler.sendMessage(newBestMsgADD);

			Scorer.persistBest();
		}

		Message scoreEndMsgADD = scoreEndHandler.obtainMessage();
		scoreEndMsgADD.what = CustomHandler.ADD_VIEW;
		scoreEndHandler.sendMessage(scoreEndMsgADD);

		handlerDataObj = new HandlerDataObject();
		handlerDataObj.setString(Scorer.getCurrentScore());

		Message scoreEndMsgEDIT = scoreEndHandler.obtainMessage();
		scoreEndMsgEDIT.what = CustomHandler.EDIT_VIEW;
		scoreEndMsgEDIT.obj = handlerDataObj;
		scoreEndHandler.sendMessage(scoreEndMsgEDIT);

		loadedTime = System.currentTimeMillis();
		
		isLoaded = true;

		Log.d(SBStore.TAG, "Game Over activity is loaded");

	}

	public void clearActivity() {

		synchronized (scene) {
			for (int i = scene.numChildren() - 1; i >= 0; i--) {
				scene.removeChildAt(i);
			}
		}

		Message yourScoreMsg = yourScoreHandler.obtainMessage();
		yourScoreMsg.what = CustomHandler.REMOVE_VIEW;
		yourScoreHandler.sendMessage(yourScoreMsg);

		Message scoreEndMsg = scoreEndHandler.obtainMessage();
		scoreEndMsg.what = CustomHandler.REMOVE_VIEW;
		scoreEndHandler.sendMessage(scoreEndMsg);

		Message newBestMsg = newBestHandler.obtainMessage();
		newBestMsg.what = CustomHandler.REMOVE_VIEW;
		newBestHandler.sendMessage(newBestMsg);

		handlerDataObj = null;

		KatanaCollection.clearList();

		isLoaded = false;

		Log.d(SBStore.TAG, "Game Over activity is cleared");
	}
}