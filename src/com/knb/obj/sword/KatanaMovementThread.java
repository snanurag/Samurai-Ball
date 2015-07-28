package com.knb.obj.sword;

import java.util.List;

import min3d.core.Object3d;
import android.util.Log;

import com.knb.FrontPage;
import com.knb.constants.SBStore;
import com.knb.data.ContextData;
import com.knb.generators.BallGenerator;
import com.knb.generators.CutBallGenerator;
import com.knb.handlers.FrontPageTouchEventHandler;
import com.knb.sounds.SoundManager;

public class KatanaMovementThread extends Thread {

	private float xTouch;
	private float yTouch;

	public void setTouchCoord(float xTouch, float yTouch) {
		this.xTouch = xTouch;
		this.yTouch = yTouch;
	}

	@Override
	public void run() {

		Object3d katana = null;

		if (ContextData.getValue(SBStore.ACTIVITY) != null
				&& (ContextData.getValue(SBStore.ACTIVITY).equals(
						SBStore.MAIN_ACTIVITY) || ContextData.getValue(
						SBStore.ACTIVITY).equals(SBStore.GAME_OVER_ACTIVITY))) {

			katana = pickKatanaOnTouch(KatanaCollection.getKatanaCollection());

		} else if (ContextData.getValue(SBStore.ACTIVITY) != null
				&& ContextData.getValue(SBStore.ACTIVITY).equals(
						SBStore.FIRST_PAGE_ACTIVITY)) {
			katana = pickKatanaOnTouchFrontPage(FrontPage.getKatanas());

			if (katana != null) {
				FrontPageTouchEventHandler.setKatanaTouched(true);
			}
		}

		if (katana != null) {
			katana.rotation().x = -90;

			while (katana.rotation().x > -270) {

				katana.rotation().x--;

			}

			SoundManager.playKatanaMovement();

			if (ContextData.getValue(SBStore.ACTIVITY) != null
					&& (ContextData.getValue(SBStore.ACTIVITY).equals(
							SBStore.MAIN_ACTIVITY) || ContextData.getValue(
							SBStore.ACTIVITY)
							.equals(SBStore.GAME_OVER_ACTIVITY))) {

				CutBallGenerator.replaceBallByCutBalls(katana.position().x,
						BallGenerator.list());
			} else if (ContextData.getValue(SBStore.ACTIVITY) != null
					&& ContextData.getValue(SBStore.ACTIVITY).equals(
							SBStore.FIRST_PAGE_ACTIVITY)) {
				CutBallGenerator.replaceBallByCutBalls(katana.position().x,
						FrontPage.getBalls());
			}

			try {
				while (katana.rotation().x > -450) {
					Thread.sleep(15);
					katana.rotation().x -= 2;
				}
			} catch (InterruptedException e) {
				Log.e("SamuraiBall", "Katana movement thread is interrupted");
			}

			katana.rotation().x = -450;

		}

	}

	private Object3d pickKatanaOnTouch(List<Object3d> list) {
		float screenXLen = 2 * SBStore.NORMALIZATION_X;

		float swordDist = screenXLen / 7f;

		if (list == null || list.size() != 7) {
			return null;
		}
		if (yTouch < (-1f * SBStore.NORMALIZATION_Y + SBStore.LENGTH_HANDLE)) {

			if (xTouch < -1f * SBStore.NORMALIZATION_X + swordDist) {

				return list.get(0);
			} else if (xTouch < -1f * SBStore.NORMALIZATION_X + 2 * swordDist) {
				return list.get(1);

			} else if (xTouch < -1f * SBStore.NORMALIZATION_X + 3 * swordDist) {
				return list.get(2);

			} else if (xTouch < -1f * SBStore.NORMALIZATION_X + 4 * swordDist) {
				return list.get(3);

			} else if (xTouch < -1f * SBStore.NORMALIZATION_X + 5 * swordDist) {
				return list.get(4);

			} else if (xTouch < -1f * SBStore.NORMALIZATION_X + 6 * swordDist) {
				return list.get(5);

			} else {
				return list.get(6);

			}
		}
		return null;
	}

	private Object3d pickKatanaOnTouchFrontPage(List<Object3d> list) {
		float screenXLen = 2 * SBStore.NORMALIZATION_X;

		float swordDist = screenXLen / 7f;

		if (list != null && list.size() == 2) {
			if (yTouch < (-1f * SBStore.NORMALIZATION_Y + SBStore.LENGTH_HANDLE)) {

				if (xTouch > -1f * SBStore.NORMALIZATION_X + swordDist
						&& xTouch < -1f * SBStore.NORMALIZATION_X + 2
								* swordDist) {
					return list.get(0);

				} else if (xTouch > -1f * SBStore.NORMALIZATION_X + 5
						* swordDist
						&& xTouch < -1f * SBStore.NORMALIZATION_X + 6
								* swordDist) {
					return list.get(1);

				}
			}
		}
		return null;
	}
}
