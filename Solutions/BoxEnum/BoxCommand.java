public enum BoxCommand{
	OPEN("open","opens the box if closed and not locked"),
	CLOSE("close","closes the box if open"),
	LOCK("lock","locks the box if closed and not locked"),
	UNLOCK("unlock","unlocks the box if locked"),
	HELP("help","displays available commands"),
	EXIT("exit", "stops application");
	
	private String _name;
	private String _description;
	
	private BoxCommand(String name, String description){
		_name = name;
		_description = description;
	}
	
	public String getName(){
		return _name;
	}
	
	public String getDescription(){
		return _description;
	}
}