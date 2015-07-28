package com.knb.images;

import com.knb.constants.SBStore;

import min3d.Shared;
import min3d.core.Object3d;
import min3d.core.Scene;
import min3d.parser.IParser;
import min3d.parser.Parser;

public class Background {

	private static Object3d background;

	public static void loadBackGroundImage(Scene scene) {
		if (background == null) {
			IParser parser = Parser.createParser(Parser.Type.OBJ, Shared
					.context().getResources(), "com.knb:raw/background_obj",
					true);
			parser.parse();
			background = parser.getParsedObject();
			background.scale().x = background.scale().y = background.scale().z = SBStore.BACKGROUND_SCALE;
			background.position().z = -1.99f;
		}

		if (scene != null) {

			scene.addChild(background);

		}
	}
}
