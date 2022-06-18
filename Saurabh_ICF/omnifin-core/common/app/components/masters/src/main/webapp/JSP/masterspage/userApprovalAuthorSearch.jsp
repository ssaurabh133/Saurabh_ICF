<!--Author Name : Nishant Rai-->
<!--Date of Creation : 06-April-2013-->
<!--Purpose  : User Approval Matrix Author-->
<!--Documentation : -->


<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/masterScript/userApprovalMatrix.js"></script>
<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);
%>
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

<body onload="enableAnchor();document.getElementById('userApprovalAuthorSearch').dealButton.focus();">
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

<html:form action="/userApprovalAuthorBehindAction" styleId="userApprovalAuthorSearch" method="post" > 	
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"/>
<html:errors/>
<fieldset>
<legend><bean:message key="lbl.userApprovalAuthor"/></legend>
<table width="100%">
	<tr>
      	<td width="13%">
      		<bean:message key="lbl.userNam" />
      		<logic:notPresent name="search">
      			<span><font color="red">*</font></span>
      		</logic:notPresent>
      	</td>
      	<td width="13%">
      		<html:text property="userId" styleClass="text" styleId="userId" maxlength="100" readonly="true" value=""/>
			<html:hidden property="lbxUserId" styleId="lbxUserId" value=""/>
			<html:button property="dealButton" styleId="dealButton" tabindex="1" onclick="openLOVCommon(511,'userApprovalAuthorSearch','userId','','', '','','','lbxUserId')" value=" " styleClass="lovbutton"  ></html:button>
      	</td>
      	<td width="13%">
      		<bean:message key="lbl.UserRole" />
      	</td>
      	<td width="13%">
      			<html:select property="role" styleId="role" styleClass="text"  value="" onchange="userRole();">
					<html:option value="" >---Select---</html:option>
					<html:option value="U">Under Writer</html:option>
					<html:option value="P">Policy Approval</html:option>
				</html:select>
	  	</td>
	</tr>
</table>
	<table width="100%">
	<tr>
		<td>
				<button type="button"  name="search" title="Alt+S" accesskey="S"  id="search" class="topformbutton2" onclick="return searchAuthorRecords('Y');"><bean:message key="button.search" /></button>
		</td>
	</tr>
	</table>
</fieldset>
</html:form>

<logic:present name="list">
<fieldset><legend><bean:message key="lbl.approvedUser"/></legend> 
<logic:empty name="list">
  <table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.userNam" /> </strong>
									</td>
									
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.UserRole" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.level" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.subRuleType" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.amountFrom" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.amountTo" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.active" /> <br> </b>
									</td>
									<tr class="white2">
	                                  <td colspan="8"><bean:message key="lbl.noDataFound" /></td>
                                    </tr>
							</tr>
			            </table>
						</td>
					</tr>
				</table> 	
  </logic:empty>
<logic:notEmpty name="list"> 
<logic:notPresent name="searchList">
<logic:present name="list">

	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" size="${requestScope.list[0].totalRecordSize}" partialList="true" requestURI="/userApprovalMatrixBehindAction.do" >
	   	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    
		<display:column property="userId" titleKey="lbl.userNam"  sortable="true" />
		<display:column property="role" titleKey="lbl.UserRole"  sortable="true"  />
		<display:column property="level" titleKey="lbl.level"  sortable="true"  style="text-align: center"/>
		<display:column property="subRuleType" titleKey="lbl.subRuleType"  sortable="true"  style="text-align: center" />
		<display:column property="amountFrom" titleKey="lbl.amountFrom"  sortable="true"  style="text-align: right"/>
		<display:column property="amountTo" titleKey="lbl.amountTo"  sortable="true"  style="text-align: right" />
		<display:column property="recStatus" titleKey="lbl.active"  sortable="true"  style="text-align: center" />
	</display:table>
</logic:present>
</logic:notPresent>
<logic:present name="searchList">

	<logic:present name="list">
	<display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" size="${requestScope.list[0].totalRecordSize}" partialList="true" requestURI="/userApprovalMatrixAction.do?method=searchApprovedRecords" >
	   	<display:setProperty name="paging.banner.placement"  value="bottom"/>
    	<display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    	<display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
	    
		<display:column property="userId" titleKey="lbl.userNam"  sortable="true" />
		<display:column property="role" titleKey="lbl.UserRole"  sortable="true"  />
		<display:column property="level" titleKey="lbl.level"  sortable="true"  style="text-align: center"/>
		<display:column property="subRuleType" titleKey="lbl.subRuleType"  sortable="true"  style="text-align: center" />
		<display:column property="amountFrom" titleKey="lbl.amountFrom"  sortable="true"  style="text-align: right"/>
		<display:column property="amountTo" titleKey="lbl.amountTo"  sortable="true"  style="text-align: right" />
		<display:column property="recStatus" titleKey="lbl.active"  sortable="true"  style="text-align: center" />
	</display:table>
</logic:present>


</logic:present>
</logic:notEmpty>
</fieldset>
</logic:present>

</body>	
</html:html>
	