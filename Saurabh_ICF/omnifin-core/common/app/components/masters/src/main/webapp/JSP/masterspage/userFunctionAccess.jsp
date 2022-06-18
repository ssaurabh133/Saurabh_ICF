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
				<html:form action="/UserFunctionAccessAction" styleId="userFunctionAccess" >
					<fieldset>
						<legend><bean:message key="lbl.ReportParameterForm"/></legend>  
						<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr>
      					<td width="13%"><bean:message key="lbl.userNam"/><span><font color="red">*</font></span></td>
      					<td width="13%">
      						<html:text property="userId" styleClass="text" styleId="userId" maxlength="100" readonly="true"/>
							<html:hidden property="lbxUserId" styleId="lbxUserId" />
							<html:button property="UserButton" styleId="UserButton" tabindex="1" onclick="openLOVCommon(278,'userFunctionAccess','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
						</td>
						<td width="20%">Module Description</td>
            			<td width="20%">
               		<html:text property="moduleDesc" styleId="moduleDesc" styleClass="text" readonly="true" tabindex="-1" />
          	   		<html:hidden  property="lbxModuleId" styleId="lbxModuleId"  />
          	   		<html:button property="ModuleButton" styleId="ModuleButton" onclick="openLOVCommon(280,'userFunctionAccess','moduleDesc','userId','lbxModuleId', 'lbxUserId','Please Select UserID First ','','lbxModuleId')" value=" " styleClass="lovbutton"> </html:button>
          	   		</td>
          	   		</tr>
						<tr>
						<td width="20%">Role Description</td>
						
            			<td width="20%">
               		<html:text property="roleDescription" styleId="roleDescription" styleClass="text" readonly="true" tabindex="-1}" value="${requestScope.userList[0].userId}"/>
          	   		<html:hidden  property="lbxRoleId" styleId="lbxRoleId" value="${requestScope.userList[0].lbxRoleId}" />
          	   		<html:button property="RoleButton" styleId="RoleButton" onclick="openLOVCommon(279,'userFunctionAccess','roleDescription','moduleDesc','lbxRoleId', 'lbxModuleId','Please Select Module Description First ','','lbxRoleId')" value=" " styleClass="lovbutton"> </html:button>
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
					<td ><button type="button" name=" mybutton"  title="Alt+G" accesskey="G"  class="topformbutton3" onclick="userFunctionAccessReport();" ><bean:message key="button.generate" /></button></td>
					</tr>
					
						</table>
							</fieldset>
				</html:form>
			</div>
		</div>

	</body>
</html>