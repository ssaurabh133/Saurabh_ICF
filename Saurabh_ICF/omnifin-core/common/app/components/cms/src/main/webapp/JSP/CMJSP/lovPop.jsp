<%@page import="com.cm.vo.PaymentMakerForCMVO"%>
<%@page import="com.logger.LoggerMsg"%>
<%@page import="java.util.Iterator"%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
	if(session.getAttribute("listForTable")!=null)
	{
    	 paymentVO = (PaymentMakerForCMVO)(session.getAttribute("listForTable"));
	}
%>
<html>
 <head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1"/>
		<meta name="author" content="" />
		 <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
	   
		 
		 <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	      <!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customerDetail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/search_customer_detail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/paymentMaker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>

		<script type="text/javascript">
      	
     function onButtonClick()
	{
 		
 		window.close();
	}
	
	function CallAction()
	{
        document.getElementById("form1").action="searchByParameter.do?method=CodeDetailsByParameter";
        document.getElementById("form1").submit();
	    return true;
	} 
	
	</script>
	<title>Pupop</title>
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
	
<center> 
<form name="form1" id="form1" action="/ajaxAction" method="post">
<table width="100%" border="1" >
<tr><td align="right">
<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
<input type="hidden" id="recordId" name="recordId" value="<%=request.getParameter("selectRadioBtn") %>"/>
<input type="hidden" id="targetFieldNameLOV" name="targetFieldNameLOV" value="${requestScope.targetFieldNameLOV}" />

<input type="hidden" id="targetHiddenLovId" name="targetHiddenLovId" value="<%=paymentVO.getLovIdComponent() %>"/>
<img width="4%" height="4%" src="<%=request.getContextPath()%>/images/save_icon.png" onclick="return saveRecord1();" alt="Save" title="Save"/>  <img src="<%=request.getContextPath()%>/images/x-icon.png" alt="Close Popup" title="Close Popup" onclick="onButtonClick();"/>
</td></tr>

<tr><td><center><b><u> <bean:message key="${listForTable.lovPageTitle}"/> </u></b></center></td></tr>

<tr><td align="right" ><bean:message key="${listForTable.lovKeyTitle}"/> : <input type="text" name="lovCode" id="lovCode" class="text" />&nbsp;<bean:message key="${listForTable.lovValueTitle}"/> :<input type="text" class="text" name="lovDesc" id="lovDesc" /> <img src="images/Goforward.png" align="right" width="4%" height="4%" alt="Go" title="Go" onclick="return CallAction();"></td></tr>
<tr> 
<td>
 
<display:table id="list"  name="list" style="width: 100%" pagesize="6" class="dataTable" cellspacing="3" requestURI="/popup.do">
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:column property="radiobutton" title="Select" />
	<display:column property="lovKey" title="<%= paymentVO.getLovKey() %>" sortable="true" />
	<display:column property="lovValue" title="<%= paymentVO.getLovValue()%>" sortable="true" />
</display:table>

</td>
</tr>

</table>
</form>
</center>
  </body>
</html>
