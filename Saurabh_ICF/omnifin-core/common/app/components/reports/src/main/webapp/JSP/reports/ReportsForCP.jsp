<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.login.roleManager.UserObject"%>

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
		 <%
	
			UserObject userobj = (UserObject) session.getAttribute("userobject");
			
			String userId = userobj.getUserId();
			session.setAttribute("userId",userId);
	%>
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
<html:form action="/CPReportsAction" styleId="reportForm"  >
<fieldset><legend><bean:message key="lbl.ReportParameterForm"/></legend>  
	<input type="hidden" name="dateRengeLimit" id="dateRengeLimit" value= "${dateRengeLimit}">
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="" />
	<html:hidden property="userID" styleId="userID"  value="${sessionScope.userobject.userId }" />
	
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<td><bean:message key="lbl.ReportType"/><span><font color="red">*</font></span></td>
		<td>
			<html:select property="reportName" styleId="reportName" onchange="disableReport()" styleClass="text">
			<option value="select">--	<bean:message key="lbl.select" />--</option>			
			<logic:present name="reportlist">
   		   		<logic:notEmpty  name="reportlist">
   		   			<html:optionsCollection name="reportlist" label="reportNameId" value="reportName" />
    			</logic:notEmpty>
    	 	</logic:present>
       	  	</html:select>
		</td>
		<td id="rf"><bean:message key="lbl.ReportFormat"/><span><font color="red">*</font></span></td>
		<td id="rfv"> 
			<html:select  property="reportformat" styleClass="text"  styleId="reportformat" >
			<logic:present name="list">
   			<logic:notEmpty  name="list">
      			<html:optionsCollection name="list" label="reportformatid" value="reportformat" />
     		</logic:notEmpty>
      		</logic:present>
            </html:select>
      	</td>						
      	
		<td id="lvl" style="display: none;"><bean:message key="lbl.level"/><span><font color="red">*</font></span></td>
		<td id="lvl2" style="display: none;">
		<html:select property="level" styleClass="text" styleId="level" >
			<html:option value="">--Select--</html:option>
			<html:option value="STAGE">STAGE</html:option>
			<html:option value="DEAL">DEAL</html:option> 						
		</html:select> 
   	</td>					
	</tr>	
	<%-- <tr id="branchRow">
		<td><bean:message key="lbl.branch"/><span><font color="red">*</font></span></td>
		<td>
			<html:select property="branch" styleClass="text" onchange="hideLovofBranch()" styleId="branch" >
			<html:option value="Specific">Specific</html:option>
			<html:option value="All">All</html:option> 						
			</html:select> 
		</td>						
		<td id="r10"><bean:message key="lbl.branchname"/><font color="red">*</font></td>
		<td id="r11" style="display: none;"><bean:message key="lbl.branchname"/></td>
		<td>
			<html:text property="branchid" styleClass="text" styleId="branchid" maxlength="100" readonly="true"/>
			<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(12,'reportForm','branchid','','', '','','','lbxBranchId')" value=" " styleClass="lovbutton"  ></html:button>
		</td>							
	</tr>			
	 --%>
	 <tr id="productCat">
						<td id="productCat1">
									<bean:message key="lbl.productCategory" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td id="productCat2">
									<html:select property="producTCategory" styleId="producTCategory" 
										onchange="disableCal(this.value)" styleClass="text" value="select">
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
								<td >
									<bean:message key="lbl.branch" />
									<html:hidden property="contextPath" styleId="contextPath"
										value="<%=request.getContextPath() %>" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td >
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
							
	 <tr id=dealnStatus>
		<td id="deal">Deal Number<span><font color="red">*</font></span></td>
		<td id="lv">
		<html:select property="loanno" styleClass="text" onchange="hideLov()" styleId="loanno">
				<html:option value="R">Range</html:option>
				<html:option value="A">All</html:option>						
		</html:select> 
		</td>
		<td id="st"><bean:message key="lbl.active"/><span><font color="red">*</font></span></td>
		<td id="stDrop">
			<div id="s1">
				<html:select property="status1" styleClass="text" styleId="status1" onchange="hideAsterik(document.getElementById('status1').value)">
						<html:option value="All">All</html:option>
						<html:option value="A">Approved</html:option>
						<html:option value="X">Rejected</html:option>
						<html:option value="P">Pending</html:option>							
				</html:select>
			</div>
			<div id="s2"  style="display:none">
				<html:select property="status2" styleClass="text">
						<html:option value="All">All</html:option>
						<html:option value="A">Approved</html:option>
						<html:option value="X">Deleted</html:option>
						<html:option value="P">Pending</html:option>							
				</html:select>
			</div>  
		</td>
	</tr>
	<tr id="dealRange">
		<td id="d10"><bean:message key="lbl.dealFrom"/><span><font color="red">*</font></span></td>
		<td id="d11" style="display: none;"><bean:message key="lbl.dealFrom"/></td>
		<td>
			<html:text styleClass="text" property="fromDeal" styleId="fromDeal" maxlength="20"  value=""  tabindex="-1"/>
            <input type="hidden" name="abc" id="abc"/>		
            <html:button property="dealNoFromSButton" styleId="dealNoFromSButton" onclick="openLOVCommon(172,'reportForm','fromDeal','','', '','','','abc')" value=" " styleClass="lovbutton"></html:button>
            <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
        </td>
        <td id="d20"><bean:message key="lbl.dealTo"/><span><font color="red">*</font></span></td>
		<td id="d21" style="display: none;"><bean:message key="lbl.dealTo"/></td>
		<td>
			<html:text styleClass="text" property="toDeal" styleId="toDeal" maxlength="20"  value=""  tabindex="-1"/>
           	<input type="hidden" name="abc1" id="abc1"/>		
           	<html:button property="dealNoToSButton" styleId="dealNoToSButton" onclick="openLOVCommon(241,'reportForm','toDeal','','', '','','','abc1')" value=" " styleClass="lovbutton"></html:button>
           	<html:hidden  property="lbxDealTo" styleId="lbxDealTo" value="" />
        </td>
	</tr>
	<tr id="branchRMRow" style="display: none;">
		<td><bean:message key="lbl.branch" /></td>
		<td>
			<html:text property="branchDet" styleClass="text" styleId="branchDet" maxlength="10" readonly="true" value="" tabindex="-1" />
			<input type="button" class="lovbutton" id="branchButton" onclick="openLOVCommon(156,'reportForm','leadBranchId','','rmAllo','lbxUserId','','','branchDet')" value=" "  name="dealButton">
			<input type="hidden" name="leadBranchId" id="leadBranchId" value=""/>
		</td>
		<td><bean:message key="lbl.leadgenerator" /></td>
		<td>
			<html:text property="rmAllo" styleClass="text" styleId="rmAllo"	maxlength="10" readonly="true" value="" tabindex="-1"/>
			<input type="button" class="lovbutton" id="leadButton" onclick="openLOVCommon(384,'reportForm','lbxUserId','userID','','userID','','','rmAllo')" value=" "  name="dealButton">
			<input type="hidden" name="lbxUserId" id="lbxUserId" value="" />
		</td>
	</tr>
	<tr id="dateRow">
		<td id="dateF1" style="display: none;"><bean:message key="lbl.FromDate"/><span id="hideAsterik" style="display:none;"><font color="red">*</font></span></td>
		<td id="dateF2" ><bean:message key="lbl.FromDate"/></td>
		<td>
			<div id="dateFromD">
				<html:text property="fromDate"  styleId="fromDate" styleClass="text" value="" maxlength="10" onchange="checkDate('fromDate');validateLeadDate();"/>
			</div>
			<div id="withOutdateFromD" style="display: none">
				<html:text property="fromDate" styleId="notfromDate" styleClass="text" readonly="true" value="" maxlength="10" tabindex="-1"/> 
			</div>
		</td>									
		<td id="dateT1" style="display: none;"><bean:message key="lbl.ToDate"/><span><font color="red">*</font></span></td>
		<td id="dateT2" ><bean:message key="lbl.ToDate"/></td>
		<td>
			<div id="dateToD">
				<html:text property="toDate"  styleId="toDate" styleClass="text" value="" maxlength="10" onchange="checkDate('toDate');validateLeadDate();"/>
			</div>
			<div id="withOutdateToD" style="display: none">
				<html:text property="toDate" styleId="nottoDate" styleClass="text" value="" readonly="true" maxlength="10" tabindex="-1"/> 
			</div>
		</td>					
	</tr>				  						
	<tr id="salesDeal" style="display: none">
		<td ><bean:message key="lbl.salesDealId"/><span><font color="red">*</font></span></td>
		<td>
			<html:text styleClass="text" property="salesDealId" styleId="salesDealId" maxlength="20"  value="" readonly="true" tabindex="-1"/>
    		<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
    		<input type="hidden" name="abc" id="abc"/>
    		<html:button property="salesDealIdButton" styleId="salesDealIdButton" onclick="changeLov()" value=" " styleClass="lovbutton"></html:button>             								
    	</td>
	</tr>		  						
	<tr id="specific_deal" style="display: none">
		<td ><bean:message key="lbl.dealNo"/><span><font color="red">*</font></span></td>
		<td>
			<html:text styleClass="text" property="specificDealNo" styleId="specificDealNo" maxlength="20"  value=""  tabindex="-1"/>
    		<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
    		<input type="hidden" name="abc" id="abc"/>
    		<html:button property="dealNoFromSButton" styleId="dealNoFromSButton" onclick="openDealLov()" value=" " styleClass="lovbutton"></html:button>             								
    	</td>
	</tr>
	<tr id="specific_lead" style="display: none">
		<td ><bean:message key="lbl.leadNo"/></td>
		<td>
			<html:text styleClass="text" property="specificLeadNo" styleId="specificLeadNo" maxlength="20"  value="" readonly="true" tabindex="-1"/>
    		<html:hidden  property="lbxLeadNo" styleId="lbxLeadNo" value="" />
    		<input type="hidden" name="abc" id="abc"/>
    		<html:button property="dealNoFromSButton" styleId="dealNoFromSButton" onclick="openLeadLOV()" value=" " styleClass="lovbutton"></html:button>             								
    	</td>
	</tr>
	<tr id="repay_calc" style="display: none">
		<td ><bean:message key="lbl.dealNo"/><span><font color="red">*</font></span></td>
		<td>
			<html:text styleClass="text" property="repayDealNo" styleId="repayDealNo" maxlength="20"  value=""  tabindex="-1"/>
    		<html:hidden property="lbxDealNo" styleId="lbxDealNo" value="" />
    		<input type="hidden" name="abc" id="abc"/>
    		<html:button property="dealNoFromSButton" styleId="dealNoFromSButton" onclick="openDealLov()" value=" " styleClass="lovbutton"></html:button>             								
    	</td>
	</tr>
	<tr id="mulProduct">
		<td><bean:message key="lbl.productDesc"/></td>
		<td> 
			<select id="productDescs"  name="productDescs" size="5" multiple="multiple"  style="width: 150px"></select>
            <html:button property="consButton" styleId="consButton" onclick="return openMultiSelectLOVCommon(2025,'reportid','productDescs','','', '','','','productIds');" value=" " styleClass="lovbutton"></html:button>
            <html:hidden property="productIds" styleId="productIds" value=""/>	      
        </td>
	</tr>
		<!-- shubham changes for Password Protected Starts -->
					 <tr id=pdf style="display: none">
								<td><bean:message key="lbl.passwordPdf"/><span><font color="red">*</font></span></td>
								<td><html:select property="passwordPdf" styleClass="text"  styleId="passwordPdf">
				<html:option value="N">No</html:option>
				<html:option value="Y">Yes</html:option>						
		</html:select></td>
								
							    </tr> 
					<!-- shubham changes for Password Protected end -->
	
	<tr>	
		<td ><button type="button" name=" mybutton"  title="Alt+G" accesskey="G" class="topformbutton3" onclick="submitReport();"  ><bean:message key="button.generate" /></button>
		</td>
	</tr>
	</table>
</fieldset>
</html:form>
</body>
</html>