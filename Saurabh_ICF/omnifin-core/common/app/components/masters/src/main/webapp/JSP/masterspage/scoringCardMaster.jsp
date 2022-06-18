
<%@ page session="true"%>
<%@page import="java.util.Date"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>

<title><bean:message key="a3s.noida" /></title>

   		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/scoringScript.js"></script>
   <script type="text/javascript">  
   </script>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
</head>

<body onload="enableAnchor();document.getElementById('scoreCardMasterSearch').scoreCardId.focus();init_fields();">
<html:form  styleId="scoreCardMasterSearch" method="post" action="/scoreCardMasters">
<html:javascript formName="ScoreCardMasterSearchDynaValidatorForm" />
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<fieldset>
<legend><bean:message key="lbl.scoringCard" /></legend> 
	<table width="100%">
	
	<tr>
     <td width="13%"><bean:message key="lbl.scoringCardCode"/></td>
     <td width="13%"><html:text property="scoreCardId" styleId="scoreCardId" style="text-align: right" styleClass="text" maxlength="10" />
      </td>
      
    <td width="13%"><bean:message key="lbl.cardDesc"/></td>
     <td width="13%"><html:text property="scoreCardDesc" styleId="scoreCardDesc" styleClass="text" maxlength="50" />
      </td>
    </tr>
 <tr> 
     <td>
   	  <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchCardScore();" ><bean:message key="button.search" /></button>
     <button type="button" name="button2"   id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="scoreCardAdd();" ><bean:message key="button.add" /></button></td>
  </tr>
	 </table>
</fieldset>

</br>
<fieldset>
		 <legend><bean:message key="lbl.scoringCardDetails" /></legend> 
		 
  <logic:present name="list">
 <logic:notEmpty name="list">
   <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/scoreCardMasters.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="scoreCardId" titleKey="lbl.scoringCardCode"  sortable="true"  />
	<display:column property="scoreCardDesc" titleKey="lbl.cardDesc"  sortable="true"  />
	<display:column property="productId" titleKey="lbl.productDesc"  sortable="true"  />
	<display:column property="cardStatus" titleKey="lbl.status"  sortable="true"  />
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
         <td><b><bean:message key="lbl.scoringCardCode"/></b></td>
        <td><b><bean:message key="lbl.cardDesc"/></b></td>     
        <td><b><bean:message key="lbl.productDesc"/></b></td>
        <td><b><bean:message key="lbl.status"/> </b></td>
	</tr>
	
 </table>    </td>
</tr>
</table>
	<script type="text/javascript">

		alert("<bean:message key="lbl.noDataFound" />");
	
	</script>

</logic:empty>
  </logic:present>
</fieldset>    
           
	</html:form>		
	<logic:present name="sms">
<script type="text/javascript">

    
	
	
	if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
			document.getElementById("scoreMasterSearch").action=sourcepath+"/scoringSearchMasters.do?method=scoringMasterList";
			document.getElementById("scoreMasterSearch").submit();
		
	}
	
	
	
</script>
</logic:present>	
  </body>
		</html>