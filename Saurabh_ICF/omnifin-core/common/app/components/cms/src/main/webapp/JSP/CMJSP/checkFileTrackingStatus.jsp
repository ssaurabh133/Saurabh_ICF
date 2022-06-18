<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="checkFileStatus" >
	<logic:notEmpty name="checkFileStatus" >
      <logic:iterate name="checkFileStatus" id="subDealList">
			$:${subDealList.fileTrackStatus}
	</logic:iterate>
    </logic:notEmpty>    
</logic:present>



