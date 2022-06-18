<%--
      Author Name-  Kaustuv Ranjan    
      Date of creation -12/04/2011
      Purpose-          
      Documentation-     
      
 --%>


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
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customerDetail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/applicantDetail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		
	
	
    <script type="text/javascript">
	
	
		function searchCustomer()
		{
			DisButClass.prototype.DisButMethod();
			var customerId=document.getElementById('customerId').value;
			var customerName=document.getElementById('customerName').value;
			var panNo=document.getElementById('panNo').value;
			var aadhaar=document.getElementById('aadhaar').value;
			var registrationNo=document.getElementById('registrationNo').value;
			var appFormNo=document.getElementById('appFormNo').value;
			var loanNO=document.getElementById('loanNO').value;
			if(loanNO)
			{
				document.getElementById('status').value= 'G';
			}
			else
			{
				document.getElementById('status').value= 'G';
			}
			if(customerId!=''||customerName!=''||panNo!=''||registrationNo!=''||appFormNo!=''||loanNO!=''|| aadhaar!='')
			{
			    //alert("searchCustomer");
				document.getElementById("LinkCustomerForm").action="<%=request.getContextPath() %>/linkCustomer.do?method=searchCustomerLoan&panNo="+panNo+"&aadhaar="+aadhaar;
				document.getElementById("processingImage").style.display = '';
			 	document.getElementById("LinkCustomerForm").submit();
			 	return true;
			}
			else
			{
				alert("Select at least one");
				DisButClass.prototype.EnbButMethod();
				return false;
			}
	}
		
 	   function selectCustomer()
 	   {
 	           // alert("In selectCustomer....."+document.getElementById("hType").value);
 	           if(checkboxCheck(document.getElementsByName('dealstore')))
 	           {
 	           		var customerId="";
 	           		var custId=document.getElementsByName('dealstore');
 	           		for(i=0;i<custId.length;i++)
 	           		{
 	           			if(custId[i].checked==true)
 	           			{
 	           				customerId=custId[i].value;
 	           				break;
 	           			}
 	           		}
 	                var applType=document.getElementById("hType").value;
	 	            
	 	            var status=document.getElementById("hstatus").value;
	 	             //alert("Customer Id: "+customerId+"status: "+status);
	                 if(customerId!='')
	                  {	
			 	          document.getElementById("LinkCustomerForm").action="approveCustomerAction.do?method=linkCustLoanMaker&codeId="+customerId+"&applType="+applType+"&status="+status;
			 	          document.getElementById("LinkCustomerForm").submit();
	                  }	            
 		     	}
 		     	else
 		     	{
 		     		alert("Select atleast one record");
 		     	}
 	   }
 		   
  
 	    function linkGCD(id,cType1)
        {
       
            curWin = 0;
			otherWindows = new Array();
          // var tableStatus=document.getElementById("hstatus").value;
          
           // alert("ok"+cType1);   
       if(cType1!=''&& cType1=='CORPORATE')
       {
       		cType='C';
       } 
       else
       {
       		cType='I';
       }  
       
        
        //   alert("Value of customerID......"+id+"Table Status: "+tableStatus+"Customer Type: "+customerType);
            
        if(cType=='I')
	    {
			var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag=notEdit";
			window.child = window.open(url,'nameI','height=400,width=1000,top=200,left=250').focus;
			otherWindows[curWin++] =window.open(url,'nameI','height=400,width=1000,top=200,left=250');
		    return true;
	    }
	   else if(cType=='C')
	    {
			var url="corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId="+id+"&cType="+cType+"&updateFlag=notEdit";
			window.child = window.open(url,'nameC','height=420,width=1130,top=200,left=250').focus;
			otherWindows[curWin++] =window.open(url,'nameC','height=400,width=1000,top=200,left=250');
			return true;
        }    
            
            
           /* if(customerType=='I')
		    {
				var url="corporateEntryForm.do?method=displayCorporateForms&customerId="+id+"&customerType=I&tableStatus="+tableStatus;
				window.open(url,'name','height=400,width=1000,top=200,left=250');
			    return true;
		    }
		   else if(customerType=='C')
		    {
				var url="corporateEntryForm.do?method=displayCorporateForms&customerId="+id+"&customerType=C&tableStatus="+tableStatus;
				window.open(url,'name','height=400,width=1000,top=200,left=250');
				return true;
	        }*/
       }  	
function checkboxCheck(ch)
{

var zx=0;
   var flag=0;
   for(i=0;i<ch.length;i++)
	{
		
		if(ch[zx].checked==false)
		{
			flag=0;
		}
		else
		{
			flag=1;
			break;
		}
		zx++;
	}
	
	return flag;
}

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
	<body onunload="closeAllLovCallUnloadBody();closeAllWindowCallUnloadBody();" oncontextmenu="return false" onload="enableAnchor();document.getElementById('LinkCustomerForm').custButton.focus();init_fields();" onclick="parent_disable()">
	<div  align="center" class="opacity" style="display:none" id="processingImage" ></div>
	
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/linkCustomer" styleId="LinkCustomerForm" method="post">
	<input type="hidden" name="hType" id="hType" value="${requestScope.applType}" />
	<input type="hidden" name="hstatus" id="hstatus" value="${requestScope.hstatus}" />
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>" />
	<div class="" style="">
		<span class="">&nbsp;</span>
	   </div>
	<fieldset>	  
	  <legend><bean:message key="lbl.details"/></legend>         
 
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
					
		   <td><bean:message key="lbl.customerID"/></td>
         <td>
            <html:text property="customerId" styleClass="text" styleId="customerId" maxlength="100" readonly="true" value="" tabindex="1"/>
			<html:hidden  property="lbxcustomerId" styleId="lbxcustomerId" value=""/>
			<html:button property="custButton" style="custButton" onclick="closeLovCallonLov1();openLOVCommon(467,'LinkCustomerForm','customerId','','', '','','','customerName')" value=" " styleClass="lovbutton"></html:button>
	       <%--<img onclick="openLOVCommon(119,'LinkCustomerForm','customerId','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif" /> --%> 
	        </td> 	  
         <td><bean:message key="lbl.customerName"/></td>
           <td ><html:text property="customerName" styleClass="text" styleId="customerName" maxlength="100" size="10" value="" onchange="upperMe('customerName');" /></td>
		</tr>
		<tr>
			<td><bean:message key="lbl.LoanNo"/></td>
			<td><html:text property="loanNO" styleClass="text" value="" styleId="loanNO" readonly="true"/>
				<html:hidden property="loanID" styleId="loanID" value="" />
				<input type="hidden" name="loanCust" id="loanCust" value="" />
				<html:button property="loanButton" styleId="loanButton" tabindex="1" onclick="openLOVCommon(306,'LinkCustomerForm','loanNO','','', '','','','loanCust');" value=" " styleClass="lovbutton" ></html:button>
			</td>
			<td><bean:message key="lbl.appFormNo"/></td>
	     	<td><html:text property="appFormNo"  styleClass="text" styleId="appFormNo" value="" maxlength="50" ></html:text></td>
		</tr>
		
	 <tr>
	  
	       <td><bean:message key="lbl.panNo"/></td>
           <td><html:text property="panNo" styleClass="text" styleId="panNo" onchange="return upperMe('panNo');"  value="" style="text-align: right" maxlength="100" size="10" /></td>
           <td><bean:message key="lbl.regNo"/></td>
           <td><html:text property="registrationNo" styleClass="text" value="" styleId="registrationNo" style="text-align: right" maxlength="100" size="10" /></td>  
     </tr>
		<tr>
	  
	       <td><bean:message key="aadhaar" /></td>
	       <td><html:text property="aadhaar" styleClass="text" styleId="aadhaar" onkeypress="return isNumber(event);" value="" style="text-align: right" maxlength="12" size="10" /></td>
     </tr>									 
		<tr>
		<html:hidden property="status" styleId="status" />
		<%-- 
		  <td >
			<bean:message key="lbl.customerStatus"/>  <font color="red">*</font>
		  </td>
		  <td >
		  	<html:select property="status" styleId="status" styleClass="text">
				  <html:option value="G">Global Customer Database</html:option>
				  <html:option value="C">Credit Processing</html:option>
			  </html:select>
		  </td>
		  --%>
		  
		  <td height="47"  >
		  <button type="button" name="button" id="button" class="topformbutton3" title="Alt+C" accesskey="C" onclick="return searchCustomer();"><bean:message key="button.searchcust" /></button></td>
		  </tr>
		
		</table>
		
	   </td></tr>
	
	</table>
	 
	</fieldset>
	
</html:form>
	</div>
<logic:present name="searchedDetail" >
<fieldset>	
		 <legend><bean:message key="lbl.applicants"/></legend>  

  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<tr>
    <td class="gridtd">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
    <tr class="white2">
        <td ><b><bean:message key="lbl.select"/></b></td>
        <td ><b><bean:message key="lbl.customerID"/></b></td>
		<td ><strong><bean:message key="lbl.customerName"/></strong></td>
        <td ><b><bean:message key="lbl.constitution"/></b></td>
        <td ><b><bean:message key="lbl.businessSegment"/></b></td>
        <td ><b><bean:message key="lbl.applicantCategory"/></b></td>
        <td ><b><bean:message key="lbl.LoanNo"/></b></td>
        <td ><b><bean:message key="lbl.appFormNo"/></b></td>
        
	</tr>
	
	 <logic:iterate name="searchedDetail" id="subsearchedDetail">
	     <tr class="white1">
	     
             <td ><input type="radio" id="dealstore" name="dealstore" value="${subsearchedDetail.customerId }" onclick="return goDealStore();"/></td> 
        <td >
        
        <a href="#" id="anchor0" onclick="return linkGCD('${subsearchedDetail.customerId }','${subsearchedDetail.custType }');">${subsearchedDetail.customerId }
	   <input type="hidden" name="cust_id" id="cust_id" value="${subsearchedDetail.customerId }" /></a>
	        </td>
	        
			<td >${subsearchedDetail.customerName }
			<input type="hidden" name="cust_name" id="cust_name" value="${subsearchedDetail.customerName }" />
			</td>
	        
	        <td >${subsearchedDetail.constitution }</td>
	        <td>${subsearchedDetail.businessSegment }</td>
	        <td >${subsearchedDetail.custType}
	        <input type="hidden" name="cust_type" id="cust_type" value="${subsearchedDetail.custType}" />
	        </td>
	        <td >${subsearchedDetail.loanNO}</td>
	        <td >${subsearchedDetail.appFormNo}</td>
          
	     </tr>
	</logic:iterate>
	
 </table>
    </td></tr>	
</table>
      <button type="button" name="button" id="button" class="topformbutton2" title="Alt+S" accesskey="S" onclick="selectCustomer();"><bean:message key="button.select" /></button>

	</fieldset>
</logic:present>
</div>
<logic:present name="procval">

<script type="text/javascript">
  		alert('${procval}');
		javascript:window.close();
</script>


</logic:present>
<logic:present name="approved">
  <script type="text/javascript">
	if('<%=request.getAttribute("approved").toString()%>'=='S')
	{   
		alert('<bean:message key="lbl.recordSuccess" />');
		 top.opener.location.href="showCustomerDetailBehindAction.do";
         javascript:window.close();
	}


<%-- else if('<%=request.getAttribute("approved").toString()%>'=='E')

	{

	
		alert('<bean:message key="msg.DataNotSaved" />');
		 top.opener.location.href="custEntryAction.do";
         javascript:window.close();
	}	--%>
	else{


		alert('<bean:message key="lbl.recordError" />');
		 top.opener.location.href="showCustomerDetailBehindAction.do";
         javascript:window.close();
	}	
</script>
</logic:present>
<logic:present name="sms">
  <script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='E')
	{	   
		alert("<bean:message key="lbl.crDealError" />");
		top.opener.location.href="showCustomerDetailBehindAction.do";
        javascript:window.close();
	}	
</script>
</logic:present>
</body>
</html:html>