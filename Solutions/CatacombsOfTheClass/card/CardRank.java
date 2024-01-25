package card;

public enum CardRank{
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7),
	EIGHT(8),
	NINE(9),
	TEN(10),
	DOLLAR(11),
	PERCENT(12),
	CARET(13),
	AMPERSAND(14);
	
	private int value;
	
	private CardRank(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
}