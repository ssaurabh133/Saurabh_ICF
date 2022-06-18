<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="receiptBranchMicrList" >
	<logic:notEmpty name="receiptBranchMicrList" >
      <logic:iterate name="receiptBranchMicrList" id="subReceiptBranchMicrList">
			  ${subReceiptBranchMicrList.depositBranchID}
			$:${subReceiptBranchMicrList.depositMicr}
	</logic:iterate>
    </logic:notEmpty>    
</logic:present>
