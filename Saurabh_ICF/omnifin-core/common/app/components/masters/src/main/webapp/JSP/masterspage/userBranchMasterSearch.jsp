
<!--Author Name : Ritu Jindal-->
<!--Date of Creation :25 May 2011  -->
<!--Purpose  : Information of USER BRANCH Master Search-->
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/userBranchScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");

int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>
</head>
<body onload="enableAnchor();init_fields();">
<html:form styleId="userBranchMasterSearchForm"  method="post"  action="/userBranchMasterSearch" >

<html:errors/>
<fieldset>
<legend><bean:message key="lbl.userBranchMaster" /></legend>

<table>
<tr> 
  
   <td width="13%"><bean:message key="lbl.userId" /></td>
    <td width="13%"> <html:text property="userSearchId" styleClass="text" styleId="userSearchId" maxlength="10" readonly="true"  />
        <img onClick="openLOVCommon(91,'userBranchMasterSearchForm','userSearchId','','', '','','','lbxUserSearchId')" src="<%= request.getContextPath()%>/images/lov.gif">
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxUserSearchId" styleId="lbxUserSearchId" value="" />
     </td>
    <td width="13%"><bean:message key="lbl.branchId" /></td>
     <td width="13%"><html:text property="branchSearchId" styleClass="text" styleId="branchSearchId" maxlength="30"   />
      <img onClick="openLOVCommon(90,'userBranchMasterSearchForm','branchSearchId','','', '','','','lbxBranchSearchId')" src="<%= request.getContextPath()%>/images/lov.gif">
       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <html:hidden  property="lbxBranchSearchId" styleId="lbxBranchSearchId" value="" />
     
     </tr>
     <tr> 
    <td>
    <button type="button"   id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="fnSearch('<bean:message key="lbl.selectAtleast" />');" ><bean:message key="button.search" /></button>   
     <input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
    <button type="button" name="add" id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="newAdd();"><bean:message key="button.add" /></button></td>
  </tr>
    
</table>
</fieldset>
<fieldset>
<legend><bean:message key="lbl.userBranchDetail" /></legend>

<logic:present name="list">
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" requestURI="/userBranchMasterBehind.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	<display:column property="userIdModify" titleKey="lbl.userId"  sortable="true"  />
	<display:column property="branchId" titleKey="lbl.branchId"  sortable="true"  />
	<display:column property="userBranchStatus" titleKey="lbl.active"  sortable="true"  /> 
	
</display:table>
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