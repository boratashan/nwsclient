package nwscore.ffrectmodel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FulfilledBy{

	@SerializedName("INTERNATIONAL")
	private List<INTERNATIONALItem> iNTERNATIONAL;

	@SerializedName("EXPRESS")
	private List<EXPRESSItem> eXPRESS;

	@SerializedName("OVERNIGHT")
	private List<OVERNIGHTItem> oVERNIGHT;

	@SerializedName("STANDARD")
	private List<STANDARDItem> sTANDARD;

	public void setINTERNATIONAL(List<INTERNATIONALItem> iNTERNATIONAL){
		this.iNTERNATIONAL = iNTERNATIONAL;
	}

	public List<INTERNATIONALItem> getINTERNATIONAL(){
		return iNTERNATIONAL;
	}

	public void setEXPRESS(List<EXPRESSItem> eXPRESS){
		this.eXPRESS = eXPRESS;
	}

	public List<EXPRESSItem> getEXPRESS(){
		return eXPRESS;
	}

	public void setOVERNIGHT(List<OVERNIGHTItem> oVERNIGHT){
		this.oVERNIGHT = oVERNIGHT;
	}

	public List<OVERNIGHTItem> getOVERNIGHT(){
		return oVERNIGHT;
	}

	public void setSTANDARD(List<STANDARDItem> sTANDARD){
		this.sTANDARD = sTANDARD;
	}

	public List<STANDARDItem> getSTANDARD(){
		return sTANDARD;
	}
}