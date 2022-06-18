<%--
      Author Name-      Amit Kumar
      Date of creation -12/07/2011
      Purpose-          Knock off Details
      Documentation-      
 --%>

<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*"%>
<%@ page import="com.connect.CommonFunction"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.logger.LoggerMsg"%>
<%@page import="com.login.roleManager.UserObject"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
 Date currentDate = new Date();
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
       UserObject userObject = (UserObject)session.getAttribute("userobject");
     LoggerMsg.info("currentDate.... "+currentDate);
     String initiationDate = userObject.getBusinessdate();		
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>

	<logic:present name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/${css }/displaytable.css" />
	</logic:present>
	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/theme1/displaytable.css" />
	</logic:notPresent>
	<!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	<script type="text/javascript"	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"	src="<%=request.getContextPath()%>/js/cmScript/knockOffMaker.js"></script>
	<script type="text/javascript"	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
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
<script type="text/javascript">
	function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('KnockOffMaker').loanButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('KnockOffMaker').businessPartnerType.focus();
     }
    
     return true;
}		
	</script>
<body onload="enableAnchor();checkChanges('KnockOffMaker');fntab();" oncontextmenu="return false"
	onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();">
<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" id="adviceDtChngFlag" value="N"/>	
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>

	<div id="centercolumn">
		<div id="revisedcontainer">

			<logic:present name="knockOffNew">
				<input type="hidden" id="modifyRecord" name="modifyRecord" value="M" />
				<html:form action="/knockOffMakerProcess" styleId="KnockOffMaker"
					method="POST">
				
					<input type="hidden" name="contextPath" id="contextPath"
						value="<%=request.getContextPath()%>" />
					<html:hidden property="knockOffId" styleId="knockOffId"
						styleClass="text" value="${requestScope.knockOffId}" />
						
					 
						
					<fieldset>
						<legend>
							<bean:message key="lbl.knockOffMaker" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td>
												<bean:message key="lbl.loanNumber" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text property="loanNumber" styleId="loanNumber"
													size="20" value="${requestScope.loanNumber}"
													styleClass="text" tabindex="-1" readonly="true"></html:text>
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${requestScope.lbxLoanNoHID}" />
												<input type="hidden" name="hID" id="hID" />
												<html:button property="loanButton" styleId="loanButton"
													onclick="openLOVCommon(6,'KnockOffMaker','loanNumber','','', '','','clearBPDesc','customerName','hID','hID');closeLovCallonLov1();"
													value=" " styleClass="lovbutton"></html:button>
											</td>
											<td>
												<bean:message key="lbl.customerName" />
											</td>
											<td>
												<html:text property="customerName" styleId="customerName"
													size="20" value="${requestScope.customerName}"
													styleClass="text" readonly="true" tabindex="-1"></html:text>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.businessPartnerType" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text property="businessPartnerType"
													styleId="businessPartnerType"
													value="${requestScope.businessPartnerType}"
													styleClass="text" />
												<html:hidden property="hBPType" styleId="hBPType"
													value="${requestScope.hBPType}" />
												<html:button property="bpButton" styleId="bpButton"
													onclick="openLOVCommon(45,'KnockOffMaker','hBPType','loanNumber', 'lbxBusinessPartnearHID','lbxLoanNoHID','Select Loan Account LOV First','','businessPartnerName','businessPartnerType');closeLovCallonLov('loanNumber');"
													value=" " styleClass="lovbutton"></html:button>
											</td>
											<td>
												<bean:message key="lbl.businessPartnerName" />
											</td>
											<td>
												<html:text property="businessPartnerName"
													styleId="businessPartnerName" size="20"
													value="${requestScope.businessPartnerName}"
													styleClass="text" tabindex="-1" readonly="true"></html:text>
												<html:hidden property="lbxBusinessPartnearHID"
													styleId="lbxBusinessPartnearHID"
													value="${requestScope.lbxBusinessPartnearHID}" />
											</td>
										</tr>
<tr>
											<td>
												<bean:message key="lbl.valueDate" /><font color="red">*</font>
											</td>
											<td>
											<logic:present name="loanDataListR">
												<html:text styleClass="text" property="valueDate"
															styleId="valueDate" maxlength="10"
															value="${requestScope.valueDate}"
															onchange="checkDate('valueDate');checkValueDate();" />
											</logic:present>
											
											<logic:notPresent name="loanDataListR">
												<html:text styleClass="text" property="valueDate"
															styleId="valueDate" maxlength="10"
															value="<%= initiationDate%>"
															onchange="checkDate('valueDate');checkValueDate();" />
											</logic:notPresent>
											
											</td><td>
											</td><td>
											
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.makerRemark" />
											</td>
											<td>
												<html:textarea property="remarks" styleId="remarks"
													value="${requestScope.remarks}" styleClass="text"></html:textarea>
											</td>
											<td>
												<bean:message key="lbl.authorRemarks" />
											</td>
											<td>
												<html:textarea styleClass="text" property="authorRemarks"
													disabled="true" value="${requestScope.authorRemarks}" />
											</td>
										</tr>
									</table>
									<button type="button" name="Search" class="topformbutton2"
										onclick="searchLoanDetails();" accesskey="S"
										title="Alt+S" ><bean:message key="button.search" /></button>
								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="knockoff">
							<tr>
								<th>
									<div align="center">
										<strong><bean:message key="lbl.receivableAdvices" />
										</strong>
									</div>
								</th>
								<th class="thnone">
									<div align="center">
										<strong><bean:message key="lbl.payableAdvices" />
										</strong>
									</div>
								</th>
							</tr>

							<tr valign="top">
								<td valign="top" class="bnone">
									<div>
										<table width="100%" border="0" cellspacing="0" cellpadding="1"
											id="dtable" class="knockoff none">
											<tr>
												<th>
													<strong><bean:message key="lbl.advicedate" />
													</strong>
												</th>
												<th>
													<b><bean:message key="lbl.charges" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.adviceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.balanceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.amountInProcess" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.knockOffAmount" />
													</b>
												</th>
												<th class="bnone">
													<b><bean:message key="lbl.select" />
													</b>
												</th>
											</tr>
											<%
												int i = 1;
											%>
											<logic:present name="loanDataListR">
												<logic:iterate id="loanDataListR1" name="loanDataListR">
													<tr>
														<td>
															${loanDataListR1.loanNumber}
														</td>
														<td>
															${loanDataListR1.charges}
														</td>
														<td>
															${loanDataListR1.originalAmountR}
														</td>
														<td>
															${loanDataListR1.balanceAmountR}
														</td>
														<td>
															${loanDataListR1.amountInProcessR}
														</td>

														<td>
															<input type="hidden" name="originalAmountR"
																id="originalAmountR<%=i%>"
																value="${loanDataListR1.originalAmountR}" />
															<input type="hidden" name="balanceAmountR"
																id="balanceAmountR<%=i%>"
																value="${loanDataListR1.balanceAmountR}" />
															<input type="hidden" name="txnAdviceIdR"
																id="txnAdviceIdR<%=i%>"
																value="${loanDataListR1.txnAdviceIdR}" />
															<input type="hidden" name="amountInProcessR"
																id="amountInProcessR<%=i%>"
																value="${loanDataListR1.amountInProcessR}" />
															<input type="hidden" name="knockOffIdR"
																id="knockOffIdR<%=i%>"
																value="${loanDataListR1.knockOffIdR}" />

															<logic:equal name="loanDataListR1"
																property="balanceAmountR" value="0.0000">
																<input type="text" class="text4"
																	id="knockOffAmountR<%=i%>" name="knockOffAmountR"
																	disabled="disabled" />
															</logic:equal>

															<logic:notEqual name="loanDataListR1"
																property="balanceAmountR" value="0.0000">

																<input type="text" class="text4"
																	id="knockOffAmountR<%=i%>" name="knockOffAmountR"
																	value="${loanDataListR1.knockOffAmountR}"
																	onkeypress="return numbersonly(event,id,18)"
																	onblur="formatNumber(this.value,id);"
																	onkeyup="checkNumber(this.value, event, 18,id); callKnockOffAmountReceivable();"
																	onfocus="keyUpNumber(this.value, event, 18,id);"
																	style="text-align: right; width:90px;" />
															</logic:notEqual>

														</td>
														<td class="bnone">
															<logic:equal name="loanDataListR1"
																property="balanceAmountR" value="0.0000">
																<input type="checkbox" name="chk" id="chkR<%=i%>"
																	disabled="disabled"
																	value="${loanDataListR1.txnAdviceIdR}" />
															</logic:equal>

															<logic:notEqual name="loanDataListR1"
																property="balanceAmountR" value="0.0000">
																<input type="checkbox" name="chk" id="chkR<%=i%>"
																	value="${loanDataListR1.txnAdviceIdR}"/>
															</logic:notEqual>


														</td>
														<%
															i++;
														%>

													</tr>
												</logic:iterate>
												<tr class="white1">
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														<b><bean:message key="lbl.Total" />
														</b>
													</td>
													<td>
														<html:text styleClass="text4" styleId="totalReveivable"
															property="totalReveivable"
															value="${totalR[0].totalReveivable}" readonly="true"
															style="text-align:right; width:90px;" tabindex="-1" />
													</td>
													<td class="bnone">
														&nbsp;
													</td>
												</tr>
											</logic:present>

										</table>
									</div>
								</td>
								<td class="bnone">
									<div>
										<table width="100%" border="0" cellspacing="0" cellpadding="1"
											id="dtable1" class="knockoff rtnone">
											<tr>
												<th>
													<b><bean:message key="lbl.select" />
													</b>
												</th>
												<th>
													<strong><bean:message key="lbl.advicedate" />
													</strong>
												</th>
												<th>
													<b><bean:message key="lbl.charges" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.adviceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.balanceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.amountInProcess" />
													</b>
												</th>
												<th class="bnone">
													<b><bean:message key="lbl.knockOffAmount" />
													</b>
												</th>
											</tr>
											<%
												int j = 1;
											%>
											<logic:present name="loanDataListP">
												<logic:iterate id="loanDataListP1" name="loanDataListP">
													<tr class="white1">
														<td>
															<logic:equal name="loanDataListP1"
																property="balanceAmountP" value="0.0000">
																<input type="checkbox" name="chk" id="chkP<%=j%>"
																	disabled="disabled"
																	value="${loanDataListP1.txnAdviceIdP}" />
															</logic:equal>
															<logic:notEqual name="loanDataListP1"
																property="balanceAmountP" value="0.0000">
																<input type="checkbox" name="chk" id="chkP<%=j%>"
																	value="${loanDataListP1.txnAdviceIdP}"
																/>
															</logic:notEqual>

														</td>
														<td>
															${loanDataListP1.loanNumber}
														</td>
														<td>
															${loanDataListP1.charges}
														</td>
														<td>
															${loanDataListP1.originalAmountP}
														</td>
														<td>
															${loanDataListP1.balanceAmountP}
														</td>
														<td>
															${loanDataListP1.amountInProcessP}
														</td>

														<td class="bnone">
															<logic:equal name="loanDataListP1"
																property="balanceAmountP" value="0.0000">
																<input type="text" class="text4"
																	id="knockOffAmountP<%=j%>" name="knockOffAmountP"
																	value="" disabled="disabled" />
															</logic:equal>

															<logic:notEqual name="loanDataListP1"
																property="balanceAmountP" value="0.0000">
																<input type="text" class="text4"
																	id="knockOffAmountP<%=j%>" name="knockOffAmountP"
																	value="${loanDataListP1.knockOffAmountP}"
																	onkeypress="return numbersonly(event,id,18)"
																	onblur="formatNumber(this.value,id);"
																	onkeyup="checkNumber(this.value, event, 18,id); callKnockOffAmountPayable();"
																	onfocus="keyUpNumber(this.value, event, 18,id);"
																	style="text-align: right; width:90px;" />
															</logic:notEqual>

															<input type="hidden" name="originalAmountP"
																id="originalAmountP<%=j%>"
																value="${loanDataListP1.originalAmountP}" />
															<input type="hidden" name="balanceAmountP"
																id="balanceAmountP<%=j%>"
																value="${loanDataListP1.balanceAmountP}" />
															<input type="hidden" name="txnAdviceIdP"
																id="txnAdviceIdP<%=j%>"
																value="${loanDataListP1.txnAdviceIdP}" />
															<input type="hidden" name="amountInProcessP"
																id="amountInProcessP<%=j%>"
																value="${loanDataListP1.amountInProcessP}" />
															<input type="hidden" name="knockOffIdP"
																id="knockOffIdP<%=j%>"
																value="${loanDataListP1.knockOffIdP}" />
														</td>
														<%
															j++;
														%>
													</tr>
												</logic:iterate>
												<tr class="white1">
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														<b><bean:message key="lbl.Total" />
														</b>
													</td>
													<td class="bnone">
														<html:text styleClass="text4" styleId="totalPayable"
															property="totalPayable" value="${totalP[0].totalPayable}"
															readonly="true" style="text-align:right; width:90px;" tabindex="-1" />
													</td>
												</tr>
											</logic:present>


										</table>
									</div>
								</td>
							</tr>
						</table>
						<logic:present name="loanDataListR">
							<logic:notEmpty name="loanDataListR">
								<logic:present name="loanDataListR">
									<logic:notEmpty name="loanDataListR">
								<button type="button" name="button22" id="save" 
							class="topformbutton2" 
							onclick="return SaveKnockOffData('P');"
							accesskey="V" title="Alt+V" ><bean:message key="button.save" /></button>
						<button type="button" name="button22" id="saveFwd" 
							class="topformbutton2" 
							onclick="return forwardBeforeSave('<bean:message key="msg.forwardBeforeSave" />');"
							accesskey="F" title="Alt+F" ><bean:message key="button.forward" /></button>
							
							</logic:notEmpty>
						</logic:present>
						</logic:notEmpty>
						</logic:present>
						<html:hidden property="adviceTypeR" styleId="adviceTypeR"
							value="R" />
						<html:hidden property="adviceTypeP" styleId="adviceTypeP"
							value="P" />
						<html:hidden property="loanId" styleId="loanId"
							value="${loanDataListR[0].loanId}" />

					</fieldset>
					<br />
				</html:form>

			</logic:present>

			<!-- --------------------------------------------Knock off Maker Save & Forward------------------------------------ -->


			<logic:present name="knockOffMakerValues">
				<input type="hidden" id="modifyRecord" name="modifyRecord" value="I" />
				<html:form action="/knockOffMakerProcess" styleId="KnockOffMaker"
					method="POST">
					
					<input type="hidden" name="loanRecStatus" value="${knockOffSearchList[0].loanRecStatus}" id="loanRecStatus" />
					<input type="hidden" name="contextPath" id="contextPath"
						value="<%=request.getContextPath()%>" />
					<html:hidden property="knockOffId" styleId="knockOffId"
						styleClass="text" value="${requestScope.knockOffId}" />
					<fieldset>
						<legend>
							<bean:message key="lbl.knockOffMaker" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td>
												<bean:message key="lbl.loanNumber" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text property="loanNumber" styleId="loanNumber"
													size="20" value="${knockOffSearchList[0].loanNumber}"
													styleClass="text" tabindex="-1" readonly="true"></html:text>
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${knockOffSearchList[0].lbxLoanNoHID}" />
											</td>
											<td>
												<bean:message key="lbl.customerName" />
											</td>
											<td>
												<html:text property="customerName" styleId="customerName"
													size="20" value="${knockOffSearchList[0].customerName}"
													styleClass="text" readonly="true" tabindex="-1"></html:text>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.businessPartnerType" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text property="businessPartnerType"
													styleId="businessPartnerType"
													value="${knockOffSearchList[0].businessPartnerType}"
													styleClass="text" readonly="true" tabindex="-1" />
												<html:hidden property="hBPType" styleId="hBPType"
													value="${knockOffSearchList[0].hBPType}" />

											</td>
											<td>
												<bean:message key="lbl.businessPartnerName" />
											</td>
											<td>
												<html:text property="businessPartnerName"
													styleId="businessPartnerName" size="20"
													value="${knockOffSearchList[0].businessPartnerName}"
													styleClass="text" tabindex="-1" readonly="true"></html:text>
												<html:hidden property="lbxBusinessPartnearHID"
													styleId="lbxBusinessPartnearHID"
													value="${knockOffSearchList[0].lbxBPNID}" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.valueDate" /><font color="red">*</font>
											</td>
											<td>
								
											<html:text styleClass="text" property="valueDate"
															styleId="valueDate1" maxlength="10"
															value="${knockOffSearchList[0].valueDate}"
															readonly="true"/>
											</td><td>
											</td><td>
											
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.makerRemark" />
											</td>
											<td>

												<html:textarea property="remarks" styleId="remarks"
													value="${knockOffSearchList[0].remarks}" styleClass="text"></html:textarea>
											</td>

											<td>
												<bean:message key="lbl.authorRemarks" />
											</td>
											<td>
												<html:textarea styleClass="text" property="authorRemarks"
													disabled="true"
													value="${knockOffSearchList[0].authorRemarks}" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="knockoff">
							<tr>
								<th>
									<div align="center">
										<strong><bean:message key="lbl.receivableAdvices" />
										</strong>
									</div>
								</th>
								<th class="thnone">
									<div align="center">
										<strong><bean:message key="lbl.payableAdvices" />
										</strong>
									</div>
								</th>
							</tr>

							<tr valign="top">
								<td valign="top" class="bnone">
									<div>
										<table width="100%" border="0" cellspacing="0" cellpadding="1"
											id="dtable" class="knockoff none">
											<tr>
												<th>
													<strong><bean:message key="lbl.loanAccountNo" />
													</strong>
												</th>
												<th>
													<b><bean:message key="lbl.charges" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.adviceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.balanceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.amountInProcess" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.knockOffAmount" />
													</b>
												</th>
												<th class="bnone">
													<b><bean:message key="lbl.select" />
													</b>
												</th>
											</tr>
											<%
												int i = 1;
											%>
											<logic:present name="loanDataListR">
												<logic:iterate id="loanDataListR1" name="loanDataListR">
													<tr>
														<td>
															${loanDataListR1.loanNumber}
														</td>
														<td>
															${loanDataListR1.charges}
														</td>
														<td>
															${loanDataListR1.originalAmountR}
														</td>
														<td>
															${loanDataListR1.balanceAmountR}
														</td>
														<td>
															${loanDataListR1.amountInProcessR}
														</td>

														<td>
															<input type="hidden" name="originalAmountR"
																id="originalAmountR<%=i%>"
																value="${loanDataListR1.originalAmountR}" />
															<input type="hidden" name="balanceAmountR"
																id="balanceAmountR<%=i%>"
																value="${loanDataListR1.balanceAmountR}" />
															<input type="hidden" name="txnAdviceIdR"
																id="txnAdviceIdR<%=i%>"
																value="${loanDataListR1.txnAdviceIdR}" />
															<input type="hidden" name="amountInProcessR"
																id="amountInProcessR<%=i%>"
																value="${loanDataListR1.amountInProcessR}" />
															<input type="hidden" name="knockOffIdR"
																id="knockOffIdR<%=i%>"
																value="${loanDataListR1.knockOffIdR}" />

															<logic:equal name="loanDataListR1"
																property="knockOffAmountR" value="0.0000">
																<input type="text" class="text4"
																	id="knockOffAmountR<%=i%>" name="knockOffAmountR"
																	disabled="disabled" />
															</logic:equal>

															<logic:notEqual name="loanDataListR1"
																property="knockOffAmountR" value="0.0000">

																<input type="text" class="text4"
																	id="knockOffAmountR<%=i%>" name="knockOffAmountR"
																	value="${loanDataListR1.knockOffAmountR}"
																	onkeypress="return numbersonly(event,id,18)"
																	onblur="formatNumber(this.value,id);"
																	onkeyup="checkNumber(this.value, event, 18,id); callKnockOffAmountReceivable();"
																	onfocus="keyUpNumber(this.value, event, 18,id);"
																	style="text-align: right; width:90px;" />
															</logic:notEqual>

														</td>
														<td class="bnone">
															<logic:equal name="loanDataListR1"
																property="knockOffAmountR" value="0.0000">
																<input type="checkbox" name="chk" id="chkR<%=i%>"
																	disabled="disabled"
																	value="${loanDataListR1.txnAdviceIdR }" />
															</logic:equal>

															<logic:notEqual name="loanDataListR1"
																property="knockOffAmountR" value="0.0000">
																<input type="checkbox" name="chk" id="chkR<%=i%>"
																	value="${loanDataListR1.txnAdviceIdR }"
																	checked="checked" />
															</logic:notEqual>
														</td>
														<%
															i++;
														%>

													</tr>
												</logic:iterate>
												<tr class="white1">
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														<b><bean:message key="lbl.Total" />
														</b>
													</td>
													<td>
														<html:text styleClass="text4" styleId="totalReveivable"
															property="totalReveivable"
															value="${totalR[0].totalReveivable}" readonly="true"
															style="text-align:right; width:90px;" />
													</td>
													<td class="bnone">
														&nbsp;
													</td>
												</tr>
											</logic:present>

										</table>
									</div>
								</td>
								<td class="bnone">
									<div>
										<table width="100%" border="0" cellspacing="0" cellpadding="1"
											id="dtable1" class="knockoff rtnone">
											<tr>
												<th>
													<b><bean:message key="lbl.select" />
													</b>
												</th>
												<th>
													<strong><bean:message key="lbl.loanAccountNo" />
													</strong>
												</th>
												<th>
													<b><bean:message key="lbl.charges" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.adviceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.balanceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.amountInProcess" />
													</b>
												</th>
												<th class="bnone">
													<b><bean:message key="lbl.knockOffAmount" />
													</b>
												</th>
											</tr>
											<%
												int j = 1;
											%>
											<logic:present name="loanDataListP">
												<logic:iterate id="loanDataListP1" name="loanDataListP">
													<tr class="white1">
														<td>
															<logic:equal name="loanDataListP1"
																property="knockOffAmountP" value="0.0000">
																<input type="checkbox" name="chk" id="chkP<%=j%>"
																	disabled="disabled"
																	value="${loanDataListP1.txnAdviceIdP }" />
															</logic:equal>
															<logic:notEqual name="loanDataListP1"
																property="knockOffAmountP" value="0.0000">
																<input type="checkbox" name="chk" id="chkP<%=j%>"
																	value="${loanDataListP1.txnAdviceIdP }"
																	checked="checked" />
															</logic:notEqual>

														</td>
														<td>
															${loanDataListP1.loanNumber }
														</td>
														<td>
															${loanDataListP1.charges }
														</td>
														<td>
															${loanDataListP1.originalAmountP }
														</td>
														<td>
															${loanDataListP1.balanceAmountP }
														</td>
														<td>
															${loanDataListP1.amountInProcessP }
														</td>

														<td class="bnone">
															<logic:equal name="loanDataListP1"
																property="knockOffAmountP" value="0.0000">
																<input type="text" class="text4"
																	id="knockOffAmountP<%=j%>" name="knockOffAmountP"
																	value="" disabled="disabled" />
															</logic:equal>

															<logic:notEqual name="loanDataListP1"
																property="knockOffAmountP" value="0.0000">
																<input type="text" class="text4"
																	id="knockOffAmountP<%=j%>" name="knockOffAmountP"
																	value="${loanDataListP1.knockOffAmountP }"
																	onkeypress="return numbersonly(event,id,18)"
																	onblur="formatNumber(this.value,id);"
																	onkeyup="checkNumber(this.value, event, 18,id); callKnockOffAmountPayable();"
																	onfocus="keyUpNumber(this.value, event, 18,id);"
																	style="text-align: right; width:90px;" />
															</logic:notEqual>

															<input type="hidden" name="originalAmountP"
																id="originalAmountP<%=j%>"
																value="${loanDataListP1.originalAmountP }" />
															<input type="hidden" name="balanceAmountP"
																id="balanceAmountP<%=j%>"
																value="${loanDataListP1.balanceAmountP }" />
															<input type="hidden" name="txnAdviceIdP"
																id="txnAdviceIdP<%=j%>"
																value="${loanDataListP1.txnAdviceIdP }" />
															<input type="hidden" name="amountInProcessP"
																id="amountInProcessP<%=j%>"
																value="${loanDataListP1.amountInProcessP }" />
															<input type="hidden" name="knockOffIdP"
																id="knockOffIdP<%=j%>"
																value="${loanDataListP1.knockOffIdP }" />
														</td>
														<%
															j++;
														%>
													</tr>
												</logic:iterate>
												<tr class="white1">
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														<b><bean:message key="lbl.Total" />
														</b>
													</td>
													<td class="bnone">
														<html:text styleClass="text4" styleId="totalPayable"
															property="totalPayable" value="${totalP[0].totalPayable}"
															readonly="true" style="text-align:right; width:90px;" />
													</td>
												</tr>
											</logic:present>


										</table>
									</div>
								</td>
							</tr>
						</table>
						<button type="button" name="button22" id="save"
							class="topformbutton2" 
							onclick="return SaveKnockOffData('P');"
							accesskey="V" title="Alt+V" ><bean:message key="button.save" /></button>
						<button type="button" name="button22" id="saveFwd"
							class="topformbutton2" 
							onclick="return SaveKnockOffData('F');"
							accesskey="F" title="Alt+F" ><bean:message key="button.forward" /></button>
						<button type="button" id="delete" class="topformbutton2" onclick="deleteKnockOff();" 
							title="Alt+D" accesskey="D"><bean:message key="button.delete" /></button>
						<html:hidden property="adviceTypeR" styleId="adviceTypeR"
							value="R" />
						<html:hidden property="adviceTypeP" styleId="adviceTypeP"
							value="P" />
						<html:hidden property="loanId" styleId="loanId"
							value="${loanDataListR[0].loanId}" />

					</fieldset>
					<br />
				</html:form>

			</logic:present>

			<!-- ------------------------------------------Knock Off Author Values-------------------------------------------- -->

			<logic:present name="knockOffAuthorValues">
				<html:form action="/knockOffMakerProcess" styleId="KnockOffMaker"
					method="POST">
					
					<input type="hidden" name="contextPath" id="contextPath"
						value="<%=request.getContextPath()%>" />
					<html:hidden property="knockOffId" styleId="knockOffId"
						styleClass="text" value="${requestScope.knockOffId}" />
					<fieldset>
						<legend>
							<bean:message key="lbl.knockOffMaker" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr>
											<td>
												<bean:message key="lbl.loanNumber" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text property="loanNumber" styleId="loanNumber"
													size="20" value="${knockOffSearchList[0].loanNumber}"
													styleClass="text" tabindex="-1" disabled="true"></html:text>
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${knockOffSearchList[0].lbxLoanNoHID}" />
											</td>
											<td>
												<bean:message key="lbl.customerName" />
											</td>
											<td>
												<html:text property="customerName" styleId="customerName"
													size="20" value="${knockOffSearchList[0].customerName}"
													styleClass="text" disabled="true" tabindex="-1"></html:text>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.businessPartnerType" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text property="businessPartnerType"
													styleId="businessPartnerType"
													value="${knockOffSearchList[0].businessPartnerType}"
													styleClass="text" disabled="true" />
												<html:hidden property="hBPType" styleId="hBPType"
													value="${knockOffSearchList[0].hBPType}" />

											</td>
											<td>
												<bean:message key="lbl.businessPartnerName" />
											</td>
											<td>
												<html:text property="businessPartnerName"
													styleId="businessPartnerName" size="20"
													value="${knockOffSearchList[0].businessPartnerName}"
													styleClass="text" tabindex="-1" disabled="true"></html:text>
												<html:hidden property="lbxBusinessPartnearHID"
													styleId="lbxBusinessPartnearHID"
													value="${knockOffSearchList[0].lbxBPNID}" />
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.valueDate" /><font color="red">*</font>
											</td>
											<td>
								
											<html:text styleClass="text" property="valueDate1"
															styleId="valueDate1" maxlength="10"
															value="${knockOffSearchList[0].valueDate}"
															disabled="true" />
											</td><td>
											</td><td>
											
											</td>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.makerRemark" />
											</td>
											<td>

												<html:textarea property="remarks" styleId="remarks"
													value="${knockOffSearchList[0].remarks}" styleClass="text"
													disabled="true"></html:textarea>
											</td>
											<td>
												<bean:message key="lbl.authorRemarks" />
											</td>
											<td>
												<html:textarea styleClass="text" property="authorRemarks"
													disabled="true"
													value="${knockOffSearchList[0].authorRemarks}" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>

					<fieldset>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="knockoff">
							<tr>
								<th>
									<div align="center">
										<strong><bean:message key="lbl.receivableAdvices" />
										</strong>
									</div>
								</th>
								<th class="thnone">
									<div align="center">
										<strong><bean:message key="lbl.payableAdvices" />
										</strong>
									</div>
								</th>
							</tr>

							<tr valign="top">
								<td valign="top" class="bnone">
									<div>
										<table width="100%" border="0" cellspacing="0" cellpadding="1"
											id="dtable" class="knockoff none">
											<tr>
												<th>
													<strong><bean:message key="lbl.loanAccountNo" />
													</strong>
												</th>
												<th>
													<b><bean:message key="lbl.charges" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.adviceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.balanceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.amountInProcess" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.knockOffAmount" />
													</b>
												</th>
												<th class="bnone">
													<b><bean:message key="lbl.select" />
													</b>
												</th>
											</tr>
											<%
												int i = 1;
											%>
											<logic:present name="loanDataListR">
												<logic:iterate id="loanDataListR1" name="loanDataListR">
													<tr>
														<td>
															${loanDataListR1.loanNumber}
														</td>
														<td>
															${loanDataListR1.charges}
														</td>
														<td>
															${loanDataListR1.originalAmountR}
														</td>
														<td>
															${loanDataListR1.balanceAmountR}
														</td>
														<td>
															${loanDataListR1.amountInProcessR}
														</td>

														<td>
															<input type="hidden" name="originalAmountR"
																id="originalAmountR<%=i%>"
																value="${loanDataListR1.originalAmountR}" />
															<input type="hidden" name="balanceAmountR"
																id="balanceAmountR<%=i%>"
																value="${loanDataListR1.balanceAmountR}" />
															<input type="hidden" name="txnAdviceIdR"
																id="txnAdviceIdR<%=i%>"
																value="${loanDataListR1.txnAdviceIdR}" />
															<input type="hidden" name="amountInProcessR"
																id="amountInProcessR<%=i%>"
																value="${loanDataListR1.amountInProcessR}" />
															<input type="hidden" name="knockOffIdR"
																id="knockOffIdR<%=i%>"
																value="${loanDataListR1.knockOffIdR}" />

															<logic:equal name="loanDataListR1"
																property="knockOffAmountR" value="0.0000">
																<input type="text" class="text4"
																	id="knockOffAmountR<%=i%>" name="knockOffAmountR"
																	disabled="disabled" />
															</logic:equal>

															<logic:notEqual name="loanDataListR1"
																property="knockOffAmountR" value="0.0000">

																<input type="text" class="text4"
																	id="knockOffAmountR<%=i%>" name="knockOffAmountR"
																	value="${loanDataListR1.knockOffAmountR}"
																	disabled="disabled" style="text-align: right; width:90px;" />
															</logic:notEqual>

														</td>
														<td class="bnone">
															<logic:equal name="loanDataListR1"
																property="knockOffAmountR" value="0.0000">
																<input type="checkbox" name="chk" id="chkR<%=i%>"
																	disabled="disabled"
																	value="${loanDataListR1.txnAdviceIdR}" />
															</logic:equal>

															<logic:notEqual name="loanDataListR1"
																property="knockOffAmountR" value="0.0000">
																<input type="checkbox" name="chk" id="chkR<%=i%>"
																	value="${loanDataListR1.txnAdviceIdR}"
																	checked="checked" disabled="disabled" />
															</logic:notEqual>


														</td>
														<%
															i++;
														%>

													</tr>
												</logic:iterate>
												<tr class="white1">
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														<b><bean:message key="lbl.Total" />
														</b>
													</td>
													<td>
														<html:text styleClass="text4" styleId="totalReveivable"
															property="totalReveivable"
															value="${totalR[0].totalReveivable}" disabled="true"
															style="text-align:right; width:90px;" />
													</td>
													<td class="bnone">
														&nbsp;
													</td>
												</tr>
											</logic:present>

										</table>
									</div>
								</td>
								<td class="bnone">
									<div>
										<table width="100%" border="0" cellspacing="0" cellpadding="1"
											id="dtable1" class="knockoff rtnone">
											<tr>
												<th>
													<b><bean:message key="lbl.select" />
													</b>
												</th>
												<th>
													<strong><bean:message key="lbl.loanAccountNo" />
													</strong>
												</th>
												<th>
													<b><bean:message key="lbl.charges" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.adviceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.balanceAmount" />
													</b>
												</th>
												<th>
													<b><bean:message key="lbl.amountInProcess" />
													</b>
												</th>
												<th class="bnone">
													<b><bean:message key="lbl.knockOffAmount" />
													</b>
												</th>
											</tr>
											<%
												int j = 1;
											%>
											<logic:present name="loanDataListP">
												<logic:iterate id="loanDataListP1" name="loanDataListP">
													<tr class="white1">
														<td>
															<logic:equal name="loanDataListP1"
																property="knockOffAmountP" value="0.0000">
																<input type="checkbox" name="chk" id="chkP<%=j%>"
																	disabled="disabled"
																	value="${loanDataListP1.txnAdviceIdP }" />
															</logic:equal>
															<logic:notEqual name="loanDataListP1"
																property="knockOffAmountP" value="0.0000">
																<input type="checkbox" name="chk" id="chkP<%=j%>"
																	value="${loanDataListP1.txnAdviceIdP }"
																	checked="checked" disabled="disabled" />
															</logic:notEqual>

														</td>
														<td>
															${loanDataListP1.loanNumber }
														</td>
														<td>
															${loanDataListP1.charges }
														</td>
														<td>
															${loanDataListP1.originalAmountP }
														</td>
														<td>
															${loanDataListP1.balanceAmountP }
														</td>
														<td>
															${loanDataListP1.amountInProcessP }
														</td>

														<td class="bnone">
															<logic:equal name="loanDataListP1"
																property="knockOffAmountP" value="0.0000">
																<input type="text" class="text4"
																	id="knockOffAmountP<%=j%>" name="knockOffAmountP"
																	value="" disabled="disabled" />
															</logic:equal>

															<logic:notEqual name="loanDataListP1"
																property="knockOffAmountP" value="0.0000">
																<input type="text" class="text4"
																	id="knockOffAmountP<%=j%>" name="knockOffAmountP"
																	value="${loanDataListP1.knockOffAmountP }"
																	disabled="disabled" style="text-align: right;width:90px;" />
															</logic:notEqual>

															<input type="hidden" name="originalAmountP"
																id="originalAmountP<%=j%>"
																value="${loanDataListP1.originalAmountP }" />
															<input type="hidden" name="balanceAmountP"
																id="balanceAmountP<%=j%>"
																value="${loanDataListP1.balanceAmountP }" />
															<input type="hidden" name="txnAdviceIdP"
																id="txnAdviceIdP<%=j%>"
																value="${loanDataListP1.txnAdviceIdP }" />
															<input type="hidden" name="amountInProcessP"
																id="amountInProcessP<%=j%>"
																value="${loanDataListP1.amountInProcessP }" />
															<input type="hidden" name="knockOffIdP"
																id="knockOffIdP<%=j%>"
																value="${loanDataListP1.knockOffIdP }" />
														</td>
														<%
															j++;
														%>
													</tr>
												</logic:iterate>
												<tr class="white1">
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													<td>
														<b><bean:message key="lbl.Total" />
														</b>
													</td>
													<td class="bnone">
														<html:text styleClass="text4" styleId="totalPayable"
															property="totalPayable" value="${totalP[0].totalPayable}"
															disabled="true" style="text-align:right; width:90px;" />
														<br>
													</td>
												</tr>
											</logic:present>
										</table>
									</div>
								</td>
							</tr>
						</table>
						<html:hidden property="adviceTypeR" styleId="adviceTypeR"
							value="R" />
						<html:hidden property="adviceTypeP" styleId="adviceTypeP"
							value="P" />
						<html:hidden property="loanId" styleId="loanId"
							value="${loanDataListR[0].loanId}" />
					</fieldset>
					<br />
				</html:form>

			</logic:present>
		</div>



	</div>
	<script>
	setFramevalues("KnockOffMaker");
</script>
</body>
</html:html>

<logic:present name="sms">
	<script type="text/javascript">	
		if('<%=request.getAttribute("sms")%>'=='S')
		{
			if('<%=request.getAttribute("sms2")%>'=='F')
			{
				alert('<bean:message key="msg.ForwardSuccessfully" />');
				window.location="<%=request.getContextPath()%>/knockOffSearchBehindAction.do?method=knockOffMakerSearch";
			}else 
				alert('<bean:message key="msg.DataSaved" />');
		}
		else if('<%=request.getAttribute("sms")%>' == 'E') {
		alert('<bean:message key="msg.DataNotSaved" />');
	}else if('<%=request.getAttribute("sms").toString()%>'=="DS")
		{
			alert("<bean:message key="lbl.dataDeleted" />");
			window.location="<%=request.getContextPath()%>/knockOffSearchBehindAction.do?method=knockOffMakerSearch";
		}else if('<%=request.getAttribute("sms").toString()%>'=='DN')
		{
			alert("<bean:message key="lbl.dataNtDeleted" />");
			
		}
</script>
</logic:present>