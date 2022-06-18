<!-- 
Author Name :- Amit Kumar
Date of Creation :28-11-2011
Purpose :-  screen for the Instalment Plan
-->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script type="text/javascript">	
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('installmentPlanForm').fromInstallment1.focus();
     }
     else if(document.getElementById('modifyRecord').value =='A')
     {}
     
     return true;
}
</script>
</head>


<body onload="enableAnchor();recoveryPercent();fntab();init_fields();">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
	<input type="hidden" class="text" name="installmentType" id="installmentType"
		value="<%=request.getAttribute("installmentType")%>" />
	<input type="hidden" class="text" name="totalInstallment" id="totalInstallment" value="<%=request.getAttribute("totalInstallment")%>" />
	<input type="hidden" class="text" name="rateType" id="rateType" value="${rateType }" />
	<input type="hidden" class="text" name="loanAmount" id="loanAmount" value="${loanAmount }" />
	<input type="hidden" class="text" name="loanId" id="loanId" value="${requestScope.loanId}" />
	<input type="hidden" class="text" name="reschId" id="reschId" value="${requestScope.reschId}" />

	<div id="centercolumn">
		<div id="revisedcontainer">
			<logic:notPresent name="cmAuthor">
				<input type="hidden" id="modifyRecord" name="modifyRecord" value="M" />
				<html:form action="/installmentPlanRepricingProcess" method="post" styleId="installmentPlanForm">
					<fieldset>
						<legend>
							<bean:message key="lbl.installmentPlan" />
						</legend>
						<table width="50%" border="0" cellspacing="4" cellpadding="0">
							<tr>
								<td>
									<bean:message key="lbl.recoveryType" />
									<font color="red">*</font>
								</td>
								<td>

									<html:select property="recoveryType" styleId="recoveryType" styleClass="text" onchange="changeRecoveryType();" value="${installmentList[0].recoveryType}">

									<html:option value="P" >PERCENTAGE</html:option>
	                                 <html:option value="F" >FLAT</html:option>
	                                 <%-- 
										<logic:present name="installmentList">
										<logic:notEmpty  name="installmentList"> 
											<logic:iterate name="installmentList" id="subInstallmentList"
												indexId="count" length="1">
												<logic:equal name="subInstallmentList"
													property="recoveryType" value="P">
													<option value="P" selected="selected">
														PERCENTAGE
													</option>
												</logic:equal>
												<logic:notEqual name="subInstallmentList"
													property="recoveryType" value="P">
													<option value="P">
														PERCENTAGE
													</option>
												</logic:notEqual>
											</logic:iterate>
											</logic:notEmpty>
										</logic:present>

										<logic:equal name="rateType" value="F">
											<logic:notEqual name="installmentType" value="R">
												<logic:present name="installmentList">
												<logic:notEmpty name="installmentList">
													<logic:iterate name="installmentList"
														id="subInstallmentList" indexId="count" length="1">
														<logic:equal name="subInstallmentList"
															property="recoveryType" value="F">
															<option value="F" selected="selected">
																FLAT
															</option>
														</logic:equal>
														<logic:notEqual name="subInstallmentList"
															property="recoveryType" value="F">
															<option value="F">
																FLAT
															</option>
														</logic:notEqual>
													</logic:iterate>
													</logic:notEmpty>
												</logic:present>
											</logic:notEqual>
										</logic:equal>
										--%>
									</html:select>
								</td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>

								<td class="gridtd">

									<table width="100%" border="0" cellspacing="1" cellpadding="1"
										id="gridTable">
										<tr class="white2">
											<td width="3%">
												<input type="checkbox" name="allchkd" id="allchkd"
													onclick="allChecked();" />
											</td>
											<td>
												<bean:message key="lbl.fromInstallment"></bean:message>
											</td>
											<td>
												<bean:message key="lbl.toInstallment"></bean:message>
											</td>

											<td>
												<bean:message key="lbl.recoveryPercen"></bean:message>
											</td>
											<td>
												<bean:message key="lbl.principalAmount"></bean:message>
											</td>
											<td>
												<bean:message key="lbl.installmentAmount"></bean:message>
											</td>
										</tr>
										<logic:present name="viewMode">
										
											<logic:present name="installmentList">
											<logic:notEmpty name="installmentList">
												<logic:iterate name="installmentList"
													id="subInstallmentList" indexId="count" length="1">

													<logic:equal name="rateType" value="F">
                                               
														<logic:equal name="subInstallmentList"
															property="recoveryType" value="F">
															
															 

															<%
																if ((request.getAttribute("installmentType") != null && request.getAttribute("installmentType").equals("P"))
																		|| (request.getAttribute("installmentType") != null && request.getAttribute("installmentType").equals("Q")))
																 {
															%>
															<tr class="white1">

																<td>
																	<input type="checkbox" name="chk"
																		id="chk<%=count.intValue()+ 1%>"
																		value="<%=count.intValue()+ 1%>" />
																</td>
																<td>
																	<input type="text" class="text" name="fromInstall"
																id="fromInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.fromInstallment}"
																		maxlength="100" readonly="readonly" />
																</td>
																<td>
																	<input type="text" class="text" name="toInstall"
																		id="toInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.toInstallment}"
																		maxlength="10" />
																</td>
																<td>
																	<input type="text" class="text" name="recoveryPer"
																		id="recoveryPercen<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.recoveryPercen}"
																		readonly="readonly" />
																</td>
																<td>
																	<input type="text" class="text" name="principalAmount"
																		id="principalAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.prinAm}"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>
																<td>
																	<input type="text" class="text"
																		name="installmentAmount"
																		id="installmentAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.instalAm}"
																		readonly="readonly"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>

															</tr>
															<%
																} 
																else 
																{
															%>
															
															<tr class="white1">
																<td>
																	<input type="checkbox" name="chk"
																		id="chk<%=count.intValue()+ 1%>"
																		value="<%=count.intValue()+ 1%>" />
																</td>
																<td>
																	<input type="text" class="text" name="fromInstall"
																		id="fromInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.fromInstallment}"
																		maxlength="100" readonly="readonly" />
																</td>
																<td>
																	<input type="text" class="text" name="toInstall"
																		id="toInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.toInstallment}"
																		maxlength="10"  />
																</td>
																<td>
																	<input type="text" class="text" name="recoveryPer"
																		id="recoveryPercen<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.recoveryPercen}"
																		readonly="readonly" />
																</td>
																<td>
																	<input type="text" class="text" name="principalAmount"
																		id="principalAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.prinAm}"
																		readonly="readonly"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>
																<td>
																	<input type="text" class="text"
																		name="installmentAmount"
																		id="installmentAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.instalAm}"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>

															</tr>
															<%
																}
															%>
														</logic:equal>
                                                       
														<logic:equal name="subInstallmentList"
															property="recoveryType" value="P">
                                                          
															<tr class="white1">

																<td>
																	<input type="checkbox" name="chk"
																		id="chk<%=count.intValue()+ 1%>"
																		value="<%=count.intValue()+ 1%>" />
																</td>
																<td>
																	<input type="text" class="text" name="fromInstall"
															id="fromInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.fromInstallment}"
																		maxlength="100" readonly="readonly" />
																</td>
																<td>
																	<input type="text" class="text" name="toInstall"
																		id="toInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.toInstallment}"
																		maxlength="10"  />
																</td>
																<td>
																	<input type="text" class="text" name="recoveryPer"
																		id="recoveryPercen<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.recoveryPercen}" />
																</td>
																<td>
																	<input type="text" class="text" name="principalAmount"
																		id="principalAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.prinAm}"
																		readonly="readonly"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>
																<td>
																	<input type="text" class="text"
																		name="installmentAmount"
																		id="installmentAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.instalAm}"
																		readonly="readonly"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>
															</tr>
														</logic:equal>

														<logic:notEqual name="subInstallmentList"
															property="recoveryType" value="P">
															
															<logic:notEqual name="subInstallmentList"
																property="recoveryType" value="F">
																<tr class="white1">
																	<td>
																		<input type="checkbox" name="chk"
																			id="chk<%=count.intValue()+ 1%>"
																			value="<%=count.intValue()+ 1%>" />
																	</td>
																	<td>
																	<input type="text" class="text" name="fromInstall"
																		id="fromInstallment<%=count.intValue()+ 1%>"
																			value="${subInstallmentList.fromInstallment}"
																			 maxlength="100" />
																	</td>
																	<td>
																		<input type="text" class="text" name="toInstall"
																			id="toInstallment<%=count.intValue()+ 1%>"
																			value="${subInstallmentList.toInstallment}"
																			 maxlength="10" />
																	</td>
																	<td>
																		<input type="text" class="text" name="recoveryPer"
																			id="recoveryPercen<%=count.intValue()+ 1%>"
																			value="${subInstallmentList.recoveryPercen}" />
																	</td>
																	<td>
																		<input type="text" class="text" name="principalAmount"
																			id="principalAmount<%=count.intValue()+ 1%>"
																			value="${subInstallmentList.prinAm}"
																			readonly="readonly"
																			onkeypress="return numbersonly(event, id, 18)"
																			onblur="formatNumber(this.value, id);"
																			onkeyup="checkNumber(this.value, event, 18, id);"
																			onfocus="keyUpNumber(this.value, event, 18, id);" />
																	</td>
																	<td>
																		<input type="text" class="text"
																			name="installmentAmount"
																			id="installmentAmount<%=count.intValue()+ 1%>"
																			value="${subInstallmentList.instalAm}"
																			readonly="readonly"
																			onkeypress="return numbersonly(event, id, 18)"
																			onblur="formatNumber(this.value, id);"
																			onkeyup="checkNumber(this.value, event, 18, id);"
																			onfocus="keyUpNumber(this.value, event, 18, id);" />
																	</td>

																</tr>
															</logic:notEqual>
														</logic:notEqual>
													</logic:equal>
													<logic:equal name="rateType" value="E">
	 
	          <logic:equal name="subInstallmentList" property="recoveryType"  value="P"> 
	           
	           		<tr class="white1">
              
		          <td><input type="checkbox" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
		          <td  ><input type="text" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subInstallmentList.fromInstallment}"  maxlength="100" readonly="readonly"/></td>
		          <td ><input type="text" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subInstallmentList.toInstallment}" maxlength="10" readonly="readonly"/></td>	      
		          <td ><input type="text" class="text" name="recoveryPer" id="recoveryPercen<%=count.intValue()+1%>" value="${subInstallmentList.recoveryPercen}" /></td>
		          <td ><input type="text" class="text" name="principalAmount" id="principalAmount<%=count.intValue()+1%>" value="${subInstallmentList.prinAm}" readonly="readonly"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		          <td ><input type="text" class="text" name="installmentAmount" id="installmentAmount<%=count.intValue()+1%>" value="${subInstallmentList.instalAm}" readonly="readonly" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
	          
              </tr>	
		          
	        </logic:equal>
	        
	         <logic:equal name="subInstallmentList" property="recoveryType"  value="F"> 
	           
	           	<%
                       if((request.getAttribute("installmentType")!=null && request.getAttribute("installmentType").equals("P"))||(request.getAttribute("installmentType")!=null && request.getAttribute("installmentType").equals("Q")))
                       {
                   %>    
              <tr class="white1">
              
		          <td><input type="checkbox" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
		          <td  ><input type="text" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subInstallmentList.fromInstallment}"  maxlength="100" readonly="readonly"/></td>
		          <td ><input type="text" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subInstallmentList.toInstallment}" maxlength="10" readonly="readonly"/></td>	      
		          <td ><input type="text" class="text" name="recoveryPer" id="recoveryPercen<%=count.intValue()+1%>" value="${subInstallmentList.recoveryPercen}"  readonly="readonly"/></td>
		          <td ><input type="text" class="text" name="principalAmount" id="principalAmount<%=count.intValue()+1%>" value="${subInstallmentList.prinAm}"   onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		          <td ><input type="text" class="text" name="installmentAmount" id="installmentAmount<%=count.intValue()+1%>" value="${subInstallmentList.instalAm}" readonly="readonly" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
	          
              </tr>
              <%
              	} 
              	else
              	{
              %>   
              	<tr class="white1">
              
		          <td><input type="checkbox" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
		          <td  ><input type="text" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subInstallmentList.fromInstallment}"  maxlength="100" readonly="readonly"/></td>
		          <td ><input type="text" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subInstallmentList.toInstallment}" maxlength="10" readonly="readonly"/></td>	      
		          <td ><input type="text" class="text" name="recoveryPer" id="recoveryPercen<%=count.intValue()+1%>" value="${subInstallmentList.recoveryPercen}"  readonly="readonly"/></td>
		          <td ><input type="text" class="text" name="principalAmount" id="principalAmount<%=count.intValue()+1%>" value="${subInstallmentList.prinAm}" readonly="readonly"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		          <td ><input type="text" class="text" name="installmentAmount" id="installmentAmount<%=count.intValue()+1%>" value="${subInstallmentList.instalAm}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
	          
              </tr>	
              <%} %>
		          
	        </logic:equal>    
	             <logic:notEqual name="subInstallmentList" property="recoveryType"  value="P"> 
              		 <logic:notEqual name="subInstallmentList" property="recoveryType"  value="F"> 
              		<tr class="white1">
              
		          <td><input type="checkbox" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
		          <td  ><input type="text" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subInstallmentList.fromInstallment}" readonly="readonly"  maxlength="100" /></td>
		          <td ><input type="text" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subInstallmentList.toInstallment}"  maxlength="10" /></td>	      
		          <td ><input type="text" class="text" name="recoveryPer" id="recoveryPercen<%=count.intValue()+1%>" value="${subInstallmentList.recoveryPercen}"  /></td>
		          <td ><input type="text" class="text" name="principalAmount" id="principalAmount<%=count.intValue()+1%>" value="${subInstallmentList.prinAm}" readonly="readonly"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		          <td ><input type="text" class="text" name="installmentAmount" id="installmentAmount<%=count.intValue()+1%>" value="${subInstallmentList.instalAm}" readonly="readonly" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
	          
                    </tr>	
                     </logic:notEqual>
              </logic:notEqual>
	           
         </logic:equal>
												</logic:iterate>
												</logic:notEmpty>
											</logic:present>
										</logic:present>


										<logic:present name="installmentList">
										
											<logic:notPresent name="viewMode">
												<logic:notEmpty name="installmentList">
												<logic:iterate name="installmentList" id="subInstallmentList" indexId="count">
												
													<logic:equal name="rateType" value="F">
														<logic:equal name="subInstallmentList" property="recoveryType" value="F">
														<%
															if ((request.getAttribute("installmentType") != null && request.getAttribute("installmentType").equals("P"))
																	|| (request.getAttribute("installmentType") != null && request.getAttribute("installmentType").equals("Q"))) 
															{
														%>
															<tr class="white1">
																<td>
																	<input type="checkbox" name="chk"
																		id="chk<%=count.intValue()+ 1%>"
																		value="<%=count.intValue()+ 1%>" />
																</td>
																<td>
																	<input type="text" class="text" name="fromInstall"
																		id="fromInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.fromInstallment}"
																		maxlength="100"
																		onkeypress="return isNumberKey(event);" />
																</td>
																<td>
																	<input type="text" class="text" name="toInstall"
																		id="toInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.toInstallment}"
																		maxlength="10" onkeypress="return isNumberKey(event);" />
																</td>
																<td>
																	<input type="text" class="text" name="recoveryPer"
																		readonly="readonly"
																		id="recoveryPercen<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.recoveryPercen}" />
																</td>
																<td>
																	<input type="text" class="text" name="principalAmount"
																		id="principalAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.prinAm}"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>
																<td>
																	<input type="text" class="text"
																		name="installmentAmount" readonly="readonly"
																		id="installmentAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.instalAm}"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>
															</tr>
														<%
															} 
															else 
															{
														%>
															<tr class="white1">
																<td>
																	<input type="checkbox" name="chk"
																		id="chk<%=count.intValue()+ 1%>"
																		value="<%=count.intValue()+ 1%>" />
																</td>
																<td>
																	<input type="text" class="text" name="fromInstall"
																		id="fromInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.fromInstallment}"
																		maxlength="100"
																		onkeypress="return isNumberKey(event);" />
																</td>
																<td>
																	<input type="text" class="text" name="toInstall"
																		id="toInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.toInstallment}"
																		maxlength="10" onkeypress="return isNumberKey(event);" />
																</td>
																<td>
																	<input type="text" class="text" name="recoveryPer"
																		readonly="readonly"
																		id="recoveryPercen<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.recoveryPercen}" />
																</td>
																<td>
																	<input type="text" class="text" name="principalAmount"
																		readonly="readonly"
																		id="principalAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.prinAm}"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>
																<td>
																	<input type="text" class="text"
																		name="installmentAmount"
																		id="installmentAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.instalAm}"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>
															</tr>
														<%
															}
														%>
														</logic:equal>

														<logic:equal name="subInstallmentList" property="recoveryType" value="P">
															<tr class="white1">
																<td>
																	<input type="checkbox" name="chk"
																		id="chk<%=count.intValue()+ 1%>"
																		value="<%=count.intValue()+ 1%>" />
																</td>
																<td>
																	<input type="text" class="text" name="fromInstall"
																		id="fromInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.fromInstallment}"
																		maxlength="100"
																		onkeypress="return isNumberKey(event);" />
																</td>
																<td>
																	<input type="text" class="text" name="toInstall"
																		id="toInstallment<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.toInstallment}"
																		maxlength="10" onkeypress="return isNumberKey(event);" />
																</td>
																<td>
																	<input type="text" class="text" name="recoveryPer"
																		id="recoveryPercen<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.recoveryPercen}" />
																</td>
																<td>
																	<input type="text" class="text" name="principalAmount"
																		readonly="readonly"
																		id="principalAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.prinAm}"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>
																<td>
																	<input type="text" class="text"
																		name="installmentAmount" readonly="readonly"
																		id="installmentAmount<%=count.intValue()+ 1%>"
																		value="${subInstallmentList.instalAm}"
																		onkeypress="return numbersonly(event, id, 18)"
																		onblur="formatNumber(this.value, id);"
																		onkeyup="checkNumber(this.value, event, 18, id);"
																		onfocus="keyUpNumber(this.value, event, 18, id);" />
																</td>
															</tr>
														</logic:equal>
														
														<logic:notEqual name="subInstallmentList"
															property="recoveryType" value="P">
															<logic:notEqual name="subInstallmentList"
																property="recoveryType" value="F">
																<tr class="white1">
																	<td>
																		<input type="checkbox" name="chk"
																			id="chk<%=count.intValue()+ 1%>"
																			value="<%=count.intValue()+ 1%>" />
																	</td>
																	<td>
																		<input type="text" class="text" name="fromInstall"
																			id="fromInstallment<%=count.intValue()+ 1%>"
																			value="${subInstallmentList.fromInstallment}"
																			maxlength="100" />
																	</td>
																	<td>
																		<input type="text" class="text" name="toInstall"
																			id="toInstallment<%=count.intValue()+ 1%>"
																			value="${subInstallmentList.toInstallment}"
																			maxlength="10" />
																	</td>
																	<td>
																		<input type="text" class="text" name="recoveryPer"
																			id="recoveryPercen<%=count.intValue()+ 1%>"
																			value="${subInstallmentList.recoveryPercen}" />
																	</td>
																	<td>
																		<input type="text" class="text" name="principalAmount"
																			id="principalAmount<%=count.intValue()+ 1%>"
																			value="${subInstallmentList.prinAm}"
																			readonly="readonly"
																			onkeypress="return numbersonly(event, id, 18)"
																			onblur="formatNumber(this.value, id);"
																			onkeyup="checkNumber(this.value, event, 18, id);"
																			onfocus="keyUpNumber(this.value, event, 18, id);" />
																	</td>
																	<td>
																		<input type="text" class="text"
																			name="installmentAmount"
																			id="installmentAmount<%=count.intValue()+ 1%>"
																			value="${subInstallmentList.instalAm}"
																			readonly="readonly"
																			onkeypress="return numbersonly(event, id, 18)"
																			onblur="formatNumber(this.value, id);"
																			onkeyup="checkNumber(this.value, event, 18, id);"
																			onfocus="keyUpNumber(this.value, event, 18, id);" />
																	</td>
																</tr>
															</logic:notEqual>
														</logic:notEqual>
													</logic:equal>
													
													<logic:equal name="rateType" value="E">
	 
	      
	       
	       <logic:equal name="subInstallmentList" property="recoveryType"  value="P"> 
	           
	           		<tr class="white1">
              
		          <td><input type="checkbox" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
		          <td  ><input type="text" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subInstallmentList.fromInstallment}"  maxlength="100" /></td>
		          <td ><input type="text" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subInstallmentList.toInstallment}" maxlength="10" /></td>	      
		          <td ><input type="text" class="text" name="recoveryPer" id="recoveryPercen<%=count.intValue()+1%>" value="${subInstallmentList.recoveryPercen}" /></td>
		          <td ><input type="text" class="text" name="principalAmount" id="principalAmount<%=count.intValue()+1%>" value="${subInstallmentList.prinAm}" readonly="readonly"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		          <td ><input type="text" class="text" name="installmentAmount" id="installmentAmount<%=count.intValue()+1%>" value="${subInstallmentList.instalAm}" readonly="readonly" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
	          
              </tr>	
		          
	        </logic:equal>
	        
	         <logic:equal name="subInstallmentList" property="recoveryType"  value="F"> 
	           
	            <%
                       if((request.getAttribute("installmentType")!=null && request.getAttribute("installmentType").equals("P"))||(request.getAttribute("installmentType")!=null && request.getAttribute("installmentType").equals("Q")))
                       {
                   %>    
				   <tr class="white1">
			         <td><input type="checkbox" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
			          <td  ><input type="text" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subInstallmentList.fromInstallment}"  maxlength="100" onkeypress="return isNumberKey(event);"/></td>
			          <td ><input type="text" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subInstallmentList.toInstallment}" maxlength="10" onkeypress="return isNumberKey(event);"/></td>	      
			          <td ><input type="text" class="text" name="recoveryPer" readonly="readonly" id="recoveryPercen<%=count.intValue()+1%>" value="${subInstallmentList.recoveryPercen}" readonly="readonly" /></td>
			          <td ><input type="text" class="text" name="principalAmount"  id="principalAmount<%=count.intValue()+1%>" value="${subInstallmentList.prinAm}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
			          <td ><input type="text" class="text" name="installmentAmount" readonly="readonly" id="installmentAmount<%=count.intValue()+1%>" value="${subInstallmentList.instalAm}"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		          </tr>
		          <%
		          		}
		          		else
		          		{
		           %>
		           	<tr class="white1">
			          <td><input type="checkbox" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
			          <td  ><input type="text" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subInstallmentList.fromInstallment}"  maxlength="100" onkeypress="return isNumberKey(event);"/></td>
			          <td ><input type="text" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subInstallmentList.toInstallment}" maxlength="10" onkeypress="return isNumberKey(event);"/></td>	      
			          <td ><input type="text" class="text" name="recoveryPer" readonly="readonly" id="recoveryPercen<%=count.intValue()+1%>" value="${subInstallmentList.recoveryPercen}" readonly="readonly" /></td>
			          <td ><input type="text" class="text" name="principalAmount" readonly="readonly"  id="principalAmount<%=count.intValue()+1%>" value="${subInstallmentList.prinAm}"   onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
			          <td ><input type="text" class="text" name="installmentAmount"  id="installmentAmount<%=count.intValue()+1%>" value="${subInstallmentList.instalAm}" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		          </tr>
		           <%
		           		}
		            %>
		          
	        </logic:equal>    
	           
	            <logic:notEqual name="subInstallmentList" property="recoveryType"  value="P"> 
              		 <logic:notEqual name="subInstallmentList" property="recoveryType"  value="F"> 
              		<tr class="white1">
              
		          <td><input type="checkbox" name="chk" id="chk<%=count.intValue()+1%>" value="<%=count.intValue()+1%>"/></td>
		          <td  ><input type="text" class="text" name="fromInstall" id="fromInstallment<%=count.intValue()+1%>" value="${subInstallmentList.fromInstallment}"  maxlength="100" /></td>
		          <td ><input type="text" class="text" name="toInstall" id="toInstallment<%=count.intValue()+1%>"  value="${subInstallmentList.toInstallment}" maxlength="10" /></td>	      
		          <td ><input type="text" class="text" name="recoveryPer" id="recoveryPercen<%=count.intValue()+1%>" value="${subInstallmentList.recoveryPercen}"  /></td>
		          <td ><input type="text" class="text" name="principalAmount" id="principalAmount<%=count.intValue()+1%>" value="${subInstallmentList.prinAm}" readonly="readonly"  onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
		          <td ><input type="text" class="text" name="installmentAmount" id="installmentAmount<%=count.intValue()+1%>" value="${subInstallmentList.instalAm}" readonly="readonly" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onkeyup="checkNumber(this.value, event, 18, id);" onfocus="keyUpNumber(this.value, event, 18, id);"/></td>
	          
                    </tr>	
                     </logic:notEqual>
              </logic:notEqual>
	           
         </logic:equal>
												</logic:iterate>
											</logic:notEmpty>	
											</logic:notPresent>
										</logic:present>

<!--										<logic:notPresent name="installmentList">-->
<!--											<logic:notPresent name="viewMode">-->
<!--												<tr class="white1">-->
<!--													<td>-->
<!--														<input type="checkbox" name="chk" id="chk1" value="" />-->
<!--													</td>-->
<!--													<td>-->
<!--														<input type="text" class="text" name="fromInstall"-->
<!--															id="fromInstallment1" value="" style="" maxlength="100"-->
<!--															onkeypress="return isNumberKey(event);" />-->
<!--													</td>-->
<!--													<td>-->
<!--														<input type="text" class="text" name="toInstall"-->
<!--															id="toInstallment1" style="" value="" maxlength="10"-->
<!--															onkeypress="return isNumberKey(event);" />-->
<!--													</td>-->
<!--													<td>-->
<!--														<input type="text" name="recoveryPer" id="recoveryPercen1"-->
<!--															value="" class="text" />-->
<!--													</td>-->
<!--													<td>-->
<!--														<input type="text" class="text" name="principalAmount"-->
<!--															readonly="readonly" id="principalAmount" value="" />-->
<!--													</td>-->
<!--													<td>-->
<!--														<input type="text" class="text" name="installmentAmount"-->
<!--															readonly="readonly" id="installmentAmount" value="" />-->
<!--													</td>-->
<!--												</tr>-->
<!--											</logic:notPresent>-->
<!--										</logic:notPresent>-->
									</table>
								</td>
							</tr>
						</table>
						<logic:present name="viewMode">
						<!-- Arun  -->
						
							<button type="button" class="topformbutton2" 
								onclick="return saveNewInstallmentPlanRescheduling();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
						
						
						<!-- Arun  -->
						</logic:present>

						<logic:notPresent name="viewMode">
						
							<button type="button" class="topformbutton2"
								onclick="return saveNewInstallmentPlanRescheduling();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
							<button type="button" id="deleteRow"
								tabindex="-1" class="topformbutton2" onclick="removeRow();"
								title="Alt+D" accesskey="D" ><bean:message key="button.deleterow" /></button>					
   							<button type="button"  tabindex="-1"
								class="topformbutton2"
								onclick="addROW('<bean:message key="msg.LoanAccountNo" />');"
								title="Alt+A" accesskey="A" ><bean:message key="button.addrow" /></button>
								
	   					</logic:notPresent>
					</fieldset>
				</html:form>
			</logic:notPresent>
			<%--          			Loan Initiation Author part																	 --%>

			<logic:present name="cmAuthor">

				<html:form action="/installmentPlanRepricingProcess" method="post" styleId="installmentPlanForm">
					<fieldset>
						<legend>
							<bean:message key="lbl.installmentPlan" />
						</legend>
						<table width="50%" border="0" cellspacing="4" cellpadding="0">
							<tr>
								<td>
									<bean:message key="lbl.recoveryType" />
									<font color="red">*</font>
								</td>
								<td>

									<html:select property="recoveryType" styleId="recoveryType" styleClass="text"
										onchange="changeRecoveryType();"  value="${installmentList[0].recoveryType}" disabled="true">

                                      <html:option value="P" >PERCENTAGE</html:option>
	                                   <html:option value="F" >FLAT</html:option>
	                                   <%-- 
										<logic:present name="installmentList">
										<logic:notEmpty  name="installmentList">
											<logic:iterate name="installmentList" id="subInstallmentList" indexId="count" length="1">
												<logic:equal name="subInstallmentList"
													property="recoveryType" value="P">
													<option value="P" selected="selected">
														PERCENTAGE
													</option>
												</logic:equal>
												<logic:notEqual name="subInstallmentList"
													property="recoveryType" value="P">
													<option value="P">
														PERCENTAGE
													</option>
												</logic:notEqual>
											</logic:iterate>
											</logic:notEmpty>
										</logic:present>

										<logic:equal name="rateType" value="F">
											<logic:present name="installmentList">
											<logic:notEmpty  name="installmentList">
												<logic:iterate name="installmentList"
													id="subInstallmentList" indexId="count" length="1">
													<logic:equal name="subInstallmentList"
														property="recoveryType" value="F">
														<option value="F" selected="selected">
															FLAT
														</option>
													</logic:equal>
													<logic:notEqual name="subInstallmentList"
														property="recoveryType" value="F">
														<option value="F">
															FLAT
														</option>
													</logic:notEqual>
												</logic:iterate>
												</logic:notEmpty>
											</logic:present>
										</logic:equal>
										--%>
									</html:select>
								</td>
							</tr>
						</table>
						<input type="hidden" id="modifyRecord" name="modifyRecord"
							value="A" />
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="gridtd">
									<table width="100%" border="0" cellspacing="1" cellpadding="1"
										id="gridTable">
										<tr class="white2">
											<td width="3%">
												<input type="checkbox" disabled="disabled" name="allchkd"
													id="allchkd" onclick="allChecked();" />
											</td>
											<td>
												<bean:message key="lbl.fromInstallment"></bean:message>
											</td>
											<td>
												<bean:message key="lbl.toInstallment"></bean:message>
											</td>

											<td>
												<bean:message key="lbl.recoveryPercen"></bean:message>
											</td>
											<td>
												<bean:message key="lbl.principalAmount"></bean:message>
											</td>
											<td>
												<bean:message key="lbl.installmentAmount"></bean:message>
											</td>
										</tr>
										<logic:present name="viewMode">
										
											<logic:present name="installmentList">
											<logic:notEmpty name="installmentList">
												<logic:iterate name="installmentList" id="subInstallmentList" indexId="count">
													<tr class="white1">
														<td>
															<input type="checkbox" disabled="disabled" name="chk"
																id="chk<%=count.intValue() + 1%>"
																value="<%=count.intValue() + 1%>" />
														</td>
														<td>
															<input type="text" disabled="disabled" class="text"
																name="fromInstall"
																id="fromInstallment<%=count.intValue() + 1%>"
																value="${subInstallmentList.fromInstallment}"
																maxlength="100" />
														</td>
														<td>
															<input type="text" disabled="disabled" class="text"
																name="toInstall"
																id="toInstallment<%=count.intValue() + 1%>"
																value="${subInstallmentList.toInstallment}"
																maxlength="10" />
														</td>
														<td>
															<input type="text" disabled="disabled" class="text"
																name="recoveryPer"
																id="recoveryPercen<%=count.intValue() + 1%>"
																value="${subInstallmentList.recoveryPercen}" />
														</td>
														<td>
															<input type="text" class="text" disabled="disabled"
																name="principalAmount"
																id="principalAmount<%=count.intValue() + 1%>"
																value="${subInstallmentList.prinAm}"
																onkeypress="return numbersonly(event, id, 18)"
																onblur="formatNumber(this.value, id);"
																onkeyup="checkNumber(this.value, event, 18, id);"
																onfocus="keyUpNumber(this.value, event, 18, id);" />
														</td>
														<td>
															<input type="text" class="text" disabled="disabled"
																name="installmentAmount"
																id="installmentAmount<%=count.intValue() + 1%>"
																value="${subInstallmentList.instalAm}"
																onkeypress="return numbersonly(event, id, 18)"
																onblur="formatNumber(this.value, id);"
																onkeyup="checkNumber(this.value, event, 18, id);"
																onfocus="keyUpNumber(this.value, event, 18, id);" />
														</td>
													</tr>
												</logic:iterate>
												</logic:notEmpty>
											</logic:present>
										</logic:present>

										<logic:present name="installmentList">
										
											<logic:notPresent name="viewMode">
											<logic:notEmpty name="installmentList">
												<logic:iterate name="installmentList" id="subInstallmentList" indexId="count">
													<tr class="white1">
														<td>
															<input type="checkbox" disabled="disabled" name="chk"
																id="chk<%=count.intValue() + 1%>"
																value="<%=count.intValue() + 1%>" />
														</td>
														<td>
															<input type="text" disabled="disabled" class="text"
																name="fromInstall"
																id="fromInstallment<%=count.intValue() + 1%>"
																value="${subInstallmentList.fromInstallment}"
																maxlength="100" />
														</td>
														<td>
															<input type="text" disabled="disabled" class="text"
																name="toInstall"
																id="toInstallment<%=count.intValue() + 1%>"
																value="${subInstallmentList.toInstallment}"
																maxlength="10" />
														</td>
														<td>
															<input type="text" disabled="disabled" class="text"
																name="recoveryPer"
																id="recoveryPercen<%=count.intValue() + 1%>"
																value="${subInstallmentList.recoveryPercen}" />
														</td>
														<td>
															<input type="text" class="text" disabled="disabled"
																name="principalAmount"
																id="principalAmount<%=count.intValue() + 1%>"
																value="${subInstallmentList.prinAm}"
																onkeypress="return numbersonly(event, id, 18)"
																onblur="formatNumber(this.value, id);"
																onkeyup="checkNumber(this.value, event, 18, id);"
																onfocus="keyUpNumber(this.value, event, 18, id);" />
														</td>
														<td>
															<input type="text" class="text" disabled="disabled"
																name="installmentAmount"
																id="installmentAmount<%=count.intValue() + 1%>"
																value="${subInstallmentList.instalAm}"
																onkeypress="return numbersonly(event, id, 18)"
																onblur="formatNumber(this.value, id);"
																onkeyup="checkNumber(this.value, event, 18, id);"
																onfocus="keyUpNumber(this.value, event, 18, id);" />
														</td>
													</tr>
												</logic:iterate>
												</logic:notEmpty>
											</logic:notPresent>
										</logic:present>

										<logic:notPresent name="installmentList">
											<logic:notPresent name="viewMode">
												<tr class="white1">
													<td>
														<input type="checkbox" name="chk" id="chk1" value=""
															disabled="disabled" />
													</td>
													<td>
														<input type="text" class="text" disabled="disabled"
															name="fromInstall" id="fromInstallment1" value=""
															style="" maxlength="100" />
													</td>
													<td>
														<input type="text" class="text" disabled="disabled"
															name="toInstall" id="toInstallment1" style="" value=""
															maxlength="10" />
													</td>
													<td>
														<input type="text" name="recoveryPer" disabled="disabled"
															id="recoveryPercen1" value="" class="text" />
													</td>
													<td>
														<input type="text" class="text" disabled="disabled"
															name="principalAmount" id="principalAmount" value="" />
													</td>
													<td>
														<input type="text" class="text" disabled="disabled"
															name="installmentAmount" id="installmentAmount" value="" />
													</td>
												</tr>
											</logic:notPresent>
										</logic:notPresent>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</html:form>
			</logic:present>
		</div>
	</div>
</body>
</html:html>

<logic:present name="sms">
	<script type="text/javascript">
		
		if('<%=request.getAttribute("sms").toString()%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
			window.opener.document.getElementById('instmntSaveFlag').value="Y";
			self.close();
		}
		else if('<%=request.getAttribute("sms").toString()%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
			
		}
	
	</script>
</logic:present>