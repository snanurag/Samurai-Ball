package com.knb;

import javax.microedition.khronos.opengles.GL10;

import min3d.Shared;
import min3d.core.Renderer;
import min3d.core.RendererActivity;
import min3d.vos.FrustumManaged;
import min3d.vos.Light;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

import com.knb.constants.SBStore;
import com.knb.data.ContextData;
import com.knb.data.ObjectLoader;
import com.knb.generators.BallGenerator;
import com.knb.generators.CutBallGenerator;
import com.knb.generators.InitBallStateGenerator;
import com.knb.images.Background;
import com.knb.obj.sword.Katana;
import com.knb.sounds.SoundManager;

public class CommonActivity extends RendererActivity {

	private MainActivity mainActivity;

	private FrontPage frontPage;

	private GameOverActivity gameOverActivity;

	private LoadingPage loadingPage;

	private BestScoreActivity bestScoreActivity;
	
	private boolean isPopupOn;

	private AlertDialog alert;

	private int counter;

	private long lastUpdateTime;

	public void initScene() {

		init();

		updateViewFrustum();

		loadingPage = new LoadingPage(scene);
		loadingPage.initScene();
		mainActivity = new MainActivity(scene);
		frontPage = new FrontPage(scene);
		gameOverActivity = new GameOverActivity(scene);
		bestScoreActivity = new BestScoreActivity(scene);
	}

	public void updateScene() {

		updateViewFrustum();

		if (ContextData.getValue(SBStore.ACTIVITY) != null
				&& ContextData.getValue(SBStore.ACTIVITY).equals(
						SBStore.LOADING_ACTIVITY) && counter++ == 1) {
			new ObjectLoader(scene, this).run();

			ContextData.setValue(SBStore.ACTIVITY, SBStore.FIRST_PAGE_ACTIVITY);

		}

		if (!isPopupOn && ContextData.areObjsLoaded()) {
			if (!frontPage.isFrontPageLoaded()
					&& ContextData.getValue(SBStore.ACTIVITY) != null
					&& ContextData.getValue(SBStore.ACTIVITY).equals(
							SBStore.FIRST_PAGE_ACTIVITY)) {

				// This clearing will also clean up the Loading page
				bestScoreActivity.clearActivity();
				gameOverActivity.clearActivity();
				frontPage.initScene();

			}

			if (frontPage.isFrontPageLoaded()
					&& ContextData.getValue(SBStore.ACTIVITY) != null
					&& ContextData.getValue(SBStore.ACTIVITY).equals(
							SBStore.FIRST_PAGE_ACTIVITY)) {
				frontPage.updateScene();
			}

			if (!mainActivity.isMainActivityLoaded()
					&& ContextData.getValue(SBStore.ACTIVITY) != null
					&& ContextData.getValue(SBStore.ACTIVITY).equals(
							SBStore.MAIN_ACTIVITY)) {
				frontPage.clearFrontPage();
				mainActivity.initScene();

			}

			if (mainActivity.isMainActivityLoaded()
					&& ContextData.getValue(SBStore.ACTIVITY) != null
					&& ContextData.getValue(SBStore.ACTIVITY).equals(
							SBStore.MAIN_ACTIVITY)) {
				mainActivity.updateScene();
				lastUpdateTime = System.currentTimeMillis();
			}

			if (!gameOverActivity.isLoaded()
					&& ContextData.getValue(SBStore.ACTIVITY) != null
					&& ContextData.getValue(SBStore.ACTIVITY).equals(
							SBStore.GAME_OVER_ACTIVITY)) {

				if(mainActivity.isMainActivityLoaded()){
					mainActivity.clearMainActivity();
				}
				// Break between ending the main activity and loading the game
				// over activity.
				if (System.currentTimeMillis() - lastUpdateTime < 1500) {
					mainActivity.updateScene();
					return;
				}
				gameOverActivity.initScene();
			}

			if (gameOverActivity.isLoaded()
					&& ContextData.getValue(SBStore.ACTIVITY) != null
					&& ContextData.getValue(SBStore.ACTIVITY).equals(
							SBStore.GAME_OVER_ACTIVITY)) {

				// Main Activity contents get cleared but it keeps on running in
				// background
				mainActivity.updateScene();
				gameOverActivity.updateScene();
			}
			
			if (!bestScoreActivity.isLoaded()
					&& ContextData.getValue(SBStore.ACTIVITY) != null
					&& ContextData.getValue(SBStore.ACTIVITY).equals(
							SBStore.BEST_SCORE_ACTIVITY)) {

				if(frontPage.isFrontPageLoaded()){
					frontPage.clearFrontPage();
				}
				
				bestScoreActivity.initScene();
			}
			
			if (bestScoreActivity.isLoaded()
					&& ContextData.getValue(SBStore.ACTIVITY) != null
					&& ContextData.getValue(SBStore.ACTIVITY).equals(
							SBStore.BEST_SCORE_ACTIVITY)) {

				bestScoreActivity.updateScene();
			}
		}
	}

	private void updateViewFrustum() {

		try {
			float width = (float) glSurfaceView().getWidth();
			float height = (float) glSurfaceView().getHeight();

			Renderer r = Shared.renderer();
			GL10 gl = r.gl();
			FrustumManaged vf = scene.camera().frustum;
			float n = vf.shortSideLength() / 2f;

			float lt, rt, btm, top;

			float _surfaceAspectRatio = width / height;
			lt = vf.horizontalCenter() - n * _surfaceAspectRatio;
			rt = vf.horizontalCenter() + n * _surfaceAspectRatio;
			btm = vf.verticalCenter() - n * 1;
			top = vf.verticalCenter() + n * 1;

			if (_surfaceAspectRatio > 1) {
				lt *= 1f / _surfaceAspectRatio;
				rt *= 1f / _surfaceAspectRatio;
				btm *= 1f / _surfaceAspectRatio;
				top *= 1f / _surfaceAspectRatio;
			}

			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glFrustumf(lt, rt, btm, top, SBStore.nearPlaneDistance,
					SBStore.farPlaneDistance);
			// gl.glMatrixMode(GL10.GL_MODELVIEW);

		} catch (RuntimeException e) {
			Log.e("SamuraiBall",
					"Runtime Exception in CommonActivity.updateViewFrustum()",
					e);
		}
	}

	private void init() {

		new SBStore(glSurfaceView());
		InitBallStateGenerator.loadInitBallStateGenerator();
		loadLights();

	}

	private void loadLights() {
		scene.lights().add(getNewLight());
		scene.lights().add(getNewLight());
		scene.lights().add(getNewLight());

	}

	private Light getNewLight() {
		Light l = new Light();
		l.spotCutoffAngle(10f);
		return l;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			android.os.Process.killProcess(android.os.Process.myPid());

		}

	}

	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		isPopupOn = true;
		BallGenerator.setAliveValue(false);
		builder.setMessage(SBStore.EXIT_MESSAGE).setCancelable(false)
				.setNegativeButton(SBStore.RESUME_BUTTON,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								isPopupOn = false;
								BallGenerator.setAliveValue(true);
							}
						}).setPositiveButton(SBStore.QUIT_BUTTON,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								android.os.Process
										.killProcess(android.os.Process.myPid());

							}
						});
		alert = builder.create();
		alert.show();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		android.os.Process.killProcess(android.os.Process.myPid());

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (!hasWindowFocus() && !isPopupOn)
			android.os.Process.killProcess(android.os.Process.myPid());

	}

}
