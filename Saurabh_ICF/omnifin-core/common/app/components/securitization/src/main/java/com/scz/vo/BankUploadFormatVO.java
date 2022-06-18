package com.scz.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class BankUploadFormatVO {
	
	private int id;
	private int poolID;
	private int instlNO;
	private Date instlDate;
	private BigDecimal instlAmount;
	private BigDecimal prinComp;
	private BigDecimal intComp;
	private BigDecimal auPrinComp;
	private BigDecimal auIntComp;
	private BigDecimal distributionRatioBank;
	private BigDecimal distributionRatioAu;
	private BigDecimal prinOS;
	private String userID;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPoolID() {
		return poolID;
	}
	public void setPoolID(int poolID) {
		this.poolID = poolID;
	}
	public int getInstlNO() {
		return instlNO;
	}
	public void setInstlNO(int instlNO) {
		this.instlNO = instlNO;
	}
	public Date getInstlDate() {
		return instlDate;
	}
	public void setInstlDate(Date instlDate) {
		this.instlDate = instlDate;
	}
	public BigDecimal getInstlAmount() {
		return instlAmount;
	}
	public void setInstlAmount(BigDecimal instlAmount) {
		this.instlAmount = instlAmount;
	}
	public BigDecimal getPrinComp() {
		return prinComp;
	}
	public void setPrinComp(BigDecimal prinComp) {
		this.prinComp = prinComp;
	}
	public BigDecimal getIntComp() {
		return intComp;
	}
	public void setIntComp(BigDecimal intComp) {
		this.intComp = intComp;
	}
	public BigDecimal getAuPrinComp() {
		return auPrinComp;
	}
	public void setAuPrinComp(BigDecimal auPrinComp) {
		this.auPrinComp = auPrinComp;
	}
	public BigDecimal getAuIntComp() {
		return auIntComp;
	}
	public void setAuIntComp(BigDecimal auIntComp) {
		this.auIntComp = auIntComp;
	}
	public BigDecimal getDistributionRatioBank() {
		return distributionRatioBank;
	}
	public void setDistributionRatioBank(BigDecimal distributionRatioBank) {
		this.distributionRatioBank = distributionRatioBank;
	}
	public BigDecimal getDistributionRatioAu() {
		return distributionRatioAu;
	}
	public void setDistributionRatioAu(BigDecimal distributionRatioAu) {
		this.distributionRatioAu = distributionRatioAu;
	}
	public BigDecimal getPrinOS() {
		return prinOS;
	}
	public void setPrinOS(BigDecimal prinOS) {
		this.prinOS = prinOS;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
}
