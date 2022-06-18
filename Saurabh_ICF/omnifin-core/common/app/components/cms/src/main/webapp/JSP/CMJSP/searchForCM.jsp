
<!-- 
Author Name :- Manisha Tomar

Date of Creation :20-09-2011
Purpose :-  screen for the Search of all Loan No 
-->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/disableBut.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/customerService/customerServiceScript.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
	
	<script type="text/javascript">
		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
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
	<body onload="enableAnchor();document.getElementById('searchForCMForm').dealNoButton.focus();init_fields();" onunload="closeAllWindowCallUnloadBody();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="defaultFormate" value="${defaultFormate}" id="defaultFormate"/>
	<input type="hidden" name="earlyClosureType" value="${earlyClosureType}" id="earlyClosureType"/>
	
	<div id="centercolumn">
	 
	<div id="revisedcontainer">
	
	<html:form action="/searchCMBehindAction" method="post" styleId="searchForCMForm">

<fieldset>	  
<legend><bean:message key="lbl.loanViewer"/></legend>    
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>	
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr><td><input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/></td></tr>
		<tr>	   
            <td><bean:message key="lbl.DealNo"></bean:message> </td>
		    <td>
		    	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		     	<html:text styleClass="text" property="dealNo" styleId="dealNo" maxlength="20"  value="" readonly="true" tabindex="-1"/>
             	<html:button property="dealNoButton" styleId="dealNoButton" onclick="openLOVCommon(172,'searchForCPForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
             	<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="" />
            </td>
		  	<td><bean:message key="lbl.loanNumber"></bean:message> </td>
		     <td>
		     	<html:text styleClass="text" property="loanNo" styleId="loanNo" maxlength="20"  value="" readonly="true" tabindex="-1"/>
             	<html:button property="loanAccountButton" styleId="loanAccountButton" onclick="openLOVCommon(225,'searchForCMForm','loanNo','lbxDealNo','','lbxDealNo','','','customerName','rvFlag')" value=" " styleClass="lovbutton"></html:button>
             	<html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="" />
             	<input type="hidden" name="rvFlag" id="rvFlag" value="${list[0].restrictedFlag}" />
            </td>
		</tr>	
		<tr>
		 	<td><bean:message key="lbl.customerName"/></td>
	     	<td><html:text property="customerName"  styleClass="text" styleId="customerName" value="" maxlength="50" ></html:text></td>
	     	<td><bean:message key="lbl.appFormNo"/></td>
	     	<td><html:text property="appFormNo"  styleClass="text" styleId="appFormNo" value="" maxlength="50" ></html:text></td>
		</tr>
		<tr>
		 	<td><bean:message key="lbl.mbnumber"/></td>
	     	<td><html:text property="mbNumber"  styleClass="text" styleId="mbNumber"  onkeypress="return isNumberKey(event);" value="" maxlength="10" ></html:text></td>
	     	<td><bean:message key="lbl.drivingLic"/></td>
	     	<td><html:text property="drivingLic"  styleClass="text" styleId="drivingLic" value="" maxlength="25" ></html:text></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.panNo"/></td>
			<td><html:text property="panNo"  styleClass="text" styleId="panNo"  onchange="return upperMe('panNo');"  value="" maxlength="10" ></html:text></td>
			<td><bean:message key="voterId"/></td>
			<td><html:text property="voterId"  styleClass="text" styleId="voterId"  onchange="return upperMe('panNo');"  value="" maxlength="20" ></html:text></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.vehicleNo"/></td>
			<td><html:text property="vehicleNo"  styleClass="text" styleId="vehicleNo"  value="" maxlength="25" ></html:text></td>
		</tr>
		
		<tr>
			<td><button type="button" class="topformbutton2"  id="search" onclick="return onsearchCM();" title="Alt+S" accesskey="S"><bean:message key="button.search" /></button></td>
		</tr>
		
		
		</table>	
	</td>	
</tr>
</table>
</fieldset>	
	
		
	 
<logic:present name="true">
<br></br>
<fieldset><legend><bean:message key="lbl.deals"/></legend>  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
    	 <td align="center"><b>Select</b></td>
	 	 <td align="center"><b><bean:message key="lbl.dealNo"/></b></td>
         <td align="center"><b><bean:message key="lbl.LoanNo"/></b></td>
	     <td align="center"><b><bean:message key="lbl.customerName"/></b></td>	     
	     <td align="center"><b><bean:message key="fatherHusband"/></b></td>	     
	     <td align="center"><b><bean:message key="lbl.dob"/></b></td>
	     <td align="center"><b><bean:message key="address.addr"/></b></td>	     	     
         <td align="center"><b><bean:message key="lbl.product"/></b></td>
         <td align="center"><b><bean:message key="lbl.scheme"/></b></td>
	     <td align="center"><b><bean:message key="lbl.appFormNo"/></b></td>
	     <td align="center"><b><bean:message key="lbl.panNo"/></b></td>
	     <td align="center"><b><bean:message key="lbl.loanStatus"/></b></td>
	     <td align="center"><b><bean:message key="lbl.branchname"/></b></td>
	     <td align="center"><b><bean:message key="lbl.disbursalStatus"/></b></td>
	     <td align="center"><b><bean:message key="lbl.npaStatus"/></b></td>
	     <td align="center"><b><bean:message key="lbl.legalRepoFlag"/></b></td>
	     <td align="center"><b><bean:message key="lbl.maker"/></b></td>
	     <td align="center"><b><bean:message key="lbl.author"/></b></td>
	     
	</tr>
	<logic:present name="list">	
		<logic:iterate name="list" id="sublist">
		<tr class="white1">
		<logic:present name="checked">
	    	<td align="center"><input type="radio" name="radioId" id="radioId" value="${sublist.lbxLoanNoHID}" checked="checked"/></td>
	    	</logic:present>
	    	<logic:notPresent name="checked">
	    	<td align="center"><input type="radio" name="radioId" id="radioId" value="${sublist.lbxLoanNoHID}" onclick="enableButton();" /></td>
	    	</logic:notPresent>
	     	<td>${sublist.dealNo}</td>
	     	<td>${sublist.loanNo}</td>
	     	<td>${sublist.customerName}</td>	     	
	     	<td>${sublist.fatherHusband}</td>
	     	<td>${sublist.dob}</td>
	     	<td>${sublist.addr}</td>	     	
	    	<td>${sublist.product}</td>	    
	    	<td>${sublist.scheme}</td>
	    	<td>${sublist.appFormNo}</td>	
	    	<td>${sublist.panNo}</td>	
	    	<td>${sublist.loanStatus}</td>	
	    	<td>${sublist.loanBranch}</td>	
	    	<td>${sublist.disbursalStatus}</td>
	    	<td>${sublist.npaStatus}</td>
	    	<td>${sublist.legalRepoFlag}</td>
	    	<td>${sublist.maker}</td>
	    	<td>${sublist.author}</td>
	    	<html:hidden  property="lbxDealNomain" styleId="lbxDealNomain" value="${sublist.lbxDealNo}" />
	    	<html:hidden  property="lbxLoanNoHIDmain" styleId="lbxLoanNoHIDmain" value="${sublist.lbxLoanNoHID}" />
	    	<html:hidden  property="loanNumbermain" styleId="loanNumbermain" value="${sublist.loanNo}" />
	    	<html:hidden  property="customerNamemain" styleId="customerNamemain" value="${sublist.customerName}" />
	    	<html:hidden  property="termination_Id" styleId="termination_Id" value="${sublist.terminationId}"/>
	    	<input type="hidden" name="hiddenRecStatus" id="hiddenRecStatus" value="${sublist.loanStatus}" />
	    	<input type="hidden" name="hiddenfundingType" id="hiddenfundingType" value="${sublist.fundingType}" />
	    	
	     </tr> 
   		</logic:iterate>
   </logic:present>
  
 <logic:present name="datalist">
 <tr class=white2>
 <td colspan="18">
 	<bean:message key="lbl.noDataFound" />
 </td>
 </tr>
 </logic:present>	
 <logic:present name="error">
	<script type="text/javascript">	
		alert('${error}');
		alert("Report can not be Generate.");
	</script>
	</logic:present>
  
   
   </table>
   </td>
</tr>
</table>
</fieldset>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
	<logic:present name="checked">
	<td>
		<button type="button" class="topformbutton2"  id="deal_viewer"  title="Alt+D" accesskey="D" onclick="return dealViewer();"><bean:message key="button.dealViewer" /></button>
		<button type="button" class="topformbutton2" id="loan_viewer"  onclick="return loanViewerPresentation();" title="Alt+L" accesskey="L"><bean:message key="button.loanViewer" /></button>
		<button type="button" class="topformbutton2" id="pdc_status"  onclick="return pdcStatusForLoanViewer();"  title="Alt+P" accesskey="P"><bean:message key="button.pdcStatus" /></button>
		<button type="button" class="topformbutton3" id="cheque_status"  onclick="chequeStatusforloanViewer();"     title="Alt+C" accesskey="C"><bean:message key="button.chequestatus" /></button>
		<button type="button" class="topformbutton3" id="statement_of_ac"  onclick="statementOfAccount();" title="Alt+S" accesskey="S"><bean:message key="button.statement" /></button>
		<button type="button" class="topformbutton4" id="old_repayment_schedule"  onclick="return viewOldRepaymentSchedule();" title="Alt+R" accesskey="R"><bean:message key="button.oldrepay" /></button>
		<button type="button" class="topformbutton4" value="Repayment Schedule" id="repayment_schedule" onclick="return viewRepaymentSchedule();" ><bean:message key="button.repayschedule1" /></button>
		<button type="button" class="topformbutton4" id="early_closer_detail"   onclick="return earlyClosureDetails();" title="Alt+E" accesskey="E"><bean:message key="button.earlyCSE" /></button>
		<button type="button" class="topformbutton3" id="allocation_detail"  onclick="allocationDetails(id);" title="Alt+A" accesskey="A"><bean:message key="button.allocDts" /></button>
		<button type="button" id="viewPay" class="topformbutton3" onclick="return viewPayableLoanViewer('<bean:message key="msg.LoanAccountNo" />');" title="Alt+P" accesskey="P"><bean:message key="button.viewPayable" /></button>
		<button type="button" class="topformbutton3" accesskey="R" title="Alt+R" onclick="return viewReceivableLoanViewer('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable"/></button>
		<button type="button"  class="topformbutton4" id="cancel" onclick="return assetInsuranceViewer();" >Asset Insurance Viewer</button>
		<button type="button"  class="topformbutton4" id="loanSummary" onclick="return loanSummaryViewer();" ><bean:message key="button.loanSummaryViewer"/></button>
		<button type="button" name="customerExposure" id="customerExposure" title="Alt+E" accesskey="E" class="topformbutton4" onclick="openCustomerExposureAtCMCustomerService('LIM');"><bean:message key="button.customerExposure"/></button>
		<button type="button" name="camReport" id="camReport" title="Alt+R" accesskey="R" class="topformbutton2" onclick="openCamReports();" ><bean:message key="button.camReport"/></button>
		<button type="button" name=" mybutton" title="Alt+V" accesskey="V" class="topformbutton4" onclick="disbursementReport();"> <bean:message key="button.disbursementVoucher" /></button>
		<button type="button" class="topformbutton2" id="ach_status"  onclick="return achStatusForLoanViewer();"  title="Alt+H" accesskey="H"><bean:message key="button.ACHStatus" /></button>
		<button type="button" name="rspRepaymentSchedule" title="Alt+S" accesskey="S" class="topformbutton4" onclick="viewRspRepaymentSchedule();"> <bean:message key="button.rspRepaymentSchedule" /></button>
	<!-- shivesh -->
	<button type="button" class="topformbutton3" id="statement_of_ac"  onclick="closedRepayment();" title="Alt+S" accesskey="S"><bean:message key="button.repayschedule2" /></button>
	</td>
	</logic:present>
	<logic:notPresent name="checked">
	<td>
		<button type="button" class="topformbutton2"  id="deal_viewer" disabled="disabled" title="Alt+D" accesskey="D" onclick="return dealViewer();"><bean:message key="button.dealViewer" /></button>
		<button type="button" class="topformbutton2" id="loan_viewer" disabled="disabled" onclick="return loanViewerPresentation();" title="Alt+L" accesskey="L"><bean:message key="button.loanViewer" /></button>
		<button type="button" class="topformbutton2" id="pdc_status"  onclick="return pdcStatusForLoanViewer();" disabled="disabled" title="Alt+P" accesskey="P"><bean:message key="button.pdcStatus" /></button>
		<button type="button" class="topformbutton3" id="cheque_status"  onclick="chequeStatusforloanViewer();"    disabled="disabled" title="Alt+C" accesskey="C"><bean:message key="button.chequestatus" /></button>
		<button type="button" class="topformbutton3" id="statement_of_ac" disabled="disabled" onclick="statementOfAccount();" title="Alt+S" accesskey="S"><bean:message key="button.statement" /></button>
		<button type="button" class="topformbutton4" id="old_repayment_schedule" disabled="disabled" onclick="return viewOldRepaymentSchedule();" title="Alt+R" accesskey="R"><bean:message key="button.oldrepay" /></button>
		<button type="button" class="topformbutton4" value="Repayment Schedule" id="repayment_schedule" disabled="disabled" onclick="return viewRepaymentSchedule();" ><bean:message key="button.repayschedule1" /></button>
		<button type="button" class="topformbutton4" id="early_closer_detail"  disabled="disabled" onclick="return earlyClosureDetails();" title="Alt+E" accesskey="E"><bean:message key="button.earlyCSE" /></button>
		<button type="button" class="topformbutton3" id="allocation_detail"  disabled="disabled" onclick="allocationDetails(id);" title="Alt+A" accesskey="A"><bean:message key="button.allocDts" /></button>
		<button type="button" id="viewPay" class="topformbutton3" onclick="return viewPayableLoanViewer('<bean:message key="msg.LoanAccountNo" />');" title="Alt+P" accesskey="P"><bean:message key="button.viewPayable" /></button>
		<button type="button" class="topformbutton3" accesskey="R" title="Alt+R" onclick="return viewReceivableLoanViewer('<bean:message key="msg.LoanAccountNo" />');" ><bean:message key="button.viewRecievable"/></button>
		<button type="button"  class="topformbutton4" id="cancel" onclick="return assetInsuranceViewer();" >Asset Insurance Viewer</button>
		<button type="button"  class="topformbutton4" id="loanSummary" onclick="return loanSummaryViewer();" ><bean:message key="button.loanSummaryViewer"/></button>
		<button type="button" name="customerExposure" id="customerExposure" title="Alt+E" accesskey="E" class="topformbutton4" onclick="openCustomerExposureAtCMCustomerService('LIM');"><bean:message key="button.customerExposure"/></button>
		<button type="button" name="camReport" id="camReport" title="Alt+R" accesskey="R" class="topformbutton2" onclick="openCamReports();" ><bean:message key="button.camReport"/></button>
		<button type="button" name=" mybutton" title="Alt+V" accesskey="V" class="topformbutton4" onclick="disbursementReport();"> <bean:message key="button.disbursementVoucher" /></button>
		<button type="button" class="topformbutton2" id="ach_status"  onclick="return achStatusForLoanViewer();"  disabled="disabled"  title="Alt+H" accesskey="H"><bean:message key="button.ACHStatus" /></button>
		<button type="button" name="rspRepaymentSchedule" title="Alt+S" accesskey="S" class="topformbutton4" onclick="viewRspRepaymentSchedule();"> <bean:message key="button.rspRepaymentSchedule" /></button>
	<!-- shivesh -->
	<button type="button" class="topformbutton3" id="statement_of_ac" disabled="disabled" onclick="closedRepayment();" title="Alt+S" accesskey="S"><bean:message key="button.repayschedule2" /></button>
	</td>
	</logic:notPresent>
</tr>
</table>
</logic:present>
 

  </html:form>

   
</div>



</div>

</body>
</html>