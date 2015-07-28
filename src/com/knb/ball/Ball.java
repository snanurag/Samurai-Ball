package com.knb.ball;

import min3d.core.Object3d;
import android.util.Log;

import com.knb.constants.SBStore;
import com.knb.data.ContextData;
import com.knb.generators.InitBallStateGenerator;

public class Ball extends AbstractObject {

	private TemporaryStateHolder tempStateHolder;

	private int collisionCounter;

	private static final int MAX_ALLOWED_COLLISIONS = 2;

	private float initX = InitBallStateGenerator.initX
			* InitBallStateGenerator.getDirection();

	private float initY = InitBallStateGenerator.initY;

	public Ball(Object3d ball) {

		super(ball, SBStore.BALL_SCALE);
		ball.position().x = initX;
		ball.position().y = initY;

		getObjState().getSpeed().registerInitXPosition(initX);

		getObjState().getSpeed().reConfigureSpeed();

		tempStateHolder = new TemporaryStateHolder();
	}

	public TemporaryStateHolder getTemporaryStateHolder() {
		return tempStateHolder;
	}

	public void setTemporarytoActuallyState() {

		objState.setPosition(tempStateHolder.getX(), tempStateHolder.getY(),
				tempStateHolder.getZ());
	}

	public void updateState(long timeElapsed) {

		try {
			if (objState.isBallStationary())
				return;

			objState.getSpeed().updateSpeed(timeElapsed);

			float x = obj.position().x + objState.getSpeed().getXv()
					* timeElapsed;
			float y = obj.position().y + objState.getSpeed().getYv()
					* timeElapsed;

			tempStateHolder.setPosition(x, y, 0f);

			if (x >= 1f * SBStore.NORMALIZATION_X) {
				collisionCounter++;
				obj.position().x = 1f * SBStore.NORMALIZATION_X;
				objState.getSpeed().setXv(
						-1f * objState.getSpeed().getXv()
								/ SBStore.SPEED_REDUCTION_X);
			} else if (x <= -1f * SBStore.NORMALIZATION_X) {

				collisionCounter++;
				obj.position().x = -1f * SBStore.NORMALIZATION_X;
				objState.getSpeed().setXv(
						-1f * objState.getSpeed().getXv()
								/ SBStore.SPEED_REDUCTION_X);
			}

			if (y <= -1f * SBStore.NORMALIZATION_Y) {

				collisionCounter++;

				// This logic is for collision of balls at the bottom
				// surface

				obj.position().y = -1f * SBStore.NORMALIZATION_Y;
				objState.getSpeed().setYv(
						-1f * objState.getSpeed().getYv()
								/ SBStore.SPEED_REDUCTION_Y);
				objState.getSpeed()
						.setXv(
								objState.getSpeed().getXv()
										/ SBStore.SPEED_REDUCTION_X);

				// Removing the obj from the canvas when it hits the
				// bottom after three collisions.
				if (Math.abs(getObjState().getSpeed().getInitXv()) > InitBallStateGenerator.SPEED_HIGHEST_ZONE_START) {
					if (collisionCounter > MAX_ALLOWED_COLLISIONS)
						markRemoved();
				} else if (collisionCounter >= MAX_ALLOWED_COLLISIONS)
					markRemoved();

			}

			Log.d(SBStore.TAG, "Next set position is x " + obj.position().x
					+ " and y " + obj.position().y);

			Log.d(SBStore.TAG, "Next update speed xv "
					+ objState.getSpeed().getXv() + " and yv "
					+ objState.getSpeed().getYv());

		} catch (Exception e) {
			Log.e(SBStore.TAG, "RuntimeException in Ball.updateState() ", e);
		}
	}

	public class TemporaryStateHolder {

		public float getX() {
			return obj.position().x;
		}

		public float getY() {
			return obj.position().y;
		}

		public float getZ() {
			return obj.position().z;
		}

		public void setPosition(float x, float y, float z) {
			obj.position().x = x;
			obj.position().y = y;
			obj.position().z = z;
		}

	}

	public void markRemoved() {
		super.markRemoved();

		// if any of the ball is missed i.e. marked removed then trigger game
		// over activity.
		if (ContextData.getValue(SBStore.ACTIVITY) != null
				&& ContextData.getValue(SBStore.ACTIVITY).equals(
						SBStore.MAIN_ACTIVITY)) {
			ContextData.setValue(SBStore.ACTIVITY, SBStore.GAME_OVER_ACTIVITY);

		}
	}

	public void increaseCollisionCounter() {
		collisionCounter++;
	}
}
