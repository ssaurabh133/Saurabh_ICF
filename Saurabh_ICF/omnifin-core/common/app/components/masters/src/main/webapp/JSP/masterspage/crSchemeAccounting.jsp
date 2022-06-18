<!--Author Name : Ankit Agarwal-->
<!--Date of Creation : 25-07-11-->
<!--Purpose  : Information of Scheme Accouting Add-->
<!--Documentation : -->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>

		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />

<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/masterScript/crSchemeMaster.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

<!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
<! [endif] -->
</head>
<body onload="enableAnchor();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

<html:form styleId="CrSchemeAccountingForm"  method="post"  action="/crStageMapping" >
<fieldset>
<legend><bean:message key="lbl.stageDetail" /></legend>
<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>"/>


<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="acDetailTbl">
  <tr class="white2">
  
 			 <td ><input type="checkbox" name="allchkd" id="allchkd" onclick="allChecked();" /></td>
			<td ><b><bean:message key="lbl.stageCode" /></b></td>
			<td ><b><bean:message key="lbl.accountFlag" /></b></td>
   
  </tr>
  <logic:present name="list">
<logic:iterate name="list" id="sublist" indexId="counter">

  <tr class="white1">
   <td>
  <logic:equal value="Yes" property="accountingFlag" name="sublist">
   <input type="checkbox" name="chk"  id="chk<%=counter.intValue()+1%>" checked="checked" value="${sublist.stageId}"/>
  </logic:equal>
 <logic:notEqual value="Yes" property="accountingFlag" name="sublist">
  <input type="checkbox" name="chk"   id="chk<%=counter.intValue()+1%>"  value="${sublist.stageId}"/>
 </logic:notEqual>
 
  <input type="hidden" name="sublist" id="stageId" />
  <input type="hidden" name="sublist" value="${requestScope.schemeId}" id ="schemId"/>
  </td>
  <td>${sublist.stageDes}</td>
  <td>${sublist.accountingFlag}</td>
  </tr>
  
  </logic:iterate>
  </logic:present>

</table></td></tr>
<tr><td>
	<logic:present name="save">
		    <button type="button" name="save" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveAccountDetail();" ><bean:message key="button.save" /></button>
	
	</logic:present>
	<logic:notPresent name="save">
		    <button type="button" name="save" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return modifyAccountDetail('${requestScope.schemeId}');" ><bean:message key="button.save" /></button>
	</logic:notPresent>	</td>	   	
</tr>
</table></fieldset>
</html:form>

<logic:present name="msg">
<script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");
		window.close();	
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='M')
	{
		alert("<bean:message key="lbl.dataModify" />");
		window.close();	
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert("<bean:message key="msg.notepadError" />");
		window.close();	
	}
	else
	{
		alert("<bean:message key="lbl.dataExist" />");
		window.close();	
	}

</script>
</logic:present>


</body>
</html:html>