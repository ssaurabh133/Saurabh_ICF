<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		
		<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
         </logic:present>
    	<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		
    	<title>Reports</title>
		 <!-- css and jquery for Datepicker -->	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-latest.min.js"></script>
	    <script> $113 = jQuery.noConflict();</script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	   	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<link rel="stylesheet" href="js/fancyBox/source/jquery.fancybox.css" type="text/css" media="screen" />
		<script type="text/javascript" src="js/fancyBox/source/jquery.fancybox.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/report/report.js"></script>
		<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

		<script type="text/javascript">
		
		    $(function() {
				$("#intCerToDate").datepicker({
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
					
			$(function() {
				$("#toDate").datepicker({
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
			$(function() {
				$("#dateFrom").datepicker({
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
					
			$(function() {
				$("#dateTo").datepicker({
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
			
			$(function() {
				$("#asonDate").datepicker({
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
			$(function() {
				$("#installmentDate").datepicker({
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
			
			$(function() {
				$("#selectDate").datepicker({
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
			
				$(function() {
				$("#presentationdate").datepicker({
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
			$(function() {
				$("#approvalDateFrom").datepicker({
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
			
			$(function() {
				$("#approvalDateTo").datepicker({
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
				
						$(function() {
				$("#fromdateforsd").datepicker({
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
					
			$(function() {
				$("#todateforsd").datepicker({
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
			$(function() {
				$("#sponsorDate").datepicker({
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
			$(function() {
				$("#balConfToDate").datepicker({
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
            $(function() {
				$("#depositDate").datepicker({
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
			$(function() {
				$("#receiptDate").datepicker({
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



		<style type="text/css">
.white {
	background: repeat-x scroll left bottom #FFFFFF;
	filter: progid : DXImageTransform.Microsoft.AlphaImageLoader ( src =
		'images/grey-up.png', sizingMethod = 'scale' );
}

.white2 {
	background: repeat-x scroll left bottom #CDD6DD;
	filter: progid : DXImageTransform.Microsoft.AlphaImageLoader ( src =
		'images/grey-up.png', sizingMethod = 'scale' );
}
</style>

</head>



	<body onload="enableAnchor();generateReport()">
	<input type="hidden" name="dateRengeLimit" id="dateRengeLimit" value= "${dateRengeLimit}">
	<input type="hidden" name="DateRangeLimitSpecial" id="DateRangeLimitSpecial" value= "${DateRangeLimitSpecial}">
	<input type="hidden" name="generateReport" id="generateReport" value= "${generateReport}">
	<input type="hidden" name="defReportName" id="defReportName" value= "${reportName}">	
	<input type="hidden" name="defReporttype" id="defReporttype" value= "${reporttype}">	
		
    <input type="hidden" name="<%=org.apache.struts.taglib.html.Constants.TOKEN_KEY %>" value="<%=session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>" >
		<div id="centercolumn">
			<div id="revisedcontainer">
				<html:form action="/AllReports" styleId="reportid">
				<input type="hidden" id="businessdate" value="${userobject.businessdate }" />
				<input type="hidden" id="eodList" name="eodList" value="${eodList}" />
				<input type="hidden" id="rvFlag" name="rvFlag" value="" />
					<fieldset>
						<legend>
							<bean:message key="lbl.ReportParameterForm" />
						</legend>
						<html:hidden property="contextPath" styleId="contextPath"
							value="<%=request.getContextPath() %>" />
						<html:hidden property="reportId" styleId="contextPath" />
						<input type="hidden" id="recStatus" value="" />
						<input type="hidden" name="formatD" id="formatD"
							value="<bean:message key="lbl.dateFormat"/>" />
						<input type="hidden" id="maxToDate" value="${maxDate}"/>
						<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr>
								<td>
									<bean:message key="lbl.ReportType" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:select property="reportName" styleId="reportName" 
										onchange="disableCal(this.value)" styleClass="text" value="select">
										<option value="select">
											--
											<bean:message key="lbl.select" />
											--
										</option>
										<logic:present name="reportlist">
											<logic:notEmpty name="reportlist">
												<html:optionsCollection name="reportlist"
													label="reportNameId" value="reportName" />
											</logic:notEmpty>
										</logic:present>
									</html:select>
								</td>

								<td id="rf">
									<bean:message key="lbl.ReportFormat" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="rfv">
									<html:select property="reportformat" styleClass="text"
										styleId="reportformat">
										<!--  		<html:option value="" >--Select--</html:option>-->
										<logic:present name="list">
											<logic:notEmpty name="list">
												<html:optionsCollection name="list" label="reportformatid"
													value="reportformat" />
											</logic:notEmpty>
										</logic:present>
									</html:select>
								</td>
								<td  id="sgk" style="display: none;">
										&emsp;&emsp;&emsp;&emsp;
								</td>
								<td  id="sgk1" style="display: none;">
										&emsp;&emsp;
								</td>
							</tr>
							<tr>
								<td id="statusL"><bean:message key="lbl.active" /></td>
							    <td id="statusV"><html:select styleClass="text" property="bucket" styleId="bucket" onchange="mngAppLevel()">
							    		<html:option value="A">Login</html:option>
							    		<html:option value="B">Opening Balance</html:option>
							    		<html:option value="C">Approved</html:option>
							    		<html:option value="D">In Process</html:option>
							    		<html:option value="E">Cancelled</html:option>
							    		<html:option value="F">Rejected</html:option>
							    		<html:option value="G">Disbursement</html:option>
							    		<html:option value="H">Dropped</html:option>
							    		<html:option value="I">Gap Amount</html:option>		
							    		<html:option value="J">Daily Login(For the Day)</html:option>	
							    		<html:option value="K">Daily Login(MTD)</html:option>						    		
							    	</html:select>
							    </td>	
								<td id="approvalL"><bean:message key="lbl.PCDAppLevel" /></td>
							    <td id="approvalV"><html:select styleClass="text" property="approvalLevel" styleId="approvalLevel" >
							    		<html:option value="1">1</html:option>
							    		<html:option value="2">2</html:option>
							    		<html:option value="3">3</html:option>
							    	</html:select>
							    </td>	
							</tr>
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
								
								
		<%--  Kumar Aman Changes Started --%>
		
						<tr id="trreporttype11" style="display: none">								
								<td>
								<bean:message key="lbl.reporttype11"/>
								</td>
								<td>
									<html:select property="reporttype11" styleId="reporttype11" styleClass="text">
										<html:option value="" >--Select--</html:option>
										<html:option value="R">Presentation Purpose</html:option>
										<html:option value="A">Accrual Purpose</html:option>
									</html:select>
								</td>
								
							</tr>		
		
							<tr id="trreporttype12" style="display: none">								
								<td>
								<bean:message key="lbl.reporttype12"/>
								</td>
								<td>
									<html:select property="reporttype12" styleId="reporttype12" styleClass="text">
										<html:option value="" >--Select--</html:option>
										<html:option value="all">All Cases</html:option>
										<html:option value="E">Expired Cases</html:option>
										<html:option value="N">Non Starter Cases</html:option>
										<html:option value="I">Early Deliquency Cases</html:option>
									</html:select>
								</td>
								
							</tr>
							<tr id="branch3">
								<td>
									<bean:message key="lbl.branch" />
									<html:hidden property="contextPath" styleId="contextPath"
										value="<%=request.getContextPath() %>" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:select property="branch4" styleClass="text"
										onchange="hideLovofBranch3()" styleId="branch4">
										<html:option value="Specific">Specific</html:option>
										<html:option value="All">All</html:option>
										</html:select>
								</td>
							</tr>
							
							
<%-- Kumar Aman Changes Started for Early Maturity AFC Report --%>

							<tr id="trreporttype13" style="display: none">								
								<td>
								<bean:message key="lbl.reporttype13"/>
								</td>
								<td>
									<html:select property="reporttype13" styleId="reporttype13" styleClass="text">
										<html:option value="" >--Select--</html:option>
										<html:option value="CA">Complete AFC Statement</html:option>
										<html:option value="WC">Working Capital Interest Calculation</html:option>
									</html:select>
								</td>
								
							</tr>

<%-- Kumar Aman Changes Ended for Early Maturity AFC Report  --%>							
							
							
							
							
							
			<%-- Kumar Aman Changes Ended --%>		
			
								
			<%--  Kumar Aman Changes Started for OD Report--%>
									
						<tr id="month" style="display: none">
								<td>
									<bean:message key="lbl.month" />
									<span><font color="red">*</font>
									</span>
								</td>
								
								<td>
									<html:text styleClass="text" property="startDate1"
										styleId="startDate1" maxlength="20" value=""		readonly="false" tabindex="-1" />
									<html:hidden property="startPeriod" styleId="startPeriod" value="" />
									<input type="hidden" name="startPeriod" id="startPeriod" />
									<html:button property="startDatelov"
										styleId="startDatelov"	onclick="openMonthLov()" value=" " styleClass="lovbutton"></html:button>
								</td>
								
<!-- 							<tr id="reporttype" style="display: none">	 -->
								<td>
								<bean:message key="lbl.reporttype"/>
								</td>
								<td>
									<html:select property="reporttype" styleId="reporttype" styleClass="text">
										<html:option value="" >--Select--</html:option>
										<html:option value="BWBS">Branch Wise Branch Summary</html:option>
										<html:option value="BWES">Branch Wise Executive Summary</html:option>
										<html:option value="ORD">OD Report Detail</html:option>
									</html:select>
								</td>
							</tr>
										
			<%-- Kumar Aman Changes Ended  OD Report--%>						
						
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
							<tr>
								<td id="productL"><bean:message key="lbl.productDesc"/></td>
		      					<td id="productV"> 
		      						<select id="productDescs"  name="productDescs" size="5" multiple="multiple"  value="" style="width: 150px"></select>
              						<html:button property="consButton" styleId="consButton" onclick="return openMultiSelectLOVCommon(2025,'reportid','productDescs','','', '','','','productIds');" value=" " styleClass="lovbutton"></html:button>
              					    <html:hidden property="productIds" styleId="productIds" value=""/>	      
              					</td>
              					<%-- <td id="branchL"><bean:message key="lbl.branchname"/></td>
								<td id="branchV">
									<html:text property="loginBranchDesc" styleClass="text" styleId="loginBranchDesc" maxlength="100" readonly="true" />
									<html:hidden property="loginBranchId" styleClass="text" styleId="loginBranchId"/>
									<html:button property="branchLov" styleId="branchLov" tabindex="1" onclick="openLOVCommon(2027,'reportid','loginBranchDesc','','', '','','','loginBranchId')" value=" " styleClass="lovbutton" />
								</td> --%>
							</tr>
							
							
							
			
							
							
							
							<%-- Manish Baranwal Code Start Here  for Drawing Power Statement --%>
						 	<tr id="drawing_power_statement" >
	                         	<td id="b11" width="13%"><bean:message key="lbl.bankName" /><span><font color="red">*</font></span></td>
	                         	<td id="b21" width="13%">
	                         	      <html:text styleClass="text" property="bankName1" styleId="bankName1" maxlength="100"  readonly="true" value="" />
	    						      <html:hidden  property="lbxBankID" styleId="lbxBankID"/>
	    						      <html:button property="bButton" styleId="bButton" onclick="openLOVCommon(584,'reportid','bankName1','','bankBranch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>	
	    						</td>
	    						<td id="b12" width="13%"><bean:message key="lbl.bankBranchName" /><span><font color="red">*</font></span></td>
	    						<td id="b22" width="13%">
		 						      <html:text styleClass="text" property="bankBranch" styleId="bankBranch" maxlength="100"  readonly="true" value="" tabindex="-1" />
									  <input type="hidden" name="bankAccount" id="bankAccount" />
	     	                          <html:button property="bButton" styleId="bButton" onclick="openLOVCommon(585,'reportid','bankBranch','bankName1','lbxBranchID', 'lbxBankID','Select Bank LOV','','lbxBranchID','bankAccount')" value=" " styleClass="lovbutton"></html:button></div>
	     	                          <html:hidden  property="lbxBranchID" styleId="lbxBranchID" />
	     	                    </td>
     	                 	</tr> 
						<!-- Code end By Manish -->
							<tr>
								<td id="lh">
									<bean:message key="lbl.loanNumber" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="deal" style="display: none;">
									Deal Number
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="lv">
									<html:select property="loanno" styleClass="text"
										onchange="hideLovofLoan()" styleId="loanno">
										<html:option value="R">Range</html:option>
										<html:option value="A">All</html:option>
									</html:select>
								</td>
								<td id="st">
									<bean:message key="lbl.active" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="st1" style="display: none">
									<bean:message key="lbl.repaymentType" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td  id="soaFor3" style="display: none" >
									<bean:message key="lbl.soaFor" /><span><font color="red">*</font></span>
								</td>
								<td id="stDrop">
									<div id="df">
										<html:select property="status" styleClass="text"
											disabled="true">
											<html:option value="All">All</html:option>
											<html:option value="A">Approved</html:option>
											<html:option value="R">Rejected</html:option>
											<html:option value="P">Pending</html:option>
											<html:option value="F">Forwarded</html:option>
										</html:select>
									</div>
									<div id="s1" style="display: none">
										<html:select property="status1" styleClass="text">
											<html:option value="All">All</html:option>
											<html:option value="A">Authorized</html:option>
											<html:option value="X">Cancel</html:option>
											<html:option value="F">Forward</html:option>
											<html:option value="P">Pending</html:option>
											<html:option value="R">Realized</html:option>
											<html:option value="D">Deposited</html:option>
										</html:select>
									</div>
									<div id="s2" style="display: none">
										<html:select property="status2" styleClass="text">
											<html:option value="All">All</html:option>
											<html:option value="A">Approved</html:option>
											<html:option value="X">Cancelled</html:option>
											<html:option value="C">Closed</html:option>
											<html:option value="P">Pending</html:option>
										</html:select>
									</div>
									<div id="s3" style="display: none">
										<html:select property="status3" styleClass="text">
											<html:option value="All">All</html:option>
											<html:option value="A">Approved</html:option>
											<html:option value="R">Rejected</html:option>
											<html:option value="C">Cancelled</html:option>
											<html:option value="P">Pending</html:option>
										</html:select>
									</div>
									<div id="s4" style="display: none">
										<html:select property="status4" styleClass="text">
											<html:option value="All">All</html:option>
											<html:option value="A">Approved</html:option>
											<html:option value="X">Deleted</html:option>
											<html:option value="P">Pending</html:option>
										</html:select>
									</div>
									<div id="s5" style="display: none">
										<html:select property="status5" styleClass="text">
											<html:option value="All">All</html:option>
											<html:option value="I">Installment</html:option>
											<html:option value="N">Non-Installment</html:option>
										</html:select>
									</div>
									<div id="s6" style="display: none">
										<html:select property="status6" styleClass="text">
											<html:option value="All">All</html:option>
											<html:option value="A">Approved</html:option>
											<html:option value="X">Rejected</html:option>
											<html:option value="P">Pending</html:option>
											<html:option value="L">Cancelled</html:option>
											<html:option value="F">Forwarded</html:option>
											<html:option value="C">Closed</html:option>
										</html:select>
									</div>
									<div id="rs6" style="display: none">
										<html:select property="rstatus" styleId="rstatus"  styleClass="text">
											<html:option value="">----Select----</html:option>
											<html:option value="D">Disbursed</html:option>
											<html:option value="RS">Rescheduled</html:option>
										
										</html:select>
									</div>
									
								<div id="soaFor4" style="display: none" >
									<html:select property="soaFor" styleClass="text">
											<html:option value="ALL">All</html:option>
											<html:option value="CS">Customer</html:option>
									</html:select>
								</div>
								</td>
							</tr>
							<%--Rohit Changes Starts for Loan Master Register --%>
							<!--  <tr id="1"> -->
							 <tr id="LOAN_MASTER_Register" style="display: none">
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
								<td id="r10">
									<bean:message key="lbl.branchname" />
									<font color="red">*</font>
								</td>
								<td id="r11" style="display: none;">
									<bean:message key="lbl.branchname" />
								</td>
								<td id ="branchSingle">
									<html:text property="branchid" styleClass="text"
										styleId="branchid" maxlength="100" readonly="true" />
									<html:hidden property="lbxBranchId" styleId="lbxBranchId"
										value="" />
									<html:button property="dealButton" styleId="dealButton"
										tabindex="1"
										onclick="openLOVCommon(12,'reportid','branchid','','', '','','','lbxBranchId')"
										value=" " styleClass="lovbutton">
									</html:button>
								</td>
								<td id ="branchMultiple" style="display: none">
								<select id="branchidMultiple"  name="branchidMultiple" size="5" multiple="multiple"  value="" style="width: 150px"></select>
									<html:hidden property="lbxBranchIdMultiple" styleId="lbxBranchIdMultiple" name="lbxBranchIdMultiple"
										value="" />
									<html:button property="dealButton" styleId="dealButtonMultiple"
										tabindex="1" onclick="return openMultiSelectLOVCommon(2075,'reportid','branchidMultiple','','', '','','','lbxBranchIdMultiple');" value=" " styleClass="lovbutton" >
									</html:button>
								</td>
							</tr>
							<%-- Rohit Changes end for Loan Master Register--%>
							<tr id="3">

								<td id="r20">
									<bean:message key="lbl.LoanFrom" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="r21" style="display: none;">
									<bean:message key="lbl.LoanFrom" />
								</td>
								<td>
									<html:text property="from" styleClass="text" value=""
										styleId="from" readonly="false" />
									<html:hidden property="lbx_loan_from_id"
										styleId="lbx_loan_from_id" value="" />
									<input type="hidden" name="abc" id="abc" />
									<div id="loan_from_lov1" style="display: Inline">
										<html:button property="loanFromButton"
											styleId="loanFromButton" tabindex="1"
											onclick="openChequeBounceFromLov();" 
											value=" " styleClass="lovbutton"></html:button>
									</div>
									<div id=loan_from_lov2 style="display: none;">
										<html:button property="loanFromButton"
											styleId="loanFromButton" tabindex="1"
											onclick="openLOVCommon(341,'reportid','from','','', '','','','abc');"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									<div id=loan_from_lov3 style="display: none;">
										<html:button property="loanFromButton"
											styleId="loanFromButton" tabindex="1"
											onclick="openLOVCommon(468,'reportid','from','','', '','','','abc');"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									
							
								</td>
								<td id="r30">
									<bean:message key="lbl.LoanTo" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="r31" style="display: none;">
									<bean:message key="lbl.LoanTo" />
								</td>
								<td>
									<html:text property="to" styleClass="text" value=""	styleId="to" readonly="false" />
									<html:hidden property="lbx_loan_to_id" styleId="lbx_loan_to_id"	value="" />
									<input type="hidden" name="abc1" id="abc1" />
									<div id=loan_to_lov1 style="display: Inline">
										<html:button property="loanToButton" styleId="loanToButton"
											tabindex="1"
											onclick="openChequeBounceToLov();"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									<div id=loan_to_lov2 style="display: none">
										<html:button property="loanToButton" styleId="loanToButton"
											tabindex="1"
											onclick="openLOVCommon(342,'reportid','to','','', '','','','abc1');"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									<div id=loan_to_lov3 style="display: none">
										<html:button property="loanToButton" styleId="loanToButton"
											tabindex="1"
											onclick="openLOVCommon(469,'reportid','to','','', '','','','abc1');"
											value=" " styleClass="lovbutton"></html:button>
									</div>
								</td>
							</tr>
							<tr id="dealRange">
								<td id="d10">
									<bean:message key="lbl.dealFrom" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="d11" style="display: none;">
									<bean:message key="lbl.dealFrom" />
								</td>
								<td>
									<html:text styleClass="text" property="fromDeal"
										styleId="fromDeal" maxlength="20" value="" readonly="false"
										onchange="openLOVCommon(172,'reportid','fromDeal','','', '','','documentCollectionDisabledDealLOV','abc','','','Y')"
										tabindex="-1" />
									<input type="hidden" name="abc" id="abc" />
									<html:button property="dealNoFromSButton"
										styleId="dealNoFromSButton"
										onclick="openLOVCommon(172,'reportid','fromDeal','','', '','','documentCollectionDisabledDealLOV','abc')"
										value=" " styleClass="lovbutton"></html:button>
									<html:hidden property="lbxDealNo" styleId="lbxDealNo" value="" />
								</td>
								<td id="d20">
									<bean:message key="lbl.dealTo" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="d21" style="display: none;">
									<bean:message key="lbl.dealTo" />
								</td>
								<td>
									<html:text styleClass="text" property="toDeal" styleId="toDeal"
										maxlength="20" value="" readonly="false"
										onchange="openLOVCommon(241,'reportid','toDeal','','', '','','documentCollectionDisabledDealLOV','abc1','','','Y')"
										 tabindex="-1" />
									<input type="hidden" name="abc1" id="abc1" />
									<html:button property="dealNoToSButton"
										styleId="dealNoToSButton"
										onclick="openLOVCommon(241,'reportid','toDeal','','', '','','documentCollectionDisabledDealLOV','abc1')"
										value=" " styleClass="lovbutton"></html:button>
									<html:hidden property="lbxDealTo" styleId="lbxDealTo" value="" />
								</td>
							</tr>
							<tr id="6" style="display: none">
								<td id="p">
									<bean:message key="lbl.loanNumber" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="np" style="display: none">
									<bean:message key="lbl.loanNumber" />
								</td>
								<td id=p1>
									<html:text property="specificLoanNo" styleClass="text" value=""
										styleId="specificLoanNo" readonly="false" tabindex="-1" />
									<html:hidden property="lbx_loan_from_id"
										styleId="lbx_loan_from_id" value="" />
									<input type="hidden" name="abc" id="abc" />
									<div id=sp_loan1 style="display: Inline">
										<html:button property="specificLoanButton"
											styleId="specificLoanButton" tabindex="1"
											onclick="openLoanLov()"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									<div id=specific_loan style="display: none">
									<input type="hidden" id ="disbursalStatus" value="F">
										<html:button property="specificLoanButton"
											styleId="specificLoanButton" tabindex="1"
											onclick="callLOV();"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									<div id=sp_loan2 style="display: none">
										<html:button property="specificLoanButton"
											styleId="specificLoanButton" tabindex="1"
											onclick="openLoanPROVLov();"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									<div id=sp_loan3 style="display: none">
										<html:button property="specificLoanButton"
											styleId="specificLoanButton" tabindex="1"
											onclick="openLOVCommon(345,'reportid','specificLoanNo','','', '','','','abc');"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									<div id=sp_loan4 style="display: none">
										<html:button property="specificLoanButton"
											styleId="specificLoanButton" tabindex="1"
											onclick="openLOVCommon(353,'reportid','specificLoanNo','','', '','','','abc');"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									<div id=sp_loan5 style="display: none">
										<html:button property="specificLoanButton"
											styleId="specificLoanButton" tabindex="1"
											onclick="callForClosureLOV();"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									<div id=sp_loan6 style="display: none">
										<html:button property="specificLoanButton"
											styleId="specificLoanButton" tabindex="1"
											onclick="LOVForOD()"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									
									<div id=sp_loan7 style="display: none">
										<html:button property="specificLoanButton"
											styleId="specificLoanButton" tabindex="1"
											onclick="openLOVCommon(345,'reportid','specificLoanNo','','', '','','','abc');"
											value=" " styleClass="lovbutton"></html:button>
									</div>
									
								</td>
								<td id="IRR">
									<bean:message key="lbl.expectedIRR" />
									<!-- <span><font color="red">*</font>
									</span> -->
								</td>
								<td id=IRR1>
								<html:text property="expectedIRR" styleClass="text" value=""
										styleId="expectedIRR" readonly="false" tabindex="-1" /> 
								</td>
								<td id="soaFor1" style="display: none" >
									<bean:message key="lbl.soaFor" /><span><font color="red">*</font></span>
								</td>
								<td id="soaFor2" style="display: none" >
									<html:select property="soaFor" styleClass="text">
											<html:option value="ALL">All</html:option>
											<html:option value="CS">Customer</html:option>
									</html:select>
								</td>								
								<td id="ind" style="display: none">
									<bean:message key="lbl.InstallmentDate" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="ind2" style="display: none">
									Presentation Date
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="ind7" style="display: none">
									<bean:message key="lbl.cutOffDate" />
								</td>
								<td id="ind8" style="display: none">
									<input type="checkbox" id="status" name="status">
									<html:hidden property="cutOffFlag" styleId="cutOffFlag" value="" />
								</td>
								<td id="ind3" style="display: none">
									<bean:message key="lbl.date" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="ind4" style="display: none">
									Billing Date
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="ind5" style="display: none">
									As On Date 
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="ind6" style="display: none">
									Effective Date  
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="ind1">
								    <div id="incptId">
									<html:text styleClass="text" property="installmentDate" styleId="installmentDate" maxlength="10" value="" onchange="checkDate('installmentDate');" />
									</div>
									<html:text style="display:none" styleClass="text" readonly="true" property="cutOffDateForIncipient" styleId="cutOffDateForIncipient" maxlength="10" value="${cutOffDateForIncipient}"/>
								</td>
								<!-- Nishant space starts -->
								<td id="pbatchLable" style="display: none">
									<bean:message key="lbl.purpose" />
								</td>
								<td id="pbatchR" style="display: none" >
							    	<html:select styleClass="text" property="batchPurpose" styleId="batchPurpose" >
							    		<html:option value="PDC">INSTALLMENT</html:option>
							    		<html:option value="PRE EMI">PRE EMI</html:option>
							    	</html:select>
							    </td>
								<!-- Nishant space ends -->
								<td id="rt_month1" style="display: none" >
									<bean:message key="lbl.interestcalculationmethod" />
								</td>
							    <td id="rt_month2" style="display: none" >
							    	<html:select styleClass="text" property="interestMethod" styleId="interestMethod" >
							    	<html:option value="S">Simple Interest</html:option>
							    	<html:option value="C">Compound Interest</html:option>
							    	</html:select>
							    </td>
								
							</tr>
							<tr id="4" >
								<td id="dateF1" style="display: none;">
									<bean:message key="lbl.FromDate" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="dateF2" >
									<bean:message key="lbl.FromDate" />
								</td>
								<td id="dateF3" style="display: none;">
									Insurance From Date(DD-MM-YYYY)
								</td>
								<td>
									<div id="dateFromD">
										<html:text property="fromDate" styleId="fromDate"
											styleClass="text" value="" maxlength="10"
											onchange="checkDate('fromDate');validateLeadDate();" />
									</div>
									<div id="withOutdateFromD" style="display: none">
										<html:text property="fromDate" styleId="notfromDate"
											styleClass="text" readonly="true" value="" maxlength="10"
											tabindex="-1" />
									</div>
								</td>
								<td id="dateT1" style="display: none;">
									<bean:message key="lbl.ToDate" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="dateT2" >
									<bean:message key="lbl.ToDate" />
								</td>
								<td id="dateT3" style="display: none;">
									Insurance To Date(DD-MM-YYYY)
								</td>
								<td>
									<div id="dateToD">
										<html:text property="toDate" styleId="toDate"
											styleClass="text" value="" maxlength="10"
											onchange="checkDate('toDate');validateLeadDate();" />
									</div>
									<div id="withOutdateToD" style="display: none">
										<html:text property="toDate" styleId="nottoDate"
											styleClass="text" value="" readonly="true" maxlength="10"
											tabindex="-1" />
									</div>
								</td>
							</tr>
							<!--						Rudra1	-->
							<tr id="partnerName1" style="display:none;">
								<td>Partner Name</td>
								<td>
									<html:text property="partnerName" styleClass="text" value="" styleId="partnerName" readonly="false" />
									<html:hidden property="lbxPartnerId" styleId="lbxPartnerId"	value="" />
									<html:button property="partnerNameButton" styleId="partnerNameButton" tabindex="1" value=" " styleClass="lovbutton"
										onclick="openLOVCommon(6071,'reportid','partnerName','','', '','','','toDate');" ></html:button>
								</td>
								
								
								<td>Loan No</td>
								<td>
									<html:text property="loan_NO" styleClass="text" value="" styleId="loan_NO" readonly="false" />
									<html:hidden property="lbxloan_Id" styleId="lbxloan_Id" value="" />
									<html:button property="loanNoButton" styleId="loanNoButton"	tabindex="1" value=" " styleClass="lovbutton"
										onclick="openLOVCommon(6072,'reportid','loan_NO','partnerName','', 'lbxPartnerId','select Value','','toDate');" ></html:button>
								</td>
							</tr>
							
							<tr id="partnershipType" style="display:none;">
								<td>Partnership Type</td>
								<td>
									<html:select property="partnerShipType" styleClass="text">
										<html:option value="All">ALL</html:option>
										<html:option value="CB">CHANNEL BUSINESS</html:option>
										<html:option value="CP">CONSORTIUM PARTNER</html:option>
									</html:select>
								</td>
								<td>Partner</td>
								<td>
									<html:select property="partnerType" styleClass="text" onchange="hideLovofPartner()" styleId="partnerType">
										<html:option value="Specific">Specific</html:option>
										<html:option value="All">All</html:option>
									</html:select>
								</td>
							</tr>
							
							<tr id="partnerCode1" style="display:none;">
								<td>Partner Code</td>
								<td>
									<select id="partnerCode" name="partnerCode" size="5" multiple="multiple"  style="width:150px"></select>
									<html:button property="partnerCodeButton" styleId="partnerCodeButton" tabindex="1" value=" " styleClass="lovbutton"
										onclick="return openMultiSelectLOVCommon(6071,'reportid','partnerCode','','','','','','lbxPartnerId');" >
									</html:button>
								</td>
							</tr>
							

							<tr id="5">
								<td id="r40">
									<bean:message key="lbl.AsOnDate" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="r41" style="display: none;">
									<bean:message key="lbl.AsOnDate" />
								</td>
								<td>
									<div id="withcal">
										<html:text property="asonDate" styleId="asonDate"
											styleClass="text" value="${userobject.businessdate}" maxlength="10"
											onchange="checkDate('asonDate');validateLeadDate();" />
									</div>
									<div id="withOutcal" style="display: none">
										<html:text property="asonDate" readonly="true"
											styleId="WasonDate" styleClass="text" value="" maxlength="10"
											tabindex="-1" />
									</div>
								</td>
							</tr>
							
<!--							Rudra ------>

							
							<tr id=b1 style="display: none">
								<td id="bnkId" style="display: none">
									<bean:message key="lbl.BankID" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id=bnkid1>
									<html:text property="BankID" styleClass="text" value=""
										styleId="BankID" readonly="false"
										onchange="openLOVCommon(7,'reportid','BankID','','', '','','','bankName','','','Y');"
										 />
									<html:hidden property="lbxBankID" styleId="lbxBankID" value="" />
									<input type="hidden" name="bankName" id="bankName" value="" />
									<html:button property="specificLoanButton"
										styleId="specificLoanButton" tabindex="1"
										onclick="openLOVCommon(7,'reportid','BankID','','', '','','','bankName');"
										value=" " styleClass="lovbutton">
									</html:button>
								</td>
							</tr>
							<tr id=rsptmemo style="display: none">
								<td><bean:message key="lbl.loanNumber"/><span><font color="red">*</font></span></td>
								<td>
									<input type="hidden" name="loanId" id="loanId" value="" />
									<input type="hidden" name="custName" id="custName" value="" />
									<input type="text" name="loanNo" id="loanNo" value=""  
									onchange="openLOVCommon(2012,'reportid','loanNo','','', '','','','custName','','','Y')"
									 tabindex="-1"/>
									
									<html:button property="loanLov" styleId="loanLov" tabindex="1"
										onclick="openLOVCommon(2012,'reportid','loanNo','','', '','','','custName')"
										value=" " styleClass="lovbutton">
									</html:button>
								</td>
							
								<td><bean:message key="lbl.receiptNo"/><span><font color="red">*</font></span></td>
								<td>
									<html:text property="instrumentNo" styleClass="text" value="" styleId="instrumentNo" readonly="false"
									onchange="openLOVCommon(2011,'reportid','instrumentNo','loanNo','', 'loanId','First Select Loan No','','instDate','instAmt','instMode',''Y,)"
									 />
									<html:hidden property="instrumentId" styleId="instrumentId" value="" />
									<input type="hidden" name="instDate" id="instDate" value="" />
									<input type="hidden" name="instAmt" id="instAmt" value="" />
									<input type="hidden" name="instMode" id="instMode" value="" />
									<html:button property="instrumentLov" styleId="instrumentLov" tabindex="1"
										onclick="openLOVCommon(2011,'reportid','instrumentNo','loanNo','', 'loanId','First Select Loan No','','instDate','instAmt','instMode')"
										value=" " styleClass="lovbutton">
									</html:button>
								</td>
							</tr>
							<tr id="specific_deal" style="display: none">
								<td>
									<bean:message key="lbl.dealNo" />
									<span><font color="red">*</font>
									</span>
								</td>

								<td>
									<html:text styleClass="text" property="specificDealNo"
										styleId="specificDealNo" maxlength="20" value=""
										readonly="false" tabindex="-1" />
									<html:hidden property="lbxDealNo" styleId="lbxDealNo" value="" />
									<input type="hidden" name="abc" id="abc" />
									<html:button property="dealNoFromSButton"
										styleId="dealNoFromSButton"
										onclick="openDealLov()" 
										value=" " styleClass="lovbutton"></html:button>
								</td>
							</tr>
							<tr id="loan_no" style="display: none">
								<td id="loan_number1" style="display: none">
									<bean:message key="lbl.loanNo" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="loan_number2" style="display: none">
									<bean:message key="lbl.loanNumber" />
								</td>
								<td>
									<html:hidden property="rdp" styleClass="text" styleId="rdp"
										value="" />
									<html:text property="rdpLoanNumber" styleClass="text"
										styleId="rdpLoanNumber" maxlength="100" readonly="false"
										onchange="openLOVCommon(277,'reportid','rdpLoanNumber','rdp','', 'rdp','','','userName','','','Y')"
										value="" tabindex="-1" />
									<html:hidden property="lbxloannumber" styleId="lbxloannumber"
										value="" />
									<input type="hidden" name="userName" id="userName" value="" />
									<html:button property="dealButton" styleId="dealButton"
										tabindex="1"
										onclick="openLOVCommon(277,'reportid','rdpLoanNumber','rdp','', 'rdp','','','userName')"
										value=" " styleClass="lovbutton"></html:button>
								</td>
							</tr>
							<tr id="group_id" style="display: none">
								<!--									<td id="groupid" style="display: none"><bean:message key="lbl.groupName"/><span><font color="red">*</font></span></td>-->
								<td id="groupid" style="display: none">
									<bean:message key="lbl.groupName" />
								</td>
								<td>
									<html:hidden property="groupid" styleClass="text"
										styleId="groupid" value="" />
									<html:text property="groupNumber" styleClass="text"
										styleId="groupNumber" maxlength="100" readonly="false" onchange="openLOVCommon(321,'reportid','groupNumber','groupid','', 'groupid','','','userName','','','Y')" value=""
										tabindex="-1" />
									<html:hidden property="lbxGroupID" styleId="lbxGroupID"
										value="" />
									<input type="hidden" name="userName" id="userName" value="" />
									<html:button property="dealButton" styleId="dealButton"
										tabindex="1"
										onclick="openLOVCommon(321,'reportid','groupNumber','groupid','', 'groupid','','','userName')"
										value=" " styleClass="lovbutton"></html:button>
								</td>
							</tr>
							<tr id="pdc" style="display: none">
								<td>
									Presentation Date
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:text styleClass="text" property="presentationdate"
										styleId="presentationdate" maxlength="10" value=""
										onchange="checkDate('presentationdate');" />
								</td>
								<td>
									<bean:message key="lbl.type" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:select property="instrumentType" styleClass="text">
										<html:option value="All">All</html:option>
										<html:option value="Y">YES</html:option>
										<html:option value="N">NO</html:option>
									</html:select>
								</td>
							</tr>

							<tr id=loannumber style="display: none">
								<td  id="LoanNoLov" style="display: none">
									<bean:message key="lbl.loanNumber" />
								</td>
								<td>
									<div id=LoanNoLov1 >
										<html:text property="loanNumber" styleClass="text" value=""
											styleId="loanNumber" readonly="false" tabindex="-1" />
										<html:hidden property="lbx_loan_Number"
											styleId="lbx_loan_Number" value="" />
										<input type="hidden" name="abc" id="abc" />
										<div id="loannumber_lov1" style="display: Inline">
											<html:button property="loanFromButton"
												styleId="loanFromButton" tabindex="1"
												onclick="openWelcomeLOV();"
												value=" " styleClass="lovbutton"></html:button>
												<html:hidden styleClass="text" property="lbxDisbursalID" styleId="lbxDisbursalID" />
												<input type="hidden" id="disbursalAMT">
												<input type="hidden" id="disbursalDateN">
												<input type="hidden" id="disbursalStatus">
										</div>
									</div>
								</td>
								<td  id="customerRoleLab" style="display: none"><bean:message key="lbl.customerRole"/>
								</td>
								<td>
									<html:select property="customerRole" styleClass="text" styleId="customerRole">
										<html:option value="A">Applicant/Co-Applicant</html:option>
										<html:option value="G">Guarantor </html:option>
									</html:select>
								</td>
							</tr>
                            <div id="datewithcal" style="display: none">
								<tr id="daterange" style="display: none">
                                    <td id="FA">
										<bean:message key="lbl.ApprovalDateFrom" />
									</td>
									 <td id='FD' style="display: none">
										<bean:message key="lbl.DisbursalDateFrom" /><font color="red">*</font>
									</td>
									<td>
										<html:text property="approvalDateFrom"
											styleId="approvalDateFrom" styleClass="text" value=""
											maxlength="10"
											onchange="checkDate('approvalDateFrom');disabledloan();" />

									</td>

									<td id='TA'> 
										<bean:message key="lbl.ApprovalDateTo" />
									</td>
									<td id='TD' style="display: none"> 
										<bean:message key="lbl.DisbursalDateTo" /><font color="red">*</font>
									</td>
									<td>
										<html:text property="approvalDateTo" styleId="approvalDateTo"
											styleClass="text" value="" maxlength="10"
											onchange="checkDate('approvalDateTo');disabledloan();" />

									</td>
								</tr>
							</div>
							<div id="datewithoutcal" style="display: none;">

								<tr id="daterangewithoutcal" style="display: none">
									<td id='FA'>
										<bean:message key="lbl.ApprovalDateFrom" />
									</td>
									 <td id='FD' style="display: none">
										<bean:message key="lbl.DisbursalDateFrom" />
									</td>
									<td>
										<html:text property="approvalDateFrom" styleId="notfromDate"
											styleClass="text" readonly="true" value="" maxlength="10"
											tabindex="-1" />
									</td>
									<td id='TA'> 
										<bean:message key="lbl.ApprovalDateTo" />
									</td>
									<td id='TD' style="display: none"> 
										<bean:message key="lbl.DisbursalDateTo" />
									</td>
									<td>

										<html:text property="approvalDateTo" styleId="nottoDate"
											styleClass="text" readonly="true" value="" maxlength="10"
											tabindex="-1" />
									</td>
								</tr>

							</div>

							<div id="datewithcal1" style="display: none">
								<tr id="daterange1" style="display: none">

									<td>
										<bean:message key="lbl.FromDate" />
									</td>
									<td>
										<html:text property="fromdateforsd" styleId="fromdateforsd"
											styleClass="text" value="" maxlength="10"
											onchange="checkDate('fromdateforsd');disabledcheckbox();" />
									</td>
									<td>
										<bean:message key="lbl.ToDate" />
									</td>
									<td>
										<html:text property="todateforsd" styleId="todateforsd"
											styleClass="text" value="" maxlength="10"
											onchange="checkDate('todateforsd');disabledcheckbox();" />
									</td>
								</tr>
							</div>
							<div id="datewithoutcal1" style="display: none;">

								<tr id="daterangewithoutcal1" style="display: none">
									<td>
										<bean:message key="lbl.FromDate" />
									</td>
									<td>
										<html:text property="fromdateforsd" styleId="notfromDate"
											styleClass="text" readonly="true" value="" maxlength="10"
											tabindex="-1" />
									</td>
									<td>
										<bean:message key="lbl.ToDate" />
									</td>
									<td>

										<html:text property="todateforsd" styleId="nottoDate"
											styleClass="text" readonly="true" value="" maxlength="10"
											tabindex="-1" />
									</td>
								</tr>
</div>
								<tr id="SDreport" style="display: none">
									<td>
										AsOnDate
									</td>
									<td>
										<logic:equal value="Y" name="AsOnDateForSD">
											<input type="checkbox" name="AsOnDateForSD"
												id="AsOnDateForSD" checked="checked"
												onclick="SDreportdisable();" />

										</logic:equal>
										<logic:notEqual value="N" name="AsOnDateForSD">
											<input type="checkbox" name="AsOnDateForSD"
												id="AsOnDateForSD" value="N" onclick="SDreportdisable();" />
										</logic:notEqual>
									</td>
								</tr>
								<tr id="dateRangeForMaturity" style="display: none">
								<td><bean:message key="lbl.FromDate" /></td>
								<td>
									<div id="fromDatewithcal">
										<html:text property="dateFrom" styleId="dateFrom"
											styleClass="text" value="" maxlength="10"
											onchange="checkDate('dateFrom');disableLoanFormaturity();" />
									</div>
									<div id="fromDatewitoutcal" style="display: none">
										<html:text property="dateFrom" styleId="notfromDate"
											styleClass="text" readonly="true" value="" maxlength="10"
											tabindex="-1" />
									</div>
								</td>
								<td><bean:message key="lbl.ToDate" /></td>
								<td>
									<div id="toDatewithcal">
										<html:text property="dateTo" styleId="dateTo"
											styleClass="text" value="" maxlength="10"
											onchange="checkDate('dateTo');disableLoanFormaturity();" />
									</div>
									<div id="toDatewithoutcal" style="display: none">
										<html:text property="dateTo" styleId="nottoDate"
											styleClass="text" value="" readonly="true" maxlength="10"
											tabindex="-1" />
									</div>
								</td>
							</tr>
							
							<tr id=batch style="display: none">
								<td  id="batch_1" style="display: none">
									<bean:message key="lbl.batch"/>
								</td>
								<td>
									<div id=Batch1 >
										<html:text property="batchNo" styleClass="text" value=""
											styleId="batchNo" readonly="false"
											onchange="openLOVCommon(333,'reportid','batchNo','','', '','','disablePresentationDate','abc','','','Y');"
											 tabindex="-1" />
										<html:hidden property="lbxBatchNo"
											styleId="lbxBatchNo" value="" />
										<input type="hidden" name="abc" id="abc" />
										<div id="Batch2" style="display: inline">
											<html:button property="loanFromButton"
												styleId="loanFromButton" tabindex="1"
												onclick="openLOVCommon(333,'reportid','batchNo','','', '','','disablePresentationDate','abc');"
												value=" " styleClass="lovbutton"></html:button>
										</div>
									</div>
								</td>
							</tr>
							
								 <tr id="Presentation_generate_batch" style="display: none">
							<td><bean:message key="lbl.presentationDate(DD-MM-YYYY)" /></td>
								<td>
									<div id="pDatewithcal" style="display: none">
										<html:text property="presentationDateForBatch" styleId="presentationDateForBatch"
											styleClass="text" value="" maxlength="10"
											onchange="checkDate('presentationDateForBatch');disableBatchNo();" />
									</div>
									<div id="pDatewithoutcal" style="display: none">
										<html:text property="presentationDateForBatch" styleId="presentationDateForBatch"
											styleClass="text" value="" readonly="true" maxlength="10"
											tabindex="-1" />
									</div>
								</td>
								
							<td>
								<bean:message key="lbl.batchGenerates"/>
								</td>
								<td>
									<html:select property="batchGenerates" styleId="batchGenerates" styleClass="text">
										<html:option value="" >--Select--</html:option>
										<html:option value="Y">YES</html:option>
										<html:option value="N">NO</html:option>
									</html:select>
								</td>
									 </tr>
							<tr id="instrument_mode" style="display: none" >
								<td style="display: none" id=receipt>Instrument Mode</td>
								<td style="display: none" id=interest>Status</td>
								<td>
								<div id=receipt_report style="display: none">
										<html:select styleId="instrumentMode" styleClass="text" property="instrumentMode">
    									<html:option value="All">All</html:option>
										<html:option value="C">CASH</html:option>
										<html:option value="Q">CHEQUE</html:option>
										<html:option value="E">ECS</html:option>
										<html:option value="D">DRAFT</html:option>
										<html:option value="N">NEFT</html:option>
										<html:option value="R">RTGS</html:option>
										<html:option value="O">OTHERS</html:option>
										<html:option value="DIR">Direct Debit</html:option>
									</html:select>
								</div>
								<div id="int_due" style="display: none">
										<html:select property="recStatus" styleClass="text" styleId="recStatus">											>
											<html:option value="All">All</html:option>
											<html:option value="A">Active</html:option>
											<html:option value="C">Closed</html:option>
											
										</html:select>
									</div>
									</td>
							</tr>
							<tr id="rate" style="display: none">
								<td><bean:message key="lbl.interestCalculationOn" /></td>
							    <td><html:select styleClass="text" property="interestType" styleId="interestType" onchange="changeRateType();">
							    		<html:option value="U">User Defined Rate</html:option>
							    		<html:option value="L">Loan Rate</html:option>
							    	</html:select>
							    </td>
								<td><bean:message key="lbl.rateInterest" /></td>
							    <td><html:text styleClass="text" property="rateInterest" styleId="rateInterest"  style="text-align: right;"  value="" onkeypress="return numbersonly(event, id, 4);" onchange="checkRate('rateInterest');"/></td>
							</tr>
							<!--sidharth code for od simulation report starts-->
							<tr id="rateMethod" style="display: none">
								<td><bean:message key="lbl.interestcalculationmethod" /></td>
							    <td><html:select styleClass="text" property="interestMethod" styleId="interestMethod" >
							    		<html:option value="S">Simple Interest</html:option>
							    		<html:option value="C">Compound Interest</html:option>
							    	</html:select>
							    </td>
							</tr>
							<!--sidharth code for od simulation report Ends-->
							<!--sidharth  code for future EMI receivable starts-->
							<tr id="future1" style="display:none">
							<td><bean:message key="lbl.underType" />
							<span><font color="red">*</font></span>
							</td>
							<td><input type="radio" name="detailOrSum" value="D" id="detailOrSum"> Detailed
					    		<input type="radio" name="detailOrSum" value="S" id="detailOrSum"> Summary
							</td>
								<td><bean:message key="lbl.receivablecomponent" /></td>
							    <td><html:select styleClass="text" property="amtType" styleId="amtType" >
							    		<html:option value="P">Only Principal</html:option>
							    		<html:option value="I">Only Interest</html:option>
							    		<html:option value="T">Total Instalment</html:option>
							    	</html:select>
							    </td>
					
							</tr>
							<tr id="future2" style="display:none">
							 <td><bean:message key="lbl.intervalfrequency" /></td>
							    <td><html:select styleClass="text" property="intervalFreq" styleId="intervalFreq" >
							    		<html:option value="M">Monthly</html:option>
							    		</html:select>
							   </td>  
							</tr>
							<!--	sidharth  code for future EMI receivable starts-->
							<tr id="ecs_notpad" style="display: none">
								<td><bean:message key="lbl.presentationDate" /><span><font color="red">*</font></span></td>
								<td><html:text styleClass="text" property="sponsorDate" styleId="sponsorDate" maxlength="10" value="" onchange="checkDate('sponsorDate');" /></td>
								<td><bean:message key="lbl.sponsorCode" /><span><font color="red">*</font></span></td>
								<td><html:select property="sponsor" styleId="sponsor" styleClass="text">
										<logic:present name="sponsorlist">
											<logic:notEmpty name="sponsorlist">
												<html:optionsCollection name="sponsorlist" label="sponsorDesc" value="sponsorCode" />
											</logic:notEmpty>
										</logic:present>
									</html:select>
							    </td>								
							</tr>
							<tr id="interest_certificate" style="display: none">
							    <td><bean:message key="lbl.FromDate" /><span><font color="red">*</font></span></td>
								<td>
												<html:select property="intCerFromDate" styleId="intCerFromDate" styleClass="text" onchange="calculateMaxDate(this.value,'intCerToDate')">
												<logic:present name="financeYear">
													<logic:notEmpty name="financeYear">
														<html:optionsCollection name="financeYear" label="financeYear" value="financeYear" />
													</logic:notEmpty>
												</logic:present>
												</html:select>
								</td>
								<td><bean:message key="lbl.ToDate" /><span><font color="red">*</font></span></td>
								<td><html:text styleClass="text" property="intCerToDate" styleId="intCerToDate" maxlength="10" value="" onchange="checkToDate(this.value,'intCerToDate');" /></td>								
							</tr>
							<!-- Nishant Space starts for Balance confirmation report -->
							<tr id="balance_certificate_report" style="display: none">
							    <td><bean:message key="lbl.FromDate" /><span><font color="red">*</font></span></td>
								<td>
												<html:select property="balConfFromDate" styleId="balConfFromDate" styleClass="text" onchange="calculateMaxDate(this.value,'balConfToDate')">
												<logic:present name="financeYear">
													<logic:notEmpty name="financeYear">
														<html:optionsCollection name="financeYear" label="financeYear" value="financeYear" />
													</logic:notEmpty>
												</logic:present>
												</html:select>
								</td>
								<td><bean:message key="lbl.ToDate" /><span><font color="red">*</font></span></td>
								<td><html:text styleClass="text" property="balConfToDate" styleId="balConfToDate" maxlength="10" value="" onchange="checkToDate(this.value,'balConfToDate');" /></td>								
							</tr>
						
							<!-- Nishant Space starts for Balance confirmation report -->
							<tr id="payable_receivable" style="display: none">
								<td><bean:message key="lbl.paymentMode" /><span><font color="red">*</font></span></td>
							    <td><html:select styleClass="text" property="paymentMode" styleId="paymentMode">
							    		<html:option value="R">Receivable</html:option>
							    		<html:option value="P">Payable</html:option>
							    	</html:select>
							    </td>
							    <td><bean:message key="lbl.chargeCode"></bean:message><span><font color="red">*</font></span></td>
		      					<td> 	 <% session.setAttribute("poolRecStatus","A"); %>     
		      						<select id="charges"  name="charges" size="5" multiple="multiple" style="width: 150px"></select>
              						<html:button property="consButton" styleId="consButton" onclick="return openMultiSelectLOVCommon(472,'reportid','charges','','', '','','','chargesId');" value=" " styleClass="lovbutton"></html:button>
              					    <html:hidden property="chargesId" styleId="chargesId" value=""/>
              					</td>
							</tr>
							<tr id="comLogo" style="display: none">
								<td><bean:message key="lbl.companyLogo"/></td>
								<td><html:select styleClass="text" property="companyLogo" styleId="companyLogo">
							    		<html:option value="Y">Yes</html:option>
							    		<html:option value="N">No</html:option>
							    	</html:select>
							    </td>
							</tr>
								<tr id="paySlipManual" style="display: none">
								<td><bean:message key="lbl.ReceiptDate" /><font color="red">*</font></td>
								<td>
									<html:text styleClass="text" property="receiptDate" styleId="receiptDate" maxlength="10" value="" onchange="checkDate('receiptDate');changeDateFormateForPaySlip()" />
									<html:hidden styleClass="text" property="receiptDateName" styleId="receiptDateName" value="" />
								</td>								
							
							<td><bean:message key="lbl.batch"/><font color="red">*</font></td>
						<td>
							<html:text property="batchNO" styleClass="text" styleId="batchNO" maxlength="100" readonly="false"
							onchange="openLOVCommon(2068,'reportid','batchNO','receiptDate','', 'receiptDateName','Select Received Date','','status','','','Y');"
							 value=""/>
							<html:hidden property="lbxBatchNO" styleId="lbxBatchNO" value="" />
							<input type="hidden" id="status" value=""/>
						<html:button property="batchButton" styleId="batchButton" onclick="openLOVCommon(2068,'reportid','batchNO','receiptDate','', 'receiptDateName','Select Received Date','','status');" value=" " styleClass="lovbutton"></html:button>
						                                                                  
						<!--<html:button property="batchButton" styleId="batchButton" tabindex="1" onclick="openLOVCommon(333,'generateBatch','batchNo','finalDate','','finalDate','','','status')" value=" " styleClass="lovbutton"  ></html:button>	
						--></td>
							</tr>

							<tr id="GET_ACTUAL_IRR_ID">
								<td>Fore Closure Charges</td>
								<td><html:text styleClass="text" style="text-align: right;" property="foreClosureCharges" styleId="foreClosureCharges" maxlength="10" value="0" onchange="numberFormatting('foreClosureCharges','2');" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id)"/></td>
								<td>Exit Load</td>
								<td><html:text styleClass="text" style="text-align: right;" property="exitLoad" styleId="exitLoad" maxlength="10" value="0" onchange="numberFormatting('exitLoad','2');" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id)"/></td>
							</tr>
                            </tr>

							<tr id="paySlip_Manual" style="display: none">
								<td><bean:message key="lbl.depositBankId"/><font color="red">*</font></td>
				 <td>	
	 				<html:text styleClass="text" property="bank" styleId="bank" maxlength="100" value="" readonly="false"
	 				onchange="closeLovCallonLov1();openLOVCommon(79,'reportid','bank','','branch', 'lbxBranchID','','','lbxBankID','','','Y')"
	 				 tabindex="-1"/>
    				<html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(79,'reportid','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>
    			    <html:hidden  property="lbxBankID" styleId="lbxBankID" value=""/>
				 </td>  
				 
				 <td><bean:message key="lbl.depositBranchId"/><font color="red">*</font></td>
				<td>
					<html:text styleClass="text" property="branch" styleId="branch1" maxlength="100" value="" readonly="false"
					onchange="openLOVCommon(80,'reportid','branch1','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode','bankAccount','Y');closeLovCallonLov('bank');"
					 tabindex="-1"/>
   					<html:hidden  property="lbxBranchID" styleId="lbxBranchID" value=""/>
  					<html:button property="branchButton" styleId="branchButton" onclick="openLOVCommon(80,'reportid','branch1','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode','bankAccount');closeLovCallonLov('bank');" value=" " styleClass="lovbutton"></html:button>  
  					<html:hidden styleClass="text" property="micr" styleId="micr" value="" />
  					<html:hidden styleClass="text" property="ifscCode" styleId="ifscCode"  value="" />
  					<html:hidden styleClass="text" property="bankAccount" styleId="bankAccount" value=""  />
  				</td>	
							</tr>
						
							<tr  id="product" style="display: none">
							<td><bean:message key="lbl.productCategory"/></td>
								<td>
									<html:select property="productCategory" styleClass="text" styleId="productCategory">
									<html:option value="CV">CV</html:option>
										<html:option value="SME">SME </html:option>
									</html:select>
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

					
					<!-- work started by amit2  -->
					
					<tr id ="instruments_mode" style="display: none">
								
									<td><bean:message key="lbl.instrumentMode"/></td>
									<td>
										<html:select property="instrumentsMode" styleId="instrumentsMode"  styleClass="text">
											<html:option value="All">All</html:option>
											<html:option value="Q">PDC</html:option>
											<html:option value="E">ECS</html:option>
											</html:select> 
									</td>
								</tr>
								
								<tr id="scheme1" style="display: none">
								<td  id="scheme_2" style="display: none">
									<bean:message key="lbl.scheme"/>
								</td>
								<td>
									<div id=scheme3 >
										<html:text property="scheme" styleClass="text" styleId="scheme" readonly="false" tabindex="-1"/>
		 		                        <html:hidden property="lbxSchemeID" styleId="lbxSchemeID"/>
		 		   						<html:button property="schemeButton" styleId="schemeButton" onclick="openLOVCommon(5060,'reportid','scheme','','','','','','lbxSchemeID')" value=" " styleClass="lovbutton"> </html:button>
										</div>
								</td>
								<td><bean:message key="lbl.ReportType"/></td>
									<td>
										<html:select property="simulationReportType" styleId="simulationReportType"  styleClass="text">
											<html:option value="M">Monthly Opening Only</html:option>
											<html:option value="S">Since Starting</html:option>
											</html:select> 
									</td>
							</tr>
								<tr id="loanId_report1" style="display:none">
								<td id="loanId_report2" style="display:none"> <bean:message key="lbl.loanNo"/></td>
								<td>
								<html:hidden property="lbxLoanId" styleId="lbxLoanId"/>
								<html:text property="REP_loanno" styleId="REP_loanno" styleClass="text" value="" readonly="false" tabindex="-1"/>
								<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(5061,'reportid','REP_loanno','','','','','','lbxLoanId')" value=" " styleClass="lovbutton"> </html:button>
								</td>
								</tr>	
								
								<!-- Avinash code starts for gst reports -->
								<tr id="gst_report">
								  <td id="gst_report_td1"><bean:message key="lbl.reportSubType" /></td>
							    <td id="gst_report_td2"><html:select styleClass="text" property="reportSubType" styleId="reportSubType" >
							    		<html:option value="TA">TAX INVOICE</html:option>
							    		<html:option value="CN">CREDIT NOTE</html:option>
							    		<html:option value="BOS">BILL OF SUPPLY</html:option>
							    		<html:option value="DN">DEBIT NOTE</html:option>
								       <html:option value="ARAP">ARAP_TAX_INVOICE_NO</html:option>		    		
							    	</html:select>
							    </td>
								
								</tr>
								
								<tr id="rtgsNeft" style="display:none">
								<td> <bean:message key="lbl.ReportType"/><font color="red">*</font></td>
								<td>
									<html:select styleClass="text" property="rtgsType" styleId="rtgsType" >
							    		<html:option value="ALL">ALL</html:option>
							    		<html:option value="DOWNLOADED">DOWNLOADED</html:option>
							    		<html:option value="ND">NON DOWNLOADED</html:option>
							    	</html:select>
								</td>
								</tr>	
								
								<!-- Avinash code ends for gst reports -->
								
								<!-- Shubham changes for Password Protected Starts -->
					<tr id=pdf style="display: none">
								<td><bean:message key="lbl.passwordPdf"/><span><font color="red">*</font></span></td>
								<td><html:select styleClass="text" property="passwordPdf" styleId="passwordPdf" >
							    		<html:option value="N">No</html:option>
							    		<html:option value="Y">Yes</html:option>
							    	</html:select></td>
						
							    </tr>
					<!-- Shubham changes for Password Protected end -->



													<!--work end	-->
						
						
						

						</table>
<!--						<div id="auFc" style="display:none">-->
<!--						<fieldset><legend><bean:message key="lbl.chargeDetail"/></legend>  -->
<!--								<table width="100%"  border="0" cellspacing="0" cellpadding="0">-->
<!--								<tr>-->
<!--    								<td class="gridtd">-->
<!--    								<table width="100%" border="0" cellspacing="1" cellpadding="1">-->
<!--    								<tr class="white2">-->
<!--    	 								<td align="center"><b><bean:message key="lbl.charge"/></b></td>-->
<!--         								<td align="center"><b><bean:message key="lbl.adviceType"/></b></td>-->
<!--	     								<td align="center"><b><bean:message key="lbl.AdviceAmount"/></b></td>-->
<!--	     								<td align="center"><b><bean:message key="lbl.adjustedAmount"/></b></td>-->
<!--	     								<td align="center"><b><bean:message key="lbl.chargeType"/></b></td>-->
<!--	     								<td align="center"><b><bean:message key="msg.chargeAmount"/></b></td>-->
<!--	     								<td align="center"><b><bean:message key="lbl.balanceAmount"/></b></td>	-->
<!--	    							</tr>-->
<!--	    							<tr class="white">-->
<!--    	 								<td align="left">EMI with Insurance</td>-->
<!--         								<td>-->
<!--         									<html:select styleClass="text" property="adviceType1" styleId="adviceType1" disabled="true">-->
<!--         										<html:option value="">Select</html:option>-->
<!--							    				<html:option value="R">Receivable</html:option>-->
<!--							    				<html:option value="P">Payable</html:option>-->
<!--							    			</html:select>-->
<!--										</td>-->
<!--	     								<td><html:text property="adviceAmount1" styleId="adviceAmount1" styleClass="text" value="0.00" readonly="true" style="text-align: right"/></td>	 -->
<!--	     								<td><html:text property="adjustedAmount1" styleId="adjustedAmount1" styleClass="text" value="0.00" readonly="true" style="text-align: right"/></td>-->
<!--	     								<td>-->
<!--         									<html:select styleClass="text" property="chargeType1" styleId="chargeType1" onchange="calBalAmt1()">-->
<!--         										<html:option value="R">Receivable</html:option>-->
<!--							    				<html:option value="P">Payable</html:option>-->
<!--							    			</html:select>-->
<!--										</td>-->
<!--	     								<td><html:text property="chargeAmount1" styleId="chargeAmount1" styleClass="text" value="0.00" style="text-align: right"  onkeypress="return numbersonly(event, id, 18)" onblur="calBalAmt1()" onkeyup="checkNumber(this.value, event, 18, id);" /></td>	 -->
<!--	     								<td><html:text property="blanceAmount1" styleId="blanceAmount1" styleClass="text" value="0.00" readonly="true" style="text-align: right"/></td>    -->
<!--	    							</tr>-->
<!--	    							<tr class="white">-->
<!--    	 								<td align="left">Legal Charges</td>-->
<!--         								<td>-->
<!--         									<html:select styleClass="text" property="adviceType2" styleId="adviceType2" disabled="true" >-->
<!--         										<html:option value="">Select</html:option>-->
<!--							    				<html:option value="R">Receivable</html:option>-->
<!--							    				<html:option value="P">Payable</html:option>-->
<!--							    			</html:select>-->
<!--										</td>-->
<!--	     								<td><html:text property="adviceAmount2" styleId="adviceAmount2" styleClass="text" value="0.00" readonly="true" style="text-align: right"/></td>	-->
<!--	     								<td><html:text property="adjustedAmount2" styleId="adjustedAmount2" styleClass="text" value="0.00" readonly="true" style="text-align: right"/></td>-->
<!--	     								<td>-->
<!--         									<html:select styleClass="text" property="chargeType2" styleId="chargeType2" onchange="calBalAmt2()">-->
<!--         										<html:option value="R">Receivable</html:option>-->
<!--							    				<html:option value="P">Payable</html:option>-->
<!--							    			</html:select>-->
<!--										</td>-->
<!--	     								<td><html:text property="chargeAmount2" styleId="chargeAmount2" styleClass="text" value="0.00" style="text-align: right" onkeypress="return numbersonly(event, id, 18)" onblur="calBalAmt2()" onkeyup="checkNumber(this.value, event, 18, id);" /></td>	 -->
<!--	     								<td><html:text property="blanceAmount2" styleId="blanceAmount2" styleClass="text" value="0.00" readonly="true" style="text-align: right"/></td>     -->
<!--	    							</tr>-->
<!--	    							<tr class="white">-->
<!--    	 								<td align="left">Collection Charges</td>-->
<!--         								<td>-->
<!--         									<html:select styleClass="text" property="adviceType3" styleId="adviceType3"  disabled="true">-->
<!--         										<html:option value="">Select</html:option>-->
<!--							    				<html:option value="R">Receivable</html:option>-->
<!--							    				<html:option value="P">Payable</html:option>-->
<!--							    			</html:select>-->
<!--										</td>-->
<!--	     								<td><html:text property="adviceAmount3" styleId="adviceAmount3" styleClass="text" value="0.00" readonly="true" style="text-align: right"/></td>	-->
<!--	     								<td><html:text property="adjustedAmount3" styleId="adjustedAmount3" styleClass="text" value="0.00" readonly="true" style="text-align: right"/></td>-->
<!--	     								<td>-->
<!--         									<html:select styleClass="text" property="chargeType3" styleId="chargeType3" onchange="calBalAmt3()">-->
<!--         										<html:option value="R">Receivable</html:option>-->
<!--							    				<html:option value="P">Payable</html:option>-->
<!--							    			</html:select>-->
<!--										</td>-->
<!--	     								<td><html:text property="chargeAmount3" styleId="chargeAmount3" styleClass="text" value="0.00" style="text-align: right"  onkeypress="return numbersonly(event, id, 18)" onblur="calBalAmt3()" onkeyup="checkNumber(this.value, event, 18, id);" /></td>	 -->
<!--	     								<td><html:text property="blanceAmount3" styleId="blanceAmount3" styleClass="text" value="0.00" readonly="true" style="text-align: right"/></td>     -->
<!--	    							</tr>-->
<!--	    							</table>-->
<!--	    							</td>-->
<!--	    						</tr>-->
<!--	    						</table>-->
<!--	    				</fieldset>-->
<!--	    				</div>-->
	    				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td>
								<button type="button" name=" mybutton" title="Alt+G"
									accesskey="G" class="topformbutton3"
									onclick="checkReportsValidation();">
									<bean:message key="button.generate" />
								</button>
								<button type="button" name=" mybutton" title="Alt+R"
										accesskey="R" class="topformbutton2"
										onclick="checkReportsValidationReset();">
										<bean:message key="button.reset" />
								</button>
								
						</td>
						</tr>
						<tr id="sendEmail" style="display:none">
						<td>
						<button type="button" name=" mybutton" title="Alt+E"
									accesskey="E" class="topformbutton4"
									onclick="sendEmail();">
									<bean:message key="button.generateEmail" />
								</button>
						</td>
						</tr>

    					</table>

	    			 </fieldset>
				</html:form>
			</div>
		</div>
		<logic:present name="error">
	<script type="text/javascript">	
		alert('${error}');
		alert("Report can not be Generate.");
	</script>
	<!-- Pooja starts for LOD -->
	</logic:present>
	<logic:present name="lodFails">
	<script type="text/javascript">	
		alert("Some document are pending to mark Received Defferd and Waived for this loan.");
	</script>
	</logic:present>
	<!-- Pooja ends for LOD -->
	<logic:present name="nocFail">
	<script type="text/javascript">	
		alert("Close all linked loans first.");
	</script>
	</logic:present>
	<logic:present name="errorFore">
	<script type = "text/javascript">
	confirm('${errorFore}');
	</script>
	</logic:present>

	</body>
</html>