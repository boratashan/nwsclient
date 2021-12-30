package nwscore.ffrectmodel;

import com.google.gson.annotations.SerializedName;

public class STANDARD{

	@SerializedName("tax_code")
	private String taxCode;

	@SerializedName("price")
	private int price;

	@SerializedName("delivery_time")
	private String deliveryTime;

	@SerializedName("display_name")
	private String displayName;

	@SerializedName("currency_code")
	private String currencyCode;

	@SerializedName("delivery_time_after_cutoff_hour")
	private String deliveryTimeAfterCutoffHour;

	@SerializedName("remorse_period")
	private int remorsePeriod;

	public void setTaxCode(String taxCode){
		this.taxCode = taxCode;
	}

	public String getTaxCode(){
		return taxCode;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setDeliveryTime(String deliveryTime){
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryTime(){
		return deliveryTime;
	}

	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName(){
		return displayName;
	}

	public void setCurrencyCode(String currencyCode){
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCode(){
		return currencyCode;
	}

	public void setDeliveryTimeAfterCutoffHour(String deliveryTimeAfterCutoffHour){
		this.deliveryTimeAfterCutoffHour = deliveryTimeAfterCutoffHour;
	}

	public String getDeliveryTimeAfterCutoffHour(){
		return deliveryTimeAfterCutoffHour;
	}

	public void setRemorsePeriod(int remorsePeriod){
		this.remorsePeriod = remorsePeriod;
	}

	public int getRemorsePeriod(){
		return remorsePeriod;
	}
}