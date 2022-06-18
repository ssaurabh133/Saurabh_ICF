<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<div id="customerNameDiv">
<logic:present name="customerNameList">
 <html:select property="customerName" styleId="customerName" value="" styleClass="text">
	<html:optionsCollection name="customerNameList" value="parameCode" label="paramName"/>
	</html:select>
</logic:present>
</div>