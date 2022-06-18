<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="retriveValues" >
      <logic:iterate name="retriveValues" id="subloanList">

			${subloanList.totalInstallments}
			$:${subloanList.installmentAmount}
			$:${subloanList.customerName}
         $:${subloanList.lbxLoanNoHID}
         $:${subloanList.loanAccNo}
	</logic:iterate>
    </logic:present>     
         