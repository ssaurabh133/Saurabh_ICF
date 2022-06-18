<!--
 	Author Name      :- PRASHANT Kumar
 	Date of Creation :- 
 	Purpose          :- To provide user interface for Deviation Approval 
 	Documentation    :- 
 -->
 
<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>

		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	    <script type="text/javascript"  src="<%=request.getContextPath() %>/js/cpScript/underWriter.js"></script>

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
<style type="text/css">
.text{
/*border:1px solid #9BB168;*/
color:#000;
font-family:arial,serif;
font-size:13px;
padding-left:2px;
width:300px;
resize:none;
height:40px;
}
</style>
	</head>
	<body onload="enableAnchor();checkChanges('deviationApprovalForm');document.getElementById('deviationApprovalForm').remark.focus();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage">
	</div>
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
	          <td >Deal Capturing</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>

	<logic:present name="queryResponse">
	<html:form action="/saveQueryResponseApproval" method="POST" styleId="saveQueryResponseApprovalForm">
 	 <input type="hidden" id="contextPath" value="<%=request.getContextPath() %>" />
  <fieldset>	
		 <legend><bean:message key="lbl.queryDetails"/></legend>

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" >
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTable">
    <tr class="white2">
        <td><b><bean:message key="lbl.queryDateTime"/></b></td>
         <td><b><bean:message key="lbl.queryType"/></b></td>
		 <td><b><bean:message key="lbl.queryRemarks"/></b></td>
		   <td><b><bean:message key="lbl.queryRaised"/></b></td>
		  <td><b><bean:message key="lbl.resolutionStatus"/></b></td>
		  <td><b><bean:message key="lbl.resolutionRemarks"/></b></td>
         </tr>
        <logic:present name="queryList">
        <logic:iterate name="queryList" id="queryListData" indexId="count">
	<tr class="white1">
	 <td>${queryListData.queryDate}</td>
	 <td>${queryListData.queryTypeDesc}</td>
   	 <td>${queryListData.queryRemarks}</td>
   	 <td>${queryListData.queryRaised}</td>
	 <td>${queryListData.resolution}</td>
	 <input type="hidden" name="dealQueryResponseId" id="dealQueryResponseId<%=count.intValue()+1 %>" value="${queryListData.dealQueryId}" />
	 <td><textarea name="resolutionRemarks" class="text" id="resolutionRemarks<%=count.intValue()+1 %>" maxlength="1000">${queryListData.resolutionRemarks}</textarea></td>
     
	 </tr>
	 </logic:iterate>
	 </logic:present>
 </table>    </td>
</tr>
</table>

	</fieldset>

	<tr><td> <button type="button" name="saveButton" value="save" class="topformbutton4" onclick="saveQueryResponseApproval();" title="Alt+V" accesskey="V"><bean:message key="button.savefrwd" /></button> </td></tr>
	 

  </html:form>
  </logic:present>
  <logic:notPresent name="queryResponse">
  	
  	  	
  	<html:form action="/saveDeviationApproval" method="post" styleId="deviationApprovalForm">
 	 <input type="hidden" id="contextPath" value="<%=request.getContextPath() %>" />
   <fieldset>	
		 <legend><bean:message key="lbl.queryDetails"/></legend>

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1" id="gridTable">
     <tr class="white2">
        <td><b><bean:message key="lbl.queryDateTime"/></b></td>
        <td><b><bean:message key="lbl.queryType"/></b></td>
		 <td><b><bean:message key="lbl.queryRemarks"/></b></td>
		  <td><b><bean:message key="lbl.queryRaised"/></b></td>
		  <td><b><bean:message key="lbl.resolutionStatus"/></b></td>
		  <td><b><bean:message key="lbl.resolutionRemarks"/></b></td>
         </tr>
        <logic:present name="queryList">
        <logic:iterate name="queryList" id="queryListData">
	<tr class="white1">
	 <td>${queryListData.queryDate}</td>
	 <td>${queryListData.queryTypeDesc}</td>
   	 <td>${queryListData.queryRemarks}</td>
   	 <td>${queryListData.queryRaised}</td>
	 <td>${queryListData.resolution}</td>
	 <td>${queryListData.resolutionRemarks}</td>
     
	 </tr>
	 </logic:iterate>
	 </logic:present>
 </table>    </td>
</tr>
</table>

	</fieldset>
  </html:form>
  	
  </logic:notPresent>
  
</div>

</div>

<script>
	parent.menu.document.test.checkModifications.value = '';
	parent.menu.document.test.getFormName.value = "saveQueryResponseApprovalForm";
</script>
</body>
<logic:present name="sms">
<script type="text/javascript">
 if('<%=request.getAttribute("sms")%>'=='S')
	{
		alert("<bean:message key="msg.ForwardSuccessfully" />");
	    parent.location="<%=request.getContextPath()%>/querResponseSearchBehindAction.do";
	}
	else if('<%=request.getAttribute("sms")%>'=='E')
	{
		alert("<bean:message key="msg.DataNotSaved" />");
		parent.location="<%=request.getContextPath()%>/querResponseSearchBehindAction.do";
	}
	
</script>
</logic:present>
</html:html>


