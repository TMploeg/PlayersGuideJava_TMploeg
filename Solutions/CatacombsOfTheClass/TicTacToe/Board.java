import java.awt.Point;
import java.util.HashMap;

public class Board{
	private Cell[][] cells;
	
	private int size = 3;
	
	public Board(){
		cells = new Cell[size][size];
		
		for(int xPos = 0; xPos < size; xPos++){
			for(int yPos = 0; yPos < size; yPos++){
				cells[xPos][yPos] = new Cell();
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
		
		getCell(location).setPlayer(player);
	}
	
	public boolean canPlay(Point location){
		if(location == null){
			throw new NullPointerException("location cannot be null");
		}
		
		return isLocationInBoard(location) && getCell(location).getPlayer() == null;
	}
	
	private boolean isLocationInBoard(Point location){
		return !(location.x >= cells.length ||
			location.y >= cells[0].length ||
			location.x < 0 ||
			location.y < 0);
	}
	
	public Cell getCell(Point location){
		return cells[location.x][location.y];
	}
	
	public Cell getCell(int xPos, int yPos){
		return cells[xPos][yPos];
	}
	
	public Player getWinnerIfAny(){
		Player winner = null;
		
		for(Cell[] winnerGroup : getWinnerCellGroups()){
			Player current = winnerGroup[0].getPlayer();
			
			if(winner == null){
				winner = current;
			}
			else if(current != winner){
				throw new RuntimeException("multiple winners detected");
			}
		}
		
		return winner;
		
		/*Cell[][] groups = getCellGroups();
		
		Player winner = null;
		
		for(Cell[] cellGroup : groups){
			Player current = null;
			
			for(Cell cell : cellGroup){
				Player cellPlayer = cell.getPlayer();
				
				if(cellPlayer == null || (current != null && current != cellPlayer)){
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
		*/
	}
	
	public boolean isDraw(){
		for(Cell[] arr : cells){
			for(Cell cell : arr){
				if(cell.getPlayer() == null){
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Cell[][] getWinnerCellGroups(){
		Cell[][] winnerRows = findWinnerGroupsFromGroupCollection(getRows());
		Cell[][] winnerCollumns = findWinnerGroupsFromGroupCollection(cells);
		Cell[][] winnerDiagonals = findWinnerGroupsFromGroupCollection(getDiagonals());
		
		Cell[][] totalWinnerGroups = new Cell[winnerRows.length + winnerCollumns.length + winnerDiagonals.length][size];
		
		int index = 0;
		
		for(Cell[] row : winnerRows){
			totalWinnerGroups[index] = row;
			index++;
		}
		
		for(Cell[] collumn : winnerCollumns){
			totalWinnerGroups[index] = collumn;
			index++;
		}
		
		for(Cell[] diagonal : winnerDiagonals){
			totalWinnerGroups[index] = diagonal;
			index++;
		}
		
		return totalWinnerGroups;
	}
	
	private Cell[][] findWinnerGroupsFromGroupCollection(Cell[][] groupCollection){
		int[] winnerIndexes = new int[groupCollection.length];
		
		int indexesIndex = 0;
		
		for(int groupIndex = 0; groupIndex < groupCollection.length; groupIndex++){
			if(isWinnerGroup(groupCollection[groupIndex])){
				winnerIndexes[indexesIndex] = groupIndex;
				indexesIndex++;
			}
		}
		
		Cell[][] winnerGroups = new Cell[indexesIndex][size];
		
		for(int i = 0; i < indexesIndex; i++){
			winnerGroups[i] = groupCollection[winnerIndexes[i]];
		}
		
		return winnerGroups;
	}
	
	private boolean isWinnerGroup(Cell[] cellGroup){
		Player current = null;
		
		for(Cell cell : cellGroup){
			Player cellPlayer = cell.getPlayer();
			
			if(cellPlayer == null || (current != null && current != cellPlayer)){
				return false;
			}
			
			current = cell.getPlayer();
		}
		
		return true;
	}
	
	private Cell[][] getRows(){
		Cell[][] rows = new Cell[size][size];
		
		for(int yPos = 0; yPos < cells[0].length; yPos++){
			for(int xPos = 0; xPos < cells.length; xPos++){
				rows[yPos][xPos] = getCell(xPos, yPos);
			}
		}
		
		return rows;
	}
	
	private Cell[][] getDiagonals(){
		Cell[][] diagonals = new Cell[2][size];
		
		for(int i = 0; i < size; i++){
			diagonals[0][i] = getCell(i, i);
			diagonals[1][i] = getCell(i, size - 1 - i);
		}
		
		return diagonals;
	}

	public int getSize(){
		return size;
	}
}