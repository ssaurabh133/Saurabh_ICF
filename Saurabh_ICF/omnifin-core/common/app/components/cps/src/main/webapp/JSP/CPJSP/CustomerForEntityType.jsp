<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
  <logic:present name="customerList">
	<select name="entityCustomerId" class="text2" id="entityCustomerId" >
<option value="">--Select--</option>
<logic:iterate id="id" name="customerList"> 
<option value="<bean:write name="id" property="customerCode" />"><bean:write name="id" property="customerDesc" /></option>
</logic:iterate>
 </select>
 
  </logic:present>