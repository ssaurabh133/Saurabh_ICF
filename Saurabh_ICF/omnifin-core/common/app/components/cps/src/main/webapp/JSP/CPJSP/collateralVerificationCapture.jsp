<%--
      Author Name-     Amit kumar
      Date of creation -13/09/2011
      Purpose-          Entry for collateral verification (capture)
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.login.roleManager.UserObject"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	UserObject userobj = (UserObject) session.getAttribute("userobject");
	String initiationDate = userobj.getBusinessdate();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />


 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		 
		 
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
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
<body onunload="closeAllWindowCallUnloadBody();" oncontextmenu="return false" onclick="parent_disable();" onload="enableAnchor();checkChanges('collateralForm');">
	<div id=centercolumn>
		<div id=revisedcontainer>
				<input type="hidden" name="contextPath" id="contextPath"
					value="<%=request.getContextPath()%>" />
					<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
				<logic:present name="collateralDetails">
				<fieldset>
				<table cellspacing=0 cellpadding=0 width="100%" border=0>
				  <tr>
				     <td class="gridtd">
				 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
				        <tr class="white2">
				          <td ><b><bean:message key="lbl.dealNo" /></b></td>
				          <td >${collateralHeader[0].dealNo}</td>
				          <td><b><bean:message key="lbl.date"/></b></td>
				          <td>${collateralHeader[0].dealDate}</td>
				          <td><b><bean:message key="lbl.customerName"/></b> </td>
				          <td colspan="3" >${collateralHeader[0].dealCustomerName}</td>
				         </tr>
				          <tr class="white2">
					          <td><b><bean:message key="lbl.productType"/></b></td>
					          <td >${collateralHeader[0].dealProductCat}</td>
					          <td ><b><bean:message key="lbl.product"/></b></td>
					          <td >${collateralHeader[0].dealProduct}</td>
					          <td ><b><bean:message key="lbl.scheme"/></b></td>
					          <td>${collateralHeader[0].dealScheme}</td>
					          <td><b><bean:message key="lbl.currentStage"/></b></td>
					          <td >COLLATERAL VERIFICATION CAPTURING</td>
				          </tr>
				        </table> 
				</td>
				</tr>
				</table>
				</fieldset>
				<html:form action="/collateralCapturing" styleId="collateralForm"
					method="post">					
					<fieldset>
						<legend>
							<bean:message key="lbl.collateralVerificationCapturingDetails" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="gridtd">
									<table width="100%" border="0" cellspacing="1" id="dtable"
										cellpadding="1">
										<tr class="white2">
											<td>
												<b><bean:message key="lbl.asscollTypes" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.collateralDescription" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.collateralValue" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.status" />
												</b>
											</td>

										</tr>
											<%
												int i = 1;
											%>
											<logic:iterate id="showCollateralDetails1"
												name="collateralDetails">
												<tr class="white1">
													<td>
														<a href="#" onclick="openPopUpCollateral('${showCollateralDetails1.collateralClass}',
															'${showCollateralDetails1.collateralId}',
															'${showCollateralDetails1.dealId}',
															'${showCollateralDetails1.verificationId}');">${showCollateralDetails1.collateralClass}</a>
															<input type="hidden" name="dealId" id="dealId<%=i%>" value="${showCollateralDetails1.dealId}" />
														<input type="hidden" name="verificationId" id="verificationId<%=i%>" value="${showCollateralDetails1.verificationId}
															" />
													</td>
													<td>
														${showCollateralDetails1.collateralDescription}
															
													</td>
													<td>
														${showCollateralDetails1.collateralValue}
															
													</td>
													<td>
														${showCollateralDetails1.collateralStatus}
															
													</td>
													<%
														i++;
													%>
												</tr>
											</logic:iterate>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<button type="button" name="forward" 
										id="forward" class="topformbutton2" title="Alt+F"
										accesskey="F"
										onclick="forwardCollateralCapturingData('${showCollateralDetails1.verificationId}');" ><bean:message key="button.forward" /></button>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>
				
				<!-- *********************** Collateral Details Completion ******************** -->
				
			<logic:present name="collateralCompletionDetails">
				<fieldset>
				<table cellspacing=0 cellpadding=0 width="100%" border=0>
				  <tr>
				     <td class="gridtd">
				 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
				        <tr class="white2">
				          <td ><b><bean:message key="lbl.dealNo" /></b></td>
				          <td >${collateralHeader[0].dealNo}</td>
				          <td><b><bean:message key="lbl.date"/></b></td>
				          <td>${collateralHeader[0].dealDate}</td>
				          <td><b><bean:message key="lbl.customerName"/></b> </td>
				          <td colspan="3" >${collateralHeader[0].dealCustomerName}</td>
				         </tr>
				          <tr class="white2">
					          <td><b><bean:message key="lbl.productType"/></b></td>
					          <td >${collateralHeader[0].dealProductCat}</td>
					          <td ><b><bean:message key="lbl.product"/></b></td>
					          <td >${collateralHeader[0].dealProduct}</td>
					          <td ><b><bean:message key="lbl.scheme"/></b></td>
					          <td>${collateralHeader[0].dealScheme}</td>
					          <td><b><bean:message key="lbl.currentStage"/></b></td>
					          <td >COLLATERAL VERIFICATION COMPLETION</td>
				          </tr>
				        </table> 
				</td>
				</tr>
				</table>
				</fieldset>
				<html:form action="/collateralCapturing" styleId="collateralForm"
					method="post">					
					<fieldset>
						<legend>
							<bean:message key="lbl.collateralVerificationCompletionDetails" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="gridtd">
									<table width="100%" border="0" cellspacing="1" id="dtable"
										cellpadding="1">
										<tr class="white2">
											<td>
												<b><bean:message key="lbl.asscollTypes" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.collateralDescription" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.collateralValue" />
												</b>
											</td>

										</tr>
											<logic:iterate id="showCollateralDetails1"
												name="collateralCompletionDetails">
												<tr class="white1">
													<td>
														<a href="#" onclick="openPopUpCollateralCompletion('${showCollateralDetails1.collateralClass}','${showCollateralDetails1.collateralId}','${showCollateralDetails1.dealId}','${showCollateralDetails1.verificationId}','F');">
															${showCollateralDetails1.collateralClass}</a>
														<input type="hidden" name="dealId" id="dealId" value="${showCollateralDetails1.dealId}" />
														<input type="hidden" name="verificationId" id="verificationId" value="${showCollateralDetails1.verificationId}" />
													</td>
													<td>
														${showCollateralDetails1.collateralDescription}
													</td>
													<td>
														${showCollateralDetails1.collateralValue}
													</td>
												</tr>
											</logic:iterate>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>
			
		<!-------------- For UnderWriter-------- -->	
			<logic:present name="listUnderWriter">
				<fieldset>
				<table cellspacing=0 cellpadding=0 width="100%" border=0>
				  <tr>
				     <td class="gridtd">
				 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
				        <tr class="white2">
				          <td ><b><bean:message key="lbl.dealNo" /></b></td>
				          <td >${collateralHeader[0].dealNo}</td>
				          <td><b><bean:message key="lbl.date"/></b></td>
				          <td>${collateralHeader[0].dealDate}</td>
				          <td><b><bean:message key="lbl.customerName"/></b> </td>
				          <td colspan="3" >${collateralHeader[0].dealCustomerName}</td>
				         </tr>
				          <tr class="white2">
					          <td><b><bean:message key="lbl.productType"/></b></td>
					          <td >${collateralHeader[0].dealProductCat}</td>
					          <td ><b><bean:message key="lbl.product"/></b></td>
					          <td >${collateralHeader[0].dealProduct}</td>
					          <td ><b><bean:message key="lbl.scheme"/></b></td>
					          <td>${collateralHeader[0].dealScheme}</td>
					          <td><b><bean:message key="lbl.currentStage"/></b></td>
					          <td >Collateral Verification Completion</td>
				          </tr>
				        </table> 
				</td>
				</tr>
				</table>
				</fieldset>
				<html:form action="/collateralCapturing" styleId="collateralForm"
					method="post">					
					<fieldset>
						<legend>
							<bean:message key="lbl.collateralVerificationCompletionDetails" />
						</legend>

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="gridtd">
									<table width="100%" border="0" cellspacing="1" id="dtable"
										cellpadding="1">
										<tr class="white2">
											<td>
												<b><bean:message key="lbl.asscollTypes" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.collateralDescription" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.collateralValue" />
												</b>
											</td>

										</tr>
										<logic:present name="listUnderWriter">
										  <logic:notEmpty name="listUnderWriter">
												<logic:iterate id="showCollateralDetails1"
													name="listUnderWriter">
													<tr class="white1">
														<td>
															<a href="#" onclick="openPopUpCollateralCompletion('${showCollateralDetails1.collateralClass}','${showCollateralDetails1.collateralId}',
																'${showCollateralDetails1.dealId}','${showCollateralDetails1.verificationId}','A');">
																${showCollateralDetails1.collateralClass}
															</a>
															<input type="hidden" name="dealId" id="dealId" value="${showCollateralDetails1.dealId}" />
															<input type="hidden" name="verificationId" id="verificationId" value="${showCollateralDetails1.verificationId}" />
														</td>
														<td>
															${showCollateralDetails1.collateralDescription}
														</td>
														<td>
															${showCollateralDetails1.collateralValue}
														</td>
													</tr>
												</logic:iterate>
												
											</logic:notEmpty>
											<logic:empty name="listUnderWriter">
												<script type="text/javascript">
			
			            							 alert('No Data Found');
			            							 self.close(); 
		                 
		   								      </script>
											</logic:empty>
										</logic:present>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>
			
		</div>
	</div>

	<logic:present name="sms">
		<script type="text/javascript">

if('<%=request.getAttribute("sms")%>'=='S')
	{
		alert('<bean:message key="lbl.assetSuccess" />');
		window.close();
		opener.document.location.reload(true);
		opener.window.close();
	}
	else if('<%=request.getAttribute("sms")%>' == 'E') 
	{
		alert('<bean:message key="lbl.assetError" />');
		window.close();
		opener.document.location.reload(true);
		opener.window.close();
	}
	else if('<%=request.getAttribute("sms")%>' == 'Not Equal') 
	{
		alert('<bean:message key="msg.saveCollateralBeforeFwd" />');
	}
	else if('<%=request.getAttribute("sms")%>' == 'FwdFailed') 
	{
		alert('<bean:message key="msg.fwdFailed" />');
		location.href="<%=request.getContextPath()%>/collateralVerificationCapturingSearch.do?method=initialcollateralVerificationCapturing";
	}
	// opener.document.location.reload(true);
	//window.close();
	//opener.document.location.reload(true);
	///alert("alert");
	//opener.window.location.href="collaterlInCMBehindAction.do";
	//opener.window.close();
</script>
	</logic:present>
<logic:present name="sms">
	<script type="text/javascript">
if('<%=request.getAttribute("sms")%>' == 'Fwd') 
{
	alert('<bean:message key="lbl.forwardSuccess" />');
	location.href="<%=request.getContextPath()%>/collateralVerificationCapturingSearch.do?method=initialcollateralVerificationCapturing";
}
</script>
</logic:present>

<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("collateralForm");
</script>

</body>
</html:html>


