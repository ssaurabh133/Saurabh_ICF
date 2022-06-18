<!--Author Name : Neeraj Kumar Tripathi-->
<!--Date of Creation : 31-July-2012-->
<!--Documentation : -->
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html:html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		<%String path=request.getContextPath();%>
		<script type="text/javascript"  src="<%=path%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript"  src="<%=path%>/js/cmScript/generateBatch.js"></script>
</head>

<body onload="enableAnchor();document.getElementById('DocUpload').docFile.focus();">
<html:form action="/generateBatchDispatchAction" method="post" styleId="paySlip">		
<fieldset><legend><bean:message key="lbl.depositBank"/></legend>  
<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
      <td>
      <table width="100%" border="0" cellspacing="1" cellpadding="4">
      <tr>
      	  <td><bean:message key="lbl.batch"/><font color="red">*</font></td>
		  <td><html:text property="batchNo" styleClass="text" styleId="batchNo" maxlength="100" readonly="true" value="${result[0].batchNo}"/></td>
      	  <td><bean:message key="lbl.presentationDate" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		  <td><html:text property="presentationDate" disabled="true"  styleId="presentationDate" styleClass="text" value="${result[0].presentationDate}"/></td>
	  </tr>
	  <tr>
		  <td><bean:message key="lbl.depositBankId"/><font color="red">*</font></td>
		  <td>	
	 			<html:text styleClass="text" property="bank" styleId="bank" maxlength="100" value="${result[0].bank}" readonly="true" tabindex="-1"/>
    			<html:button property="bankButton" styleId="bankButton" onclick="closeLovCallonLov1();openLOVCommon(79,'paySlip','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>
    		    <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${result[0].lbxBankID}"/>
		  </td>      			
       	  <td><bean:message key="lbl.depositBranchId"/><font color="red">*</font></td>
		  <td>
					<html:text styleClass="text" property="branch" styleId="branch" maxlength="100" value="${result[0].branch}" readonly="true" tabindex="-1"/>
   					<html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${result[0].lbxBranchID}"/>
  					<html:button property="branchButton" styleId="branchButton" onclick="openLOVCommon(80,'paySlip','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode','bankAccount');closeLovCallonLov('bank');" value=" " styleClass="lovbutton"></html:button>  
  					<html:hidden styleClass="text" property="micr" styleId="micr" value="${result[0].micr}" />
  					<html:hidden styleClass="text" property="ifscCode" styleId="ifscCode"  value="${result[0].ifscCode}" />
  		  </td>	
  	 </tr>
  	 <tr>		
		  <td><bean:message key="lbl.depositBnkAcc"/><font color="red">*</font></td>
		  <td><html:text styleClass="text" property="bankAccount" styleId="bankAccount"  maxlength="50" value="${result[0].bankAccount}" readonly="true"  /></td>
         <td><bean:message key="lbl.ReportType"/></td>
         <td>
         <html:select property="reportType" styleId="reportType" styleClass="text" value="${result[0].reportType}">
             <html:option value="P">PDF</html:option>
             <html:option value="E">Excel</html:option>       
           </html:select></td>
	</tr>
    <tr>		
		 <td colspan="4">
	  			<button type="button" name=" mybutton"  title="Alt+P" accesskey="P" class="topformbutton3" onclick="saveDepositBank();"><bean:message key="button.paySlip" /></button>
	  	</td>
	</tr>		  
    </table>
    </td>
</tr>
</table> 
</fieldset>  
</html:form>
<logic:present name="save">
	<script type="text/javascript">		
		alert("Deposit Bank Detail's is saved Successfully.");
		var batchNo=document.getElementById('batchNo').value;
		var bankID=document.getElementById('lbxBankID').value;
		var branchID=document.getElementById('lbxBranchID').value;
		var account=document.getElementById('bankAccount').value;
		var contextPath =document.getElementById('contextPath').value;
		var reportType=document.getElementById("reportType").value;
		window.close();
		window.opener.document.getElementById("generateBatch").action=contextPath+"/presentationBatchProcessDispatchAction.do?method=generatePaySlip&batchNo="+batchNo+"&bankID="+bankID+"&branchID="+branchID+"&account="+account+"&reportType="+reportType;
		window.opener.document.getElementById("generateBatch").submit();
	</script>
</logic:present>
<logic:present name="notSave">
	<script type="text/javascript">	
		alert("Deposit Bank Detail is not saved.");
	</script>
</logic:present>
</body>
</html:html>


