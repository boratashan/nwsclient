package nwscore.ffrectmodel;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("fulfillment_config")
	private FulfillmentConfig fulfillmentConfig;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("updated_by")
	private String updatedBy;

	@SerializedName("revision")
	private int revision;

	public void setFulfillmentConfig(FulfillmentConfig fulfillmentConfig){
		this.fulfillmentConfig = fulfillmentConfig;
	}

	public FulfillmentConfig getFulfillmentConfig(){
		return fulfillmentConfig;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUpdatedBy(String updatedBy){
		this.updatedBy = updatedBy;
	}

	public String getUpdatedBy(){
		return updatedBy;
	}

	public void setRevision(int revision){
		this.revision = revision;
	}

	public int getRevision(){
		return revision;
	}
}