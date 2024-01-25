public class Door{
	private DoorState state;
	private String passcode;
	
	public Door(String passcode){
		this.state = DoorState.LOCKED;
		
		changePasscode(null, passcode);
	}
	
	public void changePasscode(String oldPasscode, String newPasscode){
		if(passcode != null && !passcode.equals(oldPasscode)){
			System.out.println("Passcode is incorrect, cannot set new passcode");
		}
		else if(newPasscode == null || newPasscode.length() == 0){
				throw new RuntimeException("Passcode must have at least one character");
		}
		else{	
			this.passcode = newPasscode;
			System.out.println("The passcode was changed");
		}
	}
	
	public void open(){
		if(state == DoorState.LOCKED){
			System.out.println("You cannot open the door, it is locked.");
		}
		else if(state == DoorState.OPEN){
			System.out.println("You cannot open the door, it is already open.");
		}
		else{
			state = DoorState.OPEN;
			System.out.println("The door opened.");
		}
	}
	
	public void close(){
		if(state != DoorState.OPEN){
			System.out.println("You cannot close the door, it is already closed.");
		}
		else{
			state = DoorState.CLOSED;
			System.out.println("The door closed.");
		}
	}
	
	public void unlock(String passcode){
		if(!this.passcode.equals(passcode)){
			System.out.println("Passcode is incorrect");
		}
		else if(state != DoorState.LOCKED){		
			System.out.println("You cannot unlock the door, it is already unlocked.");
		}
		else{
			state = DoorState.CLOSED;
			System.out.println("The door was unlocked.");
		}
	}
	
	public void lock(){
		if(state == DoorState.LOCKED){
			System.out.println("You cannot lock the door, it is already locked.");
		}
		else if(state == DoorState.OPEN){
			System.out.println("You cannot lock the door, it is open.");
		}
		else{
			state = DoorState.LOCKED;
			System.out.println("The door was locked");
		}
	}
}