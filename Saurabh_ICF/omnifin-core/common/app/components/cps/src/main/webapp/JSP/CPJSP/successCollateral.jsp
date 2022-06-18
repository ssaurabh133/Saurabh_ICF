<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:present name="fetchCollateralDetails">


<logic:iterate name="fetchCollateralDetails" id="subfetchCollateralDetails">
	
	${subfetchCollateralDetails.assetsId}
	$:${subfetchCollateralDetails.colltype1}
	$:${subfetchCollateralDetails.colltype2}
	$:${subfetchCollateralDetails.colltype3}
	$:${subfetchCollateralDetails.assetsCollateralDesc}
	$:${subfetchCollateralDetails.assetsCollateralValue}
	$:${subfetchCollateralDetails.collateralSecurityMargin}

	$:${subfetchCollateralDetails.machineMake}
	$:${subfetchCollateralDetails.machineModel}
	$:${subfetchCollateralDetails.machineType}
	$:${subfetchCollateralDetails.machineOwner}
	$:${subfetchCollateralDetails.machineYearOfManufact}
	$:${subfetchCollateralDetails.machineIdNo}
	$:${subfetchCollateralDetails.machineSupplier}
	$:${subfetchCollateralDetails.machineCollateralCost}
	$:${subfetchCollateralDetails.machineMargin} 

	$:${subfetchCollateralDetails.propertyType}  
	$:${subfetchCollateralDetails.propertySpecify}
	$:${subfetchCollateralDetails.propertyAddress}
	$:${subfetchCollateralDetails.propertyConstruct}
	$:${subfetchCollateralDetails.propertyArea}
	$:${subfetchCollateralDetails.propertyOwner}

	$:${subfetchCollateralDetails.vehicleMake}
	$:${subfetchCollateralDetails.vehicleModel}
	$:${subfetchCollateralDetails.vehicleType}
	$:${subfetchCollateralDetails.vehicleOwner}
	$:${subfetchCollateralDetails.vehicleYearOfManufact}
	$:${subfetchCollateralDetails.vehicleRegNo}
	$:${subfetchCollateralDetails.vehicleRegDate}
	$:${subfetchCollateralDetails.vehicleChesisNo}
	$:${subfetchCollateralDetails.vehicleInsurer}
	$:${subfetchCollateralDetails.vehicleInsureDate}
	$:${subfetchCollateralDetails.vehicleCollateralCost}
	$:${subfetchCollateralDetails.vehicleMargin}

	$:${subfetchCollateralDetails.fdAmount}
	$:${subfetchCollateralDetails.fdTenure}
	$:${subfetchCollateralDetails.fdRate}
	$:${subfetchCollateralDetails.fdBookDate}
	$:${subfetchCollateralDetails.fdMatureDate}
	$:${subfetchCollateralDetails.fdAgencyName}
	$:${subfetchCollateralDetails.fdAgencyRating}
	$:${subfetchCollateralDetails.fdApplicants}	

    $:${subfetchCollateralDetails.sblcAmount}
	$:${subfetchCollateralDetails.sblcValidity}
	$:${subfetchCollateralDetails.sblcIssuingDate}
	$:${subfetchCollateralDetails.sblcParentCompany}

    $:${subfetchCollateralDetails.securityType}
	$:${subfetchCollateralDetails.securityCategory}
	$:${subfetchCollateralDetails.securityMarketValue}
		
	$:${subfetchCollateralDetails.stockType}
	$:${subfetchCollateralDetails.stockNature}
	$:${subfetchCollateralDetails.stockAddress}
	$:${subfetchCollateralDetails.stockCycle}

    $:${subfetchCollateralDetails.debtorType}
	$:${subfetchCollateralDetails.debtorTotal}
	
	$:${subfetchCollateralDetails.bgType}
	$:${subfetchCollateralDetails.bgInDate}
    
    $:${subfetchCollateralDetails.bgValidity}
	$:${subfetchCollateralDetails.bgIssuing}
    


</logic:iterate>

</logic:present>