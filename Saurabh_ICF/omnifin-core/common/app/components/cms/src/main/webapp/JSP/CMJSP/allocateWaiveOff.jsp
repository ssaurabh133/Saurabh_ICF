<!-- 
Author Name :- Neeaj Tripathi
Date of Creation :04/07/2012
Purpose :-  screen for Waive off Aocation
-->


<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
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
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/loanClosure.js"></script>
    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	<!--[if gte IE 5]>
	<style type="text/css">
	
	.white {
	background:repeat-x scroll left bottom #FFFFFF;
	filter:progid:DXImagetransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	.white2 {
	background:repeat-x scroll left bottom #CDD6DD;
	filter:progid:DXImagetransform.Microsoft.AlphaImageLoader(src='images/grey-up.png' ,sizingMethod='scale');
	}
	</style>
	<![endif]-->
	
</head>
<body onload="enableAnchor();init_fields();">
	<input type="hidden" name="contextPath"	value="<%=request.getContextPath()%>" id="contextPath" />
	<input type="hidden" name="orgWaiveAmt"	value="${orgWaiveAmt}" id="orgWaiveAmt" />
	<input type="hidden" name="loanId"	value="${loanId}" id="loanId" />
	<input type="hidden" name="terminationId"	value="${terminationId}" id="terminationId" />	
	
	<html:form action="/allocateWaiveOff" styleId="allocateWaiveOffForm" method="post">
	<fieldset><legend><bean:message key="lbl.allocateWaiveOff" /></legend>			
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="gridtd">
						<table width="100%" border="0" cellspacing="1" cellpadding="4"
							id="gridTable">
							<tr class="white2">
								<td align="center"><strong><bean:message key="lbl.charge.fee"/></strong></td>
								<td align="center"><strong><bean:message key="lbl.adviceAmount"/></strong></td>
								<td align="center"><strong><bean:message key="lbl.adjustedAmount"/></strong></td>
								<td align="center"><strong><bean:message key="lbl.totalAmtDue"/></strong></td>
								<td align="center"><strong><bean:message key="lbl.waiveOffAmount" /></strong></td>
								<td align="center"><strong><bean:message key="lbl.balanceAmount"/></strong></td>
							</tr>
							<logic:present name="allocatList">
								<logic:iterate name="allocatList" id="list" indexId="count">
									<tr class="white1">
										<td>
												${list.charges}
												<input type="hidden" align="right" name="chargeName" readonly="readonly" id="chargeName<%=count.intValue()+1%>" value="${list.chargeCode}" />
										</td>
										<td><input type="text" align="right" name="adviceAmt" readonly="readonly" id="adviceAmt<%=count.intValue()+1%>" value="${list.adviceAmt}"  style="text-align: right" /></td>
										<td><input type="text" align="right" name="adjustedAmt" readonly="readonly" id="adjustedAmt<%=count.intValue()+1%>" value="${list.adjustedAmt}"  style="text-align: right" /></td>
										<td><input type="text" align="right" name="amountDue" readonly="readonly" id="amountDue<%=count.intValue()+1%>" value="${list.amountDue}"  style="text-align: right" /></td>
										<td>
											<logic:notPresent name="earlyAuthor">
												<input type="text" align="right" name="waiveOffAmount" maxlength="20" id="waiveOffAmount<%=count.intValue()+1%>" value="${list.waiveOffAmount}"  style="text-align: right" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);calculateBalance('<%=count.intValue()+1%>');" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/>
											</logic:notPresent>
											<logic:present name="earlyAuthor">
												<input type="text" align="right" name="waiveOffAmount" maxlength="20" readonly="readonly" id="waiveOffAmount<%=count.intValue()+1%>" value="${list.waiveOffAmount}"  style="text-align: right" onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/>
											</logic:present>
										</td>
										<td><input type="text" align="right" name="balsAmt" readonly="readonly" id="balsAmt<%=count.intValue()+1%>" value="${list.balsAmt}"  style="text-align: right"/></td>
									</tr>
								</logic:iterate>
							<tr class="white1">
							    <logic:present name="totalList">
								<logic:iterate name="totalList" id="ttlList">
								<td colspan="1" class="white2">TOTAL</td>
								<td><input type="text" name="totalAdviceAmt" align="right" readonly="readonly" id="totalAdviceAmt" value="${ttlList.totalAdviceAmount}"  style="text-align: right"/></td>
								<td><input type="text" name="totalAdjustedAmt" align="right" readonly="readonly" id="totalAdjustedAmt" value="${ttlList.totalAdjustedAmount}"  style="text-align: right"/></td>
								<td><input type="text" name="totalAmountDue" align="right" readonly="readonly" id="totalAmountDue" value="${ttlList.totalAmountDue}"  style="text-align: right"/></td>
								<td><input type="text" name="totalWaiveOffAmount" align="right" readonly="readonly" id="totalWaiveOffAmount" value="${ttlList.totalWaiveOffAmount}"  style="text-align: right"/></td>
								<td><input type="text" name="totalBalsAmt" align="right" readonly="readonly" id="totalBalsAmt" value="${ttlList.totalBalsAmt}"  style="text-align: right"/></td>
							    </logic:iterate>
							    </logic:present>
							</tr>
							</logic:present>
						</table>
					</td>
				</tr>
				<tr></tr>
				<tr>				
				<td><B><bean:message key="lbl.waiveOffApprovedBy" /></B> &nbsp;&nbsp;     
               		  <html:text property="approvedBy" readonly="true" styleClass="text" styleId="approvedBy" tabindex="-1" value="${approvedBY}" />
				 	  <html:button property="approvedByButton" styleId="approvedByButton" onclick="openLOVCommon(504,'allocateWaiveOffForm','approvedBy', '','','','','','lbxapprovedBy');" value=" " styleClass="lovbutton"> </html:button>
				      <html:hidden  property="lbxapprovedBy" styleId="lbxapprovedBy" value="${lbxapprovedBy}"/>
        			    &nbsp;&nbsp;&nbsp;&nbsp;
				       <logic:notPresent name="earlyAuthor">
						<button type="button" name="save" id="save" class="topformbutton2" accesskey="V" title="Alt+V"	onclick="return saveAllocateWaiveOff();" ><bean:message key="button.save" /></button>
						</logic:notPresent>
        			 </td>
        			 <td></td>
        			 <td></td>
				</tr>				
			</table>
		</fieldset>
		<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
		</html:form>
		<logic:present name="save">
			<script type="text/javascript">
				alert("Data save successfully.");
				window.opener.document.getElementById('changeWaiveOff').value='N';
				window.opener.document.getElementById('waiveAllocated').value='Y';
				window.close();
			</script>
		</logic:present>
		<logic:present name="notSave">
			<script type="text/javascript">
				alert("Data not saved.");
			</script>
		</logic:present>
		<logic:present name="error">
			<script type="text/javascript">
				alert(&{error});
			</script>
		</logic:present>
		
		
	</body>
</html:html>