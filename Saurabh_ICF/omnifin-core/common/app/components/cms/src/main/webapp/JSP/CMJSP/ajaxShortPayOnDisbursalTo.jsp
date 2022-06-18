<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
 
 <logic:present name="adjustedShortPayAmount">
    <logic:notEmpty name="adjustedShortPayAmount" >
    			${requestScope.adjustedShortPayAmount}
    			$:${requestScope.proposedShortPayAmount}
	</logic:notEmpty>
	</logic:present>

	