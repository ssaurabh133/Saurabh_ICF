<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.logger.LoggerMsg"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp"%>
<%@include file="/JSP/commonIncludeContent.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html>

<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="" />



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
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
</head>
	<input type="hidden" id="contextPath" name="contextPath" value="<%=path%>" />
	<BODY onload="enableAnchor();">
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
		request.setAttribute("assetCollateralType",assetCollateralType);
	    request.setAttribute("new","N");
	}
 %>
	<input type="hidden" name="assetsId" id="assetsId" value='${assetsId}' />
	<input type="hidden" name="assetCollateralType" id="assetCollateralType" value='${assetCollateralType}' />
	
	<!-- 	For View Mode -->

	<logic:present name="invoiceDealViewer">
	
	<FIELDSET><LEGEND><bean:message key="lbl.invoiceDataCapturing" /></LEGEND>
	<html:form action="/collateralAssetInvoiceAction" styleId="invoiceAsset"method="post">
			<table>
			<logic:present name="invoiceDetail">
				<tr>
					<td style="width:330px;"><bean:message key="lbl.oem" /><font color="red">*</font></td>
					<td style="width:230px;">
					
						<html:text property="assetManufact" styleId="assetManufact" styleClass="text"readonly="true"value="${invoiceDetail[0].assetManufact}" tabindex="-1" />
					</td>
					<td style="width:255px;"><bean:message key="lbl.supplierND" /><font color="red">*</font></td>
					<td>
						<html:text property="machineSupplier" styleId="machineSupplier" styleClass="text"readonly="true"value="${invoiceDetail[0].machineSupplier}" tabindex="-1" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="lbl.siRdName" /><font color="red">*</font></td>
					<td><html:text property="siRdName" styleId="siRdName"styleClass="text" value="${invoiceDetail[0].siRdName}" onchange="updateInvoiceFlag();" maxlength="50" readonly="true" /></td>
				</tr>
				</logic:present>

				</table>
				<logic:present name="invoiceDtl">
				<FIELDSET><legend><bean:message key="lbl.invoiceDetails" /></legend>
					<table>
						<tr>
							<td style="width:330px;"><bean:message key="lbl.productType" /><font color="red">*</font></td>
							<td style="width:230px;">
							<html:text property="productDesc" styleId="productDesc" styleClass="text"readonly="true"value="${invoiceDtl[0].productDesc}" tabindex="-1" onchange="updateInvoiceFlag();" />
							
							</td>
							<td style="width:255px;"><bean:message key="lbl.quantity" /><font color="red">*</font></td>
								<td><html:text property="invoiceQuantity"styleId="invoiceQuantity" styleClass="text" value="${invoiceDtl[0].invoiceQuantity}" onkeypress="return numbersonly(event,id,18);" maxlength="50" onchange="updateInvoiceFlag();"  readonly="true"/></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.price" /><font color="red">*</font></td>
							<td><html:text property="invoicePrice"styleId="invoicePrice" styleClass="text" value="${invoiceDtl[0].invoicePrice}" onchange="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="50" readonly="true" /></td>
							<td><bean:message key="lbl.discount" /></td>
							<td><html:text property="invoiceDiscount"styleId="invoiceDiscount" styleClass="text" value="${invoiceDtl[0].invoiceDiscount}" onchange="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="50" readonly="true"/></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.taxAmount" /></td>
							<td><html:text property="taxAmount" styleId="taxAmount"styleClass="text" value="${invoiceDtl[0].taxAmount}" onchange="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="50" readonly="true" /></td>
							<td><bean:message key="lbl.invoiceAmount" /><font color="red">*</font></td>
							<td><html:text property="invoiceAmount" styleId="invoiceAmount"styleClass="text" value="${invoiceDtl[0].invoiceAmount}" onfocus="multiply();" onchange="updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="50" readonly="true"/></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.invoiceNo" /><font color="red">*</font></td>
							<td><html:text property="invoiceNo" styleId="invoiceNo"styleClass="text" value="${invoiceDtl[0].invoiceNo}" maxlength="50" onchange="updateInvoiceFlag();" readonly="true" /></td>
							<td><bean:message key="lbl.invoiceDate" /><font color="red">*</font></td>
							<td><html:text property="invoiceDate" styleClass="text" styleId="assetInvoiceDate1" value="${invoiceDtl[0].invoiceDate}" onchange="checkDate('invoiceDate');checkDateLessThanBusDate();updateInvoiceFlag();" readonly="true" /></td>
						</tr>
						<tr>	
							<td><bean:message key="lbl.invoicelocation" /><font color="red">*</font></td>
							<td><html:text property="invoicelocation"styleId="invoicelocation" styleClass="text" value="${invoiceDtl[0].invoicelocation}" maxlength="50" onchange="updateInvoiceFlag();" readonly="true"/></td>
						</tr>
						<tr>

						</tr>
					</table>
				</FIELDSET>
				</logic:present>
				<logic:notPresent name="invoiceDtl">
				<FIELDSET><legend><bean:message key="lbl.invoiceDetails" /></legend>
					<table>
						<tr>
							<td style="width:330px;"><bean:message key="lbl.productType" /><font color="red">*</font></td>
							<td style="width:230px;">
							<html:text property="productDesc" styleId="productDesc" styleClass="text"readonly="true"value="" tabindex="-1" />
						
							</td>
							<td style="width:255px;"><bean:message key="lbl.quantity" /><font color="red">*</font></td>
								<td><html:text property="invoiceQuantity"styleId="invoiceQuantity" styleClass="text" value="" onkeypress="return numbersonly(event,id,18);" maxlength="50" onchange="updateInvoiceFlag();" readonly="true"/></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.price" /><font color="red">*</font></td>
							<td><html:text property="invoicePrice"styleId="invoicePrice" styleClass="text" value="" onchange="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="50" readonly="true"/></td>
							<td><bean:message key="lbl.discount" /></td>
							<td><html:text property="invoiceDiscount"styleId="invoiceDiscount" styleClass="text" value="" onkeypress="return numbersonly(event,id,18);" onchange="multiply();updateInvoiceFlag();" maxlength="50" readonly="true"/></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.taxAmount" /></td>
							<td><html:text property="taxAmount" styleId="taxAmount"styleClass="text" value=""  onchange="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="50" readonly="true"/></td>
							<td><bean:message key="lbl.invoiceAmount" /><font color="red">*</font></td>
							<td><html:text property="invoiceAmount" styleId="invoiceAmount"styleClass="text" value="" onfocus="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="50" readonly="true"/></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.invoiceNo" /><font color="red">*</font></td>
							<td><html:text property="invoiceNo" styleId="invoiceNo"styleClass="text" value="" maxlength="50" onchange="updateInvoiceFlag();" readonly="true"/></td>
							<td><bean:message key="lbl.invoiceDate" /><font color="red">*</font></td>
							<td><html:text property="invoiceDate" styleClass="text" styleId="assetInvoiceDate1" value="" onchange="checkDate('invoiceDate');checkDateLessThanBusDate();updateInvoiceFlag();" readonly="true" /></td>
						</tr>
						<tr>	
							<td><bean:message key="lbl.invoicelocation" /><font color="red">*</font></td>
							<td><html:text property="invoicelocation"styleId="invoicelocation" styleClass="text" value="" maxlength="50" onchange="updateInvoiceFlag();" readonly="true" /></td>
						</tr>

					</table>
				</FIELDSET>
				</logic:notPresent>
				
				<FIELDSET><legend><bean:message key="lbl.gridToDisplayMultipleProductsCaptured" /></legend>
					<table cellspacing="0" cellpadding="3" border="0" width="100%">
					<tr class="white2">
	        			<td><B><bean:message key="contact.select" /></b></td> 
						<td><B><bean:message key="lbl.SNo" /></b></td>
						<td><B><bean:message key="lbl.productType" /></b></td>
						<td><B><bean:message key="lbl.quantity" /></b></td>
						<td><B><bean:message key="lbl.price" /></b></td>						
						<td><B><bean:message key="lbl.discount" /></b></td>	
						<td><B><bean:message key="lbl.total" /></b></td>
					</tr><% int i=0; %>
					<logic:present name="invoiceDetail">
					<logic:iterate id="subinvoiceDtl" name="invoiceDetail">
					<tr class="white1">
					<td ><input type="checkbox" name="chk1" id="chk1" value="${subinvoiceDtl.recordId}"/></td>
					<td><%=++i %></td>
					<td ><a href="#" id="anchor0" onclick="return modifyInvoice(${subinvoiceDtl.recordId});">${subinvoiceDtl.productDesc}</a></td>
					<td >${subinvoiceDtl.invoiceQuantity}</td>
					<td>${subinvoiceDtl.invoicePrice}</td>
					<td>${subinvoiceDtl.invoiceDiscount}</td>
					<td>${subinvoiceDtl.invoiceAmount}</td>
					</tr>	
					</logic:iterate>
					<tr>
					<td></td><td></td><td></td><td></td><td></td>
					<td><B><bean:message key="lbl.invoiceAmount" /></b></td>
					<td>${invoiceTotalAmount}</td>
					</tr>

					</logic:present>
				</table>
			</FIELDSET>
			
			<logic:present name="dispatchDtl">
			<FIELDSET><LEGEND><bean:message key="lbl.dispatchDetails" /></LEGEND>
				<table>
					<tr>
        				<td style="width:330px;"><bean:message key="lbl.productType" /></td>
		     			<td style="width:230px;"><logic:present name="productList">
						<html:select property="invoiceProductTypeDesc" styleId="invoiceProductTypeDesc" styleClass="text" value="${dispatchDtl[0].invoiceProductTypeDesc}" disabled="true" >
						<html:option value="">---Select----</html:option>
						<logic:notEmpty name="productList">
					  	<html:optionsCollection name="productList" label="invoiceProductTypeDesc" value="invoiceProductTypeCode" />
						</logic:notEmpty>
			 			</html:select>
			 			</logic:present>
		      			</td>
		      			<logic:notPresent name="productList">
		      			<td style="width:230px;"><html:select property="invoiceProductType"styleId="invoiceProductType" styleClass="text" value="">
						<html:option value="">---Select----</html:option></html:select></td>
		      			</logic:notPresent>
        				
        				<td style="width:255px;"><bean:message key="lbl.dispatchQuantity" /></td>
        				<td><html:text property="invoiceDispatchQuantity"styleId="invoiceDispatchQuantity" styleClass="text" value="${dispatchDtl[0].invoiceDispatchQuantity}" onkeypress="return numbersonly(event,id,18);" maxlength="50" onchange="updateDispatchFlag();" readonly="true"/></td>
					</tr>
    				<tr>
        				<td><bean:message key="lbl.dispatchLocation" /></td>
        				<td><html:text property="invoiceDispatchLocation"styleId="invoiceDispatchLocation" styleClass="text" value="${dispatchDtl[0].invoiceDispatchLocation}"maxlength="50" onchange="updateDispatchFlag();" readonly="true"/></td>
        				<td><bean:message key="lbl.delieveryLocation" /></td>
        				<td><html:text property="delieveryLocation"styleId="delieveryLocation" styleClass="text" value="${dispatchDtl[0].delieveryLocation}"maxlength="50" onchange="updateDispatchFlag();" readonly="true" /></td>
        			</tr>
     				<tr>	
        				<td><bean:message key="lbl.consigneeName" /></td>
        				<td><html:text property="invoiceConsigneeName"styleId="invoiceConsigneeName" styleClass="text" value="${dispatchDtl[0].invoiceConsigneeName}"maxlength="50" onchange="updateDispatchFlag();" readonly="true" /></td>
     					<td><bean:message key="lbl.consignmentValue" /></td>
        				<td><html:text property="consignmentValue"styleId="consignmentValue" styleClass="text" value="${dispatchDtl[0].consignmentValue}" onkeypress="return numbersonly(event,id,18);" maxlength="50" onchange="updateDispatchFlag();" readonly="true"/></td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.consigneeShippingAddress" /></td>
       					<td><html:text property="invoiceConsigneeShippingAddress"styleId="invoiceConsigneeShippingAddress" styleClass="text"value="${dispatchDtl[0].invoiceConsigneeShippingAddress}" maxlength="50" onchange="updateDispatchFlag();" readonly="true"/></td>
        				<td><bean:message key="lbl.consignmentNo" /></td>
        				<td><html:text property="invoiceConsignmentNo"styleId="invoiceConsignmentNo" styleClass="text" value="${dispatchDtl[0].invoiceConsignmentNo}"maxlength="50" onchange="updateDispatchFlag();" readonly="true"/></td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.logisticsProvider" /></td>
        				<td><html:text property="invoiceLogisticsProvider"styleId="invoiceLogisticsProvider" styleClass="text" value="${dispatchDtl[0].invoiceLogisticsProvider}"maxlength="50" onchange="updateDispatchFlag();" readonly="true" /></td>
        				<td><bean:message key="lbl.deliveryStatus" /></td>
        				<td>
        					<html:select property="invoiceDeliveryStatus"styleId="invoiceDeliveryStatus" styleClass="text" value="${dispatchDtl[0].invoiceDeliveryStatus}" onchange="updateDispatchFlag();"  disabled="true">
        					<html:option value="">---Select----</html:option>
        					<html:option value="D">Delivered</html:option>
			            	<html:option value="P">Pending</html:option>
        					</html:select>
        				</td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.dispatchDate" /></td>
        				<td><html:text property="dispatchDate" styleClass="text" styleId="dispatchDate1" value="${dispatchDtl[0].dispatchDate}"  readonly="true" /></td>
        				<td><bean:message key="lbl.delieveryDate" /></td>
        				<td><html:text property="delieveryDate" styleClass="text" styleId="delieveryDate1" value="${dispatchDtl[0].delieveryDate}"  readonly="true" /></td>
     				</tr>

     			</table>
     		</FIELDSET>
     		</logic:present>
     		<logic:notPresent name="dispatchDtl">
     		<FIELDSET><LEGEND><bean:message key="lbl.dispatchDetails" /></LEGEND>
				<table>
					<tr>
        				<td style="width:330px;"><bean:message key="lbl.productType" /></td>
		     			<logic:present name="productList">
		     			<td style="width:230px;">
						<html:select property="invoiceProductTypeDesc" styleId="invoiceProductTypeDesc" styleClass="text" value="${dispatchDtl[0].invoiceProductTypeDesc}" disabled="true">
						<html:option value="">---Select----</html:option>
						<logic:notEmpty name="productList">
					  	<html:optionsCollection name="productList" label="invoiceProductTypeDesc" value="invoiceProductTypeCode" />
						</logic:notEmpty>
						
			 			</html:select>
			 			</td>
			 			</logic:present>
		      			
		      			<logic:notPresent name="productList">
		      			<td style="width:230px;"><html:select property="invoiceProductType"styleId="invoiceProductType" styleClass="text" value="">
						<html:option value="">---Select----</html:option></html:select>
        				</td>
        				</logic:notPresent>
        				<td style="width:255px;"><bean:message key="lbl.dispatchQuantity" /></td>
        				<td><html:text property="invoiceDispatchQuantity"styleId="invoiceDispatchQuantity" styleClass="text" value="" onkeypress="return numbersonly(event,id,18);" maxlength="50" onchange="updateDispatchFlag();" readonly="true" /></td>
					</tr>
    				<tr>
        				<td><bean:message key="lbl.dispatchLocation" /></td>
        				<td><html:text property="invoiceDispatchLocation"styleId="invoiceDispatchLocation" styleClass="text" value=""maxlength="50" onchange="updateDispatchFlag();" readonly="true"/></td>
        				<td><bean:message key="lbl.delieveryLocation" /></td>
        				<td><html:text property="delieveryLocation"styleId="delieveryLocation" styleClass="text" value=""maxlength="50" onchange="updateDispatchFlag();" readonly="true"/></td>
        			</tr>
     				<tr>	
        				<td><bean:message key="lbl.consigneeName" /></td>
        				<td><html:text property="invoiceConsigneeName"styleId="invoiceConsigneeName" styleClass="text" value=""maxlength="50" onchange="updateDispatchFlag();" readonly="true"/></td>
     					<td><bean:message key="lbl.consignmentValue" /></td>
        				<td><html:text property="consignmentValue"styleId="consignmentValue" styleClass="text" value="" onkeypress="return numbersonly(event,id,18);" maxlength="50" onchange="updateDispatchFlag();" readonly="true"/></td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.consigneeShippingAddress" /></td>
       					<td><html:text property="invoiceConsigneeShippingAddress"styleId="invoiceConsigneeShippingAddress" styleClass="text"value="" maxlength="50" onchange="updateDispatchFlag();" readonly="true"/></td>
        				<td><bean:message key="lbl.consignmentNo" /></td>
        				<td><html:text property="invoiceConsignmentNo"styleId="invoiceConsignmentNo" styleClass="text" value=""maxlength="50" onchange="updateDispatchFlag();" readonly="true" /></td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.logisticsProvider" /></td>
        				<td><html:text property="invoiceLogisticsProvider"styleId="invoiceLogisticsProvider" styleClass="text" value=""maxlength="50" onchange="updateDispatchFlag();" readonly="true"/></td>
        				<td><bean:message key="lbl.deliveryStatus" /></td>
        				<td>
        					<html:select property="invoiceDeliveryStatus"styleId="invoiceDeliveryStatus" styleClass="text" value="" onchange="updateDispatchFlag();" disabled="true" >
        					<html:option value="">---Select----</html:option>
        					<html:option value="D">Delivered</html:option>
			            	<html:option value="P">Pending</html:option>
        					</html:select>
        				</td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.dispatchDate" /></td>
        				<td><html:text property="dispatchDate" styleClass="text" styleId="dispatchDate1" value=""   readonly="true" /></td>
        				<td><bean:message key="lbl.delieveryDate" /></td>
        				<td><html:text property="delieveryDate" styleClass="text" styleId="delieveryDate1" value=""  readonly="true" /></td>
     				</tr>

     			</table>
     		</FIELDSET>
     		</logic:notPresent>
		
			<FIELDSET><legend><bean:message key="lbl.gridToDisplayMultipleProductsCaptured" /></legend>
				<table cellspacing="0" cellpadding="3" border="0" width="100%">
					<tr class="white2">
	        			<td><B><bean:message key="contact.select" /></b></td> 
						<td><B><bean:message key="lbl.SNo" /></b></td>
						<td><B><bean:message key="lbl.consignmentNo" /></b></td>
						<td><B><bean:message key="lbl.consigneeName" /></b></td>
						<td><B><bean:message key="lbl.logisticsProvider" /></b></td>						
						<td><B><bean:message key="lbl.productType" /></b></td>	
						<td><B><bean:message key="lbl.deliveryStatus" /></b></td>
					</tr><% int n=0; %>
					<logic:present name="dispatchDetail">
					<logic:iterate id="subdispatchDtl" name="dispatchDetail">
					<tr class="white1">
					<td ><input type="checkbox" name="chk" id="chk${n}" value="${subdispatchDtl.recordId}"/></td>
					<td><%=++n %></td>
					<td ><a href="#" id="anchor0" onclick="return modifyDispatch(${subdispatchDtl.recordId});">${subdispatchDtl.invoiceConsignmentNo}</a></td>
					<td >${subdispatchDtl.invoiceConsigneeName}</td>
					<td>${subdispatchDtl.invoiceLogisticsProvider}</td>
					<td>${subdispatchDtl.invoiceProductTypeDesc}</td>
					<td>${subdispatchDtl.invoiceDeliveryStatus}</td>
					</tr>	
					</logic:iterate>

					</logic:present>
				</table>
			</FIELDSET>

	</html:form>
	</FIELDSET> 
	
	
	</logic:present>
	
	<!-- 	For Edit Mode -->
	
    <logic:notPresent name="invoiceDealViewer">
	<FIELDSET><LEGEND><bean:message key="lbl.invoiceDataCapturing" /></LEGEND>
	<html:form action="/collateralAssetInvoiceAction" styleId="invoiceAsset"method="post">
			<table>
			<logic:present name="invoiceDetail">
				<tr>
					<html:hidden property="colltype1" value="${invoiceDtl[0].colltype1}${assetClass }" styleClass="text" styleId ="colltype1"/>
					<html:hidden property="colltype2" value="${invoiceDtl[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="colltype2"/>
					<td style="width:330px;"><bean:message key="lbl.oem" /><font color="red">*</font></td>
					<td style="width:230px;">
					<input type="hidden" name="lbxmachineManufact" id="lbxmachineManufact" value="${invoiceDetail[0].lbxmachineManufact}" />
					
						<html:text property="assetManufact" styleId="assetManufact" styleClass="text"readonly="true"value="${invoiceDetail[0].assetManufact}" tabindex="-1" />
						<html:button property="assetManufactButton" styleId="assetManufactButton" onclick="openLOVCommon(5069,'invoiceAsset','lbxmachineManufact','','', '','','getSiRdName','assetManufact')" value=" " styleClass="lovbutton"></html:button>
					</td>
					<td style="width:255px;"><bean:message key="lbl.supplierND" /><font color="red">*</font></td>
					<td>
						<input type="hidden" name="lbxmachineSupplier" id="lbxmachineSupplier" value="${invoiceDetail[0].lbxmachineSupplier}" />
						
						
						<html:text property="machineSupplier" styleId="machineSupplier" styleClass="text"readonly="true"value="${invoiceDetail[0].machineSupplier}" tabindex="-1" />
						<html:button property="machineSupplierButton" styleId="machineSupplierButton" onclick="openLOVCommon(5070,'invoiceAsset','lbxmachineSupplier','assetManufact','', 'lbxmachineManufact','Select OEM LOV','','machineSupplier')" value=" " styleClass="lovbutton"></html:button>
					</td>
				</tr>
				<tr>
					<td><bean:message key="lbl.siRdName" /><font color="red">*</font></td>
					<td><html:text property="siRdName" styleId="siRdName"styleClass="text" value="${invoiceDetail[0].siRdName}" onchange="updateInvoiceFlag();" maxlength="50" /></td>
				</tr>
				</logic:present>
				<logic:notPresent name="invoiceDetail">
				<tr>
					<html:hidden property="colltype1" value="${invoiceDtl[0].colltype1}${assetClass }" styleClass="text" styleId ="colltype1"/>
					<html:hidden property="colltype2" value="${invoiceDtl[0].colltype2}${assetCollateralType }" styleClass="text" styleId ="colltype2"/>
					<td style="width:330px;"><bean:message key="lbl.oem" /><font color="red">*</font></td>
					<td style="width:230px;">
					<input type="hidden" name="invoiceFlag" id="invoiceFlag" value='Y' />
					<input type="hidden" name="lbxmachineManufact" id="lbxmachineManufact" value="" />
					
						<html:text property="assetManufact" styleId="assetManufact" styleClass="text"readonly="true"value="" tabindex="-1" />
						<html:button property="assetManufactButton" styleId="assetManufactButton" onclick="openLOVCommon(5069,'invoiceAsset','lbxmachineManufact','','', '','','getSiRdName','assetManufact')" value=" " styleClass="lovbutton"></html:button>
					</td>
					<td style="width:255px;"><bean:message key="lbl.supplierND" /><font color="red">*</font></td>
					<td>
						<input type="hidden" name="lbxmachineSupplier" id="lbxmachineSupplier" value="" />
						
						
						<html:text property="machineSupplier" styleId="machineSupplier" styleClass="text"readonly="true"value="" tabindex="-1" />
						<html:button property="machineSupplierButton" styleId="machineSupplierButton" onclick="openLOVCommon(5070,'invoiceAsset','lbxmachineSupplier','assetManufact','', 'lbxmachineManufact','Select OEM LOV','','machineSupplier')" value=" " styleClass="lovbutton"></html:button>
					</td>
				</tr>
				<tr>
					<td><bean:message key="lbl.siRdName" /><font color="red">*</font></td>
					<td><html:text property="siRdName" styleId="siRdName"styleClass="text" value="" onchange="updateInvoiceFlag();" maxlength="50"  /></td>
				</tr>
				</logic:notPresent>
				</table>
				<logic:present name="invoiceDtl">
				<FIELDSET><legend><bean:message key="lbl.invoiceDetails" /></legend>
					<table>
						<tr>
							<td style="width:330px;"><bean:message key="lbl.productType" /><font color="red">*</font></td>
							<td style="width:230px;">
							<input type="hidden" name="invoiceFlag" id="invoiceFlag" value='Y' />
							<input type="hidden" name="invoiceProductType" id="invoiceProductType" value="${invoiceDtl[0].invoiceProductType}" />
							<input type="hidden" name="lbxProductId" id="lbxProductId" value="" />
							<html:text property="productDesc" styleId="productDesc" styleClass="text"readonly="true"value="${invoiceDtl[0].productDesc}" tabindex="-1" onchange="updateInvoiceFlag();" />
							<html:button property="machineSupplierButton" styleId="machineSupplierButton" onclick="openLOVCommon(5068,'invoiceAsset','invoiceProductType','assetManufact','', 'lbxmachineManufact','Select OEM LOV','','productDesc')" value=" " styleClass="lovbutton"></html:button>
							</td>
							<td style="width:255px;"><bean:message key="lbl.quantity" /><font color="red">*</font></td>
								<td><html:text property="invoiceQuantity"styleId="invoiceQuantity" styleClass="text" value="${invoiceDtl[0].invoiceQuantity}" onkeypress="return numbersonly(event,id,18);" maxlength="50" onchange="updateInvoiceFlag();" /></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.price" /><font color="red">*</font></td>
							<td><html:text property="invoicePrice"styleId="invoicePrice" styleClass="text" value="${invoiceDtl[0].invoicePrice}" onchange="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="50" /></td>
							<td><bean:message key="lbl.discount" /></td>
							<td><html:text property="invoiceDiscount"styleId="invoiceDiscount" styleClass="text" value="${invoiceDtl[0].invoiceDiscount}" onchange="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="50" /></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.taxAmount" /></td>
							<td><html:text property="taxAmount" styleId="taxAmount"styleClass="text" value="${invoiceDtl[0].taxAmount}" onchange="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="50" /></td>
							<td><bean:message key="lbl.invoiceAmount" /><font color="red">*</font></td>
							<td><html:text property="invoiceAmount" styleId="invoiceAmount"styleClass="text" value="${invoiceDtl[0].invoiceAmount}" onfocus="multiply();" onchange="updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="50" /></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.invoiceNo" /><font color="red">*</font></td>
							<td><html:text property="invoiceNo" styleId="invoiceNo"styleClass="text" value="${invoiceDtl[0].invoiceNo}" maxlength="50" onchange="updateInvoiceFlag();" /></td>
							<td><bean:message key="lbl.invoiceDate" /><font color="red">*</font></td>
							<td><html:text property="invoiceDate" styleClass="text" styleId="assetInvoiceDate" value="${invoiceDtl[0].invoiceDate}" onchange="checkDate('invoiceDate');checkDateLessThanBusDate();updateInvoiceFlag();" /></td>
						</tr>
						<tr>	
							<td><bean:message key="lbl.invoicelocation" /><font color="red">*</font></td>
							<td><html:text property="invoicelocation"styleId="invoicelocation" styleClass="text" value="${invoiceDtl[0].invoicelocation}" maxlength="50" onchange="updateInvoiceFlag();"/></td>
						</tr>
						<tr>
							<td><span class="white">
								<button type="button" name="button1" class="topformbutton2"id="addInvoice" title="Alt+A" accesskey="A"onclick="return updateAssetInvoiceDtl(${invoiceDtl[0].recordId});"><bean:message key="button.add" /></button>
							</span></td>
						</tr>
					</table>
				</FIELDSET>
				</logic:present>
				<logic:notPresent name="invoiceDtl">
				<FIELDSET><legend><bean:message key="lbl.invoiceDetails" /></legend>
					<table>
						<tr>
							<td style="width:330px;"><bean:message key="lbl.productType" /><font color="red">*</font></td>
							<td style="width:230px;">
							<input type="hidden" name="invoiceFlag" id="invoiceFlag" value='N' />
							<input type="hidden" name="invoiceProductType" id="invoiceProductType" value="" />
							<input type="hidden" name="lbxProductId" id="lbxProductId" value="" />
							<html:text property="productDesc" styleId="productDesc" styleClass="text"readonly="true"value="" tabindex="-1" />
							<html:button property="machineSupplierButton" styleId="machineSupplierButton" onclick="openLOVCommon(5068,'invoiceAsset','invoiceProductType','assetManufact','', 'lbxmachineManufact','Select OEM LOV','','productDesc')" value=" " styleClass="lovbutton"></html:button>
							</td>
							<td style="width:255px;"><bean:message key="lbl.quantity" /><font color="red">*</font></td>
								<td><html:text property="invoiceQuantity"styleId="invoiceQuantity" styleClass="text" value="" onkeypress="return numbersonly(event,id,18);" onchange="updateInvoiceFlag();" maxlength="10" /></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.price" /><font color="red">*</font></td>
							<td><html:text property="invoicePrice"styleId="invoicePrice" styleClass="text" value="" onchange="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="22" /></td>
							<td><bean:message key="lbl.discount" /></td>
							<td><html:text property="invoiceDiscount"styleId="invoiceDiscount" styleClass="text" value="" onkeypress="return numbersonly(event,id,18);" onchange="multiply();updateInvoiceFlag();" maxlength="22" /></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.taxAmount" /></td>
							<td><html:text property="taxAmount" styleId="taxAmount"styleClass="text" value=""  onchange="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="22" /></td>
							<td><bean:message key="lbl.invoiceAmount" /><font color="red">*</font></td>
							<td><html:text property="invoiceAmount" styleId="invoiceAmount"styleClass="text" value="" onfocus="multiply();updateInvoiceFlag();" onkeypress="return numbersonly(event,id,18);" maxlength="22" /></td>
						</tr>
						<tr>
							<td><bean:message key="lbl.invoiceNo" /><font color="red">*</font></td>
							<td><html:text property="invoiceNo" styleId="invoiceNo"styleClass="text" value="" maxlength="20" onchange="updateInvoiceFlag();" /></td>
							<td><bean:message key="lbl.invoiceDate" /><font color="red">*</font></td>
							<td><html:text property="invoiceDate" styleClass="text" styleId="assetInvoiceDate" value="" onchange="checkDate('invoiceDate');checkDateLessThanBusDate();updateInvoiceFlag();" /></td>
						</tr>
						<tr>	
							<td><bean:message key="lbl.invoicelocation" /><font color="red">*</font></td>
							<td><html:text property="invoicelocation"styleId="invoicelocation" styleClass="text" value="" maxlength="200" onchange="updateInvoiceFlag();" /></td>
						</tr>
						<tr>
							<td><span class="white">
        						<button type="button" name="button1" class="topformbutton2"id="addInvoice" title="Alt+A" accesskey="A"onclick="return updateAssetInvoiceDtl('');"><bean:message key="button.add" /></button>
							</span></td>
						</tr>
					</table>
				</FIELDSET>
				</logic:notPresent>
				
				<FIELDSET><legend><bean:message key="lbl.gridToDisplayMultipleProductsCaptured" /></legend>
					<table cellspacing="0" cellpadding="3" border="0" width="100%">
					<tr class="white2">
	        			<td><B><bean:message key="contact.select" /></b></td> 
						<td><B><bean:message key="lbl.SNo" /></b></td>
						<td><B><bean:message key="lbl.productType" /></b></td>
						<td><B><bean:message key="lbl.quantity" /></b></td>
						<td><B><bean:message key="lbl.price" /></b></td>						
						<td><B><bean:message key="lbl.discount" /></b></td>	
						<td><B><bean:message key="lbl.total" /></b></td>
					</tr><% int i=0; %>
					<logic:present name="invoiceDetail">
					<logic:iterate id="subinvoiceDtl" name="invoiceDetail">
					<tr class="white1">
					
					<td ><input type="checkbox" name="chk1" id="chk1" value="${subinvoiceDtl.recordId}"/></td>
					<td><%=++i %></td>
					<td ><a href="#" id="anchor0" onclick="return modifyInvoice(${subinvoiceDtl.recordId});">${subinvoiceDtl.productDesc}</a></td>
					<td >${subinvoiceDtl.invoiceQuantity}</td>
					<td>${subinvoiceDtl.invoicePrice}</td>
					<td>${subinvoiceDtl.invoiceDiscount}</td>
					<td>${subinvoiceDtl.invoiceAmount}</td>
					</tr>	
					</logic:iterate>
					<tr>
					<td></td><td></td><td></td><td></td><td></td>
					<td><B><bean:message key="lbl.invoiceAmount" /></b></td>
					<td>
					<input type="hidden" name="invoiceTotalAmount" id="invoiceTotalAmount" value="${invoiceTotalAmount}"/>${invoiceTotalAmount}</td>
					</tr>
					<tr>
        			<td><span class="white"><button type="button" name="button1" class="topformbutton2"title="Alt+D" accesskey="D" onclick="deleteAssetInvoiceDetail(${subinvoiceDtl.recordId});">
						<bean:message key="button.delete" /></button></span></td>
					</tr>
					</logic:present>
				</table>
			</FIELDSET>
			
			<logic:present name="dispatchDtl">
			<FIELDSET><LEGEND><bean:message key="lbl.dispatchDetails" /></LEGEND>
				<table>
					<tr>
        				<td style="width:330px;"><bean:message key="lbl.productType" /></td>
		     			<td style="width:230px;"><logic:present name="productList">
		     			<input type="hidden" name="dispatchFlag" id="dispatchFlag" value='Y' />
		     			<input type="hidden" name="invoiceProductType" id="invoiceProductType" value="${dispatchDtl[0].invoiceProductType}" />
						<html:select property="invoiceProductTypeDesc" styleId="invoiceProductTypeDesc" styleClass="text" value="${dispatchDtl[0].invoiceProductTypeDesc}" >
						<html:option value="">---Select----</html:option>
						<logic:notEmpty name="productList">
					  	<html:optionsCollection name="productList" label="invoiceProductTypeDesc" value="invoiceProductTypeCode" />
						</logic:notEmpty>
			 			</html:select>
			 			</logic:present>
		      			</td>
		      			<logic:notPresent name="productList">
		      			<td style="width:230px;"><html:select property="invoiceProductType"styleId="invoiceProductType" styleClass="text" value="">
						<html:option value="">---Select----</html:option></html:select></td>
		      			</logic:notPresent>
        				
        				<td style="width:255px;"><bean:message key="lbl.dispatchQuantity" /></td>
        				<td><html:text property="invoiceDispatchQuantity"styleId="invoiceDispatchQuantity" styleClass="text" value="${dispatchDtl[0].invoiceDispatchQuantity}" onkeypress="return numbersonly(event,id,18);" maxlength="50" onchange="updateDispatchFlag();" /></td>
					</tr>
    				<tr>
        				<td><bean:message key="lbl.dispatchLocation" /></td>
        				<td><html:text property="invoiceDispatchLocation"styleId="invoiceDispatchLocation" styleClass="text" value="${dispatchDtl[0].invoiceDispatchLocation}"maxlength="50" onchange="updateDispatchFlag();" /></td>
        				<td><bean:message key="lbl.delieveryLocation" /></td>
        				<td><html:text property="delieveryLocation"styleId="delieveryLocation" styleClass="text" value="${dispatchDtl[0].delieveryLocation}"maxlength="50" onchange="updateDispatchFlag();" /></td>
        			</tr>
     				<tr>	
        				<td><bean:message key="lbl.consigneeName" /></td>
        				<td><html:text property="invoiceConsigneeName"styleId="invoiceConsigneeName" styleClass="text" value="${dispatchDtl[0].invoiceConsigneeName}"maxlength="50" onchange="updateDispatchFlag();" /></td>
     					<td><bean:message key="lbl.consignmentValue" /></td>
        				<td><html:text property="consignmentValue"styleId="consignmentValue" styleClass="text" value="${dispatchDtl[0].consignmentValue}" onkeypress="return numbersonly(event,id,18);" maxlength="50" onchange="updateDispatchFlag();" /></td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.consigneeShippingAddress" /></td>
       					<td><html:text property="invoiceConsigneeShippingAddress"styleId="invoiceConsigneeShippingAddress" styleClass="text"value="${dispatchDtl[0].invoiceConsigneeShippingAddress}" maxlength="50" onchange="updateDispatchFlag();" /></td>
        				<td><bean:message key="lbl.consignmentNo" /></td>
        				<td><html:text property="invoiceConsignmentNo"styleId="invoiceConsignmentNo" styleClass="text" value="${dispatchDtl[0].invoiceConsignmentNo}"maxlength="50" onchange="updateDispatchFlag();" /></td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.logisticsProvider" /></td>
        				<td><html:text property="invoiceLogisticsProvider"styleId="invoiceLogisticsProvider" styleClass="text" value="${dispatchDtl[0].invoiceLogisticsProvider}"maxlength="50" onchange="updateDispatchFlag();" /></td>
        				<td><bean:message key="lbl.deliveryStatus" /></td>
        				<td>
        					<html:select property="invoiceDeliveryStatus"styleId="invoiceDeliveryStatus" styleClass="text" value="${dispatchDtl[0].invoiceDeliveryStatus}" onchange="updateDispatchFlag();" >
        					<html:option value="">---Select----</html:option>
        					<html:option value="D">Delivered</html:option>
			            	<html:option value="P">Pending</html:option>
        					</html:select>
        				</td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.dispatchDate" /></td>
        				<td><html:text property="dispatchDate" styleClass="text" styleId="dispatchDate" value="${dispatchDtl[0].dispatchDate}" onchange="checkDate('dispatchDate');updateDispatchFlag();" /></td>
        				<td><bean:message key="lbl.delieveryDate" /></td>
        				<td><html:text property="delieveryDate" styleClass="text" styleId="delieveryDate" value="${dispatchDtl[0].delieveryDate}" onchange="checkDate('delieveryDate');updateDispatchFlag();" /></td>
     				</tr>
     				<tr>
						<td><span class="white">
							<button type="button" name="button1" class="topformbutton2"id="addDispatch" title="Alt+A" accesskey="A"onclick="return updateAssetDispatchDtl(${dispatchDtl[0].recordId});"><bean:message key="button.add" /></button>
						</span></td>
					</tr>
     			</table>
     		</FIELDSET>
     		</logic:present>
     		<logic:notPresent name="dispatchDtl">
     		<FIELDSET><LEGEND><bean:message key="lbl.dispatchDetails" /></LEGEND>
				<table>
					<tr>
        				<td style="width:330px;"><bean:message key="lbl.productType" /></td>
		     			<logic:present name="productList">
		     			<td style="width:230px;">
		     			<input type="hidden" name="dispatchFlag" id="dispatchFlag" value='N' />
		     			<input type="hidden" name="invoiceProductType" id="invoiceProductType" value="${dispatchDtl[0].invoiceProductType}" />
						<html:select property="invoiceProductTypeDesc" styleId="invoiceProductTypeDesc" styleClass="text" value="${dispatchDtl[0].invoiceProductTypeDesc}" >
						<html:option value="">---Select----</html:option>
						<logic:notEmpty name="productList">
					  	<html:optionsCollection name="productList" label="invoiceProductTypeDesc" value="invoiceProductTypeCode" />
						</logic:notEmpty>
						
			 			</html:select>
			 			</td>
			 			</logic:present>
		      			
		      			<logic:notPresent name="productList">
		      			<td style="width:230px;"><html:select property="invoiceProductType"styleId="invoiceProductType" styleClass="text" value="">
						<html:option value="">---Select----</html:option></html:select>
        				</td>
        				</logic:notPresent>
        				<td style="width:255px;"><bean:message key="lbl.dispatchQuantity" /></td>
        				<td><html:text property="invoiceDispatchQuantity"styleId="invoiceDispatchQuantity" styleClass="text" value="" onkeypress="return numbersonly(event,id,18);" maxlength="50" onchange="updateDispatchFlag();" /></td>
					</tr>
    				<tr>
        				<td><bean:message key="lbl.dispatchLocation" /></td>
        				<td><html:text property="invoiceDispatchLocation"styleId="invoiceDispatchLocation" styleClass="text" value=""maxlength="50" onchange="updateDispatchFlag();" /></td>
        				<td><bean:message key="lbl.delieveryLocation" /></td>
        				<td><html:text property="delieveryLocation"styleId="delieveryLocation" styleClass="text" value=""maxlength="50" onchange="updateDispatchFlag();" /></td>
        			</tr>
     				<tr>	
        				<td><bean:message key="lbl.consigneeName" /></td>
        				<td><html:text property="invoiceConsigneeName"styleId="invoiceConsigneeName" styleClass="text" value=""maxlength="50" onchange="updateDispatchFlag();" /></td>
     					<td><bean:message key="lbl.consignmentValue" /></td>
        				<td><html:text property="consignmentValue"styleId="consignmentValue" styleClass="text" value="" onkeypress="return numbersonly(event,id,18);" maxlength="50" onchange="updateDispatchFlag();" /></td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.consigneeShippingAddress" /></td>
       					<td><html:text property="invoiceConsigneeShippingAddress"styleId="invoiceConsigneeShippingAddress" styleClass="text"value="" maxlength="50" onchange="updateDispatchFlag();" /></td>
        				<td><bean:message key="lbl.consignmentNo" /></td>
        				<td><html:text property="invoiceConsignmentNo"styleId="invoiceConsignmentNo" styleClass="text" value=""maxlength="50" onchange="updateDispatchFlag();" /></td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.logisticsProvider" /></td>
        				<td><html:text property="invoiceLogisticsProvider"styleId="invoiceLogisticsProvider" styleClass="text" value=""maxlength="50" onchange="updateDispatchFlag();" /></td>
        				<td><bean:message key="lbl.deliveryStatus" /></td>
        				<td>
        					<html:select property="invoiceDeliveryStatus"styleId="invoiceDeliveryStatus" styleClass="text" value="" onchange="updateDispatchFlag();" >
        					<html:option value="">---Select----</html:option>
        					<html:option value="D">Delivered</html:option>
			            	<html:option value="P">Pending</html:option>
        					</html:select>
        				</td>
     				</tr>
     				<tr>
        				<td><bean:message key="lbl.dispatchDate" /></td>
        				<td><html:text property="dispatchDate" styleClass="text" styleId="dispatchDate" value="" onchange="checkDate('dispatchDate');updateDispatchFlag();" /></td>
        				<td><bean:message key="lbl.delieveryDate" /></td>
        				<td><html:text property="delieveryDate" styleClass="text" styleId="delieveryDate" value="" onchange="checkDate('delieveryDate');updateDispatchFlag();" /></td>
     				</tr>
     				<tr>
						<td><span class="white">
        				<button type="button" name="button1" class="topformbutton2"id="addDispatch" title="Alt+A" accesskey="A"onclick="return updateAssetDispatchDtl('');"><bean:message key="button.add" /></button>
						</span></td>
					</tr>
     			</table>
     		</FIELDSET>
     		</logic:notPresent>
		
			<FIELDSET><legend><bean:message key="lbl.gridToDisplayMultipleProductsCaptured" /></legend>
				<table cellspacing="0" cellpadding="3" border="0" width="100%">
					<tr class="white2">
	        			<td><B><bean:message key="contact.select" /></b></td> 
						<td><B><bean:message key="lbl.SNo" /></b></td>
						<td><B><bean:message key="lbl.consignmentNo" /></b></td>
						<td><B><bean:message key="lbl.consigneeName" /></b></td>
						<td><B><bean:message key="lbl.logisticsProvider" /></b></td>						
						<td><B><bean:message key="lbl.productType" /></b></td>	
						<td><B><bean:message key="lbl.deliveryStatus" /></b></td>
					</tr><% int n=0; %>
					<logic:present name="dispatchDetail">
					<logic:iterate id="subdispatchDtl" name="dispatchDetail">
					<tr class="white1">
					<td ><input type="checkbox" name="chk" id="chk${n}" value="${subdispatchDtl.recordId}"/></td>
					<td><%=++n %></td>
					<td ><a href="#" id="anchor0" onclick="return modifyDispatch(${subdispatchDtl.recordId});">${subdispatchDtl.invoiceConsignmentNo}</a></td>
					<td >${subdispatchDtl.invoiceConsigneeName}</td>
					<td>${subdispatchDtl.invoiceLogisticsProvider}</td>
					<td>${subdispatchDtl.invoiceProductTypeDesc}</td>
					<td>${subdispatchDtl.invoiceDeliveryStatus}</td>
					</tr>	
					</logic:iterate>
					<tr>
        				<td><span class="white">
        					<button type="button" name="button1" class="topformbutton2"title="Alt+D" accesskey="D" onclick="deleteAssetDispatchDetail(${subdispatchDtl.recordId});">
								<bean:message key="button.delete" />
							</button></span>
						</td>
					</tr>
					</logic:present>
				</table>
			</FIELDSET>
			<span class="white">
        		<button type="button" name="button2" class="topformbutton2"id="save" title="Alt+V" accesskey="V"onclick="return saveAssetDetail();">
					<bean:message key="button.save" />
				</button>
       		 </span>
	</html:form>
	</FIELDSET> 
	</logic:notPresent>
	<logic:present name="result">
    <logic:notEmpty name="result">
		<script type="text/javascript">
		if('<%=request.getAttribute("result")%>'=='dupProd')
		{
		   	alert("Product Type is already captured");
			
		}
		else if('<%=request.getAttribute("result")%>'=='dupInvoice')
		{
		   	alert("Invoice No. already exist");
			
		}
		else if('<%=request.getAttribute("result")%>'=='notAllowInvoice')
		{
		   	alert("Invoice No. should be same for current asset");
			
		}
		else if('<%=request.getAttribute("result")%>'=='Saved')
		{
		   	alert("Data saved successfully");
			
		}
		else if('<%=request.getAttribute("result")%>'=='notSaved')
		{
		   	alert("Data not saved");
			
		}
		</script>
	</logic:notEmpty>
	</logic:present>
</BODY>

</HTML>