
<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for City Details
 	Documentation    :- 
 -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 


<logic:present name="detailcityList">
<select name="dist" id="dist">
<option value="">---Select---</option>
<logic:iterate name="detailcityList" id="subdetailcityList">

<option value="${subdetailcityList.dist_code }">${subdetailcityList.dist_name } </option>

</logic:iterate>
</select>
</logic:present>

<logic:present name="detailregionList">


<logic:iterate name="detailregionList" id="subdetailregionList">
$:
${subdetailcityList.region_name }
$:
${subdetailcityList.region_code }


</logic:iterate>

</logic:present>