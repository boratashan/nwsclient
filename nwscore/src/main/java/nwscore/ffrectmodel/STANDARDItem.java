package nwscore.ffrectmodel;

import com.google.gson.annotations.SerializedName;

public class STANDARDItem{

	@SerializedName("fulfillment_node_id")
	private String fulfillmentNodeId;

	@SerializedName("provider_rate")
	private String providerRate;

	public void setFulfillmentNodeId(String fulfillmentNodeId){
		this.fulfillmentNodeId = fulfillmentNodeId;
	}

	public String getFulfillmentNodeId(){
		return fulfillmentNodeId;
	}

	public void setProviderRate(String providerRate){
		this.providerRate = providerRate;
	}

	public String getProviderRate(){
		return providerRate;
	}
}