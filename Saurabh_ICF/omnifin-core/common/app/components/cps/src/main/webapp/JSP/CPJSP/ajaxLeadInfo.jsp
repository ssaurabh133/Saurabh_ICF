<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="leadList" >

      <logic:iterate name="leadList" id="subleadList">

			${subleadList.sourceType}
			$:${subleadList.sourceName}
			$:${subleadList.sourceCode}
			$:${subleadList.dealRm}
			$:${subleadList.dealRmName}
			$:${subleadList.lbxareaCodeVal}
			$:${subleadList.areaCodename}
			$:${subleadList.source}
			$:${subleadList.lovSourceDes}
			$:${subleadList.sourcedesc}
			$:${subleadList.sourceTypeDesc}
	</logic:iterate>
    </logic:present>     
         