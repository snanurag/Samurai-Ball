package com.knb.ball;

import min3d.core.Object3d;
import android.util.Log;

import com.knb.constants.SBStore;

public class CutBall extends AbstractObject{


	public CutBall(Object3d cutBall, float x, float y) {
		super(cutBall, SBStore.BALL_SCALE);
		
		this.obj.position().x = x;
		this.obj.position().y = y;

	}

	public void updateState(long timeElapsed) {

		try {

			objState.getSpeed().updateSpeed(timeElapsed);

			float x = obj.position().x + objState.getSpeed().getXv() * timeElapsed;
			float y = obj.position().y + objState.getSpeed().getYv() * timeElapsed;

			objState.setPosition(x, y, 0f);

			if (x >= 1f * SBStore.NORMALIZATION_X || x <= -1f * SBStore.NORMALIZATION_X || y <= -1f * SBStore.NORMALIZATION_Y) {
				markRemoved();
			} 

		} catch (Exception e) {
			Log.e("SamuraiBall", "RuntimeException in Ball.updateState() ",
					e);
		}
	}

}
