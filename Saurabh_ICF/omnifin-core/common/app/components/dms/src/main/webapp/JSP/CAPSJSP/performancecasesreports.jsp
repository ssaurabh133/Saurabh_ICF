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
		
	<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
			<script type="text/javascript"
			src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/capsScript/performancereport.js"></script>

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
		<!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
	<! [endif] -->
	</head>
	
	
	
	<body onload="enableAnchor();loadJSP()">
		<div id="centercolumn">	
			<div id="revisedcontainer">	
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
		<html:form action="/performanceReports" styleId="performanceForm" >
					<fieldset>
						<legend><bean:message key="lbl.userPerformance" /></legend>
							<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
				  <td><bean:message key="lbl.reportformat" /></td>
									<td>
										<html:select property="reportformat" styleClass="text">
											<html:option value="P">PDF</html:option>
											<html:option value="E">EXCEL</html:option>
											<html:option value="H">HTML</html:option>
						
										</html:select> 
									</td>			
						</tr>	
    <tr>	
    <td >User Id</td>
      					<td >
      						<html:text property="userId" styleClass="text" styleId="userId" maxlength="100" readonly="true"/>
							<html:hidden property="lbxUserId" styleId="lbxUserId" />
							<html:button property="UserButton" styleId="UserButton" tabindex="1" onclick="openLOVCommon(278,'performanceForm','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
						</td>
				<td ><bean:message key="lbl.queue" /></td>
     		<td><html:text property="queue" styleClass="text"  styleId="queue" maxlength="30"  readonly="true"  />
      	<html:button property="queueButton" styleId="queueButton" onclick="openLOVCommon(189,'performanceForm','queue','','', '','','','lbxQueuesearchId')" value=" " styleClass="lovbutton"></html:button>
       	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
      	 <html:hidden  property="lbxQueuesearchId" styleId="lbxQueuesearchId" />
    	 </td>		
			 </tr>
			<tr>
 			    <td ><bean:message key="lbl.dateto" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
       			<td>
     			   <html:text property="todate" tabindex="8"  styleClass="text"  styleId="todate"  onchange="return checkDate('assignto');"/>
      			 </td>
          		<td ><bean:message key="lbl.datefrom" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
     	 		 <td>
      			 <html:text property="fromdate" tabindex="8"  styleClass="text"  styleId="fromdate"  onchange="return checkDate('assignfrom');"/>
        		</td>  
     		</tr>
    
    <tr>
		<td >
		<!--<html:button property=" mybutton" styleId="show" value="Show"  styleClass="topformbutton2" onclick="checkValidation();"  />
			-->
			
<button type="button" class="topformbutton2" name="mybutton"  onclick="checkValidation();" 
 							id="show" title="Alt+W" accesskey="W"  ><bean:message key="button.show" /></button>
			</td>
			</tr>
							
			</table>
			</fieldset>
			</html:form>
			</div>
		</div>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
	</body>
</html>