package com.knb.ball;

import com.knb.generators.InitBallStateGenerator;

public class Rotation {

	private float xDir = (float)(InitBallStateGenerator.getDirection()*Math.random());
	private float yDir = (float)(InitBallStateGenerator.getDirection()*Math.random());
	private float zDir = (float)(InitBallStateGenerator.getDirection()*Math.random());

	private int angle = 5;

	public float getxDir() {
		return xDir;
	}

	public float getyDir() {
		return yDir;
	}

	public float getzDir() {
		return zDir;
	}

	public int getAngle() {
		return angle;
	}
	
	
}
