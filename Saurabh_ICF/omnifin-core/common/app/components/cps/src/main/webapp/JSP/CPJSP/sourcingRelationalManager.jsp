<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:present name="relationalManagerList">
<select name="relationshipManager" id="relationshipManager">
<option value="">---Select---</option>
<logic:iterate name="relationalManagerList" id="relationalManagerListObj">
<option value="${relationalManagerListObj.id }">${relationalManagerListObj.name }</option>
</logic:iterate>
</select>
</logic:present>