<!--Author Name : Ritu Jindal-->
<!--Date of Creation :18 May 2011 -->
<!--Purpose  : Information of Role Master Search-->
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/roleScript.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
</head>
<body onload="enableAnchor();document.getElementById('roleMasterSearchForm').roleId.focus();init_fields();">


<html:form styleId="roleMasterSearchForm"  method="post"  action="/roleMasterSearch" >

<html:errors/>

<fieldset>
<legend><bean:message key="lbl.roleMaster" /></legend>
  <table width="100%">
   <tr>
    <td width="13%"><bean:message key="lbl.roleId" /></td>
      <td width="13%"><html:text property="roleId" style="text-align: right" styleClass="text" styleId="roleId" maxlength="10" /></td>
      
      <td width="13%"><bean:message key="lbl.roleDesc" /></td>
      <td width="13%"><html:text property="roleSearchDesc" styleClass="text" styleId="roleSearchDesc" maxlength="50" />
      
    </td> </tr><tr> 
   
    <td>
     <button type="button"  id="save" title="Alt+S" accesskey="S" class="topformbutton2" onclick="fnSearch('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="add" title="Alt+A" accesskey="A" id="add" class="topformbutton2" onclick="newAdd();"><bean:message key="button.add" /></button></td>
  </tr>

	</table>		

</fieldset>
<br/>
    <fieldset>
<legend><bean:message key="lbl.roledetail" /></legend>

  <logic:present name="list">
  <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/roleMasterBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="roleIdModify" titleKey="lbl.roleId"  sortable="true"  />
	<display:column property="roleDesc" titleKey="lbl.roleDesc"  sortable="true"  />
	<display:column property="moduleId" titleKey="lbl.moduleDesc"  sortable="true"  />
	<display:column property="roleStatus" titleKey="lbl.active"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.roleId" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.roleDesc" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.moduleDesc" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.active" /> <br> </b>
									</td>
									<tr class="white2" >
	                                  <td colspan="7"><bean:message key="lbl.noDataFound" /></td>
                                    </tr>
							</tr>
			            </table>
						</td>
					</tr>
				</table>
</logic:empty>
  </logic:present>
</fieldset>    
           
	</html:form>		
	<logic:present name="sms">
<script type="text/javascript">

    
	
	
	if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		
	}
	
	
	
</script>
</logic:present>	
  </body>
		</html:html>