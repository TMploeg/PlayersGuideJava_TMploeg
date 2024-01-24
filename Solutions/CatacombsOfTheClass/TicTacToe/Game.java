import java.util.Scanner;
import java.awt.Point;

public class Game{
	private Player player1;
	private Player player2;
	
	private int nrOfRounds;
	private int roundsPlayed;
	
	public Game(int nrOfRounds){
		this.nrOfRounds = nrOfRounds;
		this.roundsPlayed = 0;
		
		initPlayers();
		runRounds();
	}
	
	private void runRounds(){
		Scanner scanner = new Scanner(System.in);
		while(true){
			playRound();
			
			if(isDraw()){
				System.out.println("The game is a draw.");
				break;
			}
			else{	
				Player finalWinner = findFinalWinnerIfAny();
			
				if(finalWinner != null){
					System.out.println(finalWinner.getName() + " has won!");
					break;
				}
			}
			
			System.out.print("Press ENTER to start next round.");
			scanner.nextLine();
		}
	}
	
	private void playRound(){
		Board board = new Board();
		Player currentPlayer = player1;
		int roundNr = roundsPlayed + 1;
		
		System.out.println("\nRound " + roundNr + " start!");
		
		while(true){
			System.out.println(currentPlayer.getName() + "(" + currentPlayer.getDisplayCharacter() + ")'s turn.");
			displayBoard(board);
			
			Point location = askLocation(board);
			
			board.play(location, currentPlayer);
			
			currentPlayer = currentPlayer == player1 ? player2 : player1;
			
			Player winner = board.getWinnerIfAny();
			
			if(winner != null || board.isDraw()){
				System.out.print("Round " + roundNr + " ");
				
				if(winner != null){
					System.out.println("goes to " + winner.getName());
					winner.incrementWins();
				}
				else{
					System.out.println("is a draw");
				}
				
				displayBoard(board);
				
				roundsPlayed++;
				
				break;
			}
		}
	}
	
	private boolean isDraw(){
		return (nrOfRounds - roundsPlayed) == 0 && player1.getNrOfWins() == player2.getNrOfWins();
	}
	
	private Player findFinalWinnerIfAny(){
		int winDiff = player1.getNrOfWins() - player2.getNrOfWins();
		int remainingRounds = nrOfRounds - roundsPlayed;
			
		if(winDiff > 0){
			if(remainingRounds < winDiff){
				return player1;
			}
		}
		
		if(winDiff < 0){
			if(remainingRounds < (winDiff * -1)){
				return player2;
			}
		}
		
		return null;
	}
	
	private Point askLocation(Board board){
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.println("Please enter a location");
			
			String locationInput = scanner.nextLine();
			
			if(isValidLocationInput(locationInput)){
				char yPosInput = locationInput.charAt(0);
				
				int xPos = locationInput.charAt(1) - '1';
				int yPos = (board.getSize() - 1) - (yPosInput - (Character.isUpperCase(yPosInput) ? 'A' : 'a'));
				
				Point location = new Point(xPos, yPos);
				
				if(board.canPlay(location)){
					return location;
				}
			}
			
			System.out.println("invalid input");
		}
	}
	
	private boolean isValidLocationInput(String locationInput){
		return (
			locationInput != null &&
			locationInput.length() == 2 &&
			Character.isLetter(locationInput.charAt(0)) &&
			Character.isDigit(locationInput.charAt(1))
		);
	}
	
	private void initPlayers(){
		System.out.println("Player 1, please enter your name:");
		String player1Name = askPlayerName();
		player1 = new Player(player1Name, 'X');
		
		System.out.println("Player 2, please enter your name:");
		String player2Name = askPlayerName();
		player2 = new Player(player2Name, 'O');
	}
	
	private String askPlayerName(){
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			String nameInput = scanner.nextLine();
			
			if(nameInput == null || nameInput.length() == 0){
				System.out.println("name must have at least one character");
			}
			else if(!isUniqueName(nameInput)){
				System.out.println("name already taken");
			}
			else{
				return nameInput;
			}
			
		}
	}
	
	private boolean isUniqueName(String name){
		return (player1 == null || !player1.getName().equals(name)) && (player2 == null || !player2.getName().equals(name));
	}

	private void displayBoard(Board board){
		Cell[][] winnerGroups = board.getWinnerCellGroups();
		
		for(int yPos = 0; yPos < board.getSize(); yPos++){
			
			if(yPos > 0){
				System.out.print("  ");
				
				int nrOfDashes = (board.getSize() * 3) + (board.getSize() - 1);
				for(int i = 0; i < nrOfDashes; i++){
					System.out.print("-");
				}
				
				System.out.println();
			}
			
			char yPosDisplayCharacter = (char)((board.getSize() - 1 - yPos) + 'a');
			
			String line = yPosDisplayCharacter + " ";
			
			for(int xPos = 0; xPos < board.getSize(); xPos++){
				boolean isWinnerCell = checkIfCellIsInWinnerGroup(xPos, yPos, board, winnerGroups);
				
				if(xPos > 0){
					line += "|";
				}
				
				Player occupyingPlayer = board.getCell(xPos, yPos).getPlayer();
				
				String leftCharacter = isWinnerCell ? ">" : " ";
				String rightCharacter = isWinnerCell ? "<" : " ";
				String cellContentCharacter = occupyingPlayer != null ? Character.toString(occupyingPlayer.getDisplayCharacter()) : " ";
				
				line += leftCharacter + cellContentCharacter + rightCharacter;
			}
			
			System.out.println(line);
		}
		
		for(int i = 0; i < board.getSize(); i++){
			System.out.print("   " + (i + 1));
		}
		
		System.out.println();
	}
	
	private boolean checkIfCellIsInWinnerGroup(int xPos, int yPos, Board board, Cell[][] winnerGroups){
		for(Cell[] group : winnerGroups){
			for(Cell cell : group){
				if(board.getCell(xPos, yPos) == cell){
					return true;
				}
			}
		}
		
		return false;
	}
}