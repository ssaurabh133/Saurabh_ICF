<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="dealNumberValues" >
	<logic:notEmpty name="dealNumberValues" >
      <logic:iterate name="dealNumberValues" id="subDealList">
			$:${subDealList.lbxDealNo}
	</logic:iterate>
    </logic:notEmpty>    
</logic:present>



