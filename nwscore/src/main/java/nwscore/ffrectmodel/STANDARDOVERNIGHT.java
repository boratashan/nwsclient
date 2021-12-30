package nwscore.ffrectmodel;

import com.google.gson.annotations.SerializedName;

public class STANDARDOVERNIGHT{

	@SerializedName("use_as_customer_facing_cost")
	private boolean useAsCustomerFacingCost;

	@SerializedName("shipping_carrier_name")
	private String shippingCarrierName;

	@SerializedName("carrier_defined_service_level")
	private String carrierDefinedServiceLevel;

	@SerializedName("return_provider_rate")
	private String returnProviderRate;

	@SerializedName("service_level")
	private String serviceLevel;

	@SerializedName("shipping_type")
	private String shippingType;

	public void setUseAsCustomerFacingCost(boolean useAsCustomerFacingCost){
		this.useAsCustomerFacingCost = useAsCustomerFacingCost;
	}

	public boolean isUseAsCustomerFacingCost(){
		return useAsCustomerFacingCost;
	}

	public void setShippingCarrierName(String shippingCarrierName){
		this.shippingCarrierName = shippingCarrierName;
	}

	public String getShippingCarrierName(){
		return shippingCarrierName;
	}

	public void setCarrierDefinedServiceLevel(String carrierDefinedServiceLevel){
		this.carrierDefinedServiceLevel = carrierDefinedServiceLevel;
	}

	public String getCarrierDefinedServiceLevel(){
		return carrierDefinedServiceLevel;
	}

	public void setReturnProviderRate(String returnProviderRate){
		this.returnProviderRate = returnProviderRate;
	}

	public String getReturnProviderRate(){
		return returnProviderRate;
	}

	public void setServiceLevel(String serviceLevel){
		this.serviceLevel = serviceLevel;
	}

	public String getServiceLevel(){
		return serviceLevel;
	}

	public void setShippingType(String shippingType){
		this.shippingType = shippingType;
	}

	public String getShippingType(){
		return shippingType;
	}
}