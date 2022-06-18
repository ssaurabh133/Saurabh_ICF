<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>

	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
		request.setAttribute("no",no); 
	%>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	<logic:present name="css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
	</logic:present>
			
	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
	</logic:notPresent>

	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/sdLiquidation.js"></script>

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
<body oncontextmenu="return false" onclick="parent_disable()" onload="enableAnchor();document.getElementById('liquidationSearchForm').loanLov.focus();init_fields();" onunload="closeAllLovCallUnloadBody();">
<logic:present name="sdLiquidationMakerSearch">

		<div id="revisedcontainer">

			<html:form action="/sdLiquidationSearch" method="post"
				styleId="liquidationSearchForm">

				<fieldset>
					<legend>
						<bean:message key="lbl.SDLiquidationMakerSearch"/>
					</legend>

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>

								<table width="100%" border="0" cellspacing="1" cellpadding="1">

									<tr>
										<td nowrap="nowrap">
											<bean:message key="lbl.loanNo"/>
										</td>
										
										<td nowrap="nowrap">
										<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
											<html:text styleClass="text" property="loanNo" value=""
												styleId="loanNo"  readonly="true" maxlength="20" />
											<html:button property="loanLov" styleId="loanLov" value=" " styleClass="lovbutton"
											 onclick="openLOVCommon(126,'liquidationSearchForm','loanNo','userId','loanNo', 'userId','','','customerName');closeLovCallonLov1();" />
								<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
								<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID"  />
										</td>
										<td nowrap="nowrap">
											<bean:message key="lbl.customerName"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="customerName"
												styleId="customerName" maxlength="50" value=""/>
										</td>
									</tr>
									
									<tr>
								 <td>
									 <bean:message key="lbl.userName" />
									 </td>
									 <td>
									    <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
									    <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
									    <html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
									    <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
									    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(266,'liquidationSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
									 </td>
									</tr>
									<tr>
										<td nowrap="nowrap" colspan="4">
											<button type="button" class="topformbutton2" name="search" accesskey="S" title="Alt+S"
												id="search"  onclick="searchLiquidation('P');"><bean:message key="button.search" /></button>
											<button type="button" name="new" id="new"  accesskey="N" title="Alt+N"
												class="topformbutton2" onclick="newLiquidation();"><bean:message key="button.new" /></button>
										</td>
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
				<bean:message key="lbl.liquidatoinMakerRecords"/>
			</legend>
	
			<logic:present name="liquidationSearchList">
				<logic:notEmpty name="list">
					<display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/sdLiquidationSearch.do">
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:column property="loanNo" titleKey="lbl.loanNo" sortable="true" />
						<display:column property="customerName" titleKey="lbl.customerName" sortable="true" />
						<display:column property="loanAmount" titleKey="lbl.loanAmount" sortable="true" />
						<display:column property="product" titleKey="lbl.product" sortable="true" />
						<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
						<display:column property="reportingToUserId" titleKey="lbl.userName" sortable="true" />
					</display:table>
				</logic:notEmpty>

				<logic:empty name="list">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="gridtd">
								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr class="white2">
										<td>
											<strong><bean:message key="lbl.loanNo"/></strong>
										</td>
										<td>
											<b><bean:message key="lbl.customerName"/></b>
										</td>
										<td>
											<strong><bean:message key="lbl.loanAmount"/></strong>
										</td>
										<td>
											<strong><bean:message key="lbl.product"/></strong>
										</td>
										<td>
											<b><bean:message key="lbl.scheme"/></b>
										</td>
										<td>
											<b><bean:message key="lbl.userName"/></b>
										</td>
									</tr>
								<tr class="white2">
								<td colspan="6"> 
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
	</logic:present>


	
	<logic:present name="sdLiquidationAuthorSearch">

		<div id="revisedcontainer">

			<html:form action="/sdLiquidationSearch" method="post"
				styleId="liquidationSearchForm">

				<fieldset>
					<legend>
						<bean:message key="lbl.liquidationAuthorSearch"/>
					</legend>

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>

								<table width="100%" border="0" cellspacing="1" cellpadding="1">

									<tr>
										<td nowrap="nowrap">
											<bean:message key="lbl.loanNo"/>
										</td>
										<td nowrap="nowrap">
										<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
											<html:text styleClass="text" property="loanNo" value=""
												styleId="loanNo" value="" readonly="true" maxlength="20" />
											<html:button property="loanLov" styleId="loanLov" value=" " styleClass="lovbutton"
											 onclick="openLOVCommon(128,'liquidationSearchForm','loanNo','userId','loanNo', 'userId','','','customerName');closeLovCallonLov1();" />
								<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
								<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
										</td>
										<td nowrap="nowrap">
											<bean:message key="lbl.customerName"/>
										</td>
										<td nowrap="nowrap">
											<html:text styleClass="text" property="customerName" value=""
												styleId="customerName" maxlength="50" />
										</td>
									</tr>
									
									<tr>
									 <td>
									 <bean:message key="lbl.userName" />
									 </td>
									 <td>
									    <html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" value=""/>
									    <html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" value=""/>
									    <html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
									    <input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
									    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'liquidationSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
									 </td>
																		</tr>
									<tr>
										<td nowrap="nowrap" colspan="4">
											<button type="button" class="topformbutton2" name="search" accesskey="S" title="Alt+S"
												id="search"  onclick="searchLiquidation('F');"><bean:message key="button.search" /></button>
										</td>
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
				<bean:message key="lbl.liquidationAuthorRecords"/>
			</legend>
		
			<logic:present name="liquidationSearchList">
				<logic:notEmpty name="list">
					<display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/sdLiquidationSearch.do">
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:column property="loanNo" titleKey="lbl.loanNo" sortable="true" />
						<display:column property="customerName" titleKey="lbl.customerName" sortable="true" />
						<display:column property="loanAmount" titleKey="lbl.loanAmount" sortable="true" />
						<display:column property="product" titleKey="lbl.product" sortable="true" />
						<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
						<display:column property="reportingToUserId" titleKey="lbl.userName" sortable="true" />
					</display:table>
				</logic:notEmpty>

				<logic:empty name="list">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="gridtd">
								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr class="white2">
										<td>
											<strong><bean:message key="lbl.loanNo"/></strong>
										</td>
										<td>
											<b><bean:message key="lbl.customerName"/></b>
										</td>
										<td>
											<strong><bean:message key="lbl.loanAmount"/></strong>
										</td>
										<td>
											<strong><bean:message key="lbl.product"/></strong>
										</td>
										<td>
											<b><bean:message key="lbl.scheme"/></b>
										</td>
										<td>
											<b><bean:message key="lbl.userName"/></b>
										</td>
									</tr>
								<tr class="white2">
								<td colspan="6"> 
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
	</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
</body>
</html:html>


<logic:present name="message">
<script type="text/javascript">
if('<%=request.getAttribute("message").toString()%>'=='S')
{
	alert('<bean:message key="msg.DataSaved" />');
}
else if('<%=request.getAttribute("message").toString()%>'=='F')
{
	alert('<bean:message key="msg.DataSaved" />');
	window.location = "<%=request.getContextPath() %>/sdLiquidationSearch.do?method=sdLiquidationMakerSearch";
}
else if('<%=request.getAttribute("message").toString()%>'=='E')
{
	alert('<bean:message key="msg.DataNotSaved" />');
}
else if('<%=request.getAttribute("message").toString()%>'=='N')
{
	//alert('<bean:message key="msg.DataNotFound"/>');
}
else if('<%=request.getAttribute("message")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
	}
</script>
</logic:present>