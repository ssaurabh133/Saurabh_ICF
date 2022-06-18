<%--
      Author Name-      Pawan Kumar Singh
      Date of creation -20/04/2011
      Purpose-          Entry of Collateral Detail
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

	<head>
 <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	<!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/assetCollateral.js"></script>
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

<body onclick="parent_disable();" onload="document.getElementById('PropertyForm').assetsCollateralDesc.focus();tvLtvPerc();txnLtvPerc();docLtvPerc();acLtvPerc();tvLtvPerc1();tvLtvPerc2();init_fields();">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<%
	String assetClass=request.getParameter("assetClass");
	String assetCollateralType=request.getParameter("assetCollateralType");
	if(assetClass!=null)
	{
		request.setAttribute("assetClass",assetClass);
	}
	
	if(assetCollateralType!=null)
	{
	   if(assetCollateralType.equalsIgnoreCase("ASSET")){
	   request.setAttribute("assetCollateralTypeAsset",assetCollateralType);
	    request.setAttribute("assetCollateralType",assetCollateralType);
	    request.setAttribute("new","N");
	   }else{
	   request.setAttribute("assetCollateralTypeColl",assetCollateralType);
	     request.setAttribute("assetCollateralType",assetCollateralType);
	    request.setAttribute("new","N");
	   }
	 }
 %>

<div id=centercolumn>
<div id=revisedcontainer>	
<%--          			Insertion in deal capturing														 --%>
<logic:present name="DealCap">

<html:form action="/collateralPropertyProcessAction" styleId="PropertyForm" method="post">
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdProperty" styleClass="text" styleId ="assetsIdProperty" value="${fetchCollateralDetails[0].assetsId}"/>
<html:hidden property="colltype1" styleClass="text" styleId ="p1" value="${fetchCollateralDetails[0].colltype1}${assetClass }"/>
<html:hidden property="colltype2" styleClass="text" styleId ="p2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }"/>
<html:hidden property="colltype3" styleClass="text" styleId ="p3" value="${fetchCollateralDetails[0].colltype3}${new }"/>
<FIELDSET>
<LEGEND><bean:message key="lbl.propertyDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>        
        <TR>
          <td style="width:23%"><bean:message key="lbl.propertyDescription"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
<!--          <td><bean:message key="lbl.propertyValue"/><font color="red">*</font></td>-->
<!--          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue"  style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>-->
            <td style="width:23%"><bean:message key="lbl.propertyType"/><font color="red">*</font></td>
           <td style="width:26%">
           <html:select property="propertyType" styleId="propertyType" styleClass="text" value="${fetchCollateralDetails[0].propertyType}">
			      <logic:present name="propertyType">
				<logic:notEmpty name="propertyType">
					<html:optionsCollection name="propertyType" label="propertyTypeDesc" value="propertyTypeCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
<!--           <html:select property="propertyType" styleId="propertyType" styleClass="text" value="${fetchCollateralDetails[0].propertyType}">-->
<!--             <html:option value="R">Residential</html:option>-->
<!--             <html:option value="C">Commercial</html:option>-->
<!--             <html:option value="A">Agriculture</html:option>           -->
<!--           </html:select>-->
			</td>
          </TR>
<!--        <TR>-->
<!--         <td width="23%" style="width:23%"><bean:message key="lbl.propertySecurityMargin"/><font color="red">*</font></TD>-->
<!--         <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" style="text-align: right" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('collateralSecurityMargin');" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/></td>-->
<!--           <td style="width:23%"><bean:message key="lbl.propertyType"/></td>-->
<!--           <td style="width:26%">-->
<!--           <html:select property="propertyType" styleId="propertyType" styleClass="text" value="${fetchCollateralDetails[0].propertyType}">-->
<!--             <html:option value="R">Residential</html:option>-->
<!--             <html:option value="C">Commercial</html:option>-->
<!--             <html:option value="A">Agriculture</html:option>           -->
<!--           </html:select></td>-->
<!--		  </TR>-->
        <!--Nishant Space starts  -->
        <tr>
        	<td><bean:message key="lbl.lawyer"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="lawyerName" property="lawyerName"  maxlength="50" value="${fetchCollateralDetails[0].lawyerName}" />
		  </td>
		  <td><bean:message key="lbl.valuer"/></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="valuerName" property="valuerName" maxlength="50" value="${fetchCollateralDetails[0].valuerName}" /></td>
        </tr>
        <!--Nishant Space end  -->
		<tr>
		
<!--sssss-->
		
 		<td><bean:message key="lbl.propertyOwner"/><font color="red">*</font></td>
 		<td>
 		<logic:present name="dealAsset">
			<html:hidden  property="propertyOwner" styleId="propertyOwner" value="${fetchCollateralDetails[0].propertyOwner}"  />
   			<html:select property="propertyOwnerId" styleId="propertyOwnerId" size="5" multiple="multiple" style="width: 150" >  
   			<logic:present name="propertyOwnerList"> 			
       		<html:optionsCollection name="propertyOwnerList" value="propertyOwnerId" label="propertyOwnerDesc"/>   
       		</logic:present>   		
     		</html:select>
      		<html:button property="stateButton" styleId="stateButton" onclick="return openMultiSelectLOVCommon(492,'PropertyForm','propertyOwnerId','','', '','','','propertyOwner');" value=" " styleClass="lovbutton"></html:button>
      		</logic:present>
      	     <logic:present name="loanAsset">
      	     <html:hidden  property="propertyOwner" styleId="propertyOwner" value="${fetchCollateralDetails[0].propertyOwner}"  />
   			<html:select property="propertyOwnerId" styleId="propertyOwnerId" size="5" multiple="multiple" style="width: 150" >  
   			<logic:present name="propertyOwnerList"> 			
       		<html:optionsCollection name="propertyOwnerList" value="propertyOwnerId" label="propertyOwnerDesc"/>   
       		</logic:present>   		
     		</html:select>
      		<html:button property="stateButton" styleId="stateButton" onclick="return openMultiSelectLOVCommon(493,'PropertyForm','propertyOwnerId','','', '','','','propertyOwner');" value=" " styleClass="lovbutton"></html:button>
      	    </logic:present>
	
      		</td>
<!--sss-->
<!--          <td><bean:message key="lbl.propertyOwner"/><font color="red">*</font></td>-->
<!--		    <td nowrap="nowrap">-->
<!--		  <html:select styleClass="text" property="propertyOwner" styleId="propertyOwner" value="${fetchCollateralDetails[0].propertyOwner}">-->
<!--		  <html:option value="Applicant">Applicant</html:option>-->
<!--		   <html:option value="Co-applicant">Co-applicant</html:option>-->
<!--		   <html:option value="Guarantor">Guarantor</html:option>-->
<!--		  </html:select>-->
<!--		  </td>-->
		  <td><bean:message key="lbl.PerofConstruction"/><font color="red">*</font></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="propertyConstruct" style="text-align: right" property="propertyConstruct" maxlength="3" value="${fetchCollateralDetails[0].propertyConstruct}" onchange="setArea();" onkeyup="return checkRate('propertyConstruct');" onkeypress="return isNumberKey(event);"/></td>
		</tr>  
		<tr>
		  	<td><bean:message key="lbl.areaSQFT"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="propertyArea" property="propertyArea" maxlength="7" value="${fetchCollateralDetails[0].propertyArea}" style="text-align: right" onkeypress="return numbersonly(event,id,7);" onblur="calBuiltUpArea();" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);"/></td> 
      		<td><bean:message key="lbl.buildUpArea"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="buildUpArea" property="buildUpArea"  maxlength="7" value="${fetchCollateralDetails[0].buildUpArea}" style="text-align: right" onchange="calAreaSqf();" onkeypress="return numbersonly(event,id,7);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);"/></td> 
       </tr>     
       <tr>
       		<td><bean:message key="lbl.buildUpAreaSQMTR"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="buildUpAreaSQMTR" property="buildUpAreaSQMTR" readonly="true"  style="text-align: right" value="${fetchCollateralDetails[0].buildUpAreaSQMTR}"  onkeypress="return numbersonly(event,id,7);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);"/></td> 
         	 <td><bean:message key="lbl.superBuildUpArea" /></td>					
         <td>        
            <html:text property="superBuildUpArea" styleId="superBuildUpArea" size="20" styleClass="text"  value="${fetchCollateralDetails[0].superBuildUpArea}" onkeypress="return numbersonly(event,id,7);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);" ></html:text>
          </td>
       		<!--Nishant Space starts  -->
       		
       		<!--Nishant Space end  -->
       </tr>
       <tr>
       
         <td><bean:message key="lbl.carpetArea" /></td>					
         <td>        
            <html:text property="carpetArea" styleId="carpetArea" size="20" styleClass="text"  value="${fetchCollateralDetails[0].carpetArea}" onkeypress="return numbersonly(event,id,7);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);" ></html:text>
          </td>
          <td><bean:message key="lbl.totalArea"/></td>
         <td nowrap="nowrap"><html:text styleClass="text" styleId="totalArea" property="totalArea" maxlength="7" value="${fetchCollateralDetails[0].totalArea}" style="text-align: right" onkeypress="return numbersonly(event,id,7);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);"/></td> 
       </tr>
       <tr>
       
		<td style="width:23%"><bean:message key="lbl.propDirection"/></td>
           <td style="width:26%">
           <html:select property="propertyDirection" styleId="propertyDirection" styleClass="text" value="${fetchCollateralDetails[0].propertyDirection}">
             <html:option value="N">North</html:option>
             <html:option value="E">East</html:option>
             <html:option value="W">West</html:option>      
             <html:option value="S">South</html:option>  
             <html:option value="NE">North-East</html:option>  
             <html:option value="NW">North-West</html:option>  
             <html:option value="SW">South-West</html:option>  
             <html:option value="SE">South-East</html:option>       
           </html:select></td>
       </tr>
       <tr>
		  	<td><bean:message key="lbl.propertyValue"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" maxlength="15" value="${fetchCollateralDetails[0].assetsCollateralValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);txnLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap">
         		<html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" readonly="true" maxlength="15" value="" style="text-align: right"/>
         		<html:hidden property="loanAmt" styleId="loanAmt" value="${loanAmount}"/>
         	</td> 
       </tr>
        <tr>
		  	<td><bean:message key="lbl.technicalValuation1"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="techValuation" property="techValuation" maxlength="15" value="${fetchCollateralDetails[0].techValuation}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc();calValuationAmount();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="tvLTV" property="tvLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
       <tr>
           
           <td><bean:message key="lbl.technicalValuation2"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="technicalValuation1" property="technicalValuation1" maxlength="15" value="${fetchCollateralDetails[0].technicalValuation1}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc1();calValuationAmount();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="LTV1" property="LTV1" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
           
           </tr>
           
           
           
       
        <tr>
        	<td><bean:message key="lbl.lawyer2"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="lawyerName2" property="lawyerName2"  maxlength="50" value="${fetchCollateralDetails[0].lawyerName2}" readonly="false" /></td>
		  <td><bean:message key="lbl.valuer2"/></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="valuerName2" property="valuerName2" maxlength="50" value="${fetchCollateralDetails[0].valuerName2}"  readonly="false" /></td>
        </tr>
           
           <tr>
            <td><bean:message key="lbl.technicalValuation3" /></td>					
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="technicalValuation2" property="technicalValuation2" maxlength="15" value="${fetchCollateralDetails[0].technicalValuation2}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc2();calValuationAmount();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="LTV2" property="LTV2" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
           </tr>
           
          <tr>
        	<td><bean:message key="lbl.lawyer3"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="lawyerName3" property="lawyerName3"  maxlength="50" value="${fetchCollateralDetails[0].lawyerName3}" readonly="false" /></td>
		  <td><bean:message key="lbl.valuer3"/></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="valuerName3" property="valuerName3" maxlength="50" value="${fetchCollateralDetails[0].valuerName3}"  readonly="false" /></td>
        </tr> 
           
                      
        <tr>
        	<td><bean:message key="lbl.valuationMethod" /></td>
        	<td>
		 	<html:text styleClass="text" property="valuationMethod" styleId="valuationMethod" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].valuationMethod}"/>
  			<html:hidden  property="valuationMethodId" styleId="valuationMethodId" value="${fetchCollateralDetails[0].valuationMethodId}" />     													
  		    <html:button property="valuationMethodButton" styleId="valuationMethodButton" onclick="closeLovCallonLov1(); openLOVCommon(494,'PropertyForm','valuationMethod','','','','','valuationMeth','valuationMethodId'); " value=" " styleClass="lovbutton"></html:button>
        	</td>
        	<td><bean:message key="lbl.valuationAmount"/></td>
        	<td><html:text styleClass="text" property="valuationAmount" styleId="valuationAmount" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].valuationAmount}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calAssetsCollateralValue();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/>
        	</td>
        </tr>
        <tr>
		  	<td><bean:message key="lbl.docValue"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="docValue" property="docValue" maxlength="15" value="${fetchCollateralDetails[0].docValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);docLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="docLTV" property="docLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
        <tr>
		  	<td><bean:message key="lbl.addConstruction"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="addConstruction" property="addConstruction" maxlength="15" value="${fetchCollateralDetails[0].addConstruction}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="acLTV" property="acLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
       
       <tr>
		  	<td><bean:message key="lbl.amountFunded"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="amountFunded" property="amountFunded" maxlength="15" value="${fetchCollateralDetails[0].amountFunded}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.totalAmountFunded"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="totalAmountFunded" property="totalAmountFunded" maxlength="15" value="${fetchCollateralDetails[0].totalAmountFunded}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
       </tr>
           
           <tr>         
         	<td>
		 		<bean:message key="address.addr.one"/><font color="red">*</font>
		 	</td>
           <td nowrap="nowrap">
           		<html:text styleClass="text" styleId="propertyAddress" property="propertyAddress" maxlength="50" value="${fetchCollateralDetails[0].propertyAddress}" onchange="return upperMe('propertyAddress');"/>
			</td>	
			<td><bean:message key="address.addr.two" /></td>
			<td>
				<html:text property="addr2" styleClass="text" styleId="addr2" maxlength="50" value="${fetchCollateralDetails[0].addr2}" onchange="return upperMe('addr2');"/>
			</td>	
		</tr>
		<tr>
			<td><bean:message key="address.addr.three" /></td>
			<td>
				<html:text property="addr3" styleClass="text" styleId="addr3" maxlength="50" value="${fetchCollateralDetails[0].addr3}" onchange="return upperMe('addr3');"/>
			</td>
			<td><bean:message key="lbl.villageLandmark"/></td>
				<td nowrap="nowrap"><html:text styleClass="text" styleId="villageLandmark" property="villageLandmark"  maxlength="50" value="${fetchCollateralDetails[0].villageLandmark}" />
			</td>
		</tr>
		<tr>
			<td><bean:message key="address.country" /><font color="red">*</font></td>
           	<td>
          			<input type="hidden" name="hcommon" id="hcommon" />
          			<html:text property="country" styleId="country"  maxlength="20" value="${fetchCollateralDetails[0].country}" styleClass="text" tabindex="-1" readonly="true"/>
    				<html:hidden property="txtCountryCode" styleId="txtCountryCode" value="${fetchCollateralDetails[0].txtCountryCode}" styleClass="text" />
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    				<html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(4,'PropertyForm','country','','','','','clearCountryLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
			</td>
			<td><bean:message key="address.state" /><font color="red">*</font></td>
            <td>
                <div id="statedetail">
                	<html:text property="state" styleId="state" styleClass="text" size="20" value="${fetchCollateralDetails[0].state}" readonly="true" tabindex="-1"></html:text>
   					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${fetchCollateralDetails[0].txtStateCode}"></html:hidden>
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton" onclick="closeLovCallonLov('country');openLOVCommon(5,'PropertyForm','state','country','txtStateCode', 'txtCountryCode','Please select country first','clearStateLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
	          	</div>
			</td>
		</tr>
		<tr>
			<td><bean:message key="address.dist" /><font color="red">*</font></td>
          	<td>
          		<div id="cityID">
          			<html:text property="dist" styleId="dist" size="20" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].dist}" tabindex="-1"></html:text>
          			<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${fetchCollateralDetails[0].txtDistCode}"></html:hidden>
   					<html:button property="distButton" styleId="districtButton" onclick="closeLovCallonLov('state');openLOVCommon(20,'PropertyForm','dist','state','txtDistCode', 'txtStateCode','Please select state first','clearTahsilLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   				</div>
   			</td>
			<td><bean:message key="address.Tahsil" /></td>					
            <td> 
            	<div id="tahsildetail">
            		<html:text property="tahsil" styleId="tahsil" size="20" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].tahsil}" tabindex="-1"></html:text>
                	<html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value=""/>
 			   		<html:button property="tahsilButton" styleId="tahsilButton" onclick="closeLovCallonLov('dist');openLOVCommon(388,'PropertyForm','tahsil','dist','txnTahsilHID', 'txtDistCode','Please select District first','','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   				</div>
   			</td>	
		</tr>
		<tr>
			<td><bean:message key="address.pincode" /><font color="red">*</font></td>
			<td><html:text maxlength="6" property="pincode" styleClass="text" styleId="pincode" style="text-align: right" onkeypress="return isNumberKey(event);" value="${fetchCollateralDetails[0].pincode}"/></td>
			<logic:present name="fetchCollateralDetails">
 <logic:iterate name="fetchCollateralDetails" id="subList">
 <logic:equal name="subList" property="colltype2" value="ASSET">
 	<td><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" styleId="assetNature" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>
 </logic:equal>
  </logic:iterate>
 </logic:present>
 <logic:notPresent name="fetchCollateralDetails">
<logic:present name="assetCollateralTypeAsset" >
 <td><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" styleId="assetNature" styleClass="text" >
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>              
</logic:present> 
 </logic:notPresent>  
 
		</tr>
		<tr>
 
 <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" value="${fetchCollateralDetails[0].securityTypes}">
			      <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:equal>
		      </logic:iterate>
		      </logic:present>
		      <logic:notPresent name="fetchCollateralDetails">
		  	 <logic:present name="assetCollateralTypeAsset" >
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" value="" >
			      <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:present>
		      </logic:notPresent> 	
           	<td><bean:message key="lbl.assetLevel"/><font color="red">*</font></td>
           		<td> 
		     		<html:select property="assetLevel" styleId="assetLevel" styleClass="text" value="${fetchCollateralDetails[0].assetLevel}" >
			     			<html:option value="">---Select----</html:option>
			     		<html:option value="Negative Profile">Negative Profile</html:option>
			     		<html:option value="Caution Profile">Caution Profile</html:option>
			     		<html:option value="Others">Others</html:option> 
					</html:select>
		      </td>	
           </tr>
           <tr>
           <td><bean:message key="lbl.propertyStatus"/><font color="red">*</font></td>
   
		      <td>
		      
		      <html:select property="propertyStatus" styleId="propertyStatus" styleClass="text" value="${fetchCollateralDetails[0].propertyStatus}">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="propertyStatus">
				  <logic:notEmpty name="propertyStatus">
				  <html:optionsCollection name="propertyStatus" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>
<!--		       <select name="propertyStatus" id="propertyStatus"  class="text" >-->
<!--	              <option value="">---Select---</option>-->
<!--	              <logic:present name="propertyStatus">-->
<!--				     	<logic:iterate name="propertyStatus" id="collateralsOb">-->
<!--				     		<option  value="${collateralsOb.id}">${collateralsOb.name }</option>-->
<!--				     	</logic:iterate>-->
<!--				     </logic:present>-->
<!--	              </select>-->
		      </td>
		      	

			<td><bean:message key="lbl.propertyTitle"/></td>
   
		      <td>
		      
	              
	              <html:select property="propertyTitle" styleId="propertyTitle" styleClass="text" value="${fetchCollateralDetails[0].propertyTitle}">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="propertyTitle">
				  <logic:notEmpty name="propertyTitle">
				  <html:optionsCollection name="propertyTitle" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>
<!--	              <td>-->
<!--		       <select name="propertyStatus" id="propertyStatus"  class="text" >-->
<!--	              <option value="">---Select---</option>-->
<!--	              <logic:present name="propertyStatus">-->
<!--				     	<logic:iterate name="propertyStatus" id="collateralsOb">-->
<!--				     		<option  value="${collateralsOb.id}">${collateralsOb.name }</option>-->
<!--				     	</logic:iterate>-->
<!--				     </logic:present>-->
<!--	              </select>-->
<!--		      </td>-->
	              					
									
									
		      </td>
           </tr>
           
           </tr>
           
           <tr>
           <!-- Richa -->
          <td>
			  <bean:message key="lbl.mortgage" /><font color="red">*</font>
		</td>
	   <td>
			 <html:select property="mortgage" value="${fetchCollateralDetails[0].mortgage}" styleClass="text" styleId="mortgage">
				<html:option value="">-- Select --
</html:option>
				<logic:present name="mortageList">
					<logic:notEmpty name="mortageList">
					<html:optionsCollection name="mortageList" label="name" value="id" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
<!--				 <select name="mortgage" id="mortgage"  

class="text" value="${fetchCollateralDetails[0].propertyArea}" >-->
<!--	              <option value="">---Select---</option>-->
<!--	              <logic:present name="mortageList">-->
<!--				     	<logic:iterate 

name="mortageList" id="mortageListOb">-->
<!--				     		<option  

value="${mortageListOb.id}">${mortageListOb.name }</option>-->
<!--				     	</logic:iterate>-->
<!--				     </logic:present>-->
<!--	              </select>-->
                         
	      </td>
	      <td><bean:message key="lbl.sibdi"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="sibdi" property="sibdi" maxlength="15" value="${fetchCollateralDetails[0].sibdi}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
           </tr>   
           <tr>
		  	<logic:present name="assetCollateralType">
		  	<logic:equal value="ASSET" name="assetCollateralType">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  /></td>
		  	</logic:equal>
		  	</logic:present>
		  	
		  	<logic:equal name="actype" value="ASSET">
		  	<logic:iterate name="fetchCollateralDetails" id="subList">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  checked="checked"/></td>
		  	</logic:equal>
		  	
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" /></td>
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</logic:equal>
		  	
		  	</tr>
		  	 
		 		  <tr>
 		  <td> 
 		  <button id="save" type="button" name="button1" class="topformbutton2" title="Alt+V" accesskey="V" onclick=" savePropertyDetails();"><bean:message key="button.save" /></button> 	
 		
 		    </td>	
		  </tr>	
		 
		 <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>
		 

	</TABLE>	
	 
	
	</TD>
	</TR>
	</TABLE>

</FIELDSET> 
</html:form>
</logic:present>
<%--          			view Deal 																 --%>
<logic:present name="viewDeal">
<html:form action="/collateralPropertyProcessAction" styleId="PropertyForm" method="post">
<html:hidden property="assetsIdProperty" styleClass="text" styleId ="assetsIdProperty" value="${fetchCollateralDetails[0].assetsId}"/>
<html:hidden property="colltype1" styleClass="text" styleId ="p1" value="${fetchCollateralDetails[0].colltype1}${assetClass }"/>
<html:hidden property="colltype2" styleClass="text" styleId ="p2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }"/>
<html:hidden property="colltype3" styleClass="text" styleId ="p3" value="${fetchCollateralDetails[0].colltype3}${new }"/>
<FIELDSET>
<LEGEND><bean:message key="lbl.propertyDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0 >        
        <tr>
          <td style="width:23%"><bean:message key="lbl.propertyDescription"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
<!--          <td><bean:message key="lbl.propertyValue"/><font color="red">*</font></td>-->
<!--          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}"  style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>-->
         <td style="width:23%"><bean:message key="lbl.propertyType"/><font color="red">*</font></td>
         	<td style="width:26%">
           <html:select property="propertyType" styleId="propertyType" styleClass="text" disabled="true" value="${fetchCollateralDetails[0].propertyType}">
             <logic:present name="propertyType">
				<logic:notEmpty name="propertyType">
					<html:optionsCollection name="propertyType" label="propertyTypeDesc" value="propertyTypeCode" />
				</logic:notEmpty>
				</logic:present>     
           </html:select>
           </td>
          </tr>
<!--        <tr>-->
<!--         <td width="23%" style="width:23%"><bean:message key="lbl.propertySecurityMargin"/><font color="red">*</font></td>-->
<!--         <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" disabled="true" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('collateralSecurityMargin');" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/></td>-->
<!--           <td style="width:23%"><bean:message key="lbl.propertyType"/></td>-->
<!--           -->
<!--		  </tr>-->
		  <!--Nishant Space starts  -->
        <tr>
        	<td><bean:message key="lbl.lawyer"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="lawyerName" property="lawyerName" disabled="true" maxlength="50" value="${fetchCollateralDetails[0].lawyerName}" />
		  </td>
		  <td><bean:message key="lbl.valuer"/></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="valuerName" property="valuerName" disabled="true" maxlength="50" value="${fetchCollateralDetails[0].valuerName}" /></td>
        </tr>
        <!--Nishant Space end  -->
		<tr>
         <td><bean:message key="lbl.propertyOwner"/><font color="red">*</font></td>
 		<td>
 			<logic:present name="dealAsset">
 			<logic:notPresent name="dealLoanAsset">
			<html:hidden  property="propertyOwner" styleId="propertyOwner" value="${fetchCollateralDetails[0].propertyOwner}"  />
   			<html:select property="propertyOwnerId" styleId="propertyOwnerId" size="5" multiple="multiple" style="width: 150" disabled="disabled" >  
   			<logic:present name="propertyOwnerList"> 			
       		<html:optionsCollection name="propertyOwnerList" value="propertyOwnerId" label="propertyOwnerDesc"/>   
       		</logic:present>   		
     		</html:select>
      		</logic:notPresent>
      		</logic:present>
      	     <logic:present name="loanAsset">
      	     <logic:notPresent name="dealLoanAsset">
      	     <html:hidden  property="propertyOwner" styleId="propertyOwner" value="${fetchCollateralDetails[0].propertyOwner}"  />
   			<html:select property="propertyOwnerId" styleId="propertyOwnerId" size="5" multiple="multiple" style="width: 150" disabled="disabled" >  
   			<logic:present name="propertyOwnerList"> 			
       		<html:optionsCollection name="propertyOwnerList" value="propertyOwnerId" label="propertyOwnerDesc"/>   
       		</logic:present>   		
     		</html:select>
      		</logic:notPresent>
      	    </logic:present>
      	    
 		
		<logic:present name="dealLoanAsset">
		<html:hidden  property="propertyOwner" styleId="propertyOwner" value="${fetchCollateralDetails[0].propertyOwner}"  />
   			<html:select property="propertyOwnerId" styleId="propertyOwnerId" size="5" multiple="multiple" style="width: 150" disabled="disabled" >  
   			<logic:present name="propertyOwnerList"> 			
       		<html:optionsCollection name="propertyOwnerList" value="propertyOwnerId" label="propertyOwnerDesc"/>   
       		</logic:present>   		
     		</html:select>
		</logic:present>
     	</td>
		  <td><bean:message key="lbl.PerofConstruction"/><font color="red">*</font></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="propertyConstruct" style="text-align: right"  disabled="true" property="propertyConstruct" maxlength="3" value="${fetchCollateralDetails[0].propertyConstruct}" onchange="setArea();" onkeyup="return checkRate('propertyConstruct');" onkeypress="return isNumberKey(event);"/></td>
		</tr>  
		<tr>
		  	<td><bean:message key="lbl.areaSQFT"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="propertyArea" property="propertyArea" disabled="true" maxlength="50" value="${fetchCollateralDetails[0].propertyArea}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="calBuiltUpArea();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.buildUpArea"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="buildUpArea" property="buildUpArea" readonly="true" maxlength="50" value="${fetchCollateralDetails[0].buildUpArea}" style="text-align: right"/></td> 
       </tr> 
       <tr>
       		<td><bean:message key="lbl.buildUpAreaSQMTR"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="buildUpAreaSQMTR" property="buildUpAreaSQMTR" readonly="true" maxlength="50" value="${fetchCollateralDetails[0].buildUpAreaSQMTR}" style="text-align: right"/></td> 
         	<td><bean:message key="lbl.superBuildUpArea" /></td>					
         <td>        
            <html:text property="superBuildUpArea" styleId="superBuildUpArea" size="20" styleClass="text" readonly="true"  value="${fetchCollateralDetails[0].superBuildUpArea}" onkeypress="return numbersonly(event,id,7);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);" ></html:text>
          </td>
       		<!--Nishant Space starts  -->
       		
       		<!--Nishant Space end  -->
       </tr>
       <tr>
       
       <td><bean:message key="lbl.carpetArea" /></td>					
            <td> 
            <html:text property="carpetArea" styleId="carpetArea" size="20" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].carpetArea}" onkeypress="return numbersonly(event,id,7);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);" ></html:text>
          </td>
          <td><bean:message key="lbl.totalArea"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="totalArea" property="totalArea" disabled="true" maxlength="7" value="${fetchCollateralDetails[0].totalArea}" style="text-align: right" onkeypress="return numbersonly(event,id,7);"  onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);"/></td> 
          
          
       </tr>
       <tr>
       <td style="width:23%"><bean:message key="lbl.propDirection"/></td>
           <td style="width:26%">
           <html:select property="propertyDirection" styleId="propertyDirection" styleClass="text" disabled="true" value="${fetchCollateralDetails[0].propertyDirection}">
             <html:option value="N">North</html:option>
             <html:option value="E">East</html:option>
             <html:option value="W">West</html:option>      
             <html:option value="S">South</html:option>  
             <html:option value="NE">North-East</html:option>  
             <html:option value="NW">North-West</html:option>  
             <html:option value="SW">South-West</html:option>  
             <html:option value="SE">South-East</html:option>       
           </html:select></td>
       </tr>
       <tr>
		  	<td><bean:message key="lbl.propertyValue"/>
		  	<font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue"  disabled="true" maxlength="15" value="${fetchCollateralDetails[0].assetsCollateralValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);txnLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap">
         		<html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" readonly="true" maxlength="15" value="" style="text-align: right"/>
         		<html:hidden property="loanAmt" styleId="loanAmt" value="${loanAmount}"/>
         	</td> 
       </tr>
        <tr>
		  	<td><bean:message key="lbl.technicalValuation1"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="techValuation" property="techValuation" disabled="true" maxlength="15" value="${fetchCollateralDetails[0].techValuation}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="tvLTV" property="tvLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
       
        <tr>
           
           <td><bean:message key="lbl.technicalValuation2"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="technicalValuation1" property="technicalValuation1" disabled="true"  maxlength="15" value="${fetchCollateralDetails[0].technicalValuation1}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc1();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="LTV1" property="LTV1" readonly="true" maxlength="15"  value="" style="text-align: right"/></td> 
           
           </tr>
           
            <tr>
        	<td><bean:message key="lbl.lawyer2"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="lawyerName2" property="lawyerName2"  maxlength="50" value="${fetchCollateralDetails[0].lawyerName2}" readonly="false" /></td>
		  <td><bean:message key="lbl.valuer2"/></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="valuerName2" property="valuerName2" maxlength="50" value="${fetchCollateralDetails[0].valuerName2}"  readonly="false" /></td>
        	</tr> 
           
           <tr>
            <td><bean:message key="lbl.technicalValuation3" /></td>					
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="technicalValuation2" property="technicalValuation2" disabled="true"  maxlength="15" value="${fetchCollateralDetails[0].technicalValuation2}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc2();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="LTV2" property="LTV2" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
           </tr>
           
            <tr>
        	<td><bean:message key="lbl.lawyer3"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="lawyerName3" property="lawyerName3"  maxlength="50" value="${fetchCollateralDetails[0].lawyerName3}" readonly="false" /></td>
		  <td><bean:message key="lbl.valuer3"/></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="valuerName3" property="valuerName3" maxlength="50" value="${fetchCollateralDetails[0].valuerName3}"  readonly="false" /></td>
        </tr> 
           
           <tr>
        	<td><bean:message key="lbl.valuationMethod" /></td>
        	<td>
		 	<html:text styleClass="text" property="valuationMethod" styleId="valuationMethod" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].valuationMethod}"/>
  			<html:hidden  property="valuationMethodId" styleId="valuationMethodId" value="${fetchCollateralDetails[0].valuationMethodId}" />     													
  		   
        	</td>
        	<td><bean:message key="lbl.valuationAmount"/></td>
        	<td><html:text styleClass="text" property="valuationAmount" styleId="valuationAmount" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].valuationAmount}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calAssetsCollateralValue();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/>
        	</td>
        </tr>  
        <tr>
		  	<td><bean:message key="lbl.docValue"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="docValue" property="docValue" disabled="true" maxlength="15" value="${fetchCollateralDetails[0].docValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);docLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="docLTV" property="docLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
        <tr>
		  	<td><bean:message key="lbl.addConstruction"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="addConstruction" property="addConstruction" disabled="true" maxlength="15" value="${fetchCollateralDetails[0].addConstruction}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="acLTV" property="acLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
       
       <tr>
		  	<td><bean:message key="lbl.amountFunded"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="amountFunded" disabled="true" property="amountFunded" maxlength="15" value="${fetchCollateralDetails[0].amountFunded}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.totalAmountFunded"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="totalAmountFunded" disabled="true" property="totalAmountFunded" maxlength="15" value="${fetchCollateralDetails[0].totalAmountFunded}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
       </tr>
           
           <tr>         
         	<td>
		 		<bean:message key="address.addr.one"/><font color="red">*</font>
		 	</td>
           <td nowrap="nowrap">
           		<html:text styleClass="text" styleId="propertyAddress" property="propertyAddress" disabled="true" maxlength="50" value="${fetchCollateralDetails[0].propertyAddress}" onchange="return upperMe('propertyAddress');"/>
			</td>	
			<td><bean:message key="address.addr.two" /></td>
			<td>
				<html:text property="addr2" styleClass="text" styleId="addr2" maxlength="50" disabled="true" value="${fetchCollateralDetails[0].addr2}" onchange="return upperMe('addr2');"/>
			</td>	
		</tr>
		<tr>
			<td><bean:message key="address.addr.three" /></td>
			<td>
				<html:text property="addr3" styleClass="text" styleId="addr3" disabled="true" maxlength="50" value="${fetchCollateralDetails[0].addr3}" onchange="return upperMe('addr3');"/>
			</td>
			<td><bean:message key="lbl.villageLandmark"/></td>
				<td nowrap="nowrap"><html:text styleClass="text" styleId="villageLandmark" disabled="true" property="villageLandmark"  maxlength="50" value="${fetchCollateralDetails[0].villageLandmark}" />
			</td>
		</tr>
		<tr>
			<td><bean:message key="address.country" /><font color="red">*</font></td>
           	<td>
          			<input type="hidden" name="hcommon" id="hcommon" />
          			<html:text property="country" styleId="country" disabled="true"  maxlength="20" value="${fetchCollateralDetails[0].country}" styleClass="text" tabindex="-1" readonly="true"/>
    				<html:hidden property="txtCountryCode" styleId="txtCountryCode" value="${fetchCollateralDetails[0].txtCountryCode}" styleClass="text" />
    				
			</td>
			<td><bean:message key="address.state" /><font color="red">*</font></td>
            <td>
                <div id="statedetail">
                	<html:text property="state" styleId="state" disabled="true" styleClass="text" size="20" value="${fetchCollateralDetails[0].state}" readonly="true" tabindex="-1"></html:text>
   					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${fetchCollateralDetails[0].txtStateCode}"></html:hidden>
    				
	          	</div>
			</td>
		</tr>
		<tr>
			<td><bean:message key="address.dist" /><font color="red">*</font></td>
          	<td>
          		<div id="cityID">
          			<html:text property="dist" styleId="dist" size="20"  disabled="true" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].dist}" tabindex="-1"></html:text>
          			<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${fetchCollateralDetails[0].txtDistCode}"></html:hidden>
   					
   				</div>
   			</td>
			<td><bean:message key="address.Tahsil" /></td>					
            <td> 
            	<div id="tahsildetail">
            		<html:text property="tahsil" styleId="tahsil" size="20" disabled="true" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].tahsil}" tabindex="-1"></html:text>
                	<html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value=""/>
 			   		
   				</div>
   			</td>	
		</tr>
		<tr>
			<td><bean:message key="address.pincode" /><font color="red">*</font></td>
			<td><html:text maxlength="6" property="pincode" styleClass="text" disabled="true" styleId="pincode" style="text-align: right" onkeypress="return isNumberKey(event);" value="${fetchCollateralDetails[0].pincode}"/></td>
			<logic:present name="fetchCollateralDetails">
 <logic:iterate name="fetchCollateralDetails" id="subList">
 <logic:equal name="subList" property="colltype2" value="ASSET">
 	<td><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" styleId="assetNature" disabled="true" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>
 </logic:equal>
  </logic:iterate>
 </logic:present>
 <logic:notPresent name="fetchCollateralDetails">
<logic:present name="assetCollateralTypeAsset" >
 <td><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" disabled="true" styleId="assetNature" styleClass="text" >
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>              
</logic:present> 
 </logic:notPresent>  
 
		</tr>
		<tr>
 
 <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList"  property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" disabled="true" styleClass="text" value="${fetchCollateralDetails[0].securityTypes}">
			      <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:equal>
		      </logic:iterate>
		      </logic:present>
		      <logic:notPresent name="fetchCollateralDetails">
		  	 <logic:present name="assetCollateralTypeAsset" >
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" disabled="true" styleClass="text" value="" >
			      <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:present>
		      </logic:notPresent> 	
           	<td><bean:message key="lbl.assetLevel"/><font color="red">*</font></td>
           		<td> 
		     		<html:select property="assetLevel" styleId="assetLevel" disabled="true" styleClass="text" value="${fetchCollateralDetails[0].assetLevel}" >
		     		<html:option value="">---Select----</html:option>
			     		<html:option value="Negative Profile">Negative Profile</html:option>
			     		<html:option value="Caution Profile">Caution Profile</html:option>
			     		<html:option value="Others">Others</html:option> 
			     		<html:option value="RURAL">RURAL</html:option>
			     		<html:option value="URBAN">URBAN</html:option> 
					</html:select>
		      </td>	
           </tr>
           
            <tr>
           <td><bean:message key="lbl.propertyStatus"/><font color="red">*</font></td>
   
		      <td>
		      
		      <html:select property="propertyStatus" styleId="propertyStatus" styleClass="text"  value="${fetchCollateralDetails[0].propertyStatus}" disabled="true">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="propertyStatus">
				  <logic:notEmpty name="propertyStatus">
				  <html:optionsCollection name="propertyStatus" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>

		      </td>
<!--		      <td><bean:message key="lbl.carpetArea" /></td>					-->
<!--            <td> -->
<!--            -->
<!--            		<html:text property="carpetArea" styleId="carpetArea" size="20" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].carpetArea}" onkeypress="return numbersonly(event,id,7);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);" ></html:text>-->
<!--          </td>-->
            <!--  </tr>
           
           <tr> -->
			<td><bean:message key="lbl.propertyTitle"/></td>
   
		      <td>
		      
	              
	              <html:select property="propertyTitle" styleId="propertyTitle" styleClass="text"  value="${fetchCollateralDetails[0].propertyTitle}" disabled="true">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="viewPropertyTitle">
				  <logic:notEmpty name="viewPropertyTitle">
				  <html:optionsCollection name="viewPropertyTitle" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>

	              					
									
									
		      </td>
           </tr>
           
           <tr>
           <!-- Richa -->
          <td>
			  <bean:message key="lbl.mortgage" /><font color="red">*</font>
		</td>
	   <td>
			 <html:select property="mortgage" value="${fetchCollateralDetails[0].mortgage}" styleClass="text" styleId="mortgage" disabled="true">
				<html:option value="">-- Select --
</html:option>
				<logic:present name="viewMortageList">
					<logic:notEmpty name="viewMortageList">
					<html:optionsCollection name="viewMortageList" label="name" value="id" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>
                 
	      </td>
	      <td><bean:message key="lbl.sibdi"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="sibdi" property="sibdi" maxlength="15" value="${fetchCollateralDetails[0].sibdi}" disabled="true" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
           </tr>   
           <tr>
		  	<logic:present name="assetCollateralType">
		  	<logic:equal value="ASSET" name="assetCollateralType">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" disabled="disabled"  id="assetStandard"  /></td>
		  	</logic:equal>
		  	</logic:present>
		  	
		  	<logic:equal name="actype" value="ASSET">
		  	<logic:iterate name="fetchCollateralDetails" id="subList">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  disabled="disabled" checked="checked"/></td>
		  	</logic:equal>
		  	
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard"  disabled="disabled" id="assetStandard" /></td>
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</logic:equal>
		  	
		  	</tr>

		 
		  <tr>
 		  <td> 
<!-- 		  <html:button property="button1" styleClass="topformbutton2" value="Save" onclick="return savePropertyDetails();"/> 	-->
 		
 		    </td>	
		  </tr>	
		 
		 <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>
		 

	</TABLE>	
	 
	
	</TD>
	</TR>
	</TABLE>

</FIELDSET>
 
</html:form>
</logic:present>
</div>

<%--          			Loan Initiation Author part																	 --%>
<logic:present name="viewPropertyInCM">

	<html:form action="/collateralPropertyProcessAction" styleId="PropertyForm" method="post">

<FIELDSET>
<LEGEND><bean:message key="lbl.propertyDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>        
         <tr>
          <td style="width:23%"><bean:message key="lbl.propertyDescription"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${viewPropertyInCM[0].assetsCollateralDesc}" /></td>
<!--          <td><bean:message key="lbl.propertyValue"/><font color="red">*</font></td>-->
<!--          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" property="assetsCollateralValue" value="${fetchCollateralDetails[0].assetsCollateralValue}"  style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>-->
         <td style="width:23%"><bean:message key="lbl.propertyType"/><font color="red">*</font></td>
         	<td style="width:26%">
           <html:select property="propertyType" styleId="propertyType" styleClass="text" disabled="true" value="${viewPropertyInCM[0].propertyType}">
             <logic:present name="propertyType">
				<logic:notEmpty name="propertyType">
					<html:optionsCollection name="propertyType" label="propertyTypeDesc" value="propertyTypeCode" />
				</logic:notEmpty>
				</logic:present>
           </html:select>
           </td>
          </tr>
<!--        <tr>-->
<!--         <td width="23%" style="width:23%"><bean:message key="lbl.propertySecurityMargin"/><font color="red">*</font></td>-->
<!--         <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" disabled="true" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('collateralSecurityMargin');" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/></td>-->
<!--           <td style="width:23%"><bean:message key="lbl.propertyType"/></td>-->
<!--           -->
<!--		  </tr>-->
		  <!--Nishant Space starts  -->
        <tr>
        	<td><bean:message key="lbl.lawyer"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="lawyerName" property="lawyerName" disabled="true" maxlength="50" value="${viewPropertyInCM[0].lawyerName}" />
		  </td>
		  <td><bean:message key="lbl.valuer"/></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="valuerName" property="valuerName" disabled="true" maxlength="50" value="${viewPropertyInCM[0].valuerName}" /></td>
        </tr>
        <!--Nishant Space end  -->
		<tr>
          <td><bean:message key="lbl.propertyOwner"/><font color="red">*</font></td>
 		<td>
			<logic:present name="dealAsset">
			<logic:notPresent name="dealLoanAsset">
			<html:hidden  property="propertyOwner" styleId="propertyOwner" value="${viewPropertyInCM[0].propertyOwner}"  />
   			<html:select property="propertyOwnerId" styleId="propertyOwnerId" size="5" multiple="multiple" style="width: 150" disabled="disabled" >  
   			<logic:present name="propertyOwnerList"> 			
       		<html:optionsCollection name="propertyOwnerList" value="propertyOwnerId" label="propertyOwnerDesc"/>   
       		</logic:present>   		
     		</html:select>
      		</logic:notPresent>
      		</logic:present>
      	     <logic:present name="loanAsset">
      	     <logic:notPresent name="dealLoanAsset">
      	     <html:hidden  property="propertyOwner" styleId="propertyOwner" value="${viewPropertyInCM[0].propertyOwner}"  />
   			<html:select property="propertyOwnerId" styleId="propertyOwnerId" size="5" multiple="multiple" style="width: 150" disabled="disabled" >  
   			<logic:present name="propertyOwnerList"> 			
       		<html:optionsCollection name="propertyOwnerList" value="propertyOwnerId" label="propertyOwnerDesc"/>   
       		</logic:present>   		
     		</html:select>
      		</logic:notPresent>
      	    </logic:present>
      	    <logic:present name="dealLoanAsset">
      	     <html:hidden  property="propertyOwner" styleId="propertyOwner" value="${viewPropertyInCM[0].propertyOwner}"  />
   			<html:select property="propertyOwnerId" styleId="propertyOwnerId" size="5" multiple="multiple" style="width: 150" disabled="disabled" >  
   			<logic:present name="propertyOwnerList"> 			
       		<html:optionsCollection name="propertyOwnerList" value="propertyOwnerId" label="propertyOwnerDesc"/>   
       		</logic:present>   		
     		</html:select>
      	    </logic:present>
     	</td>
		  <td><bean:message key="lbl.PerofConstruction"/><font color="red">*</font></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="propertyConstruct" style="text-align: right"  disabled="true" property="propertyConstruct" maxlength="3" value="${viewPropertyInCM[0].propertyConstruct}" onchange="setArea();" onkeyup="return checkRate('propertyConstruct');" onkeypress="return isNumberKey(event);"/></td>
		</tr>  
		<tr>
		  	<td><bean:message key="lbl.areaSQFT"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="propertyArea" property="propertyArea" disabled="true" maxlength="50" value="${viewPropertyInCM[0].propertyArea}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="calBuiltUpArea();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.buildUpArea"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="buildUpArea" property="buildUpArea" readonly="true" maxlength="50" value="${viewPropertyInCM[0].buildUpArea}" style="text-align: right"/></td> 
       </tr> 
       <tr>
       		<td><bean:message key="lbl.buildUpAreaSQMTR"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="buildUpAreaSQMTR" property="buildUpAreaSQMTR" readonly="true" maxlength="50" value="${viewPropertyInCM[0].buildUpAreaSQMTR}" style="text-align: right"/></td> 
         	<td><bean:message key="lbl.superBuildUpArea" /></td>					
         	<td>        
            <html:text property="superBuildUpArea" styleId="superBuildUpArea" size="20" styleClass="text"  disabled="true"  value="${viewPropertyInCM[0].superBuildUpArea}" onkeypress="return numbersonly(event,id,7);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);" ></html:text>
         	 </td>
       		<!--Nishant Space starts  -->
       		
       		<!--Nishant Space end  -->
       </tr>
       <tr>
      
           <td><bean:message key="lbl.carpetArea" /></td>					
            <td> 
            
            		<html:text property="carpetArea" styleId="carpetArea" size="20" styleClass="text" readonly="true" value="${viewPropertyInCM[0].carpetArea}" onkeypress="return numbersonly(event,id,7);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);" ></html:text>
          </td>
          <td><bean:message key="lbl.totalArea"/></td>
         <td nowrap="nowrap"><html:text styleClass="text" styleId="totalArea" property="totalArea" disabled="true" maxlength="7" value="${viewPropertyInCM[0].totalArea}" style="text-align: right" onkeypress="return numbersonly(event,id,7);"   onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);"/></td>
       </tr>
       <tr>
        <td style="width:23%"><bean:message key="lbl.propDirection"/></td>
           <td style="width:26%">
           <html:select property="propertyDirection" styleId="propertyDirection" styleClass="text" disabled="true" value="${viewPropertyInCM[0].propertyDirection}">
             <html:option value="N">North</html:option>
             <html:option value="E">East</html:option>
             <html:option value="W">West</html:option>      
             <html:option value="S">South</html:option>  
             <html:option value="NE">North-East</html:option>  
             <html:option value="NW">North-West</html:option>  
             <html:option value="SW">South-West</html:option>  
             <html:option value="SE">South-East</html:option>       
           </html:select></td>
       </tr>
       <tr>
		  	<td><bean:message key="lbl.propertyValue"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue"  disabled="true" maxlength="15" value="${viewPropertyInCM[0].assetsCollateralValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);txnLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap">
         		<html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" readonly="true" maxlength="15" value="" style="text-align: right"/>
         		<html:hidden property="loanAmt" styleId="loanAmt" value="${loanAmount}"/>
         	</td> 
       </tr>
        <tr>
		  	<td><bean:message key="lbl.technicalValuation1"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="techValuation" property="techValuation" disabled="true" maxlength="15" value="${viewPropertyInCM[0].techValuation}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="tvLTV" property="tvLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
       <tr>
           
           <td><bean:message key="lbl.technicalValuation2"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="technicalValuation1" property="technicalValuation1" disabled="true"  maxlength="15" value="${viewPropertyInCM[0].technicalValuation1}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc1();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="LTV1" property="LTV1" readonly="true" maxlength="15"  value="" style="text-align: right"/></td> 
           
           </tr>
           
           <tr>
        	<td><bean:message key="lbl.lawyer2"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="lawyerName2" property="lawyerName2"  maxlength="50" value="${fetchCollateralDetails[0].lawyerName2}" readonly="false" /></td>
		  <td><bean:message key="lbl.valuer2"/></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="valuerName2" property="valuerName2" maxlength="50" value="${fetchCollateralDetails[0].valuerName2}"  readonly="false" /></td>
        </tr> 
           
           <tr>
            <td><bean:message key="lbl.technicalValuation3" /></td>					
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="technicalValuation2" property="technicalValuation2" disabled="true"  maxlength="15" value="${viewPropertyInCM[0].technicalValuation2}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc2();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="LTV2" property="LTV2" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
           </tr>  
           
            <tr>
        	<td><bean:message key="lbl.lawyer3"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="lawyerName3" property="lawyerName3"  maxlength="50" value="${fetchCollateralDetails[0].lawyerName3}" readonly="false" /></td>
		  <td><bean:message key="lbl.valuer3"/></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="valuerName3" property="valuerName3" maxlength="50" value="${fetchCollateralDetails[0].valuerName3}"  readonly="false" /></td>
        </tr> 
 <tr>
        	<td><bean:message key="lbl.valuationMethod" /></td>
        	<td>
		 	<html:text styleClass="text" property="valuationMethod" styleId="valuationMethod" maxlength="100"  tabindex="-1" readonly="true" value="${viewPropertyInCM[0].valuationMethod}"/>
  			<html:hidden  property="valuationMethodId" styleId="valuationMethodId" value="${viewPropertyInCM[0].valuationMethodId}" />     													
  		   
        	</td>
        	<td><bean:message key="lbl.valuationAmount"/></td>
        	<td><html:text styleClass="text" property="valuationAmount" styleId="valuationAmount" maxlength="100"  tabindex="-1" readonly="true" value="${viewPropertyInCM[0].valuationAmount}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calAssetsCollateralValue();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/>
        	</td>
        </tr>  
        <tr>
		  	<td><bean:message key="lbl.docValue"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="docValue" property="docValue" disabled="true" maxlength="15" value="${viewPropertyInCM[0].docValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);docLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="docLTV" property="docLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
        <tr>
		  	<td><bean:message key="lbl.addConstruction"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="addConstruction" property="addConstruction" disabled="true" maxlength="15" value="${viewPropertyInCM[0].addConstruction}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="acLTV" property="acLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
       
       <tr>
		  	<td><bean:message key="lbl.amountFunded"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="amountFunded" disabled="true" property="amountFunded" maxlength="15" value="${viewPropertyInCM[0].amountFunded}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.totalAmountFunded"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="totalAmountFunded" disabled="true" property="totalAmountFunded" maxlength="15" value="${viewPropertyInCM[0].totalAmountFunded}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
       </tr>
           
           <tr>         
         	<td>
		 		<bean:message key="address.addr.one"/><font color="red">*</font>
		 	</td>
           <td nowrap="nowrap">
           		<html:text styleClass="text" styleId="propertyAddress" property="propertyAddress" disabled="true" maxlength="50" value="${viewPropertyInCM[0].propertyAddress}" onchange="return upperMe('propertyAddress');"/>
			</td>	
			<td><bean:message key="address.addr.two" /></td>
			<td>
				<html:text property="addr2" styleClass="text" styleId="addr2" maxlength="50" disabled="true" value="${viewPropertyInCM[0].addr2}" onchange="return upperMe('addr2');"/>
			</td>	
		</tr>
		<tr>
			<td><bean:message key="address.addr.three" /></td>
			<td>
				<html:text property="addr3" styleClass="text" styleId="addr3" disabled="true" maxlength="50" value="${viewPropertyInCM[0].addr3}" onchange="return upperMe('addr3');"/>
			</td>
			<td><bean:message key="lbl.villageLandmark"/></td>
				<td nowrap="nowrap"><html:text styleClass="text" styleId="villageLandmark" disabled="true" property="villageLandmark"  maxlength="50" value="${viewPropertyInCM[0].villageLandmark}" />
			</td>
		</tr>
		<tr>
			<td><bean:message key="address.country" /><font color="red">*</font></td>
           	<td>
          			<input type="hidden" name="hcommon" id="hcommon" />
          			<html:text property="country" styleId="country" disabled="true"  maxlength="20" value="${viewPropertyInCM[0].country}" styleClass="text" tabindex="-1" readonly="true"/>
    				<html:hidden property="txtCountryCode" styleId="txtCountryCode" value="${viewPropertyInCM[0].txtCountryCode}" styleClass="text" />
    				
			</td>
			<td><bean:message key="address.state" /><font color="red">*</font></td>
            <td>
                <div id="statedetail">
                	<html:text property="state" styleId="state" disabled="true" styleClass="text" size="20" value="${viewPropertyInCM[0].state}" readonly="true" tabindex="-1"></html:text>
   					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${viewPropertyInCM[0].txtStateCode}"></html:hidden>
    				
	          	</div>
			</td>
		</tr>
		<tr>
			<td><bean:message key="address.dist" /><font color="red">*</font></td>
          	<td>
          		<div id="cityID">
          			<html:text property="dist" styleId="dist" size="20"  disabled="true" styleClass="text" readonly="true" value="${viewPropertyInCM[0].dist}" tabindex="-1"></html:text>
          			<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${viewPropertyInCM[0].txtDistCode}"></html:hidden>
   					
   				</div>
   			</td>
			<td><bean:message key="address.Tahsil" /></td>					
            <td> 
            	<div id="tahsildetail">
            		<html:text property="tahsil" styleId="tahsil" size="20" disabled="true" styleClass="text" readonly="true" value="${viewPropertyInCM[0].tahsil}" tabindex="-1"></html:text>
                	<html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value=""/>
 			   		
   				</div>
   			</td>	
		</tr>
		<tr>
			<td><bean:message key="address.pincode" /><font color="red">*</font></td>
			<td><html:text maxlength="6" property="pincode" styleClass="text" disabled="true" styleId="pincode" style="text-align: right" onkeypress="return isNumberKey(event);" value="${viewPropertyInCM[0].pincode}"/></td>
			<logic:present name="fetchCollateralDetails">
 <logic:iterate name="fetchCollateralDetails" id="subList">
 <logic:equal name="subList" property="colltype2" value="ASSET">
 	<td><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" styleId="assetNature" disabled="true" styleClass="text" value="${viewPropertyInCM[0].assetNature}">
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>
 </logic:equal>
  </logic:iterate>
 </logic:present>
 <logic:notPresent name="fetchCollateralDetails">
<logic:present name="assetCollateralTypeAsset" >
 <td><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" disabled="true" styleId="assetNature" styleClass="text" >
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>              
</logic:present> 
 </logic:notPresent>  
 
		</tr>
		<tr>
 
 <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList"  property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" disabled="true" styleClass="text" value="${viewPropertyInCM[0].securityTypes}">
			      <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:equal>
		      </logic:iterate>
		      </logic:present>
		      <logic:notPresent name="fetchCollateralDetails">
		  	 <logic:present name="assetCollateralTypeAsset" >
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" disabled="true" styleClass="text" value="" >
			      <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:present>
		      </logic:notPresent> 	
           	<td><bean:message key="lbl.assetLevel"/><font color="red">*</font></td>
           		<td> 
		     		<html:select property="assetLevel" styleId="assetLevel" disabled="true" styleClass="text" value="${viewPropertyInCM[0].assetLevel}" >
		     		 <html:option value="">---Select----</html:option>
			     	<html:option value="Negative Profile">Negative Profile</html:option>
			     		<html:option value="Caution Profile">Caution Profile</html:option>
			     		<html:option value="Others">Others</html:option> 
			     		<html:option value="RURAL">RURAL</html:option>
			     		<html:option value="URBAN">URBAN</html:option> 
					</html:select>
		      </td>	
           </tr>
             <tr>
           <td><bean:message key="lbl.propertyStatus"/><font color="red">*</font></td>
   
		      <td>
		      
		      <html:select property="propertyStatus" styleId="propertyStatus" styleClass="text"  value="${viewPropertyInCM[0].propertyStatus}" disabled="true">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="propertyStatus">
				  <logic:notEmpty name="propertyStatus">
				  <html:optionsCollection name="propertyStatus" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>

		      </td>
		      
          <!--  </tr>
           
           <tr> -->
			<td><bean:message key="lbl.propertyTitle"/></td>
   
		      <td>
		      
	              
	              <html:select property="propertyTitle" styleId="propertyTitle" styleClass="text"  value="${viewPropertyInCM[0].propertyTitle}" disabled="true">
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="viewPropertyTitle">
				  <logic:notEmpty name="viewPropertyTitle">
				  <html:optionsCollection name="viewPropertyTitle" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>

	              					
									
									
		      </td>
           </tr> 
           
           <tr>
           <!-- Richa -->
          <td>
			  <bean:message key="lbl.mortgage" /><font color="red">*</font>
		</td>
	   <td>
			 <html:select property="mortgage" value="${viewPropertyInCM[0].mortgage}" styleClass="text" styleId="mortgage" disabled="true">
				<html:option value="">-- Select --
</html:option>
				<logic:present name="viewMortageList">
					<logic:notEmpty name="viewMortageList">
					<html:optionsCollection name="viewMortageList" label="name" value="id" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>

                         
	      </td>
	      <td><bean:message key="lbl.sibdi"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="sibdi" property="sibdi" maxlength="15" value="${viewPropertyInCM[0].sibdi}" disabled="true" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
           </tr>   
           <tr>
		  	<logic:present name="assetCollateralType">
		  	<logic:equal value="ASSET" name="assetCollateralType">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" disabled="disabled" id="assetStandard"  /></td>
		  	</logic:equal>
		  	</logic:present>
		  	
		  	<logic:equal name="actype" value="ASSET">
		  	<logic:iterate name="fetchCollateralDetails" id="subList">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  disabled="disabled" checked="checked"/></td>
		  	</logic:equal>
		  	
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard"  disabled="disabled" id="assetStandard" /></td>
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</logic:equal>
		  	
		  	</tr>
		  <tr>
 		  <td> 
<!-- 		  <html:button property="button1" styleClass="topformbutton2" value="Save" onclick="return savePropertyDetails();"/> 	-->
 		
 		    </td>	
		  </tr>	
		 
		 <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>
		 

	</TABLE>	
	 
	
	</TD>
	</TR>
	</TABLE>

</FIELDSET>
 
</html:form>
</logic:present>

<%--          			Insertion in LOAN iNITIATION													 --%>
<logic:present name="loanInit">

<html:form action="/collateralPropertyProcessAction" styleId="PropertyForm" method="post">
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
<html:hidden property="assetsIdProperty" styleClass="text" styleId ="assetsIdProperty" value="${fetchCollateralDetails[0].assetsId}"/>
<html:hidden property="colltype1" styleClass="text" styleId ="p1" value="${fetchCollateralDetails[0].colltype1}${assetClass }"/>
<html:hidden property="colltype2" styleClass="text" styleId ="p2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }"/>
<html:hidden property="colltype3" styleClass="text" styleId ="p3" value="${fetchCollateralDetails[0].colltype3}${new }"/>
<FIELDSET>
<LEGEND><bean:message key="lbl.propertyDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>
 <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>        ,
        <tr>
          <td style="width:23%"><bean:message key="lbl.propertyDescription"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}" /></td>
          <td><bean:message key="lbl.propertyValue"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
         
          </tr>
        <tr>
         <td width="23%" style="width:23%"><bean:message key="lbl.propertySecurityMargin"/><font color="red">*</font></td>
         <td nowrap="nowrap"><html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('collateralSecurityMargin');" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/></td>
           <td style="width:23%"><bean:message key="lbl.propertyType"/><font color="red">*</font></td>
           <td style="width:26%">
           <html:select property="propertyType" styleId="propertyType" styleClass="text" value="${fetchCollateralDetails[0].propertyType}">
             <logic:present name="propertyType">
				<logic:notEmpty name="propertyType">
					<html:optionsCollection name="propertyType" label="propertyTypeDesc" value="propertyTypeCode" />
				</logic:notEmpty>
				</logic:present>
           </html:select>
           </td>
		  </tr>
       <tr>
          <td><bean:message key="lbl.propertyOwner"/><font color="red">*</font></td>
 		<td>
			<logic:present name="dealAsset">
			<html:hidden  property="propertyOwner" styleId="propertyOwner" value="${fetchCollateralDetails[0].propertyOwner}"  />
   			<html:select property="propertyOwnerId" styleId="propertyOwnerId" size="5" multiple="multiple" style="width: 150" >  
   			<logic:present name="propertyOwnerList"> 			
       		<html:optionsCollection name="propertyOwnerList" value="propertyOwnerId" label="propertyOwnerDesc"/>   
       		</logic:present>   		
     		</html:select>
      		<html:button property="stateButton" styleId="stateButton" onclick="return openMultiSelectLOVCommon(492,'PropertyForm','propertyOwnerId','','', '','','','propertyOwner');" value=" " styleClass="lovbutton"></html:button>
      		</logic:present>
      	     <logic:present name="loanAsset">
      	     <html:hidden  property="propertyOwner" styleId="propertyOwner" value="${fetchCollateralDetails[0].propertyOwner}"  />
   			<html:select property="propertyOwnerId" styleId="propertyOwnerId" size="5" multiple="multiple" style="width: 150" >  
   			<logic:present name="propertyOwnerList"> 			
       		<html:optionsCollection name="propertyOwnerList" value="propertyOwnerId" label="propertyOwnerDesc"/>   
       		</logic:present>   		
     		</html:select>
      		<html:button property="stateButton" styleId="stateButton" onclick="return openMultiSelectLOVCommon(493,'PropertyForm','propertyOwnerId','','', '','','','propertyOwner');" value=" " styleClass="lovbutton"></html:button>
      	    </logic:present>	
      		</td>
		  <td><bean:message key="lbl.PerofConstruction"/><font color="red">*</font></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="propertyConstruct" style="text-align: right" property="propertyConstruct" maxlength="3" value="${fetchCollateralDetails[0].propertyConstruct}" onchange="setArea();" onkeyup="return checkRate('propertyConstruct');" onkeypress="return isNumberKey(event);"/></td>
		</tr>  
		<tr>
		  	<td><bean:message key="lbl.areaSQFT"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="propertyArea" property="propertyArea" maxlength="50" value="${fetchCollateralDetails[0].propertyArea}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="calBuiltUpArea();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.buildUpArea"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="buildUpArea" property="buildUpArea"  maxlength="11" value="${fetchCollateralDetails[0].buildUpArea}" style="text-align: right" onchange="calAreaSqf();" onkeypress="return numbersonly(event,id,18);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
       </tr> 
       <tr>
       		<td><bean:message key="lbl.buildUpAreaSQMTR"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="buildUpAreaSQMTR" property="buildUpAreaSQMTR" readonly="true" maxlength="50" value="${fetchCollateralDetails[0].buildUpAreaSQMTR}" style="text-align: right"/></td> 
    		 <td><bean:message key="lbl.superBuildUpArea" /></td>					
         	<td>        
            <html:text property="superBuildUpArea" styleId="superBuildUpArea" size="20" styleClass="text"  value="${fetchCollateralDetails[0].superBuildUpArea}" onkeypress="return numbersonly(event,id,7);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);" ></html:text>
          	</td>  
       </tr>
       <tr>
       <td><bean:message key="lbl.carpetArea" /></td>					
            <td> 
            
            		<html:text property="carpetArea" styleId="carpetArea" size="20" styleClass="text"  value="${fetchCollateralDetails[0].carpetArea}" onkeypress="return numbersonly(event,id,7);" onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);" ></html:text>
          </td>
          <td><bean:message key="lbl.totalArea"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="totalArea" property="totalArea"  maxlength="7" value="${fetchCollateralDetails[0].totalArea}" style="text-align: right" onblur="formatNumber(this.value,id);" onkeypress="return numbersonly(event,id,7);"  onkeyup="checkNumber(this.value, event, 7,id);" onfocus="keyUpNumber(this.value, event, 7,id);"/></td> 
          </tr>
       <tr>
		  	<td><bean:message key="lbl.txnValue"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="txnValue" property="txnValue" maxlength="15" value="${fetchCollateralDetails[0].txnValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);txnLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap">
         		<html:text styleClass="text" styleId="txnLTV" property="LTV" readonly="true" maxlength="15" value="" style="text-align: right"/>
         		<html:hidden property="loanAmt" styleId="loanAmt" value="${loanAmount}"/>
         	</td> 
       </tr>
        <tr>
		  	<td><bean:message key="lbl.technicalValuation1"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="techValuation" property="techValuation" maxlength="15" value="${fetchCollateralDetails[0].techValuation}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="tvLTV" property="tvLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
       <tr>
           
           <td><bean:message key="lbl.technicalValuation2"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="technicalValuation1" property="technicalValuation1" maxlength="15" value="${fetchCollateralDetails[0].technicalValuation1}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc1();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="LTV1" property="LTV1" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
           
           </tr>
           
            <tr>
        	<td><bean:message key="lbl.lawyer2"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="lawyerName2" property="lawyerName2"  maxlength="50" value="${fetchCollateralDetails[0].lawyerName2}" readonly="false" /></td>
		  <td><bean:message key="lbl.valuer2"/></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="valuerName2" property="valuerName2" maxlength="50" value="${fetchCollateralDetails[0].valuerName2}"  readonly="false" /></td>
        </tr> 
           
           <tr>
            <td><bean:message key="lbl.technicalValuation3" /></td>					
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="technicalValuation2" property="technicalValuation2" maxlength="15" value="${fetchCollateralDetails[0].technicalValuation2}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);tvLtvPerc2();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="LTV2" property="LTV2" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
           </tr>  
           
            <tr>
        	<td><bean:message key="lbl.lawyer3"/></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="lawyerName3" property="lawyerName3"  maxlength="50" value="${fetchCollateralDetails[0].lawyerName3}" readonly="false" /></td>
		  <td><bean:message key="lbl.valuer3"/></td>
		     <td nowrap="nowrap"><html:text styleClass="text" styleId="valuerName3" property="valuerName3" maxlength="50" value="${fetchCollateralDetails[0].valuerName3}"  readonly="false" /></td>
        </tr> 
           
           <tr>
        	<td><bean:message key="lbl.valuationMethod" /></td>
        	<td>
		 	<html:text styleClass="text" property="valuationMethod" styleId="valuationMethod" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].valuationMethod}"/>
  			<html:hidden  property="valuationMethodId" styleId="valuationMethodId" value="${fetchCollateralDetails[0].valuationMethodId}" />     													
  		    <html:button property="valuationMethodButton" styleId="valuationMethodButton" onclick="closeLovCallonLov1(); openLOVCommon(494,'PropertyForm','valuationMethod','','','','','valuationMeth','valuationMethodId'); " value=" " styleClass="lovbutton"></html:button>
        	</td>
        	<td><bean:message key="lbl.valuationAmount"/></td>
        	<td><html:text styleClass="text" property="valuationAmount" styleId="valuationAmount" maxlength="100"  tabindex="-1" readonly="true" value="${fetchCollateralDetails[0].valuationAmount}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calAssetsCollateralValue();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/>
        	</td>
        </tr>      
        <tr>
		  	<td><bean:message key="lbl.docValue"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="docValue" property="docValue" maxlength="15" value="${fetchCollateralDetails[0].docValue}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);docLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="docLTV" property="docLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
        <tr>
		  	<td><bean:message key="lbl.addConstruction"/><font color="red">*</font></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="addConstruction" property="addConstruction" maxlength="15" value="${fetchCollateralDetails[0].addConstruction}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.LTV"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="acLTV" property="acLTV" readonly="true" maxlength="15" value="" style="text-align: right"/></td> 
       </tr>
           <tr>
		  	<td><bean:message key="lbl.amountFunded"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="amountFunded" property="amountFunded" maxlength="15" value="${fetchCollateralDetails[0].amountFunded}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
      		<td><bean:message key="lbl.totalAmountFunded"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="totalAmountFunded" property="totalAmountFunded" maxlength="15" value="${fetchCollateralDetails[0].totalAmountFunded}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);acLtvPerc();"onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td> 
       		</tr>
           <tr>         
         	<td>
		 		<bean:message key="address.addr.one"/><font color="red">*</font>
		 	</td>
           <td nowrap="nowrap">
           		<html:text styleClass="text" styleId="propertyAddress" property="propertyAddress" maxlength="50" value="${fetchCollateralDetails[0].propertyAddress}" onchange="return upperMe('propertyAddress');"/>
			</td>	
			<td><bean:message key="address.addr.two" /></td>
			<td>
				<html:text property="addr2" styleClass="text" styleId="addr2" maxlength="50" value="${fetchCollateralDetails[0].addr2}" onchange="return upperMe('addr2');"/>
			</td>	
		</tr>
		<tr>
			<td><bean:message key="address.addr.three" /></td>
			<td>
				<html:text property="addr3" styleClass="text" styleId="addr3" maxlength="50" value="${fetchCollateralDetails[0].addr3}" onchange="return upperMe('addr3');"/>
			</td>
			<td><bean:message key="lbl.villageLandmark"/></td>
				<td nowrap="nowrap"><html:text styleClass="text" styleId="villageLandmark" property="villageLandmark"  maxlength="50" value="${fetchCollateralDetails[0].villageLandmark}" />
			</td>
		</tr>
		<tr>
			<td><bean:message key="address.country" /><font color="red">*</font></td>
           	<td>
          			<input type="hidden" name="hcommon" id="hcommon" />
          			<html:text property="country" styleId="country"  maxlength="20" value="${fetchCollateralDetails[0].country}" styleClass="text" tabindex="-1" readonly="true"/>
    				<html:hidden property="txtCountryCode" styleId="txtCountryCode" value="${fetchCollateralDetails[0].txtCountryCode}" styleClass="text" />
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    				<html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(4,'PropertyForm','country','','','','','clearCountryLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
			</td>
			<td><bean:message key="address.state" /><font color="red">*</font></td>
            <td>
                <div id="statedetail">
                	<html:text property="state" styleId="state" styleClass="text" size="20" value="${fetchCollateralDetails[0].state}" readonly="true" tabindex="-1"></html:text>
   					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${fetchCollateralDetails[0].txtStateCode}"></html:hidden>
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton" onclick="closeLovCallonLov('country');openLOVCommon(5,'PropertyForm','state','country','txtStateCode', 'txtCountryCode','Please select country first','clearStateLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
	          	</div>
			</td>
		</tr>
		<tr>
			<td><bean:message key="address.dist" /><font color="red">*</font></td>
          	<td>
          		<div id="cityID">
          			<html:text property="dist" styleId="dist" size="20" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].dist}" tabindex="-1"></html:text>
          			<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${fetchCollateralDetails[0].txtDistCode}"></html:hidden>
   					<html:button property="distButton" styleId="districtButton" onclick="closeLovCallonLov('state');openLOVCommon(20,'PropertyForm','dist','state','txtDistCode', 'txtStateCode','Please select state first','clearTahsilLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   				</div>
   			</td>
			<td><bean:message key="address.Tahsil" /></td>					
            <td> 
            	<div id="tahsildetail">
            		<html:text property="tahsil" styleId="tahsil" size="20" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].tahsil}" tabindex="-1"></html:text>
                	<html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value=""/>
 			   		<html:button property="tahsilButton" styleId="tahsilButton" onclick="closeLovCallonLov('dist');openLOVCommon(388,'PropertyForm','tahsil','dist','txnTahsilHID', 'txtDistCode','Please select District first','','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   				</div>
   			</td>	
		</tr>
		<tr>
			<td><bean:message key="address.pincode" /><font color="red">*</font></td>
			<td><html:text maxlength="6" property="pincode" styleClass="text" styleId="pincode" style="text-align: right" onkeypress="return isNumberKey(event);" value="${fetchCollateralDetails[0].pincode}"/></td>
			<logic:present name="fetchCollateralDetails">
 <logic:iterate name="fetchCollateralDetails" id="subList">
 <logic:equal name="subList" property="colltype2" value="ASSET">
 	<td><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" styleId="assetNature" styleClass="text" value="${fetchCollateralDetails[0].assetNature}">
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>
 </logic:equal>
  </logic:iterate>
 </logic:present>
 <logic:notPresent name="fetchCollateralDetails">
<logic:present name="assetCollateralTypeAsset" >
 <td><bean:message key="lbl.assetNature"/></td>
          <td>
          <html:select property="assetNature" styleId="assetNature" styleClass="text" >
            <html:option value="N">NEW</html:option>
            <html:option value="O">OLD</html:option> 
          </html:select></td>              
</logic:present> 
 </logic:notPresent>  
 
		</tr>
		<tr>
 
 <logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" value="${fetchCollateralDetails[0].securityTypes}">
			      <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:equal>
		      </logic:iterate>
		      </logic:present>
		      <logic:notPresent name="fetchCollateralDetails">
		  	 <logic:present name="assetCollateralTypeAsset" >
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" value="" >
			      <logic:present name="securityType">
				<logic:notEmpty name="securityType">
					<html:optionsCollection name="securityType" label="securityDesc" value="securityCode" />
				</logic:notEmpty>
				</logic:present>
			 </html:select>
		      </td>
		      </logic:present>
		      </logic:notPresent> 	
           	<td><bean:message key="lbl.assetLevel"/><font color="red">*</font></td>
           		<td> 
		     		<html:select property="assetLevel" styleId="assetLevel" styleClass="text" value="${fetchCollateralDetails[0].assetLevel}" >
			     		<html:option value="">---Select----</html:option>
			     		<html:option value="Negative Profile">Negative Profile</html:option>
			     		<html:option value="Caution Profile">Caution Profile</html:option>
			     		<html:option value="Others">Others</html:option>  
					</html:select>
		      </td>	
           </tr>
             <tr>
           <td><bean:message key="lbl.propertyStatus"/><font color="red">*</font></td>
   
		      <td>
		      
		      <html:select property="propertyStatus" styleId="propertyStatus" styleClass="text"  value="${fetchCollateralDetails[0].propertyStatus}" >
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="propertyStatus">
				  <logic:notEmpty name="propertyStatus">
				  <html:optionsCollection name="propertyStatus" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>

		      </td>
		      
          <!--  </tr>
           
           <tr> -->
			<td><bean:message key="lbl.propertyTitle"/></td>
   
		      <td>
		      
	              
	              <html:select property="propertyTitle" styleId="propertyTitle" styleClass="text"  value="${fetchCollateralDetails[0].propertyTitle}" >
				  <option value="">--<bean:message key="lbl.select" />--</option>
				  <logic:present name="propertyTitle">
				  <logic:notEmpty name="propertyTitle">
				  <html:optionsCollection name="propertyTitle" label="name" value="id" />
				  </logic:notEmpty>
				  </logic:present>
				  </html:select>

	              					
									
									
		      </td>
           </tr>
           <tr>
           <!-- Richa -->
          <td>
			  <bean:message key="lbl.mortgage" /><font color="red">*</font>
		</td>
	   <td>
			 <html:select property="mortgage" value="${fetchCollateralDetails[0].mortgage}" styleClass="text" styleId="mortgage">
				<html:option value="">-- Select --
</html:option>
				<logic:present name="mortageList">
					<logic:notEmpty name="mortageList">
					<html:optionsCollection name="mortageList" label="name" value="id" />
					</logic:notEmpty>		
				</logic:present>
				</html:select>

                         
	      </td>
	      <td><bean:message key="lbl.sibdi"/></td>
         	<td nowrap="nowrap"><html:text styleClass="text" styleId="sibdi" property="sibdi" maxlength="15" value="${fetchCollateralDetails[0].sibdi}"  style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
           </tr>   
           
           

           
           <tr>
		  	<logic:present name="assetCollateralType">
		  	<logic:equal value="ASSET" name="assetCollateralType">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  /></td>
		  	</logic:equal>
		  	</logic:present>
		  	
		  	<logic:equal name="actype" value="ASSET">
		  	<logic:iterate name="fetchCollateralDetails" id="subList">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  checked="checked"/></td>
		  	</logic:equal>
		  	
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" /></td>
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</logic:equal>
		  	
		  	</tr>
		 		  <tr>
 		  <td> 
 		  <button type="button" name="button1" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return savePropertyDetails();"><bean:message key="button.save" /></button>	
 		
 		    </td>	
		  </tr>	
		 
		 <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>
		 

	</TABLE>	
	 
	
	</TD>
	</TR>
	</TABLE>

</FIELDSET> 
</html:form>
</logic:present>
</div>
</body>