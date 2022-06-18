<!-- 
Author Name :- MRADUL AGARWAL
Date of Creation :06-08-2013
Purpose :-  screen for the Asset Maker
-->
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
	<html>	
	<head>
	<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	request.setAttribute("no",no); %>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/asset.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>		
	</head>
	<body onload="enableAnchor();">
	<div align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">	
	<div id="revisedcontainer">
	<logic:present name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
	</logic:notPresent>
	<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	
	<html:form action="/assetAuthorProcessAction" method="post" styleId="assetMakerSearch">	          
    <fieldset>	
	<legend><bean:message key="lbl.assetInsuranceViewer"/></legend>  
  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>

    <td class="gridtd">
    <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath" />
   <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <logic:notPresent name="assetInsuranceViewer">
    <tr class="white2">	    
    	<td><b><bean:message key="lbl.assetInsurance"/></b></td>
     	<td><b><bean:message key="lbl.policyNo"/></b></td>  
	    <td><b><bean:message key="lbl.customerName"/></b></td> 	         
	    <td><b><bean:message key="lbl.coverNoteNo"/></b></td>        
        <td><b><bean:message key="lbl.insuranceAgency"/></b></td>
        <td><b><bean:message key="lbl.startDate"/></b></td>
		<td><b><bean:message key="lbl.endDate"/></b></td>
		<td><b><bean:message key="lbl.userName"/></b></td>		
	</tr>
	<tr class="white2">
<td colspan="7">
<bean:message key="lbl.noDataFound"/>
</td>
</tr>
	</logic:notPresent>
	</table>
	</td>
	</tr>
	</table>
	 <logic:present name="assetInsuranceViewer">
	<logic:notEmpty name="assetInsuranceViewer"> 
  	<display:table  id="assetInsuranceViewer"  name="assetInsuranceViewer" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${assetInsuranceViewer[0].totalRecordSize}"  >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
      
    <display:column property="assetInsuranceId" titleKey="lbl.assetInsurance"  sortable="true" /> 
    <display:column property="policyNo" titleKey="lbl.policyNo"  sortable="true" /> 
    <display:column property="insuranceAgency" titleKey="lbl.insuranceAgency"  sortable="true"  />
    <display:column property="insuranceDoneBy" titleKey="lbl.insrDoneBy"  sortable="true"  />
    <display:column property="customerName" titleKey="lbl.customerName"  sortable="true" />      
    <display:column property="premiumAmnt" titleKey="lbl.premiumAmnt"  sortable="true"  />	
	<display:column property="startDate" titleKey="lbl.startDate"  sortable="true"  />
	<display:column property="endDate" titleKey="lbl.endDate"  sortable="true"  />	
	</display:table>
	</logic:notEmpty>

<logic:empty name="assetInsuranceViewer">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    	<td><b><bean:message key="lbl.assetInsurance"/></b></td>
        <td><b><bean:message key="lbl.policyNo"/></b></td>
	    <td><b><bean:message key="lbl.customerName"/></b></td>       
	    <td><b><bean:message key="lbl.coverNoteNo"/></b></td>        
        <td><b><bean:message key="lbl.insuranceAgency"/></b></td>
        <td><b><bean:message key="lbl.startDate"/></b></td>
		<td><b><bean:message key="lbl.endDate"/></b></td>
		<td><b><bean:message key="lbl.userName"/></b></td>
	</tr>
	<tr class="white2">
<td colspan="8">
<bean:message key="lbl.noDataFound"/>
</td>
</tr>
 </table>    
 </td>
</tr>
</table>
</logic:empty>
  </logic:present>
 </fieldset>
 </html:form>
  
<logic:present name="msg"><br />
<script type="text/javascript">

if('<%=request.getAttribute("msg").toString()%>'=='F')
{
	alert("<bean:message key="lbl.forwardSuccess" />");
	
}
  else if('<%=request.getAttribute("msg").toString()%>'=='S')
{
	alert("<bean:message key="lbl.updateSuccess" />");
	document.getElementById('assetMakerSearch').action="<%=request.getContextPath()%>/assetMakerSearch.do}';
document.getElementById('assetMakerSearch').submit();
	
}
else if('<%=request.getAttribute("msg")%>'=='Locked')
   {
    alert('<%=request.getAttribute("recordId")%>' + ' '+ '<bean:message key="lbl.recordIsInUse" />');
   }
else
{
	alert("<bean:message key="lbl.erroruSuccess" />");
	
}		
</script>
</logic:present>   
</div>
</div>
</body>
</html>