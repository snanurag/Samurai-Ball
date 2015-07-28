package com.knb.obj.sword;

import java.util.ArrayList;
import java.util.List;

import min3d.core.Object3d;
import min3d.core.Scene;

import com.knb.constants.SBStore;

public class KatanaCollection {

	private static List<Object3d> list = new ArrayList<Object3d>();

	public static void buildCollection(Scene scene) {
		
		float screenXLen = 2 * SBStore.NORMALIZATION_X;

		float swordDist = screenXLen / 7f;

		synchronized (list) {
			for (int i = 1; i < 8; i++) {
				
				Object3d katana = Katana.getKatana(scene);
				if(i ==1){
					katana.position().x = -1f * SBStore.NORMALIZATION_X + (swordDist * i)/2;
				}
				else{
					katana.position().x = -1f * SBStore.NORMALIZATION_X + swordDist * (i-1)+(swordDist/2);
				}
				list.add(katana);
			}
		}
	}


	public static List<Object3d> getKatanaCollection() {
		return list;
	}
	
	public static void clearList(){
		if(list!=null){
			synchronized (list) {
				list.clear();
			}
		}
	}

}
