<!--
 	Author Name :- Ravindra Kumar
 	Date of Creation :- 
 	Purpose :- To provide user interface for first frame
 	Documentation :-  
 -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Approve Dialog</title>
</head>

<frameset rows="55,*" cols="*" framespacing="0" frameborder="no" border="0">
  <frame src="<%=request.getContextPath() %>/JSP/corporateTag.jsp" name="menu" scrolling="no" noresize="noresize" id="menu" title="menu" />
  <logic:notPresent name="custEntryU">
  <frame src="<%=request.getContextPath() %>/corporateDetailAction.do?method=displayCorporateDetails" name="body" id="body" title="body" />
  </logic:notPresent>
  <logic:present name="custEntryU">
  <frame src="<%=request.getContextPath() %>/corporateBusinessActivityBehindAction.do?method=displayBusinessActivity" name="body" id="body" title="body" />
  </logic:present>
</frameset>
<noframes>
<body onload="enableAnchor();">
</body>
</noframes></html>
