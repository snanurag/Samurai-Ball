package com.knb.ball;

import android.util.Log;

import com.knb.constants.SBStore;
import com.knb.generators.BallGenerator;
import com.knb.generators.InitBallStateGenerator;

public class Speed {

	private float initX;

	private float xv; // velocity value on the X axis
	private float yv; // velocity value on the Y axis

	private float accX; // acceleration value on X axis
	private float accY; // acceleration value on Y axis

	private float initXv;

	public Speed() {
		this.xv = InitBallStateGenerator.initxV
				* InitBallStateGenerator.getInitXvFactor();
		if (initX == 1 * SBStore.NORMALIZATION_X)
			this.xv = -1 * this.xv;
		this.yv = InitBallStateGenerator.inityV;
		this.accX = InitBallStateGenerator.initAccX;
		this.accY = InitBallStateGenerator.initAccY;
		this.initXv = this.xv;
	}

	public void reConfigureSpeed() {
		this.xv = InitBallStateGenerator.initxV
				* InitBallStateGenerator.getInitXvFactor();

		this.yv = InitBallStateGenerator.inityV;
		this.accX = InitBallStateGenerator.initAccX;
		this.accY = InitBallStateGenerator.initAccY;

		if (BallGenerator.getNoOfGeneratedBalls() <= 6) {
			this.xv = this.xv % InitBallStateGenerator.SPEED_MEDIUM_ZONE_START;
			Log.i(SBStore.TAG,
					" speed of ball under 6 being the total no. of balls"
							+ this.xv);
		}
		
		if(this.xv < InitBallStateGenerator.SPEED_MINIMUM){
			this.xv = InitBallStateGenerator.SPEED_MINIMUM;
		}
		
		if (initX == 1 * SBStore.NORMALIZATION_X)
			this.xv = -1 * this.xv;
		this.initXv = this.xv;

	}

	public float getXv() {
		return xv;
	}

	public void setXv(float xv) {
		this.xv = xv;
	}

	public float getYv() {
		return yv;
	}

	public void setYv(float yv) {
		this.yv = yv;
	}

	public void updateSpeed(long timeElapsed) {
		updateSpeed(accX, accY, timeElapsed);
	}

	public void updateSpeed(float accX, float accY, long timeElapsed) {
		xv += accX * timeElapsed;
		yv += accY * timeElapsed;

	}

	public boolean isStopped() {
		if (Math.abs(xv) <= Math.abs(SBStore.TERMINAL_XV)
				&& Math.abs(yv) <= Math.abs(SBStore.TERMINAL_YV))
			return true;
		else
			return false;
	}

	public void setAccY(float accY) {
		this.accY = accY;
	}

	public float getInitXv() {
		return initXv;
	}

	public void registerInitXPosition(float initX) {
		this.initX = initX;
	}

	public void setInitXvToZero() {
		initXv = 0;
	}
}
