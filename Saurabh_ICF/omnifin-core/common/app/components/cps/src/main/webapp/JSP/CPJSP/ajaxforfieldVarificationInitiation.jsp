<%--
      Author Name-  Ankit Agarwal
      Date of creation -1/11/2011
      Purpose- Use Ajax for Field Varification Info       
      
 --%>

<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/fieldVerificationScript.js"></script>
		


	


	
		<tr>
	
		<td width="20%">
		<logic:present name="assetList">
		<div id="assetlist">
		<html:select property="varificationSubType" styleId="varificationSubType"  styleClass="text" value="${listObj.varificationSubType}">
              <html:option value="">--Select--</html:option>
               <logic:iterate id="list" name="assetList" length="1">
               	<logic:equal name="status" value="Business">
                  <html:option value="B"><bean:write property="addType" name="list" /></html:option>
               </logic:equal>
               	<logic:equal name="status" value="OFFICE">
                  <html:option value="A"><bean:write property="addrType" name="list" /></html:option>
               </logic:equal>
               	<logic:equal name="status" value="BussOffice" >
                  <html:option value="B"><bean:write property="addType" name="list" /></html:option>
                  <html:option value="A"><bean:write property="addrType" name="list" /></html:option>
               </logic:equal>
               </logic:iterate>
       </html:select>
       </div>
       </logic:present>
       <logic:present name="tradeList">
		<div id="assetlist"> 
		<html:select property="varificationSubTypeTrade" styleId="varificationSubTypeTrade"  styleClass="text" value="${listObj.varificationSubType}">
               <html:option value="">--Select--</html:option>
                <html:option value="U">Buyer</html:option>
                <html:option value="S">Supplier</html:option>
                <html:option value="M">Market</html:option>
       </html:select>
       </div>
       </logic:present>
       <logic:present name="collList">
       <div id="assetlist"> 
		<html:select property="varificationSubTypeCollateral" styleId="varificationSubTypeCollateral"  styleClass="text" value="${listObj.varificationSubType}">
               <html:option value="">--Select--</html:option>
                <html:option value="A">Machine</html:option>
                <html:option value="V">Vehicle</html:option>
                <html:option value="P">Property</html:option>
                <html:option value="T">Stock</html:option>
                <html:option value="O">Other</html:option>
       </html:select>
       </div>
       </logic:present>
       </td>
	</tr>
	
	
