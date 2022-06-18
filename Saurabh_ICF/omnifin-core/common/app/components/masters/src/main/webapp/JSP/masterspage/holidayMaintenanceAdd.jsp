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

String holidayType=(String)request.getAttribute("holidayType");
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
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form styleId="holidayMaintenanceForm"  method="post"  action="/holiDayMasterAdd" >

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
       <td><html:select property="holidayType" styleClass="text" styleId="holidayType"  value='<%=holidayType%>' onchange="changeHolidayType();">
                      
              	      <html:option  value="Daily">Daily</html:option>
		      	      <html:option value="Weekly">Weekly</html:option>
		            </html:select></td>
       </tr>
       
       <tr id="daily" style=display:true; >            
      <td><bean:message key="lbl.holidayDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><span><font color="red">*</font></span></td>      
     <td><html:text property="holidayDate" styleClass="text" styleId="holidayDate" maxlength="50"  onchange="return checkDate('holidayDate');"  /></td>   
     
     
     <td><bean:message key="lbl.holidayDesc" /><span><font color="red">*</font></span></td>      
     <td><html:text property="holidayDes" styleClass="text" styleId="holidayDes" maxlength="50"  value="" onchange="return fnChangeCase('holidayMaintenanceForm','holidayDes');" /></td>
     
                     
    </tr>
   
    <tr id="weekly" style=display:none>
    
      <td><bean:message key="lbl.holidayDay" /><span><font color="red">*</font></span></td>   
      <td>
     <html:select property="holidayDay" styleClass="text" styleId="holidayDay"  >
                      <html:option value="">--Select--</html:option>
              	      <!--<html:option  value="MON">Monday</html:option>
		      	      <html:option value="TUE">Tuesday</html:option>
		      	      <html:option  value="WED">Wednesday</html:option>
		      	      <html:option value="THU">Thursday</html:option>
		      	      <html:option  value="FRI">Friday</html:option>-->
		      	      <html:option value="SAT">Saturday</html:option>
		      	      <html:option value="SUN">Sunday</html:option>
		            </html:select>
     </td>   
     
     
     <td><bean:message key="lbl.weekendDay" /></td>  
     
     <td>
      1<INPUT NAME="week" Id="week" TYPE="CHECKBOX" VALUE="1" checked="checked">
       2<INPUT NAME="week" Id="week" TYPE="CHECKBOX" VALUE="2">
       3<INPUT NAME="week" Id="week" TYPE="CHECKBOX" VALUE="3">
         4<INPUT NAME="week" Id="week" TYPE="CHECKBOX" VALUE="4">
          5<INPUT NAME="week" Id="week" TYPE="CHECKBOX" VALUE="5">
     
     </td>      
      
      
    </tr>
    
    
      
    <tr id="daySave" style=display:true; ><td> 
    
							                      <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
							                      <button type="button"  name="saveButton" 
							                      id="saveButton" class="topformbutton2"  title="Alt+V" accesskey="V" 
							                      onclick="return saveHoliday('holidayDate','${userobject.businessdate}');" tabindex="8"><bean:message key="button.save" /></button>
							             
  
    <br></td> </tr>
    
    <tr id="weekSave" style=display:none><td>  
    
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
							                      <button type="button"  name="saveWeeklyButton" 
							                      id="saveWeeklyButton" class="topformbutton2"  title="Alt+V" accesskey="V" 
							                      onclick="return saveHolidayforWeekend();" tabindex="8"><bean:message key="button.save" /></button>
   
    <br></td> </tr>
    
    <html:hidden  property="fromPage" styleId="fromPage"  value="addHoliday" />

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
	    changeHolidayType();
		alert("<bean:message key="lbl.holidayDataSaved" />");
		
	}
	else
	{
		alert("<bean:message key="msg.notepadError" />");	
	}

	
	
</script>

</logic:present>
  </body>
		</html:html>
