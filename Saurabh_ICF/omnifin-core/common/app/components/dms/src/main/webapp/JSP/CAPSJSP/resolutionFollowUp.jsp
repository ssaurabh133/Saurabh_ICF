
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %>
<%@ page language="java" import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<head>
<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>
<logic:present name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css" />
</logic:present>
<logic:notPresent name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css" />
</logic:notPresent>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/contactRecordingScript.js"></script>
<title><bean:message key="lbl.followUpResolDtl" /></title>
</head>
<body onload="enableAnchor();fntab();init_fields();">
	<html:form styleId="ResolutionActionForm" method="post" action="/followUpResolutionAction">

		<input type="hidden" name="contextPath"
			value="<%=request.getContextPath() %>" id="contextPath" />
		<input type="hidden" id="businessdate"
			value='${userobject.businessdate}' />

		<input type="hidden" name="formatD" id="formatD"
			value="<bean:message key="lbl.dateFormat"/>" />

		<br />
		<fieldset>
			<legend>
				<bean:message key="lbl.followUpResolDtl" />
			</legend>
		
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><input type="hidden" name="userId" id="userId"
						value="${userobject.userId}" />
						<table width="100%" border="0" cellspacing="1" cellpadding="1">

							<tr>

								<td width="20%"><bean:message key="lbl.actionCode" /> <font color="red">*</font></td>
								<td><html:text property="actionCode" styleClass="text" styleId="actionCode" value="${requestScope.ActionDesc}" maxlength="100" readonly="true"  /> 
								<html:hidden property="follActionId" styleId="actionId"  value="${requestScope.actionId}"/> 
								<html:hidden property="followupId" styleId="followupId"   value="${requestScope.followupId}" /></td>
							</tr>
							<tr>
							</tr>
							<tr>
								<td><bean:message key="lbl.resolutionStatus" /> <font color="red">*</font></td>
								<td><html:select  property="resolutionStatus" styleClass="text"  styleId="resolutionStatus"  >
							       <html:option value="" >--Select--</html:option>
							           <logic:present name="resolStatusList">
							     <logic:notEmpty name="resolStatusList">
							       <html:optionsCollection name="resolStatusList" label="resolStatusval" value="resolutionStatus" />
							     </logic:notEmpty>
							       </logic:present>
							       </html:select>
							     </td>
								<td></td><td></td>
								<td><bean:message key="lbl.ResolutionDate" />  <font color="red">*</font></td>
								<td><html:text property="resolutionDate" styleClass="text" tabindex="8"	styleId="resolutionDate" maxlength="10" readonly="true"	value="${userobject.businessdate}" /></td>
							</tr>
							<tr>
								<td><bean:message key="lbl.resolutionType" /> <font color="red">*</font></td>
								<td><html:select  property="resolutionType" styleClass="text"  styleId="resolutionType"  >
							       <html:option value="" >--Select--</html:option>
							           <logic:present name="resolTypeList">
							     <logic:notEmpty name="resolTypeList">
							       <html:optionsCollection name="resolTypeList" label="resolTypeval" value="resolutionType" />
							     </logic:notEmpty>
							       </logic:present>
							       </html:select>
							     </td>
								<td></td><td></td>
								<td width="13%"><bean:message key="lbl.ResolutionRemarks" /> <font color="red">*</font></td>
								<td><html:textarea property="resolutionRemarks" styleClass="text" styleId="resolutionRemarks"></html:textarea></td>
								
							</tr>
						</table>
						</td>
				</tr>
				<tr>
			<td align="left" >		   	
			    <button type="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveResolutionDtl();" ><bean:message key="button.save" /></button></td>
			   	<td align="left" >&nbsp;</td>
			   	<td align="left" >&nbsp;</td>
			   	<td >&nbsp;</td>
			</tr>
			<tr>
			</tr>
			</table>
			</fieldset>
			<fieldset>
		<fieldset>
		<bean:message key="lbl.followUpResolDtl" />
			<fieldset>
			
				<logic:present name="actionResolutionList">


					<display:table id="actionResolutionList"
						name="actionResolutionList" style="width: 100%" class="dataTable"
						pagesize="${requestScope.no}" cellspacing="1" partialList="true"
						size="${actionResolutionList[0].totalRecordSize}"
						requestURI="/collButtonAction.do">
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="locale.resolver"
							value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:setProperty name="locale.provider"
							value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>

						<display:column property="actionCode" titleKey="lbl.actionCode"
							sortable="true" />
						<display:column property="resolutionStatus"
							titleKey="lbl.resolutionStatus" sortable="true" />
						<display:column property="resolutionDate"
							titleKey="lbl.ResolutionDate" sortable="true" />
						<display:column property="resolutionRemarks"
							titleKey="lbl.ResolutionRemarks" sortable="true" />

					</display:table>
				</logic:present>



			</fieldset>
			<logic:notPresent name="actionResolutionList">



				<table width="100%" cellspacing="0" cellpadding="0" border="0">
					<tbody>
						<tr>
							<td class="gridtd">
								<table width="100%" cellspacing="1" cellpadding="1" border="0">
									<tbody>
										<tr align="center" class="white2">
											<td><b><bean:message key="lbl.actionCode" /> </b></td>
											<td><b><bean:message key="lbl.resolutionStatus" /> </b></td>
											<td><b><bean:message key="lbl.resolutionStatus" /> </b></td>
											<td><b><bean:message key="lbl.ResolutionRemarks" /> </b></td>

										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>

			</logic:notPresent>
		</fieldset>
	</html:form>

</body>
</html:html>
<logic:present name="msg">
	<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
		self.close();
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert('<bean:message key="msg.DataNotSaved" />');
		self.close();
	}
</script>
</logic:present>