<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="updateAssetList" >
          
  <logic:iterate name="updateAssetList" id="subloanList" >

       ${subloanList.assetNature}
     $:${subloanList.assetsCollateralDesc}
     $:${subloanList.vehicleMake}
     $:${subloanList.vehicleModel}
     $:${subloanList.assetManufact}
     $:${subloanList.machineSupplier}
  	 $:${subloanList.usageType}
     $:${subloanList.txtStateCode}
     $:${subloanList.collateralSecurityMarginDF}
     $:${subloanList.collateralSecurityMargin}
     $:${subloanList.loanAmount}
     $:${subloanList.vehicleCost}
     $:${subloanList.vehicleDiscount}
     $:${subloanList.assetsCollateralValue}
     $:${subloanList.gridValue}
     $:${subloanList.valuationCost}
     $:${subloanList.securityTypes}
     $:${subloanList.assetStandard}
     $:${subloanList.assetId}
     $:${subloanList.vehicleYearOfManufact}
     $:${subloanList.vehicleRegNo}
     $:${subloanList.vehicleRegDate}
     $:${subloanList.vehicleChesisNo}
     $:${subloanList.vehicleInsurer}
     $:${subloanList.vehicleInsureDate}
     $:${subloanList.vehicleOwner}
     $:${subloanList.engineNumber}
     $:${subloanList.idv}
     $:${subloanList.vehicleInvoiceDate}
     $:${subloanList.invoiceNumber}
     $:${subloanList.rcReceived}
     $:${subloanList.rcReceivedDate}
     $:${subloanList.invoiceAmount}
     $:${subloanList.permitReceived}
	 $:${subloanList.permitReceivedDate}
	 $:${subloanList.invoiceUpdateCheckBox}
     $:${subloanList.rcUpdateCheckBox}
	 $:${subloanList.insuranceUpdateCheckBox}
	 				
			
  	</logic:iterate> 
    </logic:present>