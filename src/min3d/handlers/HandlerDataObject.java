package min3d.handlers;

public class HandlerDataObject {
	
	private String newStr;
	
	private float size;
	
	private float leftPadding;
	
	private float rightPadding;
	
	public void setString(String str){
		this.newStr = str; 
	}
	
	public String getString(){
		return newStr;
	}
	
	public void setSize(float size){
		this.size = size;
	}
	
	public float getSize(){
		return size;
	}

	public float getLeftPadding() {
		return leftPadding;
	}

	public void setLeftPadding(float leftPadding) {
		this.leftPadding = leftPadding;
	}

	public float getRightPadding() {
		return rightPadding;
	}

	public void setRightPadding(float rightPadding) {
		this.rightPadding = rightPadding;
	}
	
	

}
