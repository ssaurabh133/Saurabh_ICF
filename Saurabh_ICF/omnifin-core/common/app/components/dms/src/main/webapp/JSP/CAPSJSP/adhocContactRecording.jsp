<!--Author Name : Nazia Parvez -->
<!--Date of Creation :29 june 2013 -->
<!--Purpose  : Adhoc Contact Recording Page -->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<html:html>
<head>

<title><bean:message key="a3s.noida" /></title>

		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/queueCodeScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/adhocContactRecordingScript.js"></script>
	
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

<body>
<div id="centercolumn">

<div id="revisedcontainer">
 	
 	
<html:form styleId="contectRecordingForm"  method="post"  action="/AdhocContactRecordingDispatchAction" >
<html:hidden property="loanId" styleId="loanId" value="${customerInfoList[0].loanId}"/>
<html:hidden property="loanno" styleId="loanno" value="${customerInfoList[0].loanNo}"/>
<input type="hidden" name="exclationFlag" id="exclationFlag" value="${requestScope.exclationFlag}"/>
<input type="hidden" name="defaultFormate" id="defaultFormate" value="${defaultFormate}"/>

<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<logic:notPresent name="caseHistory">
<fieldset>
<legend>

<logic:notPresent name="superReview">
<bean:message key="lbl.adhocContactRecording" />
</logic:notPresent>
<logic:present name="superReview">
<bean:message key="lbl.superReview" />
 </logic:present>
</legend>
<fieldset>
<legend><bean:message key="lbl.custInfo" /></legend>
<table cellpadding="0"  cellspacing="0" width="100%" >
<tr><td class="gridtd">

  <table cellpadding="1"  cellspacing="1" width="100%" >
  <tr class="white1">
  <td width="14%"><b><bean:message key="lbl.loanNumber" /></b></td>
  <td width="18%">${customerInfoList[0].loanNo}</td>
  <td width="14%"><b><bean:message key="lbl.product" /></b> </td>
  <td width="18%">${customerInfoList[0].product}</td>
  <td width="14%"><b><bean:message key="lbl.scheme" /></b></td>
  <td width="18%">${customerInfoList[0].scheme}</td>
  </tr>
  <tr class="white1">
  <td width="14%"><b><bean:message key="lbl.customerId" /></b></td>
  <td width="18%">${customerInfoList[0].customerId}</td>
  <td width="14%"><b><bean:message key="lbl.customerName" /></b></td>
  <td width="18%">${customerInfoList[0].customerName}</td>
  <td width="14%"><b><bean:message key="lbl.dob" /></b></td>
  <td width="18%">${customerInfoList[0].customerDOB}</td>
  </tr>
  <tr class="white1">
  <td width="14%"><b><bean:message key="fatherHusband" /></b></td>
  <td width="18%">${customerInfoList[0].fatherHusbandName}</td>
  <td width="14%"><b><bean:message key="lbl.panNum" /></b></td>
  <td width="18%">${customerInfoList[0].panNO}</td>
  <td width="14%"><b><bean:message key="lbl.sTaxNo" /></b></td>
  <td width="18%">${customerInfoList[0].sTaxNo}</td>
  </tr>
   <tr class="white1">
   <td width="14%"><b><bean:message key="lbl.branch" /></b></td>
  <td width="18%" colspan="5">${customerInfoList[0].branch}</td>
   </tr>
  </table>
  </td></tr></table>
</fieldset>
<fieldset>
<legend><bean:message key="lbl.contactInfo" /></legend>
<table cellpadding="0"  cellspacing="0" width="100%" border="0">
<tr><td class="gridtd">
  <table cellpadding="2"  cellspacing="1" width="100%" border="0">
  <tr class="white1">
  <td width="14%"><b><bean:message key="lbl.address" /></b></td>
  <td width="18%">${contactInfoList[0].addressDtl} </td>
  <td width="14%"><b><bean:message key="lbl.city" /></b> </td>
  <td width="18%">${contactInfoList[0].city}</td>
  <td width="14%"><b><bean:message key="lbl.stateOrProvidence" /></b></td>
  <td width="18%">${contactInfoList[0].state}</td>
  </tr >
  <tr class="white1">
  <td width="14%"><b><bean:message key="lbl.email" /></b></td>
  <td width="18%">${contactInfoList[0].email}</td>
  <td width="14%"><b><bean:message key="lbl.mobile" /></b></td>
  <td width="18%">${contactInfoList[0].primaryPhone}</td>
  <td width="14%"><b><bean:message key="lbl.phone2" /></b></td>
  <td width="18%">${contactInfoList[0].secondryPhone}</td>
  </tr>
  <tr class="white1">
  
  <td colspan="6" align="left">

   <button type="button" id="Foreclosure"  title="Alt+A" accesskey="A" class="topformbutton2"  onclick="displayAddress();" ><bean:message key="button.contactinfo" /></button>
 <logic:notPresent name="0">
  <button type="button" title="Alt+D" accesskey="D" id="AddContact" class="topformbutton2"  onclick="openaddressJsp();" ><bean:message key="button.addcontact" /></button>
 </logic:notPresent>
   <button type="button" title="Alt+K" accesskey="K"  id="BankInfo" class="topformbutton2"  onclick="displayBankInfo();" ><bean:message key="button.bankinfo" /></button>
    <button type="button" title="Alt+G" accesskey="G"   id="Gaurantor" class="topformbutton3"  onclick="displayGaurantorInfo();" ><bean:message key="button.guarantorinfo" /></button>
    <!--  <button type="button" title="Alt+I" accesskey="I"  id="supplier" class="topformbutton2"  onclick="displaySupplierInfo();"  ><bean:message key="button.supplierinfo" /></button>-->
   <button type="button" title="Alt+R" accesskey="R"  id="reference" class="topformbutton2"  onclick="displayRefrenceInfo();"  ><bean:message key="button.references" /></button>
  <button type="button" title="Alt+L" accesskey="L"  id="coapplicant" class="topformbutton3"  onclick="displayCoApplicantInfo();"  ><bean:message key="button.coapplicant" /></button>
<!--  start by sachin-->
   <button type="button" title="Alt+O" accesskey="O"  id="document" class="topformbutton2"  onclick="displayDocument();"  ><bean:message key="button.document" /></button>
   <button type="button" title="Alt+B" accesskey="B"  id="cibil" class="topformbutton2"  onclick="displayCibilInfo();"  ><bean:message key="button.cibil" /></button>
<!--end by sachin-->
  </td>
  
  </tr>
  </table>
    </td></tr></table>
</fieldset>

<fieldset>

<legend><bean:message key="lbl.loanDtl" /></legend>
<table cellpadding="0"  cellspacing="0" width="100%" border="0">
<tr><td class="gridtd">
  <table cellpadding="2" border="0"  cellspacing="1" width="100%">
    <tr  class="white1">

  <td><b><bean:message key="lbl.loanAmount" /></b></td>
  <td>${loanInfoList[0].loanAmount}</td>
     <td><b><bean:message key="lbl.appDate" /></b> </td>
  	<td>${loanInfoList[0].appDate}</td>
  	<td><b><bean:message key="lbl.secDate" /></b></td>
 	<td >${loanInfoList[0].secDate}</td>
  <td><b><bean:message key="lbl.disbursalDate" /></b></td>
  <td>${loanInfoList[0].disbersalDate}</td>
   </tr>
   <tr class="white1">
  <td><b><bean:message key="lbl.emiAmt" /></b> </td>
  <td>${loanInfoList[0].emiAmount}</td>
  <td><b><bean:message key="lbl.totalNoOfEmi" /></b></td>
  <td>${loanInfoList[0].totalNoOfEMI}</td>
 <td><b><bean:message key="lbl.noOfEmiDue" /></b></td>
  <td>${loanInfoList[0].noOfEMIDue}</td>
  <td><b><bean:message key="lbl.noOfEmiPaid" /></b></td>
  <td>${loanInfoList[0].noOfEmiPaid}</td>
  </tr>
   <tr class="white1">
 <td><b><bean:message key="lbl.assetCost" /></b> </td>
  <td>${loanInfoList[0].assetCost}</td>
    <td><b><bean:message key="lbl.principleOutStanding" /></b></td>
  <td>${loanInfoList[0].principalOut}</td>
  <td><b><bean:message key="lbl.interestOverDue" /></b></td>
  <td>${loanInfoList[0].interestOverDue}</td>
  <td><b><bean:message key="lbl.principleOverDue" /></b> </td>
  <td>${loanInfoList[0].principalOverDue}</td>
  </tr>
   <tr class="white1">
  <td><b><bean:message key="lbl.otherCharges" /></b></td>
  <td>${loanInfoList[0].otherCharge}</td>
  <td><b><bean:message key="lbl.bccCharges" /></b></td>
  <td>${loanInfoList[0].cbcChages}</td>
  <td><b><bean:message key="lbl.lppCharges" /></b></td>
  <td>${loanInfoList[0].lppCharges}</td>
  <td><b><bean:message key="lbl.totalAmtDue" /> </b></td>
  <td>${loanInfoList[0].totalAmountDue}</td>
 
 
  </tr>
   <tr class="white1">
     

   <td><b><bean:message key="lbl.intRate" /></b></td>
  <td>${loanInfoList[0].interestRate}</td>
   <td><b><bean:message key="lbl.sdInterestType" /></b></td>
  <td>${loanInfoList[0].interestType}</td>
   <td><b><bean:message key="lbl.emiStartDate" /> </b></td>
  <td>${loanInfoList[0].emiStartDate}</td>
  <td><b><bean:message key="lbl.emiEndDate" /></b></td>
  <td>${loanInfoList[0].emiEndDate}</td>
  
  

  </tr>
     <tr class="white1">   
      <td><b><bean:message key="lbl.cycle" /></b></td>
  <td>${loanInfoList[0].billingCycle}</td>
      <td><b><bean:message key="lbl.tenure" /></b></td>
  	<td>${loanInfoList[0].tenure}</td>
      <td><b><bean:message key="lbl.frequency" /></b></td>
  <td>${loanInfoList[0].frequency}</td>
 
 <td><b><bean:message key="lbl.repaymentMode" /></b></td>
 	<td >${loanInfoList[0].repaymentMode}</td>
   
  </tr>
 
  <tr class="white1">
   <td><b><bean:message key="lbl.DPD" /> </b></td>
  <td>${loanInfoList[0].dpd}</td>
  <td><b><bean:message key="lbl.dpdString" /></b></td>
  <td>${loanInfoList[0].dpdstring}</td>
   <td><b><bean:message key="lbl.accStatus" /></b></td>
  <td>${loanInfoList[0].npaStatus}</td>
<td><b><bean:message key="lbl.Rm" /></b></td>
 	<td >${loanInfoList[0].rm}</td>
 	</tr>
 	 <tr class="white1">
 	 <td><b><bean:message key="lbl.Rmsuper" /> </b></td>
      <td colspan="7">${loanInfoList[0].rmSup}</td>
 	  
 	 </tr>
   
 	
  
  </table>
 </td></tr></table>

</fieldset>
<fieldset>
<legend><bean:message key="lbl.assetInfoListGrid" /></legend>


 <table width="100%" cellspacing="0" cellpadding="0" border="0">
 <tr>
 <td class="gridtd">
 <table width="100%" cellspacing="1" cellpadding="1" border="0">
<tr align="center" class="white2">
   <td><b><bean:message key="lbl.assetType" /></b></td>
    <td><b><bean:message key="lbl.assetDesc" /></b></td>
   <td><b> <bean:message key="lbl.assetClass" /></b> </td>
  <td><b><bean:message key="lbl.assetValue" /></b></td>
   </tr>
  
 <logic:present name="assetInfoList">
 <logic:notEmpty  name="assetInfoList">
 <logic:iterate id="assetInfoListObj" name="assetInfoList">
  <tr class="white1">
  <td><bean:write name="assetInfoListObj" property="assetType"/></td>
  <td><bean:write name="assetInfoListObj" property="assetDesc"/>	</td>
  <td><bean:write name="assetInfoListObj" property="assetClass"/>	</td>
  <td><bean:write name="assetInfoListObj" property="assetValue"/>	</td>
    </tr>
    </logic:iterate>
    </logic:notEmpty>
  </logic:present>
  
  </table>
  </td>
  </tr>
  </table>


</fieldset>
<logic:present name="vehicleInfoList">
 <logic:notEmpty  name="vehicleInfoList">
<fieldset>
<legend><bean:message key="lbl.vehicleInfoListGrid" /></legend>
<table width="100%" cellspacing="0" cellpadding="0" border="0">
 <tr>
 <td class="gridtd">
 <table width="100%" cellspacing="1" cellpadding="1" border="0">
<tr align="center" class="white2">

    <td><b><bean:message key="lbl.assetDesc" /></b></td>
    
     <td><b><bean:message key="lbl.vehicleNo" /></b></td>
      <td><b><bean:message key="lbl.engno" /></b></td>
    <td><b><bean:message key="lbl.chasisno" /></b></td>
     <td><b><bean:message key="lbl.vehicleMake" /></b></td>
    <td><b><bean:message key="lbl.vehicleModel" /></b></td>
<td><b><bean:message key="lbl.registrationDate" /></b></td>  
   </tr>
  
 
 <logic:iterate id="vehicleInfoListObj" name="vehicleInfoList">
 
  <tr class="white1">
<td><bean:write name="vehicleInfoListObj" property="assetDesc"/>	</td>
  <td><bean:write name="vehicleInfoListObj" property="vehicleRegisNo"/>	</td>
  <td><bean:write name="vehicleInfoListObj" property="vehicleEngineNo"/>	</td>
    <td><bean:write name="vehicleInfoListObj" property="vehicleChasisNo"/>	</td>
  <td><bean:write name="vehicleInfoListObj" property="vehicleMake"/>	</td>
 <td><bean:write name="vehicleInfoListObj" property="vehicleModel"/>	</td>
  <td><bean:write name="vehicleInfoListObj" property="vehicleRegistrationDate"/>	</td> 
    </tr>

    </logic:iterate>
  
  
  </table>
  </td>
  </tr>
  </table>
</fieldset>
  </logic:notEmpty>
  </logic:present>
<fieldset>
<legend><bean:message key="lbl.followUpTrailGrid" /></legend>


 <table width="100%" cellspacing="0" cellpadding="0" border="0">
 <tr>
 <td class="gridtd">
 <table width="100%" cellspacing="1" cellpadding="1" border="0">
<tr align="center" class="white2">
   <td><b><bean:message key="lbl.actionDate" /></b></td>
    <td><b><bean:message key="lbl.actionCode" /></b></td>
   <td><b> <bean:message key="lbl.contactMode" /></b> </td>
  <td><b><bean:message key="lbl.placeContacted" /></b></td>
  <td><b><bean:message key="lbl.actionAmount" /></b></td>
  <td><b><bean:message key="lbl.nxtActionDate" /></b></td>
  <td><b><bean:message key="lbl.FollowUpRemark" /></b></td>
   </tr>
  
 <logic:present name="followUpGridList">
 <logic:notEmpty name="followUpGridList">
 <logic:iterate id="followUpGridListObj" name="followUpGridList">
  <tr class="white1">
  <td>	<bean:write name="followUpGridListObj" property="actionDate"/></td>
  <td><bean:write name="followUpGridListObj" property="actionCode"/>	</td>
   <td><bean:write name="followUpGridListObj" property="contactMode"/>	</td>
  <td><bean:write name="followUpGridListObj" property="placeContacted"/>	</td>
  <td><bean:write name="followUpGridListObj" property="actionAmount"/>	</td>
  <td><bean:write name="followUpGridListObj" property="nxtActionDate"/>	</td>
  <td><bean:write name="followUpGridListObj" property="remarks" />	</td>
    </tr>
    </logic:iterate>
  </logic:notEmpty>
  </logic:present>
  
  </table>
  </td>
  </tr>
  </table>


</fieldset>

<fieldset>
<legend><bean:message key="lbl.allocationDtl" /></legend>
<table cellpadding="0"  cellspacing="0" width="100%" border="0">
<tr><td class="gridtd">
  <table cellpadding="2" border="0" cellspacing="1" width="100%">
  <tr class="white1">
  <td width="14%"><b><bean:message key="lbl.queue" /></b></td>
  <td width="34%"> ${customerInfoList[0].queue} </td>
  <td width="18%"><b> <bean:message key="lbl.queuedate" /></b> </td>
  <td  >${customerInfoList[0].queueDate}</td>
 
  </tr>
  <tr class="white1">
   <td ><b><bean:message key="lbl.unit" /></b></td>
  <td >${customerInfoList[0].userId}</td>
  <td ><b><bean:message key="lbl.allocationDate" /></b></td>
  <td >${customerInfoList[0].allocationDate}</td>
    </tr>
  </table>
</td></tr></table>

</fieldset>
  <table cellpadding="0" border="0" cellspacing="1" width="100%">
  <tr>
  <td>
  <logic:notPresent name="superReview">
  <button type="button" id="FollowRec" class="topformbutton4" title="Alt+F" accesskey="F" onclick="openPopUpFollowUp();" ><bean:message key="button.followuprecording" /></button>
  </logic:notPresent>
  <logic:present name="superReview">
  <button type="button" id="FollowRec" class="topformbutton4" title="Alt+F" accesskey="F" onclick="openPopUpFollowUpReview();" ><bean:message key="button.followuprecording" /></button>
  </logic:present>
	<button type="button"   id="Follow" class="topformbutton3" title="Alt+T" accesskey="T" onclick="openFollowUpDtlPopUp();" ><bean:message key="button.followuptrail" /></button>
  <button type="button"  id="Payment" class="topformbutton2" title="Alt+P" accesskey="P" onclick="paymentInfo();" ><bean:message key="button.payment" /></button>
  <button type="button" id="Bounce" class="topformbutton2" title="Alt+B" accesskey="B" onclick="bounceInfo();" ><bean:message key="button.bounce" /></button>
    <button type="button"  id="viewPayableRecievable"  class="topformbutton5" title="Alt+V" accesskey="V" onclick="viewpayablerecievable();" ><bean:message key="button.viewPR" /></button>
  <button type="button" class="topformbutton3" id="statement_of_ac" title="Alt+S" accesskey="S" onclick="statementOfAccount()"><bean:message key="button.statement" /></button>
  <button type="button" id="Foreclosure" class="topformbutton2" title="Alt+C" accesskey="C" onclick="openForeClosurePopUp();" ><bean:message key="button.foreclosure" /></button>
  <button type="button" class="topformbutton4" value="Repayment Schedule" id="repayment_schedule" onclick="return viewRepaymentSchedule();"><bean:message key="button.repayschedule1" /></button>
  </td>
  
  </tr>
  </table>

  </fieldset>
 </logic:notPresent>    
 <logic:present name="caseHistory">
 <logic:notEmpty  name="caseHistory">
       <fieldset>
   <legend>
<bean:message key="lbl.caseHistory" />

</legend>
<fieldset>
<legend><bean:message key="lbl.custInfo" /></legend>
<table cellpadding="0"  cellspacing="0" width="100%" >
<tr><td class="gridtd">

  <table cellpadding="1"  cellspacing="1" width="100%" >
  <tr class="white1">
  <td width="14%"><b><bean:message key="lbl.loanNumber" /></b></td>
  <td width="18%">${customerInfoList[0].loanNo}</td>
  <td width="14%"><b><bean:message key="lbl.product" /></b> </td>
  <td width="18%">${customerInfoList[0].product}</td>
  <td width="14%"><b><bean:message key="lbl.scheme" /></b></td>
  <td width="18%">${customerInfoList[0].scheme}</td>
  </tr>
  <tr class="white1">
  <td width="14%"><b><bean:message key="lbl.customerId" /></b></td>
  <td width="18%">${customerInfoList[0].customerId}</td>
  <td width="14%"><b><bean:message key="lbl.customerName" /></b></td>
  <td width="18%">${customerInfoList[0].customerName}</td>
  <td width="14%"><b><bean:message key="lbl.dob" /></b></td>
  <td width="18%">${customerInfoList[0].customerDOB}</td>
  </tr>
  <tr class="white1">
  <td width="14%"><b><bean:message key="fatherHusband" /></b></td>
  <td width="18%">${customerInfoList[0].fatherHusbandName}</td>
  <td width="14%"><b><bean:message key="lbl.panNum" /></b></td>
  <td width="18%">${customerInfoList[0].panNO}</td>
  <td width="14%"><b><bean:message key="lbl.sTaxNo" /></b></td>
  <td width="18%">${customerInfoList[0].sTaxNo}</td>
  </tr>
   <tr class="white1">
   <td width="14%"><b><bean:message key="lbl.branch" /></b></td>
  <td width="18%" colspan="5">${customerInfoList[0].branch}</td>
   </tr>
  </table>
  </td></tr></table>
</fieldset>
<fieldset>
<legend><bean:message key="lbl.contactInfo" /></legend>
<table cellpadding="0"  cellspacing="0" width="100%" border="0">
<tr><td class="gridtd">
  <table cellpadding="2"  cellspacing="1" width="100%" border="0">
  <tr class="white1">
  <td width="14%"><b><bean:message key="lbl.address" /></b></td>
  <td width="18%">${contactInfoList[0].addressDtl} </td>
  <td width="14%"><b><bean:message key="lbl.city" /></b> </td>
  <td width="18%">${contactInfoList[0].city}</td>
  <td width="14%"><b><bean:message key="lbl.stateOrProvidence" /></b></td>
  <td width="18%">${contactInfoList[0].state}</td>
  </tr >
  <tr class="white1">
  <td width="14%"><b><bean:message key="lbl.email" /></b></td>
  <td width="18%">${contactInfoList[0].email}</td>
  <td width="14%"><b><bean:message key="lbl.mobile" /></b></td>
  <td width="18%">${contactInfoList[0].primaryPhone}</td>
  <td width="14%"><b><bean:message key="lbl.phone2" /></b></td>
  <td width="18%">${contactInfoList[0].secondryPhone}</td>
  </tr>
  <tr class="white1">
  
  <td colspan="6" align="left">

   <button type="button" id="Foreclosure"  title="Alt+A" accesskey="A" class="topformbutton2"  onclick="displayAddress();" ><bean:message key="button.contactinfo" /></button>
   <button type="button" title="Alt+K" accesskey="K"  id="BankInfo" class="topformbutton2"  onclick="displayBankInfo();" ><bean:message key="button.bankinfo" /></button>
    <button type="button" title="Alt+G" accesskey="G"   id="Gaurantor" class="topformbutton3"  onclick="displayGaurantorInfo();" ><bean:message key="button.guarantorinfo" /></button>
     <!--  <button type="button" title="Alt+I" accesskey="I"  id="supplier" class="topformbutton2"  onclick="displaySupplierInfo();"  ><bean:message key="button.supplierinfo" /></button>-->
   <button type="button" title="Alt+R" accesskey="R"  id="reference" class="topformbutton2"  onclick="displayRefrenceInfo();"  ><bean:message key="button.references" /></button>
  <button type="button" title="Alt+L" accesskey="L"  id="coapplicant" class="topformbutton3"  onclick="displayCoApplicantInfo();"  ><bean:message key="button.coapplicant" /></button>
  <!--  start by sachin-->
<!--   <button type="button" title="Alt+O" accesskey="O"  id="document" class="topformbutton2"  onclick="displayDocument();"  ><bean:message key="button.document" /></button>-->
<!--   <button type="button" title="Alt+B" accesskey="B"  id="cibil" class="topformbutton2"  onclick="displayCibilInfo();"  ><bean:message key="button.cibil" /></button>-->
<!--end by sachin-->
  </td>
  
  </tr>
  </table>
    </td></tr></table>
</fieldset>
<fieldset>

<legend><bean:message key="lbl.loanDtl" /></legend>
<table cellpadding="0"  cellspacing="0" width="100%" border="0">
<tr><td class="gridtd">
  <table cellpadding="2" border="0"  cellspacing="1" width="100%">
  <tr  class="white1">

  <td><b><bean:message key="lbl.loanAmount" /></b></td>
  <td>${loanInfoList[0].loanAmount}</td>
     <td><b><bean:message key="lbl.appDate" /></b> </td>
  	<td>${loanInfoList[0].appDate}</td>
  	<td><b><bean:message key="lbl.secDate" /></b></td>
 	<td >${loanInfoList[0].secDate}</td>
  <td><b><bean:message key="lbl.disbursalDate" /></b></td>
  <td>${loanInfoList[0].disbersalDate}</td>
   </tr>
   <tr class="white1">
  <td><b><bean:message key="lbl.emiAmt" /></b> </td>
  <td>${loanInfoList[0].emiAmount}</td>
  <td><b><bean:message key="lbl.totalNoOfEmi" /></b></td>
  <td>${loanInfoList[0].totalNoOfEMI}</td>
 <td><b><bean:message key="lbl.noOfEmiDue" /></b></td>
  <td>${loanInfoList[0].noOfEMIDue}</td>
  <td><b><bean:message key="lbl.noOfEmiPaid" /></b></td>
  <td>${loanInfoList[0].noOfEmiPaid}</td>
  </tr>
   <tr class="white1">
 <td><b><bean:message key="lbl.assetCost" /></b> </td>
  <td>${loanInfoList[0].assetCost}</td>
    <td><b><bean:message key="lbl.principleOutStanding" /></b></td>
  <td>${loanInfoList[0].principalOut}</td>
  <td><b><bean:message key="lbl.interestOverDue" /></b></td>
  <td>${loanInfoList[0].interestOverDue}</td>
  <td><b><bean:message key="lbl.principleOverDue" /></b> </td>
  <td>${loanInfoList[0].principalOverDue}</td>
  </tr>
   <tr class="white1">
  <td><b><bean:message key="lbl.otherCharges" /></b></td>
  <td>${loanInfoList[0].otherCharge}</td>
  <td><b><bean:message key="lbl.bccCharges" /></b></td>
  <td>${loanInfoList[0].cbcChages}</td>
  <td><b><bean:message key="lbl.lppCharges" /></b></td>
  <td>${loanInfoList[0].lppCharges}</td>
  <td><b><bean:message key="lbl.totalAmtDue" /> </b></td>
  <td>${loanInfoList[0].totalAmountDue}</td>
 
 
  </tr>
   <tr class="white1">
     

   <td><b><bean:message key="lbl.intRate" /></b></td>
  <td>${loanInfoList[0].interestRate}</td>
   <td><b><bean:message key="lbl.sdInterestType" /></b></td>
  <td>${loanInfoList[0].interestType}</td>
   <td><b><bean:message key="lbl.emiStartDate" /> </b></td>
  <td>${loanInfoList[0].emiStartDate}</td>
  <td><b><bean:message key="lbl.emiEndDate" /></b></td>
  <td>${loanInfoList[0].emiEndDate}</td>
  
  

  </tr>
     <tr class="white1">   
      <td><b><bean:message key="lbl.cycle" /></b></td>
  <td>${loanInfoList[0].billingCycle}</td>
      <td><b><bean:message key="lbl.tenure" /></b></td>
  	<td>${loanInfoList[0].tenure}</td>
      <td><b><bean:message key="lbl.frequency" /></b></td>
  <td>${loanInfoList[0].frequency}</td>
 
 <td><b><bean:message key="lbl.repaymentMode" /></b></td>
 	<td >${loanInfoList[0].repaymentMode}</td>
   
  </tr>
 
  <tr class="white1">
   <td><b><bean:message key="lbl.DPD" /> </b></td>
  <td>${loanInfoList[0].dpd}</td>
  <td><b><bean:message key="lbl.dpdString" /></b></td>
  <td>${loanInfoList[0].dpdstring}</td>
   <td><b><bean:message key="lbl.accStatus" /></b></td>
  <td>${loanInfoList[0].npaStatus}</td>
  
  <td><b><bean:message key="lbl.Rm" /></b></td>
 	<td >${loanInfoList[0].rm}</td>
 	</tr>
 	 <tr class="white1">
 	 <td><b><bean:message key="lbl.Rmsuper" /> </b></td>
     <td colspan="7">${loanInfoList[0].rmSup}</td>
      
 	 </tr>

  </table>
 </td></tr></table>

</fieldset>

<fieldset>
<legend><bean:message key="lbl.assetInfoListGrid" /></legend>


 <table width="100%" cellspacing="0" cellpadding="0" border="0">
 <tr>
 <td class="gridtd">
 <table width="100%" cellspacing="1" cellpadding="1" border="0">
<tr align="center" class="white2">
   <td><b><bean:message key="lbl.assetType" /></b></td>
    <td><b><bean:message key="lbl.assetDesc" /></b></td>
   <td><b> <bean:message key="lbl.assetClass" /></b> </td>
  <td><b><bean:message key="lbl.assetValue" /></b></td>
   </tr>
  
 <logic:present name="assetInfoList">
 <logic:notEmpty  name="assetInfoList">
 <logic:iterate id="assetInfoListObj" name="assetInfoList">
  <tr class="white1">
  <td><bean:write name="assetInfoListObj" property="assetType"/></td>
  <td><bean:write name="assetInfoListObj" property="assetDesc"/>	</td>
  <td><bean:write name="assetInfoListObj" property="assetClass"/>	</td>
  <td><bean:write name="assetInfoListObj" property="assetValue"/>	</td>
    </tr>
    </logic:iterate>
    </logic:notEmpty>
  </logic:present>
  
  </table>
  </td>
  </tr>
  </table>
</fieldset>
<logic:present name="vehicleInfoList">
 <logic:notEmpty  name="vehicleInfoList">
<fieldset>
<legend><bean:message key="lbl.vehicleInfoListGrid" /></legend>
<table width="100%" cellspacing="0" cellpadding="0" border="0">
 <tr>
 <td class="gridtd">
 <table width="100%" cellspacing="1" cellpadding="1" border="0">
<tr align="center" class="white2">

    <td><b><bean:message key="lbl.assetDesc" /></b></td>
    
     <td><b><bean:message key="lbl.vehicleNo" /></b></td>
      <td><b><bean:message key="lbl.engno" /></b></td>
    <td><b><bean:message key="lbl.chasisno" /></b></td>
     <td><b><bean:message key="lbl.vehicleMake" /></b></td>
    <td><b><bean:message key="lbl.vehicleModel" /></b></td>
<td><b><bean:message key="lbl.registrationDate" /></b></td>  
   </tr>
  
 
 <logic:iterate id="vehicleInfoListObj" name="vehicleInfoList">
 
  <tr class="white1">
<td><bean:write name="vehicleInfoListObj" property="assetDesc"/>	</td>
  <td><bean:write name="vehicleInfoListObj" property="vehicleRegisNo"/>	</td>
  <td><bean:write name="vehicleInfoListObj" property="vehicleEngineNo"/>	</td>
    <td><bean:write name="vehicleInfoListObj" property="vehicleChasisNo"/>	</td>
  <td><bean:write name="vehicleInfoListObj" property="vehicleMake"/>	</td>
 <td><bean:write name="vehicleInfoListObj" property="vehicleModel"/>	</td>
  <td><bean:write name="vehicleInfoListObj" property="vehicleRegistrationDate"/>	</td> 
    </tr>

    </logic:iterate>
  
  
  </table>
  </td>
  </tr>
  </table>
</fieldset>
  </logic:notEmpty>
  </logic:present>
<fieldset>
<legend><bean:message key="lbl.followUpTrailGrid" /></legend>


 <table width="100%" cellspacing="0" cellpadding="0" border="0">
 <tr>
 <td class="gridtd">
 <table width="100%" cellspacing="1" cellpadding="1" border="0">
<tr align="center" class="white2">
   <td><b><bean:message key="lbl.actionDate" /></b></td>
    <td><b><bean:message key="lbl.actionCode" /></b></td>
   <td><b> <bean:message key="lbl.contactMode" /></b> </td>
  <td><b><bean:message key="lbl.placeContacted" /></b></td>
  <td><b><bean:message key="lbl.actionAmount" /></b></td>
  <td><b><bean:message key="lbl.nxtActionDate" /></b></td>
  <td><b><bean:message key="lbl.FollowUpRemark" /></b></td>
   </tr>
  
 <logic:present name="followUpGridList">
 <logic:notEmpty  name="followUpGridList">
 <logic:iterate id="followUpGridListObj" name="followUpGridList">
  <tr class="white1">
  <td>	<bean:write name="followUpGridListObj" property="actionDate"/></td>
  <td><bean:write name="followUpGridListObj" property="actionCode"/>	</td>
   <td><bean:write name="followUpGridListObj" property="contactMode"/>	</td>
  <td><bean:write name="followUpGridListObj" property="placeContacted"/>	</td>
  <td><bean:write name="followUpGridListObj" property="actionAmount"/>	</td>
  <td><bean:write name="followUpGridListObj" property="nxtActionDate"/>	</td>
  <td><bean:write name="followUpGridListObj" property="remarks" />	</td>
    </tr>
    </logic:iterate>
    </logic:notEmpty>
  </logic:present>
  
  </table>
  </td>
  </tr>
  </table>


</fieldset>
<fieldset>
<legend><bean:message key="lbl.allocationDtl" /></legend>
<table cellpadding="0"  cellspacing="0" width="100%" border="0">
<tr><td class="gridtd">
  <table cellpadding="2" border="0" cellspacing="1" width="100%">
  <tr class="white1">
  <td width="14%"><b><bean:message key="lbl.queue" /></b></td>
  <td width="34%"> ${customerInfoList[0].queue} </td>
  <td width="18%"><b> <bean:message key="lbl.queuedate" /></b> </td>
  <td  >${customerInfoList[0].queueDate}</td>
 
  </tr>
  <tr class="white1">
   <td ><b><bean:message key="lbl.unit" /></b></td>
  <td >${customerInfoList[0].userId}</td>
  <td ><b><bean:message key="lbl.allocationDate" /></b></td>
  <td >${customerInfoList[0].allocationDate}</td>
    </tr>
  </table>
</td></tr></table>
</fieldset>

	<table cellpadding="0" border="0" cellspacing="1" width="100%">
	<tr>
	<td>
		<button type="button"  id="Follow" class="topformbutton3" title="Alt+T" accesskey="T" onclick="openFollowUpDtlPopUp();" ><bean:message key="button.followuptrail" /></button>
		<button type="button" id="Payment" class="topformbutton2" title="Alt+P" accesskey="P" onclick="paymentInfo();" ><bean:message key="button.payment" /></button>
		<button type="button" id="Bounce" class="topformbutton2" title="Alt+B" accesskey="B" onclick="bounceInfo();" ><bean:message key="button.bounce" /></button>
		<button type="button" id="viewPayableRecievable" class="topformbutton5" title="Alt+V" accesskey="V" onclick="viewpayablerecievable();" ><bean:message key="button.viewPR" /></button>
		<button type="button" class="topformbutton3"  id="statement_of_ac" title="Alt+S" accesskey="S" onclick="statementOfAccount()"><bean:message key="button.statement" /></button>
		<button type="button"  id="Foreclosure" class="topformbutton2" title="Alt+C" accesskey="C" onclick="openForeClosurePopUp();" ><bean:message key="button.foreclosure" />
		</button>
		<button type="button" class="topformbutton4" value="Repayment Schedule" id="repayment_schedule"  onclick="return viewRepaymentSchedule();"><bean:message key="button.repayschedule1" /></button>
	</td>

	</tr>
	</table>

	</fieldset>
	</logic:notEmpty>
	</logic:present>
	</html:form>
	
		<logic:present name="sms">
     <script type="text/javascript">

    
			if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		document.getElementById("queueCodeMasterForm").action="queueCodeMasterBehind.do";
	    document.getElementById("queueCodeMasterForm").submit();
	}
	
	else if('<%=request.getAttribute("sms").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		document.getElementById("queueCodeMasterForm").action="queueCodeMasterBehind.do";
	    document.getElementById("queueCodeMasterForm").submit();
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		
	}
	else
	{
		alert("<bean:message key="lbl.dataExist" />");
		
	}
	

</script>
</logic:present>
<div></div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
  </body>
		</html:html>