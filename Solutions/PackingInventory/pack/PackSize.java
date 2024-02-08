package pack;

public enum PackSize{
	SMALL(5, 5, 3),
	MEDIUM(7, 10, 5),
	LARGE(10, 15, 8);
	
	private double maxWeight;
	private double maxVolume;
	private int maxItemCount;
	
	private PackSize(double maxWeight, double maxVolume, int maxItemCount){
		this.maxWeight = maxWeight;
		this.maxVolume = maxVolume;
		this.maxItemCount = maxItemCount;
	}
	
	public double getMaxWeight(){
		return maxWeight;
	}
	
	public double getMaxVolume(){
		return maxVolume;
	}
	
	public int getMaxItemCount(){
		return maxItemCount;
	}
}