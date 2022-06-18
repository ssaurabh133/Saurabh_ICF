
<!--Author Name : Ritu Jindal-->
<!--Date of Creation : 23 May 2011  -->
<!--Purpose  : Information of User Master Search-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/userScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
</head>
<body onload="enableAnchor();document.getElementById('userMasterSearchForm').userSearchId.focus();init_fields();">
<div align="center" class="opacity" style="display: none"
		id="processingImage"></div>
<html:form styleId="userMasterSearchForm"  method="post"  action="/userMasterSearch" >

<html:errors/>
<fieldset>
<legend><bean:message key="lbl.userMaster" /></legend>

<table>
<tr> 
  
    <td width="13%"><bean:message key="lbl.iduser" /></td>
    <td width="13%" ><html:text property="userSearchId" styleClass="text" styleId="userSearchId" maxlength="10" /></td>
     
     <td width="13%"><bean:message key="lbl.userNam" /></td>
     <td width="13%" ><html:text property="userSearchName" styleClass="text" styleId="userSearchName" maxlength="50" /></td>
     </tr>
    
    <tr>
    <td width="13%"><bean:message key="lbl.branchId" /></td>
      <td width="13%" ><html:text property="branchSearchId" styleClass="text" styleId="branchSearchId" maxlength="10" readonly="true" tabindex="-1"  />
      <html:hidden  property="lbxBranchSearchId" styleId="lbxBranchSearchId" />
       <html:button property="branchButton" styleId="branchButton" onclick="openLOVCommon(90,'userMasterSearchForm','branchSearchId','','', '','','','lbxBranchSearchId')" value=" " styleClass="lovbutton"></html:button>
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
      <%--<img onClick="openLOVCommon(90,'userMasterSearchForm','branchSearchId','','', '','','','lbxBranchSearchId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>
     </td><td><bean:message key="lbl.reportingto" /></td>
          <td><html:text property="report" styleClass="text" styleId="report" readonly="true" tabindex="-1" value="" /> 
      
       <html:button property="userButton" styleId="userButton" onclick="openLOVCommon(217,'userMasterSearchForm','report','','', '','','','lbxReportingUser')" value=" " styleClass="lovbutton"></html:button>
      <%--<img onClick="openLOVCommon(91,'userAccessMasterSearchForm','userSearchId','','', '','','','lbxUserSearchId')" src="<%= request.getContextPath()%>/images/lov.gif"> --%>      
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId" styleId="lbxReportingUser" value="" />
     </td>
    </tr>
     
    <tr> 
    <td>
     <button type="button"  id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="fnSearch('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>
     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="add"  id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="newAdd();"><bean:message key="button.add" /></button></td>
  </tr>
</table>
</fieldset>
<br/>
<fieldset>
<legend><bean:message key="lbl.userDetail" /></legend>

  <logic:present name="list">
   <logic:notEmpty name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${list[0].totalRecordSize}" requestURI="/userMasterBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="userIdModify" titleKey="lbl.iduser"  sortable="true"  />
	<display:column property="userName" titleKey="lbl.userNam"  sortable="true"  />
	<display:column property="branchId" titleKey="lbl.branchId"  sortable="true"  />
	<display:column property="userStatus" titleKey="lbl.active"  sortable="true"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.iduser" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.userNam" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.branchId" /> <br> </b>
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