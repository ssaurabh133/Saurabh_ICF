 <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="child">
	<logic:notEmpty name="child">
		1
	</logic:notEmpty>
	<logic:empty name="child">0</logic:empty>
</logic:present>