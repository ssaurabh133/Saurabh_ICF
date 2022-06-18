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
		src="<%=request.getContextPath()%>/js/cpScript/cplead.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
					 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript">
	function newOne(){
			hideorshow(document.getElementById('leadGenerator').value);
	}
	function san(){
		if(document.getElementById("relationship").value == "New")
		{
			document.getElementById("relationshipSince").value = '0';
			document.getElementById("customerType1").removeAttribute("disabled");
			//document.getElementById("customerType").value = 'I';
			//document.getElementById("indconstitution").removeAttribute("disabled");
			//document.getElementById("corconstitution").removeAttribute("disabled");
			//document.getElementById("relationshipSince").setAttribute("disabled","true");
			
			if(document.getElementById("customerIdButton") != null){
				document.getElementById("customerIdButton").style.display='none';
			}
		
		}else {
			document.getElementById("customerType1").setAttribute("disabled","true");
			//document.getElementById("indconstitution").setAttribute("disabled","true");
			//document.getElementById("corconstitution").setAttribute("disabled","true");
			//document.getElementById("relationshipSince").removeAttribute("disabled");
			if(document.getElementById("customerIdButton") != null){
				document.getElementById("customerIdButton").style.display='';	
				document.getElementById("customerIdButton").focus();
			}
			return false;
		}
	
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

<body onload="enableAnchor();showHideDescLovOnLoad();hideorshow(document.getElementById('leadGenerator').value);custtype();san();">


	<html:form action="/leadCapturingBehindAction"
		styleId="leadCapturingDetails" method="post">
		<input type="hidden" name="leadcapt" id="leadcapt" />
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
     <input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
     <input type="hidden" id="emailMandatoryFlag" name="emailMandatoryFlag" value='${emailMandatoryFlag}'/>		   
		<html:hidden property="contextPath" styleId="contextPath"
			value="<%=request.getContextPath()%>" />
		<html:hidden property="leadGenerator1" styleClass="text"
			styleId="leadGenerator1" value="${leadDetails[0].leadGenerator1}" />
		<fieldset>

			<table cellSpacing=0 cellPadding=0 width="100%" border=0>
				<tr>
					<td class="gridtd">
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr class="white2">
								<td>
									<b><bean:message key="lbl.leadno" />
									</b>
								</td>
								<td>
									${leadDetails[0].leadId}
								</td>
								<td>
									<b><bean:message key="lbl.gendate" />
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
									LEAD CAPTURING
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

		</fieldset>
		<logic:present name="leadNew">
	
			<fieldset>
				<legend>
					<bean:message key="lbl.leadscapturing" />
				</legend>
				<table width="100%">
					<tr>
						<td width="13%">
							<bean:message key="lbl.leadgenerator" />
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
							<bean:message key="lbl.leadno" />
						</td>
						<td width="13%">
							<html:text property="leadId" styleId="leadId"
								value="${leadDetails[0].leadId}" readonly="true" tabindex="-1"
								style="text-align:right;" />
						</td>
					</tr>
				</table>
			</fieldset>
		</logic:present>

		<logic:present name="leadOther">

			<fieldset>
				<legend>
					<bean:message key="lbl.leadscapturing" />
				</legend>
				<table width="100%">
					<tr>
						<td width="13%">
							<bean:message key="lbl.leadgenerator" />
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
							<bean:message key="lbl.leadno" />
						</td>
						<td width="13%">
							<html:text property="leadId" styleId="leadId"
								value="${leadDetails[0].leadId}" tabindex="-2" readonly="true"
								style="text-align:right;" />
						</td>
					</tr>
				</table>
			</fieldset>
		</logic:present>
		<!-- /////////////////////////////////DIFFERENT START/////////////////////////////////////////// 
		<div id="gridList"><!-- Calling Data Through AJAX --</div>-->

		<logic:present name="leadNew">
		
			<fieldset id="rm">
			
				<legend>
					<bean:message key="lbl.leadGenDetail" />
				</legend>
				<table width="100%">

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
							<bean:message key="lbl.leadrmnameNew" />
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
							<bean:message key="lbl.branchname" />
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
							<span><font style="color: red;"> *</font>
							</span>
						</td>
						<td width="13%">
							<html:text property="leadgencityrm" styleId="leadgencityrm"
								styleClass="text" value="${leadRMDetails[0].leadgencityrm}"
								onblur="caseChange('leadCapturingDetails','leadgencityrm');"
								maxlength="50" />
						</td>

					</tr>


				</table>
			</fieldset>

		</logic:present>


		<logic:notPresent name="leadNew">

			<fieldset id="rm">
				<legend>
					<bean:message key="lbl.leadGenDetail" />
				</legend>
				<table width="100%">

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
							<bean:message key="lbl.leadrmnameNew" />
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
							<bean:message key="lbl.branchname" />
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
							<span><font style="color: red;"> *</font>
							</span>
						</td>
						<td width="13%">
							<html:text property="leadgencityrm" styleId="leadgencityrm"
								styleClass="text" value="${leadRMDetails[0].leadgencityrm}"
								onblur="caseChange('leadCapturingDetails','leadgencityrm');"
								maxlength="50" />
						</td>

					</tr>


				</table>
			</fieldset>

		</logic:notPresent>


		<fieldset id="vendor" style="display: none">
			<legend>
				<bean:message key="lbl.leadGenDetail" />
			</legend>
			<table width="100%">


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
						<bean:message key="lbl.branchname" />
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
						<span><font style="color: red;"> *</font>
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


			</table>
		</fieldset>


		<fieldset id="branch" style="display: none">
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
						<span><font style="color: red;"> *</font>
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
						<span><font style="color: red;"> *</font>
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
<!--				<td width="13%">-->
<!--                      <html:select styleId="sourceList" property="sourceList" styleClass="text" value="${leadDetails[0].sourceList}" onchange="return showHideDescLov();">-->
<!--							<html:option value="">---Select---</html:option>-->
<!--							<html:optionsCollection name="sourceList" label="name" value="id" />-->
<!--						</html:select>-->
<!--						</td>-->
					<!-- Nishant space starts -->
					<td width="13%">
					  	 <html:text property="sourceList" styleId="sourceList" styleClass="text" readonly="true" tabindex="-1" value="${leadDetails[0].sourceList}"></html:text>
						 <input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(535,'leadCapturingDetails','source','','','','','showHideDescLov','sourceList');" value=" " tabindex="1" name="dealButton" />
						 <html:hidden property="source" styleId="source" value="${leadDetails[0].source}"/>
					</td>	
					<!-- Nishant space ends -->
					<td width="13%">
						<bean:message key="lbl.genericDesciption" />
					</td>
					<!-- Nishant space starts -->
					<td width="13%">
						<input type="hidden" id="lbxDescription" name="lbxDescription"/>
						<html:text property="description" styleClass="text"	styleId="description" maxlength="100" value="${leadDetails[0].description}"	onblur="caseChange('leadCapturingDetails','description');"/>
						<html:button property="srcLOV" styleId="srcLOV" onclick="openDescLov();" value=" " styleClass="lovbutton"> </html:button>
					</td>
					<!-- Nishant space end -->
				</tr>
			</table>
		</fieldset>


		<!-- /////////////////////////////////DIFFERENT END/////////////////////////////////////////// -->
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
								styleClass="text" onchange="san();cleanUp();customerLov();"
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
						<html:select property="customerType1" styleClass="text"
							styleId="customerType1" value="${leadDetails[0].customerType}"
							onchange="updateCust(this.value);custtype();" >
							<html:option value="">--Select--</html:option>
							<html:option value="I">INDIVIDUAL</html:option>
							<html:option value="C">CORPORATE</html:option>
						</html:select>
						<html:hidden property="customerType" styleId="customerType" value="${leadDetails[0].customerType}" />
					</td>
					<td>

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
					

</tr>
				</table>
				</fieldset>



<div id='corporatefield' style="display: none;">
<fieldset>
			<legend> 
				<bean:message key="corporate.details" />
			</legend> 
			<table width="100%">
			<tr>
						<td width="13%" >
							<bean:message key="lbl.customername" />
							<span><font style="color: red;"> *</font> </span>
						</td>
						<td width="13%" >
							<html:text property="customerName" styleClass="text"
								styleId="customerName" maxlength="50"
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
						<font color="red">*</font>
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
						<font color="red">*</font>
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
							onchange="return upperMe('pan');" />
					</td>
					
					<td >
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
					</td>
					</tr>
	

</table>

</fieldset>
</div>


<div id='individualfield' style="display: none;">
<fieldset>
			<legend>
				<bean:message key="individual.details" />
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
				
					<td width="13%">
						<bean:message key="individual.last" />
						<font color="red">*</font>
					</td>
					<td width="13%">
						<html:text property="lastName" styleId="lastName"
							styleClass="text" value="${leadDetails[0].lastName}"
							onchange="return upperMe('lastName');" maxlength="50" />
					</td>
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
					<td>
						<bean:message key="passport" />
					</td>				
				
					<td>
						<html:text property="passport"
							styleId="passport" styleClass="text"
							value="${leadDetails[0].passport}" maxlength="20"/>
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
						<bean:message key="pan" />
					</td>
					<td>
						<html:text property="custPanInd" maxlength="10" styleClass="text"
							styleId="custPanInd" value="${leadDetails[0].custPanInd}" 
							onkeypress="return isAlphNumericKey(event);"
							onchange="return upperMe('pan');" />
					</td>
					</tr>
					
					<tr>
					<td >
						<bean:message key="lbl.salary" />
				</td>
					<td >
						<html:text property="salary" styleClass="text"
							styleId="salary" value="${leadDetails[0].salary}"
							maxlength="22" style="text-align:right; "
							onkeypress="return numbersonly(event, id, 18)"
							onblur="formatNumber(this.value, id);"
							onkeyup="checkNumber(this.value, event, 18, id);"
							onfocus="keyUpNumber(this.value, event, 18, id);" />
					</td>
				
				<td >
						<bean:message key="lbl.education" />
				</td>
				<td>
                      	<html:select styleId="eduDetail" property="eduDetail" styleClass="text" value="${leadDetails[0].eduDetail}" >
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
</div>

		<fieldset>
			<legend>
				<bean:message key="lbl.commDetails" />
			</legend>
			<table width="100%">


				<tr id="contactPersonDesignation" style="display: none;">
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
					

			</tr>
				<tr>	
					<td>
						<bean:message key="address.type" />
						<span><font style="color: red;"> *</font>
						</span>
					</td>
					<td width="13%">
						<html:select property="addresstype" styleClass="text"
							styleId="addresstype" value="${leadDetails[0].addresstype}">
							<html:option value="">--Select--</html:option>
							<html:optionsCollection name="addresstypeList"
								value="addresstypeid" label="addresstypename" />
						</html:select>
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
				<tr>
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
	</tr>
				<tr>			
					<td width="13%">
						<bean:message key="lbl.alter" />
					</td>
					<td width="13%">
						<html:text property="phoneRes" styleClass="text"
							styleId="phoneRes" maxlength="20"
							value="${leadDetails[0].phoneRes}"
							style="text-align:right;"
							onkeypress="return numericOnly(event,20);" />
					</td>
					<td width="13%">
						<bean:message key="lbl.email" />
					</td>
					<td width="13%">
						<html:text property="email" styleClass="text" styleId="email"
							maxlength="100" value="${leadDetails[0].email}"/>
					</td>
</tr>
				<tr>
					<td width="13%">
						<bean:message key="lbl.alternateemail" />
					</td>
					<td width="13%">
						<html:text property="altEmail" styleClass="text"
							styleId="altEmail" maxlength="100"
							value="${leadDetails[0].altEmail}" />
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
				<tr>			
					<td width="13%">
						<bean:message key="lbl.stability" />
						<font color="red">*</font>
					</td>
					<td>
						Year<html:text property="noOfYears" maxlength="3" styleId="noOfYears" value="${leadDetails[0].noOfYears}" styleClass="text7" onkeypress="return isNumberKey(event);"/>
						Month<html:select property="noMonths" styleId="noMonths" value="${leadDetails[0].noMonths}" styleClass="gcdAddress">
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
						<html:select property="owner" styleId="owner" value="${leadDetails[0].owner}" styleClass="text">
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
			   			
			     			
			      		<html:button property="areaCodeButton" styleId="areaCodeButton" onclick="openLOVCommon(370,'leadCapturingDetails','areaCodename','','', '','','','lbxareaCodeVal');" value=" " styleClass="lovbutton"></html:button>
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
									<strong><bean:message key="lbl.products" /> </strong><span><font
										style="color: red;"> *</font>
									</span>
								</td>
								<td>
									<strong><bean:message key="lbl.scheme" /> </strong><span><font
										style="color: red;"> *</font>
									</span>
								</td>
								<td>
									<strong><bean:message key="lbl.loanAmount" /> </strong><span><font
										style="color: red;"> *</font>
									</span>
								</td>
								<td style="width:10%">
									<strong><bean:message key="lbl.loanTenure" /> </strong><span><font
										style="color: red;"> *</font>
									</span>
								</td>
								<td>
									<strong><bean:message key="lbl.loanPurpose" /> </strong><span><font
										style="color: red;"> *</font>
									</span>
								</td>
								<td>
									<strong><bean:message key="lbl.loanType" /> </strong>
								</td>
								<td>
									<strong><bean:message key="lbl.assetDes" /> </strong>
								</td>
								<td>
									<strong><bean:message key="lbl.sectorType" /> </strong>
								</td>
							</tr>
							<tr class="white1">
								<td align="left">
									<html:text property="product" styleId="product" tabindex="-1"
										styleClass="text2" value="${leadDetails[0].product}"
										readonly="true" ></html:text>
									<input type="button" class="lovbutton" id="productButton"
										onclick="openLOVCommon(230,'leadCapturingDetails','lbxProductID','','scheme','lbxscheme','','','product')"
										value=" "  name="productButton" />
										<input type="hidden" name="lbxProductID" id="lbxProductID"
											value="${leadDetails[0].lbxProductID}" />
								</td>
								<td align="left">
									<input type="hidden" name="lbxscheme" id="lbxscheme" />
									<html:text property="scheme" styleClass="text2" tabindex="-1"
										styleId="scheme" value="${leadDetails[0].scheme}"
										readonly="true"  />
									<input type="button" class="lovbutton" id="schemeButton"
										onclick="openLOVCommon(252,'leadCapturingDetails','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId')"
										value="" name="schemeButton" />
										<input type="hidden" id="schemeId" name="schemeId"
											value="${leadDetails[0].schemeId}" />
								</td>
								<td align="left">
									<html:text property="loanAmount" styleClass="text2"
										styleId="loanAmount" maxlength="22"
										value="${leadDetails[0].loanAmount}" 
										
										onkeypress="return numbersonly(event, id, 18)"
										onblur="formatNumber(this.value, id);"
										onkeyup="checkNumber(this.value, event, 18, id);"
										onfocus="keyUpNumber(this.value, event, 18, id);" />
								</td>
								<td align="left">
									<html:text property="loanTenure" styleClass="text2"
										styleId="loanTenure" maxlength="3" style="width:100%"
										value="${leadDetails[0].loanTenure}" 
										
										onkeypress="return numericOnly(event,3);" />
								</td>
								<td align="left">
									<html:text property="loanPurpose" styleClass="text1" readonly="true"
										styleId="loanPurpose" maxlength="50"
										value="${leadDetails[0].loanPurpose}" 
										 />
									<input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(545,'leadCapturingDetails','loanPurpose','','','','','','loanPurpose');" value=" " tabindex="1" name="dealButton" />
								</td>
								
							<!-- Changes Start By Sanjog-->
								<td align="left">
									<html:select styleId="loanType" property="loanType" styleClass="text2" value="${leadDetails[0].loanType}"
										>
											<html:optionsCollection name="getLoanType" label="loanTypeName" value="loanTypeID" />
									</html:select>
								</td>
								<td align="left">
									<html:text property="otherDetails" styleClass="text2"
								styleId="otherDetails" maxlength="100"
								value="${leadDetails[0].otherDetails}" style="text-align:left;"/>
								</td>
								<td >
						            <html:select property="sectorType" styleClass="text2" styleId="sectorType" value="${leadDetails[0].sectorType}">
							               <html:optionsCollection name="sector" label="sectorName" value="sectorId" /> 
						          	</html:select>
						         </td>
							</tr>
						
							<!-- Changes End By Sanjog-->
							<tr>
								<td colspan="8" class="white1">
									<logic:present name="NEW">
										<button type="button" name="Save" id="Save"
											class="topformbutton2" onclick="return saveNewLead(id);"
											title="Alt+V" accesskey="V" >
											<bean:message key="button.save" />
										</button>
										<button type="button" name="Forward" id="Forward"
											class="topformbutton2" onclick="return saveLeadDetails(id,'<bean:message key="msg.confirmationForwardMsg" />');"
											title="Alt+F" accesskey="F" >
											<bean:message key="button.forward" />
										</button>
									</logic:present>
									<logic:notPresent name="NEW">
										<button type="button" name="Save" id="Save"
											class="topformbutton2" onclick="return saveLeadDetails(id,'');"
											title="Alt+V" accesskey="V" >
											<bean:message key="button.save" />
										</button>
										<button type="button" name="Forward" id="Forward"
											class="topformbutton2" onclick="return saveLeadDetails(id,'<bean:message key="msg.confirmationForwardMsg" />');"
											title="Alt+F" accesskey="F" >
											<bean:message key="button.forward" />
										</button>
										<button type="button" name="Delete" id="Delete"
											class="topformbutton2"
											onclick="return deleteLeadDetails(id);" title="Alt+D"
											accesskey="D" >
											<bean:message key="button.delete" />
										</button>
									</logic:notPresent>
									<html:hidden property="status" value="${leadDetails[0].status}"
										styleId="status"></html:hidden>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</fieldset>
		<script type="text/javascript">
			if(document.getElementById('leadGenerator').readOnly){
				document.getElementById('rmButton').focus();
			}
		</script>
		<logic:present name="msg">
			<script type="text/javascript">
		if('<%=request.getAttribute("msg").toString()%>'=='M'){
			hideorshow(document.getElementById('leadGenerator1').value);
			san();
			alert('<bean:message key="lbl.dataSave"/>');
			var leadId = document.getElementById('leadId').value;
			document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/leadCapturingBehindActionOnly.do?leadId="+ leadId;
			document.getElementById('leadCapturingDetails').submit();
	
		}
	</script>
		</logic:present>
		<logic:present name="fw">
			<script type="text/javascript">
			if('<%=request.getAttribute("fw").toString()%>'=='S'){
				hideorshow(document.getElementById('leadGenerator1').value);
			 	san();
				alert('<bean:message key="lbl.leadForwarded"/>');
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
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/leadCapturingBehindActionOnly.do?leadId="+ leadId;
				document.getElementById('leadCapturingDetails').submit();

	}
</script>
		</logic:present>
		<logic:present name="deletelead">
			<script type="text/javascript">
			if('<%=request.getAttribute("deletelead")
												.toString()%>'=='delete'){
			
				alert('<bean:message key="msg.leaddeleted"/>');
				document.getElementById('leadCapturingDetails').action="<%=request.getContextPath()%>/leadCapturingSearchBehind.do";
				document.getElementById('leadCapturingDetails').submit();

	}
</script>
		</logic:present>
<logic:present name="group">
<script type="text/javascript">
	
				alert('<%=request.getAttribute("group")%>');	
</script>	
</logic:present>
	</html:form>
	<div align="center" class="opacity" style="display: none"
		id="processingImage"></div>

</body>
</html:html>
