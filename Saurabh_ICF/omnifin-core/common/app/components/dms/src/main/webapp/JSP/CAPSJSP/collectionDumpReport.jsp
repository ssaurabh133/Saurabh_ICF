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
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/unallocatedreport.js"></script>
		 <script type="text/javascript" 	src="<%=request.getContextPath()%>/js/report/report.js"></script>
		 
		 <script type="text/javascript">
		
			$(function() {
				$("#fromDate").datepicker({
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
	
	
			
					
			$(function() {
				$("#toDate").datepicker({
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
	<body onload="enableAnchor();" >
		<div >	
			<html:form action="/collectionDumpReport" styleId="collectionDumpReportForm"  >
					<fieldset>
						<legend><bean:message key="lbl.ReportParameterForm"/></legend> 
						<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
						<html:hidden property="reportId" styleId="contextPath" value="Dept_mgt_report" /> 
						<input type="hidden" name="dateRengeLimit" id="dateRengeLimit" value= "${dateRengeLimit}">
						<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>"  />	
									
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr>
									<td id="rf"><bean:message key="lbl.ReportFormat"/><span><font color="red">*</font></span></td>
								<td id="rfv"> <html:select  property="reportformat" styleClass="text"  styleId="reportformat"  value="E">
                                 <html:option value="E">EXCEL</html:option>
            					
                	            </html:select>
      					</td>		
								
								
						  </tr>
						  
						<tr>
						 <td ><bean:message key="lbl.FromDate"/><font color="red">*</font></td>
							     <td>
								 <html:text property="fromDate"  styleId="fromDate" styleClass="text" value="" maxlength="10" onchange="checkDate('fromDate');"/>
								</td>
									
							<td><bean:message key="lbl.ToDate"/><font color="red">*</font></td>
						    <td>
							<html:text property="toDate"  styleId="toDate" styleClass="text" value="" maxlength="10" onchange="checkDate('toDate');"/>
							</td>
						</tr>
						
						<tr>
								<td>
								<!-- 	<td ><html:button property=" mybutton"  value="Show"  styleClass="topformbutton2" onclick="collectionDumpReportValidation();"  /> -->
								<button type="button" class="topformbutton2" name="mybutton"  onclick="collectionDumpReportValidation();" 
 								title="Alt+W" accesskey="W"  tabindex="-1" ><bean:message key="button.show" /></button>
								</td>
						</tr>
						
					</table>
					</fieldset>
				</html:form>
			</div>
			<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
	</body>
</html>