<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<%@include file="/JSP/commonIncludeContent.jsp" %> 

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
	
		<title>Reports</title>

		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/omniFinReportScript.js"></script>
			
	
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
	
	
	
	<body onload="enableAnchor();loadJSP()">
	<div id="centercolumn">	
			<div id="revisedcontainer">	
				<html:form action="/UserDetailsAction" styleId="userDetailsForm" >
					<fieldset>
						<legend><bean:message key="lbl.ReportParameterForm"/></legend>  
						<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr>
      					<td ><bean:message key="lbl.userNam"/></td>
      					<td >
      						<html:text property="userId" styleClass="text" styleId="userId" maxlength="100" readonly="true"/>
							<html:hidden property="lbxUserId" styleId="lbxUserId" />
							<html:button property="UserButton" styleId="UserButton" tabindex="1" onclick="openLOVCommon(278,'userDetailsForm','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
						</td>
						<td id="rf"><bean:message key="lbl.ReportFormat"/></td>
								<td id="rfv">
								<html:select property="reportformat" styleClass="text">
								<html:option value="P">PDF</html:option>
								<html:option value="E">EXCEL</html:option>
								<html:option value="H">HTML</html:option>
								</html:select> 
          	   			</td>
						</tr>
						<tr>
						<td ><button type="button"  title="Alt+G" accesskey="G" name=" mybutton" class="topformbutton3" onclick="userDetailsReport();"  ><bean:message key="button.generate" /></button></td>
						</tr>
					
						</table>
						</fieldset>
				</html:form>
			</div>
		</div>

	</body>
</html>