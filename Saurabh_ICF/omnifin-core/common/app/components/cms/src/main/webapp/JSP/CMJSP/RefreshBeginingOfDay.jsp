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

	<div id="result">
	<html:form action="/beginingOfDay" styleId="beginingOfDayForm"> 
  <fieldset>
  <legend><bean:message key="lbl.beginingOfDayheader" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		  <tr>
      <td class="white2" style="width:220px;"><strong><input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" /></strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.processName"/> </strong></td>
     <%-- <td class="white2" style="width:220px;" align="center"><strong> <bean:message key="lbl.imagetimer"/> </strong></td>--%>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.startTime"/></strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.endTime"/> </strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.beginingOfDaystatus"/></strong></td>
       <td class="white2" style="width:220px;"><strong><bean:message key="lbl.errorlog"/></strong></td>
    </tr>

     <% 
    List list= new ArrayList();
    list=(List)request.getAttribute("RefreshData");
    List data=new ArrayList();
    String bodFlag=(String)session.getAttribute("bodFlag");
    for(int a=0;a<list.size();a++)
    {
       data=(List)list.get(a);
    %>
    <input type="hidden" id="bodFlag" value="<%=CommonFunction.checkNull(bodFlag)%>" />
    <input type="hidden" id="startFlag" value="" />	
     <tr>  	 	
     <td class="white2" style="width:220px;"><input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" /></td>
	 <td class="white2" style="width:220px;"><%=CommonFunction.checkNull(data.get(0)).toString()%></td>
     <td class="white2" style="width:220px;"><%=CommonFunction.checkNull(data.get(1)).toString()%></td>
     <td class="white2" style="width:220px;"><%=CommonFunction.checkNull(data.get(2)).toString()%></td>
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
    if(status.equals("E")||status.equals("e"))
    {
     %>     
      <td class="white2" style="width:220px;"><a href="#" onclick="processBodError('<%=CommonFunction.checkNull(data.get(0)).toString()%>');">1234</a></td>
     <%
    }
    else
    {
    %>     
       <td class="white2" style="width:220px;"></td>
     <%
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
<button type="button" name="startProcess" disabled="disabled" id="startProcess" title="Alt+P" accesskey="P" class="topformbutton3" onclick="beginingOfDayStartprocess();"><bean:message key="button.startProcess" /></button> 
<%
}
else
{
%>
<logic:present name="disbButtonBOD">
<button type="button" name="startProcess" id="startProcess" class="topformbutton3" title="Alt+P" accesskey="P" disabled="disabled" style="opacity: 0.4;"><bean:message key="button.startProcess" /></button>
</logic:present>

<logic:notPresent name="disbButtonBOD">
<button type="button" name="startProcess" id="startProcess" class="topformbutton3" title="Alt+P" accesskey="P" onclick="beginingOfDayStartprocess();"><bean:message key="button.startProcess" /></button>
</logic:notPresent>
<%
}
 %> 
 </html:form > 
 <div  align="center">
 <logic:present name="bodRunningFlag">
 <logic:equal name="bodRunningFlag" value="B">
 <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/processingBar.gif" ></img>
 </logic:equal>
 <logic:notEqual name="bodRunningFlag" value="B">
 
 </logic:notEqual>
 </logic:present>
</div>


