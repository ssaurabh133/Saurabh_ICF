<!--Author Name : Anil Yadav-->
<!--Date of Creation : 11 Nov 2011-->
<!--Purpose  : Payment Detail(Contact Recording) -->
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
		src="<%=request.getContextPath()%>/js/capsScript/contactRecordingScript.js"></script>

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

<body>
	<html:form action="/collAddressAction" styleId="AddressShowForm"
		method="post">
		<html:hidden property="path" styleId="path"
			value="<%=request.getContextPath()%>" />
		<html:errors />

		<html:hidden property="contextPath" styleId="contextPath"
			value="<%request.getContextPath();%>" />
		<br />
		<fieldset>
			<legend>
				<bean:message key="lbl.contactInfo" />
			</legend>
			<fieldset>
				<logic:present name="list">

					<display:table id="list" name="list" style="width: 100%"
						class="dataTable" pagesize="${requestScope.no}" cellspacing="1"
						partialList="true" size="${list[0].totalRecordSize}"
						requestURI="/contactRecordingDispatchAction.do?method=showAddressDetails">
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="locale.resolver"
							value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:setProperty name="locale.provider"
							value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
						<display:column property="fromTable" titleKey="lbl.addressflag"
							sortable="true" />
						<display:column property="addr_type" titleKey="lbl.addressType"
							sortable="true" />
						<display:column property="txtDistCode" titleKey="lbl.dist"
							sortable="true" />
						<display:column property="txtStateCode" titleKey="lbl.state"
							sortable="true" />
						<display:column property="txtCountryCode" titleKey="lbl.country"
							sortable="true" />
						<display:column property="primaryPhoneNo" titleKey="lbl.primary"
							sortable="true" />
						<display:column property="faxNo" titleKey="lbl.fax"
							sortable="true" />
						<display:column property="addr1" titleKey="lbl.addr"
							sortable="true" />
						<display:column property="pincode" titleKey="lbl.pincode"
							sortable="true" />

						<tr>
							<html:button styleClass="LovCloseButton"
								property="lovlbxLawFirmDesc" onclick="onButtonClick();"
								value=" " accesskey="X" title="Alt+X"></html:button>
						</tr>

					</display:table>




				</logic:present>

			</fieldset>
			<logic:notPresent name="list">

				<table width="100%" cellspacing="0" cellpadding="0" border="0">
					<tbody>
						<tr>
							<td class="gridtd">
								<table width="100%" cellspacing="1" cellpadding="1" border="0">
									<tbody>
										<tr align="center" class="white2">
											<td>
												<b><bean:message key="lbl.addressType" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.dist" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.state" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.country" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.primary" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.fax" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.pincode" />
												</b>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>

			</logic:notPresent>

			<logic:present name="list">
				<td>
					<!--  <input type="button" value="Close" id="close"
						class="topformbutton2" title="Alt+C" accesskey="C"
						onclick="window.close();" />-->
						<button type="button" class="topformbutton2"  id="close" onclick="window.close();"
 title="Alt+C" accesskey="C"   ><bean:message key="button.close" /></button>
				</td>
			</logic:present>


		</fieldset>


	</html:form>
	<logic:present name="sms">
		<script type="text/javascript">

    
	
	
	if('<%=request.getAttribute("sms").toString()%>'=='No')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		self.close();
	}
	
	
	
</script>
	</logic:present>
	<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
</body>
</html:html>