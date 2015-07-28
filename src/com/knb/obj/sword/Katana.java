package com.knb.obj.sword;

import min3d.Shared;
import min3d.core.Object3d;
import min3d.core.Scene;
import min3d.parser.IParser;
import min3d.parser.Parser;
import android.content.Context;

import com.knb.MainActivity;
import com.knb.ball.AbstractObject;
import com.knb.constants.SBStore;

public class Katana{
	
	private static volatile Object3d katana = null;
	
	public static Object3d getKatana(Scene scene){
		if(katana == null){
			buildKatana();
		}
		Object3d k = katana.clone();

		synchronized(scene){
			scene.addChild(k);
		}
		
		return k;
	}

	public static void buildKatana(){
		if(katana == null){
			IParser parser = Parser.createParser(Parser.Type.OBJ, Shared.context()
					.getResources(), "com.knb:raw/katana_sword_obj", true);
			parser.parse();

			katana = parser.getParsedObject();

			katana.scale().x = katana.scale().y = katana.scale().z = SBStore.KATANA_SCALE;
			
			setDefaultState(katana);
		}

	}
	
	private static void setDefaultState(Object3d katana){

		katana.rotation().x = -90;
		katana.rotation().z = 90;
		
		katana.position().y = -1f*SBStore.NORMALIZATION_Y_Katana;
		katana.position().z = SBStore.RADIUS;
		
	}

}
