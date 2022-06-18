<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="retriveLoanForLiquidationValues" >

              
       <logic:iterate name="retriveLoanForLiquidationValues" id="subloanList" >
       		  ${subloanList.product}
       		$:${subloanList.scheme}
       		$:${subloanList.loanAmt}
			$:${subloanList.loanApprovalDate}    
			$:${subloanList.loanStatus}
            $:${subloanList.disbursalStatus}
	</logic:iterate> 
    </logic:present>  
    
    <logic:notEmpty name="retriveLiquidationValues" >
      <logic:iterate name="retriveLiquidationValues" id="subloanList">

			${subloanList.sdAmount}
			$:${subloanList.sdInterestType}
			$:${subloanList.sdInterestRate}
			$:${subloanList.sdCompoundingFreq}
			$:${subloanList.sdStartDate}
			$:${subloanList.sdMaturityDate}
			$:${subloanList.sdInterestAccrued}
			$:${subloanList.sdInterestAccruedDate}
			$:${subloanList.sdTDSRate}
			$:${subloanList.sdTDSDeducted}
			$:${subloanList.liquidatedAmountPrincipal}
			$:${subloanList.liquidatedAmountInterest}
			$:${subloanList.sdFinalInterest}	        
            
	</logic:iterate>
    </logic:notEmpty>    
    
    <logic:empty name="retriveLiquidationValues" >
       <bean:message key="msg.PendingLiquidationApprovals"/>
    </logic:empty>