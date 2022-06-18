<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@page import="com.login.roleManager.UserObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>
	<%
		String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":"
					+ request.getServerPort() + path + "/";

			UserObject userobj = (UserObject) session
					.getAttribute("userobject");
			String initiationDate = userobj.getBusinessdate();
			String branchId = userobj.getBranchId();
	%>


	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="" />
	
	<script type="text/javascript" 
		src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
    <script type="text/javascript" 
    	src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>

<style type="text/css">
	.readonly{
			width:150px !important;
	}
</style>

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
<body onclick="parent_disable();" onunload="closeAllLovCallUnloadBody();" onload="enableAnchor();">
	<div id=centercolumn>
		<div id=revisedcontainer>

			<!-- --------------------------------------- Deal Reassignment Edit --------------------------------- -->

				<html:form action="/dealReassignmentMaker" method="post"
					styleId="dealReassignmentEditForm">
					<fieldset>
						<legend>
							<bean:message key="lbl.editDealMovement" />
						</legend>
						<logic:present name="editDealReassignmentData">
						<logic:iterate name="editDealReassignmentData" id="subEditDealReassignmentData">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<input type="hidden" name="businessDate" id="businessDate"
											value="<%=initiationDate%>" />
										<input type="hidden"  name="lbxBranchId" id="lbxBranchId" value="<%=branchId%>" />
										<input type="hidden"  name="dealMovementId" id="dealMovementId" value="${subEditDealReassignmentData.dealMovementId}" />
										<input type="hidden"  name="lbxDealNo" id="lbxDealNo" value="${subEditDealReassignmentData.lbxDealNo}" />
										<table width="100%" border="0" cellspacing="1" cellpadding="1">
	
											<tr>
												<td width="25%">
													<bean:message key="lbl.stage" />
													<font color="red">*</font>
												</td>
												<td width="25%">
													<html:text styleClass="text" property="stage"
														styleId="stage"  maxlength="3" value="${subEditDealReassignmentData.stage}"/>
													<input type="hidden" name="contextPath"
														value="<%=request.getContextPath()%>" id="contextPath" />
												</td>
												<td width="25%">
													<bean:message key="lbl.assignTo" />
												</td>
												<td width="25%">
													<html:text styleClass="text" property="assignedTo"
													styleId="assignedTo"  maxlength="50" value=""/>
												</td>
											</tr>
											<tr>
												<td width="25%">
													<bean:message key="lbl.status" />
													<font color="red">*</font>
												</td>
												<td width="25%">
													<logic:equal name="subEditDealReassignmentData" property="recStatus" value="A">
													<input type="checkbox" name="recStatus"
														id="recStatus"  value="${subEditDealReassignmentData.recStatus}" checked="checked"/>
													</logic:equal>
													<logic:notEqual name="subEditDealReassignmentData" property="recStatus" value="A">
													<input type="checkbox" name="recStatus"
														id="recStatus"  value="${subEditDealReassignmentData.recStatus}" />
													</logic:notEqual>
												</td>
												<td width="25%">
													&nbsp;
												</td>
												<td width="25%">
													&nbsp;
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
									<button type="button" name="save" id="save" 
										class="topformbutton2" title="Alt+S" accesskey="S"
										onclick="return saveDealReassignmentEdit();"><bean:message key="button.save" /></button>
									</td>
									<td></td>
								</tr>
							</table>
						</logic:iterate>
						</logic:present>
					</fieldset>
				</html:form>
		</div>
	</div>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<script>
	setFramevalues("dealReassignmentEditForm");
</script>
</body>
</html:html>
<logic:present name="message">
<script type="text/javascript">
if("<%=request.getAttribute("message")%>"=="S")
{
	alert("<bean:message key="msg.DataSaved" />");
	alert("<%=request.getAttribute("dealId") %>");
	alert("<%=request.getContextPath()%>/dealReassignmentMaker.do?method=searchDealForReassignmentMaker&dealId=<%=request.getAttribute("dealId") %>&flag=noSave");
	parent.location="<%=request.getContextPath()%>/dealReassignmentMaker.do?method=searchDealForReassignmentMaker&dealId=<%=request.getAttribute("dealId") %>&flag=noSave";
	window.close();
}
else if("<%=request.getAttribute("message")%>"=="E")
{
	alert("<bean:message key="msg.DataNotSaved" />");
	parent.location="<%=request.getContextPath()%>/dealReassignmentMaker.do?method=searchDealForReassignmentMaker";
	window.close();
}
</script>
</logic:present>