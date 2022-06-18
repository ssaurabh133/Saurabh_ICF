<%--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for ask admin password
 	Documentation    :- 
 --%>
 
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
   	 
		 <%-- css and jquery for Datepicker --%>
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <%-- css and jquery for Datepicker --%>

		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/commonGcdFunctions.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath() %>/js/commonScript/commonMethods.js"></script>

	    <script type="text/javascript" src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script >
	
		$(function() {
		$("#newBusinessDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1900:+10',
	     showOn: 'both',
		<logic:present name="image">
    	   		buttonImage: '<%=request.getContextPath() %>/${image }/calendar.gif',
            </logic:present>
    		<logic:notPresent name="image">
    			buttonImage: '<%=request.getContextPath() %>/images/theme1/calendar.gif',
    		</logic:notPresent>
		 buttonImageOnly: true,
		 dateFormat:"<bean:message key="lbl.dateFormat"/>",
		 defaultDate:'${userobject.businessdate }'
	
	
	});
     	});
	</script>
	</head>
	<body onload="enableAnchor();document.getElementById('businessDateChange').adminPwd.focus();init_fields();">
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<form action="" method="post" id="businessDateChange">
  <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
   <fieldset>
  
  
  <legend><bean:message key="lbl.businessDateChange"></bean:message></legend>
  <bean:message key="lbl.businessDateChangeMsg"></bean:message><br /><br />
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
			
			<td width="20%"><bean:message key="lbl.origionalBusinessDate"/></td>
			<td width="35%"><input type="text" value="${sessionScope.maxBusinessDate }" id="origionalBusinessDate" readonly="readonly"/>
			</td>
			
			
			<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
			
	  </tr>
		
		<tr>
			
			<td width="20%"><bean:message key="lbl.currentBusinessDate"/></td>
			<td width="35%"><input type="text" value="${userobject.businessdate }" id="currentBusinessDate" readonly="readonly"/>
			</td>
			
			
			<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
			
	  </tr>
		  
		  <tr>
			
			<td width="20%"><bean:message key="lbl.newBusinessDate"/><font color="red">*</font></td>
			<td width="35%">
			<input type="text" value="" id="newBusinessDate" maxlength="10" onchange="checkDateNew();"/>
			</td>
			<td width="17%">&nbsp;</td>
			<td width="28%">&nbsp;</td>
		
	  </tr>
		<tr>
	        <td> 
	      <html:button property="button" value="Save" styleClass="topformbutton2" onclick="return saveNewBusinessDate();"> </html:button> </td>
	
		</tr> 
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>
	<br/> 
 </form>
</div>

</div>
</body>
<logic:present name="sms">
<script type="text/javascript">

 	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		//alert("");
		
		if(confirm("<bean:message key="lbl.businessDateChangeMsg" />")==true)
		{
		  parent.location.href="<%=request.getContextPath()%>/logoff.do";
		}
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
	
		alert('<%=request.getAttribute("errorMsg")%>');
	}
</script>
</logic:present>
</html:html>


