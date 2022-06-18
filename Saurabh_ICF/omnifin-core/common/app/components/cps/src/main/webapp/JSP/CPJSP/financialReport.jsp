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
<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ocrScript/fundFlowAnalysis.js"></script>
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
<body onload="enableAnchor();checkChanges('reportForm');">
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
										Report Download
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

			</fieldset>
<logic:notPresent name="viewInfo">
<html:form action="/generateCamReport" styleId="reportForm"  >
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>
<input type="hidden" id="status" name="status" value="${status}"/>
<fieldset><legend><bean:message key="lbl.ReportParameterForm"/></legend>
<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
	<logic:equal name="functionId" value="3000261">
		<td width="35%"><bean:message key="lbl.ReportName"/><span><font color="red">*</font></span></td>
		<td>
			<html:select property="reportName" styleId="reportName" onchange="disabledReportType();"     styleClass="text">
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
		</logic:equal>
		<logic:notEqual name="functionId" value="3000261">
		<td width="13%"><bean:message key="lbl.ReportName"/><span><font color="red">*</font></span></td>
		<td>
			<html:select property="reportName" styleId="reportName" onchange="disabledReportType();"     styleClass="text">
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
		
		<td width="13%"><bean:message key="lbl.reporttype" /></td><!-- add by Ajay -->
							<td><html:select property="reporttype" styleId="reporttype"	styleClass="text" disabled = "true">
									 <html:option value="">--Select--</html:option> 
									<html:option value="P">PDF</html:option>
									 <html:option value="E">Excel</html:option> 
								</html:select></td>
		</logic:notEqual>
								
	</tr>	
	</table>
	<tr >
		
		<td>
			 <button type="button" name="button" id="report" title="Alt+F" accesskey="R" class="topformbutton3" onclick="generateReportProcess('reportForm','');"><bean:message key="lbl.generateReport" /></button>
			 <button type="button" name="button" id="refresh" title="Alt+F" accesskey="R" class="topformbutton2" onclick="reportRefresh('reportForm');"><bean:message key="lbl.refresh" /></button>
			 
			<logic:notEqual name="functionId" value="3000261">
			 <button type="button" name="SchemeReportButton" id="SchemeReportButton" title="Alt+V" accesskey="V" class="topformbutton3" onclick="generateSchemeReport('reportForm');">SchemeReport</button>
		 		</logic:notEqual>
		</td>
								
	</tr>	
	
 </fieldset> 
 
 <fieldset><legend><bean:message key="lbl.reportInformation"/></legend>
 
          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    
    <tr class="white2">
	<logic:notPresent name="linkList">
	
        <td ><b><bean:message key="lbl.generateDateTime"/></b></td>
         <td><b><bean:message key="lbl.ReportName"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
		 
    <td><b><bean:message key="lbl.generatedBy"/> </b></td>
   
</logic:notPresent>
	</tr>
 </table>    </td>
</tr>
</table>
 <logic:present name="linkList">
 <logic:notEmpty name="linkList">
  <display:table  id="list"  name="linkList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${linkList[0].totalRecordSize}" requestURI="/generateCamReport.do?method=refreshForReportLink" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    
    <display:column property="makerDate" titleKey="lbl.generateDateTime"  sortable="false"  />
    <display:column property="reportType" titleKey="lbl.ReportName"  sortable="false"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="false"  />
	
	<display:column property="makerId" titleKey="lbl.generatedBy"  sortable="false"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="linkList">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
	
	
        <td ><b><bean:message key="lbl.generateDateTime"/></b></td>
        <td ><b><bean:message key="lbl.ReportName"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
	
       
         <td><b><bean:message key="lbl.generatedBy"/> </b></td>
	</tr>
	<tr class="white2"><td colspan="7"><bean:message key="lbl.noDataFound"/></td></tr>
	
 </table>    </td>
</tr>
</table>

</logic:empty>
</logic:present>
 </fieldset>

<%-- 
<logic:present name="msg">

 <script type="text/javascript">
	if('<%=request.getAttribute("msg").toString()%>'=='EFA')
	{
		alert('<bean:message key="msg.forwardFounFlow" />');
		parent.location.href="editFinancialAnalysis.do?method=openEditFinancialAnalysis&dealId="+'${caseId}';
	}
	else if('<%=request.getAttribute("msg").toString()%>'=='EFFA')
	{
		alert('<bean:message key="msg.forwardFinancial" />');
		parent.location.href="editFlowAnalysis.do?dealId="+'${caseId}';
	}
	
</script>

</logic:present> --%>


</html:form>


<script>
	setFramevalues("reportForm");
</script>
</logic:notPresent>

<!-- view Mode -->
<logic:present name="viewInfo">
<html:form action="/generateCamReport" styleId="reportForm"  >
<input type="hidden" id="contextPath" name="contextPath" value="<%=request.getContextPath()%>"/>


<fieldset><legend><bean:message key="lbl.ReportParameterForm"/></legend>
<table width="60%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="13%"><bean:message key="lbl.ReportType"/><span><font color="red">*</font></span></td>
		<td width="13%">
			<%-- <html:select property="reportName" styleId="reportName" styleClass="text" > --%>
			<html:select property="reportName" styleId="reportName" onchange="disabledReportType();"     styleClass="text">
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
		<!--  ajay-->
		   <logic:notEqual name="viewfundflow" value="viewfundflow">
			<td width="13%"><bean:message key="lbl.Report_Format" /></td>
			<td width="13%"><html:select property="reporttype" styleId="reporttype"	styleClass="text" disabled="true">
			<html:option value="">--Select--</html:option> 
			<html:option value="P">PDF</html:option>
			<html:option value="E">Excel</html:option> 
			</html:select></td>
			</logic:notEqual>
		<!--  ajay-->
								
	</tr>	
	 </table>
	<tr>
		
		<td>
		  <button type="button" name="button" id="report"
															title="Alt+F" accesskey="R" class="topformbutton3"
															onclick="generateReportProcess('reportForm','viewMode');">
															<bean:message key="lbl.generateReport" />
														</button> 
			<button type="button" name="button" id="refresh" 
															title="Alt+F" accesskey="R" class="topformbutton3"
															onclick="reportRefresh('reportForm');">
															<bean:message key="lbl.refresh" />
														</button>
			<button type="button" name="SchemeReportButton" id="SchemeReportButton" title="Alt+V"  accesskey="V" class="topformbutton3" onclick="generateSchemeReport('reportForm');">SchemeReport</button>
		</td>
								
	</tr>	
 </fieldset> 
 
 <fieldset><legend><bean:message key="lbl.reportInformation"/></legend>
 
          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    
    <tr class="white2">
	<logic:notPresent name="linkList">
	
        <td ><b><bean:message key="lbl.generateDateTime"/></b></td>
         <td><b><bean:message key="lbl.ReportName"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
		 
    <td><b><bean:message key="lbl.generatedBy"/> </b></td>
   
</logic:notPresent>
	</tr>
 </table>    </td>
</tr>
</table>
 <logic:present name="linkList">
 <logic:notEmpty name="linkList">
  <display:table  id="list"  name="linkList" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${linkList[0].totalRecordSize}" requestURI="/generateCamReport.do?method=refreshForReportLink" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    
    <display:column property="makerDate" titleKey="lbl.generateDateTime"  sortable="false"  />
    <display:column property="reportType" titleKey="lbl.ReportName"  sortable="false"  />
	<display:column property="customerName" titleKey="lbl.customerName"  sortable="false"  />
	
	<display:column property="makerId" titleKey="lbl.generatedBy"  sortable="false"  />
	
</display:table>
</logic:notEmpty>
<logic:empty name="linkList">
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
   
    <tr class="white2">
	
	
        <td ><b><bean:message key="lbl.generateDateTime"/></b></td>
        <td ><b><bean:message key="lbl.ReportName"/></b></td>
         <td><b><bean:message key="lbl.customerName"/></b></td>
	
       
         <td><b><bean:message key="lbl.generatedBy"/> </b></td>
	</tr>
	<tr class="white2"><td colspan="7"><bean:message key="lbl.noDataFound"/></td></tr>
	
 </table>    </td>
</tr>
</table>

</logic:empty>
  </logic:present>
 </fieldset>

</html:form>
<script>
	setFramevalues("reportForm");
</script>
</logic:present>

</body>
</html>