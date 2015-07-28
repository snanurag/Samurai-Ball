package com.knb;

import java.util.List;

import min3d.Shared;
import min3d.core.Scene;
import min3d.handlers.CustomHandler;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.knb.ball.Ball;
import com.knb.ball.CutBall;
import com.knb.ball.dynamics.Dynamics;
import com.knb.constants.SBStore;
import com.knb.data.ContextData;
import com.knb.generators.BallGenerator;
import com.knb.generators.CutBallGenerator;
import com.knb.images.Background;
import com.knb.obj.sword.Katana;
import com.knb.obj.sword.KatanaCollection;
import com.knb.sounds.SoundManager;

public class MainActivity {

	public MainActivity(Scene scene) {
		this.scene = scene;
	}

	private Scene scene;

	private List<Ball> balls = BallGenerator.list();

	private List<CutBall> leftCutBalls = CutBallGenerator.getLeftBallsList();

	private List<CutBall> rightCutBalls = CutBallGenerator.getRightBallsList();

	private BallGenerator ballGeneratorThread;

	private boolean isMainActivityLoaded;

	public void initScene() {
		init();
	}

	public void updateScene() {
		try {
			// update Scene will work for Game over activity too.
			if (isMainActivityLoaded
					|| ContextData.getValue(SBStore.ACTIVITY) != null
					&& ContextData.getValue(SBStore.ACTIVITY).equals(
							SBStore.GAME_OVER_ACTIVITY)) {

				Dynamics.makeBallMovementWithCollision(balls);

				Dynamics.makeBallMovements(leftCutBalls);

				Dynamics.makeBallMovements(rightCutBalls);

				synchronized (balls) {
					for (int i = 0; i < balls.size(); i++) {
						Ball ball = balls.get(i);
						ball.setTemporarytoActuallyState();
					}
				}

				// TODO
				// In case if we want to remove only the balls which are marked
				// as
				// removed, we can use one list only instead of collisionList
				// and
				// BallGenerator list.
				BallGenerator.clearRemovedBalls();

				CutBallGenerator.clearRemovedBalls();

			}

		} catch (RuntimeException e) {
			Log.e("SamuraiBall",
					"RuntimeException in MainActivity.updateScene() ", e);
		}
	}

	protected void onStop() {
		// TODO Auto-generated method stub
		if (ballGeneratorThread != null)
			ballGeneratorThread.interrupt();
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		onStop();
	}

	private void init() {

		Log.d(SBStore.TAG, "init MainActivity");

		ContextData.setValue(SBStore.ACTIVITY, SBStore.MAIN_ACTIVITY);

		Background.loadBackGroundImage(scene);

		ballGeneratorThread = new BallGenerator(scene);
		ballGeneratorThread.start();

		Katana.buildKatana();

		KatanaCollection.buildCollection(scene);

		Handler scoresHandler = Shared.getHandler(R.id.scores);
		Message scoresMsg = scoresHandler.obtainMessage();
		scoresMsg.what = CustomHandler.ADD_VIEW;
		scoresHandler.sendMessage(scoresMsg);

		SoundManager.playMainActivityMusic();
		
		isMainActivityLoaded = true;

		Log.d(SBStore.TAG, "Main Activity loaded");

	}

	public boolean isMainActivityLoaded() {
		return isMainActivityLoaded;
	}

	public void clearMainActivity() {
		ballGeneratorThread.interrupt();
		Handler scoreEndHandler = Shared.getHandler(R.id.scores);
		Message scoreEndMsg = scoreEndHandler.obtainMessage();
		scoreEndMsg.what = CustomHandler.REMOVE_VIEW;
		scoreEndHandler.sendMessage(scoreEndMsg);
		
		SoundManager.playGameOver();
		SoundManager.stopMainActivityMusic();
		
		isMainActivityLoaded = false;
		
		Log.d("SamuraiBall", "Main Activity is cleared");
	}

}