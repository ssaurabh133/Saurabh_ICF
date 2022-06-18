<!-- 
Author Name :- Manisha Tomar
Date of Creation :43-04-2011
Purpose :-  screen for the Payment Maker
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserObject userobj=(UserObject)session.getAttribute("userobject");
String initiationDate = userobj.getBusinessdate();
%>

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
		  <!-- css and jquery for tooltip -->
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.simpletip-1.3.1.js"></script>
			<link type="text/css" href="<%=request.getContextPath() %>/css/toolTip.css" rel="stylesheet" />
	     <!-- css and jquery for tooltip -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/paymentMaker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	
<script type="text/javascript">	
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('paymentMakerForm').paymentMode.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('paymentMakerForm').loanAccountButton.focus();
     }
     return true;
}
</script>	
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
	<body onload="enableAnchor();enableTaLoanLov();checkChanges('paymentMakerForm');document.getElementById('paymentMakerForm').loanAccountButton.focus();otherMethod();fntab();" onunload="closeAllWindowCallUnloadBody();">
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="frdLoanID" id="frdLoanID" value="${frdLoanID}"/>
	<html:hidden property="allocatePayFlag" styleId="allocatePayFlag" value="${sessionScope.allocatePayFlag}" />	
	<input type="hidden" name="cashPaymentLimit" id="cashPaymentLimit" value="${sessionScope.cashPaymentLimit}"/>
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/paymentCMProcessAction" method="post" styleId="paymentMakerForm">
        <html:errors/>
        
	<input type="hidden" name="canForward" value="${requestScope.canForward}" id="canForward" />
	 <input type="hidden" name="beforeForward" value="${requestScope.beforeForward}" id="beforeForward" />
	 <input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
	 <input type="hidden" name="businessDate" id="businessDate" value="<%=initiationDate %>"/>
	 <input type="hidden" name="loanRecStatus" value="${requestScope.loanRecStatus}" id="loanRecStatus" />
	  <input type="hidden" name="balAmount"   id="balAmount" value="${balAmount}" />

	   <fieldset>	  
	<legend><bean:message key="lbl.paymentMaker"></bean:message></legend>         
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		 <logic:present name="payableList">
		  <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
		     <tr>
			<html:hidden  property="instrumentID" styleId="instrumentID" value="${requestScope.payableList[0].instrumentID}"  />
			<input type="hidden" value="${requestScope.payableList[0].lbxLoanNoHID}" id="loanId" name="loanId"/>	
		   <td><bean:message key="lbl.loanAccount"></bean:message> </td>
		  
		
		   <td>
		 <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="${requestScope.payableList[0].loanAccountNumber}" style="background-color:#F2F2F2" readonly="true"/>
        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${requestScope.payableList[0].lbxLoanNoHID}"  />
      
        
		  </td>
	<td><bean:message key="lbl.customerName"></bean:message></td>
	<td >
<!--	 <a rel="tooltip3" id="tool">-->
		          
<!--		  <html:text property="customerName"  styleClass="text" styleId="customerName" onmouseover="applyToolTip(id);" value="${requestScope.payableList[0].customerName}" maxlength="50" style="background-color:#F2F2F2" readonly="true"></html:text>-->
		  <html:text property="customerName"  styleClass="text" styleId="customerName"  value="${requestScope.payableList[0].customerName}" maxlength="50" style="background-color:#F2F2F2" readonly="true"></html:text>             
						
<!--	</a>-->
	
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	</td>
		</tr>
	  <tr>
		
		   <td>   
	<bean:message key ="lbl.businessPartnerType"></bean:message>	   
	 </td>
	 <td>
			<html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"  value="${requestScope.payableList[0].businessPartnerType}" readonly="true" tabindex="-1"/>
			<html:hidden  property="lbxBPType" styleId="lbxBPType" value="${requestScope.payableList[0].lbxBPType}" />
			<html:button property="loanAccountButton" styleId="loanAccountButton" onclick="openLOVCommon(147,'paymentMakerForm','businessPartnerType','loanAccountNumber','lbxBPType', 'lbxLoanNoHID','Select loanAccountNumber','getPayTo','lbxBPNID','businessPartnerName');closeLovCallonLov('loanAccountNumber');" value=" " styleClass="lovbutton"></html:button>
             	   
		    </td>    
			
	 <!--  <td><html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"  value="${requestScope.payableList[0].businessPartnerType}"  style="background-color:#F2F2F2"  readonly="true"/>	
     <html:hidden  property="lbxBPType" styleId="lbxBPType" value="${requestScope.payableList[0].lbxBPType}"/>
     </td>     -->
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>
		
			<html:text styleClass="text" property="businessPartnerName" styleId="businessPartnerName" maxlength="50"  onmouseover="applyToolTip(id);" value="${requestScope.payableList[0].businessPartnerName}"  style="background-color:#F2F2F2"  readonly="true" tabindex="-1"/>
	
        <html:hidden  property="lbxBPNID" styleId="lbxBPNID" value="${requestScope.payableList[0].lbxBPNID}"/>
     
		    </td>
	 </tr>
	     <tr>
		     <td><bean:message key="lbl.payTo"></bean:message></td>
	        <td>
	        <logic:iterate id="payableListObj" name="payableList">
	        <logic:equal name="payableListObj" property="lbxBPType" value="CS">
	         <html:text property="payTo" styleId="payTo" readonly="true" styleClass="text" value="${requestScope.payableList[0].payTo}"	tabindex="-1"/>
	         <input type="hidden"  name="lbxpayTo" id="lbxpayTo" value="${requestScope.payableList[0].lbxpayTo}" tabindex="-1"/>
             <html:button property="payToButton"  styleId="payToButton" onclick="openLOVCommon(262,'paymentMakerForm','payTo','loanAccountNumber','lbxpayTo', 'lbxLoanNoHID','Select Loan Account Number','payeeNameRefresh','hid');closeLovCallonLov('loanAccountNumber');" value=" " styleClass="lovbutton"></html:button>
	        </logic:equal>
	        </logic:iterate>
	         <logic:iterate id="payableListObj" name="payableList">
	        <logic:notEqual name="payableListObj" property="lbxBPType" value="CS">
	         <html:text property="payTo" styleId="payTo" readonly="true" styleClass="text" value="${requestScope.payableList[0].payTo}"	tabindex="-1"/>
	         <input type="hidden"  name="lbxpayTo" id="lbxpayTo" value="${requestScope.payableList[0].lbxpayTo}"/>
             <html:button property="payToButton"  styleId="payToButton" onclick="openLOVCommon(262,'paymentMakerForm','payTo','loanAccountNumber','lbxpayTo', 'lbxLoanNoHID','Select Loan Account Number','payeeNameRefresh','hid');closeLovCallonLov('loanAccountNumber');" value=" " styleClass="lovbutton"></html:button>
	        </logic:notEqual>
	        </logic:iterate>
	      <!--    <html:text property="payTo" styleId="payTo" styleClass="text" value="${requestScope.payableList[0].payTo}"	readonly="true"/>
	      	  <input type="hidden"  name="lbxpayTo" id="lbxpayTo" value="${requestScope.payableList[0].lbxpayTo}"/> -->
          
	        </td>
	        <td><bean:message key="lbl.payeeName"></bean:message></td>
	        <td>
	        <a rel="tooltip3" id="tool">
	        
	        	<html:text property="payeeName" styleId="payeeName" readonly="true" styleClass="text" value="${requestScope.payableList[0].payeeName}" onmouseover="applyToolTip(id);" onchange="insertValue();" tabindex="-1" maxlength="100"/>
			</a>
	        	<input type="hidden"  name="lbxpayeeName" id="lbxpayeeName" value="${requestScope.payableList[0].lbxpayeeName}"/>
                <html:button property="payeeNameButton" disabled="true" style="display: none" styleId="payeeNameButton" onclick="openLOVCommon(263,'paymentMakerForm','payeeName','payTo|loanAccountNumber','lbxpayeeName', 'lbxpayTo|lbxLoanNoHID','Select Pay To|Select Loan Account Number','','hid');closeLovCallonLov('payTo');" value=" " styleClass="lovbutton"></html:button>	
	           <html:button property="payeeNameButton"  styleId="payeeNameButtonRSP" style="display: none" onclick="openLOVCommon(655,'paymentMakerForm','payeeName','payTo|loanAccountNumber','lbxpayeeName', 'lbxpayTo|lbxLoanNoHID','Select Pay To|Select Loan Account Number','','hid');closeLovCallonLov('payTo');" value=" " styleClass="lovbutton"></html:button>	
	         <!--  	<html:text property="payeeName" styleId="payeeName" styleClass="text" value="${requestScope.payableList[0].payeeName}" readonly="true"/>
	        	<input type="hidden"  name="lbxpayeeName" id="lbxpayeeName" value="${requestScope.payableList[0].lbxpayeeName}"/> -->
              
	        </td>
	        </tr>
	<tr>  
	<td><bean:message key ="lbl.paymentMode"></bean:message><font color="red">*</font> </td>
		 <td><span style="float:left;">
		<html:select property="paymentMode" styleId="paymentMode" styleClass="text" value="${requestScope.payableList[0].paymentMode}" onchange="fnNull();getAccountType();">
		 <html:option value="">--Select--</html:option> 
		  <html:option value="C">Cash</html:option> 
		 <html:option value="Q">Cheque</html:option> 
		 <html:option value="D">DD</html:option> 
		 <html:option value="N">NEFT</html:option> 
		 <html:option value="R">RTGS</html:option>	
		 <html:option value="S">ADJUSTMENT</html:option>	 		               
           </html:select>
           <input type="hidden"  name="lbxpaymentMode" id="lbxpaymentMode" value="${requestScope.payableList[0].lbxpaymentMode}"/> 
		 </span></td>
		
	    <td><bean:message key ="lbl.paymentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
			<td>
			<html:text property="paymentDate" styleClass="text" styleId="paymentDate" value="${requestScope.payableList[0].paymentDate}" maxlength="10" onchange="return checkDate('paymentDate');"></html:text> 
	</td>
		 
			
		</tr>
		<tr>
			  <td><bean:message key="lbl.instrumentNumber"></bean:message><font color="red">*</font> </td>
			 <td><div style="float:left;">
			 <html:text property="instrumentNumber" styleClass="text" styleId="instrumentNumber" value="${requestScope.payableList[0].instrumentNumber}" maxlength="20"></html:text>
			 </div></td>
			 <input type="hidden" name="instrumentID" id="instrumentID" value="${requestScope.payableList[0].instrumentID}" />
			 </div></td>
			 <td><bean:message key="lbl.instrumentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font> </td>
	    <td id='test'><html:text property="instrumentDate" styleClass="text" styleId="instrumentDate" value="${requestScope.payableList[0].instrumentDate}" maxlength="10" onchange="return checkDate('instrumentDate');"></html:text>
	  </td>				
		  <td id='showTest' style="display: none;"><input type="text" class="text" name="instrumentDate1" id="instrumentDate1" value="" disabled="disabled"/></td>
	    
		</tr>
		<tr>
			 
		<td><bean:message key="lbl.bankAccount"></bean:message><font color="red">*</font></td>
			 <td><div style="float:left;"> 
	 <html:text styleClass="text" property="bankAccount" styleId="bankAccount" maxlength="50"  value="${requestScope.payableList[0].bankAccount}" readonly="true" tabindex="-1"/>
    <input type="hidden"  name="lbxbankAccountID" id="lbxbankAccountID"/>
     <html:button property="bankAccountButton" styleId="bankAccountButton" onclick="openLOVCommon(64,'paymentMakerForm','bankAccount','paymentMode','lbxbankAccountID', 'lbxpaymentMode','Select Payment Mode','getChequeDetail','hid','hid1');closeLovCallonLovBAB('paymentMode');" value=" " styleClass="lovbutton"></html:button>
    <!-- <img onclick="openLOVCommon(64,'paymentMakerForm','bankAccount','paymentMode','lbxbankAccountID', 'lbxpaymentMode','Select Payment Mode','getChequeDetail','hid','hid1')" src="<%= request.getContextPath()%>/images/lov.gif"/> -->
       <input type="hidden" name="hid1" id="hid1"/> 
    <input type="hidden" name="hid" id="hid"/> 
 
    </div></td>
		   <td><bean:message key="lbl.bank"></bean:message></td>
			 <td><div style="float:left;"> 
	 <html:text styleClass="text" property="bank" styleId="bank" maxlength="50"  value="${requestScope.payableList[0].bank}" readonly="true" tabindex="-1"/>
    <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${requestScope.payableList[0].lbxBankID}"/> 
			 </div></td>
		</tr> 
	<tr>	
	<td><bean:message key="lbl.branch"></bean:message></td>
		<td>
		
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="50"  value="${requestScope.payableList[0].branch}" readonly="true" tabindex="-1"/>
         <html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${requestScope.payableList[0].lbxBranchID}"/>
   		</td>	 
	<td><bean:message key="lbl.micr"></bean:message></td>
	<td>
	<html:text property="micr" styleClass="text" styleId="micr" value="${requestScope.payableList[0].micr}" maxlength="20" readonly="true" tabindex="-1"></html:text>
	</td>
		   
	         </tr>
	         <tr>
	         <td> <bean:message key="lbl.ifsCode"></bean:message></td>
			 <td><div style="float:left;">
			 <html:text property="ifsCode" styleId="ifsCode" styleClass="text" value="${requestScope.payableList[0].ifsCode}" maxlength="20" readonly="true" tabindex="-1"></html:text>		
	         </div></td>
	         <td><bean:message key="lbl.paymentAmount"></bean:message><font color="red">*</font> </td>
			 <td><div style="float:left;">
			 <html:text property="paymentAmount" styleClass="text" styleId="paymentAmount" value="${requestScope.payableList[0].paymentAmount}" 
			 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" ></html:text>
			 <html:hidden  property="txnAdvicedID" styleId="txnAdvicedID"  value="${requestScope.payableList[0].txnAdvicedID}" />
			 <html:hidden property="hidPmntId" styleId="hidPmntId" value="${requestScope.pmntId}" />
	        </tr>
	        
	       <!-- Virender Code Start -->
			<tr>
				<td>
					<bean:message key="lbl.beneficiary_bank" />
				</td>
				<td>
					<html:text property="beneficiaryBankCode" styleId="beneficiaryBankCode" readonly="true" styleClass="text" value="${requestScope.payableList[0].beneficiaryBankCode}" tabindex="-1" />
					 <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();
					 openLOVCommon('19243','paymentMakerForm','beneficiaryBankCode','','beneficiaryBankBranchName', 'beneficiaryLbxBranchID','','','beneficiaryLbxBankID');" value=" " styleClass="lovbutton"> </html:button>
					<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
					<html:hidden property="beneficiaryLbxBankID" styleId="beneficiaryLbxBankID" value="${requestScope.payableList[0].beneficiaryLbxBankID}"/>
				</td>
		
				<td>
					<bean:message key="lbl.beneficiaryBankBranchName" />									
				</td>
				
				<td>
					<html:text property="beneficiaryBankBranchName" styleId="beneficiaryBankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${requestScope.payableList[0].beneficiaryBankBranchName}" />
                    <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="openLOVCommon(19244,'paymentMakerForm','beneficiaryBankBranchName','beneficiaryBankCode','beneficiaryLbxBranchID', 'beneficiaryLbxBankID','Please Select Bank','','micrCode','beneficiaryIfscCode','beneficiaryLbxBranchID');closeLovCallonLov('bankCode');" value=" " styleClass="lovbutton"> </html:button>
			    	<html:hidden property="beneficiaryLbxBranchID" styleId="beneficiaryLbxBranchID" value="${requestScope.payableList[0].beneficiaryLbxBranchID}"  />
			    	<input type="hidden" name="micrCode" value="" id="micrCode" />
			  </td>
				
			</tr>
			
			<tr>
				<td>
					<bean:message key="lbl.beneficiaryAccountNo"  />								
				</td>
				<td>
					<html:text property="beneficiaryAccountNo" styleId="beneficiaryAccountNo" tabindex="-1" onblur="fnChangeCase('CustBankDetailsForm','accountNo');this.value=removeSpaces1(this.value);" styleClass="text"  value="${requestScope.payableList[0].beneficiaryAccountNo}"  />
				</td>	
		
				<td>
					<bean:message key="lbl.beneficiaryIfscCode" />
				</td>
				<td>
					<html:text property="beneficiaryIfscCode" styleId="beneficiaryIfscCode" styleClass="text" readonly="true" maxlength="20" value="${requestScope.payableList[0].beneficiaryIfscCode}"  />
				</td>	
			</tr>
		    <!-- Virender Code End --> 
		    
		    <tr>
		      <td><bean:message key="lbl.tdsAmount"></bean:message></td>
	        <td> <html:text property="tdsAmount" styleId="tdsAmount" styleClass="text" value="${requestScope.payableList[0].tdsAmount}" 
	         onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" ></html:text>		
	        </td>
	         <td>TA Adjustment</td>
			<td>
				<logic:present name="taStatus">
			
			     <input type="checkbox" id="taStatus"  name="taStatus" checked="checked" onclick="enableTaLoanLov()" />
			
			   </logic:present>
			   <logic:notPresent name="taStatus">
			 
			     <input type="checkbox" id="taStatus"  name="taStatus" onclick="enableTaLoanLov()" />
			   
			   </logic:notPresent>
			</td>
	       </tr>
			<tr>
		
			<td nowrap="nowrap">
			<bean:message key="lbl.taLoanNo" />
				
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="taLoanNo" styleId="taLoanNo" value="${requestScope.payableList[0].taLoanNo}" readonly="true" maxlength="20" />				
				<html:button property="taloanButton" styleId="taloanButton" disabled="true" value=" " onclick="setSuppManufIdInSessForPayment();openLOVCommon(552,'paymentMakerForm','taLoanNo','businessPartnerType','lbxTaLoanNoHID', 'lbxBPType','Select Supplier/Manufacturer First','validateTABalancePayment','taCustomerName','balAmount');closeLovCallonLov('taLoanNo');" styleClass="lovbutton"> </html:button>
				<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
				<html:hidden property="lbxTaLoanNoHID" styleId="lbxTaLoanNoHID" value="${requestScope.payableList[0].lbxTaLoanNoHID}" />
				<html:hidden property="hid" styleId="hid" value="" />
			</td>
			<td nowrap="nowrap">
				<bean:message key="lbl.taCustomerName" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="taCustomerName"
					styleId="taCustomerName" maxlength="50" readonly="true"
					value="${requestScope.payableList[0].taCustomerName}" tabindex="-1" />
			</td>			
		</tr> 
	        
	        <tr>
	         <td><bean:message key="lbl.remark"></bean:message></td>
	         <td>
	         <textarea name="remarks" id="remarks" maxlength="1000" class="text">${requestScope.payableList[0].remarks}</textarea>      
	        </td>
	        
			
			<td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" readonly="true"
			value="${requestScope.payableList[0].authorRemarks}" /></td>
			</tr>
		 </logic:present>
		
		 <logic:present name="datatoapproveList">
		  <input type="hidden" id="modifyRecord" name="modifyRecord" value="V"/>
				<input type="hidden" value="${sessionScope.datatoapproveList[0].lbxLoanNoHID}" id="loanId" name="loanId"/>	
		   <td><bean:message key="lbl.loanAccount"></bean:message></td>
		   <td>
		   
		 <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="${sessionScope.datatoapproveList[0].loanAccountNumber}" readonly="true" disabled="true"/>
        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${sessionScope.datatoapproveList[0].lbxLoanNoHID}"  />
    
		  </td>
	<td><bean:message key="lbl.customerName"></bean:message></td>
	<td >
<!--	<a rel="tooltip3" id="tool">-->
<!--		<html:text property="customerName"  styleClass="text" styleId="customerName" onmouseover="applyToolTip(id);" value="${sessionScope.datatoapproveList[0].customerName}" maxlength="50" disabled="true"></html:text>-->
		<html:text property="customerName"  styleClass="text" styleId="customerName"  value="${sessionScope.datatoapproveList[0].customerName}" maxlength="50" disabled="true"></html:text>
<!--	</a>-->
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	</td>
		</tr>
	  <tr>
		
		   <td>		   
	<bean:message key ="lbl.businessPartnerType"></bean:message>	   
	 </td>
			<td><html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"  value="${sessionScope.datatoapproveList[0].businessPartnerType}" disabled="true"/>
       <html:hidden  property="lbxBPType" styleId="lbxBPType" value="${sessionScope.datatoapproveList[0].lbxBPType}"/></td>     
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>
	
		<html:text styleClass="text" property="businessPartnerName" styleId="businessPartnerName" maxlength="50"  value="${sessionScope.datatoapproveList[0].businessPartnerName}" onmouseover="applyToolTip(id);" disabled="true"/>

     <html:hidden  property="lbxBPNID" styleId="lbxBPNID" value="${sessionScope.datatoapproveList[0].lbxBPNID}"/>
     
		    </td>
	 </tr>
	    <tr>
		     <td><bean:message key="lbl.payTo"></bean:message></td>
	        <td>
	         <html:text property="payTo" styleId="payTo" readonly="true" styleClass="text" value="${sessionScope.datatoapproveList[0].payTo}"	disabled="true"/>
	         <input type="hidden"  name="lbxpayTo" id="lbxpayTo" value="${sessionScope.datatoapproveList[0].lbxpayTo}"/>
          
	        </td>
	        <td><bean:message key="lbl.payeeName"></bean:message></td>
	        <td>
	        <a rel="tooltip3" id="tool">
	        	<html:text property="payeeName" styleId="payeeName" readonly="true" styleClass="text" value="${sessionScope.datatoapproveList[0].payeeName}" onmouseover="applyToolTip(id);" onchange="insertValue();" maxlength="100"/>
	        	
	     </a>
	        	<input type="hidden"  name="lbxpayeeName" id="lbxpayeeName" value="${sessionScope.datatoapproveList[0].lbxpayeeName}"/>
             
	        </td>
	        </tr>
	<tr>  
	<td><bean:message key ="lbl.paymentMode"></bean:message> </td>
		 <td><span style="float:left;">
		 <html:select property="paymentMode" styleId="paymentMode" styleClass="text" value="${sessionScope.datatoapproveList[0].paymentMode}" disabled="true">
		 <html:option value="">--Select--</html:option> 
		  <html:option value="C">Cash</html:option> 
		 <html:option value="Q">Cheque</html:option> 
		 <html:option value="D">DD</html:option> 
		 <html:option value="N">NEFT</html:option> 
		 <html:option value="R">RTGS</html:option>	  
		  <html:option value="S">ADJUSTMENT</html:option>	              
           </html:select>
		 </span></td>
	    <td>
		<bean:message key ="lbl.paymentDate"></bean:message> <bean:message key="lbl.dateFormat(dd-mm-yyyy)"/>  
	 </td>
			<td>
			<html:text property="paymentDate" styleClass="text" styleId="paymentAmount" value="${sessionScope.datatoapproveList[0].paymentDate}" maxlength="100" disabled="true" readonly="true"></html:text> 
	</td>
		 
			
		</tr>
		<tr>
		
			  <td><bean:message key="lbl.instrumentNumber"></bean:message> </td>
			 <td><div style="float:left;">
			 <html:text property="instrumentNumber" styleClass="text" styleId="instrumentNumber" value="${sessionScope.datatoapproveList[0].instrumentNumber}" maxlength="20" disabled="true"></html:text>
			 </div></td>
			 <td><bean:message key="lbl.instrumentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/> </td>
	    <td><html:text property="instrumentDate" styleClass="text" styleId="paymentAmount" value="${sessionScope.datatoapproveList[0].instrumentDate}" maxlength="100" disabled="true" style="text-align: right" ></html:text>
	  </td>				
		  
	    
		</tr>
		<tr>  <td><bean:message key="lbl.bankAccount"></bean:message></td>
			<td>  <html:text styleClass="text" property="bankAccount" styleId="bankAccount" maxlength="50"  value="${sessionScope.datatoapproveList[0].depositAccountNo}" disabled="true"/></td>
		   <td>
		   <bean:message key="lbl.bank"></bean:message>
	</td>
			 <td><div style="float:left;">
			 
			 
	 <html:text styleClass="text" property="bank" styleId="bank" maxlength="50"  value="${sessionScope.datatoapproveList[0].depositBankCode}" disabled="true"/>
    <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${sessionScope.datatoapproveList[0].depositLbxBankID}"/>
    
        	  
			 </div></td>
			 
		
		</tr> 
	<tr>	
	<td><bean:message key="lbl.branch"></bean:message></td>
		<td>
		
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="50"  value="${sessionScope.datatoapproveList[0].depositBankBranchName}" disabled="true"/>
   <html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${sessionScope.datatoapproveList[0].depositLbxBranchID}"/>


		</td>	 
	<td><bean:message key="lbl.micr"></bean:message></td>
	<td>
	<html:text property="micr" styleClass="text" styleId="micr" value="${sessionScope.datatoapproveList[0].depositmicrCode}" maxlength="20"  disabled="true"></html:text>
	</td>
		   
	         </tr>
	         <tr>
	      
	         <td>
		     <bean:message key="lbl.ifsCode"></bean:message>
	         </td>
			 <td><div style="float:left;">
			 <html:text property="ifsCode" styleId="ifsCode" styleClass="text" value="${sessionScope.datatoapproveList[0].depositIfscCode}" maxlength="20" disabled="true"></html:text>		
	         </div></td>
	         <td><bean:message key="lbl.paymentAmount"></bean:message> </td>
			 <td><div style="float:left;">
			 <html:text property="paymentAmount" styleClass="text" styleId="paymentAmount" value="${sessionScope.datatoapproveList[0].paymentAmount}" maxlength="18" disabled="true" style="text-align: right" ></html:text>
			  <html:hidden  property="txnAdvicedID" styleId="txnAdvicedID" value="${sessionScope.datatoapproveList[0].txnAdvicedID}"  />
			 </div></td>
	        </tr>
	        
	        <!-- Virender Code Start -->
			<tr>
				<td>
					<bean:message key="lbl.beneficiary_bank" />
				</td>
				<td>
					<html:text property="beneficiaryBankCode" styleId="beneficiaryBankCode" readonly="true" styleClass="text" value="${sessionScope.datatoapproveList[0].beneficiary_bankCode}" tabindex="-1" />
					 <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();
					 openLOVCommon('19243','paymentMakerForm','beneficiaryBankCode','','beneficiaryBankBranchName', 'beneficiaryLbxBranchID','','','beneficiaryLbxBankID');" value=" " styleClass="lovbutton"> </html:button>
					<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
					<html:hidden property="beneficiaryLbxBankID" styleId="beneficiaryLbxBankID" value="${sessionScope.datatoapproveList[0].beneficiary_lbxBankID}"/>
				</td>
		
				<td>
					<bean:message key="lbl.beneficiaryBankBranchName" />									
				</td>
				
				<td>
					<html:text property="beneficiaryBankBranchName" styleId="beneficiaryBankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${sessionScope.datatoapproveList[0].beneficiary_bankBranchName}" />
                    <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="openLOVCommon(19244,'paymentMakerForm','beneficiaryBankBranchName','beneficiaryBankCode','beneficiaryLbxBranchID', 'beneficiaryLbxBankID','Please Select Bank','','micrCode','beneficiaryIfscCode','beneficiaryLbxBranchID');closeLovCallonLov('bankCode');" value=" " styleClass="lovbutton"> </html:button>
			    	<html:hidden property="beneficiaryLbxBranchID" styleId="beneficiaryLbxBranchID" value="${sessionScope.datatoapproveList[0].beneficiary_lbxBranchID}"  />
			    	<input type="hidden" name="micrCode" value="" id="micrCode" />
			  </td>
				
			</tr>
			
			<tr>
				<td>
					<bean:message key="lbl.beneficiaryAccountNo"  />								
				</td>
				<td>
					<html:text property="beneficiaryAccountNo" styleId="beneficiaryAccountNo" tabindex="-1" onblur="fnChangeCase('CustBankDetailsForm','accountNo');this.value=removeSpaces1(this.value);" styleClass="text"  value="${sessionScope.datatoapproveList[0].beneficiary_accountNo}"  />
				</td>	
		
				<td>
					<bean:message key="lbl.beneficiaryIfscCode" />
				</td>
				<td>
					<html:text property="beneficiaryIfscCode" styleId="beneficiaryIfscCode" styleClass="text" readonly="true" maxlength="20" value="${sessionScope.datatoapproveList[0].beneficiary_ifscCode}"  />
				</td>	
			</tr>
		    <!-- Virender Code End --> 
	        
		    <tr>
	         <td><bean:message key="lbl.tdsAmount"></bean:message> </td>
	        <td> <html:text property="tdsAmount" styleId="tdsAmount" styleClass="text" value="${sessionScope.datatoapproveList[0].tdsAmount}" maxlength="18" disabled="true" style="text-align: right" ></html:text>		
	        </td>
	         <td>TA Adjustment</td>
			<td>
			   <logic:present name="taStatus">
			   <input type="checkbox" id="taStatus"  name="taStatus" checked="checked" disabled="disabled" />
			   </logic:present>
			   <logic:notPresent name="taStatus">
			   <input type="checkbox" id="taStatus"  name="taStatus"  disabled="disabled" />
			   </logic:notPresent>
			</td>
	       </tr>
			<tr>
		
			<td nowrap="nowrap">
			<bean:message key="lbl.taLoanNo" />
				
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="taLoanNo" styleId="taLoanNo" value="${sessionScope.datatoapproveList[0].taLoanNo}" readonly="true" maxlength="20" />				
<!--				<html:button property="taloanButton" styleId="taloanButton" disabled="true" value=" " onclick="setSuppManufIdInSessForPayment();openLOVCommon(552,'paymentMakerForm','taLoanNo','businessPartnerType','lbxTaLoanNoHID', 'lbxBPType','Select Supplier/Manufacturer First','','taCustomerName');closeLovCallonLov('taLoanNo');" styleClass="lovbutton"> </html:button>-->
				<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
				<html:hidden property="lbxTaLoanNoHID" styleId="lbxTaLoanNoHID" value="${sessionScope.datatoapproveList[0].lbxTaLoanNoHID}" />
				<html:hidden property="hid" styleId="hid" value="" />
			</td>
			<td nowrap="nowrap">
				<bean:message key="lbl.taCustomerName" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="taCustomerName"
					styleId="taCustomerName" maxlength="50" readonly="true"
					value="${sessionScope.datatoapproveList[0].taCustomerName}" tabindex="-1" />
			</td>			
		</tr>
		    <tr>
	         <td><bean:message key="lbl.remark"></bean:message></td>
	         <td>
	         <textarea name="remarks" id="remarks" disabled="disabled" maxlength="1000" class="text">${sessionScope.datatoapproveList[0].remarks}</textarea>        
	        </td>
		   
			
			<td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" readonly="true"
			value="${sessionScope.datatoapproveList[0].authorRemarks}" /></td>
			</tr>
		 </logic:present>
	
		<logic:notPresent name="payableList">
		<logic:notPresent name="datatoapproveList">
    	<input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
		<tr>
		   <td><bean:message key="lbl.loanAccount"></bean:message> <font color="red">*</font></td>
		   <td>
		 <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="" readonly="true" tabindex="-1"/>
        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID"  />
        <html:button property="loanAccountButton" styleId="loanAccountButton" onclick="closeLovCallonLov1();openLOVCommon(46,'paymentMakerForm','loanAccountNumber','','', '','','bpTypeNull','customerName')" value=" " styleClass="lovbutton"></html:button>
      <!--  <img onclick="openLOVCommon(46,'paymentMakerForm','loanAccountNumber','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif" /> -->
        <input type="hidden" value='${requestScope.laonId}' id="loanId" name="loanId"/>	
		  </td>
	<td><bean:message key="lbl.customerName"></bean:message></td>
	<td >
<!--	<a rel="tooltip3" id="tool">-->

<!--		<html:text property="customerName"  styleClass="text" styleId="customerName" value="" onmouseover="applyToolTip(id);" maxlength="50" readonly="true" tabindex="-1"></html:text>-->
		<html:text property="customerName"  styleClass="text" styleId="customerName" value=""  maxlength="50" readonly="true" tabindex="-1"></html:text>
<!--	</a>-->
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	</td>
		</tr>
	      <tr>
		    <td><bean:message key ="lbl.businessPartnerType"></bean:message><font color="red">*	</font></td>
			<td>
			<html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"  value="" readonly="true" tabindex="-1"/>
			<html:hidden  property="lbxBPType" styleId="lbxBPType" />
			<html:button property="businessPartnerButton" styleId="businessPartnerButton" onclick="openLOVCommon(147,'paymentMakerForm','businessPartnerType','loanAccountNumber','lbxBPType', 'lbxLoanNoHID','Select loanAccountNumber','getPayTo','lbxBPNID','businessPartnerName');closeLovCallonLov('loanAccountNumber');" value=" " styleClass="lovbutton"></html:button>
             
		   
		    </td>    
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>
	   
		 <html:text styleClass="text" property="businessPartnerName" styleId="businessPartnerName" maxlength="50"  value="" readonly="true" onmouseover="applyToolTip(id);" tabindex="-1"/>
		
         <html:hidden  property="lbxBPNID" styleId="lbxBPNID" />        
		  </td>
		 </tr>
		 
		         <tr>
		     <td><bean:message key="lbl.payTo"></bean:message></td>
	        <td>
	         <html:text property="payTo" styleId="payTo" readonly="true" styleClass="text" value=" "	tabindex="-1"/>
	         <input type="hidden"  name="lbxpayTo" id="lbxpayTo" value=" "/>
             <html:button property="payToButton" disabled="true" styleId="payToButton" onclick="openLOVCommon(262,'paymentMakerForm','payTo','loanAccountNumber','lbxpayTo', 'lbxLoanNoHID','Select Loan Account Number','payeeNameRefresh','hid');closeLovCallonLov('loanAccountNumber');" value=" " styleClass="lovbutton"></html:button>
	        </td>
	        <td><bean:message key="lbl.payeeName"></bean:message></td>
	        <td>
	         <a rel="tooltip3" id="tool">
	                <html:text property="payeeName" styleId="payeeName" readonly="true" styleClass="text" value=" " onmouseover="applyToolTip(id);" onchange="insertValue();" tabindex="-1" maxlength="100"/>
	                
	         </a>
	        	<input type="hidden"  name="lbxpayeeName" id="lbxpayeeName" value=" "/>
                <html:button property="payeeNameButton"  styleId="payeeNameButton" onclick="openLOVCommon(263,'paymentMakerForm','payeeName','payTo|loanAccountNumber','lbxpayeeName', 'lbxpayTo|lbxLoanNoHID','Select Pay To|Select Loan Account Number','','hid');closeLovCallonLov('payTo');" value=" " styleClass="lovbutton"></html:button>	
	           <html:button property="payeeNameButton"  styleId="payeeNameButtonRSP" style="display: none" onclick="openLOVCommon(655,'paymentMakerForm','payeeName','payTo|loanAccountNumber','lbxpayeeName', 'lbxpayTo|lbxLoanNoHID','Select Pay To|Select Loan Account Number','','hid');closeLovCallonLov('payTo');" value=" " styleClass="lovbutton"></html:button>	
			</td>
	        </tr>
	<tr>  
	<td><bean:message key ="lbl.paymentMode"></bean:message><font color="red">* </font></td>
		 <td><span style="float:left;">
		 <html:select property="paymentMode" styleId="paymentMode" styleClass="text" onchange="fnNull();getAccountType();">
		 <html:option value="">--Select--</html:option> 
		  <html:option value="C">Cash</html:option> 
		 <html:option value="Q">Cheque</html:option> 
		 <html:option value="D">DD</html:option> 
		 <html:option value="N">NEFT</html:option> 
		 <html:option value="R">RTGS</html:option>
		  <html:option value="S">ADJUSTMENT</html:option>	              
           </html:select>
           <input type="hidden"  name="lbxpaymentMode" id="lbxpaymentMode"/>
		 </span></td>
	    <td><bean:message key ="lbl.paymentDate"></bean:message> <bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font> </td>
			<td>
			<html:text property="paymentDate" styleClass="text" styleId="paymentDate" value="${userobject.businessdate}" maxlength="10" onchange="return checkDate('paymentDate');"></html:text> 
	       </td>
		 
			
		</tr>
		<tr>
			 <td><bean:message key="lbl.instrumentNumber"></bean:message><font color="red">* </font></td>
			 <td><div style="float:left;">
			 <html:text property="instrumentNumber" styleClass="text" styleId="instrumentNumber" value="" maxlength="20"></html:text>
			 <input type="hidden" name="instrumentID" id="instrumentID" value="" />
			 </div></td>
			 <td><bean:message key="lbl.instrumentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">* </font></td>
	    <td id='test'><html:text property="instrumentDate" styleClass="text" styleId="instrumentDate" value="${userobject.businessdate}" maxlength="10" onchange="return checkDate('instrumentDate');"></html:text></td>	
	     <td id='showTest' style="display: none;"><input type="text" class="text" name="instrumentDate1" id="instrumentDate1" value="" disabled="disabled"/></td>
	  			
		  
	    
		</tr>
		<tr>
			<td><bean:message key="lbl.bankAccount"></bean:message><font color="red">*</font></td>
			 <td><div style="float:left;"> 
	 <html:text styleClass="text" property="bankAccount" styleId="bankAccount" maxlength="50"  value="" readonly="true" tabindex="-1"/>
	  <input type="hidden"  name="lbxbankAccountID" id="lbxbankAccountID"/>                       
	 <html:button property="bankAccountButton" styleId="bankAccountButton" onclick="openLOVCommon(64,'paymentMakerForm','bankAccount','paymentMode','lbxbankAccountID', 'lbxpaymentMode','Select Payment Mode','getChequeDetail','hid','hid1');closeLovCallonLovBAB('paymentMode');" value=" " styleClass="lovbutton"></html:button>
     <!-- <img onclick="openLOVCommon(64,'paymentMakerForm','bankAccount','paymentMode','lbxbankAccountID', 'lbxpaymentMode','Select Payment Mode','getChequeDetail','hid','hid1')" src="<%= request.getContextPath()%>/images/lov.gif"/> -->       <input type="hidden" name="hid1" id="hid1"/> 
    <input type="hidden" name="hid" id="hid"/> 
    </div></td>
		   <td>
		   <bean:message key="lbl.bank"></bean:message>
	</td>
			 <td><div style="float:left;">
			 
			 
	 <html:text styleClass="text" property="bank" styleId="bank" maxlength="50"  value="" readonly="true" tabindex="-1"/>
    <html:hidden  property="lbxBankID" styleId="lbxBankID"/>
    <!--  <img onClick="openLOVCommon(7,'paymentMakerForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif">
          -->	  
			 </div></td>
			 
		
		</tr> 
	<tr>	
	<td><bean:message key="lbl.branch"></bean:message></td>
		<td>
		
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="50"  value="" readonly="true" tabindex="-1"/>
   <html:hidden  property="lbxBranchID" styleId="lbxBranchID" />
        <!--     <img onClick="openLOVCommon(48,'paymentMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode')" src="<%= request.getContextPath()%>/images/lov.gif" >
      -->
		</td>	 
	<td><bean:message key="lbl.micr" ></bean:message></td>
	<td>
	<html:text property="micr" styleClass="text" styleId="micr" value="" maxlength="20" readonly="true" tabindex="-1"></html:text>
	</td>
		   
	         </tr>
	         <tr>
	         <td>
		   <bean:message key="lbl.ifsCode"></bean:message>
	 </td>
			 <td><div style="float:left;">
			 <html:text property="ifsCode" styleId="ifsCode" styleClass="text" value="" maxlength="20" readonly="true" tabindex="-1"></html:text>		
	         </div></td>
	         <td><bean:message key="lbl.paymentAmount"></bean:message><font color="red">* </font> </td>
			 <td><div style="float:left;">
			 <html:text property="paymentAmount" styleClass="text" styleId="paymentAmount" value="" 
			 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" ></html:text>
			 <html:hidden  property="txnAdvicedID" styleId="txnAdvicedID"  />
			 </div></td>			 
			  </tr>
			  
			<!-- Virender Code Start -->
			<tr>
				<td>
					<bean:message key="lbl.beneficiary_bank" />
				</td>
				<td>
					<html:text property="beneficiaryBankCode" styleId="beneficiaryBankCode" readonly="true" styleClass="text" value="${requestScope.payableList[0].beneficiaryBankCode}" tabindex="-1" />
					 <html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();
					 openLOVCommon('19243','paymentMakerForm','beneficiaryBankCode','','beneficiaryBankBranchName', 'beneficiaryLbxBranchID','','','beneficiaryLbxBankID');" value=" " styleClass="lovbutton"> </html:button>
					<input type="hidden" name="contextPath" value="<%=request.getContextPath()%>" id="contextPath" />
					<html:hidden property="beneficiaryLbxBankID" styleId="beneficiaryLbxBankID" value="${requestScope.payableList[0].beneficiaryLbxBankID}"/>
				</td>
		
				<td>
					<bean:message key="lbl.beneficiaryBankBranchName" />									
				</td>
				
				<td>
					<html:text property="beneficiaryBankBranchName" styleId="beneficiaryBankBranchName" readonly="true" styleClass="text" maxlength="50" tabindex="-1" value="${requestScope.payableList[0].beneficiaryBankBranchName}" />
                    <html:button property="bankBranchButton" styleId="bankBranchButton" onclick="openLOVCommon(19244,'paymentMakerForm','beneficiaryBankBranchName','beneficiaryBankCode','beneficiaryLbxBranchID', 'beneficiaryLbxBankID','Please Select Bank','','micrCode','beneficiaryIfscCode','beneficiaryLbxBranchID');closeLovCallonLov('bankCode');" value=" " styleClass="lovbutton"> </html:button>
			    	<html:hidden property="beneficiaryLbxBranchID" styleId="beneficiaryLbxBranchID" value="${requestScope.payableList[0].beneficiaryLbxBranchID}"  />
			    	<input type="hidden" name="micrCode" value="" id="micrCode" />
			  </td>
				
			</tr>
			
			<tr>
				<td>
					<bean:message key="lbl.beneficiaryAccountNo"  />								
				</td>
				<td>
					<html:text property="beneficiaryAccountNo" styleId="beneficiaryAccountNo" tabindex="-1" onblur="fnChangeCase('CustBankDetailsForm','accountNo');this.value=removeSpaces1(this.value);" styleClass="text"  value="${requestScope.payableList[0].beneficiaryAccountNo}"  />
				</td>	
		
				<td>
					<bean:message key="lbl.beneficiaryIfscCode" />
				</td>
				<td>
					<html:text property="beneficiaryIfscCode" styleId="beneficiaryIfscCode" styleClass="text" readonly="true" maxlength="20" value="${requestScope.payableList[0].beneficiaryIfscCode}"  />
				</td>	
			</tr>
		    <!-- Virender Code End --> 
			  
		    <tr>
	         <td><bean:message key="lbl.tdsAmount"></bean:message></td>
	        <td> <html:text property="tdsAmount" styleId="tdsAmount" styleClass="text" value="" 
	        onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right" ></html:text>		
	        </td>
	        <td>TA Adjustment</td>
			<td>
			   <input type="checkbox" id="taStatus"  name="taStatus" onclick="enableTaLoanLov()" />
			</td>
	       </tr>
			<tr>
		
			<td nowrap="nowrap">
			<bean:message key="lbl.taLoanNo" />
				
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="taLoanNo" styleId="taLoanNo" value="" readonly="true" maxlength="20" />				
				<html:button property="taloanButton" styleId="taloanButton" disabled="true" value=" " onclick="setSuppManufIdInSessForPayment();openLOVCommon(552,'paymentMakerForm','taLoanNo','businessPartnerType','lbxTaLoanNoHID', 'lbxBPType','Select Supplier/Manufacturer First','validateTABalancePayment','taCustomerName','balAmount');closeLovCallonLov('taLoanNo');" styleClass="lovbutton"> </html:button>
				<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
				<html:hidden property="lbxTaLoanNoHID" styleId="lbxTaLoanNoHID" value="" />
				<html:hidden property="hid" styleId="hid" value="" />
			</td>
			<td nowrap="nowrap">
				<bean:message key="lbl.taCustomerName" />
			</td>
			<td nowrap="nowrap">
				<html:text styleClass="text" property="taCustomerName"
					styleId="taCustomerName" maxlength="50" readonly="true"
					value="" tabindex="-1" />
			</td>			
		</tr>
	        <tr>
	         <td><bean:message key="lbl.makerRemark" /></td>
	         <td>
	         <textarea name="remarks" id="remarks" maxlength="1000" class="text"></textarea>
	        </td>	        
			
			<td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" readonly="true" value="" /></td>
			</tr>
	     </logic:notPresent>
		  </logic:notPresent>
	
	</table>
	</td>

	</tr>
		 <tr>
		   <logic:notPresent name="datatoapproveList">
		 <logic:present name="payableList">
	       <td>
	     <button type="button" id="save" class="topformbutton2" onclick="return onSave('<bean:message key="msg.InstDateLessPay" />','<bean:message key="msg.BussDateLessPay" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	    <button type="button" id="saveForward" class="topformbutton3" onclick="return onPaymentForward('<bean:message key="msg.plsChckView" />','<bean:message key="msg.InstDateLessPay" />','<bean:message key="msg.BussDateLessPay" />','<bean:message key="msg.confirmationForwardMsg" />');" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button>
	    <button type="button" id="viewPay" class="topformbutton3" onclick="return viewPayable('<bean:message key="msg.LoanAccBPType" />');" title="Alt+P" accesskey="P"><bean:message key="button.viewPayable" /></button>
		<button type="button" id="allocatePay" class="topformbutton3" onclick="return allocatePayable('<bean:message key="msg.plsSaveView" />');" title="Alt+A" accesskey="A"><bean:message key="button.allocPayable1" /></button>
		<button type="button" id="delete" class="topformbutton2" onclick="return deletePayment();" title="Alt+D" accesskey="D"><bean:message key="button.delete" /></button>
		  </td>
		 </logic:present>
	       <logic:notPresent name="payableList">
	       <td>
	     <button type="button" id="save" class="topformbutton2" onclick="return onSave('<bean:message key="msg.InstDateLessPay" />','<bean:message key="msg.BussDateLessPay" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
	    <button type="button" id="saveForward" class="topformbutton3" onclick="return onPaymentForward('<bean:message key="msg.plsChckView" />','<bean:message key="msg.InstDateLessPay" />','<bean:message key="msg.BussDateLessPay" />','<bean:message key="msg.confirmationForwardMsg" />');" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button>
	    <button type="button" id="viewPay" class="topformbutton3" onclick="return viewPayable('<bean:message key="msg.LoanAccBPType" />');" title="Alt+P" accesskey="P"><bean:message key="button.viewPayable" /></button>
		<button type="button" id="allocatePay" class="topformbutton3" onclick="return allocatePayable('<bean:message key="msg.plsSaveView" />');" title="Alt+A" accesskey="A"><bean:message key="button.allocPayable1" /></button>
		
		  </td>
		 </logic:notPresent>
	    </logic:notPresent>
	</tr>
	 </table>

	</fieldset>	
	
	
     
  </html:form>
  
  <!--change by Mradul-->
	<logic:present name="paymentInventoryStatus">
	<script type="text/javascript">
	if('<%=request.getAttribute("paymentInventoryStatus").toString()%>'=='CNI')
		{
			alert("Cheque not available in inventory");
			
		}else
		if('<%=request.getAttribute("paymentInventoryStatus").toString()%>'=='RU')
		{
			alert("CHEQUE IS ALLREADY USED OR NOT ALLOCATED TO LOGGED-IN BRANCH");		
		}
		else
		if('<%=request.getAttribute("paymentInventoryStatus").toString()%>'=='RNU')
		{
			alert("CHEQUE IS NOT IN SEQUENCE");		
		}
	</script>
	</logic:present>
<!--end by Mradul	-->
  
    <logic:present name="procvalForAuthor">
		<script type="text/javascript">
			if('<%=request.getAttribute("procvalForAuthor")%>'!='NONE')
			{
		   	alert('<%=request.getAttribute("procvalForAuthor").toString()%>');
			}
		</script>
  </logic:present>
  
  <logic:present name="procval">
	<script type="text/javascript">
	if(('<%=request.getAttribute("procval")%>'!='F')||('<%=request.getAttribute("procval")%>'!='OK'))
	{
	   	alert('<%=request.getAttribute("procval").toString()%>');
		
	}
	</script>
</logic:present>
		<logic:present name="msg">
		<script type="text/javascript">
		
		if('<%=request.getAttribute("msg").toString()%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
		
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='F')
		{
			var frdLoanID=document.getElementById("frdLoanID").value;
			alert("<bean:message key="lbl.forwardSuccess" />");
			window.location="<%=request.getContextPath()%>/paymentMakerSearch.do?forward=forward&frdLoanID="+frdLoanID;
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='A')
		{
			alert("<bean:message key="lbl.dataSave" />");
			window.location="<%=request.getContextPath()%>/paymentMakerSearch.do";
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
			
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='D')
		{
		
		alert("<bean:message key="lbl.instExist" />");
		}
		else if('<%=request.getAttribute("msg").toString()%>'=="NA")
		
		{
			var allocatePayFlag=document.getElementById("allocatePayFlag").value;
			
			if(allocatePayFlag =='Y')
			{
				alert("First Allocate the Payment Amount.")
			}
			else if(allocatePayFlag =='W')
			{
				if(confirm("Payment Amount has not been Allocated.Do you want to proceed"))
				{
				
					onPaymentForwardWithoutCheck('<bean:message key="msg.plsChckView" />','<bean:message key="msg.InstDateLessPay" />','<bean:message key="msg.BussDateLessPay" />');

				}
			}
			else
			{
				onPaymentForwardWithoutCheck('<bean:message key="msg.plsChckView" />','<bean:message key="msg.InstDateLessPay" />','<bean:message key="msg.BussDateLessPay" />');
			} 
		     
		}
		else if('<%=request.getAttribute("msg").toString()%>'=="DS")
		{
			alert("<bean:message key="lbl.dataDeleted" />");
			window.location="<%=request.getContextPath()%>/paymentMakerSearch.do";
		}else if('<%=request.getAttribute("msg").toString()%>'=='DN')
		{
			alert("<bean:message key="lbl.dataNtDeleted" />");
			
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='CHECKDATE')
		{
			alert("<bean:message key="lbl.dateNotEqualPayment" />");
			
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='VALIDPAYMENT')
		{
			alert("<bean:message key="lbl.amountNotEqualPayable" />");
			
		}
		</script>
		</logic:present>	

</div>



</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("paymentMakerForm");
</script>
</body>
</html:html>