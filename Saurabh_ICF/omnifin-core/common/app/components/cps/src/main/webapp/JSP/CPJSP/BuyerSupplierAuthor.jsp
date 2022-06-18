<!--Author Name : Pawan Kumar Singh-->
<!--Date of Creation : 14-April-2011-->
<!--Purpose  : Information of Buyer-->
<!--Documentation : -->

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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>		
   
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
   
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/search_customer_detail.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/customerEntry.js"></script>	
   <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
   <script type="text/javascript">    
   </script>
  
	</head>
	<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('sourcingForm').comments.focus();init_fields();">
	<div id="centercolumn">
	<div id="revisedcontainer">
	<fieldset>   
      <table cellSpacing=0 cellPadding=0 width="100%" border=0>
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
	          <td >Deal Capturing</td>
          </tr>
        </table> 
</td>
</tr>
</table>

    </fieldset>	
	<html:form action="/buyerSuppMainAction" method="post" styleId="sourcingForm">
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	
 
  <fieldset>
  <legend><bean:message key="lbl.author"/></legend>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td><bean:message key="lbl.comments"/><font color="red">*</font></td>
			 <td><div style="float:left;">
			 <textarea name="comments" id="comments" maxlength="500"></textarea>
			  
			 </div></td>
		
		<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
		 <td><bean:message key="lbl.decision"/></td>
		 <td  id=""><span style="float: left;">
		 <html:select property="decision" styleId="decision">
		        <html:option value="A">Approved</html:option>
		        <html:option value="X">Rejected</html:option>
		        <html:option value="P">Send Back</html:option>
		      </html:select> 
		 </span></td>
		</tr> 		  
		<tr>
	      <td align="left" colspan="3">
	      
	        <button type="button" id="save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveBuyerSupplierAuthor();"><bean:message key="button.save" /></button>
	      
	    </td>
		 
		</tr> 
		</table>
		
	      </td>
	</tr>
	</table>
	
	  </fieldset>
	<br/> 
	
	<logic:present name="ApprovedSuccess">
	<script type="text/javascript">
	if('<%=request.getAttribute("ApprovedSuccess").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/buyerSuppBehindAction.do?method=buyerSuppAuthor";
	 
	
	}else if('<%=request.getAttribute("ApprovedSuccess").toString()%>'=='E'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	parent.location="<%=request.getContextPath()%>/buyerSuppBehindAction.do?method=buyerSuppAuthor";
	 
	}
	 
     </script>
	
	</logic:present>
	
	



</html:form>
</div>

</div>

</body>
</html:html>