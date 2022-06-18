<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page errorPage="/JSP/errorJsp.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
	<logic:present name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
</logic:present>
			
<logic:notPresent name="css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>	   
		 
	
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
				
		<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<script type="text/javascript" src="<%=path%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=path%>/development-bundle/ui/jquery.ui.datepicker.js"></script>


	<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>
	 	

   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/handSighting.js"></script>
   
		
	
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
	<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('fileTrackingForm').loanButton.focus();init_fields();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/handSightingDispatchAction" method="post" styleId="fileTrackingForm" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
    <fieldset>
 
	<legend><bean:message key="lbl.handSightingSearch"/></legend>   
	    
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		 <tr>	
		   <td width="13%"><bean:message key="lbl.loanNo"/><span><font color="red">*</font></span></td>
			 <td width="13%">
		     	<html:text styleClass="text" property="loanNoSearch" styleId="loanNoSearch" maxlength="20"  value="" readonly="true" tabindex="-1"/>
             	<html:button property="loanAccountButton" styleId="loanAccountButton" onclick="openLOVCommon(1377,'fileTrackingForm','loanNoSearch','','lbxLoanNoHIDSearch','','','','customerName')" value=" " styleClass="lovbutton"/>
             	<html:hidden  property="lbxLoanNoHIDSearch" styleId="lbxLoanNoHIDSearch" value="" />
            </td>
            
             <td width="13%"><bean:message key="lbl.customerName"/></td>
			<td width="13%"><html:text styleClass="text" property="customerName" readonly="true" styleId="customerName"	maxlength="50" value=""/>
		
		</tr>   
		  
</table>
 <table width="100%" border="0" cellspacing="1" cellpadding="0">
		<tr>
		<td colspan="3">
		<button type="button" name="Search" id="search" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return fileTrackingSearch('viewModeSearch');" ><bean:message key="button.search" /></button>
	   </td>
		
		</tr> 		  
		  
	</table>

	</td>
    </tr>
    </table>	 
	</fieldset>	

</html:form>
<br/>
	</div>
	
 <logic:present name="list">
<fieldset>	
  
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td class="gridtd">
  
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
	</table>
	</td>
	</tr>
	</table>
<logic:notEmpty name="list"> 
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/fileTrackingBehindAction.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="loanNoSearch" titleKey="lbl.loanNo"  sortable="true"  />
    <display:column property="fileTrackReceivedDate" titleKey="lbl.receivedDate"  sortable="true"  />
    <display:column property="fileTrackingUser" titleKey="lbl.handSightingUser"  sortable="true"  />
	<display:column property="trackStatus" titleKey="lbl.fileStatus"  sortable="true"  />
	
</display:table>
</logic:notEmpty>

<logic:empty name="list">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
       <td><strong><bean:message key="lbl.loanNo"/></strong> </td>
 		<td><strong><bean:message key="lbl.receivedDate"/></strong></td>
		<td><strong><bean:message key="lbl.handSightingUser"/></strong></td>
        <td><b><bean:message key="lbl.fileStatus"/></b></td>
	</tr>
	<tr class="white2">
<td colspan="7">
<bean:message key="lbl.noDataFound" />
</td>
</tr>
 </table>    </td>
</tr>
</table>

</logic:empty>
	</fieldset>
 </logic:present>    
 

</div>
</body>
</html:html>