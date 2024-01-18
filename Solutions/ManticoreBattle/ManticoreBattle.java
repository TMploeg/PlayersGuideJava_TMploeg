import java.util.Scanner;

public class ManticoreBattle{
	private static final int START_CITY_HP = 15;
	private static final int START_MANTICORE_HP = 10;
	private static final int MIN_MANTICORE_DISTANCE = 0;
	private static final int MAX_MANTICORE_DISTANCE = 100;
	
	private static final int ROUNDS_PER_FIRE_ROUND = 3;
	private static final int ROUNDS_PER_LIGHTNING_ROUND = 5;
	
	private static int _cityHP;
	private static int _manticoreHP;
	private static int _manticoreDistance;
	private static int _roundNr;
	
	public static void main(String[] args){
		SetupGame();
		
		boolean finished;
		do{
			playRound();
			finished = checkFinished();
		}
		while(!finished);
	}
	
	private static void SetupGame(){
		System.out.println("Game Start!");
		
		_cityHP = START_CITY_HP;
		_manticoreHP = START_MANTICORE_HP;
		_manticoreDistance = TerminalHelper.askForNumberInRange(
			"Player 1, how far away from the city do you want to station the Manticore?",
			MIN_MANTICORE_DISTANCE,
			MAX_MANTICORE_DISTANCE
		);
		
		TerminalHelper.clearTerminal();
		System.out.println("Player 2, it is your turn." + "\n");
		
		_roundNr = 1;
	}
	
	private static void playRound(){
		if(_roundNr > 1){
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		}
		
		displayStatus();
		
		int damage = getExpectedCannonDamage();
		shoot(damage);
		
		if(_manticoreHP > 0){
			_cityHP--;
		}
	}
	
	private static boolean checkFinished(){
		if(_cityHP <= 0){
			TerminalHelper.printlnColor("The city of Consolas has been destroyed! The Manticore has won!", TerminalColor.RED);
		}
		else if(_manticoreHP <= 0){
			TerminalHelper.printlnColor("The Manticore has been destroyed! The city of Consolas has been saved!", TerminalColor.GREEN);
		}
		else{
			_roundNr++;
			return false;
		}
		
		return true;
	}
	
	private static void displayStatus(){
		System.out.print("STATUS  ");
		System.out.print("Round: " + _roundNr + " | ");
		System.out.print("City HP: " + _cityHP + "/" + START_CITY_HP + " | ");
		System.out.print("Manticore HP: " + _manticoreHP + "/" + START_MANTICORE_HP + " | ");
	}
	
	private static int getExpectedCannonDamage(){
		if(_roundNr % (ROUNDS_PER_FIRE_ROUND * ROUNDS_PER_LIGHTNING_ROUND) == 0){
			return 10;
		}
		else if(_roundNr % ROUNDS_PER_FIRE_ROUND == 0 || _roundNr % ROUNDS_PER_LIGHTNING_ROUND == 0){
			return 3;
		}
		else {
			return 1;
		}
	}
	
	private static void shoot(int damage){
		System.out.println("The cannon is expected to deal " + damage + " damage this round.");
		
		int attackRange = TerminalHelper.askForNumber("Enter desired cannon range:");
		
		System.out.print("That round ");
		if(attackRange > _manticoreDistance){
			TerminalHelper.printColor("OVERSHOT", TerminalColor.PURPLE);
			System.out.println(" the target.");
		}
		else if(attackRange < _manticoreDistance){
			TerminalHelper.printColor("FELL SHORT", TerminalColor.PURPLE);
			System.out.println(" of the target.");
		}
		else{
			System.out.print("was a ");
			TerminalHelper.printColor("DIRECT HIT", TerminalColor.GREEN);
			System.out.println("!");
			
			_manticoreHP -= damage;
		}
	}
}