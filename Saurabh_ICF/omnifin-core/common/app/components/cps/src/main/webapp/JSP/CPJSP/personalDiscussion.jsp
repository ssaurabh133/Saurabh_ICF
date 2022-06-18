<!--  
Author Name:- Manisha Tomar
Date of Creation:- 22/07/2011
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
		
		 
	
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
		 <!-- css and jquery for Datepicker -->
	
		<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	    <script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		
		 <!-- css and jquery for Datepicker -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/creditProcessing.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/cpScript/underWriter.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
  		<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/checkMod.js"></script>
	
<logic:present name="back">

<script type="text/javascript">
  alert("You can't move without saving before Lead Details!!!");
</script>

</logic:present>

<style type="text/css">
		textarea {
/*border:1px solid #9BB168;*/
color:#000;
font-family:arial,serif;
font-size:13px;
padding-left:2px;
width:700px;
resize:none;
height:40px;
}
</style>
	
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
<body oncontextmenu="return false" onload="enableAnchor();checkChanges('pdForm');">

<div  align="center" class="opacity" style="display: none;"  id="processingImage">
</div>
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
	
	<fieldset>

<table cellSpacing=0 cellPadding=0 width="100%" border=0>
  <tr>
     <td class="gridtd">
 	  <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr class="white2">
          <td><b><bean:message key="lbl.dealNo" /></b></td>
          <td>${dealHeader[0].dealNo}</td>
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
	          <td >Under Writer</td>
          </tr>
        </table> 
</td>
</tr>
</table>
 
</fieldset>
<logic:notPresent name="strFlagDV">	
	<html:form styleId="pdForm" action="/personalDiscussBehindAction" method="post">
	     <html:errors/> 
	

<fieldset>	  



<legend><bean:message key="lbl.personalDiscussion" /></legend>

<html:hidden property="txnType" value="D"/>
<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath() %>"/>
  <input type="hidden" name="formatD" value="<bean:message key="lbl.dateFormat"/>" id="formatD" />
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
		    	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		        	<tr>
		            	<td><bean:message key="lbl.pdCode" /><font color="red">*</font></td>
		                <td>  
		                <html:text property="pdCode" styleId="pdCode" styleClass="text" value="Personal Discussion" disabled="true"/>									
			            <html:hidden property="hidpdCode" styleId="hidpdCode" styleClass="text" value="PD"/>
					    </td>
						
	                    <td><bean:message key="lbl.pdDateTime" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		                <td nowrap="nowrap" >
		                <html:text property="pdDate" styleId="pdDate" styleClass="text3" value="" maxlength="10" onchange="checkDate('pdDate');"/>
		                <html:text property="pdTime" styleId="pdTime" styleClass="text5" size="4" value="" maxlength="5" onchange="checkPdTime();" /> 24 Hrs. Format  
              			</td>
					</tr>
	  				<tr>
	  					<td><bean:message key="lbl.personMet" /><font color="red">*</font></td>
		                <td > 
	                    <html:text property="personMet" styleId="personMet" styleClass="text" value="" maxlength="100"/> </td>
	                    <td><bean:message key="lbl.pdMeetingLocation" /><font color="red">*</font></td>
		                <td > 
	                    <html:text property="pdMeetingLocation" styleId="pdMeetingLocation" styleClass="text" value="" maxlength="100"/> </td>
	 				</tr>
	 				<tr>
	 					<td><bean:message key="lbl.pdRemark" /><font color="red">*</font></td>
						<td colspan="5"><textarea name="pdRemark" id="pdRemark"  maxlength="1000" ></textarea></td>
		   				
		   			</tr>
		   			<tr>
	 					
		   				<td><bean:message key="lbl.followUp" /><font color="red">*</font></td>
		                <td>
	  					<html:select property="followUp" styleId="followUp" styleClass="text" value="" onchange="return cpNotepadDisable()">
   					    <html:option value="">--Select--</html:option>
   					    <html:option value="Y">Yes</html:option>
   					    <html:option value="N">No</html:option>
						</html:select></td>
		   			</tr>
		 			<tr>
		 				<td><bean:message key="lbl.followupDateTime" /><bean:message key="lbl.dateFormat(dd-mm-yyyy)"/><font color="red">*</font></td>
		                <td nowrap="nowrap">
		                <div id="notshowDate" style="display: none; float: left;">
		                <input type="text"  id="followupDate1" class="text6" value="" maxlength="10" disabled="disabled"/>
		                </div>
		                <div id="showDate" style="float: left;">
		                <html:text property="followupDate"  styleId="followupDate" styleClass="text3" value="" maxlength="10" onchange="checkDate('followupDate');"/>
		                </div>
		                <html:text property="followupTime" styleId="followupTime" styleClass="text5" size="4" value="" maxlength="5" onchange="followTime()"/> 24 Hrs. Format
              			</td>
		   				<td><bean:message key="lbl.followUpPerson" /><font color="red">*</font></td>
		                <td> 
	                    <html:text property="followUpPerson" styleId="followUpPerson" styleClass="text" value="" maxlength="100"/> </td>

		   			</tr>
		   			<tr>
		   				<td><bean:message key="lbl.followUpLocation" /><font color="red">*</font></td>
		                <td> 
	                    <html:text property="followUpLocation" styleId="followUpLocation" styleClass="text" value="" maxlength="100"/> </td>
						<td><bean:message key="lbl.followupRemarks" /><font color="red">*</font></td>
						<td ><textarea name="followupRemarks" id="followupRemarks" class="text" maxlength="1000" ></textarea></td>
						
		   			</tr>
				</table>
			</td>
		</tr>
		<tr>
		   	<td>
	       <button type="button" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return onSaveofPD('<bean:message key="msg.PDDateGrtrFd" />');" ><bean:message key="button.save" /></button>
	       </td>
		</tr>
	</table>
</fieldset>
	
</html:form>

</logic:notPresent>
</div>
<fieldset>

<legend><bean:message key="lbl.personalDiscussionDetails" /></legend>

	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="gridtd">
				<table width="100%" border="0" cellspacing="1" cellpadding="1">
	               <tr class="white2">
      					<td ><strong><bean:message key="lbl.pdCode"/></strong></td>
        				<td ><strong><bean:message key="lbl.pdDateTime"/></strong></td>
						<td ><strong><bean:message key="lbl.personMet"/></strong></td>
						<td ><strong><bean:message key="lbl.pdMeetingLocation"/></strong></td>
						<td ><strong><bean:message key="lbl.pdRemark"/></strong></td>
        				<td ><strong><bean:message key="lbl.followUp"/></strong></td>
        				<td ><strong><bean:message key="lbl.followupDateTime"/></strong></td>
        				<td ><strong><bean:message key="lbl.followUpPerson"/></strong></td>
        				<td ><strong><bean:message key="lbl.followUpLocation"/></strong></td>        			
        				<td><strong><bean:message key="lbl.followupRemarks"/></strong></td>
        				<td><strong><bean:message key="lbl.userName"/></strong></td>
        				<td><strong><bean:message key="lbl.creationDateTime"/></strong></td>
    				</tr>
	  				<logic:present name="showPDList">
		 			<logic:iterate name="showPDList" id="sublist">
					<tr class="white1">
		     			<td >${sublist.pdCode}</td>
			 			<td >${sublist.pdDate}</td>
			 			<td >${sublist.personMet}</td>
			 			<td >${sublist.pdMeetingLocation}</td>
			 			<td >${sublist.pdRemark}</td>
			 			<td >${sublist.followUp}</td>
			 			<td >${sublist.followupDate}</td>
			 			<td >${sublist.followUpPerson}</td>
			 			<td >${sublist.followUpLocation}</td>			 		
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
	setFramevalues("pdForm");
</script>

</body>
</html:html>
<logic:present name="msg">
		<script type="text/javascript">
		
		if('<%=request.getAttribute("msg").toString()%>'=='S')
		{
			alert("<bean:message key="lbl.dataSave" />");
		
		}
		else 
		{
			alert("<bean:message key="lbl.errorSuccess" />");
			
		}
		
		
		</script>
		</logic:present>	
