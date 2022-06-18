<%--
      Author Name-      Prashant Kumar
      Date of creation -15/04/2011
      Purpose-          Entry of Loan Detail
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		

        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
   	  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.simpletip-1.3.1.js"></script>
   	  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/LoanDetails.js" ></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
 		 <link type="text/css" href="<%=request.getContextPath() %>/css/toolTip.css" rel="stylesheet" />
		
 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js" ></script>
 	
 	<!--jquery and js for number formatting -->
  	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.emicalculator.min.js"></script>
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/globalize.min.js"></script>
    <!-- jquery and js for number formatting -->

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
<body oncontextmenu="return false;" onload="showValues();getbusinessbutton();sumRequestedAmt();rangeAmtMar();validateReqLAmt();calLtvPerc();setMaturityTemp();showValues();editNoInstal();showInterestDueDate(false);enableAnchor();removeDueDate();enableInstallNo();checkChanges('LoanDetailsForm');calcDay();" onunload="closeAllLovCallUnloadBody();">
<div  align="center" class="opacity" style="display:none" id="processingImage" ></div>

<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>

<input type="hidden" id="checkNewRepay" name="checkNewRepay" value="<%=path %>"/>

<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
<input type="hidden" id="diffDayCount" name="diffDayCount" value="${diffDayCount }"/>
<input type="hidden" name="OldMaturityDate1" id="OldMaturityDate1" value="" />	<%-- adde by brijesh pathak --%>
			
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>

<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />



<div id=centercolumn>
<div id=revisedcontainer>
<fieldset>
  <table cellspacing="0" cellpadding="0" width="100%" border="0">
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${dealHeader[0].dealNo}</td>
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
	          <td >Deal Capturing</td>
          </tr>
        </table> 
</td>
</tr>
</table>
</fieldset>
<logic:notPresent name="viewDeal">

<html:form action="/loanProcessAction" method="post" styleId="LoanDetailsForm">

	<logic:notPresent name="leadDetail">
	
	<!-- ankita -->
	<input type="hidden" id="minFinance" name="minFinance" value="${requestScope.allDetail[0].businessType}"/>
	<input type="hidden" id="dealId" name="dealId" value="${dealHeader[0].dealId}"/>
	<input type="hidden" id="dealProduct" name="dealProduct" value="${dealHeader[0].dealProduct}"/>
	<input type="hidden" id="dealLoanIds" name="dealLoanIds" value="${requestScope.allDetail[0].dealLoanId}"/>	
	<input type="hidden" id="minFinance" name="minFinance" value="${requestScope.allDetail[0].minFinanceAmount}"/>
	<input type="hidden" id="maxFinance" name="maxFinance" value="${requestScope.allDetail[0].maxFinanceAmount}" />
	<input type="hidden" id="minMarginRate" name="minMarginRate" value="${requestScope.allDetail[0].minMRate}"/>
	<input type="hidden" id="maxMarginRate" name="maxMarginRate" value="${requestScope.allDetail[0].maxMRate}" />
	<input type="hidden" id="minTenure" name="minTenure" value="${requestScope.allDetail[0].minTenure}"/>
	<input type="hidden" id="maxTenure" name="maxTenure" value="${requestScope.allDetail[0].maxTenure}"/>
	<html:hidden styleClass="text" property="repaymentType" styleId="repaymentType" value="${requestScope.allDetail[0].repaymentType }"   />

	<html:hidden styleClass="text" property="rateMethodType" styleId="rateMethodType" value="${requestScope.allDetail[0].rateMethodType }"   />
	
	<input type="hidden" id="minFlatRate" name="minFlatRate" value="${requestScope.allDetail[0].minFlatRate}"/>
	<input type="hidden" id="maxFlatRate" name="maxFlatRate" value="${requestScope.allDetail[0].maxFlatRate}"/>
	<input type="hidden" id="minEffectiveRate" name="minEffectiveRate" value="${requestScope.allDetail[0].minEffectiveRate}"/>
	<input type="hidden" id="maxEffectiveRate" name="maxEffectiveRate" value="${requestScope.allDetail[0].maxEffectiveRate}"/>
	
	<input type="hidden" id="defaultFlatRate" name="defaultFlatRate" value="${requestScope.allDetail[0].defaultFlatRate}"/>
	<input type="hidden" id="defaultEffectiveRate" name="defaultEffectiveRate" value="${requestScope.allDetail[0].defaultEffectiveRate}"/>
	
	
	

<fieldset>


<legend><bean:message key="lb.parameter" /></legend>
<table cellspacing="0" cellpadding="0" width="100%" border="0">
<tr>
    <td>
      <table cellspacing="1" cellpadding="1" width="100%" border="0">
        <tr><html:hidden property="productType" styleId="productType"  />
        
        <html:hidden property="productTypeFlag" styleId="productTypeFlag" value="${requestScope.allDetail[0].productTypeFlag}" />
        
        <html:hidden property="dealLoanId" value="${requestScope.allDetail[0].dealLoanId}" />
          <td><bean:message key="lbl.product" /><font color="red">*</font></td>
           <td >
           
           <html:text  property="product" styleId="product" styleClass="text" readonly="true" value="${requestScope.allDetail[0].product}" tabindex="-1"/>
          <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${requestScope.allDetail[0].lbxProductID}"/>
          <logic:notPresent name="allDetail">
          <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(71,'LoanDetailsForm','product','','scheme','','','','productId','productType')" value=" " styleClass="lovbutton"> </html:button>
          <input type="hidden" id="productId" name="productId"/>
        </logic:notPresent>
          <logic:present name="allDetail">
       		 <input type="hidden" name="productButton" id="productButton" value=""/>
        </logic:present>
        </td>
          <td><bean:message key="lbl.scheme" /><font color="red">*</font></td>
          <td>
          <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" value="${requestScope.allDetail[0].scheme}" tabindex="-1"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme" value="${requestScope.allDetail[0].lbxscheme}" />
          		<logic:notPresent name="allDetail">
                <html:button property="schemeButton" styleId="schemeButton" onclick="closeLovCallonLov('product');openLOVCommon(22,'LoanDetailsForm','scheme','product','lbxscheme', 'lbxProductID','Select Product LOV','getDefaultLoanDetail','schemeId')" value=" " styleClass="lovbutton"></html:button>  								    
                <input type="hidden" id="schemeId" name="schemeId"/>
                </logic:notPresent>
                </td> 
         </tr>
        <tr>
        <logic:present name="allDetail">
			<logic:iterate id="suballDetail" name="allDetail" length="1">
				<logic:equal name="suballDetail" property="repaymentType" value="I" >
					
					
					<logic:equal name="suballDetail" property="assetFlag" value="A">
					        <td><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
							<td><html:text property="assetCost"  style="text-align: right" styleClass="text" value="${requestScope.allDetail[0].assetCost}" styleId="assetCost"  maxlength="15" onchange="numberFormatting(this.id,'2');validateAssetCost();assetRangeAmount();calLtvPerc();"/></td>
							<td><bean:message key="lbl.noOfAsset" /><font color="red">*</font></td>
           					<td ><html:text property="noOfAsset" styleClass="text" maxlength="5" style="text-align: right" value="${requestScope.allDetail[0].noOfAsset}" styleId="noOfAsset" onkeypress="return isNumberKey(event);" onblur="checkNoOfAsset()"/></td>
					</logic:equal>
					
				</logic:equal>
             </logic:iterate>
         </logic:present> 
        
         <logic:notPresent name="allDetail">          
         	<td id="assCost1" ><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
        	<td id="assCost2"><html:text property="assetCost" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].assetCost}" styleId="assetCost" maxlength="15"  onchange="numberFormatting(this.id,'2');validateAssetCost();assetRangeAmount();calLtvPerc();" /></td>
        	<td id="noOfAsst1"><bean:message key="lbl.noOfAsset" /><font color="red">*</font></td>
           	<td id="noOfAsst2"><html:text property="noOfAsset" styleClass="text" maxlength="5" style="text-align: right" value="1" styleId="noOfAsset" onkeypress="return isNumberKey(event);" onblur="checkNoOfAsset()"/></td>
          </logic:notPresent>


<!--         sachin  -->


<!--           <html:hidden property="daysBasis" styleId="daysBasis" value="${requestScope.allDetail[0].daysBasis }"/>  -->
<!--           -->
<!--           <td><bean:message key="lbl.tenure" /><font color="red">*</font></td>-->
<!--           <td ><html:text property="requestedLoanTenure" styleClass="text" maxlength="5" style="text-align: right" value="${requestScope.allDetail[0].requestedLoanTenure}" styleId="requestedLoanTenure" onkeypress="return isNumberKey(event);" onchange="calcDay();calculateInstallment();"/></td>-->
         </tr>
         
         <tr>
         <html:hidden property="daysBasis" styleId="daysBasis" value="${requestScope.allDetail[0].daysBasis }"/>  
           
           <td><bean:message key="lbl.tenure" /><font color="red">*</font></td>
           <td ><html:text property="requestedLoanTenure" styleClass="text" maxlength="5" style="text-align: right" value="${requestScope.allDetail[0].requestedLoanTenure}" styleId="requestedLoanTenure" onkeypress="return isNumberKey(event);" onchange="calcDay();calculateInstallment();calculateMaturityDateInDeal();"/></td>
         
         <logic:present name="allDetail">
			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${requestScope.allDetail[0].tenureInDay}" styleId="tenureInDay" onchange="calcMonth();calculateMaturityDateInDeal();" maxlength="4"/>
			</td>	
	         
	        </logic:present>
	        <logic:notPresent name="allDetail">

			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${requestScope.allDetail[0].tenureInDay}" styleId="tenureInDay" onchange="calcMonth();calculateMaturityDateInDeal();" maxlength="4"/>
			</td>	
	         
	        </logic:notPresent>
         
         </tr>
         
        <tr>
			

	  					<td ><bean:message key="lbl.am" /><span><font color="red">*</font></span></td>
	              <td ><html:text property="requestedLoamt" styleClass="text" styleId="requestedLoamt"  style="text-align: right"  value="${requestScope.allDetail[0].requestedLoamt}"  onchange="numberFormatting(this.id,'2');sumRequestedAmt();rangeAmtMar();validateReqLAmt();calLtvPerc();" /></td>
		        
 <logic:present name="flagLoanType" >
          <td><bean:message key="lbl.loanType" /></td>
          <td><html:select styleId="loanType" property="loanType" styleClass="text" value="NEW">
							
							<html:optionsCollection name="getLoanType" label="loanTypeName" value="loanTypeID" />
						</html:select></td>
			</logic:present>          
<logic:notPresent name="flagLoanType">
          <td><bean:message key="lbl.loanType" /></td>
          <td><html:select styleId="loanType" property="loanType" styleClass="text" value="${requestScope.allDetail[0].loanType}">
							
							<html:optionsCollection name="getLoanType" label="loanTypeName" value="loanTypeID" />
						</html:select></td>
			</logic:notPresent>

       
        </tr>
        <!--  amandeep start for insurance -->
       
        <tr>
	  				 <td><bean:message key="lbl.insurancePremium" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="insurancePremium" styleClass="text" styleId="insurancePremium" readonly="true" style="text-align: right" value="${requestScope.allDetail[0].insurancePremium}" tabindex="-1"/></td>
		                
	  				
	  				<td><bean:message key="lbl.amInsurance" /><font color="red">*</font></td>
            <td ><html:text property="requestedLoanAmount" styleClass="text" maxlength="15" style="text-align: right" readonly="true" value="${requestScope.allDetail[0].requestedLoanAmount}" styleId="requestedLoanAmount"   onchange="numberFormatting(this.id,'2');rangeAmtMar();validateReqLAmt();calLtvPerc();" /></td>
	    </tr>
       
       <!-- amandeep ends for insurance --> 
        <tr>
          <logic:present name="allDetail">
              	<logic:iterate id="suballDetail" name="allDetail" length="1">
              	  <logic:equal name="suballDetail" property="repaymentType" value="I" >
              	  		
              		
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="A">
         	  	 		<td><bean:message key="lbl.marginPercentage" /><font color="red">*</font></td>
         	  	 		<td><html:text property="marginPerc" style="text-align: right" styleClass="text"  value="${requestScope.allDetail[0].marginPerc}" styleId="marginPerc" onkeypress="return numbersonly(event, id, 2);"  onchange="checkRate('marginPerc');calMarginAmount();calLtvPerc();"  /></td>
         	  	 	</logic:equal>
           		</logic:equal>
         	   </logic:iterate>
         	 </logic:present> 
         	 
         	 <logic:notPresent name="allDetail"> 
         	 	<td id="marPer1"><bean:message key="lbl.marginPercentage" /><font color="red">*</font></td>
         	 	<td id="marPer2"><html:text property="marginPerc" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].marginPerc}" styleId="marginPerc" onkeypress="return numbersonly(event, id, 2);"   onchange="checkRate('marginPerc');calMarginAmount();calLtvPerc();" /></td>	
         	 </logic:notPresent>
          
               <logic:present name="allDetail">
              	<logic:iterate id="suballDetail" name="allDetail" length="1">
              	 <logic:equal name="suballDetail" property="repaymentType" value="I" >
              
              		
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="A">
         	  	 			 <td><bean:message key="lbl.marginAmount" /><font color="red">*</font></td>
         	  	 		<td><html:text property="marginAmount" styleClass="text" readonly="true" tabindex="-1" style="text-align: right" value="${requestScope.allDetail[0].marginAmount}" styleId="marginAmount"   /></td>
         	  	 	</logic:equal>
         	  	 </logic:equal>
         	    </logic:iterate>
         	 </logic:present> 
         	 <logic:notPresent name="allDetail"> 
         		<td id="marAmount1"><bean:message key="lbl.marginAmount" /><font color="red">*</font></td>
         	    <td id="marAmount2"><html:text property="marginAmount" readonly="true" tabindex="-1" styleClass="text" value="${requestScope.allDetail[0].marginAmount}" styleId="marginAmount" style="text-align: right" /></td>
         	 </logic:notPresent>
          
        </tr>
       
		  <tr>
          <td><bean:message key="lbl.rateType" /><font color="red">*</font></td>
          <td>
          
              <html:select property="rateType" styleClass="text" styleId="rateType" value="${requestScope.allDetail[0].rateType}" onchange="return getFinalRate();">
              <html:option value="">---Select----</html:option>
              <html:option value="F">Flat Rate</html:option>
              <html:option value="E">Effective Rate</html:option>
              </html:select>
          
          </td>
          <td><bean:message key="lbl.rateMethod" /><font color="red">*</font></td>
          <logic:present name="allDetail">
            <logic:iterate name="allDetail" id="suballDetail" length="1">
          <logic:equal  name="suballDetail" property="rateType" value="F">
	           <td>
	              <bean:message key="lbl.fixed" />
	              <input type="radio" name="type" id="type1" value="F" checked="checked"/>
                  <bean:message key="lbl.floating" />
	              <html:radio property="type"  styleId="type2" value="L" />
	      
	          </td>
          </logic:equal>
        <logic:notEqual  name="suballDetail" property="rateType" value="F">
        		   <td>
	              <bean:message key="lbl.fixed" />
	          
	           <logic:equal  name="suballDetail" property="type" value="F">
	           		<input type="radio" name="type" id="type1" value="F" checked="checked"/>
	           </logic:equal>
	           <logic:notEqual  name="suballDetail" property="type" value="F">
	               <html:radio property="type"  styleId="type1" value="F" />
	           </logic:notEqual>  
	            
	            <bean:message key="lbl.floating" />
	            <logic:equal  name="suballDetail" property="type" value="L">
	           		<input type="radio" name="type" id="type2" value="L" checked="checked" />
	           </logic:equal>
	           <logic:notEqual  name="suballDetail" property="type" value="L">
	               <html:radio property="type"  styleId="type2" value="L"  />
	           </logic:notEqual> 
	              
	          </td>
        </logic:notEqual>
          </logic:iterate>
              </logic:present>
              <logic:notPresent name="allDetail">
               <td>
              	 <bean:message key="lbl.fixed" />
              	   <html:radio property="type"  styleId="type1" value="F" />
              	   <bean:message key="lbl.floating" />
              	   <html:radio property="type"  styleId="type2" value="L" />
              	   </td>
              </logic:notPresent>
          </tr>
         
		  <tr>
		  <logic:present name="allDetail">
            <logic:iterate name="allDetail" id="suballDetail" length="1">
          <logic:equal  name="suballDetail" property="rateType" value="F">
          	<!-- Nishant space starts -->	
          	<tr>
          		<td><bean:message key="lbl.fixPriod" /></td>
          		<td><html:text property="fixPriod" styleId="fixPriod" styleClass="text"  style="text-align: right" maxlength="3" value="${requestScope.allDetail[0].fixPriod}" onkeypress="return isNumberKey(event);"/> </td>
          	</tr>
          	<!-- Nishant space starts -->
	          <td><bean:message key="lbl.baseRateType" /><font color="red">*</font></td>
	        <!--  <td >
	            <html:select property="baseRateType" styleClass="text" styleId="baseRateType" disabled="true" value="${requestScope.allDetail[0].baseRateType}" onchange="return getBaseRate();">
	             <html:option value="">---Select----</html:option>
	             <logic:present name="baseRateList">
	               <html:optionsCollection name="baseRateList" label="id" value="id" /> 
	             </logic:present>
	          </html:select>
	         </td>  -->
			  <td>

              <html:text property="baseRateType" styleClass="text" styleId="baseRateType" readonly="true" value="${requestScope.allDetail[0].baseRateType}" tabindex="-1"/>

	         </td>
	          <td><bean:message key="lbl.rate" /><font color="red">*</font></td>
	          <td><html:text property="baseRate" styleClass="text"  style="text-align: right" disabled="true" styleId="baseRate" readonly="true"  /> </td></tr>
			  <tr>
			    <td><bean:message key="lbl.markUp" /></td>

			    <td><html:text property="markUp" style="text-align: right" styleClass="text" styleId="markUp" readonly="true" value="${requestScope.allDetail[0].markUp}" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkMarkUpRate('markUp');" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" tabindex="-1" /></td>


		    </logic:equal>
		    <logic:notEqual name="suballDetail" property="rateType" value="F">
		    <!-- Nishant space starts -->
		    <tr>	
          		<td><bean:message key="lbl.fixPriod" /></td>
          		<td><html:text property="fixPriod" styleId="fixPriod" styleClass="text"  style="text-align: right" maxlength="3" value="${requestScope.allDetail[0].fixPriod}" onkeypress="return isNumberKey(event);"/> </td>
          	</tr>
          	<!-- Nishant space starts -->
		    	 <td><bean:message key="lbl.baseRateType" /><font color="red">*</font></td>
	          <%-- <td >
	            <html:select property="baseRateType" styleClass="text" styleId="baseRateType" value="${requestScope.allDetail[0].baseRateType}" onchange="return getBaseRate();">
	             <html:option value="">---Select----</html:option>
	             <logic:present name="baseRateList">
	               <html:optionsCollection name="baseRateList" label="id" value="id" /> 
	             </logic:present>
	          </html:select>
	         </td> --%>
	         
	          <td>

              <html:text property="baseRateType" styleClass="text" styleId="baseRateType" readonly="true" value="${requestScope.allDetail[0].baseRateType}" tabindex="-1"/>

	         </td>
	         
	          <td><bean:message key="lbl.rate" /><font color="red">*</font></td>
	          <td><html:text property="baseRate" styleClass="text" value="${requestScope.allDetail[0].baseRate}" style="text-align: right" styleId="baseRate" readonly="true"  /> </td></tr>
			  <tr>
			    <td><bean:message key="lbl.markUp" /></td>

			    <td><html:text property="markUp" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].markUp}" styleId="markUp"  onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkMarkUpRate('markUp');" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" readonly="true" tabindex="-1"  /></td>

		    </logic:notEqual>
		 </logic:iterate>
		</logic:present>
		<logic:notPresent name="allDetail">
		<!-- Nishant space starts -->	
		<tr>
          	<td><bean:message key="lbl.fixPriod" /></td>
          	<td><html:text property="fixPriod" styleId="fixPriod" styleClass="text"  style="text-align: right" maxlength="3" value="${requestScope.allDetail[0].fixPriod}" onkeypress="return isNumberKey(event);"/> </td>
        </tr>
        <!-- Nishant space starts -->
			 <td><bean:message key="lbl.baseRateType" /><font color="red">*</font></td>
	    <%--       <td >
	            <html:select property="baseRateType" styleClass="text" styleId="baseRateType" value="${requestScope.allDetail[0].baseRateType}" onchange="return getBaseRate();">
	             <html:option value="">---Select----</html:option>
	             <logic:present name="baseRateList">
	               <html:optionsCollection name="baseRateList" label="id" value="id" /> 
	             </logic:present>
	          </html:select>
	         </td> --%>
	         
	          <td>

              <html:text property="baseRateType" styleClass="text" styleId="baseRateType" readonly="true" value="${requestScope.allDetail[0].baseRateType}" tabindex="-1"/>

	         </td>
	         
	          <td><bean:message key="lbl.rate" /><font color="red">*</font></td>
	          <td><html:text property="baseRate" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].baseRate}" styleId="baseRate" readonly="true"  onkeypress="return numbersonly(event, id, 4)" onblur="formatNumber(this.value, id);checkRate('baseRate');" onkeyup="checkNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 3, id);"/> </td></tr>
			  <tr>
			    <td><bean:message key="lbl.markUp" /></td>

			    <td><html:text property="markUp" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].markUp}" styleId="markUp" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkMarkUpRate('markUp');" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" readonly="true" tabindex="-1"/></td>

		</logic:notPresent>
		    <td><bean:message key="lbl.finalRate" /><font color="red">*</font></td>

		   <td><html:text property="effectiveRate" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].effectiveRate}"  styleId="effectiveRate" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkRate('effectiveRate');calculateFinalRate();" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 8, id);"  /></td>

		    </tr>
		  <tr>
		    <td><bean:message key="lbl.repaymentType" /><font color="red">*</font></td>
		    <td>
		    <html:text styleClass="text" property="showRepaymentType" tabindex="-1" readonly="true" styleId="showRepaymentType" value="${requestScope.allDetail[0].showRepaymentType }"   />
		    <html:hidden styleClass="text" property="repaymentType" styleId="repaymentType" value="${requestScope.allDetail[0].repaymentType }"   />
          </td>	
		   <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
                            
<!--              <td><bean:message key="lbl.installmentType" /><font color="red">*</font></td>-->
<!--		    <td><html:select property="installmentType" styleClass="text" styleId="installmentType" value="${requestScope.allDetail[0].installmentType}">-->
<!--            <html:option value="">---Select----</html:option>-->
<!--             <html:option value="E">Eq. INSTALLMENT</html:option>-->
<!--		     <html:option value="G">Gr. INSTALLMENT</html:option>-->
<!--		     <html:option value="P">Eq. PRINCIPAL</html:option>-->
<!--		     <html:option value="Q">Gr. PRINCIPAL1</html:option>-->
<!--		     <html:option value="R">Gr. PRINCIPAL2</html:option>-->
<!--          </html:select></td>-->
				<td><bean:message key="lbl.installmentType" /><font color="red">*</font></td>
				<td>
	              <html:select property="installmentType" styleId="installmentType" styleClass="text" value="${requestScope.allDetail[0].installmentType}" onchange="editNoInstal();">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="installmentTypeList">
				  <logic:notEmpty name="installmentTypeList">
				  <html:optionsCollection name="installmentTypeList" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>
				  </td>
         
           </tr>
		    
		  <tr>
		  
		  <td><bean:message key="lbl.insMode" /><font color="red">*</font></td>
		    <td><html:select property="instMode" styleClass="text" styleId="instMode" value="${requestScope.allDetail[0].instMode}">
                    <html:option value="">---Select----</html:option>
                    <html:option value="A">ADVANCE</html:option>
		            <html:option value="R">ARREAR</html:option>
                </html:select>
            </td>
		  
		    <td><bean:message key="lbl.frequency" /><font color="red">*</font></td>
		    <td><html:select property="frequency" styleClass="text" styleId="frequency" value="${requestScope.allDetail[0].frequency}" onchange="calculateInstallment();getDueDay();editNoInstal();">
            <html:option value="">---Select----</html:option>
              <html:option value="M">MONTHLY</html:option>
		      <html:option value="B">BIMONTHLY</html:option>
		      <html:option value="Q">QUARTERLY</html:option>
		      <html:option value="H">HALFYERALY</html:option>
		      <html:option value="Y">YEARLY</html:option>
          </html:select></td>
		   
		    </tr>
		    
		     <tr>
		  
		  <td id="insComFre1"><bean:message key="lbl.interestCompoundingFrequency" />
		    <td id="insComFre2"><html:select property="interestCompoundingFrequency" styleClass="text" styleId="interestCompoundingFrequency" value="${requestScope.allDetail[0].interestCompoundingFrequency}" onchange="validateIntCompoundingFrequency();">
					
				 <html:option value="">NOT APPLICABLE</html:option>
	              <html:option value="M">MONTHLY</html:option>
			      <html:option value="B">BIMONTHLY</html:option>
			      <html:option value="Q">QUARTERLY</html:option>
			      <html:option value="H">HALFYERALY</html:option>
			      <html:option value="Y">YEARLY</html:option>
			     <%--   <logic:present name="intComFreq">
		               <html:optionsCollection name="intComFreq" label="name" value="id" /> 
		       </logic:present> --%>
         	  </html:select>
            </td>
		  
		    <td id="insCalMet1"><bean:message key="lbl.interestCalculationMethod" /><font color="red">*</font></td>
		    <td id="insCalMet2"><html:select property="interestCalculationMethod" styleClass="text" styleId="interestCalculationMethod" value="${requestScope.allDetail[0].interestCalculationMethod}" onchange="showInterestDueDate(true);setInterestDueDate();showDueDay();nullNextDue();getDueDay();">
           
           <%--   <html:option value="D">DUE DATE</html:option>
		      <html:option value="E">EOM</html:option>
		     <html:option value="F">FEOM</html:option> --%>
		       <logic:present name="intCal">
		               <html:optionsCollection name="intCal" label="name" value="id" /> 
		       </logic:present> 
          </html:select></td>
		   
		    </tr>
		    
		      <tr>
		  
				  <td id="insFre1"><bean:message key="lbl.interestFrequency" /><font color="red">*</font></td>
				    <td id="insFre2"><html:select property="interestFrequency" styleClass="text" styleId="interestFrequency" value="${requestScope.allDetail[0].interestFrequency}" onchange="setInterestDueDate();validateInterestFrequency();" >
		            
			              <html:option value="M">MONTHLY</html:option>
					      <html:option value="B">BIMONTHLY</html:option>
					      <html:option value="Q">QUARTERLY</html:option>
					      <html:option value="H">HALFYERALY</html:option>
					      <html:option value="Y">YEARLY</html:option>
					    <%--    <html:option value="X">FINANCIAL QUARTERLY</html:option>
					          <html:option value="L">FINANCIAL HALFYERALY</html:option>
					            <html:option value="Z">FINANCIAL YEARLY</html:option> --%>
					     <%--   <logic:present name="intFreq">
		               <html:optionsCollection name="intFreq" label="name" value="id" /> 
		       </logic:present> --%>
		         	  </html:select>
		            </td>
		  
		   
		    </tr>
		    
		    
		    
		    
		     <tr>
			    <td><bean:message key="lbl.noOfInstall" /></td>
			    <td><html:text property="noOfInstall" tabindex="-1" onkeypress="return isNumberKey(event);"  maxlength="5" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].noOfInstall}"  styleId="noOfInstall" readonly="true" /></td>
			     <td><bean:message key="lbl.creditPeriod" /><font color="red">*</font></td>
           <td ><html:text property="creditPeriod" styleClass="text" maxlength="5"  value="${requestScope.allDetail[0].requestedLoanTenure}" styleId="creditPeriod" onkeypress="return isNumberKey(event);" /></td>
            
          </logic:equal>
          </logic:iterate>
          </logic:present>
          
          <logic:notPresent name="allDetail">
<!--          	 <td id="installType1"><bean:message key="lbl.installmentType" /><font color="red">*</font></td>-->
<!--          	 <td id="installType2"><html:select property="installmentType" styleClass="text" styleId="installmentType" value="${requestScope.allDetail[0].installmentType}">-->
<!--             <html:option value="">---Select----</html:option>-->
<!--             <html:option value="E">Eq. INSTALLMENT</html:option>-->
<!--		     <html:option value="G">Gr. INSTALLMENT</html:option>-->
<!--		     <html:option value="P">Eq. PRINCIPAL</html:option>-->
<!--		     <html:option value="Q">Gr. PRINCIPAL1</html:option>-->
<!--		     <html:option value="R">Gr. PRINCIPAL2</html:option>-->
<!--             </html:select></td>-->
				<td id="installType1"><bean:message key="lbl.installmentType" /><font color="red">*</font></td>
				<td id="installType2">
	              <html:select property="installmentType" styleId="installmentType" styleClass="text" value="${requestScope.allDetail[0].installmentType}" onchange="editNoInstal();">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="installmentTypeList">
				  <logic:notEmpty name="installmentTypeList">
				  <html:optionsCollection name="installmentTypeList" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>
				  </td>
         
          	
          	 </tr>
		    
		  <tr>
		  
		  <td id="insMod1"><bean:message key="lbl.insMode" /><font color="red">*</font></td>
		    <td id="insMod2"><html:select property="instMode" styleClass="text" styleId="instMode" value="${requestScope.allDetail[0].instMode}">
                    <html:option value="">---Select----</html:option>
                    <html:option value="A">ADVANCE</html:option>
		            <html:option value="R">ARREAR</html:option>
                </html:select>
            </td>
		  
		    <td id="frequnc1"><bean:message key="lbl.frequency" /><font color="red">*</font></td>
		    <td id="frequnc2"><html:select property="frequency" styleClass="text" styleId="frequency" value="${requestScope.allDetail[0].frequency}" onchange="calculateInstallment();getDueDay();editNoInstal();">
            <html:option value="">---Select----</html:option>
              <html:option value="M">MONTHLY</html:option>
		      <html:option value="B">BIMONTHLY</html:option>
		      <html:option value="Q">QUARTERLY</html:option>
		      <html:option value="H">HALFYERALY</html:option>
		      <html:option value="Y">YEARLY</html:option>
          </html:select></td>
		   
		    </tr>
		    
		    
		    <tr>
		  
		  <td id="insComFre1"><bean:message key="lbl.interestCompoundingFrequency" />
		    <td id="insComFre2"><html:select property="interestCompoundingFrequency" styleClass="text" styleId="interestCompoundingFrequency" value="${requestScope.allDetail[0].interestCompoundingFrequency}" onchange="validateIntCompoundingFrequency();">
             
	             <html:option value="">NOT APPLICABLE</html:option>
				  <html:option value="M">MONTHLY</html:option>
			      <html:option value="B">BIMONTHLY</html:option>
			      <html:option value="Q">QUARTERLY</html:option>
			      <html:option value="H">HALFYERALY</html:option>
			      <html:option value="Y">YEARLY</html:option>
			   <%--     <logic:present name="intComFreq">
		               <html:optionsCollection name="intComFreq" label="name" value="id" /> 
		       </logic:present> --%>
         	  </html:select>
            </td>
		  
		    <td id="insCalMet1"><bean:message key="lbl.interestCalculationMethod" /><font color="red">*</font></td>
		    <td id="insCalMet2"><html:select property="interestCalculationMethod" styleClass="text" styleId="interestCalculationMethod" value="${requestScope.allDetail[0].interestCalculationMethod}" onchange="showInterestDueDate(true);setInterestDueDate();showDueDay();nullNextDue();getDueDay();">
            
           <%--    <html:option value="D">DUE DATE</html:option>
		      <html:option value="E">EOM</html:option>
		     <html:option value="F">FEOM</html:option>  --%>
		     
		      <logic:present name="intCal">
		               <html:optionsCollection name="intCal" label="name" value="id" /> 
		       </logic:present>
		     
          </html:select></td>
		   
		    </tr>
		    
		      <tr>
		  
				  <td id="insFre1"><bean:message key="lbl.interestFrequency" /><font color="red">*</font></td>
				    <td id="insFre2"><html:select property="interestFrequency" styleClass="text" styleId="interestFrequency" value="${requestScope.allDetail[0].interestFrequency}" onchange="setInterestDueDate();validateInterestFrequency();"  >
		             
			              <html:option value="M">MONTHLY</html:option>
					      <html:option value="B">BIMONTHLY</html:option>
					      <html:option value="Q">QUARTERLY</html:option>
					      <html:option value="H">HALFYERALY</html:option>
					      <html:option value="Y">YEARLY</html:option>
					       <%-- <html:option value="X">FINANCIAL QUARTERLY</html:option>
					          <html:option value="L">FINANCIAL HALFYERALY</html:option>
					            <html:option value="Z">FINANCIAL YEARLY</html:option> --%>
					     <%--   <logic:present name="intFreq">
		          			     <html:optionsCollection name="intFreq" label="name" value="id" /> 
		      				 </logic:present> --%>
		         	  </html:select>
		            </td>
		  	
		    
		   
		    </tr>
		    
		     <tr>
			    <td id="noOfIns1"><bean:message key="lbl.noOfInstall" /></td>
			    <td id="noOfIns2"><html:text property="noOfInstall" tabindex="-1" onkeypress="return isNumberKey(event);" maxlength="5"  styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].noOfInstall}" readonly="true" styleId="noOfInstall"  /></td>
			     
			     <td id="creditPeriod1"><bean:message key="lbl.creditPeriod" /><font color="red">*</font></td>
           <td ><html:text property="creditPeriod" styleClass="text" maxlength="5"  value="0" styleId="creditPeriod" onkeypress="return isNumberKey(event);" /></td>
          
          </logic:notPresent>
 
		    </tr>  
		    <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
	             <tr>
	             
	              <td><bean:message key="lbl.repaymentMode" /><font color="red">*</font></td>
			    <td><html:select property="paymentMode" styleClass="text" styleId="paymentMode" value="${requestScope.allDetail[0].paymentMode}">
	              <html:option value="">--Select--</html:option>
   					    <logic:present name="paymentModes">
					<logic:notEmpty name="paymentModes">
					<html:optionsCollection name="paymentModes" label="paymentMode" value="paymentModeId" />
					</logic:notEmpty>		
				</logic:present>
	          </html:select></td>
			    <td><bean:message key="lbl.noIns" /></td>
			    
			    <logic:present name="allDetail">
			    	  <td><html:text styleClass="text" property="installments" styleId="installments" value="${requestScope.allDetail[0].installments }" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
			    </logic:present>
			       <logic:notPresent name="allDetail">
			    	  <td><html:text styleClass="text" property="installments" styleId="installments" value="0" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
			    </logic:notPresent>
			      
			    
			    </tr>
		    </logic:equal>
		    	    
		    </logic:iterate>
		    </logic:present>
		    
		    <logic:notPresent name="allDetail">
		    
		    	 <tr>
	             <!--Amandeep starts here-->
	             
	              <td id="repayMod1"><bean:message key="lbl.repaymentMode" /><font color="red">*</font></td>
			    <td id="repayMod2"><html:select property="paymentMode" styleClass="text" styleId="paymentMode" value="${requestScope.allDetail[0].paymentMode}">
	              <html:option value="">--Select--</html:option>
   					<logic:present name="paymentModes">
					<logic:notEmpty name="paymentModes">
					<html:optionsCollection name="paymentModes" label="paymentMode" value="paymentModeId" />
					</logic:notEmpty>		
				</logic:present>
	          </html:select>
	          </td>
	          
	          
			    <td id="noIns1"><bean:message key="lbl.noIns" /></td>
			          <logic:present name="allDetail">
			    	  <td><html:text styleClass="text" property="installments" styleId="installments" value="${requestScope.allDetail[0].installments }" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
			    </logic:present>
			       <logic:notPresent name="allDetail">
			    	 <td id="noIns2"><html:text styleClass="text" property="installments" styleId="installments" value="0" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
			    </logic:notPresent>
			    
			    </tr>
		    
		    </logic:notPresent>
		    
		      <tr>
		      		<%-- <html:hidden property="maturityDate" styleId="maturityDate1" value="${requestScope.allDetail[0].maturityDate}" /> --%>
              		<td id="effDateHeader"><bean:message key="lbl.effDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
              		 <logic:notPresent name="allDetail">
              		 	<td id="effDate"><html:text styleClass="text" property="repayEffectiveDate" styleId="repayEffectiveDate" value="${userobject.businessdate }" maxlength="10" onchange="checkDate('repayEffectiveDate');validRepayDateInDeal();calculateMaturityDateInDeal();checkNewRepay();getDueDayNextDueDate();setInterestDueDate();" /></td>
              		 </logic:notPresent>
		    		 <logic:present name="allDetail">
              		 	<td id="effDate"><html:text styleClass="text" property="repayEffectiveDate" styleId="repayEffectiveDate" value="${requestScope.allDetail[0].repayEffectiveDate }" maxlength="10" onchange="checkDate('repayEffectiveDate');validRepayDateInDeal();calculateMaturityDateInDeal();checkNewRepay();getDueDayNextDueDate();setInterestDueDate();" /></td>
              		 </logic:present>
		    
		    
		    <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
		    <td><bean:message key="lbl.cycleDate" /><font color="red">*</font></td>
		    <td >
		    
		    <html:select property="cycleDate" styleClass="text" styleId="cycleDate" value="${requestScope.allDetail[0].cycleDate}" onchange="nullNextDue();getDueDay();">
              <html:option value="">---Select----</html:option>
              <logic:present name="cycle">
	               <html:optionsCollection name="cycle" label="name" value="id" /> 
	             </logic:present>
		   </html:select>
		    
		  	    
		    </td>
		    
		    </logic:equal>

		    </logic:iterate>
		    </logic:present>
		     <logic:notPresent name="allDetail">
		     <td id="cyclDate1"><bean:message key="lbl.cycleDate" /><font color="red">*</font></td>
		     	<td id="cyclDate2">
		    
			<div id="cycleDue2">
		    <html:select property="cycleDate" styleClass="text" styleId="cycleDate" value="${requestScope.dueDayList[0].cycleDate}" onchange="nullNextDue();getDueDay();">
              <html:option value="">---Select----</html:option>
              	<logic:present name="cycle">
	               <html:optionsCollection name="cycle" label="name" value="id" /> 
	             </logic:present>
		   </html:select>
		    </div>
		    </td>
		     </logic:notPresent>
		    </tr>
		      <tr>
		           <logic:notPresent name="allDetail">
			      		<td id="nddheader"><bean:message key="lbl.nextDueDate" /><font color="red">*</font></td>
			    		<td id="ndd">
			         			<html:text property="nextDueDate" styleClass="text"  value="${requestScope.dueDayList[0].nextDueDate}" styleId="nextDueDate" onchange="checkDueDate(value);calTenureMonthForMaturityDateNextDueDate();" />
			    		</td>
                  </logic:notPresent> 
                  <logic:present name="allDetail">
                  	<td id="nddheader"><bean:message key="lbl.nextDueDate" /><font color="red">*</font></td>
			    		<td id="ndd">
			         			<html:text property="nextDueDate" styleClass="text"  value="${requestScope.allDetail[0].nextDueDate}" styleId="nextDueDate" onchange="checkDueDate(value);calTenureMonthForMaturityDateNextDueDate();" />
			    		</td>
                  </logic:present>
				  	<td id="interestDueDateHeader1"><bean:message key="lbl.interestDueDate"/><font color="red">*</font></td>
					
					<td id = "interestDueDateHeader2">
						<logic:notPresent name="allDetail">
							<html:text styleClass="text" property="interestDueDate" styleId="interestDueDate"  onchange="datevalidate();validateInterestDueDate();" />
							
						</logic:notPresent>
						<logic:present name="allDetail">
						
							<html:text styleClass="text" property="interestDueDate" value="${requestScope.allDetail[0].interestDueDate}"  styleId="interestDueDate"  onchange="datevalidate();validateInterestDueDate();" />
						
							
						</logic:present>
					</td>
					<%--
					<div id="calImgNotHid" style="display:none">
						 <logic:notPresent name="allDetail">
							<td id="interestDueDateHeader2" ><html:text styleClass="text" property="interestDueDate" styleId="interestDueDate"  readonly="readonly"/></td>
						 </logic:notPresent>
						 <logic:present name="allDetail">
							<td id="interestDueDateHeader2" ><html:text styleClass="text" property="interestDueDate" value="${requestScope.allDetail[0].interestDueDate}"  styleId="interestDueDate"  readonly="readonly"/></td>
						 </logic:present>
					</div>
					<div id="calImgHid" style="display:none">
						 <logic:notPresent name="allDetail">
							<td id="interestDueDateHeader2"><html:text styleClass="text" property="interestDueDate" styleId="interestDueDateHid"  /></td>
						 </logic:notPresent>
						 <logic:present name="allDetail">
							<td id="interestDueDateHeader2" ><html:text styleClass="text" property="interestDueDate" value="${requestScope.allDetail[0].interestDueDate}"  styleId="interestDueDateHid"  readonly="readonly"/></td>
						 </logic:present>
					</div>
					--%>
				  </tr>
				  
				  <!-- Code Added for the Handling separate Interest first due date and maturity date| Rahul papneja| 31012018 -->
				  <tr id="SeparateInterestDate" style="display:none">
				  <!-- Start|01 | Code Added for firstInterestDueDate Handling till <td> tag closed -->
				  <td id="firstInterestDueDateLBL" style="display:none"><bean:message key="lbl.firstInterestDueDate"/></td>
				  <td id = "firstInterestDueDateTXT" style="display:none">
						<logic:notPresent name="allDetail">
							<html:text styleClass="text" property="firstInterestDueDate" styleId="firstInterestDueDate"  onchange="datevalidate();" />
							
						</logic:notPresent>
						<logic:present name="allDetail">
						
							<html:text styleClass="text" property="firstInterestDueDate" value="${requestScope.allDetail[0].firstInterestDueDate}"  styleId="firstInterestDueDate"  onchange="datevalidate();" />
						
							
						</logic:present>
					</td>
					<!-- 01| End -->
					<input type="hidden" name="OldMaturityDate" id="OldMaturityDate" value="${requestScope.allDetail[0].maturityDate}" />
				    <!-- Start|02-1 | Code Added for maturityDate Handling till <td> tag closed -->
				  <td id="maturityDateLBL"><bean:message key="lbl.maturityDate"/> <font color="red">*</font> </td>
				  <td id = "maturityDateTXT">
						<logic:notPresent name="allDetail">
							<html:text styleClass="text" property="maturityDate" styleId="maturityDate1"  onchange="calTenureMonthForMaturityDate();validateMaturityDate();" onblur="setMaturityTemp();"/>
							
						</logic:notPresent>
						<logic:present name="allDetail">
						
							<html:text styleClass="text" property="maturityDate" value="${requestScope.allDetail[0].maturityDate}"  styleId="maturityDate1"  onchange="calTenureMonthForMaturityDate();validateMaturityDate();" onblur="setMaturityTemp();" />
						
							
						</logic:present>
					</td>
					<!-- 02-1 | End -->
				  
				  
				  
				  
				  </tr>
				  <!-- Ends Here| Rahul papneja -->
				  
				  
				  
				  <tr>
		    		<td><bean:message key="lbl.purpose" /><font color="red">*</font></td>
		    		<td >
		    
		     			<a rel="tooltip3" id="tool">
		     			
		     			  <html:text property="loanPurpose" maxlength="50"  styleClass="text" readonly="true" value="${requestScope.allDetail[0].loanPurpose}" styleId="loanPurpose" onchange="return upperMe('loanPurpose');" onmouseover="applyToolTip(id);"  />
		     <%-- 			  <input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(5056,'LoanDetailsForm','loanPurpose','','','','','','loanPurpose');" value=" " tabindex="1" name="dealButton" />	--%>
		     				     <html:hidden styleClass="text" property="lbxLoanPurpose" styleId="lbxLoanPurpose" value="${requestScope.allDetail[0].lbxLoanPurpose }"   />
		     				     <input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(5056,'LoanDetailsForm','loanPurpose','product','lbxLoanPurpose','lbxProductID','Please select product first!!!','','lbxLoanPurpose');" value=" " tabindex="1" name="dealButton" />	
		     			</a>
		     		
		     			
		  	 		</td>  
		  	 		<!-- Code Added for Edit Due Date | Rahul papneja |30012018 -->
		  	 		<!--       Vishal changes start -->
			<td>
			<bean:message key="lbl.editDueDate"/><font color="red">*</font></td>
			<td>
			<html:select property="editDueDate" styleId="editDueDate" styleClass="text" value="${requestScope.allDetail[0].editDueDate}">
			<html:option value="N">NO</html:option>
			<html:option value="Y">Yes</html:option>
			<!-- <html:option value="N">NO</html:option> -->
			</html:select>
			</td>
<!-- 	   Vishal changes end --> 	
          	
		  	 		<!-- Ends Here | Rahul papneja -->
		    </tr>
		    <tr>
		    	<td ><bean:message key="lbl.sectorType" /><font color="red">*</font></td>
		    	 <td >
	            <html:select property="sectorType" styleClass="text" styleId="sectorType" onchange="showValues();" value="${requestScope.allDetail[0].sectorType}">
	             <html:option value="">---Select----</html:option>
	             <logic:present name="sector">
	               <html:optionsCollection name="sector" label="name" value="id" /> 
	             </logic:present>
	          </html:select>
	         </td>
	         
	         <%-- Start By Prashant --%>
	         <logic:present name="allDetail">
              	<logic:iterate id="suballDetail" name="allDetail" length="1">
              	<logic:equal name="suballDetail" property="repaymentType" value="I" >
              		<logic:equal name="suballDetail" property="assetFlag" value="A">
				        <td><bean:message key="lbl.LTV" /></td>
					    <td>
					         	<html:text property="ltvPerc" styleClass="text"  value="${requestScope.allDetail[0].ltvPerc}" styleId="ltvPerc"  readonly="true" />
					    </td>
					   </logic:equal>
				    </logic:equal>
	         </logic:iterate>
	         
	         </logic:present>
	          <logic:notPresent name="allDetail">
	          
              	      <td id="ltvDesc"><bean:message key="lbl.LTV" /></td>
					    <td id="ltvP">
					         	<html:text property="ltvPerc" styleClass="text"  value="${requestScope.allDetail[0].ltvPerc}" styleId="ltvPerc"  readonly="true" />
					    </td>
	         
	         </logic:notPresent>
	         
	           <%-- End By Prashant --%>
		    </tr>
<!--		   @Surendra Code Start	   -->
		    <tr >
              <logic:present name="allDetail">
                  <logic:iterate id="suballDetail" name="allDetail" length="1">
                    <logic:equal name="suballDetail" property="repaymentType" value="N" >
		        <td ><bean:message key="lbl.interestCalc" /><font color="red">*</font></td>
		    	 <td >		    	
	            <html:select property="interestCalc" styleClass="text" styleId="interestCalc" value="${requestScope.allDetail[0].interestCalc}"  >
	             <html:option value="">---Select----</html:option>
	             <logic:present name="interestFrom">
	               <html:optionsCollection name="interestFrom" label="name" value="id" /> 
	             </logic:present>	            
	          </html:select>	          
	         </td>
	          </logic:equal>
	             </logic:iterate>
	             </logic:present>
	             <logic:notPresent name="allDetail">	                       
	             <td >
	             <div id="int1" style="display:none">
	             <bean:message key="lbl.interestCalc" /><font color="red">*</font></div></td>
	              <td>	<div id="int2" style="display:none">	    	
	            <html:select property="interestCalc" styleClass="text" styleId="interestCalc" value="${requestScope.allDetail[0].interestCalc}"  >
	             <html:option value="">---Select----</html:option>
	             <logic:present name="interestFrom">
	               <html:optionsCollection name="interestFrom" label="name" value="id" /> 
	             </logic:present>	            
	          </html:select>	
	          </div>          
	         </td> 
	             </logic:notPresent>

	         </tr>       
<!--		   @Surendra Code End	   -->	
			
			<tr id="netLtvRow">
			<logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              	<logic:equal name="suballDetail" property="repaymentType" value="I" >
              	<logic:equal name="suballDetail" property="assetFlag" value="A"> 	
			<td><bean:message key="lbl.netLtv" /></td>
			<td>
			<html:text property="netLtv" styleClass="text"  value="${requestScope.allDetail[0].netLtv}" styleId="netLtv"  readonly="true" />
			</td>	
			 <td><bean:message key="lbl.totalVatAmt" /></td>
		   <td>
		   <html:text property="totalVatAmt" styleClass="text"  value="${requestScope.allDetail[0].totalVatAmt}" styleId="totalVatAmt"  readonly="true" />
		   </td>  
				</logic:equal>
				</logic:equal>
	         </logic:iterate>	         
	        </logic:present>
	        <logic:notPresent name="allDetail">
	          
           <td><bean:message key="lbl.netLtv" /></td>
		   <td>
		   <html:text property="netLtv" styleClass="text"  value="0.00" styleId="netLtv"  readonly="true" />
		   </td>  
		   <td><bean:message key="lbl.totalVatAmt" /></td>
		   <td>
		   <html:text property="totalVatAmt" styleClass="text"  value="${requestScope.allDetail[0].totalVatAmt}" styleId="totalVatAmt"  readonly="true" />
		   </td>     
	      </logic:notPresent>
	      	
 			 </tr> 
			  <tr>
		    		 
					
					
			  		 <td><bean:message key="lbl.loanClassification" /></td>
			<td>
			
			 <html:hidden  property="lbxLoanClassification" styleId="lbxLoanClassification" value="${requestScope.allDetail[0].lbxLoanClassification}" />
			<html:select property="loanClassification" styleId="loanClassification" size="5"  multiple="multiple" style="width: 120" >
   			<logic:present name="loanClassificationList">
   			<logic:notEmpty name="loanClassificationList">
       			 <html:optionsCollection name="loanClassificationList" value="loanClassificationId" label="loanClassificationLabel"/>
       		</logic:notEmpty>
       		</logic:present>
     		</html:select>
      		<html:button property="userButton" styleId="userButton1" onclick="return openMultiSelectLOVCommon(2050,'sourcingForm','loanClassification','','', '','','','lbxLoanClassification');" value=" " styleClass="lovbutton"></html:button>
     			   	</td>   

			<td><bean:message key="lbl.businessType" /><font color="red">*</font></td>
			 <td >
		   <html:select property="businessType" styleClass="text" styleId="businessType" value="${requestScope.allDetail[0].businessType}"  onchange="return getbusinessbutton();">
			 
	             	<html:option value="">---Select---</html:option>
	             		<logic:present name="getBusiness">
	               			<html:optionsCollection name="getBusiness" label="businessdesc" value="businessId" /> 
	             		</logic:present>
	          	</html:select>
	          	 <input type="hidden" name="oldPartnerType" id="oldPartnerType" value="${requestScope.allDetail[0].businessType}" /> 
         	</td>
         	


			 
			  </tr>
			  <tr>
			  <td  id="gblock" style="display: none;"><bean:message key="lbl.grossBlock" /></td>
          	 <td  id="gblock1" style="display: none;"><html:text property="grossBlock" styleClass="text" maxlength="10" style="text-align: right" value="${requestScope.allDetail[0].grossBlock}" styleId="grossBlock" onkeypress="return isNumberKey(event);" /></td>
			 
			<td  id="nblock" style="display: none;"><bean:message key="lbl.netBlock" /></td>
          	 <td  id="nblock1" style="display: none;"><html:text property="netBlock" styleClass="text" maxlength="10" style="text-align: right" value="${requestScope.allDetail[0].netBlock}" styleId="netBlock" onkeypress="return isNumberKey(event);" /></td>
          	 
			  </tr>
 
		 <tr>
			<td >
			 <button type="button" class="topformbutton2" id="Save" title="Alt+V" accesskey="V" onclick="saveLoanDetail('LoanDetailsForm');"><bean:message key="button.save" /></button>
			  <button type="button" class="topformbutton4" id="Save" title="Alt+V" accesskey="V" onclick="openPopForSectorDetails('N');"><bean:message key="button.sectorTypeDetails" /></button>
			    </td>
			    			  
			   <td id ="partner_mode" style="display: none">        
		      <button type="button" class="topformbutton4" id="partner" title="Alt+P" accesskey="P" onclick="getbusinessType();"><bean:message key="lbl.partnerDetails" /></button>
		      </td>
		</tr>
       
		 </table> </td></tr>
    </table>
    </fieldset>
<fieldset>	
		 <legend><bean:message key="lbl.loanDetail" /></legend>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
         <tr>
         <td class="gridtd">
    	 <table width="100%" border="0" cellspacing="1" cellpadding="1">
            <tr class="white2">
             <td width="3%">
	         <input type="checkbox" name="allchk" id="allchk" value="checkbox" onclick="return allChecked();"/>
	         </td> 
             <td ><strong><bean:message key="lbl.product" /></strong></td>
             <td ><strong><bean:message key="lbl.scheme" /></strong></td>
             <td ><b><bean:message key="lbl.am" /></b></td>
             <td ><strong><bean:message key="lbl.tenure" /></strong></td>
             <td ><b><bean:message key="lbl.rateType" /></b></td>
             <td ><strong><bean:message key="lbl.rateMethod" /></strong></td>
             <td ><strong><bean:message key="lbl.purpose" /></strong></td>
            </tr>
          <logic:present name="loanList">
            <logic:notEmpty name="loanList">
           <logic:iterate name="loanList" id="subloanList" length="1">
		       <tr class="white1">
		         
	             <td ><input type="checkbox" name="chk" id="chk" value="<bean:write name="subloanList" property="dealLoanId" />" /></td>	 
	             <td ><a href="#" onclick="modifyDetail(<bean:write name="subloanList" property="dealLoanId" />)"><bean:write name="subloanList" property="product" /></a></td>
	             <td ><bean:write name="subloanList" property="scheme" /></td>
	             <td ><bean:write name="subloanList" property="requestedLoanAmount" /></td>
	             <td ><bean:write name="subloanList" property="requestedLoanTenure" /></td>
	             <td ><bean:write name="subloanList" property="rateType" /></td>
	             <td><bean:write name="subloanList" property="type" /></td>
	             <td ><bean:write name="subloanList" property="loanPurpose" /></td>
	           </tr>
           </logic:iterate>
           
           <table width="100%" border="0" cellpadding="1" cellspacing="1">
                <tr class="white1">
                    <td colspan="4">
          
            <button type="button" name="buttonDelete" title="Alt+D" id="Delete" accesskey="D" class="topformbutton2" onclick="deleteLoanDetails()" ><bean:message key="button.delete" /></button>
               </td>
               </tr>
          </table>
           </logic:notEmpty>
           
            </logic:present>
            
         </table>     
        </td>
        </tr>
      </table>
      
  

</fieldset>
	
	</logic:notPresent>
	
	<%--  --- --- --- --- --- --- --- --- --- --- ---  If  Lead  Details is captured  ---  --- --- --- --- --- --- --- --- --- ---      --%>
	
	<logic:present name="leadDetail">
		
	<input type="hidden" id="minFinance" name="minFinance" value="${requestScope.allDetail[0].minFinanceAmount}"/>
	<input type="hidden" id="maxFinance" name="maxFinance" value="${requestScope.allDetail[0].maxFinanceAmount}" />
	<input type="hidden" id="minMarginRate" name="minMarginRate" value="${requestScope.allDetail[0].minMRate}"/>
	<input type="hidden" id="maxMarginRate" name="maxMarginRate" value="${requestScope.allDetail[0].maxMRate}" />
	<input type="hidden" id="minTenure" name="minTenure" value="${requestScope.allDetail[0].minTenure}"/>
	<input type="hidden" id="maxTenure" name="maxTenure" value="${requestScope.allDetail[0].maxTenure}"/>
	<html:hidden  property="repaymentType" styleId="repaymentType" value="${requestScope.allDetail[0].repaymentType }"   />
	<html:hidden  property="rateMethodType" styleId="rateMethodType" value="${requestScope.allDetail[0].type }"   />
	<html:hidden property="productType" styleId="productType"  value="${requestScope.allDetail[0].productType}" />
    <html:hidden property="productTypeFlag" styleId="productTypeFlag" value="${requestScope.allDetail[0].assetFlag}" />
    <html:hidden property="dealLoanId" value="${requestScope.allDetail[0].dealLoanId}" />
    <input type="hidden" id="minFlatRate" name="minFlatRate" value="${requestScope.allDetail[0].minFlatRate}"/>
	<input type="hidden" id="maxFlatRate" name="maxFlatRate" value="${requestScope.allDetail[0].maxFlatRate}"/>
	<input type="hidden" id="minEffectiveRate" name="minEffectiveRate" value="${requestScope.allDetail[0].minEffectiveRate}"/>
	<input type="hidden" id="maxEffectiveRate" name="maxEffectiveRate" value="${requestScope.allDetail[0].maxEffectiveRate}"/>
	
	<input type="hidden" id="defaultFlatRate" name="defaultFlatRate" value="${requestScope.allDetail[0].defaultFlatRate}"/>
	<input type="hidden" id="defaultEffectiveRate" name="defaultEffectiveRate" value="${requestScope.allDetail[0].defaultEffectiveRate}"/>
	
    
<fieldset>


<legend><bean:message key="lb.parameter" /></legend>
<table cellspacing="0" cellpadding="0" width="100%" border="0">
<tr>
    <td>
      <table cellspacing="1" cellpadding="1" width="100%" border="0">
        <tr>
          <td><bean:message key="lbl.product" /><font color="red">*</font></td>
           <td >
           
           <html:text  property="product" styleId="product" styleClass="text" readonly="true" value="${requestScope.allDetail[0].product}" tabindex="-1"/>
          <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${requestScope.allDetail[0].lbxProductID}"/>
          
<!--          <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(71,'LoanDetailsForm','product','','scheme','','','','productId','productType')" value=" " styleClass="lovbutton"> </html:button>-->
<!--          <input type="hidden" id="productId" name="productId"/>-->
        
          
        </td>
          <td><bean:message key="lbl.scheme" /><font color="red">*</font></td>
          <td>
          <html:text property="scheme" styleId="scheme" styleClass="text" readonly="true" value="${requestScope.allDetail[0].scheme}" tabindex="-1"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme" value="${requestScope.allDetail[0].lbxscheme}" />
<!--          		<html:button property="schemeButton" styleId="schemeButton" onclick="closeLovCallonLov('product');openLOVCommon(22,'LoanDetailsForm','scheme','product','lbxscheme', 'lbxProductID','Select Product LOV','getDefaultLoanDetail','schemeId')" value=" " styleClass="lovbutton"></html:button>  								    -->
<!--                <input type="hidden" id="schemeId" name="schemeId"/>-->
          		
                </td> 
         </tr>
        <tr>
        <logic:present name="allDetail">
			<logic:iterate id="suballDetail" name="allDetail" length="1">
				<logic:equal name="suballDetail" property="repaymentType" value="I" >
					
					
					<logic:equal name="suballDetail" property="assetFlag" value="A">
					        <td id="assCost1"><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
							<td id="assCost2"><html:text property="assetCost"  maxlength="15" style="text-align: right" styleClass="text" value="${requestScope.allDetail[0].assetCost}" styleId="assetCost" onchange="numberFormatting(this.id,'2');validateAssetCost();assetRangeAmount();calLtvPerc();"/></td>
							<td id="noOfAsst1"><bean:message key="lbl.noOfAsset" /><font color="red">*</font></td>
           					<td id="noOfAsst2"><html:text property="noOfAsset" styleClass="text" maxlength="5" style="text-align: right" value="${requestScope.allDetail[0].noOfAsset}" styleId="noOfAsset" onkeypress="return isNumberKey(event);" onblur="checkNoOfAsset()"/></td>
					</logic:equal>
					
				</logic:equal>
				
								
             </logic:iterate>
             
         </logic:present> 
<!--         sachin-->
         
        
         </tr>
         
         <tr>
     		 <html:hidden property="daysBasis" styleId="daysBasis" value="${requestScope.allDetail[0].daysBasis }"/>
                  
           <td><bean:message key="lbl.tenure" /><font color="red">*</font></td>
           <td ><html:text property="requestedLoanTenure" styleClass="text" maxlength="5" style="text-align: right" value="${requestScope.allDetail[0].requestedLoanTenure}" styleId="requestedLoanTenure" onkeypress="return isNumberKey(event);" onchange="calcDay(); calculateInstallment();calculateMaturityDateInDeal();"/></td>    
         
         <logic:present name="allDetail">
			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${requestScope.allDetail[0].tenureInDay}" styleId="tenureInDay" onchange="calcMonth();calculateMaturityDateInDeal();" maxlength="4"/>
			</td>	
	         
	        </logic:present>
	        <logic:notPresent name="allDetail">

			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${requestScope.allDetail[0].tenureInDay}" styleId="tenureInDay" onchange="calcMonth();calculateMaturityDateInDeal();" maxlength="4"/>
			</td>	
	         
	        </logic:notPresent>
         </tr>
         <tr>
			 <td ><bean:message key="lbl.am" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="requestedLoamt" styleClass="text" styleId="requestedLoamt" style="text-align: right"  value="${requestScope.allDetail[0].requestedLoanAmount}" onchange="numberFormatting(this.id,'2');sumRequestedAmt();rangeAmtMar();validateReqLAmt();calLtvPerc();" /></td>
		    
         
          <td><bean:message key="lbl.loanType" /></td>
          <td><html:select styleId="loanType" property="loanType" styleClass="text" value="${requestScope.allDetail[0].loanType}">
							
							<html:optionsCollection name="getLoanType" label="loanTypeName" value="loanTypeID" />
						</html:select></td>
		
		
        </tr>
         <!--  amandeep start for insurance -->
       
        <tr>
	  				
	  				 <td><bean:message key="lbl.insurancePremium" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="insurancePremium" styleClass="text" styleId="insurancePremium" readonly="true" style="text-align: right" value="${requestScope.allDetail[0].insurancePremium}" tabindex="-1"/></td>
         
	  	<td><bean:message key="lbl.amInsurance" /><font color="red">*</font></td>
           <td ><html:text property="requestedLoanAmount" styleClass="text" maxlength="15" style="text-align: right" readonly="true" value="${requestScope.allDetail[0].requestedLoanAmount}" styleId="requestedLoanAmount"   onblur="numberFormatting(this.id,'2');rangeAmtMar();validateReqLAmt();calLtvPerc();" /></td>
	  	
	  					  </tr>
       
       <!-- amandeep ends for insurance --> 
        <tr>
          <logic:present name="allDetail">
              	<logic:iterate id="suballDetail" name="allDetail" length="1">
              	  <logic:equal name="suballDetail" property="repaymentType" value="I" >
              	  		
              		
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="A">
         	  	 		<td id="marPer1"><bean:message key="lbl.marginPercentage" /><font color="red">*</font></td>
         	  	 		<td id="marPer2"><html:text property="marginPerc" style="text-align: right" styleClass="text"  value="${requestScope.allDetail[0].marginPerc}" styleId="marginPerc" onkeypress="return numbersonly(event, id, 2);"  onchange="checkRate('marginPerc');calMarginAmount();calLtvPerc();"  /></td>
         	  	 	</logic:equal>
           		</logic:equal>
           		
         	   </logic:iterate>
         	 </logic:present> 
         	
          
               <logic:present name="allDetail">
              	<logic:iterate id="suballDetail" name="allDetail" length="1">
              	 <logic:equal name="suballDetail" property="repaymentType" value="I" >
              
              		
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="A">
         	  	 			 <td id="marAmount1"><bean:message key="lbl.marginAmount" /><font color="red">*</font></td>
         	  	 		<td id="marAmount2"><html:text property="marginAmount" styleClass="text" readonly="true" tabindex="-1" style="text-align: right" value="${requestScope.allDetail[0].marginAmount}" styleId="marginAmount"   /></td>
         	  	 	</logic:equal>
         	  	 </logic:equal>
         	  	
         	    </logic:iterate>
         	 </logic:present> 
         	           
        </tr>
       
		  <tr>
          <td><bean:message key="lbl.rateType" /><font color="red">*</font></td>
          <td>
          
              <html:select property="rateType" styleClass="text" styleId="rateType" value="${requestScope.allDetail[0].rateType}" onchange="return getFinalRate();">
              <html:option value="">---Select----</html:option>
              <html:option value="F">Flat Rate</html:option>
              <html:option value="E">Effective Rate</html:option>
              </html:select>
          
          </td>
          <td><bean:message key="lbl.rateMethod" /><font color="red">*</font></td>
          <logic:present name="allDetail">
            <logic:iterate name="allDetail" id="suballDetail" length="1">
            
          <logic:equal  name="suballDetail" property="rateType" value="F">
	           <td>
	              <bean:message key="lbl.fixed" />
	              <input type="radio" name="type" id="type1" value="F" checked="checked"/>
                  <bean:message key="lbl.floating" />
	              <html:radio property="type"  styleId="type2" value="L" />
	      
	          </td>
          </logic:equal>
        <logic:notEqual  name="suballDetail" property="rateType" value="F">
        		   <td>
	              <bean:message key="lbl.fixed" />
	          
	           <logic:equal  name="suballDetail" property="type" value="F">
	           		<input type="radio" name="type" id="type1" value="F" checked="checked"/>
	           </logic:equal>
	           <logic:notEqual  name="suballDetail" property="type" value="F">
	               <html:radio property="type"  styleId="type1" value="F" />
	           </logic:notEqual>  
	            
	            <bean:message key="lbl.floating" />
	            <logic:equal  name="suballDetail" property="type" value="L">
	           		<input type="radio" name="type" id="type2" value="L" checked="checked" />
	           </logic:equal>
	           <logic:notEqual  name="suballDetail" property="type" value="L">
	               <html:radio property="type"  styleId="type2" value="L" />
	           </logic:notEqual> 
	              
	          </td>
        </logic:notEqual>
          </logic:iterate>
              </logic:present>
             
          </tr>
         
		  <tr>
		  <logic:present name="allDetail">
            <logic:iterate name="allDetail" id="suballDetail" length="1">
          <logic:equal  name="suballDetail" property="rateType" value="F">
          <!-- Nishant space starts -->	
          <tr>
          	<td><bean:message key="lbl.fixPriod" /></td>
          	<td><html:text property="fixPriod" styleId="fixPriod" styleClass="text"  style="text-align: right" readonly="true" maxlength="3" value="${requestScope.allDetail[0].fixPriod}"/> </td>
          </tr>
          	<!-- Nishant space starts -->
	          <td><bean:message key="lbl.baseRateType" /><font color="red">*</font></td>
	          <td >
	            <html:select property="baseRateType" styleClass="text" styleId="baseRateType" disabled="true" value="${requestScope.allDetail[0].baseRateType}" onchange="return getBaseRate();">
	             <html:option value="">---Select----</html:option>
	             <logic:present name="baseRateList">
	               <html:optionsCollection name="baseRateList" label="id" value="id" /> 
	             </logic:present>
	          </html:select>
	         </td>
	          <td><bean:message key="lbl.rate" /><font color="red">*</font></td>
	          <td><html:text property="baseRate" styleClass="text"  style="text-align: right"disabled="true" styleId="baseRate" readonly="true"  /> </td></tr>
			  <tr>
			    <td><bean:message key="lbl.markUp" /></td>

			    <td><html:text property="markUp" style="text-align: right" styleClass="text" styleId="markUp" value="${requestScope.allDetail[0].markUp}" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkMarkUpRate('markUp');" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" readonly="true" tabindex="-1"/></td>


		    </logic:equal>
		    <logic:notEqual name="suballDetail" property="rateType" value="F">
		    <!-- Nishant space starts -->	
		    <tr>
          		<td><bean:message key="lbl.fixPriod" /></td>
          		<td><html:text property="fixPriod" styleId="fixPriod" styleClass="text"  style="text-align: right" readonly="true" maxlength="3" value="${requestScope.allDetail[0].fixPriod}"/> </td>
          	</tr>
          	<!-- Nishant space starts -->
		    	 <td><bean:message key="lbl.baseRateType" /><font color="red">*</font></td>
	         <%--  <td >
	            <html:select property="baseRateType" styleClass="text" styleId="baseRateType" value="${requestScope.allDetail[0].baseRateType}" onchange="return getBaseRate();">
	             <html:option value="">---Select----</html:option>
	             <logic:present name="baseRateList">
	               <html:optionsCollection name="baseRateList" label="id" value="id" /> 
	             </logic:present>
	          </html:select>
	         </td> --%>
	         
	          <td>

              <html:text property="baseRateType" styleClass="text" styleId="baseRateType" readonly="true" value="${requestScope.allDetail[0].baseRateType}" tabindex="-1"/>

	         </td>
	         
	          <td><bean:message key="lbl.rate" /><font color="red">*</font></td>
	          <td><html:text property="baseRate" styleClass="text" value="${requestScope.allDetail[0].baseRate}" style="text-align: right" styleId="baseRate" readonly="true"  /> </td></tr>
			  <tr>
			    <td><bean:message key="lbl.markUp" /></td>

			    <td><html:text property="markUp" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].markUp}" styleId="markUp"  onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkMarkUpRate('markUp');" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" readonly="true" tabindex="-1"/></td>

		    </logic:notEqual>
		 </logic:iterate>
		</logic:present>
		
		    <td><bean:message key="lbl.finalRate" /><font color="red">*</font></td>

		    <td><html:text property="effectiveRate" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].effectiveRate}"  styleId="effectiveRate" onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);checkRate('effectiveRate');calculateFinalRate();" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 8, id);"  /></td>

		    </tr>
		  <tr>
		    <td><bean:message key="lbl.repaymentType" /><font color="red">*</font></td>
		    <td>
		    
		    <html:text styleClass="text" property="showRepaymentType" tabindex="-1" readonly="true" styleId="showRepaymentType" value="${requestScope.allDetail[0].showRepaymentType }"   />
		    <html:hidden styleClass="text" property="repaymentType" styleId="repaymentType" value="${requestScope.allDetail[0].repaymentType }"   />
          </td>
		    
		   <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
<!--              <td id="installType1"><bean:message key="lbl.installmentType" /><font color="red">*</font></td>-->
<!--		    <td id="installType2"><html:select property="installmentType" styleClass="text" styleId="installmentType" value="${requestScope.allDetail[0].installmentType}">-->
<!--            <html:option value="">---Select----</html:option>-->
<!--             <html:option value="E">Eq. INSTALLMENT</html:option>-->
<!--		     <html:option value="G">Gr. INSTALLMENT</html:option>-->
<!--		     <html:option value="P">Eq. PRINCIPAL</html:option>-->
<!--		     <html:option value="Q">Gr. PRINCIPAL1</html:option>-->
<!--		     <html:option value="R">Gr. PRINCIPAL2</html:option>-->
<!--          </html:select></td>-->
				<td id="installType1"><bean:message key="lbl.installmentType" /><font color="red">*</font></td>
	             <td id="installType2"><html:select property="installmentType" styleId="installmentType" styleClass="text" value="${requestScope.allDetail[0].installmentType}" onchange="editNoInstal();">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="installmentTypeList">
				  <logic:notEmpty name="installmentTypeList">
				  <html:optionsCollection name="installmentTypeList" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>
				  </td>
          
           </tr>
		    
		  <tr>
		  
		  <td id="insMod1"><bean:message key="lbl.insMode" /><font color="red">*</font></td>
		    <td id="insMod2"><html:select property="instMode" styleClass="text" styleId="instMode" value="${requestScope.allDetail[0].instMode}">
                    <html:option value="">---Select----</html:option>
                    <html:option value="A">ADVANCE</html:option>
		            <html:option value="R">ARREAR</html:option>
                </html:select>
            </td>
		  
		    <td id="frequnc1"><bean:message key="lbl.frequency" /><font color="red">*</font></td>
		    <td id="frequnc2"><html:select property="frequency" styleClass="text" styleId="frequency" value="${requestScope.allDetail[0].frequency}" onchange="calculateInstallment();getDueDay();editNoInstal();">
            <html:option value="">---Select----</html:option>
              <html:option value="M">MONTHLY</html:option>
		      <html:option value="B">BIMONTHLY</html:option>
		      <html:option value="Q">QUARTERLY</html:option>
		      <html:option value="H">HALFYERALY</html:option>
		      <html:option value="Y">YEARLY</html:option>
          </html:select></td>
		   
		    </tr>
		    
		    
		     <tr>
		  
		  <td id="insComFre1"><bean:message key="lbl.interestCompoundingFrequency" />
		    <td id="insComFre2"><html:select property="interestCompoundingFrequency" styleClass="text" styleId="interestCompoundingFrequency" value="${requestScope.allDetail[0].interestCompoundingFrequency}" onchange="validateIntCompoundingFrequency();">
             
	           <html:option value="">NOT APPLICABLE</html:option>
				  <html:option value="M">MONTHLY</html:option>
			      <html:option value="B">BIMONTHLY</html:option>
			      <html:option value="Q">QUARTERLY</html:option>
			      <html:option value="H">HALFYERALY</html:option>
			      <html:option value="Y">YEARLY</html:option>
			    <%--    <logic:present name="intComFreq">
		               <html:optionsCollection name="intComFreq" label="name" value="id" /> 
		       </logic:present --%><!-- tej -->
         	  </html:select>
            </td>
		  
		    <td id="insCalMet1"><bean:message key="lbl.interestCalculationMethod" /><font color="red">*</font></td>
		    <td id="insCalMet2"><html:select property="interestCalculationMethod" styleClass="text" styleId="interestCalculationMethod" value="${requestScope.allDetail[0].interestCalculationMethod}" onchange="showInterestDueDate(true);setInterestDueDate();validateInterestDueDate();showDueDay();nullNextDue();getDueDay();">">
            
        <%--       <html:option value="D">DUE DATE</html:option>
		      <html:option value="E">EOM</html:option>
		     <html:option value="F">FEOM</html:option> --%>
		      <logic:present name="intCal">
		               <html:optionsCollection name="intCal" label="name" value="id" /> 
		       </logic:present> 
          </html:select></td>
		   
		    </tr>
		    
		      <tr>
		  
				  <td id="insFre1"><bean:message key="lbl.interestFrequency" /><font color="red">*</font></td>
				    <td id="insFre2"><html:select property="interestFrequency" styleClass="text" styleId="interestFrequency" value="${requestScope.allDetail[0].interestFrequency}" onchange="setInterestDueDate();validateInterestFrequency();" >
		             
			              <html:option value="M">MONTHLY</html:option>
					      <html:option value="B">BIMONTHLY</html:option>
					      <html:option value="Q">QUARTERLY</html:option>
					      <html:option value="H">HALFYERALY</html:option>
					      <html:option value="Y">YEARLY</html:option>
					    <%--    <html:option value="X">FINANCIAL QUARTERLY</html:option>
					          <html:option value="L">FINANCIAL HALFYERALY</html:option>
					            <html:option value="Z">FINANCIAL YEARLY</html:option> --%>
					      <%--  <logic:present name="intFreq">
		           				    <html:optionsCollection name="intFreq" label="name" value="id" /> 
		    			   </logic:present> --%>
		         	  </html:select>
		            </td>
		  
		    	
		    </tr>
		    
		     <tr>
			    <td id="noOfIns1"><bean:message key="lbl.noOfInstall" /></td>
			    <td id="noOfIns2"><html:text property="noOfInstall" tabindex="-1" onkeypress="return isNumberKey(event);" maxlength="5" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].noOfInstall}" readonly="true" styleId="noOfInstall"  /></td>
			    <td><bean:message key="lbl.creditPeriod" /><font color="red">*</font></td>
           <td ><html:text property="creditPeriod" styleClass="text" maxlength="5"  value="${requestScope.allDetail[0].creditPeriod}" styleId="creditPeriod" onkeypress="return isNumberKey(event);" /></td>
            
          </logic:equal>
          
          </logic:iterate>
          </logic:present>
          
         
		    </tr>  
		    <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
	             <tr>
	             
	              <td id="repayMod1"><bean:message key="lbl.repaymentMode" /><font color="red">*</font></td>
			    <td id="repayMod2"><html:select property="paymentMode" styleClass="text" styleId="paymentMode" value="${requestScope.allDetail[0].paymentMode}">
	              <html:option value="">--Select--</html:option>
   					    <logic:present name="paymentModes">
					<logic:notEmpty name="paymentModes">
					<html:optionsCollection name="paymentModes" label="paymentMode" value="paymentModeId" />
					</logic:notEmpty>		
				</logic:present>
	          </html:select></td>
			    <td id="noIns1"><bean:message key="lbl.noIns" /></td>
			    
			    <logic:present name="allDetail">
			    	  <td id="noIns2"><html:text styleClass="text" property="installments" styleId="installments" value="${requestScope.allDetail[0].installments }" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
			    </logic:present>
			       <logic:notPresent name="allDetail">
			    	  <td id="noIns2"><html:text styleClass="text" property="installments" styleId="installments" value="0" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
			    </logic:notPresent>
			      
			    
			    </tr>
		    </logic:equal>
		    	    
		    </logic:iterate>
		    </logic:present>
		    
		  
		    
		      <tr>
		      		<%-- <html:hidden property="maturityDate" styleId="maturityDate1" value="${requestScope.allDetail[0].maturityDate}" /> --%>
              		<td id="effDateHeader"><bean:message key="lbl.effDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		    		<td id="effDate"><html:text styleClass="text" property="repayEffectiveDate" styleId="repayEffectiveDate" value="${userobject.businessdate}" maxlength="10" onchange="checkDate('repayEffectiveDate');validRepayDateInDeal();calculateMaturityDateInDeal();checkNewRepay();getDueDayNextDueDate();setInterestDueDate();" /></td>
		    
		    
		    <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
		    <td id="cyclDate1"><bean:message key="lbl.cycleDate" /><font color="red">*</font></td>
		    <td id="cyclDate2">
		    
		    <html:select property="cycleDate" styleClass="text" styleId="cycleDate" value="${requestScope.dueDayList[0].cycleDate}" onchange="nullNextDue();getDueDay();">
              <html:option value="">---Select----</html:option>
              <logic:present name="cycle">
	               <html:optionsCollection name="cycle" label="name" value="id" /> 
	             </logic:present>
		   </html:select>
		    
		  	    
		    </td>
		    
		    </logic:equal>

		    </logic:iterate>
		    </logic:present>
		
		    </tr>
		      <tr>
		      		<td id="nddheader"> <bean:message key="lbl.nextDueDate" /><font color="red">*</font></td>
		    		<td id="ndd">
		         			<html:text property="nextDueDate" styleClass="text"  value="${requestScope.dueDayList[0].nextDueDate}" styleId="nextDueDate" onchange="checkDueDate(value);calTenureMonthForMaturityDateNextDueDate();" />
		    		</td>
						<td id="interestDueDateHeader1"><bean:message key="lbl.interestDueDate"/><font color="red">*</font></td>
              		 <td id = "interestDueDateHeader2">
						<logic:notPresent name="allDetail">
							<html:text styleClass="text" property="interestDueDate" styleId="interestDueDate"   onchange="calTenureMonthForMaturityDateNextDueDate();datevalidate();validateInterestDueDate();"/>
							
						</logic:notPresent>
						<logic:present name="allDetail">
							<html:text styleClass="text" property="interestDueDate" value="${requestScope.allDetail[0].interestDueDate}"  styleId="interestDueDate"  onchange="calTenureMonthForMaturityDateNextDueDate();datevalidate();validateInterestDueDate();"/>
							
						</logic:present>
					</td>
		   
             </tr>
             				  <!-- Code Added for the Handling separate Interest first due date and maturity date| Rahul papneja| 31012018 -->
				  <tr id="SeparateInterestDate" style="display:none">
				  <!-- Start|01 | Code Added for firstInterestDueDate Handling till <td> tag closed -->
				  <td id="firstInterestDueDateLBL" style="display:none"><bean:message key="lbl.firstInterestDueDate"/></td>
				  <td id = "firstInterestDueDateTXT" style="display:none" >
						<logic:notPresent name="allDetail">
							<html:text styleClass="text" property="firstInterestDueDate" styleId="firstInterestDueDate" readonly="true"  onchange="datevalidate();" />
							
						</logic:notPresent>
						<logic:present name="allDetail">
						
							<html:text styleClass="text" property="firstInterestDueDate" value="${requestScope.allDetail[0].firstInterestDueDate}"  styleId="firstInterestDueDate" readonly="true"  /> <!--onchange="calTenureMonthForMaturityDate();validateMaturityDate();" /> -->
						
							
						</logic:present>
					</td>
					<!-- 01| End -->

				    <!-- Start|02-2 | Code Added for maturityDate Handling till <td> tag closed -->
				  <td id="maturityDateLBL"><bean:message key="lbl.maturityDate"/><font color="red">*</font></td>
				  <td id = "maturityDateTXT">
						<logic:notPresent name="allDetail">
							<html:text styleClass="text" property="maturityDate" styleId="maturityDate1" readonly="true"  onchange="calTenureMonthForMaturityDate();validateMaturityDate();" onblur="setMaturityTemp();" />
							
						</logic:notPresent>
						<logic:present name="allDetail">
						
							<html:text styleClass="text" property="maturityDate" value="${requestScope.allDetail[0].maturityDate}"  styleId="maturityDate1" readonly="true"  onchange="calTenureMonthForMaturityDate();validateMaturityDate();" onblur="setMaturityTemp();" />
						
							
						</logic:present>
					</td>
					<!-- 02-2 | End -->
				  
				  
				  
				  
				  </tr>
				  <!-- Ends Here| Rahul papneja -->
				  
			 <tr>
			 
		    		<td><bean:message key="lbl.purpose" /><font color="red">*</font></td>
		    		<td >
		    
		     			<a rel="tooltip3" id="tool">
		     			
		     			  <html:text property="loanPurpose" maxlength="50"  styleClass="text" readonly="true" value="${requestScope.allDetail[0].loanPurpose}" styleId="loanPurpose" onchange="return upperMe('loanPurpose');" onmouseover="applyToolTip(id);"  />
  							<html:hidden  property="lbxLoanPurpose" value="${requestScope.allDetail[0].lbxLoanPurpose}" styleId="lbxLoanPurpose"   />
		     	<%-- 		  <input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(5056,'LoanDetailsForm','loanPurpose','','','','','','loanPurpose');" value=" " tabindex="1" name="dealButton" />	--%>
		     				     <input type="button" class="lovbutton" id="leadButton1" 	onclick="openLOVCommon(5056,'LoanDetailsForm','loanPurpose','product','lbxLoanPurpose','lbxProductID','Please select product first!!!','','lbxLoanPurpose');" value=" " tabindex="1" name="dealButton" />	
		     			</a>
		     		
		     			
		  	 		</td>  
		  	 		<!-- Code Added for Edit Due Date| Rahul papneja |30012018 -->
		  	 		 <!--       Vishal changes start -->
			<td>
			<bean:message key="lbl.editDueDate"/><font color="red">*</font></td>
			<td>
			<html:select property="editDueDate" styleId="editDueDate" styleClass="text" disabled="true" value="${requestScope.allDetail[0].editDueDate}">
			<html:option value="N">NO</html:option>
			<html:option value="Y">Yes</html:option>
			<!-- <html:option value="N">NO</html:option> -->
			</html:select>
			</td>
<!-- 	   Vishal changes end -->  
		  	 		<!-- Ends Here| Rahul papneja -->
		    </tr>
		    <tr>
		    	<td ><bean:message key="lbl.sectorType" /><font color="red">*</font></td>
		    	 <td >
	            <html:select property="sectorType" styleClass="text" styleId="sectorType" onchange="showValues();" value="${requestScope.allDetail[0].sectorType}">
	             <html:option value="">---Select----</html:option>
	             <logic:present name="sector">
	               <html:optionsCollection name="sector" label="name" value="id" /> 
	             </logic:present>
	          </html:select>
	         </td>
	      
	         
	           <%-- Start By Prashant --%>
	         <logic:present name="allDetail">
              	<logic:iterate id="suballDetail" name="allDetail" length="1">
              	<logic:equal name="suballDetail" property="repaymentType" value="I" >
              		<logic:equal name="suballDetail" property="assetFlag" value="A">
				        <td><bean:message key="lbl.LTV" /></td>
					    <td>
					         	<html:text property="ltvPerc" styleClass="text"  value="${requestScope.allDetail[0].ltvPerc}" styleId="ltvPerc"  readonly="true" />
					    </td>
					   </logic:equal>
				    </logic:equal>
	         </logic:iterate>
	         
	         </logic:present>
	          <logic:notPresent name="allDetail">
	          
              	      <td id="ltvDesc"><bean:message key="lbl.LTV" /></td>
					    <td id="ltvP">
					         	<html:text property="ltvPerc" styleClass="text"  value="${requestScope.allDetail[0].ltvPerc}" styleId="ltvPerc"  readonly="true" />
					    </td>
	         
	         </logic:notPresent>
	         
	           <%-- End By Prashant --%>
	         
		    </tr>
		<!--		   @Surendra Code Start	   -->
		    <tr >
              <logic:present name="allDetail">
                  <logic:iterate id="suballDetail" name="allDetail" length="1">
                    <logic:equal name="suballDetail" property="repaymentType" value="N" >
		        <td ><bean:message key="lbl.interestCalc" /><font color="red">*</font></td>
		    	 <td >		    	
	            <html:select property="interestCalc" styleClass="text" styleId="interestCalc" value="${requestScope.allDetail[0].interestCalc}"  >
	             <html:option value="">---Select----</html:option>
	             <logic:present name="interestFrom">
	               <html:optionsCollection name="interestFrom" label="name" value="id" /> 
	             </logic:present>	            
	          </html:select>	          
	         </td>
	          </logic:equal>
	             </logic:iterate>
	             </logic:present>
	             <logic:notPresent name="allDetail">	                       
	             <td >
	             <div id="int1" style="display:none">
	             <bean:message key="lbl.interestCalc" /><font color="red">*</font></div></td>
	              <td>	<div id="int2" style="display:none">	    	
	            <html:select property="interestCalc" styleClass="text" styleId="interestCalc" value="${requestScope.allDetail[0].interestCalc}"  >
	             <html:option value="">---Select----</html:option>
	             <logic:present name="interestFrom">
	               <html:optionsCollection name="interestFrom" label="name" value="id" /> 
	             </logic:present>	            
	          </html:select>	
	          </div>          
	         </td> 
	             </logic:notPresent>
	         </tr>       
<!--		   @Surendra Code End	   -->	
		<tr id="netLtvRow">
			<logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              	<logic:equal name="suballDetail" property="repaymentType" value="I" >
              	<logic:equal name="suballDetail" property="assetFlag" value="A"> 	
			<td><bean:message key="lbl.netLtv" /></td>
			<td>
			<html:text property="netLtv" styleClass="text"  value="${requestScope.allDetail[0].netLtv}" styleId="netLtv"  readonly="true" />
			</td>	
			 <td><bean:message key="lbl.totalVatAmt" /></td>
		   <td>
		   <html:text property="totalVatAmt" styleClass="text"  value="${requestScope.allDetail[0].totalVatAmt}" styleId="totalVatAmt"  readonly="true" />
		   </td> 
				</logic:equal>
				</logic:equal>
	         </logic:iterate>	         
	        </logic:present>
	        <logic:notPresent name="allDetail">
	          
           <td><bean:message key="lbl.netLtv" /></td>
		   <td>
		   <html:text property="netLtv" styleClass="text"  value="${requestScope.allDetail[0].netLtv}" styleId="netLtv"  readonly="true" />
		   </td>     
		    <td><bean:message key="lbl.totalVatAmt" /></td>
		   <td>
		   <html:text property="totalVatAmt" styleClass="text"  value="${requestScope.allDetail[0].totalVatAmt}" styleId="totalVatAmt"  readonly="true" />
		   </td> 
	      </logic:notPresent>	    
		 </tr> 
		    <tr>
		
		    	   <td><bean:message key="lbl.loanClassification" /></td>
			<td>
			
			<html:hidden  property="lbxLoanClassification" styleId="lbxLoanClassification" value="${requestScope.allDetail[0].lbxLoanClassification}" />
			<html:select property="loanClassification" styleId="loanClassification" size="5"  multiple="multiple" style="width: 120" >
   			<logic:present name="loanClassificationList">
   			<logic:notEmpty name="loanClassificationList">
       			 <html:optionsCollection name="loanClassificationList" value="loanClassificationId" label="loanClassificationLabel"/>
       		</logic:notEmpty>
       		</logic:present>
     		</html:select>
      		<html:button property="userButton" styleId="userButton1" onclick="return openMultiSelectLOVCommon(2050,'sourcingForm','loanClassification','','', '','','','lbxLoanClassification');" value=" " styleClass="lovbutton"></html:button>
     		
     		<td><bean:message key="lbl.businessType" /><font color="red">*</font></td>
			 <td ><html:select property="businessType" styleClass="text" styleId="businessType" value="${requestScope.allDetail[0].businessType}"  onchange="return getbusinessbutton();">
			 <html:option value="">---Select---</html:option>
	             		<logic:present name="getBusiness">
	               			<html:optionsCollection name="getBusiness" label="businessdesc" value="businessId" /> 
	             		</logic:present>
	          	</html:select>
	          	 <input type="hidden" name="oldPartnerType" id="oldPartnerType" value="${requestScope.allDetail[0].businessType}" /> 
         	</td>
         	
     		
     		
	         </tr>
 
 <tr>
			  <td  id="gblock" style="display: none;"><bean:message key="lbl.grossBlock" /></td>
          	 <td  id="gblock1" style="display: none;"><html:text property="grossBlock" styleClass="text" maxlength="10" style="text-align: right" value="${requestScope.allDetail[0].grossBlock}" styleId="grossBlock" onkeypress="return isNumberKey(event);" /></td>
			 
			 <td  id="nblock" style="display: none;"><bean:message key="lbl.netBlock" /></td>
          	 <td  id="nblock1" style="display: none;"><html:text property="netBlock" styleClass="text" maxlength="10" style="text-align: right" value="${requestScope.allDetail[0].netBlock}" styleId="netBlock" onkeypress="return isNumberKey(event);" /></td>
          	 
			  </tr>
			  
		 <tr>
			<td >
			 <button type="button" class="topformbutton2" id="Save" title="Alt+V" accesskey="V" onclick="saveLoanDetail('LoanDetailsForm');"><bean:message key="button.save" /></button>
			 <button type="button" class="topformbutton2" id="refresh" title="Alt+R" accesskey="R" onclick="refreshLoanDetail();"><bean:message key="button.refresh" /></button>
			  <button type="button" class="topformbutton4" id="Save" title="Alt+V" accesskey="V" onclick="openPopForSectorDetails('N');"><bean:message key="button.sectorTypeDetails" /></button>
			    </td>
			    
			   <td id ="partner_mode" style="display: none">        
		      <button type="button" class="topformbutton4" id="partner" title="Alt+P" accesskey="P" onclick="getbusinessType();"><bean:message key="lbl.partnerDetails" /></button>
		      </td>
		</tr>
       
		 </table> </td></tr>
    </table>
    </fieldset>
<fieldset>	
		 <legend><bean:message key="lbl.loanDetail" /></legend>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
         <tr>
         <td class="gridtd">
    	 <table width="100%" border="0" cellspacing="1" cellpadding="1">
            <tr class="white2">
             <td width="3%">
	         <input type="checkbox" name="allchk" id="allchk" value="checkbox" onclick="return allChecked();"/>
	         </td> 
             <td ><strong><bean:message key="lbl.product" /></strong></td>
             <td ><strong><bean:message key="lbl.scheme" /></strong></td>
             <td ><b><bean:message key="lbl.am" /></b></td>
             <td ><strong><bean:message key="lbl.tenure" /></strong></td>
             <td ><b><bean:message key="lbl.rateType" /></b></td>
             <td ><strong><bean:message key="lbl.rateMethod" /></strong></td>
             <td ><strong><bean:message key="lbl.purpose" /></strong></td>
            </tr>
          <logic:present name="loanList">
            <logic:notEmpty name="loanList">
           <logic:iterate name="loanList" id="subloanList" length="1">
		       <tr class="white1">
		         
	             <td ><input type="checkbox" name="chk" id="chk" value="<bean:write name="subloanList" property="dealLoanId" />" /></td>	 
	             <td ><a href="#" onclick="modifyDetail(<bean:write name="subloanList" property="dealLoanId" />)"><bean:write name="subloanList" property="product" /></a></td>
	             <td ><bean:write name="subloanList" property="scheme" /></td>
	             <td ><bean:write name="subloanList" property="requestedLoanAmount" /></td>
	             <td ><bean:write name="subloanList" property="requestedLoanTenure" /></td>
	             <td ><bean:write name="subloanList" property="rateType" /></td>
	             <td><bean:write name="subloanList" property="type" /></td>
	             <td ><bean:write name="subloanList" property="loanPurpose" /></td>
	           </tr>
           </logic:iterate>
           
           <table width="100%" border="0" cellpadding="1" cellspacing="1">
                <tr class="white1">
                    <td colspan="4">
          
            <button type="button" name="buttonDelete" title="Alt+D" id="Delete" accesskey="D" class="topformbutton2" onclick="deleteLoanDetails()" ><bean:message key="button.delete" /></button>
               </td>
               </tr>
          </table>
           </logic:notEmpty>
           
            </logic:present>
            
         </table>     
        </td>
        </tr>
      </table>
      
  

</fieldset>
	</logic:present>

	
</html:form>
</logic:notPresent>
<%-- ---------------------------------------  View  Deal  --- ------------------------     --%>
<logic:present name="viewDeal">
<html:form action="/loanProcessAction" method="post" styleId="LoanDetailsForm">
<html:errors />
<logic:present name="dealId">
	<html:hidden styleId="pageStatus" property="pageStatus" value="U"/>
</logic:present>
<input type="hidden" id="dealId" name="dealId" value="${dealHeader[0].dealId}"/>
<input type="hidden" id="dealProduct" name="dealProduct" value="${dealHeader[0].dealProduct}"/>
<input type="hidden" id="dealLoanIds" name="dealLoanIds" value="${requestScope.allDetail[0].dealLoanId}"/>
<fieldset>
<legend><bean:message key="lb.parameter" /></legend>
<table cellspacing=0 cellpadding=0 width="100%" border=0>
<tr>
    <td>
      <table cellspacing=1 cellpadding=1 width="100%" border=0>
        <tr><html:hidden property="productType" value="AType" />
        <html:hidden property="dealLoanId" value="${requestScope.allDetail[0].dealLoanId}" />
          <td><bean:message key="lbl.product" /><font color="red">*</font></td>
           <td >
           <html:text  property="product" styleId="product" styleClass="text" disabled="true" value="${requestScope.allDetail[0].product}" tabindex="-1"/>
          
          </td>
          <td><bean:message key="lbl.scheme" /><font color="red">*</font></td>
          <td>
             <html:text property="scheme" styleId="scheme" styleClass="text" disabled="true" value="${requestScope.allDetail[0].scheme}" tabindex="-1"/>
          </td>
         </tr>
        <tr>
           <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail"  length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" >   
          <td><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
          <td ><html:text property="assetCost" disabled="true" maxlength="15" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].assetCost}" styleId="assetCost" onkeypress="return isNumberKey(event);" />  </td>
          <td><bean:message key="lbl.noOfAsset" /><font color="red">*</font></td>
          <td><html:text property="noOfAsset" disabled="true" styleClass="text" maxlength="5" style="text-align: right" value="${requestScope.allDetail[0].noOfAsset}" styleId="noOfAsset"/></td>
       </logic:equal>
       </logic:iterate>
       </logic:present>
              
           </tr>
         
         <tr>
         
         <td><bean:message key="lbl.tenure" /><font color="red">*</font></td>
           <td ><html:text property="requestedLoanTenure" disabled="true" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].requestedLoanTenure}" styleId="requestedLoanTenure" onkeypress="return isNumberKey(event);" onchange="calculateMaturityDateInDeal();" /></td>
         
         <logic:present name="allDetail">
			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${requestScope.allDetail[0].tenureInDay}" styleId="tenureInDay" readonly="true" onchange="calcMonth();calculateMaturityDateInDeal();" maxlength="4"/>
			</td>	
	         
	        </logic:present>
	        <logic:notPresent name="allDetail">

			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${requestScope.allDetail[0].tenureInDay}" styleId="tenureInDay" readonly="true" onchange="calcMonth();calculateMaturityDateInDeal();" maxlength="4"/>
			</td>	
	         
	        </logic:notPresent>
         </tr>  
         <tr>
			 <td ><bean:message key="lbl.am" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="requestedLoamt" styleClass="text" styleId="requestedLoamt" readonly="true" style="text-align: right"  value="${requestScope.allDetail[0].requestedLoamt}" tabindex="-1"/></td>
 
     	      	
       <td><bean:message key="lbl.loanType" /></td>
          <td><html:select styleId="loanType" property="loanType" styleClass="text" value="${requestScope.allDetail[0].loanType}" disabled="true">
							
							<html:optionsCollection name="getLoanType" label="loanTypeName" value="loanTypeID" />
						</html:select></td>
     
        </tr>
        
         <!--  amandeep start for insurance -->
       
        <tr>
	  				
	  				 <td><bean:message key="lbl.insurancePremium" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="insurancePremium" styleClass="text" styleId="insurancePremium" readonly="true" style="text-align: right" value="${requestScope.allDetail[0].insurancePremium}" tabindex="-1"/></td>
		                
	  				
	  				<td><bean:message key="lbl.amInsurance" /><font color="red">*</font></td>
           <td ><html:text property="requestedLoanAmount" disabled="true" maxlength="15" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].requestedLoanAmount}" styleId="requestedLoanAmount"  /></td>
 
	  					  </tr>
       
       
       <!-- amandeep ends for insurance --> 
        <tr>
         <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
          <td><bean:message key="lbl.marginPercentage" /><font color="red">*</font></td>
          <td><html:text property="marginPerc" disabled="true" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].marginPerc}"  styleId="marginPerc" /> </td>
         
          <td><bean:message key="lbl.marginAmount" /><font color="red">*</font></td>
          <td><html:text property="marginAmount" disabled="true" tabindex="-1" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].marginAmount}" readonly="true" styleId="marginAmount" />  </td>
       </logic:equal>
       </logic:iterate>
       </logic:present>
        </tr>
       
		  <tr>
          <td><bean:message key="lbl.rateType" /><font color="red">*</font></td>
          <td><html:select property="rateType" disabled="true" styleClass="text" styleId="rateType" value="${requestScope.allDetail[0].rateType}" onchange="return getFinalRate();">
            <html:option value="">---Select----</html:option>
              <html:option value="F">Flat Rate</html:option>
              <html:option value="E">Effective Rate</html:option>
              <html:option value="P">PTPM Rate</html:option>
          </html:select></td>
          <td><bean:message key="lbl.rateMethod" /><font color="red">*</font></td>
          <logic:present name="allDetail">
            <logic:iterate name="allDetail" id="suballDetail" length="1">
           <td>
              <bean:message key="lbl.fixed" />
          
           <logic:equal  name="suballDetail" property="type" value="F">
           		<input type="radio" name="type" disabled="disabled" id="type1" value="F" checked="checked"/>
           </logic:equal>
           <logic:notEqual  name="suballDetail" property="type" value="F">
               <html:radio property="type" disabled="true"  styleId="type1" value="F" />
           </logic:notEqual>  
            
            <bean:message key="lbl.floating" />
            <logic:equal  name="suballDetail" property="type" value="L">
           		<input type="radio" name="type" disabled="disabled" id="type2" value="L" checked="checked"/>
           </logic:equal>
           <logic:notEqual  name="suballDetail" property="type" value="L">
               <html:radio property="type" disabled="true"  styleId="type2" value="L" />
           </logic:notEqual> 
              
          </td>
          </logic:iterate>
              </logic:present>
              <logic:notPresent name="allDetail">
               <td>
              	 <bean:message key="lbl.fixed" />
              	   <html:radio property="type"  styleId="type1" disabled="true" value="F" />
              	   <bean:message key="lbl.floating" />
              	   <html:radio property="type" disabled="true"  styleId="type2" value="L" />
              	   </td>
              </logic:notPresent>
          </tr>
         <!-- Nishant space starts -->	
         <tr>
          	<td><bean:message key="lbl.fixPriod" /></td>
          	<td><html:text property="fixPriod" styleId="fixPriod" styleClass="text"  style="text-align: right" disabled="true" value="${requestScope.allDetail[0].fixPriod}"/> </td>
         </tr> 
         <!-- Nishant space starts -->
		  <tr>
          <td><bean:message key="lbl.baseRateType" /><font color="red">*</font></td>
      <%--     <td >
            <html:select property="baseRateType" disabled="true" styleClass="text" styleId="baseRateType" value="${requestScope.allDetail[0].baseRateType}" onchange="getBaseRate();">
             <html:option value="">---Select----</html:option>
             <logic:present name="baseRateList">
               <html:optionsCollection name="baseRateList" label="id" value="id" /> 
             </logic:present>
          </html:select>
         </td> --%>
         
          <td>

              <html:text property="baseRateType" styleClass="text" styleId="baseRateType" readonly="true" value="${requestScope.allDetail[0].baseRateType}" tabindex="-1"/>

         </td>
         
          <td><bean:message key="lbl.rate" /><font color="red">*</font></td>
          <td><html:text property="baseRate" disabled="true" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].baseRate}" styleId="baseRate" readonly="true" onkeypress="return isNumberKey(event);" /> </td></tr>
		  <tr>
		    <td><bean:message key="lbl.markUp" /></td>
		    <td><html:text property="markUp" disabled="true" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].markUp}" styleId="markUp"  onkeypress="return sevenDecimalnumbersonlyForMarkup(event, id, 3)" onblur="sevenDecimalNumber(this.value, id);" onkeyup="sevenDecimalcheckNumber(this.value, event, 3, id);" onchange="checkMarkUpRate('markUp');calculateFinalRate();"/></td>
		   
		    <td><bean:message key="lbl.finalRate" /><font color="red">*</font></td>
		    <td><html:text property="effectiveRate" disabled="true" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].effectiveRate}" readonly="true" styleId="effectiveRate" onkeypress="return isNumberKey(event);" /></td>
		    </tr>
		  <tr>
		    <td><bean:message key="lbl.repaymentType" /><font color="red">*</font></td>
		    <td>  <html:text styleClass="text" property="showRepaymentType" disabled="true" styleId="showRepaymentType" value="${requestScope.allDetail[0].showRepaymentType }"   />
		    <html:hidden styleClass="text" property="repaymentType" styleId="repaymentType" value="${requestScope.allDetail[0].repaymentType }"   /></td>
		    
		    <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" > 
<!--		    <td><bean:message key="lbl.installmentType" /><font color="red">*</font></td>-->
<!--		    <td><html:select property="installmentType" disabled="true" styleClass="text" styleId="installmentType" value="${requestScope.allDetail[0].installmentType}">-->
<!--             <html:option value="">---Select----</html:option>-->
<!--             <html:option value="E">Eq. INSTALLMENT</html:option>-->
<!--		     <html:option value="G">Gr. INSTALLMENT</html:option>-->
<!--		     <html:option value="P">Eq. PRINCIPAL</html:option>-->
<!--		     <html:option value="Q">Gr. PRINCIPAL1</html:option>-->
<!--		     <html:option value="R">Gr. PRINCIPAL2</html:option>-->
<!--          </html:select></td>-->
			
				<td><bean:message key="lbl.installmentType" /><font color="red">*</font></td>
	             <td><html:select property="installmentType" styleId="installmentType" disabled="true" styleClass="text" value="${requestScope.allDetail[0].installmentType}" onchange="editNoInstal();">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="installmentTypeList">
				  <logic:notEmpty name="installmentTypeList">
				  <html:optionsCollection name="installmentTypeList" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>
				  </td>

          </logic:equal>
          </logic:iterate>
          </logic:present>
		    </tr>
		  <tr>
		   <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" > 
              
		     <td><bean:message key="lbl.insMode" /><font color="red">*</font></td>
		    <td><html:select property="instMode" disabled="true" styleClass="text" styleId="instMode" value="${requestScope.allDetail[0].instMode}">
                    <html:option value="">---Select----</html:option>
                    <html:option value="A">ADVANCE</html:option>
		            <html:option value="R">ARREAR</html:option>
                </html:select>
            </td>
		    
		    
		    
		    <td><bean:message key="lbl.frequency" /><font color="red">*</font></td>
		    <td><html:select property="frequency" disabled="true" styleClass="text" styleId="frequency" value="${requestScope.allDetail[0].frequency}">
             <html:option value="">---Select----</html:option>
              <html:option value="M">MONTHLY</html:option>
		      <html:option value="B">BIMONTHLY</html:option>
		      <html:option value="Q">QUARTERLY</html:option>
		      <html:option value="H">HALFYERALY</html:option>
		      <html:option value="Y">YEARLY</html:option>
          </html:select></td>
		    
		   </logic:equal>
		   </logic:iterate>
		   </logic:present> 
		    </tr>
		   
		    <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" >  
		     <tr>
		  
		  <td id="insComFre1"><bean:message key="lbl.interestCompoundingFrequency" />
		    <td id="insComFre2"><html:select property="interestCompoundingFrequency"  disabled="true" styleClass="text" styleId="interestCompoundingFrequency" value="${requestScope.allDetail[0].interestCompoundingFrequency}" onchange="validateIntCompoundingFrequency();">
             
	            <html:option value="">NOT APPLICABLE</html:option>
				  <html:option value="M">MONTHLY</html:option>
			      <html:option value="B">BIMONTHLY</html:option>
			      <html:option value="Q">QUARTERLY</html:option>
			      <html:option value="H">HALFYERALY</html:option>
			      <html:option value="Y">YEARLY</html:option>
			      <%--  <logic:present name="intComFreq">
		               <html:optionsCollection name="intComFreq" label="name" value="id" /> 
		       </logic:present> --%>
         	  </html:select>
            </td>
		  
		    <td id="insCalMet1"><bean:message key="lbl.interestCalculationMethod" /><font color="red">*</font></td>
		    <td id="insCalMet2"><html:select property="interestCalculationMethod" disabled="true" styleClass="text" styleId="interestCalculationMethod" value="${requestScope.allDetail[0].interestCalculationMethod}" >
           
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
				    <td id="insFre2"><html:select property="interestFrequency" styleClass="text" styleId="interestFrequency" disabled="true" value="${requestScope.allDetail[0].interestFrequency}" onchange="setInterestDueDate();validateInterestFrequency();" >
		             
			              <html:option value="M">MONTHLY</html:option>
					      <html:option value="B">BIMONTHLY</html:option>
					      <html:option value="Q">QUARTERLY</html:option>
					      <html:option value="H">HALFYERALY</html:option>
					      <html:option value="Y">YEARLY</html:option>
					<%--        <html:option value="X">FINANCIAL QUARTERLY</html:option>
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
		     <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" > 
			    <td><bean:message key="lbl.noOfInstall" /></td>
			    <td><html:text property="noOfInstall" disabled="true" styleClass="text" onkeypress="return isNumberKey(event);"  maxlength="5" style="text-align: right" value="${requestScope.allDetail[0].noOfInstall}" readonly="true" styleId="noOfInstall" /></td>
			    </logic:equal>
			    </logic:iterate>
			    </logic:present>
			   <td><bean:message key="lbl.creditPeriod" /><font color="red">*</font></td>
           <td ><html:text property="creditPeriod" styleClass="text" maxlength="5"  value="${requestScope.allDetail[0].creditPeriod}" styleId="creditPeriod" onkeypress="return isNumberKey(event);" /></td>
          
		    </tr>
             <tr>
           <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" > 
		    <td><bean:message key="lbl.repaymentMode" /><font color="red">*</font></td>
             <td><html:select property="paymentMode" styleClass="text" disabled="true" styleId="paymentMode" value="${requestScope.allDetail[0].paymentMode}">
	              <html:option value="">--Select--</html:option>
   					    <logic:present name="paymentModes">
					<logic:notEmpty name="paymentModes">
					<html:optionsCollection name="paymentModes" label="paymentMode" value="paymentModeId" />
					</logic:notEmpty>		
				</logic:present>
	          </html:select></td>
            
             <td><bean:message key="lbl.noIns" /></td>
		          <logic:present name="allDetail">
			    	  <td><html:text styleClass="text" property="installments" disabled="true" styleId="installments" value="${requestScope.allDetail[0].installments }" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
			    </logic:present>
			       <logic:notPresent name="allDetail">
			    	  <td><html:text styleClass="text" property="installments" disabled="true" styleId="installments" value="0" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
			    </logic:notPresent>
		    </logic:equal>
		    </logic:iterate>
		    </logic:present>
		    </tr>
		      <tr>
		      <%-- <html:hidden property="maturityDate" styleId="maturityDate1" value="${requestScope.allDetail[0].maturityDate}" /> --%>
              <td id="effDateHeader"><bean:message key="lbl.effDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		      <td id="effDate"><html:text styleClass="text" property="repayEffectiveDate" styleId="repayEffectiveDate1" disabled="true" value="${requestScope.allDetail[0].repayEffectiveDate }" maxlength="10" onchange="checkDate('repayEffectiveDate');validRepayDateInDeal();calculateMaturityDateInDeal();checkNewRepay();getDueDayNextDueDate();setInterestDueDate();" /></td>
		   <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              <logic:equal name="suballDetail" property="repaymentType" value="I" >   
		    <td><bean:message key="lbl.cycleDate" /><font color="red">*</font></td>
		    <td><html:select property="cycleDate" styleClass="text" styleId="cycleDate" value="${requestScope.allDetail[0].cycleDate}" disabled="true" onchange="nullNextDue();getDueDay();">
              <html:option value="">---Select----</html:option>
              <logic:present name="cycle">
	               <html:optionsCollection name="cycle" label="name" value="id" /> 
	             </logic:present>
		   </html:select>
		    
		  	    
		    </td>
		    </logic:equal>
		    </logic:iterate>
		    </logic:present>
		    </tr><tr>
		    <td id="nddheader"><bean:message key="lbl.nextDueDate" /><font color="red">*</font></td>
		    <td id="ndd">
		         <html:text property="nextDueDate" disabled="true"  styleClass="text"  value="${requestScope.allDetail[0].nextDueDate}" styleId="nextDueDate1" onchange="checkDueDate(value);calTenureMonthForMaturityDateNextDueDate();" />
		    </td>
			 	<td id="interestDueDateHeader1"> <bean:message key="lbl.interestDueDate"/><font color="red">*</font></td>
              		<td id = "interestDueDateHeader2">
						<logic:notPresent name="allDetail">
							<html:text styleClass="text" property="interestDueDate" styleId="interestDueDate"  onchange="datevalidate();validateInterestDueDate();"/>
							
						</logic:notPresent>
						<logic:present name="allDetail">
							<html:text styleClass="text"  property="interestDueDate" value="${requestScope.allDetail[0].interestDueDate}"  styleId="interestDueDate" onchange="datevalidate();validateInterestDueDate();" />
							
						</logic:present>
					</td>
			</tr>
			         				  <!-- Code Added for the Handling separate Interest first due date and maturity date| Rahul papneja| 31012018 -->
				   <tr id="SeparateInterestDate" style="display:none">
				  <!-- Start|01 | Code Added for firstInterestDueDate Handling till <td> tag closed -->
				  <td id="firstInterestDueDateLBL" style="display:none"><bean:message key="lbl.firstInterestDueDate"/></td>
				  <td id = "firstIneterestDueDateTXT" style="display:none">
						<logic:notPresent name="allDetail">
							<html:text styleClass="text" property="firstInterestDueDate" styleId="firstInterestDueDate" readonly="true"  onchange="datevalidate();" />
							
						</logic:notPresent>
						<logic:present name="allDetail">
						
							<html:text styleClass="text" property="firstInterestDueDate" value="${requestScope.allDetail[0].firstInterestDueDate}" disabled="true"  styleId="firstInterestDueDate" readonly="true"  onchange="datevalidate();" />
						
							
						</logic:present>
					</td>
					<!-- 01| End -->

				    <!-- Start|02-3 | Code Added for maturityDate Handling till <td> tag closed -->
				  <td id="maturityDateLBL"><bean:message key="lbl.maturityDate"/><font color="red">*</font></td>
				  <td id = "maturityDateTXT">
						<logic:notPresent name="allDetail">
							<html:text styleClass="text" property="maturityDate" styleId="maturityDate1" readonly="true"  onchange="calTenureMonthForMaturityDate();validateMaturityDate();" onblur="setMaturityTemp();"/>
							
						</logic:notPresent>
						<logic:present name="allDetail">
						
							<html:text styleClass="text" property="maturityDate" value="${requestScope.allDetail[0].maturityDate}" disabled="true"  styleId="maturityDate1" readonly="true"  onchange="calTenureMonthForMaturityDate();validateMaturityDate();" onblur="setMaturityTemp();"/>
						
							
						</logic:present>
					</td>
					<!-- 02-3 | End -->
				  
				  
				  
				  
				  </tr>
				  <!-- Ends Here| Rahul papneja -->
			<tr>
		    <td><bean:message key="lbl.purpose" /><font color="red">*</font></td>
		    <td >
		         <a rel="tooltip" id="${requestScope.allDetail[0].loanPurpose}"><html:text property="loanPurpose" disabled="true"  styleClass="text"  value="${requestScope.allDetail[0].loanPurpose}" styleId="loanPurpose" onchange="return upperMe('loanPurpose');"  /></a>
		    </td>
		    <!-- code Added for Edit Due Date| Rahul papneja |30012018 -->
		        
 <!--       Vishal changes start -->
			<td>
			<bean:message key="lbl.editDueDate"/><font color="red">*</font></td>
			<td>
			<html:select property="editDueDate" styleId="editDueDate" styleClass="text" disabled="true" value="${allDetail[0].editDueDate}">
			<html:option value="N">NO</html:option>
			<html:option value="Y">Yes</html:option>
			<!-- <html:option value="N">NO</html:option> -->
			</html:select>
			</td>
<!-- 	   Vishal changes end -->         	
          		
		    <!-- Ends Here -->
		    </tr>
		    <tr>
		    	
		       <td><bean:message key="lbl.sectorType" /><font color="red">*</font></td>
		    	 <td >
	            <html:select property="sectorType" styleClass="text" disabled="true" styleId="sectorType" value="${requestScope.allDetail[0].sectorType}">
	             <html:option value="">---Select----</html:option>
	             <logic:present name="sector">
	               <html:optionsCollection name="sector" label="name" value="id" /> 
	             </logic:present>
	          </html:select>
	         </td>
	          
	         
	           <logic:present name="allDetail">
              	<logic:iterate id="suballDetail" name="allDetail" length="1">
              	<logic:equal name="suballDetail" property="repaymentType" value="I" >
              		<logic:equal name="suballDetail" property="assetFlag" value="A">
				        <td><bean:message key="lbl.LTV" /></td>
					    <td>
					         	<html:text property="ltvPerc" styleClass="text"  value="${requestScope.allDetail[0].ltvPerc}" styleId="ltvPerc"  readonly="true" />
					    </td>
					   </logic:equal>
				    </logic:equal>
	         </logic:iterate>
	         </logic:present>
	         
	         
		    </tr>
		    <tr >
		    <logic:present name="allDetail">
              	<logic:iterate id="suballDetail" name="allDetail" length="1">
              	<logic:equal name="suballDetail" property="repaymentType" value="N" >
		        <td ><bean:message key="lbl.interestCalc" /><font color="red">*</font></td>
		    	 <td  >
	            <html:select property="interestCalc" styleClass="text" styleId="interestCalc" value="${requestScope.allDetail[0].interestCalc}" disabled="true" >
	             <html:option value="">---Select----</html:option>
	             <logic:present name="interestFrom">
	               <html:optionsCollection name="interestFrom" label="name" value="id" /> 
	             </logic:present>
	          </html:select>
	         </td>
	         
		    </logic:equal>
	         </logic:iterate>
	         </logic:present>	</tr>	        
		 <tr>
			<logic:present name="allDetail">
			<logic:iterate id="suballDetail" name="allDetail" length="1">
             <logic:equal name="suballDetail" property="repaymentType" value="I" >
             <logic:equal name="suballDetail" property="assetFlag" value="A">
              	
			<td><bean:message key="lbl.netLtv" /></td>
			<td>
			<html:text property="netLtv" styleClass="text"  value="${requestScope.allDetail[0].netLtv}" styleId="netLtv"  readonly="true" />
			</td>
			 <td><bean:message key="lbl.totalVatAmt" /></td>
		   <td>
		   <html:text property="totalVatAmt" styleClass="text"  value="${requestScope.allDetail[0].totalVatAmt}" styleId="totalVatAmt"  readonly="true" />
		   </td> 
			</logic:equal>
			</logic:equal>
			</logic:iterate>		         
	        </logic:present>
	        </tr>

		 <tr>

			<td><bean:message key="lbl.loanClassification" /></td>
			<td>
			
			<html:hidden  property="lbxLoanClassification" styleId="lbxLoanClassification" value="${requestScope.allDetail[0].lbxLoanClassification}" />
			<html:select property="loanClassification" styleId="loanClassification" size="5" disabled="true" multiple="multiple" style="width: 120" >
   			<logic:present name="loanClassificationList">
   			<logic:notEmpty name="loanClassificationList">
       			 <html:optionsCollection name="loanClassificationList" value="loanClassificationId" label="loanClassificationLabel"/>
       		</logic:notEmpty>
       		</logic:present>
     		</html:select>
     		
     		
     		<td><bean:message key="lbl.businessType" /><font color="red">*</font></td>
			 <td >
		  <html:select property="businessType" styleClass="text" styleId="businessType" value="${requestScope.allDetail[0].businessType}"  onchange="return getbusinessbutton();">
			 
	             	<html:option value="">---Select---</html:option>
	             		<logic:present name="getBusiness">
	               			<html:optionsCollection name="getBusiness" label="businessdesc" value="businessId" /> 
	             		</logic:present>
	          	</html:select>
	          	 <input type="hidden" name="oldPartnerType" id="oldPartnerType" value="${requestScope.allDetail[0].businessType}" /> 
         	</td>
         	      		
	</td>
	
		</tr>
		<tr>
			 <td  id="gblock" style="display: none;"><bean:message key="lbl.grossBlock" /></td>
          	<td  id="gblock1" style="display: none;"><html:text property="grossBlock" styleClass="text" maxlength="10" style="text-align: right" value="${requestScope.allDetail[0].grossBlock}" styleId="grossBlock"  disabled="true" onkeypress="return isNumberKey(event);" /></td>
			 
			 <td  id="nblock" style="display: none;"><bean:message key="lbl.netBlock" /></td>
          	 <td  id="nblock1" style="display: none;"><html:text property="netBlock" styleClass="text" maxlength="10" style="text-align: right" value="${requestScope.allDetail[0].netBlock}" styleId="netBlock"   disabled="true" onkeypress="return isNumberKey(event);" /></td>
          	 
			  </tr>
<tr>
		<td>
		 <button type="button" class="topformbutton4" id="Save" title="Alt+V" accesskey="V" onclick="openPopForSectorDetails('Y');"><bean:message key="button.sectorTypeDetails" /></button>
		</td>
				
		
		<td id ="partner_mode" style="display: none">        
		      <button type="button" class="topformbutton4" id="partner" title="Alt+P" accesskey="P" onclick="getbusinessType();"><bean:message key="lbl.partnerDetails" /></button>
		      </td>
		</tr>
        
		 </table></td></tr>
    </table>
    </fieldset>
<fieldset>	
		 <legend><bean:message key="lbl.loanDetail" /></legend>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
         <tr>
         <td class="gridtd">
    	 <table width="100%" border="0" cellspacing="1" cellpadding="1">
            <tr class="white2">
             <td width="3%">
	         <input type="checkbox" name="allchk" id="allchk" disabled="disabled" value="checkbox" onclick="return allChecked();"/>
	         </td> 
             <td ><strong><bean:message key="lbl.product" /></strong></td>
             <td ><strong><bean:message key="lbl.scheme" /></strong></td>
             <td ><b><bean:message key="lbl.am" /></b></td>
             <td><strong><bean:message key="lbl.tenure" /></strong></td>
             <td ><b><bean:message key="lbl.rateType" /></b></td>
             <td ><strong><bean:message key="lbl.rateMethod" /></strong></td>
             <td ><strong><bean:message key="lbl.purpose" /></strong></td>
            </tr>
          <logic:present name="loanList">
           <logic:iterate name="loanList" id="subloanList">
		       <tr class="white1">
		         
	             <td ><input type="checkbox" disabled="disabled" name="chk" id="chk" value="<bean:write name="subloanList" property="dealLoanId" />" /></td>	 
	             <td ><a href="#" onclick="modifyDetail(<bean:write name="subloanList" property="dealLoanId" />)"><bean:write name="subloanList" property="product" /></a></td>
	             <td ><bean:write name="subloanList" property="scheme" /></td>
	             <td ><bean:write name="subloanList" property="requestedLoanAmount" /></td>
	             <td ><bean:write name="subloanList" property="requestedLoanTenure" /></td>
	             <td ><bean:write name="subloanList" property="rateType" /></td>
	             <td ><bean:write name="subloanList" property="type" /></td>
	             <td ><a rel="tooltip" id="${subloanList.loanPurpose}"><bean:write name="subloanList" property="loanPurpose" /></a></td>
	           </tr>
           </logic:iterate>
            </logic:present>
            
         </table>     
        </td>
        </tr>
      </table>
      
  <table width="100%" border="0" cellpadding="2" cellspacing="1">
    <tr>
      <td colspan="4">
   </td>
    </tr>
  </table>

</fieldset>
</html:form>
</logic:present>
<logic:present name="insertSuccess">

 <script type="text/javascript">
	if("<%=request.getAttribute("insertSuccess").toString()%>"=="S")
	{
		hideField();
		alert("<bean:message key="lbl.loanSuccess" />");
	}
	else
	{
	
		alert("<bean:message key="lbl.loanError" />");
	}	
</script>
</logic:present>
<logic:present name="deletedRecord">

 <script type="text/javascript">
	if('<%=request.getAttribute("deletedRecord").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.loanDeleteSuccess" />');
	}
	else
	{
		alert('<bean:message key="lbl.loanDeleteError" />');
	}	
</script>
</logic:present>

<logic:present name="nonProduct">

	<script type="text/javascript">
	
			alert("<bean:message key="lbl.NotInstallProduct" />");
	
		
</script>
</logic:present>


</div>
</div>
<script>
	setFramevalues("LoanDetailsForm");
</script>

</body></html:html>