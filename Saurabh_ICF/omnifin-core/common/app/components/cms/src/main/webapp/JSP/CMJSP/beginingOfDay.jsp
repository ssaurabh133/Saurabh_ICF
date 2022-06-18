<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.connect.CommonFunction"%>
<%@page import="com.connect.ConnectionDAO"%>
<%@page import="com.logger.LoggerMsg"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
	   <style type="text/css">
		.proBar{
			width:100%;
			height:100%;
			background:url("../../images/theme1/processingBar.gif") 45% 100px no-repeat;
			position: fixed;
			top: 100px;
			left: 0px
}
	</style>
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
      <%--<td class="white2" style="width:220px;" align="center"><strong> <bean:message key="lbl.imagetimer"/> </strong></td>--%>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.startTime"/></strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.endTime"/> </strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.beginingOfDaystatus"/></strong></td>
       <td class="white2" style="width:220px;"><strong><bean:message key="lbl.errorlog"/></strong></td>
    </tr>

     <% 
    List list= new ArrayList();
    list=(ArrayList)session.getAttribute("eodData");
    List data=new ArrayList();
    LoggerMsg.info("list.size()....is "+list);
    String bodFlag=(String)session.getAttribute("bodFlag");
   	if(list!=null){
 
     int max=list.size();
    for(int a=0;a<max;a++)
    {
       data=(List)list.get(a);
      
    %>
      <input type="hidden" id="bodFlag" value="<%=CommonFunction.checkNull(bodFlag)%>" />
      <input type="hidden" id="startFlag" value="" />	
     <tr>  	 	
     <td class="white2" style="width:220px;"><input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" /></td>
	 <td class="white2" style="width:220px;"><%=CommonFunction.checkNull(data.get(0)).toString()%></td>
     <td class="white2" style="width:220px;"> <%=CommonFunction.checkNull(data.get(1)).toString()%></td>
     <td class="white2" style="width:220px;"><%=CommonFunction.checkNull(data.get(2)).toString()%></td>
    <%
    
     if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("P"))
     {
     %>
     <td class="white2" style="width:220px;">
  	<img align="middle"  src="<%=request.getContextPath()%>/images/theme1/loder-blue.gif" width="20" height="20" ></img>
 	&nbsp;&nbsp;&nbsp;<strong><bean:message key="lbl.process"/></strong></td>
     <%
     }
     
     else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("F"))
     {
      %>
     <td class="white2" style="width:220px;">
	 <img align="middle" src="<%=request.getContextPath()%>/images/theme1/green.gif"  width="20" height="20" ></img>
  	 &nbsp;&nbsp;&nbsp;<strong><font color="black"><bean:message key="lbl.processFinish"/></font></strong></td>
     <%
     }
     
      else if(CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("E"))
     {
      %>
     <td class="white2" style="width:220px;">
  <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/red.gif" width="20" height="20" ></img>
 &nbsp;&nbsp;&nbsp;<strong><font color="Red"><bean:message key="lbl.ErrorInProcess"/></font></strong></td>
     <%
     }
     else
     {
     %>
     <td class="white2" style="width:220px;">
     &nbsp;&nbsp;&nbsp;<strong><font color="Black"><bean:message key="lbl.noProcess"/></font></strong></td>
     <%
     }
     
    String status=CommonFunction.checkNull(data.get(3)).toString();
    if(status.equalsIgnoreCase("E"))
    {
     %>     
      <td class="white2" style="width:220px;"><a href="#" onclick="processBodError('<%=CommonFunction.checkNull(data.get(0)).toString().toUpperCase()%>');">1234</a></td>
     <%
    }
    else
    {
    %>     
       <td class="white2" style="width:220px;"></td>
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
<button type="button" name="startProcess" disabled="disabled" id="startProcess" title="Alt+P" accesskey="P" class="topformbutton3" onclick="beginingOfDayStartprocess();"> <bean:message key="button.startProcess" /></button>
<%
}
else
{
%>
<button type="button" name="startProcess" id="startProcess"  title="Alt+P" accesskey="P"  class="topformbutton3"  onclick="beginingOfDayStartprocess();"> <bean:message key="button.startProcess" /></button>
<%
}
 %>
  </html:form>
</div>
 <div align="center" class="proBar" style="display: none"	id="imgBar"></div>
</div>

</body>
</html:html>
