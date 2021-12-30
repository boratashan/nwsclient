package nwscore.ffrectmodel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DestinationRegion{

	@SerializedName("countries")
	private List<String> countries;

	@SerializedName("zip_codes")
	private List<String> zipCodes;

	public void setCountries(List<String> countries){
		this.countries = countries;
	}

	public List<String> getCountries(){
		return countries;
	}

	public void setZipCodes(List<String> zipCodes){
		this.zipCodes = zipCodes;
	}

	public List<String> getZipCodes(){
		return zipCodes;
	}
}