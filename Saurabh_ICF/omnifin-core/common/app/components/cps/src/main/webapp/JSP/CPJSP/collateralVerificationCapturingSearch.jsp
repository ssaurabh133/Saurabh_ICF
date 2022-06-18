<!--
 	Author Name      :- Amit Kumar
 	Date of Creation :- 
 	Purpose          :- To search collateral details during deal capturing
 	Documentation    :- 
 -->

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
<html>

	<head>
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<%
			ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.ApplicationResources");
			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
		%>

		<logic:present name="css">
			<link type="text/css" rel="stylesheet"
				href="<%=request.getContextPath()%>/${css }/displaytable.css" />
		</logic:present>

		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet"
				href="<%=request.getContextPath()%>/css/theme1/displaytable.css" />
		</logic:notPresent>

		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->

		<script type="text/javascript" src="js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/popup.js"></script>
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
	<body oncontextmenu="return false" onclick="parent_disable();"
		onload="enableAnchor();document.getElementById('commonForm').dealButton.focus();">
		<input type="hidden" id="contextPath" name="contextPath"
			value="<%=request.getContextPath()%>" />
		<div id="centercolumn">
			<html:form action="/collateralVerificationCapturingSearch"
				method="post" styleId="commonForm">
				<html:hidden property="contextPath" styleId="contextPath"
					value="<%=request.getContextPath() %>" />
					<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
				<input type="hidden" name="formatD" id="formatD"
					value="<bean:message key="lbl.dateFormat"/>" />
								<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
				<div id="revisedcontainer">
					<fieldset>
						<legend>
							<bean:message key="lbl.collateralVerificationCapturingSearch" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td>
												<bean:message key="lbl.dealNo" />
											</td>
											<td>
												<html:text styleClass="text" property="dealNo"
													styleId="dealNo" value="${searchParams[0].dealNo}" maxlength="10" readonly="true" 
													/>
												<html:hidden property="lbxDealNo" styleId="lbxDealNo" value="${searchParams[0].lbxDealNo}"/>
												<html:button property="dealButton" styleId="dealButton"
													tabindex="1"
													onclick="closeLovCallonLov1();openLOVCommon(184,'commonForm','dealNo','','', '','','','customername')"
													value=" " styleClass="lovbutton"></html:button>
											</td>
											<td>
												<bean:message key="lbl.customername" />
											</td>
											<td>
												<html:text styleClass="text" property="customername"
													styleId="customername" value="${searchParams[0].customername}" maxlength="50" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.initiationDate" />
											</td>
											<td>
												<html:text styleClass="text" property="initiationDate"
													styleId="initiationDate" value="${searchParams[0].initiationDate}" maxlength="10"
													onchange="checkDate('initiationDate');" />
											</td>
											<td>
												<bean:message key="lbl.product" />
											</td>
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="${searchParams[0].product}" maxlength="50" readonly="true" />
												<html:hidden property="lbxProductID" styleId="lbxProductID"
													value="${searchParams[0].lbxProductID}" />
												<input type="hidden" id="productId" name="productId"
													value="" />
												<html:button property="productButton" style="productButton"
													onclick="closeLovCallonLov1();openLOVCommon(209,'commonForm','product','','scheme','lbxscheme','','','productId')"
													value=" " styleClass="lovbutton"></html:button>
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.scheme" />
											</td>
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" value="${searchParams[0].scheme}" maxlength="50" readonly="true" />
												<html:hidden property="lbxscheme" styleId="lbxscheme"
													value="${searchParams[0].lbxscheme}" />
												<input type="hidden" id="schemeId" name="schemeId" value="" />
												<html:button property="schemeButton" style="schemeButton"
													onclick="openLOVCommon(22,'commonForm','scheme','product','lbxscheme', 'lbxProductID','Please Select Product','','schemeId');closeLovCallonLov('product');"
													value=" " styleClass="lovbutton"></html:button>
											</td>
											<td>
						<bean:message key="lbl.userNam" />
				      </td>
				      <td>
						<html:hidden property="reportingToUserId" styleClass="text" styleId="reportingToUserId" />
						<html:text property="userName" styleClass="text" styleId="userName" maxlength="100" readonly="true" tabindex="-1" value=""/>
						<html:hidden property="lbxUserId" styleId="lbxUserId" value="" />
						<html:button property="userButton" styleId="userButton"
									onclick="openLOVCommon(266,'commonForm','userName','userId','', 'userId','','','reportingToUserId')"
									value=" " styleClass="lovbutton">
						</html:button>
   					  </td>
										</tr>
										<tr>
											<td colspan="4">
												<button type="button" name="search" id="search"
													class="topformbutton2" accesskey="S"
													title="Alt+S"
													onclick="return searchCollateralVarificationData(); "><bean:message key="button.search" /></button>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
				<fieldset>
					<legend>
						<bean:message key="lbl.collateralVerificationCapturingSearchDetails" />
					</legend>
					
					<logic:present name="list">

						<logic:notEmpty name="list">
							<display:table id="list" name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/collateralVerificationCapturingSearch.do">
								<display:setProperty name="paging.banner.placement" value="bottom" />
								<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
								<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
								<display:column property="dealNo" titleKey="lbl.dealNo" sortable="true" />
								<display:column property="initiationDate" titleKey="lbl.initiationDate" sortable="true" />
								<display:column property="customername" titleKey="lbl.customerName" sortable="true" />
								<display:column property="product" titleKey="lbl.product" sortable="true" />
								<display:column property="scheme" titleKey="lbl.scheme" sortable="true" />
								<display:column property="userName" titleKey="lbl.userNam" sortable="true" />
							
							</display:table>
						</logic:notEmpty>

						<logic:empty name="list">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="gridtd">
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
											<tr class="white2">
												<td>
													<strong><bean:message key="lbl.dealNo" /></strong>
												</td>
												<td>
													<strong><bean:message key="lbl.initiationDate" /></strong>
												</td>
												<td>
													<b><bean:message key="lbl.customerName" /></b>
												</td>
												<td>
													<strong><bean:message key="lbl.product" /></strong>
												</td>
												<td>
													<b><bean:message key="lbl.scheme" /></b>
												</td>
												<td>
													<b><bean:message key="lbl.userNam" /></b>
												</td>
											</tr>
											<tr class="white2" >
	                               				   <td colspan="6"><bean:message key="lbl.noDataFound" /></td> 
	                           				</tr>
										</table>
									</td>
								</tr>
							</table>
						</logic:empty>
					</logic:present>
				</fieldset>
			</html:form>
		</div>
		<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
</div>
	</body>
</html>

<logic:present name="message">
	<script type="text/javascript">
 if('<%=request.getAttribute("message")%>'=='Locked')
{
	alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
}
</script>
</logic:present>

