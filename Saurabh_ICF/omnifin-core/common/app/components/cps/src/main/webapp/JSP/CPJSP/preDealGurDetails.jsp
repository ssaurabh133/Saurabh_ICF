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
	
	function gaursan2(){
		
		if(document.getElementById("gaurrelationship1").value == "New")
		{
			document.getElementById("gaurcustomerType1").removeAttribute("disabled","true");
			document.getElementById("gauraddresstype1").removeAttribute("disabled","true");
			if(document.getElementById("gaurfirstName")){
				document.getElementById("gaurfirstName").removeAttribute("disabled","true");
				}
		}else{
			
			document.getElementById("gaurcustomerType1").setAttribute("disabled","true");
			document.getElementById("gauraddresstype1").setAttribute("disabled","true");
			if(document.getElementById("gaurfirstName")){
				document.getElementById("gaurfirstName").setAttribute("disabled","true");
				}
		}
		document.getElementById("gaurcustomerType1").value="";
		
	}

	function gaursan(){
		if(document.getElementById("gaurrelationship1").value == "New")
		{
			
			//document.getElementById("gaurcustomerType1").removeAttribute("disabled");
			document.getElementById("gauraddresstype1").removeAttribute("disabled","true");
			if(document.getElementById("gaurfirstName")){
				document.getElementById("gaurfirstName").removeAttribute("disabled","true");
				}
			document.getElementById("gaurcustPanInd").removeAttribute("disabled","true");
			//document.getElementById("gauraadhaar").removeAttribute("disabled","true");
			//document.getElementById("customerType").value = 'I';
			//document.getElementById("indconstitution").removeAttribute("disabled");
			//document.getElementById("corconstitution").removeAttribute("disabled");
			//document.getElementById("relationshipSince").setAttribute("disabled","true");
			
			if(document.getElementById("gaurcustomerIdButton") != null){
				document.getElementById("gaurcustomerIdButton").style.display='none';
			}
		
		}else {
			document.getElementById("gaurcustomerType1").setAttribute("disabled","true");
			document.getElementById("gauraddresstype1").setAttribute("disabled","true");
			if(document.getElementById("gaurfirstName")){
				document.getElementById("gaurfirstName").setAttribute("disabled","true");
				}
			//document.getElementById("indconstitution").setAttribute("disabled","true");
			//document.getElementById("corconstitution").setAttribute("disabled","true");
			//document.getElementById("relationshipSince").removeAttribute("disabled");
			if(document.getElementById("gaurcustomerType1").value=="C"){
				document.getElementById("guarconSubprofile").value="";
				document.getElementById("guarconSubprofile").setAttribute("disabled","true");
			}
			else
				document.getElementById("guarconSubprofile").removeAttribute("disabled","true");
			if(document.getElementById("gaurcustPanInd").value!=""){
			document.getElementById("gaurcustPanInd").setAttribute("disabled","true");
			}
			else{
			document.getElementById("gaurcustPanInd").removeAttribute("disabled","true");
			}
			/* if(document.getElementById("gauraadhaar").value!=""){
			document.getElementById("gauraadhaar").setAttribute("disabled","true");
			}
			else{
			document.getElementById("gauraadhaar").removeAttribute("disabled","true");
			} */
			if(document.getElementById("gaurcustomerIdButton") != null){
				document.getElementById("gaurcustomerIdButton").style.display='';	
				document.getElementById("gaurcustomerIdButton").focus();
				document.getElementById("gaurcustomerIdButton").removeAttribute("disabled");
			}
			return false;
		}
	
	}
	function gaursan1(){
			if(document.getElementById("gaurcustomerIdButton") != null){
				document.getElementById("gaurcustomerIdButton").style.display='';	
				document.getElementById("gaurcustomerIdButton").focus();
			}
			return false;
			}
	function coAppsan1(){
			if(document.getElementById("coAppcustomerIdButton") != null){
				document.getElementById("coAppcustomerIdButton").style.display='';	
				document.getElementById("coAppcustomerIdButton").focus();
			}
			return false;
	
	
	}
	function subProfileEnableDisable()
	{
		//alert(document.getElementById("customerType1").value);
		if(document.getElementById("customerType1").value=="C"){
			document.getElementById("appconSubprofile").value="";
			document.getElementById("appconSubprofile").setAttribute("disabled","true");
		}
		else
			document.getElementById("appconSubprofile").removeAttribute("disabled","true");
		
		if(document.getElementById("coAppcustomerType1").value=="C"){
			document.getElementById("coAppconSubprofile").value="";
			document.getElementById("coAppconSubprofile").setAttribute("disabled","true");
		}
		else
			document.getElementById("coAppconSubprofile").removeAttribute("disabled","true");
		
		if(document.getElementById("gaurcustomerType1").value=="C"){
			document.getElementById("guarconSubprofile").value="";
			document.getElementById("guarconSubprofile").setAttribute("disabled","true");
		}
		else
			document.getElementById("guarconSubprofile").removeAttribute("disabled","true");
	}
	function subProfileEnaDis()
	{
		//alert(document.getElementById("customerType1").value);
		if(document.getElementById("customerType1").value=="C"){
			document.getElementById("appconSubprofile").value="";
			document.getElementById("appconSubprofile").setAttribute("disabled","true");
		}
		else
			document.getElementById("appconSubprofile").removeAttribute("disabled","true");
		
		if(document.getElementById("coAppcustomerType1").value=="C"){
			document.getElementById("coAppconSubprofile").value="";
			document.getElementById("coAppconSubprofile").setAttribute("disabled","true");
		}
		else
			document.getElementById("coAppconSubprofile").removeAttribute("disabled","true");
		
		if(document.getElementById("gaurcustomerType1").value=="C"){
			document.getElementById("guarconSubprofile").value="";
			document.getElementById("guarconSubprofile").setAttribute("disabled","true");
		}
		else
			document.getElementById("guarconSubprofile").removeAttribute("disabled","true");
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

<body onload="gaursan();enableAnchor();showHideDescLovOnLoad();hideorshow(document.getElementById('leadGenerator').value);custtype();gaurcusttype();">


	<html:form action="/preDealGurSearch"
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
							onclick="closeLovCallonLov1();openLOVCommon(253,'leadCapturingDetails','lbxIndustry','','', '','','lovGaurIndustryLead','industry')"
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
					
					<%-- <td >
						<bean:message key="lbl.turnover" />
				</td>
					<td >
						<html:text property="turnOver" styleClass="text"
							styleId="turnOver" value="${leadDetails[0].turnOver}"
							maxlength="22" style="text-align:right; "
							onkeypress="return numbersonly(event, id, 18)"
							onblur="formatNumber(this.value, id);"
							onkeyup="checkNumber(this.value, event, 18, id);"
							onfocus="keyUpNumber(this.value, event, 18, id);" />
					</td> --%>
					</tr>
	

</table>

</fieldset>
</div>

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
						<html:select property="coAppbusinessSegment" styleId="coAppbusinessSegment"
							value="${leadDetails[0].coAppbusinessSegment}" styleClass="text">
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
						<html:text property="coAppindustry" readonly="true" styleId="coAppindustry"
							styleClass="text" value="${leadDetails[0].coAppindustry}"
							tabindex="-1" />
						<html:hidden property="coApplbxIndustry" styleId="coApplbxIndustry"
							value="${leadDetails[0].coApplbxIndustry}" />
						<html:button property="industryButton" styleId="industryButton"
							onclick="openLOVCommon(19252,'leadCapturingDetails','coApplbxIndustry','','', '','','lovGaurIndustryLead','coAppindustry');closeLovCallonLov('');"
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
					
					<%-- <td >
						<bean:message key="lbl.turnover" />
				</td>
					<td >
						<html:text property="coAppturnOver" styleClass="text"
							styleId="coAppturnOver" value="${leadDetails[0].coAppturnOver}"
							maxlength="22" style="text-align:right; "
							onkeypress="return numbersonly(event, id, 18)"
							onblur="formatNumber(this.value, id);"
							onkeyup="checkNumber(this.value, event, 18, id);"
							onfocus="keyUpNumber(this.value, event, 18, id);" />
					</td> --%>
					</tr>
	

</table>

</fieldset>
</div>

		<fieldset>
			<legend>
				<bean:message key="lbl.guar_customer_detail" />
			</legend>
			<table width="100%">
<%-- <tr>
<td><bean:message key="lbl.GuarStatus" /></td>
      <td>
      <logic:equal value="A" name="gaurStatus">

         <input type="checkbox" name="gaurStatus" id="gaurStatus" checked="checked"  onclick="gaurEnable();"/>
       </logic:equal>
      <logic:notEqual value="A" name="gaurStatus">
     
         <input type="checkbox" name="gaurStatus" id="gaurStatus" onclick="gaurEnable();" />
      </logic:notEqual></td>
</tr> --%>
								<tr>

					<td width="13%" >
						<bean:message key="lbl.relationship" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<logic:present name="leadOther">
						<td width="13%" id="gaur" >
							<html:select property="gaurrelationship" styleId="gaurrelationship1"
								styleClass="text" onchange="gaursan1();gaursan2();gaursan();gaurcleanUp();customerLov();"
								value="${leadDetails[0].gaurrelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="gauraddressId" styleClass="text" styleId="gauraddressId"  value="" />
		<html:hidden property="gaurlbxCustomerId" styleClass="text" styleId="gaurlbxCustomerId"  value="${leadDetails[0].gaurlbxCustomerId}" />
		<html:hidden property="gaurrelationship" styleClass="text" styleId="gaurrelationship"  value="${leadDetails[0].gaurrelationship}" />
		<html:button property="gaurcustomerIdButton" styleId="gaurcustomerIdButton" onclick="closeLovCallonLov1();openLOVCommon(192044,'customerForm','gaurlbxCustomerId','','','','','copyGaurCustomerDetails','gaurcustomerName','gauraddress1','gaurcustomerType');gaurcusttype();gaursan1();" value=" " styleClass="lovbutton"></html:button>				
						</td>
						<td width="13%" id="gaur1" style="display: none">
							<html:select property="gaurrelationship" styleId="gaurrelationship1"
								styleClass="text" onchange="gaursan1();gaursan2();gaursan();gaurcleanUp();customerLov();"
								value="${leadDetails[0].gaurrelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="gauraddressId" styleClass="text" styleId="gauraddressId"  value="" />
		<html:hidden property="gaurlbxCustomerId" styleClass="text" styleId="gaurlbxCustomerId"  value="${leadDetails[0].gaurlbxCustomerId}" />
	<html:hidden property="gaurrelationship" styleClass="text" styleId="gaurrelationship"  value="${leadDetails[0].gaurrelationship}" />
		<html:button property="gaurcustomerIdButton" styleId="gaurcustomerIdButton" onclick="closeLovCallonLov1();openLOVCommon(192044,'customerForm','gaurlbxCustomerId','','','','','copyGaurCustomerDetails','gaurcustomerName','gauraddress1','gaurcustomerType');gaurcusttype();" value=" " styleClass="lovbutton"></html:button>
		
							
						</td>
					</logic:present>
					<logic:present name="leadNew">
						<td width="13%">
							<html:select property="gaurrelationship" styleId="gaurrelationship1"
								styleClass="text" onchange="gaursan1();gaursan2();gaursan();gaurcleanUp();customerLov();"
								value="${leadDetails[0].gaurrelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="gauraddressId" styleClass="text" styleId="gauraddressId"  value="" />
		<html:hidden property="gaurlbxCustomerId" styleClass="text" styleId="gaurlbxCustomerId"  value="${leadDetails[0].gaurlbxCustomerId}" />
	<html:hidden property="gaurrelationship" styleClass="text" styleId="gaurrelationship"  value="${leadDetails[0].gaurrelationship}" />
		<html:button property="gaurcustomerIdButton" styleId="gaurcustomerIdButton" onclick="closeLovCallonLov1();openLOVCommon(192044,'customerForm','gaurlbxCustomerId','','','','','copyGaurCustomerDetails','gaurcustomerName','gauraddress1','gaurcustomerType');gaurcusttype();" value=" " styleClass="lovbutton"></html:button>
		
							
						</td>
					</logic:present>

					<td width="13%">
						<bean:message key="lbl.Type" />
						<span><font style="color: red;"> *</font> </span>
					</td>
					<td width="13%">
						<html:select property="gaurcustomerType1" styleClass="text"
							styleId="gaurcustomerType1" value="${leadDetails[0].gaurcustomerType}"
							onchange="gaurupdateCust(this.value);gaurcusttype();" >
							<html:option value="">--Select--</html:option>
							<html:option value="I">INDIVIDUAL</html:option>
							<html:option value="C">CORPORATE</html:option>
						</html:select>
						<html:hidden property="gaurcustomerType" styleId="gaurcustomerType" value="${leadDetails[0].gaurcustomerType}" />
					</td></tr>
					<tr>
					<td width="13%">

						<bean:message key="constitution" />
						<font color="red">*</font>
					</td>
					<td><div id="gaurIndividualconstitution" style="display:none">
						<html:select property="gaurindconstitution" styleId="gaurindconstitution"
							value="${leadDetails[0].gaurcontitutionCode}" styleClass="text"
							onchange="indConSubprofile('gaurindconstitution');appendZero();">
							<html:option value="">--Select--</html:option>
							<logic:present name="indconstitutionlist">
								<logic:notEmpty name="indconstitutionlist">
									<html:optionsCollection name="indconstitutionlist"
										label="constitution" value="contitutionCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						</div>
						<div id="gaurcorporateconstitution" style="display:none">
						<html:select property="gaurcorconstitution" styleId="gaurcorconstitution"
							value="${leadDetails[0].gaurcontitutionCode}" styleClass="text"
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
						<div id="gaurconstitution">
						<html:select property="gaurconstitution" styleId="gaurconstitution"
							value="${leadDetails[0].gaurcontitutionCode}" styleClass="text"
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
					<!-- Saurabh Code Start here for Individual Sub Profile -->
						
		<%-- <td><bean:message key="lbl.IndConSubprofile" /><font color="red">*</font></td>
		<html:hidden property="gaurconSubprofileId" styleClass="text" styleId="gaurconSubprofileId" value="${leadDetails[0].guarconSubprofile}" />
		<td><html:select property="guarconSubprofile" value="${leadDetails[0].guarconSubprofile}" styleClass="text" styleId="guarconSubprofile">
				<logic:present name="gaurindConstSubprofileList">
					<logic:notEmpty name="gaurindConstSubprofileList">
					<html:optionsCollection name="gaurindConstSubprofileList" label="indConstSubprofile" value="indConstSubprofileCode" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
		  </td> --%>
		<!-- End By saurabh -->	

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
						<html:select property="gaurbusinessSegment" styleId="gaurbusinessSegment1"
							value="${leadDetails[0].gaurbusinessSegment}" styleClass="text">
							<html:option value="">--Select--</html:option>
							<logic:present name="businessSegmentList">
								<logic:notEmpty name="businessSegmentList">
									<html:optionsCollection name="businessSegmentList"
										label="businessSegmentDesc" value="businessSegmentCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						<html:hidden property="gaurbusinessSegment" styleClass="text" styleId="gaurbusinessSegment" value="${leadDetails[0].gaurbusinessSegment}" />
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
							onclick="closeLovCallonLov1();openLOVCommon(19253,'leadCapturingDetails','gaurlbxIndustry','','', '','','lovGaurIndustryLead','gaurindustry')"
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
					
					<%-- <td >
						<bean:message key="lbl.turnover" />
				</td>
					<td >
						<html:text property="gaurturnOver" styleClass="text"
							styleId="gaurturnOver" value="${leadDetails[0].gaurturnOver}"
							maxlength="22" style="text-align:right; "
							onkeypress="return numbersonly(event, id, 18)"
							onblur="formatNumber(this.value, id);"
							onkeyup="checkNumber(this.value, event, 18, id);"
							onfocus="keyUpNumber(this.value, event, 18, id);" />
					</td> --%>
					</tr>
	

</table>

</fieldset>
</div>


<div id='gaurindividualfield'">
<fieldset>
			<legend>
				<bean:message key="lbl.g_individual.details" />
			</legend>
			<table width="100%">
			<tr>
	<td width="13%">
							<bean:message key="lbl.fname" />
							<font color="red">*</font>
						</td>
						<td width="13%">
							<html:text property="gaurfirstName" styleId="gaurfirstName"
								styleClass="text" value="${leadDetails[0].gaurfirstName}"
								onchange="return upperMe('gaurfirstName');" maxlength="50" />
						</td>
					<td width="13%">
						<bean:message key="individual.middle" />
						<!-- <font color="red">*</font> -->
					</td>
					<td width="13%">
						<html:text property="gaurmiddleName" styleId="gaurmiddleName"
							styleClass="text" value="${leadDetails[0].gaurmiddleName}"
							onchange="return upperMe('gaurmiddleName');" maxlength="50" />
					</td>
					</tr>
					<tr>
					<td width="13%">
						<bean:message key="individual.last" />
						<font color="red">*</font>
					</td>
					<td width="13%">
						<html:text property="gaurlastName" styleId="gaurlastName"
							styleClass="text" value="${leadDetails[0].gaurlastName}"
							onchange="return upperMe('gaurlastName');" maxlength="50" />
					</td>
					</tr>
				<tr>
					<td>
						<bean:message key="individual.birthdate" />
						<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
					</td>
					
				
					<td>
						<html:text property="gaurcustDOB" onchange="checkDate('gaurcustDOB');gaurcheckcustDOB('gaurcustDOB');"
							styleId="gaurcustDOB" styleClass="text"
							value="${leadDetails[0].gaurcustDOB}" />
					</td>
					<td>
						<bean:message key="fatherHusband" /><font color="red">*</font>
					</td>
					<td>
						<html:text property="gaurfatherName"
							styleId="gaurfatherName" styleClass="text" onblur="caseChange('leadCapturingDetails','gaurfatherName');"
							value="${leadDetails[0].gaurfatherName}"  maxlength="50"/>
					</td>
					<input type="hidden" name="sysDate" id="sysDate" value="${requestScope.sysDate}" />
					<input type="hidden" name="approve" id="approve" value="N" />
				
					
		
			</tr>
			<!-- Sanjog Changes Start Here -->
			<tr>
					
					<td><bean:message key="lbl.gender" /><font color="red">*</font></td>
          <td><html:select property="gaurgenderIndividual" value="${leadDetails[0].gaurgenderIndividual}" styleClass="text" styleId="gaurgenderIndividual">
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
						<html:text property="gaurdlNumber"
							styleId="gaurdlNumber" styleClass="text"
							value="${leadDetails[0].gaurdlNumber}" maxlength="20"/>
					</td>
								
		
			</tr>
			<tr>
					<td>
						<bean:message key="voterId" />
					</td>				
				
					<td>
						<html:text property="gaurvoterId"
							styleId="gaurvoterId" styleClass="text"
							value="${leadDetails[0].gaurvoterId}" maxlength="20"/>
					</td>
					
					<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="gaurcustPanInd" maxlength="10" styleClass="text"
							styleId="gaurcustPanInd" value="${leadDetails[0].gaurcustPanInd}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('gaurcustPanInd');" />
					</td>
					</tr>
					<tr>
					<td>
						<bean:message key="passport" />
					</td>				
				
					<td>
						<html:text property="gaurpassport"
							styleId="gaurpassport" styleClass="text"
							value="${leadDetails[0].gaurpassport}" maxlength="20"/>
					</td>
					<td><bean:message key="aadhaar" /></td>
          <td><html:text property="gauraadhaar" styleClass="text" styleId="gauraadhaar" readonly="true" onkeypress="return numericOnly(event,12);" value="${leadDetails[0].gauraadhaar}" maxlength="12"/></td>
					
					</tr>
					
		</table>		
	</fieldset>
</div>

		<fieldset>
			<legend>
				<bean:message key="lbl.g_commDetails" />
			</legend>
			<table width="100%">
<tr>
<td><bean:message key="lbl.CopyAdd" /></td>
 <td><input type="checkbox" name="copyAppGaur" id="copyAppGaur" onclick="copyApplicantToGaurAdd();" /></td>
 
 <%-- <td><bean:message key="lbl.CopyCoAppAdd" /></td>
 <td><input type="checkbox" name="copyCoAppGaur" id="copyCoAppGaur" onclick="copyCoApplicantToGaurAdd();" /></td> --%>
</tr>

				<%-- <tr id="contactPersonDesignation" style="display: none;">
				<td>
						<bean:message key="lbl.contactPerson" />
					</td>
					<td>
						<html:text property="contactPerson" styleClass="text"
							styleId="contactPerson" maxlength="50"
							value="${leadDetails[0].contactPerson}"
							onblur="caseChange('leadCapturingDetails','contactPerson');" />
					</td>
							<td>
						<bean:message key="lbl.persondesignation" />
					</td>
					<td><html:text property="personDesignation" styleClass="text" styleId="personDesignation" maxlength="50" value="${leadDetails[0].personDesignation}" onblur="caseChange('leadCapturingDetails','personDesignation');" />
					</td>
					

			</tr> --%>
				<tr>	
					<td>
						<bean:message key="address.type" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:select property="gauraddresstype" styleClass="text"
							styleId="gauraddresstype1" value="${leadDetails[0].gauraddresstype}">
							<html:option value="">--Select--</html:option>
							<html:optionsCollection name="addresstypeList"
								value="addresstypeid" label="addresstypename" />
						</html:select>
						<html:hidden property="gauraddresstype" styleClass="text" styleId="gauraddresstype" value="${leadDetails[0].gauraddresstype}" />
					</td>
				
					<td width="13%">
						<bean:message key="lbl.addressLine1" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="gauraddress1" styleClass="text"
							styleId="gauraddress1" maxlength="50"
							value="${leadDetails[0].gauraddress1}"
							onblur="caseChange('leadCapturingDetails','gauraddress1');" />
					</td>

</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.two" />
					</td>
					<td width="13%">
						<html:text property="gauraddress2" styleClass="text"
							styleId="gauraddress2" maxlength="50"
							value="${leadDetails[0].gauraddress2}"
							onblur="caseChange('leadCapturingDetails','gauraddress2');" />
					</td>
					<td width="13%">
						<bean:message key="lbl.three" />
					</td>
					<td width="13%">
						<html:text property="gauraddress3" styleClass="text"
							styleId="gauraddress3" maxlength="50"
							value="${leadDetails[0].gauraddress3}"
							onblur="caseChange('leadCapturingDetails','gauraddress3');" />
					</td>
</tr>
	
	<!-- pooja code starts for Guarantor PinCode -->
		<logic:present name="pincodeFlag">
<logic:equal value="N" name="pincodeFlag">
 <html:hidden property="pincodeFlag" styleId="pincodeFlag" styleClass="text" value="N"/>
<tr> 

		
	<td  width="13%">
						<bean:message key="lbl.country" /><span><font style="color: red;"> *</font> 
						 </span>
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td  width="13%">
						<logic:present name="leadDetails">
							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text"
								tabindex="-1" readonly="true" value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(192069,'leadCapturingDetails','gaurtxtCountryCode','','','','','gaurclearCountryLovChild','gaurcountry')"
								value="" name="countryButton" />
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:present>
						<logic:notPresent name="leadDetails">

							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text"
								tabindex="-1" readonly="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(192069,'leadCapturingDetails','gaurtxtCountryCode','','','','','gaurclearCountryLovChild','gaurcountry')"
								value="" name="countryButton"/>
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
					<td width="13%">
						<bean:message key="lbl.state" /><span><font style="color: red;"> *</font> 
						 </span>
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td width="13%">
						<html:text property="gaurstate" styleId="gaurstate" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].gaurstate}" />
						<input type="button" class="lovbutton" id="stateButton"
							onclick="openLOVCommon(192071,'leadCapturingDetails','gaurstate','gaurcountry','gaurtxtStateCode', 'gaurtxtCountryCode','Please select country first!!!','gaurclearStateLovChild','gaurtxtStateCode')"
							value="" name="stateButton"/>
							<input type="hidden" name="gaurtxtStateCode" id="gaurtxtStateCode"
								value="${leadDetails[0].gaurtxtStateCode}" />
					</td>
	
</tr>

<tr>
					
					

<td width="13%">
						<bean:message key="lbl.dist" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="gaurdist" styleId="gaurdist" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].gaurdist}" />
						<input type="button" class="lovbutton" id="distButton"
							onclick="openLOVCommon(192073,'leadCapturingDetails','gaurdist','gaurstate','gaurtxtDistCode', 'gaurtxtStateCode','Please select state first!!!','gaurclearDistrictLovChild','gaurtxtDistCode')"
							value=" " name="distButton"/>
							<input type="hidden" name="gaurtxtDistCode" id="gaurtxtDistCode"
								value="${leadDetails[0].gaurtxtDistCode}" />
					</td>
					
<td width="13%">
						<bean:message key="address.Tahsil" />
					</td>
					<td>
						<input type="text" name="gaurtahsilDesc" id="gaurtahsilDesc" readonly="readonly" value="${leadDetails[0].gaurtahsil}" />
						<html:hidden property="gaurtahsil" styleId="gaurtahsil" styleClass="text" value="${leadDetails[0].gaurtahsilDesc}"/>
						<html:button property="tahsilButton" styleId="tahsilButton" onclick="openLOVCommon(495,'leadCapturingDetails','gaurtahsilDesc','gaurdist','gaurtahsilDesc', 'gaurtxtDistCode','Please select District first','','gaurtahsil');" value=" " styleClass="lovbutton"> </html:button>
					</td>

</tr>


				<tr>
				<td width="13%">
						<bean:message key="lbl.pincode" />
						 <span><font style="color: red;"> *</font> 
						 </span> 
		</td>
					<td width="13%">
						<html:text property="gaurpincode" styleClass="text" styleId="gaurpincode"
							maxlength="6" value="${leadDetails[0].gaurpincode}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,6);" />
					</td>
					
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="gaurphoneOff" styleClass="text"
							styleId="gaurphoneOff" maxlength="10"
							value="${leadDetails[0].gaurphoneOff}"
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
			<td><html:text property="gaurpincode" styleId="gaurpincode" size="20" styleClass="text" readonly="true" value="${leadDetails[0].gaurpincode}" tabindex="-1"></html:text>
            <html:hidden property="gaurlbxPincode" styleId="gaurlbxPincode" styleClass="text" value=""/>
 			<html:button property="pincodeButton" styleId="pincodeButton" onclick="openLOVCommon(192026,'leadCapturingDetails','gaurpincode','','', '','','getGaurCountryStateDistrictTahsilValue','gaurtxnTahsilHID','hcommon');closeLovCallonLov('');" value=" " styleClass="lovbutton"> </html:button>
		</td>
			
		<td><bean:message key="address.Tahsil" /></td>	
			<td id="gaurlov2"> 
            	<div id="gaurtahsildetail">
            		<html:text property="gaurtahsil" styleId="gaurtahsil" size="20" styleClass="text" readonly="true" value="${leadDetails[0].gaurtahsilDesc}" ></html:text>
                	<html:hidden property="gaurtxnTahsilHID" styleId="gaurtxnTahsilHID" styleClass="text" value="${leadDetails[0].gaurtahsil}"/>
   				</div>
   			</td>
   			
   			<td id="gaurlov4" style="display: none"> 
            	<div id="gaurtahsildetail">
            		<html:text property="gaurtahsil4" styleId="gaurtahsil4" size="20" styleClass="text"  value="${leadDetails[0].gaurtahsil}" ></html:text>
                	<html:hidden property="gaurtxnTahsilHID" styleId="gaurtxnTahsilHID" styleClass="text" value="${leadDetails[0].gaurtxnTahsilHID}"/>
   				</div>
   			</td>	
   			
   			
   			
	</tr>
	
	
	<tr id="gaurlov5"> 
		<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font> -->
						</span></td>
          	<td >
          		<div id="gaurcityID">
          			<html:text property="gaurdist" styleId="gaurdist" size="20" styleClass="text" readonly="true" value="${leadDetails[0].gaurdist}" ></html:text>
          			<html:hidden property="gaurtxtDistCode" styleId="gaurtxtDistCode" styleClass="text" value="${leadDetails[0].gaurtxtDistCode}"></html:hidden>
   				</div>
   			</td>
   			
   			<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="gaurstatedetail">
                	<html:text property="gaurstate" styleId="gaurstate" styleClass="text" size="20" value="${leadDetails[0].gaurstate}" readonly="true" ></html:text>
   					<html:hidden property="gaurtxtStateCode" styleId="gaurtxtStateCode" styleClass="text" value=" ${leadDetails[0].gaurtxtStateCode}"></html:hidden>
    				<html:hidden property="gaurtahsilDesc" styleId="gaurtahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton" style="display: none" onclick="closeLovCallonLov('gaurcountry');openLOVCommon(192071,'leadCapturingDetails','gaurstate','gaurcountry','gaurtxtStateCode', 'gaurtxtStateCode','Please select country first','gaurclearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	          	</div>
			</td>
		

	</tr>
	
	<tr id="gaurlov6" style="display: none">
	
	<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
          	<td>
          		<div id="gaurcityID">
          			<html:text property="gaurdist4" styleId="gaurdist4" size="20" styleClass="text" readonly="true" value="${leadDetails[0].gaurdist}" ></html:text>
          			<html:hidden property="gaurtxtDistCode" styleId="gaurtxtDistCode" styleClass="text" value="${leadDetails[0].gaurtxtDistCode}"></html:hidden>
  					<html:button property="distButton" styleId="districtButton" onclick="closeLovCallonLov('gaurstate6');openLOVCommon(20,'leadCapturingDetails','gaurdist4','gaurstate6','gaurtxtDistCode', 'gaurtxtStateCode','Please select state first','gaurclearTahsilLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   				</div>
   			</td>
	<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="gaurstatedetail">
                	<html:text property="gaurstate6" styleId="gaurstate6" styleClass="text" size="20" value="${leadDetails[0].gaurstate}" readonly="true" ></html:text>
   					<html:hidden property="gaurtxtStateCode" styleId="gaurtxtStateCode" styleClass="text" value=" ${leadDetails[0].gaurtxtStateCode}"></html:hidden>
    				<html:hidden property="gaurtahsilDesc" styleId="gaurtahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton"  onclick="closeLovCallonLov('gaurcountry');openLOVCommon(192071,'leadCapturingDetails','gaurstate6','gaurtxtCountryCode','gaurtxtStateCode', 'gaurtxtCountryCode','Please select country first','gaurclearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
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
					<td id="gaurlov1" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text"
								 readonly="true" value="${defaultcountry[0].defaultcountryname}" />
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text"
								readonly="true"	value="${defaultcountry[0].defaultcountryname}" />
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
					
					
					<td id="gaurlov3" style="display: none" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text"
								 readonly="true" value="${leadDetails[0].gaurcountry}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(192069,'leadCapturingDetails','gaurcountry','','','','','gaurclearCountryLovChild','gaurtxtCountryCode')"
								value="" name="countryButton" />
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${leadDetails[0].gaurtxtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text"
								 readonly="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(192069,'leadCapturingDetails','gaurcountry','','','','','gaurclearCountryLovChild','gaurtxtCountryCode')"
								value="" name="countryButton"/>
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>

					
		
				
					
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="gaurphoneOff" styleClass="text"
							styleId="gaurphoneOff" maxlength="10"
							value="${leadDetails[0].gaurphoneOff}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,10);" />
					</td>
	</tr>
	</logic:equal>
	</logic:present>
	<!-- pooja code end for Guarantor PinCode -->
				
				<tr>
					<td width="13%">
						<bean:message key="lbl.email" />
					</td>
					<td width="13%">
						<html:text property="gauremail" styleClass="text" styleId="gauremail"
							maxlength="100" value="${leadDetails[0].gauremail}"/>
					</td>
					<td width="13%">
						<bean:message key="lbl.landmark" />
					</td>
					<td width="13%">
						<html:text property="gaurlandmark" styleClass="text"
							styleId="gaurlandmark" maxlength="25"
							value="${leadDetails[0].gaurlandmark}"
							onblur="caseChange('leadCapturingDetails','gaurlandmark');" />
					</td>
	</tr>

			</table>
		</fieldset>
		<fieldset>
		<logic:notPresent name="existingCustomer">
					<tr>
					<html:hidden property="updateCustId" styleClass="text" styleId="updateCustId"  value="${leadDetails[0].updateCustId}" />
						<td>
							<button type="button" name="Save" id="Save"
								class="topformbutton2" onclick="return saveGurLeadDetails(id,${leadDetails[0].leadId});"
								title="Alt+V" accesskey="V">
								<bean:message key="button.save" />
							</button>
						</td>
					</tr>
					</logic:notPresent>
		</fieldset>
		<!-- grid details -->
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
							<html:text property="customerName" styleClass="text" disabled="true" 
								styleId="customerName" maxlength="250"
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
						<input type="text" id="groupName" name="groupName" class="text" value="${leadDetails[0].groupName}"  disabled="true"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton"  disabled="true" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
				
					<div id="groupText" style="display:none">
						<input type="text" id="groupName1" name="groupName1" class="text"  disabled="true" value="${leadDetails[0].groupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:notPresent>
					
					<logic:present name="leadDetails">
					<logic:iterate name="leadDetails" id="list">
					<logic:equal property="groupType" name="list" value="N">
					<div id="groupLov" style="display:none">
						<input type="text" id="groupName" name="groupName" class="text"  disabled="true" value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton"  disabled="true" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
					<div id="groupText" >
						<input type="text" id="groupName1" name="groupName1" class="text" disabled="true"  value="${leadDetails[0].groupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
						
					</logic:equal>
					<logic:equal property="groupType" name="list" value="E">
										
					<div id="groupLov" >
						<input type="text" id="groupName" name="groupName" class="text"  disabled="true" value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton"  disabled="true" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
				
					<div id="groupText" style="display:none">
						<input type="text" id="groupName1" name="groupName1" class="text"  disabled="true" value="${leadDetails[0].groupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					<logic:equal property="groupType" name="list" value="">
										
					<div id="groupLov" style="display: none;">
						<input type="text" id="groupName" name="groupName" class="text"  disabled="true" value="${leadDetails[0].groupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton"  disabled="true" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(16,'corporateDetailForm','groupName','','', '','','','hGroupId');" />
						<html:hidden property="hGroupId" styleId="hGroupId"styleClass="text" value="${leadDetails[0].hGroupId}" />
					</div>
				
					<div id="groupText" style="display:none">
						<input type="text" id="groupName1" name="groupName1" class="text"  disabled="true" value="${leadDetails[0].groupDesc}" maxlength="50"  tabindex="-1" />
					
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
						<html:text property="registrationNo" styleId="registrationNo" disabled="true" 
							maxlength="25" value="${leadDetails[0].registrationNo}"
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
							onclick="closeLovCallonLov1();openLOVCommon(253,'leadCapturingDetails','lbxIndustry','','', '','','lovGaurIndustryLead','industry')"
							value=" " styleClass="lovbutton" >
						</html:button>
						<input type="hidden" id="hIndustry" name="hIndustry" />
					</td>
				
					<td>
						<bean:message key="lbl.subIndustry" />
						
					</td>
					<td>

						<html:text property="subIndustry"  disabled="true" 
							styleId="subIndustry" styleClass="text"
							value="${leadDetails[0].subIndustry}" tabindex="-1" />
						<html:hidden property="lbxSubIndustry" styleId="lbxSubIndustry"
							value="${leadDetails[0].lbxSubIndustry}" />
						<html:button property="subIndustryButton"
							styleId="subIndustryButton" disabled="true" 
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
					
					<%-- <td >
						<bean:message key="lbl.turnover" />
				</td>
					<td >
						<html:text property="turnOver" styleClass="text"
							styleId="turnOver" value="${leadDetails[0].turnOver}"
							maxlength="22" style="text-align:right; "
							onkeypress="return numbersonly(event, id, 18)"
							onblur="formatNumber(this.value, id);"
							onkeyup="checkNumber(this.value, event, 18, id);"
							onfocus="keyUpNumber(this.value, event, 18, id);" />
					</td> --%>
					</tr>
	

</table>

</fieldset>
</div>

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
							<html:text property="coAppcustomerName" styleClass="text" disabled="true" 
								styleId="coAppcustomerName" maxlength="250"
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
						<input type="text" id="coAppgroupName" name="coAppgroupName"  disabled="true" class="text" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" disabled="true"  styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
				
					<div id="coAppgroupText" style="display:none">
						<input type="text" id="coAppgroupName1" name="coAppgroupName1"  disabled="true" class="text" value="${leadDetails[0].coAppgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:notPresent>
					
					<logic:present name="leadDetails">
					<logic:iterate name="leadDetails" id="list">
					<logic:equal property="coAppgroupType" name="list" value="N">
					<div id="coAppgroupLov" style="display:none">
						<input type="text" id="coAppgroupName" name="coAppgroupName"  disabled="true" class="text" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" disabled="true"  styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
					<div id="coAppgroupText" >
						<input type="text" id="coAppgroupName1" name="coAppgroupName1" class="text" disabled="true"  value="${leadDetails[0].coAppgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
						
					</logic:equal>
					<logic:equal property="coAppgroupType" name="list" value="E">
										
					<div id="coAppgroupLov" >
						<input type="text" id="coAppgroupName" name="coAppgroupName"  disabled="true" class="text" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton"  disabled="true" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
				
					<div id="coAppgroupText" style="display:none">
						<input type="text" id="coAppgroupName1" name="coAppgroupName1" class="text"  disabled="true" value="${leadDetails[0].coAppgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					<logic:equal property="coAppgroupType" name="list" value="">
										
					<div id="coAppgroupLov" style="display: none;">
						<input type="text" id="coAppgroupName" name="coAppgroupName" class="text"  disabled="true" value="${leadDetails[0].coAppgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton"  disabled="true" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19250,'corporateDetailForm','coAppgroupName','','', '','','','coApphGroupId');" />
						<html:hidden property="coApphGroupId" styleId="coApphGroupId"styleClass="text" value="${leadDetails[0].coApphGroupId}" />
					</div>
				
					<div id="coAppgroupText" style="display:none">
						<input type="text" id="coAppgroupName1" name="coAppgroupName1"  disabled="true" class="text" value="${leadDetails[0].coAppgroupDesc}" maxlength="50"  tabindex="-1" />
					
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
						<html:text property="coAppregistrationNo" styleId="coAppregistrationNo" disabled="true" 
							maxlength="25" value="${leadDetails[0].coAppregistrationNo}"
							styleClass="text" />
					</td>

				

		
					<td>
						<bean:message key="businessSegment" />
						<font color="red">*</font>
					</td>
					<td>
						<html:select property="coAppbusinessSegment" styleId="coAppbusinessSegment" disabled="true" 
							value="${leadDetails[0].coAppbusinessSegment}" styleClass="text">
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
						<html:text property="coAppindustry" readonly="true" styleId="coAppindustry" disabled="true" 
							styleClass="text" value="${leadDetails[0].coAppindustry}"
							tabindex="-1" />
						<html:hidden property="coApplbxIndustry" styleId="coApplbxIndustry"
							value="${leadDetails[0].coApplbxIndustry}" />
						<html:button property="industryButton" styleId="industryButton" disabled="true" 
							onclick="openLOVCommon(19252,'leadCapturingDetails','coApplbxIndustry','','', '','','lovGaurIndustryLead','coAppindustry');closeLovCallonLov('');"
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
						<html:button property="subIndustryButton" disabled="true" 
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
						<html:text property="coAppcustPan" maxlength="10" styleClass="text" disabled="true" 
							styleId="coAppcustPan" value="${leadDetails[0].coAppcustPan}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('coAppcustPan');" />
					</td>
					
					<%-- <td >
						<bean:message key="lbl.turnover" />
				</td>
					<td >
						<html:text property="coAppturnOver" styleClass="text"
							styleId="coAppturnOver" value="${leadDetails[0].coAppturnOver}"
							maxlength="22" style="text-align:right; "
							onkeypress="return numbersonly(event, id, 18)"
							onblur="formatNumber(this.value, id);"
							onkeyup="checkNumber(this.value, event, 18, id);"
							onfocus="keyUpNumber(this.value, event, 18, id);" />
					</td> --%>
					</tr>
	

</table>

</fieldset>
</div>

		<fieldset>
			<legend>
				<bean:message key="lbl.guar_customer_detail" />
			</legend>
			<table width="100%">
<%-- <tr>
<td><bean:message key="lbl.GuarStatus" /></td>
      <td>
      <logic:equal value="A" name="gaurStatus">

         <input type="checkbox" name="gaurStatus" id="gaurStatus" checked="checked"  onclick="gaurEnable();"/>
       </logic:equal>
      <logic:notEqual value="A" name="gaurStatus">
     
         <input type="checkbox" name="gaurStatus" id="gaurStatus" onclick="gaurEnable();" />
      </logic:notEqual></td>
</tr> --%>
								<tr>

					<td width="13%" >
						<bean:message key="lbl.relationship" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<logic:present name="leadOther">
						<td width="13%" id="gaur" >
							<html:select property="gaurrelationship" styleId="gaurrelationship" disabled="true" 
								styleClass="text" onchange="gaursan1();gaursan2();gaursan();gaurcleanUp();customerLov();"
								value="${leadDetails[0].gaurrelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="gauraddressId" styleClass="text" styleId="gauraddressId"  value="" />
		<html:hidden property="gaurlbxCustomerId" styleClass="text" styleId="gaurlbxCustomerId"  value="${leadDetails[0].gaurlbxCustomerId}" />
		<html:button property="gaurcustomerIdButton" styleId="gaurcustomerIdButton" onclick="closeLovCallonLov1();openLOVCommon(192044,'customerForm','gaurlbxCustomerId','','','','','copyGaurCustomerDetails','gaurcustomerName','gauraddress1','gaurcustomerType');gaurcusttype();gaursan1();" value=" " styleClass="lovbutton"></html:button>				
						</td>
						<td width="13%" id="gaur1" style="display: none">
							<html:select property="gaurrelationship" styleId="gaurrelationship" disabled="true" 
								styleClass="text" onchange="gaursan1();gaursan2();gaursan();gaurcleanUp();customerLov();"
								value="${leadDetails[0].gaurrelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="gauraddressId" styleClass="text" styleId="gauraddressId"  value="" />
		<html:hidden property="gaurlbxCustomerId" styleClass="text" styleId="gaurlbxCustomerId"  value="${leadDetails[0].gaurlbxCustomerId}" />
	
		<html:button property="gaurcustomerIdButton" styleId="gaurcustomerIdButton" onclick="closeLovCallonLov1();openLOVCommon(192044,'customerForm','gaurlbxCustomerId','','','','','copyGaurCustomerDetails','gaurcustomerName','gauraddress1','gaurcustomerType');gaurcusttype();" value=" " styleClass="lovbutton"></html:button>
		
							
						</td>
					</logic:present>
					<logic:present name="leadNew">
						<td width="13%">
							<html:select property="gaurrelationship" styleId="gaurrelationship" disabled="true" 
								styleClass="text" onchange="gaursan1();gaursan2();gaursan();gaurcleanUp();customerLov();"
								value="${leadDetails[0].gaurrelationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="gauraddressId" styleClass="text" styleId="gauraddressId"  value="" />
		<html:hidden property="gaurlbxCustomerId" styleClass="text" styleId="gaurlbxCustomerId"  value="${leadDetails[0].gaurlbxCustomerId}" />
	
		<html:button property="gaurcustomerIdButton" styleId="gaurcustomerIdButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(192044,'customerForm','gaurlbxCustomerId','','','','','copyGaurCustomerDetails','gaurcustomerName','gauraddress1','gaurcustomerType');gaurcusttype();" value=" " styleClass="lovbutton"></html:button>
		
							
						</td>
					</logic:present>

					<td width="13%">
						<bean:message key="lbl.Type" />
						<span><font style="color: red;"> *</font> </span>
					</td>
					<td width="13%">
						<html:select property="gaurcustomerType1" styleClass="text"
							styleId="gaurcustomerType1" value="${leadDetails[0].gaurcustomerType}" disabled="true" 
							onchange="gaurupdateCust(this.value);gaurcusttype();" >
							<html:option value="">--Select--</html:option>
							<html:option value="I">INDIVIDUAL</html:option>
							<html:option value="C">CORPORATE</html:option>
						</html:select>
						<html:hidden property="gaurcustomerType" styleId="gaurcustomerType" value="${leadDetails[0].gaurcustomerType}" />
					</td></tr>
					<tr>
					<td width="13%">

						<bean:message key="constitution" />
						<font color="red">*</font>
					</td>
					<td><div id="gaurIndividualconstitution" style="display:none">
						<html:select property="gaurindconstitution" styleId="gaurindconstitution"
							value="${leadDetails[0].gaurcontitutionCode}" styleClass="text" disabled="true" 
							onchange="indConSubprofile('gaurindconstitution');appendZero();">
							<html:option value="">--Select--</html:option>
							<logic:present name="indconstitutionlist">
								<logic:notEmpty name="indconstitutionlist">
									<html:optionsCollection name="indconstitutionlist"
										label="constitution" value="contitutionCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						</div>
						<div id="gaurcorporateconstitution" style="display:none">
						<html:select property="gaurcorconstitution" styleId="gaurcorconstitution"
							value="${leadDetails[0].gaurcontitutionCode}" styleClass="text" disabled="true" 
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
						<div id="gaurconstitution">
						<html:select property="gaurconstitution" styleId="gaurconstitution" disabled="true" 
							value="${leadDetails[0].gaurcontitutionCode}" styleClass="text"
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
					<!-- Saurabh Code Start here for Individual Sub Profile -->
						
		<%-- <td><bean:message key="lbl.IndConSubprofile" /><font color="red">*</font></td>
		<html:hidden property="gaurconSubprofileId" styleClass="text" styleId="gaurconSubprofileId" value="${leadDetails[0].guarconSubprofile}" />
		<td><html:select property="guarconSubprofile" value="${leadDetails[0].guarconSubprofile}" styleClass="text" styleId="guarconSubprofile">
				<logic:present name="gaurindConstSubprofileList">
					<logic:notEmpty name="gaurindConstSubprofileList">
					<html:optionsCollection name="gaurindConstSubprofileList" label="indConstSubprofile" value="indConstSubprofileCode" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
		  </td> --%>
		<!-- End By saurabh -->	

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
							<html:text property="gaurcustomerName" styleClass="text" disabled="true" 
								styleId="gaurcustomerName" maxlength="250"
								value="${leadDetails[0].gaurcustomerName}"
								onblur="caseChange('leadCapturingDetails','gaurcustomerName');" />
						</td>
				
				
					
				
					
			
					<td width="13%">
						<bean:message key="lbl.groupName" />
						<font color="red">*</font>
					</td>
					<td width="13%">
					 
					<html:select property="gaurgroupType" styleId="gaurgroupType"  disabled="true" onchange="gaurgroupselect();" styleClass="text" value="${leadDetails[0].gaurgroupType}"> 
						<html:option value="">--Select--</html:option> 
						<html:option value="N">New</html:option> 
						<html:option value="E">Existing</html:option> 
					</html:select>
					<logic:notPresent name="leadDetails">
					<div id="gaurgroupLov" style="display:none">
						<input type="text" id="gaurgroupName" name="gaurgroupName"  disabled="true" class="text" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton" disabled="true"  styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
				
					<div id="gaurgroupText" style="display:none">
						<input type="text" id="gaurgroupName1" name="gaurgroupName1" class="text" disabled="true"  value="${leadDetails[0].gaurgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:notPresent>
					
					<logic:present name="leadDetails">
					<logic:iterate name="leadDetails" id="list">
					<logic:equal property="gaurgroupType" name="list" value="N">
					<div id="ggaurroupLov" style="display:none">
						<input type="text" id="gaurgroupName" name="gaurgroupName" disabled="true"  class="text" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton"  disabled="true" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
					<div id="gaurgroupText" >
						<input type="text" id="gaurgroupName1" name="gaurgroupName1" class="text" disabled="true"  value="${leadDetails[0].gaurgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
						
					</logic:equal>
					<logic:equal property="gaurgroupType" name="list" value="E">
										
					<div id="gaurgroupLov" >
						<input type="text" id="gaurgroupName" name="gaurgroupName" class="text"  disabled="true" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton"  disabled="true" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
				
					<div id="gaurgroupText" style="display:none">
						<input type="text" id="gaurgroupName1" name="gaurgroupName1"  disabled="true" class="text" value="${leadDetails[0].gaurgroupDesc}" maxlength="50"  tabindex="-1" />
					
					</div>
					
					</logic:equal>
					<logic:equal property="gaurgroupType" name="list" value="">
										
					<div id="gaurgroupLov" style="display: none;">
						<input type="text" id="gaurgroupName" name="gaurgroupName"  disabled="true" class="text" value="${leadDetails[0].gaurgroupName}" readonly="readonly"  tabindex="-1" />
						<html:button property="lovButton" value=" " styleClass="lovbutton"  disabled="true" styleId="groupButton" onclick="closeLovCallonLov1();openLOVCommon(19251,'corporateDetailForm','gaurgroupName','','', '','','','gaurhGroupId');" />
						<html:hidden property="gaurhGroupId" styleId="gaurhGroupId"styleClass="text" value="${leadDetails[0].gaurhGroupId}" />
					</div>
				
					<div id="gaurgroupText" style="display:none">
						<input type="text" id="gaurgroupName1" name="gaurgroupName1"  disabled="true" class="text" value="${leadDetails[0].gaurgroupDesc}" maxlength="50"  tabindex="-1" />
					
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
						<html:select property="gaurbusinessSegment" styleId="gaurbusinessSegment1" disabled="true" 
							value="${leadDetails[0].gaurbusinessSegment}" styleClass="text">
							<html:option value="">--Select--</html:option>
							<logic:present name="businessSegmentList">
								<logic:notEmpty name="businessSegmentList">
									<html:optionsCollection name="businessSegmentList"
										label="businessSegmentDesc" value="businessSegmentCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						<html:hidden property="gaurbusinessSegment" styleClass="text" styleId="gaurbusinessSegment" value="${leadDetails[0].gaurbusinessSegment}" />
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
							onclick="closeLovCallonLov1();openLOVCommon(19253,'leadCapturingDetails','gaurlbxIndustry','','', '','','lovGaurIndustryLead','gaurindustry')"
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
						<html:button property="subIndustryButton" disabled="true" 
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
						<html:text property="gaurcustPan" maxlength="10" styleClass="text" disabled="true" 
							styleId="gaurcustPan" value="${leadDetails[0].gaurcustPan}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('gaurcustPan');" />
					</td>
					
					<%-- <td >
						<bean:message key="lbl.turnover" />
				</td>
					<td >
						<html:text property="gaurturnOver" styleClass="text"
							styleId="gaurturnOver" value="${leadDetails[0].gaurturnOver}"
							maxlength="22" style="text-align:right; "
							onkeypress="return numbersonly(event, id, 18)"
							onblur="formatNumber(this.value, id);"
							onkeyup="checkNumber(this.value, event, 18, id);"
							onfocus="keyUpNumber(this.value, event, 18, id);" />
					</td> --%>
					</tr>
	

</table>

</fieldset>
</div>


<div id='gaurindividualfield'">
<fieldset>
			<legend>
				<bean:message key="lbl.g_individual.details" />
			</legend>
			<table width="100%">
			<tr>
	<td width="13%">
							<bean:message key="lbl.fname" />
							<font color="red">*</font>
						</td>
						<td width="13%">
							<html:text property="gaurfirstName" styleId="gaurfirstName" disabled="true" 
								styleClass="text" value="${leadDetails[0].gaurfirstName}"
								onchange="return upperMe('gaurfirstName');" maxlength="50" />
						</td>
					<td width="13%">
						<bean:message key="individual.middle" />
						<!-- <font color="red">*</font> -->
					</td>
					<td width="13%">
						<html:text property="gaurmiddleName" styleId="gaurmiddleName" disabled="true" 
							styleClass="text" value="${leadDetails[0].gaurmiddleName}"
							onchange="return upperMe('gaurmiddleName');" maxlength="50" />
					</td>
					</tr>
					<tr>
					<td width="13%">
						<bean:message key="individual.last" />
						<font color="red">*</font>
					</td>
					<td width="13%">
						<html:text property="gaurlastName" styleId="gaurlastName" disabled="true" 
							styleClass="text" value="${leadDetails[0].gaurlastName}"
							onchange="return upperMe('gaurlastName');" maxlength="50" />
					</td>
					</tr>
				<tr>
					<td>
						<bean:message key="individual.birthdate" />
						<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
					</td>
					
				
					<td>
						<html:text property="gaurcustDOB"  disabled="true" onchange="checkDate('gaurcustDOB');gaurcheckcustDOB('gaurcustDOB');"
							styleId="gaurcustDOB" styleClass="text"
							value="${leadDetails[0].gaurcustDOB}" />
					</td>
					<td>
						<bean:message key="fatherHusband" /><font color="red">*</font>
					</td>
					<td>
						<html:text property="gaurfatherName"
							styleId="gaurfatherName" styleClass="text"  disabled="true" onblur="caseChange('leadCapturingDetails','gaurfatherName');"
							value="${leadDetails[0].gaurfatherName}"  maxlength="50"/>
					</td>
					<input type="hidden" name="sysDate" id="sysDate" value="${requestScope.sysDate}" />
					<input type="hidden" name="approve" id="approve" value="N" />
				
					
		
			</tr>
			<!-- Sanjog Changes Start Here -->
			<tr>
					
					<td><bean:message key="lbl.gender" /><font color="red">*</font></td>
          <td><html:select property="gaurgenderIndividual" value="${leadDetails[0].gaurgenderIndividual}"  disabled="true" styleClass="text" styleId="gaurgenderIndividual">
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
						<html:text property="gaurdlNumber"
							styleId="gaurdlNumber" styleClass="text" disabled="true" 
							value="${leadDetails[0].gaurdlNumber}" maxlength="20"/>
					</td>
								
		
			</tr>
			<tr>
					<td>
						<bean:message key="voterId" />
					</td>				
				
					<td>
						<html:text property="gaurvoterId"
							styleId="gaurvoterId" styleClass="text" disabled="true" 
							value="${leadDetails[0].gaurvoterId}" maxlength="20"/>
					</td>
					
					<td>
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="gaurcustPanInd" maxlength="10" styleClass="text" disabled="true" 
							styleId="gaurcustPanInd" value="${leadDetails[0].gaurcustPanInd}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('gaurcustPanInd');" />
					</td>
					</tr>
					<tr>
					<td>
						<bean:message key="passport" />
					</td>				
				
					<td>
						<html:text property="gaurpassport" disabled="true" 
							styleId="gaurpassport" styleClass="text"
							value="${leadDetails[0].gaurpassport}" maxlength="20"/>
					</td>
					<td><bean:message key="aadhaar" /></td>
          <td><html:text property="gauraadhaar" styleClass="text" styleId="gauraadhaar" disabled="true"   onkeypress="return numericOnly(event,12);" value="${leadDetails[0].gauraadhaar}" maxlength="12"/></td>
					
					</tr>
					
		</table>		
	</fieldset>
</div>

		<fieldset>
			<legend>
				<bean:message key="lbl.g_commDetails" />
			</legend>
			<table width="100%">
<tr>
<td><bean:message key="lbl.CopyAdd" /></td>
 <td><input type="checkbox" name="copyAppGaur" id="copyAppGaur"  disabled="true" onclick="copyApplicantToGaurAdd();" /></td>
 
 <%-- <td><bean:message key="lbl.CopyCoAppAdd" /></td>
 <td><input type="checkbox" name="copyCoAppGaur" id="copyCoAppGaur"  disabled="true" onclick="copyCoApplicantToGaurAdd();" /></td> --%>
</tr>

				<%-- <tr id="contactPersonDesignation" style="display: none;">
				<td>
						<bean:message key="lbl.contactPerson" />
					</td>
					<td>
						<html:text property="contactPerson" styleClass="text"
							styleId="contactPerson" maxlength="50"
							value="${leadDetails[0].contactPerson}"
							onblur="caseChange('leadCapturingDetails','contactPerson');" />
					</td>
							<td>
						<bean:message key="lbl.persondesignation" />
					</td>
					<td><html:text property="personDesignation" styleClass="text" styleId="personDesignation" maxlength="50" value="${leadDetails[0].personDesignation}" onblur="caseChange('leadCapturingDetails','personDesignation');" />
					</td>
					

			</tr> --%>
				<tr>	
					<td>
						<bean:message key="address.type" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:select property="gauraddresstype" styleClass="text" disabled="true" 
							styleId="gauraddresstype1" value="${leadDetails[0].gauraddresstype}">
							<html:option value="">--Select--</html:option>
							<html:optionsCollection name="addresstypeList"
								value="addresstypeid" label="addresstypename" />
						</html:select>
						<html:hidden property="gauraddresstype" styleClass="text" styleId="gauraddresstype" value="${leadDetails[0].gauraddresstype}" />
					</td>
				
					<td width="13%">
						<bean:message key="lbl.addressLine1" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="gauraddress1" styleClass="text" disabled="true" 
							styleId="gauraddress1" maxlength="50"
							value="${leadDetails[0].gauraddress1}"
							onblur="caseChange('leadCapturingDetails','gauraddress1');" />
					</td>

</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.two" />
					</td>
					<td width="13%">
						<html:text property="gauraddress2" styleClass="text" disabled="true" 
							styleId="gauraddress2" maxlength="50"
							value="${leadDetails[0].gauraddress2}"
							onblur="caseChange('leadCapturingDetails','gauraddress2');" />
					</td>
					<td width="13%">
						<bean:message key="lbl.three" />
					</td>
					<td width="13%">
						<html:text property="gauraddress3" styleClass="text" disabled="true" 
							styleId="gauraddress3" maxlength="50"
							value="${leadDetails[0].gauraddress3}"
							onblur="caseChange('leadCapturingDetails','gauraddress3');" />
					</td>
</tr>
	
	<!-- pooja code starts for Guarantor PinCode -->
		<logic:present name="pincodeFlag">
<logic:equal value="N" name="pincodeFlag">
 <html:hidden property="pincodeFlag" styleId="pincodeFlag" styleClass="text" value="N"/>
<tr> 

		
		<td  width="13%">
						<bean:message key="lbl.country" /><span><font style="color: red;"> *</font> 
						 </span>
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td  width="13%">
						<logic:present name="leadDetails">
							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text" disabled="true" 
								tabindex="-1"  value="${leadDetails[0].gaurcountry}" />
							<input type="button" class="lovbutton" id="countryButton"  disabled="true" 
								onclick="openLOVCommon(192069,'leadCapturingDetails','gaurtxtCountryCode','','','','','gaurclearCountryLovChild','gaurcountry')"
								value="" name="countryButton" />
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${leadDetails[0].gaurtxtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">

							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text" disabled="true" 
								tabindex="-1" 
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton" disabled="true" 
								onclick="openLOVCommon(192069,'leadCapturingDetails','gaurtxtCountryCode','','','','','gaurclearCountryLovChild','gaurcountry')"
								value="" name="countryButton"/>
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${defaultcountry[0].gaurtxtCountryCode}" />
						</logic:notPresent>

					</td>
<td width="13%">
						<bean:message key="lbl.state" /><span><font style="color: red;"> *</font> 
						 </span>
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td width="13%">
						<html:text property="gaurstate" styleId="gaurstate" styleClass="text" disabled="true" 
							tabindex="-1" readonly="true" value="${leadDetails[0].gaurstate}" />
						<input type="button" class="lovbutton" id="stateButton" disabled="true" 
							onclick="openLOVCommon(192071,'leadCapturingDetails','gaurstate','gaurcountry','gaurtxtStateCode', 'gaurtxtCountryCode','Please select country first!!!','gaurclearStateLovChild','gaurtxtStateCode')"
							value="" name="stateButton"/>
							<input type="hidden" name="gaurtxtStateCode" id="gaurtxtStateCode"
								value="${leadDetails[0].gaurtxtStateCode}" />
					</td>	
	
</tr>

<tr>


<td width="13%">
						<bean:message key="lbl.dist" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="gaurdist" styleId="gaurdist" styleClass="text" disabled="true" 
							tabindex="-1" readonly="true" value="${leadDetails[0].gaurdist}" />
						<input type="button" class="lovbutton" id="distButton" disabled="true" 
							onclick="openLOVCommon(192073,'leadCapturingDetails','gaurdist','gaurstate','gaurtxtDistCode', 'gaurtxtStateCode','Please select state first!!!','gaurclearDistrictLovChild','gaurtxtDistCode')"
							value=" " name="distButton"/>
							<input type="hidden" name="gaurtxtDistCode" id="gaurtxtDistCode"
								value="${leadDetails[0].gaurtxtDistCode}" />
					</td>
					
				<td width="13%">
						<bean:message key="address.Tahsil" />
					</td>
					<td>
						<input type="text" name="gaurtahsilDesc" id="gaurtahsilDesc"  disabled="true"  value="${leadDetails[0].gaurtahsil}" />
						<html:hidden property="gaurtahsil" styleId="gaurtahsil" styleClass="text" value="${leadDetails[0].gaurtahsilDesc}"/>
						<html:button property="tahsilButton" styleId="tahsilButton"  disabled="true" onclick="openLOVCommon(495,'leadCapturingDetails','gaurtahsilDesc','gaurdist','gaurtahsilDesc', 'gaurtxtDistCode','Please select District first','','gaurtahsil');" value=" " styleClass="lovbutton"> </html:button>
					</td>
					
					
</tr>


				<tr>
				<td width="13%">
						<bean:message key="lbl.pincode" /><span><font style="color: red;"> *</font> 
						 </span>
						<!-- <span><font style="color: red;"> *</font> -->
						<!-- </span> -->
		</td>
					<td width="13%">
						<html:text property="gaurpincode" styleClass="text" styleId="gaurpincode" disabled="true" 
							maxlength="6" value="${leadDetails[0].gaurpincode}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,6);" />
					</td>
				
				
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="gaurphoneOff" styleClass="text"
							styleId="gaurphoneOff" maxlength="10" disabled="true" 
							value="${leadDetails[0].gaurphoneOff}"
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
			<td><html:text property="gaurpincode" styleId="gaurpincode" size="20" styleClass="text"  disabled="true"  value="${leadDetails[0].gaurpincode}" tabindex="-1"></html:text>
            <html:hidden property="gaurlbxPincode" styleId="gaurlbxPincode" styleClass="text" value=""/>
 			<html:button property="pincodeButton" styleId="pincodeButton"  disabled="true" onclick="openLOVCommon(192026,'leadCapturingDetails','gaurpincode','','', '','','getGaurCountryStateDistrictTahsilValue','gaurtxnTahsilHID','hcommon');closeLovCallonLov('');" value=" " styleClass="lovbutton"> </html:button>
		</td>
						
			
	<td><bean:message key="address.Tahsil" /></td>	
			<td id="gaurlov2"> 
            	<div id="gaurtahsildetail">
            		<html:text property="gaurtahsil" styleId="gaurtahsil" size="20" styleClass="text"  disabled="true"  value="${leadDetails[0].gaurtahsilDesc}" ></html:text>
                	<html:hidden property="gaurtxnTahsilHID" styleId="gaurtxnTahsilHID" styleClass="text" value="${leadDetails[0].gaurtahsil}"/>
   				</div>
   			</td>
   			
   			<td id="gaurlov4" style="display: none"> 
            	<div id="gaurtahsildetail">
            		<html:text property="gaurtahsil4" styleId="gaurtahsil4" size="20" styleClass="text"   disabled="true" value="${leadDetails[0].gaurtahsil}" ></html:text>
                	<html:hidden property="gaurtxnTahsilHID" styleId="gaurtxnTahsilHID" styleClass="text" value="${leadDetails[0].gaurtxnTahsilHID}"/>
   				</div>
   			</td>	
   	
	
				
   			
   			
	</tr>
	
	
	<tr id="gaurlov5"> 
			<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font> -->
						</span></td>
          	<td >
          		<div id="gaurcityID">
          			<html:text property="gaurdist" styleId="gaurdist" size="20" styleClass="text"  disabled="true"  value="${leadDetails[0].gaurdist}" ></html:text>
          			<html:hidden property="gaurtxtDistCode" styleId="gaurtxtDistCode" styleClass="text" value="${leadDetails[0].gaurtxtDistCode}"></html:hidden>
   				</div>
   			</td>
			
   			<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="gaurstatedetail">
                	<html:text property="gaurstate" styleId="gaurstate" styleClass="text" size="20" disabled="true"  value="${leadDetails[0].gaurstate}" readonly="true" ></html:text>
   					<html:hidden property="gaurtxtStateCode" styleId="gaurtxtStateCode" styleClass="text" value=" ${leadDetails[0].gaurtxtStateCode}"></html:hidden>
    				<html:hidden property="gaurtahsilDesc" styleId="gaurtahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton" style="display: none"  disabled="true" onclick="closeLovCallonLov('gaurcountry');openLOVCommon(192071,'leadCapturingDetails','gaurstate','gaurcountry','gaurtxtStateCode', 'gaurtxtStateCode','Please select country first','gaurclearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	          	</div>
			</td>
		   		

	</tr>
	
	<tr id="gaurlov6" style="display: none">
	<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
          	<td>
          		<div id="gaurcityID">
          			<html:text property="gaurdist4" styleId="gaurdist4" size="20" styleClass="text"  disabled="true"  value="${leadDetails[0].gaurdist}" ></html:text>
          			<html:hidden property="gaurtxtDistCode" styleId="gaurtxtDistCode" styleClass="text" value="${leadDetails[0].gaurtxtDistCode}"></html:hidden>
  					<html:button property="distButton" styleId="districtButton" onclick="closeLovCallonLov('gaurstate6');openLOVCommon(20,'leadCapturingDetails','gaurdist4','gaurstate6','gaurtxtDistCode', 'gaurtxtStateCode','Please select state first','gaurclearTahsilLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   				</div>
   			</td>
	
	
	<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="gaurstatedetail">
                	<html:text property="gaurstate6" styleId="gaurstate6" styleClass="text" size="20" value="${leadDetails[0].gaurstate}" readonly="true" ></html:text>
   					<html:hidden property="gaurtxtStateCode" styleId="gaurtxtStateCode" styleClass="text" value=" ${leadDetails[0].gaurtxtStateCode}"></html:hidden>
    				<html:hidden property="gaurtahsilDesc" styleId="gaurtahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton"   disabled="true" onclick="closeLovCallonLov('gaurcountry');openLOVCommon(192071,'leadCapturingDetails','gaurstate6','gaurtxtCountryCode','gaurtxtStateCode', 'gaurtxtCountryCode','Please select country first','gaurclearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
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
					<td id="gaurlov1" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text"
								  disabled="true"  value="${leadDetails[0].gaurcountry}" />
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${leadDetails[0].gaurtxtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text"
								 disabled="true" 	value="${defaultcountry[0].defaultcountryname}" />
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
					
					
					<td id="gaurlov3" style="display: none" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text"
								  disabled="true"  value="${leadDetails[0].gaurcountry}" />
							<input type="button" class="lovbutton" id="countryButton" disabled="true" 
								onclick="openLOVCommon(192069,'leadCapturingDetails','gaurcountry','','','','','gaurclearCountryLovChild','gaurtxtCountryCode')"
								value="" name="countryButton" />
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${leadDetails[0].gaurtxtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="gaurcountry" styleId="gaurcountry" styleClass="text"
								  disabled="true" 
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton" disabled="true" 
								onclick="openLOVCommon(192069,'leadCapturingDetails','gaurcountry','','','','','gaurclearCountryLovChild','gaurtxtCountryCode')"
								value="" name="countryButton"/>
								<input type="hidden" name="gaurtxtCountryCode" id="gaurtxtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
						
		
				
					
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="gaurphoneOff" styleClass="text" disabled="true" 
							styleId="gaurphoneOff" maxlength="10"
							value="${leadDetails[0].gaurphoneOff}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,10);" />
					</td>
	</tr>
	</logic:equal>
	</logic:present>
	<!-- pooja code end for Guarantor PinCode -->
				
				<tr>
					<td width="13%">
						<bean:message key="lbl.email" />
					</td>
					<td width="13%">
						<html:text property="gauremail" styleClass="text" styleId="gauremail" disabled="true" 
							maxlength="100" value="${leadDetails[0].gauremail}"/>
					</td>
					<td width="13%">
						<bean:message key="lbl.landmark" />
					</td>
					<td width="13%">
						<html:text property="gaurlandmark" styleClass="text" disabled="true" 
							styleId="gaurlandmark" maxlength="25"
							value="${leadDetails[0].gaurlandmark}"
							onblur="caseChange('leadCapturingDetails','gaurlandmark');" />
					</td>
	</tr>

			</table>
		</fieldset>
		
		<!-- grid details -->

	</logic:present>
<fieldset>	
	
		 <legend><bean:message key="lbl.guar_customer_detail"/></legend>  

  
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
							<a href="#" id="anchor0" onclick="return fetchCustDtl('${coappDetails.custId }','${leadDetails[0].leadId}','GUARANTOR');">
							
							${coappDetails.custId }</a></td>
							
	      			        <td  >${coappDetails.customerName }
	      			       </td>
	      			        
							<td >${coappDetails.gaurcustomerType }
							</td>
							<td >${coappDetails.gaurrelationship }</td>	
							
			       </tr>				
			 
	</logic:iterate>
	</logic:present> 
	
		</table>
		</td></tr>
</table>
 
 <button type="button" name="delete" class="topformbutton2" id="delete" title="Alt+D" accesskey="D" value="Delet" onclick="deleteCustomer('N',${leadDetails[0].leadId},'GUARANTOR');"><bean:message key="button.delete" /></button>

        
</fieldset>

<!-- grid details end -->
<logic:equal name="custType" value="C">

	<script type="text/javascript">
	gaurupdateCust('C');gaurcusttype();
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
				alert('<%=request.getAttribute("nameExist")%>');
				
			 
</script>	
</logic:present>
<logic:present name="gaurnameExist">
<script type="text/javascript">
var gaurnameExist = confirm('<%=request.getAttribute("gaurnameExist")%>');

if(gaurnameExist)
{
	var gaurlbxCustomerId=document.getElementById("gaurlbxCustomerId").value;
	var gaurcustPanInd=document.getElementById("gaurcustPanInd").value;
	var gauraadhaar=document.getElementById("gauraadhaar").value;
	var leadId=document.getElementById("leadId").value;
	var id="Save";
	var gaurnameExist="G";
	if(document.getElementById("updateCustId").value){
		var updateCustId=document.getElementById("updateCustId").value;
		document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealGurSave.do?method=leadEntrySave&saveForward="+id+"&gaurlbxCustomerId="+gaurlbxCustomerId+"&gaurcustPanInd="+gaurcustPanInd+"&gauraadhaar="+gauraadhaar+"&customer=guar&leadId="+leadId+"&updateCustId="+updateCustId+"&gaurnameExist="+gaurnameExist;
		document.getElementById('leadCapturingDetails').submit();
	}else{
		document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealGurSave.do?method=saveNewLead&saveForward="+id+"&gaurlbxCustomerId="+gaurlbxCustomerId+"&gaurcustPanInd="+gaurcustPanInd+"&gauraadhaar="+gauraadhaar+"&customer=guar&leadId="+leadId+"&gaurnameExist="+gaurnameExist;
		document.getElementById('leadCapturingDetails').submit();
	}

}
				alert('<%=request.getAttribute("gaurnameExist")%>');
				
			 
</script>	
</logic:present>		
	</html:form>
	<div align="center" class="opacity" style="display: none"
		id="processingImage"></div>

</body>
</html:html>
