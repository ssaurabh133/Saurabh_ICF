<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="fleetDataList" >

   
	      <logic:iterate name="fleetDataList" id="subFleetDataList">
	
				${subFleetDataList.seasoning}
				$:${subFleetDataList.currentPos}
				$:${subFleetDataList.vehicleOwner}
				$:${subFleetDataList.relationship}
				$:${subFleetDataList.vehicleNo}
				$:${subFleetDataList.vehicleModel}
				$:${subFleetDataList.mfgYear}
			
		</logic:iterate>

	
 </logic:present>     
         