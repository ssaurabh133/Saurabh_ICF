<!-- 
Author Name :- Manisha Tomar
Date of Creation :15-04-2011
Purpose :-  screen for Product Detail
-->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
			<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/loanInitiation.js"></script>
		
		<script type="text/javascript">
		
		
        function infoCall()
        {	
        document.getElementById("loanInfo").style.display='';
        }
      

		
		
			function no_disbursal()
			{
					var url="PopupDisbursal.html"
	newwindow=window.open(url,'init_disbursal','top=200,left=250,scrollbars=no,width=1366,height=768' );
	if (window.focus) {newwindow.focus()}
	return false;
			}
		</script>
		
		
	</head>

<body onload="enableAnchor();">
<DIV id=centercolumn>
<DIV id=revisedcontainer>
<html:form action="/loanDetailCMProcessAction" method="post" styleId="productPopUpForm">
<html:hidden property="basePath" styleId="basePath" value="<%=request.getContextPath()%>"/>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      <table width="100%" border="0" cellspacing="1" cellpadding="4">
        <tr>
        
        <td width="220" class="white2" style="width:150px;"><b><bean:message key="lbl.select"/></b></td>
         <td width="220" class="white2" style="width:150px;"><b><bean:message key="lbl.productType"/></b></td>
          <td width="220" class="white2" style="width:150px;"><strong><bean:message key="lbl.product"/></strong></td>
          <td width="220" class="white2" style="width:150px;"><b><bean:message key="lbl.scheme"/> </b></td>
          <td width="220" class="white2" style="width:150px;"><b><bean:message key="lbl.sanctionedLoanAmount"/></b></td>
          <td width="220" class="white2" style="width:150px;"><b><bean:message key="lbl.utilizeLoanAmount"/></b></td>
          <td width="220" class="white2" style="width:150px;"><b><bean:message key="lbl.sanctionedValidTill"/></b></td>
         </tr>
         
     <logic:present name="loanList" >
      <logic:iterate name="loanList" id="subloanList">
	     <tr>
	          
	           <td class="white1" ><input type="radio" name="dealLoanId" id="dealLoanId" value="${subloanList.loanId}" onclick="saveRecord();" /></td>
				<td width="220" class="white1" style="width:220px;">${subloanList.productType}</td>
				<td width="220" class="white1" style="width:220px;">${subloanList.product}</td>
				<td width="220" class="white1" style="width:220px;">${subloanList.scheme}</td>
				<input type="hidden" name="sancAmount" id="sancAmount" value="${subloanList.sanctionedLoanAmount}"></input>
				<input type="hidden" name="utiAmount" id="utiAmount" value="${subloanList.utilizeLoanAmount}"></input>
				<td width="220" class="white1" style="width:220px;">${subloanList.sanctionedLoanAmount}</td>
				<td width="220" class="white1" style="width:220px;">${subloanList.utilizeLoanAmount}</td>
				<td width="220" class="white1" style="width:220px;">${subloanList.sanctionedValidtill}</td>


	     </tr>
	</logic:iterate>
    </logic:present>     
         
         
       </table>    
    </td>
  </tr>
</table>

</html:form>
</DIV>
</DIV>
  </BODY>
  </html:html>
