<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 10 jan 2012
 	Purpose          :- To provide user interface for IndividualFinancialAnalysis FrameTab
 	Documentation    :- 
 -->

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

  <frame name="menu" src="<%=request.getContextPath()%>/JSP/CPJSP/individualFinancialAnalysisTab.jsp" marginheight="0" scrolling="no">
   <frame name="body" src="<%=request.getContextPath() %>/individualFinancialIncomeBehindAction.do?method=incomeBehindDetail" marginheight="0" scrolling="yes">
 </frameset>
</html>

