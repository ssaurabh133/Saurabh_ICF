<!--Author Name : Ritesh Srivastava-->
<!--Date of Creation : 09 Aug 2012-->
<!--Purpose  : Schedule Master Search->
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
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/demos/demos.css"
		rel="stylesheet" />
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/payoutScript/taxMaster.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery.formatCurrency-1.3.0.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery.formatCurrency.all.js"></script>
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/payoutScript/scheduleMaster.js"></script>
	<%
		ResourceBundle resource = ResourceBundle
					.getBundle("com.yourcompany.struts.ApplicationResources");

			int no = Integer.parseInt(resource
					.getString("msg.pageSizeForMaster"));
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

<body
	onload="enableAnchor();document.getElementById('scheduleMasterSearch').searchActivityId.focus();">
	<html:form action="/scheduleMasterBehind"
		styleId="scheduleMasterSearch" method="post">
		<html:hidden property="path" styleId="path"
			value="<%=request.getContextPath()%>" />
		<input type="hidden" name="contextPath"
			value="<%=request.getContextPath()%>" id="contextPath" />
		<html:errors />

		<fieldset>
			<legend>
				<bean:message key="lbl.payScheduleMaster" />
			</legend>
			<table width="100%">

				<tr>
					<td width="13%">
						<bean:message key="lbl.payScheduleActivityCode" />
					</td>
						
					<td>
					<html:text property="searchActivityId" styleClass="text"
							styleId="searchActivityId" maxlength="50" />
					</td>
					</tr>
					<tr>
					<td width="13%">
						<bean:message key="lbl.payScheduleStartDay" />
					</td>
					<td width="13%">
						<html:text property="searchStartDate" styleClass="text"
							styleId="searchStartDate" maxlength="50"
							onkeyup="checkVariable(this.value,id)"
							onkeypress="return numbersonly(event,id,2)"
							onfocus="keyUpNumber(this.value, event, 4,id);" />
					</td>
						<td width="13%">
						<bean:message key="lbl.payScheduleEndDay" />
					</td>
					<td width="13%">
						<html:text property="searchEndDate" styleClass="text" styleId="searchEndDate"
							onkeyup="checkVariable(this.value,id)"
							onkeypress="return numbersonly(event,id,2)"
							onfocus="keyUpNumber(this.value, event, 4,id);" />
					</td>
				</tr>
				<tr>
					<td>
						<button type="button" class="topformbutton2" id="save"
							onclick="return fnSearchCodeScheduleMaster('<bean:message key="lbl.selectAtLeast" />');"
							title="Alt+S" accesskey="S">
							<bean:message key="button.search" />
						</button>

						<button type="button" class="topformbutton2" name="add" id="add"
							onclick="return addScheduleMaster();"
									title="Alt+A" accesskey="A">
							<bean:message key="button.add" />
						</button>
					</td>

				</tr>
			</table>
		</fieldset>

		<br />
		<fieldset>
			<legend>
				<bean:message key="lbl.activityDetail" />
			</legend>

			<logic:present name="list">
				<display:table id="list" name="list" style="width: 100%"
					class="dataTable" pagesize="${requestScope.no}" cellspacing="1"
					partialList="true" size="${list[0].totalRecordSize}"
					requestURI="/scheduleMasterBehind.do">
					<display:setProperty name="paging.banner.placement" value="bottom" />
					<display:setProperty name="locale.resolver"
						value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:setProperty name="locale.provider"
						value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
					<display:column property="activityId"
						titleKey="lbl.payScheduleActivityCode" sortable="true" />
					<display:column property="startDate"
						titleKey="lbl.payScheduleStartDay" sortable="true" />
						<display:column property="startMonth"
						titleKey="lbl.payScheduleStart_month" sortable="true" />
					<display:column property="endDate" titleKey="lbl.payScheduleEndDay"
						sortable="true" />
						<display:column property="endMonth" titleKey="lbl.payScheduleEnd_month"
						sortable="true" />
					<display:column property="recStatus" style="text-align: center"
						titleKey="lbl.payScheduleRecStatus" sortable="true" />

				</display:table>
			</logic:present>

			<logic:notPresent name="list">

				<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr>
									<td width="220" class="white2" style="width: 220px;">
										<strong><bean:message
												key="lbl.payScheduleActivityCode" /> </strong>
									</td>

									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payScheduleStartDay" /> <br>
										</b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payScheduleStart_month" /> <br>
										</b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payScheduleEndDay" /> <br>
										</b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payScheduleEnd_month" /> <br>
										</b>
									</td>
									<td width="220" class="white2" style="width: 250px;">
										<b><bean:message key="lbl.payScheduleRecStatus" /> <br>
										</b>
									</td>
									<tr class="white2">
										<td colspan="7">
											<bean:message key="lbl.noDataFound" />
										</td>
									</tr>
								</tr>
							</table>
						</td>
					</tr>
				</table>

			</logic:notPresent>


		</fieldset>


	</html:form>
	<logic:present name="sms">
		<script type="text/javascript">
	
</script>
	</logic:present>
	<div align="center" class="opacity" style="display: none;"
		id="processingImage"></div>
</body>
</html:html>