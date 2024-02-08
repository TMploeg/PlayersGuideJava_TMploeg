package menu;

public interface MenuAction<T>{
	public void run(T args);
}