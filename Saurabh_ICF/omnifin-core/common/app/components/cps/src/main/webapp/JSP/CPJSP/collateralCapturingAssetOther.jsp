<%--
      Author Name-      Amit Kumar
      Date of creation -26/09/2011
      Purpose-          Entry of Collateral Capturing Detail for other collaterals
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.login.roleManager.UserObject"%>
<html:html>
<head>
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		 
		 
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	UserObject userobj = (UserObject) session.getAttribute("userobject");
	String initiationDate = userobj.getBusinessdate();
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
<body onclick="parent_disable();"
	onload="enableAnchor();document.getElementById('othersForm').refrenceNo.focus();init_fields();">
	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
	
	<input type="hidden" name="contextPath" id="contextPath"
		value="<%=request.getContextPath() %>" />

	<div id=centercolumn>
		<div id=revisedcontainer>
			<html:form action="/collateralOtherVerification" styleId="othersForm"
				method="post">
				<html:javascript formName="CollateralCapturingOtherDynaValidatorForm"/>
				<logic:present name="fetchCollateralDetails">
				<fieldset>
					<legend>
						<bean:message key="lbl.collateralVerificationCapturingDetails" />
					</legend>
					<input type="hidden" name="formatD" id="formatD"
						value="<bean:message key="lbl.dateFormat"/>" />
									<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
					<input type="hidden" name="contextPath"
						value="<%=request.getContextPath()%>" id="contextPath" />
					<html:hidden property="collateralId" styleId="collateralId" 
						value="${requestScope.collateralId}" />
					<html:hidden property="dealId" styleId="dealId" 
						value="${requestScope.dealId}" />
					<html:hidden property="collateralClass" styleId="collateralClass" 
						value="${requestScope.collateralClass}" />
					<html:hidden property="verificationId" styleId="verificationId" 
						value="${requestScope.verificationId}" />
					
					<table cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<table cellspacing="1" cellpadding="1" width="100%">
									<tr>
										<td width="25%">
											<bean:message key="lbl.refrenceNo" />
										</td>
										<td width="25%">
											<html:text property="refrenceNo" styleId="refrenceNo"
												styleClass="text" value="${fetchCollateralDetails[0].refrenceNo}" 
												maxlength="20"/>
										</td>

										<td width="25%">
											<bean:message key="lbl.appraiserName" />
											<font color="red">*</font>
										</td>
										<td width="25%">
											<html:text property="appraiser" styleId="appraiser"
												styleClass="text"  onchange="return upperMe('appraiser');" value="${fetchCollateralDetails[0].appraiser}" 
												maxlength="50" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.appraisalDate" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="appraisalDate" styleId="appraisalDate"
												styleClass="text" value="${fetchCollateralDetails[0].appraisalDate}" 
												maxlength="10" onchange="return checkDate('appraisalDate');" />
										</td>


										<td>
											<bean:message key="lbl.reportDate" />
										</td>
										<td>

											<html:text property="reportDate" styleId="reportDate"
												maxlength="10" styleClass="text"
												value="<%=initiationDate %>" readonly="true" tabindex="-1" />

										</td>

									</tr>
									<tr>
										<td>
											<bean:message key="lbl.verificationMode" />
										</td>
										<td>
											<html:select property="verificationMode"
												styleId="verificationMode" styleClass="text"
												value="${fetchCollateralDetails[0].verificationMode}">
												<html:option value="F">
													Field Visit
												</html:option>
												<html:option value="P">
													Phone
												</html:option>
												<html:option value="W">
													Website
												</html:option>
											</html:select>
										</td>
										<td>
											<bean:message key="lbl.personMet" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="personMet" styleId="personMet"
												styleClass="text" value="${fetchCollateralDetails[0].personMet}" 
												maxlength="50" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.relation" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="relation" styleId="relation"
												styleClass="text" value="${fetchCollateralDetails[0].relation}" 
												maxlength="50" />
										</td>
										<td>
											<bean:message key="lbl.phone1" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="phone1" styleId="phone1"
												styleClass="text" value="${fetchCollateralDetails[0].phone1}" 
												maxlength="10" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.phone2" />
										</td>
										<td>
											<html:text property="phone2" styleId="phone2"
												styleClass="text" value="${fetchCollateralDetails[0].phone2}" 
												maxlength="20" />
										</td>
										<td>
											<bean:message key="lbl.email" />
										</td>
										<td>
											<html:text property="email" styleId="email" styleClass="text"
												value="${fetchCollateralDetails[0].email}" maxlength="100" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset>
					<legend>
						<bean:message key="lbl.description" />
					</legend>
					<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0">
						<TR>
							<TD>
								<TABLE cellSpacing="1" cellPadding="1" width="100%" border="0">
									<TR>
										<TD width="25%" >
											<bean:message key="lbl.otherCollateralDesc" />
											<font color="red">*</font>
										</TD>
										<TD width="25%">
											<html:text styleClass="text" styleId="otherCollateralDesc"
												maxlength="500" property="otherCollateralDesc"
												value="${fetchCollateralDetails[0].otherCollateralDesc}" />
										</TD>
										<td width="25%"></td>
										<td width="25%"></td>
									</TR>

									<tr>
										<td>
											<button type="button" id="save" name="Submit20"
												class="topformbutton2" title="Alt+V"
												accesskey="V" onclick="return saveOtherVerificationDetails();" ><bean:message key="button.save" /></button>
										</td>

									</tr>
								</TABLE>
					</TABLE>
				</fieldset>
				</logic:present>
				
				<!-- ************ Collateral Verification Completion Details ************ -->
				
				<logic:present name="fetchCollateralCompletionDetails">
				<fieldset>
					<legend>
						<bean:message key="lbl.collateralVerificationCompletionDetails" />
					</legend>
					<input type="hidden" name="formatD" id="formatD"
						value="<bean:message key="lbl.dateFormat"/>" />
					<input type="hidden" name="contextPath"
						value="<%=request.getContextPath()%>" id="contextPath" />
					<html:hidden property="collateralId" styleId="collateralId" 
						value="${requestScope.collateralId}" />
					<html:hidden property="dealId" styleId="dealId" 
						value="${requestScope.dealId}" />
					<html:hidden property="collateralClass" styleId="collateralClass" 
						value="${requestScope.collateralClass}" />
					<html:hidden property="verificationId" styleId="verificationId" 
						value="${requestScope.verificationId}" />
					
					<table cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<table cellspacing="1" cellpadding="1" width="100%">
									<tr>
										<td width="25%">
											<bean:message key="lbl.refrenceNo" />
											<font color="red">*</font>
										</td>
										<td width="25%">
											<html:text property="refrenceNo" styleId="refrenceNo"
												styleClass="text" value="${fetchCollateralCompletionDetails[0].refrenceNo}" 
												maxlength="20" disabled="true" tabindex="-1" />
										</td>

										<td width="25%">
											<bean:message key="lbl.appraiserName" />
											<font color="red">*</font>
										</td>
										<td width="25%">
											<html:text property="appraiser" styleId="appraiser"
												styleClass="text" value="${fetchCollateralCompletionDetails[0].appraiser}" 
												maxlength="50" disabled="true" tabindex="-1" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.appraisalDate" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="appraisalDate" styleId="appDate"
												styleClass="text" value="${fetchCollateralCompletionDetails[0].appraisalDate}" 
												maxlength="10" onchange="return checkDate('appraisalDate');"
												disabled="true" tabindex="-1" />
										</td>


										<td>
											<bean:message key="lbl.reportDate" />
										</td>
										<td>

											<html:text property="reportDate" styleId="reportDate"
												maxlength="10" styleClass="text"
												value="<%=initiationDate %>" disabled="true" tabindex="-1" />

										</td>

									</tr>
									<tr>
										<td>
											<bean:message key="lbl.verificationMode" />
										</td>
										<td>
											<html:select property="verificationMode"
												styleId="verificationMode" styleClass="text"
												value="${fetchCollateralCompletionDetails[0].verificationMode}"
												disabled="true" tabindex="-1" >
												<html:option value="F">
													Field Visit
												</html:option>
												<html:option value="P">
													Phone
												</html:option>
												<html:option value="W">
													Website
												</html:option>
											</html:select>
										</td>
										<td>
											<bean:message key="lbl.personMet" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="personMet" styleId="personMet"
												styleClass="text" value="${fetchCollateralCompletionDetails[0].personMet}" 
												maxlength="50" disabled="true" tabindex="-1" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.relation" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="relation" styleId="relation"
												styleClass="text" value="${fetchCollateralCompletionDetails[0].relation}" 
												maxlength="50" disabled="true" tabindex="-1" />
										</td>
										<td>
											<bean:message key="lbl.phone1" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="phone1" styleId="phone1"
												styleClass="text" value="${fetchCollateralCompletionDetails[0].phone1}" 
												maxlength="10" disabled="true" tabindex="-1" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.phone2" />
										</td>
										<td>
											<html:text property="phone2" styleId="phone2"
												styleClass="text" value="${fetchCollateralCompletionDetails[0].phone2}" 
												maxlength="20" disabled="true" tabindex="-1" />
										</td>
										<td>
											<bean:message key="lbl.email" />
										</td>
										<td>
											<html:text property="email" styleId="email" styleClass="text"
												value="${fetchCollateralCompletionDetails[0].email}" maxlength="100"
												disabled="true" tabindex="-1" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset>
					<legend>
						<bean:message key="lbl.description" />
					</legend>
					<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0">
						<TR>
							<TD>
								<TABLE cellSpacing="1" cellPadding="1" width="100%" border="0">
									<TR>
										<TD width="25%">
											<bean:message key="lbl.otherCollateralDesc" />
											<font color="red">*</font>
										</TD>
										<TD width="25%">
											<html:text styleClass="text" styleId="otherCollateralDesc"
												maxlength="500" property="otherCollateralDesc"
												value="${fetchCollateralCompletionDetails[0].otherCollateralDesc}"
												disabled="true" tabindex="-1" />
										</TD>
										<td width="25%"></td>
										<td width="25%"></td>
									</TR>

									<tr>
										<td>
											<button type="button" id="close" name="Submit20"
												class="topformbutton2" onclick="javascript:window.close();" ><bean:message key="button.close" /></button>
										</td>

									</tr>
								</TABLE>
					</TABLE>
				</fieldset>
				</logic:present>
			</html:form>
		</div>
	</div>
</body>
</html:html>