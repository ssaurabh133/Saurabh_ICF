<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html>
	
	<head>
	<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
    int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
    request.setAttribute("no",no); %>
	
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cpScript/imdScript.js"></script>		
		 <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		
	
	
	<script type="text/javascript">
  
	</script>
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
<!--	<body onload="enableAnchor();generateReceiptReport();document.getElementById('receiptMakerSearch').loanAccountButton.focus();init_fields();" onunload="closeAllLovCallUnloadBody();">-->
	<body onload="enableAnchor();" >
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/imdMakerProcessAction" method="post" styleId="imdMakerSearch">
	
	
		<input type="hidden" name="forward" id="forward" value="${forward}" />
		<input type="hidden" name="frdLoanID" id="frdLoanID" value="${frdLoanID}" />
		<input type="hidden" name="frdInstrumentID" id="frdInstrumentID" value="${frdInstrumentID}" />
		<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
		<html:hidden property="dealId" styleClass="text" styleId="dealId"  value="${sessionScope.dealId}" />
 		<html:hidden property="loanId"  styleClass="text" styleId="loanId"  value="${sessionScope.loanId}" />
 		<html:hidden property="instrument"  styleClass="text" styleId="instrument"  value="${sessionScope.instrument}" />

	<fieldset>
<logic:present name="loanHeader">
<table cellSpacing=0 cellPadding=0 width="100%" border=0>
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
 </logic:present>
 <logic:present name="dealHeader">

 	  <table cellspacing="0" cellpadding="0" width="100%" border="0">
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
 </logic:present>
</fieldset>	
		
     <fieldset>	
		
 <legend><bean:message key="lbl.imdSearchRecords"/></legend>  
  	<logic:empty name="list1"> 
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    
    <tr class="white2">
	 
         <td><b><bean:message key="lbl.chargeCode"/></b></td>
	     <td><b><bean:message key="lbl.chargeDesc"/></b></td>
         <td><b><bean:message key="lbl.imdDate"/></b></td>
         <td><b><bean:message key="lbl.imdAmount"/></b></td>
	     <td><b><bean:message key="lbl.instrumentMode"/></b></td>
	       <td><b><bean:message key="lbl.instrumentMode"/></b></td>
		 <td><b><bean:message key="lbl.receiptNo"/> </b></td>
         <td><b><bean:message key="lbl.imdStatus"/> </b></td>
		 <td><b><bean:message key="lbl.status"/> </b></td>
		<td><b><bean:message key="lbl.makerId"/></b></td>
		 <td><b><bean:message key="lbl.makerDate"/> </b></td>
         <td><b><bean:message key="lbl.authorId"/> </b></td>
		 <td><b><bean:message key="lbl.authorDate"/> </b></td>

	</tr>

		</table>
		</td>
	   </tr>
		</table>
</logic:empty>
		<logic:present name="imdDetailsGrid">
		<logic:notEmpty name="list1"> 
    <display:table  id="list1"  name="list1" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1"  partialList="true" size="${list1[0].totalRecordSize}" requestURI="/imdMakerSearch.do" >
    <display:setProperty name="paging.banner.placement"  value=""/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="chargeCode" titleKey="lbl.chargeCode"  sortable="true"  />
    <display:column property="chargeDesc" titleKey="lbl.chargeDesc"  sortable="true"  />
	<display:column property="imdDate" titleKey="lbl.imdDate"  sortable="true"  />
	<display:column property="imdAmount" titleKey="lbl.imdAmount"  sortable="true"  />
	<display:column property="instrumentMode" titleKey="lbl.instrumentMode"  sortable="true"  />
	<display:column property="receiptNo" titleKey="lbl.receiptNo"  sortable="true"  />
	<display:column property="instrumentAmount" titleKey="lbl.instrumentAmount"  sortable="true"  />
    <display:column property="imdStatus" titleKey="lbl.imdStatus"  sortable="true"  />
	<display:column property="status" titleKey="lbl.status"  sortable="true"  />
	<display:column property="makerId" titleKey="lbl.makerId"  sortable="true"  />
	<display:column property="makerDate" titleKey="lbl.makerDate"  sortable="true"  />
    <display:column property="authorId" titleKey="lbl.authorId"  sortable="true"  />
	<display:column property="authorDate" titleKey="lbl.authorDate"  sortable="true"  />
	

</display:table>
</logic:notEmpty>

</logic:present> 
	

	</fieldset>

	  </html:form>
    
</div>



</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
</body>
</html>