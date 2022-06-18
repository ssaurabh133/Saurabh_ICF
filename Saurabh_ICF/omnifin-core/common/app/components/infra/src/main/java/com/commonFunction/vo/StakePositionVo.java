package com.commonFunction.vo;

import java.io.Serializable;

public class StakePositionVo implements Serializable{

	private String position_code;
	private String position_name;
	public String getPosition_code() {
		return position_code;
	}
	public void setPosition_code(String positionCode) {
		position_code = positionCode;
	}
	public String getPosition_name() {
		return position_name;
	}
	public void setPosition_name(String positionName) {
		position_name = positionName;
	}
}
