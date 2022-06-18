package com.VO;

import java.io.Serializable;

public class ConstitutionVO implements Serializable{
	
	private String contitutionCode;
	private String constitution;
	
	public String getContitutionCode() {
		return contitutionCode;
	}
	public void setContitutionCode(String contitutionCode) {
		this.contitutionCode = contitutionCode;
	}
	public String getConstitution() {
		return constitution;
	}
	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}
	
	

}
