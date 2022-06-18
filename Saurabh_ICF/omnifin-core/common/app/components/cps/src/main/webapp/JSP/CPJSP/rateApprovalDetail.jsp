 <!--Author Name : Neeraj Kumar Tripathi-->
<!--Date of Creation : 26-Jun-2013-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<html:html>
<head>
	<title><bean:message key="a3s.noida" /></title>
	<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
	</logic:present>
	<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
	</logic:notPresent>
	
	<logic:present name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
	<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
	</logic:notPresent>
	
	
	<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/rateApprovalDeal.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/formatnumber.js"></script>	
	
	
	<%
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
		int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
		request.setAttribute("no",no);
	%>
	<!--[if IE]>
		<style type="text/css">
		.opacity
		{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
		</style>
	<! [endif] -->
</head>

<body onload="enableAnchor();formatValue()">
<html:form action="/rateApprovalChecker" styleId="rateApprovalDetail" method="post" > 	
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<input type="hidden" id="businessdate" value="${userobject.businessdate }" />
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="productCat" id="productCat" value="${requestScope.productCat }" />

<logic:present name="MACHINE">
<input type="hidden" name="assetMachine" id="assetMachine" value="MACHINE" />
</logic:present>

<logic:notPresent name="MACHINE">
<input type="hidden" name="assetMachine" id="assetMachine" value="" />
</logic:notPresent>
<logic:present name="PROPERTY">
<input type="hidden" name="assetProperty" id="assetProperty" value="PROPERTY" />
</logic:present>

<logic:notPresent name="PROPERTY">
<input type="hidden" name="assetProperty" id="assetProperty" value="" />
</logic:notPresent>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
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
	          <td >Rate Approval</td>
          </tr>
        </table> 
</td>
</tr>
</table>


<fieldset><legend><bean:message key="lbl.rateApprovalDetail"/></legend>
	<fieldset><legend><bean:message key="lbl.customerDetail"/></legend>
		<table width="100%">
		<tr>	
			<td><bean:message key="lbl.location"/></td>
			<td><html:textarea  property="location" styleId="location" value="${resultList[0].location}" readonly="true" style="height:100px;width:250px;" styleClass="text"></html:textarea></td>
			<td><bean:message key="lbl.customerName"/></td>
			<td><html:text property="customerName" styleClass="text" styleId="customerName" readonly="true" tabindex="-1" style="height:100px;width:250px;" value="${resultList[0].customerName}"/></td>
		</tr>
		<tr>	
			<td><bean:message key="lbl.customerSegment"/></td>
			<td><html:text property="custConstitution" styleClass="text" styleId="custConstitution" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].custConstitution}"/></td>
			<td><bean:message key="lbl.psl"/></td>
			<td><html:text property="psl" styleClass="text" styleId="psl" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].psl}"/></td>
		</tr>
		</table>	
	</fieldset>
	<fieldset><legend><bean:message key="lbl.loanDetail"/></legend>
	<table width="100%">
	<tr>
		<html:hidden property="dealId" styleId="dealId"  value="${resultList[0].dealId}"/>
		<td><bean:message key="lbl.tradDealNo"/></td>
		<td><html:text property="dealNo" styleClass="text" styleId="dealNo" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].dealNo}"/></td>
		<td><bean:message key="grd.applicationNo"/></td>
		<td><html:text property="formNo" styleClass="text" styleId="formNo" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].formNo}"/></td>
	</tr>
	<tr>	
		<td><bean:message key="product.desc"/></td>
		<td><html:text property="product" styleClass="text" styleId="product" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].product}"/></td>
		<td><bean:message key="lbl.underwritingScheme"/></td>
		<td><html:text property="scheme" styleClass="text" styleId="scheme" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].scheme}"/></td>
	</tr>
	<tr>	
		<td><bean:message key="lbl.loanAmount"/></td>
		<td><html:text property="loanAmt" styleClass="text" styleId="loanAmt" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].loanAmt}"/></td>
		<td><bean:message key="lbl.effRate"/></td>
		<td><html:text property="effRate" styleClass="text" styleId="effRate" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].effRate}"/></td>
	</tr>
	<tr>	
		<td><bean:message key="lbl.flatRate"/></td>
		<td><html:text property="flatRate" styleClass="text" styleId="flatRate" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].flatRate}"/></td>
		<td><bean:message key="lbl.roiType"/></td>
		<td><html:text property="roiType" styleClass="text" styleId="roiType" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].roiType}"/></td>
	</tr>
	<tr>	
		<td><bean:message key="lbl.processingFeePer"/></td>
		<td><html:text property="processFeePer" styleClass="text" styleId="processFeePer" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].processFeePer}"/></td>
		<td><bean:message key="lbl.processingFeeAmt"/></td>
		<td><html:text property="processFeeAmt" styleClass="text" styleId="processFeeAmt" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].processFeeAmt}"/></td>
	</tr>
	<tr>	
		<td><bean:message key="lbl.taxOnprocessingFee"/></td>
		<td><html:text property="taxOnProcessFee" styleClass="text" styleId="taxOnProcessFee" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].taxOnProcessFee}"/></td>
		<td><bean:message key="lbl.totalProcessFee"/></td>
		<td><html:text property="totalProcessFee" styleClass="text" styleId="totalProcessFee" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].totalProcessFee}"/></td>
	</tr>
	<logic:present name="ELCATEGORY">
	<tr>	
		<td><bean:message key="lbl.tenureMon"/></td>
		<td><html:text property="dealTenure" styleClass="text" styleId="dealTenure" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].dealTenure}"/></td>
		
		<td><bean:message key="lbl.mmdIcdAmtper"/></td>
		<td><html:text property="mmdIcdPer" styleClass="text" styleId="mmdIcdPer" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].mmdIcdPer}"/></td>
	</tr>
	
	<tr>	
		<td><bean:message key="lbl.mmdIcdAmt"/></td>
		<td><html:text property="mmdIcdAmt" styleClass="text" styleId="mmdIcdAmt" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].mmdIcdAmt}"/></td>
		<td><bean:message key="lbl.IntOnMmdIcd"/></td>
		<td><html:text property="interestOnMmdIcd" styleClass="text" styleId="interestOnMmdIcd" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].interestOnMmdIcd}"/></td>
	</tr>
	<tr>	
		<td><bean:message key="lbl.NetLoanAmount"/></td>
		<td><html:text property="netLoanAmt" styleClass="text" styleId="netLoanAmt" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].netLoanAmt}"/></td>
		<td><bean:message key="lbl.netAccIrr"/></td>
		<td><html:text property="netAccIRR" styleClass="text" styleId="netAccIRR" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].netAccIRR}"/></td>
	</tr>
	<tr>	
		<td><bean:message key="lbl.grossAccIrr"/></td>
		<td><html:text property="grossAccIRR" styleClass="text" styleId="grossAccIRR" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].grossAccIRR}"/></td>
	</tr>
	</logic:present>
	<logic:notPresent name="ELCATEGORY">
	<tr>	
		<td><bean:message key="lbl.tenureMon"/></td>
		<td><html:text property="dealTenure" styleClass="text" styleId="dealTenure" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].dealTenure}"/></td>
		<td><bean:message key="lbl.isThisABalanceTransfer" /><span><font color="red">*</font></span></td>
		<logic:notPresent name="author">
		<td>
		<html:select property="isThisABalanceTransfer" styleId="isThisABalanceTransfer" styleClass="text" style="width:250px;" value="${resultList[0].isThisABalanceTransfer}" onchange="disableFieldForIsBalanceTransfer();" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="Y">YES</html:option>
			<html:option value="N">NO</html:option>
		</html:select> 
		</td>
		</logic:notPresent> 
		
		
		<logic:present name="author">
		<td>
		<html:select property="isThisABalanceTransfer" styleId="isThisABalanceTransfer" disabled="true" styleClass="text" style="width:250px;" value="${resultList[0].isThisABalanceTransfer}" onchange="disableFieldForIsBalanceTransfer();" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="Y">YES</html:option>
			<html:option value="N">NO</html:option>
		</html:select> 
		</td>
		</logic:present> 
	</tr>
	<tr>
		
		<td><bean:message key="lbl.ifBt"/></td>
		<td>

		<html:text property="ifBt" styleClass="text" styleId="ifBt"  readonly="true" style="width:250px;" value="${resultList[0].ifBt}" onkeypress="return numbersonly(event, id, 3)"  onkeyup="checkNumber(this.value, event, 3, id);"   onchange="checkRateForRateApproval();"/>

		</td>			
	</tr>
	</logic:notPresent>
	</table>
	
	</fieldset>
	
	<fieldset><legend><bean:message key="lbl.otherCharges"/></legend>
	<table width="100%">
	<tr>	
		<td><bean:message key="lbl.legalCharges"/></td>
		<td><html:text property="legalCharges" styleClass="text" styleId="legalCharges" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].legalCharges}"/></td>
		<td><bean:message key="lbl.totalLeagalCharges"/></td>
		<td><html:text property="totalLeagalCharges" styleClass="text" styleId="totalLeagalCharges" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].totalLeagalCharges}"/></td>
	</tr>
	<tr>	
		<td><bean:message key="lbl.rocCharges"/></td>
		<td><html:text property="rocCharges" styleClass="text" styleId="rocCharges" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].rocCharges}"/></td>
		<td><bean:message key="lbl.totalRocCharges"/></td>
		<td><html:text property="totalRocCharges" styleClass="text" styleId="totalRocCharges" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].totalRocCharges}"/></td>
	</tr>
	<tr>	
		<td><bean:message key="lbl.valuationCharges"/></td>
		<td><html:text property="valuationCharges" styleClass="text" styleId="valuationCharges" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].valuationCharges}"/></td>
		<td><bean:message key="lbl.totalValuationCharges"/></td>
		<td><html:text property="totalValuationCharges" styleClass="text" styleId="totalValuationCharges" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].totalValuationCharges}"/></td>
	</tr>
	<logic:present name="MACHINE">
	<tr>	
		<td><bean:message key="lbl.stampCharges"/></td>
		<td><html:text property="stampCharges" styleClass="text" styleId="stampCharges" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].stampCharges}"/></td>
		<td><bean:message key="lbl.totalStampCharges"/></td>
		<td><html:text property="totalStampCharges" styleClass="text" styleId="totalStampCharges" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].totalStampCharges}"/></td>
	</tr>
	</logic:present>
	<tr>	
		<td><bean:message key="lbl.otherCharges"/></td>
		<td><html:text property="otherCharges" styleClass="text" styleId="otherCharges" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].otherCharges}"/></td>
		<td><bean:message key="lbl.totalOtherCharges"/></td>
		<td><html:text property="totalOtherCharges" styleClass="text" styleId="totalOtherCharges" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].totalOtherCharges}"/></td>
	</tr>

	</table>
	
	</fieldset>
	<logic:present name="MACHINE">
	<fieldset><legend><bean:message key="lbl.asscolDetails"/></legend>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td class="gridtd">    
	  <table width="100%" border="0" cellspacing="1"  id="dtable" cellpadding="1">
	  <tr class="white2">        
		  <td><b><bean:message key="lbl.asscollTypes"/></b></td>
		  <td><b><bean:message key="lbl.Assets/Collateral"/></b></td>
	   	  <td><b><bean:message key="lbl.collateralDescription"/></b></td>
	      <td><b><bean:message key="lbl.collateralValue"/></b></td>
	  </tr>
	  <logic:present name="showCollateralDetails">
	  <%int i=1; %> 
	  <logic:iterate id="showCollateralDetails1" name="showCollateralDetails" >		                
	  <tr class="white1"> 	             
		  <td>${showCollateralDetails1.colltype2 }<input type="hidden" name="collType" value="${showCollateralDetails1.colltype2 }" /></td>
		  <td><a href="#" id="collupdate" onclick="collateralUpdate('${showCollateralDetails1.colltype1 }','${showCollateralDetails1.colltype2 }','${showCollateralDetails1.assetsId }');"/>${showCollateralDetails1.colltype1 }</td>	                      
		  <td>${showCollateralDetails1.assetsCollateralDesc }</td>
		  <td>${showCollateralDetails1.assetsCollateralValue }</td>	               
		  <% i++;%>
	  </tr>	 
	  </logic:iterate>
	  </logic:present>      
	  </table>
	  </td>
	 </tr>
	 </table>
	 <table width="100%">
	 <tr>
	 	<td><bean:message key="lbl.ltvPercentageOnAsset"/></td>
		<td><html:text property="ltvPercentageOnAsset" styleClass="text" styleId="ltvPercentageOnAsset" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].ltvPercentageOnAsset}"/></td>
		<td><bean:message key="lbl.ltvPercentageOnCombined"/></td>
		<td><html:text property="ltvPercentageOnCombined" styleClass="text" styleId="ltvPercentageOnCombined" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].ltvPercentageOnCombined}"/></td>
	 </tr>
	 <tr>
		<td><bean:message key="lbl.netLtvPercentageOnAsset"/></td>
		<td><html:text property="netLtvPercentageOnAsset" styleClass="text" styleId="netLtvPercentageOnAsset" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].netLtvPercentageOnAsset}"/></td>
		<td><bean:message key="lbl.netLtvPercentageOnCombined"/></td>
		<td><html:text property="netLtvPercentageOnCombined" styleClass="text" styleId="netLtvPercentageOnCombined" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].netLtvPercentageOnCombined}"/></td>
	 </tr>           				
	 </table>
	</fieldset>
	</logic:present>
	<logic:present name="PROPERTY">
	<fieldset><legend><bean:message key="lbl.asscolDetails"/></legend>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td class="gridtd">    
	  <table width="100%" border="0" cellspacing="1"  id="dtable" cellpadding="1">
	  <tr class="white2">        
		  <td><b><bean:message key="lbl.asscollTypes"/></b></td>
		  <td><b><bean:message key="lbl.Assets/Collateral"/></b></td>
	   	  <td><b><bean:message key="lbl.collateralDescription"/></b></td>
	      <td><b><bean:message key="lbl.collateralValue"/></b></td>
	  </tr>
	  <logic:present name="showCollateralDetails">
	  <%int i=1; %> 
	  <logic:iterate id="showCollateralDetails1" name="showCollateralDetails" >		                
	  <tr class="white1"> 	             
		  <td>${showCollateralDetails1.colltype2 }<input type="hidden" name="collType" value="${showCollateralDetails1.colltype2 }" /></td>
		  <td><a href="#" id="collupdate" onclick="collateralUpdate('${showCollateralDetails1.colltype1 }','${showCollateralDetails1.colltype2 }','${showCollateralDetails1.assetsId }');"/>${showCollateralDetails1.colltype1 }</td>	                      
		  <td>${showCollateralDetails1.assetsCollateralDesc }</td>
		  <td>${showCollateralDetails1.assetsCollateralValue }</td>	               
		  <% i++;%>
	  </tr>	 
	  </logic:iterate>
	  </logic:present>      
	  </table>
	  </td>
	 </tr>            				
	 </table>
	</fieldset>
	<fieldset><legend><bean:message key="lbl.sourceDetails"/></legend>
	<table width="100%">
	<tr>	
		<td><bean:message key="lbl.channelType"/></td>
		<td><html:text  property="channelType" styleId="channelType" value="${resultList[0].channelType}" readonly="true" style="width:250px;" styleClass="text"></html:text></td>
		<td><bean:message key="lbl.channelName"/></td>
		<td><html:text property="channelName" styleClass="text" styleId="channelName" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].channelName}"/></td>
	</tr>
	<logic:present name="author">
	<tr>
		<td><bean:message key="lbl.payoutApplication"/><span><font color="red">*</font></span></td>
		<td><html:text property="payoutApplication"  style="width:250px;" styleId="payoutApplication"  readonly="true" value="${resultList[0].payoutApplication}"  styleClass="text"  onblur="formatNumber(this.value,id);"/></td>
		<td><bean:message key="lbl.payoutBeing"/><span><font color="red">*</font></span></td>
		<td><html:text property="payoutBeing"  style="width:250px;" styleId="payoutBeing"  readonly="true" value="${resultList[0].payoutBeing}"  styleClass="text"  onblur="formatNumber(this.value,id);"/></td>
	</tr>
	</logic:present>
	<logic:notPresent name="author">
	<tr>
		<td><bean:message key="lbl.payoutApplication"/><span><font color="red">*</font></span></td>
		<td><html:text property="payoutApplication"  style="width:250px;" styleId="payoutApplication"  value="" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)"  styleClass="text"  /></td>
		<td><bean:message key="lbl.payoutBeing"/><span><font color="red">*</font></span></td>
		<td><html:text property="payoutBeing"  style="width:250px;" styleId="payoutBeing"   value=""  onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
	</tr>
	</logic:notPresent>
	</table>
	</fieldset>
	</logic:present>
	<logic:present name="MACHINE">
	<fieldset><legend><bean:message key="lbl.subvention"/></legend>

	<table width="100%">
	<tr>	
		<td><bean:message key="lbl.dealerSubvention"/></td>
		<td><html:text property="dealerSubvention" styleClass="text" styleId="dealerSubvention" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].dealerSubvention}"/></td>
		<td><bean:message key="lbl.manufactureSubvention"/></td>
		<td><html:text property="manufactureSubvention" styleClass="text" styleId="manufactureSubvention" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].manufactureSubvention}"/></td>
	</tr>
	</table>
	</fieldset>
	</logic:present>
	<fieldset>
	<table width="100%">
	<tr>
		<td><bean:message key="lbl.rackProcessingFee"/></td>
		<td><html:text property="rackProcessingFee" styleClass="text" styleId="rackProcessingFee" readonly="true" style="width:250px;" tabindex="-1" value="${resultList[0].rackProcessingFee}"/></td>	
		<td><bean:message key="lbl.rackRate"/></td>
		<td><html:text property="rackRate" styleClass="text" styleId="rackRate" readonly="true" tabindex="-1" style="width:250px;" value="${resultList[0].rackRate}"/></td>
	</tr>
	</table>
	</fieldset>	
	<logic:notPresent name="author">
	<fieldset><legend><bean:message key="lbl.crossSellLi"/></legend>
	<table width="100%">
	<tr>	
		<td><bean:message key="lbl.creditInsuranceCover" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:select property="creditInsuranceCover" styleId="creditInsuranceCover" styleClass="text" value="${resultList[0].creditInsuranceCover}" onchange="disableFieldForLifeInsurance();" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="Y">YES</html:option>
			<html:option value="N">NO</html:option>
		</html:select> 
		</td> 
		
		<td><bean:message key="lbl.typeOfCoverage" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:select property="typeOfCoverage" styleId="typeOfCoverage" styleClass="text" value="${resultList[0].typeOfCoverage}" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="SCF">Single Cover Full</html:option>
			<html:option value="SCP">Single Cover Partial</html:option>
			<html:option value="JCF">Joint Cover Full</html:option>
			<html:option value="JCP">Joint Cover Partial</html:option>
			<html:option value="NA">Not Applicable</html:option>
		</html:select> 
		</td>  
	</tr>
	<tr style="">	
		<td><bean:message key="lbl.dateOfBirth" /><span><font color="red">*</font></span></td>
		<td >
		<div id="dataBirth" >
		<html:text property="dateOfBirth" styleId="dateOfBirth" value="${resultList[0].dateOfBirth}" readonly="true" styleClass="text" onchange="checkDate('dateOfBirth');" />
		</div>
		<div id="newDataBirth" style="display:none">
		<html:text property="dateOfBirth"  value="${resultList[0].dateOfBirth}" readonly="true" styleClass="text"  />
		</div>
		</td>
		
		<td><bean:message key="lbl.sumAsurLoanAmt"/><span><font color="red">*</font></span></td>
		<td><html:text property="sumAsurLoanAmt"  style="text-align: right" styleId="sumAsurLoanAmt" maxlength="18" onchange="checkRate();"  value="${resultList[0].sumAsurLoanAmt}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
	</tr>
	<tr>	
		
		<td><bean:message key="lbl.creditInsuranceTenor"/><span><font color="red">*</font></span></td>
		<td><html:text property="creditInsuranceTenor" styleClass="text" styleId="creditInsuranceTenor" maxlength="5" onkeypress="return isNumberKey(event);"  value="${resultList[0].creditInsuranceTenor}" /></td>
		<td><bean:message key="lbl.csliPremiumAmt" /><span><font color="red">*</font></span></td>
		<td><html:text property="csliPremiumAmt"  style="text-align: right" styleId="csliPremiumAmt" maxlength="18" value="${resultList[0].csliPremiumAmt}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
	</tr>
	</table>
	</fieldset>
	<fieldset><legend><bean:message key="lbl.crossSellGi"/></legend>
	<table width="100%">
	<tr>
		<td><bean:message key="lbl.generalInsurance"/><span><font color="red">*</font></span></td>
		<td>
		<html:select property="generalInsurance" styleId="generalInsurance" styleClass="text" value="${resultList[0].generalInsurance}" onchange="disableFieldForGeneralInsurance();" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="Y">YES</html:option>
			<html:option value="N">NO</html:option>
		</html:select> 
		</td>  	
		<td><bean:message key="lbl.csliPremiumAmt"/><span><font color="red">*</font></span></td>
		<td><html:text property="csgiPremiumAmt"  style="text-align: right" styleId="csgiPremiumAmt" maxlength="18" value="${resultList[0].csgiPremiumAmt}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
	</tr>

	</table>
	</fieldset>
	<fieldset><legend><bean:message key="lbl.crossSellOi"/></legend>
	<table width="100%">
	<tr>
		<td><bean:message key="lbl.otherInsuranceSold"/><span><font color="red">*</font></span></td>
		<td>
		<html:select property="otherInsuranceSold" styleId="otherInsuranceSold" styleClass="text" onchange="productReadOnly();" value="${resultList[0].otherInsuranceSold}" onchange="disableFieldForOtherInsurance();" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="Y">YES</html:option>
			<html:option value="N">NO</html:option>
		</html:select> 
		
		</td> 
		<td><bean:message key="lbl.productSold"/></td>
		<td><html:text property="productSold" styleClass="text" styleId="productSold" maxlength="50" value="${resultList[0].productSold}" onkeypress="return numbersonly(event,id,18)" /></td>
	</tr>
	<tr>
		<td><bean:message key="lbl.csliPremiumAmt"/><span><font color="red">*</font></span></td>
		<td><html:text property="csoiPremiumAmt"  style="text-align: right" styleId="csoiPremiumAmt" maxlength="18" value="${resultList[0].csoiPremiumAmt}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
		<logic:present name="MACHINE">
		<td><bean:message key="lbl.transactionFee"/><span><font color="red">*</font></span></td>
		<td><html:text property="transactionFee"  style="text-align: right" styleId="transactionFee" maxlength="18" value="${resultList[0].transactionFee}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
		</logic:present>
	</tr>
	</table>
	</fieldset>
	</logic:notPresent>
	
	<logic:present name="author">
	<fieldset><legend><bean:message key="lbl.crossSellLi"/></legend>
	<table width="100%">
	<tr>	
		<td><bean:message key="lbl.creditInsuranceCover" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:select property="creditInsuranceCover" styleId="creditInsuranceCover" styleClass="text" disabled="true" tabindex="-1"  value="${resultList[0].creditInsuranceCover}" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="Y">YES</html:option>
			<html:option value="N">NO</html:option>
		</html:select> 
		</td> 
		
		<td><bean:message key="lbl.typeOfCoverage" /><span><font color="red">*</font></span></td>
		
		<td>
		<html:select property="typeOfCoverage" styleId="typeOfCoverage" styleClass="text"  disabled="true" tabindex="-1" value="${resultList[0].typeOfCoverage}" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="SCF">Single Cover Full</html:option>
			<html:option value="SCP">Single Cover Partial</html:option>
			<html:option value="JCF">Joint Cover Full</html:option>
			<html:option value="JCP">Joint Cover Partial</html:option>
			<html:option value="NA">Not Applicable</html:option>
		</html:select> 
		</td>  
	</tr>
	<tr>	
		<td><bean:message key="lbl.dateOfBirth" /><span><font color="red">*</font></span></td>
		<td ><html:text property="dateOfBirth"  value="${resultList[0].dateOfBirth}" readonly="true" tabindex="-1" styleClass="text" onchange="checkDate('dateOfBirth');" /></td>
		
		<td><bean:message key="lbl.sumAsurLoanAmt"/><span><font color="red">*</font></span></td>
		<td><html:text property="sumAsurLoanAmt"  style="text-align: right" styleId="sumAsurLoanAmt" readonly="true" tabindex="-1"  maxlength="18" onchange="checkRate();" onkeypress="return numbersonly(event,id,18)" value="${resultList[0].sumAsurLoanAmt}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
	</tr>
	<tr>	
		
		<td><bean:message key="lbl.creditInsuranceTenor"/><span><font color="red">*</font></span></td>
		<td><html:text property="creditInsuranceTenor" styleClass="text" styleId="creditInsuranceTenor" readonly="true" tabindex="-1"  maxlength="5" onkeypress="return isNumberKey(event);"  value="${resultList[0].creditInsuranceTenor}" /></td>
		<td><bean:message key="lbl.csliPremiumAmt" /><span><font color="red">*</font></span></td>
		<td><html:text property="csliPremiumAmt"  style="text-align: right" styleId="csliPremiumAmt" readonly="true" tabindex="-1"  maxlength="18" value="${resultList[0].csliPremiumAmt}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
	</tr>
	</table>
	</fieldset>
	<fieldset><legend><bean:message key="lbl.crossSellGi"/></legend>
	<table width="100%">
	<tr>
		<td><bean:message key="lbl.generalInsurance"/><span><font color="red">*</font></span></td>
		<td>
		<html:select property="generalInsurance" styleId="generalInsurance" styleClass="text"  disabled="true" tabindex="-1" value="${resultList[0].generalInsurance}" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="Y">YES</html:option>
			<html:option value="N">NO</html:option>
		</html:select> 
		</td>  	
		<td><bean:message key="lbl.csliPremiumAmt"/><span><font color="red">*</font></span></td>
		<td><html:text property="csgiPremiumAmt"  style="text-align: right" styleId="csgiPremiumAmt" maxlength="18" readonly="true" tabindex="-1" value="${resultList[0].csgiPremiumAmt}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
	</tr>

	</table>
	</fieldset>
	<fieldset><legend><bean:message key="lbl.crossSellOi"/></legend>
	<table width="100%">
	<tr>
		<td><bean:message key="lbl.otherInsuranceSold"/><span><font color="red">*</font></span></td>
		<td>
		<html:select property="otherInsuranceSold" styleId="otherInsuranceSold" styleClass="text"  disabled="true" tabindex="-1" onchange="productReadOnly();" value="${resultList[0].otherInsuranceSold}" >
			<html:option value="">--------Select--------</html:option>
			<html:option value="Y">YES</html:option>
			<html:option value="N">NO</html:option>
		</html:select> 
		
		</td> 
		<td><bean:message key="lbl.productSold"/></td>
		<td><html:text property="productSold" styleClass="text" styleId="productSold" maxlength="50"  readonly="true" tabindex="-1" value="${resultList[0].productSold}" /></td>
	</tr>
	<tr>
		<td><bean:message key="lbl.csliPremiumAmt"/><span><font color="red">*</font></span></td>
		<td><html:text property="csoiPremiumAmt"  style="text-align: right" styleId="csoiPremiumAmt" maxlength="18" readonly="true" tabindex="-1" value="${resultList[0].csoiPremiumAmt}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
		<logic:present name="MACHINE">
		<td><bean:message key="lbl.transactionFee"/><span><font color="red">*</font></span></td>
		<td><html:text property="transactionFee"  style="text-align: right" styleId="transactionFee" maxlength="18" readonly="true" tabindex="-1" value="${resultList[0].transactionFee}" onfocus="keyUpNumber(this.value, event,18,id);"  onkeyup="checkNumber(this.value, event,18,id);" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,18)" styleClass="text"  /></td>
		</logic:present>
	</tr>
	</table>
	</fieldset>
	
	</logic:present>
	
	
	
	<logic:notPresent name="author">	
	<table width="100%">
	<tr>	
		<td><bean:message key="lbl.approveBy"/><span><font color="red">*</font></span>&nbsp;&nbsp;&nbsp;		
			<html:text property="userName" styleClass="text" styleId="userName" readonly="true" tabindex="-1"/>
			<html:hidden property="appUserId" styleId="appUserId"  value=""/>
			<html:hidden property="loginUserName" styleId="loginUserName"  value="${loginUserName}"/>
			<html:hidden property="loginUserId" styleId="loginUserId"  value="${loginUserId}"/>

		 	
			<html:button property="userButton" styleId="userButton" onclick="openLOVCommon(2020,'rateApprovalDetail','userName','loginUserId','', 'loginUserId','','','appUserId')" value=" " styleClass="lovbutton"></html:button>&nbsp;&nbsp;&nbsp;
		    <button type="button" name="button" id="saveButton" title="Alt+V" accesskey="V" class="topformbutton3" onclick="saveRateApprovalData();"><bean:message key="button.save&forward" /></button>
	    </td>
	</tr>
	</table>
 </logic:notPresent>	
	
</fieldset>

</html:form>

<logic:present name="SMS">
<script type="text/javascript">

    
		if('<%=request.getAttribute("SMS").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		location.href="<%=request.getContextPath()%>/rateApprovalMaker.do?method=defaultRateApproval&source=D";

	}
	
	else if('<%=request.getAttribute("SMS").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");	
	}
	
</script>
</logic:present>
  </body>
		</html:html> 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
