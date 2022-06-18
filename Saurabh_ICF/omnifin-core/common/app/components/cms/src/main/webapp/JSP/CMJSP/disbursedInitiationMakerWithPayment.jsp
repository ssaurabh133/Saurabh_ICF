<%@page import="com.cm.vo.DisbursalMakerVO"%>
<%@page import="java.util.List"%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.login.roleManager.UserObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";

			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			String initiationDate = userobj.getBusinessdate();
	%>


	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	
		
		<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/disbursalJS.js"></script>
		
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<script type="text/javascript">
	function fntab()
{
     if(document.getElementById('modifyRecord').value =='CM')	
     {
    	 document.getElementById('disbursalMakerForm').disbursalAmount.focus();
     }
     else if(document.getElementById('modifyRecord').value =='N')
     {
    	document.getElementById('disbursalMakerForm').loanButton.focus();
     }
    
     return true;
}



	  
	</script>
<!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
<! [endif] -->
</head>
<body  onclick="parent_disable();" onunload="closeAllWindowCallUnloadBody();" onload="payeeNameRefreshValue();enableAnchor();fntab();hideRepayDate();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
	<input type="hidden" name="forwardedLoanId" id="forwardedLoanId" value="${forwardedLoanId}"/>
	<input type="hidden" name="revolvingFlag" id="revolvingFlag" value="${revolvingFlag}"/>
	<input type="hidden" name="recoveryType" id="recoveryType" value="${recoveryType}"/>
	<input type="hidden" name="disbursalFlag" id="disbursalFlag" value="${disbursalFlag}"/>
	<input type="hidden" name="repaymentType" id="repaymentType" value="${repaymentType}"/>
	<input type="hidden" name="balancePrinc" id="balancePrinc" value="${balancePrinc}"/>
	<input type="hidden" name="forwardedAmt" id="forwardedAmt" value="${forwardedAmt}"/>
	<input type="hidden" name="totalReceivableCustomer" id="totalReceivableCustomer" value="${disbursalData[0].totalReceivableCustomer}"/>
	<input type="hidden" name="amountInProcessLoan" id="amountInProcessLoan" value="${amountInProcessLoan}"/>
	<input type="hidden" name="installmentType" id="installmentType" value="${installmentType}"/>
	<input type="hidden" name="editDueDate" id="editDueDate" value="${editDueDate}"/>
	<input type="hidden" name="totalReceivableCurrShortPay" id="totalReceivableCurrShortPay" value="${sessionScope.totalReceivableCurrShortPay }" />
	<input type="hidden" name="maxDisbursalDate" id="maxDisbursalDate" value="${maxDisbursalDate}" />
	<input type="hidden" id="pdcDepositCount" name="pdcDepositCount" value="${disbursalDataNew[0].pdcDepositCount}" />
	<input type="hidden" id="pdcDepositCount1" name="pdcDepositCount1" value="${disbursalData[0].pdcDepositCount}" />
	<input type="hidden" id="interestCalculationMethod" name="interestCalculationMethod" value="${interestCalculationMethod}" />
	<input type="hidden" id="interestFrequency" name="interestFrequency" value="${interestFrequency}" />
	<input type="hidden" id="interestCompoundingFrequency" name="interestCompoundingFrequency" value="${interestCompoundingFrequency}" />
	   <!-- Virender Code End --> 
		    	    	<!-- Code added for Edit Due Date| Rahul Papneja| Edit Due Date -->
	<!-- <input type="hidden" id="editDueDate" name="editDueDate" value="${requestScope.editDueDate}" /> -->
	
	<input type="hidden" id="oldRepayEffDate" name="oldRepayEffDate" value="${requestScope.oldRepayEffDate}" />
	<input type="hidden" id="fwdStatusFlag" name="fwdStatusFlag" value="${requestScope.fwdStatusFlag}" />
	<input type="hidden" name="OldMaturityDate1" id="OldMaturityDate1" value="" />	<%-- adde by brijesh pathak --%>
	
	<!-- Ends Here -->

	
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		
<html:form action="/disbursalMaker" method="post"	styleId="disbursalMakerForm">

<input type="hidden" id="saveForwordFlag" name="saveForwordFlag" />
<input type="hidden" id="modifyRecord" name="modifyRecord" />
<input type="hidden" id="bissdate" name="businessdate" value='${userobject.businessdate}'/>
<input type="hidden" id="businessdate" name="businessdate" value='${userobject.businessdate}'/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<fieldset>
<legend><bean:message key="lbl.disbursalDetails" /></legend>
<logic:notPresent name="disbursalDataAuthor">
<!-- New page  code starts here -->
<logic:notPresent name="edit">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	<td>
	<fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	 <tr>
			<td nowrap="nowrap" width="25%">
				<bean:message key="lbl.loanNo" />
				<font color="red">*</font>
			</td>
			<td nowrap="nowrap" width="25%">
				<html:text styleClass="text" property="loanNo"
					styleId="loanNo" value="${disbursalDataNew[0].loanNo}" readonly="true" maxlength="20" />
				<html:button property="loanButton" styleId="loanButton" value=" "
					onclick="openLOVCommon(53,'disbursalMakerForm','loanNo','','', '','','generateValuesDisbursalWithPayment','hid');closeLovCallonLov1();" 
					styleClass="lovbutton"> </html:button>
				<input type="hidden" name="contextPath"
					value="<%=request.getContextPath()%>" id="contextPath" />
				<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
					value="${disbursalDataNew[0].lbxLoanNoHID}"/>
				<html:hidden property="hid" styleId="hid" value="" />
				<html:hidden property="disbursalNo" styleId="disbursalNo"
										value="${disbursalDataNew[0].disbursalNo}"/>
				<html:hidden property="disbursedAmountTemp" styleId="disbursedAmountTemp"
										value="${disbursalDataNew[0].disbursedAmountTemp}"/>
			</td>
			<td nowrap="nowrap" width="25%">
				<bean:message key="lbl.customerName" />
			</td>
			<td nowrap="nowrap" width="25%">
				<html:text styleClass="text" property="customerName"
					styleId="customerName" maxlength="50" readonly="true"
					value="${disbursalDataNew[0].customerName}" tabindex="-1" />
			</td>
	</tr>
	<tr>
											
			<td nowrap="nowrap">
				<bean:message key="lbl.product" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="product"
					styleId="product" value="${disbursalDataNew[0].product}" readonly="true" maxlength="50"
					tabindex="-1" />
			</td>
														
			<td nowrap="nowrap">
				<bean:message key="lbl.scheme" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="scheme"
					styleId="scheme" maxlength="50" value="${disbursalDataNew[0].scheme}" readonly="true"
					tabindex="-1" />
			</td>
											
	</tr>
		<tr>
				<td nowrap="nowrap">
					<bean:message key="lbl.loanAmt" />
				</td>
				<td nowrap="nowrap">
					<html:text styleClass="text" property="loanAmt"
						styleId="loanAmt" value="${disbursalDataNew[0].loanAmt}" readonly="true" maxlength="18"
						tabindex="-1" style="text-align:right;"/>
				</td>
					<td nowrap="nowrap">
					<bean:message key="lbl.disbursedAmount" />
				</td>
				<td nowrap="nowrap">
					<html:text styleClass="text" property="disbursedAmount"
						styleId="disbursedAmount" maxlength="18" value="${disbursalDataNew[0].disbursedAmount}"
						readonly="true" tabindex="-1" style="text-align:right;"/>
				</td>

	</tr>
	<tr>
			<td nowrap="nowrap">
				<bean:message key="lbl.loanApprovalDate" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="loanApprovalDate"
					styleId="loanApprovalDate" value="${disbursalDataNew[0].loanApprovalDate}" readonly="true"
					tabindex="-1" />
			</td>
											
			<td nowrap="nowrap">
				<bean:message key="lbl.disbursalDate" />
				<font color="red">*</font>
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="disbursalDate" 
					styleId="disbursalDate" maxlength="10" 
					onchange="checkDate('disbursalDate');" value="<%=initiationDate %>" />
			</td>
			
											
	</tr>
	<tr>
					<td nowrap="nowrap">
						<bean:message key="lbl.makerRemark" />

					</td>
					<td nowrap="nowrap">
						<html:textarea property="disbursalDescription"
							styleId="disbursalDescription" value="${disbursalDataNew[0].disbursalDescription}" styleClass="text"></html:textarea>
					</td>
					<td nowrap="nowrap">
						<bean:message key="lbl.authorRemarks" />
					</td>
				
					<td nowrap="nowrap">
						<html:textarea styleClass="text" property="authorRemarks"
						styleId="authorRemarks" disabled="true"	value="${disbursalDataNew[0].authorRemarks}" />
					</td>
		</tr>
 </table>
</fieldset>
        </td>
	</tr>
    <tr>
	    <td>
	   
	    <fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
						<td nowrap="nowrap" width="25%">
							<bean:message key="lbl.disbursalTo" />
								<font color="red">*</font>
						</td>
						<td nowrap="nowrap" width="25%">
							 
				              <html:select property="disbursalTo" styleId="disbursalTo" styleClass="text"  onchange="activeSupplierForAddDetails(this.value);getTotalPayableReceivable(this.value);" >
				              	<html:option value="${disbursalDataNew[0].disbursalTo}">Select</html:option>
				              	<html:option value="CS">Customer</html:option>
				              	<html:option value="SU">Dealer</html:option>
				              	<html:option value="MF">Manufacturer</html:option>
				              </html:select>
						</td>
						<td nowrap="nowrap" width="25%">
						<div id="supplierLableDiv" style="display:none">
							<bean:message key="lbl.sDealer"/><font color="red">*</font>
						</div>
						<div id="manufactLableDiv" style="display:none">
							<bean:message key="lbl.manufact"/><font color="red">*</font>
						</div>
						<div id="customerLableDiv" style="display:block">
							<bean:message key="lbl.CustomerName"/><font color="red">*</font>
						</div>			
						<div id="otherLableDiv" style="display:none">
							<bean:message key="lbl.other"/><font color="red">*</font>
						</div>	
						</td>
						<td nowrap="nowrap"  width="25%">
							 <div style="width: 100%">

								
								<div id="supplierDiv" style="display:none" >
								<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
								<html:text styleClass="text" property="businessPartnerTypeDesc" readonly="true" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${disbursalDataNew[0].businessPartnerTypeDesc}"/>
								<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
              					<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
              					<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(375,'disbursalMakerForm','businessPartnerName','lbxLoanNoHID|disbursalTo', 'lbxBusinessPartnearHID','lbxLoanNoHID|disbursalTo','Select Loan Account LOV First','clearTALoanLOV','businessPartnerTypeDesc','bankAcountName');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
         						<html:hidden property="bankAcountName" styleId="bankAcountName"  value="" styleClass="text" />
							    </div >
							    <div id="custDiv">
							    <html:text styleClass="text" maxlength="50" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDescCust" style="" readonly="true"  value="${disbursalDataNew[0].customerName}"/>
							    </div>
							<html:hidden  property="loanNo" styleId="loanNo" value="${disbursalDataNew[0].loanNo}" />
							<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${disbursalDataNew[0].lbxBusinessPartnearHID}" />
							<html:hidden  property="loanDisbursalId" styleId="loanDisbursalId" value="${requestScope.loanDisbursalId}" />
							<html:hidden  property="disbursalNo" styleId="disbursalNo" value="${requestScope.disbursalNo}" />
							<html:hidden  property="customerName" styleId="customerName" value="${requestScope.customerName}" />
						</div>
						</td>
					</tr>

	                 <tr>
						<td nowrap="nowrap">
							<bean:message key="lbl.disAmount" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="disbursalAmount"
								styleId="disbursalAmount"  value="${disbursalDataNew[0].disbursalAmount}"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" onchange="calculateNetAmount()"/>
								<input type="hidden" name="disbursedAmount" id="" value="${requestScope.disbursalAmount}" />
						</td>
						<td nowrap="nowrap">
							<bean:message key="lbl.netAmount" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="netAmount"
								styleId="netAmount" value="${disbursalDataNew[0].netAmount}"  readonly="true" tabindex="-1"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
						</td>
					 </tr>
					 <tr>
					 <td nowrap="nowrap">
							<bean:message key="lbl.totalPayable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="totalPayable"
								styleId="totalPayable" value="${requestScope.totalPayable}"  readonly="true" tabindex="-1"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
								<input type="hidden" value="${requestScope.totalPayable}" name="csTotalPayable" id="csTotalPayable"/>
								<input type="hidden" value="${requestScope.totalReceivable}" name="cstotalReceivable}" id="cstotalReceivable}"/>
						</td><td nowrap="nowrap">
							<bean:message key="lbl.adjustTotalPayable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="adjustTotalPayable"
								styleId="adjustTotalPayable" value="0.0" onchange="calculateNetAmount()"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;"  />
						</td>
					 </tr>
					  <tr>
					  
					  <td nowrap="nowrap">
							<bean:message key="lbl.totalReceivable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="totalReceivable"
								styleId="totalReceivable" value="${requestScope.totalReceivable}"  readonly="true" tabindex="-1"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
						</td><td nowrap="nowrap">
							<bean:message key="lbl.adjustTotalReceivable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="adjustTotalReceivable"
								styleId="adjustTotalReceivable" value="0.0" onchange="calculateNetAmount()" 
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
						</td>
				</tr>
				 <tr>
										
					<td nowrap="nowrap"><bean:message key="lbl.currentMonthEMI"/>
					<input type="hidden" id="EMIFlag" value="F"/>
					</td>
					<td nowrap="nowrap">
						<html:text property="currentMonthEMI" styleClass="text"  value="${disbursalData[0].currentMonthEMI}" styleId="currentMonthEMI" readonly="true" style="text-align:right;"/>
					</td>
					<%-- <td nowrap="nowrap"><bean:message key="lbl.nextMonthEMI"/></td> --%>										
					<td nowrap="nowrap">
						<html:hidden property="preEMINextMonth" styleClass="text"  value="${disbursalData[0].preEMINextMonth}"  styleId="preEMINextMonth" style="text-align:right;"/>
					</td>
				</tr>
				</table>
			</fieldset>
			</td>
		</tr>
		<tr>
	       <td>
	      
	       <fieldset>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
			
			<tr>
			<td nowrap="nowrap" width="25%">
				 <bean:message key="lbl.StartEMI" />
			</td>
			<td nowrap="nowrap" width="25%">
			
				<div id="finDisb">
					<input type="checkbox" name="finalDisbursal" id="finalDisbursal" onclick="showEffectiveDate();" />
					<html:hidden property="repayMode" styleId="repayMode" styleClass="text" value="${disbursalDataNew[0].repayMode}"/>
					
		
				</div>
				
				<input type="hidden" id="tenureMonths" name="tenureMonths" value="${disbursalData[0].loanTenure}" />
			</td>
			<td width="25%">
				<bean:message key="lbl.repayEffDate" />
			</td>
			<td width="25%">
				<div id="repayEffId" style="display: none;">
					<html:text styleClass="text" property="repayEffDate"
						styleId="repayEffDate" maxlength="10" value="<%=initiationDate %>"
						onchange="checkDate('repayEffDate');"
						tabindex="-1"/>
				</div>
				<div id="repayId">
					<html:text styleClass="text" property="repayEffDate"
						styleId="repayEff" value="${disbursalDataNew[0].repayEffDate}" disabled="true" maxlength="10" 
						tabindex="-1"/>
				</div>
				<div id="repayIdBusDate" style="display: none;">
					<html:text styleClass="text" property="repayEffDate" 
						styleId="repayEffBusDate" value="${disbursalDataNew[0].repayEffDate}"  maxlength="10" onchange="checkDate('repayEffBusDate');"/>
				</div>
			</td>
	</tr>
	<tr>
											
				<td nowrap="nowrap">
					<bean:message key="lbl.cycleDate" />
				</td>
				<td nowrap="nowrap">
					
				<div id="no3">
      									<html:select property="cycleDate" styleId="cycleDate" styleClass="text" 
      										disabled="true"   onchange="nullNextDue(this.value);"  >
							<html:option value="${disbursalDataNew[0].cycleDate}">--<bean:message key="lbl.select" />--</html:option>
							</html:select>
					</div>
					<div id="yes3" style="display: none;">
      									<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataNew[0].cycleDate}"/>
							<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataNew[0].cycleDate}" styleClass="text"  onchange="nullNextDue(this.value);" >
									    <html:option value="${disbursalDataNew[0].cycleDate}">--<bean:message key="lbl.select" />--</html:option>
									    <html:optionsCollection name="cycleDate" label="cycleDateDesc" value="cycleDateValue" />
							</html:select>
					</div>
				</td>
				<td>
					<input type="hidden" id="val" value='N'/>
				     <div id="no1">
				     	<bean:message key="lbl.nextDueDate" />
				     </div>
				     <div id="yes1" style="display: none">
				     	<bean:message key="lbl.nextDueDate" /><font color="red">*</font>
				     </div>
				</td>
			    <td>
			    	<div id="no2">
			    		<html:text property="nextDueDateNo" styleClass="text" value="" tabindex="-1"  disabled="true" />
			    	</div>
			    	<div id="yes2" style="display: none">
			    		<html:text property="nextDueDate" styleClass="text"  value="${disbursalData[0].nextDueDate}"
				          styleId="nextDueDate" onchange="checkDueDate(value);checkRepayEffectiveDate(value);calTenureMonthForMaturityDateNextDueDateDIS();" />
				    </div>										    	
			    </td>
				
				</tr>
								
	<!-- changes by ajay  -->
					<tr>			
								<td>
				
				     <div id="Ino1">
				     	<bean:message key="lbl.interestDueDate" />
				     </div>
				     <div id="Iyes1" style="display: none">
				     	<bean:message key="lbl.interestDueDate" />
				     </div>
				</td>
				
			    <td>
			    	<div id="Ino2">
			    		<html:text property="interestDueDateNo" styleClass="text" value="" tabindex="-1"  disabled="true" />
			    	</div>
			    	<div id="Iyes2" style="display: none">
			    	<input type="hidden" name="hiddenIntDueDate" id="hiddenIntDueDate" value="${disbursalData[0].interestDueDate}"    />
			    		<html:text property="interestDueDate" styleClass="text"  value="${disbursalData[0].interestDueDate}"
				          styleId="interestDueDateDis"  onchange="datevalidate();validateInterestDueDate();" />
				    </div>										    	
			    </td>	
<!-- changes by ajay end  -->
			<td nowrap="nowrap"><bean:message key="lbl.penalIntCalcDate" /></td>
			<td>
			<div id="penal1" >
			<html:text styleClass="text" property="penalIntCalcDate"
						styleId="penalIntCalcDate" maxlength="10" value="<%=initiationDate %>"
						onchange="checkDate('penalIntCalcDate');" />
			</div> 
			 <div id="penal2" style="display: none">
			<html:text styleClass="text" property="penalIntCalcDate" value="${disbursalDataNew[0].penalIntCalcDate}"
						 maxlength="10" onchange="checkDate('penalIntCalcDate');" disabled="true"/> 
			</div> 
			</td>
			</tr>
			<tr>
			<td nowrap="nowrap">
				<bean:message key="lbl.paymentFlag" />
			</td>
			<td>
			<html:select styleId="paymentFlag" property="paymentFlag" value="${disbursalDataNew[0].paymentFlag}" styleClass="text" onchange="displayHideTaPayment('paymentFlag')" >
			<html:option value="N">NO</html:option>
			<html:option value="P">Payment</html:option>
			<html:option value="T">TA Adjustment</html:option>
			</html:select>      
             </td>
             <!-- Start here Brijesh Pathak -->
				<%-- <logic:equal name="installmentType" value="S">
				<logic:equal name="editDueDate" value="Y">

					<td nowrap="nowrap"><bean:message key="lbl.maturityDate" /></td>
					<td>
					<div id="loanTenure" >  --%>
					
					<td nowrap="nowrap">
					<div id="MaturityView" style="display: none">
					<bean:message key="lbl.maturityDate" />
					</div>
					</td>
					<td>
					 <div id="MaturityView1" style="display: none">
					<html:text styleClass="text" property="maturityDate1" onblur=""
						styleId="maturityDate1" maxlength="10" value=""
						onchange="calTenureMonthForMaturityDateDIS2();" />
			
					</div>
					</td>
			

					<!-- End here Brijesh Pathak -->
					</tr>
	<!-- work started by amandeep -->
	<tr>
	<td nowrap="nowrap" width="25%">
				<bean:message key="lbl.loanCurtailment" />
			</td>
			<td nowrap="nowrap" width="25%">
				
					<input type="checkbox" name="loanCurtail" id="loanCurtail"  disabled="disabled"/>
					
				
			</td>
													
			</tr>
			<!-- work end by amandeep -->
	</table>
	</fieldset>
	</td>
	</tr>
	<tr>
	<td>
	<fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	   <tr id="taLoanNoTrDiv"  >
				<td nowrap="nowrap" width="25%">
					<bean:message key="lbl.taLoanNo" />
					
				</td>
				<td nowrap="nowrap" width="25%">
					<html:text styleClass="text" property="taLoanNo" styleId="taLoanNo" value="${disbursalDataNew[0].taLoanNo}" readonly="true" maxlength="20" />
					
					<html:button property="taloanButton" disabled="true" styleId="taloanButton" value=" " onclick="setSupplierManufacturerIdInSession();openLOVCommon(452,'disbursalMakerForm','taLoanNo','lbxBusinessPartnearHID','lbxTaLoanNoHID', 'businessPartnerTypeDesc','Select Supplier Manufacturer First','validateTABalance','taCustomerName','balAmount','LOAN_CLASSIFICATION');" styleClass="lovbutton"> </html:button>
					<input type="hidden" name="contextPath"	value="<%=request.getContextPath()%>" id="contextPath" />
					<html:hidden property="lbxTaLoanNoHID" styleId="lbxTaLoanNoHID" value="${disbursalDataNew[0].lbxTaLoanNoHID}" />
					<html:hidden property="hid" styleId="hid" value="" />
					<html:hidden property="balAmount" styleId="balAmount" value="" />
					<html:hidden property="LOAN_CLASSIFICATION" styleId="LOAN_CLASSIFICATION" value="" />
				</td>
				<td nowrap="nowrap" width="25%">
					<bean:message key="lbl.taCustomerName" />
				</td>
				<td nowrap="nowrap" width="25%">
					<html:text styleClass="text" property="taCustomerName"
						styleId="taCustomerName" maxlength="50" readonly="true"
						value="${disbursalDataNew[0].taCustomerName}" tabindex="-1" />
				</td>
				
			</tr>
	</table>
	</fieldset>
	</td>
	</tr>
		<tr>
	<td>
	<fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">	
	
		<tr>
		     <td><bean:message key="lbl.payTo"></bean:message></td>
	        <td>
	         <html:text property="payTo" styleId="payTo" readonly="true" styleClass="text" value="${disbursalDataNew[0].payTo}"	tabindex="-1"/>
	         <input type="hidden"  name="lbxpayTo" id="lbxpayTo" value="${disbursalDataNew[0].lbxpayTo}"/>
             <html:button property="payToButton"  styleId="payToButton" onclick="openLOVCommon(262,'disbursalMakerForm','payTo','lbxLoanNoHID','lbxpayTo', 'lbxLoanNoHID','Select Loan Number','payeeNameRefresh','hid');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
	        </td>
	        <td><bean:message key="lbl.payeeName"></bean:message></td>
	        <td>
	         <a rel="tooltip3" id="tool">
	                <html:text property="payeeName" styleId="payeeName" readonly="true" styleClass="text" value="${disbursalDataNew[0].payeeName}" onmouseover="applyToolTip(id);" onchange="insertValue();" tabindex="-1"/>
	                
	         </a>
	        	<input type="hidden"  name="lbxpayeeName" id="lbxpayeeName" value="${disbursalDataNew[0].lbxpayeeName}"/>
                <html:button property="payeeNameButton"  disabled="true"  styleId="payeeNameButton"  onclick="openLOVCommon(263,'disbursalMakerForm','payeeName','payTo|lbxLoanNoHID','lbxpayeeName', 'lbxpayTo|lbxLoanNoHID','Select Pay To|Select Loan Number','','hid');closeLovCallonLov('payTo');" value=" " styleClass="lovbutton"></html:button>	
	             <html:button property="payeeNameButton"    styleId="payeeNameButtonRSP" style="display: none" onclick="openLOVCommon(655,'disbursalMakerForm','payeeName','payTo|lbxLoanNoHID','lbxpayeeName', 'lbxpayTo|lbxLoanNoHID','Select Pay To|Select Loan Number','','hid');closeLovCallonLov('payTo');" value=" " styleClass="lovbutton"></html:button>	
		   </td>
	    </tr>
	  <tr id="paymenttr1">  
		    <td width="25%"><bean:message key ="lbl.paymentMode"></bean:message><font color="red">*</font> </td>
			 <td width="25%"><span style="float:left;">
			<html:select property="paymentMode" styleId="paymentMode" styleClass="text" value="${disbursalDataNew[0].paymentMode}" disabled="true" onchange="fnNull();getAccountType();">
			 <html:option value="">--Select--</html:option> 
			  <html:option value="C">Cash</html:option> 
			 <html:option value="Q">Cheque</html:option> 
			 <html:option value="D">DD</html:option> 
			 <html:option value="N">NEFT</html:option> 
			 <html:option value="R">RTGS</html:option>	
			 <html:option value="S">ADJUSTMENT</html:option>	 		               
	           </html:select>
	           <input type="hidden"  name="lbxpaymentMode" id="lbxpaymentMode" value=""/> 
			 </span></td>
			
		    <td width="25%"><bean:message key ="lbl.paymentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
			<td width="25%">
				<html:text property="paymentDate" styleClass="text" styleId="paymentDate" value="${disbursalDataNew[0].paymentDate}" maxlength="10" disabled="true" onchange="return checkDate('paymentDate');"></html:text> 
		    </td>
		 	</tr>
		 			
		<tr id="paymenttr2">
			  <td><bean:message key="lbl.instrumentNumber"></bean:message><font color="red">*</font> </td>
			 <td><div style="float:left;">
			 <html:text property="instrumentNumber" styleClass="text" styleId="instrumentNumber" value="${disbursalDataNew[0].instrumentNumber}" disabled="true" maxlength="20"></html:text>
			 </div>
			 <input type="hidden" name="instrumentID" id="instrumentID" value="" />
			</td>
			 <td><bean:message key="lbl.instrumentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font> </td>
	        <td><html:text property="instrumentDate" styleClass="text" styleId="instrumentDate" value="" maxlength="10" disabled="true" onchange="return checkDate('instrumentDate');"></html:text>
	           </td>				
		</tr>
		<tr id="paymenttr3">			 
		   <td><bean:message key="lbl.bankAccount"></bean:message><font color="red">*</font></td>
			 <td><div style="float:left;"> 
	          <html:text styleClass="text" property="bankAccount" styleId="bankAccount" maxlength="50"  value="" readonly="true" tabindex="-1"/>
              <input type="hidden"  name="lbxbankAccountID" id="lbxbankAccountID"/>
			     <html:button property="bankAccountButton" styleId="bankAccountButton" onclick="openLOVCommon(64,'disbursalMakerForm','bankAccount','paymentMode','lbxbankAccountID', 'lbxpaymentMode','Select Payment Mode','getChequeDetail','hid','hid1');" value=" " disabled="true" styleClass="lovbutton"></html:button>
			    <input type="hidden" name="hid1" id="hid1"/> 
			    <input type="hidden" name="hid" id="hid"/> 
				</div></td>
		    <td><bean:message key="lbl.bank"></bean:message></td>
			<td><div style="float:left;"> 
			 <html:text styleClass="text" property="bank" styleId="bank" maxlength="50"  value="${disbursalDataNew[0].bank}" readonly="true" tabindex="-1"/>
		    <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${disbursalDataNew[0].lbxBankID}"/> 
			</div></td>
		</tr> 
		<tr id="paymenttr4">	
			<td><bean:message key="lbl.branch"></bean:message></td>
				<td>
				<html:text styleClass="text" property="branch" styleId="branch" maxlength="50"  value="${disbursalDataNew[0].branch}" readonly="true" tabindex="-1"/>
		         <html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${disbursalDataNew[0].lbxBranchID}"/>
		   		</td>	 
				<td><bean:message key="lbl.micr"></bean:message></td>
				<td>
				<html:text property="micr" styleClass="text" styleId="micr" value="${disbursalDataNew[0].micr}" maxlength="20" readonly="true" tabindex="-1"></html:text>
				</td>
		   
	   </tr>
	    <tr id="paymenttr5">
	         <td> <bean:message key="lbl.ifsCode"></bean:message></td>
			 <td><div style="float:left;">
			 <html:text property="ifsCode" styleId="ifsCode" styleClass="text" value="${disbursalDataNew[0].ifsCode}" maxlength="20" readonly="true" tabindex="-1"></html:text>		
	         </div></td>
	         <td><bean:message key="lbl.paymentAmount"></bean:message><font color="red">*</font> </td>
			 <td>
			 <html:text property="paymentAmount" styleClass="text" styleId="paymentAmount" value="${disbursalDataNew[0].paymentAmount}" disabled="true"
			 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" disabled="disabled" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" ></html:text>
			
			 </td>
	    </tr>
	    
	       <!-- Virender Code Start -->
			<tr>
				<td>
					<bean:message key="lbl.beneficiary_bank" />
				</td>
				<td>
					<html:text property="beneficiaryBankCode" styleId="beneficiaryBankCode" readonly="true" styleClass="text" value="${disbursalDataNew[0].beneficiaryBankCode}" tabindex="-1" />
					 <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();
					 openLOVCommon('19243','disbursalMakerForm','beneficiaryBankCode','','beneficiaryBankBranchName', 'beneficiaryLbxBranchID','','','beneficiaryLbxBankID');" value=" " styleClass="lovbutton"> </html:button>
					<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
					<html:hidden property="beneficiaryLbxBankID" styleId="beneficiaryLbxBankID" value="${disbursalDataNew[0].beneficiaryLbxBankID}"/>
				</td>
		
				<td>
					<bean:message key="lbl.beneficiaryBankBranchName" />									
				</td>
				
				<td>
					<html:text property="beneficiaryBankBranchName" styleId="beneficiaryBankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${disbursalDataNew[0].beneficiaryBankBranchName}" />
                    <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="
                    openLOVCommon(19244,'disbursalMakerForm','beneficiaryBankBranchName','beneficiaryBankCode','beneficiaryLbxBranchID', 'beneficiaryLbxBankID','Please Select Bank','','micrCode','beneficiaryIfscCode','beneficiaryLbxBranchID');closeLovCallonLov('beneficiaryBankCode');" value=" " styleClass="lovbutton"> </html:button>
			    	<html:hidden property="beneficiaryLbxBranchID" styleId="beneficiaryLbxBranchID" value="${disbursalDataNew[0].beneficiaryLbxBranchID}"  />
			    	<input type="hidden" name="micrCode" value="" id="micrCode" />
			  </td>
				
			</tr>
			
			<tr>
				<td>
					<bean:message key="lbl.beneficiaryAccountNo"  />								
				</td>
				<td>
					<html:text property="beneficiaryAccountNo" styleId="beneficiaryAccountNo" tabindex="-1" onblur="fnChangeCase('CustBankDetailsForm','accountNo');this.value=removeSpaces1(this.value);" styleClass="text"  value="${disbursalDataNew[0].beneficiaryAccountNo}"  />
				</td>	
		
				<td>
					<bean:message key="lbl.beneficiaryIfscCode" />
				</td>
				<td>
					<html:text property="beneficiaryIfscCode" styleId="beneficiaryIfscCode" styleClass="text" readonly="true" maxlength="20" value="${disbursalDataNew[0].beneficiaryIfscCode}"  />
				</td>	
			</tr>
		 
	     <tr id="paymenttr6">
		      <td><bean:message key="lbl.tdsAmount"></bean:message></td>
	        <td> <html:text property="tdsAmount" styleId="tdsAmount" styleClass="text" value="${disbursalDataNew[0].tdsAmount}"  disabled="true"
	         onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" ></html:text>		
	        </td>
	         <td><bean:message key="lbl.remark"></bean:message></td>
	         <td>
	         <textarea name="remarks" id="remarks" disabled="disabled" class="text"></textarea>      
	        </td>
	   
	     </tr>
	</table>
	</fieldset>
	</td>
	</tr>
	<tr><td>
   <!--  	<button type="button" name="EMI" id="EMI" 
		class="topformbutton4" title="Alt+E" accesskey="E"
		onclick="return calculateEMI()"><bean:message key="button.calculatePreEMI" /></button>
		-->
		
	<logic:notPresent name="disAuthor">
		<button type="button" name="save" id="save"
		class="topformbutton2" title="Alt+V" accesskey="V"
		onclick="return saveDisbursalDataWithPayment(document.getElementById('lbxLoanNoHID').value);"><bean:message key="button.save" /></button>
	
	<button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"
		 class="topformbutton2"
		onclick="return forwordDisbursal();"><bean:message key="button.forward" /></button>
		
	<button type="button" class="topformbutton4" title="Alt+R" accesskey="R"
		onclick="return viewReceivableDisbursal('<bean:message key="msg.LoanAccountNo" />');" ><span class='underLine'>R</span>eceivable/Payable</button>
	
	<button type="button" class="topformbutton4" title="Alt+I" accesskey="I"
		onclick="return openOldInstallmentPlan('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.oldInstallment" /></button>									
	
	<button type="button" class="topformbutton4" title="Alt+N" accesskey="N"
		onclick="return openNewInstallmentPlan('<bean:message key="msg.LoanAccountNo" />','DIM');" ><bean:message key="button.newInstallment" /></button>
	
	<button type="button" class="topformbutton4" title="Alt+C" accesskey="C"
		onclick="return openOtherChargePlanAtDisbrsal('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.otherChargesPlan" /></button>
				
		<button type="button" name="oldDisbursal" id="oldDisbursal"
		class="topformbutton4" title="Alt+O" accesskey="O"
		onclick="return openDisbursalSchedule('<bean:message key="msg.LoanAccountNo" />');"><bean:message key="button.olddisbursal" /></button>
		
	<button type="button" name="specialCondButton" id="specialCondButton"
		class="topformbutton4" title="Alt+S" accesskey="S"
		onclick="return viewSpecialCondButton('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.specialCondition" /></button>
	
	<button type="button" name="generateRepaymentSchOld" id="generateRepaymentSchOld" title="Alt+R" accesskey="R"
		class="topformbutton4" onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.oldrepay" /></button>
		
	<button type="button" name="generateRepaymentSch"
		id="generateRepaymentSch" 
		class="topformbutton4" 
          onclick="return callRepayBeforeSave('<bean:message key="msg.saveBeforeRepay" />');"
		disabled="disabled" ><bean:message key="button.newrepay" /></button>
	</logic:notPresent> 

   </td>
	</tr>
	
	</table>
</logic:notPresent>
<!-- New page  code ends here -->
<!-- Editable part   code Starts  here -->
<logic:present name="edit">
<logic:present name="disbursalData">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<logic:iterate id="disbursalDataNewList" name="disbursalData">
	<tr>
	<td>
	<fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	 <tr>
			<td nowrap="nowrap" width="25%">
				<bean:message key="lbl.loanNo" />
				<font color="red">*</font>
			</td>
			<td nowrap="nowrap" width="25%">
				<html:text styleClass="text" property="loanNo"
					styleId="loanNo" value="${disbursalData[0].loanNo}" readonly="true" maxlength="20" />
				<input type="hidden" name="contextPath"
					value="<%=request.getContextPath()%>" id="contextPath" />
				<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
					value="${disbursalData[0].lbxLoanNoHID}" />
				<html:hidden property="hid" styleId="hid" value="" />
				<html:hidden property="disbursalNo" styleId="disbursalNo"
										value="${disbursalData[0].disbursalNo}" />
				<html:hidden property="loanDisbursalId" styleId="loanDisbursalId"
				                            value="${disbursalData[0].loanDisbursalId}"/>
				<html:hidden property="disbursedAmountTemp" styleId="disbursedAmountTemp"
										value="${disbursalData[0].disbursedAmountTemp}"/>
			</td>
			<td nowrap="nowrap" width="25%">
				<bean:message key="lbl.customerName" />
			</td>
			<td nowrap="nowrap" width="25%">
				<html:text styleClass="text" property="customerName"
					styleId="customerName" maxlength="50" readonly="true"
					value="${disbursalData[0].customerName}" tabindex="-1" />
			</td>
	</tr>
	<tr>
											
			<td nowrap="nowrap">
				<bean:message key="lbl.product" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="product"
					styleId="product" value="${disbursalData[0].product}"  readonly="true" maxlength="50"
					tabindex="-1" />
			</td>
														
			<td nowrap="nowrap">
				<bean:message key="lbl.scheme" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="scheme"
					styleId="scheme" maxlength="50" value="${disbursalData[0].scheme}"  readonly="true"
					tabindex="-1" />
			</td>
											
	</tr>
		<tr>
				<td nowrap="nowrap">
					<bean:message key="lbl.loanAmt" />
				</td>
				<td nowrap="nowrap">
					<html:text styleClass="text" property="loanAmt" value="${disbursalData[0].loanAmt}"
						styleId="loanAmt"  readonly="true" maxlength="18"
						tabindex="-1" style="text-align:right;"/>
				</td>
					<td nowrap="nowrap">
					<bean:message key="lbl.disbursedAmount" />
				</td>
				<td nowrap="nowrap">
					<html:text styleClass="text" property="disbursedAmount"
						styleId="disbursedAmount" maxlength="18" value="${disbursalData[0].disbursedAmount}"
						readonly="true" tabindex="-1" style="text-align:right;"/>
				</td>

	</tr>
	<tr>
			<td nowrap="nowrap">
				<bean:message key="lbl.loanApprovalDate" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="loanApprovalDate"
					styleId="loanApprovalDate" value="${disbursalData[0].loanApprovalDate}" readonly="true"
					tabindex="-1" />
			</td>
											
			<td nowrap="nowrap">
				<bean:message key="lbl.disbursalDate" />
				<font color="red">*</font>
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="disbursalDate" 
					styleId="disbursalDate" maxlength="10" 
					onchange="checkDate('disbursalDate');" value="${disbursalData[0].disbursalDate}" />
			</td>
											
	</tr>
	<tr>
					<td nowrap="nowrap">
						<bean:message key="lbl.makerRemark" />

					</td>
					<td nowrap="nowrap">
						<html:textarea property="disbursalDescription"
							styleId="disbursalDescription" value="${disbursalData[0].disbursalDescription}" styleClass="text"></html:textarea>
					</td>
					<td nowrap="nowrap">
						<bean:message key="lbl.authorRemarks" />
					</td>
				
					<td nowrap="nowrap">
						<html:textarea styleClass="text" property="authorRemarks"
						styleId="authorRemarks" disabled="true"	value="${disbursalData[0].authorRemarks}" />
					</td>
		</tr>
 </table>
</fieldset>
        </td>
	</tr>
<tr>
	    <td>
	   
	    <fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
						<td nowrap="nowrap" width="25%">
							<bean:message key="lbl.disbursalTo" />
								<font color="red">*</font>
						</td>
						<td nowrap="nowrap" width="25%">
							 
				              <html:select property="disbursalTo" styleId="disbursalTo" styleClass="text" value="${disbursalData[0].disbursalTo}" onchange="activeSupplierForAddDetails(this.value);getTotalPayableReceivable(this.value);" >
				              	<html:option value="">Select</html:option>
				              	<html:option value="CS">Customer</html:option>
				              	<html:option value="SU">Dealer</html:option>
				              	<html:option value="MF">Manufacturer</html:option>
				              </html:select>
						</td>
						<td nowrap="nowrap" width="25%">
						<div id="supplierLableDiv" style="display:none">
							<bean:message key="lbl.sDealer"/><font color="red">*</font>
						</div>
						<div id="manufactLableDiv" style="display:none">
							<bean:message key="lbl.manufact"/><font color="red">*</font>
						</div>
						<div id="customerLableDiv" style="display:block">
							<bean:message key="lbl.CustomerName"/><font color="red">*</font>
						</div>			
						<div id="otherLableDiv" style="display:none">
							<bean:message key="lbl.other"/><font color="red">*</font>
						</div>	
						</td>
						<td nowrap="nowrap"  width="25%">
							 <div style="width: 100%">

			<logic:equal name="disbursalDataNewList" property="disbursalTo" value="CS">
			<div id="supplierDiv" style="display:none" >
			<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
			<html:text styleClass="text" property="businessPartnerTypeDesc" readonly="true" styleId="businessPartnerTypeDesc" style=""  value="${disbursalData[0].businessPartnerTypeDesc}"/>
			<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
		 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(375,'disbursalMakerForm','businessPartnerName','lbxLoanNoHID|disbursalTo', 'lbxBusinessPartnearHID','lbxLoanNoHID|disbursalTo','Select Loan Account LOV First','clearTALoanLOV','businessPartnerTypeDesc','bankAcountName');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
         	
		   <html:hidden property="bankAcountName" styleId="bankAcountName"  value="" styleClass="text" />
		 </div >
		 <div id="custDiv">
		<html:text styleClass="text" maxlength="50" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDescCust" style="" readonly="true" disabled="true" value="${disbursalData[0].customerName}" />
		</div>
			</logic:equal>
		<logic:notEqual name="disbursalDataNewList" property="disbursalTo" value="CS">
		<div id="supplierDiv"  >
		<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
		<html:text styleClass="text" property="businessPartnerTypeDesc" readonly="true" styleId="businessPartnerTypeDesc" style="" value="${disbursalData[0].businessPartnerTypeDesc}"/>
		<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
		<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		<html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(375,'disbursalMakerForm','businessPartnerName','lbxLoanNoHID|disbursalTo', 'lbxBusinessPartnearHID','lbxLoanNoHID|disbursalTo','Select Loan Account LOV First','clearTALoanLOV','businessPartnerTypeDesc','bankAcountName');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
        						
		
		<html:hidden property="bankAcountName" styleId="bankAcountName"  value="" styleClass="text" />
		 </div >
		 <div id="custDiv" style="display:none">
		 <html:text styleClass="text" maxlength="50" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDescCust" style="" readonly="true" disabled="true" value="" />
		 </div>
		</logic:notEqual>
							<html:hidden  property="loanNo" styleId="loanNo" value="${requestScope.lbxLoanNoHID}" />
							<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${disbursalData[0].lbxBusinessPartnearHID}" />
						
						</div>
						</td>
					</tr>

	                 <tr>
						<td nowrap="nowrap">
							<bean:message key="lbl.disAmount" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="disbursalAmount"
								styleId="disbursalAmount"   value="${disbursalData[0].disbursalAmount}" 
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" onchange="calculateNetAmount()"/>
							
						</td>
						<td nowrap="nowrap">
							<bean:message key="lbl.netAmount" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="netAmount"
								styleId="netAmount" value="${disbursalData[0].netAmount}" readonly="true" tabindex="-1"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
						</td>
					 </tr>
					 <tr>
					 <td nowrap="nowrap">
							<bean:message key="lbl.totalPayable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="totalPayable"
								styleId="totalPayable"  value="${disbursalData[0].totalPayable}" readonly="true" tabindex="-1"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
								
						</td><td nowrap="nowrap">
							<bean:message key="lbl.adjustTotalPayable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="adjustTotalPayable"
								styleId="adjustTotalPayable" value="${disbursalData[0].adjustTotalPayable}" onchange="calculateNetAmount()"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;"  />
						</td>
					 </tr>
					  <tr>
					  
					  <td nowrap="nowrap">
							<bean:message key="lbl.totalReceivable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="totalReceivable"
								styleId="totalReceivable" value="${disbursalData[0].totalReceivable}"  readonly="true" tabindex="-1"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
						</td><td nowrap="nowrap">
							<bean:message key="lbl.adjustTotalReceivable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="adjustTotalReceivable"
								styleId="adjustTotalReceivable" value="${disbursalData[0].adjustTotalReceivable}" onchange="calculateNetAmount()" 
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
						</td>
				</tr>
				 <tr>
										
					<td nowrap="nowrap"><bean:message key="lbl.currentMonthEMI"/>
					<input type="hidden" id="EMIFlag" value="F"/>
					</td>
					<td nowrap="nowrap">
						<html:text property="currentMonthEMI" styleClass="text"  value="${disbursalData[0].currentMonthEMI}" styleId="currentMonthEMI" readonly="true" style="text-align:right;"/>
					</td>
					<%-- <td nowrap="nowrap"><bean:message key="lbl.nextMonthEMI"/></td> --%>										
					<td nowrap="nowrap">
						<html:hidden property="preEMINextMonth" styleClass="text"  value="${disbursalData[0].preEMINextMonth}"  styleId="preEMINextMonth"  style="text-align:right;"/>
					</td>
				</tr>
				</table>
			</fieldset>
			</td>
</tr>
		<tr>
	       <td>
	      
	       <fieldset>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
			
	<tr>
	<input type="hidden" id="tenureMonths" name="tenureMonths" value="${requestScope.loanTenure}" />
			<td nowrap="nowrap" width="25%" >
				 <bean:message key="lbl.StartEMI" />
			</td>
			<td nowrap="nowrap" width="25%">
				<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
				  <logic:equal value="F" name="fianlDisb">
				                                 
				  <input type="checkbox" name="finalDisbursal"	id="finalDisbursal" checked="checked"
				      	onclick="showEffectiveDate();" />
				</logic:equal>
				<logic:notEqual name="fianlDisb" value="F">
				
				<input type="checkbox" name="finalDisbursal"
						id="finalDisbursal" onclick="showEffectiveDate();" />
				</logic:notEqual>
				<html:hidden property="repayMode" styleId="repayMode" 
					styleClass="text" value="${disbursalData[0].repayMode}"/>
				</logic:equal>
				
				<logic:equal name="disbursalDataNewList" property="repayMode" value="N">
				<input type="checkbox" name="finalDisbursal" disabled="disabled"
					id="finalDisbursal" onclick="showEffectiveDate();" />
				<html:hidden property="repayMode" styleId="repayMode" 
					styleClass="text" value="${disbursalData[0].repayMode}"/>
				</logic:equal>
			</td>
			<td width="25%">
				<bean:message key="lbl.repayEffDate" />
			</td>
			<td width="25%">
				<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
					<logic:equal value="F" name="fianlDisb">
					<div id="repayEffId">
						<html:text styleClass="text" property="repayEffDate"
					styleId="repayEffDate" maxlength="10" value="${disbursalData[0].repayEffDate}"
					onchange="checkDate('repayEffDate');" 
					tabindex="-1"/>
					</div>
					</logic:equal>
					
					
					<logic:notEqual name="fianlDisb" value="F">
					<div id="repayId">
							<html:text styleClass="text" property="repayEffDate"
					styleId="repayEff" disabled="true" maxlength="10"
					value="" tabindex="-1"/>
					</div>
						</logic:notEqual>
					
					</logic:equal>
					<div id="repayEffId" style="display:none">
					<html:text styleClass="text" property="repayEffDate" disabled="true"
						styleId="repayEffDate" maxlength="10" value="${disbursalData[0].repayEffDate}"
					onchange="checkDate('repayEffDate');" 
						tabindex="-1"/>
					</div>
					<div id="repayId" style="display:none">
					 <html:text styleClass="text" property="repayEffDate"
						 styleId="repayEff" disabled="true" maxlength="10"
							 value="" tabindex="-1"/>
					</div>
					<logic:notEqual name="disbursalDataNewList" property="repayMode" value="I">
					<div id="repayIdBusDate" >
					<html:text styleClass="text" property="repayEffDate"  value="${disbursalData[0].repayEffDate}"
					styleId="repayEffBusDate"  maxlength="10" onchange="checkDate('repayEffBusDate');"/>
					</div>
					</logic:notEqual>
			</td>
	</tr>
	<tr>

		<td nowrap="nowrap">
			<bean:message key="lbl.cycleDate" />
		</td>
		<td nowrap="nowrap">		    
		    
		<div id="cycleDateI">
			<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
		     <logic:equal value="F" name="fianlDisb">
		       <logic:present name="cycleDate">
		         <logic:notEmpty name="cycleDate">
		
					<div id="no2" style="display: none">
						<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalData[0].cycleDateValue}"/>
					<html:select property="cycleDate" styleId="cycleDate" value="${disbursalData[0].cycleDateValue}" styleClass="text"  onchange="nullNextDue(this.value);"    disabled="true">
						<html:option value="">--<bean:message key="lbl.select" />--</html:option>
					</html:select>				
					</div>
		
						<div id="yes2" >
							<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalData[0].cycleDateValue}"/>
						<html:select property="cycleDate" styleId="cycleDate" value="${disbursalData[0].cycleDateValue}" styleClass="text"  onchange="nullNextDue(this.value);" >
						   <html:option value="">--<bean:message key="lbl.select" />--</html:option>
						   <html:optionsCollection name="cycleDate" label="cycleDateDesc" value="cycleDateValue" />
						</html:select>
						</div>
		         </logic:notEmpty>
			  </logic:present>
			</logic:equal>
		
		<logic:notEqual name="fianlDisb" value="F">
		<div id="cycleDateI">
		<div id="no2" style="display: none">
					<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalData[0].cycleDateValue}"/>
		<html:select property="cycleDate" styleId="cycleDate" value="${disbursalData[0].cycleDateValue}" styleClass="text"  onchange="nullNextDue(this.value);"   disabled="true">
			<html:option value="">--<bean:message key="lbl.select" />--</html:option>
		</html:select>				
		</div>
		
		<div id="yes2" >
			<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalData[0].cycleDateValue}"/>
		<html:select property="cycleDate" styleId="cycleDate" value="${disbursalData[0].cycleDateValue}" styleClass="text"  disabled="true" onchange="nullNextDue(this.value);" >
		   <html:option value="">--<bean:message key="lbl.select" />--</html:option>
		   <html:optionsCollection name="cycleDate" label="cycleDateDesc" value="cycleDateValue" />
		</html:select>
					</div>
				
		</div>
		</logic:notEqual>
		</logic:equal>
			
		</div>
			<logic:notEqual name="disbursalDataNewList" property="repayMode" value="I">
		<html:select property="cycleDate" styleId="cycleDate" styleClass="text" 
												disabled="true"  onchange="nullNextDue(this.value);">
		<html:option value="">--<bean:message key="lbl.select" />--</html:option>
		</html:select>
		</logic:notEqual>
		<div id="cycleDateFinal"  style="display: none">
			<logic:present name="cycleDate">
		
							<html:select property="cycleDate" styleId="cycleDate" value="${disbursalData[0].cycleDateValue}" styleClass="text"  onchange="nullNextDue(this.value);" >                          
								<logic:notEmpty name="cycleDate">
								     <html:option value="">--<bean:message key="lbl.select" />--</html:option>
		<html:optionsCollection name="cycleDate" label="cycleDateDesc" value="cycleDateValue" />
		</logic:notEmpty>
		</html:select>
		</logic:present>
			</div>
		</td>
		<td>
		
		<logic:present name="fianlDisb">
		<logic:equal value="F" name="fianlDisb">
		<input type="hidden" id="val" value="Y" />
		</logic:equal>
		<logic:notEqual value="F" name="fianlDisb">
		<input type="hidden" id="val" value="N" />
		</logic:notEqual>
		</logic:present>
		<logic:notPresent name="fianlDisb">
		
		<input type="hidden" id="val" value="N" />
		
		</logic:notPresent>
		
		<div id="no1">
			<bean:message key="lbl.nextDueDate" />
		</div>
		<div id="yes1" style="display: none">
			<bean:message key="lbl.nextDueDate" /><font color="red">*</font>
		</div>
				 
		</td>
		   <td >
		   <logic:equal name="disbursalDataNewList" property="repayMode" value="I">
		   <logic:present name="fianlDisb">
		
		<logic:equal value="F" name="fianlDisb">
		
		 <div id="no3" style="display: none;">
		<html:text property="nextDueDateNo" value="" styleClass="text" disabled="true" />
		</div>
		<div id="yes3" >
		<html:text property="nextDueDate" styleClass="text"  value="${disbursalData[0].nextDueDate}"
		    styleId="nextDueDate" onchange="checkDueDate(value);checkRepayEffectiveDate(value);calTenureMonthForMaturityDateNextDueDateDIS();" />
		</div>		
		</logic:equal>
		<logic:notEqual value="F" name="fianlDisb">
		<div id="no3">
			<html:text property="nextDueDateNo" value="" styleClass="text"  disabled="true" />
		</div>
		<div id="yes3" style="display: none">
			<html:text property="nextDueDate" styleClass="text"  value="${disbursalData[0].nextDueDate}"
		    styleId="nextDueDate" onchange="checkDueDate(value);checkRepayEffectiveDate(value);calTenureMonthForMaturityDateNextDueDateDIS();" />
		</div>	
		
		</logic:notEqual>
		</logic:present>									    	
		</logic:equal>
		<logic:equal name="disbursalDataNewList" property="repayMode" value="N">
		<div id="no3">
		<html:text property="nextDueDateNo" value="" styleClass="text"  disabled="true" />
		</div>
		</logic:equal>					
		</td>
		</tr>
		<!-- InterestDueDate added by Ajay -->
	<tr>
		<td>
			 <div id="Ino1">
			<bean:message key="lbl.interestDueDate" />
		</div>
			 <div id="Iyes1" style="display: none">
			<bean:message key="lbl.interestDueDate" />
		     </div>
			 
		</td>
		   <td >
		   <logic:equal name="disbursalDataNewList" property="repayMode" value="I">
		   <logic:present name="fianlDisb">
		
		<logic:equal value="F" name="fianlDisb">		
		 <div id="Ino3" style="display: none;">
		<html:text property="interestDueDateNo" value="" styleClass="text" disabled="true" />
		</div>
		<div id="Iyes3" >
		<input type="hidden" name="hiddenIntDueDate" id="hiddenIntDueDate" value="${disbursalData[0].interestDueDate}"  />
		<html:text property="interestDueDate" styleClass="text"  value="${disbursalData[0].interestDueDate}"
		    styleId="interestDueDateDis"   onchange="datevalidate();validateInterestDueDate();" />
		</div>
		
		</logic:equal>
		<logic:notEqual value="F" name="fianlDisb">	
		
		<div id="Ino3">
			<html:text property="interestDueDateNo" value="" styleClass="text"  disabled="true" />
		</div>
		<div id="Iyes3" style="display: none">
		<input type="hidden" name="hiddenIntDueDate" id="hiddenIntDueDate" value="${disbursalData[0].interestDueDate}"/>
			<html:text property="interestDueDate" styleClass="text"  value="${disbursalData[0].interestDueDate}"
		    styleId="interestDueDateDis" onchange="datevalidate();validateInterestDueDate();"  />
		</div>	
		
		</logic:notEqual>
		</logic:present>									    	
		</logic:equal>
		<logic:equal name="disbursalDataNewList" property="repayMode" value="N">
		<div id="Ino3">
		<html:text property="interestDueDateNo" value="" styleClass="text"  disabled="true" />
		</div>
		</logic:equal>					
		</td>
			
			<td nowrap="nowrap"><bean:message key="lbl.penalIntCalcDate" /></td>
			<td>
			<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
			<div id="penal1" style="display: none">
			<html:text styleClass="text" property="penalIntCalcDate"
						styleId="penalIntCalcDate" maxlength="10" value="<%=initiationDate %>"
						onchange="checkDate('penalIntCalcDate');" />
			</div>
			<div id="penal2" >
			<html:text styleClass="text" property="penalIntCalcDate" value=""
						 maxlength="10" onchange="checkDate('penalIntCalcDate');" disabled="true"/>
			</div>
			</logic:equal>
			<logic:notEqual name="disbursalDataNewList" property="repayMode" value="I">
			<div id="penal1" >
			<html:text styleClass="text" property="penalIntCalcDate"
						styleId="penalIntCalcDate" maxlength="10" value="${disbursalData[0].penalIntCalcDate}"
						onchange="checkDate('penalIntCalcDate');" />
			</div>
			<div id="penal2" style="display: none">
			<html:text styleClass="text" property="penalIntCalcDate" value=""
						 maxlength="10" onchange="checkDate('penalIntCalcDate');" disabled="true"/>
			</div>
			</logic:notEqual>
			</td>
	</tr>
			<tr>
			<td nowrap="nowrap">
				<bean:message key="lbl.paymentFlag" />
			</td>
			<td>
			<html:select styleId="paymentFlag" property="paymentFlag" styleClass="text" value="${disbursalData[0].paymentFlag}" onchange="displayHideTaPayment('paymentFlag')" >
			<html:option value="N">NO</html:option>
			<html:option value="P">Payment</html:option>
			<html:option value="T">TA Adjustment</html:option>
			</html:select>      
             </td>
			</td>
             <!-- Start here Brijesh Pathak -->
			 <logic:equal name="installmentType" value="S">
				<logic:equal name="editDueDate" value="Y">
					<input type="hidden" id="tenureMonths" name="tenureMonths" value="${requestScope.loanTenure}" /> 
					<td nowrap="nowrap"><bean:message key="lbl.maturityDate" /></td>
					<td>
					<logic:equal value="F" name="fianlDisb">
					<div id="loanTenure">
					<html:text styleClass="text" property="maturityDate1"
						styleId="maturityDate1" maxlength="10" value="${disbursalData[0].maturityDate1}"
						onchange="calTenureMonthForMaturityDateDIS2();" />
					
					</div>
					</logic:equal>
					<logic:notEqual value="F" name="fianlDisb">
					<div id="loanTenure" style="display: none">
					<html:text styleClass="text" property="maturityDate1"
						styleId="maturityDate1" maxlength="10" value="${disbursalData[0].maturityDate1}"
						onchange="calTenureMonthForMaturityDateDIS2();" />					
					</div>
					</logic:notEqual>
				<!-- End here Brijesh Pathak -->
					</td>
					</logic:equal> 					
			 </logic:equal> 

			</tr>
	<!-- work started by amandeep -->
	<tr>
	<td nowrap="nowrap" width="25%">
				<bean:message key="lbl.loanCurtailment" />
			</td>
			<td nowrap="nowrap" width="25%">
				<logic:present name="loanCurt">
					<logic:equal value="Y" name="loanCurt">
				      <input type="hidden" id="loanCurtVal" value="Y" />                           
				  <input type="checkbox" name="loanCurtail"	id="loanCurtail" checked="checked" />
				</logic:equal>
				<logic:notEqual name="loanCurt" value="Y">
				<input type="hidden" id="loanCurtVal" value="N" />    
				<input type="checkbox" name="loanCurtail"
						id="loanCurtail"  />
				</logic:notEqual>
					</logic:present>
			</td>			
			</tr>
			<!-- work end by amandeep -->
	</table>
	</fieldset>
	</td>
	</tr>
	<tr>
	<td>
	<fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	   <tr id="taLoanNoTrDiv"  >
				<td nowrap="nowrap" width="25%">
					<bean:message key="lbl.taLoanNo" />
					
				</td>
				<td nowrap="nowrap" width="25%">
				<html:text styleClass="text" property="taLoanNo" styleId="taLoanNo" value="${disbursalData[0].taLoanNo}" readonly="true" maxlength="20" />
				<logic:equal name="disbursalDataNewList" property="paymentFlag" value="T">
				<html:button property="taloanButton"  styleId="taloanButton" value=" " onclick="setSupplierManufacturerIdInSession();openLOVCommon(452,'disbursalMakerForm','taLoanNo','businessPartnerTypeDesc','lbxTaLoanNoHID', 'lbxBusinessPartnearHID','Select Supplier/Manufacturer First','','taCustomerName');" styleClass="lovbutton"> </html:button>
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="T">
				<html:button property="taloanButton" disabled="true" styleId="taloanButton" value=" " onclick="openLOVCommon(452,'disbursalMakerForm','taLoanNo','businessPartnerTypeDesc','lbxTaLoanNoHID', 'lbxBusinessPartnearHID','Select Supplier/Manufacturer First','','taCustomerName');" styleClass="lovbutton"> </html:button>
				</logic:notEqual>
				<input type="hidden" name="contextPath"	value="<%=request.getContextPath()%>" id="contextPath" />
				<html:hidden property="lbxTaLoanNoHID" styleId="lbxTaLoanNoHID" value="${disbursalData[0].lbxTaLoanNoHID}" />
				<html:hidden property="hid" styleId="hid" value="" />
				<html:hidden property="balAmount" styleId="balAmount" value="" />
				</td>
				<td nowrap="nowrap" width="25%">
					<bean:message key="lbl.taCustomerName" />
				</td>
				<td nowrap="nowrap" width="25%">
					<html:text styleClass="text" property="taCustomerName"
						styleId="taCustomerName" maxlength="50" readonly="true"
						value="${disbursalData[0].taCustomerName}" tabindex="-1" />
				</td>
				
			</tr>
	</table>
	</fieldset>
	</td>
	</tr>
		<tr>
	<td>
	<fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<%-- <%
		List list = (List)request.getAttribute("disbursalData");
		DisbursalMakerVO vo= (DisbursalMakerVO)list.get(0);
		System.out.println(vo.getLbxpayTo());
		%> --%>
		<tr>
		     <td><bean:message key="lbl.payTo"></bean:message></td>
	        <td>
	         <html:text property="payTo" styleId="payTo" readonly="true" styleClass="text" value="${disbursalData[0].payTo}"	tabindex="-1"/>
	         <input type="hidden"  name="lbxpayTo" id="lbxpayTo" value="${disbursalData[0].lbxpayTo}"/>
             <html:button property="payToButton" styleId="payToButton"  onclick="openLOVCommon(262,'disbursalMakerForm','payTo','lbxLoanNoHID','lbxpayTo', 'lbxLoanNoHID','Select Loan Number','payeeNameRefresh','hid');closeLovCallonLov('loanNo');" value=" " styleClass="lovbutton"></html:button>
	        </td>
	        <td><bean:message key="lbl.payeeName"></bean:message></td>
	        <td>
	         <a rel="tooltip3" id="tool">
	                <html:text property="payeeName" styleId="payeeName" readonly="true" styleClass="text" value="${disbursalData[0].payeeName}" onmouseover="applyToolTip(id);" onchange="insertValue();" tabindex="-1"/>
	                
	         </a>
	        	<input type="hidden"  name="lbxpayeeName" id="lbxpayeeName" value="${disbursalData[0].lbxpayeeName}"/>
                <%--  <%if(!vo.getLbxpayTo().equalsIgnoreCase("RSP")){ %> --%>
                <html:button property="payeeNameButton" styleId="payeeNameButton"  onclick="openLOVCommon(263,'disbursalMakerForm','payeeName','payTo|lbxLoanNoHID','lbxpayeeName', 'lbxpayTo|lbxLoanNoHID','Select Pay To|Select Loan Number','','hid');closeLovCallonLov('payTo');" value=" " styleClass="lovbutton"></html:button>	
	          <%--   <%}else{ %> --%>
	            <html:button property="payeeNameButton" styleId="payeeNameButtonRSP" style="display: none"  onclick="openLOVCommon(655,'disbursalMakerForm','payeeName','payTo|lbxLoanNoHID','lbxpayeeName', 'lbxpayTo|lbxLoanNoHID','Select Pay To|Select Loan Number','','hid');closeLovCallonLov('payTo');" value=" " styleClass="lovbutton"></html:button>	
		  <%--  <%} %> --%>
		   </td>
	    </tr>
	  <tr id="paymenttr1">  
		    <td width="25%"><bean:message key ="lbl.paymentMode"></bean:message><font color="red">*</font> </td>
			 <td width="25%"><span style="float:left;">
			 <logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:select property="paymentMode" styleId="paymentMode" styleClass="text" value="${disbursalData[0].paymentMode}" onchange="fnNull();getAccountType();">
			 <html:option value="">--Select--</html:option> 
			  <html:option value="C">Cash</html:option> 
			 <html:option value="Q">Cheque</html:option> 
			 <html:option value="D">DD</html:option> 
			 <html:option value="N">NEFT</html:option> 
			 <html:option value="R">RTGS</html:option>	
			 <html:option value="S">ADJUSTMENT</html:option>	 		               
	           </html:select>
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:select property="paymentMode" styleId="paymentMode" styleClass="text" value="" disabled="true" onchange="fnNull();getAccountType();">
			 <html:option value="">--Select--</html:option> 
			  <html:option value="C">Cash</html:option> 
			 <html:option value="Q">Cheque</html:option> 
			 <html:option value="D">DD</html:option> 
			 <html:option value="N">NEFT</html:option> 
			 <html:option value="R">RTGS</html:option>	
			 <html:option value="S">ADJUSTMENT</html:option>	 		               
	           </html:select>
				</logic:notEqual>
			 
			
	           <input type="hidden"  name="lbxpaymentMode" id="lbxpaymentMode" value=""/> 
			 </span></td>
			
		    <td width="25%"><bean:message key ="lbl.paymentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
			<td width="25%">
			 <logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:text property="paymentDate" styleClass="text" styleId="paymentDate" value="" maxlength="10" value="${disbursalData[0].paymentDate}" onchange="return checkDate('paymentDate');"></html:text>
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:text property="paymentDate" styleClass="text" styleId="paymentDate" value="" maxlength="10" disabled="true" onchange="return checkDate('paymentDate');"></html:text>
				</logic:notEqual>
				 
		    </td>
		 	</tr>
		 			
		<tr id="paymenttr2">
			  <td><bean:message key="lbl.instrumentNumber"></bean:message><font color="red">*</font> </td>
			 <td><div style="float:left;">
			 <logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
				 <html:text property="instrumentNumber" styleClass="text" styleId="instrumentNumber" value="${disbursalData[0].instrumentNumber}" maxlength="20"/>
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
				 <html:text property="instrumentNumber" styleClass="text" styleId="instrumentNumber" value="" disabled="true" maxlength="20"/>
				</logic:notEqual>
			
			 </div>
			 <input type="hidden" name="instrumentID" id="instrumentID" value="" />
			</td>
			 <td><bean:message key="lbl.instrumentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font> </td>
	        <td>
	        <logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
				 <html:text property="instrumentDate" styleClass="text" styleId="instrumentDate"  value="${disbursalData[0].instrumentDate}" maxlength="10" onchange="return checkDate('instrumentDate');"></html:text>
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
				 <html:text property="instrumentDate" styleClass="text" styleId="instrumentDate" value="" maxlength="10" disabled="true"  onchange="return checkDate('instrumentDate');"/>
				</logic:notEqual>
	       
	           </td>				
		</tr>
		<tr id="paymenttr3">			 
		   <td><bean:message key="lbl.bankAccount"></bean:message><font color="red">*</font></td>
			 <td><div style="float:left;"> 
			 
	          <html:text styleClass="text" property="bankAccount" styleId="bankAccount" maxlength="50"  value="${disbursalData[0].bankAccount}" readonly="true" tabindex="-1"/>
              <input type="hidden"  name="lbxbankAccountID" id="lbxbankAccountID" value="${disbursalData[0].lbxbankAccountID}"/>
			 <logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
				 <html:button property="bankAccountButton" styleId="bankAccountButton" onclick="openLOVCommon(64,'disbursalMakerForm','bankAccount','paymentMode','lbxbankAccountID', 'lbxpaymentMode','Select Payment Mode','getChequeDetail','hid','hid1');" value=" "  styleClass="lovbutton"></html:button>
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
				 <html:button property="bankAccountButton" styleId="bankAccountButton" onclick="openLOVCommon(64,'disbursalMakerForm','bankAccount','paymentMode','lbxbankAccountID', 'lbxpaymentMode','Select Payment Mode','getChequeDetail','hid','hid1');" value=" " disabled="true" styleClass="lovbutton"></html:button>
				</logic:notEqual>
			    
			    <input type="hidden" name="hid1" id="hid1"/> 
			    <input type="hidden" name="hid" id="hid"/> 
				</div></td>
		    <td><bean:message key="lbl.bank"></bean:message></td>
			<td>
			<div style="float:left;"> 
			<logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:text styleClass="text" property="bank" styleId="bank" maxlength="50"  value="${disbursalData[0].bank}" readonly="true" tabindex="-1"/>
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:text styleClass="text" property="bank" styleId="bank" maxlength="50"  value="" readonly="true" tabindex="-1"/>
				</logic:notEqual>
		
			 
		    <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${disbursalData[0].lbxBankID}"/> 
			</div></td>
		</tr> 
		<tr id="paymenttr4">	
			<td><bean:message key="lbl.branch"></bean:message></td>
				<td>
				<logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:text styleClass="text" property="branch" styleId="branch" maxlength="50"  value="${disbursalData[0].branch}" readonly="true" tabindex="-1"/>
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:text styleClass="text" property="branch" styleId="branch" maxlength="50"  value="" readonly="true" tabindex="-1"/>
				</logic:notEqual>
				  <html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${disbursalData[0].lbxBranchID}"/>
		   		</td>	 
				<td><bean:message key="lbl.micr"></bean:message></td>
				<td>
				<logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:text property="micr" styleClass="text" styleId="micr" value="${disbursalData[0].micr}" maxlength="20" readonly="true" tabindex="-1"></html:text>
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:text property="micr" styleClass="text" styleId="micr" value="" maxlength="20" readonly="true" tabindex="-1"></html:text>
				</logic:notEqual>
				
				</td>
		   
	   </tr>
	    <tr id="paymenttr5">
	         <td> <bean:message key="lbl.ifsCode"></bean:message></td>
			 <td><div style="float:left;">
			 <logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:text property="ifsCode" styleId="ifsCode" styleClass="text" value="${disbursalData[0].ifsCode}" maxlength="20" readonly="true" tabindex="-1"></html:text>
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
				<html:text property="ifsCode" styleId="ifsCode" styleClass="text" value="" maxlength="20" readonly="true" tabindex="-1"></html:text>
				</logic:notEqual>
			 		
	         </div></td>
	         <td><bean:message key="lbl.paymentAmount"></bean:message><font color="red">*</font> </td>
			 <td>
			 <logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
			<html:text property="paymentAmount" styleClass="text" styleId="paymentAmount" value="${disbursalData[0].paymentAmount}" 
			 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" disabled="disabled" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" />
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
		   <html:text property="paymentAmount" styleClass="text" styleId="paymentAmount" value="" disabled="true"
			 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" disabled="disabled" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" />
				</logic:notEqual>
			
			
			 </td>
	    </tr>
	    
	        <!-- Virender Code Start -->
			<tr>
				<td>
					<bean:message key="lbl.beneficiary_bank" />
				</td>
				<td>
					<html:text property="beneficiaryBankCode" styleId="beneficiaryBankCode" readonly="true" styleClass="text" value="${disbursalData[0].beneficiaryBankCode}" tabindex="-1" />
					 <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();
					 openLOVCommon('19243','disbursalMakerForm','beneficiaryBankCode','','beneficiaryBankBranchName', 'beneficiaryLbxBranchID','','','beneficiaryLbxBankID');" value=" " styleClass="lovbutton"> </html:button>
					<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
					<html:hidden property="beneficiaryLbxBankID" styleId="beneficiaryLbxBankID" value="${disbursalData[0].beneficiaryLbxBankID}"/>
				</td>
		
				<td>
					<bean:message key="lbl.beneficiaryBankBranchName" />									
				</td>
				
				<td>
					<html:text property="beneficiaryBankBranchName" styleId="beneficiaryBankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${disbursalData[0].beneficiaryBankBranchName}" />
                    <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="
                    openLOVCommon(19244,'disbursalMakerForm','beneficiaryBankBranchName','beneficiaryBankCode','beneficiaryLbxBranchID', 'beneficiaryLbxBankID','Please Select Bank','','micrCode','beneficiaryIfscCode','beneficiaryLbxBranchID');closeLovCallonLov('beneficiaryBankCode');" value=" " styleClass="lovbutton"> </html:button>
			    	<html:hidden property="beneficiaryLbxBranchID" styleId="beneficiaryLbxBranchID" value="${disbursalData[0].beneficiaryLbxBranchID}"  />
			    	<input type="hidden" name="micrCode" value="" id="micrCode" />
			  </td>
			  <%-- 
			  <td>
				<html:text property="beneficiary_bankBranchName" styleId="beneficiary_bankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${disbursalDataNew[0].beneficiary_bankBranchName}" />
				<html:button property="bankBranchButton" styleId="bankBranchButton" onclick="
				openLOVCommon(109,'disbursalMakerForm','beneficiary_bankBranchName','beneficiary_bankCode','beneficiary_lbxBranchID', 'beneficiary_lbxBankID','Please Select Bank','','micr','beneficiary_ifscCode','beneficiary_lbxBranchID');closeLovCallonLov('beneficiary_bankCode');" value=" " styleClass="lovbutton"> </html:button>
				<html:hidden property="beneficiary_lbxBranchID" styleId="beneficiary_lbxBranchID" value="${disbursalDataNew[0].beneficiary_lbxBranchID}"  />
			</td> --%>
				
			</tr>
			
			<tr>
				<td>
					<bean:message key="lbl.beneficiaryAccountNo"  />								
				</td>
				<td>
					<html:text property="beneficiaryAccountNo" styleId="beneficiaryAccountNo" tabindex="-1" onblur="fnChangeCase('CustBankDetailsForm','accountNo');this.value=removeSpaces1(this.value);" styleClass="text"  value="${disbursalData[0].beneficiaryAccountNo}"  />
				</td>	
		
				<td>
					<bean:message key="lbl.beneficiaryIfscCode" />
				</td>
				<td>
					<html:text property="beneficiaryIfscCode" styleId="beneficiaryIfscCode" styleClass="text" readonly="true" maxlength="20" value="${disbursalData[0].beneficiaryIfscCode}"  />
				</td>	
			</tr>
	
	     <tr id="paymenttr6">
		      <td><bean:message key="lbl.tdsAmount"></bean:message></td>
	        <td>
	        <logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
				 <html:text property="tdsAmount" styleId="tdsAmount" styleClass="text" value="${disbursalData[0].tdsAmount}" 
	         onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" />
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
				 <html:text property="tdsAmount" styleId="tdsAmount" styleClass="text" value=""  disabled="true"
	         onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" />
				</logic:notEqual>
	        
	        </td>
	         <td><bean:message key="lbl.remark"></bean:message></td>
	         <td>
	         <logic:equal name="disbursalDataNewList" property="paymentFlag" value="P">
				 <html:textarea property="remarks" styleId="remarks" value="${disbursalData[0].remarks}" styleClass="text"></html:textarea>      
				</logic:equal>
				<logic:notEqual name="disbursalDataNewList" property="paymentFlag" value="P">
				 <html:textarea property="remarks" styleId="remarks" disabled="true" styleClass="text"></html:textarea>      
				</logic:notEqual>
	        
	        </td>
	   
	     </tr>
	</table>
	</fieldset>
	</td>
	</tr>
	<tr><td>
   <!--  	<button type="button" name="EMI" id="EMI" 
		class="topformbutton4" title="Alt+E" accesskey="E"
		onclick="return calculateEMI()"><bean:message key="button.calculatePreEMI" /></button>
		-->
	<button type="button" name="save" id="save"
		class="topformbutton2" title="Alt+V" accesskey="V"
		onclick="return updateDisbursalDataWithPayment(document.getElementById('lbxLoanNoHID').value,'U');"><bean:message key="button.save" /></button>
	<button type="button" name="saveFwd" id="saveFwd" title="Alt+F" accesskey="F"
		 class="topformbutton2"
		onclick="return forwordDisbursal();"><bean:message key="button.forward" /></button>

	<button type="button" class="topformbutton4" title="Alt+R" accesskey="R"
		onclick="return viewReceivableDisbursal('<bean:message key="msg.LoanAccountNo" />');" ><span class='underLine'>R</span>eceivable/Payable</button>
	
	<button type="button" class="topformbutton4" title="Alt+I" accesskey="I"
		onclick="return openOldInstallmentPlan('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.oldInstallment" /></button>									
	
	<button type="button" class="topformbutton4" title="Alt+N" accesskey="N"
		onclick="return openNewInstallmentPlan('<bean:message key="msg.LoanAccountNo" />','DIM');" ><bean:message key="button.newInstallment" /></button>		
	
	<button type="button" class="topformbutton4" title="Alt+C" accesskey="C"
		onclick="return openOtherChargePlanAtDisbrsal('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.otherChargesPlan" /></button>
	
	<button type="button" name="oldDisbursal" id="oldDisbursal"
		class="topformbutton4" title="Alt+O" accesskey="O"
		onclick="return openDisbursalSchedule();"><bean:message key="button.olddisbursal" /></button>
	
	<button type="button" name="specialCondButton" id="specialCondButton"
		class="topformbutton4" title="Alt+S" accesskey="S"
		onclick="return viewSpecialCondButton('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.specialCondition" /></button>
	
	<button type="button" name="generateRepaymentSchOld" id="generateRepaymentSchOld" title="Alt+R" accesskey="R"
		class="topformbutton4" onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.oldrepay" /></button>	
	
	<button type="button" name="generateRepaymentSch"
			id="generateRepaymentSch"
			 title="Alt+R" accesskey="R"
			class="topformbutton4" onclick="return callRepay();" ><bean:message key="button.newrepay" /></button>
	
	

   </td>
	</tr>
	</logic:iterate>
	</table>

</logic:present>
</logic:present>
</logic:notPresent>
<!-- Editable part code Ends  here -->
<!-- Author part code Stats   here -->
<logic:present name="disbursalDataAuthor">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

<logic:iterate id="disbursalDataNewList" name="disbursalDataAuthor">
	<tr>
	<td>
	<fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	 <tr>
			<td nowrap="nowrap" width="25%">
				<bean:message key="lbl.loanNo" />
				<font color="red">*</font>
			</td>
			<td nowrap="nowrap" width="25%">
				<html:text styleClass="text" property="loanNo"
					styleId="loanNo" value="${disbursalDataAuthor[0].loanNo}" readonly="true" maxlength="20" />
				<input type="hidden" name="contextPath"
					value="<%=request.getContextPath()%>" id="contextPath" />
				<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
					value="${disbursalDataAuthor[0].lbxLoanNoHID}" />
				<html:hidden property="hid" styleId="hid" value="" />
				<html:hidden property="disbursalNo" styleId="disbursalNo"
										value="${disbursalDataAuthor[0].disbursalNo}" />
				<html:hidden property="loanDisbursalId" styleId="loanDisbursalId"
				                            value="${disbursalDataAuthor[0].loanDisbursalId}"/>
			</td>
			<td nowrap="nowrap" width="25%">
				<bean:message key="lbl.customerName" />
			</td>
			<td nowrap="nowrap" width="25%">
				<html:text styleClass="text" property="customerName"
					styleId="customerName" maxlength="50" readonly="true"
					value="${disbursalDataAuthor[0].customerName}" tabindex="-1" />
			</td>
	</tr>
	<tr>
											
			<td nowrap="nowrap">
				<bean:message key="lbl.product" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="product"
					styleId="product" value="${disbursalDataAuthor[0].product}"  readonly="true" maxlength="50"
					tabindex="-1" />
			</td>
														
			<td nowrap="nowrap">
				<bean:message key="lbl.scheme" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="scheme"
					styleId="scheme" maxlength="50" value="${disbursalDataAuthor[0].scheme}"  readonly="true"
					tabindex="-1" />
			</td>
											
	</tr>
		<tr>
				<td nowrap="nowrap">
					<bean:message key="lbl.loanAmt" />
				</td>
				<td nowrap="nowrap">
					<html:text styleClass="text" property="loanAmt" value="${disbursalDataAuthor[0].loanAmt}"
						styleId="loanAmt"  readonly="true" maxlength="18"
						tabindex="-1" style="text-align:right;"/>
				</td>
					<td nowrap="nowrap">
					<bean:message key="lbl.disbursedAmount" />
				</td>
				<td nowrap="nowrap">
					<html:text styleClass="text" property="disbursedAmount"
						styleId="disbursedAmount" maxlength="18" value="${disbursalDataAuthor[0].disbursedAmount}"
						readonly="true" tabindex="-1" style="text-align:right;"/>
				</td>

	</tr>
	<tr>
			<td nowrap="nowrap">
				<bean:message key="lbl.loanApprovalDate" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="loanApprovalDate"
					value="${disbursalDataAuthor[0].loanApprovalDate}" readonly="true"
					tabindex="-1" />
			</td>
											
			<td nowrap="nowrap">
				<bean:message key="lbl.disbursalDate" />
				<font color="red">*</font>
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="disbursalDate" 
					 maxlength="10" readonly="true"
					onchange="checkDate('disbursalDate');" value="${disbursalDataAuthor[0].disbursalDate}" />
			</td>
											
	</tr>
	<tr>
					<td nowrap="nowrap">
						<bean:message key="lbl.makerRemark" />

					</td>
					<td nowrap="nowrap">
						<html:textarea property="disbursalDescription" readonly="true"
							styleId="disbursalDescription" value="${disbursalDataAuthor[0].disbursalDescription}" styleClass="text"></html:textarea>
					</td>
					<td nowrap="nowrap">
						<bean:message key="lbl.authorRemarks" />
					</td>
				
					<td nowrap="nowrap">
						<html:textarea styleClass="text" property="authorRemarks"
						styleId="authorRemarks" disabled="true"	value="${disbursalDataAuthor[0].authorRemarks}" />
					</td>
		</tr>
 </table>
</fieldset>
        </td>
	</tr>
    <tr>
	    <td>
	   
	    <fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
						<td nowrap="nowrap" width="25%">
							<bean:message key="lbl.disbursalTo" />
								<font color="red">*</font>
						</td>
						<td nowrap="nowrap" width="25%">
							 
				              <html:select property="disbursalTo" styleId="disbursalTo" styleClass="text" value="${disbursalDataAuthor[0].disbursalTo}" disabled="true" onchange="activeSupplierForAddDetails(this.value);getTotalPayableReceivable(this.value);" >
				              	<html:option value="">Select</html:option>
				              	<html:option value="CS">Customer</html:option>
				              	<html:option value="SU">Dealer</html:option>
				              	<html:option value="MF">Manufacturer</html:option>
				              </html:select>
						</td>
						<td nowrap="nowrap" width="25%">
						<div id="supplierLableDiv" style="display:none">
							<bean:message key="lbl.sDealer"/><font color="red">*</font>
						</div>
						<div id="manufactLableDiv" style="display:none">
							<bean:message key="lbl.manufact"/><font color="red">*</font>
						</div>
						<div id="customerLableDiv" style="display:block">
							<bean:message key="lbl.CustomerName"/><font color="red">*</font>
						</div>			
						<div id="otherLableDiv" style="display:none">
							<bean:message key="lbl.other"/><font color="red">*</font>
						</div>	
						</td>
						<td nowrap="nowrap"  width="25%">
							 <div style="width: 100%">
			<logic:equal name="disbursalDataNewList" property="disbursalTo" value="CS">
			<div id="supplierDiv" style="display:none" >
			<html:text styleClass="text" maxlength="50" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${disbursalDataAuthor[0].businessPartnerTypeDesc}"/>
			<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
		 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
		   <html:hidden property="bankAcountName" styleId="bankAcountName"  value="" styleClass="text" />
		 </div >
		 <div id="custDiv">
		<html:text styleClass="text" maxlength="50" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDescCust" style="" readonly="true" disabled="true" value="${disbursalDataAuthor[0].customerName}" />
		</div>
			</logic:equal>
		<logic:notEqual name="disbursalDataNewList" property="disbursalTo" value="CS">
		<div id="supplierDiv"  >
		<html:text styleClass="text" maxlength="50" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDesc" style="" readonly="true" value="${disbursalDataAuthor[0].businessPartnerTypeDesc}"/>
		<html:hidden styleClass="text" property="businessPartnerType" styleId="businessPartnerType" style="" value=""/>              					
		<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		<html:hidden property="businessPartnerName" styleId="businessPartnerName"  value="" styleClass="text" />
		<html:hidden property="bankAcountName" styleId="bankAcountName"  value="" styleClass="text" />
		 </div >
		 <div id="custDiv" style="display:none">
		 <html:text styleClass="text" maxlength="50" property="businessPartnerTypeDesc" styleId="businessPartnerTypeDescCust" style="" readonly="true" disabled="true" value="${disbursalDataAuthor[0].customerName}" />
		 </div>
		</logic:notEqual>
								
								
							<html:hidden  property="loanNo" styleId="loanNo" value="${requestScope.lbxLoanNoHID}" />
							<html:hidden  property="lbxBusinessPartnearHID" styleId="lbxBusinessPartnearHID" value="${disbursalDataAuthor[0].lbxBusinessPartnearHID}" />
						
						</div>
						</td>
					</tr>

	                 <tr>
						<td nowrap="nowrap">
							<bean:message key="lbl.disAmount" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="disbursalAmount" readonly="true"
								styleId="disbursalAmount"   value="${disbursalDataAuthor[0].disbursalAmount}" 
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" onchange="calculateNetAmount()"/>
							
						</td>
						<td nowrap="nowrap">
							<bean:message key="lbl.netAmount" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="netAmount"
								styleId="netAmount" value="${disbursalDataAuthor[0].netAmount}" readonly="true" tabindex="-1"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
						</td>
					 </tr>
					 <tr>
					 <td nowrap="nowrap">
							<bean:message key="lbl.totalPayable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="totalPayable"
								styleId="totalPayable"  value="${disbursalDataAuthor[0].totalPayable}" readonly="true" tabindex="-1"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
								
						</td><td nowrap="nowrap">
							<bean:message key="lbl.adjustTotalPayable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="adjustTotalPayable" readonly="true"
								styleId="adjustTotalPayable" value="${disbursalDataAuthor[0].adjustTotalPayable}" onchange="calculateNetAmount()"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;"  />
						</td>
					 </tr>
					  <tr>
					  
					  <td nowrap="nowrap">
							<bean:message key="lbl.totalReceivable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="totalReceivable"
								styleId="totalReceivable" value="${disbursalDataAuthor[0].totalReceivable}"  readonly="true" tabindex="-1"
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
						</td><td nowrap="nowrap">
							<bean:message key="lbl.adjustTotalReceivable" />
							<font color="red">*</font>
						</td>
						<td nowrap="nowrap">
							<html:text styleClass="text" property="adjustTotalReceivable" readonly="true"
								styleId="adjustTotalReceivable" value="${disbursalDataAuthor[0].adjustTotalReceivable}" onchange="calculateNetAmount()" 
								onkeypress="return numbersonly(event,id,18)"
								onblur="formatNumber(this.value,id);"
								onkeyup="checkNumber(this.value, event, 18,id);"
								onfocus="keyUpNumber(this.value, event, 18,id);" 
								style="text-align:right;" />
						</td>
				</tr>
				 <tr>
										
					<td nowrap="nowrap"><bean:message key="lbl.currentMonthEMI"/>
					<input type="hidden" id="EMIFlag" value="F"/>
					</td>
					<td nowrap="nowrap">
						<html:text property="currentMonthEMI" styleClass="text"  value="${disbursalDataAuthor[0].currentMonthEMI}" styleId="currentMonthEMI" readonly="true" style="text-align:right;"/>
					</td>
					<%-- <td nowrap="nowrap"><bean:message key="lbl.nextMonthEMI"/></td> --%>										
					<td nowrap="nowrap">
						<html:hidden property="preEMINextMonth" styleClass="text"  value="${disbursalDataAuthor[0].preEMINextMonth}"  styleId="preEMINextMonth" style="text-align:right;"/>
					</td>
				</tr>
				</table>
			</fieldset>
			</td>
		</tr>
		<tr>
	       <td>
	      
	       <fieldset>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
			
	<tr>
			<td nowrap="nowrap" width="25%">
				 <bean:message key="lbl.StartEMI" />
			</td>
			<td nowrap="nowrap"width="25%">
				<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
				  <logic:equal value="F" name="fianlDisb">
				                                 
				  <input type="checkbox" name="finalDisbursal"	id="finalDisbursal" checked="checked"
				     disabled="disabled" 	onclick="showEffectiveDate();" />
				</logic:equal>
				<logic:notEqual name="fianlDisb" value="F">
				
				<input type="checkbox" name="finalDisbursal"
					disabled="disabled"	id="finalDisbursal" onclick="showEffectiveDate();" />
				</logic:notEqual>
				<html:hidden property="repayMode" styleId="repayMode" 
					styleClass="text" value="${disbursalDataAuthor[0].repayMode}"/>
				</logic:equal>
				
				<logic:equal name="disbursalDataNewList" property="repayMode" value="N">
				<input type="checkbox" name="finalDisbursal" disabled="disabled"
					id="finalDisbursal" onclick="showEffectiveDate();" />
				<html:hidden property="repayMode" styleId="repayMode" 
					styleClass="text" value="${disbursalDataAuthor[0].repayMode}"/>
				</logic:equal>
			</td>
			<td width="25%">
				<bean:message key="lbl.repayEffDate" />
			</td>
			<td width="25%">
				<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
					<logic:equal value="F" name="fianlDisb">
					<div id="repayEffId">
						<html:text styleClass="text" property="repayEffDate" readonly="true"
					 maxlength="10" value="${disbursalDataAuthor[0].repayEffDate}"
					onchange="checkDate('repayEffDate');" 
					tabindex="-1"/>
					</div>
					</logic:equal>
					
					
					<logic:notEqual name="fianlDisb" value="F">
					<div id="repayId">
							<html:text styleClass="text" property="repayEffDate"
					disabled="true" maxlength="10"
					value="" tabindex="-1"/>
					</div>
						</logic:notEqual>
					
					</logic:equal>
					<div id="repayEffId" style="display:none">
					<html:text styleClass="text" property="repayEffDate" disabled="true"
						maxlength="10" value="${disbursalDataAuthor[0].repayEffDate}"
					onchange="checkDate('repayEffDate');" 
						tabindex="-1"/>
					</div>
					<div id="repayId" style="display:none">
					 <html:text styleClass="text" property="repayEffDate"
					disabled="true" maxlength="10" readonly="true"
							 value="" tabindex="-1"/>
					</div>
					<logic:notEqual name="disbursalDataNewList" property="repayMode" value="I">
					<div id="repayIdBusDate" >
					<html:text styleClass="text" property="repayEffDate"  value="${disbursalDataAuthor[0].repayEffDate}"
					readonly="true" maxlength="10" onchange="checkDate('repayEffBusDate');"/>
					</div>
					</logic:notEqual>
			</td>
	</tr>
	<tr>

		<td nowrap="nowrap">
			<bean:message key="lbl.cycleDate" />
		</td>
		<td nowrap="nowrap">		    
		<div id="cycleDateI">
			<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
		     <logic:equal value="F" name="fianlDisb">
		       <logic:present name="cycleDate">
		         <logic:notEmpty name="cycleDate">
		
					<div id="no2" style="display: none">
						<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataAuthor[0].cycleDateValue}"/>
					<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataAuthor[0].cycleDateValue}" styleClass="text"  onchange="nullNextDue(this.value);"  disabled="true">
						<html:option value="">--<bean:message key="lbl.select" />--</html:option>
					</html:select>				
					</div>
		
						<div id="yes2" >
							<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataAuthor[0].cycleDateValue}"/>
						<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataAuthor[0].cycleDateValue}" styleClass="text" disabled="true"  onchange="nullNextDue(this.value);" >
						   <html:option value="">--<bean:message key="lbl.select" />--</html:option>
						   <html:optionsCollection name="cycleDate" label="cycleDateDesc" value="cycleDateValue" />
						</html:select>
						</div>
		         </logic:notEmpty>
			  </logic:present>
			</logic:equal>
		
		<logic:notEqual name="fianlDisb" value="F">
		<div id="cycleDateI">
		<div id="no2" style="display: none">
					<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataAuthor[0].cycleDateValue}"/>
		<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataAuthor[0].cycleDateValue}" styleClass="text"  onchange="nullNextDue(this.value);"   disabled="true">
			<html:option value="">--<bean:message key="lbl.select" />--</html:option>
		</html:select>				
		</div>
		
		<div id="yes2" >
			<input type="hidden" name="hiddenDueDate" id="hiddenDueDate" value="${disbursalDataAuthor[0].cycleDateValue}"/>
		<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataAuthor[0].cycleDateValue}" styleClass="text"  disabled="true" onchange="nullNextDue(this.value);" >
		   <html:option value="">--<bean:message key="lbl.select" />--</html:option>
		   <html:optionsCollection name="cycleDate" label="cycleDateDesc" value="cycleDateValue" />
		</html:select>
					</div>
				
		</div>
		</logic:notEqual>
		</logic:equal>
			
		</div>
			<logic:notEqual name="disbursalDataNewList" property="repayMode" value="I">
		<html:select property="cycleDate" styleId="cycleDate" styleClass="text" 
												disabled="true"  onchange="nullNextDue(this.value);">
		<html:option value="">--<bean:message key="lbl.select" />--</html:option>
		</html:select>
		</logic:notEqual>
		<div id="cycleDateFinal"  style="display: none">
			<logic:present name="cycleDate">
		
							<html:select property="cycleDate" styleId="cycleDate" value="${disbursalDataAuthor[0].cycleDateValue}" styleClass="text" disabled="true" onchange="nullNextDue(this.value);" >
								<logic:notEmpty name="cycleDate">
								     <html:option value="">--<bean:message key="lbl.select" />--</html:option>
		<html:optionsCollection name="cycleDate" label="cycleDateDesc" value="cycleDateValue" />
		</logic:notEmpty>
		</html:select>
		</logic:present>
			</div>
		
		
		<logic:present name="fianlDisb">
		<logic:equal value="F" name="fianlDisb">
		<input type="hidden" id="val" value="Y" />
		</logic:equal>
		<logic:notEqual value="F" name="fianlDisb">
		<input type="hidden" id="val" value="N" />
		</logic:notEqual>
		</logic:present>
		<logic:notPresent name="fianlDisb">
		
		<input type="hidden" id="val" value="N" />
		
		</logic:notPresent>
		</td>
		
		<td><div id="no1">
			<bean:message key="lbl.nextDueDate" />
		</div>
		<div id="yes1" style="display: none">
			<bean:message key="lbl.nextDueDate" /><font color="red">*</font>
		     </div>
		</td>
		   <td >
		   <logic:equal name="disbursalDataNewList" property="repayMode" value="I">
		   <logic:present name="fianlDisb">
		
		<logic:equal value="F" name="fianlDisb">
		
		 <div id="no3" style="display: none;">
		<html:text property="nextDueDateNo" value="" styleClass="text" disabled="true" />
		</div>
		<div id="yes3" >
		<html:text property="nextDueDate" styleClass="text"  value="${disbursalDataAuthor[0].nextDueDate}"
		   readonly="true" onchange="checkDueDate(value);checkRepayEffectiveDate(value);calTenureMonthForMaturityDateNextDueDateDIS();" />
		</div>
		
		</logic:equal>
		<logic:notEqual value="F" name="fianlDisb">
		<div id="no3">
			<html:text property="nextDueDateNo" value="" styleClass="text"  disabled="true" />
		</div>
		<div id="yes3" style="display: none">
			<html:text property="nextDueDate" styleClass="text"  value="${disbursalDataAuthor[0].nextDueDate}"
		   readonly="true" onchange="checkDueDate(value);checkRepayEffectiveDate(value);calTenureMonthForMaturityDateNextDueDateDIS();" />
		</div>	
		
		
		
		</logic:notEqual>
		</logic:present>									    	
		</logic:equal>
		<logic:equal name="disbursalDataNewList" property="repayMode" value="N">
		<div id="no3">
		<html:text property="nextDueDateNo" value="" styleClass="text"  disabled="true" />
		</div>
		</logic:equal>					
		</td>
		</tr>
		<!--   interest due date start here -->
		
		<tr>
		<td> <div id="Ino1">
			<bean:message key="lbl.interestDueDate" />
		</div>
		<div id="Iyes1" style="display: none">
			<bean:message key="lbl.interestDueDate" /><font color="red">*</font>
		     </div></td>
		  <td >
		   <logic:equal name="disbursalDataNewList" property="repayMode" value="I">
		   <logic:present name="fianlDisb">
		
		<logic:equal value="F" name="fianlDisb">
				
		<div id="Ino3" style="display: none;">
		<html:text property="interestDueDateNo" value="" styleClass="text" disabled="true" />
		</div>
		<div id="Iyes3" >
		<input type="hidden" name="hiddenIntDueDate" id="hiddenIntDueDate" value="${disbursalData[0].interestDueDate}"/>
		<html:text property="interestDueDate" styleClass="text"  value="${disbursalDataAuthor[0].interestDueDate}"   onchange="datevalidate();validateInterestDueDate();"     />
		</div>
		
		</logic:equal>
		<logic:notEqual value="F" name="fianlDisb">
		
		<div id="Ino3">
			<html:text property="interestDueDateNo" value="" styleClass="text"  disabled="true" />
		</div>
		<div id="Iyes3" style="display: none">
		<input type="hidden" name="hiddenIntDueDate" id="hiddenIntDueDate" value="${disbursalData[0].interestDueDate}"/>
			<html:text property="interestDueDate" styleClass="text"  value="${disbursalDataAuthor[0].interestDueDate}"
		     onchange="datevalidate();validateInterestDueDate();" />
		</div>	
		
		</logic:notEqual>
		</logic:present>									    	
		</logic:equal>
		<logic:equal name="disbursalDataNewList" property="repayMode" value="N">
		<div id="Ino3">
		<html:text property="interestDueDateNo" value="" styleClass="text"  disabled="true" />
		</div>
		</logic:equal>	
			</td>							
			
			<td nowrap="nowrap"><bean:message key="lbl.penalIntCalcDate" /></td>
			<td>
			<logic:equal name="disbursalDataNewList" property="repayMode" value="I">
			<div id="penal1" style="display: none">
			<html:text styleClass="text" property="penalIntCalcDate"
					readonly="true"  maxlength="10" value="<%=initiationDate %>"
						onchange="checkDate('penalIntCalcDate');" />
			</div>
			<div id="penal2" >
			<html:text styleClass="text" property="penalIntCalcDate" value=""
						 maxlength="10" onchange="checkDate('penalIntCalcDate');" disabled="true"/>
			</div>
			</logic:equal>
			<logic:notEqual name="disbursalDataNewList" property="repayMode" value="I">
			<div id="penal1" >
			<html:text styleClass="text" property="penalIntCalcDate"
						readonly="true" maxlength="10" value="${disbursalDataAuthor[0].penalIntCalcDate}"
						onchange="checkDate('penalIntCalcDate');" />
			</div>
			<div id="penal2" style="display: none">
			<html:text styleClass="text" property="penalIntCalcDate" value=""
						 maxlength="10" onchange="checkDate('penalIntCalcDate');" disabled="true"/>
			</div>
			</logic:notEqual>
			</td>
			</tr>
			<tr>
			<td nowrap="nowrap">
				<bean:message key="lbl.paymentFlag" />
			</td>
			<td>
			<html:select styleId="paymentFlag" property="paymentFlag" disabled="true" styleClass="text" value="${disbursalDataAuthor[0].paymentFlag}" onchange="displayHideTaPayment('paymentFlag')" >
			<html:option value="N">NO</html:option>
			<html:option value="P">Payment</html:option>
			<html:option value="T">TA Adjustment</html:option>
			</html:select>      
             </td>
	
	<!-- work started by amandeep -->
			<!-- Start here Brijesh Pathak -->
			 <logic:equal name="installmentType" value="S">
				<logic:equal name="editDueDate" value="Y">
					<input type="hidden" id="tenureMonths" name="tenureMonths" value="${requestScope.loanTenure}" /> 
					<td nowrap="nowrap"><bean:message key="lbl.maturityDate" /></td>
					<td>
					<div id="loanTenure" >
					<html:text styleClass="text" property="maturityDate1"
						styleId="maturityDate1" maxlength="10" value="${disbursalDataAuthor[0].maturityDate1}"
						onchange="calTenureMonthForMaturityDateDIS2();" />
			
			</div>
			</td>
				</logic:equal>
			</logic:equal>
			<!-- End here Brijesh Pathak -->
			</tr>
		<tr>
	<td nowrap="nowrap" width="25%">
				<bean:message key="lbl.loanCurtailment" />
			</td>
			<td nowrap="nowrap" width="25%">
				
					<logic:present name="loanCurt">
					<logic:equal value="Y" name="loanCurt">
				      <input type="hidden" id="loanCurtVal" value="Y" />                           
				  <input type="checkbox" name="loanCurtail"	id="loanCurtail"  disabled="disabled"  checked="checked" />
				</logic:equal>
				<logic:notEqual name="loanCurt" value="Y">
				<input type="hidden" id="loanCurtVal" value="N" />    
				<input type="checkbox" name="loanCurtail"
					 disabled="disabled" 	id="loanCurtail"  />
				</logic:notEqual>
					</logic:present>
			</td>
			</tr>
			<!-- work end by amandeep -->
	</table>
	</fieldset>
	</td>
	</tr>
	<tr>
	<td>
	<fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	   <tr id="taLoanNoTrDiv"  >
				<td nowrap="nowrap" width="25%">
					<bean:message key="lbl.taLoanNo" />
					
				</td>
				<td nowrap="nowrap" width="25%">
				<html:text styleClass="text" property="taLoanNo" styleId="taLoanNo" value="${disbursalDataAuthor[0].taLoanNo}" readonly="true" maxlength="20" />
				
				<input type="hidden" name="contextPath"	value="<%=request.getContextPath()%>" id="contextPath" />
				<html:hidden property="lbxTaLoanNoHID" styleId="lbxTaLoanNoHID" value="${disbursalDataAuthor[0].lbxTaLoanNoHID}" />
				<html:hidden property="hid" styleId="hid" value="" />
				<html:hidden property="balAmount" styleId="balAmount" value="" />
				</td>
				<td nowrap="nowrap" width="25%">
					<bean:message key="lbl.taCustomerName" />
				</td>
				<td nowrap="nowrap" width="25%">
					<html:text styleClass="text" property="taCustomerName"
						styleId="taCustomerName" maxlength="50" readonly="true"
						value="${disbursalDataAuthor[0].taCustomerName}" tabindex="-1" />
				</td>
				
			</tr>
	</table>
	</fieldset>
	</td>
	</tr>
		<tr>
	<td>
	<fieldset>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
		     <td><bean:message key="lbl.payTo"></bean:message></td>
	        <td>
	         <html:text property="payTo" styleId="payTo" readonly="true" styleClass="text" value="${disbursalDataAuthor[0].payTo}" tabindex="-1"/>
	        </td>
	        <td><bean:message key="lbl.payeeName"></bean:message></td>
	        <td>
	         <a rel="tooltip3" id="tool">
	                <html:text property="payeeName" styleId="payeeName" readonly="true" styleClass="text" value="${disbursalDataAuthor[0].payeeName}" onmouseover="applyToolTip(id);" onchange="insertValue();" tabindex="-1"/>
	                
	         </a>	
	        </td>
	   </tr>
	  <tr id="paymenttr1">  
		    <td width="25%"><bean:message key ="lbl.paymentMode"></bean:message><font color="red"></font>*</td>
			 <td width="25%"><span style="float:left;">
			
				<html:select property="paymentMode" styleId="paymentMode" disabled="true" styleClass="text" value="${disbursalDataAuthor[0].paymentMode}" onchange="fnNull();getAccountType();">
			 <html:option value="">--Select--</html:option> 
			  <html:option value="C">Cash</html:option> 
			 <html:option value="Q">Cheque</html:option> 
			 <html:option value="D">DD</html:option> 
			 <html:option value="N">NEFT</html:option> 
			 <html:option value="R">RTGS</html:option>	
			 <html:option value="S">ADJUSTMENT</html:option>	 		               
	           </html:select>
			
			 
			
	           <input type="hidden"  name="lbxpaymentMode" id="lbxpaymentMode" value=""/> 
			 </span></td>
			
		    <td width="25%"><bean:message key ="lbl.paymentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
			<td width="25%">
			<html:text property="paymentDate" styleClass="text" readonly="true"  maxlength="10" value="${disbursalDataAuthor[0].paymentDate}" onchange="return checkDate('paymentDate');"></html:text>
			</td>
		 	</tr>
		 			
		<tr id="paymenttr2">
			  <td><bean:message key="lbl.instrumentNumber"></bean:message><font color="red">*</font> </td>
			 <td><div style="float:left;">
			 <html:text property="instrumentNumber" readonly="true" styleClass="text" styleId="instrumentNumber" value="${disbursalDataAuthor[0].instrumentNumber}" maxlength="20"/>
			
			 </div>
			 <input type="hidden" name="instrumentID" id="instrumentID" value="" />
			</td>
			 <td><bean:message key="lbl.instrumentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font> </td>
	        <td>
	     <html:text property="instrumentDate" styleClass="text" readonly="true"  value="${disbursalDataAuthor[0].instrumentDate}" maxlength="10" onchange="return checkDate('instrumentDate');"></html:text>
			
	       
	           </td>				
		</tr>
		<tr id="paymenttr3">			 
		   <td><bean:message key="lbl.bankAccount"></bean:message><font color="red">*</font></td>
			 <td><div style="float:left;"> 
			 
	          <html:text styleClass="text" property="bankAccount" styleId="bankAccount" maxlength="50"  value="${disbursalDataAuthor[0].bankAccount}" readonly="true" tabindex="-1"/>
              <input type="hidden"  name="lbxbankAccountID" id="lbxbankAccountID" value="${disbursalDataAuthor[0].lbxbankAccountID}"/>
			    
			    <input type="hidden" name="hid1" id="hid1"/> 
			    <input type="hidden" name="hid" id="hid"/> 
				</div></td>
		    <td><bean:message key="lbl.bank"></bean:message></td>
			<td>
			<div style="float:left;"> 
		
				<html:text styleClass="text" property="bank" styleId="bank" maxlength="50"  value="${disbursalDataAuthor[0].bank}" readonly="true" tabindex="-1"/>
				
		
			 
		    <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${disbursalDataAuthor[0].lbxBankID}"/> 
			</div></td>
		</tr> 
		<tr id="paymenttr4">	
			<td><bean:message key="lbl.branch"></bean:message></td>
				<td>

				<html:text styleClass="text" property="branch" styleId="branch" maxlength="50"  value="${disbursalDataAuthor[0].branch}" readonly="true" tabindex="-1"/>
				
				  <html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${disbursalDataAuthor[0].lbxBranchID}"/>
		   		</td>	 
				<td><bean:message key="lbl.micr"></bean:message></td>
				<td>
				
				<html:text property="micr" styleClass="text" styleId="micr" value="${disbursalDataAuthor[0].micr}" maxlength="20" readonly="true" tabindex="-1"></html:text>
				
				
				</td>
		   
	   </tr>
	    <tr id="paymenttr5">
	         <td> <bean:message key="lbl.ifsCode"></bean:message></td>
			 <td><div style="float:left;">
			 
				<html:text property="ifsCode" styleId="ifsCode" styleClass="text" value="${disbursalDataAuthor[0].ifsCode}" maxlength="20" readonly="true" tabindex="-1"></html:text>
				
	         </div></td>
	         <td><bean:message key="lbl.paymentAmount"></bean:message><font color="red">*</font> </td>
			 <td>
			
			<html:text property="paymentAmount" styleClass="text" styleId="paymentAmount" value="${disbursalDataAuthor[0].paymentAmount}" readonly="true"
			 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" />
			
			 </td>
	    </tr>
	    
			<!-- Virender Code Start -->
			<tr>
				<td>
					<bean:message key="lbl.beneficiary_bank" />
				</td>
				<td>
					<html:text property="beneficiaryBankCode" styleId="beneficiaryBankCode" readonly="true" styleClass="text" value="${disbursalDataAuthor[0].beneficiaryBankCode}" tabindex="-1" />
					 <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();
					 openLOVCommon('19243','disbursalMakerForm','beneficiaryBankCode','','beneficiaryBankBranchName', 'beneficiaryLbxBranchID','','','beneficiaryLbxBankID');" value=" " styleClass="lovbutton"> </html:button>
					<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
					<html:hidden property="beneficiaryLbxBankID" styleId="beneficiaryLbxBankID" value="${disbursalDataAuthor[0].beneficiaryLbxBankID}"/>
				</td>
		
				<td>
					<bean:message key="lbl.beneficiaryBankBranchName" />									
				</td>
				
				<td>
					<html:text property="beneficiaryBankBranchName" styleId="beneficiaryBankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${disbursalDataAuthor[0].beneficiaryBankBranchName}" />
                    <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="
                    openLOVCommon(19244,'disbursalMakerForm','beneficiaryBankBranchName','beneficiaryBankCode','beneficiaryLbxBranchID', 'beneficiaryLbxBankID','Please Select Bank','','micrCode','beneficiaryIfscCode','beneficiaryLbxBranchID');closeLovCallonLov('beneficiaryBankCode');" value=" " styleClass="lovbutton"> </html:button>
			    	<html:hidden property="beneficiaryLbxBranchID" styleId="beneficiaryLbxBranchID" value="${disbursalDataAuthor[0].beneficiaryLbxBranchID}"  />
			    	<input type="hidden" name="micrCode" value="" id="micrCode" />
			  </td>
				
			</tr>
			
			<tr>
				<td>
					<bean:message key="lbl.beneficiaryAccountNo"  />								
				</td>
				<td>
					<html:text property="beneficiaryAccountNo" styleId="beneficiaryAccountNo" tabindex="-1" onblur="fnChangeCase('CustBankDetailsForm','accountNo');this.value=removeSpaces1(this.value);" styleClass="text"  value="${disbursalDataAuthor[0].beneficiaryAccountNo}"  />
				</td>	
			
				<td>
					<bean:message key="lbl.beneficiaryIfscCode" />
				</td>
				<td>
					<html:text property="beneficiaryIfscCode" styleId="beneficiaryIfscCode" styleClass="text" readonly="true" maxlength="20" value="${disbursalDataAuthor[0].beneficiaryIfscCode}"  />
				</td>	
			</tr>
	
	     <tr id="paymenttr6">
		      <td><bean:message key="lbl.tdsAmount"></bean:message></td>
	        <td>
	     
			<html:text property="tdsAmount" styleId="tdsAmount" styleClass="text" value="${disbursalDataAuthor[0].tdsAmount}"  readonly="true"
	         onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" />
			        
	        </td>
	         <td><bean:message key="lbl.remark"></bean:message></td>
	         <td>
	        <html:textarea property="remarks" styleId="remarks" readonly="true" value="${disbursalDataAuthor[0].remarks}" styleClass="text"></html:textarea>      
			</td>
	   
	     </tr>
	</table>
	</fieldset>
	</td>
	</tr>
	<tr><td>
   <!--  	<button type="button" name="EMI" id="EMI" 
		class="topformbutton4" title="Alt+E" accesskey="E"
		onclick="return calculateEMI()"><bean:message key="button.calculatePreEMI" /></button>
		-->
	<button type="button" class="topformbutton4" title="Alt+R" accesskey="R"
		onclick="return viewReceivableDisbursal('<bean:message key="msg.LoanAccountNo" />');" ><span class='underLine'>R</span>eceivable/Payable</button>
	
	<button type="button" class="topformbutton4" title="Alt+I" accesskey="I"
		onclick="return openOldInstallmentPlan('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.oldInstallment" /></button>									
	
	<button type="button" class="topformbutton4" title="Alt+N" accesskey="N"
		onclick="return openNewInstallmentPlan('<bean:message key="msg.LoanAccountNo" />','DIA');" ><bean:message key="button.newInstallment" /></button>
	
	<button type="button" class="topformbutton4" title="Alt+C" accesskey="C"
		onclick="return openOtherChargePlanAtDisbrsal('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.otherChargesPlan" /></button>			
	
	<button type="button" name="oldDisbursal" id="oldDisbursal"
		class="topformbutton4" title="Alt+O" accesskey="O"
		onclick="return openDisbursalSchedule();"><bean:message key="button.olddisbursal" /></button>
	
	<button type="button" name="specialCondButton" id="specialCondButton"
		class="topformbutton4" title="Alt+S" accesskey="S"
		onclick="return viewSpecialCondButton('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.specialCondition" /></button>
	<button type="button" name="generateRepaymentSchOld" id="generateRepaymentSchOld" title="Alt+R" accesskey="R"
		class="topformbutton4" onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key="button.oldrepay" /></button>
		
		<logic:equal value="F" name="fianlDisb">
	<button type="button" name="generateRepaymentSch"
			id="generateRepaymentSch"
			 title="Alt+R" accesskey="R"
			class="topformbutton4" onclick="return callRepay();" ><bean:message key="button.newrepay" /></button>
		</logic:equal>
		<logic:notEqual name="fianlDisb" value="F">
	<button type="button" name="generateRepaymentSch"
			id="generateRepaymentSch"
			 title="Alt+R" accesskey="R"
			class="topformbutton4" disabled="disabled" ><bean:message key="button.newrepay" /></button>	
		</logic:notEqual>
	

   </td>
	</tr>
	</logic:iterate>
	</table>

</logic:present>
<!-- Author part code end   here -->
<br/>
<logic:present name="disbursalPaymentAddDtl">
<fieldset>
<legend><bean:message key="lbl.addDisDetails"/></legend>
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="listTable">
    <tr class="white2">
       <td><logic:notPresent name="disbursalDataAuthor">
       <input type="checkbox" name="allChk" id="allChk" onclick="allCheck()"/>
       </logic:notPresent>
       <logic:present name="disbursalDataAuthor">
       <input type="checkbox" name="allChk" id="allChk" disabled="disabled" onclick="allCheck()"/>
       </logic:present>
       </td>
        <td><b><bean:message key="lbl.loanNo"/></b></td>
		<td><strong><bean:message key="lbl.disbursalNo"/></strong></td>
		<td><strong><bean:message key="lbl.disbursalTo"/></strong></td>
		<td><strong><bean:message key="lbl.disAmount"/></strong></td>
		<td><b><bean:message key="lbl.makerRemark"/></b>
		<input type="hidden" name="loanAmtF" id="loanAmtF" value="${loanAndDisbAmountList[0].loanAmt}"/>
		<input type="hidden" name="disbursedAmountF" id="disbursedAmountF" value="${loanAndDisbAmountList[0].disbursedAmount}"/>
		</td>
		</tr>
	<logic:iterate id="disbursalPaymentAddDtlObj" name="disbursalPaymentAddDtl" indexId="count">
	<tr class="white1">
	<td>
	<logic:notPresent name="disbursalDataAuthor">
     <input type="checkbox" name="chk" id="chk<%=count.intValue()+1 %>" value="${disbursalPaymentAddDtlObj.loanDisbursalId}" />
       </logic:notPresent>
       <logic:present name="disbursalDataAuthor">
    <input type="checkbox" name="chk" id="chk<%=count.intValue()+1 %>" disabled="disabled"/>
       </logic:present>
	 <input type="hidden" name="addId" id="addId<%=count.intValue()+1 %>" />
	</td>
      <td>${disbursalPaymentAddDtlObj.loanNo}
      <input type="hidden" name="loanId" id="loanId<%=count.intValue()+1 %>" value="${disbursalPaymentAddDtlObj.lbxLoanNoHID}"/>
      </td>
      <td>${disbursalPaymentAddDtlObj.disbursalNo}</td>
      <td>${disbursalPaymentAddDtlObj.disbursalTo}</td>
     <td>${disbursalPaymentAddDtlObj.disbursalAmount}
     <input type="hidden" name="loanDisbId" id="loanDisbId<%=count.intValue()+1 %>" value="${disbursalPaymentAddDtlObj.loanDisbursalId}"/>
     <input type="hidden" name="addDisbursalAmount" id="addDisbursalAmount<%=count.intValue()+1 %>" value="${disbursalPaymentAddDtlObj.disbursalAmount}"/>
     <input type="hidden" name="finDisbursal" id="finDisbursal<%=count.intValue()+1 %>" value="${disbursalPaymentAddDtlObj.finalDisbursal}"/>
     </td>
     <td>${disbursalPaymentAddDtlObj.disbursalDescription}</td>
    
   </tr>
   </logic:iterate>
 </table> 
</td>
</tr>
<tr>
<td>

<logic:notPresent name="disbursalInitionAuthor">
<logic:notPresent name="disbursalDataAuthor">
<button type="button" name="delete"	id="delete" class="topformbutton2"
    title="Alt+D" accesskey="D"  onclick="return deleteAddDetail('');" ><bean:message key="button.delete" /></button>
</logic:notPresent>
</logic:notPresent>
</td>
</tr>
</table>

</fieldset>
</logic:present>
	
</fieldset>
<logic:present name="message">
<script type="text/javascript">
if('<%=request.getAttribute("message").toString()%>'=='S')
{
	alert('<bean:message key="msg.DataSaved" />');
	
}else if('<%=request.getAttribute("message").toString()%>'=='F')
{
	alert('Data Forwarded Successfully');
	location.href="<%=request.getContextPath()%>/disbursalSearch.do?method=searchDisbursalMakerLnik";
	
}
else if('<%=request.getAttribute("message").toString()%>'=='E')
{
	alert('<bean:message key="msg.DataNotSaved" />');
}

else if('<%=request.getAttribute("message").toString()%>'=='LE')
{
	alert('Total of cash payment amount exceeds the cash payment limit per loan.');
}
else if('<%=request.getAttribute("message").toString()%>'=='D')
{
	alert('<bean:message key="lbl.dataDeleted" />');
	
}
else if('<%=request.getAttribute("message").toString()%>'=='CAPTURELEAD')
{
	alert("Please Capture Lead Repayment Schedule First.");
	
}
else if('<%=request.getAttribute("message").toString()%>'=='AMTNOTMATCH')
{
	alert("Loan Amount contribution of Lead partner is not equal to total Principal Amount entered in RSP Repayment Schedule.");
		
}
else if('<%=request.getAttribute("totalRecv")%>'=='Y')
{
	alert('<bean:message key="msg.adjustReceivable" />');
	
	
}
else if('<%=request.getAttribute("message").toString()%>'=='SancLimitExceed')
{
	alert("DISBURSAL AMOUNT SHOULD BE LESS THAN DEAL SANCTION AMOUNT.");
		
}

</script>
</logic:present>
<logic:present name="disbStatus">
	<script type="text/javascript">
if('<%=request.getAttribute("disbStatus").toString()%>'=='DisbAmt')
{
	alert('<bean:message key="msg.DisbursalAmtBig"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='DisbAmtRV')
{
	alert('<bean:message key="msg.DisbursalAmtBig"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='saveFwdFlag')
{
	alert('<bean:message key="msg.saveFwdFlag"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='ShrtPayAmt')
{
	alert('<bean:message key="msg.ShortPayAmtBig"/>');
	document.getElementById("save").removeAttribute("disabled","true");
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='LnBalPrinMoreThanLnAmt')
{
	alert('<bean:message key="msg.LnBalPrinMoreThanLnAmt"/>');
	document.getElementById("save").removeAttribute("disabled","true");
}

else if('<%=request.getAttribute("disbStatus").toString()%>'=='preDisbDate')
{
	alert('<bean:message key="msg.PreviousDisbDateAfter"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='preProposedDisbDate')
{
	alert('<bean:message key="msg.PreviousProposedDisbDate"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='disbursalAmount')
{
	alert('<bean:message key="msg.disbursalAmountSch"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='disbursalAmtForNonInst')
{
	alert('<bean:message key="msg.disbursalAmtForNonInst"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='groupExposure')
{
	alert('<bean:message key="msg.groupExposure"/>');
}
else if('<%=request.getAttribute("disbStatus").toString()%>'=='customerExposure')
{
	alert('<bean:message key="msg.customerExposure"/>');
}
else if('<%=request.getAttribute("disbStatus")%>'=='Locked')
{
	alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
}
else if('<%=request.getAttribute("disbStatus")%>'=='PRSDocs' && '<%=request.getAttribute("chkPRSPRDDocsFlag")%>'=='Y')
{
	alert('<bean:message key="msg.docsPRS"/>');
}
else if('<%=request.getAttribute("disbStatus")%>'=='PRDDocs'&& '<%=request.getAttribute("chkPRSPRDDocsFlag")%>'=='Y')
{
	alert('<bean:message key="msg.docsPRD"/>');
}
else if('<%=request.getAttribute("disbStatus")%>'=='PRSDocs' && '<%=request.getAttribute("chkPRSPRDDocsFlag")%>'=='N')
{
	var flag=confirm('<bean:message key="msg.docsPRSPRDNotReceived"/>');
	var docs="N";
	var recStatus="F";
	if(flag){
	document.getElementById("disbursalMakerForm").action="disbursalMaker.do?method=updateDisbursalDataWithPayment&docs="+docs+"&recStatus="+recStatus;
    document.getElementById("disbursalMakerForm").submit();
	}
}
else if('<%=request.getAttribute("disbStatus")%>'=='PRDDocs'&& '<%=request.getAttribute("chkPRSPRDDocsFlag")%>'=='N')
{
	var flag=confirm('<bean:message key="msg.docsPRSPRDNotReceived"/>');
	var docs="N";
	var recStatus="F";
	if(flag){
	document.getElementById("disbursalMakerForm").action="disbursalMaker.do?method=updateDisbursalDataWithPayment&docs="+docs+"&recStatus="+recStatus;
   	document.getElementById("disbursalMakerForm").submit();
	}
}else if('<%=request.getAttribute("disbStatus")%>'=='balAmount'){
alert('Balance Amount is not available for this TA Loan');
}else if('<%=request.getAttribute("disbStatus")%>'=='totalReceivable'){
alert('Allocate Total Receivable While Disbursal is Final');
}	
</script>
</logic:present>
<logic:present name="specialConditionStatus">
<script type="text/javascript">
	alert('<bean:message key="lbl.updateSpecialCondition" />');
</script>
</logic:present>
<!-- NISHANT SPACE STARTS -->
<logic:present name="newInstPlanStatus">
<script type="text/javascript">
	alert('<bean:message key="msg.instPlan" />');
</script>
</logic:present>
<!-- NISHANT SPACE ENDS -->
<!-- add by saorabh -->
<logic:present name="disbursalDate">
		<script type="text/javascript">
		if('<%=request.getAttribute("disbursalDate").toString()%>'=='F')
		{
			alert('<bean:message key="msg.disbursalDateCheck" />');
		}
				
		</script>
	</logic:present>
<!-- end by saorabh -->
<!-- start:added by indrajeet -->
<%-- <logic:present name="sblGblFlag">
if('<%=request.getAttribute("sblGblFlag").toString()%>'=='Y' && '<%=request.getAttribute("customerType").toString()%>'=='C')
{
	alert('<bean:message key="msg.groupExposure"/>');
}
else if('<%=request.getAttribute("sblGblFlag").toString()%>'=='Y' && '<%=request.getAttribute("customerType").toString()%>'=='I')
{
	alert('<bean:message key="msg.customerExposure"/>');
}
</logic:present> --%>
<!-- end:added by indrajeet -->
<logic:present name="sblFlag">
		<script type="text/javascript">
			alert('Check Single Borrower Limit');
		</script>
</logic:present>
<logic:present name="gblFlag">
		<script type="text/javascript">
			alert('Check Group Borrower Limit');
		</script>
</logic:present>
</html:form>
</body>
</html:html>

