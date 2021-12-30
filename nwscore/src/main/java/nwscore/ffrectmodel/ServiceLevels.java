package nwscore.ffrectmodel;

import com.google.gson.annotations.SerializedName;

public class ServiceLevels{

	@SerializedName("EXPRESS")
	private EXPRESS eXPRESS;

	@SerializedName("OVERNIGHT")
	private OVERNIGHT oVERNIGHT;

	@SerializedName("INTERNATIONAL")
	private INTERNATIONAL iNTERNATIONAL;

	@SerializedName("STANDARD")
	private STANDARD sTANDARD;

	public void setEXPRESS(EXPRESS eXPRESS){
		this.eXPRESS = eXPRESS;
	}

	public EXPRESS getEXPRESS(){
		return eXPRESS;
	}

	public void setOVERNIGHT(OVERNIGHT oVERNIGHT){
		this.oVERNIGHT = oVERNIGHT;
	}

	public OVERNIGHT getOVERNIGHT(){
		return oVERNIGHT;
	}

	public void setINTERNATIONAL(INTERNATIONAL iNTERNATIONAL){
		this.iNTERNATIONAL = iNTERNATIONAL;
	}

	public INTERNATIONAL getINTERNATIONAL(){
		return iNTERNATIONAL;
	}

	public void setSTANDARD(STANDARD sTANDARD){
		this.sTANDARD = sTANDARD;
	}

	public STANDARD getSTANDARD(){
		return sTANDARD;
	}
}