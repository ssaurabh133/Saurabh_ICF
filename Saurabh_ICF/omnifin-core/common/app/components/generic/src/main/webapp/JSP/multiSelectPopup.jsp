
<%@page import="com.popup.vo.TableVo"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	TableVo obj = new TableVo();
	if(session.getAttribute("listForTable")!=null)
	{
    	 obj = (TableVo)(session.getAttribute("listForTable"));
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
	        <script type="text/javascript" src="<%=path%>/js/cmScript/cmLoanInitjs.js"></script>
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>

		<script type="text/javascript">
 

	
	
		$(function() {
		$("#incorporationDate").datepicker({
		 changeMonth: true,
		 changeYear: true,
	     yearRange: '1950:2010',
	     showOn: 'both',
		 buttonImage: 'images/calendar.gif',
		 buttonImageOnly: true
	
	
	});
     	});
     	
     function onButtonClick()
	{
 		
 		window.close();
	}
	
	function CallAction()
	{
		var hdnLOVId=document.getElementById("hdnLOVId").value;
	   	var LovListItemsIds = document.getElementById("LovListItemsIds").value;
	   	 var pParentGroup=document.getElementById("pParentGroup").value;
	     var strParentOption=document.getElementById("strParentOption").value;
	     
        //alert('${requestScope.strMethod}');  pass method by Ankit
        document.getElementById("form1").action="searchByParameter.do?method=CodeDetailsByParameter&multiLoveFlag=multiLoveFlag&LovListItemsIds="+LovListItemsIds+"&strMethod="+'${requestScope.strMethod}'+"&hdnLOVId="+hdnLOVId+"&pParentGroup="+pParentGroup+"&strParentOption="+strParentOption;
       // alert(document.getElementById("form1").action);
        document.getElementById("form1").submit();
	    return true;
	} 
	
	</script>
	<title><bean:message key="${listForTable.lovPageTitle}"/></title>
	
		<%
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	
	int no=Integer.parseInt(resource.getString("msg.pageSizeForLov"));
	String lovid=(String)session.getAttribute("hdnLOVId");
	
	if(lovid!=null && lovid.equalsIgnoreCase("223")){
	no=Integer.parseInt(resource.getString("msg.pageSizeForLovBranchMul"));;
	}else if(lovid!=null && lovid.equalsIgnoreCase("399")){
	  no=Integer.parseInt(resource.getString("msg.pageSizeForLovStateMult"));;
	}
	else{
	no=6;
	}
	
	request.setAttribute("no",no);


%>
	</head>
  <body onload="enableAnchor();" oncontextmenu="return false">
  
<center> 
<form name="form1" id="form1" action="/searchByParameter" method="post">
<table width="100%" border="0" cellspacing="0" cellpading="0" >
<tr><td align="right">

<input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
<input type="hidden" id="recordId" name="recordId" value="<%=StringEscapeUtils.escapeHtml(request.getParameter("selectRadioBtn")) %>"/>
<input type="hidden" id="targetFieldNameLOV" name="targetFieldNameLOV" value="${requestScope.targetFieldNameLOV}" />
<input type="hidden" id="targetHiddenLovId" name="targetHiddenLovId" value="<%=StringEscapeUtils.escapeHtml(obj.getLovIdComponent()) %>"/>
<input type="hidden" id="multiLoveFlag" value="multiLoveFlag"/>
<input type="hidden" id="LovListItemsIds" value="${requestScope.LovListItemsIds}"/>
<input type="hidden" id="hdnLOVId" name="hdnLOVId" value="${hdnLOVId}"/>
<input type="hidden" id="pParentGroup" name="pParentGroup" value="${pParentGroup}"/>
<input type="hidden" id="strParentOption" name="strParentOption" value="${strParentOption}"/>


<input type="hidden" id="noOfColumn" name="noOfColumn" value="${requestScope.noOfColumn}">
<logic:present name="image">
	<img width="24" height="24" src="<%=request.getContextPath()%>/${image}/save_icon.png" onclick="return multiSelectLovSaveRecord('${requestScope.strMethod}');" alt="Save" title="Save"/> <img width="24" height="24" src="<%=request.getContextPath()%>/${image}/x-icon.png" alt="Close Popup" title="Close Popup" onclick="onButtonClick();"/>
</logic:present>
 <logic:notPresent name="image">
 	<img width="24" height="24" src="<%=request.getContextPath()%>/images/theme1/save_icon.png" onclick="return multiSelectLovSaveRecord('${requestScope.strMethod}');" alt="Save" title="Save"/> <img width="24" height="24" src="<%=request.getContextPath()%>/images/theme1/x-icon.png" alt="Close Popup" title="Close Popup" onclick="onButtonClick();"/>
 </logic:notPresent>
</td></tr>


<tr ><td align="left" >&nbsp;<bean:message key="${listForTable.lovValueTitle}"/>&nbsp;&nbsp;  
<input type="text" name="lovDesc" id="lovCode" class="text" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="${listForTable.otherComponent1}"/> 
<input type="text" class="text" name="nextField" id="nextField" />
<logic:present name="image">
	<img src="<%=request.getContextPath()%>/${image}/Goforward.png" width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
</logic:present>
<logic:notPresent name="image">
	<img src="<%=request.getContextPath()%>/images/theme1/Goforward.png"  width="20" height="20" alt="Go" title="Go" onclick="return CallAction();">
</logic:notPresent>
</td></tr>
<tr > 
<td>
<logic:present name="two">
<display:table id="list"  name="list" style="width: 98%" pagesize="10" class="dataTable" cellspacing="3" requestURI="/popup.do">
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column  property="radiobutton" title="<input type='checkbox' name='chkd' id='allchkd' onclick='allChecked();' />"  />

	<display:column property="lovValue" titleKey="<%= StringEscapeUtils.escapeJavaScript(obj.getLovValue())%>"  sortable="true"  />
</display:table>
</logic:present>

<logic:present name="three">
<display:table id="list"  name="list" style="width: 100%" pagesize="${requestScope.no}" class="dataTable" cellspacing="3" requestURI="/multiSelectPopup.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="radiobutton" title="<input type='checkbox' name='chkd' id='allchkd' onclick='allCheckedMultiSelLov();' />"  />

	<display:column property="lovValue" titleKey="<%= StringEscapeUtils.escapeJavaScript(obj.getLovValue())%>" sortable="true" />
	<display:column property="valOfotherComponent1" titleKey="<%=StringEscapeUtils.escapeJavaScript(obj.getOtherComponent1()) %>" ></display:column>
</display:table>
</logic:present>

</td>
</tr>

</table>
</form>
</center>
  </body>
</html>
