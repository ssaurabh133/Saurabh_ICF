package com.webservice.cp.pdvo;

public class ExperianDaasPolicyDtl {
	private String ruleCode;
	private String parameter;
	private String result;
	
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getResult() {
		return result;
	}
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "ExperianDaasPolicyDtl [ruleCode=" + ruleCode + ", parameter="
				+ parameter + ", result=" + result + "]";
	}

}
	