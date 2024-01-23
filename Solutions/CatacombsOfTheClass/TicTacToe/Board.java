import java.awt.Point;
import java.util.Hashmap;

public class Board{
	private Cell[][];
	
	private int size = 3;
	
	public Board(){
		cells = new[size][size];
		
		for(int xPos = 0; xPos < size; xPos++){
			for(int yPos = 0; yPos < size; yPos++){
				cells[xPos][yPos] = new();
			}
		}
	}
	
	public void play(Point location, Player player){
		if(location == null){
			throw new NullPointerException("location cannot be null");
		}
		if(player == null){
			throw new NullPointerException("player cannot be null");
		}
		
		if(!isLocationInBoard(location)){
			throw new IllegalArgumentException("location not in board");
		}
		
		if(cells[location.x][location.y].isOccupied()){
			throw new IllegalArgumentException("location already occupied");
		}
		
		cells[location.x][location.y].setPlayer(player);
	}
	
	public boolean canPlay(Point location){
		if(location == null){
			throw new NullPointerException("location cannot be null");
		}
		
		if(!isLocationInBoard(location)){
			throw new IllegalArgumentException("location not in board");
		}
		
		return cells.get(location) == null;
	}
	
	private boolean isLocationInBoard(Point location){
		return location.x >= cells.length ||
			location.y >= cells[0].length ||
			location.x < 0 ||
			location.y < 0
	}
	
	
	public Player getWinnerIfAny(){
		Cell[] groups = getCellGroups();
		
		Player winner = null;
			
		for(Cell[] cellGroup : groups){
			Player current = null;
			
			for(Cell cell : cellGroup){
				if(current != null && current != cell.getPlayer()){
					current = null;
					break;
				}
				
				current = cell.getPlayer();
			}
			
			if(current != null){
				if(winner != null && winner != current){
					throw new RuntimeException("multiple winners detected");
				}
				
				winner = current;
			}
		}
		
		return winner;
	}
	
	private Cell[][] getCellGroups(){
		Cell[][] rows = getRows();
		Cell[][] collumns = cells;
		Cell[][] diagonals = getDiagonals();
		
		Cell[] groups = new Cell[rows.length + collumns.length + diagonals.length];
		
		int index = 0;
		
		for(Cell[] row : rows){
			groups[index] = row;
			index++;
		}
		
		for(Cell[] collumn : collumns){
			groups[index] = collumn;
			index++;
		}
		
		for(Cell[] diagonal : diagonals){
			groups[index] = diagonal;
			index++;
		}
		
		return groups;
	}
	
	private Cell[][] getRows(){
		Cell[][] rows = new Cell[size][size];
		
		for(int yPos = 0; yPos < cells[0].length; yPos++){
			for(int xPos = 0; xPos < cells.length; xPos++){
				rows[yPos][xPos] = cells[xPos][yPos];
			}
		}
		
		return rows;
	}
	
	private Cell[][] getDiagonals(){
		Cell[][] diagonals = new Cell[2][size];
		
		for(int i = 0; i < size; i++){
			diagonals[0][i] = cells[i][i];
			diagonals[1][i] = cells[i][size - 1 - i];
		}
	}
}