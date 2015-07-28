package com.knb.generators;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import min3d.Shared;
import min3d.core.Object3d;
import min3d.core.Scene;
import min3d.parser.IParser;
import min3d.parser.Parser;
import android.content.Context;
import android.util.Log;

import com.knb.ball.Ball;
import com.knb.constants.SBStore;

public class BallGenerator extends Thread {

	private static volatile Object3d ball;

	private static volatile Scene scene;

	private static volatile int counter;
	
	private static long BALL_GENERATOR_PERIOD = 2000;

	private static int BALLS_LIMIT = 5;

	private static boolean isAlive = true;
	
	private static List<Ball> list = new LinkedList<Ball>();

	public BallGenerator(Scene scene) {
		BallGenerator.scene = scene;
		if (ball == null) {
			loadBallObject();
		}
	}

	public static synchronized void loadBallObject() {
		IParser parser = Parser.createParser(Parser.Type.OBJ, Shared.context()
				.getResources(), "com.knb:raw/ball_final_obj", true);
		parser.parse();

		ball = parser.getParsedObject();
	}

	public static Object3d getBall(Scene scene) {
		if (ball == null) {
			loadBallObject();
		}
		Object3d b = ball.clone();
		scene.addChild(b);
		counter++;
		return b;
	}

	private void generateBalls() {
		try {

			Object3d ball = BallGenerator.ball.clone();

			synchronized (list) {
				list.add(new Ball(ball));
			}
			synchronized (scene) {
				scene.addChild(ball);
			}
			if (list.size() > BALLS_LIMIT) {
				synchronized (list) {
					synchronized (scene) {
						scene.removeChild(list.remove(0).getObj3D());
					}
				}
			}
			counter++;
		} catch (RuntimeException re) {
			Log.e(SBStore.TAG, "Runtime exception in generateBalls()");
		}
	}

	@Override
	public void run() {
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Log.i(SBStore.TAG, " BallGenerator Thread was interrupted before execution.");
		}
		execute();

		// Test Cases
		// makeBallBed();
		// makeTwoBallsFallAtOppEnds();
		// twoBallsFallAtNearSameSpeed();
		// makeFourRandomBallsFall();

	}

	private void execute() {
		while (true) {
			try {
				while(isAlive){
					generateBalls();
					Thread.sleep(BALL_GENERATOR_PERIOD);
				}
			} catch (InterruptedException e) {
				Log.d("SamuraiBall", "Ball generator thread is interrupted");
				break;
			}
		}

	}

	public static List<Ball> list() {
		return list;
	}

	public static void clearRemovedBalls() {

		synchronized (list) {
			Iterator<Ball> itr = list.iterator();
			while (itr.hasNext()) {
				Ball b = itr.next();
				if (b.isRemoved()) {
					itr.remove();
					synchronized (scene) {
						scene.removeChild(b.getObj3D());
					}
				}
			}
		}
	}

	public static boolean removeBall(Ball b) {
		try {
			synchronized (list) {
				list.remove(b);
			}
			synchronized (scene) {
				scene.removeChild(b.getObj3D());
			}
			return true;
		} catch (RuntimeException re) {
			Log.e("BallGenerator->removeBall()",
					"Error in removing single obj from the list");
			return false;
		}
	}

	public static void setAliveValue(boolean alive){
		isAlive = alive;
	}
	
	public static int getNoOfGeneratedBalls(){
		return counter;
	}
	/**
	 * Make a obj bed for tests.
	 */
	private static void makeBallBed() {

		float initX = -1f * SBStore.NORMALIZATION_X;

		for (int i = 1; i <= 9; i++) {
			Object3d ball = BallGenerator.ball.clone();
			Ball b = new Ball(ball);

			b.getObjState().setPosition(
					initX + 0.2f * i * SBStore.NORMALIZATION_X,
					-1f * SBStore.NORMALIZATION_Y, 0);
			list.add(b);

			scene.addChild(ball);

		}

	}

	/**
	 * Make the two balls fall at opposite ends to colloid
	 */
	private static void makeTwoBallsFallAtOppEnds() {

		Object3d b1 = BallGenerator.ball.clone();

		Object3d b2 = BallGenerator.ball.clone();

		Ball ball1 = new Ball(b1);
		Ball ball2 = new Ball(b2);

		b1.position().x = -1f * InitBallStateGenerator.initX;
		ball1.getObjState().getSpeed().setXv(
				0.25f * InitBallStateGenerator.initxV);

		b2.position().x = 1f * InitBallStateGenerator.initX;
		ball2.getObjState().getSpeed().setXv(InitBallStateGenerator.initxV);

		list.add(ball1);
		list.add(ball2);

		scene.addChild(b1);
		scene.addChild(b2);

		BALLS_LIMIT = 2;

	}

	/**
	 * Test
	 */
	private static void twoBallsFallAtNearSameSpeed() {

		Object3d b1 = BallGenerator.ball.clone();

		Object3d b2 = BallGenerator.ball.clone();

		Ball ball1 = new Ball(b1);
		Ball ball2 = new Ball(b2);

		b1.position().x = -1f * InitBallStateGenerator.initX;
		ball1.getObjState().getSpeed().setXv(
				0.25f * InitBallStateGenerator.initxV);

		b2.position().x = -1f * InitBallStateGenerator.initX;
		ball2.getObjState().getSpeed().setXv(
				0.3f * InitBallStateGenerator.initxV);

		list.add(ball1);
		list.add(ball2);

		scene.addChild(b1);
		scene.addChild(b2);
	}

	private static void makeFourRandomBallsFall() {
		Object3d b1 = BallGenerator.ball.clone();

		Object3d b2 = BallGenerator.ball.clone();

		Object3d b3 = BallGenerator.ball.clone();

		Object3d b4 = BallGenerator.ball.clone();

		Ball ball1 = new Ball(b1);
		Ball ball2 = new Ball(b2);
		Ball ball3 = new Ball(b3);
		Ball ball4 = new Ball(b4);

		list.add(ball1);
		list.add(ball2);
		list.add(ball3);
		list.add(ball4);

		scene.addChild(b1);
		scene.addChild(b2);
		scene.addChild(b3);
		scene.addChild(b4);
	}
}
