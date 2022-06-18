<!--Author Name : Arun Kumar  Mishra-->
<!--Date of Creation : 8 August 2012-->
<!--Purpose  : Scheme BP Map Add->
<!--Documentation : -->


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<html:html>
<head>

	<title><bean:message key="a3s.noida" />
	</title>
<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>

<!-- jQuery for Datepicker Starts-->
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
 
<link type="text/css"
	href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<!-- jQuery for Datepicker ends -->

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/payoutScript/paymentReceipt.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('paymentReceiptAdd').bpButton.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('paymentReceiptAdd').bpButton.focus();
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

<body onload="enableAnchor();checkChanges('paymentReceiptAdd');fntab();init_fields();">
<html:form styleId="paymentReceiptAdd" method="post"		action="/paymentReceiptDispatch" >
<logic:notPresent name="author">
	
	 <input type="hidden" name="allocatedFlag" value="${list[0].allocateFlag}" id="allocatedFlag"/>
	 <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	  <input type="hidden" name="hcommon"  id="hcommon"/>
	   <input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	    <input type="hidden" name="paymentId"  id="paymentId" value="${list[0].paymentId}"/>
	      <input type="hidden" name="allocateAmount"  id="allocateAmount" value="${list[0].allocateAmount}"/>
	       <input type="hidden" name="tds"  id="tds" value="${list[0].tds}"/>
	        <input type="hidden" name="totalAllocatedAmount"  id="totalAllocatedAmount" value="${list[0].totalAllocatedAmount}"/>
	    
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    <input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		<fieldset>
			<legend>
				<bean:message key="lbl.payPaymentReceiptWaive" />
			</legend>
			<table cellpadding="0" cellspacing="1" width="100%">


				<logic:present name="editVal">
				<logic:notEmpty name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="M" />
						
					<logic:iterate id="listObj" name="list">
						<tr>
						<td>
							<bean:message key="lbl.bpName" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="bpName" styleClass="text" styleId="bpName" readonly="true" tabindex="-1"	value="${listObj.bpName}"/>
						  <html:button property="bpButton" styleId="bpButton" onclick="openLOVCommon(1370,'paymentReceiptAdd','bpName','','', '','','','hidName','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
                           <html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxBpId" styleId="lbxBpId" value="${listObj.lbxBpId}"/>
						
						</td>

				    	<td>
							<bean:message key="lbl.payPaymentType" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
						 <html:select property="paymentType" styleClass="text" styleId="paymentType"  value="${listObj.paymentType}">
						<html:option value="">Select</html:option>
						<html:option value="P">Payment</html:option>
						<html:option value="R">Receipt </html:option>
						<html:option value="W">Waive Off</html:option>
						
						</html:select>
						</td>

					</tr>
					<tr>
							<td>
								<bean:message key="lbl.payPaymentMode" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
						
						   <html:select property="paymentMode" styleClass="text" styleId="paymentMode" value="${listObj.paymentMode}">
							 <html:option value="">--Select--</html:option> 
							  <html:option value="C">Cash</html:option> 
							 <html:option value="Q">Cheque</html:option> 
							 <html:option value="D">DD</html:option> 
							 <html:option value="N">NEFT</html:option> 
							 <html:option value="R">RTGS</html:option>
							  <html:option value="S">ADJUSTMENT</html:option>
							  </html:select>
							</td>

							<td>
								<bean:message key="lbl.payPaymentDate" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
							<html:text property="paymentDate" styleId="paymentDate" styleClass="text" value="${listObj.paymentDate}" onchange="checkDate('paymentDate');"/>
												
							</td>

						</tr>
						<tr>
					    <td><bean:message key="lbl.payInstrumentNo" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
					    <html:text property="instrumentNo" styleClass="text" styleId="instrumentNo" value="${listObj.instrumentNo}"
								maxlength="50" />
					    </td>
					    <td><bean:message key="lbl.payInstrumentDate" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
				       <html:text property="instrumentDate" styleClass="text" styleId="instrumentDate" value="${listObj.instrumentDate}" onchange="checkDate('instrumentDate');"/>
					    </td>	
						</tr>
				 
						 <tr>
				 <td><bean:message key="lbl.payPaymentAmount" />
								<span><font color="red">*</font>
								</span></td>
				  <td> 	<html:text property="paymentAmount" styleClass="text" styleId="paymentAmount"  value="${listObj.paymentAmount}" readonly="true"
                     onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" />
                     <button type="button" class="topformbutton2" name="Allocate"  id="Allocate"  onclick="return allocatePaymentReceipt('M');" title="Alt+A" accesskey="A" ><span class='underLine'>A</span>llocate</button>
                     </td>
				   
				   <td>
							<bean:message key="lbl.payRemark" />
						</td>
						<td>
						<html:textarea property="makerRemark" styleId="makerRemark" styleClass="text" value="${listObj.makerRemark}"></html:textarea>
						</td>
				 </tr>
					</logic:iterate>
					</logic:notEmpty>
				</logic:present>

				<logic:notPresent name="editVal">
					<input type="hidden" id="modifyRecord" name="modifyRecord"
						value="I" />
					<tr>
						<td>
							<bean:message key="lbl.bpName" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="bpName" styleClass="text" styleId="bpName" readonly="true" tabindex="-1"	/>
						  <html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(1370,'paymentReceiptAdd','bpName','','', '','','','hidName','hidName','hidName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
                           <html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxBpId" styleId="lbxBpId" />
						</td>

						<td>
							<bean:message key="lbl.payPaymentType" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
						 <html:select property="paymentType" styleClass="text" styleId="paymentType" >
						<html:option value="">Select</html:option>
						<html:option value="P">Payment</html:option>
						<html:option value="R">Receipt </html:option>
						<html:option value="W">Waive Off</html:option>
						
						</html:select>
						</td>

					</tr>
					<tr>
							<td>
								<bean:message key="lbl.payPaymentMode" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
						
						   <html:select property="paymentMode" styleClass="text" styleId="paymentMode">
							 <html:option value="">--Select--</html:option> 
							  <html:option value="C">Cash</html:option> 
							 <html:option value="Q">Cheque</html:option> 
							 <html:option value="D">DD</html:option> 
							 <html:option value="N">NEFT</html:option> 
							 <html:option value="R">RTGS</html:option>
							  <html:option value="S">ADJUSTMENT</html:option>
							  </html:select>
							</td>

							<td>
								<bean:message key="lbl.payPaymentDate" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
							<html:text property="paymentDate" styleId="paymentDate" styleClass="text"  onchange="checkDate('paymentDate');"/>
												
							</td>

						</tr>
						<tr>
					    <td><bean:message key="lbl.payInstrumentNo" />
								<span><font color="red"></font>
								</span></td>
					    <td>
					    <html:text property="instrumentNo" styleClass="text" styleId="instrumentNo"
								maxlength="50" />
					    </td>
					    <td><bean:message key="lbl.payInstrumentDate" />
								<span><font color="red"></font>
								</span></td>
					    <td>
				       <html:text property="instrumentDate" styleClass="text" styleId="instrumentDate" onchange="checkDate('instrumentDate');"/>
					    </td>	
						</tr>
				 
						 <tr>
				 <td><bean:message key="lbl.payPaymentAmount" />
								<span><font color="red">*</font>
								</span></td>
				  <td> 	<html:text property="paymentAmount" styleClass="text" styleId="paymentAmount"  readonly="true"
                     onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" />
				    <button type="button" class="topformbutton2" name="Allocate"  id="Allocate"  onclick="return allocatePaymentReceipt('M');" title="Alt+A" accesskey="A" ><span class='underLine'>A</span>llocate</button>
				   </td>
				   <td>
							<bean:message key="lbl.payRemark" />
						</td>
						<td><br><html:textarea property="makerRemark" styleId="makerRemark" styleClass="text"></html:textarea>
						<br></td>
				 </tr>
				 
				</logic:notPresent>

				<tr>
					<td colspan="4">
						
						<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
						 <button type="button" class="topformbutton2" name="save"  id="save"  onclick="return savePaymentReceipt();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
                         <button type="button" class="topformbutton3" name="Forword"  id="Forword"  onclick="return forwardPaymentReceipt();" title="Alt+F" accesskey="F" ><span class='underLine'>F</span>orword</button>
						<!--  <button type="button" class="topformbutton3" name="Allocate"  id="Allocate"  onclick="return allocatePaymentReceipt('M');" title="Alt+A" accesskey="A" ><span class='underLine'>A</span>llocate Amount</button>      --> 
					</td>
				</tr>
				
 
 


			</table>


		</fieldset>




	<logic:present name="sms">
		<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("paymentReceiptAdd").action="paymentReceiptBehind.do";
	    document.getElementById("paymentReceiptAdd").submit();
	}	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	
</script>
	</logic:present>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
	</logic:notPresent>
	<logic:present name="author">
		   
	      <input type="hidden" name="allocateAmount"  id="allocateAmount" value="${authorList[0].allocateAmount}"/>
	       <input type="hidden" name="tds"  id="tds" value="${authorList[0].tds}"/>
	        <input type="hidden" name="totalAllocatedAmount"  id="totalAllocatedAmount" value="${authorList[0].totalAllocatedAmount}"/>
	    
	<fieldset>
			<legend>
				<bean:message key="lbl.payPaymentReceiptWaiveAuthor" />
			</legend>
			<table cellpadding="0" cellspacing="1" width="100%">
			<logic:iterate id="listObj" name="authorList">
						<tr>
						<td>
							<bean:message key="lbl.bpName" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
							<html:text property="bpName" styleClass="text" styleId="bpName" readonly="true" tabindex="-1"	value="${listObj.bpName}" disabled="true"/>
						<input type="hidden" id="modifyRecord" name="modifyRecord"/>
                           <html:hidden  property="hidName" styleId="hidName" value="" />
							<html:hidden  property="lbxBpId" styleId="lbxBpId" value="${listObj.lbxBpId}"/>
						 <input type="hidden" name="paymentId"  id="paymentId" value="${listObj.paymentId}"/>
						</td>

				    	<td>
							<bean:message key="lbl.payPaymentType" />
							<span><font color="red">*</font>
							</span>
						</td>
						<td>
						 <html:select property="paymentType" styleClass="text" styleId="paymentType"  value="${listObj.paymentType}" disabled="true">
						<html:option value="">Select</html:option>
						<html:option value="P">Payment</html:option>
						<html:option value="R">Receipt </html:option>
						<html:option value="W">Waive Off</html:option>
						
						</html:select>
						</td>

					</tr>
					<tr>
							<td>
								<bean:message key="lbl.payPaymentMode" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
						
						   <html:select property="paymentMode" styleClass="text" styleId="paymentMode" value="${listObj.paymentMode}" disabled="true">
							 <html:option value="">--Select--</html:option> 
							  <html:option value="C">Cash</html:option> 
							 <html:option value="Q">Cheque</html:option> 
							 <html:option value="D">DD</html:option> 
							 <html:option value="N">NEFT</html:option> 
							 <html:option value="R">RTGS</html:option>
							  <html:option value="S">ADJUSTMENT</html:option>
							  </html:select>
							</td>

							<td>
								<bean:message key="lbl.payPaymentDate" />
								<span><font color="red">*</font>
								</span>
							</td>
							<td>
							<html:text property="paymentDate" styleId="paymentDatea" styleClass="text" value="${listObj.paymentDate}" disabled="true"/>
												
							</td>

						</tr>
						<tr>
					    <td><bean:message key="lbl.payInstrumentNo" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
					    <html:text property="instrumentNo" styleClass="text" styleId="instrumentNo" value="${listObj.instrumentNo}" disabled="true"
								maxlength="50" />
					    </td>
					    <td><bean:message key="lbl.payInstrumentDate" />
								<span><font color="red">*</font>
								</span></td>
					    <td>
				       <html:text property="instrumentDate" styleClass="text" styleId="instrumentDateA" value="${listObj.instrumentDate}" disabled="true"/>
					    </td>	
						</tr>
				 
						 <tr>
				 <td><bean:message key="lbl.payPaymentAmount" />
								<span><font color="red">*</font>
								</span></td>
				  <td> 	<html:text property="paymentAmount" styleClass="text" styleId="paymentAmount"  value="${listObj.paymentAmount}" disabled="true"
                     onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" /></td>
				   
				   <td>
							<bean:message key="lbl.payRemark" />
						</td>
						<td>
						<html:textarea property="makerRemark" styleId="makerRemark" styleClass="text" value="${listObj.makerRemark}" disabled="true"></html:textarea>
						</td>
				 </tr>
				 <tr>
					<td colspan="4">
						
						<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
						 <button type="button" class="topformbutton3" name="Allocate"  id="Allocate"  onclick="return allocatePaymentReceipt('A');" title="Alt+A" accesskey="A" ><span class='underLine'>A</span>llocated Amount</button>
					</td>
				</tr>
					</logic:iterate>
			</table>
			</fieldset>
	</logic:present>
		</html:form>
<script>
	setFramevalues("paymentReceiptAdd");
</script>
	
</body>
</html:html>