package com.knb.data;

import com.knb.constants.SBStore;
import com.knb.generators.BallGenerator;
import com.knb.generators.CutBallGenerator;
import com.knb.generators.InitBallStateGenerator;
import com.knb.images.Background;
import com.knb.obj.sword.Katana;
import com.knb.sounds.SoundManager;

import min3d.core.Scene;
import android.app.Activity;

public class ObjectLoader extends Thread {

	private Activity activity;
	private Scene scene;

	public ObjectLoader(Scene scene, Activity activity) {
		this.scene = scene;
		this.activity = activity;
	}

	@Override
	public void run() {
		BallGenerator.loadBallObject();
		CutBallGenerator.loadBallObject();
		Katana.buildKatana();
		SoundManager.loadAllSounds(activity);
		Background.loadBackGroundImage(scene);
		SoundManager.loadAllMediaFiles(activity);

		ContextData.loadAllObjs();
	}

}
