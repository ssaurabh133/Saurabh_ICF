<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="depositAccountList" >
	<logic:notEmpty name="depositAccountList" >
      <logic:iterate name="depositAccountList" id="subDepositAccountList">
          	${subDepositAccountList.depositBankID}
			$:${subDepositAccountList.depositBankAccount}
			$:${subDepositAccountList.depositIfscCode}
			$:${subDepositAccountList.depositBranchID}
			$:${subDepositAccountList.depositMicr}
	  </logic:iterate>
	</logic:notEmpty>
 </logic:present>  
 
    