package com.knb.ball.dynamics;

import java.util.List;

import com.knb.ball.AbstractObject;
import com.knb.ball.Ball;

public class Dynamics {

	public static void makeBallMovements(List<? extends AbstractObject> list){

		synchronized (list) {
			for (int i=0; i< list.size(); i++) {
				
				AbstractObject ball = list.get(i);
				
				long timeElapsed = System.currentTimeMillis()
						- ball.getObjState().getLastUpdateTime();
				
				if (timeElapsed > 30) {
					timeElapsed = 30;
				}

				ball.updateState(timeElapsed);

				ball.getObjState()
						.setNewUpdateTime(System.currentTimeMillis());
				
				RotationDynamics.rotate(ball);
			}
		}
		
		
	}
	
	public static void makeBallMovementWithCollision(List<Ball> list){

		makeBallMovements(list);
		
		List<Ball> collisionList = CollisionDynamics.getCollisionList();
		
		collisionList.addAll(list);
		
		CollisionDynamics.setBallsAt2R();

		CollisionDynamics.performCollision();

		// At the end of updateScene only all the collisions are cleared.
		CollisionDynamics.clearCollisions();
		
		collisionList.clear();

	}
}
