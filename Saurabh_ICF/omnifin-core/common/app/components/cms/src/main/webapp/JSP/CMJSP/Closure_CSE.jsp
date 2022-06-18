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
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

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

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/cmScript/loanClosure.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
<script type="text/javascript" 
	src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>


<body onload="enableAnchor();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllWindowCallUnloadBody();">

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
			<input type="hidden" id="checkFlag" value="${sessionScope.checkFlag}" />
			<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
			<input type="hidden" name="foreClosurePenaltyOld" id="foreClosurePenaltyOld" value="${closureData[0].foreClosurePenalty}" />
			<input type="hidden" name="totalRecevableOld" id="totalRecevableOld" value="${closureData[0].totalRecevable}" />
			<input type="hidden" name="earlyClosureFlag" id="earlyClosureFlag" value="${earlyClosureFlag}" />
			<input type="hidden" name="saveCompleted" id="saveCompleted" value="${saveCompleted}"/>
			<input type="hidden" name="waiveAllocated" id="waiveAllocated" value="${waiveAllocated}" />
			<input type="hidden" name="changeWaiveOff" id="changeWaiveOff" value="${changeWaiveOff}" />
			<input type="hidden" name="netReceiveablePayableF" id="netReceiveablePayableF" value="${netReceiveablePayableF}" />
			
			
			
			<!-- ********************************************* For Closure Maker **************************************************** -->

			<logic:present name="closureData">
			<input type="hidden" id="modifyRecord" name="modifyRecord" value="CM"/>
				<html:form action="/earlyClosure" styleId="closureForm"
					method="post">
					<html:javascript formName="ClosureDynaValidatorForm" />
					<html:errors />
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
									value="${sessionScope.type}" />
								<html:hidden property="terminationId" styleId="terminationId"
									value="${closureData[0].terminationId}" />
								<tr>
									<td width="25%">
										<bean:message key="lbl.loanAc" /><span><font color="red">*</font></span>
									</td>
									<td width="25%">
										<html:text styleClass="text" styleId="loanAc" maxlength="20"
											property="loanAc" value="${closureData[0].loanAc}" 
											readonly="true" tabindex="-1"/>
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
											maxlength="50" property="scheme" tabindex="-1"
											value="${closureData[0].scheme}" />
									</td>
									<td>
										<bean:message key="lbl.product" />
									</td>
									<td>
										<html:text styleClass="text" styleId="product" disabled="true"
											maxlength="50" property="product" tabindex="-1"
											value="${closureData[0].product}" />
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
											readonly="true" tabindex="-1" style="text-align:right;"
											value="${closureData[0].remainingInstallments}"/>
									</td>
									<td>
										<bean:message key="lbl.requestNumber" />
									</td>
									<td>
										<html:text styleClass="text" styleId="requestNumber"
											maxlength="10" property="requestNumber"
											value="${closureData[0].requestNumber}" />
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.effectiveDate" /><span><font color="red">*</font></span>
									</td>
									<td>
										<html:text styleClass="text" styleId="effectiveDate"
											maxlength="10" property="effectiveDate"
											value="${closureData[0].effectiveDate}"
											onchange="checkDate('effectiveDate');checkMaturityDateForMatClo();" />
									</td>
									<td>
										<bean:message key="lbl.reasonForClosure" /><span><font color="red">*</font></span>
									</td>
									<td>
										<html:text styleClass="text" styleId="reasonForClosure"
											maxlength="1000" property="reasonForClosure"
											value="${closureData[0].reasonForClosure}" />
									</td>
								</tr>
								<!-- Virender Changes start -->
								<tr>
								<td><bean:message key="lbl.closureType" /></td>
					        	<td>				        	        	  	
					          	  	<html:select property="vClosureType" styleId="vClosureType" styleClass="text">
						            <option value="">-- Select --</option>	
						            <logic:present name="closureTypeList">
					          	  		<html:optionsCollection name="closureTypeList" label="doneByDesc" value="doneByCode" />
					          	  	</logic:present>				             
					          		 </html:select>
					        	</td>
					        	</tr>
								<!-- Virender Changes End -->
								<tr>
									<td colspan="2">
										<button  type="button"  name="getDetail" id="getDetail"
										class="topformbutton2"
											onclick="return getDetails();"
											accesskey="G" title="Alt+G" ><bean:message key="button.getdetail" /></button>
									</td>
									<td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
									<td nowrap="nowrap">
										<html:textarea styleClass="text" property="authorRemarks" disabled="true"
										value="${closureData[0].authorRemarks}" />
									</td>
								</tr>
								<tr><td><html:hidden property="realize" styleId="realize"	value="${closureData[0].realize}" /></td>
									<td><html:hidden property="realizeFlag" styleId="realizeFlag" value="${sessionScope.realizeFlag}" /></td>
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
											readonly="true" value="${closureData[0].balancePrincipal}"
											maxlength="18" property="balancePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDeposit" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDeposit"
											readonly="true" value="${closureData[0].secureDeposit}"
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
											readonly="true" value="${closureData[0].overduePrincipal}"
											maxlength="18" property="overduePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositInterest"
											value="${closureData[0].secureDepositInterest}"
											readonly="true" maxlength="18"
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
											readonly="true" value="${closureData[0].overdueInstallments}"
											maxlength="18" property="overdueInstallments" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositTDS"
											value="${closureData[0].secureDepositTDS}" readonly="true"
											maxlength="18" property="secureDepositTDS" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.interestTillDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="interestTillDate"
											value="${closureData[0].interestTillDate}" readonly="true"
											maxlength="18" property="interestTillDate" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDInterest"
											value="${closureData[0].gapSDInterest}" readonly="true"
											maxlength="18" property="gapSDInterest" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.unBilledInstallments" />
									</td>
									<td>
										<html:text property="unBilledInstallments"
											styleId="unBilledInstallments" styleClass="text"
											value="${closureData[0].unBilledInstallments}" maxlength="18"
											readonly="true" tabindex="-1" style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDTDS"
											value="${closureData[0].gapSDTDS}" maxlength="18"
											property="gapSDTDS" readonly="true" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.otherDues" />
									</td>
									<td>
										<html:text styleClass="text" styleId="otherDues"
											readonly="true" value="${closureData[0].otherDues}"
											maxlength="18" property="otherDues" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.otherRefunds" />
									</td>
									<td>
										<html:text styleClass="text" styleId="otherRefunds"
											readonly="true" value="${closureData[0].otherRefunds}"
											maxlength="18" property="otherRefunds" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.foreClosurePenalty" />
									</td>
									<td>
									<!--Change By Arun Starts Here  For Maturity Closure-> FORECLOSURE CHARGES NOT TO BE CALCULATED   -->
									<logic:present name="maturityClosureLabel">
							             <html:text styleClass="text" styleId="foreClosurePenalty"	value="0.00" 
											property="foreClosurePenalty"  tabindex="-1" readonly="true"  style="text-align:right;"/>
							         </logic:present >
										<logic:notPresent name="maturityClosureLabel">
										 <html:text styleClass="text" styleId="foreClosurePenalty"
											value="${closureData[0].foreClosurePenalty}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);resetReceivable()" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"
											property="foreClosurePenalty"  tabindex="-1" 
											maxlength="18" style="text-align:right;"/>
										</logic:notPresent>
											
									<!--Change By Arun Ends Here  For Maturity Closure-> FORECLOSURE CHARGES NOT TO BE CALCULATED   -->
									</td>
									<!-- Change  advanceEmiRefunds Added By Arun  -->
									<td>
									<bean:message key="lbl.advanceEmiRefunds" />
									</td>
									<td><html:text styleClass="text" styleId="advanceEmiRefunds"
											value="${closureData[0].advanceEmiRefunds}" maxlength="18" property="advanceEmiRefunds"
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
											tabindex="-1" value="${closureData[0].lppAmount}"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.totalpayable" />
									</td>
									<td><html:text styleClass="text" styleId="totalPayable"
											value="${closureData[0].totalPayable}" maxlength="18" property="totalPayable"
											style="text-align:right;" readonly="true" tabindex="-1"/>
									</td>
									
									
								</tr>
								<tr>
								<td>
									<bean:message key="lbl.interstTillLP"/>
								</td>
								<td>
									<html:text property="interstTillLP" styleId="interstTillLP"
										styleClass="text" value="${closureData[0].interstTillLP}" 
										readonly="true" maxlength="18" tabindex="-1"
										style="text-align:right;"/>
								</td>
								<td>
										<bean:message key="lbl.waiveOffAmount"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="waiveOffAmount"
											value="${closureData[0].waiveOffAmount}"
											maxlength="18" property="waiveOffAmount"
											onkeypress="return numbersonly(event,id,18)" 
											onblur="formatNumber(this.value,id);calculatNetPayRes(this.value)" onkeyup="checkNumber(this.value, event, 18,id);"
											style="text-align:right;"
											onfocus="keyUpNumber(this.value, event, 18,id);"/>
									</td>
								
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.totalRecevable" />
									</td>
									<td>
										<html:text styleClass="text" styleId="totalRecevable" readonly="true" maxlength="18" property="totalRecevable" value="${closureData[0].totalRecevable}" style="text-align:right;"/>
								   </td>
								   <td>
										<bean:message key="lbl.netReceivablePayable" />
									</td>
									<td>
										<html:text styleClass="text" styleId="netReceivablePayable"
											value="${closureData[0].netReceivablePayable}"
											readonly="true" maxlength="18"
											property="netReceivablePayable" tabindex="-1"
											style="text-align:right;"/>
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
									<logic:present name="closureButton">
										<button type="button" name="allocation" id="allocation" 
										class="topformbutton4" accesskey="W" title="Alt+W"
										onclick="allocateWaiveOff()" ><bean:message key="button.waiveOffAllocation" /></button>
									</logic:present>
									<button type="button" name="save" id="save" 
										class="topformbutton2" accesskey="V" title="Alt+V"
										onclick="return updateClosureDetails('P');" ><bean:message key="button.save" /></button>
									<button type="button" name="saveForward" id="saveForward"
										 class="topformbutton2"
										onclick="return updateClosureDetails('F');" 
										accesskey="F" title="Alt+F"><bean:message key="button.forward" /></button>
								<button type="button" 
										class="topformbutton3" 
										onclick="return viewPayableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewPayable" /></button>
									<button type="button" 
										class="topformbutton3" 
										onclick="return viewReceivableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
								<button type="button" name="view" id="view" 
										value="View Loan Details" class="topformbutton3" onclick="return viewLoanDetails();"><bean:message key="button.viewloandetails"/></button>
									<logic:present name="earlyClosureLabel">
								<button type="button" name="generateRepaymentSch" id="generateRepaymentSch"
										value="Repayment Schedule"
										class="topformbutton4" onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key= "button.repayschedule"/></button>
									</logic:present>
									<button type="button" name="delete" id="delete" 
										class="topformbutton2" accesskey="D" title="Alt+D"
										onclick="return deleteClosureDetails();"><bean:message key="button.delete" /></button>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
		
			</logic:present>


			<!-- ********************************************* For New Closure **************************************************** -->

			<logic:present name="closureNew">
			 <input type="hidden" id="modifyRecord" name="modifyRecord" value="N"/>
				<html:form action="/earlyClosure" styleId="closureForm"
					method="post">
					<html:javascript formName="ClosureDynaValidatorForm" />
					<html:errors />
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
									value="${sessionScope.type}" />
								<tr>

									<td width="25%">
										<bean:message key="lbl.loanAc" /><span><font color="red">*</font></span>
									</td>
									<td width="25%">
										<html:text styleClass="text" property="loanAc"
											styleId="loanAc" value="" readonly="true" tabindex="-1"/>
										<logic:present name="earlyClosureLabel">
										<html:button property="loanClosureLOV" value=" "
											onclick="openLOVCommon(43,'closureForm','loanAc','','', '','','generateValuesClosure','customerName');closeLovCallonLov1();"
											styleClass="lovButton"/>
										</logic:present>
										<logic:present name="maturityClosureLabel">
										<html:button property="loanClosureLOV" value=" "
											onclick="openLOVCommon(175,'closureForm','loanAc','','', '','','generateValuesClosure','customerName');closeLovCallonLov1();"
											styleClass="lovButton"/>
										</logic:present>
										<input type="hidden" name="contextPath"
											value="<%=request.getContextPath()%>" id="contextPath" />
										<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
											value="" />
									</td>
									<td width="25%">
										<bean:message key="lbl.customerName" />
									</td>
									<td width="25%">
										<html:text styleClass="text" styleId="customerName" tabindex="-1"
											maxlength="50" disabled="true" property="customerName" />
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
											disabled="true" property="loanDate" tabindex="-1"/>
									</td>
								</tr>

								<tr>
									<td>
										<bean:message key="lbl.scheme" />
									</td>
									<td>
										<html:text styleClass="text" styleId="scheme" disabled="true"
											maxlength="50" property="scheme" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.product" />
									</td>
									<td>
										<html:text styleClass="text" styleId="product" disabled="true"
											maxlength="50" property="product" tabindex="-1"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.originalTenure" />
									</td>
									<td>
										<html:text styleClass="text" styleId="originalTenure" tabindex="-1"
											disabled="true" maxlength="4" property="originalTenure" 
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.frequency" />
									</td>
									<td>
										<html:text styleClass="text" styleId="frequency" tabindex="-1"
											disabled="true" maxlength="10" property="frequency" />
									</td>
								</tr>

								<tr>
									<td>
										<bean:message key="lbl.maturityDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="maturtyDate" tabindex="-1"
											disabled="true" maxlength="10" property="maturityDate" />
									</td>
									<td>
										<bean:message key="lbl.remainingTenure" />
									</td>
									<td>
										<html:text styleClass="text" styleId="remainingTenure" tabindex="-1"
											disabled="true" maxlength="4" property="remainingTenure" 
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
											readonly="true" tabindex="-1" value=""
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.requestNumber" />
									</td>
									<td>
										<html:text styleClass="text" styleId="requestNumber"
											maxlength="10" property="requestNumber" value="" />
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.effectiveDate" /><span><font color="red">*</font></span>
									</td>
									<td>
										<html:text styleClass="text" styleId="effectiveDate"
											maxlength="10" property="effectiveDate" value=""
											onchange="checkDate('effectiveDate');checkMaturityDateForMatClo();" />
									</td>
									<td>
										<bean:message key="lbl.reasonForClosure" /><span><font color="red">*</font></span>
									</td>
									<td>
										<html:text styleClass="text" styleId="reasonForClosure"
											maxlength="1000" property="reasonForClosure" value="" />
									</td>
								</tr>
								
								<!-- Virender Changes start -->
								<tr>
								<td><bean:message key="lbl.closureType" /></td>
					        	<td>				        	        	  	
					          	  	<html:select property="vClosureType" styleId="vClosureType" styleClass="text">
						            <option value="">-- Select --</option>	
						            <logic:present name="closureTypeList">
					          	  		<html:optionsCollection name="closureTypeList" label="doneByDesc" value="doneByCode" />
					          	  	</logic:present>				             
					          		 </html:select>
					        	</td>
					        	</tr>
								<!-- Virender Changes End -->

								<tr>
									<td colspan="2">
										<button  type="button"  name="getDetail" id="getDetail"
										class="topformbutton2" 
											
											onclick="return getDetails();" 
											accesskey="G" title="Alt+G" ><bean:message key="button.getdetail" /></button>

									</td>
									<td nowrap="nowrap">
										<bean:message key="lbl.authorRemarks" />
									</td>
									<td nowrap="nowrap">
										<html:textarea styleClass="text" property="authorRemarks" disabled="true"
											value="${requestScope.authorRemarks}" />
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
											readonly="true" value="" maxlength="18"
											property="balancePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDeposit" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDeposit"
											readonly="true" value="" maxlength="18"
											property="secureDeposit" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.overduePrincipal"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="overduePrincipal"
											readonly="true" value=""
											maxlength="18" property="overduePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositInterest"
											value="" readonly="true" maxlength="18"
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
											readonly="true" value="" maxlength="18"
											property="overdueInstallments" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.secureDepositTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="secureDepositTDS"
											value="" readonly="true" maxlength="18"
											property="secureDepositTDS" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.interestTillDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="interestTillDate"
											value="" readonly="true" maxlength="18"
											property="interestTillDate" tabindex="-1"
											style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDInterest" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDInterest" value=""
											readonly="true" maxlength="18" property="gapSDInterest" 
											tabindex="-1" style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.unBilledInstallments" />
									</td>
									<td>
										<html:text property="unBilledInstallments" readonly="true"
											styleId="unBilledInstallments" styleClass="text" value=""
											maxlength="18" tabindex="-1" style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.gapSDTDS" />
									</td>
									<td>
										<html:text styleClass="text" styleId="gapSDTDS" value=""
											maxlength="18" property="gapSDTDS" readonly="true" 
											tabindex="-1" style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.otherDues" />
									</td>
									<td>
										<html:text styleClass="text" styleId="otherDues"
											readonly="true" value="" maxlength="18" property="otherDues"
											tabindex="-1" style="text-align:right;"/>
									</td>
									<td width="19%">
										<bean:message key="lbl.otherRefunds" />
									</td>
									<td width="31%">
										<html:text styleClass="text" styleId="otherRefunds"
											readonly="true" value="" maxlength="18"
											property="otherRefunds" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								
								<tr>
									<td>
										<bean:message key="lbl.foreClosurePenalty" />
									</td>
									<td>
										<!--Change By Arun Starts Here  For Maturity Closure-> FORECLOSURE CHARGES NOT TO BE CALCULATED   -->
									<logic:present name="maturityClosureLabel">
							               <html:text styleClass="text" styleId="foreClosurePenalty"	value="0.00" 
											property="foreClosurePenalty"  tabindex="-1" readonly="true"  style="text-align:right;"/>
							         </logic:present >
										<logic:notPresent name="maturityClosureLabel">
										 <html:text styleClass="text" styleId="foreClosurePenalty"
											value="${closureData[0].foreClosurePenalty}" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);resetReceivable()" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"
											property="foreClosurePenalty"  tabindex="-1" 
											maxlength="18" style="text-align:right;"/>
										</logic:notPresent>
											
									<!--Change By Arun Ends Here  For Maturity Closure-> FORECLOSURE CHARGES NOT TO BE CALCULATED   -->
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
											tabindex="-1" value="" style="text-align:right;"/>
									</td>
									<td>
										<bean:message key="lbl.totalpayable" />
									</td>
									<td><html:text styleClass="text" styleId="totalPayable"
											value="" maxlength="18" property="totalPayable"
											style="text-align:right;" readonly="true" tabindex="-1"/>
									</td>	
									
									
								</tr>
								<tr>
								<td>
									<bean:message key="lbl.interstTillLP"/>
								</td>
								<td>
									<html:text property="interstTillLP" styleId="interstTillLP"
										styleClass="text" value="" readonly="true" maxlength="18"
										tabindex="-1" style="text-align:right;"/>
								</td>
								<td>
										<bean:message key="lbl.waiveOffAmount"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="waiveOffAmount"
											value="0.00" property="waiveOffAmount"
											onkeypress="return numbersonly(event,id,18)" 
											onblur="formatNumber(this.value,id);calculatNetPayRes(this.value)" 
											onkeyup="checkNumber(this.value, event, 18,id);"
											onfocus="keyUpNumber(this.value, event, 18,id);"
											style="text-align:right;" />
									</td>
								
								
								</tr>
								<tr>
									<td>
										<bean:message key="lbl.totalRecevable" />
									</td>
									<td>
										<html:text styleClass="text" styleId="totalRecevable" readonly="true" maxlength="18" property="totalRecevable"  style="text-align:right;"/>
								   </td>	
								   <td>
										<bean:message key="lbl.netReceivablePayable" />
									</td>
									<td>
										<html:text styleClass="text" styleId="netReceivablePayable"
											value="" readonly="true" maxlength="18"
											property="netReceivablePayable" tabindex="-1"
											style="text-align:right;" />
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
									<logic:present name="closureButton">
										<button type="button" name="allocation" id="allocation" 
										class="topformbutton4" accesskey="W" title="Alt+W"
										onclick="allocateWaiveOff()" ><bean:message key="button.waiveOffAllocation" /></button>
									</logic:present>
									<button type="button" name="save" id="save" 
										class="topformbutton2" accesskey="V" title="Alt+V"
										onclick="return saveClosureDetails();" ><bean:message key="button.save" /></button>
									<button type="button" name="saveForward" id="saveForward"
										 class="topformbutton2"
										onclick="return updateDisbursalBeforeSave();"
										accesskey="F" title="Alt+F" /><bean:message key="button.forward" /></button>
										<button type="button"
										class="topformbutton3" accesskey="P" title="Alt+P"
										onclick="return viewPayableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewPayable" /></button>
										<button type="button" 
										class="topformbutton3" accesskey="R" title="Alt+R"
										onclick="return viewReceivableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
										<button type="button" name="view" id="view" accesskey="L" title="Alt+L"
										class="topformbutton3" onclick="return viewLoanDetails();"><bean:message key="button.viewloandetails"/></button>
									<logic:present name="earlyClosureLabel">
										<button type="button" name="generateRepaymentSch" id="generateRepaymentSch"
										title="Alt+S" accesskey="S"
										class="topformbutton4" onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key= "button.repayschedule"/></button>
									</logic:present>
								</td>
							</tr>
						</table>

					</fieldset>
				</html:form>
			</logic:present>

			<!-- ********************************************* For Closure Author **************************************************** -->

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
									value="${sessionScope.type}" />
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
								<!-- Virender Changes start -->
								<tr>
								<td><bean:message key="lbl.closureType" /></td>
					        	<td>				        	        	  	
					          	  	<html:select property="vClosureType" styleId="vClosureType" styleClass="text" disabled="true" value="${closureDataDisabled[0].vClosureType}">
						            <option value="${closureDataDisabled[0].vClosureType}">${closureDataDisabled[0].vClosureTypeDesc}</option>	
<%-- 						            <logic:present name="closureTypeList">
					          	  		<html:optionsCollection name="closureTypeList" label="doneByDesc" value="doneByCode" />
					          	  	</logic:present> --%>				             
					          		 </html:select>
					        	</td>
					        	</tr>
								<!-- Virender Changes End -->
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
									<logic:present name="closureButton">
										<button type="button" name="allocation" id="allocation" 
										class="topformbutton4" accesskey="W" title="Alt+W"
										onclick="allocateWaiveOff()" ><bean:message key="button.waiveOffAllocation" /></button>
									</logic:present>
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
									<logic:present name="earlyClosureLabel">
									<button type="button" name="generateRepaymentSch" id="generateRepaymentSch"
									accesskey="S" title="Alt+S" class="topformbutton4" 
									 onclick="return viewRepaymentScheduleDisbursal();" ><bean:message key= "button.repayschedule"/></button>
									</logic:present>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
				
			</logic:present>
		</div>
	</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<!--<script>-->
<!--	setFramevalues("closureForm");-->
<!--</script>	-->
</body>
</html:html>

<logic:present name="message">

<script type="text/javascript">
if(document.getElementById("closureType").value=='T')
{	
	
	if('<%=request.getAttribute("closureStatus")%>'=='AmtInProcess')
	{
		alert('<bean:message key="msg.AmtInProcessPending" />');
	}
	if("<%=request.getAttribute("message")%>"=="S")
	{
		alert("<bean:message key="msg.DataSaved" />");
		document.getElementById('saveCompleted').value='Y';
		document.getElementById('changeWaiveOff').value='Y';
		document.getElementById('waiveAllocated').value='N';
	}
	else if("<%=request.getAttribute("message")%>"=="U")
	{
		alert("<bean:message key="msg.ForwardNonEmiSuccessfully" />");
		window.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=earlyClosure";
	}
	else if("<%=request.getAttribute("message")%>"=="E")
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		window.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=earlyClosure";
	}
	else if("<%=request.getAttribute("message")%>"=="D")
	{
		alert("<bean:message key="msg.DataDelete" />");
		window.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=earlyClosure";
	}
	else if("<%=request.getAttribute("message")%>"=="EX")
	{
		alert("<bean:message key="msg.DataExist" />");
		window.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=earlyClosure";
	}
}

if(document.getElementById("closureType").value=='C')
{
	if('<%=request.getAttribute("closureStatus")%>'=='AmtInProcess')
	{
		alert('<bean:message key="msg.AmtInProcessPending" />');
	}
	if("<%=request.getAttribute("message")%>"=="S")
	{
		alert("<bean:message key="msg.DataSaved" />");
		document.getElementById('saveCompleted').value='Y';
		document.getElementById('changeWaiveOff').value='Y';
		document.getElementById('waiveAllocated').value='N';
	}
	else if("<%=request.getAttribute("message")%>"=="U")
	{
		alert("<bean:message key="msg.ForwardNonEmiSuccessfully" />");
		window.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=maturityClosure";
	}
	else if("<%=request.getAttribute("message")%>"=="E")
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		window.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=maturityClosure";
	}
	else if("<%=request.getAttribute("message")%>"=="D")
	{
		alert("<bean:message key="msg.DataDelete" />");
		window.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=maturityClosure";
	}
	else if("<%=request.getAttribute("message")%>"=="EX")
	{
		alert("<bean:message key="msg.DataExist" />");
		window.location="<%=request.getContextPath()%>/closureInitiationBehind.do?method=maturityClosure";
	}
}
</script>
</logic:present>

