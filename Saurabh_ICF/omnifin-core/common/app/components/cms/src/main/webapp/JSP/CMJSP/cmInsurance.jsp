<!--  
Author Name:- Amandeep
Date of Creation:- 23/01/2014
Purpose:-  The purpose of this page is to enter notes and follow up Remarks.
-->


<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="/JSP/sessioncheck.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 
	
		<link type="text/css" href="<%=request.getContextPath() %>/css/toolTip.css" rel="stylesheet" />
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.simpletip-1.3.1.js"></script>

	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->

		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmInsurance.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

		


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
<body oncontextmenu="return false" onload="insuranceServiceCalled();sumAssuredPercent();enableAnchor();checkChanges('cmInsuranceForm');document.getElementById('cmInsuranceForm').insuranceProvider.focus();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="sms" id="sms" value="${sms}" />
	<input type="hidden" name="sms1" id="sms1" value="${sms1}" />
		<input type="hidden" id="sumAssuredMapping"  name="sumAssuredMapping" value="${sumAssuredMapping}"/>
		<script>
	var v_nomineeParam='<bean:message key="lbl.nomineeParameter"/>';
	</script>
<div id="centercolumn">
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
<logic:notPresent name="viewmode">
<logic:notPresent name ="reload">
	<div id="revisedcontainer">
	<html:form styleId="cmInsuranceForm" action="/cmInsurance" method="post">
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
     		<input type="hidden" id="insFlag" value='false' name="insFlag"/>
    

<fieldset>	  
<legend><bean:message key="lbl.insurance" /></legend>

<!--<logic:present name="loanInitNote">-->
<!--	<font color="red">LoanId: ${sessionScope.loanId }</font>-->
<!--</logic:present>-->

	<%-- <html:hidden property="txnType" value="LIM"/>
 --%>


<html:hidden property="txnType" value="DC"/>
<html:hidden property="listSize"  styleClass="text" styleId="listSize"  value="${listSize}" />
<html:hidden property="sum"  styleClass="sum"	styleId="sum" value="${allInsuranceData[0].sum}" />
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	
		            	<td width="20%"><bean:message key="lbl.insuranceProvider" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select property="insuranceProvider" styleId="insuranceProvider" styleClass="text" onchange="getInsuranceProduct();clearData();"  value="${calculate[0].insuranceProvider}" >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="InsuranceProviders">
					<logic:notEmpty name="InsuranceProviders">
					<html:optionsCollection name="InsuranceProviders" label="insuranceProvider" value="chargeId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
				
	
				<td width="20%"><bean:message key="lbl.insuranceProduct" /><span><font color="red">*</font></span></td>
		               
		                <td width="35%"> 
		                <div id="insuranceProductDiv">
	  					<html:select property="insuranceProduct" styleId="insuranceProduct" styleClass="text"   value="${calculate[0].insuranceProduct}" onchange="insuranceServiceCalled();clearData();" >
   					    <html:option value="" styleId="id">--Select--</html:option>
   					    <logic:present name="insuranceProducts">
					<logic:notEmpty name="insuranceProducts">
					<html:optionsCollection name="insuranceProducts" label="insuranceProduct" value="insuranceProductId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</div>
						</td>
						
					</tr>
	  				<tr>
	  				
	  				
	  				 <td width="20%"><bean:message key="lbl.sumAssuPer" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="sumAssuPer" styleClass="text" styleId="sumAssuPer" onblur="sumAssuredAmount();" onchange="clearData();" onkeypress="return isNumberKey(event);" value="${allInsuranceData[0].sumAssuPer}"  maxlength="6"/></td>
		                
	  				
	  				
	  				 <td width="20%"><bean:message key="lbl.sumAssured" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="sumAssured" styleClass="text" styleId="sumAssured" onchange="sumAssuredAmountPercentage();" onkeypress="isInsuranceKey(event);"  value="${allInsuranceData[0].sumAssured}" /></td>
		                
	  				
	  				
	  					  </tr>
	 				
	 				
	 				<tr>
	 				
	 				
	 				<td><bean:message key="lbl.loanTenureYear" /><span><font color="red">*</font></span></td>
		                <td > 
	                    <html:text property="tenure" styleId="tenure" readonly="true" styleClass="text" value="${allInsuranceData[0].tenure}" maxlength="100" /> </td>
	                 
		            	<td><bean:message key="lbl.policyTenure" /><span><font color="red">*</font></span></td>
		                <td > 
	                    <html:text property="policyTenure" styleId="policyTenure" onchange="clearData();" styleClass="text" value="${allInsuranceData[0].policyTenure}" maxlength="3" /> </td>
	                 
		            	
						
						
					</tr>
					
					
					<tr>
	 				
	 				
	 					
		            	<td width="20%"><bean:message key="lbl.policyType" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select property="policyType" styleId="policyType" styleClass="text"   value="${calculate[0].policyType}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="policyTypes">
					<logic:notEmpty name="policyTypes">
					<html:optionsCollection name="policyTypes" label="policyType" value="policyTypeId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
						
						<td width="20%"><bean:message key="lbl.premiumFinanced" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select styleId="premiumFinanced" property="premiumFinanced" styleClass="text" value="${calculate[0].premiumFinanced}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="Y">Yes</html:option>
							<html:option value="N">No</html:option>
							
					    </html:select>
						</td>   
						
					</tr>
	 				
	 				
	 				<tr>
	 				
	 					<td><bean:message key="lbl.insurancePremium" /><span><font color="red">*</font></span></td>
						<td> <html:text property="insurancePremium" styleId="insurancePremium" styleClass="text"  onkeypress="return isNumberKey(event);" readonly="true" value="${calculate[0].insurancePremium}" maxlength="6" /> </td>
		   				
		   				<td><bean:message key="lbl.otherChargeId" /><span><font color="red">*</font></span></td>
						<td> <html:text property="otherChargeId" styleId="otherChargeId" styleClass="text"  readonly="true" value="${calculate[0].otherChargeId}" maxlength="100" /> </td>
		                <html:hidden  property="lbxOtherChargeId" styleId="lbxOtherChargeId"  value="${calculate[0].lbxOtherChargeId}" />
		           
		   				</tr>
		   			<tr>
		   		
		                <td><bean:message key="lbl.chargesOnInsurance" /><span><font color="red">*</font></span></td>
						<td> <html:text property="chargesOnInsurance" styleId="chargesOnInsurance" styleClass="text"  readonly="true"    value="${calculate[0].chargesOnInsurance}" maxlength="4" /> </td>
				
				<td width="20%"><bean:message key="lbl.propertyType" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select property="propertyType" styleId="propertyType" styleClass="text"   value="${calculate[0].propertyType}"  >
   					    <logic:present name="propertyTypes">
					<logic:notEmpty name="propertyTypes">
					<html:optionsCollection name="propertyTypes" label="propertyType" value="propertyTypeId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
				
				
					</tr>
		   			</table>
		   			</td></tr>
		   			</table>
		   			
		
		   			
	
<fieldset>	  
<legend><bean:message key="lbl.nomination" /></legend>

<fieldset>	  
<legend>First Nominee Detail</legend>


	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix" property="sPrefix" styleClass="text" value="${allInsuranceData[0].sPrefix}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							<%-- <html:option value="F">Miss</html:option> --%>
					    </html:select>
						</td>
						
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName" styleClass="text" styleId="nomineeName"   value="${allInsuranceData[0].nomineeName}" /></td>
		              </tr>
		              <tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName" styleClass="text" styleId="nomineeMName"   value="${allInsuranceData[0].nomineeMName}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName" styleClass="text" styleId="nomineeLName"   value="${allInsuranceData[0].nomineeLName}" /></td>
						</tr>
						<tr>
						<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth" styleClass="text" styleId="dateOfbirth"   value="${allInsuranceData[0].dateOfbirth}" /></td>
					
					<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender" property="gender" styleClass="text" value="${calculate[0].gender}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
						</tr>
						<tr>
						<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus" property="smaritalStatus" styleClass="text" value="${calculate[0].smaritalStatus}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp" styleId="relationshp" styleClass="text"   value="${calculate[0].relationshp}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
						</tr>
						<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage" styleClass="text" styleId="percentage" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${allInsuranceData[0].percentage}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr" styleClass="text" styleId="addr" value="${allInsuranceData[0].addr}"></html:textarea><td> --%>
		           		<td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	  					<html:select styleId="saddressType" property="saddressType" styleClass="text" value="${allInsuranceData[0].saddressType}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select>
						</td>
					</tr>
					
					<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr" styleClass="text" styleId="addr" value="${allInsuranceData[0].addr}"></html:text></td>
		           		
	                  <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet" styleClass="text" styleId="sinsuranceStreet" value="${allInsuranceData[0].sinsuranceStreet}"></html:text></td>
	                    </tr>
	                    <tr>
	                    <td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="snomineeArea" styleClass="text"  styleId="snomineeArea" value="${allInsuranceData[0].snomineeArea}"></html:text></td>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="snomineeCity" styleClass="text" styleId="snomineeCity" value="${allInsuranceData[0].snomineeCity}"></html:text>
	                   		<%-- <html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${allInsuranceData[0].txtDistCode}"></html:hidden>
							<html:button property="distButton" styleId="districtButton" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity','sNomineeState','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                    
	                    </tr>
	                    <tr>
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td width="13%">
						<html:text property="snomineeState" styleId="snomineeState" styleClass="text"
							 readonly="true" value="${allInsuranceData[0].snomineeState}" />
						<input type="button" class="lovbutton" id="stateButton"
							onclick="openLOVCommon(256,'cmInsuranceForm','txtStateCode','','txtStateCode', '','','','snomineeState')"
							value="" name="stateButton"/>
							<input type="hidden" name="txtStateCode" id="txtStateCode"
								value="${allInsuranceData[0].txtStateCode}" />
					</td>
	                    <%-- <td><html:text property="snomineeState" styleClass="text"  styleId="snomineeState" value="${allInsuranceData[0].snomineeState}"></html:text> --%>
	                    	<%-- <html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value="${allInsuranceData[0].txtStateCode}"></html:hidden>
							<html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState','','txtStateCode','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%><!-- </td> -->
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="snomineePin" styleClass="text" styleId="snomineePin" maxlength="6" onkeypress="return isPercentKey(evt);" value="${allInsuranceData[0].snomineePin}"></html:text></td>
	                    </tr>
	                  
					
		   			</table>
		   			</td></tr>
		   			</table> 
		   		</fieldset>	
		   		
		   		<fieldset>	  
<legend>Second Nominee Detail</legend>


	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix1" property="sPrefix1" styleClass="text" value="${allInsuranceData[0].sPrefix1}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							<%-- <html:option value="F">Miss</html:option> --%>
					    </html:select>
						</td>
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName1" styleClass="text" styleId="nomineeName1"   value="${allInsuranceData[0].nomineeName1}" /></td></tr>
		              <tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName1" styleClass="text" styleId="nomineeMName1"   value="${allInsuranceData[0].nomineeMName1}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName1" styleClass="text" styleId="nomineeLName1"   value="${allInsuranceData[0].nomineeLName1}" /></td>
						</tr>
<tr>

		 				<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth1" styleClass="text" styleId="dateOfbirth1"   value="${allInsuranceData[0].dateOfbirth1}" /></td>
<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender1" property="gender1" styleClass="text" value="${calculate[0].gender1}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
		 			</tr>
					<tr>
<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalstatus1" property="smaritalstatus1" styleClass="text" value="${calculate[0].smaritalstatus1}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
					
					
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp1" styleId="relationshp1" styleClass="text"   value="${calculate[0].relationshp1}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
					</tr>
					
					<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage1" styleClass="text" styleId="percentage1" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${allInsuranceData[0].percentage1}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr" styleClass="text" styleId="addr" value="${allInsuranceData[0].addr}"></html:textarea><td> --%>
	                    <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	  					<html:select styleId="saddressType1" property="saddressType1" styleClass="text" value="${allInsuranceData[0].saddressType1}"  >
	  					   <html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select>
						</td>
		           		
					</tr>
					
					<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr1" styleClass="text" styleId="addr1" value="${allInsuranceData[0].addr1}"></html:text></td>
	                     <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet1" styleClass="text" styleId="sinsuranceStreet1" value="${allInsuranceData[0].sinsuranceStreet1}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea1" styleClass="text"  styleId="sNomineeArea1" value="${allInsuranceData[0].sNomineeArea1}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity1" styleClass="text" styleId="sNomineeCity1" value="${allInsuranceData[0].sNomineeCity1}"></html:text>
	                   		<%-- <html:hidden property="txtDistCode1" styleId="txtDistCode1" styleClass="text" value="${allInsuranceData[0].txtDistCode1}"></html:hidden>
							<html:button property="distButton" styleId="districtButton" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity1','sNomineeState1','txtDistCode1', 'txtStateCode1','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                  
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td width="13%">
						<html:text property="sNomineeState1" styleId="sNomineeState1" styleClass="text"
							 readonly="true" value="${allInsuranceData[0].sNomineeState1}" />
						<input type="button" class="lovbutton" id="stateButton"
							onclick="openLOVCommon(192025,'cmInsuranceForm','txtStateCode1','','txtStateCode1', '','','','sNomineeState1')"
							value="" name="stateButton"/>
							<input type="hidden" name="txtStateCode1" id="txtStateCode1"
								value="${allInsuranceData[0].txtStateCode1}" />
						</td>
	                    <%-- <td><html:text property="sNomineeState1" styleClass="text"   styleId="sNomineeState1" value="${allInsuranceData[0].sNomineeState1}"></html:text> --%>
	                    	<%-- <html:hidden property="txtStateCode1" styleId="txtStateCode1" styleClass="text" value="${allInsuranceData[0].txtStateCode1}"></html:hidden>
							<html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState1','','txtStateCode1','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%><!-- </td> -->
	                   
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin1" styleClass="text" styleId="sNomineePin1" maxlength="6" onkeypress="return isPercentKey(evt);" value="${allInsuranceData[0].sNomineePin1}"></html:text></td>
	                    </tr>
					</table>
		   			</td></tr>
		   			</table>
		   		</fieldset>		
		   		<fieldset>
		   		<legend>Third Nominee Detail</legend>


	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix2" property="sPrefix2" styleClass="text" value="${allInsuranceData[0].sPrefix2}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							<%-- <html:option value="F">Miss</html:option> --%>
					    </html:select>
						</td>
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName2" styleClass="text" styleId="nomineeName2"   value="${allInsuranceData[0].nomineeName2}" /></td></tr>
		              <tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName2" styleClass="text" styleId="nomineeMName2"   value="${allInsuranceData[0].nomineeMName2}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName2" styleClass="text" styleId="nomineeLName2"   value="${allInsuranceData[0].nomineeLName2}" /></td>
						</tr>
<tr>

		 				<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth2" styleClass="text" styleId="dateOfbirth2"   value="${allInsuranceData[0].dateOfbirth2}" /></td>
<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender2" property="gender2" styleClass="text" value="${allInsuranceData[0].gender2}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
		 			</tr>
					<tr>
<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus2" property="smaritalStatus2" styleClass="text" value="${calculate[0].smaritalStatus2}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
					
					
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp2" styleId="relationshp2" styleClass="text"   value="${calculate[0].relationshp2}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
					</tr>
					
					<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage2" styleClass="text" styleId="percentage2" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${allInsuranceData[0].percentage2}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr" styleClass="text" styleId="addr" value="${allInsuranceData[0].addr}"></html:textarea><td> --%>
	                    
	                    <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	  					<html:select styleId="saddressType2" property="saddressType2" styleClass="text" value="${allInsuranceData[0].saddressType2}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select>
						</td>
		           		
					</tr>
					
					<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr2" styleClass="text" styleId="addr2" value="${allInsuranceData[0].addr2}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet2" styleClass="text" styleId="sinsuranceStreet2" value="${allInsuranceData[0].sinsuranceStreet2}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea2" styleClass="text"  styleId="sNomineeArea2" value="${allInsuranceData[0].sNomineeArea2}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity2" styleClass="text" styleId="sNomineeCity2" value="${allInsuranceData[0].sNomineeCity2}"></html:text>
	                   		<%-- <html:hidden property="txtDistCode2" styleId="txtDistCode2" styleClass="text" value="${allInsuranceData[0].txtDistCode2}"></html:hidden>
							<html:button property="distButton2" styleId="districtButton2" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity2','sNomineeState2','txtDistCode2', 'txtStateCode2','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                   
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td width="13%">
						<html:text property="sNomineeState2" styleId="sNomineeState2" styleClass="text"
							 readonly="true" value="${allInsuranceData[0].sNomineeState2}" />
						<input type="button" class="lovbutton" id="stateButton"
							onclick="openLOVCommon(192026,'cmInsuranceForm','txtStateCode2','','txtStateCode2', '','','','sNomineeState2')"
							value="" name="stateButton"/>
							<input type="hidden" name="txtStateCode2" id="txtStateCode2"
								value="${allInsuranceData[0].txtStateCode2}" />
						</td>
	                   <%--  <td><html:text property="sNomineeState2" styleClass="text"  styleId="sNomineeState2" value="${allInsuranceData[0].sNomineeState2}"></html:text> --%>
	                    	<%-- <html:hidden property="txtStateCode2" styleId="txtStateCode2" styleClass="text" value="${allInsuranceData[0].txtStateCode2}"></html:hidden>
							<html:button property="stateButton2" styleId="stateButton2" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState2','','txtStateCode2','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%><!-- </td> -->
	                   
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin2" styleClass="text" styleId="sNomineePin2" maxlength="6" onkeypress="return isPercentKey(evt);" value="${allInsuranceData[0].sNomineePin2}"></html:text></td>
	                    </tr>
					</table>
		   			</td></tr>
		   			</table>
		   		</fieldset>	
		   		<fieldset>
		   		<legend>Fourth Nominee Detail</legend>


	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix3" property="sPrefix3" styleClass="text" value="${allInsuranceData[0].sPrefix3}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							<%-- <html:option value="F">Miss</html:option> --%>
					    </html:select>
						</td>
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName3" styleClass="text" styleId="nomineeName3"   value="${allInsuranceData[0].nomineeName3}" /></td></tr>
		              <tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName3" styleClass="text" styleId="nomineeMName3"   value="${allInsuranceData[0].nomineeMName3}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName3" styleClass="text" styleId="nomineeLName3"   value="${allInsuranceData[0].nomineeLName3}" /></td>
						</tr>
<tr>

		 				<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth3" styleClass="text" styleId="dateOfbirth3"   value="${allInsuranceData[0].dateOfbirth3}" /></td>
<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender3" property="gender3" styleClass="text" value="${allInsuranceData[0].gender3}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
		 			</tr>
					<tr>
<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus3" property="smaritalStatus3" styleClass="text" value="${calculate[0].smaritalStatus3}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
					
					
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp3" styleId="relationshp3" styleClass="text"   value="${calculate[0].relationshp3}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
					</tr>
					
					<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage3" styleClass="text" styleId="percentage3" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${allInsuranceData[0].percentage3}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr" styleClass="text" styleId="addr" value="${allInsuranceData[0].addr}"></html:textarea><td> --%>
	                    
	                    <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	  					<html:select styleId="saddressType3" property="saddressType3" styleClass="text" value="${allInsuranceData[0].saddressType3}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select>
						</td>
		           		
					</tr>
					
					<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr3" styleClass="text" styleId="addr3" value="${allInsuranceData[0].addr3}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet3" styleClass="text" styleId="sinsuranceStreet3" value="${allInsuranceData[0].sinsuranceStreet3}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea3" styleClass="text"  styleId="sNomineeArea3" value="${allInsuranceData[0].sNomineeArea3}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity3" styleClass="text" styleId="sNomineeCity3" value="${allInsuranceData[0].sNomineeCity3}"></html:text>
	                   		<%-- <html:hidden property="txtDistCode3" styleId="txtDistCode3" styleClass="text" value="${allInsuranceData[0].txtDistCode3}"></html:hidden>
							<html:button property="distButton2" styleId="districtButton2" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity3','sNomineeState3','txtDistCode3', 'txtStateCode3','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                   
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td width="13%">
						<html:text property="sNomineeState3" styleId="sNomineeState3" styleClass="text"
							 readonly="true" value="${allInsuranceData[0].sNomineeState3}" />
						<input type="button" class="lovbutton" id="stateButton"
							onclick="openLOVCommon(192027,'cmInsuranceForm','txtStateCode3','','txtStateCode3', '','','','sNomineeState3')"
							value="" name="stateButton"/>
							<input type="hidden" name="txtStateCode3" id="txtStateCode3"
								value="${allInsuranceData[0].txtStateCode3}" />
						</td>
	                    <%-- <td><html:text property="sNomineeState3" styleClass="text"  styleId="sNomineeState3" value="${allInsuranceData[0].sNomineeState3}"></html:text> --%>
	                    	<%-- <html:hidden property="txtStateCode3" styleId="txtStateCode3" styleClass="text" value="${allInsuranceData[0].txtStateCode3}"></html:hidden>
							<html:button property="stateButton3" styleId="stateButton3" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState3','','txtStateCode3','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%><!-- </td> -->
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin3" styleClass="text" styleId="sNomineePin3" maxlength="6" onkeypress="return isPercentKey(evt);" value="${allInsuranceData[0].sNomineePin3}"></html:text></td>
	                    </tr>
					</table>
		   			</td></tr>
		   			</table>
		   		</fieldset>	
		   		<fieldset>
		   			   		<legend>Fifth Nominee Detail</legend>


	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix4" property="sPrefix4" styleClass="text" value="${allInsuranceData[0].sPrefix4}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							<%-- <html:option value="F">Miss</html:option> --%>
					    </html:select>
						</td>
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName4" styleClass="text" styleId="nomineeName4"   value="${allInsuranceData[0].nomineeName4}" /></td></tr>
		              <tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName4" styleClass="text" styleId="nomineeMName4"   value="${allInsuranceData[0].nomineeMName4}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName4" styleClass="text" styleId="nomineeLName4"   value="${allInsuranceData[0].nomineeLName4}" /></td>
						</tr>
<tr>

		 				<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth4" styleClass="text" styleId="dateOfbirth4"   value="${allInsuranceData[0].dateOfbirth4}" /></td>
<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender4" property="gender4" styleClass="text" value="${allInsuranceData[0].gender4}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
		 			</tr>
					<tr>
<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus4" property="smaritalStatus4" styleClass="text" value="${calculate[0].smaritalStatus4}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
					
					
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp4" styleId="relationshp4" styleClass="text"   value="${calculate[0].relationshp4}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
					</tr>
					
					<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage4" styleClass="text" styleId="percentage4" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${allInsuranceData[0].percentage4}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr" styleClass="text" styleId="addr" value="${allInsuranceData[0].addr}"></html:textarea><td> --%>
	                     <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	  					<html:select styleId="saddressType4" property="saddressType4" styleClass="text" value="${allInsuranceData[0].saddressType4}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select>
						</td>
		           		
					</tr>
					
					<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr4" styleClass="text" styleId="addr4" value="${allInsuranceData[0].addr4}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet4" styleClass="text" styleId="sinsuranceStreet4" value="${allInsuranceData[0].sinsuranceStreet4}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea4" styleClass="text"  styleId="sNomineeArea4" value="${allInsuranceData[0].sNomineeArea4}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity4" styleClass="text" styleId="sNomineeCity4" value="${allInsuranceData[0].sNomineeCity4}"></html:text>
	                   		<%-- <html:hidden property="txtDistCode4" styleId="txtDistCode4" styleClass="text" value="${allInsuranceData[0].txtDistCode4}"></html:hidden>
							<html:button property="distButton2" styleId="districtButton2" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity4','sNomineeState4','txtDistCode4', 'txtStateCode4','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                   
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                     <td width="13%">
						<html:text property="sNomineeState4" styleId="sNomineeState4" styleClass="text"
							 readonly="true" value="${allInsuranceData[0].sNomineeState4}" />
						<input type="button" class="lovbutton" id="stateButton"
							onclick="openLOVCommon(192028,'cmInsuranceForm','txtStateCode4','','txtStateCode4', '','','','sNomineeState4')"
							value="" name="stateButton"/>
							<input type="hidden" name="txtStateCode4" id="txtStateCode4"
								value="${allInsuranceData[0].txtStateCode4}" />
						</td>
	                    <%-- <td><html:text property="sNomineeState4" styleClass="text"  styleId="sNomineeState4" value="${allInsuranceData[0].sNomineeState4}"></html:text> --%>
	                    	<%-- <html:hidden property="txtStateCode4" styleId="txtStateCode4" styleClass="text" value="${allInsuranceData[0].txtStateCode4}"></html:hidden>
							<html:button property="stateButton4" styleId="stateButton4" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState4','','txtStateCode4','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%><!-- </td> -->
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin4" styleClass="text" styleId="sNomineePin4" maxlength="6" onkeypress="return isPercentKey(evt);" value="${allInsuranceData[0].sNomineePin4}"></html:text></td>
	                    </tr>
					</table>
		   			</td></tr>
		   			</table>
		   		</fieldset>	
		   		</fieldset>		
		   			

<table id="gridTable" width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table id="gridTable1" width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td> <b><bean:message key="contact.select" /></b></td>
        <td><center><b><bean:message key="lbl.borrowerName"/></b></center></td>
        <td><center><b><bean:message key="lbl.customerType"/></b></center></td>
        <td><center><b><bean:message key="lbl.dob"/></b></center></td>
        <td><center><b><bean:message key="lbl.age"/></b></center></td>
       <td><center><b><bean:message key="lbl.constitution"/></b></center></td>
	</tr>
	<tr>
		
	
		<logic:notEmpty name="customerDetailList">
		<logic:iterate name="customerDetailList" id="objcustomerDetailList" indexId="count">
		<tr class="white1">
		<td >
		<input type="checkbox" name="alchecking" id="allchk<%=count.intValue()+1%>"  value="${objcustomerDetailList.customerId }"/>
    	 </td>
		<td >${objcustomerDetailList.borrowerName}</td>
		<td >${objcustomerDetailList.customerType }</td>
		<td>${objcustomerDetailList.dob }</td>
		<td>${objcustomerDetailList.age1 }</td>
		<td>${objcustomerDetailList.customerConstitution }</td>
		
		</tr>	
	 
	   	</logic:iterate>
		</logic:notEmpty>
		</tr>
	
 </table>    </td>
</tr>

</table>
		   			
	   			
		   					
		   			 <table width="100%">
			<tbody>
					<tr>
	 
					 	<td>  <button type="button" name="calc" id="calc" class="topformbutton2" title="Alt+l" accesskey="l" onclick="calculateCmInsurance('cmInsuranceForm');" ><bean:message key="button.calcInsurance"  /></button>
      					 <button type="button"  name="save" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="saveCmInsurance()" ><bean:message key="button.save" /></button> 
 						 </td>
					</tr>
			</tbody>
			</table>
		
	
	</fieldset>
	
	
	<fieldset>	
		 <legend><bean:message key="lbl.insuranceDetails"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td> <b><input type="checkbox" name="allchked" id="allchked" onclick="allChecked();"/><bean:message key="contact.select" /></b></td>
        <td><center><b><bean:message key="lbl.insuranceProvider"/></b></center></td>
        <td><center><b><bean:message key="lbl.insuranceProduct"/></b></center></td>
        <td><center><b><bean:message key="lbl.borrowerName"/></b></center></td>
        <td><center><b><bean:message key="lbl.insurancePremium"/></b></center></td>
       
	</tr>
	
	<tr>
		
	<logic:present name="saveInsuranceData">
		<logic:notEmpty name="saveInsuranceData">
		<logic:iterate name="saveInsuranceData" id="objSaveInsuranceData" indexId="count">
		<tr class="white1">
		<td >
		<input type="checkbox" name="chk" id="chk" value="${objSaveInsuranceData.insuranceId }"/>
    	 </td>
		<td ><a href="#" id="anchor0" onclick="relodInsuranceDataLoan(${objSaveInsuranceData.insuranceId});">${objSaveInsuranceData.insuranceProvider}</a></td>
		<td >${objSaveInsuranceData.insuranceProduct }</td>
		<td>${objSaveInsuranceData.borrowerName }</td>
		<td>${objSaveInsuranceData.insurancePremium }</td>
		
		
		</tr>	
	 
	   	</logic:iterate>
		</logic:notEmpty>
		
		
		
		
		
		</logic:present>
		</tr>
		
	
 </table>    </td>
</tr>
<logic:present name="saveInsuranceDataDelete">
   <tr><td >
  	  <button type="button"  name="delete" id="delete" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteCmInsurance('cmInsuranceForm');" ><bean:message key="button.delete" /></button>
	      </td></tr>
	 </logic:present>
</table>

	</fieldset>

	
	</html:form>
	</div>
	</logic:notPresent>
	<logic:present name ="reload">


	<div id="revisedcontainer">
	<html:form styleId="cmInsuranceForm" action="/cmInsurance" method="post">
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    
	

<fieldset>	  
<legend><bean:message key="lbl.insurance" /></legend>

<!--<logic:present name="loanInitNote">-->
<!--	<font color="red">LoanId: ${sessionScope.loanId }</font>-->
<!--</logic:present>-->

	<%-- <html:hidden property="txnType" value="LIM"/>
 --%>


<html:hidden property="txnType" value="DC"/>
<html:hidden property="listSize"  styleClass="text" 	styleId="listSize"  value="${listSize}" />
<html:hidden property="sum"  styleClass="sum"	styleId="sum" value="${allInsuranceData[0].sum}" />
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	
		            	<td width="20%"><bean:message key="lbl.insuranceProvider" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select property="insuranceProvider" styleId="insuranceProvider" styleClass="text" onchange="getInsuranceProduct();clearData();"  value="${reload[0].insuranceProvider}" >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="InsuranceProviders">
					<logic:notEmpty name="InsuranceProviders">
					<html:optionsCollection name="InsuranceProviders" label="insuranceProvider" value="chargeId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
				
	
				<td width="20%"><bean:message key="lbl.insuranceProduct" /><span><font color="red">*</font></span></td>
		               
		                <td width="35%"> 
		                <div id="insuranceProductDiv">
	  					<html:select property="insuranceProduct" styleId="insuranceProduct" styleClass="text"   value="${reload[0].insuranceProduct}" onchange="insuranceServiceCalled();clearData();" >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="insuranceProducts">
					<logic:notEmpty name="insuranceProducts">
					<html:optionsCollection name="insuranceProducts" label="insuranceProduct" value="insuranceProductId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</div>
						</td>
						
					</tr>
	  				<tr>
	  				
	  				
	  				 <td width="20%"><bean:message key="lbl.sumAssuPer" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="sumAssuPer" styleClass="text" styleId="sumAssuPer" onblur="sumAssuredAmount();" onchange="clearData();" onkeypress="isNumberKey(event);" value="${reload[0].sumAssuPer}"  maxlength="6" /></td>
		                
	  				
	  				
	  				 <td width="20%"><bean:message key="lbl.sumAssured" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="sumAssured" styleClass="text" styleId="sumAssured" onchange="sumAssuredAmountPercentage();" onkeypress="isInsuranceKey(event);" readonly="true" value="${reload[0].sumAssured}" /></td>
		                
	  				
	  				
	  					  </tr>
	 				
	 				
	 				<tr>
	 				
	 				
	 				<td><bean:message key="lbl.loanTenureYear" /><span><font color="red">*</font></span></td>
		                <td > 
	                    <html:text property="tenure" styleId="tenure" readonly="true" styleClass="text" value="${reload[0].tenure}" maxlength="100" /> </td>
	                 
		            	<td><bean:message key="lbl.policyTenure" /><span><font color="red">*</font></span></td>
		                <td > 
	                    <html:text property="policyTenure" styleId="policyTenure" onchange="clearData();" styleClass="text" value="${reload[0].policyTenure}" maxlength="3" /> </td>
	                 
		            	
						
						
					</tr>
					
					
					<tr>
	 				
	 				
	 					
		            	<td width="20%"><bean:message key="lbl.policyType" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select property="policyType" styleId="policyType" styleClass="text"   value="${reload[0].policyType}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="policyTypes">
					<logic:notEmpty name="policyTypes">
					<html:optionsCollection name="policyTypes" label="policyType" value="policyTypeId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
						
						<td width="20%"><bean:message key="lbl.premiumFinanced" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select styleId="premiumFinanced" property="premiumFinanced" styleClass="text" value="${reload[0].premiumFinanced}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="Y">Yes</html:option>
							<html:option value="N">No</html:option>
							
					    </html:select>
						</td>   
						
					</tr>
	 				
	 				
	 				<tr>
	 				
	 					<td><bean:message key="lbl.insurancePremium" /><span><font color="red">*</font></span></td>
						<td> <html:text property="insurancePremium" styleId="insurancePremium" styleClass="text" onkeypress="return isNumberKey(event);" readonly="true"  value="${reload[0].insurancePremium}" maxlength="6" /> </td>
		   				
		   				<td><bean:message key="lbl.otherChargeId" /><span><font color="red">*</font></span></td>
						<td> <html:text property="otherChargeId" styleId="otherChargeId" styleClass="text"  readonly="true" value="${reload[0].otherChargeId}" maxlength="100" /> </td>
		                <html:hidden  property="lbxOtherChargeId" styleId="lbxOtherChargeId"  value="${calculate[0].lbxOtherChargeId}" />
		           
		   				</tr>
		   			<tr>
		   		
		                <td><bean:message key="lbl.chargesOnInsurance" /><span><font color="red">*</font></span></td>
						<td> <html:text property="chargesOnInsurance" styleId="chargesOnInsurance" styleClass="text"  readonly="true"    value="${reload[0].chargesOnInsurance}" maxlength="4" /> </td>
				
				<td width="20%"><bean:message key="lbl.propertyType" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select property="propertyType" styleId="propertyType" styleClass="text"   value="${reload[0].propertyType}"  >
   					    <logic:present name="propertyTypes">
					<logic:notEmpty name="propertyTypes">
					<html:optionsCollection name="propertyTypes" label="propertyType" value="propertyTypeId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
				
				
					</tr>
		   			</table>
		   			</td></tr>
		   			</table>
		   			
		
		   			
	
<fieldset>	  
<legend><bean:message key="lbl.nomination" /></legend>

<fieldset>	  
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix" property="sPrefix" styleClass="text" value="${reload[0].sPrefix}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							<%-- <html:option value="F">Miss</html:option> --%>
					    </html:select>
						</td>
						
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName" styleClass="text" styleId="nomineeName"   value="${reload[0].nomineeName}" /></td>
		              </tr>
		              <tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName" styleClass="text" styleId="nomineeMName"   value="${reload[0].nomineeMName}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName" styleClass="text" styleId="nomineeLName"   value="${reload[0].nomineeLName}" /></td>
						</tr>
						<tr>
						<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth" styleClass="text" styleId="dateOfbirth"   value="${reload[0].dateOfbirth}" /></td>
					
					<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender" property="gender" styleClass="text" value="${reload[0].gender}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
						</tr>
						<tr>
						<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus" property="smaritalStatus" styleClass="text" value="${reload[0].smaritalStatus}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp" styleId="relationshp" styleClass="text"   value="${reload[0].relationshp}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
						</tr>
						<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage" styleClass="text" styleId="percentage" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${reload[0].percentage}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr" styleClass="text" styleId="addr" value="${reload[0].addr}"></html:textarea><td> --%>
	                    <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	  					<html:select styleId="saddressType" property="saddressType" styleClass="text" value="${reload[0].saddressType}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select>
						</td>
		           		
					</tr>
					
					<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr" styleClass="text" styleId="addr" value="${reload[0].addr}"></html:text></td>
	                     <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet" styleClass="text" styleId="sinsuranceStreet" value="${reload[0].sinsuranceStreet}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="snomineeArea" styleClass="text"  styleId="snomineeArea" value="${reload[0].snomineeArea}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="snomineeCity" styleClass="text" styleId="snomineeCity" value="${reload[0].snomineeCity}"></html:text>
	                   		<%-- <html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text" value="${reload[0].txtDistCode}"></html:hidden>
							<html:button property="distButton" styleId="districtButton" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity','sNomineeState','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                  
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td><html:text property="snomineeState" styleClass="text"  styleId="snomineeState" value="${reload[0].snomineeState}"></html:text>
	                    	<%-- <html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value="${allInsuranceData[0].txtStateCode}"></html:hidden>
							<html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState','','txtStateCode','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                   
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="snomineePin" styleClass="text" styleId="snomineePin" maxlength="6" onkeypress="return isPercentKey(evt);" value="${reload[0].snomineePin}"></html:text></td>
	                    </tr>
	                  
					
		   			</table>
		   			</td></tr>
		   			</table>
		   			</fieldset>	
		   			
		   			<fieldset>	  
<legend>Second Nominee Detail</legend>


	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix1" property="sPrefix1" styleClass="text" value="${reload[0].sPrefix1}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							<%-- <html:option value="F">Miss</html:option> --%>
					    </html:select>
						</td>
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName1" styleClass="text" styleId="nomineeName1"   value="${reload[0].nomineeName1}" /></td></tr>
		              <tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName1" styleClass="text" styleId="nomineeMName1"   value="${reload[0].nomineeMName1}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName1" styleClass="text" styleId="nomineeLName1"   value="${reload[0].nomineeLName1}" /></td>
						</tr>
<tr>

		 				<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth1" styleClass="text" styleId="dateOfbirth1"   value="${reload[0].dateOfbirth1}" /></td>
<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender1" property="gender1" styleClass="text" value="${reload[0].gender1}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
		 			</tr>
					<tr>
<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalstatus1" property="smaritalstatus1" styleClass="text" value="${reload[0].smaritalstatus1}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
					
					
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp1" styleId="relationshp1" styleClass="text"   value="${reload[0].relationshp1}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
					</tr>
					
					<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage1" styleClass="text" styleId="percentage1" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${reload[0].percentage1}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr" styleClass="text" styleId="addr" value="${reload[0].addr}"></html:textarea><td> --%>
	                    <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	  					<html:select styleId="saddressType1" property="saddressType1" styleClass="text" value="${reload[0].saddressType1}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select>
						</td>
		           		
					</tr>
					
					<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr1" styleClass="text" styleId="addr1" value="${reload[0].addr1}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet1" styleClass="text" styleId="sinsuranceStreet1" value="${reload[0].sinsuranceStreet1}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea1" styleClass="text"  styleId="sNomineeArea1" value="${reload[0].sNomineeArea1}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity1" styleClass="text"  styleId="sNomineeCity1" value="${allInsuranceData[0].sNomineeCity1}"></html:text>
	                   		<%-- <html:hidden property="txtDistCode1" styleId="txtDistCode1" styleClass="text" value="${reload[0].txtDistCode1}"></html:hidden>
							<html:button property="distButton" styleId="districtButton" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity1','sNomineeState1','txtDistCode1', 'txtStateCode1','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                  
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td><html:text property="sNomineeState1" styleClass="text"  styleId="sNomineeState1" value="${reload[0].sNomineeState1}"></html:text>
	                    	<%-- <html:hidden property="txtStateCode1" styleId="txtStateCode1" styleClass="text" value="${reload[0].txtStateCode1}"></html:hidden>
							<html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState1','','txtStateCode1','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin1" styleClass="text" styleId="sNomineePin1" maxlength="6" onkeypress="return isPercentKey(evt);" value="${reload[0].sNomineePin1}"></html:text></td>
	                    </tr>
					</table>
		   			</td></tr>
		   			</table>
		   			</fieldset>
		   			
		   			<fieldset>	  
<legend>Third Nominee Detail</legend>


	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix2" property="sPrefix2" styleClass="text" value="${reload[0].sPrefix2}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							<%-- <html:option value="F">Miss</html:option> --%>
					    </html:select>
						</td>
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName2" styleClass="text" styleId="nomineeName2"   value="${reload[0].nomineeName2}" /></td></tr>
		              <tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName2" styleClass="text" styleId="nomineeMName2"   value="${reload[0].nomineeMName2}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName2" styleClass="text" styleId="nomineeLName2"   value="${reload[0].nomineeLName2}" /></td>
						</tr>
<tr>

		 				<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth2" styleClass="text" styleId="dateOfbirth2"   value="${reload[0].dateOfbirth2}" /></td>
<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender2" property="gender2" styleClass="text" value="${reload[0].gender2}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
		 			</tr>
					<tr>
<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus2" property="smaritalStatus2" styleClass="text" value="${calculate[0].smaritalStatus2}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
					
					
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp2" styleId="relationshp2" styleClass="text"   value="${reload[0].relationshp2}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
					</tr>
					
					<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage2" styleClass="text" styleId="percentage2" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${reload[0].percentage2}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr" styleClass="text" styleId="addr" value="${reload[0].addr}"></html:textarea><td> --%>
	                    <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	  					<html:select styleId="saddressType2" property="saddressType2" styleClass="text" value="${reload[0].saddressType2}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select>
						</td>
		           		
					</tr>
					
					<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr2" styleClass="text" styleId="addr2" value="${reload[0].addr2}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet2" styleClass="text" styleId="sinsuranceStreet2" value="${reload[0].sinsuranceStreet2}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea2" styleClass="text"  styleId="sNomineeArea2" value="${reload[0].sNomineeArea2}"></html:text></td>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity2" styleClass="text" styleId="sNomineeCity2" value="${reload[0].sNomineeCity2}"></html:text>
	                   		<%-- <html:hidden property="txtDistCode2" styleId="txtDistCode2" styleClass="text" value="${reload[0].txtDistCode2}"></html:hidden>
							<html:button property="distButton2" styleId="districtButton2" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity2','sNomineeState2','txtDistCode2', 'txtStateCode2','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                   
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td><html:text property="sNomineeState2" styleClass="text"  styleId="sNomineeState2" value="${reload[0].sNomineeState2}"></html:text>
	                    	<%-- <html:hidden property="txtStateCode2" styleId="txtStateCode2" styleClass="text" value="${reload[0].txtStateCode2}"></html:hidden>
							<html:button property="stateButton2" styleId="stateButton2" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState2','','txtStateCode2','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin2" styleClass="text" styleId="sNomineePin2" maxlength="6" onkeypress="return isPercentKey(evt);" value="${reload[0].sNomineePin2}"></html:text></td>
	                    </tr>
					</table>
		   			</td></tr>
		   			</table>
		   			</fieldset>
		   			
		   			<fieldset>	  
<legend>Fourth Nominee Detail</legend>


	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix3" property="sPrefix3" styleClass="text" value="${reload[0].sPrefix3}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							<%-- <html:option value="F">Miss</html:option> --%>
					    </html:select>
						</td>
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName3" styleClass="text" styleId="nomineeName3"   value="${reload[0].nomineeName3}" /></td></tr>
		              <tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName3" styleClass="text" styleId="nomineeMName3"   value="${reload[0].nomineeMName3}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName3" styleClass="text" styleId="nomineeLName3"   value="${reload[0].nomineeLName3}" /></td>
						</tr>
<tr>

		 				<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth3" styleClass="text" styleId="dateOfbirth3"   value="${reload[0].dateOfbirth3}" /></td>
<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender3" property="gender3" styleClass="text" value="${reload[0].gender3}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
		 			</tr>
					<tr>
<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus3" property="smaritalStatus3" styleClass="text" value="${reload[0].smaritalStatus3}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
					
					
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp3" styleId="relationshp3" styleClass="text"   value="${reload[0].relationshp3}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
					</tr>
					
					<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage3" styleClass="text" styleId="percentage3" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${allInsuranceData[0].reload}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr" styleClass="text" styleId="addr" value="${reload[0].addr}"></html:textarea><td> --%>
	                    <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	  					<html:select styleId="saddressType3" property="saddressType3" styleClass="text" value="${reload[0].saddressType3}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select>
						</td>
	                    
		           		
					</tr>
					
					<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr3" styleClass="text" styleId="addr3" value="${reload[0].addr3}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet3" styleClass="text" styleId="sinsuranceStreet3" value="${reload[0].sinsuranceStreet3}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea3" styleClass="text"  styleId="sNomineeArea3" value="${reload[0].sNomineeArea3}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity3" styleClass="text" styleId="sNomineeCity3" value="${reload[0].sNomineeCity3}"></html:text>
	                   		<%-- <html:hidden property="txtDistCode3" styleId="txtDistCode3" styleClass="text" value="${reload[0].txtDistCode3}"></html:hidden>
							<html:button property="distButton2" styleId="districtButton2" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity3','sNomineeState3','txtDistCode3', 'txtStateCode3','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                   
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td><html:text property="sNomineeState3" styleClass="text"  styleId="sNomineeState3" value="${reload[0].sNomineeState3}"></html:text>
	                    	<%-- <html:hidden property="txtStateCode3" styleId="txtStateCode3" styleClass="text" value="${reload[0].txtStateCode3}"></html:hidden>
							<html:button property="stateButton3" styleId="stateButton3" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState3','','txtStateCode3','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin3" styleClass="text" styleId="sNomineePin3" maxlength="6" onkeypress="return isPercentKey(evt);" value="${reload[0].sNomineePin3}"></html:text></td>
	                    </tr>
					</table>
		   			</td></tr>
		   			</table>
		   			</fieldset>
		   			
		   			<fieldset>	  
<legend>Fifth Nominee Detail</legend>


	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix4" property="sPrefix4" styleClass="text" value="${reload[0].sPrefix4}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							<%-- <html:option value="F">Miss</html:option> --%>
					    </html:select>
						</td>
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName4" styleClass="text" styleId="nomineeName4"   value="${reload[0].nomineeName4}" /></td></tr>
		              <tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName4" styleClass="text" styleId="nomineeMName4"   value="${reload[0].nomineeMName4}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName4" styleClass="text" styleId="nomineeLName4"   value="${reload[0].nomineeLName4}" /></td>
						</tr>
<tr>

		 				<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth4" styleClass="text" styleId="dateOfbirth4"   value="${reload[0].dateOfbirth4}" /></td>
<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender4" property="gender4" styleClass="text" value="${reload[0].gender4}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
		 			</tr>
					<tr>
<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus4" property="smaritalStatus4" styleClass="text" value="${reload[0].smaritalStatus4}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
					
					
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp4" styleId="relationshp4" styleClass="text"   value="${reload[0].relationshp4}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
					</tr>
					
					<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage4" styleClass="text" styleId="percentage4" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${reload[0].percentage4}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr" styleClass="text" styleId="addr" value="${reload[0].addr}"></html:textarea><td> --%>
	                    
	                    <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	  					<html:select styleId="saddressType4" property="saddressType4" styleClass="text" value="${reload[0].saddressType4}"  >
	  					   <html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select>
						</td>
		           		
					</tr>
					
					<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr4" styleClass="text" styleId="addr4" value="${reload[0].addr4}"></html:text></td>
	                     <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet4" styleClass="text" styleId="sinsuranceStreet4" value="${reload[0].sinsuranceStreet4}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea4" styleClass="text"  styleId="sNomineeArea4" value="${reload[0].sNomineeArea4}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity4" styleClass="text"  styleId="sNomineeCity4" value="${reload[0].sNomineeCity4}"></html:text>
	                   		<%-- <html:hidden property="txtDistCode4" styleId="txtDistCode4" styleClass="text" value="${reload[0].txtDistCode4}"></html:hidden>
							<html:button property="distButton2" styleId="districtButton2" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity4','sNomineeState4','txtDistCode4', 'txtStateCode4','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                   
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td><html:text property="sNomineeState4" styleClass="text"   styleId="sNomineeState4" value="${reload[0].sNomineeState4}"></html:text>
	                    	<%-- <html:hidden property="txtStateCode4" styleId="txtStateCode4" styleClass="text" value="${reload[0].txtStateCode4}"></html:hidden>
							<html:button property="stateButton4" styleId="stateButton4" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState4','','txtStateCode4','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%></td>
	                   
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin4" styleClass="text" styleId="sNomineePin4" maxlength="6" onkeypress="return isPercentKey(evt);" value="${reload[0].sNomineePin4}"></html:text></td>
	                    </tr>
					</table>
		   			</td></tr>
		   			</table>
		   			</fieldset>
		   		</fieldset>		
		   			

<table id="gridTable" width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table id="gridTable1" width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td> <b><bean:message key="contact.select" /></b></td>
        <td><center><b><bean:message key="lbl.borrowerName"/></b></center></td>
        <td><center><b><bean:message key="lbl.customerType"/></b></center></td>
        <td><center><b><bean:message key="lbl.dob"/></b></center></td>
        <td><center><b><bean:message key="lbl.age"/></b></center></td>
       <td><center><b><bean:message key="lbl.constitution"/></b></center></td>
	</tr>
	<tr>
		
	
		<logic:notEmpty name="customerDetailList">
		<logic:iterate name="customerDetailList" id="objcustomerDetailList" indexId="count">
		<tr class="white1">
		<td >
		<input type="checkbox" name="alchecking" id="allchk<%=count.intValue()+1%>"  value="${objcustomerDetailList.customerId }"/>
    	 </td>
		<td >${objcustomerDetailList.borrowerName}</td>
		<td >${objcustomerDetailList.customerType }</td>
		<td>${objcustomerDetailList.dob }</td>
		<td>${objcustomerDetailList.age1 }</td>
		<td>${objcustomerDetailList.customerConstitution }</td>
		
		</tr>	
	 
	   	</logic:iterate>
		</logic:notEmpty>
		</tr>
	
 </table>    </td>
</tr>

</table>
		   			
	   			
		   					
		   			 <table width="100%">
			<tbody>
					<tr>
	 
					 	<td>  <button type="button" name="calc" id="calc" class="topformbutton2" title="Alt+l" accesskey="l" onclick="calculateCmInsurance('cmInsuranceForm');" ><bean:message key="button.calcInsurance"  /></button>
      					 <button type="button"  name="save" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="saveCmInsurance()" ><bean:message key="button.save" /></button> 
 						 </td>
					</tr>
			</tbody>
			</table>
		
	
	</fieldset>
	
	
	<fieldset>	
		 <legend><bean:message key="lbl.insuranceDetails"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td> <b><input type="checkbox" name="allchked" id="allchked" onclick="allChecked();"/><bean:message key="contact.select" /></b></td>
        <td><center><b><bean:message key="lbl.insuranceProvider"/></b></center></td>
        <td><center><b><bean:message key="lbl.insuranceProduct"/></b></center></td>
        <td><center><b><bean:message key="lbl.borrowerName"/></b></center></td>
        <td><center><b><bean:message key="lbl.insurancePremium"/></b></center></td>
       
	</tr>
	
	<tr>
		
	<logic:present name="saveInsuranceData">
		<logic:notEmpty name="saveInsuranceData">
		<logic:iterate name="saveInsuranceData" id="objSaveInsuranceData" indexId="count">
		<tr class="white1">
		<td >
		<input type="checkbox" name="chk" id="chk" value="${objSaveInsuranceData.insuranceId }"/>
    	 </td>
		<td ><a href="#" id="anchor0" onclick="return relodInsuranceDataLoan(${objSaveInsuranceData.insuranceId});">${objSaveInsuranceData.insuranceProvider}</a></td>
		<td >${objSaveInsuranceData.insuranceProduct }</td>
		<td>${objSaveInsuranceData.borrowerName }</td>
		<td>${objSaveInsuranceData.insurancePremium }</td>
		
		
		</tr>	
	 
	   	</logic:iterate>
		</logic:notEmpty>
		
		
		
		
		
		</logic:present>
		</tr>
		
	
 </table>    </td>
</tr>
<logic:present name="saveInsuranceDataDelete">
   <tr><td >
  	  <button type="button"  name="delete" id="delete" class="topformbutton2" title="Alt+D" accesskey="D" onclick="deleteCmInsurance('cmInsuranceForm');" ><bean:message key="button.delete" /></button>
	      </td></tr>
	 </logic:present>
</table>

	</fieldset>

	
	</html:form>
	</div>
	
</logic:present>
	
</logic:notPresent>



<logic:present name="viewmode">

	<div id="revisedcontainer">
	<html:form styleId="cmInsuranceForm" action="/cmInsurance" method="post">
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
			<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    
	

	
		   	
		   	
		   	
		   	
<fieldset>	  
<legend><bean:message key="lbl.insurance" /></legend>



<html:hidden property="txnType" value="DC"/>
<html:hidden property="listSize"  styleClass="text" 	styleId="listSize"  value="${listSize}" />

<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	
		            	<td width="20%"><bean:message key="lbl.insuranceProvider" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select property="insuranceProvider" styleId="insuranceProvider" styleClass="text" onchange="getInsuranceProduct();clearData();" disabled="true"  value="${viewList[0].insuranceProvider}" >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="InsuranceProviders">
					<logic:notEmpty name="InsuranceProviders">
					<html:optionsCollection name="InsuranceProviders" label="insuranceProvider" value="chargeId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
						
						
						
						<td width="20%"><bean:message key="lbl.insuranceProduct" /><span><font color="red">*</font></span></td>
		               
		                <td width="35%"> 
		                <div id="insuranceProductDiv">
		                <html:text property="insuranceProduct" styleClass="text" styleId="insuranceProduct"  disabled="true" onchange="clearData();" value="${viewList[0].insuranceProduct}"  />
<%-- 	  					<html:select property="insuranceProduct" styleId="insuranceProduct" styleClass="text" disabled="true"   value="${viewList[0].insuranceProduct}" onchange="insuranceServiceCalled();clearData();" > --%>
<%--    					    <html:option value="">--Select--</html:option> --%>
<%--    					    <logic:present name="insuranceProducts"> --%>
<%-- 					<logic:notEmpty name="insuranceProducts"> --%>
<%-- 					<html:optionsCollection name="insuranceProducts" label="insuranceProduct" value="insuranceProductId" /> --%>
<%-- 					</logic:notEmpty>		 --%>
<%-- 				</logic:present> --%>
<%-- 						</html:select> --%>
						</div>
						</td>
						
					</tr>
	  				<tr>
	  				
	  				
	  				 <td width="20%"><bean:message key="lbl.sumAssuPer" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="sumAssuPer" styleClass="text" styleId="sumAssuPer"  disabled="true" onchange="clearData();" value="${viewList[0].sumAssuPer}"  maxlength="6"/></td>
		                
	  				
	  				
	  				 <td width="20%"><bean:message key="lbl.sumAssured" /><span><font color="red">*</font></span></td>
	                     <td ><html:text property="sumAssured" styleClass="text" styleId="sumAssured" readonly="false"  onchange="sumAssuredAmountPercentage();" value="${viewList[0].sumAssured}" /></td>
		                
	  					  </tr>
	 				
	 				
	 				<tr>
	 				
	 				<td><bean:message key="lbl.loanTenureYear" /><span><font color="red">*</font></span></td>
		                <td > 
	                    <html:text property="tenure" styleId="tenure" readonly="true" styleClass="text" value="${viewList[0].tenure}" maxlength="100" /> </td>
	                 
	                 <td><bean:message key="lbl.policyTenure" /><span><font color="red">*</font></span></td>
		                <td > 
	                    <html:text property="policyTenure" styleId="tenure" readonly="true" onchange="clearData();" styleClass="text" value="${viewList[0].policyTenure}" maxlength="3" /> </td>
	                 
		            
					</tr>
					
					
					
					
						<tr>
	 			
		            	<td width="20%"><bean:message key="lbl.policyType" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select property="policyType" styleId="policyType" styleClass="text" disabled="true"  value="${viewList[0].policyType}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="policyTypes">
					<logic:notEmpty name="policyTypes">
					<html:optionsCollection name="policyTypes" label="policyType" value="policyTypeId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
						
						
						<td width="20%"><bean:message key="lbl.premiumFinanced" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select styleId="premiumFinanced" property="premiumFinanced" styleClass="text" disabled="true"  value="${viewList[0].premiumFinanced}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="Y">Yes</html:option>
							<html:option value="N">No</html:option>
							
					    </html:select>
						</td>     
					
						
					</tr>
			
	 				
	 				<tr>
	 				
	 					<td><bean:message key="lbl.insurancePremium" /><span><font color="red">*</font></span></td>
						<td> <html:text property="insurancePremium" styleId="insurancePremium" styleClass="text" disabled="true" onkeypress="return isNumberKey(event);" value="${viewList[0].insurancePremium}" maxlength="6" /> </td>
		   				
		   				<td><bean:message key="lbl.otherChargeId" /><span><font color="red">*</font></span></td>
						<td> <html:text property="otherChargeId" styleId="otherChargeId" styleClass="text" readonly="true"  value="${viewList[0].otherChargeId}" maxlength="100" /> </td>
		                <html:hidden  property="lbxOtherChargeId" styleId="lbxOtherChargeId" value="${viewList[0].lbxOtherChargeId}" />
		               
		   				
		   				
		   				</tr>
		   			<tr>
		   				
		                
		                <td><bean:message key="lbl.chargesOnInsurance" /><span><font color="red">*</font></span></td>
						<td> <html:text property="chargesOnInsurance" styleId="chargesOnInsurance" styleClass="text" disabled="true"  value="${viewList[0].chargesOnInsurance}" maxlength="4" /> </td>
					
					<td width="20%"><bean:message key="lbl.propertyType" /><span><font color="red">*</font></span></td>
		                <td width="35%">
	  					<html:select property="propertyType" styleId="propertyType" styleClass="text" disabled="true"  value="${viewList[0].propertyType}"  >
   					    <logic:present name="propertyTypes">
					<logic:notEmpty name="propertyTypes">
					<html:optionsCollection name="propertyTypes" label="propertyType" value="propertyTypeId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
					
					
					</tr>
		   			</table>
		   			</td></tr>
		   			</table>
		   			
		   			
		   			
		   			
<fieldset>	  
<legend><bean:message key="lbl.nomination" /></legend>

<fieldset>	  
<legend>First Nominee Detail</legend>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	
		   			<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix" property="sPrefix" disabled="true" styleClass="text" value="${viewList[0].sPrefix}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							<%-- <html:option value="F">Miss</html:option> --%>
					    </html:select>
						</td>
						
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName" styleClass="text" disabled="true" styleId="nomineeName"   value="${viewList[0].nomineeName}" /></td>
		              </tr>
		              <tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName" styleClass="text" disabled="true" styleId="nomineeMName"   value="${viewList[0].nomineeMName}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /><span><font color="red">*</font></td>
	                     <td ><html:text property="nomineeLName" styleClass="text" disabled="true" styleId="nomineeLName"   value="${viewList[0].nomineeLName}" /></td>
						</tr>
						<tr>
						<td width="20%"><bean:message key="lbl.dob" /><span><font color="red">*</font></td>
	                     <td ><html:text property="dateOfbirth" styleClass="text" disabled="true" styleId="dateOfbirth"   value="${viewList[0].dateOfbirth}" /></td>
					
					<td width="20%"><bean:message key="lbl.gender" /><span><font color="red">*</font></td>
		                <td width="35%">
	  					<html:select styleId="gender" property="gender" styleClass="text" disabled="true" value="${viewList[0].gender}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
						</tr>
						<tr>
						<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus" property="smaritalStatus" disabled="true" styleClass="text" value="${viewList[0].smaritalStatus}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp" styleId="relationshp" styleClass="text"  disabled="true" value="${viewList[0].relationshp}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
						</tr>
						<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage" styleClass="text" styleId="percentage" disabled="true" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${viewList[0].percentage}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr" styleClass="text" styleId="addr" value="${allInsuranceData[0].addr}"></html:textarea><td> --%>
	                    <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	                     <html:text property="saddressType" styleClass="text" disabled="true" styleId="saddressType"  value="${viewList[0].saddressType}" />
	  					<%-- <html:select styleId="saddressType" property="saddressType" styleClass="text" disabled="true" value="${viewList[0].saddressType}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select> --%>
						</td>
		           		
					</tr>
					
					<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr" styleClass="text" styleId="addr" disabled="true" value="${viewList[0].addr}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet" styleClass="text" styleId="sinsuranceStreet" disabled="true" value="${viewList[0].sinsuranceStreet}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="snomineeArea" styleClass="text" disabled="true" styleId="snomineeArea" value="${viewList[0].snomineeArea}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="snomineeCity" styleClass="text" disabled="true" styleId="snomineeCity" value="${viewList[0].snomineeCity}"></html:text>
	                    	 <html:hidden property="txtDistCode" styleId="txtDistCode" styleClass="text"  value="${viewList[0].txtDistCode}"></html:hidden></td>
						<%--	<html:button property="distButton" styleId="districtButton" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity','sNomineeState','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
	                    
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td><html:text property="snomineeState" styleClass="text" disabled="true" styleId="snomineeState" value="${viewList[0].snomineeState}"></html:text>
	                   		 <html:hidden property="txtStateCode" styleId="txtStateCode" styleClass="text" value=" ${viewList[0].txtStateCode}"></html:hidden></td> 
							<%--<html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState','','txtStateCode','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="snomineePin" styleClass="text" disabled="true" styleId="snomineePin" value="${viewList[0].snomineePin}"></html:text></td>
	                    </tr>
	                  
					
		   			</table>
		   			</td></tr>
		   			</table>
		   			</fieldset>
		   			
		   			<fieldset>	  
<legend>Second Nominee Detail</legend>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix1" property="sPrefix1" disabled="true" styleClass="text" value="${viewList[0].sPrefix1}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							
					    </html:select>
						</td>
						
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName1" styleClass="text" disabled="true" styleId="nomineeName1"   value="${viewList[0].nomineeName1}" /></td>
		              </tr>
					<tr>
				<tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName1" styleClass="text" disabled="true" styleId="nomineeMName1"   value="${viewList[0].nomineeMName1}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName1" styleClass="text" disabled="true" styleId="nomineeLName1"   value="${viewList[0].nomineeLName1}" /></td>
						</tr>
						<tr>
						<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth1" styleClass="text" disabled="true" styleId="dateOfbirth1"   value="${viewList[0].dateOfbirth1}" /></td>
					
					<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender1" property="gender1" styleClass="text" disabled="true" value="${viewList[0].gender1}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
						</tr>
					<tr>
						<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalstatus1" property="smaritalstatus1" disabled="true" styleClass="text" value="${viewList[0].smaritalstatus1}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp1" styleId="relationshp1" styleClass="text"  disabled="true" value="${viewList[0].relationshp1}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
						</tr>
		   			<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage1" styleClass="text" styleId="percentage1" disabled="true" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${viewList[0].percentage1}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr1" styleClass="text" styleId="addr1" value="${allInsuranceData[0].addr1}"></html:textarea><td> --%>
	                    <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	                     <html:text property="saddressType1" styleClass="text" disabled="true" styleId="saddressType1"  value="${viewList[0].saddressType1}" />
	  					<%-- <html:select styleId="saddressType1" property="saddressType1" styleClass="text" disabled="true" value="${viewList[0].saddressType1}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select> --%>
						</td>
		           	
					</tr>
				<tr>
							<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr1" styleClass="text" styleId="addr1" disabled="true" value="${viewList[0].addr1}"></html:text></td>
	                     <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet1" styleClass="text" styleId="sinsuranceStreet1" disabled="true" value="${viewList[0].sinsuranceStreet1}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea1" styleClass="text" disabled="true" styleId="sNomineeArea1"  value="${viewList[0].sNomineeArea1}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity1" styleClass="text" disabled="true" styleId="sNomineeCity1" value="${viewList[0].sNomineeCity1}"></html:text>
	                    	 <html:hidden property="txtDistCode1" styleId="txtDistCode1" styleClass="text"  value="${viewList[0].txtDistCode1}"></html:hidden></td>
						<%--	<html:button property="distButton" styleId="districtButton" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity','sNomineeState','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
	                    
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td><html:text property="sNomineeState1" styleClass="text" disabled="true" styleId="sNomineeState1" value="${viewList[0].sNomineeState1}"></html:text>
	                   		 <html:hidden property="txtStateCode1" styleId="txtStateCode1" styleClass="text" value=" ${viewList[0].txtStateCode1}"></html:hidden></td> 
							<%--<html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState','','txtStateCode','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin1" styleClass="text" disabled="true" styleId="sNomineePin1" value="${viewList[0].sNomineePin1}"></html:text></td>
	                    </tr>
		   			</table>
		   			</td></tr>
		   			</table>
		   			
		   		<fieldset>	  
<legend>Third Nominee Detail</legend>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix2" property="sPrefix2" disabled="true" styleClass="text" value="${viewList[0].sPrefix2}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							
					    </html:select>
						</td>
						
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName2" styleClass="text" disabled="true" styleId="nomineeName2"   value="${viewList[0].nomineeName2}" /></td>
		              </tr>
					<tr>
				<tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName2" styleClass="text" disabled="true" styleId="nomineeMName2"   value="${viewList[0].nomineeMName2}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName2" styleClass="text" disabled="true" styleId="nomineeLName2"   value="${viewList[0].nomineeLName2}" /></td>
						</tr>
						<tr>
						<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth2" styleClass="text" disabled="true" styleId="dateOfbirth2"   value="${viewList[0].dateOfbirth2}" /></td>
					
					<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender2" property="gender2" styleClass="text" disabled="true" value="${viewList[0].gender2}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
						</tr>
					<tr>
						<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus2" property="smaritalStatus2" disabled="true" styleClass="text" value="${viewList[0].smaritalStatus2}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp2" styleId="relationshp2" styleClass="text"  disabled="true" value="${viewList[0].relationshp2}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
						</tr>
		   			<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage2" styleClass="text" styleId="percentage2" disabled="true" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${viewList[0].percentage2}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr1" styleClass="text" styleId="addr1" value="${allInsuranceData[0].addr1}"></html:textarea><td> --%>
	                     <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	                     <html:text property="saddressType2" styleClass="text" disabled="true" styleId="saddressType2"  value="${viewList[0].saddressType2}" />
	  					<%-- <html:select styleId="saddressType2" property="saddressType2" styleClass="text" disabled="true" value="${viewList[0].saddressType2}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select> --%>
						</td>
		           		
					</tr>
				<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr2" styleClass="text" styleId="addr2" disabled="true" value="${viewList[0].addr2}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet2" styleClass="text" styleId="sinsuranceStreet2" disabled="true" value="${viewList[0].sinsuranceStreet2}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea2" styleClass="text" disabled="true" styleId="sNomineeArea2" value="${viewList[0].sNomineeArea2}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity2" styleClass="text" disabled="true" styleId="sNomineeCity2" value="${viewList[0].sNomineeCity2}"></html:text>
	                    	 <html:hidden property="txtDistCode2" styleId="txtDistCode2" styleClass="text"  value="${viewList[0].txtDistCode2}"></html:hidden></td>
						<%--	<html:button property="distButton" styleId="districtButton" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity','sNomineeState','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
	                    
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td><html:text property="sNomineeState2" styleClass="text" disabled="true" styleId="sNomineeState2" value="${viewList[0].sNomineeState2}"></html:text>
	                   		 <html:hidden property="txtStateCode2" styleId="txtStateCode2" styleClass="text" value=" ${viewList[0].txtStateCode2}"></html:hidden></td> 
							<%--<html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState','','txtStateCode','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin2" styleClass="text" disabled="true" styleId="sNomineePin2" value="${viewList[0].sNomineePin2}"></html:text></td>
	                    </tr>
		   			</table>
		   			</td></tr>
		   			</table>
		   	</fieldset>
		   	
		   			   			   	<fieldset>	  
<legend>Fourth Nominee Detail</legend>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix3" property="sPrefix3" disabled="true" styleClass="text" value="${viewList[0].sPrefix3}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							
					    </html:select>
						</td>
						
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName3" styleClass="text" disabled="true" styleId="nomineeName3"   value="${viewList[0].nomineeName3}" /></td>
		              </tr>
					<tr>
				<tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName3" styleClass="text" disabled="true" styleId="nomineeMName3"   value="${viewList[0].nomineeMName3}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName3" styleClass="text" disabled="true" styleId="nomineeLName3"   value="${viewList[0].nomineeLName3}" /></td>
						</tr>
						<tr>
						<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth3" styleClass="text" disabled="true" styleId="dateOfbirth3"   value="${viewList[0].dateOfbirth3}" /></td>
					
					<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender3" property="gender3" styleClass="text" disabled="true" value="${viewList[0].gender3}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
						</tr>
					<tr>
						<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus3" property="smaritalStatus3" disabled="true" styleClass="text" value="${viewList[0].smaritalStatus3}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp3" styleId="relationshp3" styleClass="text"  disabled="true" value="${viewList[0].relationshp3}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
						</tr>
		   			<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage3" styleClass="text" styleId="percentage3" disabled="true" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${viewList[0].percentage3}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr3" styleClass="text" styleId="addr3" value="${allInsuranceData[0].addr3}"></html:textarea><td> --%>
	                     <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	                     <html:text property="saddressType3" styleClass="text" disabled="true" styleId="saddressType3"  value="${viewList[0].saddressType3}" />
	  					<%-- <html:select styleId="saddressType3" property="saddressType3" styleClass="text" disabled="true" value="${viewList[0].saddressType3}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select> --%>
						</td>
		           		
		           		
					</tr>
				<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr3" styleClass="text" styleId="addr3" disabled="true" value="${viewList[0].addr3}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet3" styleClass="text" styleId="sinsuranceStreet3" disabled="true" value="${viewList[0].sinsuranceStreet3}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea3" styleClass="text" disabled="true" styleId="sNomineeArea3" value="${viewList[0].sNomineeArea3}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity3" styleClass="text" disabled="true" styleId="sNomineeCity3" value="${viewList[0].sNomineeCity3}"></html:text>
	                    	 <html:hidden property="txtDistCode3" styleId="txtDistCode3" styleClass="text"  value="${viewList[0].txtDistCode3}"></html:hidden></td>
						<%--	<html:button property="distButton" styleId="districtButton" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity','sNomineeState','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
	                    
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td><html:text property="sNomineeState3" styleClass="text" disabled="true" styleId="sNomineeState3" value="${viewList[0].sNomineeState3}"></html:text>
	                   		 <html:hidden property="txtStateCode3" styleId="txtStateCode3" styleClass="text" value=" ${viewList[0].txtStateCode3}"></html:hidden></td> 
							<%--<html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState','','txtStateCode','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin3" styleClass="text" disabled="true" styleId="sNomineePin3" value="${viewList[0].sNomineePin3}"></html:text></td>
	                    </tr>
		   			</table>
		   			</td></tr>
		   			</table>
		   	</fieldset>
		   	
		   			   	
		   			   			   	<fieldset>	  
<legend>Fifth Nominee Detail</legend>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	<td width="20%"><bean:message key="lbl.sPrefix" /></td>
		            	<td width="35%">
	  					<html:select styleId="sPrefix4" property="sPrefix4" disabled="true" styleClass="text" value="${viewList[0].sPrefix4}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Mr</html:option>
							<html:option value="S">Miss</html:option>
							<html:option value="R">Mrs</html:option>
							<html:option value="Dr">Doctor</html:option>
							
					    </html:select>
						</td>
						
		            	<td width="20%"><bean:message key="lbl.SnomineeFName" /></td>
	                     <td ><html:text property="nomineeName4" styleClass="text" disabled="true" styleId="nomineeName4"   value="${viewList[0].nomineeName4}" /></td>
		              </tr>
					<tr>
				<tr>
						<td width="20%"><bean:message key="lbl.SnomineeMName" /></td>
	                     <td ><html:text property="nomineeMName4" styleClass="text" disabled="true" styleId="nomineeMName4"   value="${viewList[0].nomineeMName4}" /></td>
	                   
	                   
	                     <td width="20%"><bean:message key="lbl.SnomineeLName" /></td>
	                     <td ><html:text property="nomineeLName4" styleClass="text" disabled="true" styleId="nomineeLName4"   value="${viewList[0].nomineeLName4}" /></td>
						</tr>
						<tr>
						<td width="20%"><bean:message key="lbl.dob" /></td>
	                     <td ><html:text property="dateOfbirth4" styleClass="text" disabled="true" styleId="dateOfbirth4"   value="${viewList[0].dateOfbirth4}" /></td>
					
					<td width="20%"><bean:message key="lbl.gender" /></td>
		                <td width="35%">
	  					<html:select styleId="gender4" property="gender4" styleClass="text" disabled="true" value="${viewList[0].gender4}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Male</html:option>
							<html:option value="F">Female</html:option>
							
					    </html:select>
						</td>
						</tr>
					<tr>
						<td width="20%"><bean:message key="lbl.SmaritalStatus" /></td>
		                <td width="35%">
	  					<html:select styleId="smaritalStatus4" property="smaritalStatus4" disabled="true" styleClass="text" value="${viewList[0].smaritalStatus4}"  >
							<html:option value="">---Select---</html:option>
							<html:option value="M">Married</html:option>
							<html:option value="U">Unmarried</html:option>
							<html:option value="D">Divorced</html:option>
							<html:option value="W">Widowed</html:option>
							
					    </html:select>
						</td>
						<td width="20%"><bean:message key="lbl.relationShp" /></td>
						 <td width="35%">
	  					<html:select property="relationshp4" styleId="relationshp4" styleClass="text"  disabled="true" value="${viewList[0].relationshp4}"  >
   					    <html:option value="">--Select--</html:option>
   					    <logic:present name="relationshps">
					<logic:notEmpty name="relationshps">
					<html:optionsCollection name="relationshps" label="relationshp" value="relationshpId" />
					</logic:notEmpty>		
				</logic:present>
						</html:select>
						</td>
						</tr>
		   			<tr>
						
						<td width="20%"><bean:message key="lbl.sPercentage" /></td>
	                     <td ><html:text property="percentage4" styleClass="text" styleId="percentage4" disabled="true" onkeypress="return isPercentKey(evt);" onblur="return checkRate('percentage');" value="${viewList[0].percentage4}" /></td>
						<%-- <td width="20%"><bean:message key="lbl.addr" /></td>
	                    <td><html:textarea property="addr1" styleClass="text" styleId="addr1" value="${allInsuranceData[0].addr1}"></html:textarea><td> --%>
	                     <td width="20%"><bean:message key="lbl.sAddressType" /></td>
	                    <td width="35%">
	                     <html:text property="saddressType4" styleClass="text" disabled="true" styleId="saddressType4"  value="${viewList[0].saddressType4}" />
	  					<%-- <html:select styleId="saddressType4" property="saddressType4" disabled="true" styleClass="text" value="${viewList[0].saddressType4}"  >
	  					<html:option value="">---Select---</html:option>
							<option value="OFFICE">OFFICE</option>
							<option value="OTHER">OTHER</option>
							<option value="PERMANENT">PERMANENT</option>
							<option value="REGOFF">REGISTERED OFFICE</option>
							<option value="REI">RESIDENCE</option>
							<option value="RES">RESI CUM OFFICE</option>
							<option value="TP">THIRD PARTY</option></select>
					    </html:select> --%>
						</td>
		           		
					</tr>
				<tr>
						<td width="20%"><bean:message key="lbl.sNomineeStreet" /></td>
	                    <td><html:text property="addr4" styleClass="text" styleId="addr4" disabled="true" value="${viewList[0].addr4}"></html:text></td>
	                     <td width="20%"><bean:message key="lbl.sInsuranceStreet" /></td>
	                    <td><html:text property="sinsuranceStreet4" styleClass="text" styleId="sinsuranceStreet4" disabled="true" value="${viewList[0].sinsuranceStreet4}"></html:text></td>
	                    </tr>
	                    <tr>
		           		<td width="20%"><bean:message key="lbl.sNomineeArea" /></td>
	                    <td><html:text property="sNomineeArea4" styleClass="text" disabled="true" styleId="sNomineeArea4" value="${viewList[0].sNomineeArea4}"></html:text></td>
	                    <td width="20%"><bean:message key="lbl.sNomineeCity" /></td>
	                    <td><html:text property="sNomineeCity4" styleClass="text" disabled="true" styleId="sNomineeCity4" value="${viewList[0].sNomineeCity4}"></html:text>
	                    	 <html:hidden property="txtDistCode4" styleId="txtDistCode4" styleClass="text"  value="${viewList[0].txtDistCode4}"></html:hidden></td>
						<%--	<html:button property="distButton" styleId="districtButton" onclick="openLOVCommon(20,'cpInsuranceForm','sNomineeCity','sNomineeState','txtDistCode', 'txtStateCode','Please select state first!!!','clearDistrictLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
	                    
	                    </tr>
	                    <tr>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineeState" /></td>
	                    <td><html:text property="sNomineeState4" styleClass="text" disabled="true" styleId="sNomineeState4" value="${viewList[0].sNomineeState4}"></html:text>
	                   		 <html:hidden property="txtStateCode4" styleId="txtStateCode1" styleClass="text" value=" ${viewList[0].txtStateCode4}"></html:hidden></td> 
							<%--<html:button property="stateButton" styleId="stateButton" onclick="openLOVCommon(5,'cpInsuranceForm','sNomineeState','','txtStateCode','',' ','clearStateLovChildNew','hcommon');" value=" " styleClass="lovbutton"> </html:button> --%>
	                    
	                    <td width="20%"><bean:message key="lbl.sNomineePin" /></td>
	                    <td><html:text property="sNomineePin4" styleClass="text" disabled="true" styleId="sNomineePin4" value="${viewList[0].sNomineePin4}"></html:text></td>
	                    </tr>
		   			</table>
		   			</td></tr>
		   			</table>
		   	</fieldset>
		   		</fieldset>		
		   			

<table id="gridTable" width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table id="gridTable1" width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td> <b><bean:message key="contact.select" /></b></td>
        <td><center><b><bean:message key="lbl.borrowerName"/></b></center></td>
        <td><center><b><bean:message key="lbl.customerType"/></b></center></td>
        <td><center><b><bean:message key="lbl.dob"/></b></center></td>
        <td><center><b><bean:message key="lbl.age"/></b></center></td>
       <td><center><b><bean:message key="lbl.constitution"/></b></center></td>
	</tr>
	<tr>
		
	
		<logic:notEmpty name="customerDetailList">
		<logic:iterate name="customerDetailList" id="objcustomerDetailList" indexId="count">
		<tr class="white1">
		<td >
		<input type="checkbox" name="alchecking" id="allchk" disabled="disabled"  value="${objcustomerDetailList.customerId }"/>
    	 </td>
		<td >${objcustomerDetailList.borrowerName}</td>
		<td >${objcustomerDetailList.customerType }</td>
		<td>${objcustomerDetailList.dob }</td>
		<td>${objcustomerDetailList.age1 }</td>
		<td>${objcustomerDetailList.customerConstitution }</td>
		
		</tr>	
	 
	   	</logic:iterate>
		</logic:notEmpty>
		</tr>
	
 </table>    </td>
</tr>

</table>
		   			
	   			
		   					
		   			
		
	
	</fieldset>
	
	
	<fieldset>
		 <legend><bean:message key="lbl.insuranceDetails"/></legend>  
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
   
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td> <b><input type="checkbox" name="allchked" id="allchked" disabled="disabled" onclick="allChecked();"/><bean:message key="contact.select" /></b></td>
        <td><center><b><bean:message key="lbl.insuranceProvider"/></b></center></td>
        <td><center><b><bean:message key="lbl.insuranceProduct"/></b></center></td>
        <td><center><b><bean:message key="lbl.borrowerName"/></b></center></td>
        <td><center><b><bean:message key="lbl.insurancePremium"/></b></center></td>
       
	</tr>
	
	<tr>
		
	<logic:present name="saveInsuranceData">
		<logic:notEmpty name="saveInsuranceData">
		<logic:iterate name="saveInsuranceData" id="objSaveInsuranceData" indexId="count">
		<tr class="white1">
		<td >
		<input type="checkbox" name="chk" id="chk" disabled="disabled"   value="${objSaveInsuranceData.insuranceId }"/>
    	 </td>
		<td ><a href="#" id="anchor0" onclick="editInsuranceDetails(${objSaveInsuranceData.insuranceId});">${objSaveInsuranceData.insuranceProvider}</a></td>
		<td >${objSaveInsuranceData.insuranceProduct }</td>
		<td>${objSaveInsuranceData.borrowerName }</td>
		<td>${objSaveInsuranceData.insurancePremium }</td>
		
		
		
		</tr>	
	 
	   	</logic:iterate>
		</logic:notEmpty>
		
		
	
		
		
		</logic:present>
		</tr>
		
	
		   			
		   			
		</table>    </td>
</tr>   			
		   			
		   			
 </table>  			   			

</fieldset>	
</html:form>`
</div>


</logic:present>


</div>
<script>
	parent.menu.document.test.getFormName.value = document.getElementById("cmInsuranceForm").id;
</script>
</body>
</html:html>

<logic:present name="sms">
<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<bean:message key="msg.DataSaved" />');
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert('<bean:message key="msg.DataNotSaved" />');
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='totNotmatch')
	{
		alert('<bean:message key="msg.totNotMatch" />');
	
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='assetPremium')
	{
		alert('<bean:message key="msg.assetPremium" />');
	
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='combiExist')
	{
		alert('<bean:message key="msg.combiExist" />');
	
	}
	<%-- else if('<%=request.getAttribute("sms").toString()%>'=='collateralPremium')
	{
		alert('<bean:message key="msg.collateralPremium" />');
	
	} --%>
</script>
</logic:present>
<logic:present name="delete">
<script type="text/javascript">
	if('<%=request.getAttribute("delete").toString()%>'=='S')
	{
		alert('<bean:message key="msg.deleteInsurance" />');
	}
	else if('<%=request.getAttribute("delete").toString()%>'=='E')
	{
		alert('<bean:message key="msg.notDeleteInsurance" />');
	}
	
</script>
</logic:present>
