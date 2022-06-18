<%--

	Created By Sajog      
 	
 --%>
 
<%@ page language="java"%>
<%@ page session="true"%>
<%@ page import="java.util.Date"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>

	<title><bean:message key="a3s.noida" /></title>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>


	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
	<!-- css and jquery for Datepicker -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/cplead.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/formatnumber.js"></script>
	
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
			
		<script type="text/javascript">
	
	function san(){
		if(document.getElementById("relationship").value == "New"){
			document.getElementById("relationshipSince").value = '0';
			document.getElementById("relationshipSince").setAttribute("disabled","true");
			if(document.getElementById("customerIdButton") != null){
					document.getElementById("customerIdButton").style.display='none';
					}
		}else {
			document.getElementById("relationshipSince").removeAttribute("disabled");
			if(document.getElementById("customerIdButton") != null){
			document.getElementById("customerIdButton").style.display='';	
			document.getElementById("customerIdButton").focus();
		}
		}
		
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

<body onload="enableAnchor();leadchange(document.getElementById('leadGenerator1').value);checkChanges('leadAllocationDetails');san();custtype(); ">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

	<html:form action="/leadCapturingBehindAction" styleId="leadAllocationDetails" method="post">
		<input type="hidden" name="leadcapt" id="leadcapt" />
		

		<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>" />
			<html:hidden property="leadId" styleId="leadId" value="${leadDetails[0].leadId}" />
			<html:hidden property="leadGenerator1" styleClass="text" styleId="leadGenerator1" value="${leadDetails[0].leadGenerator1}"/>
	
	<logic:present name="alocationBranch">
	<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.leadno" /></b></td>
          <td >${leadDetails[0].leadId}</td>
          <td><b><bean:message key="lbl.gendate"/></b></td>
          <td>${leadDetails[0].applicationdate}</td>
          <td><b><bean:message key="lbl.customername"/></b> </td>
          <td colspan="3" >${leadDetails[0].customerName}</td>
         </tr>
          <tr class="white2">
          	  <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${leadDetails[0].productType}</td>
	          <td><b><bean:message key="lbl.product"/></b></td>
	          <td >${leadDetails[0].product}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td >${leadDetails[0].scheme}</td>
	          <td ><b><bean:message key="lbl.currentStage"/></b></td>
	          <td>LEAD ALLOCATION</td>
          </tr>
        </table>
</td>
</tr>
</table>
 
</fieldset>	
		<fieldset id="leadallocation">
			<legend>
				<bean:message key="lbl.leadallocation" />
			</legend>
			<table width="100%">
				<tr>
					<td width="13%">
						<bean:message key="lbl.leadgenerator" /><span><font style="color: red;">  *</font></span>
						
					</td>
					<td width="13%">
						<html:text styleId="leadGenerator" property="leadGenerator" readonly="true" styleClass="text" value="${leadDetails[0].leadGenerator}">
						</html:text>

					</td>
					<td width="13%"><bean:message key="lbl.leadno" /></td>
					<td width="13%"><html:text property="leadId" styleId="leadId" value="${leadDetails[0].leadId}"  readonly="true" style="text-align:right;"/></td>
				</tr>
			</table>
		</fieldset>
	</logic:present>
	<logic:present name="alocation">
	<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.leadno" /></b></td>
          <td >${leadDetails[0].leadId}</td>
          <td><b><bean:message key="lbl.gendate"/></b></td>
          <td>${leadDetails[0].applicationdate}</td>
          <td><b><bean:message key="lbl.customername"/></b> </td>
          <td colspan="3" >${leadDetails[0].customerName}</td>
         </tr>
          <tr class="white2">
          	  <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${leadDetails[0].productType}</td>
	          <td><b><bean:message key="lbl.product"/></b></td>
	          <td >${leadDetails[0].product}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td >${leadDetails[0].scheme}</td>
	          <td ><b><bean:message key="lbl.currentStage"/></b></td>
	          <td>LEAD ALLOCATION</td>
          </tr>
        </table>
</td>
</tr>
</table>
 
</fieldset>	
		<fieldset id="leadallocation">
			<legend>
				<bean:message key="lbl.leadallocation" />
			</legend>
			<table width="100%">
				<tr>
					<td width="13%">
						<bean:message key="lbl.leadgenerator" /><span><font style="color: red;">  *</font></span>
						
					</td>
					<td width="13%">
						<html:text styleId="leadGenerator" property="leadGenerator" readonly="true" styleClass="text" value="${leadDetails[0].leadGenerator}">
						</html:text>

					</td>
					<td width="13%"><bean:message key="lbl.leadno" /></td>
					<td width="13%"><html:text property="leadId" styleId="leadId" value="${leadDetails[0].leadId}"  readonly="true" style="text-align:right;"/></td>
				</tr>
			</table>
		</fieldset>
	</logic:present>
	<logic:present name="LeadViewMode">
	<fieldset>
		<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.leadno" /></b></td>
          <td >${leadDetails[0].leadId}</td>
          <td><b><bean:message key="lbl.gendate"/></b></td>
          <td>${leadDetails[0].applicationdate}</td>
          <td><b><bean:message key="lbl.customername"/></b> </td>
          <td colspan="3" >${leadDetails[0].customerName}</td>
         </tr>
          <tr class="white2">
          	  <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${leadDetails[0].productType}</td>
	          <td><b><bean:message key="lbl.product"/></b></td>
	          <td >${leadDetails[0].product}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td >${leadDetails[0].scheme}</td>
	          <td ><b><bean:message key="lbl.currentStage"/></b></td>
	          <td>LEAD VIEWER</td>
          </tr>
        </table>
</td>
</tr>
</table>
 
</fieldset>	
<fieldset>
			<legend>
				<bean:message key="lbl.leadtracking" />
			</legend>
			<table width="100%">
				<tr>
					<td width="13%">
						<bean:message key="lbl.leadgenerator" /><span><font style="color: red;">  *</font></span>
						<html:hidden property="leadallocation" styleId="leadallocation" value="${leadDetails[0].leadId}" />
					</td>
					<td width="13%">
						<html:text styleId="leadGenerator" property="leadGenerator" readonly="true" styleClass="text" value="${leadDetails[0].leadGenerator}">
						</html:text>

					</td>
					<td width="13%">
						<bean:message key="lbl.leadservicingrm" />
					</td>
					<td width="13%">
						<html:text property="servicingRm" styleClass="text"  readonly="true" styleId="servicingRm" maxlength="10" value="${leadDetails[0].servicingRm}" />
					</td>
				</tr>
			</table>
		</fieldset>
	</logic:present>
	
	<logic:present name="tracking">
	<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.leadno" /></b></td>
          <td >${leadDetails[0].leadId}</td>
          <td><b><bean:message key="lbl.gendate"/></b></td>
          <td>${leadDetails[0].applicationdate}</td>
          <td><b><bean:message key="lbl.customername"/></b> </td>
          <td colspan="3" >${leadDetails[0].customerName}</td>
         </tr>
          <tr class="white2">
          	  <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${leadDetails[0].productType}</td>
	          <td><b><bean:message key="lbl.product"/></b></td>
	          <td >${leadDetails[0].product}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td >${leadDetails[0].scheme}</td>
	          <td ><b><bean:message key="lbl.currentStage"/></b></td>
	          <td>LEAD TRACKING</td>
          </tr>
        </table>
</td>
</tr>
</table>
 
</fieldset>	
		<fieldset id="leadtracking">
			<legend>
				<bean:message key="lbl.leadtracking" />
			</legend>
			<table width="100%">
				<tr>
					<td width="13%">
						<bean:message key="lbl.leadgenerator" /><span><font style="color: red;">  *</font></span>
						<html:hidden property="leadallocation" styleId="leadallocation" value="${leadDetails[0].leadId}" />
					</td>
					<td width="13%">
						<html:text styleId="leadGenerator" property="leadGenerator" readonly="true" styleClass="text" value="${leadDetails[0].leadGenerator}">
						</html:text>

					</td>
					<td width="13%">
						<bean:message key="lbl.leadservicingrm" />
					</td>
					<td width="13%">
						<html:text property="servicingRm" styleClass="text"  readonly="true" styleId="servicingRm" maxlength="10" value="${leadDetails[0].servicingRm}" />
					</td>
				</tr>
			</table>
		</fieldset>
	</logic:present>


		<!-- /////////////////////////////////DIFFERENT END/////////////////////////////////////////// -->
		<!-- /////////////////////////////////DIFFERENT START/////////////////////////////////////////// 
		<div id="gridList"><!-- Calling Data Through AJAX --</div>-->
		
		
		<fieldset id="rm" style="display:none">
			<legend>
				<bean:message key="lbl.rmdetails" />
			</legend>
			<table width="100%">

				<tr>
					<td width="13%">
						<bean:message key="lbl.rmcode" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="rmCode1" styleClass="text" styleId="rmCode1" readonly="true" maxlength="10" value="${leadRMDetails[0].rmCode1}" />
						
					</td>

					<td width="13%">
						<bean:message key="lbl.leadrmnameNew" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="rmName1" styleId="rmName1" styleClass="text" readonly="true" value="${leadRMDetails[0].rmName1}"/>
					</td>
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.contactno" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="contactnorm" styleClass="text" readonly="true" style="text-align:right;" styleId="contactnorm" maxlength="10" value="${leadRMDetails[0].contactnorm}"/>
					</td>
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.leadgenzone" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="leadgenzonerm" styleClass="text" readonly="true" styleId="leadgenzonerm" value="${leadRMDetails[0].leadgenzonerm}"/>
						
					</td>
					<td width="13%">
						<bean:message key="lbl.leadgencity" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="leadgencityrm" styleId="leadgencityrm" readonly="true" styleClass="text" value="${leadRMDetails[0].leadgencityrm}"/>
					</td>

				</tr>


			</table>
		</fieldset>
		<fieldset id="vendor" style="display:none">
			<legend>
				<bean:message key="lbl.vendordetails" />
			</legend>
			<table width="100%">


				<tr>
					<td width="13%">
						<bean:message key="lbl.vendorcode" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="vendorCode1" styleClass="text" readonly="true" styleId="vendorCode1" value="${leadRMDetails[0].vendorCode1}" />
						
					</td>
					<td width="13%">
						<bean:message key="lbl.vendorname" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="vendorName1" styleId="vendorName1" styleClass="text" readonly="true" value="${leadRMDetails[0].vendorName1}" />
					</td>

				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.vendorhead" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="vendorHead1" styleId="vendorHead1" readonly="true" styleClass="text" value="${leadRMDetails[0].vendorHead1}" onblur="caseChange('leadCapturingDetails','vendorHead1');" />
					</td>
					<td width="13%">
						<bean:message key="lbl.contactno" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
					
						<html:text property="contactnovendor" styleClass="text" readonly="true" style="text-align:right;" styleId="contactnovendor1" maxlength="10" value="${leadRMDetails[0].contactnovendor}" />
					</td>
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.leadgenzone" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="leadgenzonevendor" styleClass="text" readonly="true" styleId="leadgenzonevendor" maxlength="10" value="${leadRMDetails[0].leadgenzonevendor}" />

					</td>
					<td width="13%">
						<bean:message key="lbl.leadgencity" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="leadgencityvendor" styleId="leadgencityvendor" readonly="true" styleClass="text" value="${leadRMDetails[0].leadgencityvendor}" />
					</td>
				</tr>


			</table>
		</fieldset>
<fieldset id="branch" style="display:none">
			<legend>
				<bean:message key="lbl.branchdetails" />
			</legend>
			<table width="100%">

				<tr>
					
					<td width="13%">
						<bean:message key="lbl.branchname" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="branchName1" styleId="branchName2" styleClass="text" readonly="true" value= "${leadRMDetails[0].branchName1}"></html:text>
					</td>
				</tr>
				<tr>
				<td width="13%">
						<bean:message key="lbl.branchexecutive" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="branchHead1" styleId="branchHead1" readonly="true" styleClass="text" value="${leadRMDetails[0].branchHead1}"/>
					</td>
					<td width="13%">
						<bean:message key="lbl.contactno" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="contactnobranch" styleClass="text" readonly="true" style="text-align:right;" styleId="contactnobranch" maxlength="10" value="${leadRMDetails[0].contactnobranch}" />
					</td>
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.leadgencity" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="leadgencitybranch" styleId="leadgencitybranch" readonly="true" styleClass="text" value="${leadRMDetails[0].leadgencitybranch}"/>
					</td>
				</tr>

			</table>
		</fieldset>
		
		<fieldset id="others" style="display: none">
			<legend>
				<bean:message key="lbl.leadGenDetail" />
			</legend>
			<table width="100%">

				<tr>
					<td width="13%">
						<bean:message key="lbl.branchname" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						
			<html:text property="branchName1Other" styleClass="text" styleId="branchName1others" readonly="true" tabindex="-1" value="${leadRMDetails[0].branchName1Other}" />
<!--			<input type="button" class="lovbutton" id="branchButton" onclick="openLOVCommon(156,'leadCapturingDetails','lbxBranchIdOther','','', '','','','branchName1others')" name="dealButton" disabled="disabled"/>-->
			<input type="hidden" name="lbxBranchIdOther" id="lbxBranchIdOther" value="${leadRMDetails[0].lbxBranchIdOther}" />

					</td>
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.branchexecutive" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
						
						<td >
							<html:text property="branchHead1Other" styleId="branchHead1others" styleClass="text" value="${leadRMDetails[0].branchHead1Other}" readonly="true" onblur="caseChange('leadCapturingDetails','branchHead1others');"	maxlength="50" />
					    </td>
					
					<td width="13%">
						<bean:message key="lbl.contactno" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="contactnobranchOther" styleClass="text" readonly="true" style="text-align:right;" styleId="contactnobranchothers" maxlength="10" value="${leadRMDetails[0].contactnobranchOther}" onkeypress="return numericOnly(event,10);" />
					</td>

				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.leadgencity" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="leadgencitybranchOther" styleId="leadgencitybranchothers" styleClass="text" value="${leadRMDetails[0].leadgencitybranchOther}" readonly="true" onblur="caseChange('leadCapturingDetails','leadgencitybranchothers');" maxlength="50" />
					</td>
				</tr>
			</table>
		</fieldset>

<fieldset>
			<legend>
				<bean:message key="lbl.sourceDetail" />
			</legend>
			<table width="100%">

				<tr>
				<td width="13%">
						<bean:message key="lbl.source" />
					</td>
				<td width="13%">

					<html:text property="sourceList" styleId="sourceList" styleClass="text" readonly="true" tabindex="-1" value="${leadDetails[0].sourceList}"></html:text>
						</td>
					<td width="13%">
						<bean:message key="lbl.genericDesciption" />
					</td>
					<td width="13%">
						<html:text property="description" styleClass="text" readonly="true" styleId="description" maxlength="100" value="${leadDetails[0].description}" />
					</td>
				</tr>
			</table>
		</fieldset>
		
	
	<logic:present name="leadDetails" >
 <logic:iterate id="leadDetailsObj" name="leadDetails">
 <fieldset>
			<legend>
				<bean:message key="lbl.customer_detail" />
			</legend>
			<table width="100%">

								<tr>

					<td width="13%">
						<bean:message key="lbl.relationship" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<logic:present name="leadOther">
						<td width="13%">
							<html:text property="relationship" styleId="relationship"
								readonly="true" styleClass="text"
								value="${leadDetails[0].relationship}">
							</html:text>
						</td>
					</logic:present>
					<logic:present name="leadNew">
						<td width="13%">
							<html:select property="relationship" styleId="relationship"
								styleClass="text" onchange="san();customerLov();" disabled="true"
								value="${leadDetails[0].relationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="addressId" styleClass="text" styleId="addressId"  value="" />
		<html:hidden property="lbxCustomerId" styleClass="text" styleId="lbxCustomerId"  value="" />
	
		<html:button property="customerIdButton" styleId="customerIdButton" onclick="closeLovCallonLov1();openLOVCommon(340,'customerForm','lbxCustomerId','','','','','copyCustomerDetails','customerName','address1','customerType');custtype();" value=" " styleClass="lovbutton"></html:button>
		
							
						</td>
					</logic:present>

					<td width="13%">
						<bean:message key="lbl.relationshipSince" />
					</td>
					<td width="13%" style="align: right">
						<html:text property="relationshipSince" styleClass="text"
							styleId="relationshipSince" maxlength="3" readonly="true"
							value="${leadDetails[0].relationshipSince}"
							onkeypress="return numericOnly(event,10);" />
					</td>
	
	
	
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.Type" />
						<span><font style="color: red;"> *</font> </span>
					</td>
					<td >
					
						<html:select property="customerType" styleClass="text" disabled="true"
							styleId="customerType" value="${leadDetails[0].customerType}"
							onchange="custtype();" >
							<html:option value="">--Select--</html:option>
							<html:option value="I">INDIVIDUAL</html:option>
							<html:option value="C">CORPORATE</html:option>
						</html:select>
					</td>					
					<td>

						<bean:message key="constitution" />
						<font color="red">*</font>
					</td>

					<td>

						<html:hidden property="constitution" styleId="constitution"
							value="${leadDetails[0].contitutionCode}"  />
							
				
					<div id="Individualconstitution" style="display:none">
						<html:select property="indconstitution" styleId="indconstitution"
							value="${leadDetails[0].contitutionCode}" styleClass="text" disabled="true"
							onchange="appendZero();">
							<html:option value="">--Select--</html:option>
							<logic:present name="indconstitutionlist">
								<logic:notEmpty name="indconstitutionlist">
									<html:optionsCollection name="indconstitutionlist"
										label="constitution" value="contitutionCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						</div>
						<div id="corporateconstitution" style="display:none">
						<html:select property="corconstitution" styleId="corconstitution"
							value="${leadDetails[0].contitutionCode}" styleClass="text" disabled="true"
							onchange="appendZero();">
							<html:option value="">--Select--</html:option>
							<logic:present name="corconstitutionlist">
								<logic:notEmpty name="corconstitutionlist">
									<html:optionsCollection name="corconstitutionlist"
										label="constitution" value="contitutionCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						</div>
						<div id="constitution">
						<html:select property="constitution" styleId="constitution" disabled="true"
							value="${leadDetails[0].contitutionCode}" styleClass="text"
							onchange="appendZero();">
							<html:option value="">--Select--</html:option>
							<logic:present name="constitutionlist">
								<logic:notEmpty name="constitutionlist">
									<html:optionsCollection name="constitutionlist"
										label="constitution" value="contitutionCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						</div>
					</td>
					

</tr>
				</table>
				</fieldset>
				
</logic:iterate>
</logic:present>					
		
		
		<logic:present name="leadDetails" >
 <logic:iterate id="leadDetailsObj" name="leadDetails">
<logic:equal value="C" name="leadDetailsObj" property="customerType">
<fieldset>
			<legend>
				<bean:message key="corporate.details" />
			</legend>
			<table width="100%">
			<tr>
						<td width="13%">
							<bean:message key="lbl.customername" />
							<span><font style="color: red;"> *</font> </span>
						</td>
						<td width="13%">
							<html:text property="customerName" styleClass="text"
								styleId="customerName" maxlength="50"
								value="${leadDetails[0].customerName}" tabindex="20"
								onblur="caseChange('leadCapturingDetails','customerName');" readonly="true" />
						</td>
				
				
					
				
					
			
					<td width="13%">
						<bean:message key="lbl.groupName" />
						<font color="red">*</font>
					</td >
					<td width="13%">
					<logic:present name="leadDetails">
					<logic:iterate name="leadDetails" id="list">
			
					<logic:equal property="groupType" name="list" value="N">
						<html:text property="groupType" styleId="groupType"  readonly="readonly"  styleClass="text" value="New" /> 
						</logic:equal>
							<logic:equal property="groupType" name="list" value="E">
									<html:text property="groupType" styleId="groupType"  readonly="readonly"  styleClass="text" value="Existing" /> 
							</logic:equal>
						
			
			
					<logic:equal property="groupType" name="list" value="N">
					<input type="text" id="groupName1" name="groupName" class="text" readonly="readonly"  value="${leadDetails[0].groupDesc}"  tabindex="-1" />
						
					</logic:equal>
				
					
					<logic:equal property="groupType" name="list" value="E">
						<input type="text" id="groupName" name="groupName" class="text" readonly="readonly"  value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						
						<html:hidden property="hGroupId1" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</logic:equal>
				
					</logic:iterate>
					</logic:present>
					</td>

					</tr><tr>

					<td>
						<bean:message key="registrationNo" />
						

					</td>
					<td>
						<html:text property="registrationNo" styleId="registrationNo"
							maxlength="25" value="${leadDetails[0].registrationNo}"
							styleClass="text" readonly="true"  />
					</td>

				

		
					<td>
						<bean:message key="businessSegment" />
						<font color="red">*</font>
					</td>
					<td>
						<html:text property="businessSegment" styleId="businessSegment"
							value="${leadDetails[0].businessSegment}" styleClass="text" readonly="true" />
							
					</td>
			
			</tr>
				<tr>

					 <td><bean:message key="lbl.industry" /><span><font style="color: red;">  *</font></span></td>
        <td>
        <html:text property="industry" readonly="true"  styleId="industry" readonly="true" styleClass="text" value="${leadDetails[0].industry}"/>
         <html:hidden  property="lbxIndustry" styleId="lbxIndustry"  value="${leadDetails[0].lbxIndustry}"/>
        
	        <input type="hidden" id="hIndustry" name="hIndustry"  value="${leadDetails[0].lbxIndustry}"/> 
          	 </td>
          	 
            <td><bean:message key="lbl.subIndustry" /><span><font style="color: red;">  *</font></span></td>
        	<td> 
        			
          		<html:text property="subIndustry" readonly="true" styleId="subIndustry" readonly="true" styleClass="text" value="${leadDetails[0].subIndustry}" />
          		<html:hidden  property="lbxSubIndustry" styleId="lbxSubIndustry" value="${leadDetails[0].lbxSubIndustry}" />
         				    
         	</td>
					</tr>
					<tr>
					<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="custPan" maxlength="10" styleClass="text"
							styleId="custPan" value="${leadDetails[0].custPan}" styleClass="text"
							 readonly="true"  />
					</td>
							
							<td width="13%"><bean:message key="lbl.turnover" /></td>
        <td><html:text property="turnOver" styleClass="text" readonly="true"  styleId="turnOver" value="${leadDetails[0].turnOver}"  />
          </td>
					</tr>
	

</table>

</fieldset>
</logic:equal>
</logic:iterate>
</logic:present>


<logic:present name="leadDetails">
 <logic:iterate id="leadDetailsObj" name="leadDetails">
<logic:equal value="I" name="leadDetailsObj" property="customerType">
<fieldset>
			<legend>
				<bean:message key="individual.details" />
			</legend>
			<table width="100%">
			<tr>
	<td width="13%">
							<bean:message key="individual.name" />
							<font color="red">*</font>
						</td>
						<td width="13%">
							<html:text property="firstName" styleId="firstName"
								styleClass="text" value="${leadDetails[0].firstName}"
								onchange="return upperMe('firstName');" maxlength="50" readonly="true" />
						</td>
				
					<td width="13%">
						<bean:message key="individual.last" />
						<font color="red">*</font>
					</td>
					<td width="13%">
						<html:text property="lastName" styleId="lastName"
							styleClass="text" value="${leadDetails[0].lastName}"
							onchange="return upperMe('lastName');" maxlength="50" readonly="true" />
					</td>
					</tr>
				<tr>
					<td>
						<bean:message key="individual.birthdate" />
						<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
						<font color="red">*</font>
					</td>
					
				
					<td>
						<html:text property="custDOB"
							styleId="custDOB" styleClass="text"
							value="${leadDetails[0].custDOB}"
							 readonly="true" />
					</td>
					<input type="hidden" name="sysDate" id="sysDate" value="${requestScope.sysDate}" />
					<input type="hidden" name="approve" id="approve" value="N" />
					
					<td>
						<bean:message key="fatherHusband" />
					</td>
					<td>
						<html:text property="fatherName" readonly="true" 
							styleId="fatherName" styleClass="text"
							value="${leadDetails[0].fatherName}" />
					</td>
				
					
		
			</tr>
			<!-- Sanjog Changes Start Here -->
			<tr>
					<td>
						<bean:message key="passport" />
					</td>				
				
					<td>
						<html:text property="passport" readonly="true" 
							styleId="passport" styleClass="text"
							value="${leadDetails[0].passport}" />
					</td>
					<td>
						<bean:message key="lbl.drivingLic" />
					</td>
					<td>
						<html:text property="dlNumber" readonly="true" 
							styleId="dlNumber" styleClass="text"
							value="${leadDetails[0].dlNumber}" />
					</td>
								
		
			</tr>
			<tr>
					<td>
						<bean:message key="voterId" />
					</td>				
				
					<td>
						<html:text property="voterId" readonly="true" 
							styleId="voterId" styleClass="text"
							value="${leadDetails[0].voterId}" />
					</td>
				<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="custPan" maxlength="10" styleClass="text"
							styleId="custPan" value="${leadDetails[0].custPan}" styleClass="text"
							 readonly="true"  />
					</td>
				</tr>
				<tr>
					<td width="13%"><bean:message key="lbl.salary" /></td>
        <td><html:text property="turnOver" styleClass="text" readonly="true"  styleId="turnOver" value="${leadDetails[0].turnOver}"  />
          </td>
          
         <td width="13%"><bean:message key="lbl.education" /></td>
        <td>
         <html:select styleId="eduDetail" property="eduDetail" styleClass="text" disabled="true" value="${leadDetails[0].eduDetail}" tabindex="-1">
			<html:option value="">---Select---</html:option>
				<logic:present name="eduDetail">
					<logic:notEmpty name="eduDetail">
						<html:optionsCollection name="eduDetail" label="eduDetailDesc" value="eduDetailCode" />
					</logic:notEmpty>
				</logic:present>
		</html:select>
		</td>
		
			</tr><!-- Sanjog Changes End Here -->
		</table>		
	</fieldset>
</logic:equal>
</logic:iterate>
</logic:present>
		

		<!-- /////////////////////////////////DIFFERENT END/////////////////////////////////////////// -->
		<fieldset>
			<legend>
				<bean:message key="lbl.commDetails" />
			</legend>
			<table width="100%">

				<tr>
					<td width="13%">
						<bean:message key="lbl.relationship" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="relationship" styleId="relationship" readonly="true"
							styleClass="text" value="${leadDetails[0].relationship}">
						</html:text>
					</td>
					<td width="13%">
						<bean:message key="lbl.relationshipSince" />
					</td>
					<td width="13%"  style="align:right"  >
						<html:text property="relationshipSince" styleClass="text"  readonly="true" styleId="relationshipSince" maxlength="50" value="${leadDetails[0].relationshipSince}" onkeypress="return numericOnly(event,10);"/>
					</td>
					
				</tr>
				<tr id="contactPersonDesignation" style="display: none;">
				<td width="13%">
						<bean:message key="lbl.contactPerson" />
					</td>
					<td width="13%">
						<html:text property="contactPerson" styleClass="text"
							styleId="contactPerson" maxlength="50" readonly="true"
							value="${leadDetails[0].contactPerson}"/>
					</td>
				<td width="13%">
						<bean:message key="lbl.persondesignation" />
					</td>
					<td width="13%">
						<html:text property="personDesignation" styleClass="text"
							styleId="personDesignation" maxlength="50" readonly="true"
							value="${leadDetails[0].personDesignation}" />
					</td>
					
			</tr>
				<tr>	
					
				<td width="13%">
						<bean:message key="address.type" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
				<html:text property="addresstype" styleClass="text" styleId="addresstype" value="${leadDetails[0].addresstype}"  readonly="true"  />
					</td>	
					
					<td width="13%">
						<bean:message key="lbl.addressLine1" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="address1" styleClass="text" readonly="true"
							styleId="address1" maxlength="50"
							value="${leadDetails[0].address1}"/>
					</td>
				</tr>
				<tr>	
				<td width="13%">
						<bean:message key="lbl.two" />
					</td>
					<td width="13%">
						<html:text property="address2" styleClass="text"
							styleId="address2" maxlength="50" readonly="true"
							value="${leadDetails[0].address2}" />
					</td>
				
					<td width="13%">
						<bean:message key="lbl.three" />
					</td>
					<td width="13%">
						<html:text property="address3" styleClass="text"
							styleId="address3" maxlength="50" readonly="true"
							value="${leadDetails[0].address3}" />
					</td>
					
					</tr>
				<tr>	
				
				<td width="13%">
						<bean:message key="lbl.country" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="country" styleId="country" styleClass="text"
							readonly="true" value="${leadDetails[0].country}"  />
					</td>
				
					<td width="13%">
						<bean:message key="lbl.state" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="state" styleId="state" styleClass="text"
							readonly="true" value="${leadDetails[0].state}" />
					</td>
			</tr>
				<tr>		
				
				<td width="13%">
						<bean:message key="lbl.dist" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="dist" styleId="dist" styleClass="text"
							readonly="true" value="${leadDetails[0].dist}" />
					</td>
					
						<td>
						<bean:message key="address.Tahsil" />
					</td>
					<td>
						<html:text property="tahsil" readonly="true" styleId="tahsil" styleClass="text"
							value="${leadDetails[0].tahsilDesc}" />
					</td>
						</tr>
				<tr>
				
					<td width="13%">
						<bean:message key="lbl.pincode" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="pincode" styleClass="text" styleId="pincode" readonly="true"  style="text-align:right;"
							maxlength="6" value="${leadDetails[0].pincode}" />
					</td>
					
				
					<td width="13%">
						<bean:message key="lbl.mobileoff" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						<html:text property="phoneOff" styleClass="text" styleId="phoneOff" readonly="true" style="text-align:right;"
							maxlength="10" value="${leadDetails[0].phoneOff}" />
					</td>
			</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.alter" />
					</td>
					<td width="13%">
						<html:text property="phoneRes" styleClass="text" readonly="true" style="text-align:right;"
							styleId="phoneRes" maxlength="10"
							value="${leadDetails[0].phoneRes}" />
					</td>
					
							
				
				<td width="13%">
						<bean:message key="lbl.email" />
					</td>
					<td width="13%">
						<html:text property="email" styleClass="text" styleId="email" readonly="true"
							maxlength="100" value="${leadDetails[0].email}" />
					</td>
						</tr>
				<tr>
				
					<td width="13%">
						<bean:message key="lbl.alternateemail" />
					</td>
					<td width="13%">
						<html:text property="altEmail" styleClass="text" readonly="true"
							styleId="altEmail" maxlength="100"
							value="${leadDetails[0].altEmail}"/>
					</td>
			
				<td width="13%">
						<bean:message key="lbl.landmark" />
					</td>
					<td width="13%">
						<html:text property="landmark" styleClass="text" readonly="true" styleId="landmark" readonly="true" maxlength="25" value="${leadDetails[0].landmark}"  onblur="caseChange('leadCapturingDetails','landmark');"/>
					</td>
					</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.stability" /><span><font style="color: red;">  *</font></span>
					</td>
					<td width="13%">
						Year<html:text property="noOfYears" styleId="noOfYears" readonly="true" value="${leadDetails[0].noOfYears}" styleClass="text7"/>
						Month<html:select property="noMonths" styleId="noMonths" disabled="true" value="${leadDetails[0].noMonths}" styleClass="gcdAddress">
						<html:option value="0">0</html:option>
							<html:option value="1">1</html:option>
							<html:option value="2">2</html:option>
							<html:option value="3">3</html:option>
							<html:option value="4">4</html:option>
							<html:option value="5">5</html:option>
							<html:option value="6">6</html:option>
							<html:option value="7">7</html:option>
							<html:option value="8">8</html:option>
							<html:option value="9">9</html:option>
							<html:option value="10">10</html:option>
							<html:option value="11">11</html:option>
						</html:select>
					</td>
				<td>
						<bean:message key="lbl.propertyOwnership"/>
					</td>
					<td>
						<html:select property="owner" disabled="true" styleId="owner" value="${leadDetails[0].owner}" styleClass="text">
							<html:option value="R">Rented</html:option>
							<html:option value="O">Owned</html:option>
							<html:option value="C">Company Provided</html:option>
						</html:select>
					</td>
					</tr>
					<tr>
       
          <td><bean:message key="lbl.areaCode" /></td> 
			  		 <td>
			  		      
			   			<html:text property="areaCodename" styleId="areaCodename" styleClass="text" readonly="true" value="${leadDetails[0].areaCodename}"/>
			   			
			     			
			      		<html:hidden  property="lbxareaCodeVal" styleId="lbxareaCodeVal" value="${leadDetails[0].lbxareaCodeVal}"  />
			
			  
			       </td>
          </tr>
          
     
			</table>
			
		</fieldset>

		
		<fieldset>
			<legend>
				<bean:message key="lbl.requested.loanDetail" />
			</legend>
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
				<tr>
					<td class="gridtd">
						<table width="100%" border="0" cellpadding="1" cellspacing="1">
							<tr class="white2">
								<td>
									<strong><bean:message key="lbl.products" /><span><font style="color: red;">  *</font></span> </strong>
								</td>
								<td>
									<strong><bean:message key="lbl.scheme" /><span><font style="color: red;">  *</font></span> </strong>
								</td>
								<td>
									<strong><bean:message key="lbl.loanAmount" /><span><font style="color: red;">  *</font></span> </strong>
								</td>
								<td>
									<strong><bean:message key="lbl.loanTenure" /> <span><font style="color: red;">  *</font></span></strong>
								</td>
								<td>
									<strong><bean:message key="lbl.loanPurpose" /> <span><font style="color: red;">  *</font></span></strong>
								</td>
								<td>
									<strong><bean:message key="lbl.loanType" /></strong>
								</td>
								<td>
									<strong><bean:message key="lbl.assetDes" /></strong>
								</td>
								<td>
									<strong><bean:message key="lbl.sectorType" /> </strong>
								</td>
							</tr>
							<tr class="white1">
								<td align="left">
									<html:text property="product" styleId="product"
										styleClass="text2" value="${leadDetails[0].product}"
										readonly="true"></html:text>
								</td>
								<td align="left">
									<input type="hidden" name="lbxscheme" id="lbxscheme" />
									<html:text property="scheme" styleClass="text2" styleId="scheme"
										value="${leadDetails[0].scheme}"  readonly="true" />
								</td>
								<td align="left">
									<html:text property="loanAmount" styleClass="text2" readonly="true"
										styleId="loanAmount" maxlength="10"
										value="${leadDetails[0].loanAmount}"  onkeypress="return numbersonly(event, id, 10)" onblur="formatNumber(this.value, id);" 
										onkeyup="checkNumber(this.value, event, 10, id);" onfocus="keyUpNumber(this.value, event, 10, id);" />
								</td>
								<td align="left">
									<html:text property="loanTenure" styleClass="text2" readonly="true" 
										styleId="loanTenure" maxlength="10"
										value="${leadDetails[0].loanTenure}"  />
								</td>
								<td align="left">
									<html:text property="loanPurpose" styleClass="text2" readonly="true"
										styleId="loanPurpose" maxlength="50"
										value="${leadDetails[0].loanPurpose}" />
								</td>
								
							<!-- Changes Start By Sanjog-->
							
								<td align="left">
									<html:select styleId="loanType" property="loanType" styleClass="text2" value="${leadDetails[0].loanType}" disabled="true"
										>
											<html:optionsCollection name="getLoanType" label="loanTypeName" value="loanTypeID" />
									</html:select>
								</td>
								<td align="left">
									<html:text property="otherDetails" styleClass="text2"
								styleId="otherDetails" maxlength="100"
								value="${leadDetails[0].otherDetails}" disabled="true" style="text-align:left;"/>
								</td>
								<td >
						            <html:select property="sectorType" styleClass="text2" styleId="sectorType" value="${leadDetails[0].sectorType}" disabled="true">
							               <html:optionsCollection name="sector" label="sectorName" value="sectorId" /> 
						          	</html:select>
						         </td>
							</tr>
							<!-- Changes End By Sanjog-->
							<logic:present name="alocation">
							<tr id="allocationBottom" class="white1">
							<td colspan="8">								
																	
									<fieldset id="leadallocationlov">
											<legend>
												<bean:message key="lbl.leadallocation" />
											</legend>
										<table width="100%">
											<tr>
												<td width="13%">
													<bean:message key="lbl.branch" /><span><font style="color: red;">  *</font></span>
												</td>
												<td width="13%">
													<html:text property="branchDet" styleClass="text" styleId="branchDet" maxlength="10" readonly="true" value="${getAlloDetail[0].branchDet}" />
													<input type="button" class="lovbutton" id="branchButton" onclick="openLOVCommon(156,'leadAllocationDetails','lbxBranchId','','rmAllo','lbxUserId','','','branchDet')" value=" " tabindex="1" name="dealButton">
													<input type="hidden" name="lbxBranchId" id="lbxBranchId" value="${getAlloDetail[0].lbxBranchId}"/>
												</td>
												<td width="13%">
													<bean:message key="lbl.rm" /><span><font style="color: red;">  *</font></span>
												</td>
												<td width="13%">
													<html:text property="rmAllo" styleClass="text" styleId="rmAllo"	maxlength="10" readonly="true" value="${alloDetails[0].rmAllo}" />
													<input type="button" class="lovbutton" id="leadButton" onclick="openLOVCommon(261,'leadAllocationDetails','lbxUserId','branchDet','lbxUserId','lbxBranchId','Please Select Branch First !!!','','rmAllo')" value=" " tabindex="2" name="dealButton">
													<input type="hidden" name="lbxUserId" id="lbxUserId" value="${alloDetails[0].lbxUserId}" />
													
												</td>
											</tr>
									
										</table>
									</fieldset>
								
							</td>
							</tr>
							</logic:present>
							<logic:present name="alocationBranch">
							<tr id="allocationBottom" class="white1">
							<td colspan="5">								
																	
									<fieldset id="leadallocationlov">
											<legend>
												<bean:message key="lbl.leadallocation" />
											</legend>
										<table width="100%">
											<tr>
												<td width="13%">
													<bean:message key="lbl.branch" /><span><font style="color: red;">  *</font></span>
												</td>
												<td width="13%">
													<html:text property="branchDet" styleClass="text" styleId="branchDet" maxlength="10" readonly="true" value="${leadRMDetails[0].branchName1}" />
													<input type="hidden" name="lbxBranchId" id="lbxBranchId" value="${leadRMDetails[0].branchCode1}"/>
												</td>
												<td width="13%">
													<bean:message key="lbl.rm" /><span><font style="color: red;">  *</font></span>
												</td>
												<td width="13%">
													<html:text property="rmAllo" styleClass="text" styleId="rmAllo"	maxlength="10" readonly="true" value="${alloDetails[0].rmAllo}" />
													<input type="button" class="lovbutton" id="leadButton" onclick="openLOVCommon(261,'leadAllocationDetails','lbxUserId','branchDet','lbxUserId','lbxBranchId','Please Select Branch First !!!','','rmAllo')" value=" " tabindex="2" name="dealButton">
													<input type="hidden" name="lbxUserId" id="lbxUserId" value="${alloDetails[0].lbxUserId}" />
												</td>
											</tr>
									
										</table>
									</fieldset>
								
							</td>
							</tr>
							</logic:present>
									<!-- /////////////////////////////////DIFFERENT END/////////////////////////////////////////// -->
									</table>
								</td>
							</tr>
						<logic:present name="alocation">
							<tr>
				
								<td colspan="7" class="white1">

									

									<button type="button" name="Allocation" id="Allocation" class="topformbutton3" onclick="return saveAllocation(id);" title="Alt+L" accesskey="L" tabindex="3"><bean:message key="button.alloclead" /></button>
									
									<html:hidden property="status" value="${leadDetails[0].status}" styleId="status"></html:hidden>
								</td>
							</tr>
							</logic:present>
							<logic:present name="alocationBranch">
							<tr>
				
								<td colspan="7" class="white1">

									

									<button type="button" name="Allocation" id="Allocation" class="topformbutton3" onclick="return saveAllocation(id);" title="Alt+L" accesskey="L" tabindex="3"><bean:message key="button.alloclead" /></button>
									
									<html:hidden property="status" value="${leadDetails[0].status}" styleId="status"></html:hidden>
								</td>
							</tr>
							</logic:present>
			</table>
		</fieldset>
	<logic:present name="msg">
			<script type="text/javascript">
if('<%=request.getAttribute("msg").toString()%>'=='LA'){
	leadchange(document.getElementById('leadGenerator1').value);
	alert('<bean:message key="lbl.leadallocated"/>');
	document.getElementById('leadAllocationDetails').action="<%=request.getContextPath()%>/leadCommonList.do";
	document.getElementById('leadAllocationDetails').submit();

	}

</script>
		</logic:present>
		
			<logic:present name="leadDetails">
			   <logic:empty name="leadDetails">
					<script type="text/javascript">
					
			             alert('No Data Found');
			             self.close(); 
		                 
		            </script>
               </logic:empty>
		</logic:present>
		
	</html:form>
	<script>
	setFramevalues("leadAllocationDetails");
</script>
		
</body>
</html:html>
