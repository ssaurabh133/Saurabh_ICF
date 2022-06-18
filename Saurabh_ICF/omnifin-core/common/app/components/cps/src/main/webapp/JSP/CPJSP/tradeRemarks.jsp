<%--
      Author Name-      Ankit Agarwal	
      Date of creation -06/09/2011
      Purpose-          Market Check info
      Documentation-     
      
 --%>

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
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
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	   
		 
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/relationship_manager.js"></script>

        <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
	   	   	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/tradeCheck.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
     
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
	
<body onload="enableAnchor();checkChanges('tradeRemarksForm');">
<div id=centercolumn>
<div id=revisedcontainer>
<fieldset>
   
      

<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${dealHeader[0].dealNo}</td>
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
	          <td >TRADE CHECK COMPLETION</td>
          </tr>
        </table> 
</td>
</tr>
</table>

</fieldset>

<html:form  action="/tradeCheck" method="post" styleId="tradeRemarksForm">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
  <fieldset>
  
 <legend><bean:message key="lbl.tradeComCheck"/></legend>
<table cellspacing=0 cellpadding=0 width="100%" border=0>
	<tr>
     <td><bean:message key="lbl.decison"/></td>
         <td><html:select property="decison" styleId="decison" styleClass="text" value="">
		 	<option value="P">Positive</option>
			<option value="N">Negative</option>
			<option value="S">Send Back</option>
 		</html:select></td>       
        
        <td><bean:message key="lbl.remark"/><font color="red">*</font></td>
          <td><label>
          
             <textarea name="textarea" class="text" maxlength="1000" id="textarea" cols="70" onblur="fnChangeCase('tradeSuplierForm','textarea')" rows="1"></textarea>
         </label></td>
	</tr>
<tr>
 <td>
<button type="button" name="search" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveTradeRemarks('${dealHeader[0].dealId}');"><bean:message key="button.save" /></button>
 </td>
</tr>
 </table>

</fieldset>

 <logic:present name="sms">
	<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/tradeCheckCompletionBehindAction.do";
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='SB'){
	      alert('<bean:message key="msg.sendBackSuccessfully"/>');
	      parent.location="<%=request.getContextPath()%>/tradeCheckCompletionBehindAction.do";
	} 
	else if('<%=request.getAttribute("sms").toString()%>'=='N'){
	      alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	      alert('${checkStageM}');
	      parent.location="<%=request.getContextPath()%>/tradeCheckCompletionBehindAction.do";
	} 
    </script>
	</logic:present>
</html:form>
</div>
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
<script>
	setFramevalues("tradeRemarksForm");
</script>

</body>
</html:html>
