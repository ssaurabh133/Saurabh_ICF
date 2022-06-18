<!--Author Name :Kanika Maheshwari-->
<!--Date of Creation :13 October 2011  -->
<!--Purpose  : Information of Allocation master search-->
<!--Documentation : -->
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.util.ResourceBundle.*"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 



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
	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/capsScript/allocation.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	<%
		ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.ApplicationResources");

			int no = Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
			request.setAttribute("no", no);
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
<body onload="enableAnchor();init_fields();">


	<html:form styleId="AllocationForm" method="post"
		action="/allocationMasterSearch">
     <input type="hidden" name="psize" id="psize"/>

		<fieldset>
			<legend>
				<bean:message key="lbl.queueAllocationMaster" />
			</legend>

			<table>
				<tr>
					<td width="13%">
						<bean:message key="lbl.queue" />
					</td>
					<td width="13%">
					<logic:present name="searchList">
					<logic:notEmpty name="searchList">
						<html:text property="queueIdSearch" styleClass="text"
							styleId="queueIdSearch"
							value="${requestScope.searchList[0].queuedesc}" maxlength="30"
							readonly="true" />
							</logic:notEmpty>
						</logic:present>
						<logic:notPresent name="searchList">
						<html:text property="queueIdSearch" styleClass="text"
							styleId="queueIdSearch"
							value="${requestScope.queuedesc}"
							readonly="true" />
						</logic:notPresent>
						<html:button property="queueButton" styleId="queueButton"
							onclick="openLOVCommon(189,'AllocationMasterSearchForm','queueIdSearch','','', '','','','lbxQueuesearchId')"
							value=" " styleClass="lovbutton"></html:button>
						<input type="hidden" name="contextPath"
							value="<%=request.getContextPath()%>" id="contextPath" />
						<logic:present name="searchList">	
						<logic:notEmpty name="searchList">
						<html:hidden property="lbxQueuesearchId"
							styleId="lbxQueuesearchId"
							value="${requestScope.searchList[0].lbxQueuesearchId}" />
							</logic:notEmpty>
							</logic:present>
							<logic:notPresent name="searchList">
						<html:hidden property="lbxQueuesearchId"
							styleId="lbxQueuesearchId"
							value="${requestScope.queuecode}" />
							</logic:notPresent>
					</td>
					<td width="13%"><bean:message key="lbl.branch" /></td>
      				<td width="13%">
      				<html:text property="showbranchName" styleClass="text" styleId="showbranchName" maxlength="10" readonly="true" tabindex="-1"  />
    				<html:hidden  property="lbxBranchId" styleId="lbxBranchId" value="" />
      		    	<html:button property="branchButton" styleId="userButton" onclick="openLOVCommon(12,'AllocationMasterSearchForm','showbranchName','','', '','','','lbxBranchId')" value=" " styleClass="lovbutton"></html:button>
 
      				</td>
				</tr>
				<tr>
				  <td width="13%"><bean:message key="lbl.userId" /></td>
					<td width="13%">
												<input type="text" name="user" id="user"
													 readonly="readonly" class="text"  />
												<input type="hidden" name="lbxUserSearchId"
													id="lbxUserSearchId" />
												
												<input type="button" value="" name="userButton"
													id="userButton" class="lovbutton"
													onclick="openLOVCommon(286,'AllocationMasterSearchForm','user','','','','','','lbxUserSearchId')"
													class="lovbutton" />
											</td>
				</tr>
				<tr>
					<td>
						<input type="button" value="Search" id="search"
							class="topformbutton2" title="Alt+F" accesskey="F"
							onclick="return fnSearch('<bean:message key="lbl.selectAtleast" />');" />
						<input type="hidden" name="path" id="path"
							value="<%=request.getContextPath()%>" />
		<button type="button" name="add" id="add" class="topformbutton2" title="Alt+A" accesskey="A" onclick="return newAdd();"><bean:message key="button.add" /></button></td>					
					</td>
				</tr>

			</table>
		</fieldset>
<fieldset>
<legend><bean:message key="lbl.queueAllocationDetail" /></legend>


  <logic:present name="list">
   <logic:notEmpty name="list">
  
  <display:table  id="list"  name="list" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list[0].totalRecordSize}" requestURI="/allocationMasterSearch.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="alloctionGroupId" titleKey="lbl.batchsId"  sortable="true"/>
	<display:column property="queueIdModify" titleKey="lbl.queueCode"  sortable="true"/>
	<display:column property="user" titleKey="lbl.mappedUser"  sortable="true"/>
	<display:column property="showbranchName" titleKey="lbl.branch"  sortable="true"/>
	<display:column property="recStatus" titleKey="lbl.Status"  sortable="true"/>
</display:table>
</logic:notEmpty>
<logic:empty name="list">
<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message key="lbl.batchsId" /> </strong>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.queueCode" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.mappedUser" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.branch" /> <br> </b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.Status" /> <br> </b>
									</td>
									<tr class="white2" >
	                                  <td colspan="5"><bean:message key="lbl.noDataFound" /></td>
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
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>