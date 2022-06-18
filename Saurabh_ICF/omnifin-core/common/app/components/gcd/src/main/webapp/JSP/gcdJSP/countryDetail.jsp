<!--
 	Author Name :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose :- To provide user interface for corporate country details
 	Documentation :-  
 -->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="detailcountryList">
<select name="state" id="state" onchange="cityDetail();">
<option value="">---Select---</option>
<logic:iterate name="detailcountryList" id="subdetailcountryList">

<option value="${subdetailcountryList.state_code }">${subdetailcountryList.state_name } </option>

</logic:iterate>
</select>
</logic:present>