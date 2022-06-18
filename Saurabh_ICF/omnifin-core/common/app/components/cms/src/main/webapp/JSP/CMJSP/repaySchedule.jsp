<!-- 
Author Name :- Vishal Singh
Date of Creation :26-03-2012
Purpose :-  Repay Schedule Maker
-->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.login.roleManager.UserObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";

			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			String initiationDate = userobj.getBusinessdate();
	%>


	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
    <script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/cmRepaySchedule.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>



<style type="text/css">
	.readonly{
			width:150px !important;
	}
</style>

<!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<!-- jQuery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=path%>/js/jquery.simpletip-1.3.1.js"></script>
		
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
<body onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('updateRepayScheduleForm');document.getElementById('updateRepayScheduleForm').loanButton.focus();">
	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
		
	
		
	<div id=centercolumn>
		<div id=revisedcontainer>

			<logic:notPresent name="viewDueDate">
			
				<html:form action="/updateRepaySchedule" method="post"
					styleId="updateRepayScheduleForm">
				<logic:present name="image">
    	   		   <input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
                </logic:present>
    		    <logic:notPresent name="image">
    			   <input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		    </logic:notPresent>
    		       <input type="hidden" id="businessdate" value='${userobject.businessdate}'/>		
	               <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />	
                   <html:hidden property="path" styleId="path" value="<%=request.getContextPath()%>"/>
                   
                    <html:hidden property="basePath" styleId="basePath" value="<%=request.getContextPath()%>"/>
                    
                    <logic:present name="loanList">
                    	<input type="hidden" name="repayEffDate" id="repayEffDateAtDueDate" value="${loanList[0].repayEffDate}" />
                    </logic:present>
                    <logic:notPresent name="loanList">
                    	<input type="hidden" name="repayEffDate" id="repayEffDateAtDueDate" value="${repayScheduleList[0].repayEffDate}" />
                    </logic:notPresent>
                    
                     <logic:present name="loanList">
                    	<input type="hidden" name="loanFrequency" id="loanFrequency" value="${loanList[0].loanFrequency}" />
                    </logic:present>
                    <logic:notPresent name="loanList">
                    	<input type="hidden" name="loanFrequency" id="loanFrequency" value="${repayScheduleList[0].loanFrequency}" />
                    </logic:notPresent>
					
					<fieldset>
						<legend>
							<bean:message key="lbl.repayScheduleDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
										
										<logic:present name="loanList">
										<input type="hidden" name="reschId" id="reschId"
										value="${loanList[0].reschId}" />
										</logic:present>
										
										<logic:notPresent name="loanList">
										<html:hidden property="reschId" styleId="reschId"
										 value="${repayScheduleList[0].reschId}" />
										</logic:notPresent>
									

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td width="25%">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<logic:present name="repayScheduleList">		                                   
											<td width="25%">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="${repayScheduleList[0].loanNo}" readonly="true" maxlength="50"  />
												
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
													
													<logic:present name="loanList">
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${loanList[0].lbxLoanNoHID}" />
													</logic:present>
													
													<logic:notPresent name="loanList">
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${repayScheduleList[0].lbxLoanNoHID}" />
													</logic:notPresent>
											</td>
											
											    </logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td width="25%">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="" readonly="true"  />
												<html:button property="loanButton" styleId="loanButton" value=" "
													onclick="openLOVCommon(298,'updateRepayScheduleForm','loanNo','','', '','','generateRepayScheduleValues','customerName');closeLovCallonLov1();" 
													styleClass="lovbutton"> </html:button>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="" />
											</td>
											
											</logic:notPresent>
											
											
											<td width="25%">
												<bean:message key="lbl.customerName" />
											</td>
											
											<logic:present name="repayScheduleList">
											<td width="25%">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="${repayScheduleList[0].customerName}" tabindex="-1" />
													
											</td>
											</logic:present>
											<logic:notPresent name="repayScheduleList">
											<td width="25%">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="" tabindex="-1" />
													
											</td>
											</logic:notPresent>		
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.product" />
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="${repayScheduleList[0].product}" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											</logic:notPresent>
											
											<td>
												<bean:message key="lbl.scheme" />
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="${repayScheduleList[0].scheme}" 
													disabled="true" tabindex="-1"/>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="" 
													disabled="true" tabindex="-1"/>
											</td>
											</logic:notPresent>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<logic:present name="repayScheduleList">
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value="${repayScheduleList[0].disbursedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value=""
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											</logic:notPresent>
											<td>
												<bean:message key="lbl.loanAmount" />
											</td>
											
											<logic:present name="repayScheduleList">
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" value="${repayScheduleList[0].outstandingLoanAmount}"
													readonly="true" tabindex="-1" />
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" value=""
													readonly="true" tabindex="-1" />
											</td>
											</logic:notPresent>
										</tr>

																				
										<tr>
											<td>
												<bean:message key="lbl.emi"/>
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="emi" 
													styleId="emi" value="${repayScheduleList[0].emi}"
													readonly="true" tabindex="-1"
													style="text-align:right;" />
											</td>
											</logic:present>
											
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="emi" 
													styleId="emi" value=""
													readonly="true" tabindex="-1"
													style="text-align:right;" />
											</td>
											</logic:notPresent>
											
											<td>
												<bean:message key="lbl.reqRefNo" />
											</td>
											
											<logic:present name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="requestRefNo"
													styleId="requestRefNo" value="${repayScheduleList[0].requestRefNo}" maxlength="10"
													
													/>
											</td>
											</logic:present>
											
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="requestRefNo"
													styleId="requestRefNo" value="" maxlength="10"
													
													/>
											</td>
											</logic:notPresent>
											
											
										</tr>
										<tr>
										
										    <td>
												<bean:message key="lbl.currerntDueDay"/>
											</td>
											
											<td>
											
											<logic:notPresent name="repayScheduleList">
											
													<html:text property="currerntDueDay" styleId="currerntDueDay"
													styleClass="text" readonly="true" tabindex="-1"
													readonly="true" tabindex="-1"
													value=""/>
											</logic:notPresent>
											<logic:present name="repayScheduleList">
											
												<html:text property="currerntDueDay" styleId="currerntDueDay"
													styleClass="text" readonly="true" tabindex="-1"
													readonly="true" tabindex="-1"
													value="${repayScheduleList[0].currentDueDay}"/>
													
											</logic:present>
											
												
											</td>
											
											
																	
										
											<td>
												<bean:message key="lbl.currentDueDate" />
												
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<html:text property="curentDueDate" styleId="curentDueDate"
													styleClass="text" readonly="true" tabindex="-1"
													readonly="true" tabindex="-1"
													value="${repayScheduleList[0].curentDueDate}"/>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<html:text property="curentDueDate" styleId="curentDueDate"
													styleClass="text" readonly="true" tabindex="-1"
													readonly="true" tabindex="-1"
													value=""/>
											</td>
											</logic:notPresent>
											
										</tr>
										
										
										<tr>
											<td>
												<bean:message key="lbl.effectiveFromInstallment" />
												<font color="red">*</font>
											</td>
											<td>
											<logic:notPresent name="repayScheduleList">
												<html:text styleClass="text" property="deferralFromInstallment"
													styleId="deferralFromInstallment" 
													value="" maxlength="3"
													readonly="true" tabindex="-1" style="text-align:right;"/>
												<html:button property="dueDateDateButton" styleId="dueDateDateButton" value=" "
													onclick="openLOVCommon(219,'updateRepayScheduleForm','curentDueDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','deferralFromInstallment');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="" />
											</logic:notPresent>
											<logic:present name="repayScheduleList">
												<html:text styleClass="text" property="deferralFromInstallment"
													styleId="deferralFromInstallment" 
													value="${repayScheduleList[0].deferralFromInstallment}" maxlength="3"
													readonly="true" tabindex="-1" style="text-align:right;"/>
												<html:button property="dueDateDateButton" styleId="dueDateDateButton" value=" "
													onclick="openLOVCommon(219,'updateRepayScheduleForm','curentDueDate','loanNo','lbxInstlNo','lbxLoanNoHID','Please Select Loan','','deferralFromInstallment');closeLovCallonLov('loanNo');" 
													styleClass="lovbutton"> 
												</html:button>
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="${repayScheduleList[0].deferralFromInstallment}" />
										  </logic:present>
												
											</td>
											
										</tr>
										
										
										<tr>
											<td>
												<bean:message key="lbl.cycleDate" /><font color="red">*</font>
											</td>
											<logic:present name="repayScheduleList">
											<td>
										     <div id="LoanCycle">
		  	  	                              <html:select property="dueDay" styleClass="text" styleId="dueDay"  value="${repayScheduleList[0].dueDay}"  onchange="nullNextDue();">
	                                          <html:option value="">---Select----</html:option>
	                                          <logic:present name="cycle">
		                                      <html:optionsCollection name="cycle" label="name" value="id" /> 
		                                      </logic:present>
			                                  </html:select>
			                               </div>
											</td>
											</logic:present>
											<logic:notPresent name="repayScheduleList">
											<td>
										     <div id="LoanCycle">
		  	  	                              <html:select property="dueDay" styleClass="text" styleId="dueDay"  value=""  onchange="nullNextDue();">
	                                          <html:option value="">---Select----</html:option>
	                                          <logic:present name="cycle">
		                                      <html:optionsCollection name="cycle" label="name" value="id" /> 
		                                      </logic:present>
			                                  </html:select>
			                               </div>
											</td>
											</logic:notPresent>
											<td>
												<bean:message key="lbl.nextDueDate" />
												<font color="red">*</font>
											</td>
											<logic:present name="repayScheduleList">
											<td><html:text styleClass="text" property="nextDueDate"
													styleId="nextDueDate" value="${repayScheduleList[0].nextDueDate}" maxlength="18" onchange="checkDate('nextDueDate');checkDueDate(value);validateDate(value);"	 />
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td><html:text styleClass="text" property="nextDueDate"
													styleId="nextDueDate" value="" maxlength="18" onchange="checkDate('nextDueDate');checkDueDate(value);validateDate(value);"	 />
											</td>
											</logic:notPresent>
											
											
										</tr>
										<tr>
										   <td>
												<bean:message key="lbl.makerRemarks"/>
<!--												<font color="red">*</font>--><!-- Commented by Nishant -->
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<textarea name="makerRemarks" id="makerRemarks"
													maxlength="1000"  class="text">${repayScheduleList[0].makerRemarks}</textarea>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<textarea name="makerRemarks" id="makerRemarks"
													maxlength="1000"  class="text"></textarea>
											</td>
											</logic:notPresent>
											
											
											<td>
												<bean:message key="lbl.reschCharges" />
												<font color="red">*</font>
											</td>
											<logic:present name="repayScheduleList">
											<td><html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="${repayScheduleList[0].reschCharges}" maxlength="18" readonly="false" tabindex="-1" 
												onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" 
												onkeyup="checkNumber(this.value, event, 18,id);" style="text-align:right;" />
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td><html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="" maxlength="18"	readonly="false" tabindex="-1" 
												onfocus="keyUpNumber(this.value, event, 18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" 
												onkeyup="checkNumber(this.value, event, 18,id);"style="text-align:right;" />
											</td>
											</logic:notPresent>
											
										</tr>
										
										<tr>
										
										<td></td><td></td>
										    <td>
												<bean:message key="lbl.authorRemarks"/>
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<textarea name="authorRemarks" id="authorRemarks"
												disabled="disabled" tabindex="-1" class="text" >${repayScheduleList[0].authorRemarks}</textarea>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<textarea name="authorRemarks" id="authorRemarks"
												disabled="disabled" tabindex="-1" class="text"></textarea>
											</td>
											</logic:notPresent>
											
											
										</tr>
										</table>
										<table>
										<tr>
										
										
												<td>									
												  <button type="button"  name="calcInterest" 
							                      id="calcInterest" class="topformbutton3"  accesskey="R"
							                      onclick="return generateRepayScheduleReschCharges();" tabindex="8"><bean:message key="button.calcharge" /></button>
							             </td>
										
										
							             <td>   				                      
							                      
							                      <button type="button"  name="saveButton" 
							                      id="saveButton" class="topformbutton2"  title="Alt+V" accesskey="V" 
							                      onclick="return saveRepaySchedule();" tabindex="8"><bean:message key="button.save" /></button>
							               </td>
							               <td>        
							                      
							                      <button type="button"  name="forwardButton" 
							                   id="forwardButton" class="topformbutton2"  title="Alt+F" accesskey="F" 
							                      onclick="return validateRepaySchedule();" tabindex="8"><bean:message key="button.forward" /></button>
							             </td>
							             
							             <td>        
							                      
							                      <button type="button"  name="generateInstallmentPlan" 
							                      id="generateInstallmentPlan" class="topformbutton4"  title="Alt+R" accesskey="R" 
							                      onclick="return viewInstallmentPlanRepricing();" tabindex="8"><bean:message key="button.oldinstplan" /></button>
							             </td> 
							             <td>
							                      <button type="button"  name="generateRepaymentSch" 
							                      id="generateRepaymentSch" class="topformbutton4"  title="Alt+R" accesskey="R" 
							                      onclick="return viewRepaymentScheduleDisbursal();" tabindex="8"><bean:message key="button.oldrepay" /></button>
							             </td>
							              <td>
							                        <button type="button"  name="generateRepaymentSchNew" 
							                      id="generateRepaymentSchNew" class="topformbutton4"  title="Alt+R" accesskey="R" 
							                      onclick="return newRepaymentScheduleRepricing();" tabindex="8"><bean:message key="button.newrepay" /></button>
							                 						                       
							              </td>
							              <logic:present name="repayScheduleList">
							              <td>
							              	
												<button type="button" name="delete" id="delete" 
												class="topformbutton2" accesskey="D" title="Alt+D"
												onclick="return deleteDueDateDetails();"><bean:message key="button.delete" /></button>
							              </td>
							              </logic:present>
							              
							              </tr>
							        
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
				</logic:notPresent>
				
			<%--          view mode of jsp     --%>	
				
				<logic:present name="viewDueDate">
			
				
					<html:form action="/updateRepaySchedule" method="post"
					styleId="updateRepayScheduleForm">
				<logic:present name="image">
    	   		   <input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
                </logic:present>
    		    <logic:notPresent name="image">
    			   <input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		    </logic:notPresent>
    		       <input type="hidden" id="businessdate" value='${userobject.businessdate}'/>		
	               <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />	
                   <html:hidden property="path" styleId="path" value="<%=request.getContextPath()%>"/>
                   
                    <html:hidden property="basePath" styleId="basePath" value="<%=request.getContextPath()%>"/>
					
					<fieldset>
						<legend>
							<bean:message key="lbl.repayScheduleDetails" />
						</legend>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<input type="hidden" name="businessDate" id="businessDate"
										value="<%=initiationDate%>" />
										
										<logic:present name="loanList">
										<input type="hidden" name="reschId" id="reschId"
										value="${loanList[0].reschId}" />
										</logic:present>
										
										<logic:notPresent name="loanList">
										<html:hidden property="reschId" styleId="reschId"
										 value="${repayScheduleList[0].reschId}" />
										</logic:notPresent>
									

									<table width="100%" border="0" cellspacing="1" cellpadding="1">

										<tr>
											<td width="25%">
												<bean:message key="lbl.loanNo" />
												<font color="red">*</font>
											</td>
											<logic:present name="repayScheduleList">		                                   
											<td width="25%">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="${repayScheduleList[0].loanNo}" readonly="true" maxlength="50"  />
												
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
													
													<logic:present name="loanList">
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${loanList[0].lbxLoanNoHID}" />
													</logic:present>
													
													<logic:notPresent name="loanList">
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="${repayScheduleList[0].lbxLoanNoHID}" />
													</logic:notPresent>
											</td>
											
											    </logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td width="25%">
												<html:text styleClass="text" property="loanNo"
													styleId="loanNo" value="" readonly="true"  />
												<html:button property="loanButton" styleId="loanButton" value=" "
													onclick="openLOVCommon(298,'updateRepayScheduleForm','loanNo','','', '','','generateRepayScheduleValues','customerName');closeLovCallonLov1();" 
													styleClass="lovbutton"> </html:button>
												<input type="hidden" name="contextPath"
													value="<%=request.getContextPath()%>" id="contextPath" />
												<html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID"
													value="" />
											</td>
											
											</logic:notPresent>
											
											
											<td width="25%">
												<bean:message key="lbl.customerName" />
											</td>
											
											<logic:present name="repayScheduleList">
											<td width="25%">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="${repayScheduleList[0].customerName}" tabindex="-1" />
													
											</td>
											</logic:present>
											<logic:notPresent name="repayScheduleList">
											<td width="25%">
												<html:text styleClass="text" property="customerName"
													styleId="customerName" maxlength="50" disabled="true"
													value="" tabindex="-1" />
													
											</td>
											</logic:notPresent>		
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.product" />
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="${repayScheduleList[0].product}" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="product"
													styleId="product" value="" maxlength="50" 
													disabled="true" tabindex="-1"/>
											</td>
											</logic:notPresent>
											
											<td>
												<bean:message key="lbl.scheme" />
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="${repayScheduleList[0].scheme}" 
													disabled="true" tabindex="-1"/>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="scheme"
													styleId="scheme" maxlength="50" value="" 
													disabled="true" tabindex="-1"/>
											</td>
											</logic:notPresent>
										</tr>
										<tr>
											<td>
												<bean:message key="lbl.disbursedAmount" />
											</td>
											<logic:present name="repayScheduleList">
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value="${repayScheduleList[0].disbursedAmount}"
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td nowrap="nowrap">
												<html:text styleClass="text" property="disbursedAmount"
													styleId="disbursedAmount" maxlength="18" value=""
													disabled="true" tabindex="-1" style="text-align:right;"/>
											</td>
											</logic:notPresent>
											<td>
												<bean:message key="lbl.loanAmount" />
											</td>
											
											<logic:present name="repayScheduleList">
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" value="${repayScheduleList[0].outstandingLoanAmount}"
													readonly="true" tabindex="-1" />
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td nowrap="nowrap">
												<html:text styleClass="text"
													property="outstandingLoanAmount" style="text-align:right;"
													styleId="outstandingLoanAmount" maxlength="18" value=""
													readonly="true" tabindex="-1" />
											</td>
											</logic:notPresent>
										</tr>

																				
										<tr>
											<td>
												<bean:message key="lbl.emi"/>
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="emi" 
													styleId="emi" value="${repayScheduleList[0].emi}"
													readonly="true" tabindex="-1"
													style="text-align:right;" />
											</td>
											</logic:present>
											
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="emi" 
													styleId="emi" value=""
													readonly="true" tabindex="-1"
													style="text-align:right;" />
											</td>
											</logic:notPresent>
											
											<td>
												<bean:message key="lbl.reqRefNo" />
											</td>
											
											<logic:present name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="requestRefNo"
													styleId="requestRefNo" value="${repayScheduleList[0].requestRefNo}" maxlength="10"
														readonly="true" tabindex="-1"
													/>
											</td>
											</logic:present>
											
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<html:text styleClass="text" property="requestRefNo"
													styleId="requestRefNo" value="" maxlength="10"
													readonly="true" tabindex="-1"
													/>
											</td>
											</logic:notPresent>
											
											
										</tr>
										<tr>
										
										    <td>
												<bean:message key="lbl.currerntDueDay"/>
											</td>
											
											<td>
												<html:text property="currerntDueDay" styleId="currerntDueDay"
													styleClass="text" readonly="true" tabindex="-1"
													readonly="true" tabindex="-1"
													value="${repayScheduleList[0].currentDueDay}"/>
											</td>
											
											
																	
										
											<td>
												<bean:message key="lbl.currentDueDate" />
												
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<html:text property="curentDueDate" styleId="curentDueDate"
													styleClass="text" readonly="true" tabindex="-1"
													readonly="true" tabindex="-1"
													value="${repayScheduleList[0].curentDueDate}"/>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<html:text property="curentDueDate" styleId="curentDueDate"
													styleClass="text" readonly="true" tabindex="-1"
													readonly="true" tabindex="-1"
													value=""/>
											</td>
											</logic:notPresent>
											
										</tr>
										
										
										<tr>
											<td>
												<bean:message key="lbl.effectiveFromInstallment" />
												<font color="red">*</font>
											</td>
											<td>
												<html:text styleClass="text"  property="deferralFromInstallment"
													styleId="deferralFromInstallment" 
													value="${repayScheduleList[0].deferralFromInstallment}" maxlength="3"
													readonly="true" tabindex="-1" style="text-align:right;"/>
												
												<html:hidden property="lbxInstlNo" styleId="lbxInstlNo" value="${repayScheduleList[0].lbxInstlNo}" />
											</td>
											
										</tr>
										
										<tr>
											<td>
												<bean:message key="lbl.cycleDate" /><font color="red">*</font>
											</td>
											<logic:present name="repayScheduleList">
											<td>
										     <div id="LoanCycle">
		  	  	                              <html:select property="dueDay" styleClass="text" styleId="dueDay"  value="${repayScheduleList[0].dueDay}"   disabled="true" onchange="nullNextDue(); ">
	                                          <html:option value="">---Select----</html:option>
	                                          <logic:present name="cycle">
		                                      <html:optionsCollection name="cycle" label="name" value="id" /> 
		                                      </logic:present>
			                                  </html:select>
			                               </div>
											</td>
											</logic:present>
											<logic:notPresent name="repayScheduleList">
											<td>
										     <div id="LoanCycle">
		  	  	                              <html:select property="dueDay" styleClass="text" styleId="dueDay"  value=" " disabled="true" onchange="nullNextDue();">
	                                          <html:option value="">---Select----</html:option>
	                                          <logic:present name="cycle">
		                                      <html:optionsCollection name="cycle" label="name" value="id" /> 
		                                      </logic:present>
			                                  </html:select>
			                               </div>
											</td>
											</logic:notPresent>
											<td>
												<bean:message key="lbl.nextDueDate" />
												<font color="red">*</font>
											</td>
											<logic:present name="repayScheduleList">
											<td><html:text styleClass="text" property="nextDueDate1"
													readonly="true" styleId="nextDueDate1" value="${repayScheduleList[0].nextDueDate}" maxlength="18" onchange="checkDueDate(value);validateDate(value);"	 />
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td><html:text styleClass="text" property="nextDueDate1"
													readonly="true" styleId="nextDueDate1" value="" maxlength="18" onchange="checkDueDate(value);validateDate(value);"	 />
											</td>
											</logic:notPresent>
											
											
										</tr>
										<tr>
										   <td>
												<bean:message key="lbl.makerRemarks"/>
<!--												<font color="red">*</font>--><!-- Commented by Nishant -->
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<textarea name="makerRemarks" id="makerRemarks"
													disabled="disabled" maxlength="1000"  class="text">${repayScheduleList[0].makerRemarks}</textarea>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<textarea name="makerRemarks" id="makerRemarks"
													disabled="disabled" maxlength="1000"  class="text"></textarea>
											</td>
											</logic:notPresent>
											
											
											<td>
												<bean:message key="lbl.reschCharges" />
												<font color="red">*</font>
											</td>
											<logic:present name="repayScheduleList">
											<td><html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="${repayScheduleList[0].reschCharges}" maxlength="18"	readonly="true" tabindex="-1" style="text-align:right;" />
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td><html:text styleClass="text" property="reschCharges" styleId="reschCharges" value="" maxlength="18"	readonly="true" tabindex="-1" style="text-align:right;" />
											</td>
											</logic:notPresent>
											
										</tr>
										
										<tr>
										
										<td></td><td></td>
										    <td>
												<bean:message key="lbl.authorRemarks"/>
											</td>
											<logic:present name="repayScheduleList">
											<td>
												<textarea name="authorRemarks" id="authorRemarks"
												disabled="disabled" tabindex="-1" class="text" >${repayScheduleList[0].authorRemarks}</textarea>
											</td>
											</logic:present>
											
											<logic:notPresent name="repayScheduleList">
											<td>
												<textarea name="authorRemarks" id="authorRemarks"
												disabled="disabled" tabindex="-1" class="text"></textarea>
											</td>
											</logic:notPresent>
											
											
										</tr>
										
									</table>
								</td>
							</tr>
						</table>
						
						<table>
						<tr>
						<td>
						 <button type="button"  name="generateInstallmentPlan" 
							                      id="generateInstallmentPlan" class="topformbutton4"  title="Alt+R" accesskey="R" 
							                      onclick="return viewInstallmentPlanRepricing();" tabindex="8"><bean:message key="button.oldinstplan" /></button>
						</td>
						<td>
						  <button type="button"  name="generateRepaymentSch" 
							                      id="generateRepaymentSch" class="topformbutton4"  title="Alt+R" accesskey="R" 
							                      onclick="return viewRepaymentScheduleDisbursal();" tabindex="8"><bean:message key="button.oldrepay" /></button>
						
						</td>
						<td>
						
						  <button type="button"  name="generateRepaymentSchNew" 
							                      id="generateRepaymentSchNew" class="topformbutton4"  title="Alt+R" accesskey="R" 
							                      onclick="return newRepaymentScheduleRepricing();" tabindex="8"><bean:message key="button.newrepay" /></button>
							                       
						
						</td>
						</tr>
						
						</table>
					</fieldset>
				</html:form>
				
				
				
				</logic:present>
			

	 </div>
	</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("updateRepayScheduleForm");
</script>	
</body>
</html:html>

<logic:present name="message">

<script type="text/javascript">
if("<%=request.getAttribute("message")%>"=="S")
{
	alert("<bean:message key="msg.DataSaved" />");
}
else if("<%=request.getAttribute("message")%>"=="newInstlPlan")
{
	alert("<bean:message key="msg.saveNewInstlPlan" />");
}

</script>
</logic:present>

<logic:present name="nonInstallmentBased">
	<script type="text/javascript">
		alert("<bean:message key="msg.nonInstallmentBased" />");
	</script>
</logic:present>
<logic:present name="delete">
<script type="text/javascript">
		alert("<bean:message key="lbl.dataDeleted" />");
		window.location = "<%=request.getContextPath()%>/repayscheduleSearchBehind.do";	
</script>
</logic:present>
<logic:present name="notDelete">
<script type="text/javascript">
		alert("<bean:message key="lbl.dataNtDeleted" />");
</script>
</logic:present>
<logic:present name="msg">

		<script type="text/javascript">
		if("<%=request.getAttribute("msg")%>"=="F")
		{
			 alert("<bean:message key="msg.ForwardSuccessfully" />");
			 location.href="<%=request.getContextPath()%>/repayscheduleSearchBehind.do";
		}
		else if("<%=request.getAttribute("msg")%>"=="E")
		{
			alert("<bean:message key="msg.ForwardNotSuccessfully" />");
			location.href="<%=request.getContextPath()%>/repayscheduleSearchBehind.do";
		}
		
		</script>
</logic:present>
   <logic:present name="procStatus">
	<script type="text/javascript">
		alert('${procStatus}');
		self.close();
	</script>
</logic:present>