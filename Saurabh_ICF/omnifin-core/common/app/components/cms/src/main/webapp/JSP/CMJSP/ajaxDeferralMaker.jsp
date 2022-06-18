<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="deferralList" >
	<logic:notEmpty name="deferralList" >
      <logic:iterate name="deferralList" id="subloanList">

			${subloanList.lbxLoanNoHID}					
			$:${subloanList.loanNo}	
			$:${subloanList.customerName}	
			$:${subloanList.product}	
			$:${subloanList.scheme}
			$:${subloanList.disbursedAmount}	
			$:${subloanList.outstandingLoanAmount}	
			$:${subloanList.deferralsSinceDsibursal}	
			$:${subloanList.lastDeferralDate}
			$:<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr class="white1">
					<td width="10%">
						${subloanList.interestRateType}
						<html:hidden property="interestRateType" styleId="interestRateType" value="${subloanList.interestRateType}"/>
					</td>
					<td width="10%">
						${subloanList.baseRateType}
						<html:hidden property="baseRateType" styleId="baseRateType" value="${subloanList.baseRateType}"/>
					</td>
					<td width="8%">
						${subloanList.baseRate}
						<html:hidden property="baseRate" styleId="baseRate" value="${subloanList.baseRate}"/>
					</td>
					<td width="10%">
						${subloanList.installmentFrequency}
						<html:hidden property="installmentFrequency" styleId="installmentFrequency" value="${subloanList.installmentFrequency}"/>
					</td>
					<td width="10%">
						${subloanList.installmentType}
						<html:hidden property="installmentType" styleId="installmentType" value="${subloanList.installmentType}"/>
					</td>
					<td width="6%">
						${subloanList.emi}
						<html:hidden property="emi" styleId="emi" value="${subloanList.emi}"/>
					</td>
					<td width="6%">
						${subloanList.tenure}
						<html:hidden property="tenure" styleId="tenure" value="${subloanList.tenure}"/>
						</td>
					<td width="8%">
						${subloanList.maturityDate}
						<html:hidden property="maturityDate" styleId="maturityDate" value="${subloanList.maturityDate}"/>
				
					</td>
					<td width="8%">
						${subloanList.nextDueDate}
						<html:hidden property="nextDueDate" styleId="nextDueDate" value="${subloanList.nextDueDate}"/>
						
					</td>
				</tr>
			</table>
	</logic:iterate>
    </logic:notEmpty>    
    
    <logic:empty name="deferralList" >
       <bean:message key="msg.PendingDeferralApprovals"/>
    </logic:empty>
</logic:present>


<logic:present name="feasibility">
<% if(request.getAttribute("feasibility").toString().equalsIgnoreCase("deferralAllowedYear"))
	{ %>
	<bean:message key="msg.deferralAllowedYear"/>
<%	} %>
<% if(request.getAttribute("feasibility").toString().equalsIgnoreCase("minGapSuccDeferralValid"))
	{ %>
	<bean:message key="msg.minGapSuccDeferralValid"/>
<%	} %>
<% if(request.getAttribute("feasibility").toString().equalsIgnoreCase("totalDeferralAllowedExceeds"))
	{ %>
	<bean:message key="msg.totalDeferralAllowedExceeds"/>
<%	} %>
<% if(request.getAttribute("feasibility").toString().equalsIgnoreCase("lockinPeriod"))
	{ %>
	<bean:message key="msg.lockinPeriod"/>
<%	} %>
</logic:present>