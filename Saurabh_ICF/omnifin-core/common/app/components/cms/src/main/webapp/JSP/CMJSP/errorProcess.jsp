<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@page import="com.logger.LoggerMsg"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript"  src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
	</head>
	<body onload="enableAnchor();">
	<div id="revisedcontainer">	
	<fieldset>	
	<legend align="top"><strong><bean:message key="lbl.errorHeader"/></strong></legend> 
	<% 
	List list= new ArrayList();
    list=(List)request.getAttribute("errorList");
    List data=new ArrayList();
   
    if(list!=null && list.size()>0)
    {
        %>
 
    <td class="gridtd">    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">  
      <td  ><strong><bean:message key="lbl.loanId"/> </strong></td>
      <td ><strong> <bean:message key="lbl.txnType"/> </strong></td>
      <td ><strong><bean:message key="lbl.txnId"/></strong></td>
      <td ><strong><bean:message key="lbl.errMSG"/></strong></td>    
    </tr>
   
    <%
  int max=list.size();
    for(int a=0;a<max;a++)
    {
       data=(List)list.get(a);
     %>
    <tr class="white1">	
     <td ><%=data.get(0)%></td>
	 <td ><%=data.get(1)%></td>
	 <td ><%=data.get(2)%></td>
	 <td ><%=data.get(3)%></td>
      </tr>
     

	
	<%   
	} 
	      
    }
    else
    {
    %>
    </table></td>
	</tr>
    	 <div id="revisedcontainer">		
		 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr><td align="center" ><b><font color="red"><bean:message key="lbl.noErrorRecord"/> </font></b></td></tr>		  
      </table>
      </div>
     	  
</div>
<%
    }
    %>
	
	</fieldset>
	
	
	 <button type="button" name="close" title="Alt+C" accesskey="C"  class="topformbutton2" id="close"  onclick="javascript:window.close();"><bean:message key="button.close" /></button>
	 </div>

 

</html:html>

 

 