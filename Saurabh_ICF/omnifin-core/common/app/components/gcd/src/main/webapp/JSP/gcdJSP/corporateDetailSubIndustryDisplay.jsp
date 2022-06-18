<!--
 	Author Name :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose :- To provide user interface for corporate sub industry details
 	Documentation :-  
 -->

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:present name="subIndustryList">
<select name="subIndustry" id="subIndustry">
<option value="">---Select---</option>
<logic:iterate name="subIndustryList" id="subIndustryListObj">
<option value="${subIndustryListObj.subIndustryCode }">${subIndustryListObj.subIndustryName } </option>
</logic:iterate>
</select>
</logic:present>