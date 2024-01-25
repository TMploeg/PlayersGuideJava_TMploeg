public class Card{
	private CardColor color;
	private CardRank rank;
	
	public Card(CardColor color, CardRank rank){
		this.color = color;
		this.rank = rank;
	}
	
	public CardColor getColor(){
		return color;
	}
	
	public CardRank getRank(){
		return rank;
	}
	
	public boolean isSpecialRank(){
		return rank.getValue() > CardRank.TEN.getValue();
	}
	
	@Override
	public String toString(){
		return "The " + toPascalCase(color.toString()) + " " + toPascalCase(rank.toString());
	}
	
	private String toPascalCase(String word){
		return word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();
	}
}