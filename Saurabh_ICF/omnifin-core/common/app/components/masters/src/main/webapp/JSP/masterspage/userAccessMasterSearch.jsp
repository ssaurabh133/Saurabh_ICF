<!--Author Name : Ritu Jindal-->
<!--Date of Creation : 25 May 2011-->
<!--Purpose  : Information of User Access Master Search-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/userAccessScript.js"></script>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
</head>
<body onload="enableAnchor();document.getElementById('userAccessMasterSearchForm').userButton.focus();init_fields();">

<html:form styleId="userAccessMasterSearchForm"  method="post"  action="/userAccessMasterSearch" >
<html:errors/>

<fieldset>
<legend><bean:message key="lbl.userAccessMaster" /></legend>
  <table width="100%">
    <tr>
    <td><bean:message key="lbl.userNam" /></td>
       <td ><html:text property="userSearchId" styleClass="text" styleId="userSearchId"  readonly="true" tabindex="-1"/>
       <html:button property="userButton" styleId="userButton" onclick="openLOVCommon(91,'userAccessMasterSearchForm','userSearchId','','', '','','','lbxUserSearchId')" value=" " styleClass="lovbutton"></html:button>
      <%--<img onClick="openLOVCommon(91,'userAccessMasterSearchForm','userSearchId','','', '','','','lbxUserSearchId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>      
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId" />
     </td>
    
       
     <td><bean:message key="lbl.moduleDesc" /></td>
       <td ><html:text property="moduleSearchId" styleClass="text" styleId="moduleSearchId"  readonly="true"  tabindex="-1"/>
       <html:button property="moduleButton" styleId="moduleButton" onclick="openLOVCommon(92,'userAccessMasterSearchForm','moduleSearchId','','roleSearchId', 'lbxRoleSearchId','','','lbxModuleSearch')" value=" " styleClass="lovbutton"></html:button>
        
      <%--<img onClick="openLOVCommon(92,'userAccessMasterSearchForm','moduleSearchId','','roleSearchId', 'lbxRoleSearchId','','','lbxModuleSearch')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>      
	<html:hidden  property="lbxModuleSearch" styleId="lbxModuleSearch" />
     </td>
     </tr>
       <tr>
     <td><bean:message key="lbl.roleDesc" /></td>
      <td ><html:text property="roleSearchId" styleClass="text" styleId="roleSearchId" maxlength="10" readonly="true" tabindex="-1"/>
      <html:button property="modulButton" styleId="modulButton" onclick="openLOVCommon(93,'userAccessMasterSearchForm','roleSearchId','moduleSearchId','lbxRoleSearchId', 'lbxModuleSearch','Please Select Module Code','','lbxRoleSearchId')" value=" " styleClass="lovbutton"></html:button>
      <%--<img onClick="openLOVCommon(93,'userAccessMasterSearchForm','roleSearchId','moduleSearchId','lbxRoleSearchId', 'lbxModuleSearch','Please Select Module Code','','lbxRoleSearchId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>      
      <html:hidden  property="lbxRoleSearchId" styleId="lbxRoleSearchId" />
       </td>
    </tr>

    <tr> 
   
    <td>
 <button type="button"   id="save" title="Alt+S" accesskey="S" class="topformbutton2" onclick="fnSearch('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="add"  title="Alt+A" accesskey="A" id="add" class="topformbutton2" onclick="newAddUserAccess();"><bean:message key="button.add" /></button></td>
  </tr>

	</table>		

</fieldset>
<br/>
    <fieldset>
<legend><bean:message key="lbl.userAccessdetail" /></legend>

  <logic:present name="list">
  <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/userAccessMasterBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="userAccessIdModify" titleKey="lbl.userAccessId"  sortable="true"  />
	<display:column property="userId" titleKey="lbl.userNam"  sortable="true"  />
	<display:column property="moduleId" titleKey="lbl.moduleDesc"  sortable="true"  />
	<display:column property="roleId" titleKey="lbl.roleDesc"  sortable="true"  />
	<display:column property="userAccessStatus" titleKey="lbl.active"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.userAccessId" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.userNam" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.moduleDesc" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.roleDesc" /> <br> </b>
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