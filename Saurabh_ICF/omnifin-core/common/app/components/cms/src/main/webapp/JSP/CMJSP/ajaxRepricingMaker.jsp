<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="rePricingList" >
	<logic:notEmpty name="rePricingList" >
      <logic:iterate name="rePricingList" id="subloanList">

			<bean:write name="subloanList" property="lbxLoanNoHID" />
			$:${subloanList.loanNo}
			$:${subloanList.customerName}
			$:${subloanList.product}
			$:${subloanList.scheme}
			$:${subloanList.disbursedAmount}
			$:${subloanList.outstandingLoanAmount}
			$:${subloanList.rePricingSinceDsibursal}
			$:${subloanList.effectiveRate}
			$:${subloanList.flatRate}
			$:${subloanList.emi}
			$:${subloanList.lastRePricingDate}
		
			$:<table width="100%" border="0" cellspacing="1" cellpadding="1">
			    <tr class="white2">
   					<td>
   						<b><bean:message key="lbl.rePricingStatus" /></b>
   					</td>
   					<td>
						<b><bean:message key="lbl.interestRateType"/></b>
					</td>
   					<td>
						<b><bean:message key="lbl.interestRateMethod"/></b>
					</td>
					
					<td>
						<b><bean:message key="lbl.baseRateType"/></b>
					</td>
					<td>
						<b><bean:message key="lbl.baseRate"/></b>
					</td>
					<td>
						<b><bean:message key="lbl.markUp"/></b>
					</td>
					<td>
						<b><bean:message key="lbl.finalRate"/></b>
					</td>
					<td>
						<b><bean:message key="lbl.installmentFreq"/></b>
					</td>
					<td>
						<b><bean:message key="lbl.installmentType"/></b>
					</td>
					<td>
						<b><bean:message key="lbl.tenure"/></b>
					</td>
					<td>
						<b><bean:message key="lbl.noOfInstallment"/></b>
					</td>
					<td>
						<b><bean:message key="lbl.maturityDate"/></b>
					</td>
				</tr>
				<tr class="white1">
					<td><b>Current</b></td>
					<td>
					<html:hidden property="interestRateTypeOld" styleId="interestRateTypeOld" value='${subloanList.interestRateTypeHid}' styleClass="text4"/>
						${subloanList.interestRateType}
					</td>
      				<td>
      				<html:hidden property="interestRateMethodOld" styleId="interestRateMethodOld" value='${subloanList.interestRateMethodHid}' styleClass="text4"/>
						${subloanList.interestRateMethod}
      				</td>
					
					<td>
					<html:hidden property="baseRateTypeOld" styleId="baseRateTypeOld" value='${subloanList.baseRateType}' styleClass="text4"/>
						${subloanList.baseRateType}
					</td>
					<td>
					<html:hidden property="baseRateOld" styleId="baseRateOld" value='${subloanList.baseRate}' styleClass="text4"/>
						${subloanList.baseRate}
					</td>
					<td>
					<html:hidden property="markupOld" styleId="markupOld" value='${subloanList.markup}' styleClass="text4"/>
						${subloanList.markup}
					</td>
					<td>
					<html:hidden property="finalRateOld" styleId="finalRateOld" value='${subloanList.finalRate}' styleClass="text4"/>
						${subloanList.finalRate}
					</td>
					<td>
					<html:hidden property="installmentFrequencyOld" styleId="installmentFrequencyOld" value='${subloanList.installmentFrequencyOldHid}' styleClass="text4"/>
						${subloanList.installmentFrequencyOld}
					</td>
					<td>
					<html:hidden property="installmentTypeOld" styleId="installmentTypeOld" value='${subloanList.installmentTypeHid}' styleClass="text4"/>
						${subloanList.installmentType}
					</td>
					<td>
					<html:hidden property="tenureOld" styleId="tenureOld" value='${subloanList.tenure}' styleClass="text4"/>
						${subloanList.tenure}
					</td>
					<td>
					<html:hidden property="noOfInstlOld" styleId="noOfInstlOld" value='${subloanList.noOfInstl}' styleClass="text4"/>
						${subloanList.noOfInstl}
					</td>
					<td>
					<html:hidden property="maturityDateOld" styleId="maturityDateOld" value='${subloanList.maturityDate}' styleClass="text4"/>
						${subloanList.maturityDate}
					</td>
				</tr>
				
				<tr class="white1">
					<td ><b>New</b></td>
					<td>
						<html:select property="interestRateType" styleId="interestRateType"
							styleClass="text5" value="${rePricingList[0].interestRateTypeHid}" 
							onchange="return repricingInterestRateTypeChange();">
							<html:option value="E">Effective</html:option>
							<html:option value="F">Flat</html:option>
						</html:select>
					</td>
      				<td>
      					<html:select property="interestRateMethod" styleId="interestRateMethod"
      						styleClass="text5" value="${rePricingList[0].interestRateMethodHid}"
      						onchange="changeInterestRateMethod()">
      						<html:option value="F">Fixed</html:option>
      						<html:option value="L">Floating</html:option>
      					</html:select>
      				</td>
					
					<td>
						<logic:iterate id="subList" name="rePricingList">
						<logic:equal name="subList" property="interestRateTypeHid" value="F">
							<html:select property="baseRateType" styleClass="text5" styleId="baseRateType" 
								disabled="true" tabindex="-1"
								value="${rePricingList[0].baseRateType}" onchange="return getBaseRate();">
								<html:option value="">--Select--</html:option>
		             			<logic:present name="baseRateList">
		               				<html:optionsCollection name="baseRateList" label="id" value="id" /> 
		            			</logic:present>
		          			</html:select>
		          		</logic:equal>
		          		<logic:equal name="subList" property="interestRateTypeHid" value="E">
							<html:select property="baseRateType" styleClass="text5" styleId="baseRateType"
								value="${rePricingList[0].baseRateType}" onchange="return getBaseRate();">
								<html:option value="">--Select--</html:option>
		             			<logic:present name="baseRateList">
		               				<html:optionsCollection name="baseRateList" label="id" value="id" /> 
		            			</logic:present>
		          			</html:select>
		          		</logic:equal>
		          		</logic:iterate>
					</td>
					<td>
						<html:text property="baseRate" styleId="baseRate" value='${subloanList.baseRate}' styleClass="text4" readonly="true" tabindex="-1" style="text-align:right;"/>
					</td>
					<td>
					<html:text property="markup" styleId="markup" value='${subloanList.markup}' styleClass="text4" readonly="true" maxlength="3" onkeypress="return sevenDecimalnumbersonly(event, id, 3)" 
							onblur="sevenDecimalNumber(this.value, id);calculateFinalRate();" 
							onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" style="text-align:right;"/>
					</td>
					<td>
					<html:text property="finalRate" styleId="finalRate" value='${subloanList.finalRate}' styleClass="text4" 
					onblur="calculateFinalRate();" 
					 tabindex="-1" style="text-align:right;"/>
					</td>
					<td>
						<html:select property="installmentFrequency" styleId="installmentFrequency"
							styleClass="text5" value="${rePricingList[0].installmentFrequency}"
							onchange="calculateInstallmentNoInRepricing()">
							<html:option value="M">Monthly</html:option>
							<html:option value="B">Bimonthly</html:option>
							<html:option value="Q">Quarterly</html:option>
							<html:option value="H">Half Yearly</html:option>
							<html:option value="Y">Yearly</html:option>
						</html:select>
					</td>
					<td>
						<html:select property="installmentType" styleId="installmentType"
							styleClass="text5" value="${rePricingList[0].installmentTypeHid}">
							<html:option value="E">Eq. Installment</html:option>
							<html:option value="G">Gr. Installment</html:option>
							<html:option value="P">Eq. Principal</html:option>
							<html:option value="Q">Gr. Principal1</html:option>
							<html:option value="S">SEPARATE PRINCIPAL & INTEREST</html:option>
						</html:select> 
					</td>
					<td>
					<html:text property="tenure" styleId="tenure" value='${subloanList.tenure}' styleClass="text4" onchange="calculateInstallmentNoInRepricing();calculateMaturityDateInRepricing();" maxlength="3" style="text-align:right;"/>
					</td>
					<td>
					<html:text property="noOfInstl" styleId="noOfInstl" value='${subloanList.noOfInstl}' styleClass="text4" maxlength="3" readonly="true" tabindex="-1" style="text-align:right;"/>
					</td>
					<td>
					<html:text property="maturityDate" styleId="maturityDate" value='${subloanList.maturityDate}' styleClass="text3" readonly="true" tabindex="-1" />
					</td>
				</tr>
			</table>
	</logic:iterate>
    </logic:notEmpty>    
    
    <logic:empty name="rePricingList" >
       <bean:message key="msg.PendingRepricingApprovals"/>
    </logic:empty>
</logic:present>


<logic:present name="feasibility">
<% if(request.getAttribute("feasibility").toString().equalsIgnoreCase("repricingAllowedYear"))
	{ %>
	<bean:message key="msg.repricingAllowedYear"/>
<%	} %>
<% if(request.getAttribute("feasibility").toString().equalsIgnoreCase("minGapSuccRepricingValid"))
	{ %>
	<bean:message key="msg.minGapSuccRepricingValid"/>
<%	} %>
<% if(request.getAttribute("feasibility").toString().equalsIgnoreCase("totalRepricingAllowedExceeds"))
	{ %>
	<bean:message key="msg.totalRepricingAllowedExceeds"/>
<%	} %>
<% if(request.getAttribute("feasibility").toString().equalsIgnoreCase("lockinPeriod"))
	{ %>
	<bean:message key="msg.lockinPeriod"/>
<%	} %>
</logic:present>