<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	UserObject userobj = (UserObject) session
			.getAttribute("userobject");
	String initiationDate = userobj.getBusinessdate();
%>

<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	
	<!-- css for Datepicker -->
	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css"
		rel="stylesheet" />
	<!-- jQuery for Datepicker -->

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>

	<!--[if gte IE 5]>
	<style type="text/css">
	
	.white {
	background:repeat-x scroll left bottom #FFFFFF;
	filter:progid:DXImagetransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	.white2 {
	background:repeat-x scroll left bottom #CDD6DD;
	filter:progid:DXImagetransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	</style>
	<![endif]-->
	<script type="text/javascript">
	function fntab()
{
     if(document.getElementById('modifyRecord').value =='CM')	
     {
    	 document.getElementById('closureForm').requestNumber.focus();
     }
     else if(document.getElementById('modifyRecord').value =='N')
     {
    	document.getElementById('closureForm').loanClosureLOV.focus();
     }
    
     return true;
}
	</script>
</head>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
<script type="text/javascript" 
	src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>


<body onload="enableAnchor();fntab();init_fields();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllWindowCallUnloadBody();">

	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
	<div id="centercolumn">
		<div id="revisedcontainer">
		<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />



			<logic:present name="closureData">
	
				<html:form action="/earlyClosure" styleId="closureForm"
					method="post">
					<fieldset>
						<fieldset>
							
							<LEGEND>
								<bean:message key="lbl.earlyClosure" />
							</LEGEND>
							
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<html:hidden property="closureType" styleId="closureType"
									value="T" />
							
								<tr>

									<td width="25%">
										<bean:message key="lbl.loanAc" /><font color="red">*</font>
									</td>
									<td width="25%">
										<html:text styleClass="text" styleId="loanAc" maxlength="20"
											property="loanAc" value="${closureData[0].loanAc}"
											disabled="true" tabindex="-1"/>
										<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
											value="${closureData[0].lbxLoanNoHID}" />
										<input type="hidden" name="contextPath" id="contextPath"
											value="<%=request.getContextPath()%>" />
									</td>

									<td width="25%">
										<bean:message key="lbl.customerName" />
									</td>
									<td width="25%">
										<html:text styleClass="text" styleId="customerName"
											maxlength="50" disabled="true" property="customerName"
											value="${closureData[0].customerName}" tabindex="-1"/>
									</td>
								</tr>

								<tr>
									<td>
										<bean:message key="lbl.initiationDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="initDate"
											maxlength="10" disabled="true" property="initiationDate"
											value="<%=initiationDate %>" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.loanDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="loanDate" maxlength="10"
											disabled="true" property="loanDate"
											value="${closureData[0].loanDate}" tabindex="-1"/>
									</td>
								</tr>

								<tr>
									<td>
										<bean:message key="lbl.scheme" />
									</td>
									<td>
										<html:text styleClass="text" styleId="scheme" disabled="true"
											maxlength="50" property="scheme"
											value="${closureData[0].scheme}" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.product" />
									</td>
									<td>
										<html:text styleClass="text" styleId="product" disabled="true"
											maxlength="50" property="product"
											value="${closureData[0].product}" tabindex="-1"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.originalTenure" />
									</td>
									<td>
										<html:text styleClass="text" styleId="originalTenure"
											disabled="true" maxlength="4" property="originalTenure"
											value="${closureData[0].originalTenure}" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.frequency" />
									</td>
									<td>
										<html:text styleClass="text" styleId="frequency"
											disabled="true" maxlength="10" property="frequency"
											value="${closureData[0].frequency}" tabindex="-1"/>
									</td>
								</tr>

								<tr>
									<td>
										<bean:message key="lbl.maturityDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="maturtyDate"
											disabled="true" maxlength="10" property="maturityDate"
											value="${closureData[0].maturityDate}" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.remainingTenure" />
									</td>
									<td>
										<html:text styleClass="text" styleId="remainingTenure"
											disabled="true" maxlength="4" property="remainingTenure"
											value="${closureData[1].remainingTenure}" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.remainingInstallments"/>
									</td>
									<td>
										<html:text property="remainingInstallments" styleClass="text"
											styleId="remainingInstallments" maxlength="10"
											disabled="true" tabindex="-1" style="text-align:right;"
											value="${closureData[0].remainingInstallments}"/>
									</td>
									<td>
										<bean:message key="lbl.requestNumber" />
									</td>
									<td>
										<html:text styleClass="text" styleId="requestNumber"
											maxlength="10" property="requestNumber"
											value="${closureData[0].requestNumber}"
											disabled="true" tabindex="-1"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.effectiveDate" /><font color="red">*</font>
									</td>
									<td>
										<html:text styleClass="text" styleId="effectiveDate"
											maxlength="10" property="effectiveDate" value=""
											onchange="checkDate('effectiveDate');checkMaturityDateForMatClo();" />
									</td>
									<td>
										<bean:message key="lbl.reasonForClosure" /><font color="red">*</font>
									</td>
									<td>
										<html:text styleClass="text" styleId="reasonForClosure"
											maxlength="1000" property="reasonForClosure"
											value="${closureData[0].reasonForClosure}"
											disabled="true" tabindex="-1"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<html:button property="getDetail" styleId="getDetail"
											value="Get Detail" styleClass="topformbutton2" 
											accesskey="D" title="Alt+D"
											onclick="return getDetailsfornew();" />

									</td>
									
									<td nowrap="nowrap">
										<bean:message key="lbl.authorRemarks" />
									</td>
									<td nowrap="nowrap">
										<html:textarea styleClass="text" property="authorRemarks" disabled="true"
											value="${closureData[0].authorRemarks}" />
									</td>
								</tr>
							</table>
						</fieldset>

						<fieldset>
							<legend>
								<bean:message key="lbl.duesAndRefunds" />
							</legend>
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr>
									<td width="25%"></td>
									<td width="25%" style="padding-left:40px">
										<b><bean:message key="lbl.dues" /></b>
									</td>
									<td width="25%"></td>
									<td width="25%" style="padding-left:40px">
										<b><bean:message key="lbl.refunds" /></b>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.balancePrincipal" />
									</td>
									<td>
										<html:text styleClass="text" styleId="balancePrincipal"
											disabled="true"
											
											maxlength="18" property="balancePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDeposit" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDeposit"
											disabled="true"
											
											maxlength="18" property="secureDeposit" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.overduePrincipal"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="overduePrincipal"
											disabled="true"
											maxlength="18" property="overduePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositInterest"
											
											disabled="true" maxlength="18"
											property="secureDepositInterest" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.overdueInstallments" />
									</td>
									<td>
										<html:text styleClass="text" styleId="overdueInstallments"
											disabled="true"
											
											maxlength="18" property="overdueInstallments" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositTDS"
											
											disabled="true" maxlength="18" property="secureDepositTDS" 
											tabindex="-1" style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.interestTillDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="interestTillDate"
											
											disabled="true" maxlength="18" property="interestTillDate" 
											tabindex="-1" style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDInterest"
										
											disabled="true" maxlength="18" property="gapSDInterest" 
											tabindex="-1" style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.unBilledInstallments" />
									</td>
									<td>
										<html:text property="unBilledInstallments"
											styleId="unBilledInstallments" styleClass="text"
											
											maxlength="18" disabled="true" tabindex="-1" 
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDTDS"
											
											property="gapSDTDS" disabled="true" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.otherDues" />
									</td>
									<td>
										<html:text styleClass="text" styleId="otherDues"
											disabled="true" 
											maxlength="18" property="otherDues" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.otherRefunds" />
									</td>
									<td>
										<html:text styleClass="text" styleId="otherRefunds"
											disabled="true"
											 maxlength="18"
											property="otherRefunds" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.foreClosurePenalty" />
									</td>
									<td>
										<html:text styleClass="text" styleId="foreClosurePenalty"
											
											maxlength="18" property="foreClosurePenalty" disabled="true" 
											tabindex="-1" style="text-align:right;"/>
									</td>
									<!-- Change  advanceEmiRefunds Added By Arun  -->
									<td>
									<bean:message key="lbl.advanceEmiRefunds" />
									</td>
									<td><html:text styleClass="text" styleId="advanceEmiRefunds"
											value="" maxlength="18" property="advanceEmiRefunds"
											style="text-align:right;" readonly="true" tabindex="-1"/>
									</td>
									
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.lppAmount"/>
									</td>
									<td>
										<html:text property="lppAmount" styleId="lppAmount" 
											styleClass="text" maxlength="18" readonly="true"
											tabindex="-1" 
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.waiveOffAmount"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="waiveOffAmount"
											
											maxlength="18" property="waiveOffAmount" tabindex="-1"
											style="text-align:right;" disabled="true"/>
									</td>
									
								</tr>
								<tr>
								<td>
									<bean:message key="lbl.interstTillLP"/>
								</td>
								<td>
									<html:text property="interstTillLP" styleId="interstTillLP"
										styleClass="text" 
										readonly="true" maxlength="18" tabindex="-1"
										style="text-align:right;"/>
								</td>
								<td>
										
										<bean:message key="lbl.netReceivablePayable" />
									</td>
									<td><html:text styleClass="text" styleId="netReceivablePayable"
											
											disabled="true" maxlength="18" tabindex="-1"
											property="netReceivablePayable" style="text-align:right;"/>
									</td>
								</tr>
							</table>
						</fieldset>
						<table>
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td>
									<button type="button"  class="topformbutton3" accesskey="P" title="Alt+P"
										onclick="return viewPayableEarly('<bean:message key="msg.LoanAccountNo" />');" >
										<bean:message key="button.viewPayable"/></button>
									<button type="button" 
										class="topformbutton3" accesskey="R" title="Alt+R"
										onclick="return viewReceivableEarly('<bean:message key="msg.LoanAccountNo" />');" >
									<bean:message key="button.viewRecievable"/></button>
							
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
				
			</logic:present>
			
			<!-- Changes by Amit Starts -->
			<logic:present name="closureDataDisabled">
	
				<html:form action="/earlyClosure" styleId="closureForm"
					method="post">
					<fieldset>
						<fieldset>
							<logic:present name="earlyClosureLabel">
							<LEGEND>
								<bean:message key="lbl.earlyClosure" />
							</LEGEND>
							</logic:present>
							<logic:present name="maturityClosureLabel">
							<LEGEND>
								<bean:message key="lbl.maturityClosure" />
							</LEGEND>
							</logic:present>
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<html:hidden property="closureType" styleId="closureType"
									value="${requestScope.type}" />
								<html:hidden property="terminationId" styleId="terminationId"
									value="${closureDataDisabled[0].terminationId}" />
								<tr>

									<td width="25%">
										<bean:message key="lbl.loanAc" /><font color="red">*</font>
									</td>
									<td width="25%">
										<html:text styleClass="text" styleId="loanAc" maxlength="20"
											property="loanAc" value="${closureDataDisabled[0].loanAc}"
											disabled="true" tabindex="-1"/>
										<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
											value="${closureDataDisabled[0].lbxLoanNoHID}" />
										<input type="hidden" name="contextPath" id="contextPath"
											value="<%=request.getContextPath()%>" />
									</td>

									<td width="25%">
										<bean:message key="lbl.customerName" />
									</td>
									<td width="25%">
										<html:text styleClass="text" styleId="customerName"
											maxlength="50" disabled="true" property="customerName"
											value="${closureDataDisabled[0].customerName}" tabindex="-1"/>
									</td>
								</tr>

								<tr>
									<td>
										<bean:message key="lbl.initiationDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="initDate"
											maxlength="10" disabled="true" property="initiationDate"
											value="<%=initiationDate %>" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.loanDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="loanDate" maxlength="10"
											disabled="true" property="loanDate"
											value="${closureDataDisabled[0].loanDate}" tabindex="-1"/>
									</td>
								</tr>

								<tr>
									<td>
										<bean:message key="lbl.scheme" />
									</td>
									<td>
										<html:text styleClass="text" styleId="scheme" disabled="true"
											maxlength="50" property="scheme"
											value="${closureDataDisabled[0].scheme}" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.product" />
									</td>
									<td>
										<html:text styleClass="text" styleId="product" disabled="true"
											maxlength="50" property="product"
											value="${closureDataDisabled[0].product}" tabindex="-1"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.originalTenure" />
									</td>
									<td>
										<html:text styleClass="text" styleId="originalTenure"
											disabled="true" maxlength="4" property="originalTenure"
											value="${closureDataDisabled[0].originalTenure}" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.frequency" />
									</td>
									<td>
										<html:text styleClass="text" styleId="frequency"
											disabled="true" maxlength="10" property="frequency"
											value="${closureDataDisabled[0].frequency}" tabindex="-1"/>
									</td>
								</tr>

								<tr>
									<td>
										<bean:message key="lbl.maturityDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="maturtyDate"
											disabled="true" maxlength="10" property="maturityDate"
											value="${closureDataDisabled[0].maturityDate}" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.remainingTenure" />
									</td>
									<td>
										<html:text styleClass="text" styleId="remainingTenure"
											disabled="true" maxlength="4" property="remainingTenure"
											value="${closureDataDisabled[1].remainingTenure}" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.remainingInstallments"/>
									</td>
									<td>
										<html:text property="remainingInstallments" styleClass="text"
											styleId="remainingInstallments" maxlength="10"
											disabled="true" tabindex="-1" style="text-align:right;"
											value="${closureDataDisabled[0].remainingInstallments}"/>
									</td>
									<td>
										<bean:message key="lbl.requestNumber" />
									</td>
									<td>
										<html:text styleClass="text" styleId="requestNumber"
											maxlength="10" property="requestNumber"
											value="${closureDataDisabled[0].requestNumber}"
											disabled="true" tabindex="-1"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.effectiveDate" /><font color="red">*</font>
									</td>
									<td>
										<html:text styleClass="text" styleId="effDate"
											maxlength="10" property="effectiveDate"
											value="${closureDataDisabled[0].effectiveDate}"
											disabled="true" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.reasonForClosure" /><font color="red">*</font>
									</td>
									<td>
										<html:text styleClass="text" styleId="reasonForClosure"
											maxlength="1000" property="reasonForClosure"
											value="${closureDataDisabled[0].reasonForClosure}"
											disabled="true" tabindex="-1"/>
									</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td nowrap="nowrap">
										<bean:message key="lbl.authorRemarks" />
									</td>
									<td nowrap="nowrap">
										<html:textarea styleClass="text" property="authorRemarks" disabled="true"
											value="${closureDataDisabled[0].authorRemarks}" />
									</td>
								</tr>
							</table>
						</fieldset>

						<fieldset>
							<legend>
								<bean:message key="lbl.duesAndRefunds" />
							</legend>
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr>
									<td width="25%"></td>
									<td width="25%" style="padding-left:40px">
										<b><bean:message key="lbl.dues" /></b>
									</td>
									<td width="25%"></td>
									<td width="25%" style="padding-left:40px">
										<b><bean:message key="lbl.refunds" /></b>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.balancePrincipal" />
									</td>
									<td>
										<html:text styleClass="text" styleId="balancePrincipal"
											disabled="true"
											value="${closureDataDisabled[0].balancePrincipal}"
											maxlength="18" property="balancePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDeposit" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDeposit"
											disabled="true"
											value="${closureDataDisabled[0].secureDeposit}"
											maxlength="18" property="secureDeposit" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.overduePrincipal"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="overduePrincipal"
											disabled="true" value="${closureDataDisabled[0].overduePrincipal}"
											maxlength="18" property="overduePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositInterest"
											value="${closureDataDisabled[0].secureDepositInterest}"
											disabled="true" maxlength="18"
											property="secureDepositInterest" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.overdueInstallments" />
									</td>
									<td>
										<html:text styleClass="text" styleId="overdueInstallments"
											disabled="true"
											value="${closureDataDisabled[0].overdueInstallments}"
											maxlength="18" property="overdueInstallments" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositTDS"
											value="${closureDataDisabled[0].secureDepositTDS}"
											disabled="true" maxlength="18" property="secureDepositTDS" 
											tabindex="-1" style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.interestTillDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="interestTillDate"
											value="${closureDataDisabled[0].interestTillDate}"
											disabled="true" maxlength="18" property="interestTillDate" 
											tabindex="-1" style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDInterest"
											value="${closureDataDisabled[0].gapSDInterest}"
											disabled="true" maxlength="18" property="gapSDInterest" 
											tabindex="-1" style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.unBilledInstallments" />
									</td>
									<td>
										<html:text property="unBilledInstallments"
											styleId="unBilledInstallments" styleClass="text"
											value="${closureDataDisabled[0].unBilledInstallments}"
											maxlength="18" disabled="true" tabindex="-1" 
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDTDS"
											value="${closureDataDisabled[0].gapSDTDS}" maxlength="18"
											property="gapSDTDS" disabled="true" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.otherDues" />
									</td>
									<td>
										<html:text styleClass="text" styleId="otherDues"
											disabled="true" value="${closureDataDisabled[0].otherDues}"
											maxlength="18" property="otherDues" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.otherRefunds" />
									</td>
									<td>
										<html:text styleClass="text" styleId="otherRefunds"
											disabled="true"
											value="${closureDataDisabled[0].otherRefunds}" maxlength="18"
											property="otherRefunds" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.foreClosurePenalty" />
									</td>
									<td>
										<html:text styleClass="text" styleId="foreClosurePenalty"
											value="${closureDataDisabled[0].foreClosurePenalty}"
											maxlength="18" property="foreClosurePenalty" disabled="true" 
											tabindex="-1" style="text-align:right;"/>
									</td>
									<!-- Change  advanceEmiRefunds Added By Arun  -->
									<td>
									<bean:message key="lbl.advanceEmiRefunds" />
									</td>
									<td><html:text styleClass="text" styleId="advanceEmiRefunds"
											value="${closureDataDisabled[0].advanceEmiRefunds}" maxlength="18" property="advanceEmiRefunds" 
											style="text-align:right;" readonly="true" tabindex="-1"/>
									</td>
											
									
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.lppAmount"/>
									</td>
									<td>
										<html:text property="lppAmount" styleId="lppAmount" 
											styleClass="text" maxlength="18" readonly="true"
											tabindex="-1" value="${closureDataDisabled[0].lppAmount}"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.waiveOffAmount"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="waiveOffAmount"
											value="${closureDataDisabled[0].waiveOffAmount}"
											maxlength="18" property="waiveOffAmount" tabindex="-1"
											style="text-align:right;" disabled="true"/>
									</td>
								
								</tr>
								<tr>
								<td>
									<bean:message key="lbl.interstTillLP"/>
								</td>
								<td>
									<html:text property="interstTillLP" styleId="interstTillLP"
										styleClass="text" value="${closureDataDisabled[0].interstTillLP}" 
										readonly="true" maxlength="18" tabindex="-1"
										style="text-align:right;"/>
								</td>
								<td>
									<bean:message key="lbl.netReceivablePayable" />
									</td>
									<td><html:text styleClass="text" styleId="netReceivablePayable"
											value="${closureDataDisabled[0].netReceivablePayable}"
											disabled="true" maxlength="18" tabindex="-1"
											property="netReceivablePayable" style="text-align:right;"/>
									</td>
								</tr>
							</table>
						</fieldset>
						<table>
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td>
									<button type="button"
										class="topformbutton3" accesskey="P" title="Alt+P"
										onclick="return viewPayableEarly('<bean:message key="msg.LoanAccountNo" />');" >
										<bean:message key="button.viewPayable" /></button>
									<button type="button" class="topformbutton3" 
									accesskey="R" title="Alt+R"
									onclick="return viewReceivableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
									<button type="button" name="view" 
									id="view" accesskey="L" title="Alt+L"
									 class="topformbutton3" onclick="return viewLoanDetails();"/><bean:message key="button.viewloandetails"/></button>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
				<script type="text/javascript">
					if(document.getElementById("closureType").value=='T')
					{
						if('<%=request.getAttribute("status")%>'=='P' || '<%=request.getAttribute("status")%>'=='F')
						{
							alert('This Loan is marked for Early Closure but not Approved yet.');
						}
					}

					if(document.getElementById("closureType").value=='C')
					{
						if('<%=request.getAttribute("status")%>'=='P' || '<%=request.getAttribute("status")%>'=='F')
						{
							alert('This Loan is marked for Maturity Closure but not Approved yet.');
							window.close();
						}
						if('<%=request.getAttribute("status")%>'=='A')
						{
							alert('This Loan is Maturity Closed.');
							window.close();
						}
					}
					
					if(document.getElementById("closureType").value=='X')
					{
						if('<%=request.getAttribute("status")%>'=='P' || '<%=request.getAttribute("status")%>'=='F')
						{
							alert('This Loan is marked for Cancellation but not Approved yet.');
							window.close();
						}
						if('<%=request.getAttribute("status")%>'=='L')
						{
							alert('This Loan is Cancelled.');
							window.close();
						}
					}
				</script>
			</logic:present>
			<!-- Changes by Amit Ends -->
		</div>
	</div>
</body>
</html:html>