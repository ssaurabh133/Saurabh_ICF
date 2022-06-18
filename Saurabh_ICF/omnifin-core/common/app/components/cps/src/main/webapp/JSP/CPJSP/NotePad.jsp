<!--  
Author Name:- Amit Kumar
Date of Creation:- 14/04/2011
Purpose:-  The purpose of this page is to enter notes and follow up Remarks.
-->


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
		 
		 
	
		<link type="text/css" href="<%=request.getContextPath() %>/css/toolTip.css" rel="stylesheet" />

		<!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/jquery.simpletip-1.3.1.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
		<script type="text/javascript" src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/gcdScript/customer_address.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/notePad.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	
<logic:present name="back">

<script type="text/javascript">
  alert("You can't move without saving before Lead Details!!!");
</script>

</logic:present>
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
<body oncontextmenu="return false" onload="enableAnchor();checkChanges('notepadForm');document.getElementById('notepadForm').noteCode.focus();">
	
	<div  align="center" class="opacity" style="display:none" id="processingImage" ></div>
	
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
	<logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
<div id="centercolumn">
	
	<div id="revisedcontainer">
	<logic:notPresent name="viewDeal">
	<html:form styleId="notepadForm" action="/notepad" method="post">
	<logic:equal name="status" value="np">
      <fieldset>
     <table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Deal Capturing</td>
          </tr>
        </table> 
</td>
</tr>
</table>
</fieldset>
</logic:equal>
<logic:notEqual name="status" value="np">
<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table>
</td>
</tr>
</table>
 
</fieldset>	
</logic:notEqual>
<logic:notPresent name="strFlagDV">
<fieldset>	  
<legend><bean:message key="lbl.notepad" /></legend>

<logic:equal name="status" value="np">
	<html:hidden property="txnType" value="DC"/>
</logic:equal>
<logic:notEqual name="status" value="np">
	<html:hidden property="txnType" value="LIM"/>
</logic:notEqual>

<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	<td width="20%"><bean:message key="lbl.noteCode" /><font color="red">*</font></td>
		                <td width="35%"> 
	         			<logic:present name="noteCode">
	            			<html:select property="noteCode" styleId="noteCode" value="" styleClass="text" >
							<option value="">--<bean:message key="lbl.select" />--</option>
								<html:optionsCollection name="noteCode" label="noteCodeDescription" value="noteCodeValue" />
			 				</html:select>
						</logic:present>
						</td>
			
	                    <td width="20%"><bean:message key="lbl.notesDateTime" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		                <td width="35%" nowrap="nowrap" >
		                <html:text property="meetingDate" styleId="date" styleClass="text3" value="${userobject.businessdate}" maxlength="10" onchange="checkDate('date');"/>
		                <html:text property="meetingTime" styleId="meetingTime" styleClass="text5" size="4" value="" maxlength="5" onchange="notepadTime()" /> 24 Hrs. Format  
              			</td>
					</tr>
	  				<tr>
	  					<td><bean:message key="lbl.personMet" /></td>
		                <td > 
	                    <html:text property="personMet" styleId="personMet" styleClass="text" value="" maxlength="100" onkeyup="return upperMe('personMet');"/> </td>
	                    <td><bean:message key="lbl.meetingLocation" /></td>
		                <td > 
	                    <html:text property="meetingLocation" styleId="meetingLocation" styleClass="text" value="" maxlength="100"/> </td>
	 				</tr>
	 				<tr>
	 					<td><bean:message key="lbl.notes" /><font color="red">*</font></td>
						<td ><textarea name="meetingRemarks" id="meetingRemarks" class="text" maxlength="1000" ></textarea></td>
		   				<td width="20%"><bean:message key="lbl.followUp" /><font color="red">*</font></td>
		                <td width="35%">
	  					<html:select property="followUp" styleId="followUp" styleClass="text" value="" onchange="return cpNotepadDisable()">
   					    <html:option value="">--Select--</html:option>
   					    <html:option value="Y">Yes</html:option>
   					    <html:option value="N">No</html:option>
						</html:select></td>
						
		   			</tr>
		   			</table>
		   			</td></tr><tr><td>
		   			<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 			<tr>
		 				<td width="20%"><bean:message key="lbl.followupDateTime" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		                <td nowrap="nowrap" width="35%" >
		                <div id="notshowDate" style="display: none; float: left;">
		                <input type="text"  id="followupDate1" class="text3" value="" maxlength="10" disabled="disabled"/>
		                </div>
		                <div id="showDate" style="float: left;">
		                <html:text property="followupDate"  styleId="followupDate" styleClass="text3" value="" maxlength="10" onchange="checkDate('followupDate');"/>
		                </div>
		                <html:text property="followupTime" styleId="followupTime" styleClass="text5" size="4" value="" maxlength="5" onchange="followTime()"/> 24 Hrs. Format
		                
              			</td>
		   				<td width="20%"><bean:message key="lbl.followUpPerson" /><font color="red">*</font></td>
		                <td width="35%" > 
	                    <html:text property="followUpPerson" styleId="followUpPerson" styleClass="text" value="" maxlength="100" onkeyup="return upperMe('followUpPerson');"/> </td>
		   				
		   			</tr>
		   			<tr>
		   				<td><bean:message key="lbl.followUpLocation" /><font color="red">*</font></td>
		                <td > 
	                    <html:text property="followUpLocation" styleId="followUpLocation" styleClass="text" value="" maxlength="100"/> </td>
						<td><bean:message key="lbl.followupRemarks" /><font color="red">*</font></td>
						<td ><textarea name="followupRemarks" id="followupRemarks" class="text" maxlength="1000" ></textarea></td>
						
		   			</tr>
				</table>
			</td>
		</tr>
		<tr>
		   	<td align="left" >		   	
		    <button type="button"  class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveNotepadData('<bean:message key="msg.DateGrtrFd" />');" ><bean:message key="button.save" /></button></td>
		   	<td align="left" >&nbsp;</td>
		   	<td align="left" >&nbsp;</td>
		   	<td >&nbsp;</td>
		</tr>
	</table>
</fieldset>
</logic:notPresent>	
</html:form>
</logic:notPresent>

	<logic:present name="viewDeal">
	
	<html:form styleId="notepadForm" action="/notepad" method="post">
	
     <fieldset>
    <logic:present name="dealId">
	  <table cellspacing="0" cellpadding="0" width="100%" border="0">
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${dealHeader[0].dealNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${dealHeader[0].dealDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${dealHeader[0].dealCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${dealHeader[0].dealProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${dealHeader[0].dealProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${dealHeader[0].dealScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Deal Capturing</td>
          </tr>
        </table> 
</td>
</tr>
</table>
</logic:present>


<logic:present name="loanId">

<table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td ><b><bean:message key="lbl.dealNo" /></b></td>
          <td >${loanHeader[0].dealLoanNo}</td>
           <td ><b><bean:message key="lbl.loanNo" /></b></td>
          <td >${loanHeader[0].loanNo}</td>
          <td><b><bean:message key="lbl.date"/></b></td>
          <td>${loanHeader[0].loanDate}</td>
          <td><b><bean:message key="lbl.customerName"/></b> </td>
          <td colspan="3" >${loanHeader[0].loanCustomerName}</td>
         </tr>
          <tr class="white2">
	          <td><b><bean:message key="lbl.productType"/></b></td>
	          <td >${loanHeader[0].loanProductCat}</td>
	          <td ><b><bean:message key="lbl.product"/></b></td>
	          <td >${loanHeader[0].loanProduct}</td>
	          <td ><b><bean:message key="lbl.scheme"/></b></td>
	          <td>${loanHeader[0].loanScheme}</td>
	          <td><b><bean:message key="lbl.currentStage"/></b></td>
	          <td >Loan Initiation</td>
          </tr>
        </table>
</td>
</tr>
</table>
 </logic:present>
</fieldset>
<fieldset>	  
<legend><bean:message key="lbl.notepad" /></legend>

<html:hidden property="txnType" value="DC"/>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	<td width="20%"><bean:message key="lbl.noteCode" /></td>
		                <td width="35%"> 
	         			<logic:present name="noteCode">
	            			<html:select property="noteCode" styleId="noteCode" styleClass="text" value="" disabled="true">
							<option value="">--<bean:message key="lbl.select" />--</option>
								<html:optionsCollection name="noteCode" label="noteCodeDescription" value="noteCodeValue" />
			 				</html:select>
						</logic:present>
						</td>
			
	                    <td width="20%"><bean:message key="lbl.notesDateTime" /></td>
		                <td width="35%" nowrap="nowrap" >
		                <html:text property="meetingDate" disabled="true" styleClass="text3" value="" maxlength="100"/>
		                <html:text property="meetingTime" styleId="meetingTime" styleClass="text5" size="4" value="" maxlength="5" onchange="notepadTime()" disabled="true" /> 24 Hrs. Format  
              			</td>
					</tr>
	  				<tr>
	  					<td><bean:message key="lbl.personMet" /></td>
		                <td > 
	                    <html:text property="personMet" styleId="personMet" styleClass="text" value="" maxlength="100" disabled="true"/> </td>
	                    <td><bean:message key="lbl.meetingLocation" /></td>
		                <td > 
	                    <html:text property="meetingLocation" styleId="meetingLocation" styleClass="text" value="" maxlength="100" disabled="true"/> </td>
	 				</tr>
	 				<tr>
	 					<td><bean:message key="lbl.notes" /></td>
						<td ><html:textarea property="meetingRemarks" styleId="meetingRemarks" rows="." styleClass="text" cols="60" value="" disabled="true"/></td>
		   				<td width="20%"><bean:message key="lbl.followUp" /></td>
		                <td width="35%">
	  					<html:select property="followUp" styleId="followUp" styleClass="text" value="" onchange="return cpNotepadDisable()" disabled="true">
   					    <html:option value="">--Select--</html:option>
   						<html:option value="Y">Yes</html:option>
   					    <html:option value="N">No</html:option>
						</html:select></td>
		   			</tr>
		   			</table>
		   			</td></tr><tr><td>
		   			<table width="100%" border="0" cellspacing="1" cellpadding="1">
		 			<tr>
		 				<td width="20%"><bean:message key="lbl.followupDateTime" /></td>
		                <td nowrap="nowrap" width="35%" >
		                <html:text property="followupDate" disabled="true" styleClass="text3" value="" maxlength="100"/>
		                <html:text property="followupTime" styleId="followupTime" disabled="true" styleClass="text5" size="4" value="" maxlength="5" onchange="followTime()"/> 24 Hrs. Format
              			</td>
		   				<td width="20%"><bean:message key="lbl.followUpPerson" /></td>
		                <td width="35%" > 
	                    <html:text property="followUpPerson" styleId="followUpPerson" disabled="true" styleClass="text" value="" maxlength="100"/> </td>
		   				
		   			</tr>
		   			<tr>
		   				<td><bean:message key="lbl.followUpLocation" /></td>
		                <td > 
	                    <html:text property="followUpLocation" styleId="followUpLocation" disabled="true" styleClass="text" value="" maxlength="100"/> </td>
						<td><bean:message key="lbl.followupRemarks" /></td>
						<td ><html:textarea property="followupRemarks" styleId="followupRemarks" disabled="true" rows="." styleClass="text" cols="60" value="" /></td>
						
		   			</tr>
				</table>
			</td>
		</tr>
	</table>
</fieldset>
	
</html:form>
</logic:present>
</div>
<fieldset>
<legend><bean:message key="lbl.notepadDetails" /></legend>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
    				<tr class="white2">
      					<td><strong><bean:message key="lbl.noteCode"/></strong></td>
        				<td><strong><bean:message key="lbl.meetingDateTime"/></strong></td>
						<td><strong><bean:message key="lbl.personMet"/></strong></td>
						<td><strong><bean:message key="lbl.meetingLocation"/></strong></td>
						<td><strong><bean:message key="lbl.meetingRemarks"/></strong></td>
        				<td><strong><bean:message key="lbl.followUp"/></strong></td>
        				<td><strong><bean:message key="lbl.followupDateTime"/></strong></td>
        				<td><strong><bean:message key="lbl.followUpPerson"/></strong></td>
        				<td><strong><bean:message key="lbl.followUpLocation"/></strong></td>        				
        				<td><strong><bean:message key="lbl.followupRemarks"/></strong></td>
        				<td><strong><bean:message key="lbl.userName"/></strong></td>
        				<td><strong><bean:message key="lbl.creationDateTime"/></strong></td>
    				</tr>
    				
    				
	  				<logic:present name="list">
		 			<logic:iterate name="list" id="sublist">
					<tr class="white1">
		     			<td>${sublist.noteCodeDescription}</td>
			 			<td>${sublist.meetingDate}</td>
			 			<td>${sublist.personMet}</td>
			 			<td>${sublist.meetingLocation}</td>
			 			<td>${sublist.meetingRemarks}</td>
			 			<td>${sublist.followUp}</td>
			 			<td>${sublist.followupDate}</td>
			 			<td>${sublist.followUpPerson}</td>
			 			<td>${sublist.followUpLocation}</td>
			 			<td>${sublist.followupRemarks}</td>
			 			<td>${sublist.userName}</td>
			 			<td>${sublist.creationDate}</td>
	      			</tr>
	    			</logic:iterate>
	   				</logic:present>
	   				<logic:present name="listInloan">
		 			<logic:iterate name="listInloan" id="sublist">
					<tr class="white1">
		     			<td>${sublist.noteCodeDescription}</td>
			 			<td>${sublist.meetingDate}</td>
			 			<td>${sublist.personMet}</td>
			 			<td>${sublist.meetingLocation}</td>
			 			<td>${sublist.meetingRemarks}</td>
			 			<td>${sublist.followUp}</td>
			 			<td>${sublist.followupDate}</td>
			 			<td>${sublist.followUpPerson}</td>
			 			<td>${sublist.followUpLocation}</td>
			 			<td>${sublist.followupRemarks}</td>
			 			<td>${sublist.userName}</td>
			 			<td>${sublist.creationDate}</td>
	      			</tr>
	    			</logic:iterate>
	   				</logic:present>
	   				<logic:present name="leadList">
		 			<logic:iterate name="leadList" id="sublist">
					<tr class="white1">
		     			<td>${sublist.noteCodeDescription}</td>
			 			<td>${sublist.meetingDate}</td>
			 			<td>${sublist.personMet}</td>
			 			<td>${sublist.meetingLocation}</td>
			 			<td>${sublist.meetingRemarks}</td>
			 			<td>${sublist.followUp}</td>
			 			<td>${sublist.followupDate}</td>
			 			<td>${sublist.followUpPerson}</td>
			 			<td>${sublist.followUpLocation}</td>
			 			<td>${sublist.followupRemarks}</td>
			 			<td>${sublist.userName}</td>
			 			<td>${sublist.creationDate}</td>
	      			</tr>
	    			</logic:iterate>
	   				</logic:present>
 				</table>
			</td>
		</tr>
	</table>

</fieldset>


</div>
<script>
	setFramevalues("notepadForm");
</script>

<div  align="center" class="opacity" style="display: none;"  id="processingImage">
		
</div>
</body>
</html:html>

<logic:present name="sms">
<script type="text/javascript">
	if('<%=request.getAttribute("sms").toString()%>'=='S')
	{
		alert('<bean:message key="msg.notepadSaved" />');
	}
	else if('<%=request.getAttribute("sms").toString()%>'=='E')
	{
		alert('<bean:message key="msg.notepadError" />');
	}
</script>
</logic:present>