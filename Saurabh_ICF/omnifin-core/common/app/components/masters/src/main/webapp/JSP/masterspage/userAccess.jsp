<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<%@include file="/JSP/commonIncludeContent.jsp" %> 

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
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/omniFinReportScript.js"></script>
		
			
		<script type="text/javascript">
		
		$(function() {
				$("#BusinessDate").datepicker({
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
					defaultDate:'${userobject.businessdate }',
					maxDate : '${userobject.businessdate }'
					});
			});
		
		</script> 		
	
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
				<html:form action="/userAccessLogAction" styleId="userAccessLog" >
					<fieldset>
						<legend><bean:message key="lbl.ReportParameterForm"/></legend>  
						<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr>
      					<td ><bean:message key="lbl.userNam"/></td>
      					<td >
      						<html:text property="userId" styleClass="text" styleId="userId" maxlength="100" readonly="true"/>
							<html:hidden property="lbxUserId" styleId="lbxUserId" />
							<html:button property="UserButton" styleId="UserButton" tabindex="1" onclick="openLOVCommon(278,'userAccessLog','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
						</td>
						
						 <td id="ind" ><bean:message key="lbl.BusinessDate"/><span><font color="red">*</font></span></td>
	       				 <td> 
	       				 <div>
					 	 <html:text styleClass="text" property="BusinessDate" styleId="BusinessDate"  maxlength="10"   onchange="checkDate('BusinessDate');"  />
					 	 </div> 
							</td>
							</tr>
						<tr>
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
						<td ><button type="button" name=" mybutton" title="Alt+G" accesskey="G"   class="topformbutton3" onclick="userAccessReport();"  ><bean:message key="button.generate" /></button></td>
						</tr>
					
						</table>
						</fieldset>
				</html:form>
			</div>
		</div>

	</body>
</html>