package nwscore.ffrectmodel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FulfillmentConfig{

	@SerializedName("routes")
	private List<RoutesItem> routes;

	@SerializedName("service_levels")
	private ServiceLevels serviceLevels;

	@SerializedName("sl_levels_priority")
	private List<String> slLevelsPriority;

	@SerializedName("provider_rates")
	private ProviderRates providerRates;

	public void setRoutes(List<RoutesItem> routes){
		this.routes = routes;
	}

	public List<RoutesItem> getRoutes(){
		return routes;
	}

	public void setServiceLevels(ServiceLevels serviceLevels){
		this.serviceLevels = serviceLevels;
	}

	public ServiceLevels getServiceLevels(){
		return serviceLevels;
	}

	public void setSlLevelsPriority(List<String> slLevelsPriority){
		this.slLevelsPriority = slLevelsPriority;
	}

	public List<String> getSlLevelsPriority(){
		return slLevelsPriority;
	}

	public void setProviderRates(ProviderRates providerRates){
		this.providerRates = providerRates;
	}

	public ProviderRates getProviderRates(){
		return providerRates;
	}
}