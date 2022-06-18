<!--Author Name : Neeraj Kumar Tripathi-->
<!--Date of Creation : 20-Mar-2012-->
<!--Documentation : -->

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.*"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
	
		<logic:present name="css">
				<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
			<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
			
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/knockOffCancellation.js"></script>  
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
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
	
	
	
<body onload="enableAnchor();" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
	<html:form action="/knockOffCancellationDispatchAction" styleId="knockOffCancellationMaker" >
		<fieldset>
			<legend><bean:message key="lbl.KnockOffCancellationMaker"/></legend>  
				<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />		
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr>
						<td><bean:message key="lbl.loanNumber"/></td>
						<td><html:text property="loanNumber" styleClass="text" value="" styleId="loanNumber" readonly="true"/>
							<html:hidden property="lbxLoanID" styleId="lbxLoanID" value="" />
							<input type="hidden" id="flag" value="P"/>
							<input type="hidden" id="userId" value="${requestScope.userId }"/>
							<html:button property="loanFromButton" styleId="loanFromButton" tabindex="1" onclick="openLOVCommon(300,'knockOffCancellationMaker','loanNumber','userId','', 'userId','','','customerName','knockOffID')" value=" " styleClass="lovbutton"  />
						</td>
				  	    <td><bean:message key="lbl.customer_name"/></td>
						<td><html:text property="customerName" styleClass="text" styleId="customerName" maxlength="100" readonly="true" value="${headerList[0].customerName}"/></td>
					</tr>
					<tr>
						<td><bean:message key="lbl.knockOffId"/></td>
						<td><html:text property="knockOffID" styleClass="text" styleId="knockOffID" maxlength="100" value="" readonly="true"/></td>
					</tr>				
					<tr>
						<td>
							<button type="button" name=" mybutton" accesskey="S" title="Alt+S" class="topformbutton2" onclick="return searchKOCData()" ><bean:message key="button.search" /></button>
							<button type="button"  name=" mybutton" accesskey="N" title="Alt+N" class="topformbutton2" onclick="knockOffCancellationMakerNew()" ><bean:message key="button.new" /></button>
						</td>
					</tr>	
					</table>
		</fieldset>
		<fieldset>
			<legend><bean:message key="lbl.knockOffCancellationMakerRecord" /></legend>
			<logic:present name="defaultKOCList">
			<logic:notEmpty name="defaultKOCList">
				<display:table  id="defaultKOCList"  name="defaultKOCList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${defaultKOCList[0].totalRecordSize}" requestURI="/knockOffCancellationMakerBehindAction.do" >
    				<display:setProperty name="paging.banner.placement"  value="bottom"/>
    				<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    				<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
				    <display:column property="knockOffId" titleKey="lbl.knockOffId"  sortable="true"  />
				    <display:column property="loanNumber" titleKey="lbl.loanNo"  sortable="true"  />
				    <display:column property="customerName" titleKey="lbl.CustomerName"  sortable="true"  />
					<display:column property="businessPartnerType" titleKey="lbl.businessPartnerType"  sortable="true"  />
					<display:column property="businessPartnerName" titleKey="lbl.businessPartnerName"  sortable="true"  />
					<display:column property="knockOffAmountR" titleKey="lbl.knockOffAmountR"  sortable="true" style="text-align: right" />
					<display:column property="knockOffAmountP" titleKey="lbl.knockOffAmountP"  sortable="true" style="text-align: right" />
			 </display:table>				
			</logic:notEmpty>
			<logic:empty name="defaultKOCList">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="gridtd">
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr class="white2">
								<td><strong><bean:message key="lbl.knockOffId" /> </strong></td>
								<td><strong><bean:message key="lbl.loanNo" /> </strong></td>
								<td><strong><bean:message key="lbl.CustomerName" /> </strong></td>
								<td><strong><bean:message key="lbl.businessPartnerType" /> </strong></td>
								<td><strong><bean:message key="lbl.businessPartnerName" /> </strong></td>
								<td><strong><bean:message key="lbl.knockOffAmountR" /> </strong></td>
								<td><strong><bean:message key="lbl.knockOffAmountP" /> </strong></td>
						</tr>
						<tr class="white2">
							<td colspan="7"><bean:message key="lbl.noDataFound" /></td>
						</tr>
						</table>
					</td>
				</tr>				
				</table>
			</logic:empty>
		</logic:present>
			<logic:present name="KOCList">
			<logic:notEmpty name="KOCList">
				<display:table  id="KOCList"  name="KOCList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${KOCList[0].totalRecordSize}" requestURI="/knockOffCancellationDispatchAction.do?method=searchKOCData" >
    				<display:setProperty name="paging.banner.placement"  value="bottom"/>
    				<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    				<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
				    <display:column property="knockOffId" titleKey="lbl.knockOffId"  sortable="true"  />
				    <display:column property="loanNumber" titleKey="lbl.loanNo"  sortable="true"  />
				    <display:column property="customerName" titleKey="lbl.CustomerName"  sortable="true"  />
					<display:column property="businessPartnerType" titleKey="lbl.businessPartnerType"  sortable="true"  />
					<display:column property="businessPartnerName" titleKey="lbl.businessPartnerName"  sortable="true"  />
					<display:column property="knockOffAmountR" titleKey="lbl.knockOffAmountR"  sortable="true"  style="text-align: right"/>
					<display:column property="knockOffAmountP" titleKey="lbl.knockOffAmountP"  sortable="true" style="text-align: right" />
			 </display:table>				
			</logic:notEmpty>
			<logic:empty name="KOCList">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="gridtd">
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr class="white2">
								<td><strong><bean:message key="lbl.knockOffId" /> </strong></td>
								<td><strong><bean:message key="lbl.loanNo" /> </strong></td>
								<td><strong><bean:message key="lbl.CustomerName" /> </strong></td>
								<td><strong><bean:message key="lbl.businessPartnerType" /> </strong></td>
								<td><strong><bean:message key="lbl.businessPartnerName" /> </strong></td>
								<td><strong><bean:message key="lbl.knockOffAmountR" /> </strong></td>
								<td><strong><bean:message key="lbl.knockOffAmountP" /> </strong></td>
						</tr>
						<tr class="white2">
							<td colspan="7"><bean:message key="lbl.noDataFound" /></td>
						</tr>
						</table>
					</td>
				</tr>				
				</table>
			</logic:empty>
		</logic:present>
	</fieldset>
	</html:form>
</body>
</html>