
<!--
 	Author Name :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose :- To provide user interface for corporate customer success
 	Documentation :-  
 -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
	 <input type="hidden" id="contextPath" name="contextPath" value="<%= request.getContextPath()%>"/>
	<form action="" id="maker" name="maker" method="post">
<logic:present name="customer_detail">

    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
	    <td><b><bean:message key="customer.id"/></b></td>
        <td><b><bean:message key="customer.name"/></b></td>
        <logic:iterate id="subcustomer_detail" name="customer_detail">
        	 <logic:equal name="subcustomer_detail" property="type" value="C">
			<td><b><bean:message key="constitution"/></b></td>
			<td><b><bean:message key="businessSegment"/></b></td>
			</logic:equal>
		</logic:iterate>
		<td><b><bean:message key="customer.type"/></b></td>

	</tr>
	
	
	<logic:iterate id="subcustomer_detail" name="customer_detail">
    <tr class="white1">
     	<td><a href="#" id="anchor0" onclick="return openUpdate(${subcustomer_detail.id});">${subcustomer_detail.id}
     	
     	<input type="hidden" name="hideId" id="hideId" value="${subcustomer_detail.id}"></a>
     	
     	</td>
        <td>${subcustomer_detail.name}</td>
        
		 <logic:equal name="subcustomer_detail" property="type" value="C">
        <td>${subcustomer_detail.custContitution}</td>
		<td>${subcustomer_detail.businessSegment}</td>
		</logic:equal>
		<td>${subcustomer_detail.fCustType}<input type="hidden" name="ctype" id="ctype" value="${subcustomer_detail.type}"></td>
   </tr>
   </logic:iterate>
 </table>
    
    </td>
</tr>
</table>
</logic:present>
</form>