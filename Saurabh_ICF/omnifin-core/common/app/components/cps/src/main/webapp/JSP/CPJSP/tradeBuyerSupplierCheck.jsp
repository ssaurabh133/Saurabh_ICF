<%--
      Author Name-  Ankit agarwal    
      Date of creation -06/09/2011
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/creditProcessing.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/gcdScript/customerDetail.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/tradeCheck.js"></script>
    	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
  
     
<style>
		.watermark {
		    color:lightgray;
		}
		.adddate {
		    color: #000;
		}
</style>
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

	<body onload="enableAnchor();checkChanges('tradeBuyerSupplierForm');" onunload="closeAllWindowCallUnloadBody();">


	<input type="hidden" name="path" id="path" value="<%=request.getContextPath()%>" />
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
    <fieldset>

<logic:present name="comList"> 
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
	          <td >TRADE CHECK CAPTURING</td>
          </tr>
        </table> 
</td>
</tr>
</table>
</logic:present> 

<logic:notPresent name="comList"> 
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
</logic:notPresent> 
</fieldset>
	
<html:form action="/tradeCheckCapSearch" method="POST" styleId="tradeBuyerSupplierForm">
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />

<fieldset>
<logic:present name="comList"> 
<legend><bean:message key="lbl.tradeCap"/> </legend>
</logic:present>

<logic:notPresent name="comList">
<legend><bean:message key="lbl.tradeCapCom"/> </legend> 
</logic:notPresent>

  <logic:present name="comList">   
   <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td class="gridtd">
    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
	    <td ><b><bean:message key="lbl.tradeCheckType"/></b></td>
	    <td><b><bean:message key="lbl.buyerSuplierName" /></b></td>
        <td><b><bean:message key="lbl.contactPerson"/></b></td>
	    <td><b><bean:message key="lbl.relation"/></b></td>
       	<td><b><bean:message key="lbl.AverageMonthlySalesPurchase"/></b></td>
        <td><b><bean:message key="lbl.paymentTerms"/></b></td>
        <td><b><bean:message key="lbl.productType"/> </b></td>
        <td><b><bean:message key="lbl.vintageOfRelationship"/> </b></td>
        <td><b><bean:message key="lbl.remark"/></b></td>
        <td><b><bean:message key="lbl.status"/></b></td>
     </tr>
 
    <logic:present name="tradeBuyerSuplierList">
		<logic:iterate name="tradeBuyerSuplierList" id="subdealdetails" >
			<tr class="white1">
	
 <td><a href="#" onclick="popUpJsp('${dealHeader[0].dealId}','${subdealdetails.tradeCheck}','${subdealdetails.verificationId}','${subdealdetails.entityType}','${subdealdetails.entityId}');" >${subdealdetails.tradeCheck}</a></td>       
          <html:hidden property="tradeCheck" styleId="tradeCheck"  value="${subdealdetails.tradeCheck}"/>
          <td>${subdealdetails.buyerSuplierName}</td>
          <td>${subdealdetails.contactPerson}</td>        
          <td>${subdealdetails.relation}</td>
          <td>${subdealdetails.averagePurchase}</td>
          <td>${subdealdetails.paymentTerms}</td>
           <td>${subdealdetails.productType}</td>
          <td>${subdealdetails.vintageOfRelationship}</td>
          <td>${subdealdetails.textarea}</td>
          <td>${subdealdetails.status}</td>
     	</tr>
		</logic:iterate>
</logic:present>
	
	    <logic:present name="tradeList">
		<logic:iterate name="tradeList" id="subdealdetails" >
	<tr class="white1">
	
 <td><a href="#" onclick="updatePopUpJsp('${dealHeader[0].dealId}','${subdealdetails.tradeCheck}','${subdealdetails.verificationId}','${subdealdetails.entityId}');" >${subdealdetails.tradeCheck}</a></td>       
          <html:hidden property="tradeCheck" styleId="tradeCheck"  value="${subdealdetails.tradeCheck}"/>
          <td>${subdealdetails.buyerSuplierName}</td>
          <td>${subdealdetails.contactPerson}</td>        
          <td>${subdealdetails.relation}</td>
          <td>${subdealdetails.averagePurchase}</td>
          <td>${subdealdetails.paymentTerms}</td>
           <td>${subdealdetails.productType}</td>
          <td>${subdealdetails.vintageOfRelationship}</td>
          <td>${subdealdetails.textarea}</td>
          <td>${subdealdetails.status}</td>
     	</tr>
		</logic:iterate>
	</logic:present>

    	</table>
     	</td>
   </tr>
 
   <tr>
      <td><button type="button" name="buyerButton" id="forward"  class="topformbutton2" title="Alt+F" accesskey="F" onclick="forwardDetails('${dealHeader[0].dealId}','${subdealdetails.verificationId}');"><bean:message key="button.forward" /></button> </td>
   </tr>

 </table>
</logic:present>



<!-- List for trade Check comp -->

<logic:notPresent name="comList"> 
 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td class="gridtd">
    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
	    <td ><b><bean:message key="lbl.tradeCheckType"/></b></td>
	    <td><b><bean:message key="lbl.buyerSuplierName" /></b></td>
        <td><b><bean:message key="lbl.contactPerson"/></b></td>
	    <td><b><bean:message key="lbl.relation"/></b></td>
       	<td><b><bean:message key="lbl.AverageMonthlySalesPurchase"/></b></td>
        <td><b><bean:message key="lbl.paymentTerms"/></b></td>
        <td><b><bean:message key="lbl.productType"/> </b></td>
        <td><b><bean:message key="lbl.vintageOfRelationship"/> </b></td>
        <td><b><bean:message key="lbl.remark"/></b></td>
     </tr>
   
    <logic:present name="tradeList">
       <logic:notEmpty name="tradeList">
			<logic:iterate name="tradeList" id="subdealdetails" >
				<tr class="white1">
		
	 <td><a href="#" onclick="viewPopUpJsp('${dealHeader[0].dealId}','${subdealdetails.tradeCheck}','${subdealdetails.verificationId}','${subdealdetails.entityId}');" >${subdealdetails.tradeCheck}</a></td>       
	          <html:hidden property="tradeCheck" styleId="tradeCheck"  value="${subdealdetails.tradeCheck}"/>
	           <td>${subdealdetails.buyerSuplierName}</td>
	          <td>${subdealdetails.contactPerson}</td>        
	          <td>${subdealdetails.relation}</td>
	          <td>${subdealdetails.averagePurchase}</td>
	          <td>${subdealdetails.paymentTerms}</td>
	           <td>${subdealdetails.productType}</td>
	          <td>${subdealdetails.vintageOfRelationship}</td>
	          <td>${subdealdetails.textarea}</td>
	  
	     	</tr>
			</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="tradeList">
								<script type="text/javascript">
							
							            alert('No Data Found');
							            self.close(); 
						                 
						   	   </script>
                            </logic:empty>
	</logic:present>
	</table>
	</td>
	</tr>
	</table>
</logic:notPresent> 


</fieldset>
	</html:form>
</div>
</div>
<logic:present name="sms">
<script type="text/javascript">
	if("<%=request.getAttribute("sms")%>"=="S")
	{
		alert("<bean:message key="lbl.dataSave" />");
		opener.document.location.href="<%=request.getContextPath()%>/tradeCheckCapSearch.do?method=tradeCheckCapturing&dealId="+'${dealHeader[0].dealId}';
		window.close();
	
	
	}else if("<%=request.getAttribute("sms")%>"=="M")
	{
		alert("<bean:message key="lbl.dataModify" />");
		window.close();
		opener.document.location.reload(true);
		opener.window.close();
	
	}else if('<%=request.getAttribute("sms").toString()%>'=='F')
	{
	alert('<bean:message key="msg.ForwardSuccessfully"/>');
	document.getElementById('tradeBuyerSupplierForm').action="<%=request.getContextPath()%>/tradeCheckCapturingBehindAction.do";
	document.getElementById('tradeBuyerSupplierForm').submit();
	
	}else if('<%=request.getAttribute("sms").toString()%>'=='N')
	{
	DisButClass.prototype.DisButMethod();
	alert("You can't Forward without saving details");
	document.getElementById('tradeBuyerSupplierForm').action="<%=request.getContextPath()%>/tradeCheckCapSearch.do?method=tradeCheckCapturing&dealId="+'${dealHeader[0].dealId}';
	document.getElementById('tradeBuyerSupplierForm').submit();
	}else	{
		alert("<bean:message key="lbl.dataExist" />");
		window.close();	
	}
</script>
</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
</div>
<script>
	setFramevalues("tradeBuyerSupplierForm");
</script>

</body>
</html:html>