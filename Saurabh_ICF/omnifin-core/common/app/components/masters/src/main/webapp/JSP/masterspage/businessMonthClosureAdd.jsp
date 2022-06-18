<!--Author Name : Asesh Kuumar->
<!--Date of Creation : 11-feb-2013-->
<!--Purpose  : Information of business Month Ratio  Master Ad-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="com.login.roleManager.UserObject;"  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserObject userobj=(UserObject)session.getAttribute("userobject");
String businessDate=userobj.getBusinessdate();
String year=businessDate.substring(6);
request.setAttribute("year",year);


%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/businessMonthClosure.js"/></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>

<script type="text/javascript">
$(function() {
	$("#endDate").datepicker({
			changeMonth: true,
		changeYear: true,
        yearRange: '1900:+10',
        showOn: 'both',
        buttonImage: document.getElementById("CalImage").value,
        buttonImageOnly: true,
        dateFormat: document.getElementById("formatD").value,
		defaultDate: document.getElementById("businessdate").value
        
	});
});
				    	
				    	
</script>

<!--   <script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	// document.getElementById('subDealerMaster').subDealerCode.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	//document.getElementById('subDealerMaster').subDealerCode.focus();
     }
     return true;
}
</script> -->
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


 <body onload="enableAnchor();fntab();init_fields();" onunload="closeAllLovCallUnloadBody();">
 <div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<html:form action="/businessMonthAdd" styleId="businessMonthAddForm" method="POST" >
 <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
 
 <html:errors/>
<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    		<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
            <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>

<fieldset>

<legend><bean:message key="lbl.businessMonthClosureMasterSearch" /></legend>

  <table width="100%" height="86">
    
<logic:present name="editVal">
<input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
<input type="hidden" id=businessMonth name="businessMonth" value="${businessMonth}"/>

<tr>
          <td>Year</td>
		<td>
				<html:text property="businessYear"  styleId="businessYear" styleClass="text" value="${list[0].businessYear}" maxlength="10" readonly="true"/>
	    </td>
		  <td>Month<span><font color="red">*</font></span></td>
	      <td>
				<html:text property="businessMonthss" styleId="businessMonthss" styleClass="text" value="${list[0].businessMonthss}" disabled="true"/>
	    </td>
	
	 </tr>
    <tr>
          <td><bean:message key="lbl.bmStartDate"/><span><font color="red">*</font></span></td>
		<td>
			<!-- <html:text  styleId="showStartDate" property="startDate" value="${list[0].startDate}" readonly="true"/> -->
			<html:hidden property="startDate"  styleId="startDate1" styleClass="text" value="${list[0].startDate}"/>
				<input type="text" id=showStartDate name="showStartDate" value="${list[0].startDate}" readonly="readonly"/>
	    </td>
		  <td><bean:message key="lbl.bmEndDate" /><span><font color="red">*</font></span></td>
	      <td>
				<html:text property="endDate"  styleId="endDate" styleClass="text" value="${list[0].endDate}" maxlength="10" onchange="checkDate('endDate');validateClosureDate();"/>
	    </td>
	
	 </tr>
	 
	 <tr>
	 <td> <bean:message key="lbl.bmStatus"/> </td>   
	  <logic:equal value="A" name="status">
       <td>  <input type="checkbox" name="recStatus" id="recStatus" checked="checked" /></td>
       </logic:equal>
      <logic:notEqual value="A" name="status">
       <td><input type="checkbox" name="recStatus" id="recStatus"  /></td>
      </logic:notEqual>
	 
  </tr>     

  </logic:present>
  
  <logic:notPresent name="editVal">
   <input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/> 
   <input type="hidden" id=businessMonth name="businessMonth" value="${businessMonth}"/>
   <tr>
          <td>Year</td>
		<td>
				<html:text property="businessYear"  styleId="businessYear" styleClass="text" value="${year}" maxlength="10" readonly="true"/>
	    </td>
		  <td>Month<span><font color="red">*</font></span></td>
	      <td>
				<html:select property="businessMonthss" styleId="businessMonthss" styleClass="text" value="" onchange="setStartDate()">
							<option value="">--Select--</option>
							<option value="1">January</option>
							<option value="2">February</option>
							<option value="3">March</option>
							<option value="4">April</option>
							<option value="5">May</option>
							<option value="6">June</option>
							<option value="7">July</option>
							<option value="8">August</option>
							<option value="9">September</option>
							<option value="10">October</option>
							<option value="11">November</option>
							<option value="12">December</option>

					</html:select>
	    </td>
	
	 </tr>
   <tr>
          <td><bean:message key="lbl.bmStartDate"/><span><font color="red">*</font></span></td>
		<td>
				<html:text property="startDate"  styleId="startDate" styleClass="text" value="" maxlength="10" readonly="true"/>  
			<!-- 	<html:text property="startDate"  styleId="startDate" styleClass="text" value="" maxlength="10"  onchange="checkDate('startDate');ValidateStartDate();"/> -->
	    </td>
		  <td><bean:message key="lbl.bmEndDate" /><span><font color="red">*</font></span></td>
	      <td><html:text property="endDate"  styleId="endDate" styleClass="text" value="" maxlength="10" onchange="checkDate('endDate');validateClosureDate();"/>
	    </td>
	
	 </tr>
	 
	 <tr>
	 <td><bean:message key="lbl.bmStatus"/> </td>   
       <td><input type="checkbox" name="recStatus" id="recStatus"  checked="checked"/></td>
	 
  </tr>     
  </logic:notPresent>
   
  
  <tr><td>
     <logic:present name="editVal">
      <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditBusinessMonthClosure();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    
     <logic:present name="editValUpdate">
      <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnEditBusinessMonthClosure();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
   
   <logic:present name="save">
    <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button"  name="save" id="save" title="Alt+V" accesskey="V" onclick="return fnSaveBusinessMonth();" class="topformbutton2" ><bean:message key="button.save" /></button>
   </logic:present>
    <br></td> 
 </tr>
  
		
	</table>		

</fieldset></html:form>

<logic:present name="sms">
		<script type="text/javascript">

    if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("businessMonthAddForm").action="closureMarketingBehindAction.do";
	    document.getElementById("businessMonthAddForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("businessMonthAddForm").action="closureMarketingBehindAction.do";
	    document.getElementById("businessMonthAddForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	} 
	else if('<%=request.getAttribute("sms").toString()%>'=='DE')
	{
		alert("<bean:message key="lbl.monthExist" />");
		
	} 
	
	else if('<%=request.getAttribute("sms").toString()%>'=='UPDE')
	{
		alert("<bean:message key="lbl.monthExist" />");
		
	} 
	
	
	</script>
</logic:present>
</body>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
</html:html>
