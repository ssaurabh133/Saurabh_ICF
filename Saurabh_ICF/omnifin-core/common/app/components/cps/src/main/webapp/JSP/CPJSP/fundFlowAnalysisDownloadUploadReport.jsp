<%@page import="java.util.ResourceBundle"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");


int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no);


%>	
<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/fundFlowAnalysis.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/commonScript/checkMod.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="report" content="" />
 <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
</head>
<body onload="enableAnchor();checkChanges('downloadUploadFundFlowReportForm');">

<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	

	<div align="center" class="opacity" style="display: none;"
		id="processingImage">
	</div>
	<div id="centercolumn">
		<div id="revisedcontainer">

			<fieldset>

				<table cellSpacing=0 cellPadding=0 width="100%" border=0>
					<tr>
						<td class="gridtd">
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr class="white2">
									<td>
										<b><bean:message key="lbl.dealNo" />
										</b>
									</td>
									<td>
										${dealHeader[0].dealNo}
									</td>
									<td>
										<b><bean:message key="lbl.date" />
										</b>
									</td>
									<td>
										${dealHeader[0].dealDate}
									</td>
									<td>
										<b><bean:message key="lbl.customerName" />
										</b>
									</td>
									<td colspan="3">
										${dealHeader[0].dealCustomerName}
									</td>
								</tr>
								<tr class="white2">
									<td>
										<b><bean:message key="lbl.productType" />
										</b>
									</td>
									<td>
										${dealHeader[0].dealProductCat}
									</td>
									<td>
										<b><bean:message key="lbl.product" />
										</b>
									</td>
									<td>
										${dealHeader[0].dealProduct}
									</td>
									<td>
										<b><bean:message key="lbl.scheme" />
										</b>
									</td>
									<td>
										${dealHeader[0].dealScheme}
									</td>
									<td>
										<b><bean:message key="lbl.currentStage" />
										</b>
									</td>
									<td>
										Download/Upload Report
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

			</fieldset>

<logic:notPresent name="viewInfo">
<html:form action="/uploadFundFlowAnalysisReport" styleId="downloadUploadFundFlowReportForm"   enctype="multipart/form-data">
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<input type="hidden" id="caseId" name="caseId" value="${fundFlowDealId}"/>
<fieldset><legend><bean:message key="lbl.ReportDownloadUpload"/></legend>
<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<td width="40%"><bean:message key="lbl.uploadFile"/><span><font color="red">*</font></span></td>
		<td>
		<logic:notPresent name="fundFlowAuthor">
			<html:file size="100" property="docFile" styleId="docFile" styleClass="text" tabindex="1"  />
		</logic:notPresent>
		<logic:present name="fundFlowAuthor">
			<html:file size="100" property="docFile" styleId="docFile" styleClass="text" tabindex="1"  disabled="true"/>
		</logic:present>	
		</td>
								
	</tr>	
	<tr>
		
		<td>
			 <button type="button" name="button" id="fundFlowReport"
															title="Alt+F" accesskey="R" class="topformbutton3"
															onclick="generateFundFlowReport();">
															<bean:message key="lbl.generateReport" />
														</button>
			<logic:notPresent name="fundFlowAuthor">											
			<button type="button" name="button" id="upload"
															title="Alt+F" accesskey="R" class="topformbutton2"
															onclick="uploadFundFlowFile();">
															<bean:message key="button.upload" />
														</button>
			</logic:notPresent>
			<logic:present name="fundFlowAuthor">
			<button type="button" name="button" id="upload"
															title="Alt+F" accesskey="R" class="topformbutton2"
															onclick="uploadFundFlowFile();"
															disabled="true">
															<bean:message key="button.upload" />
														</button>
			</logic:present>
			<logic:notPresent name="fundFlowAuthor">
			<button type="button" name="button" id="error"
															title="Alt+F" accesskey="R" class="topformbutton2"
															onclick="generateErrorFile();">
															<bean:message key="lbl.errorSummary" />
														</button>
			</logic:notPresent>
			<logic:present name="fundFlowAuthor">
			<button type="button" name="button" id="error"
															title="Alt+F" accesskey="R" class="topformbutton2"
															onclick="generateErrorFile();"
															disabled="true">
															<bean:message key="lbl.errorSummary" />
														</button>
			</logic:present>
		</td>
								
	</tr>	
	</table>
 </fieldset> 
 

</html:form>
<script>
	setFramevalues("downloadUploadFundFlowReportForm");
</script>
</logic:notPresent>

<!-- view Mode -->
<logic:present name="viewInfo1">
<html:form action="/generateCamReport" styleId="downloadUploadFundFlowReportForm"  >
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<fieldset><legend><bean:message key="lbl.ReportParameterForm"/></legend>
<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<td><bean:message key="lbl.ReportType"/><span><font color="red">*</font></span></td>
		<td>
			<html:select property="reportName" styleId="reportName" styleClass="text" disabled="true">
			<option value="">--	<bean:message key="lbl.select" />--</option>
			<logic:present name="reportList">
			<logic:notEmpty name="reportList">
			<html:optionsCollection name="reportList" label="description" value="value"  />
			</logic:notEmpty>
			</logic:present>			
			<!-- <option value="ratio">Ratio Analysis</option>	
			<option value="obligation">Obligation</option>	
			<option value="bank">Bank Analysis </option> -->	
       	  	</html:select>
		</td>
								
	</tr>	
	
	</table>
 </fieldset> 
 

</html:form>
<script>
	setFramevalues("downloadUploadFundFlowReportForm");
</script>
</logic:present>

<logic:present name="msg">

 <script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='S')
	{
		alert('<bean:message key="lbl.datasms" />');
	}
		
	else if('<%=request.getAttribute("msg").toString()%>'=='E')
	{
		alert('Data Not Uploaded."');
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='R')
	{
		alert('<bean:message key="msg.validatiionRule" />');
	}

	else if('<%=request.getAttribute("msg").toString()%>'=='fileTypeVal')
	{
		alert('<bean:message key="msg.fileTypeVal" />');
	}

	else if('<%=request.getAttribute("msg").toString()%>'=='DS')
	{
		alert('<bean:message key="msg.docForward" />');
	}
	
	else if('<%=request.getAttribute("msg").toString()%>'=='DE')
	{
		alert('<bean:message key="msg.DocNotForward" />');
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='error')
		{
		alert('${sms}')
		}
	
	
	
</script>

</logic:present>
</body>
</html>