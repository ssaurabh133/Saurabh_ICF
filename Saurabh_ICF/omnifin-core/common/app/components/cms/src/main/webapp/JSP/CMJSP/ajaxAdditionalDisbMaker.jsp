<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="addDisb" >
	<logic:notEmpty name="addDisb" >
      <logic:iterate name="addDisb" id="subloanList">

			<bean:write name="subloanList" property="sanctionedAmount"/>
			$:<bean:write name="subloanList" property="utilizedAmount"/>
			$:<bean:write name="subloanList" property="lbxLoanNoHID"/>
			$:<bean:write name="subloanList" property="loanNo"/>
			$:<bean:write name="subloanList" property="customerName"/>
			$:<bean:write name="subloanList" property="product"/>
			$:<bean:write name="subloanList" property="scheme"/>
			$:<bean:write name="subloanList" property="disbursedAmount"/>
			$:<bean:write name="subloanList" property="outstandingLoanAmount"/>
			$:<bean:write name="subloanList" property="nextDueDate"/>
			$:<bean:write name="subloanList" property="installmentType"/>
			$:<bean:write name="subloanList" property="lbxInstlNo"/>
	
			
	</logic:iterate>
    </logic:notEmpty>    
    
    <logic:empty name="addDisb" >
       
    </logic:empty>
</logic:present>



<logic:present name="genericMasterList">
			$: <div id="intsTypeVar">
			 <select name="rescheduleParameter" class="text" id="rescheduleParameter">
			<option value="">---Select----</option>
			<logic:iterate id="genericMasterObj" name="genericMasterList"> 
			<option value="<bean:write name="genericMasterObj" property="id" />"><bean:write name="genericMasterObj" property="name" /></option>
			</logic:iterate>
			 </select>
			 </div>
			    </logic:present>







