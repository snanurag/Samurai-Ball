package com.knb.ball.dynamics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import min3d.core.Object3d;
import min3d.core.Scene;

import android.util.Log;

import com.knb.ball.AbstractObject;
import com.knb.constants.SBStore;
import com.knb.generators.InitBallStateGenerator;

public class DropAllObjects extends Thread {

	public DropAllObjects(Scene scene) {
		this.scene = scene;
	}

	private Scene scene;

	private List<Object3d> list = new ArrayList<Object3d>();

	private List<DroptObject> listDropObj = new ArrayList<DroptObject>();

	private boolean areAllContentsDropped = false;

	public void drop() {
		Iterator<DroptObject> itr = listDropObj.iterator();
		while (itr.hasNext()) {
			DroptObject dropObj = itr.next();
	
			long timeElapsed = System.currentTimeMillis()
			- dropObj.getObjState().getLastUpdateTime();
			if (timeElapsed > 30) {
				timeElapsed = 30;
			}
			dropObj.getObjState().setNewUpdateTime(System.currentTimeMillis());
			dropObj.updateState(timeElapsed);

			// Clearing up removed objects also
			if (dropObj.isRemoved()) {
				itr.remove();
				scene.removeChild(dropObj.getObj3D());
			}

		}
		if (listDropObj.size() == 0) {
			list.clear();
			areAllContentsDropped = true;

		}
	}

	public boolean areAllContentsDropped() {
		return areAllContentsDropped;
	}

	public void addObject3d(Object3d o) {
		list.add(o);
	}

	public void prepareDropObjList() {
		for (Object3d o : list) {
			DroptObject dropObj = new DroptObject(o, o.scale().x);

			double random = Math.random();
			if (random > 0.5) {
				dropObj.getObjState().getSpeed().setAccY(
						(float) random * InitBallStateGenerator.initAccY);
			} else {
				dropObj.getObjState().getSpeed().setAccY(
						(float) (1 - random) * InitBallStateGenerator.initAccY);
			}
			
			dropObj.getObjState().getSpeed().setXv(0);

			listDropObj.add(dropObj);
		}
	}

	private class DroptObject extends AbstractObject {

		public DroptObject(Object3d o, float scale) {
			super(o, scale);
		}

		@Override
		public void updateState(long timeElapsed) {

			if (objState.isBallStationary())
				return;

			objState.getSpeed().updateSpeed(timeElapsed);

			float x = obj.position().x + objState.getSpeed().getXv()
					* timeElapsed;
			float y = obj.position().y + objState.getSpeed().getYv()
					* timeElapsed;

			objState.setPosition(x, y, 0);

			if (y <= -1.1f * SBStore.NORMALIZATION_Y) {

				// Removing the obj from the canvas when it hits the
				// bottom.
				markRemoved();
			}
		}
	}

}
