<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="reschCharges" >
	<logic:notEmpty name="reschCharges" >
      <logic:iterate name="reschCharges" id="subloanList">
	  <bean:write name="subloanList" property="reschCharges" />
	$:<bean:write name="subloanList" property="interestForGapPeriod" />
	</logic:iterate>
    </logic:notEmpty>
</logic:present>