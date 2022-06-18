
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
	<body onclick="parent_disable();" onload="branchLOV();init_fields();">
		<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
		</div>
	<div id="centercolumn">
	<div id="revisedcontainer">
   <fieldset>	
   <legend><bean:message key="lbl.depositInformation"/></legend>   
    
	    
	<html:form action="/holdInstrumentBankingMakerMain" method="post" styleId="sourcingForm" >
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
				    
	<input type="hidden" name="checkedinstrumentNo" id="checkedinstrumentNo" value="<%=request.getAttribute("checkedinstrumentNo")%>" />
     <input type="hidden" name="checkeddate" id="checkeddate" value="<%=request.getAttribute("checkeddate")%>" />
     <input type="hidden" name="checkedinstrumentAmount" id="checkedinstrumentAmount" value="<%=request.getAttribute("checkedinstrumentAmount")%>" />
     <input type="hidden" name="checkedstatus" id="checkedstatus" value="<%=request.getAttribute("checkedstatus")%>" />  
     <input type="hidden" name="checkedlbxBPTypeHID" id="checkedlbxBPTypeHID" value="<%=request.getAttribute("checkedlbxBPTypeHID")%>" />
     <input type="hidden" name="checkedlbxBPNID" id="checkedlbxBPNID" value="<%=request.getAttribute("checkedlbxBPNID")%>" />  
     <input type="hidden" name="checkedlbxBankID" id="checkedlbxBankID" value="<%=request.getAttribute("checkedlbxBankID")%>" />
     <input type="hidden" name="checkedlbxBranchID" id="checkedlbxBranchID" value="<%=request.getAttribute("checkedlbxBranchID")%>" />  
     <input type="hidden" name="instrumentID" id="instrumentID" value="<%=request.getAttribute("instrumentID")%>" />
     <input type="hidden" name="tdsAmountList" id="tdsAmountList" value="<%=request.getAttribute("tdsAmountList")%>" />  
     <input type="hidden" name="checkedlbxReasonHID" id="checkedlbxReasonHID" value="<%=request.getAttribute("checkedlbxReasonHID")%>" />  
	 <input type="hidden" name="pdcInstrumentIdList" id="pdcInstrumentIdList" value="<%=request.getAttribute("pdcInstrumentIdList")%>"  /> 
	 <input type="hidden" name="mode" id="mode" value="<%=request.getAttribute("mode")%>"  />
	
	<input type="hidden" name="pdcFlag" id="pdcFlag" value="<%=request.getAttribute("pdcFlag")%>" />  
	 <input type="hidden" name="instrumentType" id="instrumentType" value="<%=request.getAttribute("instrumentType")%>"  /> 
	 
	  <input type="hidden" name="checkedvalueDate" id="checkedvalueDate" value="<%=request.getAttribute("checkedvalueDate")%>"  /> 
	<input type="hidden" id="loanRecStatus" name="loanRecStatus" value="${requestScope.loanRecStatus}"/>
	
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 <tr>
	  <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		
		 <tr>			
		<td><bean:message key="lbl.depositBankId"/><font color="red">*</font></td>
		
	 <td>
	
	<html:text styleClass="text" property="bank" styleId="bank" maxlength="100" value="" readonly="true" />
    <html:hidden  property="lbxBankID" styleId="lbxBankID"/>
    
   <%--   
    <img onclick="openLOVCommon(79,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif">
   --%>
    
    <html:button property="bButton" styleId="bButton" onclick="openLOVCommon(79,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>	
    
	</td>
	
	
	<td><bean:message key="lbl.depositBranchId"/></td>
	<td>
	 	<html:text styleClass="text" property="branch" styleId="branch" maxlength="100" value="" readonly="true"/>
     	<html:hidden  property="lbxBranchID" styleId="lbxBranchID" />
     	<div id="cash"><html:button property="bButton" styleId="bButton" onclick="openLOVCommon(361,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode','bankAccount')" value=" " styleClass="lovbutton"></html:button></div>
     	<div id="adjustment"><html:button property="bButton" styleId="bButton" onclick="openLOVCommon(362,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode','bankAccount')" value=" " styleClass="lovbutton"></html:button></div>
     	<div id="other"><html:button property="bButton" styleId="bButton" onclick="openLOVCommon(80,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode','bankAccount')" value=" " styleClass="lovbutton"></html:button></div>
    </td>
	</tr> 
		
		
		
	    <tr>			
		<td><bean:message key="lbl.depositMicrCode"/><font color="red">*</font></td>
		
	 <td>
	
	 <html:text styleClass="text" property="micr" styleId="micr" value="" maxlength="100" readonly="true" /> 
    
	</td>
	
	
	<td><bean:message key="lbl.depositIfscCode"/> </td>
	<td>
	 <html:text styleClass="text" property="ifscCode" styleId="ifscCode"  maxlength="50" value="" readonly="true"/>
	</tr> 
	
		 <tr>			
		<td><bean:message key="lbl.depositBnkAcc"/><font color="red">*</font></td>
		
	 <td>
	
	  <html:text styleClass="text" property="bankAccount" styleId="bankAccount"  maxlength="50" value="" readonly="true" />
    
	</td>
	
	<td></td>
	<td></td>
	
	</tr> 
		
		 <tr>
			
	        <td>  
	        
	         <button type="button"  class="topformbutton2"  title="Alt+V" accesskey="V" id="save" onclick="return saveDepositCheque();"><bean:message key="button.save" /></button>

			</td>
		</tr>
     
	</table>
	</td>
    </tr>
    </table>	 
	
<logic:present name="DSS">
	<script type="text/javascript">
	if('<%=request.getAttribute("DSS")%>'=='S'){
	alert('<bean:message key="lbl.dataSavedSucc"/>');
    opener.location.href="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
	self.close();
	
	}else if('<%=request.getAttribute("DSS")%>'=='N'){
	alert('<bean:message key="lbl.dataNtSavedSucc"/>');
	opener.location.href="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
	self.close();
	} 
     </script>
	</logic:present>
	<logic:present name="PROCVAL">
	<script type="text/javascript">
	if('<%=request.getAttribute("PROCVAL") %>'!='S'){
	alert('<%=request.getAttribute("PROCVAL") %>');
    opener.location.href="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
	self.close();
	
	}
     </script>
	</logic:present>
	<logic:present name="recordExist">
	<script type="text/javascript">
		alert("Selected record is already processed by some other user,Please refresh the page.");
		opener.location.href="<%=request.getContextPath()%>/chequeStatusBehindAction.do";
		self.close();
	</script>
	</logic:present>
</html:form>

</fieldset>	
	</div>

</div>
</body>
</html:html>