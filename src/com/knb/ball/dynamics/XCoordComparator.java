package com.knb.ball.dynamics;

import java.util.Comparator;

import com.knb.ball.Ball;

public class XCoordComparator implements Comparator<Ball>{
	
	public int compare(Ball b1, Ball b2) {
		if(b1.getTemporaryStateHolder().getX() < b2.getTemporaryStateHolder().getX()){
			return -1;
		}
		else if(b1.getTemporaryStateHolder().getX() == b2.getTemporaryStateHolder().getX()){
			return 0;
		}
		else{
			return 1;
		}
	};
	

}
