<%--
      Author Name-      Amit Kumar
      Date of creation -23/09/2011
      Purpose-          Entry of Collateral Capturing Vehicle
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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript"
	src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	onload="enableAnchor();document.getElementById('vehicleForm').refrenceNo.focus();">
	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
	
	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
		<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<html:hidden property="businessdate" styleId="businessdate"
		value="${userobject.businessdate }" />
	<input type="hidden" name="contextPath" id="contextPath"
		value="<%=request.getContextPath()%>" />

	<div id=centercolumn>
		<div id=revisedcontainer>
			<html:form action="/collateralVehicleVerification"
				styleId="vehicleForm" method="post">
				<html:javascript formName="CollateralCapturingVehicleDynaValidatorForm"/>
				<logic:present name="fetchCollateralDetails">
				<fieldset>
					<legend>
						<bean:message key="lbl.collateralVerificationCapturingDetails" />
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
												styleClass="text" onchange="return upperMe('appraiser');" value="${fetchCollateralDetails[0].appraiser}"
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
												maxlength="10" onchange="return checkDate('appraisalDate');"/>
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
												value="${fetchCollateralDetails[0].email}" 
												maxlength="100" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset>
					<legend>
						<bean:message key="lbl.vehicleDetails" />
					</legend>
					<table cellSpacing="0" cellPadding="0" width="100%" border="0">
						<tr>
							<td>
								<table cellSpacing="1" cellPadding="1" width="100%" border="0">
									
									<tr>
										<td width="25%">
											<bean:message key="lbl.vehicleMake" />
											<font color="red">*</font>
										</td>
										<td width="25%">
											<html:text styleClass="text" styleId="vehicleMake"
												property="vehicleMake" maxlength="50"
												value="${fetchCollateralDetails[0].vehicleMake}" />
										</td>
										<td width="25%">
											<bean:message key="lbl.vehicleModel" />
											<font color="red">*</font>
										</td>
										<td width="25%">
											<html:text styleClass="text" styleId="vehicleModel"
												property="vehicleModel" maxlength="50"
												value="${fetchCollateralDetails[0].vehicleModel}" />
										</td>

									</tr>

									<tr>
										<td>
											<bean:message key="lbl.vehicleType" />
										</td>
										<td>
											<html:select property="vehicleType" styleId="vehicleType"
												styleClass="text" value="${fetchCollateralDetails[0].vehicleType}">
												<html:option value="Commercial">
													<bean:message key="lbl.commercial" />
												</html:option>
												<html:option value="Private">
													<bean:message key="lbl.private" />
												</html:option>
											</html:select>
										</td>
										<td>
											<bean:message key="lbl.vehicleOwner" />
											<font color="red">*</font>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleOwner"
												property="vehicleOwner" maxlength="50"
												value="${fetchCollateralDetails[0].vehicleOwner}" />
										</td>
									</tr>

									<tr>
										<td>
											<bean:message key="lbl.yearofManufacture" />(MM-YYYY)
											<font color="red">*</font>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleYearOfManufact"
												property="vehicleYearOfManufact"
												value="${fetchCollateralDetails[0].vehicleYearOfManufact}"
												onblur="checkFormate(this.value,this.id)" />
										</td>
										<td>
											<bean:message key="lbl.registrationNumber" />
											<font color="red">*</font>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleRegNo"
												property="vehicleRegNo" maxlength="25"
												value="${fetchCollateralDetails[0].vehicleRegNo}" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.registrationDate" />
											<font color="red">*</font>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleRegDate"
												property="vehicleRegDate"
												value="${fetchCollateralDetails[0].vehicleRegDate}"
												onchange="return checkDate('vehicleRegDate');" />
										</td>
										<td>
											<bean:message key="lbl.chasisNumber" />
											<font color="red">*</font>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleChesisNo"
												property="vehicleChesisNo" maxlength="25"
												value="${fetchCollateralDetails[0].vehicleChesisNo}" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.Insurer" />
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleInsurer"
												property="vehicleInsurer" maxlength="50"
												value="${fetchCollateralDetails[0].vehicleInsurer}" />
										</td>
										<td>
											<bean:message key="lbl.insuredDate" />
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleInsureDate"
												property="vehicleInsureDate"
												value="${fetchCollateralDetails[0].vehicleInsureDate}"
												onchange="return checkDate('vehicleInsureDate');" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.vehicleMarketPrice"/>
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="vehicleMarketPrice" 
												styleId="vehicleMarketPrice" styleClass="text"
												style="text-align:right;" maxlength="18"
												value="${fetchCollateralDetails[0].vehicleMarketPrice}"
												onkeypress="return numbersonly(event, id, 18)"
												onblur="formatNumber(this.value, id);"
												onkeyup="checkNumber(this.value, event, 18, id);"
												onfocus="keyUpNumber(this.value, event, 18, id);"/>
										</td>
										<td>
											<bean:message key="lbl.vehicleCondition"/>
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="vehicleCondition" styleId="vehicleCondition"
												styleClass="text" maxlength="50" 
												value="${fetchCollateralDetails[0].vehicleCondition}"/>
										</td>
									</tr>
									<tr>
										<td>
											<button type="button" name="Submit20" id="save"
												class="topformbutton2" title="Alt+V" accesskey="V"
												onclick="return saveVehicleVerificationDetails();" ><bean:message key="button.save" /></button>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
				</logic:present>
				
				<!-- *********************** Collateral Verification Completion Details ********** -->
				
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
												maxlength="10" disabled="true" tabindex="-1" />
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
												disabled="true" tabindex="-1">
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
												value="${fetchCollateralCompletionDetails[0].email}" 
												maxlength="100" disabled="true" tabindex="-1" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset>
					<legend>
						<bean:message key="lbl.vehicleDetails" />
					</legend>
					<table cellSpacing="0" cellPadding="0" width="100%" border="0">
						<tr>
							<td>
								<table cellSpacing="1" cellPadding="1" width="100%" border="0">
									
									<tr>
										<td width="25%">
											<bean:message key="lbl.vehicleMake" />
											<font color="red">*</font>
										</td>
										<td width="25%">
											<html:text styleClass="text" styleId="vehicleMake"
												property="vehicleMake" maxlength="50"
												value="${fetchCollateralCompletionDetails[0].vehicleMake}"
												disabled="true" tabindex="-1" />
										</td>
										<td width="25%">
											<bean:message key="lbl.vehicleModel" />
											<font color="red">*</font>
										</td>
										<td width="25%">
											<html:text styleClass="text" styleId="vehicleModel"
												property="vehicleModel" maxlength="50"
												value="${fetchCollateralCompletionDetails[0].vehicleModel}"
												disabled="true" tabindex="-1" />
										</td>

									</tr>

									<tr>
										<td>
											<bean:message key="lbl.vehicleType" />
										</td>
										<td>
											<html:select property="vehicleType" styleId="vehicleType"
												styleClass="text" value="${fetchCollateralCompletionDetails[0].vehicleType}"
												disabled="true" tabindex="-1">
												<html:option value="Commercial">
													<bean:message key="lbl.commercial" />
												</html:option>
												<html:option value="Private">
													<bean:message key="lbl.private" />
												</html:option>
											</html:select>
										</td>
										<td>
											<bean:message key="lbl.vehicleOwner" />
											<font color="red">*</font>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleOwner"
												property="vehicleOwner" maxlength="50"
												value="${fetchCollateralCompletionDetails[0].vehicleOwner}"
												disabled="true" tabindex="-1" />
										</td>
									</tr>

									<tr>
										<td>
											<bean:message key="lbl.yearofManufacture" />(MM-YYYY)
											<font color="red">*</font>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleYrOfManufact"
												property="vehicleYearOfManufact"
												value="${fetchCollateralCompletionDetails[0].vehicleYearOfManufact}"
												onchange="return checkDate('vehicleYearOfManufact');"
												disabled="true" tabindex="-1" />
										</td>
										<td>
											<bean:message key="lbl.registrationNumber" />
											<font color="red">*</font>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleRegNo"
												property="vehicleRegNo" maxlength="25"
												value="${fetchCollateralCompletionDetails[0].vehicleRegNo}"
												disabled="true" tabindex="-1" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.registrationDate" />
											<font color="red">*</font>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleRegDt"
												property="vehicleRegDate"
												value="${fetchCollateralCompletionDetails[0].vehicleRegDate}"
												onchange="return checkDate('vehicleRegDate');"
												disabled="true" tabindex="-1" />
										</td>
										<td>
											<bean:message key="lbl.chasisNumber" />
											<font color="red">*</font>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleChesisNo"
												property="vehicleChesisNo" maxlength="25"
												value="${fetchCollateralCompletionDetails[0].vehicleChesisNo}"
												disabled="true" tabindex="-1" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.Insurer" />
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleInsurer"
												property="vehicleInsurer" maxlength="50"
												value="${fetchCollateralCompletionDetails[0].vehicleInsurer}"
												disabled="true" tabindex="-1" />
										</td>
										<td>
											<bean:message key="lbl.insuredDate" />
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" styleId="vehicleInsureDt"
												property="vehicleInsureDate"
												value="${fetchCollateralCompletionDetails[0].vehicleInsureDate}"
												onchange="return checkDate('vehicleInsureDate');"
												disabled="true" tabindex="-1" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.vehicleMarketPrice"/>
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="vehicleMarketPrice" 
												styleId="vehicleMarketPrice" styleClass="text"
												style="text-align:right;" maxlength="18"
												value="${fetchCollateralCompletionDetails[0].vehicleMarketPrice}"
												onkeypress="return numbersonly(event, id, 18)"
												onblur="formatNumber(this.value, id);"
												onkeyup="checkNumber(this.value, event, 18, id);"
												onfocus="keyUpNumber(this.value, event, 18, id);"
												disabled="true" tabindex="-1"/>
										</td>
										<td>
											<bean:message key="lbl.vehicleCondition"/>
											<font color="red">*</font>
										</td>
										<td>
											<html:text property="vehicleCondition" styleId="vehicleCondition"
												styleClass="text" maxlength="50" 
												value="${fetchCollateralCompletionDetails[0].vehicleCondition}"
												disabled="true" tabindex="-1" />
										</td>
									</tr>
									<tr>
										<td>
											<button type="button" name="Submit20"  id="close" title="Alt+C" accesskey="C" 
												class="topformbutton2"
												onclick="javascript:window.close();" ><bean:message key="button.close" /></button>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
				</logic:present>
			</html:form>
		</div>
	</div>
</body>
</html:html>