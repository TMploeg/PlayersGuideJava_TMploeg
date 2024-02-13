package game;

import java.util.Random;
import map.*;

public class Game{
	private static final int MAP_SIZE = 8;
	
	private Map map;
	
	public Game(){
		map = new MapBuilder().setSize(MAP_SIZE).build();
		map.display();
	}
}