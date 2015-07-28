package com.knb.generators;

import com.knb.constants.SBStore;

public class InitBallStateGenerator {

	public static float initX;

	public static float initY;

	public static float initxV;

	public static float inityV;

	public static float initAccX;

	public static float initAccY;
	
	public static float SPEED_HIGHEST_ZONE_START; 
	
	public static float SPEED_MEDIUM_ZONE_START;
	
	public static float SPEED_MINIMUM;
	
	public static void loadInitBallStateGenerator() {
		initX = 1f * SBStore.NORMALIZATION_X;

		initY = 1f * SBStore.NORMALIZATION_Y;

		initxV = 0.005f * SBStore.NORMALIZATION_X;

		inityV = 0f * SBStore.NORMALIZATION_Y;

		initAccX = 0f * SBStore.NORMALIZATION_X;

		initAccY = -0.000003f * SBStore.NORMALIZATION_Y;

		SPEED_HIGHEST_ZONE_START = 0.7f * InitBallStateGenerator.initxV;
		
		SPEED_MEDIUM_ZONE_START = 0.4f * InitBallStateGenerator.initxV;
		
		SPEED_MINIMUM = 0.1f * InitBallStateGenerator.initxV;
	}

	public static float getInitXvFactor() {
		float factor = (float) Math.random();
		if (factor < 0.1f) {
			return 0.1f;
		}
		return factor;
	}

	public static int getDirection() {
		if (Math.random() < 0.5) {
			return 1;
		} else
			return -1;
	}
}
