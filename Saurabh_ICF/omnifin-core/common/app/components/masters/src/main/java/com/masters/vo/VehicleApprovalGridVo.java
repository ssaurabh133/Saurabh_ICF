package com.masters.vo;

import java.io.Serializable;

/**
 * @author a3sdsk023
 *
 */
/**
 * @author a3sdsk023
 *
 */
public class VehicleApprovalGridVo implements Serializable {
	private String	lbxProductID;
	private String	product;
	private String	lbxScheme;
	private String	scheme;
	private String	manufacturer;
	private String	model;
	private int		currentPageLink;
	private int		totalRecordSize;
	private String vehicleApprovalYear;
	private String	branchAmt;
	private String	hoAmt;
	private String	nationalAmt;
	private String	gridBranchAmt;
	private String	gridHoAmt;
	private String	gridNationalAmt;
	private String	status;
	private String	gridId;
	private String vehicleType;
    private String manufactId;
	private String modelDesc;
	private String modelDescId;
	private String vehicleApprovalID;
	
	private String[] vehicleApprovalYearArr;
	private String[] vehicleApprovalBranchArr;
	private String[] vehicleApprovalNationalArr;
	private String[] vehicleApprovalHOArr;
	private String	makerId;
	private String	makerDate;
	

	public String getVehicleApprovalID() {
		return vehicleApprovalID;
	}

	public void setVehicleApprovalID(String vehicleApprovalID) {
		this.vehicleApprovalID = vehicleApprovalID;
	}

	public String getLbxProductID() {
		return lbxProductID;
	}

	public void setLbxProductID(String lbxProductID) {
		this.lbxProductID = lbxProductID;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getCurrentPageLink() {
		return currentPageLink;
	}

	public void setCurrentPageLink(int currentPageLink) {
		this.currentPageLink = currentPageLink;
	}

	public int getTotalRecordSize() {
		return totalRecordSize;
	}

	public void setTotalRecordSize(int totalRecordSize) {
		this.totalRecordSize = totalRecordSize;
	}

	public String getVehicleApprovalYear() {
		return vehicleApprovalYear;
	}

	public void setVehicleApprovalYear(String vehicleApprovalYear) {
		this.vehicleApprovalYear = vehicleApprovalYear;
	}

	public String getBranchAmt() {
		return branchAmt;
	}

	public void setBranchAmt(String branchAmt) {
		this.branchAmt = branchAmt;
	}

	public String getHoAmt() {
		return hoAmt;
	}

	public void setHoAmt(String hoAmt) {
		this.hoAmt = hoAmt;
	}

	public String getNationalAmt() {
		return nationalAmt;
	}

	public void setNationalAmt(String nationalAmt) {
		this.nationalAmt = nationalAmt;
	}

	public String getGridBranchAmt() {
		return gridBranchAmt;
	}

	public void setGridBranchAmt(String gridBranchAmt) {
		this.gridBranchAmt = gridBranchAmt;
	}

	public String getGridHoAmt() {
		return gridHoAmt;
	}

	public void setGridHoAmt(String gridHoAmt) {
		this.gridHoAmt = gridHoAmt;
	}

	public String getGridNationalAmt() {
		return gridNationalAmt;
	}

	public void setGridNationalAmt(String gridNationalAmt) {
		this.gridNationalAmt = gridNationalAmt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getLbxScheme() {
		return lbxScheme;
	}

	public void setLbxScheme(String lbxScheme) {
		this.lbxScheme = lbxScheme;
	}

	public String getManufactId() {
		return manufactId;
	}

	public void setManufactId(String manufactId) {
		this.manufactId = manufactId;
	}

	public String getModelDesc() {
		return modelDesc;
	}

	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

	public String getModelDescId() {
		return modelDescId;
	}

	public void setModelDescId(String modelDescId) {
		this.modelDescId = modelDescId;
	}
	
	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public String getMakerDate() {
		return makerDate;
	}

	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

	public String[] getVehicleApprovalYearArr() {
		return vehicleApprovalYearArr;
	}

	public void setVehicleApprovalYearArr(String[] vehicleApprovalYearArr) {
		this.vehicleApprovalYearArr = vehicleApprovalYearArr;
	}

	public String[] getVehicleApprovalBranchArr() {
		return vehicleApprovalBranchArr;
	}

	public void setVehicleApprovalBranchArr(String[] vehicleApprovalBranchArr) {
		this.vehicleApprovalBranchArr = vehicleApprovalBranchArr;
	}

	public String[] getVehicleApprovalNationalArr() {
		return vehicleApprovalNationalArr;
	}

	public void setVehicleApprovalNationalArr(String[] vehicleApprovalNationalArr) {
		this.vehicleApprovalNationalArr = vehicleApprovalNationalArr;
	}

	public String[] getVehicleApprovalHOArr() {
		return vehicleApprovalHOArr;
	}

	public void setVehicleApprovalHOArr(String[] vehicleApprovalHOArr) {
		this.vehicleApprovalHOArr = vehicleApprovalHOArr;
	}

	
}
