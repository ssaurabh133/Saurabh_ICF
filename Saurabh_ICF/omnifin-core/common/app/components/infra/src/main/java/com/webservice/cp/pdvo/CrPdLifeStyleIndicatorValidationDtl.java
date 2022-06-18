package  com.webservice.cp.pdvo;

public class CrPdLifeStyleIndicatorValidationDtl {
	
	private Integer id;
	private Integer pdId;
	private String particular;
	private String signedAtResidenceOffice;
	private String remark;
	private String makerDate;
	private String makerId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPdId() {
		return pdId;
	}

	public void setPdId(Integer pdId) {
		this.pdId = pdId;
	}

	public String getParticular() {
		return particular;
	}

	public void setParticular(String particular) {
		this.particular = particular;
	}

	public String getSignedAtResidenceOffice() {
		return signedAtResidenceOffice;
	}

	public void setSignedAtResidenceOffice(String signedAtResidenceOffice) {
		this.signedAtResidenceOffice = signedAtResidenceOffice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMakerDate() {
		return makerDate;
	}

	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	@Override
	public String toString() {
		return "CrPdLifeStyleIndicatorValidationDtl [id=" + id + ", pdId="
				+ pdId + ", particular=" + particular
				+ ", signedAtResidenceOffice=" + signedAtResidenceOffice
				+ ", remark=" + remark + ", makerDate=" + makerDate
				+ ", makerId=" + makerId + "]";
	}


}
