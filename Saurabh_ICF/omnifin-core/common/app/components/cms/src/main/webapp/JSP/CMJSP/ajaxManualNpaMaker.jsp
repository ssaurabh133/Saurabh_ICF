<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="list" >

 
       <logic:iterate name="list" id="obj" >

          	${obj.lbxLoanNo}
			$:${obj.loanAmount}
			$:${obj.balancePrincipal}
			$:${obj.recievedPrincipal}
            $:${obj.overduePrincipal}
            $:${obj.totOverduePrinc}
            $:${obj.emiAmount}
            $:${obj.dpd}
            $:${obj.dpdString}
            $:${obj.lastNpaStage}
          	$:${obj.loanNo}
 			$:${obj.approvalDate}
            $:${obj.disbursalAmt}
            $:${obj.disbursalDate}
          	$:${obj.instOverdue}
	</logic:iterate> 
    </logic:present>  
 
   