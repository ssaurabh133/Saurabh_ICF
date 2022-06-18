<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'childDocs.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 
  </head>
  
  <body oncontextmenu="return false" onclick="parent_disable()" onload="enableAnchor();init_fields();">
 
 
 	<fieldset>	
	
		 <legend><bean:message key="lbl.child"/></legend>  


        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    
     
     <td ><b><input type="checkbox" name="allchk" checked="checked"  disabled="true" id="allchk" /><bean:message key="lbl.select" /></b></td> 
	    
        <td ><b><bean:message key="lbl.ID"/></b></td>
		<td ><strong><bean:message key="lbl.Name"/></strong></td>
   
	</tr>
	      
	     <logic:present name="fetchChildDocsDetails">
	                        
		<logic:iterate id="subChild" name="fetchChildDocsDetails">
	
	
					    <tr class="white1">
							<td><input type="checkbox" name="chk" id="chk" disabled="true"  checked="checked" value="${subChild.id }"/></td>
							<td>${subChild.id }</td>
	      			        <td>${subChild.name }
	      			   </tr>				
			 
	   </logic:iterate>
	

	</logic:present> 
	
		</table>
		</td></tr>
</table>
        
</fieldset>

  </body>
</html>
