<!--
 	Author Name      :- Ravindra Kumar
 	Date of Creation :- 10-jan-2012
 	Purpose          :- To provide user interface to Individual Financial Ratio Analysis
 -->
 <%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
        <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
			
	</head> 
	<body oncontextmenu="return false" onunload="closeAllLovCallUnloadBody();" onclick="parent_disable();" onload="enableAnchor();checkChanges('individualFinancialRatioAnalysisForm');">
	<div id="centercolumn">
	<div id="revisedcontainer">
	
			<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Individual Financial Analysis</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
	
	<logic:notPresent name="underWriterViewData">
	<html:form action="/individualRatioAnalysisBehindAction" styleId="individualFinancialRatioAnalysisForm" method="post">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	   <fieldset>	  
	<legend><bean:message key="lbl.ratioDetail"/></legend>
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<tr>
		<td width="17%"><bean:message key="lbl.loanAmount"/></td>
		<td width="28%">
		<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${financialDetails[0].loanAmount}" maxlength="50" tabindex="-1" readonly="true"/>
	    </td>
	    <td colspan="2"><bean:message key="lbl.tenure"/></td>
		<td colspan="4" nowrap="nowrap">
		<html:text property="tenure" styleId="tenure" styleClass="text" maxlength="50" value="${financialDetails[0].tenure}" tabindex="-1" readonly="true"/>
	   </td>
		</tr>
		
		<tr>
		<td width="17%"><bean:message key="lbl.emiAmount"/></td>
		<td width="28%">
		
		<html:text property="applicationDate" styleId="applicationDate" styleClass="text" maxlength="50" value="${ratioAnalysisList[0].emi}" readonly="true" tabindex="-1"/>
  
      </td>
		<td colspan="2"></td>
		<td colspan="4" nowrap="nowrap"></td>
		</tr>
		
		<tr>
		<td width="17%"><bean:message key="lbl.foir"/></td>
		<td width="28%">
		
		<html:text property="scheme" styleId="scheme" styleClass="text" maxlength="50" value="${ratioAnalysisList[0].yearValue}" readonly="true" tabindex="-1"/>
  
     </td>
	<td colspan="2"><bean:message key="lbl.irr"/></td>
	<td colspan="4" nowrap="nowrap">
	<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${ratioAnalysisList[1].yearValue}" maxlength="50" tabindex="-1" readonly="true"/>
    </td>
		</tr>
	
	  	  </table>
	  
	  
			</td></tr>
		</table>
		
	</fieldset>	
	<br/>

</html:form>
</logic:notPresent>
<%--   -------------- -------------------- ---------View  mode--- -------------------- -------------------- --------------------- --%>
<logic:present name="underWriterViewData">
		<html:form action="/individualRatioAnalysisBehindAction" styleId="individualFinancialRatioAnalysisForm" method="post">
	  <input type="hidden" id="contextPath" value="<%=path %>" />
	   <fieldset>	  
	<legend><bean:message key="lbl.ratioDetail"/></legend>
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">

		<tr>
		<td width="17%"><bean:message key="lbl.loanAmount"/></td>
		<td width="28%">
		<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${financialDetails[0].loanAmount}" maxlength="50" tabindex="-1" readonly="true"/>
	    </td>
	    <td colspan="2"><bean:message key="lbl.tenure"/></td>
		<td colspan="4" nowrap="nowrap">
		<html:text property="tenure" styleId="tenure" styleClass="text" maxlength="50" value="${financialDetails[0].tenure}" tabindex="-1" readonly="true"/>
	   </td>
		</tr>
		
		<tr>
		<td width="17%"><bean:message key="lbl.emiAmount"/></td>
		<td width="28%">
		
		<html:text property="applicationDate" styleId="applicationDate" styleClass="text" maxlength="50" value="${ratioAnalysisList[0].emi}" readonly="true" tabindex="-1"/>
  
      </td>
		<td colspan="2"></td>
		<td colspan="4" nowrap="nowrap"></td>
		</tr>
		
		<tr>
		<td width="17%"><bean:message key="lbl.foir"/></td>
		<td width="28%">
		
		<html:text property="scheme" styleId="scheme" styleClass="text" maxlength="50" value="${ratioAnalysisList[0].yearValue}" readonly="true" tabindex="-1"/>
  
     </td>
	<td colspan="2"><bean:message key="lbl.irr"/></td>
	<td colspan="4" nowrap="nowrap">
	<html:text  styleClass="text" property="loanAmount" styleId="loanAmount" value="${ratioAnalysisList[1].yearValue}" maxlength="50" tabindex="-1" readonly="true"/>
    </td>
		</tr>
	
	  	  </table>
	  
	  
			</td></tr>
		</table>
		
	</fieldset>	
	<br/>

</html:form>
</logic:present>
</div>



</div>
<script>
	setFramevalues("individualFinancialRatioAnalysisForm");
</script>

</body>
</html:html>