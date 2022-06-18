<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="sdAccrualValues" >


   		<html:text property="gapInterestF" styleId="gapInterestF" 
			styleClass="text" value="${requestScope.sdAccrualValues[0].gapInterest}" 
			readonly="true" maxlength="18" tabindex="-1" style="text-align:right;"/>

   $:<html:text property="gapTDSF" styleId="gapTDSF" 
   			value="${requestScope.sdAccrualValues[0].gapTDS}"
			styleClass="text" readonly="true" tabindex="-1"
			maxlength="18" style="text-align:right;"/>

</logic:present>  
    
   