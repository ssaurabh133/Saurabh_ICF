<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
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
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/report/report.js"></script>
		
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
			
			$(function() {
				$("#asonDate").datepicker({
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
	
	
	
	<body onload="enableAnchor();">
	<input type="hidden" name="dateRengeLimit" id="dateRengeLimit" value= "${dateRengeLimit}">
		<div id="centercolumn">	
			<div id="revisedcontainer">	
				<html:form action="/repaymentServicesReportsAction" styleId="repaymentServicesReport" >
					<fieldset>
						<legend>Report Parameter Form</legend>
							<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
							<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
							
								<tr>
								
									<td>Report Type</td>
									<td>
										<html:select property="reportName" styleId="reportName"  styleClass="text" value="">
											<html:option value="">---Select---</html:option>
											<html:option value="InstrumentReport">Instrument Pending/Forward/Approve/Reject During a Period </html:option>
											<html:option value="H">Hold Instrument Pending/Forward/Approve/Reject During a Period </html:option>
										    <html:option value="L">Release Instrument Pending/Forward/Approve/Reject During a Period </html:option>
										    <html:option value="X">Delete Instrument Pending/Forward/Approve/Reject During a Period </html:option>
						
										</html:select> 
									</td>
								<td id="rf"><bean:message key="lbl.ReportFormat"/><span><font color="red">*</font></span></td>
								<td id="rfv"> 
										<html:select property="reportformat" styleClass="text" value="">
											 <logic:present name="list">
   							                 <logic:notEmpty  name="list">
      						           <html:optionsCollection name="list" label="reportformatid" value="reportformat" />
     					         </logic:notEmpty>
      					 	     </logic:present>
								 </html:select> 
									</td>
								</tr>
								<tr>
							
									<td>Loan Number</td>
									<td>
										<html:text property="loanNo" styleClass="text" styleId="loanNo" maxlength="100" readonly="false"
										onchange="openLOVCommon(168,'repaymentServicesReport','loanNo','','', '','','','custName','','','Y')"
										 value=""/>
										<html:hidden property="lbx_loan_from_id" styleId="lbx_loan_from_id" value="" />																			
										<html:button property="specificLoanButton" styleId="specificLoanButton" tabindex="-1" onclick="openLOVCommon(168,'repaymentServicesReport','loanNo','','', '','','','custName')" value=" " styleClass="lovbutton"  ></html:button>
									</td>
									<td>Customer Name</td>
								     <td><html:text property="custName" styleClass="text" value="" styleId="custName" readonly="true"/>
								    </td>
								   </tr>
				
				                  <tr>
								
									<td>Instrument Mode</td>
									<td>
										<html:select property="InstrumentMode" styleId="InstrumentMode"  styleClass="text" value="">
											<html:option value="All">All</html:option>
											<html:option value="Q">PDC</html:option>
											<html:option value="E">ECS</html:option>
									<html:option value="H">ACH</html:option>
											</html:select> 
									</td>
									<td id="rf">Status</td>
									<td id="rfv">
										<html:select property="status" styleId="status" styleClass="text" value="">
											<html:option value="All">All</html:option>
											<html:option value="P">Pending</html:option>
											<html:option value="F">Forward</html:option>
											<html:option value="A">Approved</html:option>
										    <html:option value="L">Cancel</html:option>
						
										</html:select> 
									</td>
						
								</tr>
					
								<tr>
									<td >From Date (DD-MM-YYYY)</td>
									<td>
								<div id="dateFromD">
<!--									<html:text property="fromDate" styleId="fromDate" styleClass="text" value=""  /> -->
									<html:text property="fromDate"  styleId="fromDate" styleClass="text" value="" maxlength="10" onchange="checkDate('fromDate');validateLeadDate();"/>
								</div>
								<div id="withOutdateFromD" style="display: none">
									<html:text property="fromDate" styleId="notfromDate" styleClass="text" readonly="true" value=""  /> 
									
								</div>
									</td>
									<td>To Date (DD-MM-YYYY)</td>
									<td>
									<div id="dateToD">
<!--									<html:text property="toDate" styleId="toDate" styleClass="text" value=""  />-->
									<html:text property="toDate"  styleId="toDate" styleClass="text" value="" maxlength="10" onchange="checkDate('toDate');validateLeadDate();"/> 
									</div>
									<div id="withOutdateToD" style="display: none">
									<html:text property="toDate" styleId="nottoDate" styleClass="text" value="" readonly="true" /> 
									</div>
									</td>
					
								</tr>
					<tr >
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
								<tr>
								<td >
									<bean:message key="lbl.branchname" />
									<font color="red">*</font>
								</td>
								<td>
								<select id="branchid"  name="branchid" size="5" multiple="multiple" style="width: 150px"></select>
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
					
<!--								<tr>-->
<!--									<td>As on Date (DD-MM-YYYY)</td>-->
<!--									<td>-->
<!--									<div id="withcal">-->
<!--									<html:text property="asonDate" styleId="asonDate" styleClass="text"  value="${userobject.businessdate}"  />-->
<!--									</div>-->
<!--									<div id="withOutcal" style="display: none">-->
<!--									<html:text property="asonDate" readonly="true" styleId="WasonDate" styleClass="text"  value=""  />-->
<!--									</div>-->
<!--									-->
<!--									</td>-->
<!--						-->
<!--								</tr>-->
								
						
								
								
								
								<tr>	
									<td ><button type="button" name=" mybutton" title="Alt+G" accesskey="G"  class="topformbutton3" onclick="chckValidForRepay();"  ><bean:message key="button.generate" /></button>
									</td>
								</tr>
						
				
				
						</table>
		
					</fieldset>
				</html:form>
			</div>
		</div>

	</body>
</html>