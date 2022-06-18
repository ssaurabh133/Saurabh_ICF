<!--Author Name : Vishal SIngh-->
<!--Date of Creation : 14-Mrch-2012-->
<!--Purpose  : Information of Holiday Maintenance -->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>

<script type="text/javascript">

</script>

 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/loanInitiation.js"></script>
		<script type="text/javascript" src="<%=path%>/js/jquery.simpletip-1.3.1.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/holidayScript.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/bankScript.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
     <!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
<! [endif] -->   
</head>

<body onload="enableAnchor();init_fields();" >

<html:form styleId="holidayMaintenanceForm"  method="post"  action="/holiDayMasterAdd" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />

<fieldset>
<legend><bean:message key="lbl.holidayMaster" /></legend>
  <table width="100%">
    <tr>
   
	<html:hidden  property="holidayId" styleId="holidayId"  value="${requestScope.list[0].holidayId}" />
       <td><bean:message key="lbl.holidayType" /></td>
       <td><html:select property="holidayType" styleClass="text" styleId="holidayType"  disabled="true" value="${requestScope.list[0].holidayType}"  onchange="changeHolidayType(value);">
                      
              	      <html:option  value="Daily">Daily</html:option>
		      	      <html:option  value="Weekly" >Weekly</html:option>
		            </html:select></td>
       </tr>
       
       <tr  >       
      <td><bean:message key="lbl.holidayDate" /></td>      
     <td><html:text property="holidayDate1" styleClass="text" styleId="holidayDate1" maxlength="50"  disabled="true" value="${requestScope.list[0].holidayDate}" onchange="return checkDate('holidayDate1');"  /></td>   
     
     
     <td><bean:message key="lbl.holidayDesc" /></td>      
     <td><html:text property="holidayDes" styleClass="text" styleId="holidayDes" maxlength="50"  disabled="true"  value="${requestScope.list[0].holidayDes}" /></td>
     
                     
    </tr>
   
    <tr  >
    
      <td><bean:message key="lbl.status" /></td>
      <td>
     
      <logic:equal value="Active" name="status">
         <input type="checkbox" name="holidayStatus" id="holidayStatus" checked="checked" />
       </logic:equal>
      <logic:notEqual value="Active" name="status">
         <input type="checkbox" name="holidayStatus" id="holidayStatus"  />
      </logic:notEqual>

     </td> 
    </tr>    
      
    <tr >
    <td>    
   
      
      <button type="button"  name="save"       
		      id="save" class="topformbutton2"  title="Alt+V" accesskey="V" 
		      onclick="fnEditHolidayStatus();;" tabindex="8"><bean:message key="button.save" /></button>
   
    </td>
    </tr>    
   	</table>
</fieldset>           
</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("holidayMaintenanceForm").action="holidayMaintenanceBehind.do";
	    document.getElementById("holidayMaintenanceForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("holidayMaintenanceForm").action="holidayMaintenanceBehind.do";
	    document.getElementById("holidayMaintenanceForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{   
	   
		alert("<bean:message key="msg.notepadError" />");
	}
	else
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}

	
	
</script>
</logic:present>
  </body>
		</html:html>
