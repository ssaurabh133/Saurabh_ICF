<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		<title></title>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/report/report.js"></script>
	</head>
	
	
	
	<body onload="enableAnchor();loadJSP()">
	<div id="centercolumn">	
			<div id="revisedcontainer">	
				<html:form action="/commonActionForReport" styleId="documentCollectionForm" >
					<fieldset>
						<legend><bean:message key="lbl.ReportParameterForm"/></legend>
						<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
						<html:hidden property="reportId" styleId="contextPath" value="Documents_Collection" />
<!--						<input type="hidden" name="reportId" id="reportId" value="Loan_1" />-->
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
							<tr>
								<td><bean:message key="lbl.branchname"/></td>
									<td><html:text property="branchid" styleClass="text" styleId="branchid" maxlength="100" readonly="false"
									onchange="openLOVCommon(12,'reportid','branchid','','', '','','','lbxBranchId','','','Y')"
									 value=""/>
										<html:hidden property="lbxBranchId" styleId="lbxBranchId" value="" />
										<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(12,'reportid','branchid','','', '','','','lbxBranchId')" value=" " styleClass="lovbutton"  >
										</html:button>
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
									<td><bean:message key="lbl.LoanFrom"/></td>
									<td><html:text property="fromLoan" styleClass="text" value="" styleId="fromLoan" readonly="false"
									onchange="openLOVCommon(521,'documentCollectionForm','fromLoan','lbxBranchId','', 'lbxBranchId','','documentCollectionDisableLoanLOV','abc','','','Y');"
									/>
										<html:hidden property="lbx_loan_from_id" styleId="lbx_loan_from_id" value="" />
										<input type="hidden" name="abc" id="abc"/>										
										<html:button property="loanFromButton" styleId="loanFromButton" tabindex="1" onclick="openLOVCommon(521,'documentCollectionForm','fromLoan','lbxBranchId','', 'lbxBranchId','','documentCollectionDisableLoanLOV','abc');" value=" " styleClass="lovbutton" >
										</html:button>
									</td>
									<td><bean:message key="lbl.LoanTo"/></td>
									<td><html:text property="toLoan" styleClass="text" value="" styleId="toLoan" readonly="false"
									onchange="openLOVCommon(523,'documentCollectionForm','toLoan','lbxBranchId','','lbxBranchId','','documentCollectionDisableLoanLOV','abc1','','','Y');"
									/>
										<html:hidden property="lbx_loan_to_id" styleId="lbx_loan_to_id" value="" />
										<input type="hidden" name="abc1" id="abc1"/>										
										<html:button property="loanToButton" styleId="loanToButton" tabindex="1" onclick="openLOVCommon(523,'documentCollectionForm','toLoan','lbxBranchId','','lbxBranchId','','documentCollectionDisableLoanLOV','abc1');" value=" " styleClass="lovbutton" >
								</html:button>
								</td>			
										</tr>
										 <tr>
										<td><bean:message key="lbl.dealFrom"/></td>
									 	<td><html:text styleClass="text" property="fromDeal" styleId="fromDeal" maxlength="20"  value="" readonly="false"
									 	onchange="openLOVCommon(522,'documentCollectionForm','fromDeal','lbxBranchId','', 'lbxBranchId','','documentCollectionDisabledDealLOV','abc','','','Y')"
									 	 tabindex="-1"/>
             								<input type="hidden" name="abc" id="abc"/>		
             								<html:button property="dealNoFromSButton" styleId="dealNoFromSButton" onclick="openLOVCommon(522,'documentCollectionForm','fromDeal','lbxBranchId','', 'lbxBranchId','','documentCollectionDisabledDealLOV','abc')" value=" " styleClass="lovbutton"></html:button>
             								<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
            							</td>
										<td><bean:message key="lbl.dealTo"/></td>
										<td><html:text styleClass="text" property="toDeal" styleId="toDeal" maxlength="20"  value="" readonly="false"
										onchange="openLOVCommon(524,'documentCollectionForm','toDeal','lbxBranchId','', 'lbxBranchId','','documentCollectionDisabledDealLOV','abc1','','','Y')"
										 tabindex="-1"/>
             								<input type="hidden" name="abc1" id="abc1"/>		
             								<html:button property="dealNoToSButton" styleId="dealNoToSButton" onclick="openLOVCommon(524,'documentCollectionForm','toDeal','lbxBranchId','', 'lbxBranchId','','documentCollectionDisabledDealLOV','abc1')" value=" " styleClass="lovbutton"></html:button>
             								<html:hidden  property="lbxDealTo" styleId="lbxDealTo" value="" />
            							</td>
										</tr>		
										<tr>
										<td><bean:message key="lbl.Stage"/> </td>
										<td><html:select property="stage" styleId="stage"  onchange="vaildateStage();" styleClass="text" >
											<html:option value="">--Select--</html:option>
											<html:option value="PRD">PRD</html:option>
											<html:option value="PRS">PRS</html:option>
											<html:option value="POD">POD</html:option>
										    </html:select>
											</td>  
											<td><bean:message key="lbl.Status"/> </td>
										<td><html:select property="status" styleId="status" styleClass="text" >
											<html:option value="ALL">All</html:option>
											<html:option value="R">Received</html:option>
											<html:option value="D">Deffered</html:option>
											<html:option value="P">Pending</html:option>
											<html:option value="W">Waived</html:option>
											</html:select>
											</td>  
								 	 </tr>
								</table>
								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr>
										<td >
											<button type="button" name=" mybutton" title="Alt+G" accesskey="G"   class="topformbutton3" onclick="documentCollection();"  ><bean:message key="button.generate" /></button>
								  			<button type="button" name=" mybutton1" title="Alt+R" accesskey="R"  class="topformbutton2" onclick="documentCollectionReset();"  ><bean:message key="button.reset" /></button>
								  		</td>									
									</tr>
								</table>
								
						
					</fieldset>
				</html:form>
			</div>
		</div>
	</body>
 </html>