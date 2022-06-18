<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
	


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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/imdScript.js"></script>
	    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/cmScript/asset.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript">
  		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}




   
	</script>
	<script type="text/javascript">
	<!--
		function onLoadPage(){
  <%if(request.getAttribute("setID") != null) {%>
	  window.close();
	<%  }%>
	 } 	
	-->
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
	<body onload="enableAnchor();init_fields();onLoadPage()">
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/imdMakerViewAction" method="post" styleId="viewReceivableForm">
	<div class="" style="">
		<span class="">&nbsp;</span>
	   </div>                    
	
 <input type="hidden" name="canForward" value="${requestScope.canForward}" id="canForward" />
    <input type="hidden" name="amount" value="<%=request.getAttribute("amount") %>" id="amount" />
  <input type="hidden" name="receiptAmount" value="<%=request.getAttribute("receiptamount") %>" id="receiptAmount" />	 
	 <fieldset>
				<legend>
					<bean:message key="lbl.imdDetails" />
				</legend>
				<input type="hidden" name="contextPath"	value="<%=request.getContextPath()%>" id="contextPath" />			
	            <input type="hidden" name="lbxLoanNoHID" value="${requestScope.loanId}" id="lbxLoanNoHID"/>
	            <input type="hidden" name="lbxDealNo" value="${requestScope.loanId}" id="lbxDealNo"/>
	            <input type="hidden" name="instrumentID" value="${requestScope.instrumentId}" id="instrumentID"/>
	            <input type="hidden" name="businessPartnerType" value="${requestScope.bpType}" id="businessPartnerType"/>
	            <input type="hidden" name="tdsAmount" value="${requestScope.tdsAmount}" id="tdsAmount"/>
	            
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellspacing="1" cellpadding="4"
								id="gridTable">
										<tr class="white2">
											<td>
												<b><bean:message key="lbl.chargeCode" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.chargeDesc" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.chargeBPN" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.taxIncl" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.taxRate1" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.taxRate2" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.bpMethod" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.chargeAmount" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.chargeFinal" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.balanceAmount" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.imdAllocatedAmount" />
												</b>
											</td>
										</tr>

								<logic:present name="ReceivableList">
									<logic:iterate name="ReceivableList" id="subcharges" indexId="count" >
												<tr class="white1">
													<html:hidden property="chargeIdDtl"
														value="${subcharges.chargeId}" />
													<td>
														${subcharges.chargeCode}
														<input type="hidden" name="chargeCode" id="chargeCode<%=count.intValue()+1%>" value="${subcharges.chargeId}"/>
														<input type="hidden" name="txnAdvicedIDArry" id="txnAdvicedID<%=count.intValue()+1%>" value="${subcharges.chargeId}"/>
														<input type="hidden" name="chargeCodeIDArry" id="chargeCodeID<%=count.intValue()+1%>" value="${subcharges.chargeCode}"/>
													</td>
													<td>
														${subcharges.chargeDesc}
													</td>
													<td>
														${subcharges.chargeBPId}
													</td>
													<td>
														${subcharges.taxsInclusive}
													</td>
													<td>
														${subcharges.taxtRat1}
													</td>
													<td>
														${subcharges.taxtRat2}
													</td>
													<td>
														${subcharges.chargeMethod}
													</td>
													<td>
														${subcharges.chargeCal}
													</td>
													<td>
														${subcharges.chargeFinal}
													<input type="hidden" class="text" id="finalAmount<%=count.intValue()+1%>" name="finalAmount"	value=" ${subcharges.chargeFinal}" />
													</td>
													<td>
														${subcharges.balanceAmount}
													<input type="hidden" name="balanceAmount" id="balanceAmount<%=count.intValue()+1%>" value="${subcharges.balanceAmount}"/>
													</td>
													<logic:notPresent name="viewReceivableList">
													<td>
														<input type="text" class="text" id="imdAllocatedAmount<%=count.intValue()+1%>"
															 name="allocatedArry"	value=" ${subcharges.imdAllocatedAmount}" 
		    	 											 onkeypress="return numbersonly(event,id,18)" 
		    	 											 onkeyup="checkNumber(this.value, event, 18,id);" 
			 												 onfocus="keyUpNumber(this.value, event, 18,id);"/>
													</td>
													</logic:notPresent>
													<logic:present name="viewReceivableList">
													<td>
														${subcharges.imdAllocatedAmount}
													</td>
													</logic:present>

												</tr>
											</logic:iterate>
										</logic:present>
							</table>
						</td>
					</tr>
					<logic:notPresent name="viewReceivableList">
					<tr>
						<td>
							<button type="button" class="topformbutton2"  id="save" onclick="return onViewImdReceivableSave('<bean:message key="msg.sumAllocAmntShudEqRec" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
						</td>
					</tr>
					</logic:notPresent>
				</table>
			</fieldset>
	 </html:form>
<logic:present name="procval">
	<script type="text/javascript">
	if('<%=request.getAttribute("procval")%>'!= 'NONE')
	{
	   	alert('<%=request.getAttribute("procval").toString()%>');
		window.close();
	}
	</script>
</logic:present>
	  <logic:present name="msg">
		<script type="text/javascript">
		
		if('<%=request.getAttribute("msg").toString()%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
				window.close();
		
		}
	
		else if('<%=request.getAttribute("msg").toString()%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
			
		}
	
		</script>
		</logic:present>	
	 
	 
 <logic:present name="setID">

</logic:present>	
	    
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
  </body>
</html:html>
     
 