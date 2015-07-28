package min3d.handlers;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class CustomHandler extends Handler {
	
	// This value if View.GONE is 8.
	public static final int REMOVE_VIEW = View.GONE;
	
	// This value if View.GONE is 0.
	public static final int ADD_VIEW = View.VISIBLE;

	public final static int EDIT_VIEW = 1;
	
	public final static int CHANGE_SIZE =2;
	
	public final static int CHANGE_POSITION = 3;
	
	private TextView textView;
	
	public void setTextView(TextView textView){
		this.textView = textView;
	}
	
	@Override
	public void handleMessage(Message msg) {
		
		HandlerDataObject data = (HandlerDataObject)msg.obj;
		
		if(msg.what == REMOVE_VIEW){
			textView.setVisibility(View.GONE);
		}
		else if(msg.what == EDIT_VIEW){
			textView.setText(data.getString());
		}
		else if(msg.what == ADD_VIEW){
			textView.setVisibility(View.VISIBLE);
		}
		else if(msg.what == CHANGE_SIZE){
			textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, data.getSize());
		}
	}
}
