public class Player{
	private String name;
	private int nrOfWins;
	private char displayCharacter;
	
	public Player(String name, char displayCharacter){
		if(name == null || name.length() == 0){
			throw new RuntimeException("name must have at least one character");
		}
		
		this.name = name;
		this.nrOfWins = 0;
		this.displayCharacter = displayCharacter;
	}
	
	public String getName(){
		return name;
	}
	
	public void incrementWins(){
		nrOfWins++;
	}
	
	public int getNrOfWins(){
		return nrOfWins;
	}
	
	public char getDisplayCharacter(){
		return displayCharacter;
	}
}