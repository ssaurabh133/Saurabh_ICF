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
	
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

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
	src="<%=request.getContextPath()%>/js/cmScript/manual_npa.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
<script type="text/javascript" 
	src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
<body onload="enableAnchor();checkChanges('manualNpaMovementAddForm');fntab();init_fields();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllWindowCallUnloadBody();">

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



			
				<html:form action="/manualNpaMovementMakerAdd" styleId="manualNpaMovementAddForm"
					method="post">
						<input type="hidden" name="manualNpaId" id="manualNpaId" value="${requestScope.manualNpaId} " />
							<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
<!--	for maker----###										-->
			<logic:present name="maker">
				
						<fieldset>
							
							<LEGEND>
								<bean:message key="lbl.manualnpamovementmaker" />
							</LEGEND>
							
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								
							
								<tr>

									<td width="25%">
										<bean:message key="lbl.loanAc" /><font color="red">*</font>
									</td>
									<td width="25%">
										<logic:present name="statusflag">
								<logic:equal name="statusflag" value="save">
										<html:text styleClass="text" styleId="loanNo" maxlength="20"
											property="loanNo" value="${list[0].loanNo}"
											readonly="true" tabindex="-1"/>
											<html:button property="loanClosureLOV" value=" " styleId="loanbutton"
											onclick="openLOVCommon(335,'manualNpaMovementAddForm','loanNo','','', '','','generateManualNpaValues','customerName');closeLovCallonLov1();removeDataforGetDetails();"
											styleClass="lovButton"/>
										<html:hidden property="lbxLoanNo" styleId="lbxLoanNo"
											value="${list[0].lbxLoanNo}" />
									
											</logic:equal>
											</logic:present>
											<logic:present name="statusflag">
								<logic:equal name="statusflag" value="update">
								<html:text styleClass="text" styleId="loanNo" maxlength="20"
											property="loanNo" value="${list[0].loanNo}"  
											readonly="true" tabindex="-1"/>
											<html:hidden property="lbxLoanNo" styleId="lbxLoanNo"
											value="${list[0].lbxLoanNo}" />
								</logic:equal>
								</logic:present>
						
									</td>

									<td width="25%">
										<bean:message key="lbl.customerName" />
									</td>
									<td width="25%">
										<html:text styleClass="text" styleId="customerName"
											maxlength="50" readonly="true" property="customerName"
											value="${list[0].customerName}" tabindex="-1"/>
									</td>
								</tr>
								<tr>		
									<td>
										<bean:message key="lbl.products" />
									</td>
									<td>
										<html:text styleClass="text" styleId="product"
											maxlength="50" readonly="true" property="product"
											value="${list[0].product}" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.schemeforSearch" />
									</td>
									<td>
										<html:text styleClass="text" styleId="scheme"
											maxlength="50" readonly="true" property="scheme"
											value="${list[0].scheme}" tabindex="-1"/>
									</td>
							
								</tr>
								
								<tr>
									<td colspan="2">
								<logic:present name="statusflag">
										<logic:equal name="statusflag" value="save">
										<button type="button" name="getDetail" id="getDetail"
											class="topformbutton2" 
											accesskey="G" title="Alt+G"
											onclick="return getDetailsfornew();" ><bean:message key="button.getdetail" /></button>
										</logic:equal>
								</logic:present>
									</td>
									
								</tr>
							</table>
						</fieldset>

						<fieldset>
							<legend>
								<bean:message key="lbl.loanDetail" />
							</legend>
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr>
								
								<input id="getdetailsval" name="getdetailsval" type="hidden" value="${getdetailsval }" />
							
								<td width="25%">	
									<bean:message key="lbl.loanAmt" />
									</td>
									<td width="25%" >
									
										<html:text styleClass="text" styleId="loanAmount"
											readonly="true" value="${list[0].loanAmount}" maxlength="18" property="loanAmount" tabindex="-1"
											style="text-align:right;"/>
									
									</td>
									
									<td width="25%">	
									<bean:message key="lbl.balancePrincipal" />
									</td>
									<td width="25%" >
									
										<html:text styleClass="text" styleId="balancePrincipal"
											readonly="true" value="${list[0].balancePrincipal}" 
											maxlength="18" property="balancePrincipal" tabindex="-1"
											style="text-align:right;"/>
									
									</td>
								</tr>
								
									<tr>		
									<td>
										<bean:message key="lbl.loanApprovalDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="approvalDate"
											maxlength="50" readonly="true" property="approvalDate" 
											value="${list[0].approvalDate}" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.disbursedDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="disbursalDate"
											maxlength="50" readonly="true" property="disbursalDate"
											value="${list[0].disbursalDate}" tabindex="-1"/>
									</td>
							
								</tr>
								<tr>
								<td >
									<bean:message key="lbl.disbursedAmount"/>
									</td>
									<td >
									<html:text styleClass="text" styleId="disbursalAmt"
											readonly="true" value="${list[0].disbursalAmt}" 
											maxlength="18" property="disbursalAmt" tabindex="-1"
											style="text-align:right;"/>
									</td>
								
									<td >
									<bean:message key="lbl.instOverdueAmt"/>
									</td>
									<td >
									<html:text styleClass="text" styleId="instOverdue"
											readonly="true" value="${list[0].instOverdue}" 
											maxlength="18" property="instOverdue" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td >
									<bean:message key="lbl.overduePrincipal"/>
									</td>
									<td >
									<html:text styleClass="text" styleId="overduePrincipal"
											readonly="true" value="${list[0].overduePrincipal}" 
											maxlength="18" property="overduePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
								
									
									<td>
										<bean:message key="lbl.recievedprincipal" />
									</td>
									<td>
										<html:text styleClass="text" styleId="recievedPrincipal" value="${list[0].recievedPrincipal}" 
											maxlength="18" property="recievedPrincipal" readonly="true"
											tabindex="-1" style="text-align:right;"/>
									</td>
										
								</tr>
								<tr>
								
									<td>
										<bean:message key="lbl.totalOutstanding"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="totOverduePrinc" value="${list[0].totOverduePrinc}" 
											maxlength="18" property="totOverduePrinc" tabindex="-1"
											style="text-align:right;" readonly="true"/>
									</td>
									
								
									<td>
										<bean:message key="lbl.emiAmt"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="emiAmount" value="${list[0].emiAmount}" 
											maxlength="18" property="emiAmount" tabindex="-1"
											style="text-align:right;" readonly="true"/>
									</td>
							
							</tr>
								<tr>
								
									<td>
										<bean:message key="lbl.loanDPD"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="dpd" value="${list[0].dpd}" 
											maxlength="18" property="dpd" tabindex="-1"
											style="text-align:right;" readonly="true"/>
									</td>
								
									<td>
										<bean:message key="lbl.LoanDPDString"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="dpdString" value="${list[0].dpdString}" 
											maxlength="18" property="dpdString" tabindex="-1"
											style="text-align:right;" readonly="true"/>
									</td>
							
								</tr>
								<tr>
								
								
									<td>
										<bean:message key="lbl.lastnpaStatus"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="lastNpaStage" value="${list[0].lastNpaStage}" 
											maxlength="18" property="lastNpaStage" tabindex="-1"
											style="text-align:right;" readonly="true"/>
									</td>
									
								<td>
										<bean:message key="lbl.currentnpaStatus"/>
									</td>
									<td>
										
											
									<html:select property="currNpaStage" styleId="currNpaStage"  styleClass="text" value="${list[0].currNpaStage}" onchange="valchange();">
						<option value="">--<bean:message key="lbl.select" />--</option>
							 <logic:present name="npaStage">
							 <html:optionsCollection name="npaStage" label="stage" value="stage" />
							 </logic:present>
						</html:select>		
									</td>
									</tr>
								<tr>
								
								<td nowrap="nowrap">
										<bean:message key="lbl.authorRemarks" />
									</td>
									<td nowrap="nowrap">
										<html:textarea styleClass="text" property="authorRemarks" readonly="true"
											value="${list[0].authorRemarks}" />
									</td>
							
								</tr>
<input type="hidden" name="statusflag" id="statusflag" value="${statusflag}" />
								<tr >
								<td colspan="3">
								<logic:present name="statusflag">
						<logic:equal name="statusflag" value="save">
									
									<button type="button" id="save"
										class="topformbutton2" accesskey="V" title="Alt+V"
										onclick="return save_manual_npa();" >
										<bean:message key="button.save" /></button>
										
									
							</logic:equal>		
							</logic:present>
									<logic:present name="statusflag">
								<logic:equal name="statusflag" value="update">
						
									
									<button type="button" id="save"
										class="topformbutton2" accesskey="V" title="Alt+V"
										onclick="return update_manual_npa();" >
										<bean:message key="button.save" /></button>
										<button type="button" id="delete"
										class="topformbutton2" accesskey="D" title="Alt+D"
										onclick="return delete_manual_npa();" >
										<bean:message key="button.delete" /></button>
										
									
										</logic:equal>
									</logic:present>
									<input type="hidden" name="forwardflag" id="forwardflag" value="${forwardflag}" />
						
									<button type="button" id="forward"
										class="topformbutton2" accesskey="F" title="Alt+F"
										onclick="return forward_manual_npa();" >
										<bean:message key="button.forward" /></button>
										
									</td>
								</tr>
							</table>
						</fieldset>
			
						</logic:present>
						<!--	for author----###										-->
			<logic:present name="author">
				
						<fieldset>
							
							<LEGEND>
								<bean:message key="lbl.manualnpamovementmaker" />
							</LEGEND>
							
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								
							
								<tr>

									<td width="25%">
										<bean:message key="lbl.loanAc" /><font color="red">*</font>
									</td>
									<td width="25%">
								
								<html:text styleClass="text" styleId="loanNo" maxlength="20"
											property="loanNo" value="${list[0].loanNo}"
											disabled="true" tabindex="-1"/>
											<html:hidden property="lbxLoanNo" styleId="lbxLoanNo"
											value="${list[0].lbxLoanNo}" />
								
						
									</td>

									<td width="25%">
										<bean:message key="lbl.customerName" />
									</td>
									<td width="25%">
										<html:text styleClass="text" styleId="customerName"
											maxlength="50" disabled="true" property="customerName"
											value="${list[0].customerName}" tabindex="-1"/>
									</td>
								</tr>

								<tr>		
									<td>
										<bean:message key="lbl.products" />
									</td>
									<td>
										<html:text styleClass="text" styleId="product"
											maxlength="50" readonly="true" property="product"
											value="${list[0].product}" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.schemeforSearch" />
									</td>
									<td>
										<html:text styleClass="text" styleId="scheme"
											maxlength="50" readonly="true" property="scheme"
											value="${list[0].scheme}" tabindex="-1"/>
									</td>
							
								</tr>
							</table>
						</fieldset>

						<fieldset>
							<legend>
								<bean:message key="lbl.loanDetail" />
							</legend>
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr>
								<td width="25%">	
									<bean:message key="lbl.loanAmt" />
									</td>
									<td width="25%" >
									
										<html:text styleClass="text" styleId="loanAmount"
											readonly="true" value="${list[0].loanAmount}" maxlength="18" property="loanAmount" tabindex="-1"
											style="text-align:right;"/>
									
									</td>
									
									<td width="25%">	
									<bean:message key="lbl.balancePrincipal" />
									</td>
									<td width="25%" >
									
										<html:text styleClass="text" styleId="balancePrincipal"
											readonly="true" value="${list[0].balancePrincipal}" 
											maxlength="18" property="balancePrincipal" tabindex="-1"
											style="text-align:right;"/>
									
									</td>
								</tr>
								
									<tr>		
									<td>
										<bean:message key="lbl.loanApprovalDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="approvalDate"
											maxlength="50" readonly="true" property="approvalDate"
											value="${list[0].approvalDate}" tabindex="-1"/>
									</td>
									<td>
										<bean:message key="lbl.disbursedDate" />
									</td>
									<td>
										<html:text styleClass="text" styleId="disbursalDate"
											maxlength="50" readonly="true" property="disbursalDate"
											value="${list[0].disbursalDate}" tabindex="-1"/>
									</td>
							
								</tr>
								<tr>
								<td >
									<bean:message key="lbl.disbursedAmount"/>
									</td>
									<td >
									<html:text styleClass="text" styleId="disbursalAmt"
											readonly="true" value="${list[0].disbursalAmt}" 
											maxlength="18" property="disbursalAmt" tabindex="-1"
											style="text-align:right;"/>
									</td>
								
									<td >
									<bean:message key="lbl.instOverdueAmt"/>
									</td>
									<td >
									<html:text styleClass="text" styleId="instOverdue"
											readonly="true" value="${list[0].instOverdue}" 
											maxlength="18" property="instOverdue" tabindex="-1"
											style="text-align:right;"/>
									</td>
								</tr>
								<tr>
									<td >
									<bean:message key="lbl.overduePrincipal"/>
									</td>
									<td >
									<html:text styleClass="text" styleId="overduePrincipal"
											readonly="true" value="${list[0].overduePrincipal}" 
											maxlength="18" property="overduePrincipal" tabindex="-1"
											style="text-align:right;"/>
									</td>
								
									
									<td>
										<bean:message key="lbl.recievedprincipal" />
									</td>
									<td>
										<html:text styleClass="text" styleId="recievedPrincipal" value="${list[0].recievedPrincipal}" 
											maxlength="18" property="recievedPrincipal" readonly="true"
											tabindex="-1" style="text-align:right;"/>
									</td>
										
								</tr>
								<tr>
								
									<td>
										<bean:message key="lbl.totalOutstanding"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="totOverduePrinc" value="${list[0].totOverduePrinc}" 
											maxlength="18" property="totOverduePrinc" tabindex="-1"
											style="text-align:right;" readonly="true"/>
									</td>
									
								
									<td>
										<bean:message key="lbl.emiAmt"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="emiAmount" value="${list[0].emiAmount}" 
											maxlength="18" property="emiAmount" tabindex="-1"
											style="text-align:right;" readonly="true"/>
									</td>
							
							</tr>
								<tr>
								
									<td>
										<bean:message key="lbl.loanDPD"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="dpd" value="${list[0].dpd}" 
											maxlength="18" property="dpd" tabindex="-1"
											style="text-align:right;" readonly="true"/>
									</td>
								
									<td>
										<bean:message key="lbl.LoanDPDString"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="dpdString" value="${list[0].dpdString}" 
											maxlength="18" property="dpdString" tabindex="-1"
											style="text-align:right;" readonly="true"/>
									</td>
							
								</tr>
								<tr>
								
								
									<td>
										<bean:message key="lbl.lastnpaStatus"/>
									</td>
									<td>
										<html:text styleClass="text" styleId="lastNpaStage" value="${list[0].lastNpaStage}" 
											maxlength="18" property="lastNpaStage" tabindex="-1"
											style="text-align:right;" readonly="true"/>
									</td>
									
									<td>
										<bean:message key="lbl.currentnpaStatus"/>
									</td>
									<td>
										<html:text  styleClass="text" styleId="currNpaStage"  readonly="true" 
										property="currNpaStage" value="${list[0].currNpaStage}" />
									
									</td>
									</tr>
								<tr>
								
								<td nowrap="nowrap">
										<bean:message key="lbl.authorRemarks" />
									</td>
									<td nowrap="nowrap">
										<html:textarea styleClass="text" property="authorRemarks" readonly="true"
											value="${list[0].authorRemarks}" />
									</td>
							
							
								</tr>
							
								

							
							</table>
						</fieldset>
			
						</logic:present>
				</html:form>
	
		</div>
	</div>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("manualNpaMovementAddForm");
</script>
</body>
</html:html>

<logic:present name="message">

<script type="text/javascript">

if('<%=request.getAttribute("message")%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
	}
	else if('<%=request.getAttribute("message")%>'=='M')
	{
		alert('<bean:message key="lbl.dataModify" />');
	
	}
	else if('<%=request.getAttribute("message")%>'=='D')
	{
		alert('<bean:message key="lbl.dataDeleted" />');
	window.location="<%=request.getContextPath()%>/manualNpaMovementMakerSearch.do?method=searchManualNpa";
	}
		else if('<%=request.getAttribute("message")%>'=='DE')
	{
		alert('<bean:message key="lbl.dataNtDeleted" />');
	}
	
	else if('<%=request.getAttribute("message")%>'=='E')
	{
		alert('<bean:message key="msg.DataNotSaved" />');
	}

	else if('<%=request.getAttribute("message")%>'=='F')
	{
		alert('<bean:message key="lbl.canForward" />');
		window.location="<%=request.getContextPath()%>/manualNpaMovementMakerSearch.do?method=searchManualNpa";
	}

</script>
</logic:present>

<logic:present name="manualNpaMsg">

<script type="text/javascript">

	alert('<%=request.getAttribute("manualNpaMsg")%>');


</script>
</logic:present>
<logic:present name="stageresult">

<script type="text/javascript">

	alert('<%=request.getAttribute("stageresult")%>');
	

</script>
</logic:present>

