<!--
 	Author Name      :- Amit Kumar
 	Date of Creation :- 
 	Purpose          :- To provide decision for collateral details captured during deal capturing
 	Documentation    :- 
 -->

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
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
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
	
<body onload="enableAnchor();checkChanges('collateralRemarksForm');">

<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
<div id=centercolumn>
<div id=revisedcontainer>
<fieldset>
   
      

<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${collateralHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${collateralHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${collateralHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${collateralHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${collateralHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${collateralHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Collateral Verification Completion</td>
          </tr>
        </table> 
</td>
</tr>
</table>

</fieldset>

<html:form  action="/collateralCheck" method="post" styleId="collateralRemarksForm">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
<html:hidden property="dealId" styleId="dealId" value="${sessionScope.dealId}" />
<html:hidden property="verificationId" styleId="verificationId" value="${sessionScope.verificationId}" />
  <fieldset>
  
 <legend>
 	<bean:message key="lbl.collateralCheck"/>
 </legend>
<table cellspacing=0 cellpadding=0 width="100%" border=0>
	<tr>
     <td>
     	<bean:message key="lbl.decison"/>
     </td>
         <td><html:select property="decision" styleId="decision" styleClass="text" value="">
		 	<option value="P">Positive</option>
			<option value="N">Negative</option>
			<option value="S">Send Back</option>
 		</html:select></td>       
        
        <td>
        	<bean:message key="lbl.remark"/> <font color="red">*</font>
        </td>
        <td>
            <textarea name="remarks" id="remarks" maxlength="500"></textarea>
        </td>
	</tr>
<tr>
 <td><button type="button" name="search" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveCollateralCompletionDecision()"><bean:message key="button.save" /></button>
 </td>
</tr>
 </table>

</fieldset>

 <logic:present name="sms">
	<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/collateralCompletionBehindAction.do?method=collateralCompletionSearch";
		
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='SB'){
	alert('<bean:message key="msg.sendBackSuccessfully"/>');
	parent.location="<%=request.getContextPath()%>/collateralCompletionBehindAction.do?method=collateralCompletionSearch";
	} 
	else if('<%=request.getAttribute("sms").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	alert('${checkStageM}');
	parent.location="<%=request.getContextPath()%>/collateralCompletionBehindAction.do?method=collateralCompletionSearch";
	} 
    </script>
	</logic:present>
</html:form>
</div>
</div>
<script>
	setFramevalues("collateralRemarksForm");
</script>

</body>
</html:html>
