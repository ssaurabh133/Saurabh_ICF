<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="partPrePaymetList" >
	<logic:notEmpty name="partPrePaymetList" >
      <logic:iterate name="partPrePaymetList" id="subloanList">

			<bean:write name="subloanList" property="lbxLoanNoHID"/>
			$:<bean:write name="subloanList" property="loanNo"/>
			$:<bean:write name="subloanList" property="customerName"/>
			$:<bean:write name="subloanList" property="product"/>
			$:<bean:write name="subloanList" property="scheme"/>
			$:<bean:write name="subloanList" property="disbursedAmount"/>
			$:<bean:write name="subloanList" property="outstandingLoanAmount"/>
			$:<bean:write name="subloanList" property="nextDueDate"/>
			$:<bean:write name="subloanList" property="partPaymentSinceDisbursal"/>
			$:<bean:write name="subloanList" property="lastPartPaymentDate"/>
			$:<bean:write name="subloanList" property="installmentType"/>
			$:<bean:write name="subloanList" property="lbxInstlNo"/>
			$:<bean:write name="subloanList" property="partPrePaymentCal"/>
			$:<bean:write name="subloanList" property="amount"/>
			
	</logic:iterate>
    </logic:notEmpty>    
    
    <logic:empty name="partPrePaymetList" >
       <bean:message key="msg.PendingPartPrePaymentApprovals"/>
    </logic:empty>
</logic:present>

$:${recStatusForDisbursal}

<logic:present name="genericMasterList">
			$: <div id="instType">
			 <select name="partPaymentParameter" class="text" id="partPaymentParameter">
			<option value="">---Select----</option>
			<logic:iterate id="genericMasterObj" name="genericMasterList"> 
			<option value="<bean:write name="genericMasterObj" property="id" />"><bean:write name="genericMasterObj" property="name" /></option>
			</logic:iterate>
			 </select>
			 </div>
			    </logic:present>