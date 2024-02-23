package game;

public enum ShootResult{
	NO_ARROWS("You cannot shoot, your quiver is empty"),
	NO_ROOM("Your arrow shot into a wall"),
	MISSED("Your arrow didn't hit anything"),
	HIT("You arrow hit its target!");
	
	private String message;
	
	private ShootResult(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}