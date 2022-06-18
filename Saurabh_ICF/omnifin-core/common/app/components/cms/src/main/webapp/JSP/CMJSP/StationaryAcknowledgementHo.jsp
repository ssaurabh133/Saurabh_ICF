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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/stationaryMasterScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
</head>
<script type="text/javascript">
			$(function() {
				$("#additionDate").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+10',
		            showOn: 'both',
						<logic:present name="image">
    	   		buttonImage: '<%=request.getContextPath()%>/${image }/calendar.gif',
            </logic:present>
    		<logic:notPresent name="image">
    			buttonImage: '<%=request.getContextPath()%>/images/theme1/calendar.gif',
    		</logic:notPresent>
					buttonImageOnly: true,
					dateFormat:"<bean:message key="lbl.dateFormat"/>",
					defaultDate:'${userobject.businessdate }'
			})
			});
			
</script>
<body onload="enableAnchor();">
	<div align="center" class="opacity" style="display:none" id="processingImage"></div>
		<html:form action="/branchAcknowledgmentAtCM" styleId="StationaryMasterForm" >
		<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"  />
		<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
		<logic:present name="image">
				<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
    	<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    	</logic:notPresent>
				<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
				<input type="hidden" id="returnToHODate" name="returnToHODate" value="${edit[0].returnToHODate}"/>
				<input type="hidden" id="issueDate" name="issueDate" value="${edit[0].additionDate}"/>
				<input type="hidden" id="issueDate1" name="issueDate1" value="${edit[0].additionDate}"/>
		<fieldset> <legend><bean:message key="lbl.hoAcknowledgment"/></legend>
		<table width="100%" border="0" cellspacing="2" cellpadding="1">
			<tr>				     		     
			     <logic:present name="bookIssue">	
			     <td><bean:message key="lbl.BookNo"/><span><font color="red">*</font></span></td>						
				 <td>				  
					<html:text property="bookIssue" styleClass="text" styleId="bookIssue" maxlength="100" readonly="true" value="${bookIssue}"/>
				 </td>
				 </logic:present>
				 <logic:notPresent name="bookIssue">	
				 <td><bean:message key="lbl.BookNo"/><span><font color="red">*</font></span></td>						
				 <td>				  
					<html:text property="bookIssue" styleClass="text" styleId="bookIssue" maxlength="100" readonly="true" value="${edit[0].bookIssue}"/>
				 </td>
				 </logic:notPresent>
				 <logic:present name="edit">
				 <logic:notEqual name="additionDate" value=""> 	   				
				 <td><bean:message key="lbl.acknoDate"/><span><font color="red">*</font></span></td>
				 <td><html:text property="additionDate" styleClass="text" styleId="additionDat" value="${edit[0].additionDate}" readonly="true"  maxlength="10" />
				 </td>	
				 </logic:notEqual>
				 </logic:present>
				 <logic:equal name="additionDate" value=""> 	 
				 <td><bean:message key="lbl.acknoDate"/><span><font color="red">*</font></span></td>
				 <td><html:text property="additionDate" styleClass="text" styleId="additionDate" value="" maxlength="10" />
				 </td>	
				</logic:equal>		
			</tr>	
			<tr> 
			  	  <logic:notEqual name="remarks" value="">
					<td><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>
					<td><html:text property="remarks" styleClass="text" styleId="remarks" value="${edit[0].remarks}"  readonly="true" maxlength="100"  /></td>
			</logic:notEqual>
				  <logic:equal name="additionDate" value=""> 
			 		<td><bean:message key="lbl.remarks"/><span><font color="red">*</font></span></td>
					<td><html:text property="remarks" styleClass="text" styleId="remarks" maxlength="100"  /></td>
				</logic:equal>		
			</tr>
			<logic:present name="edit">
			<logic:equal name="remarks" value="">
			<tr> 
				<td align="left" colspan="2" class="form2">
				<button type="button" name="save" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return saveStationaryAcknowHo();">
				<bean:message key="button.save" /></button>					
				</td>
			</tr>
			</logic:equal>
			</logic:present>
		</table>
	    </fieldset>
	    <br/>
		</html:form>
<logic:present name="saveData">
<script type="text/javascript">

   if('<%=request.getAttribute("saveData").toString()%>'=='saveData')
	{
		alert('Data Saved Succesfully');
		document.getElementById("StationaryMasterForm").action="hoStationaryAcknowledgmentAtCM.do";
	   	document.getElementById("StationaryMasterForm").submit();
	}
</script>
</logic:present>
</body>
</html>