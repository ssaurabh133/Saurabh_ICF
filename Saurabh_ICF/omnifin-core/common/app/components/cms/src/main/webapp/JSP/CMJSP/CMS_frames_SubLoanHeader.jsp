
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
  <frame name="menu" src="<%=request.getContextPath() %>/JSP/CMJSP/loanSubHeaderInit.jsp" marginheight="0" scrolling="no">
  <frame name="subBody" src="<%=request.getContextPath() %>/JSP/CMJSP/loanDetails.jsp" marginheight="0" scrolling="yes">
</frameset>

</html>