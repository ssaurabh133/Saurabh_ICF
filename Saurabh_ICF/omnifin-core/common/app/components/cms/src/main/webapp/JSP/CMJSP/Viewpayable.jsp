<!-- 
Author Name :- Manisha Tomar
Date of Creation :15-04-2011
Purpose :-  screen for the view payable
-->

<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/paymentMaker.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		
		
	
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
	<body onload="enableAnchor();document.getElementById('viewPayableForm').allotedAmount1.focus();onLoadPage()">
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<div id="centercolumn">	
	<div id="revisedcontainer">		
	<html:form action="/paymentCMProcessAction" method="post" styleId="viewPayableForm">
	<div class="" style="">
		<span class="">&nbsp;</span>
	   </div>                    
	
   <input type="hidden" name="canForward" value="${requestScope.canForward}" id="canForward" />
    <input type="hidden" name="paymentAmount" value="<%=request.getAttribute("paymentAmount") %>" id="paymentAmount" />
     <input type="hidden" name="amount" value="<%=request.getAttribute("amount") %>" id="paymentAmount" />

	<fieldset>	
		 <legend><bean:message key="lbl.paymentDetails" /> </legend>  
            <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
            <input type="hidden" name="lbxLoanNoHID" value="${requestScope.loanId}" id="lbxLoanNoHID"/>
             <input type="hidden" name="instrumentID" value="${requestScope.instrumentId}" id="instrumentID"/>
            <input type="hidden" name="businessPartnerType" value="${requestScope.bpType}" id="businessPartnerType"/>
            <input type="hidden" name="tdsAmount" value="${requestScope.tdsAmount}" id="tdsAmount"/>
        
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" >
<tr>
    <td class="gridtd">
    <table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridTable">
      <tr class="white2">
     
        <td ><strong><bean:message key="lbl.date" /></strong></td>
        <td ><strong><bean:message key="lbl.chargeDescription" /></strong></td>
        <td><strong><bean:message key="lbl.originalAmount" /></strong></td>
        <td><b><bean:message key="lbl.balanceAmount" /> </b></td>
        <td ><b><bean:message key="lbl.tdsAmount" /></b></td>
		<td ><b><bean:message key="lbl.amountInProcess" /></b></td>
		<td><b><bean:message key="lbl.allocatedAmount" /></b></td>
		<td><b><bean:message key="lbl.tdsAllocatedAmount" /></b></td>
      </tr>
           
      
        <logic:present name="getPayableList" >
      <logic:iterate name="getPayableList" id="subPayableList" indexId="count">
      
      	
      <tr class="white1">
     
		<td>${subPayableList.paymentDate }</td>
		<td>${subPayableList.chargeDesc }</td>
		<td>${subPayableList.originalAmount }</td>

		<td>${subPayableList.balanceAmount }
		<input type="hidden" name="balanceAmount" id="balanceAmount<%=count.intValue()+1%>" value="${subPayableList.balanceAmount }"/>
		<input type="hidden" name="txnAdvicedIDArry" value="${subPayableList.txnAdvicedID}" id="txnAdvicedID<%=count.intValue()+1%>"/>
		</td>
		<td>${subPayableList.tdsadviseAmount }
		<input type="hidden" name="tdsAmountArry" id="tdsadviseAmount<%=count.intValue()+1%>" value="${subPayableList.tdsadviseAmount }" />
		 </td>
		 <td>${subPayableList.amountInProcess }
		<input type="hidden" name="amountInProcessArry" value="${subPayableList.paymentDate }" id="amountInProcessArry<%=count.intValue()+1%>"/>
		</td>
		<td> <input type="text" name="allocatedArry" id="allotedAmount<%=count.intValue()+1%>" value="${subPayableList.allotedAmount }" 
		 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
		 onfocus="keyUpNumber(this.value, event, 18,id);"/>         
		</td>
	   <td> <input type="text" name="tdsAllocatedArry" id="tdsAllocatedAmount<%=count.intValue()+1%>" value="${subPayableList.tdsAllocatedAmount }"
	     onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
		 onfocus="keyUpNumber(this.value, event, 18,id);"/>
		         
		</td>
	   
		 </tr>
		 
			
			
	</logic:iterate>
    </logic:present>  
		
  
	
	
	
    </table></td>
</tr>
</table>
<br/>

<logic:present name="getPayableList" >

 <button type="button" id="save" class="topformbutton2" onclick="return onViewPayableSave('<bean:message key="msg.allocatedAmntShudntGrtr" />','<bean:message key="msg.sumAllocAmntShudEqul" />','<bean:message key="msg.sumtdsAmntShudEqul" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button> 
</logic:present>

	</fieldset>
<br/> 	 
	 </html:form>
	 <logic:present name="procval">
	<script type="text/javascript">
	if('<%=request.getAttribute("procval")%>'!='NONE')
	{
	   	alert('<%=request.getAttribute("procval").toString()%>');
	   	window.close();
		
	}
	</script>
</logic:present>
<logic:present name="msg">
	<script type="text/javascript">
		if('<%=request.getAttribute("msg").toString()%>'=='S')
		{
			 alert("<bean:message key="lbl.dataSave" />");
			 window.close();
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
		}
	</script>
</logic:present>	
	 
	 
 <logic:present name="setID">

</logic:present>	
	    
</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
  </body>
</html:html>
     
 