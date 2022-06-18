<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Deviation Approval 
 	Documentation    :- 
 -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<%@include file="/JSP/sessioncheck.jsp"%>
<%@include file="/JSP/commonIncludeContent.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="" />
<script type="text/javascript" src="js/cpScript/creditProcessing.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/gcdScript/commonGcdFunctions.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/popup.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<logic:present name="back">

	<script type="text/javascript">
  alert("You can't move without saving before Lead Details!!!");
</script>

</logic:present>
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
<body
	onload="enableAnchor();checkChanges('deviationApprovalForm');document.getElementById('deviationApprovalForm').remark.focus();">
	<div align="center" class="opacity" style="display: none;"
		id="processingImage"></div>
	<div id="centercolumn">

		<div id="revisedcontainer">
			<logic:notPresent name="viewDeviationUND">
				<logic:notPresent name="viewDeviation">
					<html:form action="/saveDeviationApproval" method="POST"
						styleId="deviationApprovalForm">
						<input type="hidden" id="contextPath"
							value="<%=request.getContextPath()%>" />
						<fieldset>

							<legend>
								<bean:message key="lbl.deviationTab"></bean:message>
							</legend>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>

										<table width="100%" border="0" cellspacing="1" cellpadding="1">

											<tr>

												<td>
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															<td class="gridtd"><input type="hidden" id="dealId"
																class="text" name="dealId"
																value="${requestScope.dealId}" />
																<table width="100%" border="0" cellspacing="1"
																	cellpadding="4" id="gridTable1">
																	<tr class="white2">
																		
																		<td><b><bean:message key="lbl.stage" /></b></td>
																		<td><b><bean:message
																					key="lbl.ruleDescription" /></b></td>
																		<td><b><bean:message key="lbl.results" /></b></td>
																		<td><b><bean:message key="lbl.action" /></b></td>
																		<td><b><bean:message key="lbl.approvalLevel" /></b></td>
																		<td><b><bean:message key="lbl.ruleCode" /></b></td>
																		<td><b><bean:message key="lbl.remark" /></b></td>
																		<td><b><bean:message key="lbl.approve" /></b></td>

																	</tr>

																	<logic:present name="list">
																		<logic:iterate id="sublist" name="list"
																			indexId="count">
																			<tr class="white1">

																				<td><input type="hidden"
																					id="policyDecisionIds<%=count.intValue()+1 %>"
																					class="text" name="policyDecisionIds"
																					value="${sublist.policyDecisionId }" /> <input
																					type="hidden"
																					id="dealDevId<%=count.intValue()+1 %>" class="text"
																					name="dealDevId" value="${sublist.dealId}" /> 
										<logic:equal
																					name="sublist" property="stageId" value="DC">
												DEAL CAPTURING
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="DD">
												DEDUPE REFERRAL
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="CBL">
												BUREAU INITIATION
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="FVI">
												FIELD VERIFICATION
										</logic:equal>
																				<logic:equal
																					name="sublist" property="stageId" value="FFC">
												FUND FLOW ANALYSIS
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="FAC">
												FINANCIAL ANALYSIS
										</logic:equal>
										</td>
																				<td>${sublist.ruleDesc}</td>
																				<td>${sublist.ruleResult }</td>
																				<td>${sublist.ruleAction }</td>
																				<td>${sublist.approvalLevel }</td>
																				<td>${sublist.ruleCode }</td>
																				<logic:equal name="sublist" property="recordStatus"
																				value="NO">
																				<td><input type="text"
																					id="remarks" disabled="true" class="text"
																					name="remarks" value="${sublist.remark }"
																					maxlength="100" /></td>
																				<td><html:select property="recStatuses"
																						styleId="recStatuses" disabled="true" styleClass="text7"
																						value="${sublist.recStatus }">
																						<html:option value="I">--Select--</html:option>
																						<html:option value="AP">Approved</html:option>
																						<html:option value="R">Reject</html:option>
																					</html:select></td>
																					</logic:equal>
																					<logic:notEqual name="sublist" property="recordStatus"
																				value="NO">
																				<td><input type="text"
																					id="remarks<%=count.intValue()+1 %>" class="text"
																					name="remarks" value="${sublist.remark }"
																					maxlength="100" /></td>
																				<td><html:select property="recStatuses"
																						styleId="recStatuses${count+1}" styleClass="text7"
																						value="${sublist.recStatus }">
																						<html:option value="I">--Select--</html:option>
																						<html:option value="AP">Approved</html:option>
																						<html:option value="R">Reject</html:option>
																					</html:select></td>
																					</logic:notEqual>
																			</tr>
																		</logic:iterate>
																	</logic:present>
																</table></td>
														</tr>
													</table>
												</td>
											</tr>

										</table>

									</td>
								</tr>

							</table>

						</fieldset>
						<!-- Changes by Manisha Starts-->
						<fieldset>
							<legend>
								<bean:message key="lbl.manualDeviation"></bean:message>
							</legend>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>

										<table width="100%" border="0" cellspacing="1" cellpadding="1">

											<tr>

												<td>
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															<td class="gridtd"><input type="hidden" id="dealId"
																class="text" name="dealId"
																value="${requestScope.dealId}" />
																<table width="100%" border="0" cellspacing="1"
																	cellpadding="4" id="gridTable">
																	<tr class="white2">
																		<td width="3%"><input type="checkbox"
																		name="chk" id="allchk" onclick="allChecked();" /></td>
																		<td><b><bean:message key="lbl.stage" /></b></td>
																		<td><b><bean:message
																					key="lbl.ruleDescription" /></b></td>
																		<td><b><bean:message key="lbl.results" /></b></td>
																		<td><b><bean:message key="lbl.action" /></b></td>
																		<td><b><bean:message key="lbl.approvalLevel" /></b></td>
																		<td><b><bean:message key="lbl.makerRemarks" /></b></td>
																		<td><b><bean:message key="lbl.remark" /></b></td>
																		<td><b><bean:message key="lbl.approve" /></b></td>

																	</tr>

																	<logic:present name="manualDevList">
																		<logic:iterate id="list" name="manualDevList"
																			indexId="count">
																			<tr class="white1">

																				
																					<td><input type="checkbox" name="chk"
																						id="chk<%=count.intValue()+1%>"
																						value="${list.manualDevId }" /></td>
																				

																				<td><input type="hidden"
																					id="manualDeviId<%=count.intValue()+1 %>"
																					class="text" name="manualDeviId"
																					value="${list.manualDevId}" /> <input
																					type="hidden"
																					id="dealDevId<%=count.intValue()+1 %>" class="text"
																					name="dealDevId" value="${list.dealId}" />
																					${list.stage }</td>
																				<td><input type="hidden"
																					id="manualPolicyId<%=count.intValue()+1 %>"
																					class="text" name="manualPolicyId"
																					value="${list.manualId }" /> ${list.ruleDesc }</td>
																				<td>${list.ruleResult }</td>
																				<td><logic:equal name="list"
																						property="ruleAction" value="D">
												DEVIATION
										</logic:equal></td>

																				<td>${list.approvalLevel }</td>
																				<td>${list.remark }</td>
																				<logic:equal name="list" property="recordStatus"
																				value="NO">
																				<td><input type="text"
																					id="manualRemark"
																					class="text" name="manualRemark" disabled="true"
																					value="${list.authorRemark }" maxlength="100" /></td>
																				<td><html:select property="manRecStatus"
																						styleId="manRecStatus" disabled="true"
																						styleClass="text7" value="${list.manRecStat }">
																						<html:option value="I">--Select--</html:option>
																						<html:option value="AP">Approved</html:option>
																						<html:option value="R">Reject</html:option>
																						<html:option value="A">Approved</html:option>
																						<html:option value="X">Reject</html:option>
																					</html:select></td>
																					</logic:equal>
																					<logic:notEqual name="list" property="recordStatus"
																				value="NO">
																				<td><input type="text"
																					id="manualRemark<%=count.intValue()+1 %>"
																					class="text" name="manualRemark"
																					value="${list.authorRemark }" maxlength="100" /></td>
																				<td><html:select property="manRecStatus"
																						styleId="manRecStatus${count+1}"
																						styleClass="text7" value="${list.manRecStat }">
																						<html:option value="I">--Select--</html:option>
																						<html:option value="AP">Approved</html:option>
																						<html:option value="R">Reject</html:option>
																					</html:select></td>
																					</logic:notEqual>
																			</tr>
																		</logic:iterate>
																	</logic:present>
																</table></td>
														</tr>
													</table>
												</td>
											</tr>

										</table>

									</td>
								</tr>

							</table>

						</fieldset>
						<tr>
							<td>
								<button type="button" name="button" value="Save"
									class="topformbutton2"
									onclick="return onSaveDeviationApproval();" title="Alt+V"
									accesskey="V">
									<bean:message key="button.save" />
								</button>
							</td>
						</tr>
						<tr>
							<td>
								<button type="button" name="button" value="Forward"
									class="topformbutton2"
									onclick="return forwardDeviationApproval();" title="Alt+F"
									accesskey="F">
									<bean:message key="button.forward" />
								</button>
							</td>
						</tr>
						<tr>
							<td><button type="button" name="delete"
									class="topformbutton2" id="delete" title="Alt+D" accesskey="D"
									value="Delet" onclick="deleteDeviation();">
									<bean:message key="button.delete" />
								</button></td>
						</tr>
						<tr>
							<td>
								<button type="button" name="specialCondition"
									id="specialCondition" title="Alt+S" accesskey="S"
									class="topformbutton4"
									onclick="callOnLinkOrButtonWindow('specialCondition');openSpecialCondition('${sessionScope.dealId}');">
									<bean:message key="button.specialCondition" />
								</button>
							</td>
						</tr>
						<!-- Changes by Manisha Ends -->

						<br />
					</html:form>
				</logic:notPresent>

				<logic:present name="viewDeviation">

					<fieldset>

						<table cellspacing=0 cellpadding=0 width="100%" border=0>
							<tr>
								<td class="gridtd">
									<table width="100%" border="0" cellspacing="1" cellpadding="1">
										<tr class="white2">
											<td><b><bean:message key="lbl.dealNo" /></b></td>
											<td>${dealHeader[0].dealNo}</td>
											<td><b><bean:message key="lbl.date" /></b></td>
											<td>${dealHeader[0].dealDate}</td>
											<td><b><bean:message key="lbl.customerName" /></b></td>
											<td colspan="3">${dealHeader[0].dealCustomerName}</td>
										</tr>
										<tr class="white2">
											<td><b><bean:message key="lbl.productType" /></b></td>
											<td>${dealHeader[0].dealProductCat}</td>
											<td><b><bean:message key="lbl.product" /></b></td>
											<td>${dealHeader[0].dealProduct}</td>
											<td><b><bean:message key="lbl.scheme" /></b></td>
											<td>${dealHeader[0].dealScheme}</td>
											<td><b><bean:message key="lbl.currentStage" /></b></td>
											<td>Deal Capturing</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

					</fieldset>

					<html:form action="/saveDeviationApproval" method="post"
						styleId="deviationApprovalForm">
						<input type="hidden" id="contextPath"
							value="<%=request.getContextPath()%>" />
						<fieldset>

							<legend>
								<bean:message key="lbl.deviationTab"></bean:message>
							</legend>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>

										<table width="100%" border="0" cellspacing="1" cellpadding="1">

											<tr>

												<td>
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															<td class="gridtd"><input type="hidden" id="dealId"
																class="text" name="dealId"
																value="${requestScope.dealId}" />
																<table width="100%" border="0" cellspacing="1"
																	cellpadding="4">
																	<tr class="white2">
																		
																		<td><b><bean:message key="lbl.stage" /></b></td>
																		<td><b><bean:message
																					key="lbl.ruleDescription" /></b></td>
																		<td><b><bean:message key="lbl.results" /></b></td>
																		<td><b><bean:message key="lbl.action" /></b></td>
																		<td><b><bean:message key="lbl.approvalLevel" /></b></td>
																		<td><b><bean:message key="lbl.ruleCode" /></b></td>
																		<td><b><bean:message key="lbl.remark" /></b></td>
																		<td><b><bean:message key="lbl.approve" /></b></td>

																	</tr>

																	<logic:present name="list">
																		<logic:notEmpty name="list">
																			<logic:iterate id="sublist" name="list">
																				<tr class="white1">

																					<td><input type="hidden"
																						id="policyDecisionIds" class="text"
																						name="policyDecisionIds"
																						value="${sublist.policyDecisionId }" /> 
										<logic:equal
																					name="sublist" property="stageId" value="DC">
												DEAL CAPTURING
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="DD">
												DEDUPE REFERRAL
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="CBL">
												BUREAU INITIATION
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="FVI">
												FIELD VERIFICATION
										</logic:equal>
																				<logic:equal
																					name="sublist" property="stageId" value="FFC">
												FUND FLOW ANALYSIS
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="FAC">
												FINANCIAL ANALYSIS
										</logic:equal>
										</td>
																					<td>${sublist.ruleDesc}</td>
																					<td>${sublist.ruleResult }</td>
																					<td>${sublist.ruleAction }</td>
																					<td>${sublist.approvalLevel }</td>
																					<td>${sublist.ruleCode }</td>
																					<td><input type="text" id="remarks"
																						class="text" name="remarks"
																						value="${sublist.remark }" maxlength="100"
																						disabled="disabled" /></td>
																					<td><html:select property="recStatuses"
																							styleId="recStatuses" styleClass="text7"
																							disabled="true" value="${sublist.recStatus }">
																							<html:option value="I">--Select--</html:option>
																							<html:option value="A">Approved</html:option>
																							<html:option value="X">Reject</html:option>
																							<html:option value="AP">Approved</html:option>
																							<html:option value="R">Reject</html:option>
																						</html:select></td>
																				</tr>
																			</logic:iterate>
																		</logic:notEmpty>

																	</logic:present>
																</table></td>
														</tr>
													</table>
												</td>
											</tr>

										</table>

									</td>
								</tr>
								<%-- <tr><td> <html:button property="button" value="Save" styleClass="topformbutton2" onclick="return saveDeviationApproval();"> </html:button> </td></tr>--%>
							</table>

						</fieldset>
						<br />


						<!-- Changes by Manisha Starts-->
						<fieldset>
							<legend>
								<bean:message key="lbl.manualDeviation"></bean:message>
							</legend>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>

										<table width="100%" border="0" cellspacing="1" cellpadding="1">

											<tr>

												<td>
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															<td class="gridtd">

																<table width="100%" border="0" cellspacing="1"
																	cellpadding="4">
																	<tr class="white2">
																		
																		<td><b><bean:message key="lbl.stage" /></b></td>
																		<td><b><bean:message
																					key="lbl.ruleDescription" /></b></td>
																		<td><b><bean:message key="lbl.results" /></b></td>
																		<td><b><bean:message key="lbl.action" /></b></td>
																		<td><b><bean:message key="lbl.approvalLevel" /></b></td>
																		<td><b><bean:message key="lbl.makerRemarks" /></b></td>
																		<td><b><bean:message key="lbl.remark" /></b></td>
																		<td><b><bean:message key="lbl.approve" /></b></td>

																	</tr>

																	<logic:present name="manualDevList">
																		<logic:iterate id="list" name="manualDevList">
																			<tr class="white1">
																			

																				<td><input type="hidden" id="manualDeviId"
																					class="text" name="manualDeviId"
																					value="${list.manualDevId}" /> <input
																					type="hidden" id="dealDevId" class="text"
																					name="dealDevId" value="${list.dealId}" /> 
										<logic:equal
																					name="list" property="stage" value="DC">
												DEAL CAPTURING
										</logic:equal>
										<logic:equal
																					name="list" property="stage" value="DD">
												DEDUPE REFERRAL
										</logic:equal>
										<logic:equal
																					name="list" property="stage" value="CBL">
												BUREAU INITIATION
										</logic:equal>
										<logic:equal
																					name="list" property="stage" value="FVI">
												FIELD VERIFICATION
										</logic:equal>
																				<logic:equal
																					name="list" property="stage" value="FFC">
												FUND FLOW ANALYSIS
										</logic:equal>
										<logic:equal
																					name="list" property="stage" value="FAC">
												FINANCIAL ANALYSIS
										</logic:equal>
										</td>
																				<td><input type="hidden" id="policyDecisionIds"
																					class="text" name="policyDecisionIds"
																					value="${list.policyDecisionId }" />
																					${list.ruleDesc }</td>
																				<td>${list.ruleResult }</td>
																				<td><logic:equal name="list"
																						property="ruleAction" value="D">
													DEVIATION
											</logic:equal></td>

																				<td>${list.approvalLevel }</td>
																				<td>${list.remark }</td>
																				<td><input type="text" id="manualRemark"
																					class="text" name="manualRemark"
																					value="${list.authorRemark }" maxlength="100"
																					disabled="disabled" /></td>
																				<td><html:select property="manRecStatus"
																						styleId="manRecStatus" styleClass="text7"
																						disabled="true" value="${list.manRecStat }">
																						<html:option value="I">--Select--</html:option>
																						<html:option value="A">Approved</html:option>
																						<html:option value="X">Reject</html:option>
																						<html:option value="AP">Approved</html:option>
																						<html:option value="R">Reject</html:option>
																					</html:select></td>
																			</tr>
																		</logic:iterate>



																		<logic:empty name="manualDevList">
																			<logic:empty name="list">
																				<script type="text/javascript">
								
								            alert('No Data Found');
	
							             	   </script>
																			</logic:empty>
																		</logic:empty>
																	</logic:present>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>

										</table>

									</td>
								</tr>

							</table>

						</fieldset>

					</html:form>

				</logic:present>
			</logic:notPresent>
			<logic:present name="viewDeviationUND">
				<fieldset>

					<table cellspacing=0 cellpadding=0 width="100%" border=0>
						<tr>
							<td class="gridtd">
								<table width="100%" border="0" cellspacing="1" cellpadding="1">
									<tr class="white2">
										<td><b><bean:message key="lbl.dealNo" /></b></td>
										<td>${dealHeader[0].dealNo}</td>
										<td><b><bean:message key="lbl.date" /></b></td>
										<td>${dealHeader[0].dealDate}</td>
										<td><b><bean:message key="lbl.customerName" /></b></td>
										<td colspan="3">${dealHeader[0].dealCustomerName}</td>
									</tr>
									<tr class="white2">
										<td><b><bean:message key="lbl.productType" /></b></td>
										<td>${dealHeader[0].dealProductCat}</td>
										<td><b><bean:message key="lbl.product" /></b></td>
										<td>${dealHeader[0].dealProduct}</td>
										<td><b><bean:message key="lbl.scheme" /></b></td>
										<td>${dealHeader[0].dealScheme}</td>
										<td><b><bean:message key="lbl.currentStage" /></b></td>
										<td>Deal Capturing</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

				</fieldset>

				<html:form action="/saveDeviationApproval" method="post"
					styleId="deviationApprovalForm">
					<input type="hidden" id="contextPath"
						value="<%=request.getContextPath()%>" />

					<fieldset>

						<legend>
							<bean:message key="lbl.deviationTab"></bean:message>
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>

											<td>
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="gridtd"><input type="hidden" id="dealId"
															class="text" name="dealId" value="${requestScope.dealId}" />
															<table width="100%" border="0" cellspacing="1"
																cellpadding="4" id="gridTable1">
																<tr class="white2">
																	<!-- <td width="3%"><input type="checkbox"
																		name="allchk" id="allchk" onclick="allChecked();" /></td> -->
																	<td><b><bean:message key="lbl.stage" /></b></td>
																	<td><b><bean:message key="lbl.ruleDescription" /></b></td>
																	<td><b><bean:message key="lbl.results" /></b></td>
																	<td><b><bean:message key="lbl.action" /></b></td>
																	<td><b><bean:message key="lbl.approvalLevel" /></b></td>
																	<td><b><bean:message key="lbl.ruleCode" /></b></td>
																	<td><b><bean:message key="lbl.remark" /></b></td>
																	<td><b><bean:message key="lbl.approve" /></b></td>

																</tr>

																<logic:present name="list">
																	<logic:iterate id="sublist" name="list" indexId="count">
																		<tr class="white1">

																			<td><input type="hidden"
																				id="policyDecisionIds<%=count.intValue()+1 %>"
																				class="text" name="policyDecisionIds"
																				value="${sublist.policyDecisionId }" /> <input
																				type="hidden" id="dealDevId<%=count.intValue()+1 %>"
																				class="text" name="dealDevId"
																				value="${sublist.dealId}" /> 
																				<logic:equal
																					name="sublist" property="stageId" value="DC">
												DEAL CAPTURING
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="DD">
												DEDUPE REFERRAL
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="CBL">
												BUREAU INITIATION
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="FVI">
												FIELD VERIFICATION
										</logic:equal>
																				<logic:equal
																					name="sublist" property="stageId" value="FFC">
												FUND FLOW ANALYSIS
										</logic:equal>
										<logic:equal
																					name="sublist" property="stageId" value="FAC">
												FINANCIAL ANALYSIS
										</logic:equal>
										</td>
																			<td>${sublist.ruleDesc}</td>
																			<td>${sublist.ruleResult }</td>
																			<td>${sublist.ruleAction }</td>

																			<td>${sublist.approvalLevel }</td>
																			<td>${sublist.ruleCode }</td>
																			<logic:equal name="functionId" value="3000296">
																			<logic:equal name="sublist" property="recordStatus"
																				value="NO">
																				<td><input type="text"
																					id="remarks<%=count.intValue()+1 %>" class="text"
																					name="remarks" disabled="disabled"
																					value="${sublist.remark }" maxlength="100" /></td>
									
																				<td><html:select property="recStatuses"
																						styleId="recStatuses" styleClass="text7"
																						disabled="true" value="${sublist.recStatus }">
																						<html:option value="I">--Select--</html:option>
																						<html:option value="A">Approved</html:option>
																						<html:option value="X">Reject</html:option>
																						<html:option value="AP">Approved</html:option>
																						<html:option value="R">Reject</html:option>
																					</html:select></td>

																			</logic:equal>
																			<logic:notEqual name="sublist"
																				property="recordStatus" value="NO">
																				<td><input type="text"
																					id="remarks<%=count.intValue()+1 %>" class="text"
																					name="remarks" value="${sublist.remark }"
																					maxlength="100" /></td>
																				<td><html:select property="recStatuses"
																						styleId="recStatuses${count+1}" styleClass="text7"
																						value="${sublist.recStatus }">
																						<html:option value="I">--Select--</html:option>
																						<html:option value="AP">Approved</html:option>
																						<html:option value="R">Reject</html:option>
																					</html:select></td>
																			</logic:notEqual>
																			</logic:equal>
																			<logic:notEqual name="functionId" value="3000296">
																			<td><input type="text"
																					id="remarks<%=count.intValue()+1 %>" class="text"
																					name="remarks" disabled="disabled"
																					value="${sublist.remark }" maxlength="100" /></td>
									
																				<td><html:select property="recStatuses"
																						styleId="recStatuses" styleClass="text7"
																						disabled="true" value="${sublist.recStatus }">
																						<html:option value="I">--Select--</html:option>
																						<html:option value="A">Approved</html:option>
																						<html:option value="X">Reject</html:option>
																						<html:option value="AP">Approved</html:option>
																						<html:option value="R">Reject</html:option>
																					</html:select></td>
																			
																			</logic:notEqual>
																		</tr>
																	</logic:iterate>
																</logic:present>
															</table></td>
													</tr>
												</table>
											</td>
										</tr>

									</table>

								</td>
							</tr>

						</table>

					</fieldset>


					<!-- Changes by Manisha Starts-->
					<fieldset>
						<legend>
							<bean:message key="lbl.manualDeviation"></bean:message>
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>

											<td>
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="gridtd"><input type="hidden" id="dealId"
															class="text" name="dealId" value="${requestScope.dealId}" />
															<table width="100%" border="0" cellspacing="1"
																cellpadding="4" id="gridTable">
																<tr class="white2">
																	<td width="3%"><input type="checkbox"
																		name="chk" id="allchk" onclick="allChecked();" /></td>
																	<td><b><bean:message key="lbl.stage" /></b></td>
																	<td><b><bean:message key="lbl.ruleDescription" /></b></td>
																	<td><b><bean:message key="lbl.results" /></b></td>
																	<td><b><bean:message key="lbl.action" /></b></td>
																	<td><b><bean:message key="lbl.approvalLevel" /></b></td>
																	<td><b><bean:message key="lbl.makerRemarks" /></b></td>
																	<td><b><bean:message key="lbl.remark" /></b></td>
																	<td><b><bean:message key="lbl.approve" /></b></td>

																</tr>

																<logic:present name="manualDeviationUND">
																	<logic:iterate id="list" name="manualDeviationUND"
																		indexId="count">
																		<tr class="white1">
																		<logic:equal name="functionId" value="3000296">
																				<%-- <logic:equal name="list" property="recordStatus"
																				value="NO">
																				<td><input type="checkbox" name="chk"
																					id="chk<%=count.intValue()+1%>"
																					value="${list.manualDevId }" disabled="true" /></td>
																			</logic:equal>
																				<logic:notEqual name="list" property="recordStatus"
																				value="NO"> --%>
																				<td><input type="checkbox" name="chk"
																					id="chk<%=count.intValue()+1%>"
																					value="${list.manualDevId }" /></td>
																			<%-- </logic:notEqual> --%>
																			</logic:equal>
																			<logic:notEqual name="functionId" value="3000296">
																					<td><input type="checkbox" name="chk"
																					id="chk<%=count.intValue()+1%>"
																					value="${list.manualDevId }" disabled="true" /></td>
																			</logic:notEqual>

																			<input type="hidden"
																				id="manualDeviId<%=count.intValue()+1 %>"
																				class="text" name="manualDeviId"
																				value="${list.manualDevId}" />
																			<input type="hidden"
																				id="dealDevId<%=count.intValue()+1 %>" class="text"
																				name="dealDevId" value="${list.dealId}" />
																			<input type="hidden"
																				id="manualPolicyId<%=count.intValue()+1 %>"
																				class="text" name="manualPolicyId"
																				value="${list.manualId }" />
																			<td>
										<logic:equal
																					name="list" property="stage" value="DC">
												DEAL CAPTURING
										</logic:equal>
										<logic:equal
																					name="list" property="stage"  value="DD">
												DEDUPE REFERRAL
										</logic:equal>
										<logic:equal
																					name="list" property="stage"  value="CBL">
												BUREAU INITIATION
										</logic:equal>
										<logic:equal
																					name="list" property="stage"  value="FVI">
												FIELD VERIFICATION
										</logic:equal>
										<logic:equal
																					name="list" property="stage"  value="FFC">
												FUND FLOW ANALYSIS
										</logic:equal>
										<logic:equal
																					name="list" property="stage"  value="FAC">
												FINANCIAL ANALYSIS
										</logic:equal>
										</td>
																			<td><input type="hidden" id="policyDecisionIds"
																				class="text" name="policyDecisionIds"
																				value="${list.policyDecisionId }" />
																				${list.ruleDesc }</td>
																			<td>${list.ruleResult }</td>
																			<td><logic:equal name="list"
																					property="ruleAction" value="D">
													DEVIATION
											</logic:equal></td>

																			<td>${list.approvalLevel }</td>
																			<td>${list.remark }</td>
																			<logic:equal name="functionId" value="3000296">
																			<logic:equal name="list" property="recordStatus"
																				value="NO">
																				<td><input type="text" id="manualRemark"
																					class="text" name="manualRemark"
																					value="${list.authorRemark }" maxlength="100"
																					disabled="disabled" /></td>
																				<td><html:select property="manRecStatus"
																						styleId="manRecStatus" styleClass="text7"
																						disabled="true" value="${list.manRecStat }">
																						<html:option value="I">--Select--</html:option>
																						<html:option value="A">Approved</html:option>
																						<html:option value="X">Reject</html:option>
																						<html:option value="AP">Approved</html:option>
																						<html:option value="R">Reject</html:option>
																					</html:select></td>
																			</logic:equal>
																			<logic:notEqual name="list" property="recordStatus"
																				value="NO">
																				<td><input type="text" id="manualRemark<%=count.intValue()+1 %>"
																					class="text" name="manualRemark"
																					value="${list.authorRemark }" maxlength="100" /></td>
																				<td><html:select property="manRecStatus"
																						styleId="manRecStatus${count+1}"
																						styleClass="text7" value="${list.manRecStat }">
																						<html:option value="I">--Select--</html:option>
																						<html:option value="A">Approved</html:option>
																						<html:option value="X">Reject</html:option>
																					</html:select></td>
																			</logic:notEqual>
																			</logic:equal>
																			<logic:notEqual name="functionId" value="3000296">
																			<td><input type="text" id="manualRemark"
																					class="text" name="manualRemark"
																					value="${list.authorRemark }" maxlength="100"
																					disabled="disabled" /></td>
																				<td><html:select property="manRecStatus"
																						styleId="manRecStatus" styleClass="text7"
																						disabled="true" value="${list.manRecStat }">
																						<html:option value="I">--Select--</html:option>
																						<html:option value="A">Approved</html:option>
																						<html:option value="X">Reject</html:option>
																						<html:option value="AP">Approved</html:option>
																						<html:option value="R">Reject</html:option>
																					</html:select></td>
																			</logic:notEqual>
																		</tr>
																	</logic:iterate>
																</logic:present>
															</table></td>
													</tr>
												</table>
											</td>
										</tr>

									</table>

								</td>
							</tr>

						</table>

					</fieldset>
					<logic:equal name="functionId" value="3000296">
						<tr>
							<td>
								<button type="button" name="button" value="Save"
									class="topformbutton2"
									onclick="return forwardDeviationApproval();" title="Alt+V"
									accesskey="V">
									<bean:message key="button.save" />
								</button>
							</td>
						</tr>
						<tr>
							<td><button type="button" name="delete"
									class="topformbutton2" id="delete" title="Alt+D" accesskey="D"
									value="Delet" onclick="deleteDeviation();">
									<bean:message key="button.delete" />
								</button></td>
						</tr>
						<tr>
							<td><button type="button" name="specialCondition"
									id="specialCondition" title="Alt+S" accesskey="S"
									class="topformbutton4"
									onclick="callOnLinkOrButtonWindow('specialCondition');openSpecialCondition('${sessionScope.dealId}');">
									<bean:message key="button.specialCondition" />
								</button></td>
						</tr>
						<%--   <tr><td> <button type="button" name="button" value="Forward" class="topformbutton2" onclick="return forwardDeviationApproval();" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button> </td></tr> --%>
					</logic:equal>
				</html:form>
			</logic:present>
		</div>

	</div>

	<script>
	parent.menu.document.test.checkModifications.value = '';
	parent.menu.document.test.getFormName.value = "deviationApprovalForm";
</script>
</body>
<logic:present name="sms">
	<script type="text/javascript">
 if('<%=request.getAttribute("sms").toString()%>'=='A')
	{
		alert("<bean:message key="lbl.dataSave" />");
	
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.ForwardSuccessfully" />");
	 parent.location="<%=request.getContextPath()%>/deviationApprovalSearchBehindAction.do";
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		parent.location="<%=request.getContextPath()%>
		/deviationApprovalSearchBehindAction.do";
		}
	</script>
</logic:present>
</html:html>


