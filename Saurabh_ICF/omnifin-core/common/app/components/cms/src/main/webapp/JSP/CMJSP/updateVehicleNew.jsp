<!--Author Name : Manish Shukla.-->
<!--Date of Creation : 12-sep-2013-->
<!--Purpose  : Information for New Update vehical-->
<!--Documentation : -->


<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
<title><bean:message key="a3s.noida" /></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" 	src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/updateAsset.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript">

 


function fntab()
{
   document.getElementById('updateform').vehicleChesisNo.focus();
   return true;
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

<body onload="enableAnchor();" >
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
<input type="hidden" name="saveflag" id="saveflag" value="N" />
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
<html:form styleId="updateform"  method="post"  action="/updateAssetSearch" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

<fieldset>
<legend><bean:message key="lbl.updateAssetDetails"/></legend>
	<table width="100%">
	
 	<html:hidden property="lbxLoanId" styleClass="text" styleId="lbxLoanId"   value="${requestScope.list[0].lbxLoanId}" />
  	<html:hidden property="assetId" styleClass="text" styleId="assetId"   value="${requestScope.list[0].assetId}" />
  	<input type="hidden" name="saveFirst" id='saveFirst' value="${requestScope.saveFirst}"/>
 <tr>
		<td><bean:message key="lbl.loanNo" /></td>
	<td>
		<html:text property="loanNo" styleId="loanNo" styleClass="text" readonly="true" tabindex="-1"/>
		<html:hidden  property="lbxLoanId" styleId="lbxLoanId"  />
		<html:button property="loanNoButton" styleId="loanNoButton" onclick="closeLovCallonLov1();openLOVCommon(2044,'updateform','loanNo','','lbxLoanId','','','','customerName','assetId');" value=" " styleClass="lovbutton"> </html:button>
																								
	
	</td> 
	
	<td ><bean:message key="lbl.customerName"></bean:message> </td>
	<td > 
	  <html:text property="customerName" styleId="customerName" styleClass="text" readonly="true" tabindex="-1"/>	
			
	</td>
	
	</tr>	
 
    <tr>
    
     <td ><bean:message key="lbl.vechicleDescription"></bean:message> </td>
	 <td><html:text property="assetsCollateralDesc" styleId="assetsCollateralDesc" styleClass="text" />
	
		<html:button property="assetButton" styleId="assetButton" onclick="openLOVCommon(2038,'updateform','assetId','loanNo','lbxAssetId', 'lbxLoanId','Please Select Loan No first','getValuesForUpdateAsset','assetsCollateralDesc')" value=" " styleClass="lovbutton"></html:button>
		<input type="hidden"  name="lbxAssetId" id="lbxAssetId" readonly="readonly"/>
		
	</td>
	
      <td><bean:message key="lbl.assetNature" /></td>
      <td><html:text property="assetNature" readonly="true" styleClass="text" styleId="assetNature" maxlength="10"  value="${requestScope.list[0].assetNature}" readonly="true"/></td>
  
     </tr>
     <tr>
       <td><bean:message key="lbl.vehicleMake" /></td>
	<td><html:text property="vehicleMake" styleClass="text" styleId="vehicleMake" maxlength="50"  value="${requestScope.list[0].vehicleMake}" readonly="true"/>
     </td>
     
      <td><bean:message key="lbl.vehicleModel" /></td>
	<td><html:text property="vehicleModel" styleClass="text" styleId="vehicleModel" maxlength="50"  value="${requestScope.list[0].vehicleModel}" readonly="true"/>
     </td>
     
     </tr>
     
      <tr>
       <td><bean:message key="lbl.manufact" /></td>
	<td><html:text property="assetManufact" styleClass="text" styleId="assetManufact" maxlength="50"  value="${requestScope.list[0].assetManufact}" readonly="true"/>
     </td>
     
      <td><bean:message key="lbl.supplier" /></td>
	<td><html:text property="machineSupplier" styleClass="text" styleId="machineSupplier" maxlength="50"  value="${requestScope.list[0].machineSupplier}" readonly="true"/>
     </td>
     
     </tr>
     
       <tr>
       <td><bean:message key="lbl.usesType" /></td>
	<td><html:text property="usageType" styleClass="text" styleId="usageType" maxlength="50"  value="${requestScope.list[0].usageType}" readonly="true"/>
     </td>
     
      <td><bean:message key="lbl.state" /></td>
	<td><html:text property="txtStateCode" styleClass="text" styleId="txtStateCode" maxlength="50"  value="${requestScope.list[0].txtStateCode}" readonly="true"/>
     </td>
     
     </tr>
          <tr>
       <td><bean:message key="lbl.vehicleSecurityMarginDF" /></td>
	<td><html:text property="collateralSecurityMarginDF" styleClass="text" styleId="collateralSecurityMarginDF" maxlength="50"  value="${requestScope.list[0].collateralSecurityMarginDF}" readonly="true"/>
     </td>
     
      <td><bean:message key="lbl.vehicleSecurityMargin" /></td>
	<td><html:text property="collateralSecurityMargin" styleClass="text" styleId="collateralSecurityMargin" maxlength="50"  value="${requestScope.list[0].collateralSecurityMargin}" readonly="true"/>
     </td>
     
     </tr>

         <tr>
       <td><bean:message key="lbl.loanAmount" /></td>
	<td><html:text property="loanAmount" styleClass="text" styleId="loanAmount" maxlength="50"  value="${requestScope.list[0].loanAmount}" readonly="true" />
     </td>
     
      <td><bean:message key="lbl.vehicleCost" /></td>
	<td><html:text property="vehicleCost" styleClass="text" styleId="vehicleCost" maxlength="50" value="${requestScope.list[0].vehicleCost}" readonly="true" />
     </td>
     
     </tr>
     
             <tr>
       <td><bean:message key="lbl.vehiclediscount" /></td>
	<td><html:text property="vehicleDiscount" styleClass="text" styleId="vehicleDiscount" maxlength="50"  value="${requestScope.list[0].vehicleDiscount}" readonly="true"/>
     </td>
     
      <td><bean:message key="lbl.vehicleValue" /></td>
	<td><html:text property="assetsCollateralValue" styleClass="text" styleId="assetsCollateralValue" maxlength="50" value="${requestScope.list[0].assetsCollateralValue}" readonly="true" />
     </td>
     
     </tr>
     
                <tr>
       <td><bean:message key="lbl.gridValue" /></td>
	<td><html:text property="gridValue" styleClass="text" styleId="gridValue" maxlength="50"  value="${requestScope.list[0].gridValue}" readonly="true"/>
     </td>
     
      <td><bean:message key="lbl.valuationCost" /></td>
	<td><html:text property="valuationCost" styleClass="text" styleId="valuationCost" maxlength="50"  value="${requestScope.list[0].valuationCost}" readonly="true"/>
     </td>
     
     </tr>
     
                   <tr>
       <td><bean:message key="lbl.securityTypes" /></td>
	<td><html:text property="securityTypes" styleClass="text" styleId="securityTypes" maxlength="50"  value="${requestScope.list[0].securityTypes}" readonly="true" />
     </td>
     
     <td><bean:message key="lbl.authorRemarks" /></td>
	<td><html:text property="authorRemarks" styleClass="text" styleId="authorRemarks" maxlength="50"  value="${requestScope.list[0].authorRemarks}" readonly="true" />
     </td>
     
     </tr>
     
     <tr>
      <td><bean:message key="lbl.standard" /></td>
      <logic:iterate name="list" id="subList" length="1">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		<td><input type="checkbox" name="assetStandard" id="assetStandard"  disabled="disabled" checked="checked"/></td>
   </logic:equal>
   	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" disabled="disabled" /></td>
		  	</logic:notEqual>
		  	</logic:iterate>
     
     </tr>

     
                  <tr>
       <td><bean:message key="lbl.chasisNumber" /></td>
	<td><html:text property="vehicleChesisNo" styleClass="text" styleId="vehicleChesisNo" maxlength="50"  onchange="saveAfterChange();" value="${requestScope.list[0].vehicleChesisNo}" />
     </td>
     
      <td><bean:message key="lbl.engineNumber" /></td>
	<td><html:text property="engineNumber" styleClass="text" styleId="engineNumber" maxlength="50"  onchange="saveAfterChange();" value="${requestScope.list[0].engineNumber}" />
     </td>
     
     </tr>

        <tr>
       <td><bean:message key="lbl.invoiceNumber" /></td>
	<td><html:text property="invoiceNumber" styleClass="text" styleId="invoiceNumber" maxlength="50" onchange="saveAfterChange();" value="${requestScope.list[0].invoiceNumber}" />
     </td>
     
      <td><bean:message key="lbl.invoiceDate" /></td>
	<td><html:text property="vehicleInvoiceDate" styleClass="text" styleId="vehicleInvoiceDate" maxlength="50"  value="${requestScope.list[0].vehicleInvoiceDate}"  onchange="checkDate('vehicleInvoiceDate');saveAfterChange();"/>
     </td>
     
     </tr>
     
           <tr>
        <td><bean:message key="lbl.rcReceived"/></td>
		  <td>
		  <html:select property="rcReceived" styleId="rcReceived" styleClass="text" onchange="saveAfterChange();rcDateMandatory()" value="${requestScope.list[0].rcReceived}">
	              <html:option  value="">---Select---</html:option>
	              <html:option  value="Y">YES</html:option>  
		          <html:option  value="N">NO</html:option>
	      </html:select>
	      </td>
     
      <td><bean:message key="lbl.rcReceivedDate" /></td>
	<td><html:text property="rcReceivedDate" styleClass="text" styleId="rcReceivedDate" maxlength="50"  value="${requestScope.list[0].rcReceivedDate}" onchange="checkDate('rcReceivedDate'); saveAfterChange(); rcDateMandatory()" readonly="true"/>
     </td>
     
     </tr>
     
               <tr>
       <td><bean:message key="lbl.registrationNumber" /></td>
	<td><html:text property="vehicleRegNo" styleClass="text" styleId="vehicleRegNo" maxlength="50"  onchange="saveAfterChange();" value="${requestScope.list[0].vehicleRegNo}" />
     </td>
     
      <td><bean:message key="lbl.registrationDate" /></td>
	<td><html:text property="vehicleRegDate" styleClass="text" styleId="vehicleRegDate" maxlength="50"  value="${requestScope.list[0].vehicleRegDate}" onchange="checkDate('vehicleRegDate'); saveAfterChange(); " readonly="true"/>
     </td>
     
     </tr>
                    <tr>
       <td><bean:message key="lbl.yearofManufacture" />(MM-YYYY)<font color="red">*</font></td>
	<td><html:text property="vehicleYearOfManufact" styleClass="text" styleId="vehicleYearOfManufact" maxlength="50"  value="${requestScope.list[0].vehicleYearOfManufact}" onchange="saveAfterChange();checkFormate(this.value,this.id);"  />
     </td>
     
      <td><bean:message key="lbl.vehicleOwner" /></td>
	<td><html:text property="vehicleOwner" styleClass="text" styleId="vehicleOwner" maxlength="50"  value="${requestScope.list[0].vehicleOwner}"  />
     </td>
     
     </tr>
     
       <tr>
       <td><bean:message key="lbl.Insurer" /></td>
	<td><html:text property="vehicleInsurer" styleClass="text" styleId="vehicleInsurer" maxlength="50" onchange="saveAfterChange();" value="${requestScope.list[0].vehicleInsurer}" />
     </td>
     
      <td><bean:message key="lbl.insuredDate" /></td>
	<td><html:text property="vehicleInsureDate" styleClass="text" styleId="vehicleInsureDate" maxlength="50"  value="${requestScope.list[0].vehicleInsureDate}" onchange="checkDate('vehicleInsureDate'); saveAfterChange();" readonly="true" />
     </td>
     
     </tr>


     
       <tr>
       <td><bean:message key="lbl.idv" /></td>
	<td><html:text property="idv" styleClass="text" styleId="idv" maxlength="22" onkeypress="return isNumberKey(event);" onchange="saveAfterChange();" value="${requestScope.list[0].idv}" />
     </td>
     
       	<td><bean:message key="lbl.invoiceAmount" /></td>
		<td><html:text property="invoiceAmount" styleClass="text" styleId="invoiceAmount" maxlength="22" onkeypress="return isNumberKey(event);" onchange="saveAfterChange();" value="${requestScope.list[0].invoiceAmount}" />
     </td>
    
     </tr>
      
     <tr>
        <td><bean:message key="lbl.permitReceived"/></td>
		  <td>
		  <html:select property="permitReceived" styleId="permitReceived" styleClass="text" onchange="receivedDateMandatory();saveAfterChange();" value="${requestScope.list[0].permitReceived}">
	              <html:option  value="">---Select---</html:option>
	              <html:option  value="Y">YES</html:option>  
		          <html:option  value="N">NO</html:option>
	      </html:select>
	      </td>
     
      <td><bean:message key="lbl.permitReceivedDate" /></td>
	  <td><html:text property="permitReceivedDate" styleClass="text" styleId="permitReceivedDate" maxlength="50"  value="${requestScope.list[0].permitReceivedDate}" onchange="checkDate('permitReceivedDate');receivedDateMandatory();saveAfterChange();" readonly="true"/>
     </td>
     
     </tr>
  </table>
    <table>
  <tr>
 		  <td><button type="button" name="Submit20" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return fnUpdateAsset('P');"><bean:message key="button.save" /></button>  </td>
 		 
 		    <td><button type="button" name="Forward" id="Forward" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return fnUpdateAsset('F');"><bean:message key="button.forward" /></button>  </td>
 		   
 		 
		  </tr>	

	</table>		


</fieldset>
  
           
	</html:form>		
		<logic:present name="sms">
<script type="text/javascript">

    
		if('<%=request.getAttribute("sms")%>'=='S')
	{
		//alert("");
		document.getElementById("saveflag").value='Y';		
		alert("<bean:message key="lbl.dataSave" />");
	
	}
	
	else if('<%=request.getAttribute("sms")%>'=='F')
	{
		alert("<bean:message key="msg.ForwardSuccessfully" />");
		location.href="updateAssetEdit.do?method=search";
	}
	else if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
		else if('<%=request.getAttribute("sms")%>'=='DR')
	{
		alert("<bean:message key="lbl.duplicateRegNo" />");
		   
	}
</script>
</logic:present>
  </body>
		</html:html>