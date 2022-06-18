<!--Author Name : Ritu Jindal-->
<!--Date of Creation :4 JUNE 2011 -->
<!--Purpose  : Information of Change Password Master-->
<!--Documentation : -->
<%@page import="com.logger.LoggerMsg"%>
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%-- <%@include file="/JSP/sessioncheck.jsp" %> --%>
<%@ page language="java" import="java.text.SimpleDateFormat,java.util.ResourceBundle"%>
<html:html>
<head>
 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/contentstyle.css"/>
 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/subpage.css"/>
<title><bean:message key="a3s.noida" /></title>
	 
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/changePasswordScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath()%>/js/md5.js"></script> 
<script type="text/javascript" async="" src="<%=request.getContextPath()%>/js/ga.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/sha.js"></script>

<script language="javascript">

function DisableCtrlKey(e)
{
	var code = (document.all) ? event.keyCode:e.which;
	var message = "Ctrl key functionality is disabled!";
	if (parseInt(code)==17)
	{
		alert(message);
		window.event.returnValue = false;
	}
}
function clearPassword()
{

	if(document.getElementById("oldPassword") && document.getElementById("newPassword") && document.getElementById("newPassword").value=='')
		{
		document.getElementById("oldPassword").value="";
		}
	enableAnchor();
}
function allowAlphaNumericOnly(evt)  
		  {  
		  var keyCode = (evt.which) ? evt.which : evt.keyCode;
		  if( (keyCode >=48 && keyCode <=57) ||(keyCode >=97 && keyCode <=122) || (keyCode >=65 && keyCode <=90) ||(keyCode== 9)|| (keyCode==32)||(keyCode==8)||(keyCode==45)||(keyCode==46)||(keyCode==47) || (keyCode==37) ||(keyCode==38) || (keyCode==39) || (keyCode==40)) 
		     {  
		      return true;  
		     }  
		   else  
		     {  
		     alert("Special characters are not allowed");  
		     return false;  
		     }  
		  } 
</script>

</head>

<body onload="clearPassword();" oncontextmenu="return false;">

<html:form styleId="changePasswordMasterForm"  method="post"  action="/changePasswordMaster" >
 <input type="hidden" name="randomAlphaNumericSaltFroPass" id='randomAlphaNumericSaltFroPass' value="${requestScope.randomAlphaNumericSaltFroPass}" />
<html:javascript formName="ChangePasswordMasterAddDynaValidatorForm"/>
<html:errors/>
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<fieldset>
<legend><bean:message key="lbl.changePasswordMaster" /></legend>
  <table width="100%">
    <tr>
   <%--   <td ><bean:message key="lbl.iduser" /><span><font color="red">*</font></span></td>
      <td >
      
      <logic:present name="flagLogin">
 	
           <html:text property="userId" styleClass="text" value="${requestScope.name}" styleId="userId" maxlength="10" readonly="true"  />
       </logic:present>
       <logic:present name="flagCP">
      <html:text property="userId" styleClass="text" value="${requestScope.user}" styleId="userId" maxlength="10" readonly="true"  />
      
  </logic:present>
         </td>
       </tr><tr> Rohit Commented for security --%>
   <input type="hidden" name="flagLogin" id="flagLogin" value="<%=request.getAttribute("flagLogin")%>" />
      <td width="13%"><bean:message key="lbl.oldPassword" /><span><font color="red">*</font></span></td>
      <td width="13%" ><html:password property="oldPassword" styleClass="text" styleId="oldPassword" maxlength="12" onkeydown="return DisableCtrlKey(event);" /></td>
    </tr><tr>
      <td width="13%"><bean:message key="lbl.newPassword" /><span><font color="red">*</font></span></td>
      <td width="13%" ><html:password property="newPassword" styleClass="text" styleId="newPassword" maxlength="12" onkeydown="return DisableCtrlKey(event);"/></td>
    </tr><tr>
      <td width="13%"><bean:message key="lbl.confirmPassword" /><span><font color="red">*</font></span></td>
      <td width="13%" ><html:password property="confirmPassword" styleClass="text" styleId="confirmPassword" maxlength="12" onkeydown="return DisableCtrlKey(event);"/></td>
   </tr>
    
     <logic:notPresent name="flagLogin">
      
 <%  LoggerMsg.info("--------------------------flag-->"+ request.getAttribute("flagCP"));%>
     <tr>
     <td>
    
    <br>
  	
     <input type="hidden" name="flag" id="flag" value="CP" />
      <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return validatePwd(document.getElementById('newPassword').value);" class="topformbutton2" ><bean:message key="button.save" /></button>
  
    <br></td>
     </tr>
	 
     <tr>
			<td valign="top" height="30" align="center" colspan="5">
			<span style="color: rgb(255, 0, 0);font-size:10px;" ><bean:message key="msg.noReminderPasswordMsg" /></span>
				</td>
	</tr> 
	<!-- Rohit CHanges for security -->
	  <script type="text/javascript">
    
     if('<%=request.getAttribute("randomAlphaNumericSaltFroPass")%>'=='null'||'<%=request.getAttribute("randomAlphaNumericSaltFroPass")%>'=='')
 	{
    	window.location.replace("<%=request.getContextPath()%>/logoff.do");
 	} 
	if('<%=request.getAttribute("flagCP")%>'=='null')
	{
		window.location.replace("<%=request.getContextPath()%>/logoff.do");
	}
	</script>
	<!-- End...... -->
</logic:notPresent>

    <logic:present name="flagLogin">
     
       <tr>

      <td width="13%">Question1<span><font color="red">*</font></span></td>
   
	      <td width="13%" ><html:text  styleId="ques1" styleClass="text" property="ques1"  onkeypress="return allowAlphaNumericOnly(event);" onkeydown="return DisableCtrlKey(event);"/></td>
      <TD width="13%">Answer1<span><font color="red">*</font></span></TD>
         <td width="13%" ><html:password styleId="ans1" styleClass="text" property="ans1"   onkeydown="return DisableCtrlKey(event);"/></td>
         </tr>
   <tr>
      <td width="13%">Question2<span><font color="red">*</font></span></td>
      <td width="13%" ><html:text styleId="ques2" styleClass="text" property="ques2"  onkeypress="return allowAlphaNumericOnly(event);" onkeydown="return DisableCtrlKey(event);"/></td>
       <TD width="13%">Answer2<span><font color="red">*</font></span></TD>
         <td width="13%" ><html:password styleId="ans2" styleClass="text" property="ans2"   onkeydown="return DisableCtrlKey(event);"/></td>

         </tr>
             
     <tr>
     <td>
    
    <br>
     
     <input type="hidden" name="name" id="name" value="<%=request.getAttribute("name")%>" />		
     <input type="hidden" name="flag" id="flag" value="CL" />
     <button type="button" name="save" id="save" title="Alt+V" accesskey="V" onclick="return validatePwd(document.getElementById('newPassword').value);" class="topformbutton2" ><bean:message key="button.save" /></button>
    <br></td> </tr>
 
    <tr>
			<td valign="top" height="30" align="center" colspan="5">
			<span style="color: rgb(255, 0, 0);font-size:10px;" ><bean:message key="msg.noReminderPasswordMsg" /></span>
				</td>
	</tr>  
</logic:present>
	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="msg">
<em><script type="text/javascript">

    
	
	
	if('<%=request.getAttribute("msg")%>'=='M')
	{
	var userid=document.getElementById("name");
		alert('<bean:message key="lbl.oldPassNotMatch" />');
	
	}
	
	else if('<%=request.getAttribute("msg")%>'=='S')
	{
		alert('<bean:message key="lbl.dataSave" />');
		window.parent.location.href="<%=request.getContextPath()%>/loginInput.do";
	}
	else if('<%=request.getAttribute("msg")%>'=='E')
	{
	alert("New password should be different from last 5 password.");

	}
	
	
</script></em>
</logic:present>
	  
 <logic:present name="logmsg">
<script type="text/javascript">

 if(('<%=request.getAttribute("logmsg")%>')=="LM")
	{	
		alert('<bean:message key="lbl.oldPassNotMatch" />');
		document.getElementById("oldPassword").value="";	
		document.getElementById("oldPassword").focus();	
	}
	
	
	else if(('<%=request.getAttribute("logmsg")%>')=="LS")
	{
		alert('<bean:message key="lbl.dataSave" />');
		location.href="<%=request.getContextPath()%>/loadLMS.do";
		
	}
	else if(('<%=request.getAttribute("logmsg")%>')=="LE")
	{
		alert("New password should be different from last 5 password.");

	}

	
</script>
</logic:present> 
  </body>
</html:html>