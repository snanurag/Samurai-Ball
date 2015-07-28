package com.knb.ball.dynamics;

import min3d.core.Object3d;

import com.knb.ball.AbstractObject;
import com.knb.ball.Ball;
import com.knb.ball.CutBall;
import com.knb.ball.Rotation;

public class RotationDynamics {

	/**
	 * This function is used to rotate the balls along the Custom direction and custom angle
	 * @param b
	 */
	public static void rotate(AbstractObject b) {

		Object3d ball = b.getObj3D();

		Rotation ballRotation = b.getObjState().getRotation();

		ball.rotation().setCustomRotationDirection(ballRotation.getxDir(),
				ballRotation.getyDir(), ballRotation.getzDir());

		ball.rotation().customRotAngle += ballRotation.getAngle();
	}

}
