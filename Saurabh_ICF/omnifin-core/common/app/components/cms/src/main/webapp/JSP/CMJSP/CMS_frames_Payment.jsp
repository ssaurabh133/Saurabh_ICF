<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html:html>
<head>
<title></title>

</head>

<frameset rows="155,2000,27" frameborder="0" border="0" framespacing="0">
  <frame name="menu" src="JSP/CMJSP/Payment.jsp" marginheight="0" scrolling="no" >
  <frame name="body" src="JSP/CMJSP/paymentMaker.jsp" marginheight="0" scrolling="yes" >
</frameset>
</html:html>