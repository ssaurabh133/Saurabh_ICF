<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="finInstitutionList" >

   
	      <logic:iterate name="finInstitutionList" id="subFinInstitutionList">
	
				$:${subFinInstitutionList.financialInstName}
		</logic:iterate>

	
 </logic:present>     
         