package com.knb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import min3d.Shared;
import min3d.core.Object3d;
import min3d.core.Scene;
import min3d.handlers.CustomHandler;
import min3d.handlers.HandlerDataObject;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.knb.ball.Ball;
import com.knb.ball.CutBall;
import com.knb.ball.dynamics.Dynamics;
import com.knb.ball.dynamics.RotationDynamics;
import com.knb.constants.SBStore;
import com.knb.data.ContextData;
import com.knb.data.Scorer;
import com.knb.generators.BallGenerator;
import com.knb.generators.CutBallGenerator;
import com.knb.handlers.FrontPageTouchEventHandler;
import com.knb.images.Background;
import com.knb.obj.sword.Katana;
import com.knb.sounds.SoundManager;

public class FrontPage {

	private static List<Ball> balls;

	private static List<Object3d> katanas;

	private boolean isFrontPageLoaded;

	private volatile Scene scene;

	private float BALL_Y_POSITION = 0.75f;

	private List<CutBall> leftCutBalls = CutBallGenerator.getLeftBallsList();

	private List<CutBall> rightCutBalls = CutBallGenerator.getRightBallsList();

	private float katanaTouchedX;

	private HandlerDataObject handlerDataObj = new HandlerDataObject();

	private float increamentFactor;

	private int minSize = 20;

	private int maxSize = 25;

	public FrontPage(Scene scene) {
		this.scene = scene;
	}

	public void initScene() {

		Log.d(SBStore.TAG, "FrontPage is initialized");

		init();
	}

	public void updateScene() {
		try {

			Dynamics.makeBallMovements(leftCutBalls);

			Dynamics.makeBallMovements(rightCutBalls);

			if (FrontPageTouchEventHandler.isKatanaTouched()) {

				SoundManager.stopFrontPageMusic();

				Dynamics.makeBallMovements(balls);

			} else {
				synchronized (balls) {
					for (Ball ball : balls) {
						RotationDynamics.rotate(ball);
					}
				}
			}

			clearRemovedBalls();

			CutBallGenerator.clearRemovedBalls();

			if (leftCutBalls.size() > 0 && katanaTouchedX == 0) {
				katanaTouchedX = leftCutBalls.get(0).getObj3D().position().x;
			}

			if (leftCutBalls.size() == 0 && rightCutBalls.size() == 0
					&& balls.size() == 0) {

				// Getting the katana which is touched first.
				if (katanaTouchedX < 0) {
					ContextData.setValue(SBStore.ACTIVITY,
							SBStore.MAIN_ACTIVITY);
				} else if (katanaTouchedX > 0) {
					ContextData.setValue(SBStore.ACTIVITY,
							SBStore.BEST_SCORE_ACTIVITY);
				}

				FrontPageTouchEventHandler.setKatanaTouched(false);

			}
			
			changeInstructionSize();
			

		} catch (RuntimeException e) {
			Log.e("SamuraiBall",
					"RuntimeException in MainActivity.updateScene() ", e);
		}

	}

	protected void onStop() {
		// TODO Auto-generated method stub

	}

	protected void onPause() {
		// TODO Auto-generated method stub
		onStop();
	}

	private void init() {

		ContextData.setValue(SBStore.ACTIVITY, SBStore.FIRST_PAGE_ACTIVITY);

		Background.loadBackGroundImage(scene);

		katanas = placeKatanas();
		balls = placeBalls();

		// This strategy will enable the balls to fall on first strike to bottom
		for (Ball ball : balls) {
			ball.getObjState().getSpeed().setXv(0);
			ball.getObjState().getSpeed().setInitXvToZero();
			ball.increaseCollisionCounter();
		}

		Handler playHandler = Shared.getHandler(R.id.play);
		Message playMsg = playHandler.obtainMessage();
		playMsg.what = CustomHandler.ADD_VIEW;
		playHandler.sendMessage(playMsg);

		Handler highestHandler = Shared.getHandler(R.id.highest);
		Message highestMsg = highestHandler.obtainMessage();
		highestMsg.what = CustomHandler.ADD_VIEW;
		highestHandler.sendMessage(highestMsg);

		Handler touchBladeHandler = Shared.getHandler(R.id.touch_blade);
		Message touchBladeMsgADD = touchBladeHandler.obtainMessage();
		touchBladeMsgADD.what = CustomHandler.ADD_VIEW;
		touchBladeHandler.sendMessage(touchBladeMsgADD);

		// Initialize the handler with min size.
		handlerDataObj.setSize(minSize);

		CutBallGenerator.setScene(scene);
		CutBallGenerator.loadBallObject();

		Scorer.setScoreToZero();

		SoundManager.playFrontPageMusic();

		isFrontPageLoaded = true;
	}

	private float getXDivision() {

		return SBStore.NORMALIZATION_X;

	}

	private List<Object3d> placeKatanas() {

		List<Object3d> list = new ArrayList<Object3d>();

		float x = -1 * SBStore.NORMALIZATION_X + getXDivision() / 2;

		float y = -1 * SBStore.NORMALIZATION_Y_Katana;

		while (x < 1 * SBStore.NORMALIZATION_X) {

			Object3d katana = Katana.getKatana(scene);

			list.add(katana);

			katana.position().x = x;

			katana.position().y = y;

			x += getXDivision();

		}
		return list;

	}

	private List<Ball> placeBalls() {

		List<Ball> list = new ArrayList<Ball>();

		float x = -1 * SBStore.NORMALIZATION_X + getXDivision() / 2;

		float y = -1 * SBStore.NORMALIZATION_Y_Katana + BALL_Y_POSITION
				* SBStore.LENGTH_SWORD;

		while (x < 1 * SBStore.NORMALIZATION_X) {

			Object3d ball = BallGenerator.getBall(scene);

			ball.scale().x = ball.scale().y = ball.scale().z = SBStore.BALL_SCALE;

			Ball b = new Ball(ball);

			list.add(b);

			ball.position().x = x;

			ball.position().y = y;

			x += getXDivision();
		}

		return list;
	}

	public static List<Object3d> getKatanas() {
		return katanas;
	}

	public static List<Ball> getBalls() {
		return balls;
	}

	public boolean isFrontPageLoaded() {
		return isFrontPageLoaded;
	}

	public void clearRemovedBalls() {
		synchronized (balls) {
			Iterator<Ball> itr = balls.iterator();
			while (itr.hasNext()) {
				Ball b = itr.next();
				if (b.isRemoved()) {
					itr.remove();
					scene.removeChild(b.getObj3D());
				}
			}
		}
	}

	public void clearFrontPage() {

		synchronized (scene) {
			for (int i = scene.numChildren() - 1; i >= 0; i--) {
				scene.removeChildAt(i);
			}
		}

		Handler playHandler = Shared.getHandler(R.id.play);
		Message playMsg = playHandler.obtainMessage();
		playMsg.what = CustomHandler.REMOVE_VIEW;
		playHandler.sendMessage(playMsg);

		Handler highestHandler = Shared.getHandler(R.id.highest);
		Message highestMsg = highestHandler.obtainMessage();
		highestMsg.what = CustomHandler.REMOVE_VIEW;
		highestHandler.sendMessage(highestMsg);

		Handler touchBladeHandler = Shared.getHandler(R.id.touch_blade);
		Message touchBladeMsgREMOVE = touchBladeHandler.obtainMessage();
		touchBladeMsgREMOVE.what = CustomHandler.REMOVE_VIEW;
		touchBladeHandler.sendMessage(touchBladeMsgREMOVE);

		if (katanas != null)
			katanas.clear();
		if (balls != null)
			balls.clear();

		katanaTouchedX = 0;

		SoundManager.stopFrontPageMusic();

		isFrontPageLoaded = false;
	}

	private void changeInstructionSize() {

		if (handlerDataObj.getSize() <= minSize) {
			increamentFactor = 0.25f;
		} else if (handlerDataObj.getSize() >= maxSize) {
			increamentFactor = -0.25f;
		}

		handlerDataObj.setSize(handlerDataObj.getSize() + increamentFactor);

		Handler touchBladeHandler = Shared.getHandler(R.id.touch_blade);
		Message touchBladeMsg = touchBladeHandler.obtainMessage();
		touchBladeMsg.what = CustomHandler.CHANGE_SIZE;
		touchBladeMsg.obj = handlerDataObj;
		touchBladeHandler.sendMessage(touchBladeMsg);

	}
}
