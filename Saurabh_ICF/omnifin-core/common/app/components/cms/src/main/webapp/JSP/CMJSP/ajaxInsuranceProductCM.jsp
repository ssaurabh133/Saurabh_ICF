<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<div id="insuranceProductDiv">
<logic:present name="insuranceProducts">
<input type="hidden" id="sumAssuredMapping"  name="sumAssuredMapping" value="${sumAssuredMapping}"/>
 <html:select property="insuranceProduct" styleId="insuranceProduct" value="" styleClass="text" onchange="insuranceServiceCalled();" >
  	<html:option value="">--Select--</html:option>
	<html:optionsCollection name="insuranceProducts" value="insuranceProductId" label="insuranceProduct"/>
	</html:select>
</logic:present>
</div>