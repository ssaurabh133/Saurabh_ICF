<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<logic:present name="getDefaultWaiveOffDetail">
<logic:iterate name="getDefaultWaiveOffDetail" id="WaiveOffList">

    ${WaiveOffList.adviceAmount}
    $:${WaiveOffList.balanceAmount}
    $:${WaiveOffList.waivOffAmount} 
    
</logic:iterate>

</logic:present>
	