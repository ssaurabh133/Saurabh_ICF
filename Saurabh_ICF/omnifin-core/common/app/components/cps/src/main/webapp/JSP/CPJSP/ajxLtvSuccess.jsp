<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="ltvList" >

      <logic:iterate name="ltvList" id="subltvList">

			${subltvList.ltv}
			$:${subltvList.txtStateCode}
			$:${subltvList.lbxmachineManufact}
			$:${subltvList.assetManufactDesc}
			$:${subltvList.gridValue}
			$:${subltvList.assetState}
			
	</logic:iterate>
    </logic:present>     
         