package com.knb;

import min3d.Shared;
import min3d.core.Object3d;
import min3d.core.Scene;
import min3d.parser.IParser;
import min3d.parser.Parser;
import android.util.Log;

import com.knb.constants.SBStore;
import com.knb.data.ContextData;

public class LoadingPage {

	private Scene scene;

	public LoadingPage(Scene scene) {
		this.scene = scene;
	}

	public void initScene() {
		init();

		Log.d("SamuraiBall", "Loading is initialized");

	}

	public void updateScene() {
		try {
		} catch (RuntimeException e) {
			Log.e("SamuraiBall",
					"RuntimeException in MainActivity.updateScene() ", e);
		}

	}

	protected void onStop() {
		// TODO Auto-generated method stub

	}

	protected void onPause() {
		// TODO Auto-generated method stub
		onStop();
	}

	private void init() {

		IParser parser = Parser.createParser(Parser.Type.OBJ, Shared.context()
				.getResources(), "com.knb:raw/loadingpage_obj", true);
		parser.parse();

		Object3d background = parser.getParsedObject();
		background.scale().x = background.scale().y = background.scale().z = SBStore.LOADING_SCALE;
		background.position().z = -1.99f;

		scene.addChild(background);

		ContextData.setValue(SBStore.ACTIVITY, SBStore.LOADING_ACTIVITY);

	}

}
