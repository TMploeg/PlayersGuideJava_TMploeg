public class Soup{
	private FoodType _type;
	private Ingredient _mainIngredient;
	private Seasoning _seasoning;
	
	public Soup(FoodType type, Ingredient mainIngredient, Seasoning seasoning){
		_type = type;
		_mainIngredient = mainIngredient;
		_seasoning = seasoning;
	}
	
	public FoodType getType(){
		return _type;
	}
	
	public Ingredient getMainIngredient(){
		return _mainIngredient;
	}
	
	public Seasoning getSeasoning(){
		return _seasoning;
	}
}