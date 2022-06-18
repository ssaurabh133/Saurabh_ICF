<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	Date currentDate = new Date();
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>

	<title><bean:message key="a3s.noida" /></title>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/popup.js"></script>


	<!-- css and jquery for Datepicker -->

	<link type="text/css"
		href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css"
		rel="stylesheet" />
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>

	<!-- css and jquery for Datepicker -->
	<script type="text/javascript"
		src=" <%=request.getContextPath()%>/js/formatnumber.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cpScript/cibilReport.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/cmScript/dataUpload.js"></script>
</head>

<body onload="enableAnchor();" oncontextmenu="return false">
	<div align="center" class="opacity" style="display: none;"
		id="processingImage">
	</div>


	<input type="hidden" name="formatD" id="formatD"
		value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
		<input type="hidden" id="CalImage"
			value='<%=request.getContextPath()%>/${image}/calendar.gif' />
	</logic:present>
	<logic:notPresent name="image">
		<input type="hidden" id="CalImage"
			value='<%=request.getContextPath()%>/images/theme1/calendar.gif' />
	</logic:notPresent>
	<input type="hidden" id="businessdate"
		value='${userobject.businessdate}' />



	<html:form action="/dataUploadAuthor" styleId="dataUploadForm"
		method="post">

		<html:hidden property="path" styleId="path"
			value="<%=request.getContextPath()%>" />

		<logic:present name="dataList">


			<fieldset>
				<legend>
					<bean:message key="lbl.dataUploadAuthor" />
				</legend>
				<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
								<tr class="white2">

									<td>
										<strong><bean:message key="lbl.select" /> </strong>
									</td>
									<td>
										<strong><bean:message key="lbl.fileName" /> </strong>
									</td>
									<td>
										<strong><bean:message key="lbl.fileDescription" /> </strong>
									</td>
									<td>
										<strong><bean:message key="lbl.rowCount" /> </strong>
									</td>
									<td>
										<strong><bean:message key="lbl.taxAmountSum" /> </strong>
									</td>
									<td>
										<strong><bean:message key="lbl.fileStatus" /> </strong>
									</td>
									<td>
										<strong><bean:message key="lbl.authorRemarks" /> </strong>
									</td>
								</tr>

								<logic:present name="list">
									<logic:iterate name="list" id="sublist">

										<tr class="white1">
											<td>
												<input type="checkbox" name="chk" id="chk" value="" />
											</td>

											<td>
												<html:link action='/download?fileName=${sublist.fileName}'>${sublist.fileName}</html:link>
											</td>
											<input type="hidden" name="fileChkName"
												value="${sublist.fileName}" />
											<td>
												${sublist.fileDescription}
											</td>
											<td>
												${sublist.rowCount}
											</td>
											<td>
												${sublist.totalTaxAmount}
											</td>
											<td width="20">
												<html:select property="selectDropDownArr" styleClass="text"
													styleId="selectDropDownArr">
													<html:option value="A">Approved</html:option>
													<html:option value="S">Send Back</html:option>
													<html:option value="R">Reject</html:option>
												</html:select>
											</td>
											<td width="20">
												<textarea name="authorRemarks" id="authorRemarks" value=""></textarea>
											</td>
										</tr>

									</logic:iterate>

								</logic:present>

							</table>
						</td>
					</tr>
				</table>

			</fieldset>
			<logic:present name="list">
				<tr>
					<td>
						<button type="button" name="button" id="button"
							class="topformbutton3" title="Alt+P" accesskey="P"
							onclick="processFile();">
							<bean:message key="button.Process" />
						</button>
					</td>
					
				</tr>
			</logic:present>
			<logic:present name="errorLog">
						<td>
							<button type="button" name="buttonError" id="buttonError"
								class="topformbutton3" title="Alt+P" accesskey="P"
								onclick="return showErrorInProcess();">
								<bean:message key="button.errorButton" />
							</button>
						</td>
					</logic:present>
		</logic:present>

		<logic:present name="message">
			<script type="text/javascript">
	if('<%=request.getAttribute("message").toString()%>'=='success')
	{
		alert('<bean:message key="msg.success" />');
		 
	}else if('<%=request.getAttribute("message").toString()%>'=='reject'){
		alert('<bean:message key="msg.reject" />');
	}else if('<%=request.getAttribute("message").toString()%>'=='notreject'){
		alert('<bean:message key="msg.notReject" />');
	}
	else if('<%=request.getAttribute("message").toString()%>'=='sendBack'){
		alert('<bean:message key="msg.sendBack" />');
	}
	else{
		alert('<bean:message key="msg.notsuccess" />');
	}
	
</script>
		</logic:present>

		<logic:present name="checksum">
			<script type="text/javascript">
	
	if('<%=request.getAttribute("checksum").toString()%>
	' == 'change') {
		alert('<bean:message key="msg.checksumChange" />');
	}
</script>
		</logic:present>


	</html:form>

</body>
</html:html>