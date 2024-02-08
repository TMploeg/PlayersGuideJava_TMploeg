package items;

public abstract class InventoryItem {
	private double weight;
	private double volume;
	
	public InventoryItem(double weight, double volume){
		this.weight = weight;
		this.volume = volume;
	}
	
	public double getWeight(){
		return weight;
	}
	
	public double getVolume(){
		return volume;
	}
	
	@Override
	public String toString(){
		return this.getClass().getSimpleName();
	}
}