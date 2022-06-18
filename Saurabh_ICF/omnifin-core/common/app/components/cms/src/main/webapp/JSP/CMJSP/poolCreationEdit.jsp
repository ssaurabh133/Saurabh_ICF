<!-- 
Author Name :- Manisha Tomar
Date of Creation :18-07-2011
Purpose :-  screen for the Pool Creation
-->
<%@ page language="java"%>
<%@ page session="true"%>

<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ page import="java.util.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


%>


<html>

<head>

<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="" />

 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/poolCreation.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<%
String message=(String)request.getAttribute("message");

%>
<script type="text/javascript">







	</script>

</head>
<body onload="enableAnchor();init_fields();">

<div id="centercolumn">

<div id="revisedcontainer">

<html:form action="/poolCreationProcessAction" method="post" styleId="poolCreationEditForm">

     <fieldset>	 
	<legend><bean:message key="lbl.poolCreationInfo"></bean:message></legend>  
	<input type="hidden" value="<%=request.getAttribute("poolID")%>"  name="poolID" id="poolID"/>
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>

	<div class="" style=""><span class="">&nbsp;</span></div>
  

	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">

			<table width="100%" border="0" cellspacing="1" cellpadding="4" id="gridtable">
		<tr class="white2">
		 <td><b><bean:message key="lbl.select"/></b></td>			
		 <td><b><bean:message key="lbl.loanAccountNumber"/></b></td>
	     <td><b><bean:message key="lbl.customerName"/></b></td>
         <td><b><bean:message key="lbl.customerCategory"/></b></td>
         <td><b><bean:message key="lbl.customerSegment"/></b></td>
         <td><b><bean:message key="lbl.constitution"/></b></td>
       	 <td><b><bean:message key="lbl.industry"/></b></td>		
         <td><b><bean:message key="lbl.product"/></b></td>
         <td><b><bean:message key="lbl.scheme"/></b></td>
		 <td><b><bean:message key="lbl.disbursalDate"/> </b></td>
		 <td><b><bean:message key="lbl.maturityDate"/></b></td>
         <td><b><bean:message key="lbl.loanAmount"/></b></td>
         <td><b><bean:message key="lbl.interestRate"/></b></td>	    
		 <td><b><bean:message key="lbl.installmentAmount"/> </b></td>	       
         <td><b><bean:message key="lbl.totalInstallments"/></b></td>
	     <td><b><bean:message key="lbl.installmentReceived"/></b></td>
		 <td><b><bean:message key="lbl.overDueAmount"/> </b></td>
		 <td><b><bean:message key="lbl.DPD"/></b></td>
         </tr>
				
		<logic:present name="editPoolListGrid">
		
		<logic:iterate name="editPoolListGrid" id="sublist" >							
		<tr class="white1">	
		<td><html:radio property="checkId" styleId="checkId" value="loanAccountNumber" idName="sublist"/></td>
		<td>${sublist.loanAccountNumber}</td>					
	    <td>${sublist.customerName}</td>	
	    <td>${sublist.customerCategory}</td>	
	    <td>${sublist.customerSegment}</td>	
	    <td>${sublist.constitution}</td>	
	    <td>${sublist.industry}</td>	
	   	<td>${sublist.product}</td>	
	    <td>${sublist.scheme}</td>	
	    <td>${sublist.disbursalDate1}</td>	
	    <td>${sublist.maturityDate1}</td>	
	    <td>${sublist.loanAmount1}</td>	
	    <td>${sublist.interestRate1}	</td>	
	    <td>${sublist.installmentAmount1}	   </td>	
	    <td>${sublist.totalInstallments1}</td>	
	    <td>${sublist.installmentReceived1}</td>	
	    <td>${sublist.overDueAmount1}</td>	
	    <td>${sublist.DPD1}</td>	
	    </tr>
		</logic:iterate>					
		</logic:present>
		</table>
		</td>
		</tr>
		</table>
		
		<tr class="white1">			 
		<td><bean:message key="lbl.loanAccount"></bean:message> </td>
	    <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="" />
        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value=""  />        
        </tr>


	
		
		<table width="100%" border="0" cellspacing="1" cellpadding="4"
				id="gridtableButton">
				<tr>
					<td align="left" colspan="6" class="white">
		 
		 <button type="button" tabindex="-1" class="topformbutton2" onclick="removeRowData();" title="Alt+D" accesskey="D"><bean:message key="button.deleterow" /></button>&nbsp;&nbsp;&nbsp;					
	      <button type="button" tabindex="-1" class="topformbutton2" onclick="addRowData('<bean:message key="msg.LoanAccountNo" />');" title="Alt+A" accesskey="A"><bean:message key="button.addrow" /></button>&nbsp;&nbsp;&nbsp; 
	     </td>
				</tr>
	
			</table>
			</td>
		</tr>



	</fieldset>
	<br />
	

</html:form>
<logic:present name="msg">
		<script type="text/javascript">
		
		
		 if('<%=request.getAttribute("msg").toString()%>'=='E')
		  {
			alert("<bean:message key="msg.ValidLoan" />");
			
		}
	
		</script>
		</logic:present>	


		
</div>



</div>
</body>
</html>