<!--Author Name : Yogesh
Date of Creation : 29-April-2011
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>

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

<body onload="enableAnchor();document.getElementById('scoreMasterSearch').scoreCode.focus();init_fields();">
<html:errors />
<html:form  styleId="scoreMasterSearch" action="/scoringSearchMasters">
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />

<fieldset>
<legend><bean:message key="lbl.scoringCode" /></legend> 
	<table width="100%">
	
	<tr>
     <td width="13%"><bean:message key="lbl.scoringCode"/></td>
     <td width="13%"><html:text property="scoreCode" styleId="scoreCode" styleClass="text" maxlength="10" />
      </td>
      
    <td width="13%"><bean:message key="lbl.scoringDesc"/></td>
     <td width="13%"><html:text property="scoreDesc" styleId="scoreDesc" styleClass="text" maxlength="50" />
      </td>
    </tr>
 <tr> 
     <td>
   	  <button type="button"   id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="searchScore();" ><bean:message key="button.search" /></button>
     <button type="button" name="button2"   id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="scoreAdd();" ><bean:message key="button.add" /></button></td>
  </tr>
	 </table>
</fieldset>

</br>
<fieldset>
		 <legend><bean:message key="lbl.scoringDetail" /></legend> 
		 
  <logic:present name="list">
  <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/scoringSearchMasters.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="scoreCode" titleKey="lbl.scoringCode"  sortable="true"  />
	<display:column property="scoreDesc" titleKey="lbl.scoringDesc"  sortable="true"  />
	<display:column property="scoreStatus" titleKey="lbl.scoringStatus"  sortable="true"  />
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
        <td ><b><bean:message key="lbl.scoringCode"/></b></td>
         <td><b><bean:message key="lbl.scoringDesc"/></b></td>
        <td><b><bean:message key="lbl.scoringStatus"/></b></td>     
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

	}
	
	
	
</script>
</logic:present>	
  </body>
		</html>