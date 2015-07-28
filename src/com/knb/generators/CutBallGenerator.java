package com.knb.generators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import min3d.Shared;
import min3d.core.Object3d;
import min3d.core.Scene;
import min3d.parser.IParser;
import min3d.parser.Parser;
import android.util.Log;

import com.knb.ball.Ball;
import com.knb.ball.CutBall;
import com.knb.ball.Rotation;
import com.knb.ball.Speed;
import com.knb.constants.SBStore;
import com.knb.data.Scorer;
import com.knb.sounds.SoundManager;

public class CutBallGenerator {

	private static Object3d cutBall;

	private static Scene scene;

	private static List<CutBall> cutBallLeftList = new ArrayList<CutBall>();

	private static List<CutBall> cutBallRightList = new ArrayList<CutBall>();

	private static List<Ball> ballsTouched = new ArrayList<Ball>();

	public static List<CutBall> getLeftBallsList() {
		return cutBallLeftList;
	}

	public static List<CutBall> getRightBallsList() {
		return cutBallRightList;
	}

	public static void setScene(Scene scene) {
		CutBallGenerator.scene = scene;
	}

	public static void loadBallObject() {

		if (cutBall == null) {
			IParser parser = Parser
					.createParser(Parser.Type.OBJ, Shared.context()
							.getResources(), "com.knb:raw/cut_ball1_obj", true);
			parser.parse();

			cutBall = parser.getParsedObject();
		}
	}

	private static CutBall getLeftCutBall(float xTouch, float yTouch) {
		Object3d cutBallLeft = cutBall.clone();
		cutBallLeft.rotation().initRotAngle -= 90;
		cutBallLeft.rotation().initYDir = 1;
		cutBallLeft.rotation().y -= 90;
		CutBall cBallLeft = new CutBall(cutBallLeft, xTouch, yTouch);

		synchronized (cutBallLeftList) {
			cutBallLeftList.add(cBallLeft);
		}
		synchronized (scene) {
			scene.addChild(cutBallLeft);
		}
		return cBallLeft;
	}

	private static CutBall getRightCutBall(float xTouch, float yTouch) {
		Object3d cutBallRight = cutBall.clone();
		cutBallRight.rotation().initRotAngle += 90;
		cutBallRight.rotation().initYDir = 1;
		cutBallRight.rotation().y += 90;
		CutBall cBallRight = new CutBall(cutBallRight, xTouch, yTouch);

		synchronized (cutBallRight) {
			cutBallRightList.add(cBallRight);
		}
		synchronized (scene) {
			scene.addChild(cutBallRight);
		}
		return cBallRight;
	}

	/**
	 * This function returns the obj if the screen touch is on the obj.
	 * 
	 * @param xTouch
	 * @param yTouch
	 * @return
	 */
	private static Ball getTouchedBall(float xTouch, float yTouch) {

		List<Ball> balls = BallGenerator.list();

		for (int i = 0; i < balls.size(); i++) {
			Ball b = balls.get(i);
			float xDist = b.getTemporaryStateHolder().getX() - xTouch;
			float yDist = b.getTemporaryStateHolder().getY() - yTouch;

			// Code when touch was on obj
			if (Math.abs(xDist) < 2 * SBStore.RADIUS
					&& Math.abs(yDist) < 2 * SBStore.RADIUS) {
				return b;
			}

		}
		return null;
	}

	private static List<Ball> getSwordCutBall(float xCoordKatana,
			List<Ball> balls) {

		for (int i = 0; i < balls.size(); i++) {
			Ball b = balls.get(i);
			float xDist = b.getTemporaryStateHolder().getX() - xCoordKatana;

			if (Math.abs(xDist) < SBStore.RADIUS
					&& b.getTemporaryStateHolder().getY() < (-1f
							* SBStore.NORMALIZATION_Y_Katana
							+ SBStore.LENGTH_SWORD + SBStore.RADIUS)) {
				ballsTouched.add(b);
			}
		}
		return ballsTouched;

	}

	public static void replaceBallByCutBalls(float xTouch, List<Ball> list) {

		List<Ball> ballsTouched = getSwordCutBall(xTouch, list);

		for (Ball ballTouched : ballsTouched) {

			SoundManager.playBallCut();

			Log.d("CutBallGenerator", "ballTouched is " + ballTouched);

			if (ballTouched != null) {

				Scorer.score(ballTouched.getObjState().getSpeed().getXv());

				// At this point ball is remove from the BallGenerator's list
				synchronized (list) {
					list.remove(ballTouched);
				}
				synchronized (scene) {
					scene.removeChild(ballTouched.getObj3D());
				}

				CutBall cBallLeft = getLeftCutBall(ballTouched
						.getTemporaryStateHolder().getX(), ballTouched
						.getTemporaryStateHolder().getY());

				CutBall cBallRight = getRightCutBall(ballTouched
						.getTemporaryStateHolder().getX(), ballTouched
						.getTemporaryStateHolder().getY());

				Speed speedLeftCut = cBallLeft.getObjState().getSpeed();

				Speed speedRightCut = cBallRight.getObjState().getSpeed();

				Rotation rotationFullBall = ballTouched.getObjState()
						.getRotation();

				cBallLeft.getObjState().setRotation(rotationFullBall);

				cBallRight.getObjState().setRotation(rotationFullBall);

				speedLeftCut.setXv(-1f * InitBallStateGenerator.initxV * 0.1f);
				speedRightCut.setXv(1f * InitBallStateGenerator.initxV * 0.1f);

			}
		}
		ballsTouched.clear();
	}

	public static void clearRemovedBalls() {
		synchronized (cutBallLeftList) {
			Iterator<CutBall> itr = cutBallLeftList.iterator();
			while (itr.hasNext()) {
				CutBall cb = itr.next();
				if (cb.isRemoved()) {
					itr.remove();
					synchronized (scene) {
						scene.removeChild(cb.getObj3D());
					}
				}
			}
		}
		synchronized (cutBallRightList) {
			Iterator<CutBall> itr = cutBallRightList.iterator();
			while (itr.hasNext()) {
				CutBall cb = itr.next();
				if (cb.isRemoved()) {
					itr.remove();
					synchronized (scene) {
						scene.removeChild(cb.getObj3D());
					}
				}
			}
		}

	}

}
