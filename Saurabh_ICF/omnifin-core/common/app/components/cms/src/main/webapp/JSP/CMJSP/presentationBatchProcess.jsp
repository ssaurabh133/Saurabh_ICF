<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="com.logger.LoggerMsg"%>
<%@page import="com.connect.CommonFunction"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript"  src="<%=path%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript"  src="<%=path%>/js/commonScript/lovCommonScript.js"></script>
		<script type="text/javascript"  src="<%=path%>/js/cmScript/generateBatch.js"></script>
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
<!--<script type="text/javascript">-->
<!--$(function() -->
<!--{-->
<!--	$("#presentationDate").datepicker-->
<!--	({-->
<!--		changeMonth: true,-->
<!--		changeYear: true,-->
<!--		yearRange: '1900:+10',-->
<!--		showOn: 'both',-->
<!--		<logic:present name="image">buttonImage: '<%=request.getContextPath() %>/${image }/calendar.gif',</logic:present>-->
<!--    	<logic:notPresent name="image">buttonImage: '<%=request.getContextPath() %>/images/theme1/calendar.gif',</logic:notPresent>-->
<!--		buttonImageOnly: true,-->
<!--		dateFormat:"<bean:message key="lbl.dateFormat"/>",-->
<!--		defaultDate:'${userobject.businessdate }'-->
<!--	});-->
<!--});-->
<!---->
<!--</script> -->
	</head>
	
<body onload="enableAnchor();document.getElementById('DocUpload').docFile.focus();">
<html:form action="/presentationBatchProcessDispatchAction" method="post" styleId="presentationBatch">		
<fieldset><legend><bean:message key="lbl.presentationBatch"/></legend>  
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<input type="hidden" name="finalDate" id="finalDate" value="" />
<input type="hidden" id="successBatchNo" value="${successBatchNo}"/>
<input type="hidden" id="successBankId" value="${successBankId}"/>
<input type="hidden" id="successBranchID" value="${successBranchID}"/>
<input type="hidden" id="successAccount" value="${successAccount}"/>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
      <td>
      <table width="100%" border="0" cellspacing="1" cellpadding="4">
      <tr>
      	  <td><bean:message key="lbl.batch"/><font color="red">*</font></td>
		   <td>
				<html:text property="batchNo" styleClass="text" styleId="batchNo" maxlength="100" readonly="true" value="${viewBatchList[0].batchNo}"/>
				<html:hidden property="lbxBatchNo" styleId="lbxBatchNo" value="${viewBatchList[0].batchNo}" />
				<html:hidden property="status" styleId="status" value="" />
				<html:button property="batchButton" styleId="batchButton" tabindex="1" onclick="openLOVCommon(334,'presentationBatch','batchNo','finalDate','','finalDate','','bankDetail','status')" value=" " styleClass="lovbutton"  ></html:button>
		   </td>
      	  <td><bean:message key="lbl.presentationDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		  <td><html:text property="presentationDate" maxlength="10" styleId="presentationDate" readonly="true" styleClass="text" value="" onchange="checkDate('presentationDate');changeDateFormate()"/></td>
	</tr>
	<tr>
		  <td><bean:message key="lbl.depositBankId"/><font color="red">*</font></td>
		  <td>	
	 			<html:text styleClass="text" property="bank" styleId="bank" maxlength="100" value="${data.bank}" readonly="true" tabindex="-1"/>
   			<html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(79,'presentationBatch','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>
    		    <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${data.lbxBankID}"/>
		  </td>        
      	  <td><bean:message key="lbl.depositBranchId"/><font color="red">*</font></td>
		  <td>
					<html:text styleClass="text" property="branch" styleId="branch" maxlength="100" value="${data.branch}" readonly="true" tabindex="-1"/>
   					<html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${data.lbxBranchID}"/>
					<html:button property="branchButton" styleId="branchButton" onclick="openLOVCommon(80,'presentationBatch','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode','bankAccount');closeLovCallonLov('bank');" value=" " styleClass="lovbutton"></html:button> 
  					<html:hidden styleClass="text" property="micr" styleId="micr" value="${data.micr}" />
  					<html:hidden styleClass="text" property="ifscCode" styleId="ifscCode"  value="${data.ifscCode}" />
  		  </td>	
     </tr>
     <tr>		
		  <td><bean:message key="lbl.depositBnkAcc"/><font color="red">*</font></td>
		  <td><html:text styleClass="text" property="bankAccount" styleId="bankAccount"  maxlength="50" value="${data.bankAccount}" readonly="true"  /></td>
      	  	<td>
			 <bean:message key="lbl.totalNoInstrument"/>
			</td>
			<td>
			    <html:text property="totalNoInstrument" styleClass="text" styleId="totalNoInstrument" readonly="true" />
			</td>
	  </tr>
	   <tr>		
		 <td>
		  <bean:message key="lbl.totalInstrumentAmount"/>
		 </td>
		<td>
		    <html:text property="totalInstrumentAmount" styleClass="text" styleId="totalInstrumentAmount" readonly="true" />
		</td>
	  </tr>
      <tr>		
	  		<td colspan="4">
	  			<button type="button" name=" mybutton"  title="Alt+B" accesskey="B" class="topformbutton3" onclick="generateProcess();"><bean:message key="button.presentBatch" /></button>
	  		</td>
	  </tr>		  
      </table>
      </td>
</tr>
</table> 
</fieldset>  
</html:form>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>

<logic:present name="success">
	<script type="text/javascript">	
	
		alert("Presentation Batch Process is completed Successfully.");
		
		var batchNo=document.getElementById('successBatchNo').value;	
		var bankID=document.getElementById('successBankId').value;	
		var branchID=document.getElementById('successBranchID').value;	
		var account=document.getElementById('successAccount').value;
		var contextPath =document.getElementById('contextPath').value;
		document.getElementById("presentationBatch").action=contextPath+"/presentationBatchProcessDispatchAction.do?method=generatePaySlip&batchNo="+batchNo+"&bankID="+bankID+"&branchID="+branchID+"&account="+account+"&reportType=P";
		document.getElementById("presentationBatch").submit();
	</script>
</logic:present>
<logic:present name="error">
	<script type="text/javascript">	
		alert("${error}");
	</script>
</logic:present>
</body>
</html:html>

