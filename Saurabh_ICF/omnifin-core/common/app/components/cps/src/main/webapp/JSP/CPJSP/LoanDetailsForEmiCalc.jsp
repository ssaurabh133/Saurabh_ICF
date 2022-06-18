<%--
      Author Name-      Anil Yadav
      Date of creation -17/11/2012
      Purpose-          EMI Caculator
      Documentation-     
      
 --%>

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
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessingForEmiCalc.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
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
<body oncontextmenu="return false;" onload="enableAnchor();netLtvView();removeDueDate();enableInstallNo();checkChanges('LoanDetailsForm');" onunload="closeAllLovCallUnloadBody();">

<div  align="center" class="opacity" style="display:none" id="processingImage" ></div>

<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>

<input type="hidden" id="checkNewRepay" name="checkNewRepay" value="<%=path %>"/>

<input type="hidden" id="bDate" name="bDate" value="${userobject.businessdate }"/>
			
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

<html:form action="/loanProcessAction" method="post" styleId="LoanDetailsForm">

	<input type="hidden" id="minFinance" name="minFinance" value="${requestScope.allDetail[0].minFinanceAmount}"/>
	<input type="hidden" id="maxFinance" name="maxFinance" value="${requestScope.allDetail[0].maxFinanceAmount}" />
	<input type="hidden" id="minMarginRate" name="minMarginRate" value="${requestScope.allDetail[0].minMRate}"/>
	<input type="hidden" id="maxMarginRate" name="maxMarginRate" value="${requestScope.allDetail[0].maxMRate}" />
	<input type="hidden" id="minTenure" name="minTenure" value="${requestScope.allDetail[0].minTenure}"/>
	<input type="hidden" id="maxTenure" name="maxTenure" value="${requestScope.allDetail[0].maxTenure}"/>
	<html:hidden styleClass="text" property="repaymentType" styleId="repaymentType" value="${requestScope.allDetail[0].repaymentType }"   />
	<html:hidden styleClass="text" property="assetFlag" styleId="assetFlag" value="${requestScope.allDetail[0].assetFlag }"   />
	

	<html:hidden styleClass="text" property="rateMethodType" styleId="rateMethodType" value="${requestScope.allDetail[0].rateMethodType }"   />
    
<fieldset>


<legend><bean:message key="lb.parameter" /></legend>

<logic:notPresent name="editLoanDetailsForEmiCalc">
<table cellspacing="0" cellpadding="0" width="100%" border="0">
<tr>
    <td>
      <table cellspacing="1" cellpadding="1" width="100%" border="0">
        <tr><html:hidden property="productType" styleId="productType"  />
        
        <html:hidden property="productTypeFlag" styleId="productTypeFlag" value="${requestScope.allDetail[0].productTypeFlag}" />
        
        <html:hidden property="dealLoanId" value="${requestScope.allDetail[0].dealLoanId}" />
          <td><bean:message key="lbl.product" /><font color="red">*</font></td>
           <td >
           
           <html:text  property="product" styleId="product" styleClass="text"  value="${requestScope.allDetail[0].product}" tabindex="-1"/>
          <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${requestScope.allDetail[0].lbxProductID}"/>
          <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(71,'LoanDetailsForm','product','','scheme','','','','productId','productType')" value=" " styleClass="lovbutton"> </html:button>
          <input type="hidden" id="productId" name="productId"/>
        </td>
          <td><bean:message key="lbl.scheme" /><font color="red">*</font></td>
          <td>
          <html:text property="scheme" styleId="scheme" styleClass="text" value="${requestScope.allDetail[0].scheme}" tabindex="-1"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme" value="${requestScope.allDetail[0].lbxscheme}" />
          		<html:button property="schemeButton" styleId="schemeButton" onclick="closeLovCallonLov('product');openLOVCommon(22,'LoanDetailsForm','scheme','product','lbxscheme', 'lbxProductID','Select Product LOV','getDefaultLoanDetailForEmiCal','schemeId')" value=" " styleClass="lovbutton"></html:button>  								    
                <input type="hidden" id="schemeId" name="schemeId"/>
                </td> 
         </tr>
         
        <tr>
        <logic:present name="allDetail">
			<logic:iterate id="suballDetail" name="allDetail" length="1">
			
				<logic:equal name="suballDetail" property="repaymentType" value="I" >
					<logic:equal name="suballDetail" property="assetFlag" value="A">
					        <td id="assCost1"><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
							<td id="assCost2"><html:text property="assetCost"  style="text-align: right" styleClass="text" value="${requestScope.allDetail[0].assetCost}" styleId="assetCost" onkeypress="return fourDecimalnumbersonly(event, id, 18)" onblur="fourDecimalNumber(this.value, id);validateAssetCost();assetRangeAmount();" onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);" onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"/></td>
					</logic:equal>
					<logic:equal name="suballDetail" property="assetFlag" value="N">
					        <td id="assCost1" style="display: none;"><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
							<td id="assCost2" style="display: none;"><html:text property="assetCost"  style="text-align: right" styleClass="text" value="${requestScope.allDetail[0].assetCost}" styleId="assetCost" onkeypress="return fourDecimalnumbersonly(event, id, 18)" onblur="fourDecimalNumber(this.value, id);validateAssetCost();assetRangeAmount();" onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);" onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"/></td>
					</logic:equal>
				</logic:equal>
				
				<logic:equal name="suballDetail" property="repaymentType" value="N" >
					<logic:equal name="suballDetail" property="assetFlag" value="N">
					        <td id="assCost1" style="display: none;"><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
							<td id="assCost2" style="display: none;"><html:text property="assetCost"  style="text-align: right" styleClass="text" value="${requestScope.allDetail[0].assetCost}" styleId="assetCost" onkeypress="return fourDecimalnumbersonly(event, id, 18)" onblur="fourDecimalNumber(this.value, id);validateAssetCost();assetRangeAmount();" onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);" onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"/></td>
					</logic:equal>
				</logic:equal>
				
             </logic:iterate>
         </logic:present> 
        
         <logic:notPresent name="allDetail"> 
         
         	<td id="assCost1" ><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
        	<td id="assCost2"><html:text property="assetCost" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].assetCost}" styleId="assetCost"  onkeypress="return fourDecimalnumbersonly(event, id, 18)" onblur="fourDecimalNumber(this.value, id);validateAssetCost();assetRangeAmount();" onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);" onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"/></td>
          </logic:notPresent>
           
           
          
         </tr>
         <tr>
          <html:hidden property="daysBasis" styleId="daysBasis" value="${requestScope.allDetail[0].daysBasis }"/>  
           
           <td><bean:message key="lbl.tenure" /><font color="red">*</font></td>
           <td ><html:text property="requestedLoanTenure" styleClass="text" maxlength="5" style="text-align: right" value="${requestScope.allDetail[0].requestedLoanTenure}" styleId="requestedLoanTenure" onkeypress="return isNumberKey(event);" onchange="calcDay(); calculateInstallment();"/></td>
        <logic:present name="allDetail">

			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${requestScope.allDetail[0].tenureInDay}" styleId="tenureInDay" readonly="true"  />
			</td>	
       
	        </logic:present>
	        <logic:notPresent name="allDetail">

			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${requestScope.allDetail[0].tenureInDay}" styleId="tenureInDay" readonly="true" />
			</td>	
	         
	        </logic:notPresent>
        
         </tr>
        <tr>
			<td><bean:message key="lbl.am" /><font color="red">*</font></td>
           <td ><html:text property="requestedLoanAmount" styleClass="text" maxlength="22" style="text-align: right" value="${requestScope.allDetail[0].requestedLoanAmount}" styleId="requestedLoanAmount"  onkeypress="return fourDecimalnumbersonly(event, id, 18)" onblur="fourDecimalNumber(this.value, id);rangeAmtMar();validateReqLAmt();" onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);" onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"  /></td>
                  
        </tr>
        <tr>
          <logic:present name="allDetail">
              	<logic:iterate id="suballDetail" name="allDetail" length="1">
              	
              	  <logic:equal name="suballDetail" property="repaymentType" value="I" >
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="A">
         	  	 		<td id="marPer1"><bean:message key="lbl.marginPercentage" /><font color="red">*</font></td>
         	  	 		<td id="marPer2"><html:text property="marginPerc" style="text-align: right" styleClass="text"  value="${requestScope.allDetail[0].marginPerc}" styleId="marginPerc" onkeypress="return numbersonly(event, id, 2);"  onchange="checkRate('marginPerc');calMarginAmount();"  /></td>
         	  	 	</logic:equal>
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="N">
         	  	 		<td id="marPer1" style="display: none;"><bean:message key="lbl.marginPercentage" /><font color="red">*</font></td>
         	  	 		<td id="marPer2" style="display: none;"><html:text property="marginPerc" style="text-align: right" styleClass="text"  value="${requestScope.allDetail[0].marginPerc}" styleId="marginPerc" onkeypress="return numbersonly(event, id, 2);"  onchange="checkRate('marginPerc');calMarginAmount();"  /></td>
         	  	 	</logic:equal>
           		</logic:equal>
           		
           		<logic:equal name="suballDetail" property="repaymentType" value="N" >
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="N">
         	  	 		<td id="marPer1" style="display: none;"><bean:message key="lbl.marginPercentage" /><font color="red">*</font></td>
         	  	 		<td id="marPer2" style="display: none;"><html:text property="marginPerc" style="text-align: right" styleClass="text"  value="${requestScope.allDetail[0].marginPerc}" styleId="marginPerc" onkeypress="return numbersonly(event, id, 2);"  onchange="checkRate('marginPerc');calMarginAmount();"  /></td>
         	  	 	</logic:equal>
           		</logic:equal>
           		
         	   </logic:iterate>
         	 </logic:present> 
         	 
         	 <logic:notPresent name="allDetail"> 
         	 	<td id="marPer1"><bean:message key="lbl.marginPercentage" /><font color="red">*</font></td>
         	 	<td id="marPer2"><html:text property="marginPerc" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].marginPerc}" styleId="marginPerc" onkeypress="return numbersonly(event, id, 2);"   onchange="checkRate('marginPerc');calMarginAmount();" /></td>	
         	 </logic:notPresent>
          
               <logic:present name="allDetail">
              	<logic:iterate id="suballDetail" name="allDetail" length="1">
              	
              	<logic:equal name="suballDetail" property="repaymentType" value="I" >
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="A">
         	  	 		<td id="marAmount1" ><bean:message key="lbl.marginAmount" /><font color="red">*</font></td>
         	  	 		<td id="marAmount2" ><html:text property="marginAmount" styleClass="text" readonly="true" tabindex="-1" style="text-align: right" value="${requestScope.allDetail[0].marginAmount}" styleId="marginAmount"   /></td>
         	  	 	</logic:equal>
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="N">
         	  	 		<td id="marAmount1" style="display: none;"><bean:message key="lbl.marginAmount" /><font color="red">*</font></td>
         	  	 		<td id="marAmount2" style="display: none;"><html:text property="marginAmount" styleClass="text" readonly="true" tabindex="-1" style="text-align: right" value="${requestScope.allDetail[0].marginAmount}" styleId="marginAmount"   /></td>
         	  	 	</logic:equal>
         	  	 </logic:equal>
         	  	 
              	 <logic:equal name="suballDetail" property="repaymentType" value="N" >
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="N">
         	  	 		<td id="marAmount1" style="display: none;"><bean:message key="lbl.marginAmount" /><font color="red">*</font></td>
         	  	 		<td id="marAmount2" style="display: none;"><html:text property="marginAmount" styleClass="text" readonly="true" tabindex="-1" style="text-align: right" value="${requestScope.allDetail[0].marginAmount}" styleId="marginAmount"   /></td>
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
          
              <html:select property="rateType" styleClass="text" styleId="rateType" value="${requestScope.allDetail[0].rateType}" onchange="return getFinalRateForEmiCalc();">
              <html:option value="">---Select----</html:option>
              <html:option value="F">Flat Rate</html:option>
              <html:option value="E">Effective Rate</html:option>
              </html:select>
          
          </td>

		    <td><bean:message key="lbl.finalRate" /></td>

		    <td><html:text property="effectiveRate" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].effectiveRate}"  styleId="effectiveRate" onkeypress="return numbersonly(event, id, 3)" onblur="fourDecimalNumber(this.value, id);checkRate('effectiveRate');" onkeyup="checkNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 3, id);" tabindex="-1"/></td>

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
	        <td id="installType2"><html:select property="installmentType" styleId="installmentType" styleClass="text" value="${requestScope.allDetail[0].installmentType}">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="installmentTypeList">
				  <logic:notEmpty name="installmentTypeList">
				  <html:optionsCollection name="installmentTypeList" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>
				  </td>
          </logic:equal>
          
              <logic:equal name="suballDetail" property="repaymentType" value="N" >
<!--              <td id="installType1" style="display: none;"><bean:message key="lbl.installmentType" /><font color="red">*</font></td>-->
<!--		    <td id="installType2" style="display: none;"><html:select property="installmentType" styleClass="text" styleId="installmentType" value="${requestScope.allDetail[0].installmentType}">-->
<!--            <html:option value="">---Select----</html:option>-->
<!--             <html:option value="E">Eq. INSTALLMENT</html:option>-->
<!--		     <html:option value="G">Gr. INSTALLMENT</html:option>-->
<!--		     <html:option value="P">Eq. PRINCIPAL</html:option>-->
<!--		     <html:option value="Q">Gr. PRINCIPAL1</html:option>-->
<!--		     <html:option value="R">Gr. PRINCIPAL2</html:option>-->
<!--          </html:select></td>-->
			<td id="installType1" style="display: none;"><bean:message key="lbl.installmentType" /><font color="red">*</font></td>
			<td id="installType2" style="display: none;">
	              <html:select property="installmentType" styleId="installmentType" styleClass="text" value="${requestScope.allDetail[0].installmentType}">
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
	              <html:select property="installmentType" styleId="installmentType" styleClass="text" value="${requestScope.allDetail[0].installmentType}">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="installmentTypeList">
				  <logic:notEmpty name="installmentTypeList">
				  <html:optionsCollection name="installmentTypeList" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>
				  </td>
             </logic:notPresent>
          
           </tr>
           
		    
		    <tr>
		    <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
             
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
		    <td id="insMod1"><bean:message key="lbl.insMode" /><font color="red">*</font></td>
		    <td id="insMod2"><html:select property="instMode" styleClass="text" styleId="instMode" value="${requestScope.allDetail[0].instMode}">
                    <html:option value="">---Select----</html:option>
                    <html:option value="A">ADVANCE</html:option>
		            <html:option value="R">ARREAR</html:option>
                </html:select>
            </td>
		  
		    <td id="frequnc1"><bean:message key="lbl.frequency" /><font color="red">*</font></td>
		    <td id="frequnc2"><html:select property="frequency" styleClass="text" styleId="frequency" value="${requestScope.allDetail[0].frequency}" onchange="calculateInstallment();">
            <html:option value="">---Select----</html:option>
              <html:option value="M">MONTHLY</html:option>
		      <html:option value="B">BIMONTHLY</html:option>
		      <html:option value="Q">QUARTERLY</html:option>
		      <html:option value="H">HALFYERALY</html:option>
		      <html:option value="Y">YEARLY</html:option>
          </html:select></td>
		   </logic:equal>
		   
		    <logic:equal name="suballDetail" property="repaymentType" value="N" >
		    <td id="insMod1" style="display: none;"><bean:message key="lbl.insMode" /><font color="red">*</font></td>
		    <td id="insMod2" style="display: none;"><html:select property="instMode" styleClass="text" styleId="instMode" value="${requestScope.allDetail[0].instMode}">
                    <html:option value="">---Select----</html:option>
                    <html:option value="A">ADVANCE</html:option>
		            <html:option value="R">ARREAR</html:option>
                </html:select>
            </td>
		  
		    <td id="frequnc1" style="display: none;"><bean:message key="lbl.frequency" /><font color="red">*</font></td>
		    <td id="frequnc2" style="display: none;"><html:select property="frequency" styleClass="text" styleId="frequency" value="${requestScope.allDetail[0].frequency}" onchange="calculateInstallment();">
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
		    
		     <tr>
		     
		     <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
             
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
			    <td id="noOfIns1"><bean:message key="lbl.noOfInstall" /></td>
			    <td id="noOfIns2"><html:text property="noOfInstall" tabindex="-1" onkeypress="return isNumberKey(event);"  maxlength="5" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].noOfInstall}" readonly="true" styleId="noOfInstall"  /></td>
              
              <td id="noIns1" ><bean:message key="lbl.noIns" /></td>
		      <td id="noIns2" ><html:text styleClass="text" property="installments" styleId="installments" value="${requestScope.allDetail[0].installments }" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
              
              </logic:equal>
              
               <logic:equal name="suballDetail" property="repaymentType" value="N" >
			    <td id="noOfIns1" style="display: none;"><bean:message key="lbl.noOfInstall" /></td>
			    <td id="noOfIns2" style="display: none;"><html:text property="noOfInstall" tabindex="-1" onkeypress="return isNumberKey(event);"  maxlength="5" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].noOfInstall}" readonly="true" styleId="noOfInstall"  /></td>
              
              <td id="noIns1" style="display: none;"><bean:message key="lbl.noIns" /></td>
		      <td id="noIns2" style="display: none;"><html:text styleClass="text" property="installments" styleId="installments" value="${requestScope.allDetail[0].installments }" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
              
              </logic:equal>
              
          
          </logic:iterate>
          </logic:present>
         
          <logic:notPresent name="allDetail">
		  <tr>
		  
		  <td id="insMod1"><bean:message key="lbl.insMode" /><font color="red">*</font></td>
		    <td id="insMod2"><html:select property="instMode" styleClass="text" styleId="instMode" value="${requestScope.allDetail[0].instMode}">
                    <html:option value="">---Select----</html:option>
                    <html:option value="A">ADVANCE</html:option>
		            <html:option value="R">ARREAR</html:option>
                </html:select>
            </td>
		  
		    <td id="frequnc1"><bean:message key="lbl.frequency" /><font color="red">*</font></td>
		    <td id="frequnc2"><html:select property="frequency" styleClass="text" styleId="frequency" value="${requestScope.allDetail[0].frequency}" onchange="calculateInstallment();">
            <html:option value="">---Select----</html:option>
              <html:option value="M">MONTHLY</html:option>
		      <html:option value="B">BIMONTHLY</html:option>
		      <html:option value="Q">QUARTERLY</html:option>
		      <html:option value="H">HALFYERALY</html:option>
		      <html:option value="Y">YEARLY</html:option>
          </html:select></td>
		   
		    </tr>
		  </logic:notPresent>
		    
		    
		    <logic:notPresent name="allDetail">
		     <tr>
		    
			    <td id="noOfIns1"><bean:message key="lbl.noOfInstall" /></td>
			    <td id="noOfIns2"><html:text property="noOfInstall" tabindex="-1" onkeypress="return isNumberKey(event);" maxlength="5"  styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].noOfInstall}" readonly="true" styleId="noOfInstall"  /></td>
			  
			   <td id="noIns1"><bean:message key="lbl.noIns" /></td>
			   <td id="noIns2"><html:text styleClass="text" property="installments" styleId="installments" value="0" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
			  
			</tr> 
          </logic:notPresent>
          
          
		     </tr>   
		     
		    <tr>
		    		<html:hidden property="maturityDate" styleId="maturityDate1" value="${requestScope.allDetail[0].maturityDate}" />
              		<td id="effDateHeader"><bean:message key="lbl.effDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		    		<td id="effDate"><html:text styleClass="text" property="repayEffectiveDate" styleId="repayEffectiveDate" value="${requestScope.allDetail[0].repayEffectiveDate }" maxlength="10" onchange="checkDate('repayEffectiveDate');validRepayDateForEmiCalc();calculateMaturityDateForEmiCalc();checkNewRepayForEmiCalc();getDueDayNextDueDateForEmiCalc();" /></td>
		    
		   
		    <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
             
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
		    <td id="cyclDate1"><bean:message key="lbl.cycleDate" /><font color="red">*</font></td>
		    <td id="cyclDate2">
		    <html:select property="cycleDate" styleClass="text" styleId="cycleDate" value="${requestScope.allDetail[0].cycleDate}" onchange="nullNextDue();getDueDayForEmiCalc();">
              <html:option value="">---Select----</html:option>
              <logic:present name="cycle">
	               <html:optionsCollection name="cycle" label="name" value="id" /> 
	             </logic:present>
		   </html:select>
		    </td>
		    </logic:equal>
		    
		    <logic:equal name="suballDetail" property="repaymentType" value="N" >
		    <td id="cyclDate1" style="display: none;"><bean:message key="lbl.cycleDate" /><font color="red">*</font></td>
		    <td id="cyclDate2" style="display: none;">
		    <html:select property="cycleDate" styleClass="text" styleId="cycleDate" value="${requestScope.allDetail[0].cycleDate}" onchange="nullNextDue();getDueDayForEmiCalc();">
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
		    
		    <html:select property="cycleDate" styleClass="text" styleId="cycleDate" value="${requestScope.allDetail[0].cycleDate}" onchange="nullNextDue();getDueDayForEmiCalc();">
              <html:option value="">---Select----</html:option>
              	<logic:present name="cycle">
	               <html:optionsCollection name="cycle" label="name" value="id" /> 
	             </logic:present>
		   </html:select>
		    </td>
		     </logic:notPresent>
		     
		    </tr>
		    
		      <tr>
		      		<td id="nddheader"><bean:message key="lbl.nextDueDate" /><font color="red">*</font></td>
		    		<td id="ndd">
		         			<html:text property="nextDueDate" styleClass="text"  value="${requestScope.allDetail[0].nextDueDate}" styleId="nextDueDate" onchange="checkDueDate(value);" />
		    		</td>
             
		    		<td><bean:message key="lbl.purpose" /></td>
		    		<td >
		    
		     			<a rel="tooltip3" id="tool">
		     			
		     			  <html:text property="loanPurpose" maxlength="50"  styleClass="text"  value="${requestScope.allDetail[0].loanPurpose}" styleId="loanPurpose" onchange="return upperMe('loanPurpose');" onmouseover="applyToolTip(id);"  />
		     			
		     			</a>
		     		
		     			
		  	 		</td>  
		    </tr>
		     <tr >
              <logic:present name="allDetail">
                  <logic:iterate id="suballDetail" name="allDetail" length="1">
                    <logic:equal name="suballDetail" property="repaymentType" value="N" >
		        <td id="int1"><bean:message key="lbl.interestCalc" /><font color="red">*</font></td>
		    	 <td id="int2">		    	
	            <html:select property="interestCalc" styleClass="text" styleId="interestCalc" value="${requestScope.allDetail[0].interestCalc}"  >
	             <html:option value="">---Select----</html:option>
	             <logic:present name="interestFrom">
	               <html:optionsCollection name="interestFrom" label="name" value="id" /> 
	             </logic:present>	            
	          </html:select>	          
	         </td>
	          </logic:equal>
	          
	          <logic:equal name="suballDetail" property="repaymentType" value="I" >
		        <td id="int1" style="display: none;"><bean:message key="lbl.interestCalc" /><font color="red">*</font></td>
		    	 <td id="int2" style="display: none;">		    	
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
	             <td id="int1" style="display: none;">
	             <bean:message key="lbl.interestCalc" /><font color="red">*</font></td>
	           <td id="int2" style="display: none;"> 
	           <html:select property="interestCalc" styleClass="text" styleId="interestCalc" value="${requestScope.allDetail[0].interestCalc}"  >
	             <html:option value="">---Select----</html:option>
	             <logic:present name="interestFrom">
	               <html:optionsCollection name="interestFrom" label="name" value="id" /> 
	             </logic:present>	            
	          </html:select>
	          </td>	
	             </logic:notPresent>
	         </tr>
	         <tr id="netLtvRow">
	         	<td><bean:message key="lbl.netLtv" /></td>
				<td><html:text property="netLtv" styleClass="text"  value="${requestScope.allDetail[0].netLtv}" styleId="netLtv"  readonly="true" /></td>
			</tr>
 
		 <tr>
			<td >
			 <button type="button" class="topformbutton2" id="Save" title="Alt+V" accesskey="V" onclick="saveLoanDetailForEmiCalc('LoanDetailsForm');"><bean:message key="button.save" /></button>
			 
			    </td>
		</tr>
       
		 </table> </td></tr>
    </table>

</logic:notPresent>

<logic:present name="editLoanDetailsForEmiCalc">
<table cellspacing="0" cellpadding="0" width="100%" border="0">
<tr>
    <td>
      <table cellspacing="1" cellpadding="1" width="100%" border="0">
        <tr><html:hidden property="productType" styleId="productType"  />
        
        <html:hidden property="productTypeFlag" styleId="productTypeFlag" value="${requestScope.allDetail[0].productTypeFlag}" />
        
        <html:hidden property="dealLoanId" value="${requestScope.allDetail[0].dealLoanId}" />
          <td><bean:message key="lbl.product" /><font color="red">*</font></td>
           <td >
           
           <html:text  property="product" styleId="product" styleClass="text"  value="${requestScope.allDetail[0].product}" tabindex="-1"/>
          <html:hidden  property="lbxProductID" styleId="lbxProductID" value="${requestScope.allDetail[0].lbxProductID}"/>
          <html:button property="productButton" styleId="productButton" onclick="closeLovCallonLov1();openLOVCommon(71,'LoanDetailsForm','product','','scheme','','','','productId','productType')" value=" " styleClass="lovbutton"> </html:button>
          <input type="hidden" id="productId" name="productId"/>
        </td>
          <td><bean:message key="lbl.scheme" /><font color="red">*</font></td>
          <td>
          <html:text property="scheme" styleId="scheme" styleClass="text" value="${requestScope.allDetail[0].scheme}" tabindex="-1"/>
          		<html:hidden  property="lbxscheme" styleId="lbxscheme" value="${requestScope.allDetail[0].lbxscheme}" />
          		<html:button property="schemeButton" styleId="schemeButton" onclick="closeLovCallonLov('product');openLOVCommon(22,'LoanDetailsForm','scheme','product','lbxscheme', 'lbxProductID','Select Product LOV','getDefaultLoanDetailForEmiCal','schemeId')" value=" " styleClass="lovbutton"></html:button>  								    
                <input type="hidden" id="schemeId" name="schemeId"/>
                </td> 
         </tr>
        <tr>
        <logic:present name="allDetail">
			<logic:iterate id="suballDetail" name="allDetail" length="1">
				
				<logic:equal name="suballDetail" property="repaymentType" value="I" >
					<logic:equal name="suballDetail" property="assetFlag" value="A">
					        <td id="assCost1"><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
							<td id="assCost2"><html:text property="assetCost"  style="text-align: right" styleClass="text" value="${requestScope.allDetail[0].assetCost}" styleId="assetCost" onkeypress="return fourDecimalnumbersonly(event, id, 18)" onblur="fourDecimalNumber(this.value, id);validateAssetCost();assetRangeAmount();" onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);" onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"/></td>
					</logic:equal>
					<logic:equal name="suballDetail" property="assetFlag" value="N">
					        <td id="assCost1" style="display: none;"><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
							<td id="assCost2" style="display: none;"><html:text property="assetCost"  style="text-align: right" styleClass="text" value="${requestScope.allDetail[0].assetCost}" styleId="assetCost" onkeypress="return fourDecimalnumbersonly(event, id, 18)" onblur="fourDecimalNumber(this.value, id);validateAssetCost();assetRangeAmount();" onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);" onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"/></td>
					</logic:equal>
				</logic:equal>
				
				<logic:equal name="suballDetail" property="repaymentType" value="N" >
					<logic:equal name="suballDetail" property="assetFlag" value="N">
					        <td id="assCost1" style="display: none;"><bean:message key="lbl.assetCost" /><font color="red">*</font></td>
							<td id="assCost2" style="display: none;"><html:text property="assetCost"  style="text-align: right" styleClass="text" value="${requestScope.allDetail[0].assetCost}" styleId="assetCost" onkeypress="return fourDecimalnumbersonly(event, id, 18)" onblur="fourDecimalNumber(this.value, id);validateAssetCost();assetRangeAmount();" onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);" onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"/></td>
					</logic:equal>
				</logic:equal>
				
             </logic:iterate>
         </logic:present> 
        
           
         </tr>
         <tr>
         <html:hidden property="daysBasis" styleId="daysBasis" value="${requestScope.allDetail[0].daysBasis }"/> 
           <td><bean:message key="lbl.tenure" /><font color="red">*</font></td>
           <td ><html:text property="requestedLoanTenure" styleClass="text" maxlength="5" style="text-align: right" value="${requestScope.allDetail[0].requestedLoanTenure}" styleId="requestedLoanTenure" onkeypress="return isNumberKey(event);" onchange="calcDay(); calculateInstallment();"/></td>
         
          <logic:present name="allDetail">

			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${requestScope.allDetail[0].tenureInDay}" styleId="tenureInDay" readonly="true"  />
			</td>	
       
	        </logic:present>
	        <logic:notPresent name="allDetail">

			<td><bean:message key="lbl.tenureInDay" /></td>
		
			<td>
			<html:text property="tenureInDay" styleClass="text"  value="${requestScope.allDetail[0].tenureInDay}" styleId="tenureInDay" readonly="true" />
			</td>	
	         
	        </logic:notPresent>
         
         </tr>
         
         
        <tr>
			<td><bean:message key="lbl.am" /><font color="red">*</font></td>
           <td ><html:text property="requestedLoanAmount" styleClass="text" maxlength="22" style="text-align: right" value="${requestScope.allDetail[0].requestedLoanAmount}" styleId="requestedLoanAmount"  onkeypress="return fourDecimalnumbersonly(event, id, 18)" onblur="fourDecimalNumber(this.value, id);rangeAmtMar();validateReqLAmt();" onkeyup="fourDecimalcheckNumber(this.value, event, 18, id);" onfocus="fourDecimalkeyUpNumber(this.value, event, 18, id);"  /></td>
                  
        </tr>
        <tr>
          <logic:present name="allDetail">
              	<logic:iterate id="suballDetail" name="allDetail" length="1">
              	  <logic:equal name="suballDetail" property="repaymentType" value="I" >
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="A">
         	  	 		<td id="marPer1"><bean:message key="lbl.marginPercentage" /><font color="red">*</font></td>
         	  	 		<td id="marPer2"><html:text property="marginPerc" style="text-align: right" styleClass="text"  value="${requestScope.allDetail[0].marginPerc}" styleId="marginPerc" onkeypress="return numbersonly(event, id, 2);"  onchange="checkRate('marginPerc');calMarginAmount();"  /></td>
         	  	 	</logic:equal>
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="N">
         	  	 		<td id="marPer1" style="display: none;"><bean:message key="lbl.marginPercentage" /><font color="red">*</font></td>
         	  	 		<td id="marPer2" style="display: none;"><html:text property="marginPerc" style="text-align: right" styleClass="text"  value="${requestScope.allDetail[0].marginPerc}" styleId="marginPerc" onkeypress="return numbersonly(event, id, 2);"  onchange="checkRate('marginPerc');calMarginAmount();"  /></td>
         	  	 	</logic:equal>
           		</logic:equal>
           		
           		<logic:equal name="suballDetail" property="repaymentType" value="N" >
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="N">
         	  	 		<td id="marPer1" style="display: none;"><bean:message key="lbl.marginPercentage" /><font color="red">*</font></td>
         	  	 		<td id="marPer2" style="display: none;"><html:text property="marginPerc" style="text-align: right" styleClass="text"  value="${requestScope.allDetail[0].marginPerc}" styleId="marginPerc" onkeypress="return numbersonly(event, id, 2);"  onchange="checkRate('marginPerc');calMarginAmount();"  /></td>
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
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="N">
         	  	 		<td id="marAmount1" style="display: none;"><bean:message key="lbl.marginAmount" /><font color="red">*</font></td>
         	  	 		<td id="marAmount2" style="display: none;"><html:text property="marginAmount" styleClass="text" readonly="true" tabindex="-1" style="text-align: right" value="${requestScope.allDetail[0].marginAmount}" styleId="marginAmount"   /></td>
         	  	 	</logic:equal>
         	  	 </logic:equal>
         	  	 
         	  	 <logic:equal name="suballDetail" property="repaymentType" value="N" >
         	  	 	<logic:equal name="suballDetail" property="assetFlag" value="N">
         	  	 		<td id="marAmount1" style="display: none;"><bean:message key="lbl.marginAmount" /><font color="red">*</font></td>
         	  	 		<td id="marAmount2" style="display: none;"><html:text property="marginAmount" styleClass="text" readonly="true" tabindex="-1" style="text-align: right" value="${requestScope.allDetail[0].marginAmount}" styleId="marginAmount"   /></td>
         	  	 	</logic:equal>
         	  	 </logic:equal>
         	  	 
         	    </logic:iterate>
         	 </logic:present> 
          
        </tr>
       
		  <tr>
          <td><bean:message key="lbl.rateType" /><font color="red">*</font></td>
          <td>
          
              <html:select property="rateType" styleClass="text" styleId="rateType" value="${requestScope.allDetail[0].rateType}" onchange="return getFinalRateForEmiCalc();">
              <html:option value="">---Select----</html:option>
              <html:option value="F">Flat Rate</html:option>
              <html:option value="E">Effective Rate</html:option>
              </html:select>
          
          </td>

		    <td><bean:message key="lbl.finalRate" /></td>

		    <td><html:text property="effectiveRate" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].effectiveRate}"  styleId="effectiveRate" onkeypress="return numbersonly(event, id, 3)" onblur="fourDecimalNumber(this.value, id);checkRate('effectiveRate');" onkeyup="checkNumber(this.value, event, 3, id);" onfocus="keyUpNumber(this.value, event, 3, id);" tabindex="-1"/></td>

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
<!--		      <td id="installType2"><html:select property="installmentType" styleClass="text" styleId="installmentType" value="${requestScope.allDetail[0].installmentType}">-->
<!--            <html:option value="">---Select----</html:option>-->
<!--             <html:option value="E">Eq. INSTALLMENT</html:option>-->
<!--		     <html:option value="G">Gr. INSTALLMENT</html:option>-->
<!--		     <html:option value="P">Eq. PRINCIPAL</html:option>-->
<!--		     <html:option value="Q">Gr. PRINCIPAL1</html:option>-->
<!--		     <html:option value="R">Gr. PRINCIPAL2</html:option>-->
<!--          </html:select></td>-->

			<td id="installType1"><bean:message key="lbl.installmentType" /><font color="red">*</font></td>
			<td id="installType2">
	              <html:select property="installmentType" styleId="installmentType" styleClass="text" value="${requestScope.allDetail[0].installmentType}">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="installmentTypeList">
				  <logic:notEmpty name="installmentTypeList">
				  <html:optionsCollection name="installmentTypeList" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>
				  </td>
          </logic:equal>
          
           <logic:equal name="suballDetail" property="repaymentType" value="N" >
<!--             <td id="installType1" style="display: none;"><bean:message key="lbl.installmentType" /><font color="red">*</font></td>-->
<!--		    <td id="installType2" style="display: none;"><html:select property="installmentType" styleClass="text" styleId="installmentType" value="${requestScope.allDetail[0].installmentType}">-->
<!--            <html:option value="">---Select----</html:option>-->
<!--             <html:option value="E">Eq. INSTALLMENT</html:option>-->
<!--		     <html:option value="G">Gr. INSTALLMENT</html:option>-->
<!--		     <html:option value="P">Eq. PRINCIPAL</html:option>-->
<!--		     <html:option value="Q">Gr. PRINCIPAL1</html:option>-->
<!--		     <html:option value="R">Gr. PRINCIPAL2</html:option>-->
<!--          </html:select></td>-->

			<td id="installType1" style="display: none;"><bean:message key="lbl.installmentType" /><font color="red">*</font></td>
			<td id="installType2" style="display: none;">
	              <html:select property="installmentType" styleId="installmentType" styleClass="text" value="${requestScope.allDetail[0].installmentType}">
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
		    <td id="insMod1"><bean:message key="lbl.insMode" /><font color="red">*</font></td>
		    <td id="insMod2"><html:select property="instMode" styleClass="text" styleId="instMode" value="${requestScope.allDetail[0].instMode}">
                    <html:option value="">---Select----</html:option>
                    <html:option value="A">ADVANCE</html:option>
		            <html:option value="R">ARREAR</html:option>
                </html:select>
            </td>
		  
		    <td id="frequnc1"><bean:message key="lbl.frequency" /><font color="red">*</font></td>
		    <td id="frequnc2"><html:select property="frequency" styleClass="text" styleId="frequency" value="${requestScope.allDetail[0].frequency}" onchange="calculateInstallment();">
            <html:option value="">---Select----</html:option>
              <html:option value="M">MONTHLY</html:option>
		      <html:option value="B">BIMONTHLY</html:option>
		      <html:option value="Q">QUARTERLY</html:option>
		      <html:option value="H">HALFYERALY</html:option>
		      <html:option value="Y">YEARLY</html:option>
          </html:select></td>
		   </logic:equal>
		   
		   <logic:equal name="suballDetail" property="repaymentType" value="N" >
		    <td id="insMod1" style="display: none;"><bean:message key="lbl.insMode" /><font color="red">*</font></td>
		    <td id="insMod2" style="display: none;"><html:select property="instMode" styleClass="text" styleId="instMode" value="${requestScope.allDetail[0].instMode}">
                    <html:option value="">---Select----</html:option>
                    <html:option value="A">ADVANCE</html:option>
		            <html:option value="R">ARREAR</html:option>
                </html:select>
            </td>
		  
		    <td id="frequnc1" style="display: none;"><bean:message key="lbl.frequency" /><font color="red">*</font></td>
		    <td id="frequnc2" style="display: none;"><html:select property="frequency" styleClass="text" styleId="frequency" value="${requestScope.allDetail[0].frequency}" onchange="calculateInstallment();">
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
		    
		     <tr>
		     
		     <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
             
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
			    <td id="noOfIns1"><bean:message key="lbl.noOfInstall" /></td>
			    <td id="noOfIns2"><html:text property="noOfInstall" tabindex="-1" onkeypress="return isNumberKey(event);"  maxlength="5" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].noOfInstall}" readonly="true" styleId="noOfInstall"  /></td>
             
               <td id="noIns1"><bean:message key="lbl.noIns" /></td>
			   <td id="noIns2"><html:text styleClass="text" property="installments" styleId="installments" value="${requestScope.allDetail[0].installments }" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
             </logic:equal>
             
              <logic:equal name="suballDetail" property="repaymentType" value="N" >
			    <td id="noOfIns1" style="display: none;"><bean:message key="lbl.noOfInstall" /></td>
			    <td id="noOfIns2" style="display: none;"><html:text property="noOfInstall" tabindex="-1" onkeypress="return isNumberKey(event);"  maxlength="5" styleClass="text" style="text-align: right" value="${requestScope.allDetail[0].noOfInstall}" readonly="true" styleId="noOfInstall"  /></td>
             
               <td id="noIns1" style="display: none;"><bean:message key="lbl.noIns" /></td>
			   <td id="noIns2" style="display: none;"><html:text styleClass="text" property="installments" styleId="installments" value="${requestScope.allDetail[0].installments }" maxlength="5" style="text-align: right" onchange="checkInstallmentsInDeal();" /></td>
             </logic:equal>
             
             
          
          </logic:iterate>
          </logic:present>
         
		     </tr>   
		     
		    <tr>
		    		<html:hidden property="maturityDate" styleId="maturityDate1" value="${requestScope.allDetail[0].maturityDate}" />
              		<td id="effDateHeader"><bean:message key="lbl.effDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		    		<td id="effDate"><html:text styleClass="text" property="repayEffectiveDate" styleId="repayEffectiveDate" value="${requestScope.allDetail[0].repayEffectiveDate }" maxlength="10" onchange="checkDate('repayEffectiveDate');validRepayDateForEmiCalc();calculateMaturityDateForEmiCalc();checkNewRepayForEmiCalc();getDueDayNextDueDateForEmiCalc();" /></td>
		    
		   
		    <logic:present name="allDetail">
             <logic:iterate id="suballDetail" name="allDetail" length="1">
              
              <logic:equal name="suballDetail" property="repaymentType" value="I" >
		    <td id="cyclDate1"><bean:message key="lbl.cycleDate" /><font color="red">*</font></td>
		    <td id="cyclDate2">
		    <html:select property="cycleDate" styleClass="text" styleId="cycleDate" value="${requestScope.allDetail[0].cycleDate}" onchange="nullNextDue();getDueDayForEmiCalc();">
              <html:option value="">---Select----</html:option>
              <logic:present name="cycle">
	               <html:optionsCollection name="cycle" label="name" value="id" /> 
	             </logic:present>
		   </html:select>
		    </td>
		    </logic:equal>
		    
		    <logic:equal name="suballDetail" property="repaymentType" value="N" >
		    <td id="cyclDate1" style="display: none;"><bean:message key="lbl.cycleDate" /><font color="red">*</font></td>
		    <td id="cyclDate2" style="display: none;">
		    <html:select property="cycleDate" styleClass="text" styleId="cycleDate" value="${requestScope.allDetail[0].cycleDate}" onchange="nullNextDue();getDueDayForEmiCalc();">
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
		      		<td id="nddheader"><bean:message key="lbl.nextDueDate" /><font color="red">*</font></td>
		    		<td id="ndd">
		         			<html:text property="nextDueDate" styleClass="text"  value="${requestScope.allDetail[0].nextDueDate}" styleId="nextDueDate" onchange="checkDueDate(value);" />
		    		</td>
             
		    		<td><bean:message key="lbl.purpose" /></td>
		    		<td >
		    
		     			<a rel="tooltip3" id="tool">
		     			
		     			  <html:text property="loanPurpose" maxlength="50"  styleClass="text"  value="${requestScope.allDetail[0].loanPurpose}" styleId="loanPurpose" onchange="return upperMe('loanPurpose');" onmouseover="applyToolTip(id);"  />
		     			
		     			</a>
		     		
		     			
		  	 		</td>  
		    </tr>
		     <tr >
              <logic:present name="allDetail">
                  <logic:iterate id="suballDetail" name="allDetail" length="1">
                  <logic:equal name="suballDetail" property="repaymentType" value="I" >
		        <td id="int1" style="display: none;"><bean:message key="lbl.interestCalc" /><font color="red">*</font></td>
		    	 <td id="int2" style="display: none;">		    	
	            <html:select property="interestCalc" styleClass="text" styleId="interestCalc" value="${requestScope.allDetail[0].interestCalc}"  >
	             <html:option value="">---Select----</html:option>
	             <logic:present name="interestFrom">
	               <html:optionsCollection name="interestFrom" label="name" value="id" /> 
	             </logic:present>	            
	          </html:select>	          
	         </td>
	          </logic:equal>
	          
                    <logic:equal name="suballDetail" property="repaymentType" value="N" >
		        <td id="int1"><bean:message key="lbl.interestCalc" /><font color="red">*</font></td>
		    	 <td id="int2">		    	
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
	         </tr> 
	         <tr id="netLtvRow">
	         	<td><bean:message key="lbl.netLtv" /></td>
				<td><html:text property="netLtv" styleClass="text"  value="${requestScope.allDetail[0].netLtv}" styleId="netLtv"  readonly="true" /></td>
			 </tr> 
          
		    
		 <tr>
			<td >
			 <button type="button" class="topformbutton2" id="Save" title="Alt+V" accesskey="V" onclick="updateLoanDetailForEmiCalc('LoanDetailsForm');"><bean:message key="button.save" /></button>
			 
			    </td>
		</tr>
       
		 </table> </td></tr>
    </table>

</logic:present>


    </fieldset>

</html:form>

         
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

<logic:present name="back">

	<script type="text/javascript">
	
			alert("<bean:message key="msg.loanMove" />");
	
		
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
