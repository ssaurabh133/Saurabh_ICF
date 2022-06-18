<!-- 
Author Name :- Nazia
Date of Creation :3-04-2013
Purpose :-  screen for the legal decline
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.login.roleManager.UserObject"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserObject userobj=(UserObject)session.getAttribute("userobject");
String initiationDate = userobj.getBusinessdate();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html:html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<meta name="author" content="" />
		 
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/popup.js"></script>
	   
		 
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		 <!-- css and jquery for tooltip -->
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.simpletip-1.3.1.js"></script>
			<link type="text/css" href="<%=request.getContextPath() %>/css/toolTip.css" rel="stylesheet" />
	     <!-- css and jquery for tooltip -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/paymentMaker.js"></script>
	    <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>    
	    <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
			<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">	
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('receiptMakerForm').receiptMode.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('receiptMakerForm').loanAccountButton.focus();
     }
     return true;
}
</script>	
	
	<script type="text/javascript">

			function popitup(url) {
	newwindow=window.open(url,'name','height=270,width=700,top=200,left=250');
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
	<body onload="checkChanges('receiptMakerForm');document.getElementById('receiptMakerForm').loanAccountButton.focus();fntab();" onunload="closeAllWindowCallUnloadBody();">
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="forwardLoanID" id="forwardLoanID" value="${forwardLoanID}" />
	<input type="hidden" name="forwardInstrumentID" id="forwardInstrumentID" value="${forwardInstrumentID}" />
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<input type="hidden" name="loanRecStatus" value="${requestScope.loanRecStatus}" id="loanRecStatus" />
	<html:hidden property="receiptNoFlag" styleId="receiptNoFlag" value="${sessionScope.receiptNoFlag}" />
	<input type="hidden" name="loanBranch" value="${requestScope.loanBranchStatus}" id="loanBranch" />
	<div id="centercolumn">
	
	<div id="revisedcontainer">
	
	<html:form action="/receiptMakerProcessAction" method="post" styleId="receiptMakerForm">
	<html:javascript formName="ReceiptMakerDynaValidatorForm"/>
        <html:errors/>
         <logic:present name="savedReceipt">
	 <logic:iterate name="savedReceipt"  id="subReceiptList">	
	 </logic:iterate>
	 </logic:present>
        
	<input type="hidden" name="canForward" value="${requestScope.canForward}" id="canForward" /> 
	<input type="hidden" name="beforeForward" value="" id="beforeForward" />
	<input type="hidden" name="VallocatedAmount" value="" id="VallocatedAmount" />
	<input type="hidden" name="VtdsAmount" value="" id="VtdsAmount" />
	
	
	  <input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
	   <input type="hidden" name="businessDate" id="businessDate" value="<%=initiationDate %>"/>
	   <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	 
	   <fieldset>	  
	<legend><bean:message key="lbl.receiptsMaker"></bean:message></legend>         
	  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center">
		
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		<tr>
		
		 	
		 <logic:present name="savedReceipt">
		 <logic:notPresent name="newCase">
		 <input type="hidden" id="modifyRecord" name="modifyRecord" value="M"/>
		 
		 	<html:hidden property="loanId" styleId="loanId" value="${requestScope.savedReceipt[0].lbxLoanNoHID}" />
<!--			<input type="hidden" value="${requestScope.savedReceipt[0].lbxLoanNoHID}" id="loanId" name="loanId"/>	-->
		   <td><bean:message key="lbl.loanAccount"></bean:message> </td>
		   <td>
		   
		 <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="20"  value="${requestScope.savedReceipt[0].loanAccountNumber}"  style="background-color:#F2F2F2"  readonly="true"/>
        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${requestScope.savedReceipt[0].lbxLoanNoHID}"  />
<html:hidden  property="instrumentID" styleId="instrumentID" value="${requestScope.savedReceipt[0].instrumentID}"  />
        
		  </td>
	<td><bean:message key="lbl.customerName"></bean:message></td>
	<td >
	<a rel="tooltip3" id="tool">
		<html:text property="customerName"  styleClass="text" styleId="customerName" value="${requestScope.savedReceipt[0].customerName}" onmouseover="applyToolTip(id);" onchange="blankcanforward();" maxlength="50"  style="background-color:#F2F2F2"  readonly="true"/>
	</a>
	</td>
		
	  <tr>
		
		   <td>		   
	<bean:message key ="lbl.businessPartnerType"></bean:message>	   
	 </td>
			<td>
			<html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"  value="${requestScope.savedReceipt[0].businessPartnerType}" readonly="true" tabindex="-1" /> 
			<html:hidden  property="lbxBPType" styleId="lbxBPType" value="${requestScope.savedReceipt[0].lbxBPType}" />
			 <html:button property="loanAccountButton" styleId="loanAccountButton" onclick="openLOVCommon(147,'receiptMakerForm','businessPartnerType','loanAccountNumber','lbxBPType', 'lbxLoanNoHID','Select loanAccountNumber','totalRec','lbxBPNID','businessPartnerName');closeLovCallonLov('loanAccountNumber');" value=" " styleClass="lovbutton"></html:button>
           
			<!--<html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"  value="${requestScope.savedReceipt[0].businessPartnerType}"  style="background-color:#F2F2F2"  readonly="true"/>
            <html:hidden  property="lbxBPType" styleId="lbxBPType" value="${requestScope.savedReceipt[0].lbxBPType}"/>-->
            </td>       
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>
	
		<html:text styleClass="text" property="businessPartnerName" styleId="businessPartnerName" maxlength="50"  value="${requestScope.savedReceipt[0].businessPartnerName}"  style="background-color:#F2F2F2"  readonly="true" tabindex="-1"/>
        <html:hidden  property="lbxBPNID" styleId="lbxBPNID" value="${requestScope.savedReceipt[0].lbxBPNID}"/>
      
		    </td>
	 </tr>
	 </logic:notPresent>
	 
	 <!--	  change by sachin-->
<logic:present name="newCase">
		<tr>
		<html:hidden property="loanId" styleId="loanId" value="${requestScope.savedReceipt[0].lbxLoanNoHID}" />
		   <td><bean:message key="lbl.loanAccount"></bean:message><font color="red">*</font></td>
		   <td>
		   
		 <html:text styleClass="text" property="loanAccountNumber" readonly="true" styleId="loanAccountNumber" maxlength="20"  value="${requestScope.savedReceipt[0].loanAccountNumber}" tabindex="-1"/>
        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${requestScope.savedReceipt[0].lbxLoanNoHID}" />
       <html:button property="loanAccountButton" styleId="loanAccountButton" onclick="closeLovCallonLov1();openLOVCommon(265,'receiptMakerForm','loanAccountNumber','','', '','','bpTypeRNull','customerName')" value=" " styleClass="lovbutton"></html:button>
       <html:hidden  property="instrumentID" styleId="instrumentID" value="${requestScope.savedReceipt[0].instrumentID}"  />
      <!--  <img onclick="openLOVCommon(46,'receiptMakerForm','loanAccountNumber','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif" /> -->
          
		  </td>
	<td><bean:message key="lbl.customerName"></bean:message><font color="red">*</font></td>
	<td>
	<a rel="tooltip3" id="tool">
		<html:text property="customerName"  styleClass="text" styleId="customerName" value="${requestScope.savedReceipt[0].customerName}" onmouseover="applyToolTip(id);" maxlength="50" ></html:text>
	</a>
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	</td>
		</tr>
		
		 <tr>
		    <td><bean:message key ="lbl.businessPartnerType"></bean:message><font color="red">*	</font></td>
			<td>
			<html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"  value="${requestScope.savedReceipt[0].businessPartnerType}" readonly="true" tabindex="-1" /> 
			<html:hidden  property="lbxBPType" styleId="lbxBPType" value="${requestScope.savedReceipt[0].lbxBPType}"/>
			 <html:button property="businessPartnerButton" styleId="businessPartnerButton" onclick="openLOVCommon(147,'receiptMakerForm','businessPartnerType','loanAccountNumber','lbxBPType', 'lbxLoanNoHID','Select loanAccountNumber','totalRec','lbxBPNID','businessPartnerName');closeLovCallonLov('loanAccountNumber');" value=" " styleClass="lovbutton"></html:button>
             <!--     <img onclick="openLOVCommon(147,'receiptMakerForm','businessPartnerType','lbxLoanNoHID','lbxBPType', 'loanAccountNumber','Select loanAccountNumber','','lbxBPNID','businessPartnerName')" src="<%= request.getContextPath()%>/images/lov.gif"/>   --> 
		   
          
		   
		    </td>    
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>
	
		 <html:text styleClass="text" property="businessPartnerName" styleId="businessPartnerName" maxlength="50"  value="${requestScope.savedReceipt[0].businessPartnerName}" readonly="true" tabindex="-1"/>
         <html:hidden  property="lbxBPNID" styleId="lbxBPNID" value="${requestScope.savedReceipt[0].lbxBPNID}" />        
		  </td>
		 </tr>
		
		</logic:present>

<!--end by sachin-->
	<tr>  
	<td><bean:message key ="lbl.receiptMode"></bean:message><font color="red">*</font> </td>
		 <td><span style="float:left;">
		 <html:select property="receiptMode" styleId="receiptMode" styleClass="text" value="${requestScope.savedReceipt[0].receiptMode}" onchange="fnReceiptNull();cashAccountDisable();blankcanforward();">
		 <html:option value="">--Select--</html:option> 
		  <html:option value="C">Cash</html:option> 
		 <html:option value="Q">Cheque</html:option> 
		 <html:option value="D">DD</html:option> 
		 <html:option value="N">NEFT</html:option> 
		 <html:option value="R">RTGS</html:option>	
		 <html:option value="DIR">Direct Debit</html:option> 	
		 <html:option value="S">Adjustment</html:option>		 		   		            
           </html:select>
           <html:hidden property="lbxreceiptMode" styleId="lbxreceiptMode" value="${requestScope.savedReceipt[0].lbxreceiptMode}"/>
<!--           <input type="hidden"  name="lbxreceiptMode" id="lbxreceiptMode" value="${requestScope.savedReceipt[0].lbxreceiptMode}"/>  -->
		 </span></td>
	    <td>
		<bean:message key ="lbl.receiptDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/> <font color="red">*</font></td>
			<td>
			<html:text property="receiptDate" styleClass="text" onchange="blankcanforward();" styleId="receiptDate" value="${requestScope.savedReceipt[0].receiptDate}" maxlength="10" onchange="return checkDate('receiptDate');"></html:text> 
	</td>
		 
			
		</tr>
		<tr>
			
			  <td><bean:message key="lbl.instrumentNumber"></bean:message><font color="red">*</font>  </td>
			   <logic:present name="savedReceipt">
	           <logic:iterate name="savedReceipt"  id="subReceiptList">
               <logic:equal name="subReceiptList"  property="receiptMode" value="C">	 
			  <td>        
			 <html:text property="instrumentNumber" onchange="blankcanforward();" styleClass="text" styleId="instrumentNumber" disabled="true" value="${requestScope.savedReceipt[0].instrumentNumber}" maxlength="20"></html:text>
		     </td>
			 </logic:equal>
            <logic:equal name="subReceiptList"  property="receiptMode" value="S">	 
	           <td>  
			 <html:text property="instrumentNumber" onchange="blankcanforward();" styleClass="text" styleId="instrumentNumber" disabled="true" value="${requestScope.savedReceipt[0].instrumentNumber}" maxlength="20"></html:text>
		     </td>
			 </logic:equal>
			  <logic:equal name="subReceiptList"  property="receiptMode" value="DIR">	 
	           <td>  
			 <html:text property="instrumentNumber" onchange="blankcanforward();" styleClass="text" styleId="instrumentNumber" disabled="true" value="${requestScope.savedReceipt[0].instrumentNumber}" maxlength="20"></html:text>
		     </td>
			 </logic:equal>
		
			 <logic:notEqual name="subReceiptList"  property="receiptMode" value="C">
             <logic:notEqual name="subReceiptList"  property="receiptMode" value="S">
             <logic:notEqual name="subReceiptList"  property="receiptMode" value="DIR">
             <td>           
			 <html:text property="instrumentNumber" onchange="blankcanforward();" styleClass="text" styleId="instrumentNumber" disabled="false" value="${requestScope.savedReceipt[0].instrumentNumber}" maxlength="20"></html:text>
		     </td>
			   </logic:notEqual>
           </logic:notEqual>
           </logic:notEqual>
           </logic:iterate>
           </logic:present>
			 
		<td><bean:message key="lbl.instrumentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font>  </td>
	   
	    <logic:present name="savedReceipt">
	    <logic:iterate name="savedReceipt"  id="subReceiptList">
        <logic:equal name="subReceiptList"  property="receiptMode" value="C">	 
		 <td>	
		   <div id="disIdInsD" style="display: none;"> 
	          
	    <html:text property="instrumentDate" styleClass="text" onchange="blankcanforward();" styleId="instrumentDate"  value="${requestScope.savedReceipt[0].instrumentDate}" maxlength="10" onchange="return checkDate('instrumentDate');"></html:text>
	     </div>
	      <div id="disIdIns" style="display: inline">
	      <html:text property="instrumentDate" styleClass="text" onchange="blankcanforward();" styleId="instrumentDateDis" readonly="true" value="${requestScope.savedReceipt[0].instrumentDate}" maxlength="10" tabindex="-1" ></html:text>
	     </div>
	   </td>				
		  </logic:equal>
            <logic:equal name="subReceiptList"  property="receiptMode" value="S">	 
	         <td>	
	          <div id="disIdInsD" style="display: none;"> 
	          
	    <html:text property="instrumentDate" styleClass="text" onchange="blankcanforward();" styleId="instrumentDate" value="${requestScope.savedReceipt[0].instrumentDate}" maxlength="100"></html:text>
	     </div>
	      <div id="disIdIns" style="display: inline">
	      <html:text property="instrumentDate" styleClass="text" onchange="blankcanforward();" styleId="instrumentDateDis" readonly="true" value="" maxlength="100" tabindex="-1"></html:text>
	     </div>   
	  
	     </td>				
		  </logic:equal>
		   <logic:equal name="subReceiptList"  property="receiptMode" value="DIR">	 
	         <td>	
	          <div id="disIdInsD" style="display: none;"> 
	          
	    <html:text property="instrumentDate" styleClass="text" onchange="blankcanforward();" styleId="instrumentDate" value="${requestScope.savedReceipt[0].instrumentDate}" maxlength="100"></html:text>
	     </div>
	      <div id="disIdIns" style="display: inline">
	      <html:text property="instrumentDate" styleClass="text" onchange="blankcanforward();" styleId="instrumentDateDis" readonly="true" value="" maxlength="100" tabindex="-1"></html:text>
	     </div>   
	  
	     </td>				
		  </logic:equal>
	     <logic:notEqual name="subReceiptList"  property="receiptMode" value="C">
             <logic:notEqual name="subReceiptList"  property="receiptMode" value="S">
             <logic:notEqual name="subReceiptList"  property="receiptMode" value="DIR">
              <td>	
          
	      <div id="disIdInsD" style="display: inline">  
	      <html:text property="instrumentDate" styleClass="text" onchange="blankcanforward();" styleId="instrumentDate" value="${requestScope.savedReceipt[0].instrumentDate}" maxlength="100"></html:text>
	     </div>
	     <div id="disIdIns" style="display: none;">                
	     <html:text property="instrumentDate" styleClass="text" onchange="blankcanforward();" styleId="instrumentDateDis" readonly="true" value="" maxlength="100"></html:text>
	     </div>
	    </td>	
	   </logic:notEqual>
           </logic:notEqual>
           </logic:notEqual>
           </logic:iterate>
           </logic:present>	
		</tr>
		<tr>
			
			
			  <td>
     <bean:message key="lbl.bank"></bean:message>
	</td>
	 <logic:present name="savedReceipt">
	 <logic:iterate name="savedReceipt"  id="subReceiptList">
     <logic:equal name="subReceiptList"  property="receiptMode" value="C">	 
	
	<td> 
	 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100"  tabindex="-1"  value="${requestScope.savedReceipt[0].bank}" readonly="true" />
	   <div id="disId" style="display: none;"> 
       <html:hidden  property="lbxBankID" styleId="lbxBankID" disabled="true" value="${requestScope.savedReceipt[0].lbxBankID}" />
       <html:button property="loanBankButton" styleId="loanBankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'receiptMakerForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton" onchange="blankcanforward();"></html:button>
        </div>
     </td>
    </logic:equal>
    <logic:equal name="subReceiptList"  property="receiptMode" value="S">	 
	
	<td> 
	   <html:text styleClass="text" property="bank"  styleId="bank" maxlength="100"  tabindex="-1"  value="${requestScope.savedReceipt[0].bank}" readonly="true" />
	   <div id="disId" style="display: none;"> 
       <html:hidden  property="lbxBankID" styleId="lbxBankID" disabled="true" value="${requestScope.savedReceipt[0].lbxBankID}" />
       <html:button property="loanBankButton" styleId="loanBankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'receiptMakerForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton" onchange="blankcanforward();"></html:button>
        </div>
     </td>
    
    </logic:equal>
    <logic:equal name="subReceiptList"  property="receiptMode" value="DIR">	 
	
	<td> 
	   <html:text styleClass="text" property="bank"  styleId="bank" maxlength="100"  tabindex="-1"  value="${requestScope.savedReceipt[0].bank}" readonly="true" />
	   <div id="disId" style="display: none;"> 
       <html:hidden  property="lbxBankID" styleId="lbxBankID" disabled="true" value="${requestScope.savedReceipt[0].lbxBankID}" />
       <html:button property="loanBankButton" styleId="loanBankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'receiptMakerForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton" onchange="blankcanforward();"></html:button>
        </div>
     </td>
    
    </logic:equal>
           <logic:notEqual name="subReceiptList"  property="receiptMode" value="C">
           <logic:notEqual name="subReceiptList"  property="receiptMode" value="S">
            <logic:notEqual name="subReceiptList"  property="receiptMode" value="DIR">
           <td>
             
             <html:text styleClass="text" property="bank" styleId="bank" maxlength="100"  tabindex="-1"  value="${requestScope.savedReceipt[0].bank}" readonly="true" />
             <html:hidden  property="lbxBankID" styleId="lbxBankID"  disabled="false" value="${requestScope.savedReceipt[0].lbxBankID}" />
            <div id="disId" style="display:inline; ">
             <html:button property="loanBankButton" styleId="loanBankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'receiptMakerForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton" onchange="blankcanforward();"></html:button>
             <!-- <img onclick="openLOVCommon(7,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
             </div>
              
       </td>
           </logic:notEqual>
           </logic:notEqual>
           </logic:notEqual>
           </logic:iterate>
           </logic:present>
           
		<td><bean:message key="lbl.branch"></bean:message></td>
		
		<logic:present name="savedReceipt">
	   <logic:iterate name="savedReceipt"  id="subReceipt">
     <logic:equal name="subReceipt"  property="receiptMode" value="C">	 
	
	     <td> 
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  disabled="true" tabindex="-1" value="${requestScope.savedReceipt[0].branch}" readonly="true"/>
  		  <div id="disIdBranch" style="display: none;"> 
  		 <html:hidden  property="lbxBranchID" styleId="lbxBranchID" disabled="true" value="${requestScope.savedReceipt[0].lbxBranchID}"/>
  		 <html:button property="loanBranchButton" styleId="loanBranchButton" onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');" value=" " styleClass="lovbutton" onchange="blankcanforward();"></html:button>
   		 <!--    <img onclick="openLOVCommon(50,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
  	      </div>
  	       </td>
  	       
  	      </logic:equal>
  	      <logic:equal name="subReceipt"  property="receiptMode" value="DIR">	 
	
	     <td> 
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  disabled="true" tabindex="-1" value="${requestScope.savedReceipt[0].branch}" readonly="true"/>
  		  <div id="disIdBranch" style="display: none;"> 
  		 <html:hidden  property="lbxBranchID" styleId="lbxBranchID" disabled="true" value="${requestScope.savedReceipt[0].lbxBranchID}"/>
  		 <html:button property="loanBranchButton" styleId="loanBranchButton" onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');" value=" " styleClass="lovbutton" onchange="blankcanforward();"></html:button>
   		 <!--    <img onclick="openLOVCommon(50,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
  	      </div>
  	       </td>
  	       
  	      </logic:equal>
         <logic:equal name="subReceipt"  property="receiptMode" value="S">	 
	     <td> 	
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  tabindex="-1" value="${requestScope.savedReceipt[0].branch}" readonly="true"/>
  		  <div id="disIdBranch" style="display: none;"> 
  		 <html:hidden  property="lbxBranchID" styleId="lbxBranchID"  disabled="true" value="${requestScope.savedReceipt[0].lbxBranchID}"/>
  		 <html:button property="loanBranchButton" styleId="loanBranchButton" onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');" value=" " styleClass="lovbutton" onchange="blankcanforward();"></html:button>
   		 <!--    <img onclick="openLOVCommon(50,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
  	      </div>
  	   </td>
  	  	   
  	    </logic:equal>
  	    
  	     <logic:equal name="subReceipt"  property="receiptMode" value="N">	 
		     <td> 	
			 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  tabindex="-1" value="${requestScope.savedReceipt[0].branch}" readonly="true"/>
	  		  <div id="disIdBranch" style="display: none;"> 
	  		 <html:hidden  property="lbxBranchID" styleId="lbxBranchID" disabled="true" value="${requestScope.savedReceipt[0].lbxBranchID}"/>
	  		 <html:button property="loanBranchButton" styleId="loanBranchButton" onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');" value=" " styleClass="lovbutton" onchange="blankcanforward();"></html:button>
	   		 <!--    <img onclick="openLOVCommon(50,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
	  	      </div>
	  	   </td>
  	     </logic:equal>
  	      <logic:equal name="subReceipt"  property="receiptMode" value="R">	 
		     <td> 	
			 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  tabindex="-1" value="${requestScope.savedReceipt[0].branch}" readonly="true"/>
	  		  <div id="disIdBranch" style="display: none;"> 
	  		 <html:hidden  property="lbxBranchID" styleId="lbxBranchID" disabled="true" value="${requestScope.savedReceipt[0].lbxBranchID}"/>
	  		 <html:button property="loanBranchButton" styleId="loanBranchButton" onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');" value=" " styleClass="lovbutton" onchange="blankcanforward();"></html:button>
	   		 <!--    <img onclick="openLOVCommon(50,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
	  	      </div>
	  	   </td>
  	     </logic:equal>
           <logic:notEqual name="subReceipt"  property="receiptMode" value="C">
           <logic:notEqual name="subReceipt"  property="receiptMode" value="S">
           <logic:notEqual name="subReceipt"  property="receiptMode" value="N">
            <logic:notEqual name="subReceipt"  property="receiptMode" value="R">
             <logic:notEqual name="subReceipt"  property="receiptMode" value="DIR">
	           <td>
	            <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  tabindex="-1" value="${requestScope.savedReceipt[0].branch}" readonly="true"/>
	    		<html:hidden  property="lbxBranchID" styleId="lbxBranchID" disabled="false" value="${requestScope.savedReceipt[0].lbxBranchID}"/>
	  		   <div id="disIdBranch" style="display:inline;"> 
	  		   <html:button property="loanBranchButton" styleId="loanBranchButton" onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');" value=" " styleClass="lovbutton" onchange="blankcanforward();"></html:button>
	   		 <!--    <img onclick="openLOVCommon(50,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
	  	      </div>
	  	       </td>
	  	       </logic:notEqual>
  	         </logic:notEqual>
           </logic:notEqual>
           </logic:notEqual>
            </logic:notEqual>
           </logic:iterate>
           </logic:present>
		</tr> 
	<tr>	
	
	<td><bean:message key="lbl.micr"></bean:message></td>
	<td>
	<html:text property="micr" styleClass="text" styleId="micr"  maxlength="20" value="${requestScope.savedReceipt[0].micr}" readonly="true" tabindex="-1"></html:text>
	</td>
		  <td>
		   <bean:message key="lbl.ifsCode"></bean:message>
	 </td>
			 <td><div style="float:left;">
			 <html:text property="ifsCode" styleId="ifsCode" styleClass="text"  maxlength="20" value="${requestScope.savedReceipt[0].ifsCode}" readonly="true" tabindex="-1"></html:text>		
	         </div></td> 
	         </tr>
	         <tr>
	         
	         
	         <td><bean:message key="lbl.bankAccount"/><font color="red">*</font>
	       <input type="hidden" id="lbxbankAccountID"/></td>
	        <logic:present name="savedReceipt">
	     <logic:iterate name="savedReceipt"  id="subReceiptList">
         <logic:equal name="subReceiptList"  property="receiptMode" value="C">	
          <td>
         <html:text styleClass="text" property="bankAccount" styleId="bankAccount" disabled="true" value="${requestScope.savedReceipt[0].bankAccount}" onchange="blankcanforward();" maxlength="20"  />
        </td>
	       
       </logic:equal>
       <logic:equal name="subReceiptList"  property="receiptMode" value="DIR">	
          <td>
         <html:text styleClass="text" property="bankAccount" styleId="bankAccount" disabled="true" value="${requestScope.savedReceipt[0].bankAccount}" onchange="blankcanforward();" maxlength="20"  />
        </td>
	       
       </logic:equal>
         <logic:equal name="subReceiptList"  property="receiptMode" value="S">	   
         <td > 
	    
	     <html:text styleClass="text" property="bankAccount" styleId="bankAccount" disabled="true" value="${requestScope.savedReceipt[0].bankAccount}" onchange="blankcanforward();" maxlength="20"  />
	     
	     </td>
	      </logic:equal>
	       <logic:equal name="subReceiptList"  property="receiptMode" value="R">	   
         <td >  
	    
	     <html:text styleClass="text" property="bankAccount" styleId="bankAccount" disabled="true" value="${requestScope.savedReceipt[0].bankAccount}" onchange="blankcanforward();" maxlength="20"  />
	     
	     </td>
	      </logic:equal>
	        <logic:notEqual name="subReceipt"  property="receiptMode" value="C">
	        <logic:notEqual name="subReceipt"  property="receiptMode" value="DIR">
           <logic:notEqual name="subReceipt"  property="receiptMode" value="S">
             <logic:notEqual name="subReceipt"  property="receiptMode" value="R">
           
           <td>          
	     <html:text styleClass="text" property="bankAccount" styleId="bankAccount" disabled="false" value="${requestScope.savedReceipt[0].bankAccount}" onchange="blankcanforward();" maxlength="20"  />
	       </td>
         </logic:notEqual>
         </logic:notEqual>
         </logic:notEqual>
         </logic:notEqual>
         </logic:iterate>
         </logic:present>
         <td><bean:message key="lbl.receiptAmount"></bean:message><font color="red">*</font> </td>
			 <td><div style="float:left;">
			 <html:text property="receiptAmount" styleClass="text" styleId="receiptAmount" value="${requestScope.savedReceipt[0].receiptAmount}" 
			 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" onchange="blankcanforward();" style="text-align: right"></html:text>
			 
			 
			 <html:hidden  property="txnAdvicedID" styleId="txnAdvicedID"  value="${requestScope.savedReceipt[0].txnAdvicedID}" />
			 <html:hidden  property="hidPmntId" styleId="hidPmntId" value="${requestScope.savedReceipt[0].hidPmntId}" />
<!--			  <input type="hidden" name="instrumentID" id="instrumentID" value="${requestScope.savedReceipt[0].instrumentID}" />-->
			  <html:hidden property="instrumentID" styleId="instrumentID" value="${requestScope.savedReceipt[0].instrumentID}" style="text-align: right"/>
			 </div></td>
	        </tr>
		    <tr>
		     <td><bean:message key="lbl.tdsAmount"></bean:message> </td>
	        <td> <html:text property="tdsAmount" styleId="tdsAmount" styleClass="text" value="${requestScope.savedReceipt[0].tdsAmount}" 
             onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" onchange="blankcanforward();" style="text-align: right"></html:text>		
	        </td>
	        <td><bean:message key="lbl.receiptNo" ></bean:message></td>
	        <td>
	        <html:text property="receiptNo" styleClass="text" styleId="receiptNo" value="${requestScope.savedReceipt[0].receiptNo}" onchange="blankcanforward();" maxlength="20" ></html:text>
	        </td>	       
	         </tr>
	        <tr>
		    	<td><bean:message key="lbl.totalRecevable"></bean:message> </td>
	        	<td>	        	
	        	<html:text styleClass="text" property="totalRecevable" styleId="totalRecevable" readonly="true"  value="${amount}" style="text-align: right"/>
	        	</td>
	        	<td ><bean:message key="lbl.recevablePurpose"></bean:message> </td>
	        	<td >
						<html:select property="purpose" styleId="purpose"
							styleClass="text" value="${requestScope.savedReceipt[0].purpose}">
							<option value="">
								--
								<bean:message key="lbl.select" />
								--
							</option>
								<logic:notEmpty name="purposeList">
							<html:optionsCollection name="purposeList"
								label="receiptPurposeDesc" value="receiptPurposeValue" />
								</logic:notEmpty>
						</html:select>
				</td>
	        </tr>
	          <tr>
	         <td><bean:message key="lbl.makerRemark"></bean:message></td>
	         <td>
	         <textarea name="remarks" id="remarks" maxlength="1000" class="text" onchange="blankcanforward();">${requestScope.savedReceipt[0].remarks}</textarea>
	        </td>
	        
	        <td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" disabled="true"
			value="${requestScope.savedReceipt[0].authorRemarks}" onchange="blankcanforward();" /></td>
	        </tr>
	        <tr>
	        <td><bean:message key="lbl.transactionRef"></bean:message> </td>
	        <td><input type="text" name="transactionRefNo" id="transactionRefNo" value="${requestScope.savedReceipt[0].transactionRefNo}" style="text-align: left"/></td>
			</tr>
	        
		 </logic:present>
		 </tr>
		 <logic:present name="datatoapproveList">
		   <input type="hidden" id="modifyRecord" name="modifyRecord" value="V"/>
		   <html:hidden property="loanId" styleId="loanId" value="${sessionScope.datatoapproveList[0].lbxLoanNoHID}"/>
<!--				<input type="hidden" value="${sessionScope.datatoapproveList[0].lbxLoanNoHID}" id="loanId" name="loanId"/>	-->
		   <td><bean:message key="lbl.loanAccount"></bean:message> </td>
		   <td>
		   
		 <html:text styleClass="text" property="loanAccountNumber" styleId="loanAccountNumber" maxlength="100"  value="${sessionScope.datatoapproveList[0].loanAccountNumber}" readonly="true" disabled="true"/>
        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${sessionScope.datatoapproveList[0].lbxLoanNoHID}"  />
    
		  </td>
	<td><bean:message key="lbl.customerName"></bean:message></td>
	<td >
	<a rel="tooltip3" id="tool">
		<html:text property="customerName"  styleClass="text" styleId="customerName" value="${sessionScope.datatoapproveList[0].customerName}" onmouseover="applyToolTip(id);" maxlength="100" disabled="true"></html:text>
	</a>
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	</td>
		</tr>
	  <tr>
		
		   <td>		   
	<bean:message key ="lbl.businessPartnerType"></bean:message>	   
	 </td>
		<td><html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="100"  value="${sessionScope.datatoapproveList[0].businessPartnerType}" disabled="true"/>
       <html:hidden  property="lbxBPType" styleId="lbxBPType" value="${sessionScope.datatoapproveList[0].lbxBPType}"/></td>     
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>
	
		<html:text styleClass="text" property="businessPartnerName" styleId="businessPartnerName" maxlength="100"  value="${sessionScope.datatoapproveList[0].businessPartnerName}" disabled="true"/>
       <html:hidden  property="lbxBPNID" styleId="lbxBPNID" value="${sessionScope.datatoapproveList[0].lbxBPNID}"/>
     
		    </td>
	 </tr>
	<tr>  
	<td><bean:message key ="lbl.receiptMode"></bean:message> </td>
		 <td><span style="float:left;">
		 <html:select property="receiptMode" styleId="receiptMode" styleClass="text" value="${sessionScope.datatoapproveList[0].receiptMode}" disabled="true">
		 <html:option value="">--Select--</html:option> 
		  <html:option value="C">Cash</html:option> 
		 <html:option value="Q">Cheque</html:option> 
		 <html:option value="D">DD</html:option> 
		 <html:option value="N">NEFT</html:option> 
		 <html:option value="R">RTGS</html:option>	
		 <html:option value="DIR">Direct Debit</html:option>    
		  <html:option value="S">Adjustment</html:option>	          
           </html:select>
		 </span></td>
	    <td>
		<bean:message key ="lbl.receiptDate"></bean:message>  <bean:message key="lbl.dateFormat(dd-mm-yyyy)"/> 
	 </td>
			<td>
			<html:text property="receiptDate" styleClass="text" styleId="receiptAmount" value="${sessionScope.datatoapproveList[0].receiptDate}" maxlength="100" disabled="true" readonly="true"></html:text> 
	</td>
		 
			
		</tr>
		<tr>
		
			 <td><bean:message key="lbl.instrumentNumber"></bean:message> </td>
			 <td><div style="float:left;">
			 <html:text property="instrumentNumber" styleClass="text" styleId="instrumentNumber" value="${sessionScope.datatoapproveList[0].instrumentNumber}" maxlength="100" disabled="true"></html:text>
			 </div></td>
			 <td><bean:message key="lbl.instrumentDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/> </td>
	    <td><html:text property="instrumentDate" styleClass="text" styleId="receiptAmount" value="${sessionScope.datatoapproveList[0].instrumentDate}" maxlength="100" disabled="true"></html:text>
	  </td>				
		  
	    
		</tr>
		<tr>
			 
		  <td>
		   <bean:message key="lbl.bank"></bean:message>
	    </td>
			 <td><div style="float:left;">
			 
			 
	 <html:text styleClass="text" property="bank" styleId="bank" maxlength="50"  value="${sessionScope.datatoapproveList[0].bank}" disabled="true"/>
    <html:hidden  property="lbxBankID" styleId="lbxBankID" value="${sessionScope.datatoapproveList[0].lbxBankID}"/>
    
        	  
			 </div></td>
			<td><bean:message key="lbl.branch"></bean:message></td>
		<td>
		
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  value="${sessionScope.datatoapproveList[0].branch}" disabled="true"/>
   <html:hidden  property="lbxBranchID" styleId="lbxBranchID" value="${sessionScope.datatoapproveList[0].lbxBranchID}"/>


		</td> 
		
		</tr> 
	<tr>	
		 
	<td><bean:message key="lbl.micr"></bean:message></td>
	<td>
	<html:text property="micr" styleClass="text" styleId="micr" value="${sessionScope.datatoapproveList[0].micr}" maxlength="100" disabled="true"></html:text>
	</td>
		         <td>
		   <bean:message key="lbl.ifsCode"></bean:message>
	 </td>
			 <td><div style="float:left;">
			 <html:text property="ifsCode" styleId="ifsCode" styleClass="text" value="${sessionScope.datatoapproveList[0].ifsCode}" maxlength="100" disabled="true"></html:text>		
	         </div></td>
	         </tr>
	         <tr>
	   
	   
	    <td><bean:message key="lbl.bankAccount"></bean:message></td>
			<td>  <html:text styleClass="text" property="bankAccount" styleId="bankAccount" maxlength="50"  value="${sessionScope.datatoapproveList[0].bankAccount}" disabled="true"/>
			</td>
		  
	   
	         	<td><bean:message key="lbl.receiptAmount"></bean:message> </td>
			 <td><div style="float:left;">
			 <html:text property="receiptAmount" styleClass="text" styleId="receiptAmount" value="${sessionScope.datatoapproveList[0].receiptAmount}" maxlength="100" disabled="true" style="text-align: right"></html:text>
			 <html:hidden  property="txnAdvicedID" styleId="txnAdvicedID"  />
			 </div></td>
	            
	        </tr>
		    <tr>
		    <td><bean:message key="lbl.tdsAmount"></bean:message> </td>
	        <td> <html:text property="tdsAmount" styleId="tdsAmount" styleClass="text" value="${sessionScope.datatoapproveList[0].tdsAmount}" maxlength="18" disabled="true" style="text-align: right"></html:text>		
	        </td>
	        <td><bean:message key="lbl.receiptNo" ></bean:message></td>
	        <td>
	        <html:text property="receiptNo" styleClass="text" styleId="receiptNo" value="${sessionScope.datatoapproveList[0].receiptNo}" maxlength="20" readonly="true"></html:text>
	        </td>	     
	         </tr>
	         
	        <tr>
		    	<td><bean:message key="lbl.totalRecevable"></bean:message> </td>
	        	<td>	        	
	        	<html:text styleClass="text" property="totalRecevable" styleId="totalRecevable" readonly="true"  value="${sessionScope.totalRecevableAmount}" style="text-align: right"/>
	        	</td>
	        	<td ><bean:message key="lbl.recevablePurpose"></bean:message> </td>
	        	<td>
	        	<html:text styleClass="text" property="purpose" styleId="purpose" readonly="true"  value="${sessionScope.datatoapproveList[0].purpose}" style="text-align: right"/>
	        	</td>
	        </tr>
	         
	         <tr>
	         <td><bean:message key="lbl.makerRemark"></bean:message></td>
	         <td>
	         <textarea name="remarks" id="remarks" disabled="disabled" class="text">${sessionScope.datatoapproveList[0].remarks}</textarea>
	        </td>
	        
	        <td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" disabled="true"
			value="${requestScope.savedReceipt[0].authorRemarks}" /></td>
			
	        </tr>
	       	<tr>
	        <td><bean:message key="lbl.transactionRef"></bean:message> </td>
	        <td><input type="text" name="transactionRefNo" id="transactionRefNo" readonly="readonly" value="${sessionScope.datatoapproveList[0].transactionRefNo}" style="text-align: left"/></td>
			</tr>
	      
		 </logic:present>
	
		<logic:notPresent name="savedReceipt">
		<logic:notPresent name="datatoapproveList">
		<input type="hidden" id="modifyRecord" name="modifyRecord" value="I"/>
		<tr>
		<input type="hidden" value='${requestScope.laonId}' id="loanId" name="loanId"/>	
		   <td><bean:message key="lbl.loanAccount"></bean:message><font color="red">*</font></td>
		   <td>
		   
		 <html:text styleClass="text" property="loanAccountNumber" readonly="true" styleId="loanAccountNumber" maxlength="20"  value="" tabindex="-1"/>
        <html:hidden  property="lbxLoanNoHID" styleId="lbxLoanNoHID"  />
       <html:button property="loanAccountButton" styleId="loanAccountButton" onclick="closeLovCallonLov1();openLOVCommon(265,'receiptMakerForm','loanAccountNumber','','', '','','bpTypeRNull','customerName')" value=" " styleClass="lovbutton"></html:button>
      <!--  <img onclick="openLOVCommon(46,'receiptMakerForm','loanAccountNumber','','', '','','','customerName')" src="<%= request.getContextPath()%>/images/lov.gif" /> -->
          
		  </td>
	<td><bean:message key="lbl.customerName"></bean:message><font color="red">*</font></td>
	<td>
	<a rel="tooltip3" id="tool">
		<html:text property="customerName"  styleClass="text" styleId="customerName" value="" onmouseover="applyToolTip(id);" maxlength="50" ></html:text>
	</a>
	<input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
	</td>
		</tr>
	  
	  <tr>
		    <td><bean:message key ="lbl.businessPartnerType"></bean:message><font color="red">*	</font></td>
			<td>
			<html:text styleClass="text" property="businessPartnerType" styleId="businessPartnerType" maxlength="50"  value="" readonly="true" tabindex="-1" /> 
			<html:hidden  property="lbxBPType" styleId="lbxBPType" />
			 <html:button property="businessPartnerButton" styleId="businessPartnerButton" onclick="openLOVCommon(147,'receiptMakerForm','businessPartnerType','loanAccountNumber','lbxBPType', 'lbxLoanNoHID','Select loanAccountNumber','totalRec','lbxBPNID','businessPartnerName');closeLovCallonLov('loanAccountNumber');" value=" " styleClass="lovbutton"></html:button>
             <!--     <img onclick="openLOVCommon(147,'receiptMakerForm','businessPartnerType','lbxLoanNoHID','lbxBPType', 'loanAccountNumber','Select loanAccountNumber','','lbxBPNID','businessPartnerName')" src="<%= request.getContextPath()%>/images/lov.gif"/>   --> 
		   
          
		   
		    </td>    
			
			<td><bean:message key="lbl.businessPartnerName"></bean:message> </td>
			<td>
	
		 <html:text styleClass="text" property="businessPartnerName" styleId="businessPartnerName" maxlength="50"  value="" readonly="true" tabindex="-1"/>
         <html:hidden  property="lbxBPNID" styleId="lbxBPNID" />        
		  </td>
		 </tr>
	<td><bean:message key ="lbl.receiptMode"></bean:message> <font color="red">*</font></td>
		 <td><span style="float:left;">
		 <html:select property="receiptMode" styleId="receiptMode" styleClass="text" onchange="fnReceiptNull();cashAccountDisable()">
		 <html:option value="">--Select--</html:option> 
		  <html:option value="C">Cash</html:option> 
		 <html:option value="Q">Cheque</html:option> 
		 <html:option value="D">DD</html:option> 
		 <html:option value="N">NEFT</html:option> 
		 <html:option value="R">RTGS</html:option>
		  <html:option value="DIR">Direct Debit</html:option>	  
		 <html:option value="S">Adjustment</html:option>	            
           </html:select>
           <input type="hidden"  name="lbxreceiptMode" id="lbxreceiptMode"/>
		 </span></td>
		 
	    <td>
		<bean:message key ="lbl.receiptDate"></bean:message><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
			<td>
			<html:text property="receiptDate" styleClass="text" styleId="receiptDate" value="${userobject.businessdate}" maxlength="10" onchange="return checkDate('receiptDate');"></html:text> 
	</td>
		 
			
		</tr>
		<tr>
		
			 <td><bean:message key="lbl.instrumentNumber"></bean:message><font color="red">*</font> </td>
			 <td><div style="float:left;">
			 <html:text property="instrumentNumber" styleClass="text" styleId="instrumentNumber" value="" maxlength="20"></html:text>
			  <input type="hidden" name="instrumentID" id="instrumentID" value="" />
			 </div></td>
			 <td><bean:message key="lbl.instrumentDate"></bean:message>
			 <bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font> </td>
	        <td>
	        <div id="disIdInsD" style="display:inline; ">
	        <html:text property="instrumentDate" styleClass="text" styleId="instrumentDate" value="${userobject.businessdate}" maxlength="10" onchange="return checkDate('instrumentDate');"></html:text>
	        </div> 			
		     <div id="disIdIns" style="display:none; ">
	        <html:text property="instrumentDate" styleClass="text" styleId="instrumentDateDis" readonly="true" value="" maxlength="100"></html:text>
	        </div> 
	        </td>
		</tr>
		<tr>
			 
        
	     
		   <td>
		   <bean:message key="lbl.bank"></bean:message>
	</td>
	
	<td>	 
	 <html:text styleClass="text" property="bank" styleId="bank" maxlength="100"  tabindex="-1" readonly="true" />
     <html:hidden  property="lbxBankID" styleId="lbxBankID"/>
      <div id="disId" style="display:inline; ">
     <html:button property="loanBankButton" styleId="loanBankButton" onclick="closeLovCallonLov1();openLOVCommon(7,'receiptMakerForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" value=" " styleClass="lovbutton"></html:button>
     <!-- <img onclick="openLOVCommon(7,'sourcingForm','bank','','branch', 'lbxBranchID','','','lbxBankID')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
    </div></td>
		<td><bean:message key="lbl.branch"></bean:message></td>
		<td> 
		 <html:text styleClass="text" property="branch" styleId="branch" maxlength="100"  tabindex="-1" readonly="true"/>
  		 <html:hidden  property="lbxBranchID" styleId="lbxBranchID" />
  		  <div id="disIdBranch" style="display:inline; ">
  		 <html:button property="loanBranchButton" styleId="loanBranchButton" onclick="openLOVCommon(50,'receiptMakerForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifsCode');closeLovCallonLov('bank');" value=" " styleClass="lovbutton"></html:button>
   		 <!--    <img onclick="openLOVCommon(50,'sourcingForm','branch','bank','lbxBranchID', 'lbxBankID','Select Bank LOV','','micr','ifscCode')" src="<%= request.getContextPath()%>/images/lov.gif"> -->
  	   </div></td>
		</tr> 
	<tr>	
	
	<td><bean:message key="lbl.micr"></bean:message></td>
	<td>
	<html:text property="micr" styleClass="text" styleId="micr" value="" maxlength="20" readonly="true" tabindex="-1"></html:text>
	</td>
		  <td>
		   <bean:message key="lbl.ifsCode"></bean:message>
	 </td>
			 <td><div style="float:left;">
			 <html:text property="ifsCode" styleId="ifsCode" styleClass="text" value="" maxlength="20" readonly="true" tabindex="-1"></html:text>		
	         </div></td> 
	         </tr>
	         <tr>
	         
	         
	         <td><bean:message key="lbl.bankAccount"/><font color="red">*</font></td>
	     <td nowrap="nowrap" >
	     <input type="hidden" id="lbxbankAccountID"/>
	     <html:text styleClass="text" property="bankAccount" styleId="bankAccount"  maxlength="20"  />
	     </td>
	         
	         	<td><bean:message key="lbl.receiptAmount"></bean:message><font color="red">*</font> </td>
			 <td><div style="float:left;">
			 <html:text property="receiptAmount" styleClass="text" styleId="receiptAmount" value="" 
			 onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			 onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right"></html:text>
			 
			 </div></td>
	          
	        </tr>
		    <tr>
		    <td><bean:message key="lbl.tdsAmount"></bean:message> </td>
	        <td> <html:text property="tdsAmount" styleId="tdsAmount" styleClass="text" value="" 
	        onkeypress="return numbersonly(event,id,18)" onblur="formatNumber(this.value,id);" onkeyup="checkNumber(this.value, event, 18,id);" 
			onfocus="keyUpNumber(this.value, event, 18,id);" style="text-align: right"></html:text>		
	        </td>
	        <td><bean:message key="lbl.receiptNo" ></bean:message></td>
	        <td>
	        <html:text property="receiptNo" styleClass="text" styleId="receiptNo" value="" maxlength="20"></html:text>
	        </td>	    
	         </tr>
	        <tr>
		    	<td><bean:message key="lbl.totalRecevable"></bean:message> </td>
	        	<td><input type="text" name="totalRecevable" id="totalRecevable" readonly="readonly" value="0.00" style="text-align: right"/></td>
	        	<td><bean:message key="lbl.recevablePurpose"></bean:message> </td>
  				<td >
						<html:select property="purpose" styleId="purpose"
							styleClass="text" value="">
							<option value="">
								--
								<bean:message key="lbl.select" />
								--
							</option>
								<logic:notEmpty name="purposeList">
							<html:optionsCollection name="purposeList"
								label="receiptPurposeDesc" value="receiptPurposeValue" />
								</logic:notEmpty>
						</html:select>

				</td>
	        </tr>
	         <tr>
	         <td><bean:message key="lbl.makerRemark"></bean:message></td>
	         <td>
	         <textarea name="remarks" id="remarks" maxlength="1000" class="text"></textarea>      
	        </td>
	        
	        <td nowrap="nowrap"><bean:message key="lbl.authorRemarks" /></td>
			<td nowrap="nowrap"><html:textarea styleClass="text" property="authorRemarks" disabled="true"
			value="${requestScope.savedReceipt[0].authorRemarks}" /></td>
	        </tr>
	        <tr>
	        <td><bean:message key="lbl.transactionRef"></bean:message> </td>
	        <td><input type="text" name="transactionRefNo" id="transactionRefNo"  value="" style="text-align: left"/></td>
			</tr>
	     </logic:notPresent>
		  </logic:notPresent>
	
		
		
		</table>
		</td>
		</tr>
		  <tr>
		   <logic:notPresent name="datatoapproveList">
		   <logic:present name="savedReceipt">
	       <td>
	     <button type="button" id="save" class="topformbutton2" onclick="return onSaveReceipt('<bean:message key="msg.InstDateLessRecp" />','<bean:message key="msg.BussDateLessRec" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
 	     <button type="button"  id="saveForward"  class="topformbutton3" onclick="return onReceiptForwardCheck('<bean:message key="msg.InstDateLessRecp" />','<bean:message key="msg.BussDateLessRec" />','<bean:message key="msg.confirmationForwardMsg" />');" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button>
 		 <button type="button"  type='button' class="topformbutton3" onclick="return viewReceivable('<bean:message key="msg.LoanAccBPType" />');" title="Alt+R" accesskey="R"><bean:message key="button.viewRecievable" /></button>
 		 <button type="button" id="allocateRec" class="topformbutton4" onclick="return allocateReceivable('<bean:message key="msg.plsSaveViewR" />');" title="Alt+A" accesskey="A" ><bean:message key="button.allocreceivable" /></button>
		 <button type="button" id="delete" class="topformbutton2" onclick="return deleteReceipt();" title="Alt+D" accesskey="D"><bean:message key="button.delete" /></button>
		  </td>
		 </logic:present>
         <logic:notPresent name="savedReceipt">
	      <td> 
	    <button type="button" id="save" class="topformbutton2" onclick="return onSaveReceipt('<bean:message key="msg.InstDateLessRecp" />','<bean:message key="msg.BussDateLessRec" />');" title="Alt+V" accesskey="V"><bean:message key="button.save" /></button>
 	     <button type="button"  id="saveForward"  class="topformbutton3" onclick="return onReceiptForwardCheck('<bean:message key="msg.InstDateLessRecp" />','<bean:message key="msg.BussDateLessRec" />','<bean:message key="msg.confirmationForwardMsg" />');" title="Alt+F" accesskey="F"><bean:message key="button.forward" /></button>
 		 <button type="button"  type='button' class="topformbutton3" onclick="return viewReceivable('<bean:message key="msg.LoanAccBPType" />');" title="Alt+R" accesskey="R"><bean:message key="button.viewRecievable" /></button>
 		 <button type="button" id="allocateRec" class="topformbutton4" onclick="return allocateReceivable('<bean:message key="msg.plsSaveViewR" />');" title="Alt+A" accesskey="A" ><bean:message key="button.allocreceivable" /></button>
</td>
		</logic:notPresent>
		</logic:notPresent>
		 </tr>
	     
	
	</table>
	 
	</fieldset>	
	
	
     
  </html:form>
  
  
   <logic:present name="procvalForAuthor">
			<script type="text/javascript">
			if('<%=request.getAttribute("procvalForAuthor")%>'!='NONE')
			{
	   			alert('<%=request.getAttribute("procvalForAuthor").toString()%>');
			}
		</script>
	</logic:present>
	
  	<logic:present name="procval">
			<script type="text/javascript">
			if('<%=request.getAttribute("procval")%>'!='NONE')
			{
	   			alert('<%=request.getAttribute("procval").toString()%>');
			}
		</script>
	</logic:present>
	
<!--change by sachin-->
	<logic:present name="reciptStatusNo">
	<script type="text/javascript">
	if('<%=request.getAttribute("reciptStatusNo").toString()%>'=='RS')
		{
			alert("Recipt number is already exist.");
			
		
		}
	</script>
	</logic:present>
<!--end by sachin	-->

 <logic:present name="msg">
		<script type="text/javascript">
		
		if('<%=request.getAttribute("msg").toString()%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
			
		
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='F')
		{
			var frdLoanID=document.getElementById("forwardLoanID").value;
			var frdInstrumentID=document.getElementById("forwardInstrumentID").value;
			alert("<bean:message key="lbl.forwardSuccess" />");
		    window.location="<%=request.getContextPath()%>/receiptMakerSearch.do?forward=forward&frdLoanID="+frdLoanID+"&frdInstrumentID="+frdInstrumentID;
	      
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='A')
		{
			alert("<bean:message key="lbl.dataSave" />");
		    window.location="<%=request.getContextPath()%>/receiptMakerSearch.do";
	      
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='E')
		{
			alert("<bean:message key="lbl.errorSuccess" />");
			
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='D')
		{
		
		alert("<bean:message key="lbl.instExist" />");
		}
		else if('<%=request.getAttribute("msg").toString()%>'=="NA")
		
		{
		
		alert("Please Check and allocate Receivables");
		}else if('<%=request.getAttribute("msg").toString()%>'=="DS")
		{
			alert("<bean:message key="lbl.dataDeleted" />");
			window.location="<%=request.getContextPath()%>/receiptMakerSearch.do";
		}else if('<%=request.getAttribute("msg").toString()%>'=='DN')
		{
			alert("<bean:message key="lbl.dataNtDeleted" />");
			
		}
		else if('<%=request.getAttribute("msg").toString()%>'=="DA")
		{
			var forwardLoanID=document.getElementById("forwardLoanID").value;
			var forwardInstrumentID=document.getElementById("forwardInstrumentID").value;
			if(confirm("Receipt Amount has not been Allocated.Do you want to proceed")){
		  
		 var basePath=document.getElementById("contextPath").value;		 
		 document.getElementById("receiptMakerForm").action=basePath+"/receiptMakerProcessAction.do?method=onDirectForwardOnReceipt&forwardInstrumentID="+forwardInstrumentID+"&forwardLoanID="+forwardLoanID;
         document.getElementById("receiptMakerForm").submit();
		}
		
		}
		else if('<%=request.getAttribute("msg").toString()%>'=='CHECKDATE')
		{
			alert("<bean:message key="lbl.dateNotEqualReceipt" />");
			
		}
		
		</script>
		</logic:present>	
	
</div>



</div>
<div  align="center" class="opacity" style="display: none;"  id="processingImage">		
</div>
<script>
	setFramevalues("receiptMakerForm");
</script>
</body>
</html:html>