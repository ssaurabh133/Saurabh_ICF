
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:present name="schemeList">
<select name="scheme" id="scheme" class="text" onchange="return getDefaultLoanDetail();">
<option value="">---Select---</option>
<logic:iterate name="schemeList" id="subschemeList">
<option value="${subschemeList.id}">${subschemeList.name}</option>
</logic:iterate>
</select>
</logic:present>