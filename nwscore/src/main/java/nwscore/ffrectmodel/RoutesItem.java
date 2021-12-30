package nwscore.ffrectmodel;

import com.google.gson.annotations.SerializedName;

public class RoutesItem{

	@SerializedName("fulfilled_by")
	private FulfilledBy fulfilledBy;

	@SerializedName("destination_region")
	private DestinationRegion destinationRegion;

	public void setFulfilledBy(FulfilledBy fulfilledBy){
		this.fulfilledBy = fulfilledBy;
	}

	public FulfilledBy getFulfilledBy(){
		return fulfilledBy;
	}

	public void setDestinationRegion(DestinationRegion destinationRegion){
		this.destinationRegion = destinationRegion;
	}

	public DestinationRegion getDestinationRegion(){
		return destinationRegion;
	}
}