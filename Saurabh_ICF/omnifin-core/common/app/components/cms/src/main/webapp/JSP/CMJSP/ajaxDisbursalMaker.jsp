<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
 
 <logic:present name="retriveDisbursalValues">
    <logic:notEmpty name="retriveDisbursalValues" >
      <logic:iterate name="retriveDisbursalValues" id="subloanList">

			${subloanList.loanNo}
			$:${subloanList.customerName}
			$:${subloanList.loanAmt	}
			$:${subloanList.loanApprovalDate}
			$:${subloanList.product}
            $:${subloanList.scheme}
            $:${subloanList.lbxLoanNoHID}
            $:${subloanList.proposedShortPayAmount}
            $:${subloanList.disbursedAmount}
            $:${subloanList.adjustedShortPayAmount}
            $:${subloanList.disbursalNo}
            $:${subloanList.pdcDepositCount}
            $:${subloanList.repayMode}
           
            
        
            <logic:equal name="subloanList" property="repayMode" value="I">
            $:<input type="checkbox" name="finalDisbursal"
				 id="finalDisbursal" onclick="calculateMaturityDateNew();showEffectiveDate();" />
			  <html:hidden property="repayMode" styleId="repayMode" 
					styleClass="text" value="I"/>
			</logic:equal>
			<logic:equal name="subloanList" property="repayMode" value="N">
            $:<input type="checkbox" name="finalDisbursal"
				 id="finalDisbursal" onclick="showEffectiveDate();" disabled="disabled" />
			  <html:hidden property="repayMode" styleId="repayMode" 
					styleClass="text" value="N"/>
			</logic:equal>
            
		$:${sessionScope.userobject.businessdate}
		$:${subloanList.maxExpectedPayDate}
		$:${subloanList.disbursalDate}
		$:${subloanList.disbursedAmountTemp}
		$:${subloanList.revolvingFlag}
		$:${subloanList.balancePrinc}
		$:${subloanList.forwardedAmt}
		$:${subloanList.installmentType}
		

	</logic:iterate>
    </logic:notEmpty>
    
    <logic:empty name="retriveDisbursalValues" >
       <bean:message key="msg.PendingApprovals"/>
    </logic:empty>
    </logic:present>
    <logic:present name="calculatedEMI">
    <logic:notEmpty name="calculatedEMI" >
      <logic:iterate name="calculatedEMI" id="list">
			${list.currentMonthEMI}
			$:${list.preEMINextMonth}
		</logic:iterate>
	</logic:notEmpty>
    
    </logic:present>
    
    <logic:present name="cycleDate">
    <logic:present name="loanDueDay">
   <input type="text" name="hiddenDueDate" id="hiddenDueDate" value="${loanDueDay[0].cycleDateValue}"/>
	    <html:select property="cycleDate" styleId="cycleDate" value="${loanDueDay[0].cycleDateValue}" styleClass="text" onchange="nullNextDue(this.value);">
	    	<html:option value="">--<bean:message key="lbl.select" />--</html:option>
			<html:optionsCollection name="cycleDate" label="cycleDateDesc" value="cycleDateValue" />
		</html:select>
	</logic:present>
	</logic:present>
	

	<logic:present name="cycleDate">
	$:<div id="yes3">
	<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataNew[0].cycleDate}"/>
	 <select name="cycleDate" class="text" id="cycleDate"  onchange="nullNextDue(this.value);">
			<option value="">---Select----</option>
			<logic:iterate id="cycleDueDayObj" name="cycleDate"> 
			<option value="<bean:write name="cycleDueDayObj" property="id" />"><bean:write name="cycleDueDayObj" property="name" /></option>
			</logic:iterate>
	 </select>
	</div>
	</logic:present>
	<logic:present name="amountInProcessLoan">
		$:${amountInProcessLoan}
	</logic:present>
	<logic:present name="validateFormNo">
		$:${validateFormNo}
	</logic:present>
	$:${subloanList.prePartmentAmount}
	$:${recStatusForPartPayment}
	$:${interestDueDate}
	$:${subloanList.interestCalculationMethod}
	$:${subloanList.interestFrequency}
	$:${subloanList.interestCompoundingFrequency}
	$:${subloanList.loanTenure}
	$:${subloanList.editDueDate}
	
	
		