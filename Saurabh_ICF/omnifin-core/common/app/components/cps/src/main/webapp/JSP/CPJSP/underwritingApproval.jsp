<!--  
Author Name:- Amit Kumar
Date of Creation:- 13/05/2011
Purpose:-  
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />

	    <link type="text/css" href="<%=request.getContextPath() %>/css/toolTip.css" rel="stylesheet" />
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/manualAdviceMaker.js"></script>
	  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
   	  	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		
<style type="text/css">
		textarea {
/*border:1px solid #9BB168;*/
color:#000;
font-family:arial,serif;
font-size:13px;
padding-left:2px;
width:620px;
resize:none;
height:40px;
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
<body oncontextmenu="return false;" onload="enableAnchor();checkChanges('approvalForm');">

<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
	<logic:notPresent name="maker">
	<logic:notPresent name="strFlagDV">
		<div id="centercolumn">

			<div id="revisedcontainer">
				<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Under Writer</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
				<html:form action="/underwritingApproval" styleId="approvalForm"
					method="post">
					<html:hidden property="contextPath" styleId="contextPath"
						value="<%=request.getContextPath()%>" />
				
				<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
				<html:hidden property="checkContactCCount" styleId="checkContactCCount" 	value="${checkContactCCount}" />
				<html:hidden property="checkTradeCCount" styleId="checkTradeCCount" 	value="${checkTradeCCount}" />
				<html:hidden property="checkCollateralCCount" styleId="checkCollateralCCount" 	value="${checkCollateralCCount}" />
				<%--  START BY PRASHANT --%>
				<html:hidden property="checkDeviationCount" styleId="checkDeviationCount" 	value="${checkDeviationCount}" />
				<html:hidden property="leadStatus" styleId="leadStatus" value="${leadStatus}" />
				
				<html:hidden property="expoLimit" styleId="expoLimit" 	value="${expoLimit}" />
				<input type="hidden" name="businessdate" id="businessdate" value='${userobject.businessdate}'/>
				<html:hidden property="sactionValidDate" styleId="sactionValidDate" 	value="${sactionValidDate}" />
                <html:hidden property="specialCount" styleId="specialCount" 	value="${specialCount}" />
                <html:hidden property="disbursalCount" styleId="disbursalCount" 	value="${disbursalCount}" />
                <html:hidden property="countRepayType" styleId="countRepayType" 	value="${countRepayType}" />
                <html:hidden property="termSheetCount" styleId="termSheetCount" 	value="${termSheetCount}" />
                <html:hidden property="imdFlag" styleId="imdFlag" 	value="${imdFlag}" />
                  <html:hidden property="deviationRecStatus" styleId="deviationRecStatus" 	value="${recStatus}" />
				<%--  END BY PRASHANT --%>


					<fieldset>

						<legend>
							<bean:message key="lbl.approval" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
										<logic:equal name="functionId" value="3000296">
											<td width="20%">
												<bean:message key="lbl.decision" />
											</td>
											
											<td width="35%">
											<logic:present name="approve" >
												<html:select property="decision" styleId="decision"
													styleClass="text" onchange="stageReverse();">
													<html:option value="A">
															Approved
														</html:option>
													<html:option value="X">
															Rejected
														</html:option>
													<html:option value="P">
															Send Back
														</html:option>

												</html:select>
												</logic:present>
												<logic:present name="recommend" >
												<html:select property="decision" styleId="decision"
													styleClass="text" onchange="stageReverse();">
													<html:option value="A">
															Recommended
														</html:option>
													<html:option value="X">
															Rejected
														</html:option>
													<html:option value="P">
															Send Back
														</html:option>

												</html:select>
												</logic:present>
											</td>
											</logic:equal>
											<logic:notEqual name="functionId" value="3000296">
											<td width="20%">
												<bean:message key="lbl.decision" />
											</td>
											
											<td width="35%">
											<logic:present name="approve" >
												<html:select property="decision" styleId="decision"
													styleClass="text" onchange="stageReverse();">
													<html:option value="A">
															Approved
														</html:option>
													<html:option value="X">
															Rejected
														</html:option>
													<html:option value="P">
															Send Back
														</html:option>

												</html:select>
												</logic:present>
												 <logic:present name="recommend" >
												<html:select property="decision" styleId="decision"
													styleClass="text" onchange="stageReverse();">
													<html:option value="A">
															Approved
														</html:option>
													<html:option value="X">
															Rejected
														</html:option>
													<html:option value="P">
															Send Back
														</html:option>

												</html:select>
												</logic:present> 
											</td>
							</logic:notEqual>		
												<logic:equal name="functionId" value="3000296">							
									
													<td >
														<!--  <div id="reversingStageId" style="display: none;">-->
															<bean:message key="lbl.reversingStage" />
															<font color="red">*</font>
														<!-- </div> -->
													</td>
													<td >
													  <!-- 	<div id="reversingSelectId" style="display: none;"> -->
														<html:select property="reversingStage" styleId="reversingStage"
														 disabled="true"	styleClass="text" onchange="showCheckBox();">
														 <html:option value="">---SELECT---</html:option>
															<logic:present name="workFlowStage">
															<logic:notEmpty name="workFlowStage">
															<html:optionsCollection name="workFlowStage" value="id" label="name"/>
															</logic:notEmpty>
															</logic:present>
															
																
														</html:select>
													<!--</div> -->
													</td>
																										</logic:equal>
												
											
											
										</tr>
										<tr>
										
											            <td >
														<!--	<div id="reprocessingFlagId" style="display: none;"> -->
															<bean:message key="lbl.reprocessingFlag" />
															<font color="red">*</font>
													<!--	</div> -->
													</td>
													<td>
												<!--	<div id="reprocessingFlagCheckboxId" style="display: none;">  -->
												<html:select property="rpStageFlag" styleId="rpStageFlag"
														 disabled="true"	styleClass="text" >
														 
														  <html:option value="N">
																	NO
															</html:option>
															<html:option value="Y">
																	YES
																</html:option>
															
															
																
														</html:select>
														
												 <!--  </div>	-->												
													</td>
													
																		
													
													</tr>
													
													<tr>
															<td width="20%">
												<bean:message key="lbl.authorRemarks" />
												<font color="red">*</font>
											</td>
													<td colspan="5">


												<a rel="tooltip3" id="tool">

												
												   <textarea name="remarks" id="remarks"  maxlength="2000" onmouseover="applyToolTip(id);"></textarea>
												
												</a>
											
											</td>
													
													</tr>
											<tr>
											<td width="20%" >
												<bean:message key="lbl.reasons" /><font id="rejectDeal" style="display: none;" color="red">*</font>
												
											</td>
											<td width="35%" colspan="3">


												<html:text property="reasonDesc" styleClass="text" styleId="reasonDesc" maxlength="100" readonly="true" tabindex="-1" value=""/>
												<html:hidden  property="lbxReason" styleId="lbxReason" value=""/>
				                                <input type="hidden" name="abc" id="abc"/>
												<html:button property="reasonButton" disabled="true" styleId="reasonButton" onclick="openLOVCommon(380,'approvalForm','reasonDesc','','', '','','','abc')" value=" " styleClass="lovbutton"> </html:button>
							         	</td>
										</tr>
										<tr>
											<td align="left" class="form2" colspan="4">
												<div align="left">
												<logic:equal name="functionId" value="3000296">
													<button type="button" name="saveApprove" id="saveApprove"
														class="topformbutton2" title="Alt+V" accesskey="V"
														onclick="saveApprovalData();"><bean:message key="button.save" /></button>
														</logic:equal>
												<logic:equal name="functionId" value="500000123">
													<button type="button" name="saveApprove" id="saveApprove"
														class="topformbutton2" title="Alt+V" accesskey="V"
														onclick="saveColandingApprovalData();"><bean:message key="button.save" /></button>
												</logic:equal>
												</div>
											</td>
										</tr>
									</table>

								</td>
							</tr>

						</table>

					</fieldset>

				</html:form>
			</div>
			</logic:notPresent>
<logic:notEqual name="functionId" value="500000123">
			<fieldset>
				<legend>
					<bean:message key="lbl.details" />
				</legend>


				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="gridtd">

							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr class="white2">
									<td width="220">
										<b><bean:message key="lbl.creditAnalyst" />
										</b>
									</td>
									<%-- <td width="220" >
										<b><bean:message key="lbl.userId"/></b>
									</td>
									--%>
									<td width="220" >
										<b><bean:message key="lbl.approvalLevelID"/></b>
									</td>
									
									<td width="220">
										<b><bean:message key="lbl.decision" />
										</b>
									</td>
									<td width="220">
										<strong><bean:message key="lbl.authorRemarks" /> </strong>
									</td>
									<td width="220">
										<strong><bean:message key="lbl.actionDate" /> </strong>
									</td>
									<td width="220">
										<strong><bean:message key="lbl.reasons" /> </strong>
									</td>
								</tr>
								<logic:present name="creditApprovalList">
									<logic:iterate name="creditApprovalList"
										id="subCreditApprovalList">
										<tr>
											<td class="white1">${subCreditApprovalList.userName}</td>
											<td class="white1">${subCreditApprovalList.approvalLevel}</td>
											<td class="white1">${subCreditApprovalList.decision}</td>
											<td class="white1">${subCreditApprovalList.remarks}</td>
										   	<td class="white1">${subCreditApprovalList.makerDate}</td>
										   	<td class="white1">${subCreditApprovalList.reasonDesc}</td>
										</tr>
									</logic:iterate>
								</logic:present>
							</table>
						</td>
					</tr>
				</table>

			</fieldset>
</logic:notEqual>

		</div>

		<logic:present name="sms">
		

	
			<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.approveUnder" />");
	    parent.location='<%=request.getContextPath()%>/commondeal.do?method=searchDealCapturing&stage=F';
 
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.rejectUnder" />");
		parent.location='<%=request.getContextPath()%>/commondeal.do?method=searchDealCapturing&stage=F';
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Z')
	{
		alert("<bean:message key="lbl.FacilityPending" />");
		
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='ZZ')
	{
		alert("<bean:message key="lbl.FacilityMissing" />");
	
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='Q')
	{
		alert("<bean:message key="lbl.FacilityAmtPending" />");
		
		
	}
	else if('<%=request.getAttribute("sms")%>'=='P')
	{
		alert("<bean:message key="lbl.revertUnder" />");
		parent.location='<%=request.getContextPath()%>/commondeal.do?method=searchDealCapturing&stage=F';
		
	}
	else if('<%=request.getAttribute("sms")%>'=='P')
	{
		alert("some deviation are rejected,please sendback or reject this case");
		parent.location='<%=request.getContextPath()%>/commondeal.do?method=searchDealCapturing&stage=F';
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='X')
	{
		alert('${statusProc }');
		//parent.location='<%=request.getContextPath()%>/commondeal.do?method=searchDealCapturing&stage=F';
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='U')
	{
		alert("<bean:message key="lbl.requirmentUnder" />");
		//document.forms[0].action="<%=request.getContextPath()%>/underWriting.do?method=getUnderWriterDataFrameset&dealId=<%=session.getAttribute("dealId")%>";
	   // document.forms[0].submit();
	}
	</script>
		</logic:present>
	</logic:notPresent>
	<logic:present name="maker">
		<html:form action="/underwritingApproval" styleId="approvalForm"
			method="post">
			<fieldset>

				<legend>
					<bean:message key="lbl.authorRemarks" />
				</legend>


				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>

							<table width="100%" border="0" cellspacing="1" cellpadding="1">

								<tr>
									<%--		<td width="20%">
												<bean:message key="lbl.decision"/>
											</td>
											<td width="35%">
										 	<html:select property="decision"
														styleId="moduleId" value="${creditApprovalList[0].decision }" disabled="true" styleClass="text">
														<html:option value="A">
															Approved
														</html:option>
														<html:option value="P">
															Revert
														</html:option>
														<html:option value="X">
															Rejected
														</html:option>
													</html:select>
												
											</td>
--%>
									<td width="20%">
										<bean:message key="lbl.remarks" />
									</td>
									<td width="35%">

											      <a rel="tooltip3" id="tool">

												
												   <textarea name="remarks" disabled="disabled" id="remarks"  maxlength="2000" onmouseover="applyToolTip(id);">${creditApprovalList[0].remarks }</textarea>
												
												
												</a>
												
										


									</td>
								</tr>

							</table>

						</td>
					</tr>

				</table>



			</fieldset>
		</html:form>
	</logic:present>
	<script type="text/javascript">
		setFramevalues('approvalForm');
	</script>
</body>
</html:html>