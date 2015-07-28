package min3d.core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.knb.constants.SBStore;
import com.knb.handlers.TouchEventHandler;

public class CustomGLSurfaceView extends GLSurfaceView {

	
	TouchEventHandler touchEventHandler = new TouchEventHandler();

	public CustomGLSurfaceView(Context context, AttributeSet attribSet){
		super(context, attribSet);
	}

	public CustomGLSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float xTouch = getXTouchCoordinate(event.getX());
		float yTouch = getYTouchCoordinate(event.getY());

		touchEventHandler.onTocuhEvent(xTouch, yTouch);
		
		Log.d("CustomGLSurfaceView", "x is " + event.getX() + " and y is "
				+ event.getY());
		return super.onTouchEvent(event);
	}

	/**
	 * This returns the xTouchCoord respective to the frustum view in currently
	 * working plane.
	 * 
	 * @param xTouchCoord
	 * @return
	 */
	private float getXTouchCoordinate(float xTouchCoord) {

		float x = (xTouchCoord * 2 * SBStore.NORMALIZATION_X / getWidth())
				- SBStore.NORMALIZATION_X;
		return x;

	}

	/**
	 * This returns the yTouchCoord respective to the frustum view in currently
	 * working plane.
	 * 
	 * @param yTouchCoord
	 * @return
	 */
	private float getYTouchCoordinate(float yTouchCoord) {

		float y = -1f
				* (yTouchCoord * 2 * SBStore.NORMALIZATION_Y / getHeight())
				+ SBStore.NORMALIZATION_Y;
		return y;

	}
}
