<%--
      Author Name-      Neeraj Kumar Tripathi
      Date of creation  19\10\2011
      Purpose-    
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";     
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date currentDate = new Date();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />    
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<base target="body" /> 
	</head>
	<body onload="enableAnchor();">
	<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
	<form name="test" id="test">
		<div class="tab-bg">
		<div class="ddcolortabs">
			<ul>
               <logic:present name="verifCPS">
					<li><a href="<%=request.getContextPath()%>/fieldVerComBody.do?method=viewCompletionTab"><span>Contact Verification Details</span></a></li>
					<li><a href="<%=request.getContextPath()%>/fieldVerComBody.do?method=completionAuthorVerification"><span>Completion</span></a></li>
				</logic:present>
				 <logic:present name="verifCMS">
					<li><a href="<%=request.getContextPath()%>/fieldVerComBody.do?method=viewCompletionTabAtCM"><span>Contact Verification Details</span></a></li>
					<li><a href="<%=request.getContextPath()%>/fieldVerComBody.do?method=completionAuthorVerificationAtCM"><span>Completion</span></a></li>
				</logic:present>
			</ul>
		</div>
		<div class="ddcolortabsline"></div>
		<div id="tabs"></div>	
		</div>
		</form>
	</body>
</html>