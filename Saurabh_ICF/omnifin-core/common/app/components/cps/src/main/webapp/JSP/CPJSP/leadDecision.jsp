<%--

	Created By Sajog      
 	
 --%>
 
<%@ page language="java"%>
<%@ page session="true"%>
<%@ page import="java.util.Date"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>

	<title><bean:message key="a3s.noida" /></title>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/popup.js"></script>


	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cpScript/cplead.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>


	<script type="text/javascript">
		function textCounter( field, countfield, maxlimit ) {
	  if ( field.value.length > maxlimit )
	  {
	    field.value = field.value.substring( 0, maxlimit );
	    alert( 'Textarea value can only be 255 characters in length.' );
	    return false;
	  }
	  else
	  {
	    countfield.value = maxlimit - field.value.length;
	  }
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

<body onload="enableAnchor();checkChanges('leadDecision');disableDate();">

	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<html:form action="/leadCapturingBehindAction" styleId="leadDecision" method="post">
		<input type="hidden" name="leadcapt" id="leadcapt" />
	

		<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>" />
		<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
			<html:hidden property="leadId" styleId="leadId" value="${sessionScope.leadTrackNote}" />
	
	<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.leadno" /></b></td>
          <td >${leadDetails[0].leadId}</td>
          <td><b><bean:message key="lbl.gendate"/></b></td>
          <td>${leadDetails[0].applicationdate}</td>
          <td><b><bean:message key="lbl.customername"/></b> </td>
          <td colspan="3" >${leadDetails[0].customerName}</td>
         </tr>
          <tr class="white2">
          	  <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${leadDetails[0].productType}</td>
	          <td><b><bean:message key="lbl.product"/></b></td>
	          <td >${leadDetails[0].product}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td >${leadDetails[0].scheme}</td>
	          <td ><b><bean:message key="lbl.currentStage"/></b></td>
	          <td>LEAD TRACKING</td>
          </tr>
        </table>
</td>
</tr>
</table>
 
</fieldset>

				<logic:present name="tracking">					
					<fieldset id="leaddecision">
							<legend>
								<bean:message key="lbl.leaddecision" />
							</legend>
							<table width="100%">
							<tr>
									<td width="19%">
										<bean:message key="lbl.leaddecision" /><span><font style="color: red;">  *</font></span>
									</td>
									<td>
										<html:select property="decision" styleId="decision" styleClass="text" tabindex="3" value="${leadDetails[0].decision}" onchange="disableDate();">
										
										<html:option value="Approved">Approved</html:option>
										<html:option value="Rejected">Rejected</html:option>
										</html:select>
									</td>
									
									<td width="19%">
										<bean:message key="lbl.Remarks" /><span><font style="color: red;">  *</font></span>
									</td>
									<td>
										<html:textarea property="remarks" styleClass="text" styleId="remarks" rows="4" cols="12" value="${leadDetails[0].remarks}" tabindex="4" onblur="textCounter(this,this.form.counter,255);" />
										<input type="hidden" name="counter" maxlength="3" size="3" value="255" onkeypress="textCounter(this.form.counter,this,255);" />
									</td>
								</tr>
							<tr id="caseRejected">
 									<td width="13%"><bean:message key="lbl.expectedLoginDate" /><span><font style="color: red;">  *</font></span></td>
							     	<td ><html:text property="expectedLoginDate" tabindex="1"  styleClass="text" styleId="expectedLoginDate" value="${leadDetails[0].expectedLoginDate}" maxlength="10" onchange="checkDate('expectedLoginDate');"/></td>

							     	 <td width="13%"><bean:message key="lbl.expectedDisbursalDate" /><span><font style="color: red;">  *</font></span></td>
							     	<td ><html:text property="expectedDisbursalDate" tabindex="2"  styleClass="text" styleId="expectedDisbursalDate" maxlength="10" value="${leadDetails[0].expectedDisbursalDate}" onchange="checkDate('expectedDisbursalDate');" /></td>

      					 	</tr>
								
								<tr id="leadRejected">
									<td width="19%">
										<bean:message key="lbl.rej_Reason" /><span><font style="color: red;"> *</font></span>
									</td>
									<td>
					  					<html:text property="reasonDesc" styleClass="text" styleId="reasonDesc" readonly="true" tabindex="-1"/>
					  					<html:hidden property="reasonId" styleId="reasonId"  value=""/>
					  					<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(507,'leadDecision','reasonDesc','','', '','','','reasonId')" value=" " styleClass="lovbutton"></html:button>
			    	 				</td>
								</tr>
									<tr>
										<td>
											<button type="button" name="Save" title="Alt+V" accesskey="V" id="Save" class="topformbutton2" onclick="saveTrackingDetails(id,'${userobject.businessdate}');" ><bean:message key="button.save" />
											</button>
											<html:hidden property="status" value="${leadDetails[0].status}" styleId="status"></html:hidden>
										</td>
									</tr>
							</table>
						</fieldset>
					</logic:present>

<!-- Changes Start By Sanjog-->					
					<logic:present name="LeadViewMode">					
					<fieldset id="leaddecision">
							<legend>
								<bean:message key="lbl.leaddecision" />
							</legend>
							<table width="100%">
							<tr>
									<td width="19%">
										<bean:message key="lbl.leaddecision" /><span><font style="color: red;">  *</font></span>
									</td>
									<td>
										<html:select property="decision" styleId="decision" disabled="true" styleClass="text" tabindex="3" value="${leadDetailsView[0].decision}" onchange="disableDate();">
										<html:option value="A">Approved</html:option>
										<html:option value="P">Pending</html:option>
										<html:option value="L">Allocated</html:option>
										<html:option value="F">Forwarded</html:option>
										<html:option value="X">Rejected</html:option>
										</html:select>
									</td>
									
									<td width="19%">
										<bean:message key="lbl.Remarks" /><span><font style="color: red;">  *</font></span>
									</td>
									<td>
										<html:textarea property="remarks" styleClass="text" styleId="remarks" disabled="true" rows="4" cols="12" value="${leadDetailsView[0].remarks}" tabindex="4" onblur="textCounter(this,this.form.counter,255);" />
									</td>
								</tr>
							<tr id="caseRejected">
 									<td width="13%"><bean:message key="lbl.expectedLoginDate" /><span><font style="color: red;">  *</font></span></td>
							     	<td ><html:text property="expectedLoginDate" tabindex="1" disabled="true" styleClass="text" styleId="expectedLoginDate1" value="${leadDetailsView[0].expectedLoginDate}" maxlength="10"/></td>

							     	 <td width="13%"><bean:message key="lbl.expectedDisbursalDate" /><span><font style="color: red;">  *</font></span></td>
							     	<td ><html:text property="expectedDisbursalDate" tabindex="2" disabled="true"  styleClass="text" styleId="expectedDisbursalDate1" maxlength="10" value="${leadDetailsView[0].expectedDisbursalDate}"/></td>

      					 </tr>
								
							</table>
						</fieldset>
					</logic:present>
					<!-- Changes End By Sanjog-->

		
	<logic:present name="msg">
			<script type="text/javascript">

			if('<%=request.getAttribute("msg").toString()%>'=='LT'){
				disableDate();
				alert('<bean:message key="lbl.dataSave"/>');
					parent.location.href = "<%=request.getContextPath()%>/leadTrackingSearchBehind.do";
								
				}
			</script>
	</logic:present>
	</html:form>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	
</div>
   <script>
	setFramevalues("leadDecision");
</script>

</body>
</html:html>
