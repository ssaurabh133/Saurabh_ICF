<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.logger.LoggerMsg"%>
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
	   
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
	</head>
	
<body onload="enableAnchor();JavaScript:refreshProcess(5000);">
<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
<html:hidden property="basePath" styleId="basePath" value="<%=request.getContextPath()%>" />
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<div id="centercolumn">	
	<div id="revisedcontainer">	
	<html:form action="/endOfDay" styleId="endOfDayForm" > 
  <fieldset>
  <legend><bean:message key="lbl.endofdayheader" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		  <tr>
      <td class="white2" style="width:220px;"><strong><input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" /></strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.processName"/> </strong></td>
     <%-- <td class="white2" style="width:220px;" align="center"><strong> <bean:message key="lbl.imagetimer"/></strong></td>--%>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.startTime"/></strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.endTime"/> </strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.endofdaystatus"/></strong></td>
       <td class="white2" style="width:220px;"><strong><bean:message key="lbl.errorlog"/></strong></td>
    </tr>

     <% 
     LoggerMsg.info("In endOfDayStartProcess jsp..............................");
    List list= new ArrayList();
    list=(List)session.getAttribute("ProcessDone");
    List data=new ArrayList();
    String bodFlag=(String)session.getAttribute("bodFlag");
    if(list!=null)
    {
     int max=list.size();
    for(int a=0;a<max;a++)
    {
       data=(List)list.get(a);
     %>
      <input type="hidden" id="bodFlag" value="<%=CommonFunction.checkNull(bodFlag)%>" />	
     <tr>  	 	
     <td class="white2" style="width:220px;"><input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" /></td>
	 <td class="white2" style="width:220px;"><%=CommonFunction.checkNull(data.get(0)).toString()%></td>
	 <td class="white2" style="width:220px;"><%=CommonFunction.checkNull(data.get(1)).toString()%></td>
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
    if(status.equals("E")||status.equals("e"))
    {
     %>     
      <td class="white2" style="width:220px;"><a href="#" onclick="processError('<%=CommonFunction.checkNull(data.get(0))%>');">1234</a></td>
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
<button type="button" name="startProcess" disabled="disabled" id="startProcess" class="topformbutton4" onclick="endOfDayStartprocess();"><bean:message key="button.startProcess" /></button> 
<%
}
else
{
%>
<logic:present name="disbButtonEOD">
<button type="button" name="startProcess" id="startProcess" class="topformbutton4"  disabled="disabled" style="opacity: 0.4;"><bean:message key="button.startProcess" /></button>
</logic:present>

<logic:notPresent name="disbButtonEOD">
<button type="button" name="startProcess" id="startProcess" class="topformbutton4"  onclick="endOfDayStartprocess();"><bean:message key="button.startProcess" /></button>
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
<logic:present name="procMsg">
    
     <logic:present name="paramValue">
     	
     	 	 <logic:equal name="paramValue" value="Y">
     	 	 	
     	 	 	 <script type="text/javascript">

				 	
								if(confirm('${procMsg}'))
								{
								refreshProcess(5000);
					            //location.href = "endOfDay.do?method=endOfProcessAfterConfirm";
					            submitAjaxAfterConfirm();
					           
								}else{
								document.getElementById('eodProcessBar').style.display='none';
								}
					   
				
				 </script>
     	 	 	
     	 	 </logic:equal>
     	 	 <logic:equal name="paramValue" value="N">
     	 	 	
     	 	 	 <script type="text/javascript">

				 	
						
						   alert("${procMsg}");
							
					   
				
					
					
				 </script>
     	 	 	
     	 	 </logic:equal>
     	 	 
    
     </logic:present>
 


</logic:present>



<logic:present name="msg">

 <script type="text/javascript">
 	
 	if('<%=request.getAttribute("msg").toString()%>'=='L')
		{
			alert('<bean:message key="msg.successfulEod" />');
			parent.location="<%=request.getContextPath()%>/logoff.do?stopUpdateQuery=";
	    }
	    else if('<%=request.getAttribute("msg").toString()%>'=='P')
	    {
	    	alert("EOD is over for same date ! please Logout");
	    }
	   
	    else
	    {
	    	alert("EOD can not be run as BOD is pending");
	    }
 </script>

</logic:present>
 <script type="text/javascript">
refreshProcess(5000);
 </script>
</body>

</html:html>
