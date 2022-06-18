<!--Author Name : Neeraj Kumar Tripathi-->
<!--Date of Creation : 13-July-2012  -->
<!--Documentation : -->
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"	content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<title>Reports</title>
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>	
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/report/report.js"></script>
	</head>
	<script type="text/javascript">	
		function summaryReporta()
{
   	summaryReport();
	}	
			$(function() {
				$("#fromDate").datepicker({
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
					});
			});
		</script>
<body onload="enableAnchor();" >
	<html:form action="/summaryReportAction" styleId="summaryReport" >
	<fieldset><legend><bean:message key="lbl.ReportParameterForm"/></legend>  
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />	
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="source" id="source" value="${sourceReport}" />	
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<logic:equal name="sourceReport" value="CM">
		<td><bean:message key="lbl.Dashboard"/><span><font color="red">*</font></span></td>
		<td>
			<html:select property="dashboard" styleClass="text" styleId="dashboard" value="${report}">
				<html:option value="P">Pending Activities</html:option>
				<html:option value="T">Transactions Pending for Approval</html:option>
			</html:select>
		</td>
		</logic:equal>		
	    <td><bean:message key="lbl.ReportFormat"/><span><font color="red">*</font></span></td>
		<td><html:select  property="reportformat" styleClass="text"  styleId="reportformat" value="H" >
			<logic:present name="list">
   		    <logic:notEmpty  name="list">
      		<html:optionsCollection name="list" label="reportformatid" value="reportformat" />
     		</logic:notEmpty>
      		</logic:present>
            </html:select>
         </td>
         <logic:equal name="sourceReport" value="CP">
			<td></td><td></td>
		 </logic:equal>
         
    </tr>
    <tr>
    	<td><bean:message key="lbl.FromDate"/><span><font color="red">*</font></span></td>
    	<td><html:text property="fromDate" styleId="fromDate" styleClass="text" value="${stDate}" maxlength="10" onchange="checkDate('fromDate')" /></td>
    	<td><bean:message key="lbl.ToDate" /><span><font color="red">*</font></span></td>
		<td><html:text property="toDate" styleId="toDate" styleClass="text" value="${userobject.businessdate }" maxlength="10" readonly="true" /></td>
	</tr>
	<tr>
	<td ><button type="button" name=" mybutton"  title="Alt+G" accesskey="G" class="topformbutton3" onclick="summaryReporta()"  ><bean:message key="button.generate" /></button>
	</tr>	
	</table>
	</fieldset>
	</html:form>
</body>
</html>