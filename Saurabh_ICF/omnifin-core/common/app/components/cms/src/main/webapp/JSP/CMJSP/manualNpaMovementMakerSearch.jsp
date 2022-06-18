<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<%@ page language="java" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";
			ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.ApplicationResources");
			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
	%>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	<logic:present name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/${css }/displaytable.css" />
	</logic:present>

	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/css/theme1/displaytable.css" />
	</logic:notPresent>
<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/manual_npa.js"></script>
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

<body
	onload="enableAnchor();document.getElementById('additionalDisbSearchForm').loanLov.focus();init_fields();"
	oncontextmenu="return false" 
	onunload="closeAllLovCallUnloadBody();">

	<div id="revisedcontainer">

			<html:form action="/manualNpaMovementMakerSearch" method="post"
				styleId="manualNpaMovementForm">
<input type="hidden" id="userId" value="${userId}"/>
				<fieldset>
					<legend>
						<bean:message key="lbl.manualnpamovement" />
					</legend>

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>

								<table width="100%" border="0" cellspacing="1" cellpadding="1">

									<tr>

										<td width="20%">
											<bean:message key="lbl.loanNo" />
										</td>
										<td width="35%">
										<logic:notPresent name="author">
													<logic:present name="maker">
											<html:text styleClass="text" property="loanNo"
												styleId="loanNo" value="" readonly="true" tabindex="-1" />
											<html:button property="loanLov"
												onclick="openLOVCommon(322,'manualNpaMovementForm','loanNo','','', '','','','lbxLoanNo');closeLovCallonLov1();"
												value=" " styleClass="lovButton" />
											<input type="hidden" name="contextPath"
												value="<%=request.getContextPath()%>" id="contextPath" />
											<html:hidden property="lbxLoanNo" styleId="lbxLoanNo"
												value="" />
												</logic:present>
												</logic:notPresent>
													<logic:present name="author">
													
													<html:text styleClass="text" property="loanNo"
												styleId="loanNo" value="" readonly="true" tabindex="-1" />
												<input type="hidden" id="userId" value="${userId}"/>
											<html:button property="loanLov"
												onclick="openLOVCommon(323,'manualNpaMovementForm','loanNo','userId','','userId','','','lbxLoanNo');closeLovCallonLov1();"
												value=" " styleClass="lovButton" />
											<input type="hidden" name="contextPath"
												value="<%=request.getContextPath()%>" id="contextPath" />
											<html:hidden property="lbxLoanNo" styleId="lbxLoanNo"
												value="" />
												</logic:present>
										</td>
										<td nowrap="nowrap">
											<bean:message key="lbl.customerName" />
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="searchCName"
												styleId="searchCName" value="" maxlength="50" />
										</td>

									</tr>
									<tr>
									</td>
										<td nowrap="nowrap">
											<bean:message key="lbl.userName" />
										</td>
									
									<td>
									<logic:notPresent name="author">
											<logic:present name="maker">
											<html:hidden styleId="reportingToUserId" styleClass="text" value="" property="reportingToUserId"/>
											<input id="userName" class="text" type="text" readonly="readonly" value="" maxlength="100" name="userName"/>
											<input type="hidden" id="userId" value="${userId}"/>
											<input id="lbxUserId" type="hidden" value="" name="lbxUserId"/>
											<input id="userButton" class="lovbutton" type="button" onclick="openLOVCommon(266,'manualNpaMovementForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " tabindex="1" name="dealButton"/>
										</logic:present>
									</logic:notPresent>
										
									<logic:present name="author">
											<html:hidden styleId="reportingToUserId" styleClass="text" value="" property="reportingToUserId"/>
											<input id="userName" class="text" type="text" readonly="readonly" value="" maxlength="100" name="userName"/>
													<input type="hidden" id="userId" value="${userId}"/>
											<input id="lbxUserId" type="hidden" value="" name="lbxUserId"/>
											<input id="userButton" class="lovbutton" type="button" onclick="openLOVCommon(282,'manualNpaMovementForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " tabindex="1" name="dealButton"/>
									</logic:present>
									</td>
									</tr>
									

									<tr>
										<td>
									<logic:notPresent name="author">
										<logic:present name="maker">
										   	<button type="button" name="search" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="search_manual_npa();"><bean:message key="button.search" /></button>
		                               
		                                    <button type="button" name="new" id="button" class="topformbutton2" title="Alt+N" accesskey="N" onclick="new_manual_npa();" ><bean:message key="button.new" /></button>
		     							</logic:present>
		     						</logic:notPresent>
		     		
		     							 <logic:present name="author">
										   <button type="button" name="search" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="search_manual_npa_author();"><bean:message key="button.search" /></button>
		                               </logic:present>
											</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td></td>
									</tr>
								</table>

							</td>
						</tr>

					</table>

				</fieldset>

			</html:form>
		</div>

		<fieldset>
			<legend>
				<bean:message key="lbl.manualnpamovementMakerRecords" />
			</legend>

			<logic:present name="list">
				<logic:notEmpty name="list">
					<logic:notPresent name="author">
					<logic:present name="maker">
						<display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/manualNpaMovementMakerSearch.do">
							<display:setProperty name="paging.banner.placement" value="bottom" />
							<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
							<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
							<display:column property="loanNo" titleKey="lbl.loanNo" sortable="true" />
							<display:column property="customerName" titleKey="lbl.customerName" sortable="true" />
							<display:column property="lastnpastage" titleKey="lbl.lastnpaStatus" sortable="true" />
							<display:column property="currnpastage" titleKey="lbl.currentnpaStatus" sortable="true" />
							<display:column property="userId" titleKey="lbl.userName" sortable="true" />
				
						</display:table>
					</logic:present>
					</logic:notPresent>
					<logic:present name="author">
					<display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/manualNpaMovementAuthorSearch.do">
							<display:setProperty name="paging.banner.placement" value="bottom" />
							<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
							<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
							<display:column property="loanNo" titleKey="lbl.loanNo" sortable="true" />
							<display:column property="customerName" titleKey="lbl.customerName" sortable="true" />
							<display:column property="lastnpastage" titleKey="lbl.lastnpaStatus" sortable="true" />
							<display:column property="currnpastage" titleKey="lbl.currentnpaStatus" sortable="true" />
							<display:column property="userId" titleKey="lbl.userName" sortable="true" />
				
						</display:table>
					</logic:present>
				</logic:notEmpty>
				<logic:empty name="list">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="gridtd">
								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr class="white2">
										<td>
											<strong><bean:message key="lbl.loanNo" /></strong>
										</td>
										<td>
											<strong><bean:message key="lbl.customerName" /> </strong>
										</td>
										<td>
											<b><bean:message key="lbl.lastnpaStatus" /> </b>
										</td>
										<td>
											<b><bean:message key="lbl.currentnpaStatus" /> </b>
										</td>
										<td>
											<b><bean:message key="lbl.userName" /> </b>
										</td>
									</tr>
									<tr class="white2">
									<td colspan="7"> 
									<bean:message key="lbl.noDataFound" />
									</td>
									</tr>
								</table>
							</td>
						</tr>
					
						</table>

				</logic:empty>
			</logic:present>
		</fieldset>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
</body>

</html:html>

<logic:present name="message">

	<script type="text/javascript">
	if('<%=request.getAttribute("message")%>'=='S')
{
	alert("<bean:message key="msg.DataSaved" />");
}
	else if('<%=request.getAttribute("message")%>'=='E')
	{
		alert('<bean:message key="msg.DataNotSaved" />');
	}
	else if('<%=request.getAttribute("message")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		
	}
	
</script>
</logic:present>