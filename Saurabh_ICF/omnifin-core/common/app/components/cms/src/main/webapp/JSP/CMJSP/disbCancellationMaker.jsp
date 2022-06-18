<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html:html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	UserObject userobj = (UserObject) session
			.getAttribute("userobject");
	String initiationDate = userobj.getBusinessdate();
%>



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
	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/cmScript/disbursalCancellation.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<script type="text/javascript">
	function fntab() {
		if (document.getElementById('modifyRecord').value == 'N') {
			document.getElementById('cancellationForm').loanAcButton.focus();
		} else if (document.getElementById('modifyRecord').value == 'CM') {
			document.getElementById('cancellationForm').reasonForClosure
					.focus();
		}
		return true;
	}
	  
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

<body oncontextmenu="return false" onload="enableAnchor();checkChanges('cancellationForm');fntab();init_fields();"
	onclick="parent_disable();"
onunload="closeAllLovCallUnloadBody();">

	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />	
		<input type="hidden" name="businessDate" id="businessDate"
		value="<%=initiationDate %>" />				
		<input type="hidden" id="checkSaveFlag" value="Y" />
		<input type="hidden" id="checkGetDetailFlag" value="Y" />
		<input type="hidden" id="checkDataSavedFlag" value=<%=request.getAttribute("checkDataSavedFlag")%> />
	<div id="centercolumn">
		<div id="revisedcontainer">
         <input type="hidden" id="modifyRecord" name="modifyRecord" value="N" />
				<html:form action="/disbursalCancellation" styleId="cancellationForm"
					method="post">
			<!-- ********************************************* For New Cancellation **************************************************** -->

			<logic:present name="newDisCancellationMaker">
				<input type="hidden" id="billFlagYesOrNo" value="" />					
					
					<fieldset>
						<LEGEND>
							<bean:message key="lbl.disbCancellationMaker" />
						</LEGEND>

						<table width="100%" border="0" cellspacing="1" cellpadding="1">							
							<tr>

								<td>
									<bean:message key="lbl.loanAc" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:text styleClass="text" property="loanAc" styleId="loanAc"
										value="" readonly="true" />
									<html:button
										onclick="openLOVCommon(485,'cancellationForm','loanAc','','', '','','generateDisbValuesCancellation','customerName');closeLovCallonLov1();saveFlag();getDetailFlag()"
										property="loanAcButton" styleId="loanAcButton" value=" "
										styleClass="lovButton" />
									<input type="hidden" name="contextPath"
										value="<%=request.getContextPath()%>" id="contextPath" />
									<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
										value="" />
								</td>

								<td>
									<bean:message key="lbl.customerName" />
								</td>
								<td>
									<html:text styleClass="text" styleId="customerName"
										tabindex="-1" maxlength="50" disabled="true"
										property="customerName" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.loanDate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanDate" maxlength="10"
										disabled="true" property="loanDate" tabindex="-1" />
								</td>
								<td>
									<bean:message key="lbl.loanAmt" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanAmt" maxlength="18"
										disabled="true" property="loanAmt" tabindex="-1" 
										style="text-align:right;"/>
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.product" />
								</td>
								<td>
									<html:text styleClass="text" styleId="product" disabled="true"
										maxlength="50" property="product" tabindex="-1" />
								</td>
								<td>
									<bean:message key="lbl.scheme" />
								</td>
								<td>
									<html:text styleClass="text" styleId="scheme" disabled="true"
										maxlength="50" property="scheme" tabindex="-1" />
								</td>
							</tr>	
							

								

							<tr>								

								<td nowrap="nowrap">
									<bean:message key="lbl.authorRemarks" />
								</td>
								<td nowrap="nowrap">
									<html:textarea styleClass="text" property="authorRemarks"
										disabled="true" value="${requestScope.authorRemarks}" />
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<button type="button" name="getDisbDtl" id="getDisbDtl" 
										class="topformbutton4" accesskey="V" title="Alt+G"
										onclick="return getDisbursalDetails();saveFlag();" ><bean:message key="button.getDisbDtl" /></button>
									</td>
									</tr>
					</table>					
				</FIELDSET>
<!-- Grid Code -->

<fieldset>
					<legend>
						<bean:message key="lbl.disbSummary" />
					</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="gridtd">

								<table id="table1" width="100%" border="0" cellspacing="1"
									cellpadding="4">									
										<tr class="white2">
											<td align="center">
												<b><bean:message key="lbl.select" /> </b>
												<input type="checkbox" name="chkd" id="allchkd"
													onclick="allChecked();saveFlag();"  />
											</td>
											<td align="center">
												<strong><bean:message key="lbl.disbursalNumber" /> </strong>
											</td>
											<td>
												<strong><bean:message key="lbl.disbursalDate" /> </strong>
											</td>
											<td align="center">
												<strong><bean:message key="lbl.disbAmount" /> </strong>
											</td>
											<td align="center">
												<b><bean:message key="lbl.disbursedTo" /> </b>
											</td>
											<td align="center">
												<b><bean:message key="lbl.toName" /> </b>
											</td >		
											<td align="center">
												<b><bean:message key="lbl.cancellationDate" /> </b>
											</td>									
										</tr>


										<logic:present name="arrList">
										<logic:notEmpty name="arrList">
											<logic:iterate id="arrListobj" name="arrList" indexId="count">												

												
													<tr class="white2">
														<td>
															<input type="checkbox" name="chk" id=""
																value="${arrListobj.disbursalNumber}" disabled />
														</td>
														<td>
														${arrListobj.disbursalNumber}
														</td>
														<td>
															${arrListobj.disbursalDate}
														</td>

														<td>
															${arrListobj.disbursalAmount}
														</td>
														<td>
															${arrListobj.disbursedTo}
														</td>
														<td>
															${arrListobj.toName}
														</td>													
														
													</tr>												
											</logic:iterate>
											</logic:notEmpty>
										</logic:present>
								
									
								</table>
							</td>
						</tr>

						
<!--  -->
							<tr>
								<td colspan="4">
									<button type="button" name="save" id="save" 
										class="topformbutton2" accesskey="V" title="Alt+V"
										onclick="return saveCancellationDetails();" ><bean:message key="button.save" /></button>
									<button type="button" name="saveForward" id="saveForward"
										 class="topformbutton2" accesskey="F"
										title="Alt+F" onclick="return updateDisbursalBeforeSave();" ><bean:message key="button.forward" /></button>
									<button name="view" id="view" accesskey="L"
										title="Alt+L" type="button"
										class="topformbutton3" onclick="return viewLoanDetails();" ><bean:message key="button.viewloandetails" /></button>
									<button type="button"
										class="topformbutton3" accesskey="P" title="Alt+P"
										onclick="return viewPayableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewPayable" /></button>
									<button type="button"
										class="topformbutton3" accesskey="R" title="Alt+R"
										onclick="return viewReceivableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
								</td>
							</tr>
						</table>
					</fieldset>
				
			</logic:present>
			
<!-- ***********************For Cancellation  Maker with Disbursal Details *******************-->

			<logic:present name="DisCancellationMakerWithDetails">				
					
					<fieldset>
						<LEGEND>
							<bean:message key="lbl.disbCancellationMaker" />
						</LEGEND>
						<input type="hidden" id="billFlagYesOrNo" value="${arrList1[0].billFlagYesOrNo}" />	
						<table width="100%" border="0" cellspacing="1" cellpadding="1">							
							<tr>

								<td>
									<bean:message key="lbl.loanAc" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:text styleClass="text" property="loanAc" styleId="loanAc"
										value="${arrList1[0].loanAc}" readonly="true" />										
											<logic:notPresent name="savedDetails">
											<html:button
										onclick="openLOVCommon(485,'cancellationForm','loanAc','','', '','','generateDisbValuesCancellation','customerName');closeLovCallonLov1();saveFlag();getDetailFlag();"
										property="loanAcButton" styleId="loanAcButton" value=" "
										styleClass="lovButton" />
											</logic:notPresent>
									
									<input type="hidden" name="contextPath"
										value="<%=request.getContextPath()%>" id="contextPath" />
									<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
										value="${arrList1[0].lbxLoanNoHID}" />
								</td>

								<td>
									<bean:message key="lbl.customerName" />
								</td>
								<td>
									<html:text styleClass="text" styleId="customerName"
										tabindex="-1" maxlength="50" disabled="true"
										property="customerName" value="${arrList1[0].customerName}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.loanDate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanDate" maxlength="10"
										disabled="true" property="loanDate" value="${arrList1[0].loanDate}" tabindex="-1" />
								</td>
								<td>
									<bean:message key="lbl.loanAmt" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanAmt" maxlength="18"
										disabled="true" property="loanAmt" tabindex="-1" value="${arrList1[0].loanAmt}"
										style="text-align:right;"/>
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.product" />
								</td>
								<td>
									<html:text styleClass="text" styleId="product" disabled="true" value="${arrList1[0].product}"
										maxlength="50" property="product" tabindex="-1" />
								</td>
								<td>
									<bean:message key="lbl.scheme" />
								</td>
								<td>
									<html:text styleClass="text" styleId="scheme" disabled="true" value="${arrList1[0].scheme}"
										maxlength="50" property="scheme" tabindex="-1" />
								</td>
							</tr>	
							

								

							<tr>								

								<td nowrap="nowrap">
									<bean:message key="lbl.authorRemarks" />
								</td>
								<td nowrap="nowrap">
									<html:textarea styleClass="text" property="authorRemarks"
										disabled="true" value="${arrList2[0].cancellationRemarks}" />
								</td>
							</tr>
							<tr>
								<td colspan="4">									
										<logic:notPresent name="savedDetails">
										<button type="button" name="getDisbDtl" id="getDisbDtl" 
										class="topformbutton4" accesskey="V" title="Alt+G"
										onclick="return getDisbursalDetails();saveFlag();" ><bean:message key="button.getDisbDtl" /></button>
										</logic:notPresent>
										<logic:present name="savedDetails">
										<button type="button" name="getDisbDtl" id="getDisbDtl" disabled="disabled"
										class="topformbutton4" accesskey="V" title="Alt+G"
										onclick="return getDisbursalDetails();saveFlag();" ><bean:message key="button.getDisbDtl" /></button>
										</logic:present>
									</td>
									</tr>
					</table>					
				</FIELDSET>
<!-- Grid Code -->

<fieldset>
					<legend>
						<bean:message key="lbl.disbSummary" />
					</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="gridtd">

								<table id="table1" width="100%" border="0" cellspacing="1"
									cellpadding="4">									
										<tr class="white2">
											<td align="center">
												<b><bean:message key="lbl.select" /> </b>
												<input type="checkbox" name="chkd" id="allchkd"
													onclick="allChecked();saveFlag();saveReverseFlag();"  />
											</td>
											<td align="center">
												<strong><bean:message key="lbl.disbursalNumber" /> </strong>
											</td>
											<td align="center">
												<strong><bean:message key="lbl.disbursalDate" /> </strong>
											</td>
											<td align="center">
												<strong><bean:message key="lbl.disbAmount" /> </strong>
											</td>
											<td align="center">
												<b><bean:message key="lbl.disbursedTo" /> </b>
											</td>
											<td align="center">
												<b><bean:message key="lbl.toName" /> </b>
											</td>		
											<td align="center">
												<b><bean:message key="lbl.cancellationDate" /> </b>
											</td>									
										</tr>


										<logic:present name="arrList2">
										<logic:notEmpty name="arrList2">
											<logic:iterate id="arrListobj" name="arrList2" indexId="count">												

												
													<tr class="white1">
														<td align="center">
														<logic:equal value="1" name="arrListobj" property="cancellationFlag">
														<input type="checkbox" name="chk" id="chkid<%=count.intValue()+1%>" checked="checked"
															onclick="saveFlag();saveReverseFlag();"	value="${arrListobj.disbursalNumber}"  />
														</logic:equal>
															
															<logic:notEqual value="1" name="arrListobj" property="cancellationFlag">
														<input type="checkbox" name="chk" id="chkid<%=count.intValue()+1%>"
															onclick="saveFlag();saveReverseFlag();"	value="${arrListobj.disbursalNumber}"  />
														</logic:notEqual>
														</td>
														<td align="right">
															${arrListobj.disbursalNumber}															
														</td>														
														<td align="center">
														<input type="text"  name="disbursalDate"  id="disbursalDate<%=count.intValue() + 1%>" value="${arrListobj.disbursedDate}" readonly="readonly"/>	
														</td>

														<td align="right">
															${arrListobj.disbursedAmount}
														</td>
														<td>
															${arrListobj.disbursedTo}
														</td>
														<td>
															${arrListobj.toName}
														</td>														
														<logic:notEqual name="arrListobj" property="disbursalNumber"	value="">
															<script type="text/javascript">
											//alert("hii ");
											     														$(function() {
											     														
											     														var contextPath =document.getElementById('contextPath').value ;
														$("#cancellationDate<%=count.intValue() + 1%>").datepicker({
														format: "%Y-%m-%d %H:%i:%s %E %#",
														formatUtcOffset: "%: (%@)",
														placement: "inline",
														
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
 															<logic:present name="cancDateAvail">
 															<td align="center"><input type="text" name="cancellationDate" id="cancellationDate<%=count.intValue() + 1%>" 
															onchange="return checkCancellationDate('<%=count.intValue() + 1%>');"
															class="text" readonly="readonly" value="${arrListobj.cancDate}"/></td>	
 															</logic:present>	
 															<logic:notPresent name="cancDateAvail">
 															<td align="center"><input type="text" name="cancellationDate" id="cancellationDate<%=count.intValue() + 1%>" 
															onchange="return checkCancellationDate('<%=count.intValue() + 1%>');"
															class="text" readonly="readonly" value="<%=initiationDate %>"/></td>	
 															</logic:notPresent>												
															
													</logic:notEqual>
													<td style="display:none" >															
															<input type="text" name="disbursalID" id="disbursalID" value="${arrListobj.disbursalIDNew}" />
														</td>
													</tr>												
											</logic:iterate>
											</logic:notEmpty>
										</logic:present>
								
									
								</table>
							</td>
						</tr>

						
<!--  -->
							<tr>
								<td colspan="4">
									<button type="button" name="save" id="save" 
										class="topformbutton2" accesskey="V" title="Alt+V"
										onclick="return saveCancellationDetails();" ><bean:message key="button.save" /></button>
									<button type="button" name="saveForward" id="saveForward"
										 class="topformbutton2" accesskey="F"
										title="Alt+F" onclick="return forwardCancellationDetails();" ><bean:message key="button.forward" /></button>
									<button name="view" id="view" accesskey="L"
										title="Alt+L" type="button"
										class="topformbutton3" onclick="return viewLoanDetails();" ><bean:message key="button.viewloandetails" /></button>
									<button type="button"
										class="topformbutton3" accesskey="P" title="Alt+P"
										onclick="return viewPayableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewPayable" /></button>
									<button type="button"
										class="topformbutton3" accesskey="R" title="Alt+R"
										onclick="return viewReceivableEarly('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable" /></button>
								</td>
							</tr>
						</table>
					</fieldset>
				
			</logic:present>
<!-- ********************************************* For Cancellation Author **************************************************** -->
			
			<logic:present name="disbCancellationAuthorWithDetails">
				
					
					<fieldset>
						<LEGEND>
							<bean:message key="lbl.disbCancellationAuthor" />
						</LEGEND>

						<table width="100%" border="0" cellspacing="1" cellpadding="1">							
							<tr>

								<td>
									<bean:message key="lbl.loanAc" />
									<span><font color="red">*</font>
									</span>
								</td>
								<td>
									<html:text styleClass="text" property="loanAc" styleId="loanAc"
										value="${arrList1[0].loanAc}" readonly="true" />										
											
									
									<input type="hidden" name="contextPath"
										value="<%=request.getContextPath()%>" id="contextPath" />
									<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
										value="${arrList1[0].lbxLoanNoHID}" />
								</td>

								<td>
									<bean:message key="lbl.customerName" />
								</td>
								<td>
									<html:text styleClass="text" styleId="customerName"
										tabindex="-1" maxlength="50" disabled="true"
										property="customerName" value="${arrList1[0].customerName}" />
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.loanDate" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanDate" maxlength="10"
										disabled="true" property="loanDate" value="${arrList1[0].loanDate}" tabindex="-1" />
								</td>
								<td>
									<bean:message key="lbl.loanAmt" />
								</td>
								<td>
									<html:text styleClass="text" styleId="loanAmt" maxlength="18"
										disabled="true" property="loanAmt" tabindex="-1" value="${arrList1[0].loanAmt}"
										style="text-align:right;"/>
								</td>
							</tr>

							<tr>
								<td>
									<bean:message key="lbl.product" />
								</td>
								<td>
									<html:text styleClass="text" styleId="product" disabled="true" value="${arrList1[0].product}"
										maxlength="50" property="product" tabindex="-1" />
								</td>
								<td>
									<bean:message key="lbl.scheme" />
								</td>
								<td>
									<html:text styleClass="text" styleId="scheme" disabled="true" value="${arrList1[0].scheme}"
										maxlength="50" property="scheme" tabindex="-1" />
								</td>
							</tr>	
							

								

							<tr>								

								<td nowrap="nowrap">
									<bean:message key="lbl.authorRemarks" />
								</td>
								<td nowrap="nowrap">
									<html:textarea styleClass="text" property="authorRemarks"
										disabled="true" value="${arrList2[0].cancellationRemarks}" />
								</td>
							</tr>							
					</table>					
				</FIELDSET>
<!-- Grid Code -->

<fieldset>
					<legend>
						<bean:message key="lbl.disbSummary" />
					</legend>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="gridtd">

								<table id="table1" width="100%" border="0" cellspacing="1"
									cellpadding="4">									
										<tr class="white2">
											<td align="center">
												<b><bean:message key="lbl.select" /> </b>
												<input type="checkbox" name="chkd" id="allchkd" disabled="disabled"
													onclick="allChecked();saveFlag();"  />
											</td>
											<td>
												<strong><bean:message key="lbl.disbursalNumber" /> </strong>
											</td>
											<td>
												<strong><bean:message key="lbl.disbursalDate" /> </strong>
											</td>
											<td>
												<strong><bean:message key="lbl.disbAmount" /> </strong>
											</td>
											<td>
												<b><bean:message key="lbl.disbursedTo" /> </b>
											</td>
											<td>
												<b><bean:message key="lbl.toName" /> </b>
											</td>	
											<td align="center">
												<b><bean:message key="lbl.cancellationDate" /> </b>
											</td>											
										</tr>


										<logic:present name="arrList2">
										<logic:notEmpty name="arrList2">
											<logic:iterate id="arrListobj" name="arrList2" indexId="count">												

												
													<tr class="white1">
														<td align="center">
														<logic:equal value="1" name="arrListobj" property="cancellationFlag">
														<input type="checkbox" name="chk" id="<%=count.intValue()+1%>"  checked="checked" disabled="disabled"
															onclick="saveFlag();"	value="${arrListobj.disbursalNumber}"  />														
														</logic:equal>
															
															<logic:notEqual value="1" name="arrListobj" property="cancellationFlag">														
															<input type="checkbox" name="chk" id="<%=count.intValue()+1%>" disabled="disabled"
															onclick="saveFlag();"	value="${arrListobj.disbursalNumber}"  />
														</logic:notEqual>
														</td>
														<td>
															${arrListobj.disbursalNumber}
														</td>
														<td>
															${arrListobj.disbursedDate}
														</td>

														<td align="right">
															${arrListobj.disbursedAmount}
														</td>
														<td>
															${arrListobj.disbursedTo}
														</td>
														<td>
															${arrListobj.toName}
														</td>	
														<td>
															${arrListobj.cancDate}
														</td>												
														<td style="display:none" >															
															<input type="text" name="disbursalID" id="disbursalID" value="${arrListobj.disbursalIDNew}" />
														</td>													
													</tr>												
											</logic:iterate>
											</logic:notEmpty>
										</logic:present>
								
									
								</table>
							</td>
						</tr>

						
<!--  -->
							
						</table>
					</fieldset>
				
			</logic:present>
			
			
			<!-- ********************************************* Cancellation Author Ends **************************************************** -->
		</html:form>
		</div>
	</div>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<script>
	setFramevalues("cancellationForm");
</script>
</body>
</html:html>

<logic:present name="sms">

	<script type="text/javascript">
	if("<%=request.getAttribute("sms")%>"=="S")
	{
		alert("<bean:message key="msg.DataSaved" />");
	}
	
	else if("<%=request.getAttribute("sms")%>"=="E")
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		
	}
	else if("<%=request.getAttribute("sms")%>"=="FS")
	{
		alert("<bean:message key="msg.disbForwardSuccess" />");	
		window.location = "<%=request.getContextPath() %>/disbursalCancellation.do?method=disbCancellationMakerSearch";	
	}
	else if("<%=request.getAttribute("sms")%>"=="FN")
	{
		alert("<bean:message key="msg.disbNotForwardSuccess" />");		
	}	
	else if("<%=request.getAttribute("sms")%>"=="N")
	{
		alert("<bean:message key="msg.adviceValidation" />");		
	}
	else if("<%=request.getAttribute("sms")%>"=="ND")
	{
		alert("<bean:message key="msg.noDataFound" />");		
	}
	else if("<%=request.getAttribute("sms")%>"=="TA")
	{
		alert("Disbursal has been Adjusted In TA Loan No:"+"<%=request.getAttribute("taLoanNO")%>");
	}
	else if("<%=request.getAttribute("sms")%>"=="B")
	{
		alert("<bean:message key="msg.billFlag" />");		
	}
	else if("<%=request.getAttribute("sms")%>"=="P")
	{
		alert("<bean:message key="msg.recType" />");		
	}
</script>
</logic:present>
