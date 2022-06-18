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
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
   <script type="text/javascript" src="<%=path%>/js/cmScript/securitizationScript.js"></script>
   
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
			
				function defaultFocus()
			{
				document.getElementById('sourcingForm').loanButton.focus();
			}
		</script>
		
		
	</head>
	<body onload="enableAnchor();defaultFocus();init_fields();" onclick="parent_disable();" onunload="closeLOVWindow();closeAllLovCallUnloadBody();" >
	<div id="centercolumn">
	<div id="revisedcontainer">
	
	<html:form action="/poolIdMakerProcessAction" method="post" styleId="sourcingForm" >
 

	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	
	   <fieldset>	  
	<legend><bean:message key="lbl.poolIdMaker"/></legend>  
	  
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" /> 
	<input type="hidden" name="poolID" id="poolID" value="<%=request.getAttribute("poolID")%>" /> 
	  <input type="hidden" name="loanID" id="loanID" /> 
	  <input type="hidden" name="loanCustomerID" id="loanCustomerID" />     
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	

	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		
	<tr>	
	     <logic:present name="forAddEdit"> 	
		<td width="25%"><bean:message key="lbl.loanNumber"/><font color="red">*</font></td>
	    <td width="25%">
			<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" tabindex="-1" maxlength="100"  readonly="true" />
			<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(188,'sourcingForm','loanAccNo','','', '','','bringLoanDataForPool','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
		    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" /> 
	   </td>
	    </logic:present>
	    
	      <logic:present name="forPoolIdMaker">
	    	<td width="25%"><bean:message key="lbl.loanNumber"/><font color="red">*</font></td>
	    <td width="25%">
			<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo" tabindex="-1" maxlength="100"  readonly="true" />
			<html:button property="loanButton" styleId="loanButton" onclick="openLOVCommon(183,'sourcingForm','loanAccNo','','', '','','bringLoanDataForPool','customerName');closeLovCallonLov1();" value=" " styleClass="lovbutton"></html:button>
		    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" /> 
	   </td> 
	   </logic:present>
	    <td width="25%"><bean:message key="lbl.customerName"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="customerName" styleId="customerName" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	</tr>
	
	
	<tr>
	    <td width="25%"><bean:message key="lbl.product"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanProduct" styleId="loanProduct" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanScheme"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanScheme" styleId="loanScheme" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanCustType"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanCustomerType" styleId="loanCustomerType" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanCustCate"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanCustomerCtegory" styleId="loanCustomerCtegory" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanCustConstitution"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanCustomerConstituion" styleId="loanCustomerConstituion" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanCustBusSeg"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanCustomerBusinessSeg" styleId="loanCustomerBusinessSeg" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanIndustry"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanIndustry" styleId="loanIndustry" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanSubIndustry"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanSubIndustry" styleId="loanSubIndustry" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanDisbursalDate"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanDisbursalDate" styleId="loanDisbursalDate" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanMaturityDate"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanMaturityDate" styleId="loanMaturityDate" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanTenure"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanTenure" styleId="loanTenure" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanBalanceTenure"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanBalanceTenure" styleId="loanBalanceTenure" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanInstNum"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanInstallmentNum" styleId="loanInstallmentNum" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.LoanAdvEMINum"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanAdvEMINUm" styleId="loanAdvEMINUm" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanInitRate"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanInitRate" styleId="loanInitRate" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanDisbursalStatus"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanDisbursalStatus" styleId="loanDisbursalStatus" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanNPAFlag"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanNPAFlag" styleId="loanNPAFlag" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanDPD"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanDPD" styleId="loanDPD" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	<tr>
	    <td width="25%"><bean:message key="lbl.LoanDPDString"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanDPDString" styleId="loanDPDString" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.LoanAssetCost"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanAssetCost" styleId="loanAssetCost" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanAmount"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanAmount" styleId="loanAmount" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanEmi"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanEMI" styleId="loanEMI" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanAdvEMIAmount"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanAdvEMIAmount" styleId="loanAdvEMIAmount" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanBalPrinc"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanBalPrincipal" styleId="loanBalPrincipal" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanOverduePrin"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanOverduePrincipal" styleId="loanOverduePrincipal" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanRecvdPrinc"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanReceivedPrincipal" styleId="loanReceivedPrincipal" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanOverdueInsNum"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanOverdueInstNo" styleId="loanOverdueInstNo" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanOverdueAmount"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanOverdueAmount" styleId="loanOverdueAmount" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
	
	<tr>
	    <td width="25%"><bean:message key="lbl.loanBalInsNum"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanBalnceInstNo" styleId="loanBalnceInstNo" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>
	   
	   <td width="25%"><bean:message key="lbl.loanBalInstAmount"/></td>
	    <td width="25%">
	       <html:text styleClass="text" property="loanBalInstlAmount" styleId="loanBalInstlAmount" tabindex="-1" maxlength="100" readonly="true"/> 
	   </td>	
	
	</tr>
		</table>
	      </td>
	</tr>

	</table>
	
	<table>
	<tr>
			
	      <td align="left"> 
	       
	      <logic:present name="forPoolIdMaker"> 	
		 <button type="button" name="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="this.disabled='true';return saveLoanInPoolID();"><bean:message key="button.save" /></button>
         </logic:present > 	
	      
	      <logic:present name="forAddEdit"> 	
		 <button type="button" name="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="this.disabled='true';return saveLoanInPoolIDEdit();"><bean:message key="button.save" /></button> 
         </logic:present > 	
          </td>
	      <td></td>
	      <td></td>
	      <td></td>
		</tr>
 
	</table>
	</fieldset>	
	
	 

  </html:form>
</div>

</div>

 <logic:present name="alertMsg">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsg")%>') == 'Y'){
	var poolID = document.getElementById("poolID").value;
	 alert("Data Saved Successfully ");	
 
	 window.opener.location.href="<%=request.getContextPath()%>/poolIdMakerProcessAction.do?method=selectForPool&poolID="+poolID;
	 self.close();
	}
	if(('<%=request.getAttribute("alertMsg")%>') == 'N'){
	var poolID = document.getElementById("poolID").value;
	 alert("Data not Saved Successfully ");	
  window.opener.location.href="<%=request.getContextPath()%>/poolIdMakerProcessAction.do?method=selectForPool&poolID="+poolID;
	 self.close();
	}
	
	</script>
</logic:present>

 <logic:present name="msg">
	<script type="text/javascript">
	if(('<%=request.getAttribute("msg")%>') == 'S'){
		 
		 	var poolID = document.getElementById("poolID").value;		 	
			//alert("<bean:message key="lbl.dataSave" />");
			 alert("Data Saved Successfully ");	
            window.opener.location.href="<%=request.getContextPath()%>/poolIdAddEditProcessAction.do?method=selectForPoolEdit&poolID="+poolID;
	        self.close();
		}
		
		if(('<%=request.getAttribute("msg")%>') == 'E'){
	
		    var poolID = document.getElementById("poolID").value;
			 alert("Data not Saved Successfully ");	
			window.opener.location.href="<%=request.getContextPath()%>/poolIdAddEditProcessAction.do?method=selectForPoolEdit&poolID="+poolID;
	        self.close();
		}
		</script>
</logic:present>

 <logic:present name="alertMsgforSanF">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsgforSanF")%>') == 'Y'){
	 alert("Data forwarded successfully");	
	  
	}
	if(('<%=request.getAttribute("alertMsgforSanF")%>') == 'N'){
	 alert("Data not forwarde ");
	  
	}
	
	</script>
</logic:present>

 <logic:present name="alertMsgfrDel">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsgfrDel")%>') == 'Y'){
	 alert("Data deleted successfully");	
	  
	}
	if(('<%=request.getAttribute("alertMsgfrDel")%>') == 'N'){
	 alert("Data not deleted ");
	  
	}
	
	</script>
</logic:present>









</body>
</html:html>