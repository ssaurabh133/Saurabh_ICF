<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


    <logic:present name="achCustomerList" >
      <logic:iterate name="achCustomerList" id="list">
		${list.txtDealNo}
		$:${list.dteDate}
		$:${list.txtWeHerebyAuthorize}
		$:${list.txtBankAccountNo}
		$:${list.hidBankName}
		$:${list.txtBankName}
		$:${list.hidBankBranchName}
		$:${list.txtBankBranchName}
		$:${list.txtMicr}
		$:${list.txtIfsc}
		$:${list.txtLoanAmount}
		$:${list.txtTotalAmount}
		$:${list.txtPhoneNo}
		$:${list.txtEmailId}
		$:${list.selFrequency}
		$:${list.dteFromDate}
		$:${list.dteToDate}
		$:${list.txtSponsorBankCode}
		$:${list.txtUtilityCode}
		$:${list.txtReferenceNo}
	</logic:iterate>
    </logic:present>
    
    
     