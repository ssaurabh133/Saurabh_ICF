
<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for ask admin password
 	Documentation    :- 
 -->
 
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<script type="text/javascript" src="js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/login.js"></script>

	</head>
	<body onload="enableAnchor();document.getElementById('askAdminPassword').adminPwd.focus();">
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/checkAdminPwd" method="post" styleId="askAdminPassword">
  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
   <fieldset>
  
  
  <legend><bean:message key="lbl.askAdminPwd"></bean:message></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
			
			<td width="20%"><bean:message key="lbl.askAdminPwd"/><font color="red">*</font></td>
			<td width="35%">
			 <html:password  styleClass="text" property="adminPwd" styleId="adminPwd" value="" maxlength="50" />
			</td>
			<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
			
	  </tr>
		  
		<tr>
	        <td> 
<!--	      <input type="button" name="clickButton" id="clickButton" class="topformbutton3" value="Admin Password" onclick="return checkAdminPwd();" />  </td>-->
	       <html:button property="button" value="Admin Password" styleClass="topformbutton3" onclick="return checkAdminPwd();"> </html:button> </td> 
	
		</tr> 
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>
	<br/> 
</html:form>
</div>

</div>
</body>
<logic:present name="sms">
<script type="text/javascript">
	 if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert('Please enter correct password');
	}
</script>
</logic:present>
</html:html>


