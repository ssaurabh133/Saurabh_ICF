<%@page import="org.springframework.web.context.request.RequestScope"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@page import="java.util.Date"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserObject userobj=(UserObject)session.getAttribute("userobject");
String initiationDate = userobj.getBusinessdate();

%>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />		 
		<!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
    	<script type="text/javascript"  src="<%=request.getContextPath() %>/js/sczScript/buyBackOperation.js"></script>
   
		<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
        int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
        request.setAttribute("no",no); %>
	</head>
	<body onload="enableAnchor();">
		<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	
	<html:form styleId="BuyBack" action="/buyBackProcessAction" method="post" enctype="multipart/form-data">   
	 <input type="hidden" id="contextPath" value="<%=request.getContextPath() %>" /> 
	<fieldset><legend><bean:message key="lbl.buyBackSearch"/></legend>  
     
		<fieldset><legend><bean:message key="lbl.UploadData"/></legend>  	 
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
			  	<td width="16%"><bean:message key="lbl.fileDescription"/></td>
			  	<td width="29%" align="left"><html:file size="40" property="docFile" styleId="docFile" styleClass="text"/></td>
			  	<td width="40%" colspan="1" align="left"><button type="button" name="upload"  id="upload" title="Alt+U" accesskey="U" class="topformbutton2" onclick="uploadCSVLoanForBuyBack();" ><bean:message key="button.upload"/></button></td>
			<td width="15%">&nbsp</td>
        </tr>
		 </table>
	</fieldset>
    </fieldset>
	</html:form>
</body>
</html:html>
<logic:present name="msg">
	<script type="text/javascript">
	alert('${msg}');
	</script>
</logic:present>
