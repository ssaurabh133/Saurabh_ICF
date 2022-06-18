<!-- 
Author Name :- Manas
Date of Creation :43-04-2011
Purpose :-  screen for the Payment Maker
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/omniFinReportScript.js"></script>


<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	</head>
	<body onload="enableAnchor();document.getElementById('generateReport').generateBankingDate.focus();init_fields();">
	
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
	
	<html:form action="/generateReport" method="POST" styleId="generateReport">
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	    <fieldset>	  
	<legend><bean:message key="lbl.generateBankingReports"/> </legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
          <tr>
            <td><bean:message key="lbl.date"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
            <td><html:text property="generateBankingDate" styleClass="text" styleId="generateBankingDate" />
           
              </td>
            <td></td>
            <td ></td>
          </tr>
          <tr>
            <td>
              <button type="button" value="Generate" class="topformbutton3"  title="Alt+G" accesskey="G" onclick="return viewReport('<bean:message key="msg.plzSelGendate"/>');"> <bean:message key="button.generate" /></button>
            </td>
            <td nowrap="nowrap" >&nbsp;</td>
            <td></td>
            <td ></td>
          </tr>
         
        </table>
        </td>
        </tr>
	</table>
	 
	</fieldset>	
	
</html:form>
<logic:present name="empty">
	<script type="text/javascript">	
		alert("Record not found on this date.");
	</script>
	</logic:present>
</div>

</div>
</body>
</html:html>