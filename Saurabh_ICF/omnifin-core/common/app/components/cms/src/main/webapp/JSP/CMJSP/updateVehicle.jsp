<!--Author Name : Richa-->
<!--Date of Creation : 21-july-2013-->
<!--Purpose  : Information of Update vehical-->
<!--Documentation : -->

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp"%>
<%@include file="/JSP/commonIncludeContent.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>

<head>
	<title><bean:message key="a3s.noida" />
	</title>
	
		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=path%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css"
		rel="stylesheet" />
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/updateAsset.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
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

<body onload="enableAnchor();">
	<input type="hidden" name="contextPath" id="contextPath"
		value="<%=request.getContextPath()%>" />
		<html:hidden property="saveflag" styleId="saveflag" value="N" />
	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" id="businessdate"
		value='${userobject.businessdate}' />
	<logic:present name="image">
		<input type="hidden" id="CalImage"
			value='<%=request.getContextPath()%>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
		<input type="hidden" id="CalImage"
			value='<%=request.getContextPath()%>/images/theme1/calendar.gif' />
	</logic:notPresent>
	
	<logic:notPresent name="sessionAssetVehicalList">
	
	<html:form styleId="updateform" method="post"
		action="/updateAssetEdit">
		<div align="center" class="opacity" style="display: none;"
			id="processingImage"></div>

			<fieldset>
				<legend>
					<bean:message key="lbl.updateAssetDetails" />
				</legend>
				<table width="100%">
			
					<html:hidden property="lbxLoanId" styleClass="text"
						styleId="lbxLoanId" value="${requestScope.list[0].lbxLoanId}" />
					<html:hidden property="assetId" styleClass="text" styleId="assetId"
						value="${requestScope.list[0].assetId}" />
					<input type="hidden" name="saveFirst" id='saveFirst'
						value="${requestScope.saveFirst}" />
			
					<tr>
						<td>
							<bean:message key="lbl.loanNo" />
						</td>
						<td>
						
							<html:text property="loanNo" styleId="loanNo" styleClass="text"
								readonly="true" tabindex="-1" value="${requestScope.list[0].loanNo}" />
							<html:hidden property="lbxLoanId" styleId="lbxLoanId" value="${requestScope.list[0].lbxLoanId}"/>
							<logic:present name="newMode">
								<html:button property="loanNoButton" styleId="loanNoButton"
									 
							 	 onclick="closeLovCallonLov1();openLOVCommon(2044,'updateform','loanNo','','lbxLoanId','','','getValuesForUpdateLOANpdd','customerName','assetId');"
									value=" " styleClass="lovbutton">
								</html:button>
							</logic:present>



						</td>


						<td>
							<bean:message key="lbl.customerName"></bean:message>
						</td>
						<td>
							<html:text property="customerName" styleId="customerName"
								styleClass="text" readonly="true" tabindex="-1" value="${requestScope.list[0].customerName}"/>

						</td>

					</tr>

					<tr>

						<td>
							<bean:message key="lbl.vechicleDescription"></bean:message>
						</td>
						<td>
							<html:text property="assetsCollateralDesc"
								styleId="assetsCollateralDesc" styleClass="text" value="${requestScope.list[0].assetsCollateralDesc}"/>
                          <logic:present name="newMode">
							<html:button property="assetButton" styleId="assetButton"
								onclick="openLOVCommon(2038,'updateform','assetId','loanNo','lbxAssetId', 'lbxLoanId','Please Select Loan No first','getValuesForUpdateAsset','assetsCollateralDesc')"
								value=" " styleClass="lovbutton"></html:button>
						 </logic:present>
							<input type="hidden" name="lbxAssetId" id="lbxAssetId"
								readonly="readonly" value="${requestScope.list[0].lbxAssetId}" />

						</td>

						<td>
							<bean:message key="lbl.assetNature" />
						</td>
						<td>
							<html:text property="assetNature" readonly="true"
								styleClass="text" styleId="assetNature" maxlength="10"
								value="${requestScope.list[0].assetNature}" readonly="true" />
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.vehicleMake" />
						</td>
						<td>
							<html:text property="vehicleMake" styleClass="text"
								styleId="vehicleMake" maxlength="50"
								value="${requestScope.list[0].vehicleMake}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.vehicleModel" />
						</td>
						<td>
							<html:text property="vehicleModel" styleClass="text"
								styleId="vehicleModel" maxlength="50"
								value="${requestScope.list[0].vehicleModel}" readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.manufact" />
						</td>
						<td>
							<html:text property="assetManufact" styleClass="text"
								styleId="assetManufact" maxlength="50"
								value="${requestScope.list[0].assetManufact}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.supplier" />
						</td>
						<td>
							<html:text property="machineSupplier" styleClass="text"
								styleId="machineSupplier" maxlength="50"
								value="${requestScope.list[0].machineSupplier}" readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.usesType" />
						</td>
						<td>
							<html:text property="usageType" styleClass="text"
								styleId="usageType" maxlength="50"
								value="${requestScope.list[0].usageType}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.state" />
						</td>
						<td>
							<html:text property="txtStateCode" styleClass="text"
								styleId="txtStateCode" maxlength="50"
								value="${requestScope.list[0].txtStateCode}" readonly="true" />
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.vehicleSecurityMarginDF" />
						</td>
						<td>
							<html:text property="collateralSecurityMarginDF"
								styleClass="text" styleId="collateralSecurityMarginDF"
								maxlength="50"
								value="${requestScope.list[0].collateralSecurityMarginDF}"
								readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.vehicleSecurityMargin" />
						</td>
						<td>
							<html:text property="collateralSecurityMargin" styleClass="text"
								styleId="collateralSecurityMargin" maxlength="50"
								value="${requestScope.list[0].collateralSecurityMargin}"
								readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.loanAmount" />
						</td>
						<td>
							<html:text property="loanAmount" styleClass="text"
								styleId="loanAmount" maxlength="50"
								value="${requestScope.list[0].loanAmount}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.vehicleCost" />
						</td>
						<td>
							<html:text property="vehicleCost" styleClass="text"
								styleId="vehicleCost" maxlength="50"
								value="${requestScope.list[0].vehicleCost}" readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.vehiclediscount" />
						</td>
						<td>
							<html:text property="vehicleDiscount" styleClass="text"
								styleId="vehicleDiscount" maxlength="50"
								value="${requestScope.list[0].vehicleDiscount}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.vehicleValue" />
						</td>
						<td>
							<html:text property="assetsCollateralValue" styleClass="text"
								styleId="assetsCollateralValue" maxlength="50"
								value="${requestScope.list[0].assetsCollateralValue}"
								readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.gridValue" />
						</td>
						<td>
							<html:text property="gridValue" styleClass="text"
								styleId="gridValue" maxlength="50"
								value="${requestScope.list[0].gridValue}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.valuationCost" />
						</td>
						<td>
							<html:text property="valuationCost" styleClass="text"
								styleId="valuationCost" maxlength="50"
								value="${requestScope.list[0].valuationCost}" readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.securityTypes" />
						</td>
						<td>
							<html:text property="securityTypes" styleClass="text"
								styleId="securityTypes" maxlength="50"
								value="${requestScope.list[0].securityTypes}" readonly="true" />
						</td>
						<!-- Parvez starts for Author Remarks -->
						<td>
							<bean:message key="lbl.authorRemarks" />
						</td>
						<td>
							<html:text property="authorRemarks" styleClass="text"
								styleId="authorRemarks" maxlength="50"
								value="${requestScope.list[0].authorRemarks}" readonly="true" />
						</td>

</tr>
<tr>
						<td>
							<bean:message key="lbl.standard" />
						</td>
						<logic:iterate name="list" id="subList" length="1">
						
								<logic:equal value="Y" name="subList" property="assetStandard">
									<td>
										<input type="checkbox" name="assetStandard" id="assetStandard"
											disabled="disabled" checked="checked" />
									</td>
								</logic:equal>
								<logic:notEqual value="Y" name="subList" property="assetStandard">
									<td>
										<input type="checkbox" name="assetStandard" id="assetStandard"
											disabled="disabled" />
									</td>
								</logic:notEqual>
							</logic:iterate>

					</tr>
				</table>
          </fieldset>
		
		<fieldset>	
		     <legend>
					<bean:message key="lbl.InvoiceUpdate" />
				</legend>
				 <logic:present name="invoiceCheckBox">
			
				<table width="107%">
				  <tr>
				       <td>
							<bean:message key="lbl.InvoiceUpdate" />
						</td>
						<td>
						   <input type="checkbox" name="invoiceUpdateCheckBox" id="invoiceUpdateCheckBox" onclick="invoiceUpdateActivate();getPddUpdateData('INVOICE');" checked="checked"/>
					      
						</td>
				  </tr>
					<tr >
						<td>
							<bean:message key="lbl.invoiceNumber" />
						
						</td>
						<td >
							<html:text property="invoiceNumber" styleClass="text"
								styleId="invoiceNumber" maxlength="50"
								onchange="saveAfterChange();"
								value="${requestScope.list[0].invoiceNumber}" />
						</td>

						<td>
							<bean:message key="lbl.invoiceDate" />
							
							<font color="red">*</font>
						</td>
						<td width="32%">
							
							    <html:text property="vehicleInvoiceDate" styleClass="text"
								styleId="vehicleInvoiceDate" maxlength="50"
								value="${requestScope.list[0].vehicleInvoiceDate}"
								onchange="checkDate('vehicleInvoiceDate');changeInvoiceUpdate();" />
							
						</td>

					</tr>
					<tr>
					   <td>
							<bean:message key="lbl.invoiceAmount" />
							<font color="red">*</font>
						</td>
						<td>
							<html:text property="invoiceAmount" styleClass="text"
								styleId="invoiceAmount" maxlength="22"
								onkeypress="return isNumberKey(event);"
								onchange="saveAfterChange();" 
								value="${requestScope.list[0].invoiceAmount}" />
						</td>
					</tr>
					
		            <tr>
						<td>
							<bean:message key="lbl.chasisNumber" />
							<font color="red">*</font>
						</td>
						<td>
							<html:text property="vehicleChesisNo" styleClass="text"
								styleId="vehicleChesisNo" maxlength="50"
								onchange="saveAfterChange();" 
								value="${requestScope.list[0].vehicleChesisNo}" />
						</td>

						<td>
							<bean:message key="lbl.engineNumber" />
							<font color="red">*</font>
						</td>
						<td width="25%">
							<html:text property="engineNumber" styleClass="text"
								styleId="engineNumber" maxlength="50" 
								onchange="saveAfterChange();"
								value="${requestScope.list[0].engineNumber}" />
						</td>

					</tr>
			</table>
			</logic:present>
			 <logic:notPresent name="invoiceCheckBox">
			 
			
			<table width="107%">
				  <tr>
				       <td>
							<bean:message key="lbl.InvoiceUpdate" />
						</td>
						<td>
						   <input type="checkbox" name="invoiceUpdateCheckBox" id="invoiceUpdateCheckBox" onclick="invoiceUpdateActivate();getPddUpdateData('INVOICE');" />
					     
						</td>
				  </tr>
					<tr >
						<td>
							<bean:message key="lbl.invoiceNumber" />
						
						</td>
						<td >
							<html:text property="invoiceNumber" styleClass="text"
								styleId="invoiceNumber" maxlength="50" 
								onchange="saveAfterChange();"
								readonly="true"
								value="${requestScope.list[0].invoiceNumber}" />
						</td>

						<td>
							<bean:message key="lbl.invoiceDate" />
							
						</td>
						<td width="32%">
							
							    <html:text property="vehicleInvoiceDate" styleClass="text"
								styleId="vehicleInvoiceDate" maxlength="50" 
								value="${requestScope.list[0].vehicleInvoiceDate}"
								readonly="true"
								onchange="checkDate('vehicleInvoiceDate');changeInvoiceUpdate();" />
							
						</td>

					</tr>
					<tr>
					   <td>
							<bean:message key="lbl.invoiceAmount" />
							<font color="red">*</font>
						</td>
						<td>
							<html:text property="invoiceAmount" styleClass="text"
								styleId="invoiceAmount" maxlength="22"
								onkeypress="return isNumberKey(event);"
								onchange="saveAfterChange();"
								readonly="true" 
								value="${requestScope.list[0].invoiceAmount}" />
						</td>
					</tr>
					
		            <tr>
						<td>
							<bean:message key="lbl.chasisNumber" />
							<font color="red">*</font>
						</td>
						<td>
							<html:text property="vehicleChesisNo" styleClass="text"
								styleId="vehicleChesisNo" maxlength="50"
								onchange="saveAfterChange();" 
								readonly="true"
								value="${requestScope.list[0].vehicleChesisNo}" />
						</td>

						<td>
							<bean:message key="lbl.engineNumber" />
							<font color="red">*</font>
						</td>
						<td width="25%">
							<html:text property="engineNumber" styleClass="text"
								styleId="engineNumber" maxlength="50" 
								onchange="saveAfterChange();"
								readonly="true"
								value="${requestScope.list[0].engineNumber}" />
						</td>

					</tr>
			</table>
			</logic:notPresent>
       </fieldset>	
				<fieldset>	
		        <legend>
					<bean:message key="lbl.rcUpdate" />
				</legend>
				<logic:present name="rcCheckBox">
			
				<table width="108%">
				   <tr>
				       <td>
							<bean:message key="lbl.rcUpdate" />
						</td>
						<td>
						     <input type="checkbox" name="rcUpdateCheckBox" id="rcUpdateCheckBox" checked="checked"  onclick="rcUpdateActivate();getPddUpdateData('RC');"	/>
							
						</td>
				  </tr>
					<tr>
						<td>
							<bean:message key="lbl.rcReceived" />
							<font color="red">*</font>
						</td>
						<td width="40%">
							<html:select property="rcReceived" styleId="rcReceived" onclick="changeRcUpdate();"
								styleClass="text" onchange="saveAfterChange();rcDateMandatory()"
								value="${requestScope.list[0].rcReceived}">
								<html:option value="">---Select---</html:option>
								<html:option value="Y">YES</html:option>
								<html:option value="N">NO</html:option>
							</html:select>
						</td>

						<td>
							<bean:message key="lbl.rcReceivedDate" />
						</td>
						<td width="28%">
							<html:text property="rcReceivedDate" styleClass="text"
								styleId="rcReceivedDate" maxlength="50"
								value="${requestScope.list[0].rcReceivedDate}"
								onchange="checkDate('rcReceivedDate'); saveAfterChange(); rcDateMandatory();changeRcUpdate();"
								readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.registrationNumber" />
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:text property="vehicleRegNo" styleClass="text"
								styleId="vehicleRegNo" maxlength="50"
								onchange="saveAfterChange();" 
								value="${requestScope.list[0].vehicleRegNo}" />
						</td>

						<td>
							<bean:message key="lbl.registrationDate" />
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:text property="vehicleRegDate" styleClass="text"
								styleId="vehicleRegDate" maxlength="50"
								value="${requestScope.list[0].vehicleRegDate}"
								onchange="checkDate('vehicleRegDate'); saveAfterChange(); changeRcUpdate();"
								readonly="true" />
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.yearofManufacture" />
							(YYYY)
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:text property="vehicleYearOfManufact" styleClass="text"
								styleId="vehicleYearOfManufact" maxlength="50" 
								value="${requestScope.list[0].vehicleYearOfManufact}"
								onchange="saveAfterChange();checkFormateYear(this.value,this.id);" />
						</td>

						<td>
							<bean:message key="lbl.vehicleOwner" />
						</td>
						<td width="33%">
							<html:text property="vehicleOwner" styleClass="text"
								styleId="vehicleOwner" maxlength="50" 
								value="${requestScope.list[0].vehicleOwner}" />
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.permitReceived" />
						</td>
						<td width="33%">
							<html:select property="permitReceived" styleId="permitReceived"
								styleClass="text" onclick="changeRcUpdate();"
								onchange="receivedDateMandatory();saveAfterChange();"
								value="${requestScope.list[0].permitReceived}">
								<html:option value="">---Select---</html:option>
								<html:option value="Y">YES</html:option>
								<html:option value="N">NO</html:option>
							</html:select>
						</td>

						<td>
							<bean:message key="lbl.permitReceivedDate" />
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:hidden property="permitExpiryDate" styleClass="text"
								styleId="permitExpiryDate"
								value="${requestScope.list[0].permitReceivedDate}" />
							<html:text property="permitReceivedDate" styleClass="text"
								styleId="permitReceivedDate" maxlength="50" 
								value="${requestScope.list[0].permitReceivedDate}"
								onchange="checkDate('permitReceivedDate');receivedDateMandatory();saveAfterChange();changeRcUpdate();"
								readonly="true" />
						</td>

					</tr>
					
<%-- Changes Start by Kumar Aman --%>					
					<tr>
					
					<td>
							<bean:message key="lbl.endorsementdate" />
					</td>

						<td>
							<html:text property="endorsementdate" styleClass="text"
								styleId="endorsementdate" maxlength="50"  readonly="true"
								value="${requestScope.list[0].endorsementdate}"	 
								/>

						</td>

					</tr>
<%-- Changes End by Kumar Aman --%>	
				</table>
				</logic:present>
				<logic:notPresent name="rcCheckBox">
		
				<table width="108%">
				   <tr>
				       <td>
							<bean:message key="lbl.rcUpdate" />
						</td>
						<td>
						     <input type="checkbox" name="rcUpdateCheckBox" id="rcUpdateCheckBox"  onclick="rcUpdateActivate();getPddUpdateData('RC')"	/>
							
						</td>
				  </tr>
					<tr>
						<td>
							<bean:message key="lbl.rcReceived" />
							<font color="red">*</font>
						</td>
						<td width="40%">
							<html:select property="rcReceived" styleId="rcReceived" onclick="changeRcUpdate();"
								styleClass="text" onchange="saveAfterChange();rcDateMandatory()"
								value="${requestScope.list[0].rcReceived}">
								<html:option value="">---Select---</html:option>
								<html:option value="Y">YES</html:option>
								<html:option value="N">NO</html:option>
							</html:select>
						</td>

						<td>
							<bean:message key="lbl.rcReceivedDate" />
						</td>
						<td width="28%">
							<html:text property="rcReceivedDate" styleClass="text"
								styleId="rcReceivedDate" maxlength="50"
								value="${requestScope.list[0].rcReceivedDate}"
								readonly="true"
								onchange="checkDate('rcReceivedDate'); saveAfterChange(); rcDateMandatory();changeRcUpdate();"
								 />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.registrationNumber" />
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:text property="vehicleRegNo" styleClass="text"
								styleId="vehicleRegNo" maxlength="50"
								onchange="saveAfterChange();" 
								readonly="true"
								value="${requestScope.list[0].vehicleRegNo}" />
						</td>

						<td>
							<bean:message key="lbl.registrationDate" />
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:text property="vehicleRegDate" styleClass="text"
								styleId="vehicleRegDate" maxlength="50"
								value="${requestScope.list[0].vehicleRegDate}"
								readonly="true"
								onchange="checkDate('vehicleRegDate'); saveAfterChange(); changeRcUpdate();"
								/>
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.yearofManufacture" />
							(YYYY)
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:text property="vehicleYearOfManufact" styleClass="text"
								styleId="vehicleYearOfManufact" maxlength="50" 
								value="${requestScope.list[0].vehicleYearOfManufact}"
								readonly="true"
								onchange="saveAfterChange();checkFormateYear(this.value,this.id);" />
						</td>

						<td>
							<bean:message key="lbl.vehicleOwner" />
						</td>
						<td width="33%">
							<html:text property="vehicleOwner" styleClass="text"
								styleId="vehicleOwner" maxlength="50" 
								readonly="true"
								value="${requestScope.list[0].vehicleOwner}" />
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.permitReceived" />
						</td>
						<td width="33%">
							<html:select property="permitReceived" styleId="permitReceived"
								styleClass="text" onclick="changeRcUpdate();"
								onchange="receivedDateMandatory();saveAfterChange();"
								value="${requestScope.list[0].permitReceived}">
								<html:option value="">---Select---</html:option>
								<html:option value="Y">YES</html:option>
								<html:option value="N">NO</html:option>
							</html:select>
						</td>

						<td>
							<bean:message key="lbl.permitReceivedDate" />
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:hidden property="permitExpiryDate" styleClass="text"
								styleId="permitExpiryDate"
								value="${requestScope.list[0].permitReceivedDate}" />
							<html:text property="permitReceivedDate" styleClass="text"
								styleId="permitReceivedDate" maxlength="50" 
								value="${requestScope.list[0].permitReceivedDate}"
								readonly="true"
								onchange="checkDate('permitReceivedDate');receivedDateMandatory();saveAfterChange();changeRcUpdate();"
								/>
						</td>
					</tr>
<%-- Changes Start by Kumar Aman --%>					
					<tr>
					
					     <td>
							<bean:message key="lbl.endorsementdate" />
							
						</td>


						<td>
							<html:text property="endorsementdate" styleClass="text"
								styleId="endorsementdate" maxlength="50"  readonly="true"
								value="${requestScope.list[0].endorsementdate}"	
								 />

						</td>
					</tr>
<%-- Changes End by Kumar Aman --%>					
				</table>
				</logic:notPresent>
				
          </fieldset>	
				<fieldset>	
		        <legend>
					<bean:message key="lbl.insuranceUpdate" />
				</legend>
				
				 <logic:present name="insuranceCheckBox">
			
				  <table width="100%">
				     <tr>
				       <td>
							<bean:message key="lbl.insuranceUpdate" />
						</td>
						<td>
						    <input type="checkbox" name="insuranceUpdateCheckBox" id="insuranceUpdateCheckBox"  onclick="insuranceUpdateActivate();getPddUpdateData('INSURANCE');" checked="checked" />
							
						</td>
				    </tr>
					<tr>
											<%--	<td>
							<bean:message key="lbl.Insurer" />
							<font color="red">*</font>
						</td>
						<td width="25%">
							<html:text property="vehicleInsurer" styleClass="text"
								styleId="vehicleInsurer" maxlength="50" 
								onchange="saveAfterChange();"
								value="${requestScope.list[0].vehicleInsurer}" />
						</td> --%>

						
						<td><bean:message key="lbl.Insurer"></bean:message> <font color="red">* </font></td>
		   <td>
		    <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="50"  value="${requestScope.list[0].insuranceAgency}" readonly="true" tabindex="-1"/>
            <html:hidden property="lbxInsuranceAgency" styleId="lbxInsuranceAgency"/>
            <html:button property="insuranceButton" styleId="insuranceButton" onclick="openLOVCommon(81,'assetMakerForm','insuranceAgency','','', '','','','lbxInsuranceAgency')" value=" " styleClass="lovbutton"></html:button>
		  </td>	
						<td>
							<bean:message key="lbl.insuredDate" />
							<font color="red">*</font>
						</td>
						<td >
							<html:text property="vehicleInsureDate" styleClass="text"
								styleId="vehicleInsureDate" maxlength="50" readonly="true"
								value="${requestScope.list[0].vehicleInsureDate}"
								onchange="checkDate('vehicleInsureDate'); saveAfterChange();changeInsuranceUpdate();"
								 />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.idv" />
						</td>
						<td width="25%">
							<html:text property="idv" styleClass="text" styleId="idv"
								maxlength="22" onkeypress="return isNumberKey(event);"
								onchange="saveAfterChange();" value="${requestScope.list[0].idv}" />
						</td>

<%--kumar aman change start --%>
						<td>
							<bean:message key="lbl.renewaldate" />
							
						</td>
						<td >
							<html:text property="renewaldate" styleClass="text"
								styleId="renewaldate" maxlength="50" readonly="true"
								value="${requestScope.list[0].renewaldate}"
								onchange="checkDate('renewaldate'); saveAfterChange();changeInsuranceUpdate();"
								 />
								
						</td>

					</tr>
					
					<tr>
					<td>
							<bean:message key="lbl.premamt" />
						</td>
						<td>
							<html:text property="premamt" styleClass="text"
								styleId="premamt" maxlength="22"
								onkeypress="return isNumberKey(event);"
								onchange="saveAfterChange();"
								 value="${requestScope.list[0].premamt}" />
							
						</td>
						
						<td>
							<bean:message key="lbl.premdate" />
						</td>
						<td >
							<html:text property="premdate" styleClass="text"
								styleId="premdate" maxlength="50" readonly="true"
								value="${requestScope.list[0].premdate}"
								onchange="checkDate('premdate'); saveAfterChange();changeInsuranceUpdate();"
								 />
								
						</td>
						
						</tr>
<%-- kumar aman change end --%>					
               </table>
               </logic:present>
               <logic:notPresent name="insuranceCheckBox">
              
               			  <table width="100%">
				     <tr>
				       <td>
							<bean:message key="lbl.insuranceUpdate" />
						</td>
						<td>
						  <input type="checkbox" name="insuranceUpdateCheckBox" id="insuranceUpdateCheckBox"  onclick="insuranceUpdateActivate();getPddUpdateData('INSURANCE');" />
						
						</td>
				    </tr>
					<tr>
								<!--	<td>
							<bean:message key="lbl.Insurer" />
							<font color="red">*</font>
						</td>
						<td width="25%">
							<html:text property="vehicleInsurer" styleClass="text"
								styleId="vehicleInsurer" maxlength="50" 
								onchange="saveAfterChange();"
								readonly="true"
								value="${requestScope.list[0].vehicleInsurer}" />
						</td> -->

<td><bean:message key="lbl.Insurer"></bean:message> <font color="red">* </font></td>
		   <td>
		    <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="50"  value="" readonly="true" tabindex="-1"/>
            <html:hidden property="lbxInsuranceAgency" styleId="lbxInsuranceAgency"/>
            <html:button property="insuranceButton" styleId="insuranceButton" onclick="openLOVCommon(81,'assetMakerForm','insuranceAgency','','', '','','','lbxInsuranceAgency')" value=" " styleClass="lovbutton"></html:button>
		  </td>
						<td>
							<bean:message key="lbl.insuredDate" />
							<font color="red">*</font>
						</td>
						<td >
							<html:text property="vehicleInsureDate" styleClass="text"
								styleId="vehicleInsureDate" maxlength="50"
								value="${requestScope.list[0].vehicleInsureDate}"
								readonly="true"
								onchange="checkDate('vehicleInsureDate'); saveAfterChange();changeInsuranceUpdate();"
								 />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.idv" />
						</td>
						<td width="25%">
							<html:text property="idv" styleClass="text" styleId="idv"
								maxlength="22" onkeypress="return isNumberKey(event);"
								onchange="saveAfterChange();" 
								readonly="true"
								value="${requestScope.list[0].idv}" />
						</td>

						

					<%--kumar aman change start --%>
						<td>
							<bean:message key="lbl.renewaldate" />
							
						</td>
						<td >
							<html:text property="renewaldate" styleClass="text"
								styleId="renewaldate" maxlength="50" readonly="true"
								value="${requestScope.list[0].renewaldate}" readonly="true" 
								onchange="checkDate('renewaldate'); saveAfterChange();changeInsuranceUpdate();"
								 />
						</td>

					</tr>
					
					<tr>
					<td>
							<bean:message key="lbl.premamt" />
						</td>
						<td>
							<html:text property="premamt" styleClass="text"
								styleId="premamt" maxlength="22"
								onkeypress="return isNumberKey(event);"
								onchange="saveAfterChange();" value="${requestScope.list[0].premamt}" />
						</td>
						
						<td>
							<bean:message key="lbl.premdate" />
						</td>
						<td >
							<html:text property="premdate" styleClass="text"
								styleId="premdate" maxlength="50" readonly="true"
								value="${requestScope.list[0].premdate}" 
								onchange="checkDate('premdate'); saveAfterChange();changeInsuranceUpdate();"
								 />
						</td>
						
						</tr>
<%-- kumar aman change end --%>	
               </table>
               </logic:notPresent>
               
		</fieldset>
				
				<table>
					<tr>
						<td>
							<button type="button" name="Submit20" id="save"
								class="topformbutton2" title="Alt+V" accesskey="V"
								onclick="return fnUpdateAsset('P');">
								<bean:message key="button.save" />
							</button>
						</td>

						<td>
							<button type="button" name="Forward" id="Forward"
								class="topformbutton2" title="Alt+V" accesskey="V"
								onclick="return fnUpdateAsset('F');">
								<bean:message key="button.forward" />
							</button>
						</td>
						<td>
							<button type="button" name="delete" id="delete"
								class="topformbutton2" title="Alt+D" accesskey="D"
								onclick="return fnDeleteAsset();">
								<bean:message key="button.delete" />
							</button>
						</td>

					</tr>

				</table>
<%--Added New Table For View Per Veichle Details Start ------------sarvesh ----------------------------------%>

<%--  <fieldset>
   <legend><bean:message key="lbl.assetDetails"/></legend> 

  <logic:present name="assetlist">
  <logic:notEmpty name="assetlist">
  <display:table  id="assetlist"  name="assetlist" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${assetlist[0].totalRecordSize}" requestURI="/updateAssetEdit.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="pddLbxLoanId"  titleKey="lbl.loanNo"  sortable="true"  />
    <display:column property="pddassetId" titleKey="lbl.assetId"  sortable="true"  />
    <display:column property="pddassetsCollateralDesc" titleKey="lbl.vechicleDescription"  sortable="true"  />
    <display:column property="pddvehicleChesisNo" titleKey="lbl.chasisNumber"  sortable="true"  />
    <display:column property="pddengineNumber" titleKey="lbl.engineNumber"  sortable="true"  />
    <display:column property="pddvehicleRegNo" titleKey="lbl.registrationNo"  sortable="true"  />
     
   
</display:table>
</logic:notEmpty> 
<logic:empty name="assetlist">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="2" cellspacing="1">
								<tr class="white2">
								<td  align="center">
									<b><bean:message key="lbl.loanNo" /> <br> </b>
							    </td>
									<td  align="center">
									<b><bean:message key="lbl.assetId" /> <br> </b>
									</td>
																		
									<td align="center">
										<b><bean:message key="lbl.vechicleDescription" /> <br> </b>
									</td>
									<td align="center">
										<b><bean:message key="lbl.chasisNumber" /> <br> </b>
									</td>
									<td align="center">
										<b><bean:message key="lbl.engineNumber" /> <br> </b>
									</td>
									<td align="center">
										<b><bean:message key="lbl.registrationNo" /> <br> </b>
									</td>
									
							</tr>
			                <tr class="white2" >
	                                  <td colspan="6"><bean:message key="lbl.noDataFound" /></td>
                            </tr>
			            </table>
						</td>
					</tr>
				</table>
</logic:empty>

  </logic:present>
</fieldset> --%>


<logic:present name="assetlist">
		<fieldset>
			<legend>
				<bean:message key="lbl.assetDetails" />
			</legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="gridtd">
							<input type="hidden" name="psize" id="psize" />
							<input type="hidden" name="pcheck" id="pcheck" />
							<table id="gridtable" style="width: 100%;" border="0" cellspacing="1" cellpadding="4">
								<tr class="white2">
									<td><b><bean:message key="lbl.loanNo"/></b></td>
									<td><b><bean:message key="lbl.loanId"/></b></td>
									<td><b><bean:message key="lbl.assetId"/></b></td>
							        <td><b><bean:message key="lbl.vechicleDescription"/></b></td>
									<td><b><bean:message key="lbl.chasisNumber"/></b></td>								      
								    <td><b><bean:message key="lbl.engineNumber"/></b></td>
									<td><b><bean:message key="lbl.registrationNo"/></b></td>  
									<%-- <td><b><bean:message key="lbl.yearofManufacture"/></b></td>
									<td><b><bean:message key="lbl.assetNature"/></b></td>  
								    <td><b><bean:message key="lbl.vehicleOwner"/></b></td> --%>									
																			
								</tr>
									<logic:iterate name="assetlist"
										id="assetlist" indexId="i">
										<tr class="white1" style="width: 25px;">
											<td>${assetlist.loanNo }</td>
											<td>${assetlist.pddLbxLoanId} 
											</td>
											<td>${assetlist.pddassetId}
											</td>
											<td>${assetlist.pddassetsCollateralDesc} 
											</td>
											<td>${assetlist.pddvehicleChesisNo} 
											</td>
											<td>${assetlist.pddengineNumber} 
											</td>
											<td>${assetlist.pddvehicleRegNo} 
											</td>
											<%-- <td>${assetlist.yearofManufacture}
											</td>
											<td>${assetlist.vehicleModel} 
											</td>
											<td>${assetlist.vehicleOwner}
											</td> --%>
											
										</tr>
								</logic:iterate>		
							</table>
						</td>
					</tr>				
				</table>	
		</fieldset>
	</logic:present>




<%--Added New Table For View Per Veichle Details End --%>

</html:form>
</logic:notPresent>




<%--      	view mode										 --%>
<logic:present name="sessionAssetVehicalList">
	
	<html:form styleId="updateform" method="post"
		action="/updateAssetEdit">
		<div align="center" class="opacity" style="display: none;"
			id="processingImage"></div>

			<fieldset>
				<legend>
					<bean:message key="lbl.updateAssetDetails" />
				</legend>
				<table width="100%">
			
				
					<tr>
						<td>
							<bean:message key="lbl.loanNo" />
						</td>
						<td>
						
							<html:text property="loanNo" styleId="loanNo" styleClass="text"
								readonly="true" tabindex="-1" value="${sessionAssetVehicalList[0].loanNo}"/>
							
						</td>


						<td>
							<bean:message key="lbl.customerName"></bean:message>
						</td>
						<td>
							<html:text property="customerName" styleId="customerName"
								styleClass="text" readonly="true" tabindex="-1" value="${sessionAssetVehicalList[0].customerName}"/>

						</td>

					</tr>

					<tr>

						<td>
							<bean:message key="lbl.vechicleDescription"></bean:message>
						</td>
						<td>
							<html:text property="assetsCollateralDesc"
								styleId="assetsCollateralDesc" readonly="true" styleClass="text" value="${sessionAssetVehicalList[0].assetsCollateralDesc}"/>
                         
						</td>

						<td>
							<bean:message key="lbl.assetNature" />
						</td>
						<td>
							<html:text property="assetNature"
								styleClass="text" styleId="assetNature" maxlength="10"
								value="${sessionAssetVehicalList[0].assetNature}" readonly="true" />
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.vehicleMake" />
						</td>
						<td>
							<html:text property="vehicleMake" styleClass="text"
								styleId="vehicleMake" maxlength="50"
								value="${sessionAssetVehicalList[0].vehicleMake}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.vehicleModel" />
						</td>
						<td>
							<html:text property="vehicleModel" styleClass="text"
								styleId="vehicleModel" maxlength="50"
								value="${sessionAssetVehicalList[0].vehicleModel}" readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.manufact" />
						</td>
						<td>
							<html:text property="assetManufact" styleClass="text"
								styleId="assetManufact" maxlength="50"
								value="${sessionAssetVehicalList[0].assetManufact}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.supplier" />
						</td>
						<td>
							<html:text property="machineSupplier" styleClass="text"
								styleId="machineSupplier" maxlength="50"
								value="${sessionAssetVehicalList[0].machineSupplier}" readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.usesType" />
						</td>
						<td>
							<html:text property="usageType" styleClass="text"
								styleId="usageType" maxlength="50"
								value="${sessionAssetVehicalList[0].usageType}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.state" />
						</td>
						<td>
							<html:text property="txtStateCode" styleClass="text"
								styleId="txtStateCode" maxlength="50"
								value="${sessionAssetVehicalList[0].txtStateCode}" readonly="true" />
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.vehicleSecurityMarginDF" />
						</td>
						<td>
							<html:text property="collateralSecurityMarginDF"
								styleClass="text" styleId="collateralSecurityMarginDF"
								maxlength="50"
								value="${sessionAssetVehicalList[0].collateralSecurityMarginDF}"
								readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.vehicleSecurityMargin" />
						</td>
						<td>
							<html:text property="collateralSecurityMargin" styleClass="text"
								styleId="collateralSecurityMargin" maxlength="50"
								value="${sessionAssetVehicalList[0].collateralSecurityMargin}"
								readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.loanAmount" />
						</td>
						<td>
							<html:text property="loanAmount" styleClass="text"
								styleId="loanAmount" maxlength="50"
								value="${sessionAssetVehicalList[0].loanAmount}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.vehicleCost" />
						</td>
						<td>
							<html:text property="vehicleCost" styleClass="text"
								styleId="vehicleCost" maxlength="50"
								value="${sessionAssetVehicalList[0].vehicleCost}" readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.vehiclediscount" />
						</td>
						<td>
							<html:text property="vehicleDiscount" styleClass="text"
								styleId="vehicleDiscount" maxlength="50"
								value="${sessionAssetVehicalList[0].vehicleDiscount}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.vehicleValue" />
						</td>
						<td>
							<html:text property="assetsCollateralValue" styleClass="text"
								styleId="assetsCollateralValue" maxlength="50"
								value="${sessionAssetVehicalList[0].assetsCollateralValue}"
								readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.gridValue" />
						</td>
						<td>
							<html:text property="gridValue" styleClass="text"
								styleId="gridValue" maxlength="50"
								value="${sessionAssetVehicalList[0].gridValue}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.valuationCost" />
						</td>
						<td>
							<html:text property="valuationCost" styleClass="text"
								styleId="valuationCost" maxlength="50"
								value="${sessionAssetVehicalList[0].valuationCost}" readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.securityTypes" />
						</td>
						<td>
							<html:text property="securityTypes" styleClass="text"
								styleId="securityTypes" maxlength="50"
								value="${sessionAssetVehicalList[0].securityTypes}" readonly="true" />
						</td>

						<td>
							<bean:message key="lbl.standard" />
						</td>
						<logic:iterate name="sessionAssetVehicalList" id="subList" length="1">
							<logic:equal value="Y" name="subList" property="assetStandard">
								<td>
									<input type="checkbox" name="assetStandard" id="assetStandard"
										disabled="disabled" checked="checked" />
								</td>
							</logic:equal>
							<logic:notEqual value="Y" name="subList" property="assetStandard">
								<td>
									<input type="checkbox" name="assetStandard" id="assetStandard"
										disabled="disabled" />
								</td>
							</logic:notEqual>
						</logic:iterate>

					</tr>
				</table>
          </fieldset>
		
		<fieldset>	
		     <legend>
					<bean:message key="lbl.InvoiceUpdate" />
				</legend>
				
				<table width="107%">
				  <tr>
				       <td>
							<bean:message key="lbl.InvoiceUpdate" />
						</td>
						<td>
						   <logic:present name="invoiceCheckBox">
					          <input type="checkbox" name="invoiceUpdateCheckBox" disabled="disabled" id="invoiceUpdateCheckBox" onclick="invoiceUpdateActivate();" checked="checked"/>
					       </logic:present>
					       <logic:notPresent name="invoiceCheckBox">
					          <input type="checkbox" name="invoiceUpdateCheckBox" disabled="disabled" id="invoiceUpdateCheckBox" onclick="invoiceUpdateActivate();" />
					       </logic:notPresent>
						</td>
				  </tr>
					<tr >
						<td>
							<bean:message key="lbl.invoiceNumber" />
						
						</td>
						<td >
							<html:text property="invoiceNumber" styleClass="text"
								styleId="invoiceNumber" maxlength="50" readonly="true"
								onchange="saveAfterChange();"
								value="${sessionAssetVehicalList[0].invoiceNumber}" />
						</td>

						<td>
							<bean:message key="lbl.invoiceDate" />
							
						</td>
						<td width="32%">
							
							    <html:text property="vehicleInvoiceDate" styleClass="text"
								styleId="vehicleInvoiceDateViewMode" maxlength="50" readonly="true"
								value="${sessionAssetVehicalList[0].vehicleInvoiceDate}"
								onchange="checkDate('vehicleInvoiceDate');changeInvoiceUpdate();" />
							
						</td>

					</tr>
					<tr>
					   <td>
							<bean:message key="lbl.invoiceAmount" />
							<font color="red">*</font>
						</td>
						<td>
							<html:text property="invoiceAmount" styleClass="text"
								styleId="invoiceAmount" maxlength="22"
								onkeypress="return isNumberKey(event);"
								onchange="saveAfterChange();" readonly="true"
								value="${sessionAssetVehicalList[0].invoiceAmount}" />
						</td>
					</tr>
					
		            <tr>
						<td>
							<bean:message key="lbl.chasisNumber" />
							<font color="red">*</font>
						</td>
						<td>
							<html:text property="vehicleChesisNo" styleClass="text"
								styleId="vehicleChesisNo" maxlength="50"
								onchange="saveAfterChange();" readonly="true"
								value="${sessionAssetVehicalList[0].vehicleChesisNo}" />
						</td>

						<td>
							<bean:message key="lbl.engineNumber" />
							<font color="red">*</font>
						</td>
						<td width="25%">
							<html:text property="engineNumber" styleClass="text"
								styleId="engineNumber" maxlength="50" readonly="true"
								onchange="saveAfterChange();"
								value="${sessionAssetVehicalList[0].engineNumber}" />
						</td>

					</tr>
			</table>
			
       </fieldset>	
				<fieldset>	
		        <legend>
					<bean:message key="lbl.rcUpdate" />
				</legend>
				<table width="108%">
				   <tr>
				       <td>
							<bean:message key="lbl.rcUpdate" />
						</td>
						<td>
						    <logic:present name="rcCheckBox">
							  <input type="checkbox" name="rcUpdateCheckBox" disabled="disabled" id="rcUpdateCheckBox" checked="checked"  onclick="rcUpdateActivate();"	/>
							</logic:present>
							 <logic:notPresent name="rcCheckBox">
							  <input type="checkbox" name="rcUpdateCheckBox" disabled="disabled" id="rcUpdateCheckBox"  onclick="rcUpdateActivate();"	/>
							</logic:notPresent>
						</td>
				  </tr>
					<tr>
						<td>
							<bean:message key="lbl.rcReceived" />
							<font color="red">*</font>
						</td>
						<td width="40%">
							<html:select property="rcReceived" disabled="true" styleId="rcReceived" onclick="changeRcUpdate();"
								styleClass="text" onchange="saveAfterChange();rcDateMandatory()"
								value="${sessionAssetVehicalList[0].rcReceived}">
								<html:option value="">---Select---</html:option>
								<html:option value="Y">YES</html:option>
								<html:option value="N">NO</html:option>
							</html:select>
						</td>

						<td>
							<bean:message key="lbl.rcReceivedDate" />
						</td>
						<td width="28%">
							<html:text property="rcReceivedDate" styleClass="text"
								styleId="rcReceivedDateViewMode" maxlength="50"
								value="${sessionAssetVehicalList[0].rcReceivedDate}"
								readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.registrationNumber" />
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:text property="vehicleRegNo" styleClass="text"
								styleId="vehicleRegNo" maxlength="50"
								onchange="saveAfterChange();" readonly="true"
								value="${sessionAssetVehicalList[0].vehicleRegNo}" />
						</td>

						<td>
							<bean:message key="lbl.registrationDate" />
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:text property="vehicleRegDate" styleClass="text"
								styleId="vehicleRegDateViewMode" maxlength="50"
								value="${sessionAssetVehicalList[0].vehicleRegDate}"
								readonly="true" />
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.yearofManufacture" />
							(YYYY)
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:text property="vehicleYearOfManufact" styleClass="text"
								styleId="vehicleYearOfManufact" maxlength="50" readonly="true"
								value="${sessionAssetVehicalList[0].vehicleYearOfManufact}"
								 />
						</td>

						<td>
							<bean:message key="lbl.vehicleOwner" />
						</td>
						<td width="33%">
							<html:text property="vehicleOwner" styleClass="text"
								styleId="vehicleOwner" maxlength="50" readonly="true"
								value="${sessionAssetVehicalList[0].vehicleOwner}" />
						</td>

					</tr>
					<tr>
						<td>
							<bean:message key="lbl.permitReceived" />
						</td>
						<td width="33%">
							<html:select property="permitReceived" disabled="true" styleId="permitReceived"
								styleClass="text" onclick="changeRcUpdate();"
								onchange="receivedDateMandatory();saveAfterChange();"
								value="${sessionAssetVehicalList[0].permitReceived}">
								<html:option value="">---Select---</html:option>
								<html:option value="Y">YES</html:option>
								<html:option value="N">NO</html:option>
							</html:select>
						</td>

						<td>
							<bean:message key="lbl.permitReceivedDate" />
							<font color="red">*</font>
						</td>
						<td width="33%">
							<html:hidden property="permitExpiryDate" styleClass="text"
								styleId="permitExpiryDateViewMode"
								value="${sessionAssetVehicalList[0].permitReceivedDate}" />
							<html:text property="permitReceivedDate" styleClass="text"
								styleId="permitReceivedDateViewMode" maxlength="50" 
								value="${sessionAssetVehicalList[0].permitReceivedDate}"
								readonly="true" />
						</td>

					</tr>
<%-- Changes Start by Kumar Aman --%>					
					<tr>
					
					<td>
							<bean:message key="lbl.endorsementdate" />
							
						</td>


						<td>
							<html:text property="endorsementdate1" styleClass="text"
								styleId="endorsementdate1" maxlength="50"  readonly="true"
								value="${sessionAssetVehicalList[0].endorsementdate}"	 
								/>

						</td>
</tr>

<%-- Changes End by Kumar Aman --%>	
				</table>
          </fieldset>	
				<fieldset>	
		        <legend>
					<bean:message key="lbl.insuranceUpdate" />
				</legend>
				  <table width="100%">
				     <tr>
				       <td>
							<bean:message key="lbl.insuranceUpdate" />
						</td>
						<td>
						    <logic:present name="insuranceCheckBox">
							  <input type="checkbox" name="insuranceUpdateCheckBox" disabled="disabled" id="insuranceUpdateCheckBox"  onclick="insuranceUpdateActivate();" checked="checked" />
							</logic:present>
							<logic:notPresent name="insuranceCheckBox">
							  <input type="checkbox" name="insuranceUpdateCheckBox" disabled="disabled" id="insuranceUpdateCheckBox"  onclick="insuranceUpdateActivate();" />
							</logic:notPresent>
						</td>
				    </tr>
					<tr>
					<!--	<td>
							<bean:message key="lbl.Insurer" />
							<font color="red">*</font>
						</td>
						<td width="25%">
							<html:text property="vehicleInsurer" styleClass="text"
								styleId="vehicleInsurer" maxlength="50" readonly="true"
								onchange="saveAfterChange();"
								value="${sessionAssetVehicalList[0].vehicleInsurer}" />
						</td> -->

						<td><bean:message key="lbl.Insurer"></bean:message> <font color="red">* </font></td>
		   <td>
		    <html:text styleClass="text" property="insuranceAgency" styleId="insuranceAgency" maxlength="50"  value="${sessionAssetVehicalList[0].insuranceAgency}" readonly="true" tabindex="-1"/>
            <html:hidden property="insuranceAgency" styleId="lbxInsuranceAgency"/>
           <!-- <html:button property="insuranceButton" styleId="insuranceButton" style="display: none" onclick="openLOVCommon(81,'assetMakerForm','insuranceAgency','','', '','','','lbxInsuranceAgency')" value=" " styleClass="lovbutton"></html:button> -->
		  </td>	
				
						<td>
							<bean:message key="lbl.insuredDate" />
							<font color="red">*</font>
						</td>
						<td >
							<html:text property="vehicleInsureDate" styleClass="text"
								styleId="vehicleInsureDateViewMode" maxlength="50"
								value="${sessionAssetVehicalList[0].vehicleInsureDate}"
						
								readonly="true" />
						</td>

					</tr>

					<tr>
						<td>
							<bean:message key="lbl.idv" />
						</td>
						<td width="25%">
							<html:text property="idv" styleClass="text" styleId="idv"
								maxlength="22" onkeypress="return isNumberKey(event);"
								onchange="saveAfterChange();" readonly="true"
								value="${sessionAssetVehicalList[0].idv}" />
						</td>

						

					<%--kumar aman change start --%>
						<td>
							<bean:message key="lbl.renewaldate" />
							
						</td>
						<td >
							<html:text property="renewaldate1" styleClass="text"
								styleId="renewaldate1" maxlength="50" readonly="true"
								value="${sessionAssetVehicalList[0].renewaldate}"	 />
								
						</td>

					</tr>
					
					<tr>
					<td>
							<bean:message key="lbl.premamt" />
						</td>
						<td>
							<html:text property="premamt" styleClass="text"
								styleId="premamt" maxlength="22"
								onkeypress="return isNumberKey(event);"
								onchange="saveAfterChange();"	 readonly="true" value="${sessionAssetVehicalList[0].premamt}" />
						</td>
						
						<td>
							<bean:message key="lbl.premdate" />
						
						</td>
						<td >
							<html:text property="premdate1" styleClass="text"
								styleId="premdate1" maxlength="50"  readonly="true"
								value="${sessionAssetVehicalList[0].premdate}"	 />
							
						</td>
						
						</tr>
<%-- kumar aman change end --%>	
               </table>
		</fieldset>
	
</html:form>
</logic:present>	
	

<logic:present name="vehicalUpdt">

<script type="text/javascript">

var loanId= document.getElementById("lbxLoanId").value;

	if('<%=request.getAttribute("vehicalUpdt")%>'=='CHASIS')
	{
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.ChasisAlreadyExist" />"))

		{
	
			document.getElementById("updateform").action="updateAssetEdit.do?method=updateAsset&loanId="+loanId+"&saveFlag="+'${statusFlag}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("updateform").submit();
		
		}
		
		
}
else if('<%=request.getAttribute("vehicalUpdt")%>'=='ENGINE')
{		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.engineAlreadyExist" />"))

		{
		document.getElementById("updateform").action="updateAssetEdit.do?method=updateAsset&loanId="+loanId+"&saveFlag="+'${statusFlag}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("updateform").submit();
		
		}
		
		
		}
		else if('<%=request.getAttribute("vehicalUpdt")%>'=='REGNO')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.RegistrationAlreadyExist" />"))

		{document.getElementById("updateform").action="updateAssetEdit.do?method=updateAsset&loanId="+loanId+"&saveFlag="+'${statusFlag}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("updateform").submit();
		
		}
		
		
}

else if('<%=request.getAttribute("vehicalUpdt")%>'=='CHASIS_ENGINE')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.ChasisEngineAlreadyExist" />"))

		{document.getElementById("updateform").action="updateAssetEdit.do?method=updateAsset&loanId="+loanId+"&saveFlag="+'${statusFlag}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("updateform").submit();
		
		}
		
		
}
else if('<%=request.getAttribute("vehicalUpdt")%>'=='CHASIS_REGNO')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.ChasisRegnoAlreadyExist" />"))

		{
document.getElementById("updateform").action="updateAssetEdit.do?method=updateAsset&loanId="+loanId+"&saveFlag="+'${statusFlag}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("updateform").submit();
			
		
		}
		
		
}
else if('<%=request.getAttribute("vehicalUpdt")%>'=='ENGINE_REGNO')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.EngineRegnoAlreadyExist" />"))

		{document.getElementById("updateform").action="updateAssetEdit.do?method=updateAsset&loanId="+loanId+"&saveFlag="+'${statusFlag}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("updateform").submit();
			
		}
		
		
}
else if('<%=request.getAttribute("vehicalUpdt")%>'=='CHASIS_ENGINE_REGNO')
		{		
		var strChasisFlag='Y';
		if(confirm("<bean:message key="lbl.ChasisEngineRegnoAlreadyExist" />"))

		{document.getElementById("updateform").action="updateAssetEdit.do?method=updateAsset&loanId="+loanId+"&saveFlag="+'${statusFlag}'+"&strChasisFlag="+strChasisFlag;
			document.getElementById("updateform").submit();
			
		}
		
		
}




	</script>
</logic:present>

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
		location.href="<%=request.getContextPath()%>/updateAssetSearch.do";
	}
	else if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert("<bean:message key="lbl.dataExist" />");	
	}
	else if('<%=request.getAttribute("sms")%>'=='DR')
	{
		alert("<bean:message key="lbl.duplicateRegNo" />");
	}
	else if('<%=request.getAttribute("sms")%>'=='DS')
	{
		alert("<bean:message key="lbl.dataDeleted" />");
	}
	else if('<%=request.getAttribute("sms")%>'=='DE')
	{
		alert("<bean:message key="lbl.dataNtDeleted" />");
	}
</script>
	</logic:present>

</body>
</html:html>