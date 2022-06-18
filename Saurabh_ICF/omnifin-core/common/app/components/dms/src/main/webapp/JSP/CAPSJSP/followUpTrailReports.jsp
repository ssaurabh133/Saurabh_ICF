<!--Author Name : Manish Shukla-->
<!--Date of Creation : 31 Aug 2013 -->
<!--Purpose  : Reports (FollowpTrail Reports)-->
<!--Documentation : -->

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
			src="<%=request.getContextPath()%>/js/capsScript/followUpTrailReports.js"></script>
		<script type="text/javascript"
			src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	</head>

	<body onload="enableAnchor();">
		<html:form action="/followUpTrailDispatch" styleId="followUpTrailReportForm">
		<fieldset>
			<legend><bean:message key="lbl.followUpTrail"/></legend>
			<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
			<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
			<input type="hidden" name="format" id="format" value="F" />
			<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
				<td><bean:message key="lbl.reportformat"/></td>
				<td>
					<html:select property="reportformat" styleClass="text" styleId="reportformat">
						<html:option value="P">PDF</html:option>
						<html:option value="E">EXCEL</html:option>
						<html:option value="H">HTML</html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><bean:message key="lbl.loanAccountNo" /></td>
				<td>
					<html:text styleClass="text" property="loanno" styleId="loanno" readonly="true" tabindex="-1"/>
					<html:button property="loanSearchButton" styleId="loanSearchButton" onclick="openLOVCommon(557,'FollowUpTrailForm','loanno','','', '','','','customerName');"	value=" " styleClass="lovbutton"></html:button>
					<html:hidden property="lbxLoanno" styleId="lbxLoanno" />
					<html:hidden property="customerName" styleId="customerName" />
				</td>
				<td><bean:message key="lbl.actionTaken"/></td>
				<td>
					<html:text property="user" styleClass="text" styleId="user" readonly="true" tabindex="-1"/>
					<html:button property="userButton" styleId="userButton" value=" " styleClass="lovbutton" onclick="openLOVCommon(556,'FollowUpTrailForm','user','','', '','','','actionTaken')"></html:button>
					<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
					<html:hidden property="lbxUserSearchId" styleId="user" />
					<html:hidden property="actionTaken" styleId="actionTaken" />
				</td>
			</tr>
		  	<tr>
				<td>
					<bean:message key="lbl.datefrom" />
					<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
					<span><font color="red">*</font>
									</span>
				</td>
				<td><html:text property="fromdate" tabindex="8" styleClass="text" styleId="fromdate" onchange="return checkDate('assignfrom');" /></td>
				<td>
					<bean:message key="lbl.dateto" />
					<bean:message key="lbl.dateFormat(dd-mm-yyyy)" />
					<span><font color="red">*</font>
									</span>
				</td>
				<td><html:text property="todate" tabindex="8" styleClass="text" styleId="todate" onchange="return checkDate('assignto');" /></td>
			</tr> 
			 <tr>
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
								<select id="branchid"  name="branchid" size="5" multiple="multiple"  value="" style="width: 150px"></select>
									<html:hidden property="lbxBranchId" styleId="lbxBranchId"
										value="" />
									<html:button property="dealButton" styleId="dealButton"
										tabindex="1"
										onclick="return openMultiSelectLOVCommon(2075,'reportid','branchid','','', '','','','lbxBranchId');"
										value=" " styleClass="lovbutton" >
									</html:button>
								</td>
								
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
			<tr>
				<td>
						<!--  <input type="button" name=" mybutton" value="Show" styleId="show"
							class="topformbutton2" onclick="javaScript:save('<bean:message key="lbl.selectAtLeast"/>');" />
						-->								
					   <button type="button" class="topformbutton2" name="mybutton" id="show" onclick="save();"  
 								title="Alt+W" accesskey="W"   ><bean:message key="button.show" /></button>
				</td>
			</tr>
		</table>
	</fieldset>
	</html:form>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
	</body>
</html>