<!-- 
Author Name :- Manisha Tomar
Date of Creation :13-09-2011
Purpose :-  screen for the view payable
-->


<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cmScript/cmLoanInitjs.js"></script>
	
	
	<script type="text/javascript">

		function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
	if (window.focus) {newwindow.focus()}
	return false;
}

function checkAll(master)
{
var checked = master.checked;
var col = document.getElementsByTagName("INPUT");
for (var i=0;i<col.length;i++) 
{
col[i].checked= checked;
}
}
   
	</script>
	<script type="text/javascript">
	<!--
		function onLoadPage(){
  <%if(request.getAttribute("setID") != null) {%>
	  window.close();
	<%  }%>
	 } 	
	-->
	</script>
	
	
	</head>
	<body onload="enableAnchor();onLoadPage();init_fields();">
	
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
	<html:form action="/viewPayableEarlyClousreAction" method="post" styleId="viewPayableForm">
	<div class="" style="">
		<span class="">&nbsp;</span>
	   </div>                    

	<fieldset>	
		 <legend><bean:message key="lbl.paymentDetails" /> </legend>  
            <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
          
        
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" >
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridTable">
      <tr class="white2">
     
        <td  ><strong><bean:message key="lbl.date" /></strong></td>
        <td ><strong><bean:message key="lbl.chargeDescription" /></strong></td>
        <td ><strong><bean:message key="lbl.originalAmount" /></strong></td>
        <td><b><bean:message key="lbl.balanceAmount" /> </b></td>
        <td><b><bean:message key="lbl.tdsAmount" /></b></td>
		<td><b><bean:message key="lbl.amountInProcess" /></b></td>
	    <td><b><bean:message key="lbl.allocatedAmount" /></b></td>
		<td ><b><bean:message key="lbl.tdsAllocatedAmount" /></b></td>
		
      </tr>
        <logic:present name="allocatedPayableList" >
        <logic:notEmpty name="allocatedPayableList">
      <logic:iterate name="allocatedPayableList" id="subPayableList" indexId="count">
      <tr class="white1">
     

	<td >${subPayableList.paymentDate}
	 </td>
	<td >${subPayableList.chargeDesc}
	</td>
	
	<td >${subPayableList.originalAmount}
	</td>
	
<td >${subPayableList.balanceAmount}
	 </td>
		
	<td >${subPayableList.tdsadviseAmount}
	</td>
     
     
  <td >${subPayableList.amountInProcess}
	 </td>
	<td >${subPayableList.allotedAmount}
	</td>
	
	<td >${subPayableList.tdsAllocatedAmount}
	</td>   

	 </tr>
		 
			
			
	</logic:iterate>
	</logic:notEmpty>
    </logic:present>  
    
     <logic:notPresent name="allocatedPayableList" >
     
     <logic:present name="allocatedReceivableList" >
     <logic:notEmpty  name="allocatedReceivableList" >
      <logic:iterate name="allocatedReceivableList" id="subReceivableList" indexId="count">
      <tr class="white1">
     
	<td >${subReceivableList.receiptDate}
	
	 </td>
	<td >${subReceivableList.chargeDesc}
	</td>
	
	<td >${subReceivableList.originalAmount}
	
	</td>
	
<td >${subReceivableList.balanceAmount}
	
	 </td>
		
	<td >${subReceivableList.tdsadviseAmount}
	
	</td>
     
     
  <td >${subReceivableList.amountInProcess}
	</td>
	<td >${subReceivableList.allotedAmount}
	</td>
	
	<td >${subReceivableList.tdsAllocatedAmount}
	</td>   

		 </tr>
		 
			
			
	</logic:iterate>
	</logic:notEmpty>
    </logic:present>  
		
  </logic:notPresent>
	
	
	
    </table></td>
</tr>
</table>
<br/>

	</fieldset>
<br/> 	 
	 </html:form>
	 
 <logic:present name="setID">

</logic:present>	
	    
</div>

  </body>
</html:html>
     
 