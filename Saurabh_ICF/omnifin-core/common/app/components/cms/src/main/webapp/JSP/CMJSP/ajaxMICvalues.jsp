<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="retriveValues" >              
       <logic:iterate name="retriveValues" id="subloanList" >
          	${subloanList.loanAc}
			$:${subloanList.loanDate}
			$:${subloanList.customerName}
			$:${subloanList.product}
            $:${subloanList.scheme}
            $:${subloanList.lbxLoanNoHID} 
            $:${subloanList.repayEffectiveDate}
			$:${subloanList.lastAccrualDate}
            $:${subloanList.roi}
            $:${subloanList.lastIntCalcDate}
            $:${subloanList.balancePrincipal}          
	</logic:iterate> 
    </logic:present>  
    
    <logic:present name="getDetails" >
      <logic:iterate name="getDetails" id="subloanList">
			${subloanList.interestTillDate}			        
	</logic:iterate>
    </logic:present>      
      