<!--Author Name : Arun Kumar  Mishra-->
<!--Date of Creation : 8 August 2012-->
<!--Purpose  : Allocate Payment receipt->
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

<body onload="enableAnchor();init_fields();">


	<html:form styleId="allocatePaymentReceipt" method="post"action="/paymentReceiptDispatch" >
	<input type="hidden" name="paymentId" id="paymentId" value="${requestScope.paymentId}"/>
	<input type="hidden" name="paymentAmount" id="paymentAmount" value="${requestScope.paymentAmount}"/>
	<input type="hidden" id="tdsRate" name="tdsRate" value="${requestScope.tdsRate}"/>
	 <input type="hidden" name="chkValue" id="chkValue" />
	<fieldset>
			<legend>
				<bean:message key="lbl.paymentReceiptAllocation" />
			</legend>
			<logic:notPresent name="authorFlag">
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1" id="gridTable">
								<tr>
								<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.select" /> </strong>
									</td>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.bpName" /> </strong>
									</td>
									
								<!-- 	<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payLoanNo" /> <br> </b>
									</td> -->
							<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.activity" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payScheme" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.orgAmount" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.balAmount" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.totalAllocateAmount" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.allocateAmount" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payTds" /> <br> </b>
									</td>
									</tr>
						<logic:present name="allocateList">
						<logic:iterate id="allocateListObj" name="allocateList" indexId="count">
						<tr class="white1">
						<td>
						
						<input type="checkbox" name="chk" id="chk<%= count.intValue()+1%>"/>
						
						
						<input type="hidden" name="txnCaseId<%= count.intValue()+1%>" value="${allocateListObj.txnCaseId}" id="txnCaseId<%=count.intValue()+1%>"/>
						</td>
						<td>
						<input type="hidden" name="bpId" value="${allocateListObj.bpId}" id="bpId<%=count.intValue()+1%>"/>
						${allocateListObj.bpName }
						</td>
					
						<td>${allocateListObj.activityDesc}</td>
						<td>${allocateListObj.schemeName}</td>
						<td><input type="text" class="text3"  name="orgAmount" value="${allocateListObj.orgAmount }" id="orgAmount<%=count.intValue()+1%>" readonly="readonly"/></td>
						<td><input type="text" class="text3"  name="balAmount" value="${allocateListObj.balanceAmount}" id="balAmount<%=count.intValue()+1%>" readonly="readonly"/></td>
						<td><input type="text" class="text"  name="totalAllocateAmount<%= count.intValue()+1%>" value="" id="totalAllocateAmount<%= count.intValue()+1%>" 
						onchange="allocateAmountAndTDS('<%= count.intValue()+1%>')"  onkeypress="return numbersonly(event,this.id,18);"  onblur="formatNumber(this.value,this.id);" /></td>
						<td><input type="text" class="text3" name="allocateAmount<%= count.intValue()+1%>" value="" id="allocateAmount<%= count.intValue()+1%>" 
						  onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" readonly="readonly" /></td>
						<td><input type="text" class="text3"  name="tds<%= count.intValue()+1%>" value="" id="tds<%= count.intValue()+1%>" 
						  onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" readonly="readonly" /></td>
						</tr>
						</logic:iterate>
						</logic:present>
						
			            </table>
						</td>
					</tr>
					<tr>
					<td>
					<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
					<button type="button" class="topformbutton2" name="save"  id="save"  onclick="return saveAllocatePaymentReceipt();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
                        
					</td>
					
					</tr>
				</table>
			</logic:notPresent>
			<logic:present name="authorFlag">
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1" id="gridTable">
								<tr>
								<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.select" /> </strong>
									</td>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.bpName" /> </strong>
									</td>
										
								<!-- 	<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payLoanNo" /> <br> </b>
									</td> -->
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.activity" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payScheme" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.orgAmount" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.balAmount" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.totalAllocateAmount" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.allocateAmount" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payTds" /> <br> </b>
									</td>
									
									</tr>
						<logic:present name="allocateList">
						<logic:iterate id="allocateListObj" name="allocateList" indexId="count">
						<tr class="white1">
						<td>
						
					
						<input type="checkbox" name="chk" id="chk<%= count.intValue()+1%>" disabled="disabled"/>
						
						
						<input type="hidden" name="txnCaseId<%= count.intValue()+1%>" value="${allocateListObj.txnCaseId}" id="txnCaseId<%=count.intValue()+1%>"/>
						</td>
						<td>
						<input type="hidden" name="bpId" value="${allocateListObj.bpId}" id="bpId<%=count.intValue()+1%>"/>
						${allocateListObj.bpName }
						</td>
						
						<td>${allocateListObj.activityDesc}</td>
						<td>${allocateListObj.schemeName}</td>
						<td><input type="text" class="text3"  name="orgAmount" value="${allocateListObj.orgAmount }" id="orgAmount<%=count.intValue()+1%>" readonly="readonly"/></td>
						<td><input type="text" class="text3"  name="balAmount" value="${allocateListObj.balanceAmount}" id="balAmount<%=count.intValue()+1%>" readonly="readonly"/></td>
						<td><input type="text" class="text" name="totalAllocateAmount<%= count.intValue()+1%>" value="" id="totalAllocateAmount<%= count.intValue()+1%>" 
						  onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" readonly="readonly"/></td>
						<td><input type="text"  class="text3" name="allocateAmount<%= count.intValue()+1%>" value="" id="allocateAmount<%= count.intValue()+1%>" 
						  onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" readonly="readonly"/></td>
						<td><input type="text" class="text3"  name="tds<%= count.intValue()+1%>" value="" id="tds<%= count.intValue()+1%>" 
						  onkeypress="return numbersonly(event,this.id,18);" onkeyup="checkNumber(this.value, event, 18,this.id);" onblur="formatNumber(this.value,this.id);" readonly="readonly"/></td>
						</tr>
						</logic:iterate>
						</logic:present>
						
			            </table>
						</td>
					</tr>
					<tr>
					<td>
					<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
					 
                        
					</td>
					
					</tr>
				</table>
			</logic:present>
			
			</fieldset>
	</html:form>
	<logic:present name="allocateAmount">
		<script type="text/javascript">
	//	alert("In allocate amount");
	var allocteAmount='<%=request.getAttribute("allocateAmount").toString()%>';
	var tds='<%=request.getAttribute("tds").toString()%>';
	var totalAllocatedAmount='<%=request.getAttribute("totalAllocatedAmount").toString()%>';
	var allocteAmountStr=allocteAmount.split(":");
	var tdsStr=tds.split(":");
	var totalAllocatedAmountStr=totalAllocatedAmount.split(":");
	//alert(allocteAmountStr);
	for(var i=0;i<totalAllocatedAmountStr.length;i++){
	var allocateAm=allocteAmountStr[i].split("@");
	var tdsAm=tdsStr[i].split("@");
	var totalAllocatedAm=totalAllocatedAmountStr[i].split("@");
	//alert("allocateAm:-"+allocateAm);
	var table = document.getElementById("gridTable");	
	var rowCount = table.rows.length;
	for(var j=1;j<rowCount;j++){
	if(parseFloat(document.getElementById('txnCaseId'+j).value)==parseFloat(allocateAm[0].trim())){
	document.getElementById('chk'+j).checked=true;
	document.getElementById('totalAllocateAmount'+j).value=totalAllocatedAm[1];
	document.getElementById('allocateAmount'+j).value=allocateAm[1];
	document.getElementById('tds'+j).value=tdsAm[1];
	
	}
	}
	}
	 </script>
	</logic:present>
		<logic:present name="sms">
		<script type="text/javascript">

    
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		//document.getElementById("paymentReceiptAdd").action="schemeBpMapBehind.do";
	    //document.getElementById("paymentReceiptAdd").submit();
	   window.opener.document.getElementById("allocatedFlag").value="Y";
	    window.close();
	}	
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	
</script>
	</logic:present>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
	</body>
	</html:html>
	
	