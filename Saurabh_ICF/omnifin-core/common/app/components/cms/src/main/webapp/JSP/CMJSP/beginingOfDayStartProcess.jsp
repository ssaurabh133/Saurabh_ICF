<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.connect.CommonFunction"%>
<%@page import="com.connect.ConnectionDAO"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
	    <link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	</head>
	
<body onload="enableAnchor();JavaScript:refreshBodProcess(5000);">
<div id="centercolumn">	
	<div id="revisedcontainer">	
	<html:form action="/beginingOfDay" styleId="beginingOfDayForm"> 
  <fieldset>
  <legend><bean:message key="lbl.beginingOfDayheader" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		  <tr>
      <td class="white2" style="width:220px;"><strong><input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" /></strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.processName"/> </strong></td>
     <%-- <td class="white2" style="width:220px;" align="center"><strong> <bean:message key="lbl.imagetimer"/></strong></td>--%>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.startTime"/></strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.endTime"/> </strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.beginingOfDaystatus"/></strong></td>
       <td class="white2" style="width:220px;"><strong><bean:message key="lbl.errorlog"/></strong></td>
    </tr>

     <% 
    List list= new ArrayList();
    list=(List)session.getAttribute("ProcessDone");
    List data=new ArrayList();
     String bodFlag=(String)session.getAttribute("bodFlag");
  if(list!=null){
     int max=list.size();
    for(int a=0;a<max;a++)
    {
       data=(List)list.get(a);
     %>
       <input type="hidden" id="bodFlag" value="<%=CommonFunction.checkNull(bodFlag)%>" />
       <input type="hidden" id="startFlag" value="" />		
     <tr class="white2">  	 	
     <td  ><input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" /></td>
	 <td ><%=CommonFunction.checkNull(data.get(0)).toString()%></td>
	 <td ><%=CommonFunction.checkNull(data.get(1)).toString()%></td>
     <td ><%=CommonFunction.checkNull(data.get(2)).toString()%></td>
     <%
     if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("P"))
     {
     %>
     <td class="white2" style="width:220px;"><%
if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("F"))
{
%>
<img align="middle" src="<%=request.getContextPath()%>/images/theme1/green.gif"  width="20" height="20" ></img>
  <%  
 }
 else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("P"))
 {
 %>
  <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/loder-blue.gif" width="20" height="20" ></img>
 <% 
 }
  else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("E"))
 {
 %>
  <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/red.gif" width="20" height="20" ></img>
 <% 
 }
 else 
 {
  %>
   <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/blue.gif" width="20" height="20" ></img>
   <%
 }
 %>&nbsp;&nbsp;&nbsp;<strong><bean:message key="lbl.process"/></strong></td>
     <%
     }
     else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("F"))
     {
      %>
     <td class="white2" style="width:220px;"><%
if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("F"))
{
%>
<img align="middle" src="<%=request.getContextPath()%>/images/theme1/green.gif"  width="20" height="20" ></img>
  <%  
 }
 else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("P"))
 {
 %>
  <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/loder-blue.gif" width="20" height="20" ></img>
 <% 
 }
  else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("E"))
 {
 %>
  <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/red.gif" width="20" height="20" ></img>
 <% 
 }
 else 
 {
  %>
   <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/blue.gif" width="20" height="20" ></img>
   <%
 }
 %>&nbsp;&nbsp;&nbsp;<strong><font color="black"><bean:message key="lbl.processFinish"/></font></strong></td>
     <%
     }
      else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("E"))
     {
      %>
     <td class="white2" style="width:220px;"><%
if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("F"))
{
%>
<img align="middle" src="<%=request.getContextPath()%>/images/theme1/green.gif"  width="20" height="20" ></img>
  <%  
 }
 else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("P"))
 {
 %>
  <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/loder-blue.gif" width="20" height="20" ></img>
 <% 
 }
  else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("E"))
 {
 %>
  <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/red.gif" width="20" height="20" ></img>
 <% 
 }
 else 
 {
  %>
   <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/blue.gif" width="20" height="20" ></img>
   <%
 }
 %>&nbsp;&nbsp;&nbsp;<strong><font color="Red"><bean:message key="lbl.ErrorInProcess"/></font></strong></td>
     <%
     }
     else
     {
     %>
     <td class="white2" style="width:220px;"><%
if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("F"))
{
%>
<img align="middle" src="<%=request.getContextPath()%>/images/theme1/green.gif"  width="20" height="20" ></img>
  <%  
 }
 else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("P"))
 {
 %>
  <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/loder-blue.gif" width="20" height="20" ></img>
 <% 
 }
  else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("E"))
 {
 %>
  <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/red.gif" width="20" height="20" ></img>
 <% 
 }
 else 
 {
  %>
   <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/blue.gif" width="20" height="20" ></img>
   <%
 }
 %>&nbsp;&nbsp;&nbsp;<strong><font color="Black"><bean:message key="lbl.noProcess"/></font></strong></td>
     <%
     }
    String status=CommonFunction.checkNull(data.get(3)).toString();
    if(status.equalsIgnoreCase("E"))
    {
     %>     
      <td class="white2" style="width:220px;"><a href="#" onclick="processBodError('<%=CommonFunction.checkNull(data.get(0))%>');">1234</a></td>
     <%
    }
    else
    {
    %>     
       <td class="white2" style="width:220px;"><br /><br /></td>
     <%
    }     
    }
    }
     %>  
   
      </tr>     
		</table>  
	
	  </fieldset>
  <%
ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
String dbType=resource.getString("lbl.dbType");
String query;
if(dbType.equalsIgnoreCase("MSSQL"))
{
query="SELECT COUNT(1) FROM EOD_BOD_PROCESS_LOG_DTL WHERE EOD_BOD_PROCESS_ID = (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL) AND ISNULL(PROCESS_STATUS,'I')='P'";
}
else
{
query="SELECT COUNT(1) FROM EOD_BOD_PROCESS_LOG_DTL WHERE EOD_BOD_PROCESS_ID = (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL) AND IFNULL(PROCESS_STATUS,'I')='P'";
}
String result=ConnectionDAO.singleReturn(query);
int count=Integer.parseInt(result);
if(count>0)
{
%>
<!--<html:button property="startProcess" disabled="true" styleId="startProcess" styleClass="topformbutton2" value="Start Process" onclick="beginingOfDayStartprocess();"></html:button> 
-->
<logic:present name="disbButton">
<button type="button" name="startProcess" id="startProcess"  title="Alt+P" accesskey="P"  class="topformbutton3"  disabled="disabled"> 
<bean:message key="button.startProcess" /></button>
</logic:present>

<logic:notPresent name="disbButton">
<button type="button" name="startProcess" id="startProcess"  title="Alt+P" accesskey="P"  class="topformbutton3"  onclick="beginingOfDayStartprocess();"> 
<bean:message key="button.startProcess" /></button>
</logic:notPresent>
<%
}
else
{
%>
<!--<html:button property="startProcess" styleId="startProcess" styleClass="topformbutton2" value="Start Process" onclick="beginingOfDayStartprocess();"></html:button>
-->
<logic:present name="disbButtonBOD">
<button type="button" name="startProcess" id="startProcess"  title="Alt+P" accesskey="P"  class="topformbutton3"  disabled="disabled" style="opacity: 0.4;"> 
<bean:message key="button.startProcess" /></button>
</logic:present>

<logic:notPresent name="disbButtonBOD">
<button type="button" name="startProcess" id="startProcess"  title="Alt+P" accesskey="P"  class="topformbutton3"  onclick="beginingOfDayStartprocess();"> 
<bean:message key="button.startProcess" /></button>
</logic:notPresent>
<%
}
 %>
  
</html:form>
<div id="eodProcessBar" align="center">
 <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/processingBar.gif"  ></img>

</div>

</div>
</div>
</body>

<logic:present name="msg">
 <script type="text/javascript">
 	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.BODProcessComplete" />');
	}
	else
	{
		alert("BOD Process is already over for this Business date");
	}
 </script>
	
</logic:present>
</html:html>
