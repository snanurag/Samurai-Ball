package com.knb.ball.dynamics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.util.Log;

import com.knb.ball.Ball;
import com.knb.constants.SBStore;
import com.knb.generators.BallGenerator;

public class CollisionDynamics {

	public static String TAG = "SamuraiBall:CollisionDynamics->";
	private static List<Ball> collisionList = new ArrayList<Ball>();

	private static XCoordComparator xCoordComparator = new XCoordComparator();

	public static List getCollisionList() {
		return collisionList;
	}

	public static void performCollision() {

		sortCollisionList();

		for (int i = 0; i < collisionList.size() - 1; i++) {
			for (int j = i + 1; j < collisionList.size(); j++) {
				if (collisionList.get(j).getTemporaryStateHolder().getX()
						- collisionList.get(i).getTemporaryStateHolder().getX() <= SBStore.COLLISION_DISTANCE) {
					if (collisionList.get(i).getObjState().isBallStationary()
							&& collisionList.get(j).getObjState()
									.isBallStationary()
							|| collisionList.get(i).getObjState().isColloid()
							|| collisionList.get(j).getObjState().isColloid())
						continue;

					if (isCollisionPossible(collisionList.get(i), collisionList
							.get(j))) {
						doColloide(collisionList.get(i), collisionList.get(j));
					}

				} else {
					break;
				}

			}

		}
	}

	/**
	 * Sorts the collisionList on the basis of their x coordinate.
	 */
	private static void sortCollisionList() {
		Collections.sort(collisionList, xCoordComparator);
	}

	/**
	 * Calculates if the collision between two balls is possible.
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	private static boolean isCollisionPossible(Ball b1, Ball b2) {

		if (Math.abs(b1.getTemporaryStateHolder().getX() - b2.getTemporaryStateHolder().getX()) <= SBStore.COLLISION_DISTANCE
				&& Math
						.abs(b1.getTemporaryStateHolder().getY()
								- b2.getTemporaryStateHolder().getY()) <= SBStore.COLLISION_DISTANCE
				&& getDistanceBetweenBalls(b1, b2) <= SBStore.COLLISION_DISTANCE
				&& areBallsApproaching(b1, b2))
			return true;
		else
			return false;
	}

	/**
	 * Returns distance between two balls.
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	private static float getDistanceBetweenBalls(Ball b1, Ball b2) {
		float dist = 0;
		float x = b1.getTemporaryStateHolder().getX() - b2.getTemporaryStateHolder().getX();
		float y = b1.getTemporaryStateHolder().getY() - b2.getTemporaryStateHolder().getY();
		dist = (float) Math.pow(x * x + y * y, 0.5);
		return dist;
	}

	/**
	 * On collision of Ball b1 and b2, their speeds are interchanged if both are
	 * moving. If one obj is stationary then the other balls speed is reversed.
	 * 
	 * @param b1
	 * @param b2
	 */
	private static void doColloide(Ball b1, Ball b2) {

		float xVB1 = b1.getObjState().getSpeed().getXv();
		float yVB1 = b1.getObjState().getSpeed().getYv();

		float xVB2 = b2.getObjState().getSpeed().getXv();
		float yVB2 = b2.getObjState().getSpeed().getYv();

		if (b1.getObjState().isBallStationary()) {
			b2.getObjState().getSpeed().setXv(
					-1f * xVB2 / SBStore.SPEED_REDUCTION_X);
			b2.getObjState().getSpeed().setYv(
					-1f * yVB2 / SBStore.SPEED_REDUCTION_Y);
		} else if (b2.getObjState().isBallStationary()) {
			b1.getObjState().getSpeed().setXv(
					-1f * xVB1 / SBStore.SPEED_REDUCTION_X);
			b1.getObjState().getSpeed().setYv(
					-1f * yVB1 / SBStore.SPEED_REDUCTION_Y);
		} else {
			b1.getObjState().getSpeed().setXv(xVB2);
			b1.getObjState().getSpeed().setYv(yVB2);

			b2.getObjState().getSpeed().setXv(xVB1);
			b2.getObjState().getSpeed().setYv(yVB1);
		}

		b1.getObjState().setColloid();
		b2.getObjState().setColloid();

		Log.d("SamuraiBall", "Ball b1 after collision: Speed xv "
				+ b1.getObjState().getSpeed().getXv() + " and yv "
				+ b1.getObjState().getSpeed().getYv() + " Position x "
				+ b1.getTemporaryStateHolder().getX() + " and y "
				+ b1.getTemporaryStateHolder().getY());
		Log.d("SamuraiBall", "Ball b2 after collision: Speed xv "
				+ b2.getObjState().getSpeed().getXv() + " and yv "
				+ b2.getObjState().getSpeed().getYv() + " Position x "
				+ b2.getTemporaryStateHolder().getX() + " and y "
				+ b2.getTemporaryStateHolder().getY());
	}

	/**
	 * Clears the collision state of all the balls in the collision list.
	 */
	public static void clearCollisions() {
		for (Ball b : collisionList) {
			b.getObjState().clearColloid();
		}
	}

	/**
	 * This function calculates if the two balls are approaching to each other.
	 * It first calculates the relative velocity of Ball b1 and b2. Then it
	 * takes the dot product of perpendicular unit vector of velocity and
	 * distance between the balls to find the minimum possible distance at the
	 * time of collision. That distance should be less than or equal to 2 times
	 * of radius.
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean areBallsApproaching(Ball b1, Ball b2) {
		
		float[] vRelXY = getRelativeVelofBalls(b1, b2);
		float xVRel = vRelXY[0];
		float yVRel = vRelXY[1];

		if (xVRel < 0 || yVRel < 0) {
			float vRel = (float) Math.pow(xVRel * xVRel + yVRel * yVRel, 0.5);
			float minDistAtCollisionTime = getDotProduct(b1.getTemporaryStateHolder()
					.getX()
					- b2.getTemporaryStateHolder().getX(), b1.getTemporaryStateHolder().getY()
					- b2.getTemporaryStateHolder().getY(), yVRel / vRel, -1f * xVRel
					/ vRel);
			if (minDistAtCollisionTime <= 2f * SBStore.RADIUS) {
				return true;
			} else
				return false;

		} else {
			return false;
		}

	}

	/**
	 * It returns the array containing x and y direction relative velocities of
	 * Ball b1 and b2.
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	private static float[] getRelativeVelofBalls(Ball b1, Ball b2) {
		
		float xVRel = 0;
		float yVRel = 0;

		if (b1.getTemporaryStateHolder().getX() < b2.getTemporaryStateHolder().getX()) {
			xVRel = b2.getObjState().getSpeed().getXv()
					- b1.getObjState().getSpeed().getXv();
		} else {
			xVRel = b1.getObjState().getSpeed().getXv()
					- b2.getObjState().getSpeed().getXv();
		}

		if (b1.getTemporaryStateHolder().getY() < b2.getTemporaryStateHolder().getY()) {
			yVRel = b2.getObjState().getSpeed().getYv()
					- b1.getObjState().getSpeed().getYv();
		} else {
			yVRel = b1.getObjState().getSpeed().getYv()
					- b2.getObjState().getSpeed().getYv();
		}

		float[] vRel = { xVRel, yVRel };

		return vRel;

	}

	/**
	 * This function will return the dot product of (x1,y1) and (x2,y2) vectors.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private static float getDotProduct(float x1, float y1, float x2, float y2) {
		return x1 * x2 + y1 * y2;
	}

	public static void setBallsAt2R() {
		
		sortCollisionList();
		
		for (int i = 0; i < collisionList.size() - 1; i++) {
			for (int j = i + 1; j < collisionList.size(); j++) {
				if (getDistanceBetweenBalls(collisionList.get(i), collisionList
						.get(j)) < 2 * SBStore.RADIUS) {
					ensure2RDistanceBetweenBalls(collisionList.get(i),
							collisionList.get(j));
				}
				else{
					break;
				}
			}
		}
	}

	/**
	 * Equation would be (x+vxt)2 + (y+vyt)2 = (2R)2 => (vx2+vy2)t2 +
	 * 2(x*vx+y*vy)t + x2+y2 - (2R)2 = 0
	 * 
	 * 
	 * @param b1
	 * @param b2
	 */
	private static void ensure2RDistanceBetweenBalls(Ball b1, Ball b2) {

		if (getDistanceBetweenBalls(b1, b2) < 2f * SBStore.RADIUS) {

			float[] vRelXY = getRelativeVelofBalls(b1, b2);

			float vXRel = vRelXY[0];
			float vYRel = vRelXY[1];

			float x = Math.abs(b1.getTemporaryStateHolder().getX()
					- b2.getTemporaryStateHolder().getX());
			float y = Math.abs(b1.getTemporaryStateHolder().getY()
					- b2.getTemporaryStateHolder().getY());

			Log.d(TAG + "ensure2RDistanceBetweenBalls ",
					"Before ensuring 2R the b1 position ("
							+ b1.getTemporaryStateHolder().getX() + ","
							+ b1.getTemporaryStateHolder().getY() + ") and b2 position ("
							+ b2.getTemporaryStateHolder().getX() + ","
							+ b2.getTemporaryStateHolder().getY() + ")");

			if (areBallsApproaching(b1, b2)) {

				vXRel = -vXRel;
				vYRel = -vYRel;

				float t = findTimeToSeperate(x, y, vXRel, vYRel);

				if (t > 0) {
					float xb1 = -1f * b1.getObjState().getSpeed().getXv() * t
							+ b1.getTemporaryStateHolder().getX();
					float yb1 = -1f * b1.getObjState().getSpeed().getYv() * t
							+ b1.getTemporaryStateHolder().getY();
					float xb2 = -1f * b2.getObjState().getSpeed().getXv() * t
							+ b2.getTemporaryStateHolder().getX();
					float yb2 = -1f * b2.getObjState().getSpeed().getYv() * t
							+ b2.getTemporaryStateHolder().getY();

					b1.getTemporaryStateHolder().setPosition(xb1, yb1, 0);
					b2.getTemporaryStateHolder().setPosition(xb2, yb2, 0);

				}
			}

			// No manipulation is required for now when balls are separating
			// apart.

			// else if (vXRel > 0 && vYRel >= 0) {
			// float t = findTimeToSeperate(x, y, vXRel, vYRel);
			//
			// if (t > 0) {
			// float xb1 = b1.getBallState().getSpeed().getXv() * t
			// + b1.getBallState().getX();
			// float yb1 = b1.getBallState().getSpeed().getYv() * t
			// + b1.getBallState().getY();
			// float xb2 = b2.getBallState().getSpeed().getXv() * t
			// + b2.getBallState().getX();
			// float yb2 = b2.getBallState().getSpeed().getYv() * t
			// + b2.getBallState().getY();
			//
			// b1.getBallState().setPosition(xb1, yb1, 0);
			// b2.getBallState().setPosition(xb2, yb2, 0);
			//
			//
			// }
			// }

			Log.d(TAG + "ensure2RDistanceBetweenBalls ",
					"After ensuring 2R the b1 position ("
							+ b1.getTemporaryStateHolder().getX() + ","
							+ b1.getTemporaryStateHolder().getY() + ") and b2 position ("
							+ b2.getTemporaryStateHolder().getX() + ","
							+ b2.getTemporaryStateHolder().getY() + ")");

		}

	}

	private static float findTimeToSeperate(float x, float y, float vXRel,
			float vYRel) {
		// c = x2+y2 - (2R)2
		float c = x * x + y * y - 4 * SBStore.RADIUS * SBStore.RADIUS;

		if (c < 0) {

			// b = 2(x*vx+y*vy)
			float b = 2 * (x * vXRel + y * vYRel);

			// a = (vx2+vy2)
			float a = vXRel * vXRel + vYRel * vYRel;

			float D = b * b - 4 * a * c;

			float t = (-b + (float) Math.pow(D, 0.5)) / (2f * a);

			Log.d(TAG + "findTimeToSeperate ", "The a b c and t are a " + a
					+ " b " + b + " c " + c + " and t " + t);

			return t;
		} else
			return -1;

	}
}
