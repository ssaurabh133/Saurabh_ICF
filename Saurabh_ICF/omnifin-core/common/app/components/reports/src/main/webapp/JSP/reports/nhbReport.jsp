<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"	content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
        </logic:present>
    	<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    	</logic:notPresent>
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<title>Reports</title>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/report/report.js"></script>
		<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>

		<script type="text/javascript">
		
			$(function() 
		    {
				$("#toDate").datepicker
				({
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
			$(function() 
		    {
				$("#asonDate").datepicker
				({
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
	</head>
	<body onload="enableAnchor();">
		<html:form action="/nhbReportAction" styleId="nhbReportId">
			<input type="hidden" id="businessdate" value="${userobject.businessdate }" />
			<input type="hidden" id="eodList" value="${eodList}"/>
			<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
			<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
			<input type="hidden" id="maxToDate" value="${maxDate}"/>
			<fieldset><legend><bean:message key="lbl.NHBReportParameterForm" /></legend>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
				<td width="13%"><bean:message key="lbl.ReportType" /><span><font color="red">*</font></span></td>
				<td width="13%"><html:select property="reportId" styleId="reportId"	onchange="arrangeView()" styleClass="text">
										<option value="">--<bean:message key="lbl.select" />--</option>
										<option value="NHB_Monthly_Report">NHB Monthly Report</option>
										<option value="NHB_Report_4_1">NHB Report Saction 4.1(A,B,C)</option>
										<option value="NHB_Report_4_1D">NHB Report Saction 4.1(D)</option>
										<option value="NHB_Report_4_2">NHB Report Saction 4.2</option>
										<option value="NHB_Report_4_3">NHB Report Saction 4.3</option>
										<option value="NHB_Report_4_4">NHB Report Saction 4.4</option>
										<option value="NHB_Report_4_5">NHB Report Saction 4.5</option>
										
								</html:select>
				</td>
				<td width="13"><bean:message key="lbl.ReportFormat" /><span><font color="red">*</font></span></td>
				<td width="13%"><html:select property="reportformat" styleClass="text"	styleId="reportformat" value="E">
										 <logic:present name="list">
											<logic:notEmpty name="list">
												<html:optionsCollection name="list" label="reportformatid"	value="reportformat" />
											</logic:notEmpty>
										</logic:present>
										</html:select>
				</td>
			</tr>
			<tr id="dateRange">
				<td width="13%"><bean:message key="lbl.FromDate" /><span><font color="red">*</font></span></td>
				<td width="13%">
						<html:select property="fromDate" styleId="fromDate" styleClass="text" onchange="calculateMaxDate(this.value,'toDate')">
						<logic:present name="financeYear">
						<logic:notEmpty name="financeYear">
						<html:optionsCollection name="financeYear" label="financeYear" value="financeYear" />
						</logic:notEmpty>
						</logic:present>
						</html:select>
				</td>
				<td width="13%"><bean:message key="lbl.ToDate" /><span><font color="red">*</font></span></td>
				<td width="13%"><html:text property="toDate" styleId="toDate" styleClass="text" value="${maxDate}" maxlength="10"	onchange="checkToDate(this.value,'toDate');" /></td>
			</tr>
			<tr id="asOnDateRow">
				<td width="13%"><bean:message key="lbl.AsOnDate" /><span><font color="red">*</font></span></td>
				<td width="13%"><html:text property="asonDate" styleId="asonDate"	styleClass="text" value="${userobject.businessdate}" /></td>
				<td width="13%"></td>
				<td width="13%"></td>
			</tr>
			<tr id="schemeRow">				
				<td width="13%"><bean:message key="scheme.code" /><span><font color="red">*</font></span></td>
				<td width="13%">
						<html:select property="scheme" styleId="scheme" styleClass="text">
						<logic:present name="scheme">
						<logic:notEmpty name="scheme">
						<html:optionsCollection name="scheme" label="schemeDesc" value="schemeID" />
						</logic:notEmpty>
						</logic:present>
						</html:select>
				</td>
				<td width="13%"></td>
				<td width="13%"></td>
			</tr>
			<tr><td><button type="button" name=" mybutton" title="Alt+G" accesskey="G" class="topformbutton3" onclick="generateNHBReport();"><bean:message key="button.generate" /></button></td>
			</tr>
			</table>
			</fieldset>
		</html:form>
</html>