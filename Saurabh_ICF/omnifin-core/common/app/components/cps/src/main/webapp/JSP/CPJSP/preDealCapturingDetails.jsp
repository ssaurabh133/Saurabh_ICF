<%--     Created By Sanjog     --%>

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
	
	
	function san2(){
		
		document.getElementById("customerType1").value="";
		
		
	}
	
	function san(){
		if(document.getElementById("relationship").value == "New")
		{
			if(document.getElementById("cibilDone").value != "cibilDone"){
			document.getElementById("customerType1").removeAttribute("disabled");
			document.getElementById("addresstype1").removeAttribute("disabled","true");
			if(document.getElementById("firstName")){
				document.getElementById("firstName").removeAttribute("disabled","true");
			}
			}
			//document.getElementById("customerType").value = 'I';
			//document.getElementById("indconstitution").removeAttribute("disabled");
			//document.getElementById("corconstitution").removeAttribute("disabled");
			//document.getElementById("relationshipSince").setAttribute("disabled","true");
			document.getElementById("custPanInd").removeAttribute("disabled","true");
			//document.getElementById("aadhaar").removeAttribute("disabled","true");
			if(document.getElementById("customerIdButton") != null){
				document.getElementById("customerIdButton").style.display='none';
			}
			/* if(document.getElementById("customerType1").value=="C"){
				document.getElementById("appconSubprofile").value="";
				document.getElementById("appconSubprofile").setAttribute("disabled","true");				
			} */
		}
		
		else {
			//alert("Existing");
			document.getElementById("customerType1").setAttribute("disabled","true");
			document.getElementById("addresstype1").setAttribute("disabled","true");
			if(document.getElementById("firstName")){
				document.getElementById("firstName").setAttribute("disabled","true");
				}
			//document.getElementById("indconstitution").setAttribute("disabled","true");
			//document.getElementById("corconstitution").setAttribute("disabled","true");
			//document.getElementById("relationshipSince").removeAttribute("disabled");
			/* if(document.getElementById("customerType1").value=="C"){
				document.getElementById("appconSubprofile").value="";
				document.getElementById("appconSubprofile").setAttribute("disabled","true");				
			}
			 else{
				document.getElementById("appconSubprofile").removeAttribute("disabled","true");
			 } */
			//alert("Existing11");
			if(document.getElementById("custPanInd").value!=""){
			document.getElementById("custPanInd").setAttribute("disabled","true");
			}
			else
				{
				document.getElementById("custPanInd").removeAttribute("disabled","true");
				}
			/* if(document.getElementById("aadhaar").value!=""){
			document.getElementById("aadhaar").setAttribute("disabled","true");
			}
			else{
				document.getElementById("aadhaar").removeAttribute("disabled","true");
			} */
			if(document.getElementById("customerIdButton") != null){
				document.getElementById("customerIdButton").style.display='';	
				document.getElementById("customerIdButton").focus();
			}
			return false;
		}
	
	}
	function coAppsan2(){
		
	 
		if(document.getElementById("coApprelationship").value == "New")
		{
			document.getElementById("coAppcustomerType1").removeAttribute("disabled");
			
		}else{
			
			document.getElementById("coAppcustomerType1").setAttribute("disabled");
		}
	
		document.getElementById("coAppcustomerType1").value="";
		
	}
	
	
	
	function gaursan2(){
		
		if(document.getElementById("gaurrelationship").value == "New")
		{
			document.getElementById("gaurcustomerType1").removeAttribute("disabled");
			
		}else{
			
			document.getElementById("gaurcustomerType1").setAttribute("disabled");
		}
		document.getElementById("gaurcustomerType1").value="";
		
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
	/* function subProfileEnableDisable()
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
	} */
	/* function subProfileEnaDis()
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
	} */
	 
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

<body onload="enableAnchor();showHideDescLovOnLoad();hideorshow(document.getElementById('leadGenerator').value);san();custtype();">


	<html:form action="/preDealCapturingBehindAction"
		styleId="leadCapturingDetails" method="post">
		<input type="hidden" name="leadcapt" id="leadcapt" />
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
     <input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
     <input type="hidden" id="emailMandatoryFlag" name="emailMandatoryFlag" value='${emailMandatoryFlag}'/>		
     <input type="hidden" id="minTenure" name="minTenure" value='${minTenure}'/>
	<input type="hidden" id="maxTenure" name="maxTenure" value='${maxTenure}'/>   
	<input type="hidden" id="cibilDone" name="cibilDone" value='${cibilDone}'/>   
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
		
		<!-- /////////////////////////////////DIFFERENT START/////////////////////////////////////////// 
		<div id="gridList"><!-- Calling Data Through AJAX --</div>-->

		<logic:present name="leadNew">
		
			<fieldset id="rm">
			
				<legend>
					<bean:message key="lbl.preDealGenDetail" />
				</legend>
				<table width="100%">
						<tr>
						<td width="13%">
							<bean:message key="lbl.leadgeneratorBy" />
							<span><font style="color: red;"> *</font>
							</span>
							<html:hidden property="leadId" styleId="leadId"
								value="${leadDetails[0].leadId}" />
						</td>
						<td width="13%"> 
							<html:select styleId="leadGenerator" property="leadGeneratorSelect" disabled="true"
								styleClass="text" value="${leadRMDetails[0].leadGenerator}"
								onchange="return leadempty(value);" >

								<html:option value="">---Select---</html:option>
								<html:option value="RM">
								RM / SALES EXEC
							</html:option>
							<html:option value="RO">
								RM / SALES EXEC
							</html:option>
								<html:option value="VENDOR">
								DEALER
							</html:option>
								<html:option value="BRANCH">
								TELECALLER
							</html:option>
							<html:option value="OTHERS">
								OTHERS
							</html:option>
								</html:select>
								<html:hidden property="leadGenerator" value="${leadRMDetails[0].leadGenerator}"/>

						</td>
						<td width="13%">
							<bean:message key="lbl.cust_ref" />
						</td>
						<td width="13%">
							<html:text property="dealNo" styleId="dealNo" styleClass="text"
								value="${leadDetails[0].dealNo}" readonly="true" tabindex="-1"
								style="text-align:right;" />
						</td>
					</tr>
					<tr>
						<td width="13%">
							<bean:message key="lbl.rmcode" />
							<span><font style="color: red;"> *</font>
							</span>
						</td>
						<td width="13%">

							<html:text property="rmCode1" styleClass="text" styleId="rmCode1"
								readonly="true" tabindex="-1" maxlength="10"
								value="${leadNew[0].rmCode1}" />
							<input type="button" class="lovbutton" id="rmButton"
								onClick="openLOVCommon(211,'leadCapturingDetails','rmCode1','','', '','','','rmName1','contactnorm','leadgenzonerm')"
								value=" " name="dealButton"/>
								<input type="hidden" name="lbxUserId" id="lbxUserId" />
						</td>
						<td width="13%">
							<bean:message key="lbl.rmname" />
							<span><font style="color: red;"> *</font>
							</span>
						</td>
						<td width="13%">
							<html:text property="rmName1" styleId="rmName1" styleClass="text"
								readonly="true" tabindex="-1" value="${leadNew[0].rmName1}" />
						</td>
					</tr>
					<tr>
						<td width="13%">
							<bean:message key="lbl.contactno" />
							<span><font style="color: red;"> *</font>
							</span>
						</td>
						<td width="13%">
							<html:text property="contactnorm" style="text-align:right;"
								styleClass="text" styleId="contactnorm" maxlength="10"
								value="${leadNew[0].contactnorm}" 
								onkeypress="return numericOnly(event,10);" />
						</td>
					</tr>
					<tr>
						<td width="13%">
							<bean:message key="lbl.branch" />
							<span><font style="color: red;"> *</font>
							</span>
						</td>
						<td width="13%">
							<html:text property="leadgenzonerm" styleClass="text"
								readonly="true" tabindex="-1" styleId="leadgenzonerm"
								maxlength="10" value="${leadNew[0].leadgenzonerm}" />
							<!--						<input type="hidden" class="lovbutton" id="dealButton" onClick="openLOVCommon(156,'leadCapturingDetails','leadgenzonerm','','', '','','','lbxRegionID')" value=" " tabindex="6" name="dealButton">-->
							<input type="hidden" name="lbxRegionID" id="lbxRegionID" value="" />
						</td>
						<td width="13%">
							<bean:message key="lbl.leadgencity" />
							<span><!-- <font style="color: red;"> *</font> -->
							</span>
						</td>
						<td width="13%">
							<html:text property="leadgencityrm" styleId="leadgencityrm"
								styleClass="text" value="${leadRMDetails[0].leadgencityrm}"
								onblur="caseChange('leadCapturingDetails','leadgencityrm');"
								maxlength="50" />
						</td>

					</tr>
<%-- <tr>
				<td width="13%">
						<bean:message key="lbl.sourceTypeDesc" />
					</td>
					<td width="13%">
					  	 <html:text property="sourceList" styleId="sourceList" styleClass="text" readonly="true" tabindex="-1" value="${leadDetails[0].sourceList}"></html:text>
						 <input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(535,'leadCapturingDetails','source','','','','','showHideDescLov','sourceList');" value=" " tabindex="1" name="dealButton" />
						 <html:hidden property="source" styleId="source" value="${leadDetails[0].source}"/>
					</td>	
					<!-- Nishant space ends -->
					<td width="13%">
						<bean:message key="lbl.source.desc" />
					</td>
					<!-- Nishant space starts -->
					<td width="13%">
						<input type="hidden" id="lbxDescription" name="lbxDescription"/>
						<html:text property="description" styleClass="text"	styleId="description" maxlength="100" value="${leadDetails[0].description}"	onblur="caseChange('leadCapturingDetails','description');"/>
						<html:button property="srcLOV" styleId="srcLOV" onclick="openDescLov();" value=" " styleClass="lovbutton"> </html:button>
					</td>
					<!-- Nishant space end -->
				</tr> --%>

				</table>
			</fieldset>

		</logic:present>


		<logic:notPresent name="leadNew">

			<fieldset id="rm">
				<legend>
					<bean:message key="lbl.preDealGenDetail" />
				</legend>
				<table width="100%">
<tr>
						<td width="13%">
							<bean:message key="lbl.leadgeneratorBy" />
						</td>
						<td width="13%">
							<html:hidden property="leadGenerator1" styleClass="text"
								styleId="leadGenerator1" value="${leadDetails[0].leadGenerator}" />
							<html:hidden styleId="leadGenerator" property="leadGenerator"
								styleClass="text" value="${leadDetails[0].leadGenerator1}"></html:hidden>
							<html:text styleId="leadGenerator" property="leadGenerator"
								styleClass="text" tabindex="-1" readonly="true"
								value="${leadDetails[0].leadGenerator}"
								onchange="return leadchange(value);"></html:text>

						</td>
						<td width="13%">
							<bean:message key="lbl.cust_ref" />
						</td>
						<td width="13%">
							<html:text property="dealNo" styleId="dealNo" styleClass="text"
								value="${leadDetails[0].dealNo}" tabindex="-2" readonly="true"
								style="text-align:right;" />
						</td>
					</tr>
					<tr>
						<td width="13%">
							<bean:message key="lbl.rmcode" />
							<span><font style="color: red;"> *</font>
							</span>
						</td>
						<td width="13%">
							<html:text property="rmCode1" styleClass="text" styleId="rmCode1"
								readonly="true" maxlength="10"
								value="${leadRMDetails[0].rmCode1}" tabindex="-1" />
							<input type="button" class="lovbutton" id="rmButton"
								onClick="openLOVCommon(211,'leadCapturingDetails','rmCode1','','', '','','','rmName1','contactnorm','leadgenzonerm')"
								value=" " name="dealButton">
								<input type="hidden" name="lbxUserId" id="lbxUserId" />
						</td>
						<td width="13%">
							<bean:message key="lbl.rmname" />
							<span><font style="color: red;"> *</font>
							</span>
						</td>
						<td width="13%">
							<html:text property="rmName1" styleId="rmName1" styleClass="text"
								readonly="true" value="${leadRMDetails[0].rmName1}"
								tabindex="-1" />
						</td>
					</tr>
					<tr>
						<td width="13%">
							<bean:message key="lbl.contactno" />
							<span><font style="color: red;"> *</font>
							</span>
						</td>
						<td width="13%">
							<html:text property="contactnorm" style="text-align:right;"
								styleClass="text" styleId="contactnorm" maxlength="10"
								value="${leadRMDetails[0].contactnorm}"
								onkeypress="return numericOnly(event,10);" />
						</td>
					</tr>
					<tr>
						<td width="13%">
							<bean:message key="lbl.branch" />
							<span><font style="color: red;"> *</font>
							</span>
						</td>
						<td width="13%">
							<html:text property="leadgenzonerm" styleClass="text"
								readonly="true" tabindex="-1" styleId="leadgenzonerm"
								maxlength="10" value="${leadRMDetails[0].leadgenzonerm}" />
							<input type="hidden" class="lovbutton" id="dealButton"
								onClick="openLOVCommon(156,'leadCapturingDetails','leadgenzonerm','','', '','','','lbxRegionID')"
								value=" " name="dealButton">
								<input type="hidden" name="lbxRegionID" id="lbxRegionID"
									value="" />
						</td>
						<td width="13%">
							<bean:message key="lbl.leadgencity" />
							<span><!-- <font style="color: red;"> *</font> -->
							</span>
						</td>
						<td width="13%">
							<html:text property="leadgencityrm" styleId="leadgencityrm"
								styleClass="text" value="${leadRMDetails[0].leadgencityrm}"
								onblur="caseChange('leadCapturingDetails','leadgencityrm');"
								maxlength="50" />
						</td>

					</tr>
<%-- <tr>
				<td width="13%">
						<bean:message key="lbl.sourceTypeDesc" />
					</td>
					<td width="13%">
					  	 <html:text property="sourceList" styleId="sourceList" styleClass="text" readonly="true" tabindex="-1" value="${leadDetails[0].sourceList}"></html:text>
						 <input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(535,'leadCapturingDetails','source','','','','','showHideDescLov','sourceList');" value=" " tabindex="1" name="dealButton" />
						 <html:hidden property="source" styleId="source" value="${leadDetails[0].source}"/>
					</td>	
					<!-- Nishant space ends -->
					<td width="13%">
						<bean:message key="lbl.source.desc" />
					</td>
					<!-- Nishant space starts -->
					<td width="13%">
						<input type="hidden" id="lbxDescription" name="lbxDescription"/>
						<html:text property="description" styleClass="text"	styleId="description" maxlength="100" value="${leadDetails[0].description}"	onblur="caseChange('leadCapturingDetails','description');"/>
						<html:button property="srcLOV" styleId="srcLOV" onclick="openDescLov();" value=" " styleClass="lovbutton"> </html:button>
					</td>
					<!-- Nishant space end -->
				</tr> --%>

				</table>
			</fieldset>

		</logic:notPresent>


		<fieldset id="vendor" style="display: none">
			<legend>
				<bean:message key="lbl.preDealGenDetail" />
			</legend>
			<table width="100%">

						<tr>
						<td width="13%">
							<bean:message key="lbl.leadgeneratorBy" />
							<span><font style="color: red;"> *</font>
							</span>
							<html:hidden property="leadId" styleId="leadId"
								value="${leadDetails[0].leadId}" />
						</td>
						<td width="13%"> 
							<html:select styleId="leadGenerator" property="leadGeneratorSelect" disabled="true"
								styleClass="text" value="BRANCH"
								onchange="return leadempty(value);" >

								<html:option value="">---Select---</html:option>
								<html:option value="RM">
								RM / SALES EXEC
							</html:option>
							<html:option value="RO">
								RM / SALES EXEC
							</html:option>
								<html:option value="VENDOR">
								DEALER
							</html:option>
								<html:option value="BRANCH">
								TELECALLER
							</html:option>
							<html:option value="OTHERS">
								OTHERS
							</html:option>
								</html:select>
								<html:hidden property="leadGenerator" value="${leadRMDetails[0].leadGenerator}"/>

						</td>
						<td width="13%">
							<bean:message key="lbl.cust_ref" />
						</td>
						<td width="13%">
							<html:text property="dealNo" styleId="dealNo" styleClass="text"
								value="${leadDetails[0].dealNo}" readonly="true" tabindex="-1"
								style="text-align:right;" />
						</td>
					</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.vendorcode" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="vendorCode1" styleClass="text"
							readonly="true" styleId="vendorCode1" maxlength="10"
							value="${leadRMDetails[0].vendorCode1}" />
						<input type="button" class="lovbutton" id="vendorButton" onclick="openLOVCommon(34,'leadCapturingDetails','vendorCode1','','', '','','','vendorName1')"
							value=" " name="vendorButton" />
							<input type="hidden" name="lbxvendorCode" id="lbxvendorCode" />
					</td>
					<td width="13%">
						<bean:message key="lbl.vendorname" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="vendorName1" styleId="vendorName1"
							styleClass="text" readonly="true" tabindex="-1"
							value="${leadRMDetails[0].vendorName1}" />
					</td>

				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.vendorhead" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="vendorHead1" styleId="vendorHead1"
							styleClass="text" readonly="true" value="${leadRMDetails[0].vendorHead1}"
							onblur="caseChange('leadCapturingDetails','vendorHead1');"
							maxlength="50" />
					</td>
					<td width="13%">
						<bean:message key="lbl.contactno" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">

						<html:text property="contactnovendor" style="text-align:right;"
							styleClass="text" styleId="contactnovendor1" maxlength="10"
							value="${leadRMDetails[0].contactnovendor}"
							onkeypress="return numericOnly(event,10);" />
					</td>
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.branch" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<logic:present name="leadRMDetails">
							<html:text property="leadgenzonevendor" styleClass="text"
								readonly="true" tabindex="-1" styleId="leadgenzonevendor"
								maxlength="10" value="${leadRMDetails[0].leadgenzonevendor}" />

						</logic:present>
						<logic:notPresent name="leadRMDetails">
							<html:text property="leadgenzonevendor" styleClass="text"
								readonly="true" tabindex="-1" styleId="leadgenzonevendor"
								maxlength="10" value="${sessionScope.userobject.branchName}" />
						</logic:notPresent>
						<input type="button" class="lovbutton"
							id="leadgenzonevendorButton"
							onclick="openLOVCommon(156,'leadCapturingDetails','lbxRegionID1','','', '','','','leadgenzonevendor')"
							value=" " name="dealButton" />
							<logic:present name="leadRMDetails">
								<input type="hidden" name="lbxRegionID1" id="lbxRegionID1"
									value="${leadRMDetails[0].lbxRegionID1}" />
							</logic:present>
							<logic:notPresent name="leadRMDetails">
								<input type="hidden" name="lbxRegionID1" id="lbxRegionID1"
									value="${sessionScope.userobject.branchId}" />
							</logic:notPresent>
					</td>
					<td width="13%">
						<bean:message key="lbl.leadgencity" />
						<span><!-- <font style="color: red;"> *</font> -->
						</span>
					</td>
					<td width="13%">
						<html:text property="leadgencityvendor"
							styleId="leadgencityvendor" styleClass="text"
							value="${leadRMDetails[0].leadgencityvendor}"
							maxlength="50"
							onblur="caseChange('leadCapturingDetails','leadgencityvendor');" />
					</td>
				</tr>
<%-- <tr>
				<td width="13%">
						<bean:message key="lbl.sourceTypeDesc" />
					</td>
					<td width="13%">
					  	 <html:text property="sourceList" styleId="sourceList" styleClass="text" readonly="true" tabindex="-1" value="${leadDetails[0].sourceList}"></html:text>
						 <input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(535,'leadCapturingDetails','source','','','','','showHideDescLov','sourceList');" value=" " tabindex="1" name="dealButton" />
						 <html:hidden property="source" styleId="source" value="${leadDetails[0].source}"/>
					</td>	
					<!-- Nishant space ends -->
					<td width="13%">
						<bean:message key="lbl.source.desc" />
					</td>
					<!-- Nishant space starts -->
					<td width="13%">
						<input type="hidden" id="lbxDescription" name="lbxDescription"/>
						<html:text property="description" styleClass="text"	styleId="description" maxlength="100" value="${leadDetails[0].description}"	onblur="caseChange('leadCapturingDetails','description');"/>
						<html:button property="srcLOV" styleId="srcLOV" onclick="openDescLov();" value=" " styleClass="lovbutton"> </html:button>
					</td>
					<!-- Nishant space end -->
				</tr> --%>
			</table>
		</fieldset>


		<fieldset id="branch" style="display: none">
			<legend>
				<bean:message key="lbl.preDealGenDetail" />
			</legend>
			<table width="100%">
						<tr>
						<td width="13%">
							<bean:message key="lbl.leadgeneratorBy" />
							<span><font style="color: red;"> *</font>
							</span>
							<html:hidden property="leadId" styleId="leadId"
								value="${leadDetails[0].leadId}" />
						</td>
						<td width="13%"> 
							<html:select styleId="leadGenerator" property="leadGeneratorSelect" disabled="true"
								styleClass="text" value="BRANCH"
								onchange="return leadempty(value);" >

								<html:option value="">---Select---</html:option>
								<html:option value="RM">
								RM / SALES EXEC
							</html:option>
							<html:option value="RO">
								RM / SALES EXEC
							</html:option>
								<html:option value="VENDOR">
								DEALER
							</html:option>
								<html:option value="BRANCH">
								TELECALLER
							</html:option>
							<html:option value="OTHERS">
								OTHERS
							</html:option>
								</html:select>
								<html:hidden property="leadGenerator" value="${leadRMDetails[0].leadGenerator}"/>

						</td>
						<td width="13%">
							<bean:message key="lbl.cust_ref" />
						</td>
						<td width="13%">
							<html:text property="dealNo" styleId="dealNo" styleClass="text"
								value="${leadDetails[0].dealNo}" readonly="true" tabindex="-1"
								style="text-align:right;" />
						</td>
					</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.branch" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<logic:present name="leadRMDetails">
							<html:text property="branchName1" styleClass="text"
								styleId="branchName1" readonly="true" tabindex="-1"
								value="${leadRMDetails[0].branchName1}" />
						</logic:present>
						<logic:notPresent name="leadRMDetails">
							<html:text property="branchName1" styleClass="text"
								styleId="branchName1" readonly="true" tabindex="-1"
								value="${sessionScope.userobject.branchName}" />
						</logic:notPresent>

						<input type="button" class="lovbutton" id="branchButton"
							onclick="openLOVCommon(156,'leadCapturingDetails','lbxBranchId','','', '','','','branchName1')"
							name="dealButton" />


							<logic:notPresent name="leadRMDetails">
								<input type="hidden" name="lbxBranchId" id="lbxBranchId"
									value="${leadRMDetails[0].lbxBranchId}" />
							</logic:notPresent>
							<logic:present name="leadRMDetails">
								<input type="hidden" name="lbxBranchId" id="lbxBranchId"
									value="${sessionScope.userobject.branchId}" />
							</logic:present>
							
					</td>
					
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.userNam" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td>
							
						<html:text property="branchHead1" styleId="branchHead1"
							styleClass="text" readonly="true" tabindex="-1"
							value="${leadRMDetails[0].branchHead1}"></html:text>
							<input type="button" class="lovbutton" id="leadButton"
								onclick="openLOVCommon(488,'leadCapturingDetails','branchHead1','','','','','','contactnobranch')"
								value=" " tabindex="1" name="dealButton" />
							<html:hidden property="lbxbranchHead1" styleId="lbxbranchHead1" value="${leadRMDetails[0].lbxbranchHead1}"/>
							
						</td>
					<td width="13%">
						<bean:message key="lbl.contactno" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="contactnobranch" styleClass="text"
							style="text-align:right;" styleId="contactnobranch"
							maxlength="10" value="${leadRMDetails[0].contactnobranch}"
							onkeypress="return numericOnly(event,10);" />
					</td>
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.leadgencity" />
						<span><!-- <font style="color: red;"> *</font> -->
						</span>
					</td>
					<td width="13%">
						<html:text property="leadgencitybranch"
							styleId="leadgencitybranch" styleClass="text"
							value="${leadRMDetails[0].leadgencitybranch}" 
							onblur="caseChange('leadCapturingDetails','leadgencitybranch');"
							maxlength="50" />
					</td>
				</tr>
				<%-- <tr>
				<td width="13%">
						<bean:message key="lbl.sourceTypeDesc" />
					</td>
					<td width="13%">
					  	 <html:text property="sourceList" styleId="sourceList" styleClass="text" readonly="true" tabindex="-1" value="${leadDetails[0].sourceList}"></html:text>
						 <input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(535,'leadCapturingDetails','source','','','','','showHideDescLov','sourceList');" value=" " tabindex="1" name="dealButton" />
						 <html:hidden property="source" styleId="source" value="${leadDetails[0].source}"/>
					</td>	
					<!-- Nishant space ends -->
					<td width="13%">
						<bean:message key="lbl.source.desc" />
					</td>
					<!-- Nishant space starts -->
					<td width="13%">
						<input type="hidden" id="lbxDescription" name="lbxDescription"/>
						<html:text property="description" styleClass="text"	styleId="description" maxlength="100" value="${leadDetails[0].description}"	onblur="caseChange('leadCapturingDetails','description');"/>
						<html:button property="srcLOV" styleId="srcLOV" onclick="openDescLov();" value=" " styleClass="lovbutton"> </html:button>
					</td>
					<!-- Nishant space end -->
				</tr> --%>
			</table>
		</fieldset>
		
		<fieldset id="others" style="display: none">
			<legend>
				<bean:message key="lbl.preDealGenDetail" />
			</legend>
			<table width="100%">
						<tr>
						<td width="13%">
							<bean:message key="lbl.leadgeneratorBy" />
							<span><font style="color: red;"> *</font>
							</span>
							<html:hidden property="leadId" styleId="leadId"
								value="${leadDetails[0].leadId}" />
						</td>
						<td width="13%"> 
							<html:select styleId="leadGenerator" property="leadGeneratorSelect" disabled="true"
								styleClass="text" value="OTHERS"
								onchange="return leadempty(value);" >

								<html:option value="">---Select---</html:option>
								<html:option value="RM">
								RM / SALES EXEC
							</html:option>
							<html:option value="RO">
								RM / SALES EXEC
							</html:option>
								<html:option value="VENDOR">
								DEALER
							</html:option>
								<html:option value="BRANCH">
								TELECALLER
							</html:option>
							<html:option value="OTHERS">
								OTHERS
							</html:option>
								</html:select>
								<html:hidden property="leadGenerator" value="${leadRMDetails[0].leadGenerator}"/>

						</td>
						<td width="13%">
							<bean:message key="lbl.cust_ref" />
						</td>
						<td width="13%">
							<html:text property="dealNo" styleId="dealNo" styleClass="text"
								value="${leadDetails[0].dealNo}" readonly="true" tabindex="-1"
								style="text-align:right;" />
						</td>
					</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.branch" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<logic:present name="leadRMDetails">
							<html:text property="branchName1Other" styleClass="text"
								styleId="branchName1others" readonly="true" tabindex="-1"
								value="${leadRMDetails[0].branchName1Other}" />
						</logic:present>
						<logic:notPresent name="leadRMDetails">
							<html:text property="branchName1Other" styleClass="text"
								styleId="branchName1others" readonly="true" tabindex="-1"
								value="${sessionScope.userobject.branchName}" />
						</logic:notPresent>

						<input type="button" class="lovbutton" id="branchButton"
							onclick="openLOVCommon(156,'leadCapturingDetails','lbxBranchIdOther','','', '','','','branchName1others')"
							name="dealButton" />


							<logic:notPresent name="leadRMDetails">
								<input type="hidden" name="lbxBranchIdOther" id="lbxBranchIdOther"
									value="${leadRMDetails[0].lbxBranchIdOther}" />
							</logic:notPresent>
							<logic:present name="leadRMDetails">
								<input type="hidden" name="lbxBranchIdOther" id="lbxBranchIdOther"
									value="${sessionScope.userobject.branchId}" />
							</logic:present>
							
					</td>
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.branchexecutive" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
						
						<td >
							<html:text property="branchHead1Other" styleId="branchHead1others"
							styleClass="text" value="${leadRMDetails[0].branchHead1Other}" readonly="true"
							onblur="caseChange('leadCapturingDetails','branchHead1others');"
							maxlength="50" />
					    </td>
					
					<td width="13%">
						<bean:message key="lbl.contactno" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="contactnobranchOther" styleClass="text"
							style="text-align:right;" styleId="contactnobranchothers"
							maxlength="10" value="${leadRMDetails[0].contactnobranchOther}"
							onkeypress="return numericOnly(event,10);" />
					</td>
				</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.leadgencity" />
						<span><!-- <font style="color: red;"> *</font> -->
						</span>
					</td>
					<td width="13%">
						<html:text property="leadgencitybranchOther"
							styleId="leadgencitybranchothers" styleClass="text"
							value="${leadRMDetails[0].leadgencitybranchOther}"
							onblur="caseChange('leadCapturingDetails','leadgencitybranchothers');"
							maxlength="50" />
					</td>
				</tr>
				<%-- <tr>
				<td width="13%">
						<bean:message key="lbl.sourceTypeDesc" />
					</td>

					<td width="13%">
					  	 <html:text property="sourceList" styleId="sourceList" styleClass="text" readonly="true" tabindex="-1" value="${leadDetails[0].sourceList}"></html:text>
						 <input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(535,'leadCapturingDetails','source','','','','','showHideDescLov','sourceList');" value=" " tabindex="1" name="dealButton" />
						 <html:hidden property="source" styleId="source" value="${leadDetails[0].source}"/>
					</td>	
					<!-- Nishant space ends -->
					<td width="13%">
						<bean:message key="lbl.source.desc" />
					</td>
					<!-- Nishant space starts -->
					<td width="13%">
						<input type="hidden" id="lbxDescription" name="lbxDescription"/>
						<html:text property="description" styleClass="text"	styleId="description" maxlength="100" value="${leadDetails[0].description}"	onblur="caseChange('leadCapturingDetails','description');"/>
						<html:button property="srcLOV" styleId="srcLOV" onclick="openDescLov();" value=" " styleClass="lovbutton"> </html:button>
					</td>
					<!-- Nishant space end -->
				</tr> --%>
			</table>
		</fieldset>
		<fieldset>
		<table width="100%">
		<tr>
				<td width="13%">
						<bean:message key="lbl.sourceTypeDesc" /><span><font style="color: red;"> *</font></span>
					</td>
					<td width="13%">
					  	 <html:text property="sourceList" styleId="sourceList" styleClass="text" readonly="true" value="${leadDetails[0].sourceList}"></html:text>
						 <input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(535,'leadCapturingDetails','source','','','','','showHideDescLov','sourceList');" value=" "  name="dealButton" />
						 <html:hidden property="source" styleId="source" value="${leadDetails[0].source}"/>
					</td>	
					<!-- Nishant space ends -->
					<td width="13%">
						<bean:message key="lbl.source.desc" /><span><font style="color: red;"> *</font></span>
					</td>
					<!-- Nishant space starts -->
					<td width="13%">
						<input type="hidden" id="lbxDescription" name="lbxDescription"/>
						<html:text property="description" styleClass="text"	styleId="description" maxlength="100"  value="${leadDetails[0].description}"	onblur="caseChange('leadCapturingDetails','description');"/>
						<html:button property="srcLOV" styleId="srcLOV" onclick="openDescLov();" value=" " styleClass="lovbutton"> </html:button>
					</td>
					<!-- Nishant space end -->
				</tr>
				<tr>
					<!-- pooja code for Application Form No -->
					<TD><bean:message key="lbl.applicationFormNo"  /><font color="red">*</font>
						</TD>
         				<TD ><html:text property="applicationFormNoRm" styleClass="text" maxlength="20" 
         				styleId="applicationFormNoRm" value="${leadDetails[0].applicationFormNoRm}" onkeyup="return upperMe('applicationFormNoRm');" />
         				</TD>
				<!-- pooja code for Application Form No -->
				</tr>
				</table>
		</fieldset>
<fieldset>
			<legend>
				<bean:message key="lbl.requested.loanDetail" />
			</legend>
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="1" cellspacing="1">
							
							<tr>
							<td width="13%"><bean:message key="lbl.relationshipManager"/><font color="red">*</font></td>
         		 <td width="13%">
      		    <html:text property="relationshipManager" readonly="true" styleId="relationshipManager" styleClass="text" value="${leadDetails[0].relationshipManager}" tabindex="-1" />
          		<html:hidden  property="lbxRelationship" styleId="lbxRelationship" value="${leadDetails[0].lbxRelationship}" />
                <html:button property="lbxSubIndustryButton" styleId="lbxSubIndustryButton" onclick="closeLovCallonLov1();openLOVCommon(13,'leadCapturingDetails','relationshipManager','branch','lbxRelationship', 'lbxBranchId','Please Select Branch','','hCommon')" value=" " styleClass="lovbutton"> </html:button>
          		<input type="hidden" name="hCommon" id="hCommon" />
          		</td>
          		<td width="13%"><bean:message key="lbl.relationshipofficer"/></td>
          		<td width="13%">
  				<html:text property="generatedUser" readonly="true" styleId="generatedUser" styleClass="text" value="${leadDetails[0].generatedUser}" tabindex="-1" />
          		<html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId" value="${leadDetails[0].lbxUserSearchId}" />
          		<html:button property="lbxIndustryButton" styleId="lbxIndustryButton" onclick="closeLovCallonLov1();openLOVCommon(502,'leadCapturingDetails','generatedUser','branch','lbxUserSearchId', 'lbxBranchId','Please Select Branch','checkRelationshipManage','hCommon','relationshipManager')" value=" " styleClass="lovbutton"> </html:button>
          </td>
          </tr>
          
          <tr>
								<td width="13%">
									<bean:message key="lbl.products" /><span><font
										style="color: red;"> *</font>
									</span>
								</td>
								<td width="13%">
									<html:text property="product" styleId="product" tabindex="-1"
										styleClass="text" value="${leadDetails[0].product}"
										readonly="true" ></html:text>
									<input type="button" class="lovbutton" id="productButton"
										onclick="openLOVCommon(230,'leadCapturingDetails','lbxProductID','','scheme','lbxscheme','','','product')"
										value=" "  name="productButton" />
										<input type="hidden" name="lbxProductID" id="lbxProductID"
											value="${leadDetails[0].lbxProductID}" />
								</td>
								<td width="13%">
									<bean:message key="lbl.scheme" /><span><font
										style="color: red;"> *</font>
									</span>
								</td>
								<td width="13%">
									<input type="hidden" name="lbxscheme" id="lbxscheme" />
									<html:text property="scheme" styleClass="text" tabindex="-1" styleId="scheme" value="${leadDetails[0].scheme}" readonly="true"  />
									<input type="button" class="lovbutton" id="schemeButton"
										onclick="openLOVCommon(252,'leadCapturingDetails','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','getPreDealDefaultLoanDetail','schemeId')"
										value="" name="schemeButton" />
										<input type="hidden" id="schemeId" name="schemeId" value="${leadDetails[0].schemeId}" />
										<input type="hidden" id="lbxscheme" name="lbxscheme" />
										
								</td></tr>
								<tr>	
								<td width="13%">
									<bean:message key="lbl.loanAmount" /><span><font
										style="color: red;"> *</font>
									</span>
								</td>
							<td width="13%">
									<html:text property="loanAmount" styleClass="text"
										styleId="loanAmount" maxlength="22"
										value="${leadDetails[0].loanAmount}" 
										
										onkeypress="return numbersonly(event, id, 18)"
										onblur="formatNumber(this.value, id);"
										onkeyup="checkNumber(this.value, event, 18, id);"
										onfocus="keyUpNumber(this.value, event, 18, id);" />
								</td>
								<td width="13%">
									<bean:message key="lbl.loanTenure" /><span><font
										style="color: red;"> *</font>
									</span>
								</td>
								<td width="13%">
									<html:text property="loanTenure" styleClass="text"
										styleId="loanTenure" maxlength="3" 
										value="${leadDetails[0].loanTenure}" 
										
										onkeypress="return numericOnly(event,3);" />
								</td>
		
							</tr>
												</table>
							</td>
							</tr>
							</table>
							
</fieldset>
		<!-- /////////////////////////////////DIFFERENT END/////////////////////////////////////////// -->
		<logic:notPresent name="cibilDone">
		<fieldset>
			<legend>
				<bean:message key="lbl.app_customer_detail" />
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
							<html:select property="relationship" styleId="relationship"
								styleClass="text" onchange="san();san2();cleanUp();customerLov();"
								value="${leadDetails[0].relationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="addressId" styleClass="text" styleId="addressId"  value="" />
		<html:hidden property="lbxCustomerId" styleClass="text" styleId="lbxCustomerId"  value="${leadDetails[0].lbxCustomerId}" />
		<html:button property="customerIdButton" styleId="customerIdButton" onclick="closeLovCallonLov1();openLOVCommon(192042,'customerForm','lbxCustomerId','','','','','copyAppCustomerDetails','customerName','address1','customerType');custtype();san();" value=" " styleClass="lovbutton"></html:button>			
						</td>
					</logic:present>
					<logic:present name="leadNew">
						<td width="13%">
							<html:select property="relationship" styleId="relationship"
								styleClass="text" onchange="san();san2();cleanUp();customerLov();"
								value="${leadDetails[0].relationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="addressId" styleClass="text" styleId="addressId"  value="" />
		<html:hidden property="lbxCustomerId" styleClass="text" styleId="lbxCustomerId"  value="${leadDetails[0].lbxCustomerId}" />
	
		<html:button property="customerIdButton" styleId="customerIdButton" onclick="closeLovCallonLov1();openLOVCommon(192042,'customerForm','lbxCustomerId','','','','','copyAppCustomerDetails','customerName','address1','customerType');custtype();" value=" " styleClass="lovbutton"></html:button>
		
							
						</td>
					</logic:present>

					<td width="13%">
						<bean:message key="lbl.Type" />
						<span><font style="color: red;"> *</font> </span>
					</td>
					<td width="13%">
						<html:select property="customerType1" styleClass="text"
							styleId="customerType1" value="${leadDetails[0].customerType}"
							onchange="updateCust(this.value);custtype();" >
							<html:option value="">--Select--</html:option>
							<html:option value="I">INDIVIDUAL</html:option>
							<html:option value="C">CORPORATE</html:option>
						</html:select>
						<html:hidden property="customerType" styleId="customerType" value="${leadDetails[0].customerType}" />
					</td></tr>
					<tr>
					<td width="13%">
					<bean:message key="constitution" />
						<font color="red">*</font>
					</td>
					<td><div id="Individualconstitution" style="display:none">
						<html:select property="indconstitution" styleId="indconstitution" 
							value="${leadDetails[0].contitutionCode}" styleClass="text"
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
							value="${leadDetails[0].contitutionCode}" styleClass="text"
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
						<html:select property="constitution" styleId="constitution"
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
		
						<!-- Saurabh Code Start here for Applicant Individual Sub Profile -->
		<%-- <html:hidden property="appconSubprofileId" styleClass="text" styleId="appconSubprofileId" value="${leadDetails[0].appconSubprofile}" />
		<td><bean:message key="lbl.IndConSubprofile" /><font color="red">*</font></td>
		 <td><html:select property="appconSubprofile"   value="${leadDetails[0].appconSubprofile}" styleClass="text" styleId="appconSubprofile">
				<logic:present name="indConstSubprofileList">
					<logic:notEmpty name="indConstSubprofileList">
					<html:optionsCollection name="indConstSubprofileList" label="indConstSubprofile" value="indConstSubprofileCode" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
		  </td> --%>
		<!-- End By saurabh -->	
				</tr>
				
				</table>
				</fieldset>



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
						<html:select property="businessSegment" styleId="businessSegment1"
							value="${leadDetails[0].businessSegment}" styleClass="text">
							<html:option value="">--Select--</html:option>
							<logic:present name="businessSegmentList">
								<logic:notEmpty name="businessSegmentList">
									<html:optionsCollection name="businessSegmentList"
										label="businessSegmentDesc" value="businessSegmentCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						<html:hidden property="businessSegment" styleId="businessSegment"
							value="${leadDetails[0].businessSegment}" />
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
							onclick="closeLovCallonLov1();openLOVCommon(253,'leadCapturingDetails','lbxIndustry','','', '','','lovIndustryLead','industry')"
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


<div id='individualfield' style="display: none;">
<fieldset>
			<legend>
				<bean:message key="lbl.individual.details" />
			</legend>
			<table width="100%">
			<tr>
	<td width="13%">
							<bean:message key="lbl.fname" />
							<font color="red">*</font>
						</td>
						<td width="13%">
							<html:text property="firstName" styleId="firstName"
								styleClass="text" value="${leadDetails[0].firstName}"
								onchange="return upperMe('firstName');" maxlength="50" />
						</td>
				<!-- pooja change for middle name -->
					<td width="13%">
						<bean:message key="individual.middle" />
						<!-- <font color="red">*</font> -->
					</td>
					<td width="13%">
						<html:text property="middleName" styleId="middleName"
							styleClass="text" value="${leadDetails[0].middleName}"
							onchange="return upperMe('middleName');" maxlength="50" />
					</td>
					</tr>
					<tr>
					<td width="13%">
						<bean:message key="individual.last" />
						<font color="red">*</font>
					</td>
					<td width="13%">
						<html:text property="lastName" styleId="lastName"
							styleClass="text" value="${leadDetails[0].lastName}"
							onchange="return upperMe('lastName');" maxlength="50" />
					</td>
					<!-- pooja change for middle name -->
					</tr>
				<tr>
					<td>
						<bean:message key="individual.birthdate" />
						<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
					</td>
					
				
					<td>
						<html:text property="custDOB" onchange="checkDate('custDOB');checkcustDOB('custDOB');"
							styleId="custDOB" styleClass="text"
							value="${leadDetails[0].custDOB}" />
					</td>
					<td>
						<bean:message key="fatherHusband" /><font color="red">*</font>
					</td>
					<td>
						<html:text property="fatherName"
							styleId="fatherName" styleClass="text" onblur="caseChange('leadCapturingDetails','fatherName');"
							value="${leadDetails[0].fatherName}"  maxlength="50"/>
					</td>
					<input type="hidden" name="sysDate" id="sysDate" value="${requestScope.sysDate}" />
					<input type="hidden" name="approve" id="approve" value="N" />
				
					
		
			</tr>
			<!-- Sanjog Changes Start Here -->
			<tr>
			<td><bean:message key="lbl.gender" /><font color="red">*</font></td>
          <td><html:select property="genderIndividual" value="${leadDetails[0].genderIndividual}" styleClass="text" styleId="genderIndividual">
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
						<html:text property="dlNumber"
							styleId="dlNumber" styleClass="text"
							value="${leadDetails[0].dlNumber}" maxlength="20"/>
					</td>
								
		
			</tr>
			<tr>
					<td>
						<bean:message key="voterId" />
					</td>				
				
					<td>
						<html:text property="voterId"
							styleId="voterId" styleClass="text"
							value="${leadDetails[0].voterId}" maxlength="20"/>
					</td>
					
					<td>
						<bean:message key="pan" /><font color="red">*</font></td>
					</td>
					<td>
						<html:text property="custPanInd" maxlength="10" styleClass="text"
							styleId="custPanInd" value="${leadDetails[0].custPanInd}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('custPanInd');" />
					</td>
					</tr>
					<tr>
					<td>
						<bean:message key="passport" />
					</td>				
				
					<td>
						<html:text property="passport"
							styleId="passport" styleClass="text"
							value="${leadDetails[0].passport}" maxlength="20"/>
					</td>
					<td><bean:message key="aadhaar" /></td>
					<html:hidden property="haadhaar" styleClass="text" styleId="haadhaar" value="" />
          <td><html:text property="aadhaar" styleClass="text" styleId="aadhaar" readonly="true" onkeypress="return numericOnly(event,12);" value="${leadDetails[0].aadhaar}" maxlength="12"/></td>
					
					</tr>
					
		</table>		
	</fieldset>
</div>

		<fieldset>
			<legend>
				<bean:message key="lbl.a_commDetails" />
			</legend>
			<table width="100%">


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
						<html:select property="addresstype" styleClass="text"
							styleId="addresstype1" value="${leadDetails[0].addresstype}">
							<html:option value="">--Select--</html:option>
							<html:optionsCollection name="addresstypeList"
								value="addresstypeid" label="addresstypename" />
						</html:select>
						<html:hidden property="addresstype" styleClass="text" styleId="addresstype" value="${leadDetails[0].addresstype}" />
					</td>
				
					<td width="13%">
						<bean:message key="lbl.addressLine1" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="address1" styleClass="text"
							styleId="address1" maxlength="50"
							value="${leadDetails[0].address1}"
							onblur="caseChange('leadCapturingDetails','address1');" />
					</td>

</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.two" />
					</td>
					<td width="13%">
						<html:text property="address2" styleClass="text"
							styleId="address2" maxlength="50"
							value="${leadDetails[0].address2}"
							onblur="caseChange('leadCapturingDetails','address2');" />
					</td>
					<td width="13%">
						<bean:message key="lbl.three" />
					</td>
					<td width="13%">
						<html:text property="address3" styleClass="text"
							styleId="address3" maxlength="50"
							value="${leadDetails[0].address3}"
							onblur="caseChange('leadCapturingDetails','address3');" />
					</td>
</tr>
<%-- 				<tr>
					<td width="13%">
						<bean:message key="lbl.country" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<logic:present name="leadDetails">
							<html:text property="country" styleId="country" styleClass="text"
								tabindex="-1" readonly="true" value="${leadDetails[0].country}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','txtCountryCode','','','','','clearCountryLovChild','country')"
								value="" name="countryButton" />
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${leadDetails[0].txtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">

							<html:text property="country" styleId="country" styleClass="text"
								tabindex="-1" readonly="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','txtCountryCode','','','','','clearCountryLovChild','country')"
								value="" name="countryButton"/>
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>

					<td width="13%">
						<bean:message key="lbl.state" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="state" styleId="state" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].state}" />
						<input type="button" class="lovbutton" id="stateButton"
							onclick="openLOVCommon(256,'leadCapturingDetails','txtStateCode','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChild','state')"
							value="" name="stateButton"/>
							<input type="hidden" name="txtStateCode" id="txtStateCode"
								value="${leadDetails[0].txtStateCode}" />
					</td>
</tr>
				<tr>


					<td width="13%">
						<bean:message key="lbl.dist" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="dist" styleId="dist" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].dist}" />
						<input type="button" class="lovbutton" id="distButton"
							onclick="openLOVCommon(257,'leadCapturingDetails','txtDistCode','state','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChild','dist')"
							value=" " name="distButton"/>
							<input type="hidden" name="txtDistCode" id="txtDistCode"
								value="${leadDetails[0].txtDistCode}" />
					</td>
				<td width="13%">
						<bean:message key="address.Tahsil" />
					</td>
					<td>
						<input type="text" name="tahsilDesc" id="tahsilDesc" readonly="readonly" value="${leadDetails[0].tahsilDesc}" />
						<html:hidden property="tahsil" styleId="tahsil" styleClass="text" value="${leadDetails[0].tahsil}"/>
						<html:button property="tahsilButton" styleId="tahsilButton" onclick="openLOVCommon(495,'leadCapturingDetails','tahsilDesc','dist','', 'txtDistCode','Please select District first','','tahsil');" value=" " styleClass="lovbutton"> </html:button>
					</td>
		</tr>
				<tr>			
				<td width="13%">
						<bean:message key="lbl.pincode" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="pincode" styleClass="text" styleId="pincode"
							maxlength="6" value="${leadDetails[0].pincode}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,6);" />
					</td>
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="phoneOff" styleClass="text"
							styleId="phoneOff" maxlength="10"
							value="${leadDetails[0].phoneOff}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,10);" />
					</td>
				</tr> --%>
<!-- pooja code starts for Applicant Pincode -->
<logic:present name="pincodeFlag">
<logic:equal value="N" name="pincodeFlag">
 <html:hidden property="pincodeFlag" styleId="pincodeFlag" styleClass="text" value="N"/>
<tr> 


						<!-- <span><font style="color: red;"> *</font> -->
						<!-- </span> -->
		
					
					<td  width="13%">
						<bean:message key="lbl.country" />
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td  width="13%">
						<logic:present name="leadDetails">
							<html:text property="country" styleId="country" styleClass="text"
								tabindex="-1" readonly="true" value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','txtCountryCode','','','','','clearCountryLovChild','country')"
								value="" name="countryButton" />
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:present>
						<logic:notPresent name="leadDetails">

							<html:text property="country" styleId="country" styleClass="text"
								tabindex="-1" readonly="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','txtCountryCode','','','','','clearCountryLovChild','country')"
								value="" name="countryButton"/>
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
					<td width="13%">
						<bean:message key="lbl.state" />
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td width="13%">
						<html:text property="state" styleId="state" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].state}" />
						<input type="button" class="lovbutton" id="stateButton"
							onclick="openLOVCommon(5,'leadCapturingDetails','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChild','txtStateCode')"
							value="" name="stateButton"/>
							<input type="hidden" name="txtStateCode" id="txtStateCode"
								value="${leadDetails[0].txtStateCode}" />
					</td>
					
	
</tr>

<tr>

<td width="13%">
						<bean:message key="lbl.dist" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="dist" styleId="dist" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].dist}" />
						<input type="button" class="lovbutton" id="distButton"
							onclick="openLOVCommon(257,'leadCapturingDetails','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChild','txtDistCode')"
							value=" " name="distButton"/>
							<input type="hidden" name="txtDistCode" id="txtDistCode"
								value="${leadDetails[0].txtDistCode}" />
					</td>
					<td width="13%">
						<bean:message key="address.Tahsil" />
					</td>
					<td width="13%">
						<html:text property="tahsilDesc" styleId="tahsilDesc" styleClass="text" readonly="readonly" value="${leadDetails[0].tahsil}" />
						<%-- <input type="text" name="tahsilDesc" id="tahsilDesc" readonly="readonly" value="${leadDetails[0].tahsil}" /> --%>
						<html:hidden property="tahsil" styleId="tahsil" styleClass="text" value="${leadDetails[0].tahsilDesc}"/>
						<html:button property="tahsilButton" styleId="tahsilButton" onclick="openLOVCommon(495,'leadCapturingDetails','tahsilDesc','dist','tahsilDesc', 'txtDistCode','Please select District first','','tahsil');" value=" " styleClass="lovbutton"> </html:button>
					</td>
					
</tr>


				<tr>
							<td width="13%">
						<bean:message key="lbl.pincode" /></td>
<td width="13%">
						<html:text property="pincode" styleClass="text" styleId="pincode"
							maxlength="6" value="${leadDetails[0].pincode}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,6);" />
					</td>
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="phoneOff" styleClass="text"
							styleId="phoneOff" maxlength="10"
							value="${leadDetails[0].phoneOff}"
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
			<td><html:text property="pincode" styleId="pincode" size="20" styleClass="text" readonly="true" value="${leadDetails[0].pincode}" tabindex="-1"></html:text>
            <html:hidden property="lbxPincode" styleId="lbxPincode" styleClass="text" value=""/>
 			<html:button property="pincodeButton" styleId="pincodeButton" onclick="openLOVCommon(5080,'leadCapturingDetails','pincode','','', '','','getCountryStateDistrictTahsilValue','txnTahsilHID','hcommon');closeLovCallonLov('');" value=" " styleClass="lovbutton"> </html:button>
		</td>
			
		<td><bean:message key="address.Tahsil" /></td>	
			<td id="lov2"> 
            	<div id="tahsildetail">
            		<html:text property="tahsil" styleId="tahsil" size="20" styleClass="text" readonly="true" value="${leadDetails[0].tahsilDesc}" ></html:text>
                	<html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value="${leadDetails[0].tahsil}"/>
<%--  			   		<html:button property="tahsilButton" styleId="tahsilButton" onclick="closeLovCallonLov('dist');openLOVCommon(388,'PropertyForm','tahsil','dist','txnTahsilHID', 'txtDistCode','Please select District first','','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
   				</div>
   			</td>
   			
   			<td id="lov4" style="display: none"> 
            	<div id="tahsildetail">
            		<html:text property="tahsil4" styleId="tahsil4" size="20" styleClass="text"  value="${leadDetails[0].tahsil}" ></html:text>
                	<html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value="${leadDetails[0].txnTahsilHID}"/>
<%--  			   		<html:button property="tahsilButton" styleId="tahsilButton" onclick="closeLovCallonLov('dist');openLOVCommon(388,'PropertyForm','tahsil','dist','txnTahsilHID', 'txtDistCode','Please select District first','','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
   				</div>
   			</td>	
   			
   			
   			
	</tr>
	
	
	<tr id="lov5"> 
		<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font> -->
						</span></td>
          	<td >
          		<div id="cityID">
          			<html:text property="dist" styleId="dist" size="20" styleClass="text" readonly="true" value="${leadDetails[0].dist}" ></html:text>
          			<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${leadDetails[0].txtDistCode}"></html:hidden>
<%--    					<html:button property="distButton" styleId="districtButton" onclick="closeLovCallonLov('state');openLOVCommon(20,'PropertyForm','dist','state','txtDistCode', 'txtStateCode','Please select state first','clearTahsilLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
   				</div>
   			</td>
   			
   			<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="statedetail">
                	<html:text property="state" styleId="state" styleClass="text" size="20" value="${leadDetails[0].state}" readonly="true" ></html:text>
   					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${leadDetails[0].txtStateCode}"></html:hidden>
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton" style="display: none" onclick="closeLovCallonLov('country');openLOVCommon(5,'leadCapturingDetails','state','country','txtStateCode', 'txtCountryCode','Please select country first','clearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	          	</div>
			</td>
		

	</tr>
	
	<tr id="lov6" style="display: none">
	
	<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
          	<td>
          		<div id="cityID">
          			<html:text property="dist4" styleId="dist4" size="20" styleClass="text" readonly="true" value="${leadDetails[0].dist}" ></html:text>
          			<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${leadDetails[0].txtDistCode}"></html:hidden>
  					<html:button property="distButton" styleId="districtButton" onclick="closeLovCallonLov('state6');openLOVCommon(20,'leadCapturingDetails','dist4','state6','txtDistCode', 'txtStateCode','Please select state first','clearTahsilLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   				</div>
   			</td>
	<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="statedetail">
                	<html:text property="state6" styleId="state6" styleClass="text" size="20" value="${leadDetails[0].state}" readonly="true" ></html:text>
   					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${leadDetails[0].txtStateCode}"></html:hidden>
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton"  onclick="closeLovCallonLov('country');openLOVCommon(5,'leadCapturingDetails','state6','txtCountryCode','txtStateCode', 'txtCountryCode','Please select country first','clearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	          	</div>
			</td>
	
	
	
	</tr>
	
	<tr>
					<td width="13%">
						<bean:message key="lbl.country" />
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td id="lov1" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="country" styleId="country" styleClass="text"
								 readonly="true" value="${leadDetails[0].country}" />
							<!-- <input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','country','','','','','clearCountryLovChild','txtCountryCode')"
								value="" name="countryButton" /> -->
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${leadDetails[0].txtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="country" styleId="country" styleClass="text"
								readonly="true"	value="${defaultcountry[0].defaultcountryname}" />
							<!-- <input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','txtCountryCode','','','','','clearCountryLovChild','country')"
								value="" name="countryButton"/> -->
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
					
					
					<td id="lov3" style="display: none" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="country" styleId="country" styleClass="text"
								 readonly="true" value="${leadDetails[0].country}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','country','','','','','clearCountryLovChild','txtCountryCode')"
								value="" name="countryButton" />
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${leadDetails[0].txtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="country" styleId="country" styleClass="text"
								 readonly="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','country','','','','','clearCountryLovChild','txtCountryCode')"
								value="" name="countryButton"/>
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>

					
		
				
					
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="phoneOff" styleClass="text"
							styleId="phoneOff" maxlength="10"
							value="${leadDetails[0].phoneOff}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,10);" />
					</td>
	</tr>
	</logic:equal>
	</logic:present>
<!-- pooja code end for Applicant Pincode -->
				
				<tr>
					<td width="13%">
						<bean:message key="lbl.email" />
					</td>
					<td width="13%">
						<html:text property="email" styleClass="text" styleId="email"
							maxlength="100" value="${leadDetails[0].email}"/>
					</td>
					<td width="13%">
						<bean:message key="lbl.landmark" />
					</td>
					<td width="13%">
						<html:text property="landmark" styleClass="text"
							styleId="landmark" maxlength="25"
							value="${leadDetails[0].landmark}"
							onblur="caseChange('leadCapturingDetails','landmark');" />
					</td>
	</tr>
			
				

			</table>
		</fieldset>
		</logic:notPresent>
		
				<logic:present name="cibilDone">
		<fieldset>
			<legend>
				<bean:message key="lbl.app_customer_detail" />
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
						<html:hidden property="relationship" styleClass="text" styleId="relationship"  value="${leadDetails[0].relationship}" />
							<html:select property="relationship" styleId="relationship" disabled="true" 
								styleClass="text" onchange="san();san2();cleanUp();customerLov();"
								value="${leadDetails[0].relationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							
							<html:hidden property="addressId" styleClass="text" styleId="addressId"  value="" />
		<html:hidden property="lbxCustomerId" styleClass="text" styleId="lbxCustomerId"  value="${leadDetails[0].lbxCustomerId}" />
		<html:button property="customerIdButton" styleId="customerIdButton"  disabled="true" onclick="closeLovCallonLov1();openLOVCommon(192042,'customerForm','lbxCustomerId','','','','','copyAppCustomerDetails','customerName','address1','customerType');custtype();san();" value=" " styleClass="lovbutton"></html:button>			
						</td>
					</logic:present>
					<logic:present name="leadNew">
						<td width="13%">
						<html:hidden property="relationship" styleClass="text" styleId="relationship"  value="${leadDetails[0].relationship}" />
							<html:select property="relationship" styleId="relationship" disabled="true" 
								styleClass="text" onchange="san();san2();cleanUp();customerLov();"
								value="${leadDetails[0].relationship}">
								<html:option value="New">New</html:option>
								<html:option value="Existing">Existing</html:option>
							</html:select>
							<html:hidden property="addressId" styleClass="text" styleId="addressId"  value="" />
		<html:hidden property="lbxCustomerId" styleClass="text" styleId="lbxCustomerId"  value="${leadDetails[0].lbxCustomerId}" />
	
		<html:button property="customerIdButton" styleId="customerIdButton" disabled="true"  onclick="closeLovCallonLov1();openLOVCommon(192042,'customerForm','lbxCustomerId','','','','','copyAppCustomerDetails','customerName','address1','customerType');custtype();" value=" " styleClass="lovbutton"></html:button>
		
							
						</td>
					</logic:present>

					<td width="13%">
						<bean:message key="lbl.Type" />
						<span><font style="color: red;"> *</font> </span>
					</td>
					<td width="13%">
						<html:select property="customerType1" styleClass="text" disabled="true" 
							styleId="customerType1" value="${leadDetails[0].customerType}"
							onchange="updateCust(this.value);custtype();" >
							<html:option value="">--Select--</html:option>
							<html:option value="I">INDIVIDUAL</html:option>
							<html:option value="C">CORPORATE</html:option>
						</html:select>
						<html:hidden property="customerType" styleId="customerType" value="${leadDetails[0].customerType}" />
					</td></tr>
					<tr>
					<td width="13%">
					<bean:message key="constitution" />
						<font color="red">*</font>
					</td>
					<td><div id="Individualconstitution" style="display:none">
						<html:select property="indconstitution" styleId="indconstitution" 
							value="${leadDetails[0].contitutionCode}" styleClass="text"
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
							value="${leadDetails[0].contitutionCode}" styleClass="text"
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
						<html:select property="constitution" styleId="constitution"
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
		
						<!-- Saurabh Code Start here for Applicant Individual Sub Profile -->
		<%-- <html:hidden property="appconSubprofileId" styleClass="text" styleId="appconSubprofileId" value="${leadDetails[0].appconSubprofile}" />
		<td><bean:message key="lbl.IndConSubprofile" /><font color="red">*</font></td>
		 <td><html:select property="appconSubprofile"   value="${leadDetails[0].appconSubprofile}" styleClass="text" styleId="appconSubprofile">
				<logic:present name="indConstSubprofileList">
					<logic:notEmpty name="indConstSubprofileList">
					<html:optionsCollection name="indConstSubprofileList" label="indConstSubprofile" value="indConstSubprofileCode" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
		  </td> --%>
		<!-- End By saurabh -->	
				</tr>
				
				</table>
				</fieldset>



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
							<html:text property="customerName" styleClass="text" readonly="true" 
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
						<html:text property="registrationNo" styleId="registrationNo" readonly="true" 
							maxlength="25" value="${leadDetails[0].registrationNo}"
							styleClass="text" />
					</td>

				

		
					<td>
						<bean:message key="businessSegment" />
						<font color="red">*</font>
					</td>
					<td>
						<html:select property="businessSegment" styleId="businessSegment1"
							value="${leadDetails[0].businessSegment}" styleClass="text">
							<html:option value="">--Select--</html:option>
							<logic:present name="businessSegmentList">
								<logic:notEmpty name="businessSegmentList">
									<html:optionsCollection name="businessSegmentList"
										label="businessSegmentDesc" value="businessSegmentCode" />
								</logic:notEmpty>
							</logic:present>
						</html:select>
						<html:hidden property="businessSegment" styleId="businessSegment"
							value="${leadDetails[0].businessSegment}" />
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
							onclick="closeLovCallonLov1();openLOVCommon(253,'leadCapturingDetails','lbxIndustry','','', '','','lovIndustryLead','industry')"
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
						<html:text property="custPan" maxlength="10" styleClass="text" readonly="true" 
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


<div id='individualfield' style="display: none;">
<fieldset>
			<legend>
				<bean:message key="lbl.individual.details" />
			</legend>
			<table width="100%">
			<tr>
	<td width="13%">
							<bean:message key="lbl.fname" />
							<font color="red">*</font>
						</td>
						<td width="13%">
							<html:text property="firstName" styleId="firstName" readonly="true" 
								styleClass="text" value="${leadDetails[0].firstName}"
								onchange="return upperMe('firstName');" maxlength="50" />
						</td>
				<!-- pooja change for middle name -->
					<td width="13%">
						<bean:message key="individual.middle" />
						<!-- <font color="red">*</font> -->
					</td>
					<td width="13%">
						<html:text property="middleName" styleId="middleName" readonly="true" 
							styleClass="text" value="${leadDetails[0].middleName}"
							onchange="return upperMe('middleName');" maxlength="50" />
					</td>
					</tr>
					<tr>
					<td width="13%">
						<bean:message key="individual.last" />
						<font color="red">*</font>
					</td>
					<td width="13%">
						<html:text property="lastName" styleId="lastName" readonly="true" 
							styleClass="text" value="${leadDetails[0].lastName}"
							onchange="return upperMe('lastName');" maxlength="50" />
					</td>
					<!-- pooja change for middle name -->
					</tr>
				<tr>
					<td>
						<bean:message key="individual.birthdate" />
						<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
					</td>
					
				
					<td>
						<html:text property="custDOB" onchange="checkDate('custDOB');checkcustDOB('custDOB');"
							styleId="custDOB1" styleClass="text" readonly="true" 
							value="${leadDetails[0].custDOB}" />
							<html:hidden property="custDOB" styleId="custDOB"
							value="${leadDetails[0].custDOB}" />
							
					</td>
					<td>
						<bean:message key="fatherHusband" /><font color="red">*</font>
					</td>
					<td>
						<html:text property="fatherName"
							styleId="fatherName" styleClass="text"  readonly="true" onblur="caseChange('leadCapturingDetails','fatherName');"
							value="${leadDetails[0].fatherName}"  maxlength="50"/>
					</td>
					<input type="hidden" name="sysDate" id="sysDate" value="${requestScope.sysDate}" />
					<input type="hidden" name="approve" id="approve" value="N" />
				
					
		
			</tr>
			<!-- Sanjog Changes Start Here -->
			<tr>
			<td><bean:message key="lbl.gender" /><font color="red">*</font></td>
          <td><html:select property="genderIndividual" value="${leadDetails[0].genderIndividual}" styleClass="text" disabled="true" styleId="genderIndividual1">
				<logic:present name="GenderCategory">
					<logic:notEmpty name="GenderCategory">
					<html:optionsCollection name="GenderCategory" label="genderDesc" value="genderCode" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
				<html:hidden property="genderIndividual" styleId="genderIndividual"
							value="${leadDetails[0].genderIndividual}" />
		  </td>
					
					<td>
						<bean:message key="lbl.drivingLic" />
					</td>
					<td>
						<html:text property="dlNumber"
							styleId="dlNumber" styleClass="text" readonly="true" 
							value="${leadDetails[0].dlNumber}" maxlength="20"/>
					</td>
								
		
			</tr>
			<tr>
					<td>
						<bean:message key="voterId" />
					</td>				
				
					<td>
						<html:text property="voterId"
							styleId="voterId" styleClass="text" readonly="true" 
							value="${leadDetails[0].voterId}" maxlength="20"/>
					</td>
					
					<td>
						<bean:message key="pan" /><font color="red">*</font></td>
					</td>
					<td>
						<html:text property="custPanInd" maxlength="10" styleClass="text" readonly="true" 
							styleId="custPanInd" value="${leadDetails[0].custPanInd}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('custPanInd');" />
					</td>
					</tr>
					<tr>
					<td>
						<bean:message key="passport" />
					</td>				
				
					<td>
						<html:text property="passport"
							styleId="passport" styleClass="text" readonly="true" 
							value="${leadDetails[0].passport}" maxlength="20"/>
					</td>
					<td><bean:message key="aadhaar" /></td>
					<html:hidden property="haadhaar" styleClass="text" styleId="haadhaar" value="" />
          <td><html:text property="aadhaar" styleClass="text" styleId="aadhaar"   readonly="true" onkeypress="return numericOnly(event,12);" value="${leadDetails[0].aadhaar}" maxlength="12"/></td>
					
					</tr>
					
		</table>		
	</fieldset>
</div>

		<fieldset>
			<legend>
				<bean:message key="lbl.a_commDetails" />
			</legend>
			<table width="100%">


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
						<html:select property="addresstype" styleClass="text" disabled="true"
							styleId="addresstype1" value="${leadDetails[0].addresstype}">
							<html:option value="">--Select--</html:option>
							<html:optionsCollection name="addresstypeList"
								value="addresstypeid" label="addresstypename" />
						</html:select>
						<html:hidden property="addresstype" styleId="addresstype"
							value="${leadDetails[0].addresstype}" />
					</td>
				
					<td width="13%">
						<bean:message key="lbl.addressLine1" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="address1" styleClass="text" readonly="true" 
							styleId="address1" maxlength="50"
							value="${leadDetails[0].address1}"
							onblur="caseChange('leadCapturingDetails','address1');" />
					</td>

</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.two" />
					</td>
					<td width="13%">
						<html:text property="address2" styleClass="text" readonly="true" 
							styleId="address2" maxlength="50"
							value="${leadDetails[0].address2}"
							onblur="caseChange('leadCapturingDetails','address2');" />
					</td>
					<td width="13%">
						<bean:message key="lbl.three" />
					</td>
					<td width="13%">
						<html:text property="address3" styleClass="text" readonly="true" 
							styleId="address3" maxlength="50"
							value="${leadDetails[0].address3}"
							onblur="caseChange('leadCapturingDetails','address3');" />
					</td>
</tr>
<%-- 				<tr>
					<td width="13%">
						<bean:message key="lbl.country" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<logic:present name="leadDetails">
							<html:text property="country" styleId="country" styleClass="text"
								tabindex="-1" readonly="true" value="${leadDetails[0].country}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','txtCountryCode','','','','','clearCountryLovChild','country')"
								value="" name="countryButton" />
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${leadDetails[0].txtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">

							<html:text property="country" styleId="country" styleClass="text"
								tabindex="-1" readonly="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','txtCountryCode','','','','','clearCountryLovChild','country')"
								value="" name="countryButton"/>
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>

					<td width="13%">
						<bean:message key="lbl.state" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="state" styleId="state" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].state}" />
						<input type="button" class="lovbutton" id="stateButton"
							onclick="openLOVCommon(256,'leadCapturingDetails','txtStateCode','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChild','state')"
							value="" name="stateButton"/>
							<input type="hidden" name="txtStateCode" id="txtStateCode"
								value="${leadDetails[0].txtStateCode}" />
					</td>
</tr>
				<tr>


					<td width="13%">
						<bean:message key="lbl.dist" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="dist" styleId="dist" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].dist}" />
						<input type="button" class="lovbutton" id="distButton"
							onclick="openLOVCommon(257,'leadCapturingDetails','txtDistCode','state','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChild','dist')"
							value=" " name="distButton"/>
							<input type="hidden" name="txtDistCode" id="txtDistCode"
								value="${leadDetails[0].txtDistCode}" />
					</td>
				<td width="13%">
						<bean:message key="address.Tahsil" />
					</td>
					<td>
						<input type="text" name="tahsilDesc" id="tahsilDesc" readonly="readonly" value="${leadDetails[0].tahsilDesc}" />
						<html:hidden property="tahsil" styleId="tahsil" styleClass="text" value="${leadDetails[0].tahsil}"/>
						<html:button property="tahsilButton" styleId="tahsilButton" onclick="openLOVCommon(495,'leadCapturingDetails','tahsilDesc','dist','', 'txtDistCode','Please select District first','','tahsil');" value=" " styleClass="lovbutton"> </html:button>
					</td>
		</tr>
				<tr>			
				<td width="13%">
						<bean:message key="lbl.pincode" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="pincode" styleClass="text" styleId="pincode"
							maxlength="6" value="${leadDetails[0].pincode}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,6);" />
					</td>
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="phoneOff" styleClass="text"
							styleId="phoneOff" maxlength="10"
							value="${leadDetails[0].phoneOff}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,10);" />
					</td>
				</tr> --%>
<!-- pooja code starts for Applicant Pincode -->
<logic:present name="pincodeFlag">
<logic:equal value="N" name="pincodeFlag">
 <html:hidden property="pincodeFlag" styleId="pincodeFlag" styleClass="text" value="N"/>
<tr> 

		
					
					<td  width="13%">
						<bean:message key="lbl.country" />
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td  width="13%">
						<logic:present name="leadDetails">
							<html:text property="country" styleId="country" styleClass="text"
								tabindex="-1" readonly="true" value="${leadDetails[0].country}" />
							<input type="button" class="lovbutton" id="countryButton" disabled="true"
								onclick="openLOVCommon(255,'leadCapturingDetails','txtCountryCode','','','','','clearCountryLovChild','country')"
								value="" name="countryButton" />
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${leadDetails[0].txtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">

							<html:text property="country" styleId="country" styleClass="text"
								tabindex="-1" readonly="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton" disabled="true"
								onclick="openLOVCommon(255,'leadCapturingDetails','txtCountryCode','','','','','clearCountryLovChild','country')"
								value="" name="countryButton"/>
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
					<td width="13%">
						<bean:message key="lbl.state" />
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td width="13%">
						<html:text property="state" styleId="state" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].state}" />
						<input type="button" class="lovbutton" id="stateButton" disabled="true"
							onclick="openLOVCommon(5,'leadCapturingDetails','state','country','txtStateCode', 'txtCountryCode','Please select country first!!!','clearStateLovChild','txtStateCode')"
							value="" name="stateButton"/>
							<input type="hidden" name="txtStateCode" id="txtStateCode"
								value="${leadDetails[0].txtStateCode}" />
					</td>
	
</tr>

<tr>


<td width="13%">
						<bean:message key="lbl.dist" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="dist" styleId="dist" styleClass="text"
							tabindex="-1" readonly="true" value="${leadDetails[0].dist}" />
						<input type="button" class="lovbutton" id="distButton" disabled="true"
							onclick="openLOVCommon(257,'leadCapturingDetails','dist','state','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChild','txtDistCode')"
							value=" " name="distButton"/>
							<input type="hidden" name="txtDistCode" id="txtDistCode"
								value="${leadDetails[0].txtDistCode}" />
					</td>
					<td width="13%">
						<bean:message key="address.Tahsil" />
					</td>
					<td width="13%">
						<html:text property="tahsilDesc" styleId="tahsilDesc" styleClass="text" readonly="readonly" value="${leadDetails[0].tahsil}" />
						<%-- <input type="text" name="tahsilDesc" id="tahsilDesc" readonly="readonly" value="${leadDetails[0].tahsil}" /> --%>
						<html:hidden property="tahsil" styleId="tahsil" styleClass="text" value="${leadDetails[0].tahsilDesc}"/>
						<html:button property="tahsilButton" styleId="tahsilButton"  disabled="true" onclick="openLOVCommon(495,'leadCapturingDetails','tahsilDesc','dist','tahsilDesc', 'txtDistCode','Please select District first','','tahsil');" value=" " styleClass="lovbutton"> </html:button>
					</td>
					
</tr>


				<tr>
				<td width="13%">
						<bean:message key="lbl.pincode" />
						<!-- <span><font style="color: red;"> *</font> -->
						<!-- </span> -->
		</td>
				<td width="13%">
						<html:text property="pincode" styleClass="text" styleId="pincode" readonly="true" 
							maxlength="6" value="${leadDetails[0].pincode}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,6);" />
					</td>
					

					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="phoneOff" styleClass="text" readonly="true"
							styleId="phoneOff" maxlength="10"
							value="${leadDetails[0].phoneOff}"
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
			<td><html:text property="pincode" styleId="pincode" size="20" styleClass="text" readonly="true" value="${leadDetails[0].pincode}" tabindex="-1"></html:text>
            <html:hidden property="lbxPincode" styleId="lbxPincode" styleClass="text" value=""/>
 			<html:button property="pincodeButton" styleId="pincodeButton"  disabled="true" onclick="openLOVCommon(5080,'leadCapturingDetails','pincode','','', '','','getCountryStateDistrictTahsilValue','txnTahsilHID','hcommon');closeLovCallonLov('');" value=" " styleClass="lovbutton"> </html:button>
		</td>
			
		<td><bean:message key="address.Tahsil" /></td>	
			<td id="lov2"> 
            	<div id="tahsildetail">
            		<html:text property="tahsil" styleId="tahsil" size="20" styleClass="text" readonly="true" value="${leadDetails[0].tahsilDesc}" ></html:text>
                	<html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value="${leadDetails[0].tahsil}"/>
<%--  			   		<html:button property="tahsilButton" styleId="tahsilButton" onclick="closeLovCallonLov('dist');openLOVCommon(388,'PropertyForm','tahsil','dist','txnTahsilHID', 'txtDistCode','Please select District first','','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
   				</div>
   			</td>
   			
   			<td id="lov4" style="display: none"> 
            	<div id="tahsildetail">
            		<html:text property="tahsil4" styleId="tahsil4" size="20" styleClass="text"   disabled="true" value="${leadDetails[0].tahsil}" ></html:text>
                	<html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value="${leadDetails[0].txnTahsilHID}"/>
<%--  			   		<html:button property="tahsilButton" styleId="tahsilButton" onclick="closeLovCallonLov('dist');openLOVCommon(388,'PropertyForm','tahsil','dist','txnTahsilHID', 'txtDistCode','Please select District first','','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
   				</div>
   			</td>	
   			
   			
   			
	</tr>
	
	
	<tr id="lov5"> 
		<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font> -->
						</span></td>
          	<td >
          		<div id="cityID">
          			<html:text property="dist" styleId="dist" size="20" styleClass="text" readonly="true" value="${leadDetails[0].dist}" ></html:text>
          			<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${leadDetails[0].txtDistCode}"></html:hidden>
<%--    					<html:button property="distButton" styleId="districtButton" onclick="closeLovCallonLov('state');openLOVCommon(20,'PropertyForm','dist','state','txtDistCode', 'txtStateCode','Please select state first','clearTahsilLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
   				</div>
   			</td>
   			
   			<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="statedetail">
                	<html:text property="state" styleId="state" styleClass="text" size="20" value="${leadDetails[0].state}" readonly="true" ></html:text>
   					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${leadDetails[0].txtStateCode}"></html:hidden>
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton" style="display: none"  disabled="true" onclick="closeLovCallonLov('country');openLOVCommon(5,'leadCapturingDetails','state','country','txtStateCode', 'txtCountryCode','Please select country first','clearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	          	</div>
			</td>
		

	</tr>
	
	<tr id="lov6" style="display: none">
	
	<td><bean:message key="address.dist" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
          	<td>
          		<div id="cityID">
          			<html:text property="dist4" styleId="dist4" size="20" styleClass="text" readonly="true" value="${leadDetails[0].dist}" ></html:text>
          			<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${leadDetails[0].txtDistCode}"></html:hidden>
  					<html:button property="distButton" styleId="districtButton"  disabled="true" onclick="closeLovCallonLov('state6');openLOVCommon(20,'leadCapturingDetails','dist4','state6','txtDistCode', 'txtStateCode','Please select state first','clearTahsilLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   				</div>
   			</td>
	<td><bean:message key="address.state" /><!-- <span><font style="color: red;"> *</font>
						</span> --></td>
            <td>
                <div id="statedetail">
                	<html:text property="state6" styleId="state6" styleClass="text" size="20" value="${leadDetails[0].state}" readonly="true" ></html:text>
   					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${leadDetails[0].txtStateCode}"></html:hidden>
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton"  disabled="true" onclick="closeLovCallonLov('country');openLOVCommon(5,'leadCapturingDetails','state6','txtCountryCode','txtStateCode', 'txtCountryCode','Please select country first','clearStateLovChildState','hcommon');" value=" " styleClass="lovbutton"> </html:button> 
	          	</div>
			</td>
	
	
	
	</tr>
	
	<tr>
					<td width="13%">
						<bean:message key="lbl.country" />
						<!-- <span><font style="color: red;"> *</font>
						</span> -->
					</td>
					<td id="lov1" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="country" styleId="country" styleClass="text"
								 readonly="true" value="${leadDetails[0].country}" />
							<!-- <input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','country','','','','','clearCountryLovChild','txtCountryCode')"
								value="" name="countryButton" /> -->
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${leadDetails[0].txtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="country" styleId="country" styleClass="text"
								readonly="true"	value="${defaultcountry[0].defaultcountryname}" />
							<!-- <input type="button" class="lovbutton" id="countryButton"
								onclick="openLOVCommon(255,'leadCapturingDetails','txtCountryCode','','','','','clearCountryLovChild','country')"
								value="" name="countryButton"/> -->
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>
					
					
					<td id="lov3" style="display: none" width="13%">
						<logic:present name="leadDetails">
						<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="country" styleId="country" styleClass="text"
								 readonly="true" value="${leadDetails[0].country}" />
							<input type="button" class="lovbutton" id="countryButton"  disabled="true"
								onclick="openLOVCommon(255,'leadCapturingDetails','country','','','','','clearCountryLovChild','txtCountryCode')"
								value="" name="countryButton" />
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${leadDetails[0].txtCountryCode}" />
						</logic:present>
						<logic:notPresent name="leadDetails">
							<input type="hidden" name="hcommon" id="hcommon" />
							<html:text property="country" styleId="country" styleClass="text"
								 readonly="true"
								value="${defaultcountry[0].defaultcountryname}" />
							<input type="button" class="lovbutton" id="countryButton" disabled="true"
								onclick="openLOVCommon(255,'leadCapturingDetails','country','','','','','clearCountryLovChild','txtCountryCode')"
								value="" name="countryButton"/>
								<input type="hidden" name="txtCountryCode" id="txtCountryCode"
									value="${defaultcountry[0].defaultcountryid}" />
						</logic:notPresent>

					</td>

					
		
				
					
					<td width="13%">
						<bean:message key="lbl.mobileoff" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:text property="phoneOff" styleClass="text"
							styleId="phoneOff" maxlength="10" readonly="true"
							value="${leadDetails[0].phoneOff}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,10);" />
					</td>
	</tr>
	</logic:equal>
	</logic:present>
<!-- pooja code end for Applicant Pincode -->
				
				<tr>
					<td width="13%">
						<bean:message key="lbl.email" />
					</td>
					<td width="13%">
						<html:text property="email" styleClass="text" styleId="email" readonly="true"
							maxlength="100" value="${leadDetails[0].email}"/>
					</td>
					<td width="13%">
						<bean:message key="lbl.landmark" />
					</td>
					<td width="13%">
						<html:text property="landmark" styleClass="text" readonly="true"
							styleId="landmark" maxlength="25"
							value="${leadDetails[0].landmark}"
							onblur="caseChange('leadCapturingDetails','landmark');" />
					</td>
	</tr>
			
				

			</table>
		</fieldset>
		</logic:present>
		<!-- Rohit Changes for coApplicant ang gaurantor -->
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
							onclick="openLOVCommon(19252,'leadCapturingDetails','coApplbxIndustry','','', '','','','coAppindustry');closeLovCallonLov('');"
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
							value="${leadDetails[0].lbxIndustry}" />
						<html:button property="industryButton" styleId="industryButton"
							onclick="closeLovCallonLov1();openLOVCommon(19253,'leadCapturingDetails','gaurlbxIndustry','','', '','','','gaurindustry')"
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

		<!-- Rohit end -->
		<fieldset>
						<tr>
								<td>
									<logic:present name="NEW">
									
										<button type="button" name="coappDtl" id="coappDtl" title="Alt+E" accesskey="E"
										 class="topformbutton4" onclick="callPreDealCoappDtl('${leadDetails[0].leadId}','COAPPL');">
										 <bean:message key="button.coappDtl"/>
										 </button>
										 	<button type="button" name="gurDtl" id="gurDtl" title="Alt+E" accesskey="E"
										 class="topformbutton4" onclick="callPreDealGurDtl('${leadDetails[0].leadId}','GUARANTOR');">
										 <bean:message key="button.gaurDtl"/>
										 </button>
										 </logic:present>
										 <logic:notPresent name="NEW">
									
										<button type="button" name="coappDtl" id="coappDtl" title="Alt+E" accesskey="E"
										 class="topformbutton4" onclick="callPreDealCoappDtl('${leadDetails[0].leadId}','COAPPL');">
										 <bean:message key="button.coappDtl"/>
										 </button>
										 <button type="button" name="gurDtl" id="gurDtl" title="Alt+E" accesskey="E"
										 class="topformbutton4" onclick="callPreDealGurDtl('${leadDetails[0].leadId}','GUARANTOR');">
										 <bean:message key="button.gaurDtl"/>
										 </button>
										 </logic:notPresent>
									</td>
						 </tr>
						 <br></br>
		
							<tr>
								<td>
									<logic:present name="NEW">
										<button type="button" name="Save" id="Save"
											class="topformbutton2" onclick="return saveNewLead(id);"
											title="Alt+V" accesskey="V" >
											<bean:message key="button.save" />
										</button>
										
										<button type="button" name="dedupeReferral" id="dedupeReferral" title="Alt+E" accesskey="E"
										 class="topformbutton4" onclick="callDedupeReferralPreDeal('LE','${leadDetails[0].leadId}');">
										 <bean:message key="button.dedupeReferral"/>
										 </button>
										 	<button type="button" name="cibil" id="cibil" title="Alt+E" accesskey="E"
										 class="topformbutton4" onclick="callCibilPreDeal('LE','${leadDetails[0].leadId}');">
										 <bean:message key="button.cibil"/>
										 </button>
									</logic:present>
									<logic:notPresent name="NEW">
										<button type="button" name="Save" id="Save"
											class="topformbutton2" onclick="return saveLeadDetails(id,'');"
											title="Alt+V" accesskey="V" >
											<bean:message key="button.save" />
										</button>
										
									<%-- 	<button type="button" name="Delete" id="Delete"
											class="topformbutton2"
											onclick="return deleteLeadDetails(id);" title="Alt+D"
											accesskey="D" >
											<bean:message key="button.delete" />
										</button> --%>
										<button type="button" name="dedupeReferral" id="dedupeReferral" title="Alt+E" accesskey="E"
										 class="topformbutton4" onclick="callDedupeReferralPreDeal('LE','${leadDetails[0].leadId}');">
										 <bean:message key="button.dedupeReferral"/>
										 </button>
										 <logic:present name="leadDetails">
										<logic:iterate name="leadDetails" id="list">
										 <logic:equal property="lbxProductID" name="list" value="LAP">
										 <button type="button" name="imd" id="imd" title="Alt+I" accesskey="I"
										 class="topformbutton4" onclick="callIMDPreDeal('${leadDetails[0].dupDealId}');">
										 <bean:message key="button.captureIMD"/>
										 </button>
										 </logic:equal>
										 </logic:iterate>
										 </logic:present>
										 <button type="button" name="cibil" id="cibil" title="Alt+E" accesskey="E"
										 class="topformbutton4" onclick="callCibilPreDeal('LE','${leadDetails[0].leadId}');">
										 <bean:message key="button.cibil"/>
										 </button>
									</logic:notPresent>
									<html:hidden property="status" value="${leadDetails[0].status}"
										styleId="status"></html:hidden>
								</td>
								
							</tr>

							
		</fieldset>
		

							<logic:notPresent name="NEW">
							<fieldset>
							<tr>
														<td width="13%">
										<bean:message key="lbl.predealdecision" /><span><font style="color: red;">  *</font></span>
									</td>
									<td width="13%">
										<html:select property="decision" styleId="decision" styleClass="text" tabindex="3" value="${leadDetails[0].decision}" onchange="disableDate();">
										
										<html:option value="Approved">Approved</html:option>
										<html:option value="Rejected">Rejected</html:option>
										</html:select>
									</td>
									<td width="13%"><button type="button" name="Forward" id="Forward"
											class="topformbutton2" onclick="return saveTrackingDetails(id,'${userobject.businessdate}');"
											title="Alt+F" accesskey="F" >
											<bean:message key="button.forward" />
										</button><td>
										</tr>
										</fieldset>
							</logic:notPresent>	

		<script type="text/javascript">
			if(document.getElementById('leadGenerator').readOnly){
				document.getElementById('rmButton').focus();
			}
		</script>
		<logic:present name="msg">
			<script type="text/javascript">
			if('<%=request.getAttribute("msg").toString()%>'=='LT'){
				
				alert('<bean:message key="lbl.preDealForwarded"/>');
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehind.do?method=leadEntry&leadId=NEW";
				document.getElementById('leadCapturingDetails').submit();
		
			}
		if('<%=request.getAttribute("msg").toString()%>'=='dedupe'){
				
				alert('<bean:message key="lbl.dedupeCheck"/>');
				var leadId = document.getElementById('leadId').value;
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
				document.getElementById('leadCapturingDetails').submit();
			}
		if('<%=request.getAttribute("msg").toString()%>'=='Cibil'){
	
				alert('<bean:message key="lbl.bureauCheck"/>');
				var leadId = document.getElementById('leadId').value;
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
				document.getElementById('leadCapturingDetails').submit();
			}
		if('<%=request.getAttribute("msg").toString()%>'=='imd'){
			
			alert('Please Capture IMD Details first!!!');
			var leadId = document.getElementById('leadId').value;
			document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
			document.getElementById('leadCapturingDetails').submit();
		}
		if('<%=request.getAttribute("msg").toString()%>'=='M'){
			hideorshow(document.getElementById('leadGenerator1').value);
			san();
			alert('<bean:message key="lbl.dataSave"/>');
			var leadId = document.getElementById('leadId').value;
			document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
			document.getElementById('leadCapturingDetails').submit();
	
		}
	</script>
		</logic:present>
		<logic:present name="fw">
			<script type="text/javascript">
			if('<%=request.getAttribute("fw").toString()%>'=='S'){
				hideorshow(document.getElementById('leadGenerator1').value);
			 	san();
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
			 	san();
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
                var leadId = document.getElementById('leadId').value;
    			document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
    			document.getElementById('leadCapturingDetails').submit();
</script>   
</logic:present>
<logic:present name="panNoExist">
<script type="text/javascript">
				alert('<%=request.getAttribute("panNoExist")%>');
				<%-- var leadId = document.getElementById('leadId').value;
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
				document.getElementById('leadCapturingDetails').submit(); --%>
			
</script>	
</logic:present>
<logic:present name="uidExist">
<script type="text/javascript">
				alert('<%=request.getAttribute("uidExist")%>');
				<%-- var leadId = document.getElementById('leadId').value;
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/preDealCapturingBehindActionOnly.do?leadId="+ leadId;
				document.getElementById('leadCapturingDetails').submit(); --%>
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
	</html:form>
	<div align="center" class="opacity" style="display: none"
		id="processingImage"></div>

</body>
</html:html>
