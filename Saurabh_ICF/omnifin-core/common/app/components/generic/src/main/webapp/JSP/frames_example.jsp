<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<title>Omni Fin - A S Software Services Pvt Ltd</title>

</head>

<input type="hidden" name="username" id="username" value="aaa" />
<frameset id="outerFrameSet" rows="109,*,27" frameborder="0" border="0" framespacing="0" >
  <frame name="topNav" src="JSP/top_nav.jsp"  marginheight="0" marginwidth="0" >
  
 <frameset id="setJsIds" cols="237,90%,0" frameborder="0" border="0" framespacing="0" >
	<frame id="leftContant" name="menu" src="JSP/menu_1.jsp" marginheight="0" marginwidth="0" scrolling="yes" noresize>
	<frame name="content" src="JSP/common.jsp" marginheight="0" marginwidth="0" scrolling="yes" noresize>
	<frame name="related" src="JSP/related.html" marginheight="0" marginwidth="0" scrolling="no" noresize>
</frameset>

  <frame name="footer" src="JSP/footer.jsp" scrolling="no" noresize>


<noframes>
<p>This section (everything between the 'noframes' tags) will only be displayed if the users' browser doesn't support frames. You can provide a link to a non-frames version of the website here. Feel free to use HTML tags within this section.</p>
</noframes>

</frameset>
</html>