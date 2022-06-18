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
		
		
		
		<script type="text/javascript">
	
	
			
				function changeval(val)
				{
				
					if(val=="R")
					{
						document.getElementById("replaceRecievable").style.display="none";
						document.getElementById("replacePayable").style.display="";
						
					}
					if(val=="P")
					{
						document.getElementById("replacePayable").style.display="none";
						document.getElementById("replaceRecievable").style.display="";
						
					}
					
				}	
				
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
				<html:form action="/commonActionForReport" styleId="chequeStatusForm" >
					<fieldset>
						<legend><bean:message key="lbl.ReportParameterForm"/></legend>  
						<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
						<html:hidden property="reportId" styleId="contextPath" value="cheque_Status" />
						<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
							
								<tr>
									<td><bean:message key="lbl.loanNumber"/></td>
									<td><html:text property="loanNumber" styleClass="text" value="" styleId="loanNumber" readonly="true"/>
										<html:hidden property="lbx_loan_from_id" styleId="lbx_loan_from_id" value="" />
										<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
										<html:button property="loanFromButton" styleId="loanFromButton" tabindex="1" onclick="openLOVCommon(341,'chequeStatusForm','loanNumber','','', '','','','customerName')" value=" " styleClass="lovbutton"  />
									</td>
									<td><bean:message key="lbl.customer_name"/></td>
									<td>
										<html:text property="customerName" styleClass="text" styleId="customerName" maxlength="100" readonly="true"/>
									</td>
								</tr>
								<tr>
									<td><bean:message key="lbl.instrumenttype"/></td>
									<td>
										<html:select property="instrumentType" styleId="instrumentType"   onchange="changeval(this.value);" styleClass="text" >
											
											<html:option value="R">Recievable</html:option>
											<html:option value="P">Payable</html:option>
										</html:select>
									</td>
									<td><bean:message key="lbl.statusType"/></td>
									
									<td>
										<div id="replaceRecievable" style="display:none">
									  		<html:select property="statusTypeR" styleId="statusTypeR"  styleClass="text" >
											<html:option value="All">All</html:option>
										<html:option value="C">Sent To Customer</html:option>
										<html:option value="S">Stopped</html:option>
										<html:option value="R">Realized</html:option>
										<html:option value="X">Cancelled</html:option>
											</html:select>
										
										</div>
										
										<div id="replacePayable"  >
										<html:select property="statusTypeP" styleId="statusTypeP" styleClass="text">
										<html:option value="All">All</html:option>
											<html:option value="D">Deposit</html:option>
											<html:option value="B">Bounced</html:option>
											<html:option value="R">Realized</html:option>
											<html:option value="X">Cancelled</html:option>
										
										</html:select>
										</div>
										
									</td>
									</tr>
										<tr>
											<td ><bean:message key="lbl.FromDate"/><span><font color="red">*</font></span></td>
										<td>
<!--										<html:text property="fromDate" styleId="fromDate" styleClass="text" value=""  /> -->
										<html:text property="fromDate"  styleId="fromDate" styleClass="text" value="" maxlength="10" onchange="checkDate('fromDate');validateLeadDate();"/>
										</td>										
										<td><bean:message key="lbl.ToDate"/><span><font color="red">*</font></span></td>
										<td>
<!--										<html:text property="toDate" styleId="toDate" styleClass="text" value=""  /> -->
										<html:text property="toDate"  styleId="toDate" styleClass="text" value="" maxlength="10" onchange="checkDate('toDate');validateLeadDate();"/>
										</td>
										</tr>
										<tr>
								<td id="rf"><bean:message key="lbl.ReportFormat"/><span><font color="red">*</font></span></td>
								<td id="rfv"> <html:select  property="reportformat" styleClass="text"  styleId="reportformat" >
            			   	  <logic:present name="list">
   							  <logic:notEmpty  name="list">
      									<html:optionsCollection name="list" label="reportformatid" value="reportformat" />
     						</logic:notEmpty>
      					 	</logic:present>
                	 		</html:select>
      					</td>					
									</tr>
		<!-- 							changes by amit -->
									
								 <tr id="productCat">
						<td>
									<bean:message key="lbl.productCategory" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:select property="producTCategory" styleId="producTCategory" 
										onchange="disableCal(reportName.value)" styleClass="text" value="select">
										<option value="All">
											--
											All
											--
										</option>
										<logic:present name="productlist">
											<logic:notEmpty name="productlist">
												<html:optionsCollection name="productlist"
													label="producTCategoryID" value="producTCategory" />
											</logic:notEmpty>
										</logic:present>
									</html:select>
								</td>
								<td>
									<bean:message key="lbl.branch" />
									<html:hidden property="contextPath" styleId="contextPath"
										value="<%=request.getContextPath() %>" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:select property="branch" styleClass="text"
										onchange="hideLovofBranch()" styleId="branch">
										<html:option value="Specific">Specific</html:option>
										<html:option value="All">All</html:option>
										</html:select>
								</td>
								</tr>
								<tr id ="branchS">
								<td >
									<bean:message key="lbl.branchname" />
									<font color="red">*</font>
								</td>
								<td>
								<select id="branchid"  name="branchid" size="5" multiple="multiple"  value="" style="width: 150px"></select>
									<html:hidden property="lbxBranchId" styleId="lbxBranchId"
										value="" />
									<html:button property="dealButton" styleId="dealButton"
										tabindex="1"
										onclick="return openMultiSelectLOVCommon(2075,'reportid','branchid','','', '','','','lbxBranchId');"
										value=" " styleClass="lovbutton" >
									</html:button>
								</td>
								</tr>	
								
								 <tr id="loanCAl">
						<td>
									<bean:message key="lbl.loanClassification" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:select property="loanClassification" styleId="loanClassification" 
										onchange="disableCal(reportName.value)" styleClass="text" value="select">
										<option value="All">
											--
											All
											--
										</option>
										<logic:present name="loanClasslist">
											<logic:notEmpty name="loanClasslist">
												<html:optionsCollection name="loanClasslist"
													label="producTCategoryID" value="producTCategory" />
											</logic:notEmpty>
										</logic:present>
									</html:select>
								</td>
							</tr>	
	
		<!-- 							changes ends here -->							
									
									<tr>
										<td ><html:button property=" mybutton"  value="Generate"  styleClass="topformbutton2" onclick="chequeStatusReport();"  /></td>
									</tr>	
							</table>
						</fieldset>
				</html:form>
			</div>
		</div>

	</body>
</html>