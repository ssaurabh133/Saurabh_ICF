<!--Author Name :- Mradul Agarwal-->
<!--Date of Creation : 19_Jan_2013-->
<!--Purpose :-  Stationary Addition Screen-->

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="com.logger.LoggerMsg"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="" />
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
</logic:notPresent>
		<!-- css for Datepicker -->
<link type="text/css" href="development-bundle/demos/demos.css" rel="stylesheet" />
<link type="text/css"
	href="development-bundle/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<!-- jQuery for Datepicker -->
<script type="text/javascript" src="development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="development-bundle/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="development-bundle/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/stationaryMasterScript.js"></script>

</head>
<body onload="enableAnchor();">
	<div align="center" class="opacity" style="display:none" id="processingImage"></div>
		<html:form action="/stationaryIssuanceMasterAtCM" styleId="StationaryMasterForm" >
		<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"  />
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
		<logic:present name="image">
				<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
    	<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    	</logic:notPresent>
				<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
				
				
		<fieldset> <legend><bean:message key="lbl.receipt_details"/></legend>
		<table width="100%" border="0" cellspacing="2" cellpadding="1">
			<tr>
			   <td><bean:message key="lbl.receiptNo"/><span><font color="red">*</font></span></td>
							
				<td>
				  
					<html:text property="receiptNo" styleClass="text" styleId="receiptNo" maxlength="10" onkeypress="return isNumberKey(event);" />
					
			   </td>				
				<td><bean:message key="lbl.status"/><span><font color="red">*</font></span></td>
				<td>
				<html:select property="status" styleClass="text" styleId="status">						
					<html:option value="">--Select--</html:option>
					<html:option value="X">Canceled</html:option>
				</html:select></td>
			</tr>	
			<tr>
			<td><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>							
			<td>				  
				<html:text property="remarks" styleClass="text" styleId="remarks" maxlength="100" />
		    </td>
		    <td><bean:message key="lbl.userNam"/><span><font color="red">*</font></span></td>							
			<td>				  
				<html:text property="userName" styleClass="text" styleId="userName" maxlength="150" value="${userName}" readonly="true"/>
		    </td>		
			</tr>
			<tr> 
				<td align="left" colspan="2" class="form2">
				
				<button type="button" name="Search" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveForwaradCancel();">
				<bean:message key="button.save" /></button>
					
				</td>
			</tr>
		</table>
	    </fieldset>
			
	</html:form>
 <logic:present name="msg">
	<script type="text/javascript">

   if('<%=request.getAttribute("msg")%>'=='S')
	{
		alert("<bean:message key="msg.notepadSaved"/>");
	}
	else  if('<%=request.getAttribute("msg")%>'=='UC')
	{
	   alert("<bean:message key="lbl.usedCanceled"/>");
	}
	else  if('<%=request.getAttribute("msg")%>'=='PUC')
	{
	   alert("<bean:message key="lbl.prevUsedCanceled"/>");
	}
	else  if('<%=request.getAttribute("msg")%>'=='U')
	{
	   alert("<bean:message key="lbl.gl.receiptMaker"/>");
	}
	else
	{
		alert("<bean:message key="lbl.dataError"/>");
		
	}
</script>
</logic:present>
	
	</body>
</html>