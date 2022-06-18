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
		src="<%=request.getContextPath()%>/js/cmScript/loanClosure.js"></script>
	
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

<body onload="enableAnchor();document.getElementById('closureSearchForm').loanLov.focus();init_fields();" oncontextmenu="return false" onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">

	<logic:present name="earlyClosure">

			<div id="revisedcontainer">

				<html:form action="/closureInitiation" method="post"
					styleId="closureSearchForm">

					<fieldset>
						<legend>
							<bean:message key="lbl.earlyClosureMakerSearch" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>

											<td>
												<bean:message key="lbl.loanAc" />
											</td>
											<td>
												<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
												<html:text styleClass="text" property="loanAc" styleId="loanAc" value="" readonly="true" tabindex="-1"/>
												<html:button property="loanLov" onclick="openLOVCommon(37,'closureSearchForm','loanAc','userId','loanAc', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovButton" />
												<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName" styleId="customerName" value="" maxlength="50" />
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
										    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(266,'closureSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
										 </td>
										</tr>

										<tr>
											<td>
												<button type="button" class="topformbutton2" name="search" id="search" accesskey="S" title="Alt+S" onclick="return searchLoanClosure('T','P');" ><bean:message key="button.search" /></button>
												<button type="button" class="topformbutton2" name="new" id="new"  accesskey="N" title="Alt+N" onclick="openNewClosure('T');" ><bean:message key="button.new" /></button>
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
						<bean:message key="lbl.earlyClosureMakerRecords" />
					</legend>

					<logic:present name="searchDataList">
						<logic:notEmpty name="list"> 
  							<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/closureInitiation.do" >
    							<display:setProperty name="paging.banner.placement"  value="bottom"/>
    							<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:column property="loanAc" titleKey="lbl.loanAc"  sortable="true"  />
    							<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
								<display:column property="product" titleKey="lbl.product"  sortable="true"  />
								<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
								<display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
								<display:column property="loanDate" titleKey="lbl.loanDate"  sortable="true"  />
								<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
							</display:table>
						</logic:notEmpty>

						<logic:empty name="list">
 							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
								<tr>
    								<td class="gridtd">
    									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										    <tr class="white2">
											<td>
												<strong><bean:message key="lbl.loanAc" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.customerName" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.product" /></strong>
											</td>
											<td>
												<b><bean:message key="lbl.scheme" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanAmount" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanDate" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.userName" /></b>
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
		</logic:present>
	


	<logic:present name="maturityClosure">

			<div id="revisedcontainer">

				<html:form action="/closureInitiation" method="post"
					styleId="closureSearchForm">

					<fieldset>
						<legend>
							<bean:message key="lbl.maturityClosureMakerSearch" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>

											<td>
												<bean:message key="lbl.loanAc" />
											</td>
											<td>
												<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
												<html:text styleClass="text" property="loanAc" styleId="loanAc" value="" readonly="true" />
												<html:button property="loanLov" onclick="openLOVCommon(38,'closureSearchForm','loanAc','userId','loanAc', 'userId','','','customerName');closeLovCallonLov1();" value=" " styleClass="lovButton" />
												<input type="hidden" name="contextPath"value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"value="" />
											</td>
											<td>
												<bean:message key="lbl.customerName" />
											</td>
											<td>
												<html:text styleClass="text" property="customerName"
													styleId="customerName" value="" maxlength="50" />
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
											    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(266,'closureSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
											 </td>
										</tr>
										
										<tr>
											<td>
												<button type="button" class="topformbutton2" name="search"
													id="search"  accesskey="S" title="Alt+S"
													onclick="return searchLoanClosure('C','P');" ><bean:message key="button.search" /></button>
												<button type="button" class="topformbutton2" name="new"
													id="new" accesskey="N" title="Alt+N"
													onclick="openNewClosure('C');" ><bean:message key="button.new" /></button>
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
						<bean:message key="lbl.maturityClosureMakerRecords" />
					</legend>


					<logic:present name="searchDataList">
						<logic:notEmpty name="list"> 
  							<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/closureInitiation.do" >
    							<display:setProperty name="paging.banner.placement"  value="bottom"/>
    							<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:column property="loanAc" titleKey="lbl.loanAc"  sortable="true"  />
    							<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
								<display:column property="product" titleKey="lbl.product"  sortable="true"  />
								<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
								<display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
								<display:column property="loanDate" titleKey="lbl.loanDate"  sortable="true"  />
								<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
							</display:table>
						</logic:notEmpty>

						<logic:empty name="list">
 							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
								<tr>
    								<td class="gridtd">
    									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										    <tr class="white2">
											<td>
												<strong><bean:message key="lbl.loanAc" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.customerName" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.product" /></strong>
											</td>
											<td>
												<b><bean:message key="lbl.scheme" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanAmount" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanDate" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.userName" /></b>
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
		</logic:present>
	


	<logic:present name="cancellationClosure">

			<div id="revisedcontainer">

				<html:form action="/closureInitiation" method="post"
					styleId="closureSearchForm">

					<fieldset>
						<legend>
							<bean:message key="lbl.cancellationMakerSearch" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>

											<td width="20%">
												<bean:message key="lbl.loanAc" />
											</td>
											<td width="35%">
												<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
												<html:text styleClass="text" property="loanAc"
													styleId="loanAc" value="" readonly="true" />
												<html:button property="loanLov"
													onclick="openLOVCommon(39,'closureSearchForm','loanAc','userId','loanAc', 'userId','','','customerName');closeLovCallonLov1();"
													value=" " styleClass="lovButton" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" value="" maxlength="50" />
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
											    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(266,'closureSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
											 </td>
										</tr>

										<tr>
											<td>
												<button type="button" class="topformbutton2" name="search"
													id="search" accesskey="S" title="Alt+S"
													onclick="return searchLoanClosure('X','P');" ><bean:message key="button.search" /></button>
												<button type="button" class="topformbutton2" name="new" accesskey="N" title="Alt+N"
													id="new"  onclick="openNewClosure('X');" ><bean:message key="button.new" /></button>
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
						<bean:message key="lbl.cancellationMakerRecords" />
					</legend>

					<logic:present name="searchDataList">
						<logic:notEmpty name="list"> 
  							<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/closureInitiation.do" >
    							<display:setProperty name="paging.banner.placement"  value="bottom"/>
    							<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:column property="loanAc" titleKey="lbl.loanAc"  sortable="true"  />
    							<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
								<display:column property="product" titleKey="lbl.product"  sortable="true"  />
								<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
								<display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
								<display:column property="loanDate" titleKey="lbl.loanDate"  sortable="true"  />
								<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
							</display:table>
						</logic:notEmpty>

						<logic:empty name="list">
 							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
								<tr>
    								<td class="gridtd">
    									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										    <tr class="white2">
											<td>
												<strong><bean:message key="lbl.loanAc" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.customerName" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.product" /></strong>
											</td>
											<td>
												<b><bean:message key="lbl.scheme" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanAmount" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanDate" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.userName" /></b>
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
		</logic:present>


	<!-- **************************** Author part starts here*********************************** -->

	<logic:present name="earlyClosureAuthor">

			<div id="revisedcontainer">

				<html:form action="/closureInitiation" method="post"
					styleId="closureSearchForm">

					<fieldset>
						<legend>
							<bean:message key="lbl.earlyClosureAuthorSearch" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>

											<td width="20%">
												<bean:message key="lbl.loanAc" />
											</td>
											<td width="35%">
												<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
												<html:text styleClass="text" property="loanAc"
													styleId="loanAc" value="" readonly="true" />
												<html:button property="loanLov"
													onclick="openLOVCommon(40,'closureSearchForm','loanAc','userId','loanAc', 'userId','','','customerName');closeLovCallonLov1();"
													value=" " styleClass="lovButton" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" value="" maxlength="50" />
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
											    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'closureSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
											 </td>
											 	<td><bean:message key="lbl.selectiveBranch"/></td>
										    	<td>
										            <html:text property="selectiveBranch" styleClass="text" styleId="selectiveBranch" maxlength="100" readonly="true" value=""/>
										     		<html:hidden property="lbxBranchId" styleClass="text" styleId="lbxBranchId" value=""/>
										      		<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(505,'CreditForm','lbxBranchId','','', '','','','selectiveBranch')" value=" " styleClass="lovbutton"  ></html:button>
												</td>
										</tr>
											<tr>
												<td><bean:message key="lbl.allBranch"/></td>
										    	<td><html:checkbox property="allBranches" styleClass="text"styleId="allBranches"/></td>
											</tr>	

										<tr>
											<td>
												<button type="button" class="topformbutton2" name="search"
													id="search"  accesskey="S" title="Alt+S" 
													onclick="return searchLoanClosureAuthor('T','F');" ><bean:message key="button.search" /></button>
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
						<bean:message key="lbl.earlyClosureAuthorRecords" />
					</legend>

					<logic:present name="searchDataList">
						<logic:notEmpty name="list"> 
  							<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/closureInitiation.do" >
    							<display:setProperty name="paging.banner.placement"  value="bottom"/>
    							<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:column property="loanAc" titleKey="lbl.loanAc"  sortable="true"  />
    							<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
								<display:column property="product" titleKey="lbl.product"  sortable="true"  />
								<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
								<display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
								<display:column property="loanDate" titleKey="lbl.loanDate"  sortable="true"  />
								<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
							</display:table>
						</logic:notEmpty>

						<logic:empty name="list">
 							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
								<tr>
    								<td class="gridtd">
    									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										    <tr class="white2">
											<td>
												<strong><bean:message key="lbl.loanAc" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.customerName" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.product" /></strong>
											</td>
											<td>
												<b><bean:message key="lbl.scheme" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanAmount" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanDate" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.userName" /></b>
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
		</logic:present>
	


	<logic:present name="maturityClosureAuthor">

			<div id="revisedcontainer">

				<html:form action="/closureInitiation" method="post"
					styleId="closureSearchForm">

					<fieldset>
						<legend>
							<bean:message key="lbl.maturityClosureAuthorSearch" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>

											<td width="20%">
												<bean:message key="lbl.loanAc" />
											</td>
											<td width="35%">
												<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
												<html:text styleClass="text" property="loanAc"
													styleId="loanAc" value="" readonly="true" />
												<html:button property="loanLov"
													onclick="openLOVCommon(41,'closureSearchForm','loanAc','userId','loanAc', 'userId','','','customerName');closeLovCallonLov1();"
													value=" " styleClass="lovButton" />
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" value="" maxlength="50" />
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
											    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'closureSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
											 </td>
										</tr>

										<tr>
											<td align="left">
												<button type="button" class="topformbutton2" name="search"
													id="search"  accesskey="S" title="Alt+S"
													onclick="return searchLoanClosureAuthor('C','F');" ><bean:message key="button.search" /></button>
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
						<bean:message key="lbl.maturityClosureAuthorRecords" />
					</legend>

					<logic:present name="searchDataList">
						<logic:notEmpty name="list"> 
  							<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/closureInitiation.do" >
    							<display:setProperty name="paging.banner.placement"  value="bottom"/>
    							<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:column property="loanAc" titleKey="lbl.loanAc"  sortable="true"  />
    							<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
								<display:column property="product" titleKey="lbl.product"  sortable="true"  />
								<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
								<display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
								<display:column property="loanDate" titleKey="lbl.loanDate"  sortable="true"  />
								<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
							</display:table>
						</logic:notEmpty>

						<logic:empty name="list">
 							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
								<tr>
    								<td class="gridtd">
    									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										    <tr class="white2">
											<td>
												<strong><bean:message key="lbl.loanAc" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.customerName" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.product" /></strong>
											</td>
											<td>
												<b><bean:message key="lbl.scheme" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanAmount" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanDate" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.userName" /></b>
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
		</logic:present>
	


	<logic:present name="cancellationClosureAuthor">

			<div id="revisedcontainer">

				<html:form action="/closureInitiation" method="post"
					styleId="closureSearchForm">

					<fieldset>
						<legend>
							<bean:message key="lbl.cancellationAuthorSearch" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="4">

										<tr>

											<td width="20%">
												<bean:message key="lbl.loanAc" />
											</td>
											<td width="35%">
												<input type="hidden" name="userId" id="userId" value="${userobject.userId}" />
												<html:text styleClass="text" property="loanAc"
													styleId="loanAc" value="" readonly="true" />
												<html:button property="loanLov"
													onclick="openLOVCommon(42,'closureSearchForm','loanAc','userId','loanAc', 'userId','','','customerName');closeLovCallonLov1();"
													value=" " styleClass="lovButton"/>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="" />
											</td>
											<td nowrap="nowrap">
												<bean:message key="lbl.customerName" />
											</td>
											<td nowrap="nowrap">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" value="" maxlength="50" />
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
											    <html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(282,'closureSearchForm','userName','userId','', 'userId','','','reportingToUserId')" value=" " styleClass="lovbutton"  ></html:button>
											 </td>
										</tr>

										<tr>
											<td align="left">
												<button type="button" class="topformbutton2" name="search"
													id="search"  accesskey="S" title="Alt+S"
													onclick="return searchLoanClosureAuthor('X','F');" ><bean:message key="button.search" /></button>
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
						<bean:message key="lbl.cancellationAuthorRecords" />
					</legend>


					<logic:present name="searchDataList">
						<logic:notEmpty name="list"> 
  							<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/closureInitiation.do" >
    							<display:setProperty name="paging.banner.placement"  value="bottom"/>
    							<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    							<display:column property="loanAc" titleKey="lbl.loanAc"  sortable="true"  />
    							<display:column property="customerName" titleKey="lbl.customerName"  sortable="true"  />
								<display:column property="product" titleKey="lbl.product"  sortable="true"  />
								<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
								<display:column property="loanAmount" titleKey="lbl.loanAmount"  sortable="true"  />
								<display:column property="loanDate" titleKey="lbl.loanDate"  sortable="true"  />
								<display:column property="reportingToUserId" titleKey="lbl.userName"  sortable="true"  />
							</display:table>
						</logic:notEmpty>

						<logic:empty name="list">
 							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
								<tr>
    								<td class="gridtd">
    									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										    <tr class="white2">
											<td>
												<strong><bean:message key="lbl.loanAc" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.customerName" /></strong>
											</td>
											<td>
												<strong><bean:message key="lbl.product" /></strong>
											</td>
											<td>
												<b><bean:message key="lbl.scheme" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanAmount" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.loanDate" /></b>
											</td>
											<td>
												<b><bean:message key="lbl.userName" /></b>
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
		</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
</body>
</html:html>

<logic:present name="sms">

	<script type="text/javascript">
	if('<%=request.getAttribute("sms")%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
	}
	else if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert('<bean:message key="msg.DataNotSaved" />');
	}
	else if('<%=request.getAttribute("sms")%>'=='AP')
	{
		alert('<bean:message key="msg.ActivePayment" />');
	}
	else if('<%=request.getAttribute("sms")%>'=='Locked')
	{
		alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
		
	}
	
</script>
</logic:present>