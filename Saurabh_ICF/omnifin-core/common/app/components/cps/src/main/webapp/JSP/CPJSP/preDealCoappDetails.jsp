<%--     Created By Pooja     --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ page import="java.util.Date"%>
<%@page import="com.lowagie.text.Document"%>
<%@ page language="java" import="java.util.Calendar"%>
<%@ page language="java" import="java.text.SimpleDateFormat,java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>

	<title><bean:message key="a3s.noida" />
	</title>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/popup.js"></script>

 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cpScript/cpPreDeal.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
					 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript">
	function newOne(){
			hideorshow(document.getElementById('leadGenerator').value);
	}
	
	
	function coAppsan2(){
		
	 
		if(document.getElementById("coApprelationship1").value == "New")
		{
			document.getElementById("coAppcustomerType1").removeAttribute("disabled");
			
		}else{
			
			document.getElementById("coAppcustomerType1").setAttribute("disabled");
		}
	
		document.getElementById("coAppcustomerType1").value="";
		
	}
	
	
	
	function coAppsan(){
		
		
		
		if(document.getElementById("coApprelationship1").value == "New")
		{
			document.getElementById("coAppaddresstype1").removeAttribute("disabled","true");
			if(document.getElementById("coAppfirstName")){
			document.getElementById("coAppfirstName").removeAttribute("disabled","true");
			}
			document.getElementById("coAppcustPanInd").removeAttribute("disabled","true");
		//	document.getElementById("coAppaadhaar").removeAttribute("disabled","true");
			if(document.getElementById("coAppcustomerIdButton") != null){
				document.getElementById("coAppcustomerIdButton").style.display='none';
			}
		
		}else {
			
			
			document.getElementById("coAppcustomerType1").setAttribute("disabled","true");
			document.getElementById("coAppaddresstype1").setAttribute("disabled","true");
			if(document.getElementById("coAppfirstName")){
				document.getElementById("coAppfirstName").setAttribute("disabled","true");
				}
			if(document.getElementById("coAppcustPanInd").value!=""){
			document.getElementById("coAppcustPanInd").setAttribute("disabled","true");
			}
			else{
				document.getElementById("coAppcustPanInd").removeAttribute("disabled","true");
			}
		/*	if(document.getElementById("coAppaadhaar").value!=""){
			document.getElementById("coAppaadhaar").setAttribute("disabled","true");
			}
			else{
			document.getElementById("coAppaadhaar").removeAttribute("disabled","true");
			}*/
			if(document.getElementById("coAppcustomerIdButton") != null){
				document.getElementById("coAppcustomerIdButton").style.display='';	
				document.getElementById("coAppcustomerIdButton").focus();
				document.getElementById("coAppcustomerIdButton").removeAttribute("disabled");
			}
			return false;
		}
	
	}
	
	function coAppsan1(){
			if(document.getElementById("coAppcustomerIdButton") != null){
				document.getElementById("coAppcustomerIdButton").style.display='';	
				document.getElementById("coAppcustomerIdButton").focus();
			}
			return false;
	
	
	}
	 
	</script>
		<%
		     ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	  
	         String dateFormat=resource.getString("lbl.dateForDisbursal");
			 Calendar currentDate = Calendar.getInstance();
			 SimpleDateFormat formatter= new SimpleDateFormat(dateFormat);
			 String sysDate = formatter.format(currentDate.getTime());	 
			 request.setAttribute("sysDate",sysDate);		
		 %>
		 
		<style type="text/css">
		
		legend{
		font-size:  1.2em;
		}
		</style> 
		 
		 
		 
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

<body onload="coAppsan();coAppcusttype();enableAnchor();showHideDescLovOnLoad();hideorshow(document.getElementById('leadGenerator').value);">


	<html:form action="/preDealCoappSearch"
		styleId="leadCapturingDetails" method="post">
		<input type="hidden" name="leadcapt" id="leadcapt" />
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
     <input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
     <input type="hidden" id="emailMandatoryFlag" name="emailMandatoryFlag" value='${emailMandatoryFlag}'/>		
     <input type="hidden" id="minTenure" name="minTenure" value='${minTenure}'/>
	<input type="hidden" id="maxTenure" name="maxTenure" value='${maxTenure}'/>
	<input type="hidden" id="leadId" name="leadId" value='${leadId}'/>   
	
		<html:hidden property="contextPath" styleId="contextPath"
			value="<%=request.getContextPath()%>" />
			<logic:present name="leadNew">
		<html:hidden property="leadGenerator1" styleClass="text"
			styleId="leadGenerator1" value="${leadRMDetails[0].leadGenerator1}" />
			</logic:present>
			<logic:notPresent name="leadNew">
			<html:hidden property="leadGenerator1" styleClass="text"
			styleId="leadGenerator1" value="${leadRMDetails[0].leadGenerator1}" />
			</logic:notPresent>
		<fieldset>

			<table cellSpacing=0 cellPadding=0 width="100%" border=0>
				<tr>
					<td class="gridtd">
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr class="white2">
								<td>
									<b><bean:message key="lbl.preDealNo" />
									</b>
								</td>
								<td>
									${leadDetails[0].leadId}
								</td>
								<td>
									<b><bean:message key="lbl.preDealgendate" />
									</b>
								</td>
								<td>
									${leadDetails[0].applicationdate}
								</td>
								<td>
									<b><bean:message key="lbl.customername" />
									</b>
								</td>
								<td colspan="3">
									${leadDetails[0].customerName}
								</td>
							</tr>
							<tr class="white2">
								<td>
									<b><bean:message key="lbl.productType" />
									</b>
								</td>
								<td>
									${leadDetails[0].productType}
								</td>
								<td>
									<b><bean:message key="lbl.product" />
									</b>
								</td>
								<td>
									${leadDetails[0].product}
								</td>
								<td>
									<b><bean:message key="lbl.scheme" />
									</b>
								</td>
								<td>
									${leadDetails[0].scheme}
								</td>
								<td>
									<b><bean:message key="lbl.currentStage" />
									</b>
								</td>
								<td>
									SINGLE PAGE BUREAU
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

		</fieldset>
		
<logic:notPresent name="cibilDone">
<div id='corporatefield' style="display: none;">
<fieldset>
			<legend> 
				<bean:message key="lbl.corporate.details" />
			</legend> 
			<table width="100%">
			<tr>
						<td width="13%" >
							<bean:message key="lbl.customername" />
							<span><font style="color: red;"> *</font> </span>
						</td>
						<td width="13%" >
							<html:text property="customerName" styleClass="text"
								styleId="customerName" maxlength="250"
								value="${leadDetails[0].customerName}"
								onblur="caseChange('leadCapturingDetails','customerName');" />
						</td>
				
				
					
				
					
			
					<td width="13%">
						<bean:message key="lbl.groupName" />
						<font color="red">*</font>
					</td>
					<td width="13%">
					 
					<html:select property="groupType" styleId="groupType" onchange="groupselect();" styleClass="text" value="${leadDetails[0].groupType}"> 
						<html:option value="">--Select--</html:option> 
						<html:option value="N">New</html:option> 
						<html:option value="E">Existing</html:option> 
					</html:select>
					<logic:notPresent name="leadDetails">
					<div id="groupLov" style="display:none">
						<input type="text" id="groupName" name="groupName" class="text" value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
				
					<div id="groupText" style="display:none">
						<input type="text" id="groupName1" name="groupName1" class="text" value="${leadDetails[0].groupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:notPresent>
					
					<logic:present name="leadDetails">
					<logic:iterate name="leadDetails" id="list">
					<logic:equal property="groupType" name="list" value="N">
					<div id="groupLov" style="display:none">
						<input type="text" id="groupName" name="groupName" class="text" value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
					<div id="groupText" >
						<input type="text" id="groupName1" name="groupName1" class="text" value="${leadDetails[0].groupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
						
					</logic:equal>
					<logic:equal property="groupType" name="list" value="E">
										
					<div id="groupLov" >
						<input type="text" id="groupName" name="groupName" class="text" value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
				
					<div id="groupText" style="display:none">
						<input type="text" id="groupName1" name="groupName1" class="text" value="${leadDetails[0].groupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					<logic:equal property="groupType" name="list" value="">
										
					<div id="groupLov" style="display: none;">
						<input type="text" id="groupName" name="groupName" class="text" value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
				
					<div id="groupText" style="display:none">
						<input type="text" id="groupName1" name="groupName1" class="text" value="${leadDetails[0].groupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
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
							styleClass="text" />
					</td>

				

		
					<td>
						<bean:message key="businessSegment" />
						<font color="red">*</font>
					</td>
					<td>
						<html:select property="businessSegment" styleId="businessSegment"
							value="${leadDetails[0].businessSegment}" styleClass="text">
							<html:option value="">--Select--</html:option>
							<logic:present name="businessSegmentList">
								<logic:notEmpty name="businessSegmentList">
									<html:optionsCollection name="businessSegmentList"
										label="businessSegmentDesc" value="businessSegmentCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
			
			</tr>
				<tr>

					<td>
						<bean:message key="lbl.industry" />
						
					</td>
					<td>
						<html:text property="industry" readonly="true" styleId="industry"
							styleClass="text" value="${leadDetails[0].industry}"
							tabindex="-1" />
						<html:hidden property="lbxIndustry" styleId="lbxIndustry"
							value="${leadDetails[0].lbxIndustry}" />
						<html:button property="industryButton" styleId="industryButton"
							onclick="closeLovCallonLov1();openLOVCommon(253,'leadCapturingDetails','lbxIndustry','','', '','','lovCoAppIndustryLead','industry')"
							value=" " styleClass="lovbutton" >
						</html:button>
						<input type="hidden" id="hIndustry" name="hIndustry" />
					</td>
				
					<td>
						<bean:message key="lbl.subIndustry" />
						
					</td>
					<td>

						<html:text property="subIndustry" readonly="true"
							styleId="subIndustry" styleClass="text"
							value="${leadDetails[0].subIndustry}" tabindex="-1" />
						<html:hidden property="lbxSubIndustry" styleId="lbxSubIndustry"
							value="${leadDetails[0].lbxSubIndustry}" />
						<html:button property="subIndustryButton"
							styleId="subIndustryButton"
							onclick="closeLovCallonLov('industry');openLOVCommon(254,'leadCapturingDetails','hIndustry','industry','lbxSubIndustry', 'lbxIndustry','Please Select Industry','','subIndustry')"
							value=" " styleClass="lovbutton" >
						</html:button>
					</td>
					</tr>
					<tr>
					
					<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="custPan" maxlength="10" styleClass="text"
							styleId="custPan" value="${leadDetails[0].custPan}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('custPan');" />
					</td>
					</tr>
	

</table>

</fieldset>
</div>


		<!-- Rohit Changes for coApplicant ang gaurantor -->
		<fieldset>
			<legend>
				<bean:message key="lbl.Co_app_customer_detail" />
			</legend>
			<table width="100%">
<%-- <tr>
<td><bean:message key="lbl.CoAppStatus" /></td>
      <td>
      <logic:equal value="A" name="coAppStatus">

         <input type="checkbox" name="coAppStatus" id="coAppStatus" checked="checked"  onclick="coAppEnable();"/>
       </logic:equal>
      <logic:notEqual value="A" name="coAppStatus">
     
         <input type="checkbox" name="coAppStatus" id="coAppStatus" onclick="coAppEnable();" />
      </logic:notEqual></td>
</tr> --%>
								<tr>

					<td width="13%">
						<bean:message key="lbl.relationship" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<logic:present name="leadOther">
						<td width="13%" id="coApp">
							<html:select property="coApprelationship" styleId="coApprelationship1"
								styleClass="text" onchange="coAppsan();coAppsan2();coAppcleanUp();customerLov();"
								value="${leadDetails[0].coApprelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="coAppaddressId" styleClass="text" styleId="coAppaddressId"  value="" />
								<html:hidden property="coApprelationship" styleClass="text" styleId="coApprelationship"  value="${leadDetails[0].coApprelationship}" />
		<html:hidden property="coApplbxCustomerId" styleClass="text" styleId="coApplbxCustomerId"  value="${leadDetails[0].coApplbxCustomerId}" />
	
		<html:button property="coAppcustomerIdButton" styleId="coAppcustomerIdButton" onclick="closeLovCallonLov1();openLOVCommon(192043,'customerForm','coApplbxCustomerId','','','','','copyCoAppCustomerDetails','coAppcustomerName','coAppaddress1','coAppcustomerType');custtype();coAppsan1();" value=" " styleClass="lovbutton"></html:button>
		
					
						</td>
						<td width="13%" id="coApp1" style="display: none">
							<html:select property="coApprelationship" styleId="coApprelationship1"
								styleClass="text" onchange="coAppsan();coAppsan2();coAppcleanUp();customerLov();"
								value="${leadDetails[0].coApprelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="coAppaddressId" styleClass="text" styleId="coAppaddressId"  value="" />
		<html:hidden property="coApplbxCustomerId" styleClass="text" styleId="coApplbxCustomerId"  value="${leadDetails[0].coApplbxCustomerId}" />
	<html:hidden property="coApprelationship" styleClass="text" styleId="coApprelationship"  value="${leadDetails[0].coApprelationship}" />
		<html:button property="coAppcustomerIdButton" styleId="coAppcustomerIdButton" onclick="closeLovCallonLov1();openLOVCommon(192043,'customerForm','coApplbxCustomerId','','','','','copyCoAppCustomerDetails','coAppcustomerName','coAppaddress1','coAppcustomerType');custtype();" value=" " styleClass="lovbutton"></html:button>
		
							
						</td>
					</logic:present>
					<logic:present name="leadNew">
						<td width="13%">
							<html:select property="coApprelationship" styleId="coApprelationship"
								styleClass="text" onchange="coAppsan();coAppsan2();coAppcleanUp();customerLov();"
								value="${leadDetails[0].coApprelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="coAppaddressId" styleClass="text" styleId="coAppaddressId"  value="" />
		<html:hidden property="coApplbxCustomerId" styleClass="text" styleId="coApplbxCustomerId"  value="${leadDetails[0].coApplbxCustomerId}" />
	
		<html:button property="coAppcustomerIdButton" styleId="coAppcustomerIdButton" onclick="closeLovCallonLov1();openLOVCommon(192043,'customerForm','coApplbxCustomerId','','','','','copyCoAppCustomerDetails','coAppcustomerName','coAppaddress1','coAppcustomerType');custtype();" value=" " styleClass="lovbutton"></html:button>
		
							
						</td>
					</logic:present>

					<td width="13%">
						<bean:message key="lbl.Type" />
						<span><font style="color: red;"> *</font> </span>
					</td>
					<td width="13%">
						<html:select property="coAppcustomerType1" styleClass="text"
							styleId="coAppcustomerType1" value="${leadDetails[0].coAppcustomerType}"
							onchange="coAppupdateCust(this.value);coAppcusttype();" >
							<html:option value="">--Select--</html:option>
							<html:option value="I">INDIVIDUAL</html:option>
							<html:option value="C">CORPORATE</html:option>
						</html:select>
						<html:hidden property="coAppcustomerType" styleId="coAppcustomerType" value="${leadDetails[0].coAppcustomerType}" />
					</td></tr>
					<tr>
					<td width="13%">

						<bean:message key="constitution" />
						<font color="red">*</font>
					</td>
					<td><div id="coAppIndividualconstitution" style="display:none">
						<html:select property="coAppindconstitution" styleId="coAppindconstitution"
							value="${leadDetails[0].coAppcontitutionCode}" styleClass="text"
							onchange="indConSubprofile('coAppindconstitution');appendZero();">
							<html:option value="">--Select--</html:option>
							<logic:present name="indconstitutionlist">
								<logic:notEmpty name="indconstitutionlist">
									<html:optionsCollection name="indconstitutionlist"
										label="constitution" value="contitutionCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						</div>
						<div id="coAppcorporateconstitution" style="display:none">
						<html:select property="coAppcorconstitution" styleId="coAppcorconstitution"
							value="${leadDetails[0].coAppcontitutionCode}" styleClass="text"
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
						<div id="coAppconstitution">
						<html:select property="coAppconstitution" styleId="coAppconstitution"
							value="${leadDetails[0].coAppcontitutionCode}" styleClass="text"
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



<div id='coAppcorporatefield' style="display: none;">
<fieldset>
			<legend> 
				<bean:message key="lbl.c_corporate.details" />
			</legend> 
			<table width="100%">
			<tr>
						<td width="13%" >
							<bean:message key="lbl.customername" />
							<span><font style="color: red;"> *</font> </span>
						</td>
						<td width="13%" >
							<html:text property="coAppcustomerName" styleClass="text"
								styleId="coAppcustomerName" maxlength="250"
								value="${leadDetails[0].coAppcustomerName}"
								onblur="caseChange('leadCapturingDetails','coAppcustomerName');" />
						</td>
				
				
					
				
					
			
					<td width="13%">
						<bean:message key="lbl.groupName" />
						<font color="red">*</font>
					</td>
					<td width="13%">
					 
					<html:select property="coAppgroupType" styleId="coAppgroupType" onchange="coAppgroupselect();" styleClass="text" value="${leadDetails[0].coAppgroupType}"> 
						<html:option value="">--Select--</html:option> 
						<html:option value="N">New</html:option> 
						<html:option value="E">Existing</html:option> 
					</html:select>
					<logic:notPresent name="leadDetails">
					<div id="coAppgroupLov" style="display:none">
						<input type="text" id="coAppgroupName" name="coAppgroupName" class="text" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
				
					<div id="coAppgroupText" style="display:none">
						<input type="text" id="coAppgroupName1" name="coAppgroupName1" class="text" value="${leadDetails[0].coAppgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:notPresent>
					
					<logic:present name="leadDetails">
					<logic:iterate name="leadDetails" id="list">
					<logic:equal property="coAppgroupType" name="list" value="N">
					<div id="coAppgroupLov" style="display:none">
						<input type="text" id="coAppgroupName" name="coAppgroupName" class="text" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
					<div id="coAppgroupText" >
						<input type="text" id="coAppgroupName1" name="coAppgroupName1" class="text" value="${leadDetails[0].coAppgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
						
					</logic:equal>
					<logic:equal property="coAppgroupType" name="list" value="E">
										
					<div id="coAppgroupLov" >
						<input type="text" id="coAppgroupName" name="coAppgroupName" class="text" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
				
					<div id="coAppgroupText" style="display:none">
						<input type="text" id="coAppgroupName1" name="coAppgroupName1" class="text" value="${leadDetails[0].coAppgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					<logic:equal property="coAppgroupType" name="list" value="">
										
					<div id="coAppgroupLov" style="display: none;">
						<input type="text" id="coAppgroupName" name="coAppgroupName" class="text" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
				
					<div id="coAppgroupText" style="display:none">
						<input type="text" id="coAppgroupName1" name="coAppgroupName1" class="text" value="${leadDetails[0].coAppgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					</logic:iterate>
					</logic:present>
					</td>

					</tr><tr>

					<td>
						<bean:message key="registrationNo" />

					</td>
					<td>
						<html:text property="coAppregistrationNo" styleId="coAppregistrationNo"
							maxlength="25" value="${leadDetails[0].coAppregistrationNo}"
							styleClass="text" />
					</td>

				

		
					<td>
						<bean:message key="businessSegment" />
						<font color="red">*</font>
					</td>
					<td>
						<html:select property="coAppbusinessSegment" styleId="coAppbusinessSegment1"
							value="${leadDetails[0].coAppbusinessSegment}" styleClass="text">
							<html:option value="">--Select--</html:option>
							<logic:present name="businessSegmentList">
								<logic:notEmpty name="businessSegmentList">
									<html:optionsCollection name="businessSegmentList"
										label="businessSegmentDesc" value="businessSegmentCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						<html:hidden property="coAppbusinessSegment" styleClass="text" styleId="coAppbusinessSegment" value="${leadDetails[0].coAppbusinessSegment}" />
					</td>
			
			</tr>
				<tr>

					<td>
						<bean:message key="lbl.industry" />
						
					</td>
					<td>
						<html:text property="coAppindustry" readonly="true" styleId="coAppindustry"
							styleClass="text" value="${leadDetails[0].coAppindustry}"
							tabindex="-1" />
						<html:hidden property="coApplbxIndustry" styleId="coApplbxIndustry"
							value="${leadDetails[0].coApplbxIndustry}" />
						<html:button property="industryButton" styleId="industryButton"
							onclick="openLOVCommon(19252,'leadCapturingDetails','coApplbxIndustry','','', '','','lovCoAppIndustryLead','coAppindustry');closeLovCallonLov('');"
							value=" " styleClass="lovbutton" >
						</html:button>
						<input type="hidden" id="coApphIndustry" name="coApphIndustry" />
					</td>
				
					<td>
						<bean:message key="lbl.subIndustry" />
						
					</td>
					<td>

						<html:text property="coAppsubIndustry" readonly="true"
							styleId="coAppsubIndustry" styleClass="text"
							value="${leadDetails[0].coAppsubIndustry}" tabindex="-1" />
						<html:hidden property="coApplbxSubIndustry" styleId="coApplbxSubIndustry"
							value="${leadDetails[0].coApplbxSubIndustry}" />
						<html:button property="subIndustryButton"
							styleId="subIndustryButton"
							onclick="closeLovCallonLov('coAppindustry');openLOVCommon(19254,'leadCapturingDetails','coApphIndustry','coAppindustry','coApplbxSubIndustry', 'coApplbxIndustry','Please Select Industry','','coAppsubIndustry')"
							value=" " styleClass="lovbutton" >
						</html:button>
						
					</td>
					</tr>
					<tr>
					
					<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="coAppcustPan" maxlength="10" styleClass="text"
							styleId="coAppcustPan" value="${leadDetails[0].coAppcustPan}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('coAppcustPan');" />
					</td>
					</tr>
	

</table>

</fieldset>
</div>


<div id='coAppindividualfield'">
<fieldset>
			<legend>
				<bean:message key="lbl.c_individual.details" />
			</legend>
			<table width="100%">
			<tr>
	<td width="13%">
							<bean:message key="lbl.fname" />
							<font color="red">*</font>
						</td>
						<td width="13%">
							<html:text property="coAppfirstName" styleId="coAppfirstName"
								styleClass="text" value="${leadDetails[0].coAppfirstName}"
								onchange="return upperMe('coAppfirstName');" maxlength="50" />
						</td>
				<!-- pooja change for middle name -->
					<td width="13%">
						<bean:message key="individual.middle" />
						<!-- <font color="red">*</font> -->
					</td>
					<td width="13%">
						<html:text property="coAppmiddleName" styleId="coAppmiddleName"
							styleClass="text" value="${leadDetails[0].coAppmiddleName}"
							onchange="return upperMe('coAppmiddleName');" maxlength="50" />
					</td>
					</tr>
					<tr>
					<td width="13%">
						<bean:message key="individual.last" />
						<font color="red">*</font>
					</td>
					<td width="13%">
						<html:text property="coApplastName" styleId="coApplastName"
							styleClass="text" value="${leadDetails[0].coApplastName}"
							onchange="return upperMe('coApplastName');" maxlength="50" />
					</td>
					</tr>
					<!-- pooja change for middle name -->
				<tr>
					<td>
						<bean:message key="individual.birthdate" />
						<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
					</td>
					
				
					<td>
						<html:text property="coAppcustDOB" onchange="checkDate('coAppcustDOB');coAppcheckcustDOB('coAppcustDOB');"
							styleId="coAppcustDOB" styleClass="text"
							value="${leadDetails[0].coAppcustDOB}" />
					</td>
					<td>
						<bean:message key="fatherHusband" /><font color="red">*</font>
					</td>
					<td>
						<html:text property="coAppfatherName"
							styleId="coAppfatherName" styleClass="text" onblur="caseChange('leadCapturingDetails','coAppfatherName');"
							value="${leadDetails[0].coAppfatherName}"  maxlength="50"/>
					</td>
					<input type="hidden" name="sysDate" id="sysDate" value="${requestScope.sysDate}" />
					<input type="hidden" name="approve" id="approve" value="N" />
				
					
		
			</tr>
			<!-- Sanjog Changes Start Here -->
			<tr>
					
					<td><bean:message key="lbl.gender" /><font color="red">*</font></td>
          <td><html:select property="coAppgenderIndividual" value="${leadDetails[0].coAppgenderIndividual}" styleClass="text" styleId="coAppgenderIndividual">
				<logic:present name="GenderCategory">
					<logic:notEmpty name="GenderCategory">
					<html:optionsCollection name="GenderCategory" label="genderDesc" value="genderCode" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
		  </td>
					<td>
						<bean:message key="lbl.drivingLic" />
					</td>
					<td>
						<html:text property="coAppdlNumber"
							styleId="coAppdlNumber" styleClass="text"
							value="${leadDetails[0].coAppdlNumber}" maxlength="20"/>
					</td>
								
		
			</tr>
			<tr>
					<td>
						<bean:message key="voterId" />
					</td>				
				
					<td>
						<html:text property="coAppvoterId"
							styleId="coAppvoterId" styleClass="text"
							value="${leadDetails[0].coAppvoterId}" maxlength="20"/>
					</td>
					
					<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="coAppcustPanInd" maxlength="10" styleClass="text"
							styleId="coAppcustPanInd" value="${leadDetails[0].coAppcustPanInd}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('coAppcustPanInd');" />
					</td>
					</tr>
					<tr>
					<td>
						<bean:message key="passport" />
					</td>				
				
					<td>
						<html:text property="coApppassport"
							styleId="coApppassport" styleClass="text"
							value="${leadDetails[0].coApppassport}" maxlength="20"/>
					</td>
        <td><bean:message key="aadhaar" /></td>
          <td><html:text property="coAppaadhaar" styleClass="text" styleId="coAppaadhaar" readonly="true"   onkeypress="return numericOnly(event,12);" value="${leadDetails[0].coAppaadhaar}" maxlength="12"/></td>
					
					</tr>
					
		</table>		
	</fieldset>
</div>

		<fieldset>
			<legend>
				<bean:message key="lbl.c_commDetails" />
			</legend>
			<table width="100%">

<tr>
<td><bean:message key="lbl.CopyAdd" /></td>
 <td><input type="checkbox" name="copyAppCoapp" id="copyAppCoapp" onclick="copyApplicantToCoAppAdd();" /></td>
</tr>
				<tr>	
					<td>
						<bean:message key="address.type" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:select property="coAppaddresstype" styleClass="text"
							styleId="coAppaddresstype1" value="${leadDetails[0].coAppaddresstype}">
							<html:option value="">--Select--</html:option>
							<html:optionsCollection name="addresstypeList"
								value="addresstypeid" label="addresstypename" />
						</html:select>
						<html:hidden property="coAppaddresstype" styleClass="text" styleId="coAppaddresstype" value="${leadDetails[0].coAppaddresstype}" />
					</td>
				
					<td width="13%">
						<bean:message key="lbl.addressLine1" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="coAppaddress1" styleClass="text"
							styleId="coAppaddress1" maxlength="50"
							value="${leadDetails[0].coAppaddress1}"
							onblur="caseChange('leadCapturingDetails','coAppaddress1');" />
					</td>

</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.two" />
					</td>
					<td width="13%">
						<html:text property="coAppaddress2" styleClass="text"
							styleId="coAppaddress2" maxlength="50"
							value="${leadDetails[0].coAppaddress2}"
							onblur="caseChange('leadCapturingDetails','coAppaddress2');" />
					</td>
					<td width="13%">
						<bean:message key="lbl.three" />
					</td>
					<td width="13%">
						<html:text property="coAppaddress3" styleClass="text"
							styleId="coAppaddress3" maxlength="50"
							value="${leadDetails[0].coAppaddress3}"
							onblur="caseChange('leadCapturingDetails','coAppaddress3');" />
					</td>
</tr>
	<!-- pooja code start for Co-Applicant Pincode -->
	<logic:present name="pincodeFlag">
<logic:equal value="N" name="pincodeFlag">
 <html:hidden property="pincodeFlag" styleId="pincodeFlag" styleClass="text" value="N"/>
<tr> 

		
	
		<td  width="13%">
						<bean:message key="lbl.country" /><span><font style="color: red;"> *</font>
						</span>
					</td>
					<td  width="13%">
						<logic:present name="leadDetails">
							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								tabindex="-1" readonly="true" value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(192068,'leadCapturingDetails','coApptxtCountryCode','','','','','coAppclearCountryLovChild','coAppcountry')"
								value="" name="countryButton" />
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:present>
						<logic:notPresent name="leadDetails">

							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								tabindex="-1" readonly="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(192068,'leadCapturingDetails','coApptxtCountryCode','','','','','coAppclearCountryLovChild','coAppcountry')"
								value="" name="countryButton"/>
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>

<td width="13%">
						<bean:message key="lbl.state" />
						 <span><font style="color: red;"> *</font>
						</span> 
					</td>
					<td width="13%">
						<html:text property="coAppstate" styleId="coAppstate" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].coAppstate}" />
						<input type="button" class="lovbutton" id="stateButton"
							onclick="openLOVCommon(192070,'leadCapturingDetails','coAppstate','coAppcountry','coApptxtStateCode', 'coApptxtCountryCode','Please select country first!!!','coAppclearStateLovChild','coApptxtStateCode')"
							value="" name="stateButton"/>
							<input type="hidden" name="coApptxtStateCode" id="coApptxtStateCode"
								value="${leadDetails[0].coApptxtStateCode}" />
					</td>	
</tr>

<tr>


<td width="13%">
						<bean:message key="lbl.dist" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="coAppdist" styleId="coAppdist" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].coAppdist}" />
						<input type="button" class="lovbutton" id="distButton"
							onclick="openLOVCommon(192072,'leadCapturingDetails','coAppdist','coAppstate','coApptxtDistCode', 'coApptxtStateCode','Please select state first!!!','coAppclearDistrictLovChild','coApptxtDistCode')"
							value=" " name="distButton"/>
							<input type="hidden" name="coApptxtDistCode" id="coApptxtDistCode"
								value="${leadDetails[0].coApptxtDistCode}" />
					</td>
					
					<td width="13%">
						<bean:message key="address.Tahsil" />
					</td>
					<td>
						<input type="text" name="coApptahsilDesc" id="coApptahsilDesc" readonly="readonly" value="${leadDetails[0].coApptahsilDesc}" />
						<html:hidden property="coApptahsil" styleId="coApptahsil" styleClass="text" value="${leadDetails[0].coApptahsil}"/>
						<html:button property="tahsilButton" styleId="tahsilButton" onclick="openLOVCommon(495,'leadCapturingDetails','coApptahsilDesc','coAppdist','coApptahsilDesc', 'coApptxtDistCode','Please select District first','','coApptahsil');" value=" " styleClass="lovbutton"> </html:button>
					</td>
					
</tr>


				<tr>
				<td width="13%">
						<bean:message key="lbl.pincode" /><span><font style="color: red;"> *</font>
						</span>
		</td>
					<td width="13%">
						<html:text property="coApppincode" styleClass="text" styleId="coApppincode"
							maxlength="6" value="${leadDetails[0].coApppincode}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,6);" />
					</td>
				
				
									<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="coAppphoneOff" styleClass="text"
							styleId="coAppphoneOff" maxlength="10"
							value="${leadDetails[0].coAppphoneOff}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,10);" />
					</td>
	</tr>
	</logic:equal>
	</logic:present>
	
	<logic:present name="pincodeFlag">
<logic:equal value="Y" name="pincodeFlag">
	<html:hidden property="pincodeFlag" styleId="pincodeFlag" styleClass="text" value="Y"/>
	<tr>
 
		<td><bean:message key="address.pincode" /><span><font style="color: red;"> *</font>
						</span></td>
			<td><html:text property="coApppincode" styleId="coApppincode" size="20" styleClass="text" readonly="true" value="${leadDetails[0].coApppincode}" tabindex="-1"></html:text>
            <html:hidden property="coApplbxPincode" styleId="coApplbxPincode" styleClass="text" value=""/>
 			<html:button property="pincodeButton" styleId="pincodeButton" onclick="openLOVCommon(192025,'leadCapturingDetails','coApppincode','','', '','','getCoAppCountryStateDistrictTahsilValue','coApptxnTahsilHID','hcommon');closeLovCallonLov('');" value=" " styleClass="lovbutton"> </html:button>
		</td>
						
<td><bean:message key="address.Tahsil" /></td>	
			<td id="coApplov2"> 
            	<div id="coApptahsildetail">
            		<html:text property="coApptahsil" styleId="coApptahsil" size="20" styleClass="text" readonly="true" value="${leadDetails[0].coApptahsilDesc}" ></html:text>
                	<html:hidden property="coApptxnTahsilHID" styleId="coApptxnTahsilHID" styleClass="text" value="${leadDetails[0].coApptahsil}"/>
   				</div>
   			</td>
   			
   			<td id="coApplov4" style="display: none"> 
            	<div id="coApptahsildetail">
            		<html:text property="coApptahsil4" styleId="coApptahsil4" size="20" styleClass="text"  value="${leadDetails[0].coApptahsil}" ></html:text>
                	<html:hidden property="coApptxnTahsilHID" styleId="coApptxnTahsilHID" styleClass="text" value="${leadDetails[0].coApptxnTahsilHID}"/>
   				</div>
   			</td>
			
				
   			
   			
	</tr>
	
	
	<tr id="coApplov5"> 
	<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font> -->
						</span></td>
          	<td >
          		<div id="coAppcityID">
          			<html:text property="coAppdist" styleId="coAppdist" size="20" styleClass="text" readonly="true" value="${leadDetails[0].coAppdist}" ></html:text>
          			<html:hidden property="coApptxtDistCode" styleId="coApptxtDistCode" styleClass="text" value="${leadDetails[0].coApptxtDistCode}"></html:hidden>
   				</div>
   			</td>
	<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="coAppstatedetail">
                	<html:text property="coAppstate" styleId="coAppstate" styleClass="text" size="20" value="${leadDetails[0].coAppstate}" readonly="true" ></html:text>
   					<html:hidden property="coApptxtStateCode" styleId="coApptxtStateCode" styleClass="text" value=" ${leadDetails[0].coApptxtStateCode}"></html:hidden>
    				<html:hidden property="coApptahsilDesc" styleId="coApptahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton" style="display: none" onclick="closeLovCallonLov('coAppcountry');openLOVCommon(192070,'leadCapturingDetails','coAppstate','coAppcountry','coApptxtStateCode', 'coApptxtCountryCode','Please select country first','coAppclearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	          	</div>
			</td>
		
   			
   			
		

	</tr>
	
	<tr id="coApplov6" style="display: none">
	<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
          	<td>
          		<div id="coAppcityID">
          			<html:text property="coAppdist4" styleId="coAppdist4" size="20" styleClass="text" readonly="true" value="${leadDetails[0].coAppdist}" ></html:text>
          			<html:hidden property="coApptxtDistCode" styleId="coApptxtDistCode" styleClass="text" value="${leadDetails[0].coApptxtDistCode}"></html:hidden>
  					<html:button property="distButton" styleId="districtButton" onclick="closeLovCallonLov('coAppstate6');openLOVCommon(20,'leadCapturingDetails','coAppdist4','coAppstate6','coApptxtDistCode', 'coApptxtStateCode','Please select state first','coAppclearTahsilLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   				</div>
   			</td>
	
	<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="coAppstatedetail">
                	<html:text property="coAppstate6" styleId="coAppstate6" styleClass="text" size="20" value="${leadDetails[0].coAppstate}" readonly="true" ></html:text>
   					<html:hidden property="coApptxtStateCode" styleId="coApptxtStateCode" styleClass="text" value=" ${leadDetails[0].coApptxtStateCode}"></html:hidden>
    				<html:hidden property="coApptahsilDesc" styleId="coApptahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton"  onclick="closeLovCallonLov('coAppcountry');openLOVCommon(192070,'leadCapturingDetails','coAppstate6','coApptxtCountryCode','coApptxtStateCode', 'coApptxtCountryCode','Please select country first','coAppclearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	          	</div>
			</td>
	
	
	
	
	</tr>
	
	<tr>
	<td width="13%">
						<bean:message key="lbl.country" /><span><font style="color: red;"> *</font>
						</span>
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td id="coApplov1" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								 readonly="true" value="${defaultcountry[0].defaultcountryname}" />
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
										value="${defaultcountry[0].defaultcountryid}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								readonly="true"	value="${defaultcountry[0].defaultcountryname}" />
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
					
					
					<td id="coApplov3" style="display: none" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								 readonly="true" value="${leadDetails[0].coAppcountry}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(192068,'leadCapturingDetails','coAppcountry','','','','','coAppclearCountryLovChild','coApptxtCountryCode')"
								value="" name="countryButton" />
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
									value="${leadDetails[0].coApptxtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								 readonly="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(192068,'leadCapturingDetails','coAppcountry','','','','','coAppclearCountryLovChild','coApptxtCountryCode')"
								value="" name="countryButton"/>
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
		
   	
						
		
				
					
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="coAppphoneOff" styleClass="text"
							styleId="coAppphoneOff" maxlength="10"
							value="${leadDetails[0].coAppphoneOff}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,10);" />
					</td>
	</tr>
	</logic:equal>
	</logic:present>
	<!-- pooja code end for Co-Applicant Pincode -->
				
				<tr>
					<td width="13%">
						<bean:message key="lbl.email" />
					</td>
					<td width="13%">
						<html:text property="coAppemail" styleClass="text" styleId="coAppemail"
							maxlength="100" value="${leadDetails[0].coAppemail}"/>
					</td>
					<td width="13%">
						<bean:message key="lbl.landmark" />
					</td>
					<td width="13%">
						<html:text property="coApplandmark" styleClass="text"
							styleId="coApplandmark" maxlength="25"
							value="${leadDetails[0].coApplandmark}"
							onblur="caseChange('leadCapturingDetails','coApplandmark');" />
					</td>
	</tr>
			
				

			</table>
		</fieldset>
		
<div id='gaurcorporatefield' style="display: none;">
<fieldset>
			<legend> 
				<bean:message key="lbl.g_corporate.details" />
			</legend> 
			<table width="100%">
			<tr>
						<td width="13%" >
							<bean:message key="lbl.customername" />
							<span><font style="color: red;"> *</font> </span>
						</td>
						<td width="13%" >
							<html:text property="gaurcustomerName" styleClass="text"
								styleId="gaurcustomerName" maxlength="250"
								value="${leadDetails[0].gaurcustomerName}"
								onblur="caseChange('leadCapturingDetails','gaurcustomerName');" />
						</td>
				
				
					
				
					
			
					<td width="13%">
						<bean:message key="lbl.groupName" />
						<font color="red">*</font>
					</td>
					<td width="13%">
					 
					<html:select property="gaurgroupType" styleId="gaurgroupType" onchange="gaurgroupselect();" styleClass="text" value="${leadDetails[0].gaurgroupType}"> 
						<html:option value="">--Select--</html:option> 
						<html:option value="N">New</html:option> 
						<html:option value="E">Existing</html:option> 
					</html:select>
					<logic:notPresent name="leadDetails">
					<div id="gaurgroupLov" style="display:none">
						<input type="text" id="gaurgroupName" name="gaurgroupName" class="text" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
				
					<div id="gaurgroupText" style="display:none">
						<input type="text" id="gaurgroupName1" name="gaurgroupName1" class="text" value="${leadDetails[0].gaurgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:notPresent>
					
					<logic:present name="leadDetails">
					<logic:iterate name="leadDetails" id="list">
					<logic:equal property="gaurgroupType" name="list" value="N">
					<div id="ggaurroupLov" style="display:none">
						<input type="text" id="gaurgroupName" name="gaurgroupName" class="text" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
					<div id="gaurgroupText" >
						<input type="text" id="gaurgroupName1" name="gaurgroupName1" class="text" value="${leadDetails[0].gaurgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
						
					</logic:equal>
					<logic:equal property="gaurgroupType" name="list" value="E">
										
					<div id="gaurgroupLov" >
						<input type="text" id="gaurgroupName" name="gaurgroupName" class="text" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
				
					<div id="gaurgroupText" style="display:none">
						<input type="text" id="gaurgroupName1" name="gaurgroupName1" class="text" value="${leadDetails[0].gaurgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					<logic:equal property="gaurgroupType" name="list" value="">
										
					<div id="gaurgroupLov" style="display: none;">
						<input type="text" id="gaurgroupName" name="gaurgroupName" class="text" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
				
					<div id="gaurgroupText" style="display:none">
						<input type="text" id="gaurgroupName1" name="gaurgroupName1" class="text" value="${leadDetails[0].gaurgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					</logic:iterate>
					</logic:present>
					</td>

					</tr><tr>

					<td>
						<bean:message key="registrationNo" />

					</td>
					<td>
						<html:text property="gaurregistrationNo" styleId="gaurregistrationNo"
							maxlength="25" value="${leadDetails[0].gaurregistrationNo}"
							styleClass="text" />
					</td>

				

		
					<td>
						<bean:message key="businessSegment" />
						<font color="red">*</font>
					</td>
					<td>
						<html:select property="gaurbusinessSegment" styleId="gaurbusinessSegment"
							value="${leadDetails[0].gaurbusinessSegment}" styleClass="text">
							<html:option value="">--Select--</html:option>
							<logic:present name="businessSegmentList">
								<logic:notEmpty name="businessSegmentList">
									<html:optionsCollection name="businessSegmentList"
										label="businessSegmentDesc" value="businessSegmentCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
			
			</tr>
				<tr>

					<td>
						<bean:message key="lbl.industry" />
						
					</td>
					<td>
						<html:text property="gaurindustry" readonly="true" styleId="gaurindustry"
							styleClass="text" value="${leadDetails[0].gaurindustry}"
							tabindex="-1" />
						<html:hidden property="gaurlbxIndustry" styleId="gaurlbxIndustry"
							value="${leadDetails[0].gaurlbxIndustry}" />
						<html:button property="industryButton" styleId="industryButton"
							onclick="closeLovCallonLov1();openLOVCommon(19253,'leadCapturingDetails','gaurlbxIndustry','','', '','','lovCoAppIndustryLead','gaurindustry')"
							value=" " styleClass="lovbutton" >
						</html:button>
						<input type="hidden" id="gaurhIndustry" name="gaurhIndustry" />
					</td>
				
					<td>
						<bean:message key="lbl.subIndustry" />
						
					</td>
					<td>

						<html:text property="gaursubIndustry" readonly="true"
							styleId="gaursubIndustry" styleClass="text"
							value="${leadDetails[0].gaursubIndustry}" tabindex="-1" />
						<html:hidden property="gaurlbxSubIndustry" styleId="gaurlbxSubIndustry"
							value="${leadDetails[0].gaurlbxSubIndustry}" />
						<html:button property="subIndustryButton"
							styleId="subIndustryButton"
							onclick="closeLovCallonLov('industry');openLOVCommon(192038,'leadCapturingDetails','gaurhIndustry','gaurindustry','gaurlbxSubIndustry', 'gaurlbxIndustry','Please Select Industry','','gaursubIndustry')"
							value=" " styleClass="lovbutton" >
						</html:button>
					</td>
					</tr>
					<tr>
					
					<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="gaurcustPan" maxlength="10" styleClass="text"
							styleId="gaurcustPan" value="${leadDetails[0].gaurcustPan}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('gaurcustPan');" />
					</td>
					
					
					</tr>

				</table>

</fieldset>
</div>
<fieldset>
<logic:notPresent name="existingCustomer">
					<tr>
					<html:hidden property="updateCustId" styleClass="text" styleId="updateCustId"  value="${leadDetails[0].updateCustId}" />
						<td>
							<button type="button" name="Save" id="Save"
								class="topformbutton2" onclick="return saveCoappLeadDetails(id,${leadDetails[0].leadId});"
								title="Alt+V" accesskey="V">
								<bean:message key="button.save" />
							</button>
						</td>
					</tr>
					</logic:notPresent>
</fieldset>
</logic:notPresent>
<logic:present name="cibilDone">
<div id='corporatefield' style="display: none;">
<fieldset>
			<legend> 
				<bean:message key="lbl.corporate.details" />
			</legend> 
			<table width="100%">
			<tr>
						<td width="13%" >
							<bean:message key="lbl.customername" />
							<span><font style="color: red;"> *</font> </span>
						</td>
						<td width="13%" >
							<html:text property="customerName" styleClass="text"
								styleId="customerName" maxlength="250" disabled="true"
								value="${leadDetails[0].customerName}"
								onblur="caseChange('leadCapturingDetails','customerName');" />
						</td>
				
				
					
				
					
			
					<td width="13%">
						<bean:message key="lbl.groupName" />
						<font color="red">*</font>
					</td>
					<td width="13%">
					 
					<html:select property="groupType" styleId="groupType" onchange="groupselect();"  disabled="true" styleClass="text" value="${leadDetails[0].groupType}"> 
						<html:option value="">--Select--</html:option> 
						<html:option value="N">New</html:option> 
						<html:option value="E">Existing</html:option> 
					</html:select>
					<logic:notPresent name="leadDetails">
					<div id="groupLov" style="display:none">
						<input type="text" id="groupName" name="groupName" class="text"  disabled="true" value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
				
					<div id="groupText" style="display:none">
						<input type="text" id="groupName1" name="groupName1" class="text" value="${leadDetails[0].groupDesc}"  disabled="true" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:notPresent>
					
					<logic:present name="leadDetails">
					<logic:iterate name="leadDetails" id="list">
					<logic:equal property="groupType" name="list" value="N">
					<div id="groupLov" style="display:none">
						<input type="text" id="groupName" name="groupName" class="text" value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
					<div id="groupText" >
						<input type="text" id="groupName1" name="groupName1" class="text" value="${leadDetails[0].groupDesc}"  disabled="true" maxlength="50"  tabindex="-1" />
					
					</div>
						
					</logic:equal>
					<logic:equal property="groupType" name="list" value="E">
										
					<div id="groupLov" >
						<input type="text" id="groupName" name="groupName" class="text" value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
				
					<div id="groupText" style="display:none">
						<input type="text" id="groupName1" name="groupName1" class="text" value="${leadDetails[0].groupDesc}"  disabled="true" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					<logic:equal property="groupType" name="list" value="">
										
					<div id="groupLov" style="display: none;">
						<input type="text" id="groupName" name="groupName" class="text" value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
				
					<div id="groupText" style="display:none">
						<input type="text" id="groupName1" name="groupName1" class="text" value="${leadDetails[0].groupDesc}"  disabled="true" maxlength="50"  tabindex="-1" />
					
					</div>
					
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
							maxlength="25" value="${leadDetails[0].registrationNo}"  disabled="true"
							styleClass="text" />
					</td>

				

		
					<td>
						<bean:message key="businessSegment" />
						<font color="red">*</font>
					</td>
					<td>
						<html:select property="businessSegment" styleId="businessSegment" disabled="true"
							value="${leadDetails[0].businessSegment}" styleClass="text">
							<html:option value="">--Select--</html:option>
							<logic:present name="businessSegmentList">
								<logic:notEmpty name="businessSegmentList">
									<html:optionsCollection name="businessSegmentList"
										label="businessSegmentDesc" value="businessSegmentCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
			
			</tr>
				<tr>

					<td>
						<bean:message key="lbl.industry" />
						
					</td>
					<td>
						<html:text property="industry" readonly="true" styleId="industry" disabled="true"
							styleClass="text" value="${leadDetails[0].industry}"
							tabindex="-1" />
						<html:hidden property="lbxIndustry" styleId="lbxIndustry"
							value="${leadDetails[0].lbxIndustry}" />
						<html:button property="industryButton"  disabled="true" styleId="industryButton"
							onclick="closeLovCallonLov1();openLOVCommon(253,'leadCapturingDetails','lbxIndustry','','', '','','lovCoAppIndustryLead','industry')"
							value=" " styleClass="lovbutton" >
						</html:button>
						<input type="hidden" id="hIndustry" name="hIndustry" />
					</td>
				
					<td>
						<bean:message key="lbl.subIndustry" />
						
					</td>
					<td>

						<html:text property="subIndustry" readonly="true"
							styleId="subIndustry" styleClass="text"
							value="${leadDetails[0].subIndustry}"  disabled="true" tabindex="-1" />
						<html:hidden property="lbxSubIndustry" styleId="lbxSubIndustry"
							value="${leadDetails[0].lbxSubIndustry}" />
						<html:button property="subIndustryButton"
							styleId="subIndustryButton"  disabled="true"
							onclick="closeLovCallonLov('industry');openLOVCommon(254,'leadCapturingDetails','hIndustry','industry','lbxSubIndustry', 'lbxIndustry','Please Select Industry','','subIndustry')"
							value=" " styleClass="lovbutton" >
						</html:button>
					</td>
					</tr>
					<tr>
					
					<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="custPan" maxlength="10" styleClass="text"
							styleId="custPan" value="${leadDetails[0].custPan}"  disabled="true"
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('custPan');" />
					</td>
					</tr>
	

</table>

</fieldset>
</div>


		<!-- Rohit Changes for coApplicant ang gaurantor -->
		<fieldset>
			<legend>
				<bean:message key="lbl.Co_app_customer_detail" />
			</legend>
			<table width="100%">
<%-- <tr>
<td><bean:message key="lbl.CoAppStatus" /></td>
      <td>
      <logic:equal value="A" name="coAppStatus">

         <input type="checkbox" name="coAppStatus" id="coAppStatus" checked="checked"  onclick="coAppEnable();"/>
       </logic:equal>
      <logic:notEqual value="A" name="coAppStatus">
     
         <input type="checkbox" name="coAppStatus" id="coAppStatus" onclick="coAppEnable();" />
      </logic:notEqual></td>
</tr> --%>
								<tr>

					<td width="13%">
						<bean:message key="lbl.relationship" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<logic:present name="leadOther">
						<td width="13%" id="coApp">
							<html:select property="coApprelationship"  disabled="true" styleId="coApprelationship"
								styleClass="text" onchange="coAppsan();coAppsan2();coAppcleanUp();customerLov();"
								value="${leadDetails[0].coApprelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="coAppaddressId" styleClass="text" styleId="coAppaddressId"  value="" />
		<html:hidden property="coApplbxCustomerId" styleClass="text" styleId="coApplbxCustomerId"  value="${leadDetails[0].coApplbxCustomerId}" />
	
		<html:button property="coAppcustomerIdButton" styleId="coAppcustomerIdButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(192043,'customerForm','coApplbxCustomerId','','','','','copyCoAppCustomerDetails','coAppcustomerName','coAppaddress1','coAppcustomerType');custtype();coAppsan1();" value=" " styleClass="lovbutton"></html:button>
		
					
						</td>
						<td width="13%" id="coApp1" style="display: none">
							<html:select property="coApprelationship" styleId="coApprelationship"
								styleClass="text" onchange="coAppsan();coAppsan2();coAppcleanUp();customerLov();"  disabled="true"
								value="${leadDetails[0].coApprelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="coAppaddressId" styleClass="text" styleId="coAppaddressId"  value="" />
		<html:hidden property="coApplbxCustomerId" styleClass="text" styleId="coApplbxCustomerId"  value="${leadDetails[0].coApplbxCustomerId}" />
	
		<html:button property="coAppcustomerIdButton" styleId="coAppcustomerIdButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(192043,'customerForm','coApplbxCustomerId','','','','','copyCoAppCustomerDetails','coAppcustomerName','coAppaddress1','coAppcustomerType');custtype();" value=" " styleClass="lovbutton"></html:button>
		
							
						</td>
					</logic:present>
					<logic:present name="leadNew">
						<td width="13%">
							<html:select property="coApprelationship" styleId="coApprelationship"  disabled="true"
								styleClass="text" onchange="coAppsan();coAppsan2();coAppcleanUp();customerLov();"
								value="${leadDetails[0].coApprelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="coAppaddressId" styleClass="text" styleId="coAppaddressId"  value="" />
		<html:hidden property="coApplbxCustomerId" styleClass="text" styleId="coApplbxCustomerId"  value="${leadDetails[0].coApplbxCustomerId}" />
	
		<html:button property="coAppcustomerIdButton" styleId="coAppcustomerIdButton" onclick="closeLovCallonLov1();openLOVCommon(192043,'customerForm','coApplbxCustomerId','','','','','copyCoAppCustomerDetails','coAppcustomerName','coAppaddress1','coAppcustomerType');custtype();" value=" " styleClass="lovbutton"></html:button>
		
							
						</td>
					</logic:present>

					<td width="13%">
						<bean:message key="lbl.Type" />
						<span><font style="color: red;"> *</font> </span>
					</td>
					<td width="13%">
						<html:select property="coAppcustomerType1" styleClass="text"
							styleId="coAppcustomerType1" value="${leadDetails[0].coAppcustomerType}"
							onchange="coAppupdateCust(this.value);coAppcusttype();"  disabled="true" >
							<html:option value="">--Select--</html:option>
							<html:option value="I">INDIVIDUAL</html:option>
							<html:option value="C">CORPORATE</html:option>
						</html:select>
						<html:hidden property="coAppcustomerType" styleId="coAppcustomerType" value="${leadDetails[0].coAppcustomerType}" />
					</td></tr>
					<tr>
					<td width="13%">

						<bean:message key="constitution" />
						<font color="red">*</font>
					</td>
					<td><div id="coAppIndividualconstitution" style="display:none">
						<html:select property="coAppindconstitution" styleId="coAppindconstitution"
							value="${leadDetails[0].coAppcontitutionCode}" styleClass="text"  disabled="true"
							onchange="indConSubprofile('coAppindconstitution');appendZero();">
							<html:option value="">--Select--</html:option>
							<logic:present name="indconstitutionlist">
								<logic:notEmpty name="indconstitutionlist">
									<html:optionsCollection name="indconstitutionlist"
										label="constitution" value="contitutionCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						</div>
						<div id="coAppcorporateconstitution" style="display:none">
						<html:select property="coAppcorconstitution" styleId="coAppcorconstitution"
							value="${leadDetails[0].coAppcontitutionCode}" styleClass="text"  disabled="true"
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
						<div id="coAppconstitution">
						<html:select property="coAppconstitution" styleId="coAppconstitution"
							value="${leadDetails[0].coAppcontitutionCode}" styleClass="text"  disabled="true"
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



<div id='coAppcorporatefield' style="display: none;">
<fieldset>
			<legend> 
				<bean:message key="lbl.c_corporate.details" />
			</legend> 
			<table width="100%">
			<tr>
						<td width="13%" >
							<bean:message key="lbl.customername" />
							<span><font style="color: red;"> *</font> </span>
						</td>
						<td width="13%" >
							<html:text property="coAppcustomerName" styleClass="text"
								styleId="coAppcustomerName" maxlength="250"  disabled="true"
								value="${leadDetails[0].coAppcustomerName}"
								onblur="caseChange('leadCapturingDetails','coAppcustomerName');" />
						</td>
				
				
					
				
					
			
					<td width="13%">
						<bean:message key="lbl.groupName" />
						<font color="red">*</font>
					</td>
					<td width="13%">
					 
					<html:select property="coAppgroupType" styleId="coAppgroupType"  disabled="true" onchange="coAppgroupselect();" styleClass="text" value="${leadDetails[0].coAppgroupType}"> 
						<html:option value="">--Select--</html:option> 
						<html:option value="N">New</html:option> 
						<html:option value="E">Existing</html:option> 
					</html:select>
					<logic:notPresent name="leadDetails">
					<div id="coAppgroupLov" style="display:none">
						<input type="text" id="coAppgroupName" name="coAppgroupName" class="text" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
				
					<div id="coAppgroupText" style="display:none">
						<input type="text" id="coAppgroupName1" name="coAppgroupName1" class="text"  disabled="true" value="${leadDetails[0].coAppgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:notPresent>
					
					<logic:present name="leadDetails">
					<logic:iterate name="leadDetails" id="list">
					<logic:equal property="coAppgroupType" name="list" value="N">
					<div id="coAppgroupLov" style="display:none">
						<input type="text" id="coAppgroupName" name="coAppgroupName" class="text" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
					<div id="coAppgroupText" >
						<input type="text" id="coAppgroupName1" name="coAppgroupName1" class="text"  disabled="true" value="${leadDetails[0].coAppgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
						
					</logic:equal>
					<logic:equal property="coAppgroupType" name="list" value="E">
										
					<div id="coAppgroupLov" >
						<input type="text" id="coAppgroupName" name="coAppgroupName" class="text" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
				
					<div id="coAppgroupText" style="display:none">
						<input type="text" id="coAppgroupName1" name="coAppgroupName1" class="text" value="${leadDetails[0].coAppgroupDesc}"  disabled="true" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					<logic:equal property="coAppgroupType" name="list" value="">
										
					<div id="coAppgroupLov" style="display: none;">
						<input type="text" id="coAppgroupName" name="coAppgroupName" class="text" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
				
					<div id="coAppgroupText" style="display:none">
						<input type="text" id="coAppgroupName1" name="coAppgroupName1" class="text" value="${leadDetails[0].coAppgroupDesc}"  disabled="true" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					</logic:iterate>
					</logic:present>
					</td>

					</tr><tr>

					<td>
						<bean:message key="registrationNo" />

					</td>
					<td>
						<html:text property="coAppregistrationNo" styleId="coAppregistrationNo"
							maxlength="25" value="${leadDetails[0].coAppregistrationNo}"  disabled="true"
							styleClass="text" />
					</td>

				

		
					<td>
						<bean:message key="businessSegment" />
						<font color="red">*</font>
					</td>
					<td>
						<html:select property="coAppbusinessSegment" styleId="coAppbusinessSegment1"  disabled="true"
							value="${leadDetails[0].coAppbusinessSegment}" styleClass="text">
							<html:option value="">--Select--</html:option>
							<logic:present name="businessSegmentList">
								<logic:notEmpty name="businessSegmentList">
									<html:optionsCollection name="businessSegmentList"
										label="businessSegmentDesc" value="businessSegmentCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
							<html:hidden property="coAppbusinessSegment" styleClass="text" styleId="coAppbusinessSegment" value="${leadDetails[0].coAppbusinessSegment}" />
					</td>
			
			</tr>
				<tr>

					<td>
						<bean:message key="lbl.industry" />
						
					</td>
					<td>
						<html:text property="coAppindustry" readonly="true" styleId="coAppindustry"  disabled="true"
							styleClass="text" value="${leadDetails[0].coAppindustry}"
							tabindex="-1" />
						<html:hidden property="coApplbxIndustry" styleId="coApplbxIndustry"
							value="${leadDetails[0].coApplbxIndustry}" />
						<html:button property="industryButton" styleId="industryButton"  disabled="true"
							onclick="openLOVCommon(19252,'leadCapturingDetails','coApplbxIndustry','','', '','','lovCoAppIndustryLead','coAppindustry');closeLovCallonLov('');"
							value=" " styleClass="lovbutton" >
						</html:button>
						<input type="hidden" id="coApphIndustry" name="coApphIndustry" />
					</td>
				
					<td>
						<bean:message key="lbl.subIndustry" />
						
					</td>
					<td>

						<html:text property="coAppsubIndustry"  disabled="true"
							styleId="coAppsubIndustry" styleClass="text"
							value="${leadDetails[0].coAppsubIndustry}" tabindex="-1" />
						<html:hidden property="coApplbxSubIndustry" styleId="coApplbxSubIndustry"
							value="${leadDetails[0].coApplbxSubIndustry}" />
						<html:button property="subIndustryButton"
							styleId="subIndustryButton" disabled="true"
							onclick="closeLovCallonLov('coAppindustry');openLOVCommon(19254,'leadCapturingDetails','coApphIndustry','coAppindustry','coApplbxSubIndustry', 'coApplbxIndustry','Please Select Industry','','coAppsubIndustry')"
							value=" " styleClass="lovbutton" >
						</html:button>
					</td>
					</tr>
					<tr>
					
					<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="coAppcustPan" maxlength="10" styleClass="text"
							styleId="coAppcustPan" value="${leadDetails[0].coAppcustPan}"  disabled="true"
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('coAppcustPan');" />
					</td>
					</tr>
	

</table>

</fieldset>
</div>


<div id='coAppindividualfield'">
<fieldset>
			<legend>
				<bean:message key="lbl.c_individual.details" />
			</legend>
			<table width="100%">
			<tr>
	<td width="13%">
							<bean:message key="lbl.fname" />
							<font color="red">*</font>
						</td>
						<td width="13%">
							<html:text property="coAppfirstName" styleId="coAppfirstName"  disabled="true"
								styleClass="text" value="${leadDetails[0].coAppfirstName}"
								onchange="return upperMe('coAppfirstName');" maxlength="50" />
						</td>
				<!-- pooja change for middle name -->
					<td width="13%">
						<bean:message key="individual.middle" />
						<!-- <font color="red">*</font> -->
					</td>
					<td width="13%">
						<html:text property="coAppmiddleName" styleId="coAppmiddleName" disabled="true"
							styleClass="text" value="${leadDetails[0].coAppmiddleName}"
							onchange="return upperMe('coAppmiddleName');" maxlength="50" />
					</td>
					</tr>
					<tr>
					<td width="13%">
						<bean:message key="individual.last" />
						<font color="red">*</font>
					</td>
					<td width="13%">
						<html:text property="coApplastName" styleId="coApplastName" disabled="true"
							styleClass="text" value="${leadDetails[0].coApplastName}"
							onchange="return upperMe('coApplastName');" maxlength="50" />
					</td>
					</tr>
					<!-- pooja change for middle name -->
				<tr>
					<td>
						<bean:message key="individual.birthdate" />
						<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
					</td>
					
				
					<td>
						<html:text property="coAppcustDOB"  disabled="true" onchange="checkDate('coAppcustDOB');coAppcheckcustDOB('coAppcustDOB');"
							styleId="coAppcustDOB" styleClass="text"
							value="${leadDetails[0].coAppcustDOB}" />
					</td>
					<td>
						<bean:message key="fatherHusband" /><font color="red">*</font>
					</td>
					<td>
						<html:text property="coAppfatherName"
							styleId="coAppfatherName" styleClass="text"  disabled="true" onblur="caseChange('leadCapturingDetails','coAppfatherName');"
							value="${leadDetails[0].coAppfatherName}"  maxlength="50"/>
					</td>
					<input type="hidden" name="sysDate" id="sysDate" value="${requestScope.sysDate}" />
					<input type="hidden" name="approve" id="approve" value="N" />
				
					
		
			</tr>
			<!-- Sanjog Changes Start Here -->
			<tr>
					
					<td><bean:message key="lbl.gender" /><font color="red">*</font></td>
          <td><html:select property="coAppgenderIndividual"  disabled="true" value="${leadDetails[0].coAppgenderIndividual}" styleClass="text" styleId="coAppgenderIndividual">
				<logic:present name="GenderCategory">
					<logic:notEmpty name="GenderCategory">
					<html:optionsCollection name="GenderCategory" label="genderDesc" value="genderCode" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
		  </td>
					<td>
						<bean:message key="lbl.drivingLic" />
					</td>
					<td>
						<html:text property="coAppdlNumber"
							styleId="coAppdlNumber" styleClass="text" disabled="true"
							value="${leadDetails[0].coAppdlNumber}" maxlength="20"/>
					</td>
								
		
			</tr>
			<tr>
					<td>
						<bean:message key="voterId" />
					</td>				
				
					<td>
						<html:text property="coAppvoterId"
							styleId="coAppvoterId" styleClass="text"  disabled="true"
							value="${leadDetails[0].coAppvoterId}" maxlength="20"/>
					</td>
					
					<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="coAppcustPanInd" maxlength="10" styleClass="text"  disabled="true"
							styleId="coAppcustPanInd" value="${leadDetails[0].coAppcustPanInd}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('coAppcustPanInd');" />
					</td>
					</tr>
					<tr>
					<td>
						<bean:message key="passport" />
					</td>				
				
					<td>
						<html:text property="coApppassport"
							styleId="coApppassport" styleClass="text" disabled="true"
							value="${leadDetails[0].coApppassport}" maxlength="20"/>
					</td>
				<td><bean:message key="aadhaar" /></td>
          <td><html:text property="coAppaadhaar" styleClass="text" styleId="coAppaadhaar"  disabled="true"   onkeypress="return numericOnly(event,12);" value="${leadDetails[0].coAppaadhaar}" maxlength="12"/></td>
					
					</tr>
					
		</table>		
	</fieldset>
</div>

		<fieldset>
			<legend>
				<bean:message key="lbl.c_commDetails" />
			</legend>
			<table width="100%">

<tr>
<td><bean:message key="lbl.CopyAdd" /></td>
 <td><input type="checkbox" name="copyAppCoapp" id="copyAppCoapp"  disabled="true" onclick="copyApplicantToCoAppAdd();" /></td>
</tr>
				<tr>	
					<td>
						<bean:message key="address.type" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:select property="coAppaddresstype" styleClass="text" disabled="true"
							styleId="coAppaddresstype1" value="${leadDetails[0].coAppaddresstype}">
							<html:option value="">--Select--</html:option>
							<html:optionsCollection name="addresstypeList"
								value="addresstypeid" label="addresstypename" />
						</html:select>
						<html:hidden property="coAppaddresstype" styleClass="text" styleId="coAppaddresstype" value="${leadDetails[0].coAppaddresstype}" />
					</td>
				
					<td width="13%">
						<bean:message key="lbl.addressLine1" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="coAppaddress1" styleClass="text" disabled="true"
							styleId="coAppaddress1" maxlength="50"
							value="${leadDetails[0].coAppaddress1}"
							onblur="caseChange('leadCapturingDetails','coAppaddress1');" />
					</td>

</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.two" />
					</td>
					<td width="13%">
						<html:text property="coAppaddress2" styleClass="text" disabled="true"
							styleId="coAppaddress2" maxlength="50"
							value="${leadDetails[0].coAppaddress2}"
							onblur="caseChange('leadCapturingDetails','coAppaddress2');" />
					</td>
					<td width="13%">
						<bean:message key="lbl.three" />
					</td>
					<td width="13%">
						<html:text property="coAppaddress3" styleClass="text" disabled="true"
							styleId="coAppaddress3" maxlength="50"
							value="${leadDetails[0].coAppaddress3}"
							onblur="caseChange('leadCapturingDetails','coAppaddress3');" />
					</td>
</tr>
	<!-- pooja code start for Co-Applicant Pincode -->
	<logic:present name="pincodeFlag">
<logic:equal value="N" name="pincodeFlag">
 <html:hidden property="pincodeFlag" styleId="pincodeFlag" styleClass="text" value="N"/>
<tr> 

		
		<td  width="13%">
						<bean:message key="lbl.country" /><span><font style="color: red;"> *</font>
						</span>
					</td>
					<td  width="13%">
						<logic:present name="leadDetails">
							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								tabindex="-1" readonly="true" value="${defaultcountry[0].defaultcountryname}" />
							<input type="button"  disabled="true" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(192068,'leadCapturingDetails','coApptxtCountryCode','','','','','coAppclearCountryLovChild','coAppcountry')"
								value="" name="countryButton" />
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:present>
						<logic:notPresent name="leadDetails">

							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								tabindex="-1"  disabled="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"  disabled="true"
								onclick="openLOVCommon(192068,'leadCapturingDetails','coApptxtCountryCode','','','','','coAppclearCountryLovChild','coAppcountry')"
								value="" name="countryButton"/>
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
	
<td width="13%">
						<bean:message key="lbl.state" />
						<span><font style="color: red;"> *</font>
						</span> 
					</td>
					<td width="13%">
						<html:text property="coAppstate" styleId="coAppstate" styleClass="text" disabled="true"
							tabindex="-1" readonly="true" value="${leadDetails[0].coAppstate}" />
						<input type="button" class="lovbutton" id="stateButton"  disabled="true"
							onclick="openLOVCommon(192070,'leadCapturingDetails','coAppstate','coAppcountry','coApptxtStateCode', 'coApptxtCountryCode','Please select country first!!!','coAppclearStateLovChild','coApptxtStateCode')"
							value="" name="stateButton"/>
							<input type="hidden" name="coApptxtStateCode" id="coApptxtStateCode"
								value="${leadDetails[0].coApptxtStateCode}" />
					</td>	
</tr>

<tr>

<td width="13%">
						<bean:message key="lbl.dist" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="coAppdist" styleId="coAppdist" styleClass="text" disabled="true" 
							tabindex="-1" readonly="true" value="${leadDetails[0].coAppdist}" />
						<input type="button" class="lovbutton" id="distButton"  disabled="true"
							onclick="openLOVCommon(192072,'leadCapturingDetails','coAppdist','coAppstate','coApptxtDistCode', 'coApptxtStateCode','Please select state first!!!','coAppclearDistrictLovChild','coApptxtDistCode')"
							value=" " name="distButton"/>
							<input type="hidden" name="coApptxtDistCode" id="coApptxtDistCode"
								value="${leadDetails[0].coApptxtDistCode}" />
					</td>
					
					<td width="13%">
						<bean:message key="address.Tahsil" />
					</td>
					<td>
						<input type="text" name="coApptahsilDesc" id="coApptahsilDesc" readonly="readonly" value="${leadDetails[0].coApptahsilDesc}" />
						<html:hidden property="coApptahsil" styleId="coApptahsil" styleClass="text" value="${leadDetails[0].coApptahsil}"/>
						<html:button property="tahsilButton" styleId="tahsilButton"  disabled="true" onclick="openLOVCommon(495,'leadCapturingDetails','coApptahsilDesc','coAppdist','coApptahsilDesc', 'coApptxtDistCode','Please select District first','','coApptahsil');" value=" " styleClass="lovbutton"> </html:button>
					</td>
				
					

</tr>


				<tr>
				<td width="13%">
						<bean:message key="lbl.pincode" /><span><font style="color: red;"> *</font>
						</span>
		</td>
					<td width="13%">
						<html:text property="coApppincode" styleClass="text" styleId="coApppincode" disabled="true"
							maxlength="6" value="${leadDetails[0].coApppincode}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,6);" />
					</td>
				
				
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="coAppphoneOff" styleClass="text"
							styleId="coAppphoneOff" maxlength="10"  disabled="true"
							value="${leadDetails[0].coAppphoneOff}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,10);" />
					</td>
	</tr>
	</logic:equal>
	</logic:present>
	
	<logic:present name="pincodeFlag">
<logic:equal value="Y" name="pincodeFlag">
	<html:hidden property="pincodeFlag" styleId="pincodeFlag" styleClass="text" value="Y"/>
	<tr>
 
		<td><bean:message key="address.pincode" /><span><font style="color: red;"> *</font>
						</span></td>
			<td><html:text property="coApppincode" styleId="coApppincode" size="20" styleClass="text"  disabled="true" value="${leadDetails[0].coApppincode}" tabindex="-1"></html:text>
            <html:hidden property="coApplbxPincode" styleId="coApplbxPincode" styleClass="text" value=""/>
 			<html:button property="pincodeButton" styleId="pincodeButton"  disabled="true" onclick="openLOVCommon(192025,'leadCapturingDetails','coApppincode','','', '','','getCoAppCountryStateDistrictTahsilValue','coApptxnTahsilHID','hcommon');closeLovCallonLov('');" value=" " styleClass="lovbutton"> </html:button>
		</td>
					
<td><bean:message key="address.Tahsil" /></td>	
			<td id="coApplov2"> 
            	<div id="coApptahsildetail">
            		<html:text property="coApptahsil" styleId="coApptahsil" size="20" styleClass="text"  disabled="true" value="${leadDetails[0].coApptahsilDesc}" ></html:text>
                	<html:hidden property="coApptxnTahsilHID" styleId="coApptxnTahsilHID" styleClass="text" value="${leadDetails[0].coApptahsil}"/>
   				</div>
   			</td>
   			
   			<td id="coApplov4" style="display: none"> 
            	<div id="coApptahsildetail">
            		<html:text property="coApptahsil4" styleId="coApptahsil4" size="20" styleClass="text"   disabled="true" value="${leadDetails[0].coApptahsil}" ></html:text>
                	<html:hidden property="coApptxnTahsilHID" styleId="coApptxnTahsilHID" styleClass="text" value="${leadDetails[0].coApptxnTahsilHID}"/>
   				</div>
   			</td>
			
				
   			
   			
	</tr>
	
	
	<tr id="coApplov5">
	<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font> -->
						</span></td>
          	<td >
          		<div id="coAppcityID">
          			<html:text property="coAppdist" styleId="coAppdist" size="20" styleClass="text"  disabled="true" value="${leadDetails[0].coAppdist}" ></html:text>
          			<html:hidden property="coApptxtDistCode" styleId="coApptxtDistCode" styleClass="text" value="${leadDetails[0].coApptxtDistCode}"></html:hidden>
   				</div>
   			</td>
	 
	<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="coAppstatedetail">
                	<html:text property="coAppstate" styleId="coAppstate" styleClass="text" size="20" value="${leadDetails[0].coAppstate}"  disabled="true" ></html:text>
   					<html:hidden property="coApptxtStateCode" styleId="coApptxtStateCode" styleClass="text" value=" ${leadDetails[0].coApptxtStateCode}"></html:hidden>
    				<html:hidden property="coApptahsilDesc" styleId="coApptahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton" style="display: none"  disabled="true" onclick="closeLovCallonLov('coAppcountry');openLOVCommon(192070,'leadCapturingDetails','coAppstate','coAppcountry','coApptxtStateCode', 'coApptxtCountryCode','Please select country first','coAppclearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	          	</div>
			</td>
	
		   			
   					

	</tr>
	
	<tr id="coApplov6" style="display: none">
	<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
          	<td>
          		<div id="coAppcityID">
          			<html:text property="coAppdist4" styleId="coAppdist4" size="20" styleClass="text" readonly="true" value="${leadDetails[0].coAppdist}" ></html:text>
          			<html:hidden property="coApptxtDistCode" styleId="coApptxtDistCode" styleClass="text" value="${leadDetails[0].coApptxtDistCode}"></html:hidden>
  					<html:button property="distButton" styleId="districtButton" onclick="closeLovCallonLov('coAppstate6');openLOVCommon(20,'leadCapturingDetails','coAppdist4','coAppstate6','coApptxtDistCode', 'coApptxtStateCode','Please select state first','coAppclearTahsilLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   				</div>
   			</td>
	
	<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="coAppstatedetail">
                	<html:text property="coAppstate6" styleId="coAppstate6" styleClass="text" size="20" value="${leadDetails[0].coAppstate}"  disabled="true" ></html:text>
   					<html:hidden property="coApptxtStateCode" styleId="coApptxtStateCode" styleClass="text" value=" ${leadDetails[0].coApptxtStateCode}"></html:hidden>
    				<html:hidden property="coApptahsilDesc" styleId="coApptahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton"  disabled="true" onclick="closeLovCallonLov('coAppcountry');openLOVCommon(192070,'leadCapturingDetails','coAppstate6','coApptxtCountryCode','coApptxtStateCode', 'coApptxtCountryCode','Please select country first','coAppclearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	          	</div>
			</td>
	
	
	
	
	</tr>
	
	<tr>
		
   	
						
		<td width="13%">
						<bean:message key="lbl.country" /><span><font style="color: red;"> *</font>
						</span>
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td id="coApplov1" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								  disabled="true" value="${defaultcountry[0].defaultcountryname}" />
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								 disabled="true"	value="${defaultcountry[0].defaultcountryname}" />
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
					
					
					<td id="coApplov3" style="display: none" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								  disabled="true" value="${leadDetails[0].coAppcountry}" />
							<input type="button"  disabled="true" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(192068,'leadCapturingDetails','coAppcountry','','','','','coAppclearCountryLovChild','coApptxtCountryCode')"
								value="" name="countryButton" />
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
									value="${leadDetails[0].coApptxtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="coAppcountry" styleId="coAppcountry" styleClass="text"
								 readonly="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"  disabled="true"
								onclick="openLOVCommon(192068,'leadCapturingDetails','coAppcountry','','','','','coAppclearCountryLovChild','coApptxtCountryCode')"
								value="" name="countryButton"/>
								<input type="hidden" name="coApptxtCountryCode" id="coApptxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
				
					
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="coAppphoneOff" styleClass="text"  disabled="true"
							styleId="coAppphoneOff" maxlength="10"
							value="${leadDetails[0].coAppphoneOff}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,10);" />
					</td>
	</tr>
	</logic:equal>
	</logic:present>
	<!-- pooja code end for Co-Applicant Pincode -->
				
				<tr>
					<td width="13%">
						<bean:message key="lbl.email" />
					</td>
					<td width="13%">
						<html:text property="coAppemail" styleClass="text" styleId="coAppemail"  disabled="true"
							maxlength="100" value="${leadDetails[0].coAppemail}"/>
					</td>
					<td width="13%">
						<bean:message key="lbl.landmark" />
					</td>
					<td width="13%">
						<html:text property="coApplandmark" styleClass="text"  disabled="true"
							styleId="coApplandmark" maxlength="25"
							value="${leadDetails[0].coApplandmark}"
							onblur="caseChange('leadCapturingDetails','coApplandmark');" />
					</td>
	</tr>
			
				

			</table>
		</fieldset>
		
<div id='gaurcorporatefield' style="display: none;">
<fieldset>
			<legend> 
				<bean:message key="lbl.g_corporate.details" />
			</legend> 
			<table width="100%">
			<tr>
						<td width="13%" >
							<bean:message key="lbl.customername" />
							<span><font style="color: red;"> *</font> </span>
						</td>
						<td width="13%" >
							<html:text property="gaurcustomerName" styleClass="text"  disabled="true"
								styleId="gaurcustomerName" maxlength="250"
								value="${leadDetails[0].gaurcustomerName}"
								onblur="caseChange('leadCapturingDetails','gaurcustomerName');" />
						</td>
				
				
					
				
					
			
					<td width="13%">
						<bean:message key="lbl.groupName" />
						<font color="red">*</font>
					</td>
					<td width="13%">
					 
					<html:select property="gaurgroupType" styleId="gaurgroupType" onchange="gaurgroupselect();"  disabled="true" styleClass="text" value="${leadDetails[0].gaurgroupType}"> 
						<html:option value="">--Select--</html:option> 
						<html:option value="N">New</html:option> 
						<html:option value="E">Existing</html:option> 
					</html:select>
					<logic:notPresent name="leadDetails">
					<div id="gaurgroupLov" style="display:none">
						<input type="text" id="gaurgroupName" name="gaurgroupName" class="text" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
				
					<div id="gaurgroupText" style="display:none">
						<input type="text" id="gaurgroupName1" name="gaurgroupName1" class="text" value="${leadDetails[0].gaurgroupDesc}"  disabled="true" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:notPresent>
					
					<logic:present name="leadDetails">
					<logic:iterate name="leadDetails" id="list">
					<logic:equal property="gaurgroupType" name="list" value="N">
					<div id="ggaurroupLov" style="display:none">
						<input type="text" id="gaurgroupName" name="gaurgroupName" class="text" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
					<div id="gaurgroupText" >
						<input type="text" id="gaurgroupName1" name="gaurgroupName1" class="text" value="${leadDetails[0].gaurgroupDesc}"  disabled="true" maxlength="50"  tabindex="-1" />
					
					</div>
						
					</logic:equal>
					<logic:equal property="gaurgroupType" name="list" value="E">
										
					<div id="gaurgroupLov" >
						<input type="text" id="gaurgroupName" name="gaurgroupName" class="text" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" styleId="groupButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
				
					<div id="gaurgroupText" style="display:none">
						<input type="text" id="gaurgroupName1" name="gaurgroupName1" class="text" value="${leadDetails[0].gaurgroupDesc}"  disabled="true" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					<logic:equal property="gaurgroupType" name="list" value="">
										
					<div id="gaurgroupLov" style="display: none;">
						<input type="text" id="gaurgroupName" name="gaurgroupName" class="text" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton"  disabled="true" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
				
					<div id="gaurgroupText" style="display:none">
						<input type="text" id="gaurgroupName1" name="gaurgroupName1" class="text" value="${leadDetails[0].gaurgroupDesc}"  disabled="true" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					</logic:iterate>
					</logic:present>
					</td>

					</tr><tr>

					<td>
						<bean:message key="registrationNo" />

					</td>
					<td>
						<html:text property="gaurregistrationNo" styleId="gaurregistrationNo" disabled="true"
							maxlength="25" value="${leadDetails[0].gaurregistrationNo}"
							styleClass="text" />
					</td>

				

		
					<td>
						<bean:message key="businessSegment" />
						<font color="red">*</font>
					</td>
					<td>
						<html:select property="gaurbusinessSegment" styleId="gaurbusinessSegment" disabled="true"
							value="${leadDetails[0].gaurbusinessSegment}" styleClass="text">
							<html:option value="">--Select--</html:option>
							<logic:present name="businessSegmentList">
								<logic:notEmpty name="businessSegmentList">
									<html:optionsCollection name="businessSegmentList"
										label="businessSegmentDesc" value="businessSegmentCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
					</td>
			
			</tr>
				<tr>

					<td>
						<bean:message key="lbl.industry" />
						
					</td>
					<td>
						<html:text property="gaurindustry" readonly="true" styleId="gaurindustry" disabled="true"
							styleClass="text" value="${leadDetails[0].gaurindustry}"
							tabindex="-1" />
						<html:hidden property="gaurlbxIndustry" styleId="gaurlbxIndustry"
							value="${leadDetails[0].gaurlbxIndustry}" />
						<html:button property="industryButton" styleId="industryButton" disabled="true"
							onclick="closeLovCallonLov1();openLOVCommon(19253,'leadCapturingDetails','gaurlbxIndustry','','', '','','lovCoAppIndustryLead','gaurindustry')"
							value=" " styleClass="lovbutton" >
						</html:button>
						<input type="hidden" id="gaurhIndustry" name="gaurhIndustry" />
					</td>
				
					<td>
						<bean:message key="lbl.subIndustry" />
						
					</td>
					<td>

						<html:text property="gaursubIndustry"  disabled="true"
							styleId="gaursubIndustry" styleClass="text"
							value="${leadDetails[0].gaursubIndustry}" tabindex="-1" />
						<html:hidden property="gaurlbxSubIndustry" styleId="gaurlbxSubIndustry"
							value="${leadDetails[0].gaurlbxSubIndustry}" />
						<html:button property="subIndustryButton"  disabled="true"
							styleId="subIndustryButton"
							onclick="closeLovCallonLov('industry');openLOVCommon(192038,'leadCapturingDetails','gaurhIndustry','gaurindustry','gaurlbxSubIndustry', 'gaurlbxIndustry','Please Select Industry','','gaursubIndustry')"
							value=" " styleClass="lovbutton" >
						</html:button>
					</td>
					</tr>
					<tr>
					
					<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="gaurcustPan" maxlength="10" styleClass="text"
							styleId="gaurcustPan" value="${leadDetails[0].gaurcustPan}"  disabled="true"
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('gaurcustPan');" />
					</td>
					
					
					</tr>

				</table>

</fieldset>
</div>
</logic:present>
 <%-- <fieldset>
					<tr>
					<html:hidden property="updateCustId" styleClass="text" styleId="updateCustId"  value="${leadDetails[0].updateCustId}" />
						<td>
							<button type="button" name="Save" id="Save"
								class="topformbutton2" onclick="return saveCoappLeadDetails(id,${leadDetails[0].leadId});"
								title="Alt+V" accesskey="V">
								<bean:message key="button.save" />
							</button>
						</td>
					</tr>
</fieldset> --%> 

<!-- grid details -->
<fieldset>	
	
		 <legend><bean:message key="lbl.Co_app_customer_detail"/></legend>  

  
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
   
     
     <td width="3%" ><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();" /></td> 
	    
        <td ><b><bean:message key="lbl.customerID"/></b></td>
		<td ><strong><bean:message key="lbl.customerName"/></strong></td>
        
        <td ><b><bean:message key="lbl.applicantCategory"/></b></td>
        <td><b><bean:message key="lbl.existingCustomer"/></b></td>
	</tr>
	           
	                        <logic:present name="coappDetails">
	                        
							<logic:iterate id="coappDetails" name="coappDetails">
	
	
					<tr class="white1">
							
			 				<td   ><input type="checkbox" name="chk" id="chk" value="${coappDetails.custId }"/></td>
							<td  >
							<a href="#" id="anchor0" onclick="return fetchCustDtl('${coappDetails.custId }','${leadDetails[0].leadId}','COAPPL');">
							
							${coappDetails.custId }</a></td>
							
	      			        <td  >${coappDetails.customerName }
	      			       </td>
	      			        
							<td >${coappDetails.coAppcustomerType }
							</td>
							<td >${coappDetails.coApprelationship }</td>	
							
			       </tr>				
			 
	</logic:iterate>
	</logic:present> 
	
		</table>
		</td></tr>
</table>
 
 <button type="button" name="delete" class="topformbutton2" id="delete" title="Alt+D" accesskey="D" value="Delet" onclick="deleteCustomer('N',${leadDetails[0].leadId},'COAPPL');"><bean:message key="button.delete" /></button>

        
</fieldset>
<!-- grid details end -->

<logic:equal name="custType" value="C">

	<script type="text/javascript">
	coAppupdateCust('C');coAppcusttype();
	</script>


</logic:equal>

<logic:present name="msg">
			<script type="text/javascript">
			if('<%=request.getAttribute("msg").toString()%>'=='LT'){
				
				alert('<bean:message key="lbl.preDealForwarded"/>');
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehind.do?method=leadEntry&leadId=NEW";
				document.getElementById('leadCapturingDetails').submit();
		
			}
		if('<%=request.getAttribute("msg").toString()%>'=='M'){
			hideorshow(document.getElementById('leadGenerator1').value);
			alert('<bean:message key="lbl.dataSave"/>');
		}
		if('<%=request.getAttribute("msg").toString()%>'=='S'){
			hideorshow(document.getElementById('leadGenerator1').value);
			alert('<bean:message key="lbl.dataDeleted"/>');
			}
		if('<%=request.getAttribute("msg").toString()%>'=='N'){
			hideorshow(document.getElementById('leadGenerator1').value);
			alert('<bean:message key="lbl.dataNtDeleted"/>');
			}
		if('<%=request.getAttribute("msg").toString()%>'=='L'){
			alert('<bean:message key="lbl.dataNtDeletedCibil"/>');
			}
	</script>
		</logic:present>
		<logic:present name="fw">
			<script type="text/javascript">
			if('<%=request.getAttribute("fw").toString()%>'=='S'){
				hideorshow(document.getElementById('leadGenerator1').value);
				alert('<bean:message key="lbl.preDealForwarded"/>');
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/leadCommonList.do?flag=Y";
				document.getElementById('leadCapturingDetails').submit();

	}
		</script>
		</logic:present>
		<logic:present name="Al">
			<script type="text/javascript">
			if('<%=request.getAttribute("Al").toString()%>'=='L'){
				hideorshow(document.getElementById('leadGenerator1').value);
				alert('Lead Forwarded and Allocated Succesfully');
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/leadCommonList.do?flag=Y";
				document.getElementById('leadCapturingDetails').submit();

	}
</script>
		</logic:present>
		<logic:present name="procvalue">
			<script type="text/javascript">
			if('<%=request.getAttribute("procvalue").toString()%>'=='ProcError'){
				alert(<%=request.getAttribute("procvalue").toString()%>);
				var leadId = document.getElementById('leadId').value;
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
				document.getElementById('leadCapturingDetails').submit();

	}
</script>
		</logic:present>
		<logic:present name="deletelead">
			<script type="text/javascript">
			if('<%=request.getAttribute("deletelead").toString()%>'=='delete'){
			
				alert('<bean:message key="msg.leaddeleted"/>');
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehind.do?method=leadEntry&leadId=NEW";
				document.getElementById('leadCapturingDetails').submit();

	}
</script>
		</logic:present>
<logic:present name="group">
<script type="text/javascript">
	
				alert('<%=request.getAttribute("group")%>');	
</script>	
</logic:present>
<logic:present name="dedupeFail">
<script type="text/javascript">
    
                alert('Cibil or Dedupe is Pending');    
</script>   
</logic:present>
<logic:present name="panNoExist">
<script type="text/javascript">
				alert('<%=request.getAttribute("panNoExist")%>');
				
			 
</script>	
</logic:present>
<logic:present name="uidExist">
<script type="text/javascript">
				alert('<%=request.getAttribute("uidExist")%>');
				
</script>	
</logic:present>

<!-- Shashank Starts  -->
<logic:present name="voterExist">
<script type="text/javascript">
				alert('<%=request.getAttribute("voterExist")%>');
				<%-- var leadId = document.getElementById('leadId').value;
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
				document.getElementById('leadCapturingDetails').submit(); --%>
			 
</script>	
</logic:present>
<logic:present name="dlExist">
<script type="text/javascript">
				alert('<%=request.getAttribute("dlExist")%>');
				<%-- var leadId = document.getElementById('leadId').value;
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
				document.getElementById('leadCapturingDetails').submit(); --%>
			 
</script>	
</logic:present>
<logic:present name="passsportExist">
<script type="text/javascript">
				alert('<%=request.getAttribute("passsportExist")%>');
				<%-- var leadId = document.getElementById('leadId').value;
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
				document.getElementById('leadCapturingDetails').submit(); --%>
			 
</script>	
</logic:present>
<logic:present name="regisExist">
<script type="text/javascript">
				alert('<%=request.getAttribute("regisExist")%>');
				<%-- var leadId = document.getElementById('leadId').value;
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
				document.getElementById('leadCapturingDetails').submit(); --%>
			 
</script>	
</logic:present>
<!-- Shashank Ends  -->

<logic:present name="nameExist">
<script type="text/javascript">
var coAppNameExist = confirm('<%=request.getAttribute("nameExist")%>');

if(coAppNameExist)
{
	var coApplbxCustomerId=document.getElementById("coApplbxCustomerId").value;
	var coAppcustPanInd=document.getElementById("coAppcustPanInd").value;
	var coAppaadhaar=document.getElementById("coAppaadhaar").value;
	var leadId=document.getElementById("leadId").value;
	var id="Save";
	var coAppNameExist="C";
	if(document.getElementById("updateCustId").value){
		var updateCustId=document.getElementById("updateCustId").value;
		document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCoappSave.do?method=leadEntrySave&saveForward="+id+"&coApplbxCustomerId="+coApplbxCustomerId+"&coAppcustPanInd="+coAppcustPanInd+"&coAppaadhaar="+coAppaadhaar+"&customer=coapp&leadId="+leadId+"&updateCustId="+updateCustId+"&coAppNameExist="+coAppNameExist;
		document.getElementById('leadCapturingDetails').submit();
	}else{
		document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCoappSave.do?method=saveNewLead&saveForward="+id+"&coApplbxCustomerId="+coApplbxCustomerId+"&coAppcustPanInd="+coAppcustPanInd+"&coAppaadhaar="+coAppaadhaar+"&customer=coapp&leadId="+leadId+"&coAppNameExist="+coAppNameExist;
		document.getElementById('leadCapturingDetails').submit();
	}

}	

				
			 
</script>	
</logic:present>
<logic:present name="gaurnameExist">
<script type="text/javascript">
				alert('<%=request.getAttribute("gaurnameExist")%>');
				
			 
</script>	
</logic:present>
	</html:form>
	<div align="center" class="opacity" style="display: none"
		id="processingImage"></div>

</body>
</html:html>
