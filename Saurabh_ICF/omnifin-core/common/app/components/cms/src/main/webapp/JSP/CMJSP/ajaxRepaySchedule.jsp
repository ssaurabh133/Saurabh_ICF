<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="repayScheduleList" >
	<logic:notEmpty name="repayScheduleList" >
      <logic:iterate name="repayScheduleList" id="subloanList">

			<bean:write name="subloanList" property="lbxLoanNoHID" />
			$:${subloanList.loanNo}
			$:${subloanList.customerName}
			$:${subloanList.product}
			$:${subloanList.scheme}
			$:${subloanList.disbursedAmount}
			$:${subloanList.outstandingLoanAmount}
			$:${subloanList.curentDueDate}
			$:${subloanList.repaymentType}
			$:${subloanList.emi}
			$:${subloanList.dueDay}
			$:${subloanList.repayEffDate}
			$:${subloanList.loanFrequency}
			
		
			
	</logic:iterate>
    </logic:notEmpty>    
    
    <logic:empty name="repayScheduleList" >
    
      </logic:empty>
</logic:present>


