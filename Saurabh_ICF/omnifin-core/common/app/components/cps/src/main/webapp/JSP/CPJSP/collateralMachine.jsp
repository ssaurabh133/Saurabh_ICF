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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/assetCollateral.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>
	
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

<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('MachineForm').assetsCollateralDesc.focus();init_fields()">
<div  align="center" class="opacity" style="display:none" id="processingImage"></div>
		<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
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
 <input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
  <input type="hidden" name="machinAddress" id="machinAddress" value="${machinAddress}" />

<div id=centercolumn>
<div id=revisedcontainer>
<%--          			Insertion in deal capturing														 --%>
<logic:present name="DealCap">

<html:form action="/collateralMachineryProcessAction" styleId="MachineForm" method="post" > 

<html:hidden property="businessdate" styleId="businessdate" value="${userobject.businessdate }" />
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
	<html:hidden property="assetsIdMachine" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdMachine"/>
	<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="ass"/>
	<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="ass1"/>
	<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="ass2"/>
<FIELDSET>
<LEGEND><bean:message key="lbl.machineryDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>

 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>   
   
        <TR>
          <TD><bean:message key="lbl.machineryDescription"/><font color="red">*</font></TD>
          <td><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}"/></td>
          
          <TD><bean:message key="lbl.machineryCost"/><font color="red">*</font></TD>
          <td><html:text styleClass="text" styleId="assetsCost" property="assetsCost"  value="${fetchCollateralDetails[0].assetsCost}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calculateMacValue();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
         </tr>
         <tr>
          <TD><bean:message key="lbl.discount"/></TD>
          <td><html:text styleClass="text" styleId="assetsDiscount" property="assetsDiscount" value="${fetchCollateralDetails[0].assetsDiscount}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calculateMacValue();" onkeyup="checkNumber(this.value, event,18,id);" onfocus="keyUpNumber(this.value, event,18,id);"/></td>
          
          <TD><bean:message key="lbl.machineryValue"/><font color="red">*</font></TD>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);discount();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
         </TR>
        <TR>
          <TD><bean:message key="lbl.machineMargin"/></TD>
          <TD noWrap><html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" style="text-align: right" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('collateralSecurityMargin');calculateMacValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </TD>
           
          <TD><bean:message key="lbl.machineMake"/><font color="red">*</font></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineMake" property="machineMake" maxlength="50"  value="${fetchCollateralDetails[0].machineMake}"/> </TD>
		  
        </TR>
        <TR>
		<td width="23%" style="width:23%"><bean:message key="lbl.machineModel"/><font color="red">*</font></td>
		<td nowrap="nowrap"><html:text styleClass="text" styleId="machineModel" property="machineModel" maxlength="50" value="${fetchCollateralDetails[0].machineModel}"/></td>
          <td><bean:message key="lbl.machineType"/></td>
          <td>
          <html:select property="machineType" styleId="machineType" styleClass="text" value="${fetchCollateralDetails[0].machineType}">
            <html:option value="L">Local</html:option>
            <html:option value="I">Imported</html:option> 
          </html:select></td>
		 </TR>
		  <TR>
		   <TD><bean:message key="lbl.machineryOwner"/></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineOwner" property="machineOwner" maxlength="50" value="${fetchCollateralDetails[0].machineOwner}"/> </TD>
		    <TD><bean:message key="lbl.yearofManufacture"/></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineYearOfManufact" property="machineYearOfManufact" value="${fetchCollateralDetails[0].machineYearOfManufact}" onchange="return checkDate('machineYearOfManufact');"/> </TD>
		  </TR>
		  
		  
		  <tr>
		  
		  		 <TD><bean:message key="lbl.identificationNumber"/></TD>
                <TD noWrap><html:text styleClass="text" styleId="machineIdNo" property="machineIdNo" maxlength="20" value="${fetchCollateralDetails[0].machineIdNo}"/></TD>
		 	  
	
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
			          <html:select property="assetNature" styleId="assetNature" styleClass="text"  value="" >
			            <html:option value="N">NEW</html:option>
			            <html:option value="O">OLD</html:option> 
			          </html:select></td>
			                
			</logic:present> 
 </logic:notPresent>   			  </tr>
 
 <tr>
		    <td><bean:message key="lbl.manufact"/><font color="red">*</font></td>
            <td nowrap="nowrap">
           	<html:text property="assetManufact" styleClass="text" styleId="assetManufact"  readonly="true" value="${fetchCollateralDetails[0].assetManufactDesc}" tabindex="-1"/>
			<input type="hidden" name="lbxmachineManufact" id="lbxmachineManufact" value="${fetchCollateralDetails[0].lbxmachineManufact}" />
			<html:button property="assetManufactButton" styleId="assetManufactButton" onclick="clearSupplierLovChild();openLOVCommon(66,'MachineForm','assetManufact','','', '','','','assetManufact')" value=" " styleClass="lovbutton"></html:button>
		   <%--<img onClick="openLOVCommon(66,'MachineForm','assetManufact','','', '','','','assetManufactDesc')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>  	  
        	
            </td>
<!--              <td><bean:message key="lbl.manufactDesc"/><font color="red">*</font></td>-->
<!--          <td nowrap="nowrap">-->
<!--               -->
<!--        	<html:text property="assetManufactDesc" styleClass="text" styleId="assetManufactDesc"  readonly="true" value="${fetchCollateralDetails[0].assetManufactDesc}" />-->
<!--        	-->
<!--        	-->
<!--          </td>-->
<!--		    </tr>-->
<!--		  -->
<!--		  <tr>-->
		  
          <td><bean:message key="lbl.supplier"/><font color="red">*</font></td>
          <td nowrap="nowrap">
       
          
          	<html:text property="machineSupplier" styleClass="text" styleId="machineSupplier"  readonly="true" value="${fetchCollateralDetails[0].supplierDesc}" tabindex="-1"/>
			<input type="hidden" name="lbxmachineSupplier" id="lbxmachineSupplier" value="${fetchCollateralDetails[0].lbxmachineSupplier}" />
			<html:hidden property="empanelledStatus" styleClass="text" styleId="empanelledStatus" value=""/>
			<html:button property="assetManufactButton" styleId="assetManufactButton" onclick="openLOVCommon(412,'MachineForm','machineSupplier','assetManufact','', 'lbxmachineManufact','Select Manufacturer LOV','','machineSupplier','empanelledStatus')" value=" " styleClass="lovbutton"></html:button>
		   <%--<img onClick="openLOVCommon(65,'MachineForm','machineSupplier','','', '','','','supplierDesc')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>  	  
        	
          </td>
          
<!--           <td><bean:message key="lbl.supplierDesc"/><font color="red">*</font></td>-->
<!--          <td nowrap="nowrap">-->
<!--           -->
<!--        	   <html:text property="supplierDesc" styleClass="text" styleId="supplierDesc"  readonly="true" value="${fetchCollateralDetails[0].supplierDesc}" />-->
<!--        -->
<!--          </td>-->
          
		    </tr>
		    
		  	<tr>
		  	<td><bean:message key="lbl.invoiceDate"/></td>
		  	<td nowrap="nowrap"><html:text property="invoiceDate" styleClass="text" styleId="invoiceDate" value="${fetchCollateralDetails[0].invoiceDate}" onchange="checkDate('invoiceDate');checkDateLessThanBusDate();" /></td>
		  	 
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
		  	</tr>
		  	<tr>         
         		<td>
         			<bean:message key="address.addr.one"/>
         			<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>			 
         		</td>
           		<td><html:text styleClass="text" styleId="propertyAddress" property="propertyAddress" maxlength="50" value="${fetchCollateralDetails[0].propertyAddress}" onchange="return upperMe('propertyAddress');"/></td>	
				<td><bean:message key="address.addr.two" /></td>
				<td><html:text property="addr2" styleClass="text" styleId="addr2" maxlength="50" value="${fetchCollateralDetails[0].addr2}" onchange="return upperMe('addr2');"/></td>	
			</tr>
			<tr>
				<td><bean:message key="address.addr.three" /></td>
				<td><html:text property="addr3" styleClass="text" styleId="addr3" maxlength="50" value="${fetchCollateralDetails[0].addr3}" onchange="return upperMe('addr3');"/></td>
				<td>
					<bean:message key="address.country" />
					<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>
				</td>
           		<td>
          			<input type="hidden" name="hcommon" id="hcommon" />
          			<html:text property="country" styleId="country"  maxlength="20" value="${fetchCollateralDetails[0].country}" styleClass="text" tabindex="-1" readonly="true"/>
    				<html:hidden property="txtCountryCode" styleId="txtCountryCode" value="${fetchCollateralDetails[0].txtCountryCode}" styleClass="text" />
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    				<html:button property="countryButton" styleId="countryButton" onclick="closeLovCallonLov1();openLOVCommon(4,'MachineForm','country','','','','','clearCountryLovChild','hcommon');" value=" " styleClass="lovbutton"> </html:button>
				</td>
			</tr>
			<tr>				
				<td>
					<bean:message key="address.state" />
					<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>
				</td>
            	<td>
                	<html:text property="state" styleId="state" styleClass="text" size="20" value="${fetchCollateralDetails[0].state}" readonly="true" tabindex="-1"></html:text>
   					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${fetchCollateralDetails[0].txtStateCode}"></html:hidden>
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    				<html:button property="stateButton" styleId="stateButton" onclick="closeLovCallonLov('country');openLOVCommon(5,'MachineForm','state','country','', 'txtCountryCode','Please select country first','clearStateLovChild','txtStateCode');" value=" " styleClass="lovbutton"> </html:button>
	          	</td>
	          	<td>
	          		<bean:message key="address.dist" />
	          		<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>
	          	</td>
          		<td>
          			<html:text property="dist" styleId="dist" size="20" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].dist}" tabindex="-1"></html:text>
          			<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${fetchCollateralDetails[0].txtDistCode}"></html:hidden>
   					<html:button property="distButton" styleId="districtButton" onclick="closeLovCallonLov('state');openLOVCommon(20,'MachineForm','dist','state','', 'txtStateCode','Please select state first','clearTahsilLovChild','txtDistCode');" value=" " styleClass="lovbutton"> </html:button>
   				</td>
			</tr>
			<tr>				
				<td><bean:message key="address.Tahsil" /></td>					
            	<td> 
	            	<html:text property="tahsil" styleId="tahsil" size="20" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].tahsil}" tabindex="-1"></html:text>
                	<html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value=""/>
 			   		<html:button property="tahsilButton" styleId="tahsilButton" onclick="closeLovCallonLov('dist');openLOVCommon(388,'MachineForm','tahsil','dist','txnTahsilHID', 'txtDistCode','Please select District first','','hcommon');" value=" " styleClass="lovbutton"> </html:button>
   				</td>	
   				<td><bean:message key="address.pincode" /></td>
				<td><html:text maxlength="6" property="pincode" styleClass="text" styleId="pincode" style="text-align: right" onkeypress="return isNumberKey(event);" value="${fetchCollateralDetails[0].pincode}"/></td>
			</tr>
			<tr>
		  	<logic:present name="assetCollateralType">
		  	<logic:equal value="ASSET" name="assetCollateralType">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" /></td>
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
 		   <button type="button" name="machinebutton" class="topformbutton2" id="save" title="Alt+V" accesskey="V" onclick="return saveMachineDetails();"><bean:message key="button.save" /></button> </td>	
 		  
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
<html:form action="/collateralMachineryProcessAction" styleId="MachineForm" method="post" >   
	<html:hidden property="assetsIdMachine" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdMachine"/>
	<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}" styleClass="text" styleId ="ass"/>
	<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}" styleClass="text" styleId ="ass1"/>
	<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}" styleClass="text" styleId ="ass2"/>
<FIELDSET>
<LEGEND><bean:message key="lbl.machineryDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>

 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>   
   
        <TR>
          <TD><bean:message key="lbl.machineryDescription"/><font color="red">*</font></TD>
          <td><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${fetchCollateralDetails[0].assetsCollateralDesc}"/></td>
          
          <TD><bean:message key="lbl.machineryCost"/><font color="red">*</font></TD>
          <td><html:text styleClass="text" styleId="assetsCost" property="assetsCost" disabled="true" value="${fetchCollateralDetails[0].assetsCost}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calculateMacValue();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
         </tr>
         <tr>
          <TD><bean:message key="lbl.discount"/></TD>
          <td><html:text styleClass="text" styleId="assetsDiscount" property="assetsDiscount" disabled="true" value="${fetchCollateralDetails[0].assetsDiscount}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('assetsDiscount');calculateMacValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/></td>
          
          
          <TD><bean:message key="lbl.machineryValue"/><font color="red">*</font></TD>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" property="assetsCollateralValue" style="text-align: right" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
          </TR>
        <TR>
          <TD><bean:message key="lbl.machineMargin"/><font color="red">*</font></TD>
          <TD noWrap><html:text styleClass="text" styleId="collateralSecurityMargin" disabled="true" property="collateralSecurityMargin" style="text-align: right" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('collateralSecurityMargin');" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </TD>
           
          <TD><bean:message key="lbl.machineMake"/><font color="red">*</font></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineMake" property="machineMake" disabled="true" value="${fetchCollateralDetails[0].machineMake}"/> </TD>
		  
        </TR>
        <TR>
		<td width="23%" style="width:23%"><bean:message key="lbl.machineModel"/><font color="red">*</font></td>
		<td nowrap="nowrap"><html:text styleClass="text" styleId="machineModel" disabled="true" property="machineModel" value="${fetchCollateralDetails[0].machineModel}"/></td>
          <td><bean:message key="lbl.machineType"/></td>
          <td> <html:select property="machineType" styleId="machineType" styleClass="text" disabled="true" value="${fetchCollateralDetails[0].machineType}">
            <html:option value="L">Local</html:option>
            <html:option value="I">Imported</html:option> 
          </html:select></td>
		 </TR>
		  <TR>
		   <TD><bean:message key="lbl.machineryOwner"/></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineOwner" property="machineOwner" disabled="true" value="${fetchCollateralDetails[0].machineOwner}"/> </TD>
		    <TD><bean:message key="lbl.yearofManufacture"/></TD>
          <TD noWrap><html:text styleClass="text"  property="machineYearOfManufact" disabled="true" value="${fetchCollateralDetails[0].machineYearOfManufact}"/> </TD>
		  </TR>
		  <TR>
		   <TD><bean:message key="lbl.identificationNumber"/></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineIdNo" property="machineIdNo" disabled="true" value="${fetchCollateralDetails[0].machineIdNo}"/></TD>
         
          </TR>
          
          <tr>
          <td><bean:message key="lbl.manufact"/><font color="red">*</font></td>
            <td nowrap="nowrap">
           	<html:text property="assetManufact" styleClass="text" styleId="assetManufact"  readonly="true" value="${fetchCollateralDetails[0].assetManufactDesc}" tabindex="-1"/>          
          </td>
          <td><bean:message key="lbl.supplier"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="machineSupplier" disabled="true" property="machineSupplier" value="${fetchCollateralDetails[0].supplierDesc}"/></td>
		    </tr>
		
		  <TR>
		    <td><bean:message key="lbl.collateralCost"/><font color="red">*</font></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="machineCollateralCost" disabled="true" property="machineCollateralCost" value="${fetchCollateralDetails[0].machineCollateralCost}" onkeypress="return isNumberKey(event);"/></td>
		  <td><bean:message key="lbl.marginPercentage"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="machineMargin" disabled="true" property="machineMargin" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeypress="return checkRate('machineMargin');"/></td>
		  </TR>	
		  <tr>         
         		<td><bean:message key="address.addr.one"/>
         			<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>
         		</td>
           		<td><html:text styleClass="text" styleId="propertyAddress" readonly="true" property="propertyAddress" maxlength="50" value="${fetchCollateralDetails[0].propertyAddress}" onchange="return upperMe('propertyAddress');"/></td>	
				<td><bean:message key="address.addr.two" /></td>
				<td><html:text property="addr2" styleClass="text" styleId="addr2" readonly="true" maxlength="50" value="${fetchCollateralDetails[0].addr2}" onchange="return upperMe('addr2');"/></td>	
			</tr>
			<tr>
				<td><bean:message key="address.addr.three" /></td>
				<td><html:text property="addr3" styleClass="text" styleId="addr3" readonly="true" maxlength="50" value="${fetchCollateralDetails[0].addr3}" onchange="return upperMe('addr3');"/></td>
				<td>
					<bean:message key="address.country" />
					<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>
				</td>
           		<td>
          			<input type="hidden" name="hcommon" id="hcommon" />
          			<html:text property="country" styleId="country"  maxlength="20" value="${fetchCollateralDetails[0].country}" styleClass="text" tabindex="-1" readonly="true"/>
    				<html:hidden property="txtCountryCode" styleId="txtCountryCode" value="${fetchCollateralDetails[0].txtCountryCode}" styleClass="text" />
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    			</td>
			</tr>
			<tr>				
				<td>
					<bean:message key="address.state" />
					<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>
				</td>
            	<td>
                	<html:text property="state" styleId="state" styleClass="text" size="20" value="${fetchCollateralDetails[0].state}" readonly="true" tabindex="-1"></html:text>
   					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${fetchCollateralDetails[0].txtStateCode}"></html:hidden>
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    			</td>
	          	<td>
	          		<bean:message key="address.dist" />
	          		<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>
	          	</td>
          		<td>
          			<html:text property="dist" styleId="dist" size="20" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].dist}" tabindex="-1"></html:text>
          			<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${fetchCollateralDetails[0].txtDistCode}"></html:hidden>
   				</td>
			</tr>
			<tr>				
				<td><bean:message key="address.Tahsil" /></td>					
            	<td> 
	            	<html:text property="tahsil" styleId="tahsil" size="20" styleClass="text" readonly="true" value="${fetchCollateralDetails[0].tahsil}" tabindex="-1"></html:text>
                	<html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value=""/>
 			   	</td>	
   				<td><bean:message key="address.pincode" /></td>
				<td><html:text maxlength="6" property="pincode" readonly="true" styleClass="text" styleId="pincode" style="text-align: right" onkeypress="return isNumberKey(event);" value="${fetchCollateralDetails[0].pincode}"/></td>
			</tr>
		  <tr>
		  	<logic:present name="fetchCollateralDetails">
			 <logic:iterate name="fetchCollateralDetails" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" disabled="true" value="${fetchCollateralDetails[0].securityTypes}">
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
		      
		    <logic:equal name="actype" value="ASSET">
		  	<logic:iterate name="fetchCollateralDetails" id="subList">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  checked="checked" disabled="disabled"/></td>
		  	</logic:equal>
		  	
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" disabled="disabled"/></td>
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</logic:equal>
		  	
		  </tr>
		  
		   		
		  <tr>
 			
<!-- 		  <td>-->
<!-- 		   <html:button property="machinebutton" styleClass="topformbutton2" value="Save" onclick="return saveMachineDetails('MachineForm');"/> </td>	-->
 		  
		  </tr>		   		  
		  <html:hidden property="dealId" value="1" styleClass="text" styleId ="dealId"/>
		 
		         
	</TABLE>
	</TD>
	</TR>
	</TABLE>
	</FIELDSET>		
	

</html:form>
</logic:present>

<%--          			Loan Initiation Author part																	 --%>
<logic:present name="viewMachineInCM">
  
  <html:form action="/collateralMachineryProcessAction" styleId="MachineForm" method="post" >   
	
<FIELDSET>
<LEGEND><bean:message key="lbl.machineryDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>

 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>   
   
        <TR>
          <TD><bean:message key="lbl.machineryDescription"/><font color="red">*</font></TD>
          <td><html:text styleClass="text" styleId="assetsCollateralDesc" disabled="true" property="assetsCollateralDesc" value="${viewMachineInCM[0].assetsCollateralDesc}"/></td>
          
          <TD><bean:message key="lbl.machineryCost"/><font color="red">*</font></TD>
          <td><html:text styleClass="text" styleId="assetsCost" property="assetsCost" disabled="true" value="${viewMachineInCM[0].assetsCost}" style="text-align: right" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);calculateMacValue();" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
         </tr>
         <tr>
          <TD><bean:message key="lbl.discount"/></TD>
          <td><html:text styleClass="text" styleId="assetsDiscount" property="assetsDiscount" disabled="true" value="${viewMachineInCM[0].assetsDiscount}" style="text-align: right" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('assetsDiscount');calculateMacValue();" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/></td>
          
          
          <TD><bean:message key="lbl.machineryValue"/><font color="red">*</font></TD>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" disabled="true" property="assetsCollateralValue" style="text-align: right" value="${viewMachineInCM[0].assetsCollateralValue}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
          </TR>
        <TR>
          <TD><bean:message key="lbl.machineMargin"/></TD>
          <TD noWrap><html:text styleClass="text" styleId="collateralSecurityMargin" disabled="true" property="collateralSecurityMargin" style="text-align: right" value="${viewMachineInCM[0].collateralSecurityMargin}" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('collateralSecurityMargin');" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);"/> </TD>
           
          <TD><bean:message key="lbl.machineMake"/><font color="red">*</font></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineMake" property="machineMake" disabled="true" value="${viewMachineInCM[0].machineMake}"/> </TD>
		  
        </TR>
        <TR>
		<td width="23%" style="width:23%"><bean:message key="lbl.machineModel"/><font color="red">*</font></td>
		<td nowrap="nowrap"><html:text styleClass="text" styleId="machineModel" disabled="true" property="machineModel" value="${viewMachineInCM[0].machineModel}"/></td>
          <td><bean:message key="lbl.machineType"/></td>
          <td><html:select property="machineType" styleId="machineType" styleClass="text" disabled="true" value="${viewMachineInCM[0].machineType}">
            <option><bean:message key="lbl.local"/></option>
            <option><bean:message key="lbl.imported"/></option> 
          </html:select></td>
		 </TR>
		  <TR>
		   <TD><bean:message key="lbl.machineryOwner"/></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineOwner" property="machineOwner" disabled="true" value="${viewMachineInCM[0].machineOwner}"/> </TD>
		    <TD><bean:message key="lbl.yearofManufacture"/></TD>
          <TD noWrap><html:text styleClass="text"  property="machineYearOfManufact" disabled="true" value="${viewMachineInCM[0].machineYearOfManufact}"/> </TD>
		  </TR>
		  <TR>
		   <TD><bean:message key="lbl.identificationNumber"/></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineIdNo" property="machineIdNo" disabled="true" value="${viewMachineInCM[0].machineIdNo}"/></TD>
          </TR>
          <tr>
           <td><bean:message key="lbl.manufact"/><font color="red">*</font></td>
            <td nowrap="nowrap">
           	<html:text property="assetManufact" styleClass="text" styleId="assetManufact"  readonly="true" value="${viewMachineInCM[0].assetManufactDesc}" tabindex="-1"/>          
          </td>
          <td><bean:message key="lbl.supplier"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="machineSupplier" disabled="true" property="machineSupplier" value="${viewMachineInCM[0].supplierDesc}"/></td>		    		   
		    </tr>
		
		  <TR>
		    <td><bean:message key="lbl.collateralCost"/><font color="red">*</font></td>
		    <td nowrap="nowrap"><html:text styleClass="text" styleId="machineCollateralCost" disabled="true" property="machineCollateralCost" value="${viewMachineInCM[0].machineCollateralCost}" onkeypress="return isNumberKey(event);"/></td>
		  <td><bean:message key="lbl.marginPercentage"/><font color="red">*</font></td>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="machineMargin" disabled="true" property="machineMargin" value="${viewMachineInCM[0].collateralSecurityMargin}" onkeypress="return checkRate('machineMargin');"/></td>
		  </TR>	
		  <tr>         
         		<td><bean:message key="address.addr.one"/>
         			<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>
         		</td>
           		<td><html:text styleClass="text" styleId="propertyAddress" readonly="true" property="propertyAddress" maxlength="50" value="${viewMachineInCM[0].propertyAddress}" onchange="return upperMe('propertyAddress');"/></td>	
				<td><bean:message key="address.addr.two" /></td>
				<td><html:text property="addr2" styleClass="text" styleId="addr2" readonly="true" maxlength="50" value="${viewMachineInCM[0].addr2}" onchange="return upperMe('addr2');"/></td>	
			</tr>
			<tr>
				<td><bean:message key="address.addr.three" /></td>
				<td><html:text property="addr3" styleClass="text" styleId="addr3" readonly="true" maxlength="50" value="${viewMachineInCM[0].addr3}" onchange="return upperMe('addr3');"/></td>
				<td>
					<bean:message key="address.country" />
					<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>
				</td>
           		<td>
          			<input type="hidden" name="hcommon" id="hcommon" />
          			<html:text property="country" styleId="country"  maxlength="20" value="${viewMachineInCM[0].country}" styleClass="text" tabindex="-1" readonly="true"/>
    				<html:hidden property="txtCountryCode" styleId="txtCountryCode" value="${viewMachineInCM[0].txtCountryCode}" styleClass="text" />
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    			</td>
			</tr>
			<tr>				
				<td>
					<bean:message key="address.state" />
					<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>
				</td>
            	<td>
                	<html:text property="state" styleId="state" styleClass="text" size="20" value="${viewMachineInCM[0].state}" readonly="true" tabindex="-1"></html:text>
   					<html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${viewMachineInCM[0].txtStateCode}"></html:hidden>
    				<html:hidden property="tahsilDesc" styleId="tahsilDesc" value="" styleClass="text" />
    			</td>
	          	<td>
	          		<bean:message key="address.dist" />
	          		<logic:present name="machinAddress">
         			<logic:equal name="machinAddress" value="Y">
         			<font color="red">*</font>
         			</logic:equal>
         			</logic:present>
	          	</td>
          		<td>
          			<html:text property="dist" styleId="dist" size="20" styleClass="text" readonly="true" value="${viewMachineInCM[0].dist}" tabindex="-1"></html:text>
          			<html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${viewMachineInCM[0].txtDistCode}"></html:hidden>
   				</td>
			</tr>
			<tr>				
				<td><bean:message key="address.Tahsil" /></td>					
            	<td> 
	            	<html:text property="tahsil" styleId="tahsil" size="20" styleClass="text" readonly="true" value="${viewMachineInCM[0].tahsil}" tabindex="-1"></html:text>
                	<html:hidden property="txnTahsilHID" styleId="txnTahsilHID" styleClass="text" value=""/>
 			   	</td>	
   				<td><bean:message key="address.pincode" /></td>
				<td><html:text maxlength="6" property="pincode" readonly="true" styleClass="text" styleId="pincode" style="text-align: right" onkeypress="return isNumberKey(event);" value="${viewMachineInCM[0].pincode}"/></td>
			</tr>
		  <tr>
     	<logic:present name="viewMachineInCM">
			 <logic:iterate name="viewMachineInCM" id="subList">
			 <logic:equal name="subList" property="colltype2" value="ASSET">
		  	 <td><bean:message key="lbl.securityTypes"/></td>
		     <td> 
		     <html:select property="securityTypes" styleId="securityTypes" styleClass="text" disabled="true" value="${viewMachineInCM[0].securityTypes}">
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
		      
		     <logic:present name="viewMachineInCM">
		     <logic:equal value="ASSET" name="acType">
		  	<logic:iterate name="viewMachineInCM" id="subList">
		  	<logic:equal value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard"  checked="checked" disabled="disabled"/></td>
		  	</logic:equal>
		  	<logic:notEqual value="Y" name="subList" property="assetStandard">
		  	<td><bean:message key="lbl.standard"/></td>
		  	<td><input type="checkbox" name="assetStandard" id="assetStandard" disabled="disabled"/></td>
		  	</logic:notEqual>
		  	</logic:iterate>
		  	</logic:equal>
		  	</logic:present>
		
		  	
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
	
<html:form action="/collateralMachineryProcessAction" styleId="MachineForm" method="post" > 
<html:hidden property="cmStatus" styleId="cmStatus" value="${requestScope.cmStatus }" />
<html:hidden property="loanId" styleId="loanId" value="${requestScope.loanId }" />  
	<html:hidden property="assetsIdMachine" value="${fetchCollateralDetails[0].assetsId}" styleClass="text" styleId ="assetsIdMachine"/>
	<html:hidden property="colltype1" value="${fetchCollateralDetails[0].colltype1}${assetClass }" styleClass="text" styleId ="ass"/>
	<html:hidden property="colltype2" value="${fetchCollateralDetails[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="ass1"/>
	<html:hidden property="colltype3" value="${fetchCollateralDetails[0].colltype3}${new }" styleClass="text" styleId ="ass2"/>
<FIELDSET>
<LEGEND><bean:message key="lbl.machineryDetails"/></LEGEND>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
<TD>

 <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>   
   
        <TR>
          <TD><bean:message key="lbl.machineryDescription"/><font color="red">*</font></TD>
          <td><html:text styleClass="text" styleId="assetsCollateralDesc" property="assetsCollateralDesc" maxlength="100" value="${fetchCollateralDetails[0].assetsCollateralDesc}"/></td>
          <TD><bean:message key="lbl.machineryValue"/><font color="red">*</font></TD>
          <td nowrap="nowrap"><html:text styleClass="text" styleId="assetsCollateralValue" property="assetsCollateralValue" style="text-align: right" maxlength="22.4" value="${fetchCollateralDetails[0].assetsCollateralValue}" onkeypress="return numbersonly(event,id,18);" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" onfocus="keyUpNumber(this.value, event, 18,id);"/></td>
          </TR>
        <TR>
          <TD><bean:message key="lbl.machineMargin"/></TD>
          <TD noWrap><html:text styleClass="text" styleId="collateralSecurityMargin" property="collateralSecurityMargin" style="text-align: right" value="${fetchCollateralDetails[0].collateralSecurityMargin}" onkeypress="return numbersonly(event,id,3);" onblur="formatNumber(this.value,id);checkRate('collateralSecurityMargin');" onkeyup="checkNumber(this.value, event,3,id);" onfocus="keyUpNumber(this.value, event,3,id);" /> </TD>
           
          <TD><bean:message key="lbl.machineMake"/><font color="red">*</font></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineMake" property="machineMake" maxlength="50"  value="${fetchCollateralDetails[0].machineMake}"/> </TD>
		  
        </TR>
        <TR>
		<td width="23%" style="width:23%"><bean:message key="lbl.machineModel"/><font color="red">*</font></td>
		<td nowrap="nowrap"><html:text styleClass="text" styleId="machineModel" property="machineModel" maxlength="50" value="${fetchCollateralDetails[0].machineModel}"/></td>
          <td><bean:message key="lbl.machineType"/></td>
          <td>
          <html:select property="machineType" styleId="machineType" styleClass="text" value="${fetchCollateralDetails[0].machineType}">
            <html:option value="L">Local</html:option>
            <html:option value="I">Imported</html:option> 
          </html:select></td>
		 </TR>
		  <TR>
		   <TD><bean:message key="lbl.machineryOwner"/></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineOwner" property="machineOwner" maxlength="50" value="${fetchCollateralDetails[0].machineOwner}"/> </TD>
		    <TD><bean:message key="lbl.yearofManufacture"/></TD>
          <TD noWrap><html:text styleClass="text" styleId="machineYearOfManufact" property="machineYearOfManufact" value="${fetchCollateralDetails[0].machineYearOfManufact}" onchange="return checkDate('machineYearOfManufact');"/> </TD>
		  </TR>
		  
		  
		  <tr>
		  		 <TD><bean:message key="lbl.identificationNumber"/></TD>
                <TD noWrap><html:text styleClass="text" styleId="machineIdNo" property="machineIdNo" maxlength="20" value="${fetchCollateralDetails[0].machineIdNo}"/></TD>
		
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
		  
		  <TR>
		  
          <td><bean:message key="lbl.supplier"/><font color="red">*</font></td>
          <td nowrap="nowrap">
       
          
          	<html:text property="machineSupplier" styleClass="text" styleId="machineSupplier"  readonly="true" value="${fetchCollateralDetails[0].lbxmachineSupplier}" tabindex="-1"/>
			<input type="hidden" name="lbxmachineSupplier" id="lbxmachineSupplier" value="${fetchCollateralDetails[0].lbxmachineSupplier}" />
			<html:button property="machineSupplierbutton" styleId="machineSupplierbutton" onclick="openLOVCommon(65,'MachineForm','machineSupplier','','', '','','','supplierDesc')" value=" " styleClass="lovbutton"></html:button>
		 <%-- <img onClick="openLOVCommon(65,'MachineForm','machineSupplier','','', '','','','supplierDesc')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>   	  
        	
          </td>
          
           <td><bean:message key="lbl.supplierDesc"/><font color="red">*</font></td>
          <td nowrap="nowrap">
               
        	<html:text property="supplierDesc" styleClass="text" styleId="supplierDesc"  readonly="true" value="${fetchCollateralDetails[0].supplierDesc}" onfocus="statusSDesc();"/>
        	
          </td>
          
		    </TR>
		    <tr>
		    <td><bean:message key="lbl.manufact"/><font color="red">*</font></td>
            <td nowrap="nowrap">
           	<html:text property="assetManufact" styleClass="text" styleId="assetManufact"  readonly="true" value="${fetchCollateralDetails[0].lbxmachineManufact}" tabindex="-1" />
			<input type="hidden" name="lbxmachineManufact" id="lbxmachineManufact" value="${fetchCollateralDetails[0].lbxmachineManufact}" />
			<html:button property="assetManufactButton" styleId="assetManufactButton" onclick="openLOVCommon(66,'MachineForm','assetManufact','','', '','','','assetManufactDesc')" value=" " styleClass="lovbutton"></html:button>
		 <%-- <img onClick="openLOVCommon(66,'MachineForm','assetManufact','','', '','','','assetManufactDesc')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>   	  
        	
            </td>
              <td><bean:message key="lbl.manufactDesc"/><font color="red">*</font></td>
          <td nowrap="nowrap">
               
        	<html:text property="assetManufactDesc" styleClass="text" styleId="assetManufactDesc"  readonly="true" value="${fetchCollateralDetails[0].assetManufactDesc}" onfocus="statusMDesc();"/>
        	
          </td>
		    </tr>
		  		
		  <tr>
 			
 		  <td>
 		   <button type="button" name="machinebutton" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveMachineDetails();"><bean:message key="button.save" /></button> </td>	
 		  
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
</div>
</body>