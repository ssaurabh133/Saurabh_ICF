<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="bpList" >
          	${bpList[0].lbxBPNID}
			$:${bpList[0].customerName}
			$:${bpList[0].loanRepaymentType}
  </logic:present>  
    <logic:present name="amount" >
			$:${amount}
  </logic:present>  
    