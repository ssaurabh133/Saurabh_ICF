<%--
      Author Name-     Amit Kumar
      Date of creation -23/09/2011
      Purpose-          Entry of Collateral Verification for Stocks
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
	onload="enableAnchor();document.getElementById('stockForm').refrenceNo.focus();">
	<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
	<html:hidden property="businessdate" styleId="businessdate"
		value="${userobject.businessdate }" />
		<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="contextPath" id="contextPath"
		value="<%=request.getContextPath()%>" />

	<div id=centercolumn>
		<div id=revisedcontainer>
			<html:form action="/collateralStockVerification"
				styleId="stockForm" method="post">
				<html:javascript formName="CollateralCapturingStockDynaValidatorForm"/>
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
						<bean:message key="lbl.stockDetails" />
					</legend>
					<table cellSpacing="0" cellPadding="0" width="100%" border="0">
						<tr>
							<td>
								<table cellSpacing="1" cellPadding="1" width="100%" border="0">
									<tr>
										<td width="25%">
											<bean:message key="lbl.stockType" />
										</td>
										<td width="25%">
											<html:select property="stockType" styleId="stockType"
												styleClass="text"
												value="${fetchCollateralDetails[0].stockType}">
												<html:option value="Raw Material">
													Raw Material
												</html:option>
												<html:option value="Semi Finished">
													Semi Finished
												</html:option>
												<html:option value="Finished">
													Finished
												</html:option>
											</html:select>
										</td>
										<td width="25%">
											<bean:message key="lbl.stockNature" />
										</td>
										<td width="25%">
											<html:select property="stockNature" styleId="stockNature"
												styleClass="text"
												value="${fetchCollateralDetails[0].stockNature}">
												<html:option value="Perishable">
													Perishable
												</html:option>
												<html:option value="Non Perishable">
													Non Perishable
												</html:option>
											</html:select>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.stockGodownAddress" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text styleClass="text" styleId="stockAddress"
												maxlength="50" property="stockAddress"
												value="${fetchCollateralDetails[0].stockAddress}" />
										</td>
										<td>
											<bean:message key="lbl.stockInventoryCycle" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text styleClass="text" styleId="stockCycle"
												maxlength="50" property="stockCycle"
												value="${fetchCollateralDetails[0].stockCycle}" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.stockValue"/>
											<font color="red">*</font>
										</td>
										<td><html:text property="stockValue" styleId="stockValue"
												styleClass="text" style="text-align:right;"
												value="${fetchCollateralDetails[0].stockValue}" 
												onkeypress="return numbersonly(event, id, 18)"
												onblur="formatNumber(this.value, id);"
												onkeyup="checkNumber(this.value, event, 18, id);"
												onfocus="keyUpNumber(this.value, event, 18, id);"/>
										</td>
									</tr>
									<tr>
										<td>
											<button type="button" name="Submit20" id="save"
												title="Alt+V" accesskey="V" class="topformbutton2"
												onclick="return saveStockVerificationDetails();" ><bean:message key="button.save" /></button>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
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
						<bean:message key="lbl.stockDetails" />
					</legend>
					<table cellSpacing="0" cellPadding="0" width="100%" border="0">
						<tr>
							<td>
								<table cellSpacing="1" cellPadding="1" width="100%" border="0">
									<tr>
										<td width="25%">
											<bean:message key="lbl.stockType" />
										</td>
										<td width="25%">
											<html:select property="stockType" styleId="stockType"
												styleClass="text"
												value="${fetchCollateralCompletionDetails[0].stockType}"
												disabled="true" tabindex="-1" >
												<html:option value="Raw Material">
													Raw Material
												</html:option>
												<html:option value="Semi Finished">
													Semi Finished
												</html:option>
												<html:option value="Finished">
													Finished
												</html:option>
											</html:select>
										</td>
										<td width="25%">
											<bean:message key="lbl.stockNature" />
										</td>
										<td width="25%">
											<html:select property="stockNature" styleId="stockNature"
												styleClass="text"
												value="${fetchCollateralCompletionDetails[0].stockNature}"
												disabled="true" tabindex="-1" >
												<html:option value="Perishable">
													Perishable
												</html:option>
												<html:option value="Non Perishable">
													Non Perishable
												</html:option>
											</html:select>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.stockGodownAddress" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text styleClass="text" styleId="stockAddress"
												maxlength="50" property="stockAddress"
												value="${fetchCollateralCompletionDetails[0].stockAddress}"
												disabled="true" tabindex="-1" />
										</td>
										<td>
											<bean:message key="lbl.stockInventoryCycle" />
											<font color="red">*</font>
										</td>
										<td>
											<html:text styleClass="text" styleId="stockCycle"
												maxlength="50" property="stockCycle"
												value="${fetchCollateralCompletionDetails[0].stockCycle}"
												disabled="true" tabindex="-1" />
										</td>
									</tr>
									<tr>
										<td>
											<bean:message key="lbl.stockValue"/>
											<font color="red">*</font>
										</td>
										<td><html:text property="stockValue" styleId="stockValue"
												styleClass="text" style="text-align:right;"
												value="${fetchCollateralCompletionDetails[0].stockValue}" 
												onkeypress="return numbersonly(event, id, 18)"
												onblur="formatNumber(this.value, id);"
												onkeyup="checkNumber(this.value, event, 18, id);"
												onfocus="keyUpNumber(this.value, event, 18, id);"
												disabled="true" tabindex="-1" />
										</td>
									</tr>
									<tr>
										<td>
											<button type="button" name="Submit20" id="close" title="Alt+C" accesskey="C" 
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