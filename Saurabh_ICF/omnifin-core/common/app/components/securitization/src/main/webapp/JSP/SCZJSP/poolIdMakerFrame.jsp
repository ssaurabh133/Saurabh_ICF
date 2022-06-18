<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<html>
<head>
<title></title>

</head>


<frameset rows="30,*" cols="*" framespacing="0" frameborder="no" border="0" >

  <frame name="menu" src="<%=request.getContextPath() %>/JSP/SCZJSP/poolIDTab.jsp" marginheight="0" scrolling="no">
  <frame name="body" src="<%=request.getContextPath() %>/JSP/SCZJSP/poolIdMakerAdd.jsp" marginheight="0" scrolling="yes">



</frameset>
</html>