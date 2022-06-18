<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%
		//for releasing lock record from application level object 
	   com.login.roleManager.UserObject userobj=( com.login.roleManager.UserObject)session.getAttribute("userobject");
	   ServletContext context = config.getServletContext();
	   if(context!=null && userobj!=null)
		{
		  boolean Lflag = com.lockRecord.action.ReleaseRecordFromObject.releaselockedRecord(userobj.getUserId(), context);
		}
	%> 
<html>
<head>
<title><bean:message key="title.name"/></title>



</head>
	<body onload="enableAnchor();">
	
		<jsp:include page="/JSP/top_nav_home.jsp" />
		<jsp:include page="/JSP/welcome.jsp" />
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody><tr>

			<td class="login-footer-left"><img width="13"  src="<%=request.getContextPath()%>/images/theme1/spacer.gif" /></td>
			<td width="100%" align="center" class="login-footer-middle">
			 &copy; Copyright 2011  <a href="http://a3spl.com" target="_blank">
A S Software Services Pvt Ltd</a> | <a href="#">Terms &amp; Conditions</a>
   </td>
			<td class="login-footer-right"><img width="15"  src="<%=request.getContextPath()%>/images/theme1/spacer.gif" /></td>
	</tr>
</tbody></table>
	</body>

</html>