public class Cell{
	private Player occupyingPlayer;
	
	public boolean isOccupied(){
		return occupyingPlayer != null;
	}
	
	public void setPlayer(Player player){
		occupyingPlayer = player;
	}
	
	public Player getPlayer(){
		return occupyingPlayer;
	}
}