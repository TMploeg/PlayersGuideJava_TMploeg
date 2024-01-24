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
		while(true){
			Player roundWinner = playRound();
			roundsPlayed++;
			
			System.out.print("Round " + roundsPlayed + " ");
			if(roundWinner != null){
				roundWinner.incrementWins();
				
				System.out.println("goes to " + roundWinner.getName() + "!");
			}
			else{
				System.out.println("is a draw");
			}
			
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
		}
	}
	
	private Player playRound(){
		Board board = new Board();
		Player currentPlayer = player1;
		
		while(true){
			System.out.println(currentPlayer.getName() + "'s turn.");
			displayBoard(board);
			
			Point location = askLocation(board);
			board.play(location, currentPlayer);
			
			currentPlayer = currentPlayer == player1 ? player2 : player1;
			
			Player winner = board.getWinnerIfAny();
			
			if(winner != null){
				return winner;
			}
			
			if(board.isDraw()){
				return null;
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
				int yPos = yPosInput - (Character.isUpperCase(yPosInput) ? 'A' : 'a');
				
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
			System.out.print(yPosDisplayCharacter + " ");
			
			for(int xPos = 0; xPos < board.getSize(); xPos++){
				System.out.print(" ");
				if(xPos > 0){
					System.out.print("| ");
				}
				
				Player occupyingPlayer = board.getCell(xPos, yPos).getPlayer();
				System.out.print(occupyingPlayer != null ? occupyingPlayer.getDisplayCharacter() : " ");
			}
			
			System.out.println();
		}
		
		for(int i = 0; i < board.getSize(); i++){
			System.out.print("   " + (i + 1));
		}
		
		System.out.println();
	}
}