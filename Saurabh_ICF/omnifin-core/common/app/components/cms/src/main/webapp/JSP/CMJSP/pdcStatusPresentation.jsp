<!-- 
Author Name :- Manas
Date of Creation :43-04-2011
Purpose :-  screen for the Payment Maker
-->

<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 



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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>		 
        <script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>

      <script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/cmLoanInitjs.js"></script>
   
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
	<body onclick="parent_disable();" onload="enableAnchor();document.getElementById('sourcingForm').instrumentType.focus();init_fields();">
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
	<html:form action="/holdInstrumentBankingMakerMain" method="post" styleId="sourcingForm" >

	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<logic:notPresent name="author">
	   <fieldset>	  
	   <logic:present name="hold">
	<legend><bean:message key="lbl.holdInstrumentMaker"/></legend>     
     </logic:present>    
     
     <logic:present name="release">
	<legend><bean:message key="lbl.releaseInstrumentMaker"/></legend>     
     </logic:present>  
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	

	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		
		<tr>			
		<td><bean:message key="lbl.loanAccountNumber"/></td>
		
	 <td>
	
	<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo"  maxlength="100"  value="${requestScope.loanNumber}" readonly="true"/>
    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${arryList[0].lbxLoanNoHID}" />
	</td>
	
	<td><bean:message key="lbl.customerName"/></td>
	<td>
	<html:text styleClass="text" property="customerName" styleId="customerName"  maxlength="100" value="${requestScope.customerName}"  readonly="true"/> </td>
	</tr>

	 
		
	  
	  
	     
	</table>
	      </td>
	</tr>
	</table>
	</fieldset>	
	</logic:notPresent>
	 
	<fieldset>	  
	<logic:present name="hold">
	<legend><bean:message key="lbl.holdInstrumentSummary"/></legend>         
	   </logic:present>
	   
	   <logic:present name="release">
	<legend><bean:message key="lbl.releaseInstrumentSummary"/></legend>         
	   </logic:present>
	
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table id="table1" width="100%" border="0" cellspacing="1" cellpadding="4">
      
         
         <logic:present name="check">
        <tr class="white2">
        
          <td><strong><bean:message key="lbl.duedate"/></strong></td>
         <td><strong><bean:message key="lbl.pdcDate"/></strong></td>
         <td><b><bean:message key="lbl.instrumentMode"/></b></td>
          <td><b><bean:message key="lbl.instrumentNo"/></b></td>
          <td><b><bean:message key="lbl.installmentNo"/> </b></td>
          <td><b><bean:message key="lbl.pdcAmount"/> </b></td>
          
          <td><b><bean:message key="lbl.bank" /></b></td>
          <td><b><bean:message key="lbl.branch" /></b></td>
          <td><b><bean:message key="lbl.micr" /></b></td>
          <td><b><bean:message key="lbl.ifscCode" /></b></td>
          <td><b><bean:message key="lbl.bankAccount" /></b></td>
          <td><b><bean:message key="lbl.purpose" /></b></td>
   
          
          <td><b><bean:message key="lbl.ecsTransactionCode" /></b></td>
          <td><b><bean:message key="lbl.customeracType"/></b> </td>
          <td><b><bean:message key="lbl.spnserBnkBrncCode"/> </b></td>
          <td><b><bean:message key="lbl.utilityNo"/> </b></td>
          <!-- Nishant space starts -->
			<td><b><bean:message key="lbl.currentStatus"/> </b></td>
			<td><b><bean:message key="lbl.pdcStatus"/> </b></td>
			<td><b><bean:message key="lbl.reasons"/> </b></td>
			<td>
				<b><bean:message key="lbl.location" /> </b>
			</td>
			<td>
				<b><bean:message key="lbl.clearingType" /> </b>
			</td>
			<td>
				<b><bean:message key="lbl.totalEMI" /> </b>
			</td>
			<td>
				<b><bean:message key="lbl.maker" /> </b>
			</td>
			<td>
				<b><bean:message key="lbl.makerRemarks" /> </b>
			</td>
			<td>
				<b><bean:message key="lbl.makerDate" /> </b>
			</td>
			<td>
				<b><bean:message key="lbl.author" /> </b>
			</td>
			<td>
				<b><bean:message key="lbl.authorRemarks" /> </b>
			</td>
			<td>
				<b><bean:message key="lbl.authorDate" /> </b>
			</td>
          <!-- Nishant space ends -->
          
         
           
          </tr>
      
    
         
         <logic:present name="arrList">
         <logic:iterate id="arrListobj" name="arrList">
         <tr class="white1">
           <td>${arrListobj.date}</td>
         <td>${arrListobj.pdcDate}</td>
         <logic:equal name="arrListobj" property="instrumentType" value="Q">
         <td>PDC</td>
         </logic:equal>
          <logic:equal name="arrListobj" property="instrumentType" value="E">
         <td>ECS</td>
         </logic:equal>
          <logic:equal name="arrListobj" property="instrumentType" value="H">
         <td>ACH</td>
         </logic:equal>
          <td>${arrListobj.instrumentNo}</td>
             <td>${arrListobj.installmentNo}</td>
          <td>${arrListobj.instrumentAmount}</td>
          
           <td>${arrListobj.bank}</td>
          <td>${arrListobj.branch}</td>
          <td>${arrListobj.micr}</td>
          <td>${arrListobj.ifscCode}</td>
          <td>${arrListobj.bankAccount}</td>
          <td>${arrListobj.purpose}</td>
          
           <td>${arrListobj.ecsTransactionCode}</td>
          <td>${arrListobj.customeracType}</td>
          <td>${arrListobj.spnserBnkBrncCode}</td>
          <td>${arrListobj.utilityNo}</td>
          <!-- Nishant space starts -->
			<td>${arrListobj.currentStatus}</td>
			<td>${arrListobj.pdcStatus}</td>
			<td>${arrListobj.reason}</td>  
			<td>${arrListobj.location}</td>
			<td>${arrListobj.clearingType}</td>
			<td>${arrListobj.totalEMI}</td> 
			<td>${arrListobj.maker}</td>
			<td>${arrListobj.remarks}</td>
			<td>${arrListobj.makerDate}</td> 
			<td>${arrListobj.authorName}</td>
			<td>${arrListobj.authorRemarks}</td>
			<td>${arrListobj.authorDate}</td>    
         <!-- Nishant space ends -->
          <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
          <html:hidden property="status" styleId="status" value="${arrListobj.status}"/>
          <html:hidden property="instrumentID" styleId="instrumentID" value="${arrListobj.instrumentID}"/>
          </tr>

         </logic:iterate>
         </logic:present>
       </logic:present>
       
      <logic:present name="notCheck">
        <tr class="white2">
         <td><strong><bean:message key="lbl.date"/></strong></td>
         <td><b><bean:message key="lbl.instrumentMode"/></b></td>
          <td><b><bean:message key="lbl.instrumentNo"/></b></td>
           <td><b><bean:message key="lbl.installmentNo"/> </b></td>
          <td><b><bean:message key="lbl.pdcAmount"/> </b></td>
          <td><b><bean:message key="lbl.bank" /></b></td>
          <td><b><bean:message key="lbl.branch" /></b></td>
          <td><b><bean:message key="lbl.micr" /></b></td>
          <td><b><bean:message key="lbl.ifscCode" /></b></td>
          <td><b><bean:message key="lbl.bankAccount" /></b></td>
          <td><b><bean:message key="lbl.purpose" /></b></td>   
          
          <td><b><bean:message key="lbl.ecsTransactionCode" /></b></td>
          <td><b><bean:message key="lbl.customeracType"/></b> </td>
          <td><b><bean:message key="lbl.spnserBnkBrncCode"/> </b></td>
          <td><b><bean:message key="lbl.utilityNo"/> </b></td>
          
          
          
          <td><b><bean:message key="lbl.holdReason"/></b></td>
          <td><b><bean:message key="lbl.currStatus"/></b></td>
         <td><b><bean:message key="lbl.prevStatus"/></b></td>
         
         
         
         
         
          </tr>
      
    
         
         <logic:present name="arrList">
         <logic:iterate id="arrListobj" name="arrList">
         <tr class="white1">
  
         <td>${arrListobj.date}</td>
         <logic:equal name="arrListobj" property="instrumentType" value="Q">
         <td>PDC</td>
         </logic:equal>
          <logic:equal name="arrListobj" property="instrumentType" value="E">
         <td>ECS</td>
         </logic:equal>
          <logic:equal name="arrListobj" property="instrumentType" value="H">
         <td>ACH</td>
         </logic:equal>
          <td>${arrListobj.instrumentNo}</td>
             <td>${arrListobj.installmentNo}</td>
          <td>${arrListobj.instrumentAmount}</td>
          
           <td>${arrListobj.bank}</td>
          <td>${arrListobj.branch}</td>
          <td>${arrListobj.micr}</td>
          <td>${arrListobj.ifscCode}</td>
          <td>${arrListobj.bankAccount}</td>
          <td>${arrListobj.purpose}</td>
          
           <td>${arrListobj.ecsTransactionCode}</td>
          <td>${arrListobj.customeracType}</td>
          <td>${arrListobj.spnserBnkBrncCode}</td>
          <td>${arrListobj.utilityNo}</td>

         <td>${arrListobj.holdReason}</td>
         
         <logic:equal name="arrListobj" property="toStatus" value="H">
         <td>Hold</td>
         </logic:equal>
          <logic:equal name="arrListobj" property="toStatus" value="A">
         <td>Active</td>
         </logic:equal>
         
         <logic:equal name="arrListobj" property="fromStatus" value="H">
         <td>Hold</td>
         </logic:equal>
          <logic:equal name="arrListobj" property="fromStatus" value="A">
         <td>Active</td>
         </logic:equal>
           
          <html:hidden property="lbxLoanNoHidden" styleId="lbxLoanNoHidden" value="lbxLoanNoHID"/>
            
          </tr>

         </logic:iterate>
         </logic:present>
       </logic:present>
       
   
         
          
          <tr>
          </tr> 
        </table>    </td>
  </tr>

   
</table>

  </fieldset>

 <logic:present name="alertMsgfrsvnfrhold">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsgfrsvnfrhold")%>') == 'Y'){
	 alert("PDC forwarded successfully for hold");
	}
	if(('<%=request.getAttribute("alertMsgfrsvnfrhold")%>') == 'N'){
	 alert("PDC not forwarded successfully for hold");
	}
	
	</script>
	</logic:present>
	
	<logic:present name="alertMsgfrsvnfrrel">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsgfrsvnfrrel")%>') == 'Y'){
	 alert("PDC forwarded successfully for release");
	}
	if(('<%=request.getAttribute("alertMsgfrsvnfrrel")%>') == 'N'){
	 alert("PDC not forwarded successfully for release");
	}
	
	</script>
	</logic:present>
 
 
 
  </html:form>
</div>

</div>
</body>
</html:html>