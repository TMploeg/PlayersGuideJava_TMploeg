package game;

import exceptions.CollectionEmptyException;

public class Quiver {
	private int nrOfArrows;
	
	public Quiver(int nrOfArrows){
		this.nrOfArrows = nrOfArrows;
	}
	
	public boolean hasArrows(){
		return nrOfArrows > 0;
	}
	
	public void takeArrow(){
		if(!hasArrows()){
			throw new CollectionEmptyException("quiver has no arrows"); 
		}
		
		nrOfArrows--;
	}
}