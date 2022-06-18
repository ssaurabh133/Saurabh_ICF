<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="onAssetGetVehicleDetails"  >
    
       ${onAssetGetVehicleDetails[0].assetDesc}
     $:${onAssetGetVehicleDetails[0].assetNature}     
     $:${onAssetGetVehicleDetails[0].assetMake}
     $:${onAssetGetVehicleDetails[0].assetModel}
     $:${onAssetGetVehicleDetails[0].dealerName}
     $:${onAssetGetVehicleDetails[0].engineNumber}
     $:${onAssetGetVehicleDetails[0].chasisNumber}
     $:${onAssetGetVehicleDetails[0].registrationNumber}
     
</logic:present>