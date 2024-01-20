public enum DefaultArrow{
	ELITE(ArrowHead.STEEL, 95, Fletching.PLASTIC),
	BEGINNER(ArrowHead.WOOD, 75, Fletching.GOOSE_FEATHERS),
	MARKSMAN(ArrowHead.STEEL, 65, Fletching.GOOSE_FEATHERS),
	HUNTER(ArrowHead.OBSIDIAN, 70, Fletching.TURKEY_FEATHERS);
			
	private ArrowHead _arrowHead;
	private int _shaftLength;
	private Fletching _fletching;
	
	private DefaultArrow(ArrowHead arrowHead, int shaftLengthInCentimeters, Fletching fletching){
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
}