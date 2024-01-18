import java.util.Scanner;

public class BoxEnum{
	private static BoxState _state = BoxState.LOCKED;
	
	public static void main(String[] args){
		
	}
	
	public static void open(){
		if(_state == BoxState.LOCKED){
			System.out.print("the box is locked.");
		}
		else if(_state == BoxState.OPEN){
			System.out.print("The box is already open.");
		}
		else{
			_state = BoxState.OPEN;
			System.out.print("The box is open");
		}
	}
	
	public static void close(){
		if(_state != BoxState.OPEN){
			System.out.print("The box is already closed.");
		}
		else{
			_state = BoxState.CLOSED;
			System.out.print("The box is closed");
		}
	}
	
	public static void unlock(){
		if(_state != BoxState.LOCKED){
			System.out.print("The box is already unlocked.");
		}
		else{
			_state = BoxState.CLOSED;
			System.out.print("The box is unlocked");
		}
	}
	
	public static void lock(){
		if(_state == BoxState.LOCKED){
			System.out.print("the box is already locked.");
		}
		else if(_state == BoxState.OPEN){
			System.out.print("The box is not closed.");
		}
		else{
			_state = BoxState.LOCKED;
			System.out.print("The box is locked");
		}
	}
	
	public static void showCommands(){
		
	}
}