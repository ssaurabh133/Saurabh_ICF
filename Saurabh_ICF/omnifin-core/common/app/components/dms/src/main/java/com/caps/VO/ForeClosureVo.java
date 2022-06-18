package com.caps.VO;

import org.apache.struts.action.ActionForm;

public class ForeClosureVo extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	private String balancePrincipal;
	private String overduePrincipal;
	private String overdueInstallments;
	private String unBilledInstallments;
	private String otherDues;
	private String interestTillDate;
	private String foreClosurePenalty;
	private String interstTillLP;
	private String lppAmount;
	private String otherRefunds;
	private String secureDeposit;
	private String secureDepositInterest;
	private String secureDepositTDS;
	private String earlyClosureWarn;
	private String procWarning;
	private String netReceivablePayable;
	private String waiveOffAmount;
	private String gapSDInterest;
	private String gapSDTDS;
	
	public String getWaiveOffAmount() {
		return waiveOffAmount;
	}
	public void setWaiveOffAmount(String waiveOffAmount) {
		this.waiveOffAmount = waiveOffAmount;
	}
	public String getNetReceivablePayable() {
		return netReceivablePayable;
	}
	public void setNetReceivablePayable(String netReceivablePayable) {
		this.netReceivablePayable = netReceivablePayable;
	}
	public String getEarlyClosureWarn() {
		return earlyClosureWarn;
	}
	public void setEarlyClosureWarn(String earlyClosureWarn) {
		this.earlyClosureWarn = earlyClosureWarn;
	}
	public String getProcWarning() {
		return procWarning;
	}
	public void setProcWarning(String procWarning) {
		this.procWarning = procWarning;
	}
	public String getBalancePrincipal() {
		return balancePrincipal;
	}
	public void setBalancePrincipal(String balancePrincipal) {
		this.balancePrincipal = balancePrincipal;
	}
	public String getOverduePrincipal() {
		return overduePrincipal;
	}
	public void setOverduePrincipal(String overduePrincipal) {
		this.overduePrincipal = overduePrincipal;
	}
	public String getOverdueInstallments() {
		return overdueInstallments;
	}
	public void setOverdueInstallments(String overdueInstallments) {
		this.overdueInstallments = overdueInstallments;
	}
	public String getUnBilledInstallments() {
		return unBilledInstallments;
	}
	public void setUnBilledInstallments(String unBilledInstallments) {
		this.unBilledInstallments = unBilledInstallments;
	}
	public String getOtherDues() {
		return otherDues;
	}
	public void setOtherDues(String otherDues) {
		this.otherDues = otherDues;
	}
	public String getInterestTillDate() {
		return interestTillDate;
	}
	public void setInterestTillDate(String interestTillDate) {
		this.interestTillDate = interestTillDate;
	}
	public String getForeClosurePenalty() {
		return foreClosurePenalty;
	}
	public void setForeClosurePenalty(String foreClosurePenalty) {
		this.foreClosurePenalty = foreClosurePenalty;
	}
	public String getInterstTillLP() {
		return interstTillLP;
	}
	public void setInterstTillLP(String interstTillLP) {
		this.interstTillLP = interstTillLP;
	}
	public String getLppAmount() {
		return lppAmount;
	}
	public void setLppAmount(String lppAmount) {
		this.lppAmount = lppAmount;
	}
	public String getOtherRefunds() {
		return otherRefunds;
	}
	public void setOtherRefunds(String otherRefunds) {
		this.otherRefunds = otherRefunds;
	}
	public String getSecureDeposit() {
		return secureDeposit;
	}
	public void setSecureDeposit(String secureDeposit) {
		this.secureDeposit = secureDeposit;
	}
	public String getSecureDepositInterest() {
		return secureDepositInterest;
	}
	public void setSecureDepositInterest(String secureDepositInterest) {
		this.secureDepositInterest = secureDepositInterest;
	}
	public String getSecureDepositTDS() {
		return secureDepositTDS;
	}
	public void setSecureDepositTDS(String secureDepositTDS) {
		this.secureDepositTDS = secureDepositTDS;
	}
	public String getGapSDInterest() {
		return gapSDInterest;
	}
	public void setGapSDInterest(String gapSDInterest) {
		this.gapSDInterest = gapSDInterest;
	}
	public String getGapSDTDS() {
		return gapSDTDS;
	}
	public void setGapSDTDS(String gapSDTDS) {
		this.gapSDTDS = gapSDTDS;
	}

}
