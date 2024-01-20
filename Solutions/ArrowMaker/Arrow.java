public class Arrow{
	private ArrowHead _arrowHead;
	private int _shaftLength;
	private Fletching _fletching;
	
	public Arrow(ArrowHead arrowHead, int shaftLengthInCentimeters, Fletching fletching){
		_arrowHead = arrowHead;
		_shaftLength = shaftLengthInCentimeters;
		_fletching = fletching;
	}
	
	public ArrowHead getArrowHead(){
		return _arrowHead;
	}
	
	public int getShaftLength(){
		return _shaftLength;
	}
	
	public Fletching getFletching(){
		return _fletching;
	}
	
	public static Arrow createDefaultArrow(DefaultArrow defaultArrowType){
		return new Arrow(defaultArrowType.getArrowHead(), defaultArrowType.getShaftLength(), defaultArrowType.getFletching());
	}
}