
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<title></title>

</head>

<frameset rows="30,*" cols="*" framespacing="0" frameborder="no" border="0" >
  <frame name="menu" src="<%=request.getContextPath() %>/JSP/CPJSP/DealEntry.jsp" marginheight="0" scrolling="no">
  <logic:notEqual name="functionId" value="500000123">
  <frame name="body" src="<%=request.getContextPath() %>/JSP/CPJSP/leadEntry.jsp" marginheight="0" scrolling="yes">
  </logic:notEqual>
  <logic:equal name="functionId" value="500000123">
  <frame name="body" src="<%=request.getContextPath() %>/custEntryAction.do?&status=UWA">
  </logic:equal>
</frameset>
</html>