<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="rateDetailList" >
	<logic:notEmpty name="rateDetailList" >
      <logic:iterate name="rateDetailList" id="subloanList">
            $:${requestScope.status}
			$:${subloanList.loanAmount}
			$:${subloanList.balanceAmount}
			$:${subloanList.loanFinalRate}
			$:${subloanList.currentRateType}
			$:${subloanList.loanRateType}
			
	</logic:iterate>
    </logic:notEmpty>    
    
    <logic:empty name="rateDetailList" >
    
      </logic:empty>
</logic:present>


