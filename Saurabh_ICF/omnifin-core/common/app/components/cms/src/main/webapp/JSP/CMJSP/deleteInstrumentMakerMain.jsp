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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
   		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/instrumentCapturing.js"></script>
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
	<body onclick="parent_disable();" onload="enableAnchor();checkChanges('sourcingForm');document.getElementById('sourcingForm').instrumentType.focus();init_fields();">
	<div  align="center" class="opacity" style="display: none;"  id="processingImage"></div>
	<div id="centercolumn">
	<div id="revisedcontainer">
	<html:form action="/holdInstrumentBankingMakerMain" method="post" styleId="sourcingForm" >

	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	
	<logic:notPresent name="deleteAuthor">
	   <fieldset>	  
	   
	    
	<legend><bean:message key="lbl.deleteInstrumentMaker"/></legend>     
     
    
     
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		
		
		<tr>			
		<td><bean:message key="lbl.loanAccountNumber"/>*</td>
		
	 <td>
	
	<html:text styleClass="text" property="loanAccNo" styleId="loanAccNo"  maxlength="100"  value="${arryList[0].loanAccNo}" readonly="true"/>
    <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" value="${arryList[0].lbxLoanNoHID}" />
	</td>
	
	<td><bean:message key="lbl.customerName"/></td>
	<td>
	<html:text styleClass="text" property="customerName" styleId="customerName"  maxlength="100" value="${arryList[0].customerName}"  readonly="true"/> </td>
	</tr>

	  <tr>
	    <td><bean:message key="lbl.instrumentMode"/></td>
	    <td><label>
	    <html:select property="instrumentType" styleId="instrumentType" styleClass="text">
	            <html:option value="">--Select--</html:option>
		        <html:option value="Q">PDC</html:option>
		        <html:option value="E">ECS</html:option>
		        <html:option value="DIR">Direct Debit</html:option>
		        <html:option value="H">NACH</html:option>
		      </html:select> 
	    </label></td>
		 <td><bean:message key="lbl.instrumentNo"/></td>
	    <td nowrap="nowrap" ><label>
	      <html:text styleClass="text" property="instrumentNo" styleId="instrumentNo"  maxlength="20"  onkeypress="return isNumberKey(event);"  />
	    </label></td>
	  </tr>
		
	  
	  <tr>
	   <td><bean:message key="lbl.installmentDate"/><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/></td>
	    <td nowrap="nowrap" ><label>
	      <html:text styleClass="text" property="date" styleId="date"  maxlength="20" />
	    </label></td>
	    <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      
	      </tr>
	     
	      <tr>
	    <td nowrap="nowrap" ><label>
	    
	    <button type="button" id="save" class="topformbutton2" title="Alt+S" accesskey="S" onclick="return searchIndiDeleteInstrument();"><bean:message key="button.search" /></button>
	  
	    </label></td>
	    <td>&nbsp;</td>
	      <td>&nbsp;</td>
	    </tr>
	   
	</table>
	      </td>
	</tr>
	</table>
	</fieldset>	
	</logic:notPresent>
	
	
	<fieldset>	  

	<legend><bean:message key="lbl.deleteInstrumentSummary"/></legend>         

	
	
    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="gridtd">
      
      <table id="table1" width="100%" border="0" cellspacing="1" cellpadding="4">
      
         
         <logic:present name="deleteMaker">
         
        <tr class="white2">
         <td><input type="checkbox" name="chkd" id="allchkd"  onclick="allChecked();" /></td>
         
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
          
          
          
          
          <td><b><bean:message key="lbl.deleteReason"/></b>
          <%-- Manisha changes for copy to all remarks start here --%>
          <button type="button" class="topformbutton3" onclick="copyToAll();"><bean:message key="lbl.copyToAll" /></button>
           <%-- Manisha changes for copy to all remarks ends here --%>
          </td>
          
          
          </tr>
      
    
         
         <logic:present name="arrList">
         <logic:notEmpty name="arrList">
         <logic:iterate id="arrListobj" name="arrList" indexId="count">
         
         <tr class="white1">
         <logic:equal name="arrListobj" property="status" value="A">
         <td><input type="checkbox" name="chk" id="chk" value="${arrListobj.status}" /></td>
         </logic:equal>
         <logic:equal name="arrListobj" property="status" value="H">
         <td><input type="checkbox" name="chk" id="chk" value="${arrListobj.status}" /></td>
         </logic:equal>
          <logic:equal name="arrListobj" property="status" value="L">
         <td><input type="checkbox" name="chk" id="chk" value="${arrListobj.status}" /></td>
         </logic:equal>
      
         <td>${arrListobj.date}</td>
         <td>${arrListobj.pdcDate}</td>
         <logic:equal name="arrListobj" property="instrumentType" value="Q">
         <td>PDC</td>
         </logic:equal>
          <logic:equal name="arrListobj" property="instrumentType" value="E">
         <td>ECS</td>
          </logic:equal>
          <logic:equal name="arrListobj" property="instrumentType" value="H">
         <td>NACH</td>
          </logic:equal>
         <logic:equal name="arrListobj" property="instrumentType" value="DIR">
         <td>Direct Debit</td>
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
          
            
           	<td ><input type="text" id="holdReason<%=count.intValue()+1 %>" class="text" name="holdReason" onkeypress="return isSpecialChar(event);" value="" maxlength="150"/> 
          
          </td>
          
         
          <html:hidden property="lbxLoanNoHID" styleId="lbxLoanNoHID" />
          <html:hidden property="status" styleId="status" value="${arrListobj.status}"/>
          <html:hidden property="instrumentID" styleId="instrumentID" value="${arrListobj.instrumentID}"/>
          </tr>

         </logic:iterate>
         </logic:notEmpty>
         </logic:present>
       </logic:present>
       
      <logic:present name="deleteAuthor">
      
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
          
          <td><b><bean:message key="lbl.deleteReason"/></b></td>
       
      
          </tr>
      
         <logic:present name="arrList">
         <logic:notEmpty  name="arrList">
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
         <td>NACH</td>
          </logic:equal>
         <logic:equal name="arrListobj" property="instrumentType" value="DIR">
         <td>Direct Debit</td>
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
          <td>${arrListobj.utilityNo} </td>
          <td>${arrListobj.holdReason}
         
          </td>

          <html:hidden property="lbxLoanNoHidden" styleId="lbxLoanNoHidden" value="lbxLoanNoHID"/>
            
          </tr>

         </logic:iterate>
         </logic:notEmpty>
         </logic:present>
       </logic:present>
  
          <tr>
          </tr> 
        </table>    </td>
  </tr>
     <logic:present name="deleteMaker">  
   <tr>
      <td colspan="4">&nbsp;
      
      <button type="button"  id="savenfor" class="topformbutton2" title="Alt+F" accesskey="F" onclick="return savenForPDCDeleteInstrument('<bean:message key="lbl.plsSelOneRecord"/>','<bean:message key="lbl.plsToggleOneRecord"/>','<bean:message key="msg.confirmationForwardMsg" />');"><bean:message key="button.forward" /></button>
               
     </td>
    </tr>
 
</logic:present>
</table>

<table width="100%" border="0" cellpadding="2" cellspacing="1">
    </table>
  </fieldset>
  <logic:present name="alertMsgfrsvnfrdel">
	<script type="text/javascript">
	if(('<%=request.getAttribute("alertMsgfrsvnfrdel")%>') == 'Y'){
	 alert("PDC forwarded successfully for delete");
	}
	if(('<%=request.getAttribute("alertMsgfrsvnfrdel")%>') == 'N'){
	 alert("PDC not forwarded successfully for delete");
	}
	
	</script>
	</logic:present>
  
  </html:form>
</div>

</div>
<script>
	setFramevalues("sourcingForm");
</script>
</body>
</html:html>