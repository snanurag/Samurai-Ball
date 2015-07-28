package com.knb.ball;

import min3d.core.Object3d;

import com.knb.constants.SBStore;

public abstract class AbstractObject {

	protected Object3d obj;

	protected ObjState objState;
	
	protected boolean removed;

	public AbstractObject(Object3d obj, float scale) {
		this.obj = obj;
		obj.scale().x = obj.scale().y = obj.scale().z = scale;
		objState = new ObjState();
	}

	public abstract void updateState(long timeElapsed);
	
	public boolean isRemoved() {
		return removed;
	}

	public void markRemoved() {
		removed = true;
	}
	
	public Object3d getObj3D() {
		return obj;
	}

	public ObjState getObjState() {
		return objState;
	}

	public class ObjState {

		private Speed objSpeed = new Speed();

		private Rotation ballRotation = new Rotation();
		
		private long updateTime;

		private boolean colloid;

		public Speed getSpeed() {
			return objSpeed;
		}

		public void setRotation(Rotation ballRotation) {
			this.ballRotation = ballRotation;
		}
		
		public Rotation getRotation() {
			return ballRotation;
		}

		public void setNewUpdateTime(long updateTime) {
			this.updateTime = updateTime;
		}

		public long getLastUpdateTime() {
			return updateTime;
		}

		public void setColloid() {
			colloid = true;
		}

		public boolean isColloid() {
			return colloid;
		}

		public void clearColloid() {
			colloid = false;
		}

		public void setPosition(float x, float y, float z) {

			obj.position().x = x;
			obj.position().y = y;
			obj.position().z = z;

		}

		public boolean isBallStationary() {
			if (objSpeed.isStopped()
					&& obj.position().y == -1f * SBStore.NORMALIZATION_Y)
				return true;
			else
				return false;
		}
	}

}
