package pack;

import items.InventoryItem;

public class Pack{
	private final double maxWeight;
	private final double maxVolume;
	private final int maxItemCount;
	
	private InventoryItem[] items;
	
	public Pack(PackSize packSize){
		this.maxWeight = packSize.getMaxWeight();
		this.maxVolume = packSize.getMaxVolume();
		this.maxItemCount = packSize.getMaxItemCount();
		
		items = new InventoryItem[0];
	}
	
	public boolean canAdd(InventoryItem item){
		return (
			(getTotalWeight() + item.getWeight() <= maxWeight) &&
			(getTotalVolume() + item.getVolume() <= maxVolume) &&
			(getItemCount() + 1 <= maxItemCount)
		);
	}
	
	public void add(InventoryItem item){
		if(!canAdd(item)){
			throw new IllegalArgumentException("cannot add item to pack");
		}
		
		InventoryItem[] newItems = new InventoryItem[items.length + 1];
		
		for(int i = 0; i < items.length; i++){
			newItems[i] = items[i];
		}
		
		newItems[newItems.length - 1] = item;
		
		items = newItems;
	}
	
	public double getTotalWeight(){
		double totalWeight = 0;
		
		for(InventoryItem item : items){
			totalWeight += item.getWeight();
		}
		
		return totalWeight;
	}
	
	public double getMaxWeight(){
		return maxWeight;
	}
	
	public double getTotalVolume(){
		double totalVolume = 0;
		
		for(InventoryItem item : items){
			totalVolume += item.getVolume();
		}
		
		return totalVolume;
	}
	
	public double getMaxVolume(){
		return maxVolume;
	}
	
	public int getItemCount(){
		return items.length;
	}
	
	public int getMaxItemCount(){
		return maxItemCount;
	}
	
	@Override
	public String toString(){
		String output = this.getClass().getSimpleName() + ", CONTENTS: { ";
		
		for(int i = 0; i < maxItemCount; i++){
			output += (i < items.length ? items[i] : "_") + " ";
		}
		
		output += "}";
		
		return output;
	}
}