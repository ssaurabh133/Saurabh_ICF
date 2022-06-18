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
            $:${subloanList.frequency}
            $:${subloanList.originalTenure}
            $:${subloanList.maturityDate}
            $:${subloanList.lbxLoanNoHID}
            $:${subloanList.remainingInstallments}
            $:${retriveValues[1].remainingTenure}
	</logic:iterate> 
    </logic:present>  
    
    <logic:present name="duesRefundsList" >
      <logic:iterate name="duesRefundsList" id="subloanList">

			${subloanList.balancePrincipal}
			$:${subloanList.secureDeposit}
			$:${subloanList.overdueInstallments}
			$:${subloanList.secureDepositInterest}
			$:${subloanList.interestTillDate}
			$:${subloanList.secureDepositTDS}
            $:${subloanList.unBilledInstallments}
            $:${subloanList.gapSDInterest}
            $:${subloanList.foreClosurePenalty}
            $:${subloanList.gapSDTDS}
            $:${subloanList.otherDues}
            $:${subloanList.otherRefunds}
            $:${subloanList.netReceivablePayable}
            $:${subloanList.interstTillLP}
            $:${subloanList.lppAmount}
            $:${subloanList.overduePrincipal}
            $:${subloanList.earlyClosureWarn}
            $:${subloanList.procWarning}  
            $:${subloanList.totalRecevable}          
            $:${subloanList.advanceEmiRefunds}
            $:${subloanList.totalPayable}
	</logic:iterate>
    </logic:present>  
    
    <logic:present name="cancellationValues" >
      <logic:iterate name="cancellationValues" id="subloanList">

			${subloanList.loanAc}
			$:${subloanList.lbxLoanNoHID}    
			$:${subloanList.customerName}
			$:${subloanList.loanDate}
            $:${subloanList.loanAmt}
            $:${subloanList.product}
            $:${subloanList.scheme}
            $:${subloanList.originalTenure}
            $:${subloanList.frequency}
            $:${subloanList.maturityDate}
            $:${subloanList.rate}
            $:${subloanList.billedPrincipal}
            $:${subloanList.disbursalStatus}
            $:${subloanList.disbursedAmount}
            $:${subloanList.balancePrincipal}
            $:${subloanList.overduePrincipal}
            $:${subloanList.billedInstallments}
            $:${subloanList.billedInstallmentAmount}
            $:${subloanList.receivedInstallmentAmount}
            $:${cancellationValues[1].remainingTenure}
            
            
	</logic:iterate>
    </logic:present>  