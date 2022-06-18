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
		ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.ApplicationResources");

			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
	%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
	<title><bean:message key="a3s.noida" />
	</title>
	
	<logic:present name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/${css }/displaytable.css" />
	</logic:present>
	<logic:notPresent name="css">
		<link type="text/css" rel="stylesheet"
			href="<%=request.getContextPath()%>/css/theme1/displaytable.css" />
	</logic:notPresent>
	
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/scoringScript.js"></script>		
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	
</head>

<body onload="enableAnchor();">
	<html:form action="/parameterScoreDef" styleId="ParameterScoreDef" method="post">
	
		<logic:present name="save">	
		<script type="text/javascript">

    if('<%=request.getAttribute("save").toString()%>'=='S')
	{
		alert("<bean:message key="lbl.dataSave" />");		
	}
	if('<%=request.getAttribute("save").toString()%>'=='E')
	{
		alert("<bean:message key="lbl.dataError" />");		
	}
	</script>	
	</logic:present>
		<input type="hidden" name="flag" id="flag" />	
		<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />			
		<fieldset>
			<legend>
				<bean:message key="lbl.parameterDesc" />
			</legend>
			<table width="100%">
						
				<tr>
					<td>
						<bean:message key="lbl.scoreCardID" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="scoreCardId" readonly="true" styleClass="text" styleId="scoreCardId"/>
				      <html:button styleClass="lovbutton" styleId="scoreButton" onclick="openLOVCommon(243,'ParameterScoreDef','scoreCardId','','lbxScoreDesc', '','','','description','profit');" value=" " tabindex="1" property="dealButton">
				      </html:button>
				      <html:hidden property="lbxScoreDesc" styleId="lbxScoreDesc"/>
     					
				     </td>
				     <td>
						<bean:message key="lbl.parameterDescription" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="description" readonly="true" styleClass="text" styleId="description"/>
				     </td></tr>
				     <tr>
				      <td>
						<bean:message key="lbl.parameterProfit" /><span><font color="red">*</font></span>
					</td>
				      <td>
				      <html:text property="profit" readonly="true" styleClass="text" styleId="profit"/>
				     </td> 
				</tr>
				
				<tr>
<td>
<button id="save" class="topformbutton2" type="button" onclick="searchParameterDesc();" accesskey="S" title="Alt+S" ><bean:message key="button.search" /></button>
<button id="add" class="topformbutton2" type="button" title="Alt+A" onclick="newParameterAdd();" accesskey="A" name="add">><bean:message key="button.add" /></button>
</td><td></td>
	</tr>
			</table>
		</fieldset>
		<fieldset>
		<legend>
				<bean:message key="lbl.parameterinfo" />
			</legend>		
				<logic:present name="paramlist">
				<display:table id="list" name="paramlist" style="width: 100%"
					class="dataTable" pagesize="${requestScope.no}" cellspacing="1"
					partialList="true" size="${requestScope.rowsige}"
					requestURI="/parameterScoreDef.do">
					<display:setProperty name="paging.banner.placement" value="bottom" />
					<display:setProperty name="locale.resolver"	value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:setProperty name="locale.provider"	value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
										
					<display:column property="parameterLink" titleKey="lbl.scoreCardID" sortable="true" />
					<display:column property="description" titleKey="lbl.parameterDescription" sortable="true" />
					<display:column property="profit" titleKey="lbl.parameterProfit" sortable="true" />
					
				</display:table>
				</logic:present>
			
		</fieldset>
		
		

			
	</html:form>	

</body>
</html:html>