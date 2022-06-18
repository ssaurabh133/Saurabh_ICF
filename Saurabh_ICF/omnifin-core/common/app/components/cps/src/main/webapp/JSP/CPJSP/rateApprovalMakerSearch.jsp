<!--Author Name : Neeraj Kumar Tripathi-->
<!--Date of Creation : 26-Jun-2013-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<html:html>
<head>
	<title><bean:message key="a3s.noida" /></title>
	<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
	</logic:present>
	<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
	</logic:notPresent>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/rateApprovalDeal.js"></script>	
	<%
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
		request.setAttribute("no",no);
	%>
	<!--[if IE]>
		<style type="text/css">
		.opacity
		{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
		</style>
	<! [endif] -->
</head>

<body onload="enableAnchor();">
<html:form action="/rateApprovalMaker" styleId="rateApprovalMaker" method="post" > 	
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<fieldset><legend><bean:message key="lbl.rateApprovalMakerSearch"/></legend>
	<table width="100%">
	<tr>	
		<td><bean:message key="lbl.dealNo"/><span><font color="red">*</font></span></td>
		<td>
			<html:hidden styleClass="text" property="dealId" styleId="dealId" value=""/>
			<html:text styleClass="text"   property="dealNo" styleId="dealNo" readonly="true" value="" tabindex="-1"/>
			<html:button property="lovDealButton" styleId="lovDealButton" onclick="openLOVCommon(2019,'rateApprovalMaker','dealNo','','','','','','customerName');" value=" " styleClass="lovbutton"></html:button>
        </td>  
        <td><bean:message key="lbl.customerName"/></td>
		<td><html:text styleClass="text" maxlength="10" property="customerName" styleId="customerName" value=""  maxlength="100"/></td>
	</tr>
	<tr>
		<td><button type="button" name="search" title="Alt+S" accesskey="S" id="button" class="topformbutton2" onclick="return searchrateApprovalMaker();"><bean:message key="button.search" /></button></td>
	</tr>
    </table>
</fieldset>
</html:form>

<fieldset><legend><bean:message key="lbl.applicationDetails"/></legend>  
<logic:notPresent name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">    
    <tr class="white2">		
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
        <td><b><bean:message key="lbl.customerName"/></b></td>	
        <td><b><bean:message key="lbl.applicationDate"/></b></td>
       	<td><b><bean:message key="grd.applicationNo"/></b></td>      
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/></b></td>    
	</tr>
 	</table>    
 	</td>
</tr>
</table>
</logic:notPresent>
<logic:present name="list">
<logic:notEmpty name="list">
<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/rateApprovalMaker.do?method=defaultRateApproval" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>    
    <display:column property="gridDealNo" titleKey="lbl.dealNo"  sortable="true"  />
	<display:column property="gridCustName" titleKey="lbl.customerName"  sortable="true"  />
	<display:column property="applicationdate" titleKey="lbl.applicationDate"  sortable="true"  />
	<display:column property="applicationno" titleKey="grd.applicationNo"  sortable="true"  />
	<display:column property="product" titleKey="lbl.product"  sortable="true"  />
	<display:column property="scheme" titleKey="lbl.scheme"  sortable="true"  />
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">   
    <tr class="white2">	
        <td ><b><bean:message key="lbl.dealNo"/></b></td>
        <td><b><bean:message key="lbl.customerName"/></b></td>	
        <td><b><bean:message key="lbl.applicationDate"/></b></td>
       	<td><b><bean:message key="grd.applicationNo"/></b></td>      
        <td><b><bean:message key="lbl.product"/></b></td>
        <td><b><bean:message key="lbl.scheme"/></b></td>         
	</tr>
	<tr class="white2"><td colspan="7"><bean:message key="lbl.noDataFound"/></td></tr>	
 	</table>    
 	</td>
</tr>
</table>
</logic:empty>
</logic:present>
</fieldset>
</body>
</html:html>