<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.logger.LoggerMsg"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html>
<head>

     <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	 <meta http-equiv="pragma" content="no-cache" />
	 <meta http-equiv="cache-control" content="no-cache" />
	 <meta http-equiv="expires" content="0" />
	 <meta name="author" content="" />

<title><bean:message key="title.name"/></title>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/contentstyle.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/subpage.css"/>
<link href="<%=request.getContextPath()%>/ddtabmenufiles/ddcolortabs.css" type="text/css" rel="stylesheet"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/mainstyle.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/login.js"></script>

     <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	 <meta http-equiv="pragma" content="no-cache" />
	 <meta http-equiv="cache-control" content="no-cache" />
	 <meta http-equiv="expires" content="0" />
	 <meta name="author" content="" />

<title>LPS - A S Software Services Pvt Ltd</title>

  </head>
  
  <body onload="enableAnchor();" marginwidth="0" marginheight="0" background="<%=request.getContextPath()%>/images/bg_loginpage.gif" scroll="yes"  topmargin="0" leftmargin="0" >
  
  <logic:present name="msg1">
  <logic:notEmpty name="msg1">
  <script type="text/javascript">
		if('<%=application.getAttribute("msg1").toString()%>'=='R')
 		{
 	    	alert("<bean:message key="lbl.allUserLogoutEOD" />");
 	    	top.parent.location="<%=request.getContextPath()%>/logoff.do"; 	 	
 		}
 		
	</script>
	</logic:notEmpty>
	</logic:present>
	
 
	
  	<logic:present name="msg">
  	 <logic:notEmpty name="msg">
	<script type="text/javascript">
		if('<%=application.getAttribute("msg").toString()%>'=='E')
		{
			alert('<bean:message key="lbl.eodIsRunning" />');
			
			top.parent.location="<%=request.getContextPath()%>/logoff.do";
		}
		else if('<%=application.getAttribute("msg").toString()%>'=='B')
	    {
	    	alert("Login is not allowed as EOD is running and BOD is not yet completed");
	    	
	    	top.parent.location="<%=request.getContextPath()%>/logoff.do";
	    	
	    }
	</script>
	</logic:notEmpty>
	</logic:present>
	<logic:notPresent name="msg">
	<script type="text/javascript">
			alert("<bean:message key="lbl.allReadyLogin" />");
			top.parent.location="<%=request.getContextPath()%>/logoff.do?stopUpdateQuery=stopUpdateQuery";
	</script>		
	</logic:notPresent>
	
   
  </body>
</html>
</script>