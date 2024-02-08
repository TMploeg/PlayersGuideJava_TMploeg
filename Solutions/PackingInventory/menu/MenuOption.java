package menu;

public class MenuOption<TActionArgs>{
	private final String name;
	private final MenuAction<TActionArgs> onSelect;
	private final TActionArgs actionArgs;
	
	public MenuOption(String name, TActionArgs actionArgs, MenuAction onSelect){
		if(name == null || name.length() == 0){
			throw new IllegalArgumentException("menu option name must have at least one character");
		}
		
		if(onSelect == null){
			throw new NullPointerException();
		}
		
		this.name = name;
		this.actionArgs = actionArgs;
		this.onSelect = onSelect;
	}
	
	public void select(){
		onSelect.run(actionArgs);
	}
	
	@Override
	public String toString(){
		return name;
	}
}