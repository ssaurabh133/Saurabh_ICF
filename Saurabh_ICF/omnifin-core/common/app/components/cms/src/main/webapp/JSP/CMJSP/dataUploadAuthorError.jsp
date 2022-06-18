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
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
     
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       Date currentDate = new Date();

%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

<head>

<title><bean:message key="a3s.noida" /></title>

 <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	   
		 
		<!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/cibilReport.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/dataUpload.js"></script>
</head>

<body onload="enableAnchor();" oncontextmenu="return false" >
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>


<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
    		
    		
  		
<html:form action="/dataUploadAuthor" styleId="dataUploadErrorForm" method="post">

 <html:hidden property="path" styleId="path" value="<%=request.getContextPath()%>"/>

<logic:present name="dataList" >


	<fieldset>
		 <legend><bean:message key="lbl.dataUploadAuthorError" /></legend> 
<table width="100%" border="0" cellpadding="0" cellspacing="1" >
<tr>
 <td class="gridtd">
 <table width="100%" border="0" cellpadding="4" cellspacing="1">
 <tr class="white2">
 			
 			<td><strong><bean:message key="lbl.Host_LoanNo"/></strong></td>
 			<td><strong><bean:message key="lbl.Host_DealerCode"/></strong></td>
 			<td><strong><bean:message key="lbl.Host_DealerBankA/C"/></strong></td>
 			<td><strong><bean:message key="lbl.TXN_Date"/></strong></td>
 			<td><strong><bean:message key="lbl.TXN_Amount"/></strong></td>
			<td><strong><bean:message key="lbl.TXN_Flag"/></strong></td>
       		<td><strong><bean:message key="lbl.Xls_Row_Number"/></strong></td>
       		<td><strong><bean:message key="lbl.Error_Description"/></strong></td>
      	</tr>
  
<logic:present name="errorList">
<logic:iterate name="errorList" id="sublist" >

  <tr  class="white1">
  			
			<td>${sublist.host_loan_no}</td>
			<td>${sublist.host_dealer_code}</td>
			<td>${sublist.host_dealer_bank_acount}</td>
			<td>${sublist.txn_date}</td>
			<td>${sublist.txn_amount}</td>
			<td>${sublist.txn_flag}</td>
			<td>${sublist.xlsrownumber}</td>
			<td>${sublist.error_description}</td>
			
			
	 </tr>

  </logic:iterate>

  </logic:present>

</table>
</td></tr></table>

</fieldset>    
		
</logic:present>


</html:form>	

  </body>
		</html:html>