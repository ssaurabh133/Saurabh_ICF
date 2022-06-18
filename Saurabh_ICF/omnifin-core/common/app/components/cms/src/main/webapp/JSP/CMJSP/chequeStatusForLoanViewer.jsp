<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.Iterator"%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
		<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1"/>
		<meta name="author" content="" />
		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
       <script type="text/javascript" src="<%=request.getContextPath() %>/js/popup.js"></script>
	   
		 
		 <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	      <!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customerDetail.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/search_customer_detail.js"></script>
 
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/lovCommonScript.js"></script>
	 	
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/cmLoanInitjs.js"></script>
   
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
	<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('sourcingForm').loanButton.focus();">
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/chequeStatusAction" method="post" styleId="sourcingForm" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="loanId" id="loanId" value="<%=request.getAttribute("loanId")%>" />
    <fieldset>
 
	<legend><bean:message key="lbl.chequeStatusTracking"/></legend>   
	    
 
	   
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>	
		 	<td><bean:message key="lbl.loanNumber"/></td>
		    <td><html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" maxlength="100"  readonly="true" tabindex="-1" value="${requestScope.loanNumber}"/></td>
            <td><bean:message key="lbl.customerName"/></td>
			<td><html:text styleClass="text" property="customerName" styleId="customerName"  maxlength="100"  readonly="true" tabindex="-1" value="${requestScope.customerName}"/> </td>
	   </tr>
       <tr>  
           <td><bean:message key="lbl.instrumentType"/></td>
	       <td>
	<html:select property="instrumentType" styleId="instrumentType" styleClass="text"   >
	            
		        <html:option value="P" >Payment</html:option>
		        <html:option value="R">Receipt</html:option>
   </html:select> 
          </td>
           
	       <td><bean:message key="lbl.pdc.ecs"/></td>
	       <td>
	<html:select property="pdcFlag" styleId="pdcFlag" styleClass="text" >
				<html:option value="ALL">All</html:option>
		        <html:option value="Y">Yes</html:option>
		        <html:option value="N">No</html:option>
   </html:select> 
          </td>
		</tr>
		
	    <tr>
	     <td><bean:message key="lbl.instrumentNo"/></td>
	    <td nowrap="nowrap" ><label>
	      <html:text styleClass="text" property="instrumentNo" styleId="instrumentNo"  maxlength="20"   onkeypress="return isNumberKey(event);"  />
	    </label></td>
		<td><bean:message key="lbl.receiptNo"/></td>
		<td><html:text property="reciptNo"  styleClass="text" styleId="reciptNo"  value="" maxlength="25" ></html:text></td>
	
  </tr>
		 <tr>
		   <td><bean:message key="lbl.paymentMode"/></td>    
			<td id="P">
	<html:select property="paymentMode" styleId="paymentMode" styleClass="text" >
					<html:option value="ALL">All</html:option>
					<html:option value="Q">Cheque</html:option>
					<html:option value="C">Cash</html:option>
					<html:option value="D">Draft</html:option>
					<html:option value="R">RTGS</html:option>
					<html:option value="N">NEFT</html:option>
					<html:option value="B">Direct Debit</html:option>
					<html:option value="E">ECS</html:option>
		 			<html:option value="S">ADJUSTMENT</html:option>
		       
 
             </html:select>      
              </td>
              
              <td><bean:message key="lbl.noOfRecords"/></td>    
			<td>
	<html:select property="noOfRecords" styleId="noOfRecords" styleClass="text" >
					
					<html:option value="10">10</html:option>
					<html:option value="20">20</html:option>
					<html:option value="30">30</html:option>
					<html:option value="40">40</html:option>
					<html:option value="50">50</html:option>
		       
 
             </html:select>      
              </td>
		 </tr>
		 <tr>
		 	<td><bean:message key="lbl.status"/></td>
		  <td>
		  <div id="arun"  >
		  
		  <html:select property="chequeStatus" styleId="chequeStatus" styleClass="text" >
	
	
					<html:option value="ALL">All</html:option>
					<html:option value="A">Approved</html:option>
					<html:option value="C">Sent To Customer</html:option>
					<html:option value="S">Stop Payment</html:option>
					<html:option value="D">Deposited</html:option>
					<html:option value="R">Realized</html:option>
					<html:option value="B">Bounced</html:option>
					 
					 
               
             </html:select>      
		  </div>
		  <div id="manas" style="display: none">
		  
		  <html:select property="chequeStatus" styleId="chequeStatus" styleClass="text" >
	
	
					<html:option value="ALL">All</html:option>
					<html:option value="A">Approved</html:option>
				 	<html:option value="D">Deposited</html:option>
					<html:option value="R">Realized</html:option>
					<html:option value="B">Bounced</html:option>
					 
               
             </html:select>      
		  </div>
		  
      
              </td>
		 </tr>
	
		 <tr>
<!--			<td align="left" >&nbsp;</td>-->
<!--			<td align="left" >&nbsp;</td>-->
<!--	        <td align="left" >&nbsp;</td>-->
	        <td>  
			    <button type="button"  title="Alt+S" accesskey="S" name="button" class="topformbutton2" onclick="return searchchequeStatusforloanViewer();"><bean:message key="button.search" /></button> 
			</td>
		</tr>
		 	  
		  
	</table>
	</td>
    </tr>
    </table>	 
	</fieldset>	
	
	<logic:present name="dss">
	<script type="text/javascript">
	if('<%=request.getAttribute("dss").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusAction.do?method=searchChequesByPayment";
	document.getElementById('sourcingForm').submit();
	
	}else if('<%=request.getAttribute("dss").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusBehindAction.do?method=searchChequesByPayment";
	document.getElementById('sourcingForm').submit();
	} 
     </script>
	</logic:present>
	
	<logic:present name="DSS">
	<script type="text/javascript">
	if('<%=request.getAttribute("DSS").toString()%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
	document.getElementById('sourcingForm').submit();
	window.close();
	
	}else if('<%=request.getAttribute("DSS").toString()%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	document.getElementById('sourcingForm').action="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
	document.getElementById('sourcingForm').submit();
	window.close();
	} 
     </script>
	</logic:present>
	
</html:form>
	</div>
	
  
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  
  
  <logic:present name="list">
<tr>
    <td >  
   
    <display:table  id="list" class="dataTable"  name="list" style="width: 100%" pagesize="${list[0].noOfRecords}" partialList="true" size="${list[0].totalRecordSize}" cellspacing="1" requestURI="/searchCMBehindAction.do?method=searchChequeStatusForLoanViewer">
    <display:setProperty name="paging.banner.placement"  value="bottom"/>  
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
   
    <display:column property="loanAccNo" titleKey="lbl.LoanNo"  sortable="true"  />
	<display:column property="instrumentNo" titleKey="lbl.instrumentNo"  sortable="true"  />
	<display:column property="date" titleKey="lbl.instrumentDate"  sortable="true"  />
	<display:column property="instrumentAmount" titleKey="lbl.instrumentAmount"  sortable="true"  />
	<display:column property="instrumentType" titleKey="lbl.instrumentType"  sortable="true"  />
	<display:column property="businessPartnerDesc" titleKey="lbl.BPName"  sortable="true"  />
	<display:column property="businessPartnerType" titleKey="lbl.bptype"  sortable="true"  />
	<display:column property="bank" titleKey="lbl.bank"  sortable="true"  />
	<display:column property="branch" titleKey="lbl.branch"  sortable="true"  />	
	<display:column property="bankAccount" titleKey="lbl.acno"  sortable="true"  />
	<display:column property="statusName" titleKey="lbl.status"  sortable="true"  />
	<display:column property="reason" titleKey="lbl.reason_desc"  sortable="true"  />
	<display:column property="valueDate" titleKey="lbl.valueDate"  sortable="true"  />
	<display:column property="reciptNo" titleKey="lbl.receiptNo"  sortable="true"  />
	<display:column property="depositBank" titleKey="lbl.depositBankName"  sortable="true"  />
	<display:column property="depositBankBranch" titleKey="lbl.depositBranchName"  sortable="true"  />
	<display:column property="depositBankAccount" titleKey="lbl.depositBnkAcc"  sortable="true"  />
	<display:column property="authorId" titleKey="lbl.author"  sortable="true"  />
	</display:table>

   
    </td></tr>
    </logic:present>
    
 </table>   

<logic:present name="alertMsg">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
	 alert("Data not found");	
	}	
	</script>
</logic:present>
 		<logic:present name="error">
	<script type="text/javascript">	
		alert('${error}');
		alert("Report can not be Generate.");
	</script>
	</logic:present>

</div>
</body>
</html:html>