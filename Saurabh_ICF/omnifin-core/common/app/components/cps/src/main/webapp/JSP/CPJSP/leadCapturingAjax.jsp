<%--

	Created By Sajog      
 	
 --%>
 
<%@ page language="java"%>
<%@ page session="true"%>
<%@ page import="java.util.Date"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html:html>
  
  <body onload="enableAnchor();">

	<html:form action="/ajaxAction1" styleId="LeadAjaxParameters" method="post">
		<input type="hidden" name="leadcapt" id="leadcapt" />
		<html:javascript formName="LeadAjaxParametersDynaValidator" />

		<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>" />
  

    </html:form>
  </body>
</html:html>
