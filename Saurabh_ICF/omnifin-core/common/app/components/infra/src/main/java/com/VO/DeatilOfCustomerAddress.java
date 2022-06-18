package com.VO;

public class DeatilOfCustomerAddress {
	
	private String addr1;
	private String addr2;
	private String addr3;
	private String state_code;
	private String state_name;
	private String dist_code;
	private String dist_name;
	private String region_code;
	private String region_name;
	
	public String getDist_code() {
		return dist_code;
	}
	public void setDist_code(String distCode) {
		dist_code = distCode;
	}
	public String getDist_name() {
		return dist_name;
	}
	public void setDist_name(String distName) {
		dist_name = distName;
	}
	public String getRegion_code() {
		return region_code;
	}
	public void setRegion_code(String regionCode) {
		region_code = regionCode;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String regionName) {
		region_name = regionName;
	}

	public String getState_code() {
		return state_code;
	}
	public void setState_code(String stateCode) {
		state_code = stateCode;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String stateName) {
		state_name = stateName;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr3() {
		return addr3;
	}
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	
}
