<!-- 
Author Name :- Manisha Tomar
Date of Creation :15-04-2011
Purpose :-  screen for the Loan Detail
-->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
response.setHeader("Pragma","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Expires","0");
response.setDateHeader("Expires",0);

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>
<script type="text/javascript">
 window.history.forward(1);
</script>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
	<!-- Rohit Chnages for security -->
	<META HTTP-EQUIV="Cache-Control" content="no-cache, no-store, must-revalidate"> <!-- HTTP 1.1 -->
        <META HTTP-EQUIV="Pragma" content="no-cache, no-store, must-revalidate">        <!-- HTTP 1.0 -->
        <META HTTP-EQUIV="Expires" CONTENT="0"> 
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<!-- Rohit Changes for security end -->
		
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/loanInitiation.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/loanDetails.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<!--jquery and js for number formatting -->
  	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.emicalculator.min.js"></script>
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/globalize.min.js"></script>
    <!-- jquery and js for number formatting -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript">
	   
	   function changePlanRepayment()
	   {
	   		document.getElementById("dateChange").value='Y';	
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


<%--<body  onload="enableAnchor();getKey(this);"> --%>


<body  onload="setMaturityTemp();editNoInstal();showInterestDueDate(false);businessbutton();getFinalRateInCMOnLoad();calculateFinalRate();enableAnchor();hideField();enableInstallNo();checkChanges('sourcingForm');document.getElementById('dealButton').focus();" onload="enableAnchor();getKey(this);" onunload="closeAllLovCallUnloadBody();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
	<input type="hidden" name="OldMaturityDate1" id="OldMaturityDate1" value="" /> <%-- adde by brijesh pathak --%>
	<input type="hidden" id="businessdate" name="businessdate" value="${userobject.businessdate }"/>
<html:hidden property="basePath" styleId="basePath" value="<%=request.getContextPath()%>" />
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" id="diffDayCount" name="diffDayCount" value="${diffDayCount }"/>
<div id=revisedcontainer>
<logic:notPresent name="cmAuthor">
	<html:form action="/loanDetailCMProcessAction.do?method=updateLoanDetails" styleId="sourcingForm" method="post">
    <!-- html:javascript formName="LoanDetailsCMDynaValidatorForm" /-->
	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<html:hidden property="loanBranch" styleId="loanBranch" value="" />
	<html:hidden property="loanIndustry" styleId="loanIndustry" value="" />
	<html:hidden property="loanSubIndustry" styleId="loanSubIndustry" value="" />
	<html:hidden property="loanCustomerId" styleId="loanCustomerId" value="" />
	<html:hidden property="loanCustomerExisting" styleId="loanCustomerExisting" value="" />
	<html:hidden property="loanApprovalDate" styleId="loanApprovalDate" value="" />
	<html:hidden property="loanCustomerType" styleId="loanCustomerType" value="" />
	<html:hidden property="productType" styleId="productType"   />
	<html:hidden property="productCat" styleId="productCat"   />
	<input type="hidden" name="loanDealId" id="loanDealId"   value="${loanList[0].loanDealId}"/>
	<input type="hidden" name="sanctionedDate" id="sanctionedDate"   />
	<input type="hidden" name="dealIrr1" id="dealIrr1"   /> 
	 <input type="hidden" name="dealIrr2" id="dealIrr2"  value="${loanList[0].dealIrr2 }"/>
	<input type="hidden" name="dealEffectiveRate" id="dealEffectiveRate"   />
	<input type="hidden" name="dealFlatRate" id="dealFlatRate"   />
	<input type="hidden" name="oneDealOneLoan" id="oneDealOneLoan"  value="${loanList[0].oneDealOneLoan}" />
	<html:hidden property="repaymentType" styleClass="text" styleId="repaymentType"  value="${loanList[0].repaymentType }" />
	<input type="hidden" name="plan" id="plan"  value="<%=session.getAttribute("plan") %>" />
	<input type="hidden" name="repayment" id="repayment"  value="<%=session.getAttribute("repayment") %>" />
	<input type="hidden" id="defaultRepayDate" value="${dateList[0].repayEffectiveDate}"/>
	<input type="hidden" id="defaultNextDate" value="${dateList[0].nextDueDate}"/>
	
	
	<input type="hidden" id="minFlatRate" value="${loanList[0].minFlatRate}"/>
	<input type="hidden" id="maxFlatRate" value="${loanList[0].maxFlatRate}"/>
	<input type="hidden" id="minEffRate" value="${loanList[0].minEffRate}"/>
	<input type="hidden" id="maxEffRate" value="${loanList[0].maxEffRate}"/>
	<input type="hidden" id="minTenure" value="${loanList[0].minTenure}"/>
	<input type="hidden" id="maxTenure" value="${loanList[0].maxTenure}"/>
	<input type="hidden" id="assetFlag" value="${loanList[0].assetFlag}"/>
	<html:hidden  property="dateChange" styleId="dateChange" value="N"/>
	<input type="hidden" id="validteFormNo" value="Y"/>
	
	
	
		
	
	<input type="hidden" id="checkNewRepay" name="checkNewRepay" value="<%=path %>"/>

<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
			
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	   <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>	
  <fieldset>	  
<legend><bean:message key="lbl.dealParameters"/></legend>

<table cellSpacing="0" cellPadding="0" width="100%" border="0" >
  <Tbody>
  <tr>
    <td>
      
      <table cellSpacing="1" cellPadding="1" width="100%" border="0" >
       
       <tr>
       <logic:notPresent name="loanList">
       	<td><bean:message key="lbl.dealNo"/><font color="red">*</font></td>
       	<td>
       	<input type="hidden" name="hcommon" id="hcommon"/>
       	 <html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" value="${loanList[0].dealNo }" tabindex="-1"/>
		 <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${loanList[0].lbxDealNo }"/>
         <html:button property="dealButton" styleId="dealButton" onclick="closeLovCallonLov1();openLOVCommon(69,'sourcingForm','dealNo','','', '','','checkDealSanctionVaildTill','customerName')" styleClass="lovbutton" value=" "/> 	  
	     </td>
	      </logic:notPresent>
	     <logic:present name="loanList">
	     	<input type="hidden" name="dealButton" id="dealButton" value=""/>
	     </logic:present> 
	      <logic:present name="loanList">
       	<td><bean:message key="lbl.dealNo"/><font color="red">*</font></td>
       	<td>
       	<input type="hidden" name="hcommon" id="hcommon"/>
       	 <html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" readonly="true" value="${loanList[0].dealNo }" tabindex="-1"/>
		 <html:hidden  property="lbxDealNo" styleId="lbxDealNo" value="${loanList[0].lbxDealNo }"/> 	  
	     </td>
	      </logic:present>
	      
	        <td><bean:message key="lbl.customerName" /></td>
			<td >
			 <input type="text" name="customerName" class="text" id="customerName" readonly="readonly" value="${loanList[0].customerName }" tabindex="-1"/>
	   		</td>
	   </tr>
	   <tr>
	   	<td><bean:message key="lbl.product" /><font color="red">*</font></td>
			<td >
			 <input type="text" name="showProduct" class="text" id="showProduct" readonly="readonly" value="${loanList[0].showProduct }" tabindex="-1"/>
	         <html:hidden styleClass="text" property="product" styleId="product" value="${loanList[0].product }"/>
	   <logic:notPresent name="loanList">
	   <html:button property="pbutton" styleId="pbutton" onclick="productPopUp();" value=" " styleClass="lovbutton"></html:button>
	  <%--<img SRC="<%=request.getContextPath() %>/images/lov.gif"  onclick="productPopUp();"  /> --%>	  
	   </logic:notPresent>   
	  
	 
       <html:hidden property="loanId" styleId="loanId" value="${loanList[0].loanId }"/>
       
	   </td>
	   <logic:present name="loanId">
	    <td><bean:message key="lbl.scheme" />  </td>
		 <td> 
		   <input type="text" name="showScheme" class="text" id="showScheme" readonly="readonly" value="${loanList[0].showScheme }" tabindex="-1"/>
	        <html:hidden styleClass="text" property="scheme" styleId="scheme"   value="${loanList[0].scheme }" />
	      </td>
	   </logic:present>
       
         <logic:notPresent name="loanId">  
		 <td><bean:message key="lbl.scheme" />  </td>
		 <td> 
		   <input type="text" name="showScheme" class="text" id="showScheme" readonly="readonly" value="${loanList[0].showScheme }" tabindex="-1"/>
	        <html:hidden styleClass="text" property="scheme" styleId="scheme"   value="${loanList[0].scheme }" /> </td>
	     
	    </logic:notPresent>
	    </tr>
	    <tr>
	    
	     <td><bean:message key="lbl.sanctionedLoanAmount" /></td>
			<td> 
	    <html:text styleClass="text" property="sanctionedLoanAmount" styleId="sanctionedLoanAmount" style="text-align: right;"   readonly="true" value="${loanList[0].sanctionedLoanAmount }" tabindex="-1"/> </td>
	     <td><bean:message key="lbl.utilizeLoanAmount" /></td>
			<td> 
	    <html:text styleClass="text" property="utilizeLoanAmount" styleId="utilizeLoanAmount" style="text-align: right"  readonly="true" value="${loanList[0].utilizeLoanAmount }" tabindex="-1"/> </td>
	       
	    </tr>
        <tr>
         <td><bean:message key="lbl.sanctionedDate" /></td>
		       <td>
		       <html:text styleClass="text" property="sanctionDate" styleId="sanctionDate" readonly="true" value="${loanList[0].sanctionDate }"/>
		       </td>
          <td> <bean:message key="lbl.sanctionedValidTill" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/> </td>
          <td>
          <html:text styleClass="text" property="sanctionedValidtill" styleId="sanctionedValidtill"   maxlength="10" readonly="true"  value="${loanList[0].sanctionedValidtill }" tabindex="-1"/>
              </td>
              </tr>
          <tr>
          <logic:present name="loanList">
		    <logic:iterate name="loanList"  id="subloanList">
		   	<logic:equal name="subloanList"  property="repaymentType" value="I">
		   		<logic:equal name="subloanList"  property="assetFlag" value="A">
	          <td><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
	          <td>
	          		<logic:present name="editable">
	          			<html:text styleClass="text" property="assetCost" styleId="assetCost"  style="text-align: right;"  value="${loanList[0].assetCost }" onchange="numberFormatting(this.id,'2');calculateMargin();calLtvPerc();" />
	          		</logic:present>
	          		<logic:notPresent name="editable">
	          			<html:text styleClass="text" property="assetCost" styleId="assetCost"  style="text-align: right;"  readonly="true" value="${loanList[0].assetCost }" tabindex="-1"/>
	          		</logic:notPresent>
	          		
	          </td>
	          <td><bean:message key="lbl.noOfAsset" /><font color="red">*</font></td>
           	  <td>
           	  		<logic:present name="editable">
	          			<html:text property="noOfAsset" styleClass="text" maxlength="5" style="text-align: right" value="${loanList[0].noOfAsset}" styleId="noOfAsset" onkeypress="return isNumberKey(event);" onblur="checkNoOfAsset()"/>
	          		</logic:present>
	          		<logic:notPresent name="editable">
	          			<html:text property="noOfAsset" styleClass="text" maxlength="5" style="text-align: right" value="${loanList[0].noOfAsset}" styleId="noOfAsset" readonly="true"/>
	          		</logic:notPresent>
	          </td>	          		
	          </logic:equal>
	        </logic:equal>
	        </logic:iterate>
	        </logic:present>
	        
	        <logic:notPresent name="loanList">
	       		 <td id="asset1"><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
	         	 <td id="asset2">
	         	 	<logic:present name="editable">
	          			<html:text styleClass="text" property="assetCost" styleId="assetCost"  style="text-align: right;" value="${loanList[0].assetCost }" onchange="numberFormatting(this.id,'2');calculateMargin();calLtvPerc();" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);calculateMargin();calLtvPerc();" />
	          		</logic:present>
	          		<logic:notPresent name="editable">
	          			<html:text styleClass="text" property="assetCost" styleId="assetCost"  style="text-align: right;"  readonly="true" value="${loanList[0].assetCost }" tabindex="-1"/>
	          		</logic:notPresent>	   
	          	</td>
	          	<td id="assetCT1"><bean:message key="lbl.noOfAsset" /><font color="red">*</font></td>
           		<td id="assetCT2">
           			<logic:present name="editable">
	          			<html:text property="noOfAsset" styleClass="text" maxlength="5" style="text-align: right" value="1" styleId="noOfAsset" onkeypress="return isNumberKey(event);" onblur="checkNoOfAsset()"/>
	          		</logic:present>
	          		<logic:notPresent name="editable">
	          			<html:text property="noOfAsset" styleClass="text" maxlength="5" style="text-align: right" value="1" styleId="noOfAsset" readonly="true"/>
	          		</logic:notPresent>    
           		</td>  	 					
	        </logic:notPresent>
	        </tr>
       
       
      
        <tr>
        <logic:present name="loanList">
		   <logic:iterate name="loanList"  id="subloanList">
		   	<logic:equal name="subloanList"  property="repaymentType" value="I">
		   		<logic:equal name="subloanList"  property="assetFlag" value="A">
		          <td><bean:message key="lbl.margin" /> %</td>
		          <td>
		          		<html:text styleClass="text" property="margin" styleId="margin"  style="text-align: right;"  readonly="true" value="${loanList[0].margin }" tabindex="-1"/>
		          </td>
		          <td><bean:message key="lbl.marginAmount"/></td>
		          <td>
		          		<html:text styleClass="text" property="marginAmount" styleId="marginAmount" style="text-align: right;"  readonly="true" value="${loanList[0].marginAmount }" tabindex="-1"/>		          		
		          </td>
		          </logic:equal>
      		 </logic:equal>
	      </logic:iterate>
	    </logic:present>
	   
	    <logic:notPresent name="loanList">
	       		  <td id="marg1"><bean:message key="lbl.margin" /> %</td>
		          <td id="marg2">
		          		<html:text styleClass="text" property="margin" styleId="margin"  style="text-align: right;"  readonly="true" value="${loanList[0].margin }" tabindex="-1"/>
	          	  </td>
		          <td id="margAmount1"><bean:message key="lbl.marginAmount"/></td>
		          <td id="margAmount2">
		          		<html:text styleClass="text" property="marginAmount" styleId="marginAmount" style="text-align: right;"  readonly="true" value="${loanList[0].marginAmount }" tabindex="-1"/>	          			
		          </td>
	        </logic:notPresent>
	   
	    </tr>
        <tr>
          <td><bean:message key="lbl.loanAmount" /><font color="red">*</font></td>
          <td><html:text styleClass="text" property="requestedLoamt" styleId="requestedLoamt" style="text-align: right;"   onchange="numberFormatting(this.id,'2');sumRequestedAmt1();calculateMargin();calLtvPerc();" value="${loanList[0].requestedLoamt }" /></td>
          <td><bean:message key="grd.applicationNo" /><font color="red">*</font></td>
          <td>
          		<logic:present name="editable">
	          		<html:text styleClass="text" property="formNo" styleId="formNo" onchange="validateFormNo()" value="${loanList[0].formNo }"/>
	          	</logic:present>
	          	<logic:notPresent name="editable">
	          		<html:text styleClass="text" property="formNo" styleId="formNo" readonly="true" onchange="" value="${loanList[0].formNo }"/>
	          	</logic:notPresent> 
	      </td>        		
          
              
<!--          <logic:present name="loanList">-->
<!--		   <logic:iterate name="loanList"  id="subloanList">-->
<!--           <logic:equal name="subloanList"  property="oneDealOneLoan" value="Y">-->
<!--           		<logic:present name="editable">-->
<!--		          	<html:text styleClass="text" property="loanAmount" styleId="loanAmount" style="text-align: right;"   onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);calculateMargin()" onchange="checkNumber(this.value, event, 18, id);checkLoanAmount();" onfocus="keyUpNumber(this.value, event, 18, id);" value="${loanList[0].loanAmount }" />-->
<!--	          	</logic:present>-->
<!--	          	<logic:notPresent name="editable">-->
<!--		          	<html:text styleClass="text" property="loanAmount" readonly="true" styleId="loanAmount" style="text-align: right;"   onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onchange="checkNumber(this.value, event, 18, id);checkLoanAmount();" onfocus="keyUpNumber(this.value, event, 18, id);" value="${loanList[0].loanAmount }" indexed="-1"/>-->
<!--	          	</logic:notPresent>-->
<!--           </logic:equal>-->
<!--           <logic:notEqual name="subloanList"  property="oneDealOneLoan" value="Y">-->
<!--           		<logic:present name="editable">-->
<!--		          	<html:text styleClass="text" property="loanAmount"  styleId="loanAmount" style="text-align: right;"   onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);calculateMargin()" onchange="checkNumber(this.value, event, 18, id);checkLoanAmount();" onfocus="keyUpNumber(this.value, event, 18, id);" value="${loanList[0].loanAmount }" />-->
<!--	          	</logic:present>-->
<!--	          	<logic:notPresent name="editable">-->
<!--		          	<html:text styleClass="text" property="loanAmount"  styleId="loanAmount" style="text-align: right;"   onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onchange="checkNumber(this.value, event, 18, id);checkLoanAmount();" onfocus="keyUpNumber(this.value, event, 18, id);" value="${loanList[0].loanAmount }" readonly="true" indexed="-1"/>-->
<!--	          	</logic:notPresent>-->
<!--            </logic:notEqual>-->
<!--           </logic:iterate>-->
<!--           </logic:present>-->
<!--           <logic:notPresent  name="loanList">-->
<!--           		<logic:present name="editable">-->
<!--		          	<html:text styleClass="text" property="loanAmount"  styleId="loanAmount" style="text-align: right;"   onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);calculateMargin()" onchange="checkNumber(this.value, event, 18, id);checkLoanAmount();" onfocus="keyUpNumber(this.value, event, 18, id);" value="${loanList[0].loanAmount }" />-->
<!--	          	</logic:present>-->
<!--	          	<logic:notPresent name="editable">-->
<!--		          	<html:text styleClass="text" property="loanAmount"  styleId="loanAmount" style="text-align: right;"   onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" onchange="checkNumber(this.value, event, 18, id);checkLoanAmount();" onfocus="keyUpNumber(this.value, event, 18, id);" value="${loanList[0].loanAmount }" readonly="true" indexed="-1"/>-->
<!--	          	</logic:notPresent>-->
<!--           </logic:notPresent>-->
<!--          </td>-->

<!-- amandeep changes for insurance -->
          <tr>
          
           <td><bean:message key="lbl.insurancePremium" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="insurancePremium" styleClass="text" styleId="insurancePremium" readonly="true" style="text-align: right" value="${loanList[0].insurancePremium}" /></td>
		           
		           
		           	<td><bean:message key="lbl.amInsurance1" /><font color="red">*</font></td>
           <td ><html:text property="loanAmount"  maxlength="15" readonly="true" styleClass="text" style="text-align: right" onchange="numberFormatting(this.id,'2');" value="${loanList[0].loanAmount}" styleId="loanAmount"  /></td>
 
	  			   
          
          </tr>
          
         <!--  amandeep changes for insurance ends -->

			
          </tr>
          <tr>
          <html:hidden property="daysBasis" styleId="daysBasis" value="${loanList[0].daysBasis }"/>  
          <td><bean:message key="lbl.tenureMonths"/><font color="red">*</font></td>
          <td>
          		<logic:present name="editable">
		          		<html:text styleClass="text" property="tenureMonths" styleId="tenureMonths" style="text-align: right;" maxlength="5" value="${loanList[0].tenureMonths }" style="text-align: right;"   onkeypress="return isNumberKey(event);"  onblur="calcInstallment(); " onchange="calcDay();calculateMaturityDateInLoan();" onfocus="keyUpNumber(this.value, event, 18, id);"/>
	          	</logic:present>
	          	<logic:notPresent name="editable">
		          		<html:text styleClass="text" property="tenureMonths" styleId="tenureMonths" style="text-align: right;"    maxlength="5" readonly="true" value="${loanList[0].tenureMonths }" tabindex="-1" onchange="calcDay();calculateMaturityDateInLoan();" onblur="calcInstallment(); "/>
	          	</logic:notPresent>          		
          </td>
          
          <logic:present name="loanList">

			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${loanList[0].tenureInDay}" styleId="tenureInDay" onchange="calcMonths();calculateMaturityDateInLoan();" maxlength="4"/>
			</td>	
        
	        </logic:present>
	        <logic:notPresent name="loanList">

			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${loanList[0].tenureInDay}" styleId="tenureInDay" onchange="calcMonths();calculateMaturityDateInLoan();" maxlength="4"/>
			</td>	
	         
	        </logic:notPresent>
          
			</tr>		  
		  <tr>
          <td><bean:message key="lbl.rateType" /></td>
<!--          <td >-->
<!--             <input type="text" name="showRateType" class="text" id="showRateType" readonly="readonly"  value="${loanList[0].showEffectiveRate }" tabindex="-1"/>-->
<!--             <html:hidden property="rateType" styleClass="text" styleId="rateType" value="${loanList[0].rateType }"/>-->
<!--         -->
<!--            </td>-->
            <!-- Nishant space starts -->
            <td>
              <html:select property="rateType" styleClass="text" styleId="rateType" value="${loanList[0].rateType}" onchange="return getFinalRateInCM();">
              <html:option value="">---Select----</html:option>
              <html:option value="F">Flat Rate</html:option>
              <html:option value="E">Effective Rate</html:option>
              </html:select>
          	</td>
          	
            <!-- Nishant space ends -->
          <td><bean:message key="lbl.rateMethod" /></td>
          <td>
          <input type="hidden" name="rateMethod" id="rateMethod" value="${loanList[0].dealRateMethod }"   />
          <logic:notPresent name="loanList">
          <bean:message key="lbl.fixed" />
            
              <html:radio property="dealRateMethod"  styleId="fixed" value="F" />
           
           <bean:message key="lbl.floating" /> 
           
             <html:radio property="dealRateMethod" styleId="floating" value="L" />
           </logic:notPresent>
          
            <logic:present name="loanList">
            
            	<logic:iterate name="loanList" id="loanListOb">
               <logic:equal name="loanListOb" property="rateType" value="F">
              
	            <logic:equal name="loanListOb" property="dealRateMethod" value="F">
	        
	             <bean:message key="lbl.fixed" />
	              <input type="radio" name="dealRateMethod"  id="fixed" value="F" checked="checked" />
	               <bean:message key="lbl.floating" /> 
	               <input type="radio" name="dealRateMethod"  id="floating" value="L" disabled="disabled" tabindex="-1"/>
	           </logic:equal>
	        	
	           </logic:equal>
	       
              <logic:equal name="loanListOb" property="rateType" value="E">
             <logic:equal name="loanListOb" property="dealRateMethod" value="L">
              <bean:message key="lbl.fixed" />
              <input type="radio" name="dealRateMethod" disabled="disabled"  id="fixed" value="F" />
              <bean:message key="lbl.floating" /> 
               <input type="radio" name="dealRateMethod"  id="floating" value="L"  checked="checked"/>
             </logic:equal>
               <logic:equal name="loanListOb" property="dealRateMethod" value="F">
	        
	             <bean:message key="lbl.fixed" />
	              <input type="radio" name="dealRateMethod"  id="fixed" value="F" checked="checked" />
	               <bean:message key="lbl.floating" /> 
	               <input type="radio" name="dealRateMethod"  id="floating" value="L" disabled="disabled" tabindex="-1"/>
	           </logic:equal>
             </logic:equal>
            </logic:iterate>
            </logic:present>
           
          </td>
             
          </tr>
          <!-- Nishant space starts -->	
          	<tr>
          		<td><bean:message key="lbl.fixPriod" /></td>
          		<td><html:text property="fixPriod" styleId="fixPriod" styleClass="text"  style="text-align: right" maxlength="3" readonly="true" value="${loanList[0].fixPriod}" onkeypress="return isNumberKey(event);"/> </td>
         
          	 <logic:present name="loanList">
          	<logic:iterate name="loanList"  id="subloanList">
          	
		   	<logic:equal name="subloanList"  property="repaymentType" value="I"> 
           	<%-- <td id="creditPeriod"><bean:message key="lbl.creditPeriod" /><font color="red">*</font></td>  --%>
          
           	<td id="creditPeriod1"><bean:message key="lbl.creditPeriod" /><font color="red">*</font></td> 
           <td ><html:text property="creditPeriod" styleClass="text" maxlength="5" style="text-align: right" value="${loanList[0].creditPeriod}" styleId="creditPeriod" onkeypress="return isNumberKey(event);"/></td>
         
       </logic:equal>
         </logic:iterate>
         </logic:present>
           <logic:notPresent name="loanList">
           
           	<td id="creditPeriod1"><bean:message key="lbl.creditPeriod" /><font color="red">*</font></td> 
           <td ><html:text property="creditPeriod" styleClass="text" maxlength="5" style="text-align: right" value="${loanList[0].creditPeriod}" styleId="creditPeriod" onkeypress="return isNumberKey(event);"/></td>
       
          
           </logic:notPresent>
          	</tr>
          	<!-- Nishant space starts -->
		  <tr>
          <td><bean:message key="lbl.baseRateType" /></td>
<!--          <td><label>-->
<!--          -->
<!--              <html:text property="baseRateType" styleClass="text" styleId="baseRateType" readonly="true" value="${loanList[0].baseRateType }" tabindex="-1"/>-->
<!--          -->
<!--          </label></td>-->
			<!-- Nishant Space starts -->
			 <td >
	            <html:select property="baseRateType" styleClass="text" styleId="baseRateType" value="${loanList[0].baseRateType}" onchange="return getBaseRateInCM();">
	             	<html:option value="">---Select----</html:option>
	             		<logic:present name="baseRateList">
	               			<html:optionsCollection name="baseRateList" label="id" value="id" /> 
	             		</logic:present>
	          	</html:select>
         	</td>
			<!-- Nishant space ends -->
          <td><bean:message key="lbl.baseRate" /></td>
          <td><html:text styleClass="text" property="baseRate" styleId="baseRate" value="${loanList[0].baseRate }" style="text-align: right;"  readonly="true" tabindex="-1"/></td></tr>
		  <tr>
		    <td><bean:message key="lbl.markUp" /></td>
		    <td>
		    	<logic:present name="editable">
		          		<html:text styleClass="text" property="markUp" styleId="markUp" value="${loanList[0].markUp }" style="text-align: right;" maxlength="10" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 18)" onblur="sevenDecimalNumber(this.value, id);" onchange="checkNumber(this.value, event, 18, id)" onfocus="sevenDecimalcheckNumber(this.value, event, 18, id);" readonly="true" tabindex="-1"/>
		          		                                                                    
	          	</logic:present>
	          	<logic:notPresent name="editable">
		          		<html:text styleClass="text" property="markUp" styleId="markUp" value="${loanList[0].markUp }" style="text-align: right;"  readonly="true" tabindex="-1"/>
	          	</logic:notPresent> 
		    </td>
		    <td><bean:message key="lbl.effectiveRate"/></td>
		   <td><html:text styleClass="text" property="effectiveRate" styleId="effectiveRate" value="${loanList[0].effectiveRate }" style="text-align: right;" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 18)"  onblur="sevenDecimalNumber(this.value, id);checkRate('effectiveRate');calculateFinalRate();" onkeyup="sevenDecimalcheckNumber(this.value, event, 8, id);" onfocus="keyUpNumber(this.value, event, 8, id);"/></td>
		    </tr>
		  <tr>
		    <td><bean:message key="lbl.repaymentType" /></td>
		    <td>
		      <input type="text" name="showRepaymentType" class="text" id="showRepaymentType" readonly="readonly" value="${loanList[0].showRepayment }" tabindex="-1"/>
		       <html:hidden property="repaymentType" styleClass="text" styleId="repaymentType"  value="${loanList[0].repaymentType }" />
		    </td>
		    
		  <logic:present name="loanList">
		   <logic:iterate name="loanList"  id="subloanList">
		   	<logic:equal name="subloanList"  property="repaymentType" value="I">
			    <td><bean:message key="lbl.installmentType" /></td>
			    <td>
			        <logic:notPresent name="editable">
			    		<input type="text" name="showInstallmentType" class="text" id="showInstallmentType" readonly="readonly" value="${loanList[0].showInstallment }" tabindex="-1"/>
			    		<html:hidden property="installmentType"  styleClass="text"styleId="installmentType"  value="${loanList[0].installmentType }"/>
			    	</logic:notPresent>
			    	<logic:present name="editable">	
			    	    <input type="hidden" name="showInstallmentType" class="text" id="showInstallmentType" value="${loanList[0].showInstallment }"/>		    	
			    		<html:select property="installmentType" styleId="installmentType" styleClass="text" value="${loanList[0].installmentType }" onchange="editNoInstal();">
				  		<option value="">--<bean:message key="lbl.select" />--</option>
				  		<logic:present name="installmentTypeList">
				  		<logic:notEmpty name="installmentTypeList">
				  		<html:optionsCollection name="installmentTypeList" label="name" value="id" />
				  		</logic:notEmpty>
				  		</logic:present>
				  		</html:select>	
				  	</logic:present>				  		
			   </td>
		    </logic:equal>
		  </logic:iterate>
		</logic:present>
		<logic:notPresent name="loanList">
				<td id="instType1"><bean:message key="lbl.installmentType" /></td>
			    <td id="instType2">
			    	<logic:notPresent name="editable">
			    		<input type="text" name="showInstallmentType" class="text" id="showInstallmentType" readonly="readonly" value="${loanList[0].showInstallment }" tabindex="-1"/>
			    		<html:hidden property="installmentType"  styleClass="text"styleId="installmentType"  value="${loanList[0].installmentType }"/>
			    	</logic:notPresent>
			    	<logic:present name="editable">	
			    	    <input type="hidden" name="showInstallmentType" class="text" id="showInstallmentType" value="${loanList[0].showInstallment }"/>		    	
			    		<html:select property="installmentType" styleId="installmentType" styleClass="text" value="${loanList[0].installmentType }" onchange="editNoInstal();">
				  		<option value="">--<bean:message key="lbl.select" />--</option>
				  		<logic:present name="installmentTypeList">
				  		<logic:notEmpty name="installmentTypeList">
				  		<html:optionsCollection name="installmentTypeList" label="name" value="id" />
				  		</logic:notEmpty>
				  		</logic:present>
				  		</html:select>	
				  	</logic:present>
			   </td>
		</logic:notPresent>
		    </tr>
		  <tr>
		  <logic:present name="loanList">
		    <logic:iterate name="loanList"  id="subloanList">
		    	<logic:equal name="subloanList"  property="repaymentType" value="I">
		  	    <td><bean:message key="lbl.frequency" /><font color="red">*</font></td>
<!--			    <td>-->
<!--			    <input type="text" name="showFrequency" class="text" id="showFrequency" readonly="readonly" value="${loanList[0].showFrequency }" tabindex="-1"/>-->
<!--			      <html:hidden property="frequency" styleClass="text" styleId="frequency"  value="${loanList[0].frequency }"/>-->
<!--			    </td>-->
				<td><html:select property="frequency" styleClass="text" styleId="frequency" value="${loanList[0].frequency}" onchange="calculateInstallmentInCM();getDueDayInCM();editNoInstal();">
            		  <html:option value="">---Select----</html:option>
		              <html:option value="M">MONTHLY</html:option>
				      <html:option value="B">BIMONTHLY</html:option>
				      <html:option value="Q">QUARTERLY</html:option>
				      <html:option value="H">HALFYERALY</html:option>
				      <html:option value="Y">YEARLY</html:option>
		          </html:select>
		        </td>
			    <td><bean:message key="lbl.repaymentMode" /></td>
			    <td>
			    	<logic:notPresent name="editable">
			    		<input type="text" name="showRepaymentMode" class="text" id="showRepaymentMode" readonly="readonly" value="${loanList[0].showRepayMode }" tabindex="-1"/>
			     		<html:hidden property="repaymentMode" styleClass="text" styleId="repaymentMode"  value="${loanList[0].repaymentMode }"/>
			    	</logic:notPresent>
			    	<logic:present name="editable">
			    	    <input type="hidden" name="showRepaymentMode" class="text" id="showRepaymentMode" value="${loanList[0].showRepayMode }"/>
			    		<html:select property="repaymentMode" styleClass="text" styleId="repaymentMode" value="${loanList[0].repaymentMode }">
	              		 <html:option value="">--Select--</html:option>
	              			<logic:present name="paymentModes">
	              			<logic:notEmpty name="paymentModes">
	              			<html:optionsCollection name="paymentModes" label="paymentMode" value="paymentModeId" />
	              			</logic:notEmpty>
	              			</logic:present>
	          			</html:select>
			    	</logic:present>			     
			   </td>
		     </logic:equal>
		  </logic:iterate>
	 </logic:present>
	 <logic:notPresent name="loanList">
	  <td id="freq1"><bean:message key="lbl.frequency" /><font color="red">*</font></td>
<!--			    <td id="freq2">-->
<!--			    <input type="text" name="showFrequency" class="text" id="showFrequency" readonly="readonly" value="${loanList[0].showFrequency }" tabindex="-1"/>-->
<!--			      <html:hidden property="frequency" styleClass="text" styleId="frequency"  value="${loanList[0].frequency }"/>-->
<!--			    </td>-->
				<td id="freq2"><html:select property="frequency" styleClass="text" styleId="frequency" value="${loanList[0].frequency}" onchange="calculateInstallmentInCM();getDueDayInCM();editNoInstal();">
            		  <html:option value="">---Select----</html:option>
		              <html:option value="M">MONTHLY</html:option>
				      <html:option value="B">BIMONTHLY</html:option>
				      <html:option value="Q">QUARTERLY</html:option>
				      <html:option value="H">HALFYERALY</html:option>
				      <html:option value="Y">YEARLY</html:option>
		          </html:select>
		        </td>
			    
			    <td id="repayMode1"><bean:message key="lbl.repaymentMode" /></td>
			    <td id="repayMode2">			    
			     		<logic:notPresent name="editable">
			    			<input type="text" name="showRepaymentMode" class="text" id="showRepaymentMode" readonly="readonly" value="${loanList[0].showRepayMode }" tabindex="-1"/>
			     			<html:hidden property="repaymentMode" styleClass="text" styleId="repaymentMode"  value="${loanList[0].repaymentMode }"/>
			    		</logic:notPresent>
			    		<logic:present name="editable">
			    			<input type="hidden" name="showRepaymentMode" class="text" id="showRepaymentMode" value="${loanList[0].showRepayMode }"/>
				    		<html:select property="repaymentMode" styleClass="text" styleId="repaymentMode" value="${loanList[0].repaymentMode }">
	              			 <html:option value="">--Select--</html:option>
	              			<logic:present name="paymentModes">
	              			<logic:notEmpty name="paymentModes">
	              			<html:optionsCollection name="paymentModes" label="paymentMode" value="paymentModeId" />
	              			</logic:notEmpty>
	              			</logic:present>
	          				</html:select>
			    		</logic:present>	
			   </td>
	 </logic:notPresent>
		    </tr>
		    <logic:notPresent name="loanList">
		    <tr>
		  	       <td id="insComFre1"><bean:message key="lbl.interestCompoundingFrequency" /><font color="red">*</font></td>
		    <td id="insComFre2"><html:select property="interestCompoundingFrequency" styleClass="text" styleId="interestCompoundingFrequency" value="${loanList[0].interestCompoundingFrequency}" onchange="validateIntCompoundingFrequency();">
               
                <html:option value="">NOT APPLICABLE</html:option>
	            <html:option value="M">MONTHLY</html:option>
			      <html:option value="B">BIMONTHLY</html:option>
			      <html:option value="Q">QUARTERLY</html:option>
			      <html:option value="H">HALFYERALY</html:option>
			      <html:option value="Y">YEARLY</html:option>
		<%-- 	    <logic:present name="intComFreq">
		               <html:optionsCollection name="intComFreq" label="name" value="id" /> 
		       </logic:present> --%>
         	  </html:select>
            </td>
		  
		    <td id="insCalMet1"><bean:message key="lbl.interestCalculationMethod" /><font color="red">*</font></td>
		    <td id="insCalMet2"><html:select property="interestCalculationMethod" styleClass="text" styleId="interestCalculationMethod" value="${loanList[0].interestCalculationMethod}"  onchange="showInterestDueDate(true);setInterestDueDate();showDueDay();nullNextDue();getDueDay();">" >
            <%-- <html:option value="">---Select----</html:option> --%>
            <logic:present name="intCal">
		               <html:optionsCollection name="intCal" label="name" value="id" /> 
		       </logic:present> 
           <%--      <html:option value="D">DUE DATE</html:option>
		      <html:option value="E">EOM</html:option>
		      <html:option value="F">FEOM</html:option> --%>
          </html:select></td>
		   
		    </tr>
		
		    <tr>
		  
				  <td id="insFre1"><bean:message key="lbl.interestFrequency" /><font color="red">*</font></td>
				    <td id="insFre2"><html:select property="interestFrequency" styleClass="text" styleId="interestFrequency" value="${loanList[0].interestFrequency}" onchange="setInterestDueDate();validateInterestFrequency();">
		               <%-- <html:option value="">---Select----</html:option> --%>
			             <html:option value="M">MONTHLY</html:option>
					      <html:option value="B">BIMONTHLY</html:option>
					      <html:option value="Q">QUARTERLY</html:option>
					      <html:option value="H">HALFYERALY</html:option>
					      <html:option value="Y">YEARLY</html:option>
					      <%--   <html:option value="X">FINANCIAL QUARTERLY</html:option>
					          <html:option value="L">FINANCIAL HALFYERALY</html:option>
					            <html:option value="Z">FINANCIAL YEARLY</html:option> --%>
					     <%--    <logic:present name="intFreq">
		           			    <html:optionsCollection name="intFreq" label="name" value="id" /> 
		       				</logic:present> --%>
		         	  </html:select>
		            </td>
		    </tr>
		    	</logic:notPresent>
		  
		  <logic:present name="loanList">
		    <logic:iterate name="loanList"  id="subloanList">
		    	<logic:equal name="subloanList"  property="repaymentType" value="I">
		    	<tr>
		  	       <td id="insComFre1"><bean:message key="lbl.interestCompoundingFrequency" /><font color="red">*</font></td>
		    <td id="insComFre2"><html:select property="interestCompoundingFrequency" styleClass="text" styleId="interestCompoundingFrequency" value="${loanList[0].interestCompoundingFrequency}" onchange="validateIntCompoundingFrequency();">
           <%--    <html:option value="">---Select----</html:option> --%>
            <html:option value="">NOT APPLICABLE</html:option>
	              <html:option value="M">MONTHLY</html:option>
			      <html:option value="B">BIMONTHLY</html:option>
			      <html:option value="Q">QUARTERLY</html:option>
			      <html:option value="H">HALFYERALY</html:option>
			      <html:option value="Y">YEARLY</html:option>
			    <%--    <logic:present name="intComFreq">
		               <html:optionsCollection name="intComFreq" label="name" value="id" /> 
		       </logic:present> --%>
         	  </html:select>
            </td>
		  
		    <td id="insCalMet1"><bean:message key="lbl.interestCalculationMethod" /><font color="red">*</font></td>
		    <td id="insCalMet2"><html:select property="interestCalculationMethod" styleClass="text" styleId="interestCalculationMethod" value="${loanList[0].interestCalculationMethod}" onchange="showInterestDueDate(true);setInterestDueDate();showDueDay();nullNextDue();getDueDay();">" >
           <%--  <html:option value="">---Select----</html:option> --%>
           <%--    <html:option value="D">DUE DATE</html:option>
		      <html:option value="E">EOM</html:option>
		      <html:option value="F">FEOM</html:option> --%>
		      			   
			    <logic:present name="intCal">
		               <html:optionsCollection name="intCal" label="name" value="id" /> 
		       </logic:present> 
          </html:select></td>
		   
		    </tr>
		    
		      <tr>
		  
				  <td id="insFre1"><bean:message key="lbl.interestFrequency" /><font color="red">*</font></td>
				    <td id="insFre2"><html:select property="interestFrequency" styleClass="text" styleId="interestFrequency" value="${loanList[0].interestFrequency}" onchange="setInterestDueDate();validateInterestFrequency();">
		            <%--   <html:option value="">---Select----</html:option> --%>
			              <html:option value="M">MONTHLY</html:option>
					      <html:option value="B">BIMONTHLY</html:option>
					      <html:option value="Q">QUARTERLY</html:option>
					      <html:option value="H">HALFYERALY</html:option>
					      <html:option value="Y">YEARLY</html:option>
					      <%--  <html:option value="X">FINANCIAL QUARTERLY</html:option>
					          <html:option value="L">FINANCIAL HALFYERALY</html:option>
					            <html:option value="Z">FINANCIAL YEARLY</html:option> --%>
					      <%--  <logic:present name="intFreq">
		           			    <html:optionsCollection name="intFreq" label="name" value="id" /> 
		       				</logic:present> --%>
		         	  </html:select>
		            </td>
		    </tr>
		  	     
		  	     
		  	   
		     </logic:equal>
		  </logic:iterate>
	 </logic:present>
	 
		   
		    
		    
		    <tr>
		<logic:present name="loanList">
		    <logic:iterate name="loanList"  id="subloanList">
		    	<logic:equal name="subloanList"  property="repaymentType" value="I">
		    
					<td><bean:message key="lbl.installmentMode" /></td>
					<td>
						<logic:notPresent name="editable">
							<input type="text" name="showInstallmentMode" class="text" id="showInstallmentMode" readonly="readonly" value="${loanList[0].showInstMode }" tabindex="-1"/>
							<html:hidden property="dealInstallmentMode" styleClass="text" styleId="dealInstallmentMode"  value="${loanList[0].dealInstallmentMode }"/>
						</logic:notPresent>	
						<logic:present name="editable">
							<input type="hidden" name="showInstallmentMode" class="text" id="showInstallmentMode" value="${loanList[0].showInstMode }"/>
							<html:select property="dealInstallmentMode" styleClass="text" styleId="dealInstallmentMode" value="${loanList[0].dealInstallmentMode }">
                    		<html:option value="">---Select----</html:option>
                    		<html:option value="A">ADVANCE</html:option>
		            		<html:option value="R">ARREAR</html:option>
                			</html:select>							
						</logic:present>
					</td>					  
					<td><bean:message key="lbl.totalIns" /></td>
		        	<td><html:text styleClass="text" property="loanNoofInstall" styleId="loanNoofInstall" onkeypress="return isNumberKey(event);" value="${loanList[0].loanNoofInstall }" maxlength="5" style="text-align: right" tabindex="-1"/></td>
		   		 </logic:equal>
		    </logic:iterate>
		  </logic:present>
		    
		   <logic:notPresent name="loanList">
		   	<td id="instMode1"><bean:message key="lbl.installmentMode" /></td>
					<td id="instMode2">					
					 	<logic:notPresent name="editable">
							<input type="text" name="showInstallmentMode" class="text" id="showInstallmentMode" readonly="readonly" value="${loanList[0].showInstMode }" tabindex="-1"/>
							<html:hidden property="dealInstallmentMode" styleClass="text" styleId="dealInstallmentMode"  value="${loanList[0].dealInstallmentMode }"/>
						</logic:notPresent>	
						<logic:present name="editable">
							<input type="hidden" name="showInstallmentMode" class="text" id="showInstallmentMode" value="${loanList[0].showInstMode }"/>
							<html:select property="dealInstallmentMode" styleClass="text" styleId="dealInstallmentMode" value="${loanList[0].dealInstallmentMode }">
                    		<html:option value="">---Select----</html:option>
                    		<html:option value="A">ADVANCE</html:option>
		            		<html:option value="R">ARREAR</html:option>
                			</html:select>							
						</logic:present>
					</td>
					  
					<td id="totalIns1"><bean:message key="lbl.totalIns" /></td>
		        	<td id="totalIns2"><html:text styleClass="text" property="loanNoofInstall" onkeypress="return isNumberKey(event);" styleId="loanNoofInstall" value="${loanList[0].loanNoofInstall }" maxlength="5" style="text-align: right" tabindex="-1"/></td>
		   
		   </logic:notPresent> 
		    </tr>
		    <tr>
		 <logic:present name="loanList">
		   <logic:iterate name="loanList"  id="subloanList">
		    <logic:equal name="subloanList"  property="repaymentType" value="I">
		     <td><bean:message key="lbl.noIns" /></td>
		     <td>
		     	<logic:notPresent name="editable">
		     		<html:text styleClass="text" property="installments" readonly="true" style="text-align: right" styleId="installments" value="${loanList[0].installments }" maxlength="5" onchange="checkInstallments();"/>
		     	</logic:notPresent>
		     	<logic:present name="editable">
		     		<html:text styleClass="text" property="installments" style="text-align: right" styleId="installments" value="${loanList[0].installments }" maxlength="5" onchange="checkInstallments();"/>
		     	</logic:present>
		     </td>
		     <td id="cycleDate1"><bean:message key="lbl.cycleDate" /><font color="red">*</font></td>
		     <td id="cycleDate1">
		      <logic:equal name="subloanList"  property="oneDealOneLoan" value="Y">
		      <html:select property="dueDay" styleClass="text" styleId="dueDayOneLoan"   value="${loanList[0].dueDay}" onchange="nullNextDue();getDueDay(id);changePlanRepayment()">
		       <html:option value="">---Select-</html:option>
	              <logic:present name="cycle">
		               <html:optionsCollection name="cycle" label="name" value="id" /> 
		             </logic:present>
			   </html:select>
                 </logic:equal>
                 <logic:notEqual name="subloanList"  property="oneDealOneLoan" value="Y">
                  <html:select property="dueDay" styleClass="text" styleId="dueDay" value="${loanList[0].dueDay}"  onchange="nullNextDue();getDueDay(id);changePlanRepayment()">
	              <html:option value="">---Select----</html:option>
	              <logic:present name="cycle">
		               <html:optionsCollection name="cycle" label="name" value="id" /> 
		             </logic:present>
			   </html:select>
                 </logic:notEqual>
			   
			   
			   </td>
		   </logic:equal>
	    </logic:iterate>
	</logic:present> 
		  	  
		 <logic:notPresent name="loanList">
		  	   <td id="noIns1"><bean:message key="lbl.noIns" /></td>
		       <td id="noIns2">
		       			<logic:notPresent name="editable">
		     				<html:text styleClass="text" property="installments" readonly="true" style="text-align: right" styleId="installments" value="${loanList[0].installments }" maxlength="5" onchange="checkInstallments();"/>
		     			</logic:notPresent>
		     			<logic:present name="editable">
		     				<html:text styleClass="text" property="installments" style="text-align: right" styleId="installments" value="${loanList[0].installments }" maxlength="5" onchange="checkInstallments();"/>
		     			</logic:present>
		       </td>
		       <td id="cycleDate1"><bean:message key="lbl.cycleDate" /><font color="red">*</font></td>
		  	   <td id="cycleDate2"><div id="mulLoanCycle">
		  	   	<div id="cycleDue1">
		  	  	  <html:select property="dueDay" styleClass="text" styleId="dueDay"  value="${loanList[0].dueDay}"  onchange="nullNextDue();getDueDay(id);changePlanRepayment()">
	              <html:option value="">---Select----</html:option>
	              <logic:present name="cycle">
		               <html:optionsCollection name="cycle" label="name" value="id" /> 
		             </logic:present>
			   </html:select>
			   </div></div>
			   <div id="oneLoanCycle" style="display: none">
			   <div id="cycleDue2">
			    <html:select property="dueDay" styleClass="text" styleId="dueDayOneLoan" onchange="nullNextDue();getDueDay(id);changePlanRepayment()">
			    <html:option value="">---Select----</html:option>
			    <logic:present name="cycle">
		               <html:optionsCollection name="cycle" label="name" value="id" /> 
		             </logic:present>
			    </html:select>
			   </div></div>
			   </td>
		  	  </logic:notPresent> 

		    </tr>
		    <tr>
		    	<td id="nddheader"><bean:message key="lbl.nextDueDate" /> <font color="red">*</font></td>
		    <td id="ndd">
		    	<html:text property="nextDueDate" styleClass="text"  value="${loanList[0].nextDueDate}" styleId="nextDueDate" onchange="calTenureMonthForMaturityDateNextDueDate();checkDueDate(value);changePlanRepayment()"/>
		    </td>
			
			<td id="interestDueDateHeader1" style="display:none;">  <bean:message key="lbl.interestDueDate"/><font color="red">*</font></td>
              		<td id = "interestDueDateHeader2" style="display:none;">
						<logic:notPresent name="editable">
							<html:text styleClass="text" property="interestDueDate" styleId="interestDueDate"   value="${loanList[0].interestDueDate}"  onchange="datevalidate();validateInterestDueDate();"/>
<%-- 							<html:text styleClass="text" property="interestDueDate" styleId="interestDueDate1" style="display:none" value="${loanList[0].interestDueDate}"   readonly="true" /> --%>
						</logic:notPresent>
						<logic:present name="editable">
							<html:text styleClass="text" property="interestDueDate" value="${loanList[0].interestDueDate}"  styleId="interestDueDate"  onchange="datevalidate();validateInterestDueDate();"/>
<%-- 							<html:text styleClass="text" property="interestDueDate" styleId="interestDueDate1" style="display:none" value="${loanList[0].interestDueDate}" readonly="true"/> --%>
						</logic:present>
					</td>
		   
		    
		    </tr>
		    
		    		  <!-- Code Added for the Handling separate Interest first due date and maturity date| Rahul papneja| 31012018 -->
				  <tr id="SeparateInterestDate" style="display:none">
				  <!-- Start|01 | Code Added for firstInterestDueDate Handling till <td> tag closed -->
				  <td id="firstInterestDueDateLBL" style="display:none"><bean:message key="lbl.firstInterestDueDate"/></td>
				  <td id = "firstInterestDueDateTXT" style="display:none">
						<logic:notPresent name="loanList">
							<html:text styleClass="text" property="firstInterestDueDate" styleId="firstInterestDueDate"  onchange="datevalidate();" />
							
						</logic:notPresent>
						<logic:present name="loanList">
						
							<html:text styleClass="text" property="firstInterestDueDate" value="${loanList[0].firstInterestDueDate}"  styleId="firstInterestDueDate"  onchange="datevalidate();" />
						
							
						</logic:present>
					</td>
					<!-- 01| End -->

				    <!-- Start|02 | Code Added for maturityDate Handling till <td> tag closed -->
				  <td id="maturityDateLBL"><bean:message key="lbl.maturityDate"/><font color="red">*</font> </td>
				  <td id = "maturityDateTXT">
						<logic:notPresent name="loanList">
							<html:text styleClass="text" property="maturityDate" styleId="maturityDate1"  onblur="setMaturityTemp();" onchange="calTenureMonthForMaturityDate();validateMaturityDate();" />
							
						</logic:notPresent>
						<logic:present name="loanList">
						
							<html:text styleClass="text" property="maturityDate" value="${loanList[0].maturityDate}"  styleId="maturityDate1" onblur="setMaturityTemp();"  onchange="calTenureMonthForMaturityDate();validateMaturityDate();datevalidate();" />
						
							
						</logic:present>
					</td>
					<!-- 02 | End -->
				  
				  
				  
				  
				  </tr>
				  <!-- Ends Here| Rahul papneja -->
		    
		    
		       <tr>
		    <td><bean:message key="lbl.agreementDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		    <td><html:text styleClass="text" property="agreementDate" styleId="agreementDate" value="${loanList[0].agreementDate }" maxlength="10" onchange="checkDate('agreementDate');validAggrDate();" /></td>
		    <td id="effDateHeader"><bean:message key="lbl.effDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		    <td id="effDate">
		    
		    <logic:present name="loanList">
		   <logic:iterate name="loanList"  id="subloanList">
		    <logic:equal name="subloanList"  property="oneDealOneLoan" value="Y">		    
		    <html:text styleClass="text" property="repayEffectiveDate" styleId="repayEffectiveDateOneLoan"  maxlength="10" value="${loanList[0].repayEffectiveDate }" onchange="checkDate('repayEffectiveDateOneLoan');validRepayDate1();calMaturityDate(this.value);getDueDayNextDueDate(this.value);changePlanRepayment();setInterestDueDate();" />
		    </logic:equal>
		    <logic:notEqual name="subloanList"  property="oneDealOneLoan" value="Y">
		    <html:text styleClass="text" property="repayEffectiveDate" styleId="repayEffectiveDate" value="${loanList[0].repayEffectiveDate }" maxlength="10" onchange="checkDate('repayEffectiveDate');validRepayDate();calMaturityDate(this.value);getDueDayNextDueDate(this.value);changePlanRepayment();setInterestDueDate();" />
		     </logic:notEqual>
		    </logic:iterate>
		    </logic:present>
		     		  
		    <logic:notPresent name="loanList" >
		   	<div id="mulLoanRepay">
		    <html:text styleClass="text" property="repayEffectiveDate" styleId="repayEffectiveDate" value="${loanList[0].repayEffectiveDate }" maxlength="10" onchange="checkDate('repayEffectiveDate');validRepayDate();calMaturityDate(this.value);getDueDayNextDueDate(this.value);changePlanRepayment();setInterestDueDate();"/>
		    </div>
		    <div id="oneLoanRepay" style="display: none">
		    <html:text styleClass="text" property="repayEffectiveDate" styleId="repayEffectiveDateOneLoan"  maxlength="10" onchange="checkDate('repayEffectiveDateOneLoan');validRepayDate1();calMaturityDate(this.value);getDueDayNextDueDate(this.value);changePlanRepayment();setInterestDueDate();"/>
		    </div>
		    </logic:notPresent>
		   <%--  <input type="hidden" name="maturityDate" id="maturityDate1" value="${loanList[0].maturityDate }" /> --%>
		    </td>
		    </tr>
		 <%--   
			<tr>
		 <logic:present name="loanList">
		   <logic:iterate name="loanList"  id="subloanList">
		    <logic:equal name="subloanList"  property="repaymentType" value="I">	
				 <td><bean:message key="lbl.typeOfDisbursal" /></td>
		    	<td>
		   						    
		       <logic:empty name="loanList">
		       		<html:select property="typeOfDisbursal" styleClass="text" styleId="typeOfDisbursal" value="${loanList[0].typeOfDisbursal }" onchange="enableDisbursal();">
              	      <html:option  value="S">Single</html:option>
		      	      <html:option value="M">Multiple</html:option>
		            </html:select>
		      
		      </td>
		    <td><bean:message key="lbl.noOfDisbursal" /><font color="red">*</font></td>
		    <td>
		   <div id="disId" style="display: none;">  
		   <html:text property="noOfDisbursal" styleId="noOfDisbursal" maxlength="3" style="text-align: right" onkeypress="return numbersonly(event,id,18)" styleClass="text" value="${loanList[0].noOfDisbursal }"  />  
<!--			    <html:select property="noOfDisbursal" styleId="noOfDisbursal" styleClass="text" value="${loanList[0].noOfDisbursal }"  >-->
<!--			            <html:option value="">---Select---</html:option>-->
<!--			            <html:option value="2">2</html:option>-->
<!--		                <html:option value="3">3</html:option>-->
<!--				        <html:option value="4">4</html:option>-->
<!--				        -->
<!--			
	    </html:select> -->
		      </div>
		     <div id="disSingle">   
		      
		        <html:text property="noOfDisbursalText" styleClass="text" styleId="noOfDisbursalText" readonly="true" value="1" tabindex="-1"/>
<!--		        <logic:present name="loanList">-->
<!--		        	<html:text property="noOfDisbursal" styleClass="text" styleId="noOfDisbursal" readonly="true"  value="${loanList[0].noOfDisbursal }"/>-->
<!--		        </logic:present>-->
		      </div>
		       </logic:empty>
		       <logic:notEmpty name="loanList">
		      
		       <logic:iterate name="loanList" id="subloanList" >
		       		<logic:equal name="subloanList" property="typeOfDisbursal" value="M">
		       			<html:select property="typeOfDisbursal" styleClass="text" styleId="typeOfDisbursal" value="${loanList[0].typeOfDisbursal }" onchange="enableDisbursal();">
              	         	    <html:option value="M">Multiple</html:option>
              	         	     <html:option  value="S">Single</html:option>
		             </html:select>
		      
		      </td>
		    <td><bean:message key="lbl.noOfDisbursal" /><font color="red">*</font></td>
		    <td>
		   <div id="disId" >  
		   <html:text property="noOfDisbursal" styleId="noOfDisbursal" maxlength="3" style="text-align: right" styleClass="text" onkeypress="return numbersonly(event,id,18)" value="${loanList[0].noOfDisbursal }" />  
<!--			    <html:select property="noOfDisbursal" styleId="noOfDisbursal" styleClass="text" value="${loanList[0].noOfDisbursal }" >-->
<!--			          	 <html:option value="">---Select---</html:option>-->
<!--			            <html:option value="2">2</html:option>-->
<!--		                <html:option value="3">3</html:option>-->
<!--				        <html:option value="4">4</html:option>-->
<!--				        -->
<!--			    </html:select> -->
		      </div>
		     <div id="disSingle" style="display: none;">   
		
		        <html:text property="noOfDisbursalText" styleClass="text" styleId="noOfDisbursalText" readonly="true" value="1" tabindex="-1"/>
<!--		        <logic:present name="loanList">-->
<!--		        	<html:text property="noOfDisbursal" styleClass="text" styleId="noOfDisbursal" readonly="true"  value="${loanList[0].noOfDisbursal }"/>-->
<!--		        </logic:present>-->
		      </div>
		     </logic:equal>
		     <logic:notEqual name="subloanList" property="typeOfDisbursal" value="M">
		       <html:select property="typeOfDisbursal" styleClass="text" styleId="typeOfDisbursal" value="${loanList[0].typeOfDisbursal }" onchange="enableDisbursal();">
              	      <html:option  value="S">Single</html:option>
		      	      <html:option value="M">Multiple</html:option>
		      </html:select>
		      
		      </td>
		    <td><bean:message key="lbl.noOfDisbursal" /><font color="red">*</font></td>
		    <td>
		   <div id="disId" style="display: none;">  
		   <html:text property="noOfDisbursal" styleId="noOfDisbursal" maxlength="3" style="text-align: right" styleClass="text" onkeypress="return numbersonly(event,id,18)" value="${loanList[0].noOfDisbursal }" />  
<!--			    <html:select property="noOfDisbursal" styleId="noOfDisbursal" styleClass="text" value="${loanList[0].noOfDisbursal }" >-->
<!--			          	 <html:option value="">---Select---</html:option>-->
<!--			            <html:option value="2">2</html:option>-->
<!--		                <html:option value="3">3</html:option>-->
<!--				        <html:option value="4">4</html:option>-->
<!--				        -->
<!--			    </html:select> -->
		      </div>
		     <div id="disSingle">   
		      
		        <html:text property="noOfDisbursalText" styleClass="text" styleId="noOfDisbursalText" readonly="true" value="1" tabindex="-1"/>
<!--		        <logic:present name="loanList">-->
<!--		        	<html:text property="noOfDisbursal" styleClass="text" styleId="noOfDisbursal" readonly="true"  value="${loanList[0].noOfDisbursal }"/>-->
<!--		        </logic:present>-->
		      </div>
		      </logic:notEqual>
		      </logic:iterate >
		      </logic:notEmpty>
		      </logic:equal>
		     </logic:iterate >
		      </logic:present>
		      
		      <logic:notPresent name="loanList">
		      <td id="tyeOfDis1"><bean:message key="lbl.typeOfDisbursal" /></td>
		       	<td id="tyeOfDis2">	 <html:select property="typeOfDisbursal" styleClass="text" styleId="typeOfDisbursal" value="${loanList[0].typeOfDisbursal }" onchange="enableDisbursal();">
              	      <html:option  value="S">Single</html:option>
		      	      <html:option value="M">Multiple</html:option>
		      </html:select>
		      
		      </td>
		    <td id="noOfDis1"><bean:message key="lbl.noOfDisbursal" /><font color="red">*</font></td>
		    <td id="noOfDis2">
		   <div id="disId" style="display: none;">    
		    <html:text property="noOfDisbursal" styleId="noOfDisbursal" styleClass="text" maxlength="3" value="${loanList[0].noOfDisbursal }" onkeypress="return numbersonly(event,id,18)" style="text-align: right"/>
<!--			    <html:select property="noOfDisbursal" styleId="noOfDisbursal" styleClass="text" value="${loanList[0].noOfDisbursal }" >-->
<!--			          	 <html:option value="">---Select---</html:option>-->
<!--			            <html:option value="2">2</html:option>-->
<!--		                <html:option value="3">3</html:option>-->
<!--				        <html:option value="4">4</html:option>-->
<!--				        -->
<!--			    </html:select> -->
		      </div>
		     <div id="disSingle">   
		      
		        <html:text property="noOfDisbursalText" styleClass="text" styleId="noOfDisbursalText" readonly="true" style="text-align: right" value="1"/>
<!--		        <logic:present name="loanList">-->
<!--		        	<html:text property="noOfDisbursal" styleClass="text" styleId="noOfDisbursal" readonly="true"  value="${loanList[0].noOfDisbursal }"/>-->
<!--		        </logic:present>-->
		      </div>
		      
		      
		      </td>
		     </logic:notPresent>
		      </tr>
		      
		--%>       
		   
		    <tr>
		    
		    <td><bean:message key="lbl.sectorType" /><font color="red">*</font></td>
		    <td>
		       	<logic:notPresent name="editable">
			    	<input type="text" name="showSectorType" class="text"  id="showSectorType" readonly="readonly" value="${loanList[0].showSectorType }" tabindex="-1"/>
		          	<html:hidden property="sectorType" styleClass="text"  styleId="sectorType"  value="${loanList[0].sectorType }"/>
			    </logic:notPresent>
			    <logic:present name="editable">
			    	<input type="hidden" name="showSectorType" class="text"  id="showSectorType" readonly="readonly" value="${loanList[0].showSectorType }" tabindex="-1"/>
				    <html:select property="sectorType" styleClass="text" styleId="sectorType" value="${loanList[0].sectorType}">
	             	<html:option value="">---Select----</html:option>
	             		<logic:present name="sector">
	               		<html:optionsCollection name="sector" label="name" value="id" /> 
	             		</logic:present>
	             	</html:select>
			    </logic:present>	
			</td>
		        	
		  <logic:present name="loanList">
		   <logic:iterate name="loanList"  id="subloanList">
		     <logic:equal name="subloanList"  property="repaymentType" value="I">

		    <td><bean:message key="lbl.podo" /><font color="red">*</font></td>
		      <td>
		      	 <html:select property="podoFlag" styleClass="text" styleId="podoFlag" value="${loanList[0].podoFlag }" onchange="enableDisbursal();">
		      	 	 <html:option  value="N">None</html:option>
              	      <html:option  value="P">PO</html:option>
		      	      <html:option value="D">DO</html:option>
		      </html:select>
		      </td>
		       </logic:equal>
		     </logic:iterate>
		   </logic:present>
		   <logic:notPresent name="loanList">
		    <td id="pdo1"><bean:message key="lbl.podo" /><font color="red">*</font></td>
		      <td id="pdo2">
		      	 <html:select property="podoFlag" styleClass="text" styleId="podoFlag" value="${loanList[0].podoFlag }" onchange="enableDisbursal();">
		      	 	 <html:option  value="N">None</html:option>
              	      <html:option  value="P">PO</html:option>
		      	      <html:option value="D">DO</html:option>
		      </html:select>
		      </td>
		   </logic:notPresent>
		    </tr>
<!--		   @Surendra Code Start	   -->
		    <tr >
              <logic:present name="loanList">
                  <logic:iterate id="subloanList" name="loanList" length="1">
                    <logic:equal name="subloanList" property="repaymentType" value="N" >
		        <td ><bean:message key="lbl.interestCalc" /><font color="red">*</font></td>
		    	 <td >	
		    	  <html:text property="showInterestCalc" styleClass="text" styleId="showInterestCalc" value="${loanList[0].showInterestCalc}" readonly="true" tabindex="-1" />	    	
	            <html:hidden property="interestCalc" styleClass="text" styleId="interestCalc" value="${loanList[0].interestCalc}"  />
	            	          
	         </td>
	          </logic:equal>
	             </logic:iterate>
	             </logic:present>
	             <logic:notPresent name="loanList">	                       
	             <td >
	             <div id="int1" style="display:none">
	             <bean:message key="lbl.interestCalc" /><font color="red">*</font></div></td>
	              <td>	<div id="int2" style="display:none">
	            <html:text property="showInterestCalc" styleClass="text" styleId="showInterestCalc" value="${loanList[0].showInterestCalc}" readonly="true" tabindex="-1" />  	    	
	            <html:hidden property="interestCalc" styleClass="text" styleId="interestCalc" value="${loanList[0].interestCalc}" />	
	          </div>          
	         </td> 
	             </logic:notPresent>
	         </tr>       
<!--		   @Surendra Code End	   -->		    
		    
		   <tr>
		   <logic:present name="loanList">
		     <logic:iterate name="loanList"  id="subloanList">
		      <logic:equal name="subloanList"  property="repaymentType" value="I">
		    		  <td><bean:message key="lbl.repaySMKTIRR2" /></td>
				    <td>
				    	<html:text property="iir2" styleClass="text" styleId="iir2" readonly="true" value="${loanList[0].dealIrr2 }"/>
				    </td>
				  </logic:equal>
				</logic:iterate>
			</logic:present>
			<logic:notPresent name="loanList">
			 	<td id="repaySmk1"><bean:message key="lbl.repaySMKTIRR2" /></td>
				    <td id="repaySmk2">
				    	<html:text property="iir2" styleClass="text" styleId="iir2" readonly="true" value="${loanList[0].dealIrr2 }"/>
				    </td>			
			</logic:notPresent>
			      
			      		  <!-- Code Added for Edit Due Date | Rahul papneja | 30012018 -->
			       <!-- Vishal changes start -->
			<td>
			<bean:message key="lbl.editDueDate"/><font color="red">*</font></td>
			<td>
			<html:select property="editDueDate" styleId="editDueDate" styleClass="text" value="${loanList[0].editDueDate}">
			<html:option value="N">NO</html:option>
			<html:option value="Y">Yes</html:option>
			<!-- <html:option value="N">NO</html:option> -->
			</html:select>
			</td>
<!-- 	   Vishal changes end --> 
			  <!-- Ends Here -->
			      
			      
		   </tr>
		   
		   <tr>
		  <logic:present name="loanList">
		     <logic:iterate name="loanList"  id="subloanList">
		      <logic:equal name="subloanList"  property="repaymentType" value="I">
		  
		   		  <td><bean:message key="lbl.insurancedoneby" /></td>
		    <td >
		    	 
		      	 <html:select property="insuranceDoneBy" styleClass="text" styleId="insuranceDoneBy" value="${loanList[0].insuranceDoneBy }" >
		      	 	<html:option  value="">---Select---</html:option>
		      	 	 <html:option  value="B">BORROWER</html:option>
              	     <html:option  value="L">LENDER</html:option>
              	     <html:option  value="D">DEALER</html:option>
		      	</html:select>
		    
	         </td>
	      	</logic:equal>
	      </logic:iterate>
	     </logic:present>  
	     <logic:notPresent name="loanList">
	     		  <td id="insuranceBy1"><bean:message key="lbl.insurancedoneby" /></td>
		    <td id="insuranceBy2">
		    	 
		      	 <html:select property="insuranceDoneBy" styleClass="text" styleId="insuranceDoneBy" value="${loanList[0].insuranceDoneBy }" >
		      	 	<html:option  value="">---Select---</html:option>
		      	 	 <html:option  value="B">BORROWER</html:option>
              	     <html:option  value="L">LENDER</html:option>
              	     <html:option  value="D">DEALER</html:option>
		      	</html:select>
		    
	         </td>
	     </logic:notPresent>
	     
	      	     
	          <%-- Start By Prashant --%>
	         <logic:present name="loanList">
		        <logic:iterate name="loanList"  id="subloanList">
		            <logic:equal name="subloanList"  property="repaymentType" value="I">

              		<logic:equal name="subloanList" property="assetFlag" value="A">
				        <td><bean:message key="lbl.LTV" /></td>
					    <td>
					         	<html:text property="ltvPerc" styleClass="text"  value="${loanList[0].ltvPerc}" styleId="ltvPerc"  readonly="true" />
					    </td>
					   </logic:equal>
				    </logic:equal>
	         </logic:iterate>
	         
	         </logic:present>
	          <logic:notPresent name="loanList">
	          
              	      <td id="ltvDesc"><bean:message key="lbl.LTV" /></td>
					    <td id="ltvP">
					         	<html:text property="ltvPerc" styleClass="text"  value="${loanList[0].ltvPerc}" styleId="ltvPerc"  readonly="true" />
					    </td>
	         
	         </logic:notPresent>
	         
	           <%-- End By Prashant --%>
	     
	     
		   </tr>
		   <tr>
		   		<td><bean:message key="lbl.areaCode" />1</td> 
				<td>
			  		<html:text property="areaCodename" styleId="areaCodename" styleClass="text" readonly="readonly" value="${loanList[0].areaCodename}"/>
			   		<html:button property="areaCodeButton" styleId="areaCodeButton" onclick="openLOVCommon(370,'leadCapturingDetails','areaCodename','','', '','','','lbxareaCodeVal');" value=" " styleClass="lovbutton"></html:button>
			     	<html:hidden  property="lbxareaCodeVal" styleId="lbxareaCodeVal" value="${loanList[0].lbxareaCodeVal}"  />
			    </td>	
			    
			
		   </tr>
			    
			 <tr id="netLtvRow">
			 <html:hidden property="serviceTax" value="${loanList[0].serviceTax}" styleId="serviceTax"/>
			 <html:hidden property="serviceAmount" value="${loanList[0].serviceAmount}" styleId="serviceAmount"/>
			<logic:present name="loanList">
             <logic:iterate name="loanList"  id="subloanList">
		            <logic:equal name="subloanList"  property="repaymentType" value="I">
		            <logic:equal name="subloanList" property="assetFlag" value="A">
			<td><bean:message key="lbl.netLtv" /></td>
			<td>
			<html:text property="netLtv" styleClass="text"  value="${loanList[0].netLtv}" styleId="netLtv"  readonly="true" />
			</td>
			 <td><bean:message key="lbl.totalVatAmt" /></td>
		   <td>
		   <html:hidden property="vatPercent" value="${loanList[0].vatPercent}" styleId="vatPercent"/>
		   <html:text property="totalVatAmt" styleClass="text"  value="${loanList[0].totalVatAmt}" styleId="totalVatAmt"  readonly="true" />
		   </td>  
			</logic:equal>
			</logic:equal>
			</logic:iterate>		         
	        </logic:present>
	        <logic:notPresent name="loanList">

			<td><bean:message key="lbl.netLtv" /></td>
			<td>
			<html:text property="netLtv" styleClass="text"  value="${loanList[0].netLtv}" styleId="netLtv"  readonly="true" />
			</td>
	          <td><bean:message key="lbl.totalVatAmt" /></td>
		   <td>
		    <html:hidden property="vatPercent" value="${loanList[0].vatPercent}" styleId="vatPercent"/>
		   <html:text property="totalVatAmt" styleClass="text"  value="${loanList[0].totalVatAmt}" styleId="totalVatAmt"  readonly="true" />
		   </td>  
	        </logic:notPresent>
	        
	        
	        </tr>

		     <tr>
		    	<td><bean:message key="lbl.makerRemarks" /></td>
		        <td><html:textarea  property="remarks" styleId="remarks" value="${loanList[0].remarks }"  styleClass="text"></html:textarea></td>
		       <%--  <td><bean:message key="lbl.noIns" /></td>
		    <td><html:text styleClass="text" property="installments" style="text-align: right" styleId="installments" value="${loanList[0].installments }" maxlength="5" onchange="checkInstallments();"/></td>
		    
		    --%>
		    
		    <td><bean:message key="lbl.authorRemarks" /></td>
		      <td>
		      	<html:textarea property="authorRemarks" readonly="true" styleClass="text" styleId="authorRemarks" value="${loanList[0].authorRemarks }"></html:textarea>
		      </td>
		    </tr>

			
			
   
				
			 <tr>
			          <td><bean:message key="lbl.loanClassification" /></td>
			<td>
					          		
     		<html:hidden  property="lbxLoanClassification" styleId="lbxLoanClassification" value="${loanList[0].lbxLoanClassification}" />
   			<html:select property="loanClassification" styleId="loanClassification" size="5" multiple="multiple" style="width: 120" >
   			<logic:present name="loanClassificationList">
   			<logic:notEmpty name="loanClassificationList">
       			 <html:optionsCollection name="loanClassificationList" value="loanClassificationId" label="loanClassificationLabel"/>
       		</logic:notEmpty>
       		</logic:present>
     		</html:select>
      		<html:button property="userButton" styleId="userButton1" onclick="return openMultiSelectLOVCommon(2050,'sourcingForm','loanClassification','','', '','','','lbxLoanClassification');" value=" " styleClass="lovbutton"></html:button>
     		
     		
     		
		   
	          </td>  
	           <!-- Amit Space starts -->
	         <td><bean:message key="lbl.businessType" /><font color="red">*</font></td>
			 <td >
	            <html:select property="businessType" styleClass="text" styleId="businessType1" disabled="true" value="${loanList[0].businessType}" onclick="return getbusinessloan();" onchange="return getbusinessbutton();">
	             	<html:option value="">---Select---</html:option>
	             		<logic:present name="getBusiness">
	               			<html:optionsCollection name="getBusiness" label="businessdesc" value="businessId" /> 
	             		</logic:present>
	          	</html:select>
	          	 <input type="hidden" name="oldPartnerType" id="oldPartnerType" value="${loanList[0].businessType}" />
	          	  <input type="hidden" name="businessType" id="businessType" value="${loanList[0].businessType}" /> 
         	</td>
			<!-- Amit space ends -->  		       
			<%--  			 
			 <tr>
		<td><bean:message key="lbl.purpose" /><font color="red">*</font></td>
			<td >
		    	<a rel="tooltip3" id="tool">
		     		 <html:text property="loanPurpose" maxlength="50"  styleClass="text" readonly="true" value="${loanList[0].loanPurpose}" styleId="loanPurpose" onchange="return upperMe('loanPurpose');" onmouseover="applyToolTip(id);"  />
						<html:hidden styleClass="text" property="lbxLoanPurpose" styleId="lbxLoanPurpose" value="${loanList[0].lbxLoanPurpose }"   />
		     				 <input type="button" class="lovbutton" id="leadButton1" onclick="openLOVCommon(5056,'sourcingForm','loanPurpose','product','lbxLoanPurpose','lbxProductID','Please select product first!!!','','lbxLoanPurpose');" value=" "  />	
		     			</a>
		     		</td>  
		</tr>			
			  --%>
			
			
			  </tr>
			  <tr>
			  <td><bean:message key="lbl.grossBlock" /></td>
          	 <td ><html:text property="grossBlock" styleClass="text" maxlength="10" style="text-align: right" value="${loanList[0].grossBlock}" styleId="grossBlock"   onkeypress="return isNumberKey(event);" /></td>
			 
			 <td><bean:message key="lbl.netBlock" /></td>
          	 <td ><html:text property="netBlock" styleClass="text" maxlength="10" style="text-align: right" value="${loanList[0].netBlock}" styleId="netBlock"    onkeypress="return isNumberKey(event);" /></td>
          	 
			  </tr>
			  
	
		      <tr>
		      <td colspan="2">
		       <button type="button" name="button"   id="saveButton" class="topformbutton2" onclick="saveLoanInCM();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
		       <button type="button" name="button"  title="Alt+F" accesskey="F" id="forwardButton" class="topformbutton2" onclick="return validateLoanInit('<bean:message key="msg.confirmationForwardMsg" />');"> <bean:message key="button.forward" /></button>
		       <button type="button" class="topformbutton2"  id="deal_viewer"  title="Alt+D" accesskey="D" onclick="dealViewerLoanInitiation();"><bean:message key="button.dealViewer" /></button>
		       <button type="button" class="topformbutton4" id="Save" title="Alt+V" accesskey="V" onclick="openPopForSectorDetails('N');"><bean:message key="button.sectorTypeDetails" /></button> 
			 </td>
			<td id ="partner_mode" style="display: none">        
		      <button type="button" class="topformbutton4" id="partner" title="Alt+P" accesskey="P" onclick="getbusinessType();"><bean:message key="lbl.partnerDetails" /></button>
		      </td>
		      
		      
		      </tr>		      
		  </table>
		  </td></tr>
		  
</Tbody></table>

 </fieldset>
 

 <logic:present name="loanS">

 <script type="text/javascript">
 
  	var repaymentType = document.getElementById("repaymentType").value; 
  	if(repaymentType=="N")
  	{
  		document.getElementById('nddheader').style.display='none';
  		document.getElementById('ndd').style.display='none';
  		document.getElementById('effDateHeader').style.display='none';
  		document.getElementById('effDate').style.display='none';
  	}
 	if('<%=request.getAttribute("loanS")%>'=='S')
	{
			//parent.menu.document.notForward.recStatus.value = "P";
			alert("<bean:message key="lbl.loanSuccess" />");
	}
	if('<%=request.getAttribute("loanS")%>'=='X')
	{
		alert("<bean:message key="lbl.loanCheckForDeal" />");
		parent.parent.location="<%=request.getContextPath()%>/creditListAction.do?method=searchLinkForMaker";
	}
	if('<%=request.getAttribute("loanS")%>'=='LimitCross')
	{
		alert("<bean:message key="lbl.limitAmount" />");
	}
	
</script>
</logic:present>
        
    <logic:present name="status">

 <script type="text/javascript">
 
	if('<%=request.getAttribute("status")%>'=='SD')
	{
		alert('<bean:message key="lbl.securityMove" />');
	}
	else if('<%=request.getAttribute("status")%>'=='LD')
	{	
		alert("<bean:message key="lbl.disbMove" />");
	}
	else if('<%=request.getAttribute("status").toString()%>'=='CA')
	{
		alert("<bean:message key="lbl.cummAddr" />");
	}
	else if('<%=request.getAttribute("status").toString()%>'=='sidbi')
	{
		alert("please capture SIDBI Value in Asset Collateral screen.");
	}
	else if('<%=request.getAttribute("status").toString()%>'=='GA')
	{
		alert("<bean:message key="lbl.custGA" />");
	}
	else if('<%=request.getAttribute("status")%>'=='AC')
	{	
		alert("<bean:message key="lbl.assetMove" />");
	}
	else if('<%=request.getAttribute("status")%>'=='CH')
	{
		alert('<bean:message key="lbl.chargeMove" />');
	}
	else if('<%=request.getAttribute("status")%>'=='DC')
	{
		alert('<bean:message key="lbl.docsMove" />');
	}	
	else if('<%=request.getAttribute("status")%>'=='LD')
	{
		alert("<bean:message key="lbl.canNotMove" />");
	}
	else if('<%=request.getAttribute("status")%>'=='CM2')
	{
		alert("<bean:message key="lbl.assetMove" />");
	}	
	else if('<%=request.getAttribute("status")%>'=='CM3')
	{
		alert("<bean:message key="lbl.saveChargeFeeThenForward" />");
	}	
	else if('<%=request.getAttribute("status")%>'=='CM4')
	{
		alert("<bean:message key="lbl.securityMove" />");
	}	
	else if('<%=request.getAttribute("status")%>'=='CM5')
	{
		alert("<bean:message key="lbl.saveInstallmentPlanThenForward" />");
	}
	else if('<%=request.getAttribute("status")%>'=='CM6')
	{
		alert("<bean:message key="lbl.selectPaymentScheduleThenForward" />");
	}		
	else if('<%=request.getAttribute("status")%>'=='CM7')
	{
		alert("<bean:message key="lbl.saveDocCollectionThenForward" />");
	}
	else if('<%=request.getAttribute("status")%>'=='CM8')
	{
		alert("<bean:message key="lbl.saveDisbursalThenForward" />");
	}		
	else if('<%=request.getAttribute("status")%>'=='TE')
	{
		alert("<bean:message key="lbl.tenureEqual" />");
	}
	else if('<%=request.getAttribute("status").toString()%>'=='ASSETCT')
	{
		alert("<bean:message key="lbl.noOfAssetError" />");
	}	
	else if('<%=request.getAttribute("status").toString()%>'=='assetCost')
	{
		alert("<bean:message key="lbl.assetCostMatch" />");
	}	
	else if('<%=request.getAttribute("status").toString()%>'=='assetMapped')
	{
		alert("<bean:message key="lbl.assetMapped" />");
	}	
	else if('<%=request.getAttribute("status").toString()%>'=='relationStatus')
	{
		alert("Please capture Relationship Details in Customer Address Details Tab");
	}
	else if('<%=request.getAttribute("status").toString()%>'=='mobileVerif')
	{
		alert("Mobile No. is not Verified for Applicant.");
	}
	else if('<%=request.getAttribute("status").toString()%>'=='emailVerif')
	{
		alert("Email Id is not Verified for Applicant.");
	}
	else if('<%=request.getAttribute("status").toString()%>'=='1')
	{
	        DisButClass.prototype.DisButMethod();
			alert("Asset cost at Loan Details Screen should be equal to Sum of Value of all Assets displayed in Asset/Collaterals Screen. .");
			DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='PLZASSETCAP')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.plzAssetCap" />");
		//location.href="docsCollectionBehindAction.do";
		//document.forms[0].action="docsCollectionBehindAction.do";
	    //document.forms[0].submit();
	    DisButClass.prototype.EnbButMethod();
	}
	else if ('<%=request.getAttribute("status").toString()%>'=='LNASTNTMATMAR')
	{
			DisButClass.prototype.DisButMethod();
			var msg="Sum of Margin Amount and Loan Amount should be equal to Asset Cost at Loan Details Screen.";
			alert(msg);
			DisButClass.prototype.EnbButMethod();
	}	
	else if('<%=request.getAttribute("status")%>'=='UT')
	{
		alert("<bean:message key="lbl.utiLizedAmount" />");
		parent.parent.location="<%=request.getContextPath()%>/JSP/CMJSP/loanInitMakerSearch.jsp";
	}
	else if('<%=request.getAttribute("status").toString()%>'=='charge101')
	{
	//DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.charge101"/>");
		//location.href="openLoanDetailAction.do";
		//DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='charge102')
	{
	//DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.charge102"/>");
		//location.href="openLoanDetailAction.do";
		//DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='bothcharge')
	{
	//DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.bothcharge"/>");
		//location.href="openLoanDetailAction.do";
		//DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='bothNotExist')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.bothchargeREQ"/>");
		//location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='UPDATEDEVIATION')
	{
		DisButClass.prototype.DisButMethod();
		alert("<bean:message key="lbl.updateDeviation"/>");
		//location.href="openLoanDetailAction.do";
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='CSM')
	{
	    DisButClass.prototype.DisButMethod();
		alert('${checkStageM}');
		DisButClass.prototype.EnbButMethod();
	}
	else if('<%=request.getAttribute("status").toString()%>'=='VEHICLECT')
	{
		DisButClass.prototype.DisButMethod();
		var msg="Captured Make Model For Vehicle Class In Asset/Collaterals Is Not Mapped With Selected Product And Scheme.";
		alert(msg);
		DisButClass.prototype.EnbButMethod();
	}
</script>
</logic:present>

<!-- add by saorabh -->
<logic:present name="result">
<script type="text/javascript">
if('<%=request.getAttribute("result")%>'=='count')
	{
		alert('<bean:message key="msg.partnerCount" />');
	}
	else if('<%=request.getAttribute("result")%>'=='al')
	{	
		alert("<bean:message key="msg.servicePartner" />");
	}
	else if('<%=request.getAttribute("result").toString()%>'=='pt')
	{
		alert("<bean:message key="msg.partnerCount" />");
	}
	else if('<%=request.getAttribute("result").toString()%>'=='rel')
	{
		alert("<bean:message key="msg.serviceRate1" />");
	}
	else if('<%=request.getAttribute("result")%>'=='rlee')
	{	
		alert("<bean:message key="msg.serviceRate" />");
	}
	else if('<%=request.getAttribute("result")%>'=='vat')
	{	
		alert("<bean:message key="msg.checkVatPercent" />");
	}
	else if('<%=request.getAttribute("result")%>'=='AMOUNTNOTMATCH')
	{	
		alert("<bean:message key="msg.checkParticipationAmt" />");
	}
	else if('<%=request.getAttribute("result")%>'=='ONEMUSTLEADPARTNER')
	{	
		alert("<bean:message key="msg.oneMustLeadPartner" />");
	}
	else if('<%=request.getAttribute("result")%>'=='RATENOTMATCH')
	{	
		alert("<bean:message key="msg.rateNotMatch" />");
	}
	else if('<%=request.getAttribute("result")%>'=='checkAllParticipationAmt')
	{	
		alert("<bean:message key="msg.checkAllParticipationAmt" />");
	}

	
</script>
</logic:present>

<logic:present name="vatPercent">
<script type="text/javascript">
if('<%=request.getAttribute("vatPercent")%>'=='vat')
{	
	 alert("<bean:message key="msg.checkVatPercent" />");
}
</script>
</logic:present>
<!-- //end by saorabh -->

<logic:present name="countSms">
 <script type="text/javascript">
	if("<%=request.getAttribute("countSms")%>"=="NA")
	{
		alert("There are some document left to receive ");
		
	}
	
	</script>
</logic:present>

<logic:present name="Loansms">
 <script type="text/javascript">
	if("<%=request.getAttribute("Loansms")%>"=="S")
	{
		alert("<bean:message key="lbl.loanStageSuccess" />");
		parent.parent.location="<%=request.getContextPath()%>/JSP/CMJSP/loanInitMakerSearch.jsp";
	}
	
	</script>
</logic:present>
    
<logic:present name="back">

	<script type="text/javascript">
	
			alert("<bean:message key="lbl.canNotMove" />");
	
		
</script>
</logic:present>
<logic:present name="insertError">
<script type="text/javascript">
	alert('${insertError}');
	parent.parent.location="<%=request.getContextPath()%>/creditListAction.do?method=searchLinkForMaker";
</script>
</logic:present>


<logic:present name="nonProduct">

	<script type="text/javascript">
	
			alert("<bean:message key="lbl.NotInstallProduct" />");
	
		
</script>
</logic:present>

 

  </html:form>
</logic:notPresent>







<%--          			Loan Initiation Author part																	 --%>

<logic:present name="cmAuthor">

	<html:form  action="/loanDetailCMProcessAction.do?method=updateLoanDetails" styleId="sourcingForm" method="post">

	<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath() %>" />
	<html:hidden property="loanBranch" styleId="loanBranch" value="" />
	<html:hidden property="loanIndustry" styleId="loanIndustry" value="" />
	<html:hidden property="loanSubIndustry" styleId="loanSubIndustry" value="" />
	<html:hidden property="loanCustomerId" styleId="loanCustomerId" value="" />
	<html:hidden property="loanCustomerExisting" styleId="loanCustomerExisting" value="" />
	<html:hidden property="loanApprovalDate" styleId="loanApprovalDate" value="" />
	<html:hidden property="loanCustomerType" styleId="loanCustomerType" value="" />
	<html:hidden property="productType" styleId="productType"   />
	<html:hidden property="productCat" styleId="productCat"   />
	<input type="hidden" name="sanctionedDate" id="sanctionedDate"   />
	<input type="hidden" id="assetFlag" value="${loanList[0].assetFlag}"/>
	<html:hidden  property="dateChange" styleId="dateChange" value="N"/>
	<input type="hidden" id="userId" name="userId" value="${sessionScope.editUserId}"/>
<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>		
  <fieldset>	  
<legend><bean:message key="lbl.dealParameters"/></legend>
<input type="hidden" id="dealButton" value="k"/>
<table cellSpacing="0" cellPadding="0" width="100%" border="0" >
  <Tbody>
  <tr>
    <td>
      <table cellSpacing="1" cellPadding="2" width="100%" border="0" >
       
       <tr>
       	<td><bean:message key="lbl.dealNo"/></td>
       	<td>
       	<input type="hidden" name="hcommon" id="hcommon"/>
       	 <html:text property="dealNo" styleClass="text" styleId="dealNo" maxlength="100" disabled="true" value="${loanList[0].dealNo }" tabindex="-1"/>
		 <html:hidden  property="lbxDealNo" styleId="lbxDealNo"  value="${loanList[0].lbxDealNo }"/>
		  <html:hidden  property="loanDealId" styleId="loanDealId"  value="${loanList[0].loanDealId }"/>
       </td>
	      
	        <td><bean:message key="lbl.customerName" /></td>
			<td >
			 <input type="text" name="customerName" class="text" id="customerName" disabled="disabled" value="${loanList[0].customerName }" tabindex="-1"/>
			   <html:hidden property="basePath" styleId="basePath" value="<%=request.getContextPath()%>" />
		       <html:hidden property="loanId" styleId="loanId" value="${loanList[0].loanId }"/>
       
	   		</td>
	</tr>
	<tr>
		<td><bean:message key="lbl.product" /></td>
		<td >
			<input type="text" name="showProduct" class="text" id="showProduct" disabled="disabled" value="${loanList[0].showProduct }" tabindex="-1"/>
	        <html:hidden styleClass="text" property="product" styleId="product" value="${loanList[0].product }"/>
	   <logic:notPresent name="loanId">
	     <html:button property="pbutton" styleId="pbutton" onclick="productPopUp();" value=" " styleClass="lovbutton"></html:button>
	  <%--<img SRC="<%=request.getContextPath() %>/images/lov.gif"  onclick="productPopUp();"  /> --%>	  

	   </logic:notPresent>   
	  
	   <html:hidden property="basePath" styleId="basePath" value="<%=request.getContextPath()%>" />
       <html:hidden property="loanId" styleId="loanId" value="${loanList[0].loanId }"/>
       
	   </td>
	   <logic:present name="loanId">
	    <td><bean:message key="lbl.scheme" />  </td>
		 <td> 
		   <input type="text" name="showScheme" class="text" id="showScheme" disabled="disabled" value="${loanList[0].showScheme }" tabindex="-1"/>
	        <html:hidden styleClass="text" property="scheme" styleId="scheme"   value="${loanList[0].scheme }" />
	      </td>
	   </logic:present>
           <logic:notPresent name="loanId"> 
		 <td><bean:message key="lbl.scheme" />  </td>
		 <td> 
		   <input type="text" name="showScheme" class="text" id="showScheme" disabled="disabled" value="${loanList[0].showScheme }" tabindex="-1"/>
	        <html:hidden styleClass="text" property="scheme" styleId="scheme"   value="${loanList[0].scheme }" /> </td>
	     

	    </logic:notPresent>
	   </tr>
	    <tr>
	     <td><bean:message key="lbl.sanctionedLoanAmount" /></td>
			<td> 
	    <html:text styleClass="text" property="sanctionedLoanAmount" styleId="sanctionedLoanAmount" style="text-align: right;"  disabled="true" value="${loanList[0].sanctionedLoanAmount }" tabindex="-1"/> </td>
	     <td><bean:message key="lbl.utilizeLoanAmount" /></td>
			<td> 
	    <html:text styleClass="text" property="utilizeLoanAmount" styleId="utilizeLoanAmount" style="text-align: right;"  disabled="true" value="${loanList[0].utilizeLoanAmount }" tabindex="-1"/> </td>
	       
	    </tr>
        <tr>
         <td><bean:message key="lbl.sanctionedDate" /></td>
		    <td >
		    	<html:text styleClass="text" property="sanctionDate" styleId="sanctionDate" readonly="true" value="${loanList[0].sanctionDate }" tabindex="-1"/> 
		    </td>
	         
          <td> <bean:message key="lbl.sanctionedValidTill" /> </td>
          <td>
          <html:text styleClass="text" property="sanctionedValidtill" styleId="sanctionedValidtill"   maxlength="10" disabled="true"  value="${loanList[0].sanctionedValidtill }" tabindex="-1"/>
              </td>
              </tr>
              <tr>
        <logic:present name="loanList">
		   <logic:notPresent name="editLoanMaker">
           <logic:iterate name="loanList" id="loanListOb">
             <logic:equal name="loanListOb" property="repaymentType" value="I">
             <logic:equal name="loanListOb"  property="assetFlag" value="A">
		          <td><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
		          <td><html:text styleClass="text" property="assetCost" styleId="assetCost"  style="text-align: right;"  disabled="true" value="${loanList[0].assetCost }" tabindex="-1"/></td>
		          <td><bean:message key="lbl.noOfAsset" /><font color="red">*</font></td>
           	  	  <td><html:text property="noOfAsset" styleClass="text" maxlength="5" style="text-align: right" value="${loanList[0].noOfAsset}" disabled="true" styleId="noOfAsset" /></td>	     
		        </logic:equal>
		      </logic:equal>
		   </logic:iterate>
			</logic:notPresent>	   
		   <logic:present name="editLoanMaker">
           <logic:iterate name="loanList" id="loanListOb">
             <logic:equal name="loanListOb" property="repaymentType" value="I">
             <logic:equal name="loanListOb"  property="assetFlag" value="A">
		          <td><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
		         <td> <html:text styleClass="text" property="assetCost" styleId="assetCost"  style="text-align: right;" value="${loanList[0].assetCost }" onchange="numberFormatting(this.id,'2');calculateMargin();calLtvPerc();changePlanRepayment();" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);calculateMargin();calLtvPerc();" /></td>
		          <td><bean:message key="lbl.noOfAsset" /><font color="red">*</font></td>
           	  	  <td><html:text property="noOfAsset" styleClass="text" maxlength="5" style="text-align: right" value="${loanList[0].noOfAsset}" styleId="noOfAsset" onkeypress="return isNumberKey(event);" onblur="changePlanRepayment();checkNoOfAsset();"/></td>	     
		        </logic:equal>
		      </logic:equal>
		   </logic:iterate>
		</logic:present>
		</logic:present>
        </tr>
        <tr>
     <logic:present name="loanList">
	    <logic:notPresent name="editLoanMaker">
       <logic:iterate name="loanList" id="loanListOb">
         <logic:equal name="loanListOb" property="repaymentType" value="I">
         <logic:equal name="loanListOb"  property="assetFlag" value="A">
          <td><bean:message key="lbl.margin" /> %</td>
          <td><html:text styleClass="text" property="margin" styleId="margin"  style="text-align: right;"  disabled="true" value="${loanList[0].margin }" tabindex="-1"/></td>
          <td><bean:message key="lbl.marginAmount"/></td>
          <td><html:text styleClass="text" property="marginAmount" styleId="marginAmount" disabled="true" style="text-align: right;"  readonly="true" value="${loanList[0].marginAmount }" tabindex="-1"/></td>
          </logic:equal>
         </logic:equal>
       </logic:iterate>
	    </logic:notPresent>
       <logic:present name="editLoanMaker">
       <logic:iterate name="loanList" id="loanListOb">
         <logic:equal name="loanListOb" property="repaymentType" value="I">
         <logic:equal name="loanListOb"  property="assetFlag" value="A">
          <td><bean:message key="lbl.margin" /> %</td>
          <td><html:text property="margin" style="text-align: right" styleClass="text"  value="${loanList[0].margin }" styleId="margin" readonly="true"    tabindex="-1" /></td>
          <td><bean:message key="lbl.marginAmount"/></td>
          <td><html:text styleClass="text" property="marginAmount" styleId="marginAmount"  style="text-align: right;"   value="${loanList[0].marginAmount }" onchange="numberFormatting(this.id,'2');calMarginPercentage();calLtvPerc();changePlanRepayment();" onkeypress="return numbersonly(event, id, 18)" onblur="formatNumber(this.value, id);" /></td>
          </logic:equal>
         </logic:equal>
       </logic:iterate>
       </logic:present>
     </logic:present>    
        </tr>
        <tr>
          <td><bean:message key="lbl.loanAmount" /></td>
          <td><html:text styleClass="text" property="requestedLoamt"  styleId="requestedLoamt" disabled="true"  style="text-align: right;" onchange="numberFormatting(this.id,'2');sumRequestedAmt1();calculateMargin();calLtvPerc();checkLoanAmount();" value="${loanList[0].requestedLoamt }"  tabindex="-1"/></td>
         <logic:notPresent name="editLoanMaker">
          <td><bean:message key="grd.applicationNo" /><font color="red">*</font></td>
          <td><html:text styleClass="text" property="formNo" styleId="formNo" onchange="" disabled="true" value="${loanList[0].formNo }"/></td>
		  </logic:notPresent>
          
          <logic:present name="editLoanMaker">
          <td><bean:message key="grd.applicationNo" /><font color="red">*</font></td>
         <td> <html:text styleClass="text" property="formNo" styleId="formNo" onchange="validateFormNo();changePlanRepayment();" value="${loanList[0].formNo }"/></td>
          </logic:present>
		</tr>
		<!-- amandeep changes for insurance -->
		  <tr>
          
           <td><bean:message key="lbl.insurancePremium" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="insurancePremium" styleClass="text" styleId="insurancePremium" readonly="true" style="text-align: right" value="${loanList[0].insurancePremium}" /></td>
		           
		           
		           	<td><bean:message key="lbl.amInsurance1" /><font color="red">*</font></td>
           <td ><html:text property="loanAmount"  maxlength="15" readonly="true" styleClass="text" style="text-align: right" value="${loanList[0].loanAmount}" styleId="loanAmount"  /></td>
 
	  			   
          
          </tr>
          
         <!--  amandeep changes for insurance ends -->
		  <tr>
		  <td><bean:message key="lbl.tenureMonths"/></td>
          <td><html:text styleClass="text" property="tenureMonths" styleId="tenureMonths"   maxlength="5"  style="text-align: right;"  disabled="true" value="${loanList[0].tenureMonths }" tabindex="-1" onblur="calcInstallment();" onchange="calcDay();calculateMaturityDateInLoan();" /></td>
		  
		  <logic:present name="loanList">

			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${loanList[0].tenureInDay}" styleId="tenureInDay" readonly="true" maxlength="4"/>
			</td>	
	         
	        </logic:present>
		  </tr>
		  <tr>
          <td><bean:message key="lbl.rateType" /></td>
          <td >
             <input type="text" name="showRateType" class="text" id="showRateType" disabled="disabled"  value="${loanList[0].showEffectiveRate }" tabindex="-1"/>
             <html:hidden property="rateType" styleClass="text" styleId="rateType" disabled="true" value="${loanList[0].rateType }" />
         
            </td>
          <td><bean:message key="lbl.rateMethod" /></td>
          <td>
                    
            <logic:present name="loanList">
            
            	<logic:iterate name="loanList" id="loanListOb">
             <logic:equal name="loanListOb" property="rateType" value="F">
	            <logic:equal name="loanListOb" property="dealRateMethod" value="F">
	        
	             <bean:message key="lbl.fixed" />
	              <input type="radio" name="dealRateMethod"  id="fixed" value="F" disabled="disabled" checked="checked" />
	               <bean:message key="lbl.floating" /> 
	               <input type="radio" name="dealRateMethod"  id="floating" value="L" disabled="disabled" tabindex="-1"/>
	           </logic:equal>
	            
	        	
		        <logic:equal name="loanListOb" property="dealRateMethod" value="L">
	              <bean:message key="lbl.fixed" />
	              <input type="radio" name="dealRateMethod"  id="fixed" disabled="disabled" value="F" />
	              <bean:message key="lbl.floating" /> 
	               <input type="radio" name="dealRateMethod"  id="floating" value="L" disabled="disabled"  tabindex="-1"/>
	             </logic:equal>
             <logic:equal name="loanListOb" property="dealRateMethod" value="">
	             <bean:message key="lbl.fixed" />
	              <input type="radio" name="dealRateMethod"  id="fixed" value="F"  disabled="disabled" />
	               <bean:message key="lbl.floating" /> 
	               <input type="radio" name="dealRateMethod"  id="floating" value="L" disabled="disabled"  tabindex="-1"/>
	           </logic:equal>
	           
	           
             </logic:equal>
              <logic:equal name="loanListOb" property="rateType" value="E">
             <logic:equal name="loanListOb" property="dealRateMethod" value="L">
              <bean:message key="lbl.fixed" />
              <input type="radio" name="dealRateMethod" disabled="disabled"  id="fixed" value="F" />
              <bean:message key="lbl.floating" /> 
               <input type="radio" name="dealRateMethod"  id="floating" value="L" disabled="disabled" checked="checked"  tabindex="-1"/>
             </logic:equal>
               <logic:equal name="loanListOb" property="dealRateMethod" value="F">
	        
	             <bean:message key="lbl.fixed" />
	              <input type="radio" name="dealRateMethod" disabled="disabled"  id="fixed" value="F" checked="checked" />
	               <bean:message key="lbl.floating" /> 
	               <input type="radio" name="dealRateMethod"  id="floating" value="L" disabled="disabled"  tabindex="-1"/>
	           </logic:equal>
             </logic:equal>
            </logic:iterate>
            </logic:present>
           
           
          </td>
             
          </tr>
          <!-- Nishant space starts -->	
          	<tr>
          		<td><bean:message key="lbl.fixPriod" /></td>
          		<td><html:text property="fixPriod" styleId="fixPriod" styleClass="text"  style="text-align: right" maxlength="3" disabled="true" value="${loanList[0].fixPriod}" onkeypress="return isNumberKey(event);"/> </td>
          	<logic:present name="loanList">
          	<logic:iterate name="loanList"  id="subloanList">
		   	<logic:equal name="subloanList"  property="repaymentType" value="I"> 
          	
          	<%-- <td id="creditPeriod1"><bean:message key="lbl.creditPeriod" /><font color="red">*</font></td> --%>
          	<td id="creditPeriod1"><bean:message key="lbl.creditPeriod" /><font color="red">*</font></td>
           <td ><html:text property="creditPeriod" styleClass="text" maxlength="5" value="${loanList[0].creditPeriod}" styleId="creditPeriod" onkeypress="return isNumberKey(event);" disabled="true"/></td>
          </logic:equal>
         </logic:iterate>
         </logic:present>
         <logic:notPresent name="loanList">
         
         <td id="creditPeriod1"><bean:message key="lbl.creditPeriod" /><font color="red">*</font></td>
           <td ><html:text property="creditPeriod" styleClass="text" maxlength="5" value="${loanList[0].creditPeriod}" styleId="creditPeriod" onkeypress="return isNumberKey(event);" disabled="true"/></td>
         
         
         </logic:notPresent> 
          	</tr>
          	<!-- Nishant space starts -->
		  <tr>
          <td><bean:message key="lbl.baseRateType" /></td>
          <td><label>
          
              <html:text property="baseRateType" styleClass="text" styleId="baseRateType" disabled="true" value="${loanList[0].baseRateType }" tabindex="-1"/>
          
          </label></td>
          <td><bean:message key="lbl.baseRate" /></td>
          <td><html:text styleClass="text" property="baseRate" styleId="baseRate" value="${loanList[0].baseRate }" style="text-align: right;" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" disabled="true" tabindex="-1"/></td></tr>
		  <tr>
		    <td><bean:message key="lbl.markUp" /></td>
		    <td><html:text styleClass="text" property="markUp" styleId="markUp" value="${loanList[0].markUp }" maxlength="100" disabled="true" tabindex="-1"/></td>
		    <td><bean:message key="lbl.effectiveRate"/></td>
		    <td><html:text styleClass="text" property="effectiveRate" styleId="effectiveRate" disabled="true" value="${loanList[0].effectiveRate }" style="text-align: right;" onkeypress="return numbersonly(event,id,3)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 3,id);" onfocus="keyUpNumber(this.value, event, 3,id);" readonly="true" tabindex="-1"/></td>
		    </tr>
		  <tr>
		    <td><bean:message key="lbl.repaymentType" /></td>
		    <td>
		      <input type="text" name="showRepaymentType" class="text" id="showRepaymentType" disabled="disabled" value="${loanList[0].showRepayment }" tabindex="-1"/>
		       <html:hidden property="repaymentType" styleClass="text" styleId="repaymentType" disabled="true"  value="${loanList[0].repaymentType }" />
		    </td>
	<logic:present name="loanList">
       <logic:iterate name="loanList" id="loanListOb">
         <logic:equal name="loanListOb" property="repaymentType" value="I">
		    <td><bean:message key="lbl.installmentType" /></td>
		    <td>
		    	    <logic:notPresent name="editable">
			    		<input type="text" name="showInstallmentType" class="text" id="showInstallmentType" readonly="readonly" value="${loanList[0].showInstallment }" tabindex="-1"/>
			    		<html:hidden property="installmentType"  styleClass="text"styleId="installmentType"  value="${loanList[0].installmentType }"/>
			    	</logic:notPresent>
			    	<logic:present name="editable">	
			    	    <input type="hidden" name="showInstallmentType" class="text" id="showInstallmentType" value="${loanList[0].showInstallment }"/>		    	
			    		<html:select property="installmentType" styleId="installmentType" styleClass="text" disabled="true" value="${loanList[0].installmentType }" onchange="editNoInstal();">
				  		<option value="">--<bean:message key="lbl.select" />--</option>
				  		<logic:present name="installmentTypeList">
				  		<logic:notEmpty name="installmentTypeList">
				  		<html:optionsCollection name="installmentTypeList" label="name" value="id" />
				  		</logic:notEmpty>
				  		</logic:present>
				  		</html:select>	
				  	</logic:present>
		   </td>
		  </logic:equal>
		</logic:iterate>
 </logic:present>		   
		    </tr>
		  <tr>
	  <logic:present name="loanList">
       <logic:iterate name="loanList" id="loanListOb">
         <logic:equal name="loanListOb" property="repaymentType" value="I">
		    <td><bean:message key="lbl.frequency" /><font color="red">*</font></td>
		    <td>
		    <input type="text" name="showFrequency" class="text" id="showFrequency" disabled="disabled" value="${loanList[0].showFrequency }"/>
		      <html:hidden property="frequency" styleClass="text" styleId="frequency" disabled="true"   value="${loanList[0].frequency }"/>
		    </td>
		    <td><bean:message key="lbl.repaymentMode" /></td>
		    <td>		    
		     		<logic:notPresent name="editable">
			    		<input type="text" name="showRepaymentMode" class="text" id="showRepaymentMode" readonly="readonly" value="${loanList[0].showRepayMode }" tabindex="-1"/>
			     		<html:hidden property="repaymentMode" styleClass="text" styleId="repaymentMode"  value="${loanList[0].repaymentMode }"/>
			    	</logic:notPresent>
			    	<logic:present name="editable">
			    		<input type="hidden" name="showRepaymentMode" class="text" id="showRepaymentMode" value="${loanList[0].showRepayMode }"/>
			    		<html:select property="repaymentMode" styleClass="text" disabled="true" styleId="repaymentMode" value="${loanList[0].repaymentMode }">
	              		  <html:option value="">--Select--</html:option>
	              			<logic:present name="paymentModes">
	              			<logic:notEmpty name="paymentModes">
	              			<html:optionsCollection name="paymentModes" label="paymentMode" value="paymentModeId" />
	              			</logic:notEmpty>
	              			</logic:present>
	          			</html:select>
			    	</logic:present>	
		   </td>
		 </logic:equal>
		 </logic:iterate>
		 </logic:present>     
		   
		    </tr>
		     <logic:present name="loanList">
		    <logic:iterate name="loanList"  id="subloanList">
		    	<logic:equal name="subloanList"  property="repaymentType" value="I">
		    	<tr>
		  	       <td id="insComFre1"><bean:message key="lbl.interestCompoundingFrequency" /><font color="red">*</font></td>
		    <td id="insComFre2"><html:select property="interestCompoundingFrequency" styleClass="text" disabled="disabled" styleId="interestCompoundingFrequency" value="${loanList[0].interestCompoundingFrequency}" onchange="validateIntCompoundingFrequency();">
             <%--  <html:option value="">---Select----</html:option> --%>
              <html:option value="">NOT APPLICABLE</html:option>
	              <html:option value="M">MONTHLY</html:option>
			      <html:option value="B">BIMONTHLY</html:option>
			      <html:option value="Q">QUARTERLY</html:option>
			      <html:option value="H">HALFYERALY</html:option>
			      <html:option value="Y">YEARLY</html:option>
			    <%--    <logic:present name="intComFreq">
		               <html:optionsCollection name="intComFreq" label="name" value="id" /> 
		       </logic:present> --%>
         	  </html:select>
            </td>
		  
		    <td id="insCalMet1"><bean:message key="lbl.interestCalculationMethod" /><font color="red">*</font></td>
		    <td id="insCalMet2"><html:select property="interestCalculationMethod" styleClass="text" disabled="disabled" styleId="interestCalculationMethod" value="${loanList[0].interestCalculationMethod}" onchange="showInterestDueDate(true);setInterestDueDate();showDueDay();nullNextDue();getDueDay();">" >
            <%-- <html:option value="">---Select----</html:option> --%>
             <%--    <html:option value="D">DUE DATE</html:option>
		      <html:option value="E">EOM</html:option>
		      <html:option value="F">FEOM</html:option> --%>
		    
		       <logic:present name="intCal">
		               <html:optionsCollection name="intCal" label="name" value="id" /> 
		       </logic:present> 
          </html:select></td>
		   
		    </tr>
		    
		      <tr>
		  
				  <td id="insFre1"><bean:message key="lbl.interestFrequency" /><font color="red">*</font></td>
				    <td id="insFre2"><html:select property="interestFrequency" styleClass="text"  disabled="disabled" styleId="interestFrequency" value="${loanList[0].interestFrequency}" onchange="setInterestDueDate();validateInterestFrequency();">
		            <%--   <html:option value="">---Select----</html:option> --%>
			              <html:option value="M">MONTHLY</html:option>
					      <html:option value="B">BIMONTHLY</html:option>
					      <html:option value="Q">QUARTERLY</html:option>
					      <html:option value="H">HALFYERALY</html:option>
					      <html:option value="Y">YEARLY</html:option>
					      <%--  <html:option value="X">FINANCIAL QUARTERLY</html:option>
					          <html:option value="L">FINANCIAL HALFYERALY</html:option>
					            <html:option value="Z">FINANCIAL YEARLY</html:option> --%>
					     <%--   <logic:present name="intFreq">
		             			  <html:optionsCollection name="intFreq" label="name" value="id" /> 
		       				</logic:present> --%>
		         	  </html:select>
		            </td>
		    </tr>
		  	     
		  	     
		  	   
		     </logic:equal>
		  </logic:iterate>
	 </logic:present>
		    
		    
		    <tr>
 	  <logic:present name="loanList">
       <logic:iterate name="loanList" id="loanListOb">
         <logic:equal name="loanListOb" property="repaymentType" value="I">
		    <td><bean:message key="lbl.installmentMode" /></td>
		    <td>		    
		     	<logic:notPresent name="editable">
					<input type="text" name="showInstallmentMode" class="text" id="showInstallmentMode" readonly="readonly" value="${loanList[0].showInstMode }" tabindex="-1"/>
					<html:hidden property="dealInstallmentMode" styleClass="text" styleId="dealInstallmentMode"  value="${loanList[0].dealInstallmentMode }"/>
				</logic:notPresent>	
				<logic:present name="editable">
						<input type="hidden" name="showInstallmentMode" class="text" id="showInstallmentMode" value="${loanList[0].showInstMode }"/>
						<html:select property="dealInstallmentMode" styleClass="text" styleId="dealInstallmentMode" disabled="true" value="${loanList[0].dealInstallmentMode }">
                   		<html:option value="">---Select----</html:option>
                   		<html:option value="A">ADVANCE</html:option>
		           		<html:option value="R">ARREAR</html:option>
                		</html:select>							
				</logic:present>
		    </td>
		    
		    <td><bean:message key="lbl.totalIns" /></td>
		        <td><html:text styleClass="text" property="loanNoofInstall" styleId="loanNoofInstall" value="${loanList[0].loanNoofInstall }" style="text-align: right" disabled="true" maxlength="5" /></td>
		   </logic:equal>
		 </logic:iterate>
	</logic:present>	    
		    </tr>
		    
		      <tr>
    <logic:present name="loanList">
       <logic:iterate name="loanList" id="loanListOb">
         <logic:equal name="loanListOb" property="repaymentType" value="I">	 
		     <td><bean:message key="lbl.noIns" /></td>
		    <td><html:text styleClass="text" property="installments" disabled="true" style="text-align: right" styleId="installments" value="${loanList[0].installments }" maxlength="5" onchange="checkInstallments();"/></td>
		     <td><bean:message key="lbl.cycleDate" /><font color="red">*</font></td>
		    <td >
		    <logic:present name="LoanCycle">   			
		    	<html:select property="dueDay" styleClass="text" disabled="true" styleId="dueDay" value="${loanList[0].dueDay}"  onchange="nullNextDue();getDueDay(id);">
              	<html:option value="">---Select----</html:option>
              	<html:optionsCollection name="LoanCycle" label="name" value="id" /> 
	          	</html:select>
		   </logic:present>
		   <logic:notPresent name="LoanCycle">
		   		<html:select property="dueDay" styleClass="text" disabled="true" styleId="dueDay" value="${loanList[0].dueDay}"  onchange="nullNextDue();getDueDay(id);">
              	<html:option value="">---Select----</html:option>
              	<logic:present name="cycle">
	               <html:optionsCollection name="cycle" label="name" value="id" /> 
	            </logic:present>
		   		</html:select>
		   </logic:notPresent>

		    </td>
		 </logic:equal>
		 </logic:iterate>
		 </logic:present>   
		    </tr>
		    <logic:present name="loanList">
           <logic:iterate name="loanList" id="loanListOb">
             <logic:equal name="loanListOb" property="repaymentType" value="I">
			    <tr>
			    	<td><bean:message key="lbl.nextDueDate" /> <font color="red">*</font></td>
			    <td >
			         <html:text property="nextDueDate" styleClass="text" disabled="true"  value="${loanList[0].nextDueDate}" styleId="nextdate" onchange="" />
			    </td>
				     <!-- Ajay WORK starts -->
			
<td id="interestDueDateHeader1" style="display:none;"> <bean:message key="lbl.interestDueDate"/><font color="red">*</font></td>
              		 <td id = "interestDueDateHeader2" style="display:none;">
						<logic:notPresent name="editable">
							<html:text styleClass="text" property="interestDueDate" styleId="interestDueDate"  value="${loanList[0].interestDueDate}"  onchange="datevalidate();validateInterestDueDate();"/>
<%-- 							<html:text styleClass="text" property="interestDueDate" styleId="interestDueDate1" style="display:none" readonly="true" value="${loanList[0].interestDueDate}"/> --%>
						</logic:notPresent>
						<logic:present name="editable">
							<html:text styleClass="text" property="interestDueDate" value="${loanList[0].interestDueDate}"  styleId="interestDueDate"  onchange="datevalidate();validateInterestDueDate();"/>
<%-- 							<html:text styleClass="text" property="interestDueDate" styleId="interestDueDate1" style="display:none" value="${loanList[0].interestDueDate}" readonly="true"/> --%>
						</logic:present>
					</td>
					 
     <!-- Ajay WORK ENDS -->
			    </tr>
		    </logic:equal>
		    </logic:iterate>
		    </logic:present>
		       		  <!-- Code Added for the Handling separate Interest first due date and maturity date| Rahul papneja| 31012018 -->
				  <tr id="SeparateInterestDate" style="display:none">
				  <!-- Start|01 | Code Added for firstInterestDueDate Handling till <td> tag closed -->
				  <td id="firstInterestDueDateLBL" style="display:none"><bean:message key="lbl.firstInterestDueDate"/></td>
				  <td id = "firstInterestDueDateTXT" style="display:none">
						<logic:notPresent name="loanList">
							<html:text styleClass="text" property="firstInterestDueDate" styleId="firstInterestDueDate"  onchange="datevalidate();" />
							
						</logic:notPresent>
						<logic:present name="loanList">
						
							<html:text styleClass="text" property="firstInterestDueDate" value="${loanList[0].firstInterestDueDate}" disabled="true"  styleId="firstInterestDueDate"  onchange="datevalidate();" />
						
							
						</logic:present>
					</td>
					<!-- 01| End -->

				    <!-- Start|02 | Code Added for maturityDate Handling till <td> tag closed -->
				  <td id="maturityDateLBL"><bean:message key="lbl.maturityDate"/><font color="red">*</font></td>
				  <td id = "maturityDateTXT">
						<logic:notPresent name="loanList">
							<html:text styleClass="text" property="maturityDate" styleId="maturityDate1"  onblur="setMaturityTemp();" onchange="calTenureMonthForMaturityDate();validateMaturityDate();" />
							
						</logic:notPresent>
						<logic:present name="loanList">
						
							<html:text styleClass="text" property="maturityDate" value="${loanList[0].maturityDate}" disabled="true"  onblur="setMaturityTemp();" styleId="maturityDate1"  onchange="calTenureMonthForMaturityDate();validateMaturityDate();datevalidate();" />
						
							
						</logic:present>
					</td>
					<!-- 02 | End -->
				  
				  
				  
				  
				  </tr>
				  <!-- Ends Here| Rahul papneja -->
		       <tr>
		    <td><bean:message key="lbl.agreementDate" /></td>
		    <td><html:text styleClass="text" property="agreementDate" disabled="true"  value="${loanList[0].agreementDate }" maxlength="10" onchange=""/></td>
		    <logic:present name="loanList">
           <logic:iterate name="loanList" id="loanListOb">
             <logic:equal name="loanListOb" property="repaymentType" value="I">
		    <td><bean:message key="lbl.effDate"/></td>
		    <td><html:text styleClass="text" property="repayEffectiveDate"  value="${loanList[0].repayEffectiveDate }" disabled="true" maxlength="10" /></td>
		    </logic:equal>
		    </logic:iterate>
		    </logic:present>
		    </tr>
			<%--<tr>
			
	 <logic:present name="loanList">
       <logic:iterate name="loanList" id="loanListOb">
         <logic:equal name="loanListOb" property="repaymentType" value="I">	 
			 <td><bean:message key="lbl.typeOfDisbursal" /></td>
		    <td><html:select property="typeOfDisbursal" styleClass="text" disabled="true" styleId="typeOfDisbursal" value="${loanList[0].typeOfDisbursal }" onchange="enableDisbursal();">
              	   
              	      <html:option  value="S">Single</html:option>
		      	      <html:option value="M">Multiple</html:option>
		      </html:select></td>
		    <td><bean:message key="lbl.noOfDisbursal" /></td>
		    <td>
		   <div id="disId" style="display: none;">  
		   <html:text property="noOfDisbursal" styleId="noOfDisbursal" disabled="true" maxlength="3" styleClass="text" value="${loanList[0].noOfDisbursal }" style="text-align: right" onkeypress="return numbersonly(event,id,18)" />  
<!--		    <html:select property="noOfDisbursal" styleId="noOfDisbursal" disabled="true" styleClass="text" value="${loanList[0].noOfDisbursal }">-->
<!--		          -->
<!--		            <html:option value="2">2</html:option>-->
<!--	                <html:option value="3">3</html:option>-->
<!--			        <html:option value="4">4</html:option>-->
<!--		          </html:select> -->
		      </div>
		  <div id="disSingle">    
		        <html:text property="noOfDisbursal" styleClass="text" styleId="noOfDisbursal" style="text-align: right" disabled="true" value="1" value="${loanList[0].noOfDisbursal }"/>
		      </div>
		      </td>
		    </logic:equal>
		   </logic:iterate>
		  </logic:present>  
		      </tr>
 --%>
		    <tr>
		    	
		       <%--  <td><bean:message key="lbl.noIns" /></td>
		    <td><html:text styleClass="text" property="installments" styleId="installments" value="${loanList[0].installments }" style="text-align: right" disabled="true" maxlength="5" onchange="checkInstallments();"/></td>
		    --%>
		     <td><bean:message key="lbl.sectorType"/><font color="red">*</font></td>
		    <td>
			<logic:notPresent name="editLoanMaker">
		    	 <logic:notPresent name="editable">
			    	<input type="text" name="showSectorType" class="text"  id="showSectorType" readonly="readonly" value="${loanList[0].showSectorType }" disabled="disabled" tabindex="-1"/>
		          	<html:hidden property="sectorType" styleClass="text"  styleId="sectorType"  value="${loanList[0].sectorType }"/>
			    </logic:notPresent>
			    <logic:present name="editable">
			    	<input type="hidden" name="showSectorType" class="text"  id="showSectorType" readonly="readonly" value="${loanList[0].showSectorType }" disabled="disabled" tabindex="-1"/>
				    <html:select property="sectorType" styleClass="text" styleId="sectorType" value="${loanList[0].sectorType}" disabled="true">
	             	<html:option value="">---Select----</html:option>
	             		<logic:present name="sector">
	               		<html:optionsCollection name="sector" label="name" value="id" /> 
	             		</logic:present>
	             	</html:select>
			    </logic:present>
			    </logic:notPresent>
			    <logic:present name="editLoanMaker">
			    	<input type="hidden" name="showSectorType" class="text"  id="showSectorType" readonly="readonly" value="${loanList[0].showSectorType }" onchange="changePlanRepayment();" />
				    <html:select property="sectorType" styleClass="text" styleId="sectorType" value="${loanList[0].sectorType}" >
	             	<html:option value="">---Select----</html:option>
	             		<logic:present name="sector">
	               		<html:optionsCollection name="sector" label="name" value="id" /> 
	             		</logic:present>
	             	</html:select>
			    </logic:present>	
		    </td>
	 <logic:present name="loanList">
       <logic:iterate name="loanList" id="loanListOb">
         <logic:equal name="loanListOb" property="repaymentType" value="I">	       
		   <td><bean:message key="lbl.podo" /><font color="red">*</font></td>
		   	<logic:present name="editLoanMaker">
		      <td>
		      <html:select property="podoFlag" styleClass="text" styleId="podoFlag" value="${loanList[0].podoFlag }" onchange="enableDisbursal();changePlanRepayment();">
		      	 	 <html:option  value="N">None</html:option>
              	      <html:option  value="P">PO</html:option>
		      	      <html:option value="D">DO</html:option>
		      </html:select>
		      
		      </td>
		      </logic:present>
		      <logic:notPresent name="editLoanMaker">
		      <td>
		      	 <html:select property="podoFlag" styleClass="text" disabled="true" styleId="podoFlag" value="${loanList[0].podoFlag }" onchange="enableDisbursal();">
		      	 	 <html:option  value="N">None</html:option>
              	      <html:option  value="P">PO</html:option>
		      	      <html:option value="D">DO</html:option>
		      </html:select>
		      </td>
			</logic:notPresent>  
		   </logic:equal>
		</logic:iterate>
	</logic:present> 
		    </tr>
<!--		   @Surendra Code Start	   -->
		    <tr >
              <logic:present name="loanList">
                  <logic:iterate id="subloanList" name="loanList" length="1">
                    <logic:equal name="subloanList" property="repaymentType" value="N" >
		        <td ><bean:message key="lbl.interestCalc" /><font color="red"></font></td>
		    	 <td >	
		    	  <html:text property="showInterestCalc" styleClass="text" styleId="showInterestCalc" value="${loanList[0].showInterestCalc}" readonly="true" tabindex="-1" />	    	
	            <html:hidden property="interestCalc" styleClass="text" styleId="interestCalc" value="${loanList[0].interestCalc}"  />
	            	          
	         </td>
	          </logic:equal>
	             </logic:iterate>
	             </logic:present>
	             <logic:notPresent name="loanList">	                       
	             <td >
	             <div id="int1" style="display:none">
	             <bean:message key="lbl.interestCalc" /><font color="red">*</font></div></td>
	              <td>	<div id="int2" style="display:none">
	            <html:text property="showInterestCalc" styleClass="text" styleId="showInterestCalc" value="${loanList[0].showInterestCalc}" readonly="true" tabindex="-1" />  	    	
	            <html:hidden property="interestCalc" styleClass="text" styleId="interestCalc" value="${loanList[0].interestCalc}" />	
	          </div>          
	         </td> 
	             </logic:notPresent>
	             
	         </tr>       
<!--		   @Surendra Code End	   -->	
		    <tr>
		  <logic:present name="loanList">
       <logic:iterate name="loanList" id="loanListOb">
         <logic:equal name="loanListOb" property="repaymentType" value="I">	 
		   		  <td><bean:message key="lbl.repaySMKTIRR2" /></td>
		    <td >
		    	 <html:text property="iir2" styleClass="text" styleId="iir2" readonly="true" value="${loanList[0].dealIrr2 }" tabindex="-1"/>
		    </td>
	     </logic:equal>
	     </logic:iterate>
	        		  <!-- Code Added for Edit Due Date | Rahul papneja | 30012018 -->
			       <!-- Vishal changes start -->
			<td>
			<bean:message key="lbl.editDueDate"/><font color="red">*</font></td>
			<td>
			<html:select property="editDueDate" styleId="editDueDate"  disabled="true"  styleClass="text" value="${loanList[0].editDueDate}">
			<html:option value="N">NO</html:option>
			<html:option value="Y">Yes</html:option>
			<!-- <html:option value="N">NO</html:option> -->
			</html:select>
			</td>
<!-- 	   Vishal changes end --> 
			  <!-- Ends Here -->
	     </logic:present>  
	     
	     
		   </tr>
		   
		   <tr>
		 	  <logic:present name="loanList">
       <logic:iterate name="loanList" id="loanListOb">
         <logic:equal name="loanListOb" property="repaymentType" value="I">	   
		   		  <td><bean:message key="lbl.insurancedoneby" /></td>
		<logic:notPresent name="editLoanMaker">	
		   		
		    <td >
		    	 
		      	 <html:select property="insuranceDoneBy" styleClass="text" disabled="true"  styleId="insuranceDoneBy" value="${loanList[0].insuranceDoneBy }" >
		      	 <html:option  value="">---Select---</html:option>
		      	 	 <html:option  value="B">BORROWER</html:option>
              	     <html:option  value="L">LENDER</html:option>
              	     <html:option  value="D">DEALER</html:option>
		      	</html:select>
		    
	         </td>
			 </logic:notPresent>
	         <logic:present name="editLoanMaker">
	          <td >
		    	 
				<html:select property="insuranceDoneBy" styleClass="text" styleId="insuranceDoneBy" value="${loanList[0].insuranceDoneBy }" onchange="changePlanRepayment();" >
		      	 	<html:option  value="">---Select---</html:option>
		      	 	 <html:option  value="B">BORROWER</html:option>
              	     <html:option  value="L">LENDER</html:option>
              	     <html:option  value="D">DEALER</html:option>
		      	</html:select>
		    
	         </td>
	         </logic:present>
	     </logic:equal>
	     </logic:iterate>
	     </logic:present>  
	     
	     <logic:present name="loanList">
		        <logic:iterate name="loanList"  id="subloanList">
		            <logic:equal name="subloanList"  property="repaymentType" value="I">

              		<logic:equal name="subloanList" property="assetFlag" value="A">
				        <td><bean:message key="lbl.LTV" /></td>
					    <td>
					         	<html:text property="ltvPerc" styleClass="text"  value="${loanList[0].ltvPerc}" styleId="ltvPerc"  readonly="true" />
					    </td>
					   </logic:equal>
				    </logic:equal>
	         </logic:iterate>
	         
	         </logic:present>
	      
		   </tr>
		   <tr>
		   		<td><bean:message key="lbl.areaCode" /></td> 
				<logic:notPresent name="editLoanMaker">
				<td>    
			   			<html:text property="areaCodename" styleId="areaCodename" styleClass="text" readonly="true" value="${loanList[0].areaCodename}"/>
			   		    <html:hidden  property="lbxareaCodeVal" styleId="lbxareaCodeVal" value="${loanList[0].lbxareaCodeVal}"  />
			    </td>
				</logic:notPresent>
			    <logic:present name="editLoanMaker">
			    <td>    
			  		<html:text property="areaCodename" styleId="areaCodename" styleClass="text" readonly="readonly" value="${loanList[0].areaCodename}"/>
			   		<html:button property="areaCodeButton" styleId="areaCodeButton" onclick="openLOVCommon(370,'leadCapturingDetails','areaCodename','','', '','','','lbxareaCodeVal');" onchange="changePlanRepayment();" value=" " styleClass="lovbutton"></html:button>
			     	<html:hidden  property="lbxareaCodeVal" styleId="lbxareaCodeVal" value="${loanList[0].lbxareaCodeVal}"  />	
			    </td>
			    </logic:present>
			     <td><bean:message key="lbl.loanClassification" /></td>
			<td>
			
			<html:hidden  property="lbxLoanClassification" styleId="lbxLoanClassification" value="${loanList[0].lbxLoanClassification}" />
			<html:select property="loanClassification" styleId="loanClassification" size="5" disabled="true" multiple="multiple" style="width: 120" >
   			<logic:present name="loanClassificationList">
   			<logic:notEmpty name="loanClassificationList">
       			 <html:optionsCollection name="loanClassificationList" value="loanClassificationId" label="loanClassificationLabel"/>
       		</logic:notEmpty>
       		</logic:present>
     		</html:select>
      	   
		      
		   
	</td>
	 
		</tr>    
		  
			       
			  <!--		    start by sachin-->
			  
			   <tr>
			<logic:present name="loanList">
             <logic:iterate name="loanList"  id="subloanList">
		      <logic:equal name="subloanList"  property="repaymentType" value="I">
		       <logic:equal name="subloanList" property="assetFlag" value="A">
			<td><bean:message key="lbl.netLtv" /></td>
			<td>
			<html:text property="netLtv" styleClass="text"  value="${loanList[0].netLtv}" styleId="netLtv"  readonly="true" />
			</td>
			 <td><bean:message key="lbl.totalVatAmt" /></td>
		   <td>
		   <html:text property="totalVatAmt" styleClass="text"  value="${loanList[0].totalVatAmt}" styleId="totalVatAmt"  readonly="true" />
		   </td>  
			</logic:equal>
			</logic:equal>
			</logic:iterate>		         
	        </logic:present>
	     
	        </tr>
		   
		        
<!--	end by sachin	      -->     
		     <tr>
		    	<td><bean:message key="lbl.makerRemarks" /><!-- <font color="red">*</font> --></td>
		        <td><html:textarea property="remarks" readonly="true" styleClass="text" styleId="remarks" value="${loanList[0].remarks }"></html:textarea></td>
		       <%--  <td><bean:message key="lbl.noIns" /></td>
		    <td><html:text styleClass="text" property="installments" style="text-align: right" styleId="installments" value="${loanList[0].installments }" maxlength="5" onchange="checkInstallments();"/></td>
		    
		    --%>
		    
		    <td><bean:message key="lbl.authorRemarks" /></td>
		      <td>
		      	<html:textarea property="authorRemarks" readonly="true" styleClass="text" styleId="authorRemarks" value="${loanList[0].authorRemarks }"></html:textarea>
		      </td>
		    </tr>

		    <tr>
           		<td><bean:message key="lbl.editLoanAuthor" /></td>
		   		<td><html:textarea property="editRemarks" readonly="true" styleClass="text" styleId="editRemarks" value="${loanList[0].editRemarks}"></html:textarea></td>
           	 <!-- Amit Space starts -->
	         <td><bean:message key="lbl.businessType" /></td>
			 <td >
	            <html:select property="businessType" styleClass="text" styleId="businessType1"  disabled="true" value="${loanList[0].businessType}" onclick="return getbusinessbutton();">
	             	<html:option value="">---Select---</html:option>
	             		<logic:present name="getBusiness">
	               			<html:optionsCollection name="getBusiness" label="businessdesc" value="businessId" /> 
	             		</logic:present>
	          	</html:select>
	          	 <input type="hidden" name="oldPartnerType" id="oldPartnerType" value="${loanList[0].businessType}" /> 
	          	 <input type="hidden" name="businessType" id="businessType" value="${loanList[0].businessType}" />    
         	</td>
			<!-- Amit space ends -->
           	</tr>
			<tr>
					   		
		   		<logic:present name="editLoanAuthor">
		   		<td><bean:message key="lbl.servicingBranch" /></td> 
				<td>    
			   			<html:text property="servicingBranch" styleId="servicingBranch" styleClass="text" readonly="true" value="${loanList[0].servicingBranch}"/>
			   		    <html:hidden  property="lbxservicingBranch" styleId="lbxservicingBranch" value="${loanList[0].lbxBranchId}"  />
			    </td>
			    </logic:present>
            <logic:present name="editLoanMaker">
			    <td><bean:message key="lbl.servicingBranch" /></td> 
			    <td>    
			  		<html:text property="servicingBranch" styleId="servicingBranch" styleClass="text" readonly="readonly" value="${loanList[0].servicingBranch}"/>
			   		<html:button property="servicingBranchButton" styleId="servicingBranchButton" onclick="openLOVCommon(148,'sourcingForm','servicingBranch','userId','lbxBranchId', 'userId','','','lbxBranchId');" onchange="changePlanRepayment();" value=" " styleClass="lovbutton"></html:button>
			     	<html:hidden  property="lbxBranchId" styleId="lbxBranchId" value="${loanList[0].lbxBranchId}"  />	
			    </td>
		    </logic:present> 
				</tr>
             <tr>
			  <td><bean:message key="lbl.grossBlock" /></td>
          	 <td ><html:text property="grossBlock" styleClass="text" maxlength="10" style="text-align: right" value="${loanList[0].grossBlock}" styleId="grossBlock"  disabled="true"  onkeypress="return isNumberKey(event);" /></td>
			 
			 <td><bean:message key="lbl.netBlock" /></td>
          	 <td ><html:text property="netBlock" styleClass="text" maxlength="10" style="text-align: right" value="${loanList[0].netBlock}" styleId="netBlock"    disabled="true"  onkeypress="return isNumberKey(event);" /></td>
          	 
			  </tr>
		    <tr>
		      <td>
			    <logic:present name="editLoanMaker">
			    <button type="button" name="button"   id="saveButton" class="topformbutton2" onclick="saveLoanInCMForEdit();" title="Alt+V" accesskey="V" ><bean:message key="button.save" /></button>
			   <button type="button" name="button"  title="Alt+F" accesskey="F" id="forwardButton" class="topformbutton2" onclick="return editLoanForward();"> <bean:message key="button.forward" /></button>		
		    </logic:present> 
		    <tr>
		      <td>
                <button type="button" class="topformbutton2"  id="deal_viewer"  title="Alt+D" accesskey="D" onclick="dealViewerLoanInitiation();"><bean:message key="button.dealViewer" /></button>
                <button type="button" class="topformbutton4" id="Save" title="Alt+V" accesskey="V" onclick="openPopForSectorDetails('Y');"><bean:message key="button.sectorTypeDetails" /></button>
              </td>
              <td id ="partner_mode" style="display: none">  
             	 <button type="button" class="topformbutton4" id="partner" title="Alt+P" accesskey="P" onclick="getbusinessType();"><bean:message key="lbl.partnerDetails" /></button>
		      </td>
            </tr>
		  </table>
		  </td></tr>
		  
</Tbody></table>

 </fieldset>
 
  </html:form>

   <logic:present name="editSms">

 <script type="text/javascript">
 
	if('<%=request.getAttribute("editSms")%>'=='S')
	{
		alert("Data Saved Successfully");
	}
	else if('<%=request.getAttribute("editSms")%>'=='E')
	{	
		alert("Data Not saved");
	}
	</script>
	</logic:present>

<logic:present name="editError">
<script type="text/javascript">
	alert('${editError}');
</script>
</logic:present>
  <logic:present name="editSuccess">
	<script type="text/javascript">
 	alert("<bean:message key="lbl.forwardSuccess" />");
 	parent.parent.location="<%=request.getContextPath()%>/editLoanDetailMaker.do?method=defaultEditLoanMaker";
</script>
</logic:present>

</logic:present>
</div>
<script>

	setFramevalues("sourcingForm");
</script>
  </body>
</html:html>
