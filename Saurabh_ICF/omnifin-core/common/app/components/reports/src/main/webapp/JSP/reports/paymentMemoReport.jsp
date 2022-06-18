<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<title>Reports</title>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/report/report.js"></script>
		
		
		
		
	
		<style type="text/css">
		
			.white {
			background:repeat-x scroll left bottom #FFFFFF;
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
			}
			.white2 {
			background:repeat-x scroll left bottom #CDD6DD;
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
			}
		</style>
	</head>
	
	
	
<body onload="enableAnchor();" >
	<html:form action="/paymentMemoGenerate" styleId="paymentMemoReport" >
		<fieldset>
			<legend><bean:message key="lbl.ReportParameterForm"/></legend>  
				<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />		
					<table width="100%" border="0" cellspacing="1" cellpadding="1">
					<tr>
							<td><bean:message key="lbl.loanNumber"/><font color="red">*</font></td>
							<td><html:text property="loanNumber" styleClass="text" value="" styleId="loanNumber" readonly="false"
							onchange="openLOVCommon(299,'paymentMemoReport','loanNumber','','', '','','','customerName','','','Y')" 
							/>
										<html:hidden property="lbx_loan_from_id" styleId="lbx_loan_from_id" value="" />
										<html:hidden property="instrumentId" styleId="instrumentId" value="" />
										<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
										<html:button property="loanFromButton" styleId="loanFromButton" tabindex="1" onclick="openLOVCommon(299,'paymentMemoReport','loanNumber','','', '','','','customerName','instrumentId')" value=" " styleClass="lovbutton"  />
							</td>
							<td><bean:message key="lbl.customer_name"/></td>
							<td>
									<html:text property="customerName" styleClass="text" styleId="customerName" maxlength="100" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td ><html:button property=" mybutton"  value="Generate"  styleClass="topformbutton2" onclick="return paymentMemoReport();"  /></td>
						</tr>	
					</table>
		</fieldset>
	</html:form>
</body>
</html>