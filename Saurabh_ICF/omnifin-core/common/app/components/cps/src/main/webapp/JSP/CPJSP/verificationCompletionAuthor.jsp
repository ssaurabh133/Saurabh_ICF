<%--
      Author Name-  Neeraj Tripathi
      Date of creation -12/10/2011
      Purpose-          
      Documentation-     
      
 --%>
 
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
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
	   
		 
	
    <logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
		
	 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		  
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/fieldVerificationScript.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
  
     
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>



	</head>

<body onload="enableAnchor();checkChanges('tradeRemarksForm');">
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<logic:present name="verifCPS">
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
	          <td >CONTACT VERIFICATION COMPLETION</td>
          </tr>
        </table> 
	</td>
	</tr>
	</table>
 	</fieldset> 

<html:form  action="/fieldVerComBody" method="post" styleId="tradeRemarksForm">
<input type="hidden" name="verificationCapId" id="verificationCapId" value="" />
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<fieldset>  
<legend><bean:message key="lbl.fieldCheck"/></legend>
<table cellspacing=0 cellpadding=0 width="100%" border=0>
	<tr>
     <td><bean:message key="lbl.decison"/></td>
         <td><html:select property="decison" styleId="decison" styleClass="text" value="" onchange="selectValForSendback(value)">
		 	<option value="P">Positive</option>
			<option value="N">Negative</option>
			<option value="S">Send Back</option>
			<option value="R">Refer</option>
			<%-- <option value="S">Send Back</option> --%>
 		</html:select></td>        
        <td><bean:message key="lbl.remark"/><font color="red">*</font></td>
          <td><label>
                <textarea name="textarea" id="textarea" cols="70" rows="1"></textarea>
         </label></td>
	</tr>
<tr>
 <td>
 
<button type="button" name="search" id="searchButton" class="topformbutton2" title="Alt+V" accesskey="V" onclick="saveFiedlCheck();"><bean:message key="button.save" /></button>
 </td>
</tr>
 </table>

</fieldset>

 <logic:present name="sms">
	<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/fieldVarificationCompletionBehindAction.do?method=verificationCompletionCreditProcessing";
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='SB'){
	alert('<bean:message key="msg.sendBackSuccessfully"/>');
	parent.location="<%=request.getContextPath()%>/fieldVarificationCompletionBehindAction.do?method=verificationCompletionCreditProcessing";
	} 
	else if('<%=request.getAttribute("sms").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	alert('${checkStageM}');
	parent.location="<%=request.getContextPath()%>/fieldVarificationCompletionBehindAction.do?method=verificationCompletionCreditProcessing";
	} 
    </script>
	</logic:present>
</html:form>
   <script>
	setFramevalues("tradeRemarksForm");
</script>
</logic:present>

<%--   For CM --%>
<logic:present name="verifCMS">

	    <fieldset>
	<table cellspacing=0 cellpadding=0 width="100%" border=0>
  	<tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table> 
	</td>
	</tr>
	</table>
 	</fieldset> 

<html:form  action="/fieldVerComBody" method="post" styleId="tradeRemarksForm">
<input type="hidden" name="verificationCapId" id="verificationCapId" value="" />
<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
<fieldset>  
<legend><bean:message key="lbl.fieldCheck"/></legend>
<table cellspacing=0 cellpadding=0 width="100%" border=0>
	<tr>
     <td><bean:message key="lbl.decison"/></td>
         <td><html:select property="decison" styleId="decison" styleClass="text" value="" onchange="selectValForSendbackAtCM(value)">
		 	<option value="P">Positive</option>
			<option value="N">Negative</option>
			<option value="S">Send Back</option>
			<option value="R">Refer</option>
			<%-- <option value="S">Send Back</option> --%>
 		</html:select></td>        
        <td><bean:message key="lbl.remark"/><font color="red">*</font></td>
          <td><label>
                <textarea name="textarea" id="textarea" cols="70" rows="1"></textarea>
         </label></td>
	</tr>
<tr>
 <td>
 
<button type="button" name="saveButton" id="saveButton" class="topformbutton2" title="Alt+V" accesskey="V" onclick="saveFiedlCheckAtCM();"><bean:message key="button.save" /></button>
 </td>
</tr>
 </table>

</fieldset>

 <logic:present name="sms">
	<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/fieldVarificationCompletionBehindAction.do?method=verificationCompletionCreditManagement";
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='SB'){
	alert('<bean:message key="msg.sendBackSuccessfully"/>');
	parent.location="<%=request.getContextPath()%>/fieldVarificationCompletionBehindAction.do?method=verificationCompletionCreditManagement";
	} 
	else if('<%=request.getAttribute("sms").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	alert('${checkStageM}');
	parent.location="<%=request.getContextPath()%>/fieldVarificationCompletionBehindAction.do?method=verificationCompletionCreditManagement";
	} 
    </script>
	</logic:present>
</html:form>
   <script>
	setFramevalues("tradeRemarksForm");
</script>

</logic:present>
</body>
</html:html>