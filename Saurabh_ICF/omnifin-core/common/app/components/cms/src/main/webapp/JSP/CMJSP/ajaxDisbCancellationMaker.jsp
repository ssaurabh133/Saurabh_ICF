<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%> 
    
    <logic:present name="disbCancellationValues" >
      <logic:iterate name="disbCancellationValues" id="subloanList">

			${subloanList.loanAc}
			$:${subloanList.lbxLoanNoHID}    
			$:${subloanList.customerName}
			$:${subloanList.loanDate}
            $:${subloanList.loanAmt}
            $:${subloanList.product}
            $:${subloanList.scheme}              
            
            
	</logic:iterate>
    </logic:present>  