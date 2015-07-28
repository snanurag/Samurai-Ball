package com.knb.constants;

import android.opengl.GLSurfaceView;

public class SBStore {

	public SBStore(GLSurfaceView glSurfaceView) {

		/*
		 * Normalization along x and y is taken so that no matter where are the
		 * coordinates in the whole frustum, it can be worked with -1 and 1 of x
		 * and y extreme coordinates just multiplying them with normalization of
		 * x and y.
		 */

		NORMALIZATION_X = screenVsNearPlaneRatio * nearPlaneHeight
				* glSurfaceView.getWidth() / glSurfaceView.getHeight();
		NORMALIZATION_Y = screenVsNearPlaneRatio * nearPlaneHeight;

		RADIUS_ABSOLUTE = 0.25f;

		RADIUS = RADIUS_ABSOLUTE * BALL_SCALE;

		COLLISION_DISTANCE = 2.01f * RADIUS;

		screenVsNearPlaneRatioKatana = (5 - RADIUS) / nearPlaneDistance;

		NORMALIZATION_Y_Katana = screenVsNearPlaneRatioKatana * nearPlaneHeight;

		NORMALIZATION_X_Katana = screenVsNearPlaneRatioKatana * nearPlaneHeight
				* glSurfaceView.getWidth() / glSurfaceView.getHeight();
		
		DIST_BETWEEN_LETTERS_MAX = 0.5f*NORMALIZATION_X;

		DIST_BETWEEN_LETTERS_MIN = 0.2f*NORMALIZATION_X;

	}

	public static float NORMALIZATION_X;

	public static float NORMALIZATION_Y;

	private static float RADIUS_ABSOLUTE;

	public static float RADIUS;

	public static float COLLISION_DISTANCE;

	public static float nearPlaneHeight = 0.5f;

	public static float nearPlaneDistance = 3f;

	public static float farPlaneDistance = 7f;

	public static float screenVsNearPlaneRatio = 5f / nearPlaneDistance;

	public static float screenVsNearPlaneRatioKatana;

	public static float NORMALIZATION_Y_Katana;

	public static float NORMALIZATION_X_Katana;

	public static float BALL_SCALE = 0.25f;

	public static float TERMINAL_XV = 0.00000000001f;

	public static float TERMINAL_YV = 0.00008f;

	public static float SPEED_REDUCTION_X = 2f;

	public static float SPEED_REDUCTION_Y = 1.4f;

	public static float CUT_SPEED_REDUCTION_Y = 0.2f;

	public static float CUT_SPEED_REDUCTION_X = 0.75f;

	public static float CUT_SPEED_INCREMENT_X = 1.25f;

	public static float KATANA_SCALE = 0.5f;
	
	private static float LENGTH_SWORD_ABSOLUTE = 2.22f;
	
	public static float LENGTH_SWORD = LENGTH_SWORD_ABSOLUTE * KATANA_SCALE;
	
	public static float LENGTH_HANDLE_ABSOLUTE = 0.86f;
	
	public static float LENGTH_HANDLE = LENGTH_HANDLE_ABSOLUTE * KATANA_SCALE;
	
	public static float DIST_BETWEEN_LETTERS_MAX;

	public static float DIST_BETWEEN_LETTERS_MIN;
	
	public static float TEXT_SCALE_MEDIUM = 0.3f;
	
	public static float TEXT_SCALE_SMALL = 0.2f;
	
	public static String EXIT_MESSAGE = "Sure you want to Quit?";

	public static String RESUME_BUTTON = "resume";
	
	public static String QUIT_BUTTON = "quit";
	
	public static float BACKGROUND_SCALE = 1.25f;
	
	public static float LOADING_SCALE = 1.22f;
	
	public static String TAG = "SamuraiBall";
	
	// ContextData Constants
	public static String ACTIVITY = "activity";

	public static String MAIN_ACTIVITY = "main_activity";
	
	public static String GAME_OVER_ACTIVITY = "game_over_activity";
	
	public static String FIRST_PAGE_ACTIVITY = "first_page_activity";
	
	public static String LOADING_ACTIVITY = "loading_activity";
	
	public static String BEST_SCORE_ACTIVITY  = "best_activity";
	
}
