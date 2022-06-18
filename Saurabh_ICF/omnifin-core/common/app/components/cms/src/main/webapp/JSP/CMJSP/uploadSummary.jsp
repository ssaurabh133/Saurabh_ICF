<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@page import="com.logger.LoggerMsg"%>
<%@page import="java.util.logging.Logger"%>
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
	<body onload="enableAnchor();init_fields();">
	<logic:present name="uploadSummary">
	<%
	String Batch_id="";
	String makerdate="";
	String No_Of_Rejected="";
	String No_Of_Uploaded="";
	String total="";
	total=(String)session.getAttribute("TotalUploadandReject");
	List data=new ArrayList();	
	ArrayList list=(ArrayList)request.getAttribute("uploadSummary");
for(int a=0;a<list.size();a++)
{
data=(ArrayList)list.get(a);
	Batch_id=data.get(0).toString();
    makerdate=data.get(1).toString();
	No_Of_Rejected=data.get(2).toString();
	No_Of_Uploaded=data.get(3).toString();	
}
//LoggerMsg.info(data.get(0).toString());
	
	 %>
	 <%
	UserObject userobj=(UserObject)session.getAttribute("userobject");
	 String userId=userobj.getUserId();
	  %>
	<div id="revisedcontainer">
		
	<fieldset>	  
	<legend align="top"><bean:message key="lbl.uploadSummary"/></legend>  
	   <table width="100%" border="0" cellspacing="1" cellpadding="4">	  
       	<tr>
       	 <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>"/>
       	 <td  width="23%" style="width:23%"><b><bean:message key="lbl.UserDate"/></b>&nbsp;<%=makerdate%></td>
		  <td align="center" width="23%" style="width:23%"><b><bean:message key="lbl.batchId"/></b>&nbsp;<%=Batch_id%></td>		 
	      <td align="right" width="23%"><b><bean:message key="lbl.userName"/></b>&nbsp;<%=userId%></td>
		</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
     <tr>

      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.NoOfR_uploaded"/> </strong></td>
      <td class="white2" style="width:250px;"><strong><bean:message key="lbl.NoOfR_rejected"/> </strong></td>
      </tr>
      <tr>
		  <td><%=No_Of_Uploaded%></td>	
	      <td><%=No_Of_Rejected%></td>
		</tr>
		  
      </table>
 
	  </fieldset> 
	 &nbsp <b><bean:message key="lbl.totalRecords"/></b>&nbsp;&nbsp;&nbsp;&nbsp;<%=total%><br/> 
	 &nbsp <b><bean:message key="lbl.endofrecords"/></b></br>
	  &nbsp <b><bean:message key="lbl.uploadSummary1"/></b></br>
	 &nbsp <button type="button" name="close" class="topformbutton2" id="close" title="Alt+C" accesskey="C" onclick="javascript:window.close();"><bean:message key="button.close" /></button>
</div>
</logic:present>
<logic:present name="nodata">
	 <div id="revisedcontainer">
		
	<fieldset>	  
	<legend align="top"><bean:message key="lbl.uploadSummary"/></legend>  
	   <table width="100%" border="0" cellspacing="1" cellpadding="4">	  
      	
      	<tr><td align="center" ><b><font color="red"><bean:message key="lbl.nodata"/> </font></b></td></tr>
		  
      </table>
 
	  </fieldset>  
	 <button type="button" name="close" class="topformbutton2" id="close" title="Alt+C" accesskey="C" onclick="javascript:window.close();"><bean:message key="button.close" /></button>
</div>
  </logic:present> 
</body>
</html:html>

 

 