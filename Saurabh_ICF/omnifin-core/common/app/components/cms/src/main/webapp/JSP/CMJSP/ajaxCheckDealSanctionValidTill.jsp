<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:equal name="checkDealValidTill" value="T" >
T
</logic:equal>  
       
    
<logic:equal name="checkDealValidTill" value="F">
       <bean:message key="msg.sanctionValid" />
</logic:equal>