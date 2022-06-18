<!--
 	Author Name      :- Arun Kumar Mishra
 	Date of Creation :- 17-10-2012
 	Purpose          :- Interface to Individual Financial Bank Account Analysis
 -->

 <%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
            <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/individualFinancialAnalysis.js"></script> 
		    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
  		    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
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
	<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();checkChanges('bankAccountAnalysisForm');" >
	<div id="centercolumn">
	<div id="revisedcontainer">


<fieldset>

<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Individual Financial Analysis</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
	
<logic:notPresent name="underWriterViewData">
	<html:form action="/bankAccountAnalysisAction" styleId="bankAccountAnalysisForm" method="post">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	 
	   <fieldset>	  
	<legend><bean:message key="lbl.bankAccountDetails"/></legend>
	  <logic:present name="bankAcAnalysisDetail">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<tr>
		<td colspan="2"><bean:message key="lbl.dealNo"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="10" readonly="true" value="${bankAcAnalysisDetail[0].dealNo}"  tabindex="-1"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${bankAcAnalysisDetail[0].lbxDealNo}" />
			<input type="hidden" id="dealId" value=""/>
			<!--			<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(58,'fundFlowAnalysisSearchForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>-->
	
     </td>
	<td width="17%"><bean:message key="lbl.customerName"/><font color="red">*</font></td>
	<td width="28%">
	<html:text property="customerName" styleClass="text" styleId="customerName" readonly="true"  maxlength="50" value="${bankAcAnalysisDetail[0].customerName}"/>
	</td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.name"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		<input type="hidden" id="hCommon" value=""/>
		<html:text property="bankName" styleId="bankName" styleClass="text" maxlength="50" value="${bankAcAnalysisDetail[0].bankName}" readonly="true" tabindex="-1"/>
		
   <html:hidden  property="lbxBankID" styleId="lbxBankID"  value="${bankAcAnalysisDetail[0].lbxBankID}"/>
   <html:button property="bankButton" styleId="bankButton" value=" " onclick="closeLovCallonLov1();openLOVCommon(7,'bankAccountAnalysisForm','bankName','','bankBranch', 'lbxBranchID','','','hCommon')" styleClass="lovbutton"> </html:button>
  
     </td>
	<td width="17%"><bean:message key="lbl.bankBranchName"/><font color="red">*</font></td>
	<td width="28%">
	<html:text  styleClass="text" property="bankBranch" styleId="bankBranch" value="${bankAcAnalysisDetail[0].bankBranch}" maxlength="50" tabindex="-1" readonly="true"/>
   <html:hidden property="lbxBranchID" styleId="lbxBranchID" value="${bankAcAnalysisDetail[0].lbxBranchID}"/>
   
   <html:button property="bankBrnchButton" styleId="bankBrnchButton" value=" " onclick="closeLovCallonLov('bankName');openLOVCommon(8,'bankAccountAnalysisForm','bankBranch','bankName','lbxBranchID', 'lbxBankID','Please select Bank First!','','hCommon');" styleClass="lovbutton"> </html:button>
   </td>
		</tr>
	  <tr>
	    <td colspan="2"><bean:message key="lbl.aCType"/><font color="red">*</font> </td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:select property="accountType" styleId="accountType" styleClass="text" value="${bankAcAnalysisDetail[0].accountType}">
	    <html:option value="">Select</html:option>
	    <html:option value="1">CC/OD</html:option>
	    <html:option value="2">Current</html:option>
	    <html:option value="3">Saving</html:option>
	    <html:option value="4">Others</html:option>
	    </html:select>
	    
	    </td>
		<td><bean:message key="lbl.accountNo"/> <font color="red">*</font></td>
		<td nowrap="nowrap" ><html:text  styleClass="text" property="accountNo" styleId="accountNo" value="${bankAcAnalysisDetail[0].accountNo}" maxlength="20" style="text-align: right;"
		onkeypress="return isNumberKey(event);" /> </td>
	  </tr>
		 <tr>
		   <td><div align="left"><bean:message key="lbl.statementMonthAndYear"/> <font color="red">*</font></div></td>
		   <td>&nbsp;</td>
		   <td  nowrap="nowrap" >
		     
		         <div align="left">
		          <html:select property="month" styleId="month" styleClass="text" styleClass="text" style="width:57px !important; min-width:55px !important;" value="${bankAcAnalysisDetail[0].month}">
		         	<html:option value="1">Jan</html:option>
		         	<html:option value="2">Feb</html:option>
		         	<html:option value="3">Mar</html:option>
		         	<html:option value="4">Apr</html:option>
		         	<html:option value="5">May</html:option>
		         	<html:option value="6">Jun</html:option>
		         	<html:option value="7">Jul</html:option>
		         	<html:option value="8">Aug</html:option>
		         	<html:option value="9">Sep</html:option>
		         	<html:option value="10">Oct</html:option>
		         	<html:option value="11">Nov</html:option>
		         	<html:option value="12">Dec</html:option>
		         </html:select>
		         
		         <html:select property="year" styleId="year" styleClass="text" value="${bankAcAnalysisDetail[0].year}" style="width:90px !important; min-width:85px !important;">
<!--		         <html:option value="">--Select--</html:option>-->
		         	<logic:present name="yearList">
						<html:optionsCollection name="yearList"	label="statementMonthAndYear" value="statementMonthAndYear" />
					</logic:present>
		         </html:select>
<!--		           <html:text  styleClass="text" property="statementMonthAndYear" styleId="statementMonthAndYear" value="${bankAcAnalysisDetail[0].statementMonthAndYear}" maxlength="22" />-->
	            </div></td>
		   <td  nowrap="nowrap" >&nbsp;</td>
		   <td  nowrap="nowrap" >&nbsp;</td>
		   <td  nowrap="nowrap" >&nbsp;</td>
		    <td  nowrap="nowrap" ><bean:message key="lbl.totalEMI"/></td>
			 <td  nowrap="nowrap" > <html:text  styleClass="text" property="totalEMI" styleId="totalEMI" value="${bankAcAnalysisDetail[0].totalEMI}" maxlength="22" style="text-align: right;" 
			 onkeypress="return numbersonly(event,id,18)"  onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		 </tr>
		    <tr>
	    <td colspan="2"><bean:message key="lbl.credit"/><font color="red">*</font> </td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="credit" styleId="credit" value="${bankAcAnalysisDetail[0].credit}" maxlength="22" style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" /></td>
		<td><bean:message key="lbl.debit"/><font color="red">*</font></td>
		<td nowrap="nowrap" > 
		<html:text  styleClass="text" property="debit" styleId="debit" value="${bankAcAnalysisDetail[0].debit}" maxlength="22" style="text-align: right;"
		onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	  </tr>
	   <tr>
	    <td colspan="2"><bean:message key="lbl.highestBalance"/></td>
	    <td colspan="4" nowrap="nowrap" > 
	    <html:text  styleClass="text" property="highestBalance" styleId="highestBalance" value="${bankAcAnalysisDetail[0].highestBalance}" maxlength="22" style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" /></td>
		<td><bean:message key="lbl.balance"/></td>
		<td nowrap="nowrap" >
		<html:text  styleClass="text" property="lowestBalance" styleId="lowestBalance" value="${bankAcAnalysisDetail[0].lowestBalance}" maxlength="22" style="text-align: right;" 
		onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>        </td>
	  </tr>
	   <tr>
	    <td colspan="2"><bean:message key="lbl.bounceamount"/></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="checkBounceAmount" styleId="checkBounceAmount" value="${bankAcAnalysisDetail[0].checkBounceAmount}" maxlength="22" style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.checkBouncingFrequency"/></td>
		<td nowrap="nowrap" ><html:text  styleClass="text" property="checkBounceFrequency" styleId="checkBounceFrequency" value="${bankAcAnalysisDetail[0].checkBounceFrequency}" maxlength="3" style="text-align: right;" 
		onkeypress="return isNumberKey(event);" />       </td>
	  </tr>
	   <tr>
	    <td colspan="2"><bean:message key="lbl.overLimitAmount"/></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="overLimitAmount" styleId="overLimitAmount" value="${bankAcAnalysisDetail[0].overLimitAmount}" maxlength="22" style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.overlimitFrequency"/></td>
		<td nowrap="nowrap" ><html:text  styleClass="text" property="overLimitFrequency" styleId="overLimitFrequency" value="${bankAcAnalysisDetail[0].overLimitFrequency}" maxlength="3" style="text-align: right;"
		onkeypress="return isNumberKey(event);" /> 
		<input type="hidden" id="bankAnalysisID" value="${bankAcAnalysisDetail[0].bankAcAnId}"/> 
		<logic:present name="fundFlowDealId">
			<input type="hidden" id="dealId" value="${sessionScope.fundFlowDealId}"/>
		</logic:present>
			
		       </td>
	  </tr>
	  <tr>
	    <td colspan="2"><bean:message key="lbl.chequeBouncing"/></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="chequeBouncing" styleId="chequeBouncing" value="${bankAcAnalysisDetail[0].chequeBouncing}" maxlength="3" style="text-align: right;"
	    onkeypress="return numbersonly(event,id,3)"  onkeyup="checkNumber(this.value, event, 3,id);"	onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		
		<td><bean:message key="lbl.limitObligation"/> <font color="red">*</font></td>
	    <td nowrap="nowrap" >
	    <html:text  styleClass="text" property="limitObligation" styleId="limitObligation" value="${bankAcAnalysisDetail[0].limitObligation}" maxlength="3" style="text-align: right;"
	    onkeypress="return numbersonly(event,id,3)"  onkeyup="checkNumber(this.value, event, 3,id);"	onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</tr>
		
		 <tr>
	    <td colspan="2"><bean:message key="lbl.totalCreditTransaction"/></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="totalCreditTxn" styleId="totalCreditTxn" value="${bankAcAnalysisDetail[0].totalCreditTxn}"  style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/></td>
		<td><bean:message key="lbl.totalDebitTransaction"/></td>
	    <td nowrap="nowrap" >
	    <html:text  styleClass="text" property="totalDebitTxn" styleId="totalDebitTxn" value="${bankAcAnalysisDetail[0].totalDebitTxn}"  style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/></td>
		</tr>
		 <tr>
	    <td colspan="2"><bean:message key="lbl.varificationmethod"/><font color="red">*</font></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${bankAcAnalysisDetail[0].varificationMethod}">
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
	    </td>
		<td><bean:message key="lbl.balanceAmountFA"/></td>
	    <td nowrap="nowrap" >
	    <html:text  styleClass="text" property="balanceAmount" styleId="balanceAmount" value="${bankAcAnalysisDetail[0].balanceAmount}"  style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event,18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/></td>
		</tr>
		 <tr>
	    <td colspan="2"><bean:message key="lbl.remarks"/><font color="red">*</font></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:textarea  styleClass="text" property="remarks" styleId="remarks" value="${bankAcAnalysisDetail[0].remarks}"   /></td>
		<td></td>
	    <td nowrap="nowrap" ></td>
		</tr>
	  <tr>
			<td  colspan="10" class="white">
			<logic:present name="insert">
				<button type="button" name="Save" class="topformbutton2" id="Save" title="Alt+V" accesskey="V" onclick="return saveBankAccountAnalysis();"><bean:message key="button.save" /></button>
			</logic:present>
			<logic:notPresent name="insert">
				<button type="button" name="Save" class="topformbutton2" id="Save" title="Alt+V" accesskey="V" onclick="return updateBankAccountAnalysisIndividual(${bankAcAnalysisDetail[0].bankAcAnId});" ><bean:message key="button.save" /></button>
			</logic:notPresent>
			
            <button type="button" name="button"  id="Forward" title="Alt+F" accesskey="F" class="topformbutton3" onclick="return individualFinancialAnalysisForward();"><bean:message key="button.forward" /></button>
			
			</td>
			</tr>
		</table>
		
	      </td>
	</tr>

	
	</table>
	 </logic:present>
	 <logic:notPresent name="bankAcAnalysisDetail">
	
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<tr>
		<td colspan="2"><bean:message key="lbl.dealNo"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="10"  readonly="true" value="${dealHeader[0].dealNo}"  tabindex="-1"/>
		<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${dealHeader[0].dealId}" />
		 </td>
		<td width="17%"><bean:message key="lbl.customerName"/><font color="red">*</font></td>
		<td width="28%">
		 <html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" maxlength="50" value="${dealHeader[0].dealCustomerName}"/>
	   </td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.name"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		<input type="hidden" id="hCommon" value=""/>
		<html:text property="bankName" styleId="bankName" styleClass="text" maxlength="50" value="" readonly="true" tabindex="-1"/>
		
   <html:hidden  property="lbxBankID" styleId="lbxBankID"  value=""/>
   <html:button property="bankButton" styleId="bankButton" value=" " onclick="closeLovCallonLov1();openLOVCommon(7,'bankAccountAnalysisForm','bankName','','bankBranch', 'lbxBranchID','','','hCommon')" styleClass="lovbutton"> </html:button>
  
     </td>
	<td width="17%"><bean:message key="lbl.bankBranchName"/><font color="red">*</font></td>
	<td width="28%">
	<html:text  styleClass="text" property="bankBranch" styleId="bankBranch" value="" maxlength="50" tabindex="-1" readonly="true"/>
   <html:hidden property="lbxBranchID" styleId="lbxBranchID" value=""/>
   
   <html:button property="bankBrnchButton" styleId="bankBrnchButton" value=" " onclick="closeLovCallonLov('bankName');openLOVCommon(8,'bankAccountAnalysisForm','bankBranch','bankName','lbxBranchID', 'lbxBankID','Please select Bank First!','','hCommon');" styleClass="lovbutton"> </html:button>
   </td>
		</tr>
	  <tr>
	    <td colspan="2"><bean:message key="lbl.aCType"/><font color="red">*</font> </td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:select property="accountType" styleId="accountType" styleClass="text" value="">
	    <html:option value="">Select</html:option>
	    <html:option value="1">CC/OD</html:option>
	    <html:option value="2">Current</html:option>
	    <html:option value="3">Saving</html:option>
	    <html:option value="4">Others</html:option>
	    </html:select>
	    
	    </td>
		<td><bean:message key="lbl.accountNo"/> <font color="red">*</font></td>
		<td nowrap="nowrap" ><html:text  styleClass="text" property="accountNo" styleId="accountNo" value="" maxlength="20" style="text-align: right;"
		onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);" /> </td>
	  </tr>
		 <tr>
		   <td><div align="left"><bean:message key="lbl.statementMonthAndYear"/> <font color="red">*</font></div></td>
		   <td>&nbsp;</td>
		   <td  nowrap="nowrap" >
		     
		         <div align="left">
		         <html:select property="month" styleId="month" styleClass="text" style="width:57px !important; min-width:55px !important;" value="">
		         	<html:option value="1">Jan</html:option>
		         	<html:option value="2">Feb</html:option>
		         	<html:option value="3">Mar</html:option>
		         	<html:option value="4">Apr</html:option>
		         	<html:option value="5">May</html:option>
		         	<html:option value="6">Jun</html:option>
		         	<html:option value="7">Jul</html:option>
		         	<html:option value="8">Aug</html:option>
		         	<html:option value="9">Sep</html:option>
		         	<html:option value="10">Oct</html:option>
		         	<html:option value="11">Nov</html:option>
		         	<html:option value="12">Dec</html:option>
		         </html:select>
		         
		         <html:select property="year" styleId="year" styleClass="text" value="" style="width:90px !important; min-width:85px !important;">
<!--		         <html:option value="">--Select--</html:option>-->
		         	<logic:present name="yearList">
						<html:optionsCollection name="yearList"	label="statementMonthAndYear" value="statementMonthAndYear" />
					</logic:present>
		         </html:select>
<!--		           <html:text  styleClass="text" property="statementMonthAndYear" styleId="statementMonthAndYear" value="" maxlength="22" />-->
	            </div></td>
		   <td  nowrap="nowrap" >&nbsp;</td>
		   <td  nowrap="nowrap" >&nbsp;</td>
		   <td  nowrap="nowrap" >&nbsp;</td>
		    <td  nowrap="nowrap" ><bean:message key="lbl.totalEMI"/></td>
			 <td  nowrap="nowrap" > <html:text  styleClass="text" property="totalEMI" styleId="totalEMI" value="" maxlength="22" style="text-align: right;" 
			 onkeypress="return numbersonly(event,id,18)"  onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		 </tr>
		    <tr>
	    <td colspan="2"><bean:message key="lbl.totalCreditAmount"/><font color="red">*</font> </td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="credit" styleId="credit" value="" maxlength="22" style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" /></td>
		<td><bean:message key="lbl.totalDebitAmount"/><font color="red">*</font></td>
		<td nowrap="nowrap" > 
		<html:text  styleClass="text" property="debit" styleId="debit" value="" maxlength="22" style="text-align: right;"
		onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	  </tr>
	   <tr>
	    <td colspan="2"><bean:message key="lbl.highestBalance"/></td>
	    <td colspan="4" nowrap="nowrap" > 
	    <html:text  styleClass="text" property="highestBalance" styleId="highestBalance" value="" maxlength="22" style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" /></td>
		<td><bean:message key="lbl.balance"/></td>
		<td nowrap="nowrap" >
		<html:text  styleClass="text" property="lowestBalance" styleId="lowestBalance" value="" maxlength="22" style="text-align: right;" 
		onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>        </td>
	  </tr>
	   <tr>
	    <td colspan="2"><bean:message key="lbl.bounceamount"/> </td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="checkBounceAmount" styleId="checkBounceAmount" value="" maxlength="22" style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.checkBouncingFrequency"/> </td>
		<td nowrap="nowrap" ><html:text  styleClass="text" property="checkBounceFrequency" styleId="checkBounceFrequency" value="" maxlength="3" style="text-align: right;"
		onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" />       </td>
	  </tr>
	   <tr>
	    <td colspan="2"><bean:message key="lbl.overLimitAmount"/></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="overLimitAmount" styleId="overLimitAmount" value="" maxlength="22" style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)"  onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.overlimitFrequency"/></td>
		<td nowrap="nowrap" ><html:text  styleClass="text" property="overLimitFrequency" styleId="overLimitFrequency" value="" maxlength="3" style="text-align: right;"
		onkeypress="return isNumberKey(event);"  onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" />
				
	    <input type="hidden" id="bankAnalysisID" value="" />    
		<logic:present name="fundFlowDealId">
			<input type="hidden" id="dealId" value="${sessionScope.fundFlowDealId}"/>
		</logic:present>
		<logic:notPresent name="fundFlowDealId">
			<input type="hidden" id="dealId" value=""/>
		</logic:notPresent>
			</td>
	  </tr>
	   <tr>
	    <td colspan="2"><bean:message key="lbl.chequeBouncing"/></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="chequeBouncing" styleId="chequeBouncing" value=""  style="text-align: right;"
	    onkeypress="return numbersonly(event,id,3)"  onkeyup="checkNumber(this.value, event, 3,id);"	onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		<td><bean:message key="lbl.limitObligation"/> <font color="red">*</font></td>
	    <td nowrap="nowrap" >
	    <html:text  styleClass="text" property="limitObligation" styleId="limitObligation" value=""  style="text-align: right;"
	    onkeypress="return numbersonly(event,id,3)"  onkeyup="checkNumber(this.value, event, 3,id);"	onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</tr>
		 <tr>
	    <td colspan="2"><bean:message key="lbl.totalCreditTransaction"/></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="totalCreditTxn" styleId="totalCreditTxn" value=""  style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/></td>
		<td><bean:message key="lbl.totalDebitTransaction"/></td>
	    <td nowrap="nowrap" >
	    <html:text  styleClass="text" property="totalDebitTxn" styleId="totalDebitTxn" value=""  style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/></td>
		</tr>
		 <tr>
	    <td colspan="2"><bean:message key="lbl.varificationmethod"/><font color="red">*</font></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="">
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
	    </td>
		<td><bean:message key="lbl.balanceAmountFA"/></td>
	    <td nowrap="nowrap" >
	    <html:text  styleClass="text" property="balanceAmount" styleId="balanceAmount" value=""  style="text-align: right;"
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event,18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/></td>
		</tr>
		 <tr>
	    <td colspan="2"><bean:message key="lbl.remarks"/><font color="red">*</font></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:textarea  styleClass="text" property="remarks" styleId="remarks" value=""   /></td>
		<td></td>
	    <td nowrap="nowrap" ></td>
		</tr>
	  <tr>
			<td nowrap colspan="10" class="white">
			<button type="button" name="Save" class="topformbutton2" id="Save" title="Alt+V" accesskey="V" onclick="return saveBankAccountAnalysisIndividual();" ><bean:message key="button.save" /></button>
			<button type="button" name="button"  id="Forward" title="Alt+F" accesskey="F" class="topformbutton3" onclick="return individualFinancialAnalysisForward();"><bean:message key="button.forward" /></button>
			<!-- 
			<logic:present name="fundFlowDealId">
				   <button type="button" class="topformbutton2" onclick="bankAccClearWithoutLoanCust();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
			 </logic:present>
			 <logic:notPresent name="fundFlowDealId">
			  	<button type="button" class="topformbutton2" onclick="bankAccountAnalysisClear();" accesskey="C" title="Alt+C"><bean:message key="button.clear" /></button>
			 </logic:notPresent>
			 --> 		 
			</td>
			</tr>
		</table>
		
	      </td>
	</tr>
	
	
	</table>
	<logic:present name="back">
				<script type="text/javascript">
	alert("You can't move without Fill Up Bank Analysis Detail!!!");
	
    </script>
    </logic:present>
	 </logic:notPresent>

</fieldset>	
	<br/>
	<logic:present name="financialDealId">
	<fieldset>	
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td >
    	 <b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="contact.select" /></b>
    	</td>
	    <td ><strong><bean:message key="lbl.name"/> </strong></td>
        <td ><strong><bean:message key="lbl.branchDesc"/> </strong></td>
        <td ><strong><bean:message key="lbl.monthAndYear"/> </strong></td>
		<td ><strong><bean:message key="lbl.credit"/> </strong></td>
		<td ><strong><bean:message key="lbl.debit"/> </strong></td>
		<td ><strong><bean:message key="lbl.highestBalance"/>  </strong></td>
		<td ><strong><bean:message key="lbl.balance"/> </strong></td>
		<td ><strong><bean:message key="lbl.amount"/> </strong></td>
		<td ><strong><bean:message key="lbl.percentage"/> </strong></td>
		<td ><strong><bean:message key="lbl.bounceamount"/> </strong></td>
		<td ><strong><bean:message key="lbl.frequency"/> </strong></td>
		<td ><strong><bean:message key="lbl.limitamount"/> </strong></td>
		<td ><strong><bean:message key="lbl.overlimitFrequency"/></strong></td>
		</tr>
    	<logic:present name="bankAcAnalysisList">
			<logic:iterate id="subBankAcAnalysisList" name="bankAcAnalysisList">
				<tr class="white1">
					<td >
					<input type="checkbox" name="chk" id="chk" value="${subBankAcAnalysisList.bankAcAnId }"/>
    	 			</td>
					<td ><a href="#" id="anchor0" onclick="return getBankAcAnalysisIndividual(${subBankAcAnalysisList.bankAcAnId });">${subBankAcAnalysisList.bankName }</a></td>
					<td >${subBankAcAnalysisList.bankBranch }</td>
					<td>${subBankAcAnalysisList.statementMonthAndYear }</td>
					<td>${subBankAcAnalysisList.credit }</td>
					<td>${subBankAcAnalysisList.debit }</td>
					<td>${subBankAcAnalysisList.highestBalance }</td>
					<td>${subBankAcAnalysisList.lowestBalance }</td>
					<td>${subBankAcAnalysisList.swingAmt }</td>
					<td>${subBankAcAnalysisList.swingPercent }</td>
					<td>${subBankAcAnalysisList.checkBounceAmount }</td>
					<td>${subBankAcAnalysisList.checkBounceFrequency }</td>
					<td>${subBankAcAnalysisList.overLimitAmount }</td>
					<td>${subBankAcAnalysisList.overLimitFrequency }</td>
				
				</tr>	
			</logic:iterate>
			<input type="hidden" id="dealId" value="${subBankAcAnalysisList.lbxDealNo }"/>
		</logic:present>	

 </table>
    
    </td>
</tr>
</table>

		<button type="button" name="button1" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteBankAcAnalysis();"><bean:message key="button.delete" /></button>
 	
	</fieldset>
	</logic:present>
</html:form>
</logic:notPresent>
<%--   -------------- -------------------- ---------View  mode--- -------------------- -------------------- --------------------- --%>
<logic:present name="underWriterViewData">
	<html:form action="/bankAccountAnalysisAction" styleId="bankAccountAnalysisForm" method="post">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	   <fieldset>	  
	<legend><bean:message key="lbl.bankAccountDetails"/></legend>
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
<tr>
		<td colspan="2"><bean:message key="lbl.dealNo"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		<html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="10" readonly="true" value="${bankAcAnalysisDetail[0].dealNo}"  tabindex="-1"/>
			<html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${bankAcAnalysisDetail[0].lbxDealNo}" />
			<input type="hidden" id="dealId" value=""/>
			<!--			<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="closeLovCallonLov1();openLOVCommon(58,'fundFlowAnalysisSearchForm','dealNo','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>-->
	
     </td>
	<td width="17%"><bean:message key="lbl.customerName"/><font color="red">*</font></td>
	<td width="28%">
	<html:text property="customerName" styleClass="text" styleId="customerName" readonly="true"  maxlength="50" value="${bankAcAnalysisDetail[0].customerName}"/>
	</td>
		</tr>
		
		<tr>
		<td colspan="2"><bean:message key="lbl.name"/><font color="red">*</font></td>
		<td colspan="4" nowrap="nowrap">
		<input type="hidden" id="hCommon" value=""/>
		<html:text property="bankName" styleId="bankName" styleClass="text" maxlength="50" value="${bankAcAnalysisDetail[0].bankName}" readonly="true" tabindex="-1"/>
		
   <html:hidden  property="lbxBankID" styleId="lbxBankID"  value="${bankAcAnalysisDetail[0].lbxBankID}"/>
  <!--   <html:button property="bankButton" styleId="bankButton" value=" " onclick="closeLovCallonLov1();openLOVCommon(7,'bankAccountAnalysisForm','bankName','','bankBranch', 'lbxBranchID','','','hCommon')" styleClass="lovbutton"> </html:button>
    -->
     </td>
	<td width="17%"><bean:message key="lbl.bankBranchName"/><font color="red">*</font></td>
	<td width="28%">
	<html:text  styleClass="text" property="bankBranch" styleId="bankBranch" value="${bankAcAnalysisDetail[0].bankBranch}"  maxlength="50" tabindex="-1" readonly="true"/>
   <html:hidden property="lbxBranchID" styleId="lbxBranchID" value="${bankAcAnalysisDetail[0].lbxBranchID}"/>
   
   <!--  <html:button property="bankBrnchButton" styleId="bankBrnchButton" value=" " onclick="closeLovCallonLov('bankName');openLOVCommon(8,'bankAccountAnalysisForm','bankBranch','bankName','lbxBranchID', 'lbxBankID','Please select Bank First!','','hCommon');" styleClass="lovbutton"> </html:button>
   -->
   </td>
		</tr>
	  <tr>
	    <td colspan="2"><bean:message key="lbl.aCType"/><font color="red">*</font> </td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:select property="accountType" styleId="accountType" styleClass="text" value="${bankAcAnalysisDetail[0].accountType}" disabled="true">
	    <html:option value="">Select</html:option>
	    <html:option value="1">CC/OD</html:option>
	    <html:option value="2">Current</html:option>
	    <html:option value="3">Saving</html:option>
	    <html:option value="4">Others</html:option>
	    </html:select>
	    
	    </td>
		<td><bean:message key="lbl.accountNo"/> <font color="red">*</font></td>
		<td nowrap="nowrap" ><html:text  styleClass="text" property="accountNo" styleId="accountNo" value="${bankAcAnalysisDetail[0].accountNo}" maxlength="20" style="text-align: right;" readonly="true" 
		onkeypress="return isNumberKey(event);" /> </td>
	  </tr>
		 <tr>
		   <td><div align="left"><bean:message key="lbl.statementMonthAndYear"/> <font color="red">*</font></div></td>
		   <td>&nbsp;</td>
		   <td  nowrap="nowrap" >
		     
		         <div align="left">
		          <html:select property="month" styleId="month" styleClass="text" styleClass="text" style="width:57px !important; min-width:55px !important;" value="${bankAcAnalysisDetail[0].month}" disabled="true">
		         	<html:option value="1">Jan</html:option>
		         	<html:option value="2">Feb</html:option>
		         	<html:option value="3">Mar</html:option>
		         	<html:option value="4">Apr</html:option>
		         	<html:option value="5">May</html:option>
		         	<html:option value="6">Jun</html:option>
		         	<html:option value="7">Jul</html:option>
		         	<html:option value="8">Aug</html:option>
		         	<html:option value="9">Sep</html:option>
		         	<html:option value="10">Oct</html:option>
		         	<html:option value="11">Nov</html:option>
		         	<html:option value="12">Dec</html:option>
		         </html:select>
		         
		         <html:select property="year" styleId="year" styleClass="text" value="${bankAcAnalysisDetail[0].year}" style="width:90px !important; min-width:85px !important;" disabled="">
<!--		         <html:option value="">--Select--</html:option>-->
		         	<logic:present name="yearList">
						<html:optionsCollection name="yearList"	label="statementMonthAndYear" value="statementMonthAndYear" />
					</logic:present>
		         </html:select>
<!--		           <html:text  styleClass="text" property="statementMonthAndYear" styleId="statementMonthAndYear" value="${bankAcAnalysisDetail[0].statementMonthAndYear}" maxlength="22" />-->
	            </div></td>
		   <td  nowrap="nowrap" >&nbsp;</td>
		   <td  nowrap="nowrap" >&nbsp;</td>
		   <td  nowrap="nowrap" >&nbsp;</td>
		    <td  nowrap="nowrap" ><bean:message key="lbl.totalEMI"/></td>
			 <td  nowrap="nowrap" > <html:text  styleClass="text" property="totalEMI" styleId="totalEMI" value="${bankAcAnalysisDetail[0].totalEMI}" maxlength="22" style="text-align: right;" 
			 onkeypress="return numbersonly(event,id,18)"  onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);" readonly="true" /></td>
		 </tr>
		    <tr>
	    <td colspan="2"><bean:message key="lbl.credit"/><font color="red">*</font> </td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="credit" styleId="credit" value="${bankAcAnalysisDetail[0].credit}" maxlength="22" style="text-align: right;" readonly="true" 
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" /></td>
		<td><bean:message key="lbl.debit"/><font color="red">*</font></td>
		<td nowrap="nowrap" > 
		<html:text  styleClass="text" property="debit" styleId="debit" value="${bankAcAnalysisDetail[0].debit}" maxlength="22" style="text-align: right;" readonly="true" 
		onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
	  </tr>
	   <tr>
	    <td colspan="2"><bean:message key="lbl.highestBalance"/></td>
	    <td colspan="4" nowrap="nowrap" > 
	    <html:text  styleClass="text" property="highestBalance" styleId="highestBalance" value="${bankAcAnalysisDetail[0].highestBalance}" maxlength="22" style="text-align: right;" readonly="true" 
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);" /></td>
		<td><bean:message key="lbl.balance"/></td>
		<td nowrap="nowrap" >
		<html:text  styleClass="text" property="lowestBalance" styleId="lowestBalance" value="${bankAcAnalysisDetail[0].lowestBalance}" maxlength="22" style="text-align: right;"  readonly="true" 
		onkeypress="return numberswithminus(event,id,18)" onblur="formatNumberMinus(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/>        </td>
	  </tr>
	   <tr>
	    <td colspan="2"><bean:message key="lbl.bounceamount"/></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="checkBounceAmount" styleId="checkBounceAmount" value="${bankAcAnalysisDetail[0].checkBounceAmount}" maxlength="22" style="text-align: right;" readonly="true" 
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.checkBouncingFrequency"/></td>
		<td nowrap="nowrap" ><html:text  styleClass="text" property="checkBounceFrequency" styleId="checkBounceFrequency" value="${bankAcAnalysisDetail[0].checkBounceFrequency}" maxlength="3" style="text-align: right;" readonly="true" 
		onkeypress="return isNumberKey(event);" />       </td>
	  </tr>
	   <tr>
	    <td colspan="2"><bean:message key="lbl.overLimitAmount"/></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="overLimitAmount" styleId="overLimitAmount" value="${bankAcAnalysisDetail[0].overLimitAmount}" maxlength="22" style="text-align: right;" readonly="true" 
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
		<td><bean:message key="lbl.overlimitFrequency"/></td>
		<td nowrap="nowrap" ><html:text  styleClass="text" property="overLimitFrequency" styleId="overLimitFrequency" value="${bankAcAnalysisDetail[0].overLimitFrequency}" maxlength="3" style="text-align: right;" readonly="true" 
		onkeypress="return isNumberKey(event);" /> 
		<input type="hidden" id="bankAnalysisID" value="${bankAcAnalysisDetail[0].bankAcAnId}"/> 
		 </td>
	  </tr>
	  <tr>
	    <td colspan="2"><bean:message key="lbl.chequeBouncing"/></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="chequeBouncing" styleId="chequeBouncing" value="${bankAcAnalysisDetail[0].chequeBouncing}" maxlength="3" style="text-align: right;" readonly="true" 
	    onkeypress="return numbersonly(event,id,3)"  onkeyup="checkNumber(this.value, event, 3,id);"	onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		
		<td><bean:message key="lbl.limitObligation"/> <font color="red">*</font></td>
	    <td nowrap="nowrap" >
	    <html:text  styleClass="text" property="limitObligation" styleId="limitObligation" value="${bankAcAnalysisDetail[0].limitObligation}" maxlength="3" style="text-align: right;" readonly="true" 
	    onkeypress="return numbersonly(event,id,3)"  onkeyup="checkNumber(this.value, event, 3,id);"	onfocus="keyUpNumber(this.value, event, 3,id);"/></td>
		</tr>
		
		 <tr>
	    <td colspan="2"><bean:message key="lbl.totalCreditTransaction"/></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:text  styleClass="text" property="totalCreditTxn" styleId="totalCreditTxn" value="${bankAcAnalysisDetail[0].totalCreditTxn}"  style="text-align: right;" readonly="true" 
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/></td>
		<td><bean:message key="lbl.totalDebitTransaction"/></td>
	    <td nowrap="nowrap" >
	    <html:text  styleClass="text" property="totalDebitTxn" styleId="totalDebitTxn" value="${bankAcAnalysisDetail[0].totalDebitTxn}"  style="text-align: right;" readonly="true" 
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event, 18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/></td>
		</tr>
		 <tr>
	    <td colspan="2"><bean:message key="lbl.varificationmethod"/><font color="red">*</font></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:select property="varificationMethod" styleId="varificationMethod" styleClass="text" value="${bankAcAnalysisDetail[0].varificationMethod}" disabled="true">
	    <html:option value="">Select</html:option>
	    <html:optionsCollection name="verifMethodList" label="name" value="id"/>
	    </html:select>
	    </td>
		<td><bean:message key="lbl.balanceAmountFA"/></td>
	    <td nowrap="nowrap" >
	    <html:text  styleClass="text" property="balanceAmount" styleId="balanceAmount" value="${bankAcAnalysisDetail[0].balanceAmount}"  style="text-align: right;" readonly="true" 
	    onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);"  onkeyup="checkNumber(this.value, event,18,id);"	onfocus="keyUpNumber(this.value, event,18,id);"/></td>
		</tr>
		 <tr>
	    <td colspan="2"><bean:message key="lbl.remarks"/><font color="red">*</font></td>
	    <td colspan="4" nowrap="nowrap" >
	    <html:textarea  styleClass="text" property="remarks" styleId="remarks" value="${bankAcAnalysisDetail[0].remarks}" readonly="true"   /></td>
		<td></td>
	    <td nowrap="nowrap" ></td>
		</tr>
	  	  </table>
	  <fieldset>	
		 <legend></legend>  
  
                  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr class="white2">
    	<td >
    	 <b><input type="checkbox" name="allchk" id="allchk" onclick="allChecked();"/><bean:message key="contact.select" /></b>
    	</td>
	    <td ><strong><bean:message key="lbl.name"/> </strong></td>
        <td ><strong><bean:message key="lbl.branchDesc"/> </strong></td>
        <td ><strong><bean:message key="lbl.monthAndYear"/> </strong></td>
		<td ><strong><bean:message key="lbl.credit"/> </strong></td>
		<td ><strong><bean:message key="lbl.debit"/> </strong></td>
		<td ><strong><bean:message key="lbl.highestBalance"/>  </strong></td>
		<td ><strong><bean:message key="lbl.balance"/> </strong></td>
		<td ><strong><bean:message key="lbl.amount"/> </strong></td>
		<td ><strong><bean:message key="lbl.percentage"/> </strong></td>
		<td ><strong><bean:message key="lbl.bounceamount"/> </strong></td>
		<td ><strong><bean:message key="lbl.frequency"/> </strong></td>
		<td ><strong><bean:message key="lbl.limitamount"/> </strong></td>
		<td ><strong><bean:message key="lbl.overlimitFrequency"/></strong></td>
		</tr>
    	<logic:present name="bankAcAnalysisList">
			<logic:iterate id="subBankAcAnalysisList" name="bankAcAnalysisList">
				<tr class="white1">
					<td >
					<input type="checkbox" name="chk" id="chk" value="${subBankAcAnalysisList.bankAcAnId }" disabled="disabled"/>
    	 			</td>
					<td ><a href="#" id="anchor0" onclick="return getBankAcAnalysisIndividual(${subBankAcAnalysisList.bankAcAnId });">${subBankAcAnalysisList.bankName }</a></td>
					<td >${subBankAcAnalysisList.bankBranch }</td>
					<td>${subBankAcAnalysisList.statementMonthAndYear }</td>
					<td>${subBankAcAnalysisList.credit }</td>
					<td>${subBankAcAnalysisList.debit }</td>
					<td>${subBankAcAnalysisList.highestBalance }</td>
					<td>${subBankAcAnalysisList.lowestBalance }</td>
					<td>${subBankAcAnalysisList.swingAmt }</td>
					<td>${subBankAcAnalysisList.swingPercent }</td>
					<td>${subBankAcAnalysisList.checkBounceAmount }</td>
					<td>${subBankAcAnalysisList.checkBounceFrequency }</td>
					<td>${subBankAcAnalysisList.overLimitAmount }</td>
					<td>${subBankAcAnalysisList.overLimitFrequency }</td>
				
				</tr>	
			</logic:iterate>
			<input type="hidden" id="dealId" value="${subBankAcAnalysisList.lbxDealNo }"/>
		</logic:present>	

 </table>
    
    </td>
</tr>
</table>

	</fieldset>
	  
  <tr>
			<td nowrap colspan="10" class="white">
			<logic:present name="financialDealId">
			    <input type="hidden" id="dealId" value="${sessionScope.financialDealId}"/>
		    </logic:present>
		
			</td>
			</tr></td></tr>
		</table>
	</fieldset>	
	
</html:form>
</logic:present>
</div>



</div>
<logic:present name="sms">
 <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert("<bean:message key="msg.DataSaved" />");
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='B')
	{
			alert("<bean:message key="lbl.firstUpdateThenForward" />");
			location.href="individualFinancialBankAccAnalysisAction.do?method=openIndividualFinancialBankAccBehind";    
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
		alert("<bean:message key="lbl.forwardSuccess" />");
		parent.location.href="financialAnalysisSearchBehindAction.do";
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
	}
	
	
	</script>
</logic:present>
<logic:present name="notForward">

	<script type="text/javascript">
	
		alert("<bean:message key="lbl.notForward" />")
		
</script>
</logic:present>
<logic:present name="checkStageM">

	<script type="text/javascript">
	
		alert('${checkStageM}');
		
</script>
</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("bankAccountAnalysisForm");
</script>

</body>
</html:html>
