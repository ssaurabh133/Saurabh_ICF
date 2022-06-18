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
       
	    <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/documentRelated.js"></script>
  </head>
  
  <body oncontextmenu="return false" onclick="parent_disable()" onload="enableAnchor();init_fields();">
  
  <%
  	String st = request.getParameter("pageStatus");
  	if(!CommonFunction.checkNull(st).equalsIgnoreCase("") && CommonFunction.checkNull(st).equalsIgnoreCase("MC"))
  	{
   %>
   
	<fieldset>	
	
		 <legend><bean:message key="lbl.child"/></legend>  

  <input type="hidden" name="rowNo" id="rowNo" value="${rowCount }"/>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    
     
     <td ><b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="lbl.select" /></b></td> 
	    
        <td ><b><bean:message key="lbl.ID"/></b></td>
		<td ><strong><bean:message key="lbl.Name"/></strong></td>
   
	</tr>
	      
	     <logic:present name="child">
	                        
		<logic:iterate id="subChild" name="child">
	
	
					    <tr class="white1">
							<td>
							
						   <logic:present name="collects">
								<%
											int k=0; 
								%>
								<logic:iterate name="collects" id="subcollects">
								 
									<logic:equal name="subChild" property="id" value="${subcollects}" >
										<%
											 k=1; 
										%>
									   <input type="checkbox" name="chk" id="chk" checked="checked" value="${subChild.id }"/>
									</logic:equal>
								</logic:iterate>   
								
								    <%
										if( k==0)
										{ 
								     %>
											<input type="checkbox" name="chk" id="chk"  value="${subChild.id }"/>
											
										<%} %>
							  
							    
							</logic:present>
							<logic:notPresent name="collects">
								<input type="checkbox" name="chk" id="chk"  value="${subChild.id }"/>
							</logic:notPresent>
								
								 <%-- <input type="checkbox" name="chk" id="chk"  value="${subChild.id }"/>--%> 
								
							</td>
							
							<td>${subChild.id }</td>
	      			        <td>${subChild.name }
	      			   </tr>				
			 
	   </logic:iterate>
	
	 <% session.removeAttribute("collects"); %>
	    <% session.removeAttribute("child"); %>
        <% session.removeAttribute("rowCount"); %>
	</logic:present> 
	
		</table>
		</td></tr>
</table>
 
 <button type="button" name="delete" class="topformbutton2" id="Add" title="Alt+A" accesskey="A" onclick="return addChild();"><bean:message key="button.add" /></button>

        
</fieldset>

<%
	}
	else if(!CommonFunction.checkNull(st).equalsIgnoreCase("") && st.equalsIgnoreCase("VC"))
	{
 %>
 
 	<fieldset>	
	
		 <legend><bean:message key="lbl.child"/></legend>  

  <input type="hidden" name="rowNo" id="rowNo" value="${rowCount }"/>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    
     
     <td ><b><input type="checkbox" name="allchk" disabled="disabled" id="allchk" onclick="allChecked();"/><bean:message key="lbl.select" /></b></td> 
	    
        <td ><b><bean:message key="lbl.ID"/></b></td>
		<td ><strong><bean:message key="lbl.Name"/></strong></td>
   
	</tr>
	      
	     <logic:present name="child">
	                        
		<logic:iterate id="subChild" name="child">
	
	
					    <tr class="white1">
							<td>
							
						   <logic:present name="collects">
								<%
											int k=0; 
								%>
								<logic:iterate name="collects" id="subcollects">
								 
									<logic:equal name="subChild" property="id" value="${subcollects}" >
										<%
											 k=1; 
										%>
									  <input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" value="${subChild.id }"/>
									</logic:equal>
								</logic:iterate>   
								
								    <%
										if( k==0)
										{ 
								     %>
											<input type="checkbox" name="chk" id="chk" disabled="disabled"  value="${subChild.id }"/>
											
										<%} %>
							  
							    
							</logic:present>
							<logic:notPresent name="collects">
								<input type="checkbox" name="chk" id="chk" disabled="disabled"  value="${subChild.id }"/>
							</logic:notPresent>
								
								 <%-- <input type="checkbox" name="chk" id="chk"  value="${subChild.id }"/>--%> 
								
							</td>
							
							<td>${subChild.id }</td>
	      			        <td>${subChild.name }
	      			   </tr>				
			 
	   </logic:iterate>
	
	 <% session.removeAttribute("collects"); %>
	    <% session.removeAttribute("child"); %>
        <% session.removeAttribute("rowCount"); %>
	</logic:present> 
	
		</table>
		</td></tr>
</table>
 <%-- 
 <html:button property="delete" styleClass="topformbutton2" styleId="Add" value="Add" title="Alt+A" accesskey="A" onclick="return addChild();"/>
 --%>

        
</fieldset>
 
 <%
 	}
  %>
  </body>
</html>
